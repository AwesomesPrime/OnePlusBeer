package frontend.controller;

import com.jfoenix.controls.*;
import controller.EntityController;
import entities.*;
import entities.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import orm.EmployeeDatabaseService;
import orm.StandDatabaseService;
import utilities.AlerterMessagePopup;

import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.HOURS;

public class EditEmployeePlanController implements Initializable {

    private final AlerterMessagePopup popup = new AlerterMessagePopup();

    @FXML
    private JFXTextField txtTravelStart, txtTravelDistance, txtTravelExpenses,txtTimePause, txtBonus;

    @FXML
    private JFXTimePicker timeStart, timeEnd;

    @FXML
    private JFXDatePicker dateStart, dateEnd;

    @FXML
    private JFXTextArea txtComment;

    @FXML
    private JFXComboBox<Employee> cbEmployee;

    @FXML
    private JFXComboBox<StandPlan> cbStandPlan;

    @FXML
    private ScrollPane editRPPane;

    private EmployeePlan employeePlan;
    private final EntityController controller = new EntityController();

    @FXML
    public void initialize(URL url, ResourceBundle rb){
        EmployeeDatabaseService employeeDatabaseService = new EmployeeDatabaseService();

        ObservableList<Employee> employeeList = FXCollections.observableList(employeeDatabaseService.getAll(Employee.class));
        cbEmployee.setItems(employeeList);

        StandDatabaseService standDatabaseService = new StandDatabaseService();

        ObservableList<StandPlan> standPlanList = FXCollections.observableList(standDatabaseService.getAll(StandPlan.class));
        cbStandPlan.setItems(standPlanList);
    }

    /**
     * Liest eingegebenen Daten aus Event view
     *  event Event Entität
     */
    public void setDataFromView(EmployeePlan employeePlan) {

        this.employeePlan = employeePlan;

        EmployeeDatabaseService employeeDatabaseService = new EmployeeDatabaseService();
        StandDatabaseService standDatabaseService = new StandDatabaseService();

        cbStandPlan.getSelectionModel().select(indexOfStandInList(standDatabaseService.getAll(StandPlan.class),employeePlan.getStandPlan().getId()));
        cbEmployee.getSelectionModel().select(indexOfEmployeeInList(employeeDatabaseService.getAll(Employee.class),employeePlan.getEmployee().getId()));
        txtTravelStart.setText(employeePlan.getTravelStart());
        txtTravelDistance.setText(Double.toString(employeePlan.getTravelDistance()));
        txtTravelExpenses.setText(Double.toString(employeePlan.getTravelExpenses()));
        timeStart.setValue(employeePlan.getStartWorkingTime()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalTime());
        dateStart.setValue(employeePlan.getStartWorkingTime()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        timeEnd.setValue(employeePlan.getEndWorkingTime()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalTime());
        dateEnd.setValue(employeePlan.getEndWorkingTime()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        txtTimePause.setText(Long.toString(employeePlan.getPauseTime()));
        txtComment.setText((employeePlan.getComment()));
        txtBonus.setText(Double.toString(employeePlan.getBonus()));
    }

    /**
     * Editieren des übergebenen Events
     *  event Event Entität
     */

    @FXML
    public void apply(ActionEvent event){
        try{
            if(validateWorktimeForMiniJober() && validateLegalBreaKTime()){
                controller.save(EmployeePlan.class, generate());
                Stage stage = (Stage) editRPPane.getScene().getWindow();
                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                popup.generateInformationPopupWindow("Einsatzplan wurde verarbeitet.");
            }
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
    }

    private boolean validateLegalBreaKTime() {
        long worktime = calcWorkingTimeInHours();
        return (worktime > 8 && Long.valueOf(txtTimePause.getText()) > 60) || (worktime > 9 && Long.valueOf(txtTimePause.getText()) > 75) || worktime >= 10;
    }

    private Date getDateFromPickers(JFXDatePicker datePicker){
        Calendar date = Calendar.getInstance();
        date.set(datePicker.getValue().getYear(),
                datePicker.getValue().getMonthValue()-1,
                datePicker.getValue().getDayOfMonth());

        return date.getTime();
    }


    private Date getDateFromPickers(JFXDatePicker datePicker, JFXTimePicker timePicker){
        Calendar date = Calendar.getInstance();
        date.set(datePicker.getValue().getYear(),
                datePicker.getValue().getMonthValue()-1,
                datePicker.getValue().getDayOfMonth(),
                timePicker.getValue().getHour(),
                timePicker.getValue().getMinute(),
                timePicker.getValue().getSecond());

        return date.getTime();
    }

    private EmployeePlan generate() {

        if(this.employeePlan == null){
            this.employeePlan =  new EmployeePlan();
        }

        employeePlan.setComment(txtComment.getText());
        employeePlan.setEmployee(cbEmployee.getValue());
        employeePlan.setEndWorkingTime(getDateFromPickers(dateStart, timeStart));
        employeePlan.setStartWorkingTime(getDateFromPickers(dateEnd, timeEnd));
        employeePlan.setPauseTime(Long.valueOf(txtTimePause.getText()).longValue());
        employeePlan.setStandPlan(cbStandPlan.getValue());
        employeePlan.setTravelDistance(Double.parseDouble(txtTravelDistance.getText()));
        employeePlan.setTravelExpenses(Double.parseDouble(txtTravelExpenses.getText()));
        employeePlan.setTravelStart(txtTravelStart.getText());
        employeePlan.setBonus(Double.valueOf(txtBonus.getText()).doubleValue());

        return employeePlan;
    }

    private void validateInput() {
        validateWorktimeForMiniJober();
    }

    private long calcWorkingTimeInHours() {
        return HOURS.between(timeStart.getValue(), timeEnd.getValue());
    }

    private boolean validateWorktimeForMiniJober() {
        Employee employee = cbEmployee.getValue();
        if( employee.getStateByEmploymentLaw().getIncomeMax() == 450 ) {
            long planedWorkTimeForEmployee = getEffectivWorkingTime();
            Event event = cbStandPlan.getValue().getEvent();
            Double workedTimeHours = employee.getWorkedTimeHoursInMonth(event.getStartDate().getMonth(), event.getEndDate().getYear());

            double possibleHoursPerMonth = 450/employee.getBruttoPerHour();
            double remainingPossibleWorkingHours = possibleHoursPerMonth - workedTimeHours;
            if((remainingPossibleWorkingHours - planedWorkTimeForEmployee) <= 0){
              popup.generateErrorPopupWindow("Die für den Mitarbeiter " + employee.getFullName() + " geplannte Arbeitszeit überschreitet die mögliche Zeit.");
              return false;
            }
        }
        return true;
    }

    private long getEffectivWorkingTime() {
        return calcWorkingTimeInHours() - Long.valueOf(txtTimePause.getText());
    }

    private int indexOfEmployeeInList(List<Employee> employeeList, int employeeId) {
        for (int i = 0; i <= employeeList.size(); i++) {
            if(employeeList.get(i).getId() == employeeId){
                return i;
            }
        }
        return -1;
    }

    private int indexOfStandInList(List<StandPlan> standPlanList, int standId) {
        for (int i = 0; i <= standPlanList.size(); i++) {
            if(standPlanList.get(i).getId() == standId){
                return i;
            }
        }
        return -1;
    }

}

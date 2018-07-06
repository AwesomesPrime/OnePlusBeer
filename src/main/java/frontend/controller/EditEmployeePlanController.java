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
import orm.EventDatabaseService;
import orm.StandDatabaseService;
import utilities.AlerterMessagePopup;
import validation.InputValidation;

import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.HOURS;

public class EditEmployeePlanController implements Initializable {

    private final InputValidation inputValidation = new InputValidation();
    private final AlerterMessagePopup popup = new AlerterMessagePopup();

    @FXML
    private JFXTextField txtTravelStart, txtTravelDistance, txtTravelExpenses,txtTimePause;

    @FXML
    private JFXTimePicker timeStart, timeEnd;

    @FXML
    private JFXDatePicker dateStart, dateEnd;

    @FXML
    private JFXTextArea txtComment;

    @FXML
    private JFXComboBox<Employee> cbEmployee;

    @FXML
    private JFXComboBox<StandPlan> cbStand;

    @FXML
    private JFXComboBox<Event> cbEvent;

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
        cbStand.setItems(standPlanList);

        EventDatabaseService eventDatabaseService = new EventDatabaseService();

        ObservableList<Event> eventList = FXCollections.observableList(eventDatabaseService.getAll(Event.class));
        cbEvent.setItems(eventList);
    }

    /**
     * Liest eingegebenen Daten aus Event view
     *  event Event Entität
     */
    public void setDataFromView(EmployeePlan rp) {

        this.employeePlan = rp;

        EmployeeDatabaseService employeeDatabaseService = new EmployeeDatabaseService();
        StandDatabaseService standDatabaseService = new StandDatabaseService();

        cbStand.getSelectionModel().select(indexOfStandInList(standDatabaseService.getAll(StandPlan.class),rp.getStandPlan().getId()));
        cbEmployee.getSelectionModel().select(indexOfEmployeeInList(employeeDatabaseService.getAll(Employee.class),rp.getEmployee().getId()));
        txtTravelStart.setText(rp.getTravelStart());
        txtTravelDistance.setText(Double.toString(rp.getTravelDistance()));
        txtTravelExpenses.setText(Double.toString(rp.getTravelExpenses()));
        timeStart.setValue(rp.getStartWorkingTime()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalTime());
        dateStart.setValue(rp.getStartWorkingTime()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        timeEnd.setValue(rp.getEndWorkingTime()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalTime());
        dateEnd.setValue(rp.getEndWorkingTime()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        txtTimePause.setText(Long.toString(rp.getPauseTime()));
        txtComment.setText((rp.getComment()));
    }

    /**
     * Editieren des übergebenen Events
     *  event Event Entität
     */

    @FXML
    public void apply(ActionEvent event){
        try{
            if(validateWorktimeForMiniJober() && validateLegalBreaKTime()){
                if(this.employeePlan == null) {
                    EmployeePlan plan = new EmployeePlan();
                    controller.save(EmployeePlan.class, generateResourcePlan(plan));
                } else {
                    controller.save(EmployeePlan.class, generateResourcePlan(this.employeePlan));
                }
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

    private EmployeePlan generateResourcePlan(EmployeePlan plan) {

        plan.setComment(txtComment.getText());
        plan.setEmployee(cbEmployee.getValue());
        plan.setEndWorkingTime(getDateFromPickers(dateStart, timeStart));
        plan.setStartWorkingTime(getDateFromPickers(dateEnd, timeEnd));
        plan.setPauseTime(Long.valueOf(txtTimePause.getText()).longValue());
        plan.setStandPlan(cbStand.getValue());
        plan.setTravelDistance(Double.parseDouble(txtTravelDistance.getText()));
        plan.setTravelExpenses(Double.parseDouble(txtTravelExpenses.getText()));
        plan.setTravelStart(txtTravelStart.getText());

        return plan;
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
            Event event = cbEvent.getValue();
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

    private int indexOfEventInList(List<Event> eventList, int eventId) {
        for (int i = 0; i <= eventList.size(); i++) {
            if(eventList.get(i).getId() == eventId){
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

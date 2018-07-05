package frontend.resourcePlanning;

import com.jfoenix.controls.*;
import controller.ResourcePlanningController;
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

import java.awt.*;
import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class EditResourcePlanningController implements Initializable {

    private final InputValidation inputValidation = new InputValidation();
    private final AlerterMessagePopup popup = new AlerterMessagePopup();

    @FXML
    private JFXTextField txtTravelStart, txtTravelDistance, txtTravelExpenses,txtTimePause;

    @FXML
    private JFXTimePicker timeStart, timeEnd;

    @FXML
    private JFXTextArea txtComment;

    @FXML
    private JFXComboBox<Employee> cbEmployee;

    @FXML
    private JFXComboBox<Stand> cbStand;

    @FXML
    private JFXComboBox<Event> cbEvent;

    @FXML
    private ScrollPane editRPPane;

    private ResourcePlanning resourcePlanning;
    private final ResourcePlanningController resourcePlanningController = new ResourcePlanningController();

    @FXML
    public void initialize(URL url, ResourceBundle rb){
        EmployeeDatabaseService employeeDatabaseService = new EmployeeDatabaseService();

        ObservableList<Employee> employeeList = FXCollections.observableList(employeeDatabaseService.getAll(Employee.class));
        cbEmployee.setItems(employeeList);

        StandDatabaseService standDatabaseService = new StandDatabaseService();

        ObservableList<Stand> standList = FXCollections.observableList(standDatabaseService.getAll(Stand.class));
        cbStand.setItems(standList);

        EventDatabaseService eventDatabaseService = new EventDatabaseService();

        ObservableList<Event> eventList = FXCollections.observableList(eventDatabaseService.getAll(Event.class));
        cbEvent.setItems(eventList);
    }

    /**
     * Liest eingegebenen Daten aus Event view
     *  event Event Entität
     */
    public void getDataFromRPView(ResourcePlanning rp) {

        this.resourcePlanning = rp;

        EmployeeDatabaseService employeeDatabaseService = new EmployeeDatabaseService();
        StandDatabaseService standDatabaseService = new StandDatabaseService();
        EventDatabaseService eventDatabaseService = new EventDatabaseService();

        cbStand.getSelectionModel().select(indexOfStandInList(standDatabaseService.getAll(Stand.class),rp.getStand().getId()));
        cbEmployee.getSelectionModel().select(indexOfEmployeeInList(employeeDatabaseService.getAll(Employee.class),rp.getEmployee().getId()));
        cbEvent.getSelectionModel().select(indexOfEventInList(eventDatabaseService.getAll(Event.class),rp.getEvent().getId()));
        txtTravelStart.setText(rp.getTravelStart());
        txtTravelDistance.setText(Double.toString(rp.getTravelDistance()));
        txtTravelExpenses.setText(Double.toString(rp.getTravelExpenses()));
        timeStart.setValue(rp.getStartWorkingTime()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalTime());
        timeEnd.setValue(rp.getEndWorkingTime()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalTime());
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
            validateInput();
            if(this.resourcePlanning == null) {
                ResourcePlanning plan = new ResourcePlanning();
                resourcePlanningController.addResourcePlan(generateResourcePlan(plan));
            } else {
                resourcePlanningController.addResourcePlan(generateResourcePlan(this.resourcePlanning));
            }
            Stage stage = (Stage) editRPPane.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            popup.generateInformationPopupWindow("Einsatzplan wurde verarbeitet.");
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
    }



    private ResourcePlanning generateResourcePlan(ResourcePlanning plan) {


        Calendar startDate = Calendar.getInstance();
        startDate.set(
                timeStart.getValue().getHour(),
                timeStart.getValue().getMinute(),
                timeStart.getValue().getSecond());
        Calendar endDate = Calendar.getInstance();
        endDate.set(
                timeEnd.getValue().getHour(),
                timeEnd.getValue().getMinute(),
                timeEnd.getValue().getSecond());


        plan.setComment(txtComment.getText());
        plan.setEmployee(cbEmployee.getValue());
        plan.setEndWorkingTime(endDate.getTime());
        plan.setStartWorkingTime(startDate.getTime());
        plan.setEvent(cbEvent.getValue());
        plan.setPauseTime(Long.valueOf(txtTimePause.getText()).longValue());
        plan.setStand(cbStand.getValue());
        plan.setTravelDistance(Double.parseDouble(txtTravelDistance.getText()));
        plan.setTravelExpenses(Double.parseDouble(txtTravelExpenses.getText()));
        plan.setTravelStart(txtTravelStart.getText());

        return plan;
    }

    private void validateInput() {

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

    private int indexOfStandInList(List<Stand> standList, int standId) {
        for (int i = 0; i <= standList.size(); i++) {
            if(standList.get(i).getId() == standId){
                return i;
            }
        }
        return -1;
    }

}

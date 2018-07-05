package frontend.resourcePlanning;

import com.jfoenix.controls.*;
import controller.EventController;
import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import orm.EmployeeDatabaseService;
import orm.EventDatabaseService;
import orm.StandDatabaseService;
import orm.StateByEmploymentLawDatabaseService;
import utilities.AlerterMessagePopup;
import validation.InputValidation;

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
    }

    /**
     * Editieren des übergebenen Events
     *  event Event Entität
     */
    @FXML
    public void apply(ActionEvent event){
        try{
            popup.generateInformationPopupWindow(cbEmployee.getSelectionModel().getSelectedItem().getFirstName()+" wurde NICHT verarbeitet.");
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
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

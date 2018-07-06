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
import orm.StandDatabaseService;
import orm.EventDatabaseService;
import utilities.AlerterMessagePopup;
import validation.InputValidation;

import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.HOURS;

public class EditStandPlanController implements Initializable {

    private final InputValidation inputValidation = new InputValidation();
    private final AlerterMessagePopup popup = new AlerterMessagePopup();

    @FXML
    private JFXTextField street, plz, city;

    @FXML
    private JFXTimePicker openingTime, closingTime;

    @FXML
    private JFXComboBox<Event> event;

    @FXML
    private JFXComboBox<Stand> stand;

    @FXML
    private ScrollPane editStandPlanPane;

    private StandPlan standPlan;
    private final EntityController controller = new EntityController();

    @FXML
    public void initialize(URL url, ResourceBundle rb){
        StandDatabaseService standDatabaseService = new StandDatabaseService();

        ObservableList<Stand> standList = FXCollections.observableList(standDatabaseService.getAll(Stand.class));
        stand.setItems(standList);

        EventDatabaseService eventDatabaseService = new EventDatabaseService();

        ObservableList<Event> eventList = FXCollections.observableList(eventDatabaseService.getAll(Event.class));
        event.setItems(eventList);
    }

    /**
     * Liest eingegebenen Daten aus Event view
     *  event Event Entität
     */
    public void setDataFromView(StandPlan standPlan) {

        this.standPlan = standPlan;

        StandDatabaseService standDatabaseService = new StandDatabaseService();
        EventDatabaseService eventDatabaseService = new EventDatabaseService();

        stand.getSelectionModel().select(indexOfStandInList(standDatabaseService.getAll(StandPlan.class),standPlan.getStand().getId()));
        event.getSelectionModel().select(indexOfEventInList(eventDatabaseService.getAll(Event.class), standPlan.getEvent().getId()));
        street.setText(standPlan.getStreet());
        plz.setText(standPlan.getPlz());
        city.setText(standPlan.getCity());
        openingTime.setValue(standPlan.getOpeningTime()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime());
        closingTime.setValue(standPlan.getClosingTime()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime());
    }

    /**
     * Editieren des übergebenen Events
     *  event Event Entität
     */

    @FXML
    public void apply(ActionEvent event){
        try{
            if(this.standPlan == null) {
                StandPlan plan = new StandPlan();
                controller.save(StandPlan.class, generateResourcePlan(plan));
            } else {
                controller.save(StandPlan.class, generateResourcePlan(this.standPlan));
            }
            Stage stage = (Stage) editStandPlanPane.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            popup.generateInformationPopupWindow("Einsatzplan wurde verarbeitet.");
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
    }


    private StandPlan generateResourcePlan(StandPlan plan) {


        Calendar startDate = Calendar.getInstance();
        startDate.set(
                openingTime.getValue().getHour(),
                openingTime.getValue().getMinute(),
                openingTime.getValue().getSecond());
        Calendar endDate = Calendar.getInstance();
        endDate.set(
                closingTime.getValue().getHour(),
                closingTime.getValue().getMinute(),
                closingTime.getValue().getSecond());


        plan.setStand(stand.getValue());
        plan.setEvent(event.getValue());
        plan.setStreet(street.getText());
        plan.setPlz(plz.getText());
        plan.setCity(city.getText());
        plan.setOpeningTime(startDate.getTime());
        plan.setClosingTime(endDate.getTime());

        return plan;
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

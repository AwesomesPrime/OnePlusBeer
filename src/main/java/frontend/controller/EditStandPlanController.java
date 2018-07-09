package frontend.controller;

import com.jfoenix.controls.*;
import controller.EntityController;
import entities.*;
import entities.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import orm.StandDatabaseService;
import orm.EventDatabaseService;
import usermanagement.ActiveUser;
import utilities.AlerterMessagePopup;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EditStandPlanController implements Initializable {

    private final AlerterMessagePopup popup = new AlerterMessagePopup();

    @FXML
    private JFXTextField street, plz, city;

    @FXML
    private JFXTimePicker openingTime, closingTime;

    @FXML
    private JFXDatePicker openingDate, closingDate;

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

        event.valueProperty().addListener(new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue<? extends Event> observable, Event oldValue, Event newValue) {
                openingDate.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        setDisable(empty
                                || date.isAfter(newValue.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                                || date.isBefore(newValue.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                                || date.isBefore(LocalDate.now()) && ActiveUser.getPermission()<=2);
                    }
                });
                openingDate.setValue(null);
                openingDate.setEditable(false);

                closingDate.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        setDisable(empty
                                || date.isAfter(newValue.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                                || date.isBefore(newValue.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                                || date.isBefore(LocalDate.now()) && ActiveUser.getPermission()<=2);
                    }
                });
                closingDate.setValue(null);
                closingDate.setEditable(false);

            }
        });

        openingTime._24HourViewProperty().setValue(true);
        closingTime._24HourViewProperty().setValue(true);
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
        openingDate.setValue(standPlan.getOpeningTime()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        closingDate.setValue(standPlan.getClosingTime()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
    }

    /**
     * Editieren des übergebenen Events
     *  event Event Entität
     */

    @FXML
    public void apply(ActionEvent event){
        Calendar startDate = getStartDate();
        Calendar endDate = getEndDate();
        Calendar startTime = getStartTime();
        Calendar endTime = getEndTime();
        if(plz.getText().matches("[\\d]{5}")){
            try{
                controller.save(StandPlan.class, generate());
                Stage stage = (Stage) editStandPlanPane.getScene().getWindow();
                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                popup.generateInformationPopupWindow("Einsatzplan wurde verarbeitet.");
            }
            catch(NumberFormatException e){
                popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
            }
        } else if(endDate.before(startDate)) {
            popup.generateInformationPopupWindow("Das Enddatum liegt vor dem Startdatum.");

        } else if (endTime.before(startDate)) {
            popup.generateInformationPopupWindow("Die Endzeit liegt vor der Startzeit.");

        } else {
            popup.generateInformationPopupWindow("Ihre Eingaben beinhalten Fehler. Daher konnte der Vorgang nicht gespeichert werden");

        }
    }

    private Calendar getStartDate() {
        Calendar startDate = Calendar.getInstance();
        startDate.set(
                openingDate.getValue().getYear(),
                openingDate.getValue().getMonthValue(),
                openingDate.getValue().getDayOfMonth());
        return startDate;
    }

    private Calendar getEndDate() {
        Calendar endDate = Calendar.getInstance();
        endDate.set(
                closingDate.getValue().getYear(),
                closingDate.getValue().getMonthValue(),
                closingDate.getValue().getDayOfMonth());
        return endDate;
    }

    private Calendar getStartTime() {
        Calendar start = Calendar.getInstance();
        start.set(
                openingTime.getValue().getHour(),
                openingTime.getValue().getMinute(),
                openingTime.getValue().getSecond());
        return start;
    }
    private Calendar getEndTime() {
        Calendar end = Calendar.getInstance();
        end.set(
                closingTime.getValue().getHour(),
                closingTime.getValue().getMinute(),
                closingTime.getValue().getSecond());
        return end;
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


    private StandPlan generate() {
        if(this.standPlan == null){
            this.standPlan =  new StandPlan();
        }
        standPlan.setStand(stand.getValue());
        standPlan.setEvent(event.getValue());
        standPlan.setStreet(street.getText());
        standPlan.setPlz(plz.getText());
        standPlan.setCity(city.getText());
        standPlan.setOpeningTime(getDateFromPickers(openingDate, openingTime));
        standPlan.setClosingTime(getDateFromPickers(closingDate, closingTime));

        return standPlan;
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

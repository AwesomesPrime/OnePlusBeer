package frontend.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import controller.EntityController;
import entities.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utilities.AlerterMessagePopup;

import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.ResourceBundle;

public class EditEventController implements Initializable {

    private final AlerterMessagePopup popup = new AlerterMessagePopup();

    @FXML
    private AnchorPane eventPane;

    @FXML
    private JFXTextField txtStrasse;

    @FXML
    private JFXTextField txtHausNr;

    @FXML
    private JFXTextField txtOrt;

    @FXML
    private JFXTextField txtPLZ;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXDatePicker dateStart;

    @FXML
    private JFXTimePicker timeStart;

    @FXML
    private JFXDatePicker dateEnd;

    @FXML
    private JFXTimePicker timeEnd;

    @FXML
    private ScrollPane editEventPane;

    EntityController controller = new EntityController();
    Event event;

    @FXML
    public void initialize(URL url, ResourceBundle rb){


    }

    /**
     * Liest eingegebenen Daten aus Event view
     * @param event Event Entität
     */
    public void setDataFromView(Event event) {

        this.event = event;

        txtStrasse.setText(event.getStreet());
        txtHausNr.setText(event.getHouseNumber());
        txtOrt.setText(event.getCity());
        txtPLZ.setText(event.getPlz());
        txtName.setText(event.getName());
        dateStart.setValue(event.getStartDate()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate());
        timeStart.setValue(event.getStartDate()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalTime());
        dateEnd.setValue(event.getEndDate()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate());
        timeEnd.setValue(event.getEndDate()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalTime());
    }

    /**
     * Editieren des übergebenen Events
     * @param event Event Entität
     */
    @FXML
    public void apply(ActionEvent event){
        Calendar startDate = getStartDate();
        Calendar endDate = getEndDate();
        if(txtPLZ.getText().matches("[\\d]{5}")) {
            try{
                controller.save(Event.class, generate());
                Stage stage = (Stage) editEventPane.getScene().getWindow();
                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                popup.generateInformationPopupWindow("Einsatzplan wurde verarbeitet.");
            }
            catch(NumberFormatException e){
                popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
            }
        } else if(endDate.before(startDate)) {
            popup.generateInformationPopupWindow("Das Enddatum liegt vor dem Startdatum");

        } else {
            popup.generateInformationPopupWindow("Ihre Eingaben beinhalten Fehler. Daher konnte der Vorgang nicht gespeichert werden");
        }
    }

    private Calendar getStartDate() {
        Calendar startDate = Calendar.getInstance();
        startDate.set(
                dateStart.getValue().getYear(),
                dateStart.getValue().getMonthValue(),
                dateStart.getValue().getDayOfMonth(),
                timeStart.getValue().getHour(),
                timeStart.getValue().getMinute(),
                timeStart.getValue().getSecond());
        return startDate;
    }

    private Calendar getEndDate() {
        Calendar endDate = Calendar.getInstance();
        endDate.set(
                dateEnd.getValue().getYear(),
                dateEnd.getValue().getMonthValue(),
                dateEnd.getValue().getDayOfMonth(),
                timeEnd.getValue().getHour(),
                timeEnd.getValue().getMinute(),
                timeEnd.getValue().getSecond());
        return endDate;
    }

    private Event generate() {

        if(this.event == null){
            this.event =  new Event();
        }
        Calendar startDate = getStartDate();
        Calendar endDate = getEndDate();

        event.setName(txtName.getText());
        event.setStartDate(startDate.getTime());
        event.setEndDate(endDate.getTime());
        event.setStreet(txtStrasse.getText());
        event.setHouseNumber(txtHausNr.getText());
        event.setPlz(txtPLZ.getText());
        event.setCity(txtOrt.getText());

        return event;
    }
}

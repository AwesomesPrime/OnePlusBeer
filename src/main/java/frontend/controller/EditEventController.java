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
import validation.InputValidation;

import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.ResourceBundle;

public class EditEventController implements Initializable {

    private final InputValidation inputValidation = new InputValidation();
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

    @FXML
    public void initialize(URL url, ResourceBundle rb){


    }

    /**
     * Liest eingegebenen Daten aus Event view
     * @param event Event Entität
     */
    public void setDataFromView(Event event) {

        txtStrasse.setText(event.getStreet());
        txtHausNr.setText(event.getHouseNumber());
        txtOrt.setText(event.getCity());
        txtPLZ.setText(event.getPlz());
        txtName.setText(event.getName());
        dateStart.setValue(event.getStartDate()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate());
        timeStart.setValue(event.getStartTime());
        dateEnd.setValue(event.getEndDate()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate());
        timeEnd.setValue(event.getEndTime());
    }

    /**
     * Editieren des übergebenen Events
     * @param event Event Entität
     */
    @FXML
    public void apply(ActionEvent event){
        try{
           EntityController controller = new EntityController();

            Calendar startDate = Calendar.getInstance();
            startDate.set(
                    dateStart.getValue().getYear(),
                    dateStart.getValue().getMonthValue(),
                    dateStart.getValue().getDayOfMonth(),
                    timeStart.getValue().getHour(),
                    timeStart.getValue().getMinute(),
                    timeStart.getValue().getSecond());
            Calendar endDate = Calendar.getInstance();
            endDate.set(
                    dateEnd.getValue().getYear(),
                    dateEnd.getValue().getMonthValue(),
                    dateEnd.getValue().getDayOfMonth(),
                    timeEnd.getValue().getHour(),
                    timeEnd.getValue().getMinute(),
                    timeEnd.getValue().getSecond());

            controller.save(Event.class,  new Event( txtName.getText(),
                                                startDate.getTime(),
                                                endDate.getTime(),
                                                timeStart.getValue(),
                                                timeEnd.getValue(),
                                                txtStrasse.getText(),
                                                txtHausNr.getText(),
                                                txtPLZ.getText(),
                                                txtOrt.getText()));
            popup.generateInformationPopupWindow(txtName.getText() + " wurde verarbeitet.");
            Stage stage = (Stage) editEventPane.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
    }

    private void validateInput() {

    }

}

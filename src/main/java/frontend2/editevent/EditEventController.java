package frontend2.editevent;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import controller.EventController;
import entities.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utilities.AlerterMessagePopup;
import validation.InputValidation;

import java.io.IOException;
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
    public void initialize(URL url, ResourceBundle rb){


    }

    public void getDataFromEventView(Event event) {

        txtStrasse.setText(event.getStreet());
        txtHausNr.setText(Integer.toString(event.getHouseNumber()));
        txtOrt.setText(event.getCity());
        txtPLZ.setText(event.getPlz());
        txtName.setText(event.getName());
        dateStart.setValue(event.getStart()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate());
        timeStart.setValue(event.getStartTime());
        dateEnd.setValue(event.getEnd()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate());
        timeEnd.setValue(event.getEndTime());
    }

    public void onMouseClickEmployee(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/employeelist/employeelist.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,1000,800);
        Stage stage = (Stage) eventPane.getScene().getWindow();
        stage.setScene(newScene);
    }

    public void onMouseClickMain(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/mainMenu/mainMenu.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,1000,800);
        Stage stage = (Stage) eventPane.getScene().getWindow();
        stage.setScene(newScene);

    }

    public void onMouseClickEvent(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/eventlist/eventlist.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,1000,800);
        Stage stage = (Stage) eventPane.getScene().getWindow();
        stage.setScene(newScene);
    }

    @FXML
    public void clickEdit(ActionEvent event){
        try{
           EventController eventController = new EventController();

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

            eventController.addEvent( new Event( txtName.getText(),
                                                startDate.getTime(),
                                                endDate.getTime(),
                                                timeStart.getValue(),
                                                timeEnd.getValue(),
                                                txtStrasse.getText(),
                                                Integer.parseInt(txtHausNr.getText()),
                                                txtPLZ.getText(),
                                                txtOrt.getText()));
            popup.generateInformationPopupWindow(txtName.getText() + " wurde verarbeitet.");
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
    }

    private void validateInput() {

    }

}

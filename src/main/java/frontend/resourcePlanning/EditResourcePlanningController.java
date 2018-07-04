package frontend.resourcePlanning;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import controller.EventController;
import entities.Event;
import entities.ResourcePlanning;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import utilities.AlerterMessagePopup;
import validation.InputValidation;

import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.ResourceBundle;

public class EditResourcePlanningController implements Initializable {

    private final InputValidation inputValidation = new InputValidation();
    private final AlerterMessagePopup popup = new AlerterMessagePopup();

    @FXML
    private JFXTextField txtID;


    @FXML
    public void initialize(URL url, ResourceBundle rb){


    }

    /**
     * Liest eingegebenen Daten aus Event view
     *  event Event Entität
     */
    public void getDataFromRPView(ResourcePlanning RP) {
        txtID.setText(Integer.toString(RP.getId()));
    }

    /**
     * Editieren des übergebenen Events
     *  event Event Entität
     */
    @FXML
    public void apply(ActionEvent event){
        try{

            popup.generateInformationPopupWindow(txtID.getText() + " wurde NICHT verarbeitet.");
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
    }

    private void validateInput() {

    }

}

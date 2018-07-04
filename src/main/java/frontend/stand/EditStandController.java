package frontend.stand;

import com.jfoenix.controls.*;
import controller.StandController;
import entities.Stand;
import entities.ProfessionalStanding;
import entities.StandDescription;
import entities.StateByEmploymentLaw;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import orm.ProfessionalStandingDatabaseService;
import orm.StateByEmploymentLawDatabaseService;
import utilities.AlerterMessagePopup;
import validation.InputValidation;

import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class EditStandController implements Initializable {

    private final InputValidation inputValidation = new InputValidation();
    private final AlerterMessagePopup popup = new AlerterMessagePopup();
    private final StandController StandController = new StandController();

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtType;

    @FXML
    private JFXTextField txtStreet;


    @FXML
    private JFXTextField txtPLZ;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextArea txtComments;

    @FXML
    private JFXTimePicker TimeOpen;

    @FXML
    private JFXTimePicker TimeClose;

    private Stand Stand;


    @FXML
    public void initialize(URL url, ResourceBundle rb){

    }

    public void getDataFromStandView(Stand Stand) {

        this.Stand = Stand;


        txtName.setText(Stand.getStandDescription().getName());
        txtType.setText(Stand.getStandDescription().getType());
        txtStreet.setText(Stand.getStreet());
        txtPLZ.setText(Stand.getZip());
        txtCity.setText(Stand.getCity());
        TimeClose.setValue(Stand.getClosingTime());
        TimeOpen.setValue(Stand.getOpeningTimes());
        txtComments.setText(Stand.getStandDescription().getComment());
    }

    @FXML
    public void apply(ActionEvent event){
        try{
            validateInput();
            if(this.Stand == null) {
                getStandDescription();
                StandController.addStand(generateStand());
            } else {
                StandController.addStand(generateStandOnExisting());
            }
            popup.generateInformationPopupWindow(txtType.getText() + " wurde verarbeitet.");
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
    }

    private Stand generateStandOnExisting() {
        Stand Stand = this.Stand;


        Stand.getStandDescription().setName(txtName.getText());
        Stand.getStandDescription().setType(txtType.getText());
        Stand.setStreet(txtStreet.getText());
        Stand.setZip(txtPLZ.getText());
        Stand.setCity(txtCity.getText());
        Stand.setOpeningTimes(TimeOpen.getValue());
        Stand.setClosingTime(TimeOpen.getValue());
        Stand.getStandDescription().setComment(txtComments.getText());

        return Stand;
    }

    private Stand generateStand() {

        return new Stand(txtStreet.getText()
                        ,txtPLZ.getText()
                        ,txtStreet.getText()
                        ,TimeOpen.getValue()
                        ,TimeClose.getValue()
                        ,getStandDescription());
    }

    private StandDescription getStandDescription() {
        StandDescription desc = new StandDescription();
        desc.setComment(txtComments.getText());
        desc.setType(txtType.getText());
        desc.setName(txtName.getText());

        StandController.addStandDescription(desc);
        return desc;
    }

    private void validateInput() {

    }

}

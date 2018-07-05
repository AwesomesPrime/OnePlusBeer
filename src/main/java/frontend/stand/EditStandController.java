package frontend.stand;

import com.jfoenix.controls.*;
import controller.StandController;
import entities.Stand;
import entities.StandDescription;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utilities.AlerterMessagePopup;
import validation.InputValidation;

import java.net.URL;
import java.util.ResourceBundle;

public class EditStandController implements Initializable {

    private final InputValidation inputValidation = new InputValidation();
    private final AlerterMessagePopup popup = new AlerterMessagePopup();
    private final StandController standController = new StandController();

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

    private Stand stand;


    @FXML
    public void initialize(URL url, ResourceBundle rb){

    }

    public void getDataFromStandView(Stand Stand) {

        this.stand = Stand;


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
            if(this.stand == null) {
                standController.addStand(generateStand());
            } else {
                standController.addStand(generateStandOnExisting());
            }
            popup.generateInformationPopupWindow(txtName.getText() + " wurde verarbeitet.");
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
    }

    private Stand generateStandOnExisting() {
        Stand stand = this.stand;
        StandDescription desc = this.stand.getStandDescription();

        stand.setStreet(txtStreet.getText());
        stand.setZip(txtPLZ.getText());
        stand.setCity(txtCity.getText());
        stand.setOpeningTimes(TimeOpen.getValue());
        stand.setClosingTime(TimeOpen.getValue());
        desc.setName(txtName.getText());
        desc.setType(txtType.getText());
        desc.setComment(txtComments.getText());

        standController.addStandDescription(desc);
        return stand;
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

        standController.addStandDescription(desc);
        return desc;
    }

    private void validateInput() {

    }

}

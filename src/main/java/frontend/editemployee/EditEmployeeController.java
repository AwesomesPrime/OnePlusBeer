package frontend.editemployee;

import com.jfoenix.controls.JFXDatePicker;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import entities.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import controller.EmployeeController;
import javafx.scene.control.Alert;
import utilities.AlerterMessagePopup;
import validation.InputValidation;

import java.awt.*;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class EditEmployeeController implements Initializable {

    private final InputValidation inputValidation = new InputValidation();
    private final AlerterMessagePopup popup = new AlerterMessagePopup();

    @FXML
    private JFXTextField txtVorname;
    @FXML
    private JFXTextField txtNachname;
    @FXML
    private JFXTextField txtOrt;
    @FXML
    private JFXTextField txtPLZ;
    @FXML
    private JFXTextField txtStrasse;
    @FXML
    private JFXTextField txtHausNr;
    @FXML
    private JFXTextField txtFestnetz;
    @FXML
    private JFXTextField txtMobil;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtIBAN;
    @FXML
    private JFXTextField txtBIC;
    @FXML
    private JFXTextField txtBruttoStdSatz;
    @FXML
    private JFXTextField txtBeschZustand;
    @FXML
    private JFXTextField txtArbRechtStatus;
    @FXML
    private JFXTextField txtSteuerID;
    @FXML
    private JFXTextField txtAnstellungsstatus;
    @FXML
    private JFXTextArea txtBemerkung;
    @FXML
    private JFXDatePicker dateBesschSeit;


    @FXML
    public void initialize(URL url, ResourceBundle rb){

    }

    public void getDataFromEmployeeView(Employee employee) {
        txtVorname.setText(employee.getFirstName());
        txtNachname.setText(employee.getLastName());
        txtStrasse.setText(employee.getStreet());
        txtHausNr.setText(Integer.toString(employee.getHouseNumber()));
        txtPLZ.setText(Integer.toString(employee.getPlz()));
        txtOrt.setText(employee.getCity());
        txtFestnetz.setText(employee.getPhoneNumber());
        txtMobil.setText(employee.getMobileNumber());
        txtEmail.setText(employee.getMailAddress());
        txtIBAN.setText(employee.getIban());
        txtBIC.setText(employee.getBic());
        txtBruttoStdSatz.setText(Double.toString(employee.getBruttoPerHour()));
        //dateBesschSeit.setValue(employee.getStartOfEmployment());
        txtSteuerID.setText(employee.getTaxNumber());
        txtBemerkung.setText(employee.getComments());

    }

    @FXML
    public void clickEdit(ActionEvent event){
        try{
            EmployeeController employeeController = new EmployeeController();
            validateInput();
            employeeController.addEmployee( new Employee(   "", txtVorname.getText(),
                    txtNachname.getText(),txtStrasse.getText(),
                    Integer.parseInt(txtHausNr.getText()), Integer.parseInt(txtPLZ.getText()),
                    txtOrt.getText(), txtFestnetz.getText(),
                    txtMobil.getText(), txtEmail.getText(),
                    txtIBAN.getText(), txtBIC.getText(),
                    Integer.parseInt(txtBruttoStdSatz.getText()), dateBesschSeit.getValue().toString(),
                    true,0,
                    txtSteuerID.getText(),0, txtBemerkung.getText() ));
                    popup.generateInformationPopupWindow(txtVorname.getText() + " " + txtNachname.getText() + " wurde verarbeitet.");
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
    }

    private void validateInput() {
        if( !inputValidation.validateText(txtVorname.getText())) {
            popup.generateWarningPopupWindow("Der Vorname beinhaltet ungültig Zeichen.");
        }
        if( !inputValidation.validateText(txtNachname.getText())) {
            popup.generateWarningPopupWindow("Der Nachname beinhaltet ungültig Zeichen.");
        }
        if( !inputValidation.validateText(txtStrasse.getText())){
            popup.generateWarningPopupWindow("Die Straße beinhaltet ungültig Zeichen.");
        }
        if(!inputValidation.validateText(txtOrt.getText())) {
            popup.generateWarningPopupWindow("Der Ort beinhaltet ungültig Zeichen.");
        }
        if(!inputValidation.validateText(txtEmail.getText())) {
            popup.generateWarningPopupWindow("Die Email-Adresse beinhaltet ungültig Zeichen.");
        }
        if(!inputValidation.validateText(txtIBAN.getText())) {
            popup.generateWarningPopupWindow("Die IBAN ist ungültig.");
        }
        if(!inputValidation.validateText(txtBIC.getText())) {
            popup.generateWarningPopupWindow("Der BIC ist ungültig.");
        }
        if(!inputValidation.validatePhone(txtFestnetz.getText())) {
            popup.generateWarningPopupWindow("Die Festnetztnummer beinhaltet ungültige Zeichen.");
        }
        if(!inputValidation.validatePhone(txtMobil.getText())) {
            popup.generateWarningPopupWindow("Die Mobilnummer beinhaltet ungültige Zeichen.");
        }
    }

}

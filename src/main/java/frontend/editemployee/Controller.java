package frontend.editemployee;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import entities.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import controller.EmployeeController;
import validation.InputValidation;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final InputValidation inputValidation = new InputValidation();


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
    }

    @FXML
    public void clickEdit(ActionEvent event){
        try{
            EmployeeController employeeController = new EmployeeController();
            validateInput();
            employeeController.addEmployee( new Employee(   "Herr", txtVorname.getText(),
                    txtNachname.getText(),txtStrasse.getText(),
                    Integer.parseInt(txtHausNr.getText()), Integer.parseInt(txtPLZ.getText()),
                    txtOrt.getText(), txtFestnetz.getText(),
                    txtMobil.getText(), txtEmail.getText(),
                    txtIBAN.getText(), txtBIC.getText(),
                    Integer.parseInt(txtBruttoStdSatz.getText()), dateBesschSeit.getValue().toString(),
                    true,0,
                    txtSteuerID.getText(),0, txtBemerkung.getText() ));
        }
        catch(Exception e){
            //TODO popup wenn bei txtPLZ kein int eingegeben wird
            System.out.println(e.getMessage());
        }
    }

    private void validateInput() {
        if( !inputValidation.validateText(txtVorname.getText())) {
            //TODO show error message for invalid text
        }
        if( !inputValidation.validateText(txtNachname.getText())) {

        }
        if( !inputValidation.validateText(txtStrasse.getText())){

        }
        if(!inputValidation.validateText(txtOrt.getText())) {

        }
        if(!inputValidation.validateText(txtEmail.getText())) {

        }
        if(!inputValidation.validateText(txtIBAN.getText())) {

        }
        if(!inputValidation.validateText(txtBIC.getText())) {

        }
    }

}

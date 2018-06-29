package frontend.editemployee;

import com.jfoenix.controls.JFXDatePicker;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import entities.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import controller.EmployeeController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utilities.AlerterMessagePopup;
import validation.InputValidation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class EditEmployeeController implements Initializable {

    private final InputValidation inputValidation = new InputValidation();
    private final AlerterMessagePopup popup = new AlerterMessagePopup();
    private final EmployeeController employeeController = new EmployeeController();


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
    private AnchorPane editPane;

    private Employee employee;


    @FXML
    public void initialize(URL url, ResourceBundle rb){

    }

    public void getDataFromEmployeeView(Employee employee) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(employee.getStartOfEmployment(), formatter);
        this.employee = employee;

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
        dateBesschSeit.setValue(date);
        txtSteuerID.setText(employee.getTaxNumber());
        txtBemerkung.setText(employee.getComments());

    }

    @FXML
    public void apply(ActionEvent event){
        try{
            validateInput();
            if(this.employee == null) {
                employeeController.addEmployee(generateEmployee());
                generateEmployee();
            } else {

                employeeController.addEmployee(this.employee);

            }
            popup.generateInformationPopupWindow(txtVorname.getText() + " " + txtNachname.getText() + " wurde verarbeitet.");
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
    }

    private Employee generateEmployeeOnExisting() {
        Employee employee = this.employee;
        employee.setFirstName(txtVorname.getText());
        employee.setLastName(txtNachname.getText());
        employee.setStreet(txtStrasse.getText());
        employee.setHouseNumber(Integer.parseInt(txtHausNr.getText()));
        employee.setPlz(Integer.parseInt(txtPLZ.getText()));
        employee.setCity(txtOrt.getText());
        employee.setPhoneNumber(txtFestnetz.getText());
        employee.setMobileNumber(txtMobil.getText());
        employee.setMailAddress(txtEmail.getText());
        employee.setIban(txtIBAN.getText());
        employee.setBic(txtBIC.getText());
        employee.setBruttoPerHour(Integer.parseInt(txtBruttoStdSatz.getText()));
        employee.setStartOfEmployment(dateBesschSeit.getValue().toString());
        //employee.setActivityState(//TODO switch for activity);
        employee.setTaxNumber(txtSteuerID.getText());
        employee.setComments(txtBemerkung.getText());

        return employee;
    }

    private Employee generateEmployee() {
        return new Employee("", txtVorname.getText(),
                txtNachname.getText(), txtStrasse.getText(),
                Integer.parseInt(txtHausNr.getText()), Integer.parseInt(txtPLZ.getText()),
                txtOrt.getText(), txtFestnetz.getText(),
                txtMobil.getText(), txtEmail.getText(),
                txtIBAN.getText(), txtBIC.getText(),
                Integer.parseInt(txtBruttoStdSatz.getText()), dateBesschSeit.getValue().toString(),
                true, 0,
                txtSteuerID.getText(), 0, txtBemerkung.getText());
    }

    public void onMouseClickEmployee(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/employeelist/employeelist.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,1000,800);
        Stage stage = (Stage) editPane.getScene().getWindow();
        stage.setScene(newScene);
    }

    public void onMouseClickMain(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/mainMenu/mainMenu.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,1000,800);
        Stage stage = (Stage) editPane.getScene().getWindow();
        stage.setScene(newScene);

    }

    public void onMouseClickEvent(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/eventlist/eventlist.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,1000,800);
        Stage stage = (Stage) editPane.getScene().getWindow();
        stage.setScene(newScene);
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

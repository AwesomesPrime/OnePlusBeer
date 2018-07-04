package frontend.employee;

import com.jfoenix.controls.*;
import com.jfoenix.validation.base.ValidatorBase;
import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
import controller.EmployeeController;
import entities.Employee;
import entities.ProfessionalStanding;
import entities.StateByEmploymentLaw;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import orm.ProfessionalStandingDatabaseService;
import orm.StateByEmploymentLawDatabaseService;
import utilities.AlerterMessagePopup;
import validation.*;

import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class EditEmployeeController implements Initializable {

    private final InputValidation inputValidation = new InputValidation();
    private final AlerterMessagePopup popup = new AlerterMessagePopup();
    private final EmployeeController employeeController = new EmployeeController();

    private ValidatorBase vBase;

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
    private JFXComboBox cbProfessionalStanding;

    @FXML
    private JFXComboBox cbStateByEmploymentLaw;

    @FXML
    private JFXTextField txtSteuerID;

    @FXML
    private JFXCheckBox chkActivityState;

    @FXML
    private JFXTextArea txtBemerkung;

    @FXML
    private JFXDatePicker dateBesschSeit;

    @FXML
    private AnchorPane editPane;

    private Employee employee;


    @FXML
    public void initialize(URL url, ResourceBundle rb){

        setValidators();

        StateByEmploymentLawDatabaseService lawStateDbService = new StateByEmploymentLawDatabaseService();

        ObservableList<StateByEmploymentLaw> lawStates = FXCollections.observableList(lawStateDbService.getAll(StateByEmploymentLaw.class));
        cbStateByEmploymentLaw.setItems(lawStates);

        ProfessionalStandingDatabaseService profStandDbService = new ProfessionalStandingDatabaseService();

        ObservableList<ProfessionalStanding> profStands = FXCollections.observableList(profStandDbService.getAll(ProfessionalStanding.class));
        cbProfessionalStanding.setItems(profStands);
    }

    private void setValidators(){

        EmailValidator emailValidator = new EmailValidator();
        PhoneValidator phoneValidator = new PhoneValidator();
        IBANValidator ibanValidator = new IBANValidator();
        BICValidator bicValidator = new BICValidator();
        TextValidator textValidator = new TextValidator();
        ZipValidator zipValidator = new ZipValidator();
        emailValidator.setMessage("Ungültige E-Mail Adresse");
        phoneValidator.setMessage("Ungültige Telefonnummer");
        ibanValidator.setMessage("Ungültige IBAN");
        bicValidator.setMessage("Ungültige BIC");
        zipValidator.setMessage("Ungültige PLZ");

        txtEmail.getValidators().add(emailValidator);
        txtEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    txtEmail.validate();
                }
            }
        });

        txtFestnetz.getValidators().add(phoneValidator);
        txtFestnetz.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    txtFestnetz.validate();
                }
            }
        });

        txtMobil.getValidators().add(phoneValidator);
        txtMobil.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    txtMobil.validate();
                }
            }
        });

        txtIBAN.getValidators().add(ibanValidator);
        txtIBAN.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    txtIBAN.validate();
                }
            }
        });

        txtBIC.getValidators().add(bicValidator);
        txtBIC.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    txtBIC.validate();
                }
            }
        });

        txtPLZ.getValidators().add(zipValidator);
        txtPLZ.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    txtPLZ.validate();
                }
            }
        });

    }

    public void getDataFromEmployeeView(Employee employee) {

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
        dateBesschSeit.setValue(employee.getStartOfEmployment()
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate());
        txtSteuerID.setText(employee.getTaxNumber());
        txtBemerkung.setText(employee.getComments());
        cbProfessionalStanding.getSelectionModel().select(indexOfProfessionalStandingInList(cbProfessionalStanding.getItems(), employee.getProfessionalStanding().getId()));
        cbStateByEmploymentLaw.getSelectionModel().select(indexOfStandByEmploymentLawInList(cbStateByEmploymentLaw.getItems(), employee.getStateByEmploymentLaw().getId()));
        chkActivityState.setSelected(employee.getActivityState());
    }

    @FXML
    public void apply(ActionEvent event){
        try{
            if(this.employee == null) {
                employeeController.addEmployee(generateEmployee());
                generateEmployee();
            } else {
                employeeController.addEmployee(generateEmployeeOnExisting());
            }
            popup.generateInformationPopupWindow(txtVorname.getText() + " " + txtNachname.getText() + " wurde verarbeitet.");
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
    }

    private Employee generateEmployeeOnExisting() {
        Employee employee = this.employee;

        Calendar startDate = Calendar.getInstance();
        startDate.set(
                dateBesschSeit.getValue().getYear(),
                dateBesschSeit.getValue().getMonthValue(),
                dateBesschSeit.getValue().getDayOfMonth());

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
        employee.setBruttoPerHour(Double.parseDouble(txtBruttoStdSatz.getText()));
        employee.setStartOfEmployment(startDate.getTime());
        employee.setActivityState(chkActivityState.isSelected());
        employee.setProfessionalStanding((ProfessionalStanding) cbProfessionalStanding.getSelectionModel().getSelectedItem());
        employee.setStateByEmploymentLaw((StateByEmploymentLaw) cbStateByEmploymentLaw.getSelectionModel().getSelectedItem());
        employee.setTaxNumber(txtSteuerID.getText());
        employee.setComments(txtBemerkung.getText());

        return employee;
    }

    private Employee generateEmployee() {
        ProfessionalStandingDatabaseService professionalStandingService = new ProfessionalStandingDatabaseService();
        StateByEmploymentLawDatabaseService stateByEmploymentLawService = new StateByEmploymentLawDatabaseService();

        Calendar startDate = Calendar.getInstance();
        startDate.set(
                dateBesschSeit.getValue().getYear(),
                dateBesschSeit.getValue().getMonthValue(),
                dateBesschSeit.getValue().getDayOfMonth());

        return new Employee("", txtVorname.getText(),
                txtNachname.getText(), txtStrasse.getText(),
                Integer.parseInt(txtHausNr.getText()), Integer.parseInt(txtPLZ.getText()),
                txtOrt.getText(), txtFestnetz.getText(),
                txtMobil.getText(), txtEmail.getText(),
                txtIBAN.getText(), txtBIC.getText(),
                Double.parseDouble(txtBruttoStdSatz.getText()), startDate.getTime(),
                chkActivityState.isSelected(), (StateByEmploymentLaw) cbStateByEmploymentLaw.getSelectionModel().getSelectedItem(),
                txtSteuerID.getText(), (ProfessionalStanding) cbProfessionalStanding.getSelectionModel().getSelectedItem(), "");
    }

    private int indexOfProfessionalStandingInList(List<ProfessionalStanding> psList, int professionalStandingId) {
        for (int i = 0; i <= psList.size(); i++) {
            if(psList.get(i).getId() == professionalStandingId){
                return i;
            }
        }
        return -1;
    }

    private int indexOfStandByEmploymentLawInList(List<StateByEmploymentLaw> stateLawlList, int stateByEmploymentlawId) {
        for (int i = 0; i <= stateLawlList.size(); i++) {
            if(stateLawlList.get(i).getId() == stateByEmploymentlawId){
                return i;
            }
        }
        return -1;
    }
}

package frontend.employee;

import com.jfoenix.controls.*;
import controller.EmployeeController;
import entities.Employee;
import entities.ProfessionalStanding;
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
        StateByEmploymentLawDatabaseService lawStateDbService = new StateByEmploymentLawDatabaseService();

        ObservableList<StateByEmploymentLaw> lawStates = FXCollections.observableList(lawStateDbService.getAll(StateByEmploymentLaw.class));
        cbStateByEmploymentLaw.setItems(lawStates);

        ProfessionalStandingDatabaseService profStandDbService = new ProfessionalStandingDatabaseService();

        ObservableList<ProfessionalStanding> profStands = FXCollections.observableList(profStandDbService.getAll(ProfessionalStanding.class));
        cbProfessionalStanding.setItems(profStands);
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
            validateInput();
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

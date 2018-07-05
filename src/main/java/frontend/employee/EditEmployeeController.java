package frontend.employee;

import com.jfoenix.controls.*;
import com.jfoenix.validation.base.ValidatorBase;
import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
import controller.EmployeeController;
import entities.Employee;
import entities.ProfessionalStanding;
import entities.StateByEmploymentLaw;
import entities.UserPermission;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import orm.ProfessionalStandingDatabaseService;
import orm.StateByEmploymentLawDatabaseService;
import orm.UserPermissionDatabaseService;
import utilities.AlerterMessagePopup;
import validation.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
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
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXTextField txtStreet;

    @FXML
    private JFXTextField txtHouseNumber;

    @FXML
    private JFXTextField txtPLZ;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtPhoneNumber;

    @FXML
    private JFXTextField txtMobileNumber;

    @FXML
    private JFXTextField txtMailAddress;

    @FXML
    private JFXTextField txtIBAN;

    @FXML
    private JFXTextField txtBIC;

    @FXML
    private JFXTextField txtBruttoPerHour;

    @FXML
    private JFXComboBox cbProfessionalStanding;

    @FXML
    private JFXComboBox cbStateByEmploymentLaw;

    @FXML
    private JFXTextField txtTaxNumber;

    @FXML
    private JFXCheckBox chkActivityState;

    @FXML
    private JFXTextArea txtComments;

    @FXML
    private JFXDatePicker dateStartOfEmployment;

    @FXML
    private ScrollPane editEmployeePane;

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

        txtMailAddress.getValidators().add(emailValidator);
        txtMailAddress.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    txtMailAddress.validate();
                }
            }
        });

        txtPhoneNumber.getValidators().add(phoneValidator);
        txtPhoneNumber.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    txtPhoneNumber.validate();
                }
            }
        });

        txtMobileNumber.getValidators().add(phoneValidator);
        txtMobileNumber.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    txtMobileNumber.validate();
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

    /**
     * liest die Bedienereingabe der EmployeeView aus
     * @param employee employee Entität
     */
    public void getDataFromEmployeeView(Employee employee) {

        this.employee = employee;

        txtFirstName.setText(employee.getFirstName());
        txtLastName.setText(employee.getLastName());
        txtStreet.setText(employee.getStreet());
        txtHouseNumber.setText(employee.getHouseNumber());
        txtPLZ.setText(Integer.toString(employee.getPlz()));
        txtCity.setText(employee.getCity());
        txtPhoneNumber.setText(employee.getPhoneNumber());
        txtMobileNumber.setText(employee.getMobileNumber());
        txtMailAddress.setText(employee.getMailAddress());
        txtIBAN.setText(employee.getIban());
        txtBIC.setText(employee.getBic());
        txtBruttoPerHour.setText(Double.toString(employee.getBruttoPerHour()));
        dateStartOfEmployment.setValue(employee.getStartOfEmployment()
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate());
        txtTaxNumber.setText(employee.getTaxNumber());
        txtComments.setText(employee.getComments());
        cbProfessionalStanding.getSelectionModel().select(indexOfProfessionalStandingInList(cbProfessionalStanding.getItems(), employee.getProfessionalStanding().getId()));
        cbStateByEmploymentLaw.getSelectionModel().select(indexOfStandByEmploymentLawInList(cbStateByEmploymentLaw.getItems(), employee.getStateByEmploymentLaw().getId()));
        chkActivityState.setSelected(employee.getActivityState());
    }

    /**
     * Event des Speicherbuttons,
     * Entweder ein Update oder Save
     * @param event
     */
    @FXML
    public void apply(ActionEvent event){
        try{
            if(this.employee == null) {
                employeeController.addEmployee(generateEmployee());
            } else {
                employeeController.addEmployee(generateEmployeeOnExisting());
            }
            popup.generateInformationPopupWindow(txtFirstName.getText() + " " + txtLastName.getText() + " wurde verarbeitet.");
            Stage stage = (Stage) editEmployeePane.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
    }

    /**
     * erstelle Employee von vorhandenem Employee
     * @return Employee
     */
    private Employee generateEmployeeOnExisting() {
        Employee employee = this.employee;

        Calendar startDate = Calendar.getInstance();
        startDate.set(
                dateStartOfEmployment.getValue().getYear(),
                dateStartOfEmployment.getValue().getMonthValue(),
                dateStartOfEmployment.getValue().getDayOfMonth());
        employee.setFirstName(txtFirstName.getText());
        employee.setLastName(txtLastName.getText());
        employee.setStreet(txtStreet.getText());
        employee.setHouseNumber(txtHouseNumber.getText());
        employee.setPlz(Integer.parseInt(txtPLZ.getText()));
        employee.setCity(txtCity.getText());
        employee.setPhoneNumber(txtPhoneNumber.getText());
        employee.setMobileNumber(txtMobileNumber.getText());
        employee.setMailAddress(txtMailAddress.getText());
        employee.setIban(txtIBAN.getText());
        employee.setBic(txtBIC.getText());
        employee.setBruttoPerHour(Double.parseDouble(txtBruttoPerHour.getText()));
        employee.setStartOfEmployment(startDate.getTime());
        employee.setActivityState(chkActivityState.isSelected());
        employee.setProfessionalStanding((ProfessionalStanding) cbProfessionalStanding.getSelectionModel().getSelectedItem());
        employee.setStateByEmploymentLaw((StateByEmploymentLaw) cbStateByEmploymentLaw.getSelectionModel().getSelectedItem());
        employee.setTaxNumber(txtTaxNumber.getText());
        employee.setComments(txtComments.getText());

        return employee;
    }

    /**
     * erstelle neuen Employee von View eingaben
     * @return Employee
     */
    private Employee generateEmployee() {
        ProfessionalStandingDatabaseService professionalStandingService = new ProfessionalStandingDatabaseService();
        StateByEmploymentLawDatabaseService stateByEmploymentLawService = new StateByEmploymentLawDatabaseService();
        UserPermissionDatabaseService userPermissionService = new UserPermissionDatabaseService();

        Calendar startDate = Calendar.getInstance();
        startDate.set(
                dateStartOfEmployment.getValue().getYear(),
                dateStartOfEmployment.getValue().getMonthValue(),
                dateStartOfEmployment.getValue().getDayOfMonth());

        return new Employee("", txtFirstName.getText(),
                txtLastName.getText(), txtStreet.getText(),
                txtHouseNumber.getText(), Integer.parseInt(txtPLZ.getText()),
                txtCity.getText(), txtPhoneNumber.getText(),
                txtMobileNumber.getText(), txtMailAddress.getText(),
                txtIBAN.getText(), txtBIC.getText(),
                Double.parseDouble(txtBruttoPerHour.getText()), startDate.getTime(),
                chkActivityState.isSelected(), (StateByEmploymentLaw) cbStateByEmploymentLaw.getSelectionModel().getSelectedItem(),
                txtTaxNumber.getText(), (ProfessionalStanding) cbProfessionalStanding.getSelectionModel().getSelectedItem(), "", userPermissionService.get(UserPermission.class, 1), "changeme");
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

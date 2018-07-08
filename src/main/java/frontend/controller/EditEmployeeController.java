package frontend.controller;

import com.jfoenix.controls.*;
import com.jfoenix.validation.base.ValidatorBase;
import controller.EntityController;
import entities.Employee;
import entities.ProfessionalStanding;
import entities.StateByEmploymentLaw;
import entities.UserPermission;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import orm.ProfessionalStandingDatabaseService;
import orm.StateByEmploymentLawDatabaseService;
import orm.UserPermissionDatabaseService;
import utilities.AlerterMessagePopup;
import validation.*;

import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.ResourceBundle;

public class EditEmployeeController implements Initializable {

    private final AlerterMessagePopup popup = new AlerterMessagePopup();
    private final EntityController controller = new EntityController();

    @FXML
    private JFXTextField
            salutation,
            firstName,
            lastName,
            street,
            houseNumber,
            plz,
            city,
            phoneNumber,
            mobileNumber,
            mailAddress,
            password,
            iban,
            bic,
            bruttoPerHour,
            taxNumber
    ;

    @FXML
    private JFXTextArea comments;

    @FXML
    private JFXDatePicker startOfEmployment;

    @FXML
    private JFXComboBox
            professionalStanding,
            stateByEmploymentLaw,
            userPermission
    ;

    @FXML
    private JFXCheckBox activityState;

    private Employee employee;


    @FXML
    public void initialize(URL url, ResourceBundle rb){

        setValidators();

        UserPermissionDatabaseService userPermissionDatabaseService = new UserPermissionDatabaseService();

        ObservableList<UserPermission> permissions = FXCollections.observableList(userPermissionDatabaseService.getAll(UserPermission.class));
        userPermission.setItems(permissions);

        StateByEmploymentLawDatabaseService lawStateDbService = new StateByEmploymentLawDatabaseService();

        ObservableList<StateByEmploymentLaw> lawStates = FXCollections.observableList(lawStateDbService.getAll(StateByEmploymentLaw.class));
        stateByEmploymentLaw.setItems(lawStates);

        ProfessionalStandingDatabaseService profStandDbService = new ProfessionalStandingDatabaseService();

        ObservableList<ProfessionalStanding> profStands = FXCollections.observableList(profStandDbService.getAll(ProfessionalStanding.class));
        professionalStanding.setItems(profStands);
    }


    private <T extends ValidatorBase> void initializeValidators(T validator, String message, JFXTextField input){
        validator.setMessage(message);

        input.getValidators().add(validator);
        input.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !input.getText().equals("")){
                input.validate();
            }
        });
    }

    private void setValidators(){
        initializeValidators(new EmailValidator(), "Ungültige E-Mail-Adresse", mailAddress);
        initializeValidators(new PhoneValidator(), "Ungültige Telefonnummer", phoneNumber);
        initializeValidators(new PhoneValidator(), "Ungültige Telefonnummer", mobileNumber);
        initializeValidators(new IBANValidator(), "Ungültige IBAN", iban);
        initializeValidators(new BICValidator(), "Ungültige BIC", bic);
        initializeValidators(new PLZValidator(), "Ungültige PLZ", plz);
    }

    /**
     * liest die Bedienereingabe der EmployeeView aus
     * @param employee employee Entität
     */
    public void setDataFromView(Employee employee) {

        this.employee = employee;

        salutation.setText(employee.getSalutation());
        firstName.setText(employee.getFirstName());
        lastName.setText(employee.getLastName());
        street.setText(employee.getStreet());
        houseNumber.setText(employee.getHouseNumber());
        plz.setText(Integer.toString(employee.getPlz()));
        city.setText(employee.getCity());
        phoneNumber.setText(employee.getPhoneNumber());
        mobileNumber.setText(employee.getMobileNumber());
        mailAddress.setText(employee.getMailAddress());
        iban.setText(employee.getIban());
        bic.setText(employee.getBic());
        bruttoPerHour.setText(Double.toString(employee.getBruttoPerHour()));
        startOfEmployment.setValue(employee.getStartOfEmployment()
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate());
        taxNumber.setText(employee.getTaxNumber());
        comments.setText(employee.getComments());
        password.setText(employee.getPassword());
        professionalStanding.getSelectionModel().select(employee.getProfessionalStanding().getId()-1);
        stateByEmploymentLaw.getSelectionModel().select(employee.getStateByEmploymentLaw().getId()-1);
        userPermission.getSelectionModel().select(employee.getUserPermission().getId()-1);
        activityState.setSelected(employee.getActivityState());
    }

    /**
     * Event des Speicherbuttons,
     * Entweder ein Update oder Save
     * @param event
     */
    @FXML
    public void apply(ActionEvent event){
        if(validateInputs()) {
            try {
                controller.save(Employee.class, generate());
                popup.generateInformationPopupWindow(firstName.getText() + " " + lastName.getText() + " wurde verarbeitet.");
                Stage stage = (Stage) salutation.getScene().getWindow();
                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            }
            catch(NumberFormatException e){
                popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
            }
        } else {
            popup.generateInformationPopupWindow("Ihre Eingaben beinhalten Fehler. Daher konnte der Vorgang nicht gespeichert werden");
        }
    }

    /**
     * erstelle Employee von vorhandenem Employee
     * @return Employee
     */
    private Employee generate() {
        if(this.employee == null){
            this.employee =  new Employee();
        }

        Calendar startDate = Calendar.getInstance();
        startDate.set(
                startOfEmployment.getValue().getYear(),
                startOfEmployment.getValue().getMonthValue(),
                startOfEmployment.getValue().getDayOfMonth());
        employee.setFirstName(firstName.getText());
        employee.setSalutation(salutation.getText());
        employee.setLastName(lastName.getText());
        employee.setStreet(street.getText());
        employee.setHouseNumber(houseNumber.getText());
        employee.setPlz(Integer.parseInt(plz.getText()));
        employee.setCity(city.getText());
        employee.setPhoneNumber(phoneNumber.getText());
        employee.setMobileNumber(mobileNumber.getText());
        employee.setMailAddress(mailAddress.getText());
        employee.setIban(iban.getText());
        employee.setBic(bic.getText());
        employee.setBruttoPerHour(Double.parseDouble( bruttoPerHour.getText()));
        employee.setStartOfEmployment(startDate.getTime());
        employee.setActivityState(activityState.isSelected());
        employee.setProfessionalStanding((ProfessionalStanding) professionalStanding.getSelectionModel().getSelectedItem());
        employee.setStateByEmploymentLaw((StateByEmploymentLaw) stateByEmploymentLaw.getSelectionModel().getSelectedItem());
        employee.setUserPermission((UserPermission) userPermission.getSelectionModel().getSelectedItem());
        employee.setTaxNumber(taxNumber.getText());
        employee.setComments(comments.getText());
        employee.setPassword(password.getText());

        return employee;
    }

    private boolean validateInputs() {
        return plz.getText().matches("[\\d]{5}")
                && phoneNumber.getText().matches("[\\d| ]+")
                && iban.getText().matches("^DE\\d{2}\\s?([0-9a-zA-Z]{4}\\s?){4}[0-9a-zA-Z]{2}$")
                && mailAddress.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
                && bic.getText().matches("([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)");
    }
}

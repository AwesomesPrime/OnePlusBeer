package frontend.layout;

import com.jfoenix.controls.JFXTextField;
import entities.Employee;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import orm.EmployeeDatabaseService;
import usermanagement.ActiveUser;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<Employee> tableView;

    @FXML
    private TableColumn<Employee, String> salutation, name, address, phoneNumber, mobileNumber, mailAddress, iban, bic, bruttoPerHour, startOfEmployment, activityState, stateByEmploymentLaw, taxNumber, professionalStanding, comments, userPermission, password;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        DecimalFormat df = new DecimalFormat("#.00");
        DateFormat daf = new SimpleDateFormat("dd.MM.yyyy");

        txtSearch.setLabelFloat(true);

        salutation.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSalutation()));
        name.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName()));
        address.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStreet() + " " + cellData.getValue().getHouseNumber() + ", " + cellData.getValue().getPlz() + " " + cellData.getValue().getCity()));
        phoneNumber.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPhoneNumber()));
        mobileNumber.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMobileNumber()));
        mailAddress.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMailAddress()));
        startOfEmployment.setCellValueFactory(cellData -> new SimpleObjectProperty<>(daf.format(cellData.getValue().getStartOfEmployment())));
        professionalStanding.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProfessionalStanding().getDescription()));
        stateByEmploymentLaw.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStateByEmploymentLaw().getDescription()));
        taxNumber.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTaxNumber()));
        iban.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIban()));
        bic.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBic()));
        bruttoPerHour.setCellValueFactory(cellData -> new SimpleObjectProperty<>(df.format(cellData.getValue().getBruttoPerHour()) + "€"));
        activityState.setCellValueFactory(cellData ->   {
                                                            if (cellData.getValue().getActivityState()) {
                                                                return new SimpleObjectProperty<>("Aktiv");
                                                            } else {
                                                                return new SimpleObjectProperty<>("Inaktiv");
                                                            }
                                                        });
        comments.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getComments()));
        userPermission.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUserPermission().getDescription()));
        password.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPassword()));

        EmployeeDatabaseService employeeDatabaseService = new EmployeeDatabaseService();
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(employeeDatabaseService.getEmployeeByEmail(ActiveUser.getMailAdress()));
        ObservableList<Employee> employeeList = FXCollections.observableList(employees);

        tableView.setItems(employeeList);

    }
}

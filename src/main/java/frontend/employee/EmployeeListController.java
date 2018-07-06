package frontend.employee;

import com.jfoenix.controls.JFXTextField;
import entities.Employee;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import orm.EmployeeDatabaseService;
import sun.applet.Main;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeListController implements Initializable {

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<Employee> tableView;

    @FXML
    private TableColumn<Employee, String> salutation, name, address, phoneNumber, mobileNumber, mailAddress, iban, bic, bruttoPerHour, startOfEmployment, activityState, stateByEmploymentLaw, taxNumber, professionalStanding, comments;

    @FXML
    private TableColumn<Employee, Integer> colID;

    @FXML
    public void initialize(URL url, ResourceBundle rb){
        DecimalFormat df = new DecimalFormat("#.00");
        DateFormat daf = new SimpleDateFormat("dd.MM.yyyy");

        txtSearch.setLabelFloat(true);

        salutation.setCellValueFactory(cellData             -> new SimpleObjectProperty<>(cellData.getValue().getSalutation()));
        name.setCellValueFactory(cellData                   -> new SimpleObjectProperty<>(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName()));
        address.setCellValueFactory(cellData                -> new SimpleObjectProperty<>(cellData.getValue().getStreet() + " " + cellData.getValue().getHouseNumber()+ ", " + cellData.getValue().getPlz()+ " " + cellData.getValue().getCity()));
        phoneNumber.setCellValueFactory(cellData            -> new SimpleObjectProperty<>(cellData.getValue().getPhoneNumber()));
        mobileNumber.setCellValueFactory(cellData           -> new SimpleObjectProperty<>(cellData.getValue().getMobileNumber()));
        mailAddress.setCellValueFactory(cellData            -> new SimpleObjectProperty<>(cellData.getValue().getMailAddress()));
        startOfEmployment.setCellValueFactory(cellData      -> new SimpleObjectProperty<>(daf.format(cellData.getValue().getStartOfEmployment())));
        professionalStanding.setCellValueFactory(cellData   -> new SimpleObjectProperty<>(cellData.getValue().getProfessionalStanding().getDescription()));
        stateByEmploymentLaw.setCellValueFactory(cellData   -> new SimpleObjectProperty<>(cellData.getValue().getStateByEmploymentLaw().getDescription()));
        taxNumber.setCellValueFactory(cellData              -> new SimpleObjectProperty<>(cellData.getValue().getTaxNumber()));
        iban.setCellValueFactory(cellData                   -> new SimpleObjectProperty<>(cellData.getValue().getIban()));
        bic.setCellValueFactory(cellData                    -> new SimpleObjectProperty<>(cellData.getValue().getBic()));
        bruttoPerHour.setCellValueFactory(cellData          -> new SimpleObjectProperty<>(df.format(cellData.getValue().getBruttoPerHour()) + "€"));
        activityState.setCellValueFactory(cellData          -> {
            if(cellData.getValue().getActivityState()){
                return new SimpleObjectProperty<>("Aktiv");
            }else{
                return new SimpleObjectProperty<>("Inaktiv");
            }
        });
        comments.setCellValueFactory(cellData               -> new SimpleObjectProperty<>(cellData.getValue().getComments()));

        EmployeeDatabaseService employeeDatabaseService = new EmployeeDatabaseService();
        List<Employee> employees = employeeDatabaseService.getAll(Employee.class);
        ObservableList<Employee> employeeList = FXCollections.observableList(employees);

        tableView.setItems(employeeList);

    }

    public void editEmployee(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Employee currentItemSelected = tableView.getSelectionModel().getSelectedItem();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/employee/editemployee.fxml"));
            Parent root = fxmlLoader.load();
            EditEmployeeController editEmployeeController = fxmlLoader.<EditEmployeeController>getController();
            editEmployeeController.getDataFromEmployeeView(currentItemSelected);
            Scene editScene = new Scene(root,500,500);
            editScene.getStylesheets().add(Main.class.getResource("/styles/basic.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(editScene);
            stage.setOnCloseRequest(e -> tableView.refresh());
            stage.show();
        }
    }

    public void addEmployee(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/employee/editemployee.fxml"));
        Parent root = fxmlLoader.load();
        Scene addScene = new Scene(root,500,500);
        addScene.getStylesheets().add(Main.class.getResource("/styles/basic.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(addScene);
        stage.show();
    }
}
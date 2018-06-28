package frontend.employeelist;

import com.jfoenix.controls.JFXTextField;
import entities.Employee;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import orm.EmployeeDatabaseService;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<Employee> tableView;

    @FXML
    private TableColumn<Employee, String> colVorname, colNachname;
    @FXML
    private TableColumn<Employee, Integer> colID, colStatusArbRecht, colBerufsstatus;

    @FXML
    public void initialize(URL url, ResourceBundle rb){

        txtSearch.setLabelFloat(true);

        colID.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colVorname.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFirstName()));
        colNachname.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLastName()));
        colStatusArbRecht.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStateByEmploymentLaw()));
        colBerufsstatus.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getWorkingStatus()));

        EmployeeDatabaseService employeeDatabaseService = new EmployeeDatabaseService();
        ArrayList<Employee> employees = employeeDatabaseService.getAll(Employee.class);
        ObservableList<Employee> employeeList = FXCollections.observableList(employees);

        tableView.setItems(employeeList);

    }



}

package frontend.controller;

import controller.EntityController;
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
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeListController extends GenericListController implements Initializable {
    @FXML
    private TableView<Employee> tableView;

    @FXML
    private TableColumn<Employee, String>   id,
                                            salutation,
                                            name,
                                            address,
                                            phoneNumber,
                                            mobileNumber,
                                            mailAddress,
                                            iban,
                                            bic,
                                            bruttoPerHour,
                                            startOfEmployment,
                                            activityState,
                                            stateByEmploymentLaw,
                                            taxNumber,
                                            professionalStanding,
                                            comments,
                                            userPermission,
                                            password;

    private final EntityController controller = new EntityController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        search.setLabelFloat(true);

        DecimalFormat df = new DecimalFormat("#.00");
        DateFormat daf = new SimpleDateFormat("dd.MM.yyyy");

        id.setCellValueFactory(cellData                     -> new SimpleObjectProperty<>(cellData.getValue().getId() + ""));
        salutation.setCellValueFactory(cellData             -> new SimpleObjectProperty<>(cellData.getValue().getSalutation()));
        name.setCellValueFactory(cellData                   -> new SimpleObjectProperty<>(cellData.getValue().getFullName()));
        address.setCellValueFactory(cellData                -> new SimpleObjectProperty<>(cellData.getValue().getAdress()));
        phoneNumber.setCellValueFactory(cellData            -> new SimpleObjectProperty<>(cellData.getValue().getPhoneNumber()));
        mobileNumber.setCellValueFactory(cellData           -> new SimpleObjectProperty<>(cellData.getValue().getMobileNumber()));
        mailAddress.setCellValueFactory(cellData            -> new SimpleObjectProperty<>(cellData.getValue().getMailAddress()));
        startOfEmployment.setCellValueFactory(cellData      -> new SimpleObjectProperty<>(daf.format(cellData.getValue().getStartOfEmployment())));
        professionalStanding.setCellValueFactory(cellData   -> new SimpleObjectProperty<>(cellData.getValue().getProfessionalStanding().getDescription()));
        stateByEmploymentLaw.setCellValueFactory(cellData   -> new SimpleObjectProperty<>(cellData.getValue().getStateByEmploymentLaw().getDescription()));
        taxNumber.setCellValueFactory(cellData              -> new SimpleObjectProperty<>(cellData.getValue().getTaxNumber()));
        iban.setCellValueFactory(cellData                   -> new SimpleObjectProperty<>(cellData.getValue().getIban()));
        bic.setCellValueFactory(cellData                    -> new SimpleObjectProperty<>(cellData.getValue().getBic()));
        bruttoPerHour.setCellValueFactory(cellData          -> new SimpleObjectProperty<>(df.format(cellData.getValue().getBruttoPerHour()) + " €"));
        activityState.setCellValueFactory(cellData          -> {
            if(cellData.getValue().isActive()){
                return new SimpleObjectProperty<>("Aktiv");
            }else{
                return new SimpleObjectProperty<>("Inaktiv");
            }
        });
        comments.setCellValueFactory(cellData               -> new SimpleObjectProperty<>(cellData.getValue().getComments()));
        userPermission.setCellValueFactory(cellData         -> new SimpleObjectProperty<>(cellData.getValue().getUserPermission().getDescription()));
        password.setCellValueFactory(cellData               -> new SimpleObjectProperty<>(cellData.getValue().getPassword()));

        ObservableList<Employee> list = getAllItems();

        tableView.setItems(list);

        setFXML("frontend/fxml/editemployee.fxml");
    }

    @Override
    protected boolean checkPermission() {
        return ActiveUser.getPermission() > 2;
    }

    @Override
    protected void resetTableView() {
        tableView.getItems().clear();
        tableView.getItems().addAll(getAllItems());
    }

    @Override
    protected void refreshTableView() {
        tableView.refresh();
    }

    @Override
    protected Employee getSelectedItem() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void setDataToEdit(){
        EditEmployeeController editController = fxmlLoader.getController();
        editController.setDataFromView(getSelectedItem());
    }

    @Override
    protected void copySelectedItem() {
        try{
            getSelectedItem().setId(0);
            controller.save(Employee.class, getSelectedItem());
            resetTableView();
        }
        catch(Exception e){
            popup.generateWarningPopupWindow("Der Datensatz: "+getSelectedItem().getId()+" konnte nicht kopiert werden!");
        }
    }

    @Override
    protected void deleteSelectedItem() {
        try{
            controller.delete(Employee.class, getSelectedItem());
            resetTableView();
        }
        catch(Exception e){
            popup.generateWarningPopupWindow("Der Datensatz: "+getSelectedItem().getId()+" konnte nicht gelöscht werden!");
        }
    }

    @Override
    protected ObservableList<Employee> getAllItems(){
        EmployeeDatabaseService databaseService = new EmployeeDatabaseService();
        List<Employee> list = databaseService.getAll(Employee.class);
        return FXCollections.observableList(list);
    }

    @Override
    protected ObservableList<Employee> getFilteredItems(){
        EmployeeDatabaseService databaseService = new EmployeeDatabaseService();
        List<Employee> list = databaseService.search(search.getText());
        return FXCollections.observableList(list);
    }

    @Override
    public void search() {
        tableView.getItems().clear();
        tableView.getItems().addAll(getFilteredItems());
    }
}

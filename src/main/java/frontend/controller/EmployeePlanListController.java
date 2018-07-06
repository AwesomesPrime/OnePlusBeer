package frontend.controller;

import controller.EntityController;
import entities.EmployeePlan;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import orm.EmployeePlanDatabaseService;
import usermanagement.ActiveUser;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeePlanListController extends GenericListController implements Initializable {
    @FXML
    private TableView<EmployeePlan> tableView;

    @FXML
    private TableColumn<EmployeePlan, String>   id,
                                                employee,
                                                standPlan,
                                                startWorkingTime,
                                                endWorkingTime,
                                                pauseTime,
                                                travelExpenses,
                                                travelDistance,
                                                travelStart,
                                                comment,
                                                bonus;

    private final EntityController controller = new EntityController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        search.setLabelFloat(true);

        DecimalFormat df = new DecimalFormat("#.00");
        DateFormat daf = new SimpleDateFormat("dd.MM.yyyy - HH:mm");

        id.setCellValueFactory(cellData                 -> new SimpleObjectProperty<>(cellData.getValue().getId() + ""));
        employee.setCellValueFactory(cellData           -> new SimpleObjectProperty<>(cellData.getValue().getEmployee().getFullName()));
        standPlan.setCellValueFactory(cellData          -> new SimpleObjectProperty<>(cellData.getValue().getStandPlan().getStand().getName()));
        startWorkingTime.setCellValueFactory(cellData   -> new SimpleObjectProperty<>(daf.format(cellData.getValue().getStartWorkingTime())));
        endWorkingTime.setCellValueFactory(cellData     -> new SimpleObjectProperty<>(daf.format(cellData.getValue().getEndWorkingTime())));
        pauseTime.setCellValueFactory(cellData          -> new SimpleObjectProperty<>(cellData.getValue().getPauseTime() + " Minuten"));
        travelExpenses.setCellValueFactory(cellData     -> new SimpleObjectProperty<>(df.format(cellData.getValue().getTravelExpenses()) + " €"));
        travelDistance.setCellValueFactory(cellData     -> new SimpleObjectProperty<>(df.format(cellData.getValue().getTravelDistance()) + " km"));
        travelStart.setCellValueFactory(cellData        -> new SimpleObjectProperty<>(cellData.getValue().getTravelStart()));
        comment.setCellValueFactory(cellData            -> new SimpleObjectProperty<>(cellData.getValue().getComment()));
        bonus.setCellValueFactory(cellData              -> new SimpleObjectProperty<>(df.format(cellData.getValue().getBonus()) + " €"));

        ObservableList<EmployeePlan> list = getAllItems();

        tableView.setItems(list);

        setFXML("frontend/fxml/editemployeeplan.fxml");
    }

    @Override
    protected boolean checkPermission() {
        if(ActiveUser.getPermission() > 1 && getSelectedItem().getEndWorkingTime().getTime() > new Date().getTime()){
            return true;
        }else if(ActiveUser.getPermission() > 2){
            return true;
        }else{
            popup.generateWarningPopupWindow("Keine Berechtigung zum Bearbeiten des Datensatzes "+getSelectedItem().getId());
            return false;
        }
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
    protected EmployeePlan getSelectedItem() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void setDataToEdit(){
        EditEmployeePlanController editController = fxmlLoader.getController();
        editController.setDataFromView(getSelectedItem());
    }

    @Override
    protected void copySelectedItem() {
        try{
            getSelectedItem().setId(0);
            controller.save(EmployeePlan.class, getSelectedItem());
            resetTableView();
        }
        catch(Exception e){
            popup.generateWarningPopupWindow("Der Datensatz: "+getSelectedItem().getId()+" konnte nicht kopiert werden!");
        }
    }

    @Override
    protected void deleteSelectedItem() {
        try{
            controller.delete(EmployeePlan.class, getSelectedItem());
            resetTableView();
        }
        catch(Exception e){
            popup.generateWarningPopupWindow("Der Datensatz: "+getSelectedItem().getId()+" konnte nicht gelöscht werden!");
        }
    }

    @Override
    protected ObservableList<EmployeePlan> getAllItems(){
        EmployeePlanDatabaseService databaseService = new EmployeePlanDatabaseService();
        List<EmployeePlan> list = databaseService.getAll(EmployeePlan.class);
        return FXCollections.observableList(list);
    }

    @Override
    protected ObservableList<EmployeePlan> getFilteredItems(){
        EmployeePlanDatabaseService databaseService = new EmployeePlanDatabaseService();
        List<EmployeePlan> list = databaseService.search(search.getText());
        return FXCollections.observableList(list);
    }

    @Override
    public void search() {
        tableView.getItems().clear();
        tableView.getItems().addAll(getFilteredItems());
    }
}

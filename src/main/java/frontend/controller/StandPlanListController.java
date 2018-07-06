package frontend.controller;

import controller.EntityController;
import entities.StandPlan;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
//import orm.StandPlanDatabaseService;
import orm.StandPlanDatabaseService;
import usermanagement.ActiveUser;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StandPlanListController extends GenericListController implements Initializable {
    @FXML
    private TableView<StandPlan> tableView;

    @FXML
    private TableColumn<StandPlan, String>  id,
                                            name,
                                            address,
                                            openingTime,
                                            closingTime;

    private final EntityController controller = new EntityController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        search.setLabelFloat(true);

        DateFormat daf = new SimpleDateFormat("dd.MM.yyyy - HH:mm");

        id.setCellValueFactory(cellData             -> new SimpleObjectProperty<>(cellData.getValue().getId() + ""));
        name.setCellValueFactory(cellData           -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        address.setCellValueFactory(cellData        -> new SimpleObjectProperty<>(cellData.getValue().getAddress()));
        openingTime.setCellValueFactory(cellData    -> new SimpleObjectProperty<>(daf.format(cellData.getValue().getOpeningTime())));
        closingTime.setCellValueFactory(cellData    -> new SimpleObjectProperty<>(daf.format(cellData.getValue().getClosingTime())));

        ObservableList<StandPlan> list = getAllItems();

        tableView.setItems(list);

        setFXML("frontend/fxml/editstandplan.fxml");
    }

    @Override
    protected boolean checkPermission() {
        if(ActiveUser.getPermission() > 1 && getSelectedItem().getClosingTime().getTime() > new Date().getTime()){
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
    protected StandPlan getSelectedItem() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void setDataToEdit(){
        EditStandPlanController editController = fxmlLoader.getController();
        editController.setDataFromView(getSelectedItem());
    }

    @Override
    protected void copySelectedItem() {
        try{
            getSelectedItem().setId(0);
            controller.save(StandPlan.class, getSelectedItem());
            resetTableView();
        }
        catch(Exception e){
            popup.generateWarningPopupWindow("Der Datensatz: "+getSelectedItem().getId()+" konnte nicht kopiert werden!");
        }
    }

    @Override
    protected void deleteSelectedItem() {
        try{
            controller.delete(StandPlan.class, getSelectedItem());
            resetTableView();
        }
        catch(Exception e){
            popup.generateWarningPopupWindow("Der Datensatz: "+getSelectedItem().getId()+" konnte nicht gelöscht werden!");
        }
    }

    @Override
    protected ObservableList<StandPlan> getAllItems(){
        StandPlanDatabaseService databaseService = new StandPlanDatabaseService();
        List<StandPlan> list = databaseService.getAll(StandPlan.class);
        return FXCollections.observableList(list);
    }

    @Override
    protected ObservableList<StandPlan> getFilteredItems(){
        StandPlanDatabaseService databaseService = new StandPlanDatabaseService();
        List<StandPlan> list = databaseService.search(search.getText());
        return FXCollections.observableList(list);
    }

    @Override
    public void search() {
        tableView.getItems().clear();
        tableView.getItems().addAll(getFilteredItems());
    }
}

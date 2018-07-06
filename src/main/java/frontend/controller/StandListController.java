package frontend.controller;

import controller.EntityController;
import entities.Stand;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import orm.StandDatabaseService;
import usermanagement.ActiveUser;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class StandListController extends GenericListController implements Initializable {
    @FXML
    private TableView<Stand> tableView;

    @FXML
    private TableColumn<Stand, String>  id,
                                        name,
                                        type,
                                        comment;

    private final EntityController controller = new EntityController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        search.setLabelFloat(true);

        DecimalFormat df = new DecimalFormat("#.00");
        DateFormat daf = new SimpleDateFormat("dd.MM.yyyy");

        id.setCellValueFactory(cellData                     -> new SimpleObjectProperty<>(cellData.getValue().getId() + ""));
        name.setCellValueFactory(cellData                   -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        type.setCellValueFactory(cellData                   -> new SimpleObjectProperty<>(cellData.getValue().getType()));
        comment.setCellValueFactory(cellData                -> new SimpleObjectProperty<>(cellData.getValue().getComment()));

        ObservableList<Stand> list = getAllItems();

        tableView.setItems(list);

        setFXML("frontend/fxml/editstand.fxml");
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
    protected Stand getSelectedItem() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void setDataToEdit(){
        EditStandController editController = fxmlLoader.getController();
        editController.setDataFromView(getSelectedItem());
    }

    @Override
    protected void copySelectedItem() {
        try{
            getSelectedItem().setId(0);
            controller.save(Stand.class, getSelectedItem());
            resetTableView();
        }
        catch(Exception e){
            popup.generateWarningPopupWindow("Der Datensatz: "+getSelectedItem().getId()+" konnte nicht kopiert werden!");
        }
    }

    @Override
    protected void deleteSelectedItem() {
        try{
            controller.delete(Stand.class, getSelectedItem());
            resetTableView();
        }
        catch(Exception e){
            popup.generateWarningPopupWindow("Der Datensatz: "+getSelectedItem().getId()+" konnte nicht gelöscht werden!");
        }
    }

    @Override
    protected ObservableList<Stand> getAllItems(){
        StandDatabaseService databaseService = new StandDatabaseService();
        List<Stand> list = databaseService.getAll(Stand.class);
        return FXCollections.observableList(list);
    }

    @Override
    protected ObservableList<Stand> getFilteredItems(){
        StandDatabaseService databaseService = new StandDatabaseService();
        List<Stand> list = databaseService.search(search.getText());
        return FXCollections.observableList(list);
    }

    @Override
    public void search() {
        tableView.getItems().clear();
        tableView.getItems().addAll(getFilteredItems());
    }
}

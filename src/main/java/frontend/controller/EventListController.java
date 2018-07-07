package frontend.controller;

import controller.EntityController;
import entities.Event;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import orm.EventDatabaseService;
import usermanagement.ActiveUser;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EventListController extends GenericListController implements Initializable {
    @FXML
    private TableView<Event> tableView;

    @FXML
    private TableColumn<Event, String>  id,
                                        name,
                                        startDate,
                                        endDate,
                                        address;

    private final EntityController controller = new EntityController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        search.setLabelFloat(true);

        DateFormat daf = new SimpleDateFormat("dd.MM.yyyy, HH:mm");

        id.setCellValueFactory(cellData             -> new SimpleObjectProperty<>(cellData.getValue().getId() + ""));
        name.setCellValueFactory(cellData           -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        startDate.setCellValueFactory(cellData      -> new SimpleObjectProperty<>(daf.format(cellData.getValue().getStartDate())));
        endDate.setCellValueFactory(cellData        -> new SimpleObjectProperty<>(daf.format(cellData.getValue().getEndDate())));
        address.setCellValueFactory(cellData        -> new SimpleObjectProperty<>(cellData.getValue().getAdress()));

        ObservableList<Event> list = getAllItems();

        tableView.setItems(list);

        setFXML("frontend/fxml/editevent.fxml");
    }

    @Override
    protected boolean checkPermission() {
        if(ActiveUser.getPermission() > 1 && (getSelectedItem() == null || getSelectedItem().getEndDate().getTime() > new Date().getTime())){
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
    protected Event getSelectedItem() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    @Override
    protected void setDataToEdit(){
        EditEventController editController = fxmlLoader.getController();
        editController.setDataFromView(getSelectedItem());
    }

    @Override
    protected void copySelectedItem() {
        try{
            getSelectedItem().setId(0);
            controller.save(Event.class, getSelectedItem());
            resetTableView();
        }
        catch(Exception e){
            popup.generateWarningPopupWindow("Der Datensatz: "+getSelectedItem().getId()+" konnte nicht kopiert werden!");
        }
    }

    @Override
    protected void deleteSelectedItem() {
        try{
            controller.delete(Event.class, getSelectedItem());
            resetTableView();
        }
        catch(Exception e){
            popup.generateWarningPopupWindow("Der Datensatz: "+getSelectedItem().getId()+" konnte nicht gelöscht werden!");
        }
    }

    @Override
    protected ObservableList<Event> getAllItems(){
        EventDatabaseService databaseService = new EventDatabaseService();
        List<Event> list = databaseService.getAll(Event.class);
        return FXCollections.observableList(list);
    }

    @Override
    protected ObservableList<Event> getFilteredItems(){
        EventDatabaseService databaseService = new EventDatabaseService();
        List<Event> list = databaseService.search(search.getText());
        return FXCollections.observableList(list);
    }

    @Override
    public void search() {
        tableView.getItems().clear();
        tableView.getItems().addAll(getFilteredItems());
    }
}

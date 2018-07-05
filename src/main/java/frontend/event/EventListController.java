package frontend.event;

import com.jfoenix.controls.JFXTextField;
import controller.EmployeeController;
import controller.EventController;
import entities.Employee;
import entities.Event;
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
import orm.EventDatabaseService;
import utilities.AlerterMessagePopup;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EventListController implements Initializable {

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<Event> tableView;

    @FXML
    private TableColumn<Event, String> colAnschrift, colName;

    @FXML
    private TableColumn<Event, Date> colStart, colEnd;

    private final EventController eventController = new EventController();
    private final AlerterMessagePopup popup = new AlerterMessagePopup();

    @FXML
    public void initialize(URL url, ResourceBundle rb){

        txtSearch.setLabelFloat(true);

        colName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        colAnschrift.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStreet() + " " + cellData.getValue().getHouseNumber()+ ", " + cellData.getValue().getPlz()+ " " + cellData.getValue().getCity()));
        colStart.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStart()));
        colEnd.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEnd()));

        ObservableList<Event> eventList = getItems();

        tableView.setItems(eventList);
    }

    public ObservableList<Event> getItems(){
        EventDatabaseService eventDatabaseService = new EventDatabaseService();
        ArrayList<Event> events = eventDatabaseService.getAll(Event.class);
        return FXCollections.observableList(events);
    }

    public void editEvent(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Event currentItemSelected = tableView.getSelectionModel().getSelectedItem();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/event/editevent.fxml"));
            Parent root = fxmlLoader.load();
            EditEventController editEventController = fxmlLoader.<EditEventController>getController();
            editEventController.getDataFromEventView(currentItemSelected);
            Scene editScene = new Scene(root,500,500);
            editScene.getStylesheets().add(EventListController.class.getResource("/styles/basic.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(editScene);
            stage.setOnCloseRequest(e -> tableView.refresh());
            stage.show();
        }
    }

    public void addEvent(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/event/editevent.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,500,500);
        newScene.getStylesheets().add(EventListController.class.getResource("/styles/basic.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(newScene);
        stage.setOnCloseRequest(e -> {
            tableView.getItems().clear();
            tableView.getItems().addAll(getItems());
        });
        stage.show();
    }

    public void deleteEvent(MouseEvent event) throws IOException {
        Event currentItemSelected = tableView.getSelectionModel().getSelectedItem();
        try{
            eventController.deleteEvent(currentItemSelected);

            tableView.getItems().clear();
            tableView.getItems().addAll(getItems());

        }
        catch(Exception e){
            popup.generateWarningPopupWindow("Der Datensatz: "+currentItemSelected.getId()+" konnte nicht gelöscht werden!");
        }

    }
}

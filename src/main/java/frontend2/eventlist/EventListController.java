package frontend2.eventlist;

import com.jfoenix.controls.JFXTextField;
import entities.Event;
import frontend2.editevent.EditEventController;
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
import orm.EventDatabaseService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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

    @FXML
    public void initialize(URL url, ResourceBundle rb){

        txtSearch.setLabelFloat(true);

        colName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        colAnschrift.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStreet() + " " + cellData.getValue().getHouseNumber()+ ", " + cellData.getValue().getPlz()+ " " + cellData.getValue().getCity()));
        colStart.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStart()));
        colEnd.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEnd()));

        EventDatabaseService eventDatabaseService = new EventDatabaseService();
        ArrayList<Event> events = eventDatabaseService.getAll(Event.class);
        ObservableList<Event> eventList = FXCollections.observableList(events);

        tableView.setItems(eventList);
    }

    public void editEvent(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Event currentItemSelected = tableView.getSelectionModel().getSelectedItem();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend2/editevent/editevent.fxml"));
            Parent root = fxmlLoader.load();
            EditEventController editEventController = fxmlLoader.<EditEventController>getController();
            editEventController.getDataFromEventView(currentItemSelected);
            Scene editScene = new Scene(root,500,500);
            Stage stage = new Stage();
            stage.setScene(editScene);
            stage.show();
        }
    }

    public void addEvent(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend2/editevent/editevent.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,500,500);
        Stage stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }
}

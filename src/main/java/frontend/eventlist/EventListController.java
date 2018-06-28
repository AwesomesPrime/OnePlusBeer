package frontend.eventlist;

import com.jfoenix.controls.JFXTextField;
import entities.Employee;
import entities.Event;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import orm.EmployeeDatabaseService;
import orm.EventDatabaseService;

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
        colAnschrift.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAddress()));
        colStart.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStart()));
        colEnd.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEnd()));

        EventDatabaseService eventDatabaseService = new EventDatabaseService();
        ArrayList<Event> events = eventDatabaseService.getAll(Event.class);
        ObservableList<Event> eventList = FXCollections.observableList(events);

        tableView.setItems(eventList);
    }

}

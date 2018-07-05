package frontend.reports;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import controller.EventController;
import entities.Employee;
import entities.Event;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import orm.EmployeeDatabaseService;
import orm.EventDatabaseService;
import utilities.AlerterMessagePopup;
import validation.InputValidation;

import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    private final InputValidation inputValidation = new InputValidation();
    private final AlerterMessagePopup popup = new AlerterMessagePopup();

    @FXML
    public JFXDatePicker dpStartDateLohnkosten, dpEndDateLohnkosten, dpLohnmeldung, dpEndDateEinsatzplanung,
            dpStartDateEinsatzplanung;

    @FXML
    private GridPane gridPane;

    @FXML
    private ComboBox<Employee> cbEmployeeEinsatzplanung, cbEmployeeLohnkosten;

    @FXML
    private ComboBox<Event> cbVeranstaltungEinsatzplanung;


    @FXML
    public void initialize(URL url, ResourceBundle rb){
        EventDatabaseService eventDatabaseService = new EventDatabaseService();
        cbVeranstaltungEinsatzplanung.setItems(FXCollections.observableList(eventDatabaseService.getAll()));

        EmployeeDatabaseService employeeDatabaseService = new EmployeeDatabaseService();
        cbEmployeeEinsatzplanung.setItems(FXCollections.observableList(employeeDatabaseService.getAll()));
        cbEmployeeLohnkosten.setItems(FXCollections.observableList(employeeDatabaseService.getAll()));
    }

    public void generateEinsatzplanung(ActionEvent event) {
        Employee e = cbEmployeeEinsatzplanung.getSelectionModel().getSelectedItem();
        Event ev = cbVeranstaltungEinsatzplanung.getSelectionModel().getSelectedItem();

        Calendar startDate = Calendar.getInstance();
        startDate.set(
                dpStartDateEinsatzplanung.getValue().getYear(),
                dpStartDateEinsatzplanung.getValue().getMonthValue(),
                dpStartDateEinsatzplanung.getValue().getDayOfMonth()
        );

        Calendar endDate = Calendar.getInstance();
        startDate.set(
                dpEndDateEinsatzplanung.getValue().getYear(),
                dpEndDateEinsatzplanung.getValue().getMonthValue(),
                dpEndDateEinsatzplanung.getValue().getDayOfMonth()
        );

        //TODO: report generieren
    }

    public void generateLohnkostenreport(ActionEvent event) {
        Employee e = cbEmployeeLohnkosten.getSelectionModel().getSelectedItem();

        Calendar startDate = Calendar.getInstance();
        startDate.set(
                dpStartDateLohnkosten.getValue().getYear(),
                dpStartDateLohnkosten.getValue().getMonthValue(),
                dpStartDateLohnkosten.getValue().getDayOfMonth()
        );

        Calendar endDate = Calendar.getInstance();
        startDate.set(
                dpEndDateLohnkosten.getValue().getYear(),
                dpEndDateLohnkosten.getValue().getMonthValue(),
                dpEndDateLohnkosten.getValue().getDayOfMonth()
        );

        //TODO: report generieren
    }

    public void generateLohnMeldung(ActionEvent event) {
        int month = dpLohnmeldung.getValue().getMonthValue();

        //TODO: report generieren
    }

    public void generateMitarbeiteruebersicht(ActionEvent event) {
        //TODO: report generieren
    }
}

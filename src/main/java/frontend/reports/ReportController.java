package frontend.reports;

import com.jfoenix.controls.JFXDatePicker;
import entities.Employee;
import entities.Event;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import net.sf.jasperreports.engine.JRException;
import orm.EmployeeDatabaseService;
import orm.EventDatabaseService;
import reports.CallReport;
import utilities.AlerterMessagePopup;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

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

    private final CallReport report = new CallReport();

    @FXML
    public void initialize(URL url, ResourceBundle rb){
        EventDatabaseService eventDatabaseService = new EventDatabaseService();
        cbVeranstaltungEinsatzplanung.setItems(FXCollections.observableList(eventDatabaseService.getAll()));

        EmployeeDatabaseService employeeDatabaseService = new EmployeeDatabaseService();
        cbEmployeeEinsatzplanung.setItems(FXCollections.observableList(employeeDatabaseService.getAll()));
        cbEmployeeLohnkosten.setItems(FXCollections.observableList(employeeDatabaseService.getAll()));
    }

    public void generateEinsatzplanung(ActionEvent event) {
        Employee employee = cbEmployeeEinsatzplanung.getSelectionModel().getSelectedItem();
        Event selectedEvent = cbVeranstaltungEinsatzplanung.getSelectionModel().getSelectedItem();
        if(dpStartDateEinsatzplanung != null && dpEndDateEinsatzplanung != null) {
            Calendar startDate = Calendar.getInstance();
            startDate.set(
                    dpStartDateEinsatzplanung.getValue().getYear(),
                    dpStartDateEinsatzplanung.getValue().getMonthValue()
            );

            Calendar endDate = Calendar.getInstance();
            endDate.set(
                    dpEndDateEinsatzplanung.getValue().getYear(),
                    dpEndDateEinsatzplanung.getValue().getMonthValue()
            );

            Date start = new Date(startDate.getTimeInMillis());
            Date end= new Date(endDate.getTimeInMillis());

            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("prmEmployeeId", employee.getId());
            parameterMap.put("prmEvent", selectedEvent.getId());
            parameterMap.put("prmStartDate", start);
            parameterMap.put("prmEndDate", end);


            try {
                report.generateReport("Lohnkostenreport", parameterMap);
            } catch (JRException e1) {
                popup.generateErrorPopupWindow("Report konnte nicht erstellt werden, versuchen Sie es erneut.");
            } catch (SQLException e1) {
                popup.generateErrorPopupWindow("Report konnte nicht erstellt werden, bitte starten Sie das Programm neu.");
            }
        }
        popup.generateInformationPopupWindow("Bitte wählen Sie einen Zeitraum aus.");
    }

    public void generateLohnkostenreport(ActionEvent event){
        Employee employee = cbEmployeeLohnkosten.getSelectionModel().getSelectedItem();
        if(dpStartDateLohnkosten.getValue() != null && dpEndDateLohnkosten.getValue() != null &&  employee != null) {
            Calendar start = Calendar.getInstance();
            start.set(
                    dpStartDateLohnkosten.getValue().getYear(),
                    dpStartDateLohnkosten.getValue().getMonthValue(),
                    dpStartDateLohnkosten.getValue().getDayOfMonth()
            );

            Calendar end = Calendar.getInstance();
            end.set(
                    dpEndDateLohnkosten.getValue().getYear(),
                    dpEndDateLohnkosten.getValue().getMonthValue(),
                    dpEndDateLohnkosten.getValue().getDayOfMonth()
            );


            Date startDate = new Date(start.getTimeInMillis());
            Date endDate = new Date(end.getTimeInMillis());

            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("prmEmployeeId", employee.getId());
            parameterMap.put("prmStartDate", startDate);
            parameterMap.put("prmEndDate", endDate);

            try {
                report.generateReport("Lohnkostenreport", parameterMap);
            } catch (JRException e1) {
                popup.generateErrorPopupWindow("Report konnte nicht erstellt werden, versuchen Sie es erneut.");
            } catch (SQLException e1) {
                popup.generateErrorPopupWindow("Report konnte nicht erstellt werden, bitte starten Sie das Programm neu.");
            }
        } else {
            popup.generateInformationPopupWindow("Bitte wählen Sie alle Felder aus.");
        }
    }

    public void generateLohnMeldung(ActionEvent event) {
        int month = dpLohnmeldung.getValue().getMonthValue();

        //TODO: report generieren
    }

    public void generateMitarbeiteruebersicht(ActionEvent event) {

        Map<String, Object> parameterMap = new HashMap<>();

        try {
            report.generateReport("Mitarbeiteruebersicht", parameterMap);
        } catch (JRException e1) {
            popup.generateErrorPopupWindow("Report konnte nicht erstellt werden, versuchen Sie es erneut.");
        } catch (SQLException e1) {
            popup.generateErrorPopupWindow("Report konnte nicht erstellt werden, bitte starten Sie das Programm neu.");
        }
    }
}

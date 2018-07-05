package frontend.resourcePlanning;

import com.jfoenix.controls.JFXTextField;
import entities.ResourcePlanning;
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
import orm.ResourcePlanningDatabaseService;
import sun.applet.Main;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class RPListController implements Initializable {

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<ResourcePlanning> tableView;


    @FXML
    private TableColumn<ResourcePlanning, Integer> colID;

    @FXML
    private TableColumn<ResourcePlanning, String> colEventName,colStartDate, colEndDate, colStand,
                                                    colEmployeeName, colStartWorkingTime , colEndWorkingTime,
                                                    colPauseTime, colTravelDistance, colWorkedTime, colComment;
    @FXML
    public void initialize(URL url, ResourceBundle rb){

        txtSearch.setLabelFloat(true);

        colID.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colEventName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEvent().getName()));
        colStartDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEvent().getStart().toString()));
        colEndDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEvent().getEnd().toString()));
        colStand.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStand().getStandDescription().getName()));
        colEmployeeName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmployee().getFirstName()+" "+cellData.getValue().getEmployee().getLastName()));
        colStartWorkingTime.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStartWorkingTime().toString()));
        colEndWorkingTime.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEndWorkingTime().toString()));
        colPauseTime.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Long.toString(cellData.getValue().getPauseTime())));
        colTravelDistance.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Double.toString(cellData.getValue().getTravelDistance())));
        colWorkedTime.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Double.toString(cellData.getValue().getEmployee().getWorkedTimeInMonth(cellData.getValue().getStartWorkingTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue(), cellData.getValue().getStartWorkingTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear()))));
        colComment.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getComment()));

        ResourcePlanningDatabaseService ResourcePlanningListDatabaseService = new ResourcePlanningDatabaseService();
        List<ResourcePlanning> ResourcePlanning = ResourcePlanningListDatabaseService.getAll(ResourcePlanning.class);
        ObservableList<ResourcePlanning> ResourcePlanningList = FXCollections.observableList(ResourcePlanning);

        tableView.setItems(ResourcePlanningList);

    }

    public void editRP(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            ResourcePlanning currentItemSelected = tableView.getSelectionModel().getSelectedItem();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/resourcePlanning/editrp.fxml"));
            Parent root = fxmlLoader.load();
            EditResourcePlanningController editResourcePlanningController = fxmlLoader.<EditResourcePlanningController>getController();
            editResourcePlanningController.getDataFromRPView(currentItemSelected);
            Scene editScene = new Scene(root,500,500);
            editScene.getStylesheets().add(Main.class.getResource("/styles/basic.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(editScene);
            stage.show();
        }
    }

    public void addRP(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/resourcePlanning/editrp.fxml"));
        Parent root = fxmlLoader.load();
        Scene addScene = new Scene(root,500,500);
        addScene.getStylesheets().add(Main.class.getResource("/styles/basic.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(addScene);
        stage.show();
    }
}

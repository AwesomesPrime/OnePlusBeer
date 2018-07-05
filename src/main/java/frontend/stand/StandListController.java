package frontend.stand;

import com.jfoenix.controls.JFXTextField;
import entities.Stand;
import frontend.stand.EditStandController;
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
import orm.StandDatabaseService;
import sun.applet.Main;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StandListController implements Initializable {

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<Stand> tableView;

    @FXML
    private TableColumn<Stand, String> colStandDescription, colName;

    @FXML
    private TableColumn<Stand, Integer> colID;

    @FXML
    public void initialize(URL url, ResourceBundle rb){

        txtSearch.setLabelFloat(true);

        colID.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colStandDescription.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStandDescription().getComment()));
        colName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStandDescription().getName()));

        StandDatabaseService StandDatabaseService = new StandDatabaseService();
        List<Stand> Stands = StandDatabaseService.getAll(Stand.class);
        ObservableList<Stand> StandList = FXCollections.observableList(Stands);

        tableView.setItems(StandList);

    }

    public void editStand(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Stand currentItemSelected = tableView.getSelectionModel().getSelectedItem();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/stand/editstand.fxml"));
            Parent root = fxmlLoader.load();
            EditStandController editStandController = fxmlLoader.<EditStandController>getController();
            editStandController.getDataFromStandView(currentItemSelected);
            Scene editScene = new Scene(root,500,500);
            editScene.getStylesheets().add(Main.class.getResource("/styles/basic.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(editScene);
            stage.show();
        }
    }

    public void addStand(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/stand/editstand.fxml"));
        Parent root = fxmlLoader.load();
        Scene addScene = new Scene(root,500,500);
        addScene.getStylesheets().add(Main.class.getResource("/styles/basic.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(addScene);
        stage.show();
    }
}

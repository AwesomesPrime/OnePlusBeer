package frontend.mainMenu;

import com.jfoenix.controls.JFXTextField;
import entities.Employee;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import orm.EmployeeDatabaseService;
import frontend.editemployee.EditEmployeeController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class mainMenuController implements Initializable {


    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label menuEmployee;


    @FXML
    public void initialize(URL url, ResourceBundle rb){
        //Leer
    }

    public void openLogin(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/login/login.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,1000,800);
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setScene(newScene);
    }

    public void onMouseClickEmployee(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/employeelist/employeelist.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,1000,800);
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setScene(newScene);
    }

    public void onMouseClickEvent(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/eventlist/eventlist.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,1000,800);
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setScene(newScene);
    }

}

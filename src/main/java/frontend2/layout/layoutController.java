package frontend2.layout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class layoutController implements Initializable {


    @FXML
    private AnchorPane menuPane;

    @FXML
    private Label menuLogin;

    @FXML
    private Label menuEvent;

    @FXML
    private Label menuEmployee;

    @FXML
    private Pane currentPane;


    @FXML
    public void initialize(URL url, ResourceBundle rb){
        //Leer
    }

    public void openLogin(MouseEvent event) throws IOException {
        GridPane loginPane =  FXMLLoader.load(getClass().getResource("/frontend2/login/login.fxml"));
        currentPane.getChildren().removeAll();
        currentPane.getChildren().add(loginPane);
    }

    public void openEmployeeList(MouseEvent event) throws IOException {
        ScrollPane employeePane =  FXMLLoader.load(getClass().getResource("/frontend2/employeelist/employeelist.fxml"));
        currentPane.getChildren().removeAll();
        currentPane.getChildren().add(employeePane);
    }

    public void openEventList(MouseEvent event) throws IOException {
        ScrollPane eventPane =  FXMLLoader.load(getClass().getResource("/frontend2/eventlist/eventlist.fxml"));
        currentPane.getChildren().removeAll();
        currentPane.getChildren().add(eventPane);
    }

}

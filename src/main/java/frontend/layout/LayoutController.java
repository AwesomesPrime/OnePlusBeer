package frontend.layout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import usermanagement.ActiveUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LayoutController implements Initializable {

    @FXML
    private Pane currentPane;

    @FXML
    private Label username;


    @FXML
    public void initialize(URL url, ResourceBundle rb){
        //
    }

    private void initializePanes(Node node){
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        setUsername();
    }

    public void openLogin(MouseEvent event) throws IOException {
        GridPane loginPane =  FXMLLoader.load(getClass().getResource("/frontend/login/login.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        initializePanes(loginPane);
        currentPane.getChildren().add(loginPane);
    }

    public void openEmployeeList(MouseEvent event) throws IOException {
        ScrollPane employeePane =  FXMLLoader.load(getClass().getResource("/frontend/employee/employeelist.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        initializePanes(employeePane);
        currentPane.getChildren().add(employeePane);
    }

    public void openEventList(MouseEvent event) throws IOException {
        ScrollPane eventPane =  FXMLLoader.load(getClass().getResource("/frontend/event/eventlist.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        initializePanes(eventPane);
        currentPane.getChildren().add(eventPane);
    }

    public void setUsername(){
        this.username.setText(ActiveUser.getUsername());
    }
}

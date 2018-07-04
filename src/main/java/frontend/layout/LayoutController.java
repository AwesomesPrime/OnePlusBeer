package frontend.layout;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entities.User;
import javafx.event.ActionEvent;
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
import orm.UserDatabaseService;
import usermanagement.ActiveUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static usermanagement.ActiveUser.*;

public class LayoutController implements Initializable{

    @FXML
    private Pane currentPane;

    @FXML
    public Label username;

    @FXML
    JFXTextField txtUsername;

    @FXML
    JFXPasswordField txtPassword;

    @FXML
    GridPane loginPane;

    @FXML
    public void initialize(URL url, ResourceBundle rb){
        txtUsername.setLabelFloat(true);
        txtPassword.setLabelFloat(true);
        initializePanes(loginPane);
    }

    private void initializePanes(Node node){
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
    }

    public void openLogin(MouseEvent event) throws IOException {
        if(getMailAddress() != ""){
            GridPane loginPane =  FXMLLoader.load(getClass().getResource("/frontend/home/home.fxml"));
            currentPane.getChildren().remove(0, currentPane.getChildren().size());
            initializePanes(loginPane);
            currentPane.getChildren().add(loginPane);
        }
    }

    public void openEmployeeList(MouseEvent event) throws IOException {
        ScrollPane employeePane = FXMLLoader.load(getClass().getResource("/frontend/employee/employeelist.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        initializePanes(employeePane);
        currentPane.getChildren().add(employeePane);
    }


    public void openStandList(MouseEvent event) throws IOException {
        ScrollPane standlist = FXMLLoader.load(getClass().getResource("/frontend/stand/standlist.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        initializePanes(standlist);
        currentPane.getChildren().add(standlist);
    }

    public void openEventList(MouseEvent event) throws IOException {
        ScrollPane eventPane =  FXMLLoader.load(getClass().getResource("/frontend/event/eventlist.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        initializePanes(eventPane);
        currentPane.getChildren().add(eventPane);
    }

    @FXML
    public void checkLogin(ActionEvent event){
        UserDatabaseService userService = new UserDatabaseService();
        User user = userService.getUserByEmail(txtUsername.getText());

        if(user != null){
            if(user.getPassword().equals(txtPassword.getText())){
                new ActiveUser(user);
                username.setText(user.getEmployee().getFirstName() + " " + user.getEmployee().getLastName());
            }else {
                System.out.println("Das Passwort ist falsch!");
            }
        }else{
            System.out.println("Nutzer existiert nicht!");
        }
    }
}

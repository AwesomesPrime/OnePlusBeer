package frontend.layout;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entities.Employee;
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
import net.bytebuddy.dynamic.scaffold.TypeWriter;
import orm.EmployeeDatabaseService;
import usermanagement.ActiveUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LayoutController implements Initializable{

    @FXML
    private Pane currentPane;

    @FXML
    private Label menuLogin;

    @FXML
    private Label menuEmployee;

    @FXML
    private Label menuStand;

    @FXML
    private Label menuEvent;

    @FXML
    public Label username;

    @FXML
    JFXTextField txtUsername;

    @FXML
    JFXPasswordField txtPassword;

    @FXML
    GridPane loginPane;

    private int permission = 1;

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

        if(ActiveUser.isSet()){
            menuLogin.setText("Home");
        }else{
            menuLogin.setText("Login");
        }

        switch(permission){
            case 1: //Standard
                hideAll();
                break;
            case 2: //Planer
                showAll();
                GridPane.setRowIndex(menuStand, 4);
                GridPane.setRowIndex(menuEvent, 5);
                break;
            case 3: //Manager
                showAll();
                break;
            case 4: //Admin
                showAll();
                break;
        }
    }

    private void showAll(){
        menuEmployee.setVisible(true);
        menuStand.setVisible(true);
        menuEvent.setVisible(true);
    }

    private void hideAll(){
        menuEmployee.setVisible(false);
        menuStand.setVisible(false);
        menuEvent.setVisible(false);
    }

    public void openLogin() throws IOException {
        if(ActiveUser.isSet()){
            ScrollPane loginPane =  FXMLLoader.load(getClass().getResource("/frontend/layout/home.fxml"));
            currentPane.getChildren().remove(0, currentPane.getChildren().size());
            initializePanes(loginPane);
            currentPane.getChildren().add(loginPane);
        }else{
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

    public void openRPList(MouseEvent event) throws IOException {
        ScrollPane eventPane =  FXMLLoader.load(getClass().getResource("/frontend/resourcePlanning/rplist.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        initializePanes(eventPane);
        currentPane.getChildren().add(eventPane);
    }

    @FXML
    public void checkLogin(ActionEvent event) throws IOException {
        EmployeeDatabaseService employeeService = new EmployeeDatabaseService();
        Employee employee = employeeService.getEmployeeByEmail(txtUsername.getText());

        if(employee != null){
            if(employee.getPassword().equals(txtPassword.getText())){
                new ActiveUser(employee);
                permission = ActiveUser.getPermission();
                openLogin();
                username.setText(employee.getFirstName() + " " + employee.getLastName());
            }else {
                System.out.println("Das Passwort ist falsch!");
            }
        }else{
            System.out.println("Nutzer existiert nicht!");
        }
    }
}

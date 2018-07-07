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
import usermanagement.ActiveUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import orm.EmployeeDatabaseService;

public class LayoutController implements Initializable{

    @FXML
    private Pane currentPane;

    @FXML
    private Label logout;

    @FXML
    private Label menuLogin;

    @FXML
    private Label menuEmployee;

    @FXML
    private Label menuStand;

    @FXML
    private Label menuEvent;

    @FXML
    private Label menuEmployeePlan;

    @FXML
    private Label menuStandPlan;

    @FXML
    private Label menuReport;

    @FXML
    public Label username;

    @FXML
    JFXTextField txtUsername;

    @FXML
    JFXPasswordField txtPassword;

    @FXML
    GridPane loginPane;

    private int permission;

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

        permission = ActiveUser.getPermission();

        switch(permission){
            case 1: //Standard
                hideAll();
                if(ActiveUser.isSet()){
                    logout.setVisible(true);
                }
                menuEmployeePlan.setVisible(true);
                GridPane.setRowIndex(menuEmployeePlan, 4);
                break;
            case 2: //Planer
                hideAll();
                logout.setVisible(true);
                menuEvent.setVisible(true);
                menuStandPlan.setVisible(true);
                menuEmployeePlan.setVisible(true);
                menuReport.setVisible(true);
                GridPane.setRowIndex(menuEvent, 4);
                GridPane.setRowIndex(menuStandPlan, 5);
                GridPane.setRowIndex(menuEmployeePlan, 6);
                GridPane.setRowIndex(menuReport, 7);
                break;
            case 3: //Manager
                showAll();
                GridPane.setRowIndex(menuEmployee, 4);
                GridPane.setRowIndex(menuStand, 5);
                GridPane.setRowIndex(menuEvent, 6);
                GridPane.setRowIndex(menuStandPlan, 7);
                GridPane.setRowIndex(menuEmployeePlan, 8);
                GridPane.setRowIndex(menuReport, 8);
                break;
            case 4: //Admin
                showAll();
                GridPane.setRowIndex(menuEmployee, 4);
                GridPane.setRowIndex(menuStand, 5);
                GridPane.setRowIndex(menuEvent, 6);
                GridPane.setRowIndex(menuStandPlan, 7);
                GridPane.setRowIndex(menuEmployeePlan, 8);
                break;
        }
    }

    private void showAll(){
        logout.setVisible(true);
        menuEmployee.setVisible(true);
        menuStand.setVisible(true);
        menuEvent.setVisible(true);
        menuStandPlan.setVisible(true);
        menuEmployeePlan.setVisible(true);
        menuReport.setVisible(true);
    }

    private void hideAll(){
        logout.setVisible(false);
        menuEmployee.setVisible(false);
        menuStand.setVisible(false);
        menuEvent.setVisible(false);
        menuStandPlan.setVisible(false);
        menuEmployeePlan.setVisible(false);
        menuReport.setVisible(false);
    }

    public void logout(MouseEvent event) throws IOException {
        new ActiveUser(null);
        username.setText("");
        openLogin();
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
        ScrollPane employeePane = FXMLLoader.load(getClass().getResource("/frontend/fxml/employeelist.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        initializePanes(employeePane);
        currentPane.getChildren().add(employeePane);
    }


    public void openStandList(MouseEvent event) throws IOException {
        ScrollPane standlist = FXMLLoader.load(getClass().getResource("/frontend/fxml/standlist.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        initializePanes(standlist);
        currentPane.getChildren().add(standlist);
    }

    public void openEventList(MouseEvent event) throws IOException {
        ScrollPane eventPane =  FXMLLoader.load(getClass().getResource("/frontend/fxml/eventlist.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        initializePanes(eventPane);
        currentPane.getChildren().add(eventPane);
    }

    public void openStandPlanList(MouseEvent event) throws IOException {
        ScrollPane eventPane =  FXMLLoader.load(getClass().getResource("/frontend/fxml/standplanlist.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        initializePanes(eventPane);
        currentPane.getChildren().add(eventPane);
    }

    public void openEmployeePlanList(MouseEvent event) throws IOException {
        ScrollPane eventPane =  FXMLLoader.load(getClass().getResource("/frontend/fxml/employeeplanlist.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        initializePanes(eventPane);
        currentPane.getChildren().add(eventPane);
    }

    public void openReports(MouseEvent event) throws IOException {
        ScrollPane eventPane =  FXMLLoader.load(getClass().getResource("/frontend/reports/generateReport.fxml"));
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
                username.setText(employee.getFullName());
            }else {
                System.out.println("Das Passwort ist falsch!");
            }
        }else{
            System.out.println("Nutzer existiert nicht!");
        }
    }
}

package frontend.editevent;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import controller.EmployeeController;
import entities.Employee;
import entities.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import validation.InputValidation;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditEventController implements Initializable {

    private final InputValidation inputValidation = new InputValidation();

    @FXML
    private AnchorPane eventPane;

    @FXML
    private JFXTextField txtStrasse;
    @FXML
    private JFXTextField txtHausNr;
    @FXML
    private JFXTextField txtOrt;
    @FXML
    private JFXTextField txtPLZ;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXDatePicker dateStart;
    @FXML
    private JFXTimePicker timeStart;
    @FXML
    private JFXDatePicker dateEnd;
    @FXML
    private JFXTimePicker timeEnd;

    @FXML
    public void initialize(URL url, ResourceBundle rb){


    }

    public void getDataFromEventView(Event event) {

        txtStrasse.setText(event.getStreet());
        txtHausNr.setText(Integer.toString(event.getHouseNumber()));
        txtOrt.setText(event.getCity());
        txtPLZ.setText(event.getPlz());
        txtName.setText(event.getName());
        //dateStart.setValue(event.getStart());
        //timeStart ???
        //dateEnd.setValue(event.getEnd());
        //timeEnd ???
    }

    public void onMouseClickEmployee(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/employeelist/employeelist.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,1000,800);
        Stage stage = (Stage) eventPane.getScene().getWindow();
        stage.setScene(newScene);
    }

    public void onMouseClickMain(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/mainMenu/mainMenu.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,1000,800);
        Stage stage = (Stage) eventPane.getScene().getWindow();
        stage.setScene(newScene);

    }

    public void onMouseClickEvent(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/eventlist/eventlist.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,1000,800);
        Stage stage = (Stage) eventPane.getScene().getWindow();
        stage.setScene(newScene);
    }

    @FXML
    public void clickEdit(ActionEvent event){

    }

    private void validateInput() {

    }

}

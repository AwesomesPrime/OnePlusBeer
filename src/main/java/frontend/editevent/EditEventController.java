package frontend.editevent;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.EmployeeController;
import entities.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import validation.InputValidation;

import java.net.URL;
import java.util.ResourceBundle;

public class EditEventController implements Initializable {

    private final InputValidation inputValidation = new InputValidation();

    @FXML
    public void initialize(URL url, ResourceBundle rb){

    }

    @FXML
    public void clickEdit(ActionEvent event){

    }

    private void validateInput() {

    }

}

package frontend.editemployee;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    public void initialize(URL url, ResourceBundle rb){

    }

    @FXML
    public void clickEdit(ActionEvent event){
        System.out.println("Clicked Edit");
    }

}

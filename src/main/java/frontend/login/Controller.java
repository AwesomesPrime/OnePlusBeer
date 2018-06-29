package frontend.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    JFXTextField txtUsername;
    @FXML
    JFXPasswordField txtPassword;
    @FXML
    private GridPane LoginPane;

    @FXML
    public void initialize(URL url, ResourceBundle rb){
        txtUsername.setLabelFloat(true);
        txtPassword.setLabelFloat(true);
    }

    @FXML
    public void checkLogin(ActionEvent event) throws IOException {

        System.out.println("Clicked Login");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/mainMenu/mainMenu.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root,1000,800);
        Stage stage = (Stage) LoginPane.getScene().getWindow();
        stage.setScene(newScene);
    }

}

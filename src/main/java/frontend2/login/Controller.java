package frontend2.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import orm.UserDatabaseService;
import usermanagement.ActiveUser;

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
        UserDatabaseService userService = new UserDatabaseService();
        User user = userService.getUserByEmail(txtUsername.getText());

        if(user != null){
            if(user.getPassword().equals(txtPassword.getText())){
                new ActiveUser(user);
                System.out.println(ActiveUser.getMailAddress() + " => Permissionlevel: " + ActiveUser.getPermission());

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/mainMenu/mainMenu.fxml"));
                Parent root = fxmlLoader.load();
                Scene newScene = new Scene(root,1000,800);
                Stage stage = (Stage) LoginPane.getScene().getWindow();
                stage.setScene(newScene);
            }else {
                System.out.println("Das Passwort ist falsch!");
            }
        }else{
            System.out.println("Nutzer existiert nicht!");
        }
    }

}

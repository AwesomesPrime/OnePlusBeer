package frontend.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import orm.UserDatabaseService;
import usermanagement.ActiveUser;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    JFXTextField txtUsername;

    @FXML
    JFXPasswordField txtPassword;

    @FXML
    public void initialize(URL url, ResourceBundle rb){
        txtUsername.setLabelFloat(true);
        txtPassword.setLabelFloat(true);
    }

    @FXML
    public void checkLogin(ActionEvent event){
        UserDatabaseService userService = new UserDatabaseService();
        User user = userService.getUserByEmail(txtUsername.getText());

        if(user != null){
            if(user.getPassword().equals(txtPassword.getText())){
                new ActiveUser(user);
                System.out.println(ActiveUser.getMailAddress() + " => Permissionlevel: " + ActiveUser.getPermission());
            }else {
                System.out.println("Das Passwort ist falsch!");
            }
        }else{
            System.out.println("Nutzer existiert nicht!");
        }
    }

}

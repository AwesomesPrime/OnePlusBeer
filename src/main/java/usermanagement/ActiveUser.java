package usermanagement;

import entities.User;
import javafx.beans.property.SimpleStringProperty;
import java.io.Serializable;

public class ActiveUser implements Serializable {
    private static User activeUser;
    private static int permission;
    private static String mailAddress;
    private static String username = "NoBody";
    public static SimpleStringProperty activeUserName = new SimpleStringProperty("Nope");

    public ActiveUser(User activeUser){
        activeUser = activeUser;
        permission = activeUser.getUserPermission().getId();
        mailAddress = activeUser.getEmployee().getMailAddress();
        username = activeUser.getEmployee().getFirstName() + " " + activeUser.getEmployee().getLastName();
        activeUserName = new SimpleStringProperty(activeUser.getEmployee().getFirstName() + " " + activeUser.getEmployee().getLastName());
    }

    public ActiveUser(){

    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static int getPermission() {
        return permission;
    }

    public static String getMailAddress() {
        return mailAddress;
    }

    public static String getUsername() {
        return username;
    }

    public static SimpleStringProperty getActiveUserName() {
        return activeUserName;
    }
}

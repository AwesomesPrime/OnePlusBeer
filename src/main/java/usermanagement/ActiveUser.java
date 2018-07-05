package usermanagement;

import entities.Employee;

import java.io.Serializable;

public class ActiveUser implements Serializable {
    private static Employee activeUser;
    private static int permission;
    private static String username;
    private static String mailAdress;
    private static boolean set = false;

    public ActiveUser(Employee activeUser){
        activeUser = activeUser;
        permission = activeUser.getUserPermission().getId();
        username = activeUser.getFirstName() + " " + activeUser.getLastName();
        mailAdress = activeUser.getMailAddress();
        set = true;
    }

    public ActiveUser(){

    }

    public static Employee getActiveUser() {
        return activeUser;
    }

    public static int getPermission() {
        return permission;
    }

    public static String getUsername() {
        return username;
    }

    public static String getMailAdress() {
        return mailAdress;
    }

    public static boolean isSet(){
        return set;
    }
}

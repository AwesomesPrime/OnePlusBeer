package usermanagement;

import entities.User;

public class ActiveUser {
    private static User activeUser;
    private static int permission;
    private static String mailAddress;
    private static String username;

    public ActiveUser(User activeUser){
        activeUser = activeUser;
        permission = activeUser.getUserPermission().getId();
        mailAddress = activeUser.getEmployee().getMailAddress();
        username = activeUser.getEmployee().getFirstName() + " " + activeUser.getEmployee().getLastName();
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
}

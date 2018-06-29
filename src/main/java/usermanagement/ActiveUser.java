package usermanagement;

import entities.User;

public class ActiveUser {
    private static User activeUser;
    private static int permission;
    private static String mailAddress;

    public ActiveUser(User activeUser){
        activeUser = activeUser;
        permission = activeUser.getUserPermission().getId();
        mailAddress = activeUser.getEmployee().getMailAddress();
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
}

package orm;

import entities.UserPermission;

import java.util.ArrayList;

/**
 * Created by Ulokal on 26.06.2018.
 */
public class UserPermissionDatabaseService extends GenericDatabaseService<UserPermission> {

    public UserPermissionDatabaseService(){
        ArrayList<UserPermission> userpermissions = this.getAll(UserPermission.class);
        if(userpermissions.size() == 0){
            UserPermission standard = new UserPermission("Standard");
            UserPermission planer = new UserPermission("Planer");
            UserPermission manager = new UserPermission("Manager");
            UserPermission admin = new UserPermission("Admin");
            this.save(standard);
            this.save(planer);
            this.save(manager);
            this.save(admin);
        }
    }
}

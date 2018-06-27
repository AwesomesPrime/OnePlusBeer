package orm;

import entities.Employee;
import entities.User;
import entities.UserPermission;

import java.util.ArrayList;

/**
 * Created by Ulokal on 26.06.2018.
 */
public class UserDatabaseService extends GenericDatabaseService<User> {

    public UserDatabaseService(){
        EmployeeDatabaseService employeeService = new EmployeeDatabaseService();
        UserPermissionDatabaseService userPermissionService = new UserPermissionDatabaseService();

        ArrayList<User> userpermissions = this.getAll(User.class);
        if(userpermissions.size() == 0){
            entities.User user = new entities.User( employeeService.get(Employee.class, 1), userPermissionService.get(UserPermission.class, 2), "pwtest");
            this.save(user);
        }
    }
}

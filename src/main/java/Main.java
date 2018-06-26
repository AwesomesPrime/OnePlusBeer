import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import orm.GenericDatabaseService;

import java.util.ArrayList;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("frontend/login/login.fxml"));
        primaryStage.setTitle("OnePlusBier");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args){

        /*GenericDatabaseService<Employee> employeeService = new GenericDatabaseService<Employee>();
        GenericDatabaseService<UserPermission> userPermissionService = new GenericDatabaseService<UserPermission>();
        GenericDatabaseService<User> userService = new GenericDatabaseService<User>();


        Employee employee = new Employee( "Herr", "Robin", "Kitzelmann", "Nordring", 60, 42579, "Heiligenhaus", "0123456789", "015902633063", "robin.kitzelmann@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50, "01.01.2010", 0, 0, "684312468473214", 0, "Comment" );
        employeeService.save(employee);
        employee = new Employee( "Herr", "Robi", "Kitzelmann", "Nordring", 60, 42579, "Heiligenhaus", "0123456789", "015902633063", "robin.kitzelmann@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50, "01.01.2010", 0, 0, "684312468473214", 0, "Comment" );
        employeeService.save(employee);
        employee = new Employee( "Herr", "Rob", "Kitzelmann", "Nordring", 60, 42579, "Heiligenhaus", "0123456789", "015902633063", "robin.kitzelmann@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50, "01.01.2010", 0, 0, "684312468473214", 0, "Comment" );
        employeeService.save(employee);

        UserPermission userpermission = new UserPermission("Manager");
        userPermissionService.save(userpermission);

        User user = new User(employee, userpermission, "pwtest");
        userService.save(user);


        ArrayList<Employee> employees = employeeService.getAll(Employee.class);
        for(Employee e: employees){
            System.out.println(e.toString());
        }

        ArrayList<UserPermission> userpermissions = userPermissionService.getAll(UserPermission.class);
        for(UserPermission up: userpermissions){
            System.out.println(up.toString());
        }

        ArrayList<User> users = userService.getAll(User.class);
        for(User u: users){
            System.out.println(u.toString());
        }*/

        launch(args);
    }
}

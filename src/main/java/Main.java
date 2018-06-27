import entities.User;
import entities.UserPermission;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import orm.EmployeeDatabaseService;
import orm.GenericDatabaseService;
import orm.UserDatabaseService;
import orm.UserPermissionDatabaseService;

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

        EmployeeDatabaseService employeeService = new EmployeeDatabaseService();
        UserPermissionDatabaseService userPermissionService = new UserPermissionDatabaseService();
        UserDatabaseService userService = new UserDatabaseService();

        entities.Employee employee = new entities.Employee( "Herr", "Robin", "Kitzelmann", "Nordring", 60, 42579, "Heiligenhaus", "0123456789", "015902633063", "robin.kitzelmann@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50, "01.01.2010", 0, 0, "684312468473214", 0, "Comment" );
        employeeService.save(employee);
        employee = new entities.Employee( "Herr", "Robi", "Kitzelmann", "Nordring", 60, 42579, "Heiligenhaus", "0123456789", "015902633063", "robin.kitzelmann@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50, "01.01.2010", 0, 0, "684312468473214", 0, "Comment" );
        employeeService.save(employee);
        employee = new entities.Employee( "Herr", "Rob", "Kitzelmann", "Nordring", 60, 42579, "Heiligenhaus", "0123456789", "015902633063", "robin.kitzelmann@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50, "01.01.2010", 0, 0, "684312468473214", 0, "Comment" );
        employeeService.save(employee);

        entities.User user = new entities.User(employee, userPermissionService.get(UserPermission.class, 2), "pwtest");
        userService.save(user);


        ArrayList<entities.Employee> employees = employeeService.getAll(entities.Employee.class);
        for(entities.Employee e: employees){
            System.out.println(e.toString());
        }

        ArrayList<entities.UserPermission> userpermissions = userPermissionService.getAll(entities.UserPermission.class);
        for(entities.UserPermission up: userpermissions){
            System.out.println(up.toString());
        }

        ArrayList<entities.User> users = userService.getAll(entities.User.class);
        for(entities.User u: users){
            System.out.println(u.toString());
        }

        launch(args);
    }
}

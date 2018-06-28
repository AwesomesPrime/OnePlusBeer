import entities.Employee;
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
        Parent root = FXMLLoader.load(getClass().getResource("frontend/editevent/editevent.fxml"));
        primaryStage.setTitle("OnePlusBier");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    public static void main(String[] args){
        UserPermissionDatabaseService userPermissionService = new UserPermissionDatabaseService();
        EmployeeDatabaseService employeeService = new EmployeeDatabaseService();
        UserDatabaseService userService = new UserDatabaseService();


        List<Employee> employees = employeeService.getAll(entities.Employee.class);
        for(entities.Employee e: employees){
            System.out.println(e.toString());
        }

        List<entities.UserPermission> userpermissions = userPermissionService.getAll(entities.UserPermission.class);
        for(entities.UserPermission up: userpermissions){
            System.out.println(up.toString());
        }

        List<entities.User> users = userService.getAll(entities.User.class);
        for(entities.User u: users){
            System.out.println(u.toString());
        }

        launch(args);
    }
}

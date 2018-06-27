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
        Parent root = FXMLLoader.load(getClass().getResource("frontend/editemployee/editemployee.fxml"));
        primaryStage.setTitle("OnePlusBier");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    public static void main(String[] args){

        /*GenericDatabaseService<entities.Employee> employeeService = new GenericDatabaseService<entities.Employee>();
        GenericDatabaseService<entities.UserPermission> userPermissionService = new GenericDatabaseService<entities.UserPermission>();
        GenericDatabaseService<entities.User> userService = new GenericDatabaseService<entities.User>();


        entities.Employee employee = new entities.Employee( "Herr", "Robin", "Kitzelmann", "Nordring", 60, 42579, "Heiligenhaus", "0123456789", "015902633063", "robin.kitzelmann@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50, "01.01.2010", 0, 0, "684312468473214", 0, "Comment" );
        employeeService.save(employee);
        employee = new entities.Employee( "Herr", "Robi", "Kitzelmann", "Nordring", 60, 42579, "Heiligenhaus", "0123456789", "015902633063", "robin.kitzelmann@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50, "01.01.2010", 0, 0, "684312468473214", 0, "Comment" );
        employeeService.save(employee);
        employee = new entities.Employee( "Herr", "Rob", "Kitzelmann", "Nordring", 60, 42579, "Heiligenhaus", "0123456789", "015902633063", "robin.kitzelmann@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50, "01.01.2010", 0, 0, "684312468473214", 0, "Comment" );
        employeeService.save(employee);

        entities.UserPermission userpermission = new entities.UserPermission("Manager");
        userPermissionService.save(userpermission);

        entities.User user = new entities.User(employee, userpermission, "pwtest");
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
        }*/

        launch(args);
    }
}

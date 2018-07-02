import entities.Employee;
import entities.Event;
import entities.User;
import entities.UserPermission;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import orm.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("frontend2/layout/basic.fxml"));
        primaryStage.setTitle("OnePlusBeer");
        Scene mainScene = new Scene(root, 1000, 800);
        mainScene.getStylesheets().add(Main.class.getResource("/styles/basic.css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.show();
    }

    public static void main(String[] args){
        loadSampleData();
        launch(args);
    }

    private static void loadSampleData(){
        UserPermissionDatabaseService userPermissionService = new UserPermissionDatabaseService();
        EmployeeDatabaseService employeeService = new EmployeeDatabaseService();
        UserDatabaseService userService = new UserDatabaseService();
        EventDatabaseService eventDatabaseService = new EventDatabaseService();

        /*Load Initial Data for Userpermission */
        ArrayList<UserPermission> userpermissions = userPermissionService.getAll(UserPermission.class);
        if(userpermissions.size() == 0){
            UserPermission standard = new UserPermission("Standard");
            UserPermission planer = new UserPermission("Planer");
            UserPermission manager = new UserPermission("Manager");
            UserPermission admin = new UserPermission("Admin");

            userPermissionService.save(standard);
            userPermissionService.save(planer);
            userPermissionService.save(manager);
            userPermissionService.save(admin);
        }

        /* Load Sample Data for Employee */
        ArrayList<Employee> employees = employeeService.getAll(Employee.class);
        if(employees.size() == 0){
            Employee employee = new Employee( "Herr", "Robin", "Kitzelmann", "Nordring", 60, 42579, "Heiligenhaus", "0123456789", "015902633063", "robin.kitzelmann@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50, "2010-01-01", true, 0, "684312468473214", 0, "Comment" );
            employeeService.save(employee);
        }

        /* Load Sample Data for User */
        ArrayList<User> userlist = userService.getAll(User.class);
        if(userlist.size() == 0){
            entities.User user = new entities.User( employeeService.get(Employee.class, 1), userPermissionService.get(UserPermission.class, 2), "pwtest");
            userService.save(user);
        }


        /* Load Sample Data for Event */
        ArrayList<Event> events = eventDatabaseService.getAll(Event.class);
        if(events.size() == 0){
            Calendar startDate = Calendar.getInstance();
            startDate.set(2018,11,1, 0, 0, 0);

            Calendar endDate = Calendar.getInstance();
            endDate.set(2018,11,23, 0, 0 ,0);

            Event event = new Event( "Weihnachtsmarkt 2018", startDate.getTime() , endDate.getTime(), LocalTime.now(),LocalTime.now(),"Straße", 12, "1256", "Düsseldorf");
            eventDatabaseService.save(event);

            startDate.set(2017,11,1, 0, 0, 0);

            endDate.set(2017,11,23, 0, 0 ,0);

            event = new Event( "Weihnachtsmarkt 2017", startDate.getTime() , endDate.getTime(),LocalTime.now(),LocalTime.now(), "Hauptstraße", 19, "40597", "MalleSüd");
            System.out.println(endDate.getTime().toString());
            eventDatabaseService.save(event);
        }
    }
}

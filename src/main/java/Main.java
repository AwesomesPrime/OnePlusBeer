import entities.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import orm.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("frontend/mainMenu/MainMenu.fxml"));
        primaryStage.setTitle("OnePlusBeer");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    public static void main(String[] args){

        loadSampleData();

        /*TestOutput*/
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

        List<User> users = userService.getAll(entities.User.class);
        for(entities.User u: users){
            System.out.println(u.toString());
        }

        launch(args);
    }

    private static void loadSampleData(){
        UserPermissionDatabaseService userPermissionService = new UserPermissionDatabaseService();
        EmployeeDatabaseService employeeService = new EmployeeDatabaseService();
        UserDatabaseService userService = new UserDatabaseService();
        EventDatabaseService eventDatabaseService = new EventDatabaseService();
        ResourcePlanningDatabaseService resourcePlanningDatabaseService = new ResourcePlanningDatabaseService();
        StandDatabaseService standDatabaseService = new StandDatabaseService();

        Employee employee = null;
        Event eventOne = null;
        Event eventTwo = null;
        Stand stand = null;

        /*Load Sample Data for Userpermission */
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
            employee = new Employee( "Herr", "Robin", "Kitzelmann", "Nordring", 60, 42579, "Heiligenhaus", "0123456789", "015902633063", "robin.kitzelmann@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50, "01.01.2010", true, 0, "684312468473214", 0, "Comment" );
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

            eventOne = new Event( "Weihnachtsmarkt 2018", startDate.getTime() , endDate.getTime(), LocalTime.now(),LocalTime.now(),"Straße", 12, "1256", "Düsseldorf");
            eventDatabaseService.save(eventOne);

            startDate.set(2017,11,1, 0, 0, 0);

            endDate.set(2017,11,23, 0, 0 ,0);

            eventTwo = new Event( "Weihnachtsmarkt 2017", startDate.getTime() , endDate.getTime(),LocalTime.now(),LocalTime.now(), "Hauptstraße", 19, "40597", "MalleSüd");
            System.out.println(endDate.getTime().toString());
            eventDatabaseService.save(eventTwo);
        }

        /* Load Sample Data for Stand */
        ArrayList<Stand> stands = standDatabaseService.getAll(Stand.class);
        if(stands.size() == 0){
            StandDescription standDescription = new StandDescription("Oettinger Stand", "Bierstand", "IloveBier");
            stand = new Stand("hanstraße","1544","malleSüd",LocalTime.now(), LocalTime.MIDNIGHT, standDescription);
            standDatabaseService.save(stand);
        }

        /* Load Sample Data for ResourcePlanning */
        ArrayList<ResourcePlanning> resourcePlannings = resourcePlanningDatabaseService.getAll(ResourcePlanning.class);
        if(resourcePlannings.size() == 0){

            Calendar startWorkingTime = Calendar.getInstance();
            startWorkingTime.set(2018,11,1,8,0,0);

            Calendar endWorkingTime = Calendar.getInstance();
            endWorkingTime.set(2018,11,1,18,0,0);

            ResourcePlanning resourcePlanningOne = new ResourcePlanning(employee,eventOne,stand,startWorkingTime.getTime(),endWorkingTime.getTime(),30,30.4);
            resourcePlanningDatabaseService.save(resourcePlanningOne);

            startWorkingTime.set(2018,11,2,8,0,0);
            endWorkingTime.set(2018,11,2,16,0,0);

            ResourcePlanning resourcePlanningTwo = new ResourcePlanning(employee,eventOne,stand,startWorkingTime.getTime(), endWorkingTime.getTime(),30,30.4);
            resourcePlanningDatabaseService.save(resourcePlanningTwo);

            startWorkingTime.set(2018,10,3,5,0,0);
            endWorkingTime.set(2018,10,3,21,0,0);

            ResourcePlanning resourcePlanningThree = new ResourcePlanning(employee,eventTwo,stand,startWorkingTime.getTime(), endWorkingTime.getTime(), 30, 30.4);
            resourcePlanningDatabaseService.save(resourcePlanningThree);
        }

    }
}

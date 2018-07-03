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

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("frontend/layout/basic.fxml"));
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
        ProfessionalStandingDatabaseService professionalStandingService = new ProfessionalStandingDatabaseService();
        StateByEmploymentLawDatabaseService stateByEmploymentLawService = new StateByEmploymentLawDatabaseService();
        EmployeeDatabaseService employeeService = new EmployeeDatabaseService();
        UserDatabaseService userService = new UserDatabaseService();
        EventDatabaseService eventDatabaseService = new EventDatabaseService();
        ResourcePlanningDatabaseService resourcePlanningDatabaseService = new ResourcePlanningDatabaseService();
        StandDatabaseService standDatabaseService = new StandDatabaseService();

        Employee employee = null;
        Event eventOne = null;
        Event eventTwo = null;
        Stand stand = null;

        /*Load Initial Data for UserPermission */
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

        /*Load Initial Data for ProfessionalStanding */
        ArrayList<ProfessionalStanding> professionalstandings = professionalStandingService.getAll(ProfessionalStanding.class);
        if(professionalstandings.size() == 0){
            ProfessionalStanding student = new ProfessionalStanding("Student");
            ProfessionalStanding auszubildender = new ProfessionalStanding("Auszubildende/r");
            ProfessionalStanding arbeitnehmer = new ProfessionalStanding("Arbeitnehmer/in");
            ProfessionalStanding beamter = new ProfessionalStanding("Beamte/r");
            ProfessionalStanding selbststaendiger = new ProfessionalStanding("Selbstständige/r");

            professionalStandingService.save(student);
            professionalStandingService.save(auszubildender);
            professionalStandingService.save(arbeitnehmer);
            professionalStandingService.save(beamter);
            professionalStandingService.save(selbststaendiger);
        }

        /*Load Initial Data for StateByEmploymentLaw */
        ArrayList<StateByEmploymentLaw> statesByEmploymentLaw = stateByEmploymentLawService.getAll(StateByEmploymentLaw.class);
        if(statesByEmploymentLaw.size() == 0){
            StateByEmploymentLaw shorttermemployment = new StateByEmploymentLaw("kurzfristige Beschäftigung");
            StateByEmploymentLaw minijob = new StateByEmploymentLaw("Minijob");
            StateByEmploymentLaw socialinsurance = new StateByEmploymentLaw("sozialversicherungspflichtig");

            stateByEmploymentLawService.save(shorttermemployment);
            stateByEmploymentLawService.save(minijob);
            stateByEmploymentLawService.save(socialinsurance);
        }

        /* Load Sample Data for Employee */
        ArrayList<Employee> employees = employeeService.getAll(Employee.class);
        if(employees.size() == 0){
            Calendar startDate = Calendar.getInstance();
            startDate.set(2018,11,1, 0, 0, 0);
            employee = new Employee( "Herr", "Robin", "Kitzelmann", "Nordring", 60, 42579, "Heiligenhaus", "0123456789", "015902633063", "robin.kitzelmann@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50,  startDate.getTime(), true, stateByEmploymentLawService.get(StateByEmploymentLaw.class, 1), "684312468473214", professionalStandingService.get(ProfessionalStanding.class, 1), "Comment" );
            employeeService.save(employee);
        }

        /* Load Sample Data for User */
        ArrayList<User> userlist = userService.getAll(User.class);
        if(userlist.size() == 0){
            User user = new User( employeeService.get(Employee.class, 1), userPermissionService.get(UserPermission.class, 2), "pwtest");
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

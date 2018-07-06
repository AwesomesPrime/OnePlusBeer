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
//        primaryStage.setMaximized(true);
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
        EventDatabaseService eventDatabaseService = new EventDatabaseService();
        EmployeePlanDatabaseService employeePlanDatabaseService = new EmployeePlanDatabaseService();
        StandDatabaseService standDatabaseService = new StandDatabaseService();

        Employee employee = null;
        Event eventOne = null;
        Event eventTwo = null;
        StandPlan standPlan = null;

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
            StateByEmploymentLaw shorttermemployment = new StateByEmploymentLaw("kurzfristige Beschäftigung", 0, 450);
            StateByEmploymentLaw minijob = new StateByEmploymentLaw("Minijob", 450, 0);
            StateByEmploymentLaw socialinsurance = new StateByEmploymentLaw("Vollerwerb", 0, 850);
            StateByEmploymentLaw midijob = new StateByEmploymentLaw("Midijob", 850, 451);

            stateByEmploymentLawService.save(shorttermemployment);
            stateByEmploymentLawService.save(minijob);
            stateByEmploymentLawService.save(socialinsurance);
            stateByEmploymentLawService.save(midijob);
        }

        /* Load Sample Data for Employee */
        ArrayList<Employee> employees = employeeService.getAll(Employee.class);
        if(employees.size() == 0){
            Calendar startDate = Calendar.getInstance();
            startDate.set(2018,11,1, 0, 0, 0);
            employee = new Employee( "Herr", "Robin", "Kitzelmann", "Nordring", "60", 42579, "Heiligenhaus", "0123456789", "015902633063", "standard@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50,  startDate.getTime(), true, stateByEmploymentLawService.get(StateByEmploymentLaw.class, 1), "684312468473214", professionalStandingService.get(ProfessionalStanding.class, 1), "Comment", userPermissionService.get(UserPermission.class, 1), "changeme" );
            employeeService.save(employee);
            employee = new Employee( "Herr", "Robin", "Kitzelmann", "Nordring", "60", 42579, "Heiligenhaus", "0123456789", "015902633063", "planer@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50,  startDate.getTime(), true, stateByEmploymentLawService.get(StateByEmploymentLaw.class, 1), "684312468473214", professionalStandingService.get(ProfessionalStanding.class, 1), "Comment", userPermissionService.get(UserPermission.class, 2), "changeme" );
            employeeService.save(employee);
            employee = new Employee( "Herr", "Robin", "Kitzelmann", "Nordring", "60", 42579, "Heiligenhaus", "0123456789", "015902633063", "manager@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50,  startDate.getTime(), true, stateByEmploymentLawService.get(StateByEmploymentLaw.class, 1), "684312468473214", professionalStandingService.get(ProfessionalStanding.class, 1), "Comment", userPermissionService.get(UserPermission.class, 3), "changeme" );
            employeeService.save(employee);
            employee = new Employee( "Herr", "Robin", "Kitzelmann", "Nordring", "60", 42579, "Heiligenhaus", "0123456789", "015902633063", "admin@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50,  startDate.getTime(), true, stateByEmploymentLawService.get(StateByEmploymentLaw.class, 1), "684312468473214", professionalStandingService.get(ProfessionalStanding.class, 1), "Comment", userPermissionService.get(UserPermission.class, 4), "changeme" );
            employeeService.save(employee);
        }
        else{
            employee = employeeService.get(Employee.class, 1);
        }


        /* Load Sample Data for Event */
        ArrayList<Event> events = eventDatabaseService.getAll(Event.class);
        if(events.size() == 0){
            Calendar startDate = Calendar.getInstance();
            startDate.set(2018,11,1, 0, 0, 0);

            Calendar endDate = Calendar.getInstance();
            endDate.set(2018,11,23, 0, 0 ,0);

            eventOne = new Event( "Weihnachtsmarkt 2018", startDate.getTime() , endDate.getTime(), LocalTime.now(),LocalTime.now(),"Straße", "12", "1256", "Düsseldorf");
            eventDatabaseService.save(eventOne);

            startDate.set(2017,11,1, 0, 0, 0);

            endDate.set(2017,11,23, 0, 0 ,0);

            eventTwo = new Event( "Weihnachtsmarkt 2017", startDate.getTime() , endDate.getTime(),LocalTime.now(),LocalTime.now(), "Hauptstraße", "19", "40597", "MalleSüd");
            eventDatabaseService.save(eventTwo);
        }

        /* Load Sample Data for StandPlan */
        ArrayList<StandPlan> standPlans = standDatabaseService.getAll(StandPlan.class);
        if(standPlans.size() == 0){
            Stand stand = new Stand("Oettinger StandPlan", "Bierstand", "IloveBier");

            standDatabaseService.save(stand);

            Calendar openingTime = Calendar.getInstance();
            Calendar closingTIme = Calendar.getInstance();

            openingTime.set(2018,11,1,8,0,0);
            closingTIme.set(2018,11,1,18,0,0);

            standPlan = new StandPlan(stand, eventOne, "hanstraße","1544","malleSüd",openingTime.getTime(), closingTIme.getTime());
            standDatabaseService.save(standPlan);
        }

        /* Load Sample Data for EmployeePlan */
        ArrayList<EmployeePlan> employeePlans = employeePlanDatabaseService.getAll(EmployeePlan.class);
        if(employeePlans.size() == 0){

            Calendar startWorkingTime = Calendar.getInstance();
            Calendar endWorkingTime = Calendar.getInstance();

            startWorkingTime.set(2018,11,1,8,0,0);
            endWorkingTime.set(2018,11,1,18,0,0);

            EmployeePlan employeePlanOne = new EmployeePlan(employee, standPlan,startWorkingTime.getTime(),endWorkingTime.getTime(),15,30.4, 20, "Hier", "Kommentar", 3.50);
            employeePlanDatabaseService.save(employeePlanOne);

            startWorkingTime.set(2018,11,2,8,0,0);
            endWorkingTime.set(2018,11,2,16,0,0);

            EmployeePlan employeePlanTwo = new EmployeePlan(employee, standPlan, startWorkingTime.getTime(), endWorkingTime.getTime(), 30, 30.4, 20, "Hier", "Kommentar", 3.50);
            employeePlanDatabaseService.save(employeePlanTwo);

            startWorkingTime.set(2018,10,3,5,0,0);
            endWorkingTime.set(2018,10,3,21,0,0);

            EmployeePlan employeePlanThree = new EmployeePlan(employee, standPlan, startWorkingTime.getTime(), endWorkingTime.getTime(), 30, 30.4, 20, "Hier", "Kommentar", 3.50);
            employeePlanDatabaseService.save(employeePlanThree);
        }
    }
}

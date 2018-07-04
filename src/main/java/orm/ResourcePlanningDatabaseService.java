package orm;

import entities.Employee;
import entities.ResourcePlanning;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by Ulokal on 29.06.2018.
 */
public class ResourcePlanningDatabaseService extends GenericDatabaseService<ResourcePlanning> {

    /**
     * @param term Der Suchbegriff mit dem alle Attribute der ResourcePlaning durchsucht werden
     * @return Gibt eine ArrayList mit allen übereinstimmenden ResourcePlanings zurück
     */
    public ArrayList<ResourcePlanning> search(String term) {
        ArrayList<ResourcePlanning> allEvents = this.getAll(ResourcePlanning.class);
        ArrayList<ResourcePlanning> resultEvents = allEvents.stream().filter(resourcePlanning -> Integer.toString(resourcePlanning.getId()).contains((term)) ||
                resourcePlanning.getStartWorkingTime().toString().contains(term) ||
                resourcePlanning.getEndWorkingTime().toString().contains(term) ||
                Long.toString(resourcePlanning.getPauseTime()).contains(term) ||
                Double.toString(resourcePlanning.getTravelExpenses()).contains(term)).collect(Collectors.toCollection(ArrayList::new));

        return resultEvents;
    }

    /**
     * @param startDate StartDatum nach dem gefiltert werden soll
     * @param endDate EndDatum nach dem gefiltert werden soll
     * @return ArrayList mit allen Einträge die in dem Zeitraum liegen
     */
    public ArrayList<ResourcePlanning> filterWorkingTimeByDate(Date startDate, Date endDate){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<ResourcePlanning> resultResourcePlanning;

        try{
            resultResourcePlanning = (ArrayList<ResourcePlanning>) session.createCriteria(ResourcePlanning.class)
                    .add(Restrictions.between("startWorkingTime",startDate,endDate))
                    .list();
        } catch (Exception e){
            e.printStackTrace();
            resultResourcePlanning = null;
        }
        finally {
            transaction.commit();
            session.close();
        }

        return resultResourcePlanning;
    }

    /**
     * @param email Email des Employees dessen Pläne man haben möchte
     * @return ArrayList mit allen ResourcePlnanings des Employees die in der Zukunft liegen
     */
    public ArrayList<ResourcePlanning> filterNextPlansByEmail(String email){
        ArrayList<ResourcePlanning> resultResourcePlaning;
        EmployeeDatabaseService employeeDatabaseService = new EmployeeDatabaseService();
        Employee employee = employeeDatabaseService.getEmployeeByEmail(email);


        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            Calendar clad = Calendar.getInstance();
             resultResourcePlaning = (ArrayList<ResourcePlanning>) session.createCriteria(ResourcePlanning.class)
                     .createAlias("employee", "e")
                     .add(Restrictions.and(Restrictions.gt("startWorkingTime", clad.getTime()),
                             Restrictions.eq("e.id",employee.getId())))
                     .list();
        } catch (Exception e){
            e.printStackTrace();
            resultResourcePlaning = null;
        } finally {
            transaction.commit();
            session.close();
        }

        return resultResourcePlaning;



    }
}

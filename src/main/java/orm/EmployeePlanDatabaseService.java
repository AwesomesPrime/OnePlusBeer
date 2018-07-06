package orm;

import entities.Employee;
import entities.EmployeePlan;
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
public class EmployeePlanDatabaseService extends GenericDatabaseService<EmployeePlan> {

    /**
     * @param term Der Suchbegriff mit dem alle Attribute der ResourcePlaning durchsucht werden
     * @return Gibt eine ArrayList mit allen übereinstimmenden ResourcePlanings zurück
     */
    public ArrayList<EmployeePlan> search(String term) {
        ArrayList<EmployeePlan> allEvents = this.getAll(EmployeePlan.class);
        ArrayList<EmployeePlan> resultEvents = allEvents.stream().filter(resourcePlanning -> Integer.toString(resourcePlanning.getId()).contains((term)) ||
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
    public ArrayList<EmployeePlan> filterWorkingTimeByDate(Date startDate, Date endDate){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<EmployeePlan> resultEmployeePlan;

        try{
            resultEmployeePlan = (ArrayList<EmployeePlan>) session.createCriteria(EmployeePlan.class)
                    .add(Restrictions.between("startWorkingTime",startDate,endDate))
                    .list();
        } catch (Exception e){
            e.printStackTrace();
            resultEmployeePlan = null;
        }
        finally {
            transaction.commit();
            session.close();
        }

        return resultEmployeePlan;
    }

    /**
     * @param email Email des Employees dessen Pläne man haben möchte
     * @return ArrayList mit allen ResourcePlnanings des Employees die in der Zukunft liegen
     */
    public ArrayList<EmployeePlan> filterNextPlansByEmail(String email){
        ArrayList<EmployeePlan> resultResourcePlaning;
        EmployeeDatabaseService employeeDatabaseService = new EmployeeDatabaseService();
        Employee employee = employeeDatabaseService.getEmployeeByEmail(email);


        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            Calendar clad = Calendar.getInstance();
             resultResourcePlaning = (ArrayList<EmployeePlan>) session.createCriteria(EmployeePlan.class)
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

    public ArrayList<EmployeePlan> getResourcePlansInMonth (Employee employee, int month, int year){
        ArrayList<EmployeePlan> employeesResourcePlanings;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try{
            Calendar lowCalendar = Calendar.getInstance();
            lowCalendar.set(year,month,1,0,0,0);

            Calendar highCalendar = Calendar.getInstance();
            highCalendar.set(year,month,31,0,0,0);

            employeesResourcePlanings = (ArrayList<EmployeePlan>) session.createCriteria(EmployeePlan.class)
                    .createAlias("employee", "e")
                    .add(Restrictions.and(Restrictions.eq("e.id", employee.getId()),
                            Restrictions.between("startWorkingTime", lowCalendar.getTime(), highCalendar.getTime())))
                    .list();
        } catch (Exception e){
            e.printStackTrace();
            employeesResourcePlanings = null;
        }
        finally {
            transaction.commit();
            session.close();
        }

        return employeesResourcePlanings;
    }
}

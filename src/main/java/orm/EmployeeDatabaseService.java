package orm;

import entities.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ulokal on 26.06.2018.
 */
public class EmployeeDatabaseService extends GenericDatabaseService<Employee> {

    public EmployeeDatabaseService(){
    }

    public ArrayList<Employee> search (String term){
        ArrayList<Employee> allEmployees = this.getAll(Employee.class);

        ArrayList<Employee> resultEmployees =  allEmployees.stream().filter(employee -> Boolean.toString(employee.getActivityState()).contains(term)||
                                                                                        Integer.toString(employee.getId()).contains(term) ||
                                                                                        employee.getSalutation().contains(term) ||
                                                                                        employee.getFirstName().contains(term) ||
                                                                                        employee.getLastName().contains(term) ||
                                                                                        employee.getLastName().contains(term) ||
                                                                                        Integer.toString(employee.getHouseNumber()).contains(term)||
                                                                                        Integer.toString(employee.getPlz()).contains(term) ||
                                                                                        employee.getCity().contains(term) ||
                                                                                        employee.getPhoneNumber().contains(term) ||
                                                                                        employee.getMobileNumber().contains(term) ||
                                                                                        employee.getMailAddress().contains(term) ||
                                                                                        employee.getIban().contains(term) ||
                                                                                        employee.getBic().contains(term) ||
                                                                                        Double.toString(employee.getBruttoPerHour()).contains(term) ||
                                                                                        employee.getStateByEmploymentLaw().getDescription().contains(term) ||
                                                                                        employee.getTaxNumber().contains(employee.getTaxNumber()) ||
                                                                                        employee.getProfessionalStanding().getDescription().contains(term) ||
                                                                                        employee.getComments().contains(term)).collect(Collectors.toCollection(ArrayList::new));

        return resultEmployees;
    }


    public ArrayList<Employee> filterByName(String lastName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Employee> resultEmployees;

        try{
            resultEmployees = (ArrayList<Employee>) session.createCriteria(Employee.class)
                    .add(Restrictions.like("lastName", lastName))
                    .list();
        } catch (Exception e){
            e.printStackTrace();
            resultEmployees = null;
        }
        finally {
            transaction.commit();
            session.close();
        }

        return resultEmployees;
    }

    public ArrayList<Employee> filterByName(String lastName, String firstName){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Employee> resultEmployees;

        try{
            resultEmployees = (ArrayList<Employee>) session.createCriteria(Employee.class)
                    .add( Restrictions.and(Restrictions.like("lastName", lastName) ,
                                           Restrictions.like("firstName" , firstName)))
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            resultEmployees = null;
        }
        finally {
            transaction.commit();
            session.close();
        }

        return resultEmployees;
    }
}

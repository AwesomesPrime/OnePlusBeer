package orm;

import entities.Employee;

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
                                                                                        employee.getStartOfEmployment().contains(term) ||
                                                                                        Integer.toString(employee.getStateByEmploymentLaw()).contains(term) ||
                                                                                        employee.getTaxNumber().contains(employee.getTaxNumber()) ||
                                                                                        Integer.toString(employee.getWorkingStatus()).contains(term) ||
                                                                                        employee.getComments().contains(term)).collect(Collectors.toCollection(ArrayList::new));

        return resultEmployees;
    }
}

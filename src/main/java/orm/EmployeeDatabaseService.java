package orm;

import entities.Employee;

import java.util.ArrayList;

/**
 * Created by Ulokal on 26.06.2018.
 */
public class EmployeeDatabaseService extends GenericDatabaseService<Employee> {

    public EmployeeDatabaseService(){
        ArrayList<Employee> employees = this.getAll(Employee.class);
        if(employees.size() == 0){
            Employee employee = new Employee( "Herr", "Robin", "Kitzelmann", "Nordring", 60, 42579, "Heiligenhaus", "0123456789", "015902633063", "robin.kitzelmann@yahoo.de","DE01 2345 6789 1234 5678 90", "WEAREBIC", 8.50, "01.01.2010", true, 0, "684312468473214", 0, "Comment" );
            this.save(employee);
        }
    }
}

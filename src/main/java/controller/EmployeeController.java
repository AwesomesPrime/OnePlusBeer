package controller;

import entities.Employee;
import orm.EmployeeDatabaseService;

import java.util.ArrayList;

public class EmployeeController {

    public void addEmployee(Employee employee) {

        EmployeeDatabaseService employeeService = new EmployeeDatabaseService();
        employeeService.save(employee);
    }

    public ArrayList<Employee> getAllActiveEmployee() {

        EmployeeDatabaseService employeeService = new EmployeeDatabaseService();
        ArrayList<Employee> employeeListAll =  employeeService.getAll( Employee.class);
        ArrayList<Employee> employeeList = new ArrayList<Employee>();

        for (Employee employee:employeeListAll) {
            if (employee.getActivityState()){
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

}

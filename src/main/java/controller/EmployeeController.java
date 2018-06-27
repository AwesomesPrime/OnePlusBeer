package controller;

import entities.Employee;
import orm.EmployeeDatabaseService;

public class EmployeeController {

    public void addEmployee(Employee employee) {

        EmployeeDatabaseService employeeService = new EmployeeDatabaseService();
        employeeService.save(employee);
    }

}

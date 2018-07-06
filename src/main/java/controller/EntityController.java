package controller;

import entities.*;
import orm.*;

import java.util.ArrayList;

public class EntityController {

    public <T> void save(Class<T> type, T entity){
        if(type == Employee.class){
            EmployeeDatabaseService service = new EmployeeDatabaseService();
            service.save(entity);
        }else if(type == Stand.class){
            StandDatabaseService service = new StandDatabaseService();
            service.save(entity);
        }else if(type == Event.class){
            EventDatabaseService service = new EventDatabaseService();
            service.save(entity);
        }else if(type == StandPlan.class){
            StandPlanDatabaseService service = new StandPlanDatabaseService();
            service.save(entity);
        }else if(type == EmployeePlan.class){
            EmployeePlanDatabaseService service = new EmployeePlanDatabaseService();
            service.save(entity);
        }
    }

    public <T> void delete(Class<T> type, T entity){
        if(type == Employee.class){
            EmployeeDatabaseService service = new EmployeeDatabaseService();
            service.delete(entity);
        }else if(type == Stand.class){
            StandDatabaseService service = new StandDatabaseService();
            service.delete(entity);
        }else if(type == Event.class){
            EventDatabaseService service = new EventDatabaseService();
            service.delete(entity);
        }else if(type == StandPlan.class){
            StandPlanDatabaseService service = new StandPlanDatabaseService();
            service.delete(entity);
        }else if(type == EmployeePlan.class){
            EmployeePlanDatabaseService service = new EmployeePlanDatabaseService();
            service.delete(entity);
        }
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

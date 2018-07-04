package interfaces;

import entities.Employee;

import java.util.Date;

public interface Validation {
    public boolean validateStartDateBeforeEndDate(Date start, Date end);
    public boolean validateLegalWorktime(Employee employee, double planedWorkTime);
}

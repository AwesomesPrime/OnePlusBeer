package validation;

import entities.Employee;
import enums.EmploymentLawStates;
import enums.WorkingStatus;
import interfaces.Validation;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constants.Constants.MINIJOB_BRUTTO_PER_MONTH;
import static enums.EmploymentLawStates.KURZFRISTG_BESCHAEFTIGT;

public class InputValidation implements Validation {

    public boolean validateStartDateBeforeEndDate(Date start, Date end) { return start.before(end);}

}

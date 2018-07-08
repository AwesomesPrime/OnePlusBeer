package validation;

import interfaces.Validation;

import java.util.Date;

public class InputValidation implements Validation {

    public boolean validateStartDateBeforeEndDate(Date start, Date end) { return start.before(end);}
}

package validation;

import interfaces.Validation;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation implements Validation {

    public boolean validateStartDateBeforeEndDate(Date start, Date end) { return start.before(end);}

}

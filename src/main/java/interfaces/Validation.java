package interfaces;

import java.util.Date;

public interface Validation {
    public boolean validateStartDateBeforeEndDate(Date start, Date end);
}

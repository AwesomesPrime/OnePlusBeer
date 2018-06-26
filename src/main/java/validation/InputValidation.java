package validation;

import interfaces.Validation;

import java.util.Date;

public class InputValidation implements Validation {

    public boolean validateText(String text) {
        return text.matches("[a-z|A-Z| ä|ö|ü|ß|Ö|Ä|Ü]+");
    }

    public boolean validateZIP(String zip) {
        return zip.matches("[\\d]{5}");
    }

    public boolean validateStartDateBeforeEndDate(Date start, Date end) { return start.before(end);}

    public boolean validateEmail(String mail) {
        return mail.matches("[a-z]+@{1}[a-z]+.{1}[a-z]+");
    }

    public boolean validateIBAN(String iban) {
        return iban.matches("^DE\\d{2}\\s?([0-9a-zA-Z]{4}\\s?){4}[0-9a-zA-Z]{2}$");
    }

    public boolean validateBIC(String bic) {
        return bic.matches("([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)");
    }

    public boolean validateWorktime(Long worktimeInMin) {
        return worktimeInMin < 540L;
    }

}

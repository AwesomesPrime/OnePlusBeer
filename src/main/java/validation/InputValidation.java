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

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * validiert texteingabe auf deutsche Buchstaben und Leerzeichen
     * @param text zu prüfender Text
     * @return boolean
     */
    public boolean validateText(String text) {
        return text.matches("[a-z|A-Z| ä|ö|ü|ß|Ö|Ä|Ü]+");
    }


    /** validiert deutsche PLZ
     * @param zip zu prüfende PLZ
     * @return boolean
     */
    public boolean validateZIP(String zip) {
        return zip.matches("[\\d]{5}");
    }

    /** prüft ob das start datum vor dem end datum liegt
     * @param start zu prüfendes start datum
     * @param end zu prüfendes end datum
     * @return boolean
     */
    public boolean validateStartDateBeforeEndDate(Date start, Date end) { return start.before(end);}

    /** validiert email adresse
     * @param emailStr email die geprüft werden soll
     * @return boolean
     */
    public boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    /** validiert IBAN
     * @param iban zu prüfende IBAN
     * @return boolean
     */
    public boolean validateIBAN(String iban) {
        return iban.matches("^DE\\d{2}\\s?([0-9a-zA-Z]{4}\\s?){4}[0-9a-zA-Z]{2}$");
    }

    /** validiert BIC
     * @param bic zu validierende BIC
     * @return boolean
     */
    public boolean validateBIC(String bic) {
        return bic.matches("([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)");
    }

    /** prüft die gesetzlich maximale Arbeitszeit in Minuten
     * @param worktimeInMin zu überprüfende Arbeitszeit in Minuten
     * @return boolean
     */
    public boolean validateWorktime(Long worktimeInMin) {
        return worktimeInMin < 540L;
    }

    /** validiert Telefonnummer
     * @param phone zu validierende Telefonnummer
     * @return boolean
     */
    public boolean validatePhone(String phone) {
        return phone.matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");
    }

    public boolean validateLegalWorktime(Employee employee, double planedWorkTime) {
        if ( employee.getStateByEmploymentLaw()== EmploymentLawStates.MINIJOB ) {
            double allowedHoursPerMonth = getAllowedHoursPerMonthBasedOnEmplyomentStatus(employee);
            double remainingHours = allowedHoursPerMonth - employee.getWorkedTimePerMonthInHours();
            if ((remainingHours - planedWorkTime) >= 0){
                return true;
            }
            return false;
        }
        return true;
    }

    public double getAllowedHoursPerMonthBasedOnEmplyomentStatus(Employee employee) {
        return MINIJOB_BRUTTO_PER_MONTH/employee.getBruttoPerHour();
    }

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

}

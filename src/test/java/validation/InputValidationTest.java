package validation;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class InputValidationTest {

    private final InputValidation inputValidation = new InputValidation();

    /**
     * Unit Test für Text Validation
     */
    @Test
    public void validateText() {
        // GIVEN
        String toPass = "Haasd as Üöopa ß";

        // WHEN
        boolean expected = inputValidation.validateText(toPass);

        // THEN
        Assert.assertTrue(expected);

    }

    @Test
    public void validateText_toBeFalse() {
        // GIVEN
        String toFail = "Haasd as Üöopa 12";

        // WHEN
        boolean expected = inputValidation.validateText(toFail);

        // THEN
        Assert.assertFalse(expected);

    }

    /**
     * Unit Test für PLZ Validation
     */
    @Test
    public void validateZip() {
        // GIVEN
        String toPass = "40882";

        // WHEN
        boolean expected = inputValidation.validateZIP(toPass);

        // THEN
        Assert.assertTrue(expected);

    }

    @Test
    public void validateZip_toBeFalse() {
        // GIVEN
        String toFail = "12345t";

        // WHEN
        boolean expected = inputValidation.validateZIP(toFail);

        // THEN
        Assert.assertFalse(expected);

    }

    /**
     * Unit Test für Datums Validation
     */

    @Test
    public void validateStartDateBeforeEndDate() {
        // GIVEN
        Date dt1 = new Date();
        Date dt2 = new Date(dt1.getTime() + (1000 * 60 * 60 * 24));

        // WHEN
        boolean expected = inputValidation.validateStartDateBeforeEndDate(dt1,dt2);

        // THEN
        Assert.assertTrue(expected);

    }

    @Test
    public void validateStartDateBeforeEndDate_toBeFalse() {
        // GIVEN
        Date dt1 = new Date();
        Date dt2 = new Date(dt1.getTime() + (1000 * 60 * 60 * 24));

        // WHEN
        boolean expected = inputValidation.validateStartDateBeforeEndDate(dt2,dt1);

        // THEN
        assertFalse(expected);
    }

    @Test
    public void validateEmail() {
        // GIVEN
        String email = "niklein@gmail.com";

        // WHEN
        boolean expected =inputValidation.validateEmail(email);

        // THEN
        Assert.assertTrue(expected);
    }

    @Test
    public void validateEmail_toBeFalse() {
        // GIVEN
        String email = "@nikleingmail.com";
        String email1 = "nikleingmail.com@";
        String email2 = "niöäüklein@gmail.com";
        String email3 = "niöäüklein@gmailcom";

        // WHEN
        boolean expected = inputValidation.validateEmail(email);
        boolean expected1 = inputValidation.validateEmail(email1);
        boolean expected2 = inputValidation.validateEmail(email2);
        boolean expected3 = inputValidation.validateEmail(email3);

        // THEN
        assertFalse(expected);
        assertFalse(expected1);
        assertFalse(expected2);
        assertFalse(expected3);
    }

    @Test
    public void validateIBAN() {
        // GIVEN
        String iban = "DE19123412341234123412";
        String iban2 = "DE19 1234 1234 1234 1234 12";
        // WHEN
        boolean expected = inputValidation.validateIBAN(iban);
        boolean expected2 = inputValidation.validateIBAN(iban2);

        // THEN
        assertTrue(expected);
        assertTrue(expected2);
    }

    @Test
    public void validateIBAN_toBeFalse() {
        // GIVEN
        String iban = "DE19123412341233412";
        String iban2 = "DE19 1234 1234ADS 34 1234 12";
        // WHEN
        boolean expected = inputValidation.validateIBAN(iban);
        boolean expected2 = inputValidation.validateIBAN(iban2);

        // THEN
        assertFalse(expected);
        assertFalse(expected2);
    }

    @Test
    public void validateBIC() {
        // GIVEN
        String bic = "DUSSDEDDXXX";
        String bic2 = "DUSSDEDDXUS";
        // WHEN
        boolean expected = inputValidation.validateBIC(bic);
        boolean expected2 = inputValidation.validateBIC(bic2);

        // THEN
        assertTrue(expected);
        assertTrue(expected2);
    }

    @Test
    public void validateBIC_ToBeFalse() {
        // GIVEN
        String bic = "DUSSDEDDXX";
        String bic2 = "DUSSDEDDXaUS";
        // WHEN
        boolean expected = inputValidation.validateBIC(bic);
        boolean expected2 = inputValidation.validateBIC(bic2);

        // THEN
        assertFalse(expected);
        assertFalse(expected2);
    }

}
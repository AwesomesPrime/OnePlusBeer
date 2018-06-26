import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name="Employee")
public class Employee
{
    //-------------------------------------------------------------------------
    //  Vars
    //-------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "salutation")
    private String salutation;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "street")
    private String street;

    @Column(name = "houseNumber")
    private int houseNumber;

    @Column(name = "plz")
    private int plz;

    @Column(name = "city")
    private String city;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "mobileNumber")
    private String mobileNumber;

    @Column(name = "mailAddress")
    private String mailAddress;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bic")
    private String bic;

    @Column(name = "f")
    private double bruttoPerHour;

    @Column(name = "startOfEmployment")
    private String startOfEmployment;

    @Column(name = "activityState")
    private int activityState;

    @Column(name = "stateByEmploymentLaw")
    private int stateByEmploymentLaw;

    @Column(name = "taxNumber")
    private String taxNumber;

    @Column(name = "workingStatus")
    private int workingStatus;

    @Column(name = "comments")
    private String comments;


    //-------------------------------------------------------------------------
    //  Constructor(s)
    //-------------------------------------------------------------------------
    public Employee() {

    }

    public Employee(String salutation, String firstName, String lastName, String street, int houseNumber, int plz, String city, String phoneNumber, String mobileNumber, String mailAddress, String iban, String bic, double bruttoPerHour, String startOfEmployment, int activityState, int stateByEmploymentLaw, String taxNumber, int workingStatus, String comments) {
        this.salutation = salutation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.houseNumber = houseNumber;
        this.plz = plz;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.mobileNumber = mobileNumber;
        this.mailAddress = mailAddress;
        this.iban = iban;
        this.bic = bic;
        this.bruttoPerHour = bruttoPerHour;
        this.startOfEmployment = startOfEmployment;
        this.activityState = activityState;
        this.stateByEmploymentLaw = stateByEmploymentLaw;
        this.taxNumber = taxNumber;
        this.workingStatus = workingStatus;
        this.comments = comments;
    }


    //-------------------------------------------------------------------------
    //  Get / Set
    //-------------------------------------------------------------------------
    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public double getBruttoPerHour() {
        return bruttoPerHour;
    }

    public void setBruttoPerHour(double bruttoPerHour) {
        this.bruttoPerHour = bruttoPerHour;
    }

    public String getStartOfEmployment() {
        return startOfEmployment;
    }

    public void setStartOfEmployment(String startOfEmployment) {
        this.startOfEmployment = startOfEmployment;
    }

    public int getActivityState() {
        return activityState;
    }

    public void setActivityState(int activityState) {
        this.activityState = activityState;
    }

    public int getStateByEmploymentLaw() {
        return stateByEmploymentLaw;
    }

    public void setStateByEmploymentLaw(int stateByEmploymentLaw) {
        this.stateByEmploymentLaw = stateByEmploymentLaw;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public int getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(int workingStatus) {
        this.workingStatus = workingStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    //-------------------------------------------------------------------------
    //  toString()
    //-------------------------------------------------------------------------
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", salutation='" + salutation + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", plz=" + plz +
                ", city='" + city + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", mobileNumber=" + mobileNumber +
                ", mailAddress='" + mailAddress + '\'' +
                ", iban='" + iban + '\'' +
                ", bic='" + bic + '\'' +
                ", bruttoPerHour=" + bruttoPerHour +
                ", startOfEmployment='" + startOfEmployment + '\'' +
                ", activityState='" + activityState + '\'' +
                ", stateByEmploymentLaw='" + stateByEmploymentLaw + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", workingStatus='" + workingStatus + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }


}

package entities;

import javax.persistence.*;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Employee Entität der Datenbank
 */
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
    private String houseNumber;

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

    @Column(name = "bruttoPerHour")
    private double bruttoPerHour;

    @Column(name = "startOfEmployment")
    private Date startOfEmployment;

    @Column(name = "activityState")
    private boolean activityState;

    @ManyToOne
    @JoinColumn(name = "stateByEmploymentLaw")
    private StateByEmploymentLaw stateByEmploymentLaw;

    @Column(name = "taxNumber")
    private String taxNumber;

    @ManyToOne
    @JoinColumn(name = "professionalStanding")
    private ProfessionalStanding professionalStanding;

    @Column(name = "comments")
    private String comments;

    @Column(name="workedTimePerMonthInHours")
    private int workedTimePerMonthInHours;

    //-------------------------------------------------------------------------
    //  Constructor(s)
    //-------------------------------------------------------------------------
    public Employee() {

    }

    public Employee(String salutation, String firstName, String lastName, String street, String houseNumber, int plz, String city, String phoneNumber, String mobileNumber, String mailAddress, String iban, String bic, double bruttoPerHour, Date startOfEmployment, boolean activityState, StateByEmploymentLaw stateByEmploymentLaw, String taxNumber, ProfessionalStanding professionalStanding, String comments) {
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
        this.professionalStanding = professionalStanding;
        this.comments = comments;
        this.workedTimePerMonthInHours = workedTimePerMonthInHours;
    }

    //-------------------------------------------------------------------------
    //  Get / Set
    //-------------------------------------------------------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
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

    public Date getStartOfEmployment() {
        return startOfEmployment;
    }

    public void setStartOfEmployment(Date startOfEmployment) {
        this.startOfEmployment = startOfEmployment;
    }

    public boolean isActive() {
        return activityState;
    }

    public boolean getActivityState() {
        return activityState;
    }

    public void setActivityState(boolean activityState) {
        this.activityState = activityState;
    }

    public StateByEmploymentLaw getStateByEmploymentLaw() {
        return stateByEmploymentLaw;
    }

    public void setStateByEmploymentLaw(StateByEmploymentLaw stateByEmploymentLaw) {
        this.stateByEmploymentLaw = stateByEmploymentLaw;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public ProfessionalStanding getProfessionalStanding() {
        return professionalStanding;
    }

    public void setProfessionalStanding(ProfessionalStanding professionalStanding) {
        this.professionalStanding = professionalStanding;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isActivityState() {
        return activityState;
    }

    public int getWorkedTimePerMonthInHours() {
        return workedTimePerMonthInHours;
    }

    public void setWorkedTimePerMonthInHours(int workedTimePerMonthInHours) {
        this.workedTimePerMonthInHours = workedTimePerMonthInHours;
    }

    //-------------------------------------------------------------------------
    //  toString()
    //-------------------------------------------------------------------------

    @Override
    public String toString() {
        return firstName + " "+ lastName;
    }
}

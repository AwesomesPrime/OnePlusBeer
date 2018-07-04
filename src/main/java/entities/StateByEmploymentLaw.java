package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Table(name="StateByEmploymentLaw")
public class StateByEmploymentLaw
{
    //-------------------------------------------------------------------------
    //  Vars
    //-------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @Column (name = "incomeMax")
    private double incomeMax;

    @Column (name = "incomeMin")
    private double incomeMin;


    //-------------------------------------------------------------------------
    //  Constructor(s)
    //-------------------------------------------------------------------------
    public StateByEmploymentLaw() {

    }

    public StateByEmploymentLaw(String description, double incomeCap, double incomeMin) {
        this.description = description;
        this.incomeMax = incomeCap;
        this.incomeMin = incomeMin;
    }

    public StateByEmploymentLaw(int id, String description,double incomeCap, double incomeMin) {
        this.id = id;
        this.description = description;
        this.incomeMax = incomeCap;
        this.incomeMin = incomeMin;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getIncomeMax() {
        return incomeMax;
    }

    public void setIncomeMax(double incomeMax) {
        this.incomeMax = incomeMax;
    }

    public double getIncomeMin() {
        return incomeMin;
    }

    public void setIncomeMin(double incomeMin) {
        this.incomeMin = incomeMin;
    }

    //-------------------------------------------------------------------------
    //  toString()
    //-------------------------------------------------------------------------

    @Override
    public String toString() {
        return "StateByEmploymentLaw{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", incomeMax=" + incomeMax +
                ", incomeMin=" + incomeMin +
                '}';
    }
}

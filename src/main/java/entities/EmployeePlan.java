package entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Ulokal on 29.06.2018.
 * Resourceplanungs Entität mit Fremdschlüssel auf Employee und StandPlan
 */
@Entity
@Table(name = "EmployeePlan")
public class EmployeePlan {

    //-------------------------------------------------------------------------
    //  Vars
    //-------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "employee")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "standPlan")
    private StandPlan standPlan;

    @Column(name = "startWorkingTime")
    private Date startWorkingTime;

    @Column(name = "endWorkingTime")
    private Date endWorkingTime;

    @Column(name ="pauseTime")
    private long pauseTime;

    @Column(name = "travelExpenses")
    private double travelExpenses;

    @Column(name = "travelDistance")
    private double travelDistance;

    @Column(name = "travelStart")
    private String travelStart;

    @Column(name = "comment")
    private String comment;

    @Column(name = "bonus")
    private double bonus;

    public EmployeePlan(){}

    public EmployeePlan(Employee employee, StandPlan standPlan, Date startWorkingTime, Date endWorkingTime, long pauseTime, double travelExpenses, double travelDistance, String travelStart, String comment, double bonus) {
        this.employee = employee;
        this.standPlan = standPlan;
        this.startWorkingTime = startWorkingTime;
        this.endWorkingTime = endWorkingTime;
        this.pauseTime = pauseTime;
        this.travelExpenses = travelExpenses;
        this.travelDistance = travelDistance;
        this.travelStart = travelStart;
        this.comment = comment;
        this.bonus = bonus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public StandPlan getStandPlan() {
        return standPlan;
    }

    public void setStandPlan(StandPlan standPlan) {
        this.standPlan = standPlan;
    }

    public Date getStartWorkingTime() {
        return startWorkingTime;
    }

    public void setStartWorkingTime(Date startWorkingTime) {
        this.startWorkingTime = startWorkingTime;
    }

    public Date getEndWorkingTime() {
        return endWorkingTime;
    }

    public void setEndWorkingTime(Date endWorkingTime) {
        this.endWorkingTime = endWorkingTime;
    }

    public long getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(long pauseTime) {
        this.pauseTime = pauseTime;
    }

    public double getTravelExpenses() {
        return travelExpenses;
    }

    public void setTravelExpenses(double travelExpenses) {
        this.travelExpenses = travelExpenses;
    }

    public double getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(double travelDistance) {
        this.travelDistance = travelDistance;
    }

    public String getTravelStart() {
        return travelStart;
    }

    public void setTravelStart(String travelStart) {
        this.travelStart = travelStart;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "EmployeePlan{" +
                "id=" + id +
                ", employee=" + employee +
                ", standPlan=" + standPlan +
                ", startWorkingTime=" + startWorkingTime +
                ", endWorkingTime=" + endWorkingTime +
                ", pauseTime=" + pauseTime +
                ", travelExpenses=" + travelExpenses +
                ", travelDistance=" + travelDistance +
                ", travelStart='" + travelStart + '\'' +
                ", comment='" + comment + '\'' +
                ", bonus=" + bonus +
                '}';
    }

    public String getStringWithAll() {
        return
                id + " " +
                        employee + " " +
                        standPlan + " " +
                        startWorkingTime + " " +
                        endWorkingTime + " " +
                        pauseTime + " " +
                        travelExpenses + " " +
                        travelDistance + " " +
                        travelStart + " " +
                        comment + " " +
                        bonus;
    }
}

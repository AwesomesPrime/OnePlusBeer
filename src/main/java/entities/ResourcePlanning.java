package entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Ulokal on 29.06.2018.
 * Resourceplanungs Entität mit Fremdschlüssel auf Employee, Event und Stand
 */
@Entity
@Table(name = "ResourcePlanning")
public class ResourcePlanning {

    //-------------------------------------------------------------------------
    //  Vars
    //-------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_employee")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "fk_event")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "fk_stand")
    private Stand stand;

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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
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

    public ResourcePlanning(){}

    public ResourcePlanning(Employee employee, Event event, Stand stand, Date startWorkingTime, Date endWorkingTime, long pauseTime, double travelExpenses) {
        this.employee = employee;
        this.event = event;
        this.stand = stand;
        this.startWorkingTime = startWorkingTime;
        this.endWorkingTime = endWorkingTime;
        this.pauseTime = pauseTime;
        this.travelExpenses = travelExpenses;
    }



    @Override
    public String toString() {
        return "ResourcePlanning{" +
                "id=" + id +
                ", employee=" + employee +
                ", event=" + event +
                ", stand=" + stand +
                ", startWorkingTime=" + startWorkingTime +
                ", endWorkingTime=" + endWorkingTime +
                ", pauseTime=" + pauseTime +
                ", travelExpenses=" + travelExpenses +
                '}';
    }


}

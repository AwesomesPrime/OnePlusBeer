package entities;


import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

/**
 * Entität für StandPlan
 */
@Entity
@Table(name="StandPlan")
public class StandPlan {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name ="event")
    private Event event;

    @ManyToOne
    @JoinColumn(name ="stand")
    private Stand stand;

    @Column(name = "street")
    private String street;

    @Column(name = "plz")
    private String plz;

    @Column(name = "city")
    private String city;

    @Column(name = "openingTime")
    private Date openingTime;

    @Column (name="closingTime")
    private Date closingTime;

    public StandPlan() {
    }

    public StandPlan(Stand stand, Event event, String street, String plz, String city, Date openingTime, Date closingTime) {
        this.stand = stand;
        this.event = event;
        this.street = street;
        this.plz = plz;
        this.city = city;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public String getName(){
        return event.getName() + " - " + stand.getName();
    }

    public String getAddress(){
        return street + ", " + plz + " " + city;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getStringWithAll() {
        return  id + " " +
                event + " " +
                stand + " " +
                street + " " +
                plz + " " +
                city + " " +
                openingTime + " " +
                closingTime;
    }
}

package entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Event")
public class Event {

    //-------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "startDate")
    private Date start;

    @Column(name = "endDate")
    private Date end;

    @Column(name = "street")
    private String street;

    @Column(name = "houseNumber")
    private int houseNumber;

    @Column(name = "plz")
    private String plz;

    @Column(name = "city")
    private String city;


    public Event() {
    }

    public Event(String name, Date start, Date end, String street, int houseNumber, String plz, String city) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.street = street;
        this.houseNumber = houseNumber;
        this.plz = plz;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
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

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", plz='" + plz + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

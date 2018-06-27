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

    @Column(name = "address")
    private String address;


    public Event() {
    }

    public Event(String name, Date start, Date end, String address) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", address='" + address + '\'' +
                '}';
    }
}

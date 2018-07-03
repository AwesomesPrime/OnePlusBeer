package entities;


import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name="Stand")
public class Stand {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "street")
    private String street;

    @Column(name = "zip")
    private String zip;

    @Column(name = "city")
    private String city;

    @Column(name = "openingtime")
    private LocalTime openingTimes;

    @Column (name="closingtime")
    private LocalTime closingTime;

    @ManyToOne
    @JoinColumn(name ="fk_standDescription")
    private StandDescription standDescription;

    public Stand() {
    }

    public Stand(String street, String zip, String city, LocalTime openingTimes, LocalTime closingTime, StandDescription standDescription) {
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.openingTimes = openingTimes;
        this.closingTime = closingTime;
        this.standDescription = standDescription;
    }

    public LocalTime getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(LocalTime openingTimes) {this.openingTimes = openingTimes;}

    public LocalTime getClosingTime() {return closingTime;}

    public void setClosingTime(LocalTime closingTime) {this.closingTime = closingTime;}

    public int getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    @Override
    public String toString() {
        return "Stand{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                ", openingTimes=" + openingTimes +
                ", closingTime=" + closingTime +
                '}';
    }

    public StandDescription getStandDescription() {
        return standDescription;
    }

    public void setStandDescription(StandDescription standDescription) {
        this.standDescription = standDescription;
    }
}

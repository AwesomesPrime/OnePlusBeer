package entities;


import javax.persistence.*;

@Entity
@Table(name="Stand")
public class Stand {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "street")
    private String street;

    @Column(name = "zip")
    private String zip;

    @Column(name = "city")
    private String city;

    @Column(name = "openingtime")
    private String openingTimes;

    @Column(name = "note")
    private String note;

    public Stand() {
    }

    public Stand(String type, String street, String zip, String city, String openingTimes, String note) {
        this.type = type;
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.openingTimes = openingTimes;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(String openingTimes) {
        this.openingTimes = openingTimes;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Stand{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", street='" + street + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                ", openingTimes='" + openingTimes + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}

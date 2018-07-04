package entities;

import javax.persistence.*;

/**
 * Entität für Standbeschreibung
 */
@Entity
@Table(name="Stand_description")

public class StandDescription {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    @Column(name="comment")
    private String Comment;

    public StandDescription() {
    }

    public StandDescription(String name, String type, String comment) {
        this.name = name;
        this.type = type;
        Comment = comment;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    @Override
    public String toString() {
        return "StandDescription{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", Comment='" + Comment + '\'' +
                '}';
    }
}

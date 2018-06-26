import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Table(name="UserPermission")
public class UserPermission
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


    //-------------------------------------------------------------------------
    //  Constructor(s)
    //-------------------------------------------------------------------------
    public UserPermission() {

    }

    public UserPermission(String description) {
        this.description = description;
    }

    public UserPermission(int id, String description) {
        this.id = id;
        this.description = description;
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


    //-------------------------------------------------------------------------
    //  toString()
    //-------------------------------------------------------------------------
    @Override
    public String toString() {
        return "UserPermission{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}

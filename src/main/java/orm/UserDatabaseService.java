package orm;

import entities.Employee;
import entities.User;
import entities.UserPermission;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;

/**
 * Created by Ulokal on 26.06.2018.
 */
public class UserDatabaseService extends GenericDatabaseService<User> {

    public UserDatabaseService(){

    }

    @SuppressWarnings("unchecked")
    public User getUserByEmail(String email){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user;

        try {
            ArrayList<Employee> employee = (ArrayList<Employee>) session.createCriteria(Employee.class)
                    .add(Restrictions.like("mailAddress", email))
                    .list();
            int employeeId  = employee.get(0).getId();

            ArrayList<User> userWithEmail = (ArrayList<User>) session.createCriteria(User.class)
                    .createAlias("employee","e")
                    .add(Restrictions.eq("e.id", employeeId))
                    .list();

            user =  userWithEmail.get(0);
        }
        catch (Exception e){
            user = null;
        }
        finally {
            transaction.commit();
            session.close();
        }
        return user;

    }
}

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
        EmployeeDatabaseService employeeService = new EmployeeDatabaseService();
        UserPermissionDatabaseService userPermissionService = new UserPermissionDatabaseService();

        ArrayList<User> userpermissions = this.getAll(User.class);
        if(userpermissions.size() == 0){
            entities.User user = new entities.User( employeeService.get(Employee.class, 1), userPermissionService.get(UserPermission.class, 2), "pwtest");
            this.save(user);
        }
    }

    @SuppressWarnings("unchecked")
    public User getUserByEmail(String email){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            ArrayList<Employee> employee = (ArrayList<Employee>) session.createCriteria(Employee.class)
                    .add(Restrictions.like("mailAddress", email))
                    .list();
            int employeeId  = employee.get(0).getId();

            ArrayList<User> user = (ArrayList<User>) session.createCriteria(User.class)
                    .createAlias("employee","e")
                    .add(Restrictions.eq("e.id", employeeId))
                    .list();

            transaction.commit();
            session.close();

            return user.get(0);
        }
        catch (Exception e){
            transaction.commit();
            session.close();

            return null;
        }

    }
}

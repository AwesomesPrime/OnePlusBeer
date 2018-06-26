import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;

/**
 * Created by Stefan on 25.06.2018.
 */
public class GenericDatabaseService<T> implements IGenericDatabaseService<T> {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public <T> T get(Class<T> type,  int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Object result = session.get(type, id);
        transaction.commit();
        session.close();
        return (T) result;
        //return sessionFactory.getCurrentSession().get(type,id);
    }

    public <T> ArrayList<T> getAll(Class<T> type) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(type);
        transaction.commit();

        ArrayList<T> resultList = (ArrayList<T>) criteria.list();
        session.close();
        return resultList;
    }

    public <T> void save(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(entity);
        transaction.commit();
        session.close();
    }

    public <T> void delete(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }
}

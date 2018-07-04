package orm;

import interfaces.IGenericDatabaseService;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;

/**
 * Created by Stefan on 25.06.2018.
 */
public abstract class GenericDatabaseService<T> implements IGenericDatabaseService<T> {

    protected SessionFactory sessionFactory = SessionFactoryService.getSessionFactory();

    /**
     * @param type Klasse der zu findenden Entität
     * @param id Id der zu findenden Entiät
     * @param <T> Typparameter
     * @return Gibt die Entität mit der übereinstimmenden Id zurück
     */
    public <T> T get(Class<T> type,  int id) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Object result = null;

        try {
            result = session.get(type, id);
            transaction.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        finally {
            session.close();
        }
        return (T) result;
    }

    /**
     * @param type Klasse der Entität
     * @param <T> Typparameter
     * @return ArrayList mit allen Entitäten der übergebenen Klasse
     */
    public <T> ArrayList<T> getAll(Class<T> type) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<T> resultList;

        try {
            Criteria criteria = session.createCriteria(type);
            resultList = (ArrayList<T>) criteria.list();
            transaction.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            resultList = null;
        }
        finally {
            session.close();
        }
        return resultList;
    }

    /**
     * @param entity Zu speicherende Entität
     * @param <T> Typparameter
     */
    public <T> void save(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.saveOrUpdate(entity);
            transaction.commit();

        }
        catch(Exception e) {
            transaction.rollback();
        }
        finally {
            session.close();
        }
    }

    /**
     * @param entity Zu löschende Entität
     * @param <T> Typparameter
     */
    public <T> void delete(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(entity);
            transaction.commit();
        }
        catch (Exception e){
            transaction.rollback();
        }
        finally {
            session.close();
        }
    }
}

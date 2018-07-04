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

    /*public <T> ArrayList<T> search (Class<T> type,ArrayList<SearchParam> params) {
        SessionFactoryService session = sessionFactory.openSession();

        Criteria cr = session.createCriteria(type);
        for (int i = 0; i < params.size(); i++) {
            if(params.get(i).getOperation().equals("like")){
                cr.add(Restrictions.like(params.get(i).getColumn(), params.get(i).getValue()));
            }
        }

        return (ArrayList<T>) cr.list();
    }*/
}

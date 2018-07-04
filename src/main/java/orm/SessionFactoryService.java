package orm;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryService {

    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public SessionFactoryService(){

    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

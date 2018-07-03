package orm;

import entities.ResourcePlanning;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by Ulokal on 29.06.2018.
 */
public class ResourcePlanningDatabaseService extends GenericDatabaseService<ResourcePlanning> {

    public ArrayList<ResourcePlanning> search(String term) {
        ArrayList<ResourcePlanning> allEvents = this.getAll(ResourcePlanning.class);
        ArrayList<ResourcePlanning> resultEvents = allEvents.stream().filter(resourcePlanning -> Integer.toString(resourcePlanning.getId()).contains((term)) ||
                resourcePlanning.getStartWorkingTime().toString().contains(term) ||
                resourcePlanning.getEndWorkingTime().toString().contains(term) ||
                Long.toString(resourcePlanning.getPauseTime()).contains(term) ||
                Double.toString(resourcePlanning.getTravelExpenses()).contains(term)).collect(Collectors.toCollection(ArrayList::new));

        return resultEvents;
    }

    public ArrayList<ResourcePlanning> filterWorkingTimeByDate(Date startDate, Date endDate){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<ResourcePlanning> resultResourcePlanning;

        try{
            resultResourcePlanning = (ArrayList<ResourcePlanning>) session.createCriteria(ResourcePlanning.class)
                    .add(Restrictions.between("startWorkingTime",startDate,endDate))
                    .list();
        } catch (Exception e){
            e.printStackTrace();
            resultResourcePlanning = null;
        }
        finally {
            transaction.commit();
            session.close();
        }

        return resultResourcePlanning;
    }
}

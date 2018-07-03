package orm;

import entities.ResourcePlanning;

import java.util.ArrayList;
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
}

package orm;

import entities.Stand;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Ulokal on 29.06.2018.
 */
public class StandDatabaseService extends GenericDatabaseService<Stand> {

    /**
     * @param term Der Suchbegriff mit dem alle Attribute der Stände durchsucht werden
     * @return Gibt eine ArrayList mit allen übereinstimmenden Ständen zurück
     */
    public ArrayList<Stand> search (String term) {
        ArrayList<Stand> allEvents = this.getAll(Stand.class);
        ArrayList<Stand> resultEvents = allEvents.stream().filter(stand -> Integer.toString(stand.getId()).contains(term) ||
                                                                  stand.getStreet().contains(term) ||
                                                                  stand.getZip().contains(term) ||
                                                                  stand.getCity().contains(term) ||
                                                                  stand.getOpeningTimes().toString().contains(term) ||
                                                                  stand.getClosingTime().toString().contains(term)).collect(Collectors.toCollection(ArrayList::new));

        return resultEvents;
    }
}

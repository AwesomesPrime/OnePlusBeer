package orm;

import entities.Stand;

import java.util.ArrayList;

/**
 * Created by Ulokal on 29.06.2018.
 */
public class StandDatabaseService extends GenericDatabaseService<Stand> {

    /**
     * @param term Der Suchbegriff mit dem alle Attribute der Stände durchsucht werden
     * @return Gibt eine ArrayList mit allen übereinstimmenden Ständen zurück
     */
    public ArrayList<Stand> search (String term) {
        ArrayList<Stand> allStands = this.getAll(Stand.class);
        ArrayList<Stand> resultStands = (ArrayList<Stand>) allStands.stream().filter(stand -> Integer.toString(stand.getId()).contains(term) ||
                                                                  stand.getName().contains(term) ||
                                                                  stand.getType().contains(term) ||
                                                                  stand.getComment().contains(term));

        return resultStands;
    }
}

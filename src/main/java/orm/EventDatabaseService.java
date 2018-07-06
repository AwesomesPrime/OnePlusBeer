package orm;

import entities.Event;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Ulokal on 26.06.2018.
 */
public class EventDatabaseService extends GenericDatabaseService<Event> {

    public EventDatabaseService(){
    }

    /**
     * @param term Der Suchbegriff mit dem alle Attribute der Events durchsucht werden
     * @return Gibt eine ArrayList mit allen übereinstimmenden Events zurück.
     */
    public ArrayList<Event> search(String term){
        ArrayList<Event> allEvents = this.getAll(Event.class);
        ArrayList<Event> resultEvents = allEvents.stream().filter(event -> event.getStringWithAll().contains(term))
                .collect(Collectors.toCollection(ArrayList::new));
        return resultEvents;
    }


    public <T> ArrayList<Event> getAll() {
        return super.getAll(Event.class);
    }
}

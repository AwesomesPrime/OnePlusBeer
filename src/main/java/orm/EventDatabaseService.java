package orm;

import entities.Employee;
import entities.Event;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by Ulokal on 26.06.2018.
 */
public class EventDatabaseService extends GenericDatabaseService<Event> {

    public EventDatabaseService(){
    }

    public ArrayList<Event> search(String term){
        ArrayList<Event> allEvents = this.getAll(Event.class);
        ArrayList<Event> resultEvents = allEvents.stream().filter(event -> Integer.toString(event.getId()).contains(term) ||
                                                                  event.getName().contains(term) ||
                                                                  event.getStart().toString().contains(term) ||
                                                                  event.getEnd().toString().contains(term) ||
                                                                  event.getStartTime().toString().contains(term) ||
                                                                  event.getEndTime().toString().contains(term) ||
                                                                  event.getStreet().contains(term) ||
                                                                  Integer.toString(event.getHouseNumber()).contains(term) ||
                                                                  event.getPlz().contains(term) ||
                                                                  event.getCity().contains(term)).collect(Collectors.toCollection(ArrayList::new));
        return resultEvents;
    }
}

package orm;

import entities.Employee;
import entities.Event;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ulokal on 26.06.2018.
 */
public class EventDatabaseService extends GenericDatabaseService<Event> {

    public EventDatabaseService(){
        ArrayList<Event> events = this.getAll(Event.class);
        if(events.size() == 0){
            Calendar startDate = Calendar.getInstance();
            startDate.set(2018,11,1, 0, 0, 0);

            Calendar endDate = Calendar.getInstance();
            endDate.set(2018,11,23, 0, 0 ,0);

            Event event = new Event( "Weihnachtsmarkt 2018", startDate.getTime() , endDate.getTime(), "Straße", 12, "1256", "Düsseldorf");
            this.save(event);

            startDate.set(2017,11,1, 0, 0, 0);

            endDate.set(2017,11,23, 0, 0 ,0);

            event = new Event( "Weihnachtsmarkt 2017", startDate.getTime() , endDate.getTime(), "Hauptstraße", 19, "40597", "MalleSüd");
            System.out.println(endDate.getTime().toString());
            this.save(event);
        }
    }
}

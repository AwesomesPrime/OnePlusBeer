package controller;

import entities.Event;
import orm.EmployeeDatabaseService;
import orm.EventDatabaseService;

public class EventController {
    public void addEvent(Event eve) {

        EventDatabaseService eventDatabaseService = new EventDatabaseService();
        eventDatabaseService.save(eve);
    }

    public void deleteEvent(Event eve) {

        EventDatabaseService eventDatabaseService = new EventDatabaseService();
        eventDatabaseService.delete(eve);
    }
}

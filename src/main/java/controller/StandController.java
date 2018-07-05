package controller;


import entities.Stand;

import entities.StandDescription;
import orm.StandDatabaseService;

public class StandController {
    public void addStand(Stand stand){

        StandDatabaseService standDatabaseService = new StandDatabaseService();
        standDatabaseService.save(stand);
    }
    public void addStandDescription(StandDescription desc){

        StandDatabaseService standDatabaseService = new StandDatabaseService();
        standDatabaseService.save(desc);
    }

    public void deleteStand(Stand stand){

        StandDatabaseService standDatabaseService = new StandDatabaseService();
        standDatabaseService.delete(stand);
    }
}

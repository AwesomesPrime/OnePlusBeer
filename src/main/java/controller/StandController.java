package controller;


import entities.Stand;

import entities.StandDescription;
import orm.StandDatabaseService;

public class StandController {
    public void addStand(Stand stand, StandDescription desc){

        StandDatabaseService standDatabaseService = new StandDatabaseService();
        standDatabaseService.save(desc);
        standDatabaseService.save(stand);
    }
}

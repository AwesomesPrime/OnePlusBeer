package controller;


import entities.ResourcePlanning;
import orm.ResourcePlanningDatabaseService;

public class ResourcePlanningController {

    public void addResourcePlan(ResourcePlanning rp) {
        ResourcePlanningDatabaseService resourcePlanningDatabaseService = new ResourcePlanningDatabaseService();
        resourcePlanningDatabaseService.save(rp);
    }
}

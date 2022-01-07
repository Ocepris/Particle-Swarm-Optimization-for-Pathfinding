package simulation;

import entities.Entity;
import visuals.EntityRenderer;

import java.util.ArrayList;

public class EntityController {
    private ArrayList<Entity> entitiyList;
    private EntityRenderer entRenderer;

    public EntityController(EntityRenderer entRenderer){
        this.entRenderer = entRenderer;
    }
}

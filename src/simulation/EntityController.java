package simulation;

import entities.Entity;
import math.Vector2D;
import visuals.EntityRenderer;

import java.util.ArrayList;

public class EntityController {
    private ArrayList<Entity> entitiyList;
    private EntityRenderer entRenderer;
    public static Vector2D GLOBAL_BEST;

    public EntityController(EntityRenderer entRenderer){
        this.entRenderer = entRenderer;
    }
}

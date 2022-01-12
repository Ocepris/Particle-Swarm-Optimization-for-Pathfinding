package simulation;

import entities.Entity;
import math.Vector2D;
import visuals.EntityRenderer;

import java.util.ArrayList;

public class EntityController {
    private ArrayList<Entity> entityList;
    private EntityRenderer entRenderer;
    private EnvironmentController envController;
    private Map2D map2D;
    public static Vector2D GLOBAL_BEST;

    public EntityController(EntityRenderer entRenderer, EnvironmentController envController){
        this.entRenderer = entRenderer;
        this.envController = envController;
        map2D = envController.getMap();
    }

    public void createEntities(int amount, Vector2D startPos)
    {
        for(int i = 0; i < amount; i++)
            entityList.add(new Entity(startPos));
    }

    public void update()
    {
        double bestDistance = GLOBAL_BEST.getDistance(map2D.getGoal());

        for (Entity entity: entityList) {
            entity.move();

            double distanceToGoal = entity.getPosition().getDistance(map2D.getGoal());
            if(distanceToGoal < bestDistance)
            {
                bestDistance = distanceToGoal;
                GLOBAL_BEST = entity.getPosition();
            }

        }
    }
}

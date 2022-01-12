package simulation;

import entities.Entity;
import math.Vector2D;

import java.util.ArrayList;

public class EntityController {
    private ArrayList<Entity> entityList = new ArrayList<>();
    private EnvironmentController envController;
    private Map2D map2D;
    public static Vector2D GLOBAL_BEST;

    public EntityController(EnvironmentController envController){
        this.envController = envController;
        map2D = envController.getMap();
    }

    public void createEntities(int amount)
    {
        int offset = Simulation.BLOCKSIZE /2;
        double x = map2D.getStart().getX() * Simulation.BLOCKSIZE + offset;
        double y =  map2D.getStart().getY() * Simulation.BLOCKSIZE + offset;

        Vector2D startPos = new Vector2D(x,y);
        GLOBAL_BEST = startPos;

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

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<Entity> entityList) {
        this.entityList = entityList;
    }


}

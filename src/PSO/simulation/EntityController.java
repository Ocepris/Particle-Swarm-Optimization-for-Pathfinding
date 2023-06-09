package PSO.simulation;

import PSO.misc.PathOptimizer;
import PSO.entities.Entity;
import PSO.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class EntityController implements IEntityController {
    public static final float MIN_DISTANCE_TO_GOAL = 5.2f;
    private ArrayList<Entity> entityList = new ArrayList<>();
    private final Map2D map2D;
    private Entity entityFirstInGoal = null;
    private final ArrayList<Entity> entitiesInGoal = new ArrayList<>();

    public EntityController(EnvironmentController envController){
        map2D = envController.getMap();
    }

    public void createEntities(int amount)
    {
        entitiesInGoal.clear();
        double x = map2D.getStart().getX();
        double y =  map2D.getStart().getY();

        Vector2D startPos = new Vector2D(x,y);

        for(int i = 0; i < amount; i++)
            entityList.add(new Entity(startPos, map2D));
    }

    public void update()
    {
        double bestDistance = map2D.getGlobalBest().getDistance(map2D.getGoal());
        double worstDistance = Double.MIN_VALUE;

        for (Entity entity: entityList) {

            if(entity.getPosition().getDistance(map2D.getGoal()) < MIN_DISTANCE_TO_GOAL)
            {
                if(entity.inGoal)
                    continue;

                entity.inGoal = true;
                entitiesInGoal.add(entity);
                if(entityFirstInGoal == null)
                    entityFirstInGoal = entity;

                continue;

            }

            entity.move();


            double personalBestDistance = entity.getPersonalBest().getDistance(map2D.getGoal());

            if(personalBestDistance > worstDistance)
                worstDistance = personalBestDistance;


            double distanceToGoal = entity.getPosition().getDistance(map2D.getGoal());
            if(map2D.getValueOf((int)entity.getPosition().getX()/Simulation.BLOCK_SIZE,(int)entity.getPosition().getY()/Simulation.BLOCK_SIZE) == Map2D.BORDER)
                distanceToGoal = Double.MAX_VALUE;

            if(distanceToGoal < bestDistance)
            {
                bestDistance = distanceToGoal;
                map2D.setGlobalBestPosition(entity.getPosition().clone());
            }

            if(distanceToGoal < personalBestDistance)
                entity.setPersonalBest(entity.getPosition().clone());

        }

    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<Entity> entityList) {
        this.entityList = entityList;
        entitiesInGoal.clear();
    }


    @Override
    public List<Vector2D> getBestPath()
    {
        if(entityFirstInGoal == null)
            throw new NullPointerException();

        var bestPath = entityFirstInGoal.getPath();
        PathOptimizer pathOptimizer = new PathOptimizer();
        double minDist = Double.MAX_VALUE;
        for (Entity e: entitiesInGoal) {
            List<Vector2D> path = e.getPath();
            double dist = pathDistance(pathOptimizer.optimizePath(path));
            if(dist < minDist)
            {
                minDist = dist;
                bestPath = path;
            }
        }

        return (bestPath);
    }

    private double pathDistance(List<Vector2D> path)
    {
        double dist = 0;
        for(int i = 1; i < path.size(); i++)
        {
            Vector2D pos1 = path.get(i-1);
            Vector2D pos2 = path.get(i);
            dist += pos1.getDistance(pos2);
        }

        return dist;
    }

}

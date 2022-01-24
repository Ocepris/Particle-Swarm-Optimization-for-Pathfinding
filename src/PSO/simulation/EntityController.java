package PSO.simulation;

import IntersectionTester.PathOptimizer;
import PSO.entities.Entity;
import PSO.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class EntityController implements IEntityController {
    private ArrayList<Entity> entityList = new ArrayList<>();
    private EnvironmentController envController;
    private Map2D map2D;
    public static Vector2D GLOBAL_BEST;
    private Entity entityFirstInGoal = null;
    private ArrayList<Entity> entitiesInGoal = new ArrayList();

    public EntityController(EnvironmentController envController){
        this.envController = envController;
        map2D = envController.getMap();
    }

    public void createEntities(int amount)
    {
        entitiesInGoal.clear();
        double x = map2D.getStart().getX();
        double y =  map2D.getStart().getY();

        Vector2D startPos = new Vector2D(x,y);
        GLOBAL_BEST = startPos;

        for(int i = 0; i < amount; i++)
            entityList.add(new Entity(startPos));
    }
    public void createEntityGrid(){
        int offset = Simulation.BLOCKSIZE /2;
        int amountX = map2D.getSizeX() - 2;
        int amountY = map2D.getSizeY() - 2;

        GLOBAL_BEST = new Vector2D(0, 0);

        for(int x=1; x<=amountX; x++){
            for(int y=1; y<=amountY; y++){
                entityList.add(new Entity(new Vector2D(x*Simulation.BLOCKSIZE + offset, y*Simulation.BLOCKSIZE + offset)));
            }
        }
    }



    public void update()
    {
        double bestDistance = GLOBAL_BEST.getDistance(map2D.getGoal());
        double worstDistance = Double.MIN_VALUE;

        for (Entity entity: entityList) {

            if(entity.getPosition().getDistance(map2D.getGoal()) < 5.2f)
            {
                if(entity.inGoal)
                    continue;

                entity.inGoal = true;
                entitiesInGoal.add(entity);
                if(entityFirstInGoal == null)
                    entityFirstInGoal = entity;

                continue;

            }

            entity.move(map2D);


            double personalBestDistance = entity.getPersonalBest().getDistance(map2D.getGoal());

            if(personalBestDistance > worstDistance)
                worstDistance = personalBestDistance;


            double distanceToGoal = entity.getPosition().getDistance(map2D.getGoal());
            if(map2D.getValueOf((int)entity.getPosition().getX()/Simulation.BLOCKSIZE,(int)entity.getPosition().getY()/Simulation.BLOCKSIZE) == Map2D.BORDER)
                distanceToGoal = Double.MAX_VALUE;

            if(distanceToGoal < bestDistance)
            {
                bestDistance = distanceToGoal;
                GLOBAL_BEST = entity.getPosition().clone();
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

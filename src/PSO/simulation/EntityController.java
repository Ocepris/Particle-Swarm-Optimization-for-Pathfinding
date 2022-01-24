package PSO.simulation;

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

    public EntityController(EnvironmentController envController){
        this.envController = envController;
        map2D = envController.getMap();
    }

    public void createEntities(int amount)
    {
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
    }


    @Override
    public List<Vector2D> getBestPath()
    {
        var bestPath = entityFirstInGoal.getPath();
        return (bestPath);
    }


}

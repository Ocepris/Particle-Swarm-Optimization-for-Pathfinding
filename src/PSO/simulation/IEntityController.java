package PSO.simulation;

import PSO.entities.Entity;
import PSO.math.Vector2D;
import PSO.misc.updatable;

import java.util.List;

public interface IEntityController extends updatable {
    public List<Vector2D> getBestPath();
    public List<Entity> getEntityList();
    public void createEntities(int amount);
}

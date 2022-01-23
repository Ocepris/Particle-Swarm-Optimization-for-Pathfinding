package PSO.simulation;

import Evolution.updatable;
import PSO.math.Vector2D;

import java.util.List;

public interface IEntityController extends updatable {
    public List<Vector2D> getBestPath();
}

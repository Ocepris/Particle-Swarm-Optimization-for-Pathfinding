package PSO.simulation;

import PSO.math.Vector2D;
import PSO.misc.updatable;

import java.util.List;

public interface IEntityController extends updatable {
    public List<Vector2D> getBestPath();
}

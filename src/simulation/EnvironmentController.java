package simulation;

import visuals.Frame;

import java.util.ArrayList;

public class EnvironmentController {
    private Map2D map;

    public EnvironmentController(Map2D map2d){
        this.map = map2d;
    }

    public void loadMap(Map2D map){
        this.map = map;
    }

    public Map2D getMap() {
        return this.map;
    }
}

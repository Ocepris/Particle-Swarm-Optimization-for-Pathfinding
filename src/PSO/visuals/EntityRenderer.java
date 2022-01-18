package PSO.visuals;

import PSO.entities.Entity;
import PSO.simulation.EntityController;

import java.awt.*;
import java.util.ArrayList;

public class EntityRenderer implements Renderer{
    private Frame frame;
    private EntityController entityController;


    public EntityRenderer(Frame frame, EntityController entityController)
    {
        this.frame = frame;
        this.entityController = entityController;

    }

    @Override
    public void render(Graphics g) {
        ArrayList<Entity> entities = entityController.getEntityList();
        if(entities == null || entities.size() == 0)
            return;

        g.setColor(Color.BLUE);
        for (Entity entity:entities) {
            entity.draw(g);
        }


    }

}

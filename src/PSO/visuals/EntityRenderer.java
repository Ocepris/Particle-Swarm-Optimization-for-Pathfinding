package PSO.visuals;

import PSO.entities.Entity;
import PSO.simulation.IEntityController;

import java.awt.*;
import java.util.List;

public class EntityRenderer implements Renderer{
    private IEntityController entityController;


    public EntityRenderer(IEntityController entityController)
    {
        this.entityController = entityController;
    }

    @Override
    public void render(Graphics g) {
        List<Entity> entities = entityController.getEntityList();
        if(entities == null || entities.size() == 0)
            return;

        g.setColor(Color.BLUE);
        for (Entity entity:entities) {
            entity.draw(g);
        }


    }

}

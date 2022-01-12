package visuals;

import entities.Entity;
import math.Vector2D;
import simulation.EntityController;

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

        for (Entity entity:entities) {
            Vector2D pos = entity.getPosition();
            drawCenteredCircle(g, (int) pos.getX(), (int) pos.getY(), 10);
        }


    }

    public void drawCenteredCircle(Graphics g, int x, int y, int r) {
        x = x-(r/2);
        y = y-(r/2);
        g.fillOval(x,y,r,r);
    }

}

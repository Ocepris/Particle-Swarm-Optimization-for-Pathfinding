package entities;

import math.Vector2D;
import simulation.EntityController;

import java.awt.*;

public class Entity {

    private Vector2D position;
    private Vector2D direction;

    private double distance = 3;
    private Vector2D personalBest;


    public Entity(Vector2D startPos)
    {
        this.position = new Vector2D(startPos.getX(), startPos.getY());
        this.personalBest = startPos;
        this.direction = new Vector2D(Math.random(), Math.random()).mult(distance);

    }


    public void move(){

        //Calculate direction for Vectors to move into
        Vector2D dir = new Vector2D(direction.getX(),direction.getY()).normalize().mult(distance);
        Vector2D pBest = new Vector2D(personalBest.getX() - position.getX(), personalBest.getY() - position.getY()).normalize().mult(distance);
        Vector2D gBest = new Vector2D(EntityController.GLOBAL_BEST.getX() - position.getX(), EntityController.GLOBAL_BEST.getY() - position.getY()).normalize().mult(distance);

        //Random distance
        dir.mult(2 * Math.random());
        pBest.mult(2 * Math.random());
        gBest.mult(2 * Math.random());

        //Update Position and direction Vector
        double oldX = position.getX();
        double oldY = position.getY();

        this.position.add(dir).add(pBest).add(gBest);

        direction.setX(position.getX() - oldX);
        direction.setY(position.getY() - oldY);

    }

    public void draw(Graphics g){
        Vector2D pos = this.getPosition();
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        int r = 10;
        x = x-(r/2);
        y = y-(r/2);
        g.setColor(Color.BLUE);
        g.fillOval(x,y,r,r);
        g.setColor(Color.black);
        g.drawOval(x,y,r,r);
    }

    public Vector2D getPosition(){return position;}

    public void jump(){

    }

    public Vector2D getPersonalBest() {
        return personalBest;
    }

    public void setPersonalBest(Vector2D personalBest) {
        this.personalBest = personalBest;
    }
}

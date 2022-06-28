package PSO.entities;

import PSO.math.Vector2D;
import PSO.simulation.Map2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Entity {

    private final Vector2D position;
    private final Vector2D direction;
    private final Vector2D dir = new Vector2D();
    private final Vector2D pBest = new Vector2D();
    private final Vector2D gBest = new Vector2D();

    private final Map2D map;
    private int ticksSinceLastImprovement = 0;
    private final List<Vector2D> path = new ArrayList<>();
    public boolean inGoal = false;

    private final double distance = 4;
    private Vector2D personalBest;


    public Entity(Vector2D startPos, Map2D map)
    {
        this.position = new Vector2D(startPos.getX(), startPos.getY());
        this.personalBest = startPos;
        this.map = map;

        //Random start direction
        this.direction = new Vector2D(Math.random() -0.5f, Math.random() -0.5f).mult(distance);
    }

    boolean oneDirection = false;
    public void move(){

        //Calculate direction for Vectors to move into
        dir.setValues(direction.getX(), direction.getY()).normalize().mult(distance);
        pBest.setValues(personalBest.getX() - position.getX(), personalBest.getY() - position.getY()).normalize().mult(distance);
        gBest.setValues(map.getGlobalBest().getX() - position.getX(), map.getGlobalBest().getY() - position.getY()).normalize().mult(distance);

        //Randomize distance
        dir.mult(3 * Math.random());
        pBest.mult(0.5 * Math.random());
        gBest.mult(0.5 * Math.random());

        //Ignores the Swarm intelligence after x interations without improvement and starts a pure random search until it improves its personal best
        if(ticksSinceLastImprovement > 100)
            gBest.mult(0);
        if(ticksSinceLastImprovement > 300)
            pBest.mult(0);
        oneDirection = ticksSinceLastImprovement > 310;


        updatePosition();

        ticksSinceLastImprovement++;

    }

    private void updatePosition() {
        //Update Position and direction Vector
        double oldX = position.getX();
        double oldY = position.getY();

        this.position.add(dir).add(pBest).add(gBest);
        direction.setX(position.getX() - oldX);
        direction.setY(position.getY() - oldY);

        if(!isPositionValid())
        {
            resetPosition(oldX, oldY);
            return;
        }

        if(!oneDirection)
            path.add(position.clone());
    }

    private void resetPosition(double oldX, double oldY) {
        this.position.setX(oldX);
        this.position.setY(oldY);
        this.direction.setValues(Math.random() -0.5f, Math.random() -0.5f).mult(distance);
        if(oneDirection)
            path.add(position.clone());
    }

    private boolean isPositionValid()
    {
        return !(map.getBlockAtCoordinates(position.getX(),position.getY()) == Map2D.BORDER);
    }

    
    public void draw(Graphics g){
        Vector2D pos = this.getPosition();
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        int radius = 10;
        drawCircle(g, x, y, radius);

    }
    
    private void drawCircle(Graphics g, int x, int y, int radius)
    {
        x = x-(radius/2);
        y = y-(radius/2);
        
        g.setColor(Color.BLUE);
        g.fillOval(x,y,radius,radius);
        g.setColor(Color.black);
        g.drawOval(x,y,radius,radius);
    }

    public Vector2D getPosition(){return position;}

    public Vector2D getPersonalBest() {
        return personalBest;
    }

    public void setPersonalBest(Vector2D personalBest) {
        this.personalBest = personalBest;
        ticksSinceLastImprovement = 0;
    }

    public List<Vector2D> getPath(){return path;};
}

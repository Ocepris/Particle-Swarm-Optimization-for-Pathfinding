package Evolution;

import PSO.math.Vector2D;
import PSO.simulation.EnvironmentController;
import PSO.simulation.Map2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Dot implements updatable
{

	Vektor pos;
	Vektor vel; // Geschwindigkeit
	Vektor acc; // Beschleunigung
	Boolean allowedToTouchBorder = true;
	EnvironmentController environmentController;
	boolean living = true;
	boolean reachedGoal = false;
	Brain brain;


	float fitness;

	public Dot(EnvironmentController environmentController)
	{
		this.environmentController = environmentController;

		brain = new Brain(600);
		Vector2D start = environmentController.getMap().getStart();
		pos = new Vektor((float) start.getX(),(float) start.getY());
		vel = new Vektor(0, 0);
		acc = new Vektor(0, 0);
	}


	public List<Vector2D> calculatePath()
	{
		ArrayList<Vector2D> path = new ArrayList<>();
		Vector2D start = environmentController.getMap().getStart();
		pos = new Vektor((float) start.getX(),(float) start.getY());
		vel = new Vektor(0, 0);
		acc = new Vektor(0, 0);
		brain.step = 0;
		reachedGoal =false;
		living = true;

		float oldX = pos.getX();
		float oldY = pos.getY();

		while (!reachedGoal)
		{
			update();
//			if(pos.getX() != oldX || pos.getY() != oldY)
//			{
				path.add(new Vector2D(pos.getX(), pos.getY()));
				oldX = pos.getX();
				oldY = pos.getY();

//			}

		}

		return path;

	}

	@Override
	public void update()
	{
		float oldX = pos.getX();
		float oldY = pos.getY();

		if(brain.step > Population.minSteps + 10)
			living = false;

		if(living && !reachedGoal)
		{
			if(brain.directions.length > brain.step)
			{
				acc = brain.directions[brain.step];
				brain.step++;

			}
			else
				living = false;


			vel.add(acc);
			vel.limit(5);
			pos.add(vel);

		}


		Map2D map = environmentController.getMap();
		int blockSize = environmentController.getBlockSize();
		int blockValue = map.getValueOf((int) pos.getX() / blockSize,(int) pos.getY() / blockSize);
		if(blockValue == Map2D.BORDER)
		{
			if(allowedToTouchBorder)
			{
				pos.setX(oldX);
				pos.setY(oldY);
			}
			else
				living = false;

		}
		else if(blockValue == Map2D.GOAL)
			reachedGoal = true;


	}

	public void calcFitness()
	{
		float distanceToGoal = pos.getDistance(Game.goal);
		if(reachedGoal)
		{
			fitness = (float) (1.3+  600 - brain.step);
		}
		else
			fitness = (float) (1f / (distanceToGoal * distanceToGoal));
	}

	public double absoluteDistanceToGoal()
	{
		List<Vector2D> path = calculatePath();
		double dist = 0;
		for(int i = 1; i < path.size(); i++)
		{
			Vector2D pos1 = path.get(i - 1);
			Vector2D pos2 = path.get(i);
			dist += pos1.getDistance(pos2);
		}
		return dist;
	}

	public void draw(Graphics g)
	{

		g.setColor(brain.color);
//		if(Population.img != null)
//			g.drawImage(Population.img,(int)pos.getX() -20,(int) pos.getY()-20,40,40,null);

		g.fillOval((int) pos.getX(), (int) pos.getY(), 5, 5);
		
	}

	public Dot createBaby()
	{
		Random rnd = new Random();
		Dot baby = new Dot(environmentController);
		baby.brain = brain.clone();
		
		baby.brain.color = new Color(clip(rnd.nextInt(4)+brain.color.getRed()-2),clip( rnd.nextInt(4)+brain.color.getGreen()-2),clip( rnd.nextInt(4)+brain.color.getBlue()-2));
		return baby;
	}
	
	public int clip(int v)
	{
		if(v<0)
		{
			v+=256;
		}
		else
		v %=256;
		return v;
	}
	
	

	@Override
	public String toString()
	{
		return ("DOT " + pos);
	}
}

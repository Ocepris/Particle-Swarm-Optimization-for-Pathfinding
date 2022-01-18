package Evolution;

import PSO.simulation.EnvironmentController;
import PSO.simulation.Simulation;
import PSO.visuals.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements updatable, Renderer, KeyListener
{
	
	static Vektor goal;
	Population population;
	boolean fastforward = false;
	public Game(EnvironmentController envController)
	{
		goal = new Vektor((float) envController.getMap().getGoal().getX(),(float) envController.getMap().getGoal().getY());
		population = new Population(250, envController);
		
	}


	@Override
	public void update()
	{
		population.update();

		if(population.allDotsDead())
		{
			population.calcFitness();
			population.darwin();

		}

	}

	public void draw(Graphics g)
	{
		population.draw(g);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub
		if(e.getKeyChar() =='p')
		{
			Simulation.maxLoopTime = 1000/60;
			Simulation.fastforward = false;
			Main.fastforward = false;
		}
		else if(e.getKeyChar() =='f')
		{
			Simulation.maxLoopTime = 1000/500000;
			Simulation.fastforward = true;
			Main.fastforward = true;
		}
			
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		draw(g);
	}
}
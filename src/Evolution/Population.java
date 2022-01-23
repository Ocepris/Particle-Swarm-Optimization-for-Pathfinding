package Evolution;

import PSO.simulation.EnvironmentController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Population implements updatable
{
	Dot[] dots;
	int gen = 0;
	float maxFit=0;
	static float minSteps=600;
	boolean fastforward = false;
	static BufferedImage img;
	private Dot bestDot = null;
	private Random rnd;
	public Population(int size, EnvironmentController environmentController)
	{
		try {
			if(Population.img == null)
				Population.img = ImageIO.read(new File("./res/meatboy.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		dots = new Dot[size];
		for(int i = 0; i < size; i++)
		{
			dots[i] = new Dot(environmentController);
		}

	}

	@Override
	public void update()
	{

		Arrays.stream(dots).parallel().forEach(Dot::update);


	}
	
	
	public void draw(Graphics g)
	{
		if(!fastforward)
		{
			for(Dot d : dots)
				d.draw(g);
			
			g.drawString("Gen: "+gen+"  Fit: "+maxFit+"  Steps: "+minSteps, 40, 40);
			
		}
	}

	public void calcFitness()
	{
		bestDot = dots[0];

		for(Dot d : dots)
		{
			d.calcFitness();
			if(d.fitness > bestDot.fitness)
				bestDot = d;
		}
	}

	boolean allDotsDead()
	{
		for(Dot d : dots)
		{
			if(d.living && !d.reachedGoal)
				return false;
		}
		return true;
	}

	public void darwin()
	{
		Dot[] newDots = new Dot[dots.length];
		float fs = calcFitnessSum();
		Dot best=dots[0];
		for(int i=0;i<newDots.length;i++)
		{
			Dot parent = SelectParent();
			newDots[i] = parent.createBaby();
			if(dots[i].fitness>best.fitness)
				best = dots[i];
		}
		dots = newDots;
		mutatePop();
		dots[0] = best;
		maxFit = best.fitness;
		minSteps = best.brain.step;
		gen++;
		if(gen%1000==0)
			System.out.println(gen);
		
		
	}

	public void mutatePop()
	{
		Random rnd  = new Random();
		for(int i = 0; i < dots.length; i++)
		{
			
			dots[i].brain.mutate();
		}
	}

	public float calcFitnessSum()
	{
		float fitnessSum = 0;
		for(int i = 0; i < dots.length; i++)
		{
			fitnessSum += dots[i].fitness;
		}
		return fitnessSum;
	}

	public Dot SelectParent()
	{
		int i1 = ThreadLocalRandom.current().nextInt(dots.length);
		int i2 = ThreadLocalRandom.current().nextInt(dots.length);

		Dot d1 = dots[i1];
		Dot d2 = dots[i2];

		if(d1.fitness > d2.fitness)
			return d1;
		else
			return d2;

	}

	public Dot getBestDot(){return bestDot;}

}

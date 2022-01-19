package Evolution;

import PSO.simulation.EnvironmentController;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Population implements updatable
{
	Dot[] dots;
	int gen = 0;
	float maxFit=0;
	static float minSteps=600;
	boolean fastforward = false;
	static BufferedImage img;
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
		for(Dot d : dots)
		{
			d.calcFitness();
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
			Dot parent = SelectParent(fs);
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
			
			dots[i].brain.mutate(dots[(int)(rnd.nextFloat()*dots.length)]);
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

	public Dot SelectParent(float FitnessSum)
	{
		Random rnd = new Random();
		float rand = rnd.nextFloat() * FitnessSum;
		float runningSum = 0;
		for(Dot d:dots)
		{
			runningSum += d.fitness;
			if(runningSum > rand)
			{
				return d;
			}
		}

		// Should never Happen but why not
		System.out.println("DANGER!!");
		return new Dot(null);

	}

}

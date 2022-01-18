package Evolution;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame implements Runnable
{

	public static int HEIGHT = 1000;
	public static int WIDTH = 1000;
	static boolean fastforward = false;

	public static int FPS = 60;
	public static long maxLoopTime = 1000 / FPS;

	private Game game;

	public Main()
	{
		setTitle("DotRoad");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.lightGray);
//		game = new Game();
		this.addKeyListener(game);
		add(game);
		setVisible(true);

	}

	public static void main(String[] arg)
	{
		Main main = new Main();
		new Thread(main).start();
	}

	public boolean running = true;

	@Override
	public void run()
	{
		long timestamp;
		long oldTimestamp;
		while (running)
		{
			oldTimestamp = System.currentTimeMillis();
			update();
			timestamp = System.currentTimeMillis();
			if(timestamp - oldTimestamp > maxLoopTime)
			{
				// System.out.println("Wir sind zu sp�t!");
				continue;
			}
			render();
			timestamp = System.currentTimeMillis();
			// System.out.println(maxLoopTime + " : " + (timestamp -
			// oldTimestamp));
			if(!fastforward)
			if(timestamp - oldTimestamp <= maxLoopTime)
			{
				try
				{
					Thread.sleep(maxLoopTime - (timestamp - oldTimestamp));
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	void update()
	{
		game.update();
	}

	void render()
	{
		if(!fastforward)
		{

			game.draw(null);
			this.repaint();
	
		}
	
	}
}
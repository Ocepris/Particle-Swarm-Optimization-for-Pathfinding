package PSO.simulation;

import Evolution.Game;
import Evolution.updatable;
import PSO.visuals.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Simulation implements KeyListener, Runnable
{

	public final static int BLOCKSIZE = 50;
	public static int FPS = 60;
	public static long maxLoopTime = 1000 / FPS;
	public boolean running = true;
	public static boolean fastforward = false;

	private updatable entityController;
	private EnvironmentController envController;
	private RenderController rc;
	private StatsContainer sc = new StatsContainer("0.09", "");
	private int iteration = 0;
	Frame frame;
	Thread t;


	public static void main(String[] args)
	{
		new Simulation();

	}

	public Simulation()
	{
		rc = new RenderController();
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
		logger.info("Booting..");

		initPSO();

		frame.getJFrame().addKeyListener(this);

		t = new Thread(this);
		t.run();
	}

	public void initPSO()
	{

		initMap();

		EntityController ec = new EntityController(envController);
		entityController = ec;
		EntityRenderer entityRenderer = new EntityRenderer(null, ec);
		ec.createEntities(300);

		StatsRenderer sr = new StatsRenderer(sc, (envController.getMap().getSizeX()*BLOCKSIZE) + 20);
		rc.addRenderer(sr);
		rc.addRenderer(entityRenderer);

		frame.setRenderController(rc);
		rc.setFrame(frame);
		rc.triggerRepaint();


	}

	public void initMap()
	{
		String mapPath = "res/test_map.csv";
		sc.setValue("map_name", mapPath);
		envController = new EnvironmentController(BLOCKSIZE);
		envController.loadMapFromCSVFile(mapPath);
		EnvironmentRenderer envRenderer = new EnvironmentRenderer(envController);
		rc.addRenderer(envRenderer);

		if(frame == null)
			frame = envRenderer.createMapMatchingFrame();


	}

	public void initEvo()
	{
		initMap();
		Game game = new Game(envController);
		entityController = game;
		frame.add(game);
		frame.getJFrame().addKeyListener(game);

		StatsRenderer sr = new StatsRenderer(sc, (envController.getMap().getSizeX()*BLOCKSIZE) + 20);
		EnvironmentController envController = new EnvironmentController(BLOCKSIZE);

		rc.addRenderer(sr);
		rc.addRenderer(game);
		rc.setFrame(frame);
		frame.setRenderController(rc);

	}

	public void clearSimulation()
	{
		EntityController.GLOBAL_BEST = null;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyChar() =='2')
		{
			running = false;
			clearSimulation();
			initEvo();
			running = true;
		}
		else if(e.getKeyChar() == '1')
		{
			running = false;
			clearSimulation();
			initPSO();
			running = true;
		}


	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void update()
	{
		iteration++;
		entityController.update();
		sc.setValue("iteration", String.valueOf(this.iteration));
	}

	public void render()
	{
		rc.triggerRepaint();

	}

	@Override
	public void run()
	{
		long timestamp;
		long oldTimestamp;
		while (true)
		{
			if(!running)
			{
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;

			}

			oldTimestamp = System.currentTimeMillis();
			update();
			timestamp = System.currentTimeMillis();
			if(timestamp - oldTimestamp > maxLoopTime)
				continue;

			if(!fastforward)
				render();
			timestamp = System.currentTimeMillis();

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
}

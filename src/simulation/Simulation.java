package simulation;

import visuals.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Simulation implements KeyListener, Runnable
{

	public final static int BLOCKSIZE = 50;
	private EntityController entityController;
	private RenderController rc;
	private StatsContainer sc = new StatsContainer("0.09", "");
	private int iteration = 0;
	Thread t;


	public static void main(String[] args)
	{
		new Simulation();

	}

	public Simulation()
	{
		init();

	}

	public void init()
	{
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
		logger.info("Booting..");

		String mapPath = "res/test_map.csv";

		rc = new RenderController();

		EnvironmentController envController = new EnvironmentController(BLOCKSIZE);
		envController.loadMapFromCSVFile(mapPath);

		EnvironmentRenderer envRenderer = new EnvironmentRenderer(envController);
		rc.addRenderer(envRenderer);

		entityController = new EntityController(envController);
		EntityRenderer entityRenderer = new EntityRenderer(null, entityController);
		entityController.createEntities(300);

		sc.setValue("map_name", mapPath);
		StatsRenderer sr = new StatsRenderer(sc, (envController.getMap().getSizeX()*BLOCKSIZE) + 20);
		rc.addRenderer(sr);
		rc.addRenderer(entityRenderer);

		Frame frame = envRenderer.createMapMatchingFrame();
		frame.getJFrame().addKeyListener(this);
		frame.setRenderController(rc);
		rc.setFrame(frame);
		rc.triggerRepaint();

		t = new Thread(this);
		t.run();

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyChar() =='u')
		{
			entityController.update();
			rc.triggerRepaint();
			this.iteration++;
			sc.setValue("iteration", String.valueOf(this.iteration));
		}


	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void run() {
		while (iteration < 300000)
		{
			iteration++;
			entityController.update();
			rc.triggerRepaint();

			sc.setValue("iteration", String.valueOf(this.iteration));
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}

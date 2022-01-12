package simulation;

import visuals.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Simulation implements KeyListener
{

	public final static int BLOCKSIZE = 20;
	private EntityController entityController;
	private RenderController rc;
	private StatsContainer sc = new StatsContainer("0.09", "");
	private int iteration = 0;

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
		entityController.createEntities(30);

		sc.setValue("map_name", mapPath);
		StatsRenderer sr = new StatsRenderer(sc, (envController.getMap().getSizeX()*BLOCKSIZE) + 20);
		rc.addRenderer(sr);
		rc.addRenderer(entityRenderer);

		Frame frame = envRenderer.createMapMatchingFrame();
		frame.getJFrame().addKeyListener(this);
		frame.setRenderController(rc);
		rc.setFrame(frame);
		rc.triggerRepaint();
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
}

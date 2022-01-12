package simulation;

import math.Vector2D;
import visuals.EnvironmentRenderer;
import visuals.Frame;
import visuals.RenderController;
import visuals.StatsRenderer;
import visuals.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulation
{

	public static void main(String[] args)
	{
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
		logger.info("Booting..");

		String mapPath = "res/test_map.csv";

		RenderController rc = new RenderController();

		EnvironmentController envController = new EnvironmentController();
		envController.loadMapFromCSVFile(mapPath);

		EnvironmentRenderer envRenderer = new EnvironmentRenderer(envController, 20);
		rc.addRenderer(envRenderer);

		EntityRenderer entityRenderer = new EntityRenderer(null);
		EntityController entityController = new EntityController(entityRenderer,envController);

		StatsContainer sc = new StatsContainer("0.09", mapPath);
		StatsRenderer sr = new StatsRenderer(sc);
		rc.addRenderer(sr);

		Frame frame = envRenderer.createMapMatchingFrame();
		frame.setRenderController(rc);
		rc.setFrame(frame);
		rc.triggerRepaint();
	}
}

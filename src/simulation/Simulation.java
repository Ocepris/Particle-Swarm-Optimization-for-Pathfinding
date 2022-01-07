package simulation;

import visuals.EnvironmentRenderer;
import visuals.Frame;
import visuals.RenderController;
import visuals.StatsRenderer;

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

		EnvironmentRenderer envRenderer = new EnvironmentRenderer(envController, 50);
		rc.addRenderer(envRenderer);

		StatsContainer sc = new StatsContainer("0.09", mapPath);
		StatsRenderer sr = new StatsRenderer(sc);
		rc.addRenderer(sr);

		Frame frame = envRenderer.createMapMatchingFrame();
		frame.setRenderController(rc);
		rc.setFrame(frame);
		rc.triggerRepaint();
	}
}

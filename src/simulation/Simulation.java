package simulation;

import visuals.EnvironmentRenderer;
import visuals.Frame;
import visuals.RenderController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulation
{

	public static void main(String[] args)
	{
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
		logger.info("Booting..");

		//Setup Map
		int[][] testValues = {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		};
		Map2D testMap = new Map2D(testValues);
		testMap.setValues(testValues);

		RenderController rc = new RenderController();

		EnvironmentController envController = new EnvironmentController(testMap);
		EnvironmentRenderer envRenderer = new EnvironmentRenderer(envController, 50);

		rc.addRenderer(envRenderer);

		Frame frame = envRenderer.createMapMatchingFrame();
		frame.setRenderController(rc);
		rc.setFrame(frame);
		rc.triggerRepaint();
	}
}

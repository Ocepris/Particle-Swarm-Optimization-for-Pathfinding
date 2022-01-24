package PSO.simulation;

import AStar.AStarGrid;
import AStar.AStarNode;
import AStar.NodeState;
import Evolution.Game;
import IntersectionTester.PathOptimizer;
import PSO.math.Vector2D;
import PSO.visuals.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.abs;


public class Simulation implements KeyListener, Runnable
{

	public final static int BLOCKSIZE = 50;
	public static int FPS = 60;
	public static long maxLoopTime = 1000 / FPS;
	public boolean running = true;
	public static boolean fastforward = false;

	private IEntityController entityController;
	private EnvironmentController envController;
	private EnvironmentRenderer envRenderer;
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
		String mapPath = "res/circle_map.csv";
		sc.setValue("map_name", mapPath);
		envController = new EnvironmentController(BLOCKSIZE);
		envController.loadMapFromCSVFile(mapPath);
		envRenderer = new EnvironmentRenderer(envController);
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
		else if(e.getKeyChar() == 'o')
		{
			List<Vector2D> bestPath = entityController.getBestPath();
			PathOptimizer pathOptimizer = new PathOptimizer();
			envRenderer.setPath(pathOptimizer.optimizePath(bestPath));

		}else if(e.getKeyChar() == 'u')
		{
			List<Vector2D> bestPath = entityController.getBestPath();
			envRenderer.setPath(bestPath);

		}
		else if(e.getKeyChar() == 'a')
		{
			Runnable run = () -> {

				Map2D map = envController.getMap();
				int width = map.getSizeX() * Simulation.BLOCKSIZE;
				int height = map.getSizeY()* Simulation.BLOCKSIZE;
				AStarGrid aStarGrid = new AStarGrid(width,height);
				for(int i = 0; i < width; i++)
					for(int j = 0; j < height; j++)
					{
						int val = map.getValueOf(i / Simulation.BLOCKSIZE,j / Simulation.BLOCKSIZE);
						if(val == Map2D.BORDER)
							aStarGrid.setNodeState(i,j, NodeState.NOT_WALKABLE);
					}

				int startX = (int) map.getStart().getX();
				int startY = (int) map.getStart().getY();
				int goalX = (int) map.getGoal().getX();
				int goalY = (int) map.getGoal().getY();

				List<AStarNode> nodes = aStarGrid.getPath(startX,startY,goalX,goalY);

				ArrayList<Vector2D> path = new ArrayList<>();
				for (AStarNode node : nodes) {
					path.add(new Vector2D(node.getX(), node.getY()));
				}

				envRenderer.setBestPath(path);


			};


			Thread thread = new Thread(run);
			thread.start();

		}

		if(e.getKeyChar() =='p')
		{
			Simulation.maxLoopTime = 1000/60;
			Simulation.fastforward = false;
		}
		else if(e.getKeyChar() =='f')
		{
			Simulation.maxLoopTime = 1000/500000;
			Simulation.fastforward = true;
		}

	}


	public List<Vector2D> optimizePath(List<Vector2D> path)
	{

		Vector2D v11 = new Vector2D(1,1);
		Vector2D v22 = new Vector2D(1,5);
		Vector2D v33 = new Vector2D(0,2);
		Vector2D v44 = new Vector2D(3,5);

		var t = intersection(v11,v22,v33,v44);

		for(int i = 0; i < path.size() -1; i++)
			for(int j = path.size() -1; j-1 > i+1; j--) {

				System.out.println(i +" "+j);
				Vector2D v1 = path.get(i);
				Vector2D v2 = path.get(i + 1);
				Vector2D v3 = path.get(j - 1);
				Vector2D v4 = path.get(j);


				Vector2D intersectionPoint = intersection(v1, v2, v3, v4);
				if (intersectionPoint != null) {
					ArrayList<Vector2D> newPath = new ArrayList<>();
					for (int x = 0; x <= i; x++) {
						newPath.add(path.get(x));
					}
					newPath.add(intersectionPoint);
					for (int x = j; x < path.size(); x++) {
						newPath.add(path.get(x));
					}

					return optimizePath(newPath);
				}
			}

		return path;
	}

	public Vector2D intersection(Vector2D A, Vector2D B, Vector2D C, Vector2D D)
	{
		double x1 = A.getX();
		double x2 = B.getX();
		double y1 = A.getY();
		double y2 = B.getY();

		double x3 = C.getX();
		double x4 = D.getX();
		double y3 = C.getY();
		double y4 = D.getY();



		// Line AB represented as a1x + b1y = c1
		double a1 = B.getY() - A.getY();
		double b1 = A.getX() - B.getX();
		double c1 = a1*(A.getY()) + b1*(A.getY());

		// Line CD represented as a2x + b2y = c2
		double a2 = D.getY() - C.getY();
		double b2 = C.getX() - D.getX();
		double c2 = a2*(C.getX())+ b2*(C.getY());

		double determinant = a1*b2 - a2*b1;

		if (determinant == 0)
		{
			// The lines are parallel. This is simplified
			// by returning a pair of FLT_MAX
			return null;
		}
		else
		{
			double x = (b2*c1 - b1*c2)/determinant;
			double y = (a1*c2 - a2*c1)/determinant;

			double slope = A.slope(B);
			var b = A.getY() - (slope * A.getX());

			double expectedY = slope * x +b;
			double diffY = abs(y - expectedY);
			if(diffY > 3f)
				return null;

			double expectedX = (y-b) / slope;
			double diffX = abs(x - expectedX);
			if(diffX > 3f)
				return null;


			return new Vector2D(x, y);
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

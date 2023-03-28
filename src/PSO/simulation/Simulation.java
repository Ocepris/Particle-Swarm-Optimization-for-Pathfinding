package PSO.simulation;

import AStar.AStarGrid;
import AStar.AStarNode;
import AStar.NodeState;
import PSO.math.Vector2D;
import PSO.misc.PathOptimizer;
import PSO.visuals.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Simulation implements KeyListener, Runnable {

    public final static int BLOCK_SIZE = 50;
    public static int FPS = 60;
    public static long maxLoopTime = 1000 / FPS;
    public static boolean fastForward = false;
    private final RenderController renderController;
    private final StatsContainer statsContainer = new StatsContainer("0.09", "");
    private final Logger logger;
    public boolean running = true;
    public boolean disableUpdates = true;
    private IEntityController entityController;
    private EnvironmentController envController;
    private EnvironmentRenderer envRenderer;
    private int iteration = 0;
    private Frame simulationFrame;
    private String mapPath = "res/maps/test_map.csv";
    private String mapName = "test_map";


    public Simulation() {
        renderController = new RenderController();
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.ALL);
        logger.info("Booting..");

        initPSO();

        simulationFrame.getJFrame().addKeyListener(this);

        Thread simulationThread = new Thread(this);
        simulationThread.start();
    }

    public static void main(String[] args) {
        new Simulation();
    }

    public void initPSO() {

        initMapFromMapPath(mapPath);

        entityController = new EntityController(envController);
        EntityRenderer entityRenderer = new EntityRenderer(entityController);
        entityController.createEntities(100);

        StatsRenderer statsRenderer = new StatsRenderer(statsContainer, (envController.getMap().getSizeX() * BLOCK_SIZE) + 20);
        renderController.addRenderer(statsRenderer);
        renderController.addRenderer(entityRenderer);

        simulationFrame.setRenderController(renderController);
        renderController.setFrame(simulationFrame);
        renderController.triggerRepaint();


    }


    private void initMapFromMapPath(String mapPath)
    {
        statsContainer.setValue("map_name", mapName);
        envController = new EnvironmentController(BLOCK_SIZE);
        envController.loadMapFromCSVFile(mapPath);
        envRenderer = new EnvironmentRenderer(envController);
        renderController.addRenderer(envRenderer);

        if (simulationFrame == null)
            simulationFrame = envRenderer.createMapMatchingFrame();


    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar())
        {
            case 'r' -> restartSimulation();
            case 'o' -> drawOptimizedPathToGoal();
            case 'u' -> drawPathToGoal();
            case 'a' -> drawAStarPathToGoal();
            case 'f' -> toggleSimulationSpeed();
            case 'l' -> loadMap();
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            this.disableUpdates = !disableUpdates;
        }

    }

    private void loadMap()
    {
        selectMap();
        renderController.clear();
        restartSimulation();
    }

    private void selectMap() {
        JFileChooser jfc = new JFileChooser(System.getProperty("user.dir")+"/res/maps");
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = jfc.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getPath());
            mapName = selectedFile.getName();
            mapPath = selectedFile.getAbsolutePath();
        }

    }

    private void toggleSimulationSpeed() {
        if (!fastForward)
            speedUpSimulation();
        else
            slowDownSimulation();
    }

    private void restartSimulation() {
        running = false;
        initPSO();
        running = true;
        disableUpdates = true;
    }

    private void slowDownSimulation() {
        Simulation.maxLoopTime = 1000 / 60;
        Simulation.fastForward = false;
    }

    private void speedUpSimulation() {
        Simulation.maxLoopTime = 1000 / 500000;
        Simulation.fastForward = true;
    }

    private void drawAStarPathToGoal() {

        logger.info("Started A*");
        Runnable run = () -> {
            Map2D map = envController.getMap();
            int width = map.getSizeX() * Simulation.BLOCK_SIZE;
            int height = map.getSizeY() * Simulation.BLOCK_SIZE;
            AStarGrid aStarGrid = new AStarGrid(width, height);
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++)
                {
                    int val = map.getValueOf(i / Simulation.BLOCK_SIZE, j / Simulation.BLOCK_SIZE);
                    if (val == Map2D.BORDER)
                        aStarGrid.setNodeState(i, j, NodeState.NOT_WALKABLE);
                }

            int startX = (int) map.getStart().getX();
            int startY = (int) map.getStart().getY();
            int goalX = (int) map.getGoal().getX();
            int goalY = (int) map.getGoal().getY();

            List<AStarNode> nodes = aStarGrid.getPath(startX, startY, goalX, goalY);

            ArrayList<Vector2D> path = new ArrayList<>();
            for (AStarNode node : nodes)
            {
                path.add(new Vector2D(node.getX(), node.getY()));
            }
            envRenderer.setBestPath(path);

            logger.info("A* finished");
        };

        Thread thread = new Thread(run);

        thread.start();
    }

    private void drawPathToGoal() {
        List<Vector2D> bestPath = entityController.getBestPath();
        envRenderer.setPath(bestPath);
    }

    private void drawOptimizedPathToGoal() {
        List<Vector2D> bestPath = entityController.getBestPath();
        PathOptimizer pathOptimizer = new PathOptimizer();
        envRenderer.setPath(pathOptimizer.optimizePath(bestPath));
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void update() {
        iteration++;
        entityController.update();
        statsContainer.setValue("iteration", String.valueOf(this.iteration));
    }

    public void render() {
        renderController.triggerRepaint();

    }

    @Override
    public void run() {
        long timestamp;
        long oldTimestamp;
        while (true)
        {
            if (!running)
            {
                try
                {
                    Thread.sleep(500);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                continue;

            }

            oldTimestamp = System.currentTimeMillis();
            if (!disableUpdates)
                update();
            timestamp = System.currentTimeMillis();
            if (timestamp - oldTimestamp > maxLoopTime)
                continue;

            if (!fastForward)
                render();
            timestamp = System.currentTimeMillis();

            if (!fastForward)
                if (timestamp - oldTimestamp <= maxLoopTime)
                {
                    try
                    {
                        Thread.sleep(maxLoopTime - (timestamp - oldTimestamp));
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
        }

    }
}

package PSO.visuals;

import PSO.math.Vector2D;
import PSO.simulation.EnvironmentController;
import PSO.simulation.Map2D;

import java.awt.*;
import java.util.List;
import java.util.logging.Logger;

public class EnvironmentRenderer implements Renderer {
    public static final int PATH_WIDTH = 5;
    public static final int STATS_CONTAINER_WIDTH = 270;
    public static final int BOTTOM_PADDING = 20;
    private final EnvironmentController envController;
    private final int blockSize;
    private List<Vector2D> path;
    private List<Vector2D> bestPath;
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public EnvironmentRenderer(EnvironmentController envController) {
        this.envController = envController;
        this.blockSize = envController.getBlockSize();
    }

    public Frame createMapMatchingFrame() {
        if (envController.getMap() != null)
        {
            int sizeX = envController.getMap().getSizeX() + 1;
            int sizeY = envController.getMap().getSizeY() + 1;

            return new Frame((sizeX * blockSize) + STATS_CONTAINER_WIDTH, (sizeY * blockSize) + BOTTOM_PADDING);
        }
        else
        {
            logger.warning("Can't create Frame without a valid map!");
            return null;
        }
    }

    @Override
    public void render(Graphics g) {
        if (envController.isMapLoaded())
            this.drawMap(g);

    }

    private void drawPath(Graphics g, List<Vector2D> path, Color color) {
        if (path == null)
            return;

        g.setColor(color);
        Graphics2D g2 = (Graphics2D) g;
        var oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(PATH_WIDTH));


        Vector2D startPos = null;
        for (Vector2D pos : path)
        {
            if (startPos == null)
            {
                startPos = pos;
                continue;
            }
            g.drawLine((int) startPos.getX(), (int) startPos.getY(), (int) pos.getX(), (int) pos.getY());
            startPos = pos;
        }

        g2.setStroke(oldStroke);
    }

    private void drawMap(Graphics g) {
        Map2D map = envController.getMap();
        for (int y = 0; y < map.getSizeY(); y++)
        {
            for (int x = 0; x < map.getSizeX(); x++)
            {
                int blockType = map.getValueOf(x, y);
                g.setColor(getBlockColor(blockType));

                g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
                g.setColor(Color.GRAY);
                g.drawRect(x * blockSize, y * blockSize, blockSize, blockSize);
            }
        }

        drawPath(g, bestPath, Color.blue);
        drawPath(g, path, Color.RED);
    }

    private Color getBlockColor(int blockType) {
        if (blockType == Map2D.BORDER)
        {
            return Color.BLACK;
        }
        else if (blockType == Map2D.GOAL)
        {
            return Color.GREEN;
        }
        else if (blockType == Map2D.START)
        {
            return Color.BLUE;
        }
        else
        {
            return Color.white;
        }
    }

    public void setPath(List<Vector2D> path) {
        this.path = path;
    }

    public void setBestPath(List<Vector2D> bestPath) {
        this.bestPath = bestPath;
    }
}

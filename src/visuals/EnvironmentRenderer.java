package visuals;

import simulation.EnvironmentController;
import simulation.Map2D;

import java.awt.*;
import java.time.Clock;
import java.util.logging.Logger;

public class EnvironmentRenderer implements Renderer{
    private EnvironmentController envController;
    private int blockSize;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public EnvironmentRenderer(){

    }

    public EnvironmentRenderer(EnvironmentController envController, int blockSize){
        this.envController = envController;
        this.blockSize = blockSize;
    }

    public Frame createMapMatchingFrame(){
        if(envController.getMap() != null){
            int sizeX = envController.getMap().getSizeX()+1;
            int sizeY = envController.getMap().getSizeY()+1;

            return new Frame(sizeX*blockSize, sizeY*blockSize);
        }else{
            logger.warning("Can't create Frame without a valid map!");
            return null;
        }
    }

    @Override
    public void render(Graphics g) {
        if(envController.isMapLoaded()) {
            this.drawMap(g);
        }
    }

    private void drawMap(Graphics g){
        Map2D map = envController.getMap();
        System.out.println("Map dimensions: " + map.getSizeX() + " x " + map.getSizeY());
        System.out.println("Test: " + map.getValueOf(2, 3));
        for(int row = 0; row < map.getSizeY(); row++){
            for(int col = 0; col < map.getSizeX(); col++){
                int blockType = map.getValueOf(row, col);
                System.out.print(blockType);
                //TODO blockTypes improvement
                if(blockType == 1){
                    g.setColor(Color.BLACK);
                }else if(blockType == 2){
                    g.setColor(Color.orange);
                }else if(blockType == 3){
                    g.setColor(Color.BLUE);
                }else{
                    g.setColor(Color.white);
                }

                g.fillRect(col*blockSize, row*blockSize, blockSize, blockSize);
                g.setColor(Color.GRAY);
                g.drawRect(col*blockSize, row*blockSize, blockSize, blockSize);
            }
            System.out.println();
        }
    }

    public void setEnvController(EnvironmentController envController) {
        this.envController = envController;
    }
}

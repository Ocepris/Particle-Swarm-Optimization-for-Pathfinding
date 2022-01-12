package visuals;

import simulation.EntityController;
import simulation.EnvironmentController;
import simulation.Map2D;

import java.awt.*;
import java.util.logging.Logger;

public class EnvironmentRenderer implements Renderer{
    private EnvironmentController envController;
    private int blockSize;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public EnvironmentRenderer(){

    }

    public EnvironmentRenderer(EnvironmentController envController){
        this.envController = envController;
        this.blockSize = envController.getBlockSize();
    }

    public Frame createMapMatchingFrame(){
        if(envController.getMap() != null){
            int sizeX = envController.getMap().getSizeX()+1;
            int sizeY = envController.getMap().getSizeY()+1;

            return new Frame((sizeX*blockSize)+ 250, (sizeY*blockSize) + 20);
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
        for(int y = 0; y < map.getSizeY(); y++){
            for(int x = 0; x < map.getSizeX(); x++){
                int blockType = map.getValueOf(x, y);
                if(blockType == Map2D.BORDER){
                    g.setColor(Color.BLACK);
                }else if(blockType == Map2D.GOAL){
                    g.setColor(Color.orange);
                }else if(blockType == Map2D.START){
                    g.setColor(Color.BLUE);
                }else{
                    g.setColor(Color.white);
                }

                g.fillRect(x*blockSize, y*blockSize, blockSize, blockSize);
                g.setColor(Color.GRAY);
                g.drawRect(x*blockSize, y*blockSize, blockSize, blockSize);
            }
        }

        if(EntityController.GLOBAL_BEST != null)
        {
            int x =(int) EntityController.GLOBAL_BEST.getX()/blockSize;
            int y =(int) EntityController.GLOBAL_BEST.getY()/blockSize;
            g.setColor(Color.RED);


            g.fillRect(x*blockSize, y*blockSize, blockSize, blockSize);
            g.setColor(Color.GRAY);
            g.drawRect(x*blockSize, y*blockSize, blockSize, blockSize);
        }
    }

    public void drawCenteredCircle(Graphics g, int x, int y, int r) {
        x = x-(r/2);
        y = y-(r/2);
        g.fillOval(x,y,r,r);
    }

    public void setEnvController(EnvironmentController envController) {
        this.envController = envController;
    }
}

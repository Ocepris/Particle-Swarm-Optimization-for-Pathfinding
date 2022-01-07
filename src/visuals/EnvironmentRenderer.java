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
        System.out.println(envController.getMap());
        if(envController.getMap() != null){
            int sizeX = envController.getMap().getSizeX()+1;
            int sizeY = envController.getMap().getSizeY()+1;

            System.out.println(sizeX + " " + sizeY);

            return new Frame(sizeX*blockSize, sizeY*blockSize);
        }else{
            logger.warning("Can't create Frame without a valid map!");
            return null;
        }
    }

    @Override
    public void render(Graphics g) {
        this.drawMap(g);
    }

    private void drawMap(Graphics g){
        Map2D map = envController.getMap();
        for(int x = 0; x < map.getSizeX(); x++){
            for(int y = 0; y < map.getSizeY(); y++){
                int blockType = map.getValueOf(x, y);
                //TODO blockTypes improvement
                if(blockType == 1){
                    g.setColor(Color.BLACK);
                }else{
                    g.setColor(Color.white);
                }

                g.fillRect(x*blockSize, y*blockSize, blockSize, blockSize);
                g.setColor(Color.GRAY);
                g.drawRect(x*blockSize, y*blockSize, blockSize, blockSize);
            }
        }
    }

    public void setEnvController(EnvironmentController envController) {
        this.envController = envController;
    }
}

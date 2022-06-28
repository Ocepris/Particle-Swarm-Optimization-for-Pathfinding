package MapGenerator;

import PSO.simulation.Map2D;
import PSO.simulation.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MapPanel extends JPanel implements MouseListener {

    Map2D map;
    int HEIGHT;
    int WIDTH;
    int[][] mapArray;
    public MapPanel(int[][] mapArray)
    {
        this.mapArray = mapArray;
        HEIGHT = mapArray.length;
        WIDTH = mapArray[0].length;
        map = new Map2D(mapArray);
        this.addMouseListener(this);
    }

    private void drawMap(Graphics g){
        for(int y = 0; y < HEIGHT; y++){
            for(int x = 0; x < WIDTH; x++){
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

                g.fillRect(x* Simulation.BLOCK_SIZE, y*Simulation.BLOCK_SIZE, Simulation.BLOCK_SIZE, Simulation.BLOCK_SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(x*Simulation.BLOCK_SIZE, y*Simulation.BLOCK_SIZE, Simulation.BLOCK_SIZE, Simulation.BLOCK_SIZE);
            }
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawMap(g);
    }


    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        int xPos = e.getX() / Simulation.BLOCK_SIZE;
        int yPos = e.getY() / Simulation.BLOCK_SIZE;
        int blockValue = mapArray[yPos][xPos];
        int newBlockValue = 0;

        if(e.getButton() == MouseEvent.BUTTON1)
            newBlockValue = (blockValue + 1) % 4;

        mapArray[yPos][xPos] = newBlockValue;
        map.setValues(mapArray);

        repaint();

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}

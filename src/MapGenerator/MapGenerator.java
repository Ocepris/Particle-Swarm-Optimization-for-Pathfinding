package MapGenerator;

import PSO.simulation.Map2D;
import PSO.simulation.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MapGenerator extends JFrame implements KeyListener {
    private int WIDTH;
    private int HEIGHT;
    public static int FPS = 60;
    public static long maxLoopTime = 1000 / FPS;
    int[][] mapArray;
    public static void main(String[] arg)
    {
        MapGenerator mapGenerator = new MapGenerator();
    }

    public MapGenerator()
    {
        this.setTitle("Map Generator");
        inputSize();
        setSize((WIDTH + 1) * Simulation.BLOCKSIZE, (HEIGHT + 1) * Simulation.BLOCKSIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.lightGray);
        mapArray = new int[HEIGHT][WIDTH];
        createBorder();
        MapPanel mapPanel = new MapPanel(mapArray);
        this.add(mapPanel);

        this.addKeyListener(this);
        setVisible(true);


    }

    public void createBorder()
    {
        for(int i = 0; i < mapArray.length; i ++)
        {
            mapArray[i][0] = Map2D.BORDER;
            mapArray[i][mapArray[i].length - 1] = Map2D.BORDER;
        }

        for(int i = 0; i < mapArray[0].length; i ++)
        {

            mapArray[0][i] = Map2D.BORDER;
            mapArray[mapArray.length - 1][i] = Map2D.BORDER;
        }



    }


    public void inputSize()
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Width in blocks");
            String width = reader.readLine();
            System.out.println("Height in blocks");
            String height = reader.readLine();

            WIDTH = Integer.parseInt(width);
            HEIGHT = Integer.parseInt(height);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'p')
            printMap();
    }

    public void printMap()
    {
        for(int y = 0; y < mapArray.length; y++)
            for(int x = 0; x < mapArray[0].length; x++)
            {
                if(x < mapArray[0].length -1)
                    System.out.print(mapArray[y][x]+",");
                else
                    System.out.print(mapArray[y][x]+"\n");

            }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}

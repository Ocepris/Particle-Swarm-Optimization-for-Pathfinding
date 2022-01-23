package IntersectionTester;

import javax.swing.*;
import java.awt.*;

public class IntersectionDebugger extends JFrame {

    public static void main(String[] arg)
    {
        new IntersectionDebugger();
    }

    public IntersectionDebugger()
    {
        this.setTitle("Map Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.lightGray);
        DrawPanel drawPanel = new DrawPanel();
        this.addKeyListener(drawPanel);
        this.add(drawPanel);
        this.setSize(1500,1000);
        setVisible(true);
    }



}

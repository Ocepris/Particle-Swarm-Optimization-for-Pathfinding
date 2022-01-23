package IntersectionTester;

import PSO.math.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements KeyListener, MouseListener {

    ArrayList<Vector2D> path = new ArrayList<>();
    PathOptimizer pathOptimizer;
    public DrawPanel()
    {
        this.addMouseListener(this);
        this.addKeyListener(this);
        pathOptimizer = new PathOptimizer();
    }


    public void drawCircle(Graphics g, Vector2D vec)
    {
        int x = (int) vec.getX();
        int y = (int) vec.getY();
        int r = 10;
        x = x-(r/2);
        y = y-(r/2);
        g.fillOval(x,y,r,r);
        g.setColor(Color.black);
        g.drawOval(x,y,r,r);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.black);
        for (Vector2D vec: path) {
            g.setColor(Color.black);
            drawCircle(g,vec);
        }

        if(path.size() >= 2)
            for(int i = 0; i< path.size() -1; i++)
            {
                Vector2D vec1 = path.get(i);
                Vector2D vec2 = path.get(i+1);
                g.drawLine((int)vec1.getX(),(int)vec1.getY(),(int)vec2.getX(),(int)vec2.getY());
            }

        var intersections = pathOptimizer.getAllIntersection(path);

        for (Vector2D vec:intersections) {

            g.setColor(Color.red);
            drawCircle(g,vec);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() =='r')
        {
            path.clear();
            repaint();
        }

        if(e.getKeyChar() =='i');
        {
            path = (ArrayList<Vector2D>) pathOptimizer.optimizePath(path);
            repaint();

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == 1)
        {
            path.add(new Vector2D(e.getX(),e.getY()));
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

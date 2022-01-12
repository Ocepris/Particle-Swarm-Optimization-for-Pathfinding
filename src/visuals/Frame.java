package visuals;

import javax.swing.*;
import java.awt.*;

public class Frame extends JPanel{
    private JFrame frame;
    private RenderController rc;

    public Frame(int res_x, int res_y){
        frame = new JFrame("Frame");
        frame.setSize(res_x, res_y);
        frame.setVisible(true);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setRenderController(RenderController rc){
        this.rc = rc;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(rc != null){
            for(Renderer r : rc.getRenderers()){
                r.render(g);
            }
        }

    }

    public JFrame getJFrame(){return frame;}
}

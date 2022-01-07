package visuals;

import javax.swing.*;

public class Frame {
    private JFrame frame;

    public Frame(int res_x, int res_y){
        frame = new JFrame("Frame");
        frame.setSize(res_x, res_y);
        frame.setVisible(true);
    }
}

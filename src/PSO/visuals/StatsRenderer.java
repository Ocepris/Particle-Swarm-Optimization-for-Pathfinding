package PSO.visuals;

import PSO.simulation.StatsContainer;

import java.awt.*;

public class StatsRenderer implements Renderer{
    private final StatsContainer stats;
    private final int xOffset;
    private int yOffset = 20;


    public StatsRenderer(StatsContainer stats, int xOffset){
        this.stats = stats;
        this.xOffset = xOffset;
    }
    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        yOffset = 20;
        for(String statType : stats.getKeys()){
            drawString(g,(statType + ": " + stats.getValue(statType)));

        }

        drawString(g,"");
        drawString(g, "Controls:");
        drawString(g, "'Space' to start/pause Simulation");
        drawString(g, "' R ' to reload");
        drawString(g, "' F ' to speed up / slow down");
        drawString(g,"");
        drawString(g, "' U ' to to draw unoptimized path");
        drawString(g, "' O ' to to draw optimized path");
        drawString(g, "' A ' to to run A* (very slow)");
        drawString(g,"");

      }

    private void drawString(Graphics g,String string)
    {
        g.drawString(string, xOffset, yOffset);
        yOffset +=20;
    }




}

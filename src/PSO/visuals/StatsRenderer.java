package PSO.visuals;

import PSO.simulation.StatsContainer;

import java.awt.*;

public class StatsRenderer implements Renderer{
    private StatsContainer stats;
    private int xOffset;

    public StatsRenderer(StatsContainer stats, int xOffset){
        this.stats = stats;
        this.xOffset = xOffset;
    }
    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        int offset = 20;
        for(String statType : stats.getKeys()){
            g.drawString(statType + ": " + stats.getValue(statType), xOffset, offset);
            offset += 20;
        }
    }
}

package visuals;

import simulation.StatsContainer;

import java.awt.*;

public class StatsRenderer implements Renderer{
    private StatsContainer stats;

    public StatsRenderer(StatsContainer stats){
        this.stats = stats;
    }
    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        int offset = 20;
        for(String statType : stats.getKeys()){
            g.drawString(statType + ": " + stats.getValue(statType), 10, offset);
            offset += 20;
        }
    }
}

package PSO.visuals;

import PSO.simulation.StatsContainer;

import java.awt.*;

public class StatsRenderer implements Renderer {
    private static final int NEW_LINE_OFFSET = 20;
    private static final int MARGIN_FROM_TOP = 20;

    private final StatsContainer stats;
    private final int xOffset;
    private int yOffset;


    public StatsRenderer(StatsContainer stats, int xOffset) {
        this.stats = stats;
        this.xOffset = xOffset;
    }

    @Override
    public void render(Graphics g) {
        this.yOffset = MARGIN_FROM_TOP;

        setFontSettings(g);
        drawStats(g);
        drawControls(g);
    }

    private void drawStats(Graphics g) {
        for (String statType : stats.getKeys())
        {
            drawString(g, (statType + ": " + stats.getValue(statType)));

        }


    }

    private void setFontSettings(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));

    }

    private void drawControls(Graphics g) {
        drawString(g, "");
        drawString(g, "Controls:");
        drawString(g, "'Space' to start/pause Simulation");
        drawString(g, "' R ' to reload");
        drawString(g, "' F ' to speed up / slow down");
        drawString(g, "");
        drawString(g, "' U ' to to draw unoptimized path");
        drawString(g, "' O ' to to draw optimized path");
        drawString(g, "' A ' to to run A* (very slow)");
        drawString(g, "");

    }

    private void drawString(Graphics g, String string) {
        g.drawString(string, xOffset, yOffset);
        nextLine();
    }

    private void nextLine() {
        yOffset += NEW_LINE_OFFSET;
    }


}

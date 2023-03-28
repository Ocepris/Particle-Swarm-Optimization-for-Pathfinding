package PSO.visuals;

import java.util.ArrayList;

public class RenderController {
    private final ArrayList<Renderer> renderers = new ArrayList<>();

    private Frame frame;
    public ArrayList<Renderer> getRenderers() {
        return renderers;
    }

    public RenderController(){

    }

    public void triggerRepaint(){
        frame.repaint();
    }

    public void addRenderer(Renderer r){
        renderers.add(r);
    }
    public void clear(){renderers.clear();}
    public void setFrame(Frame frame) {
        this.frame = frame;
    }
}

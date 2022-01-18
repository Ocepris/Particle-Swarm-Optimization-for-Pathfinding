package PSO.visuals;

import java.util.ArrayList;

public class RenderController {
    private ArrayList<Renderer> renderers = new ArrayList<>();

    private Frame frame;

    public RenderController(Frame frame){
        this.frame = frame;
    }

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
    public void clearRenderers(){renderers.clear();}
    public void removeRenderer(Renderer r){
        renderers.remove(r);
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }
}

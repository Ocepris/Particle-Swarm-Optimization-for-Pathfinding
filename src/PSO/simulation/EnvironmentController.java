package PSO.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class EnvironmentController {
    private Map2D map;
    private final int blockSize;
    private boolean mapLoaded = false;

    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public EnvironmentController(int blockSize){
        this.blockSize = blockSize;
    }

    public void loadMapFromCSVFile(String path){
        this.setMapLoaded(false);
        ArrayList<Integer[]> lineArray = new ArrayList<>();

        try(BufferedReader br = Files.newBufferedReader(Path.of(path),StandardCharsets.UTF_8)){
            String rowString = br.readLine();
            while(rowString != null){
                ArrayList<Integer> line = new ArrayList<>();
                String[] elements = rowString.split(",");

                Integer[] integerLine = Stream.of(elements).map(Integer::valueOf).toArray(Integer[]::new);
                lineArray.add(integerLine);

                rowString = br.readLine();
            }

            //convert to intArray
            int[][] intArray = new int[lineArray.size()][lineArray.get(0).length];
            for(int x=0; x < lineArray.size(); x++){
                for(int y=0; y < lineArray.get(0).length; y++){
                    intArray[x][y] = lineArray.get(x)[y];
                }
            }

            this.map = new Map2D(intArray);
            this.setMapLoaded(true);
            logger.info("Loaded map " + path + " to controller");

        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public Map2D getMap() {
        return this.map;
    }

    public boolean isMapLoaded() {
        return mapLoaded;
    }

    public void setMapLoaded(boolean mapLoaded) {
        this.mapLoaded = mapLoaded;
    }

    public int getBlockSize(){
        return this.blockSize;
    }
}

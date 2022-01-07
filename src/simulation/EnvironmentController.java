package simulation;

import visuals.Frame;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

public class EnvironmentController {
    private Map2D map;

    public EnvironmentController(Map2D map2d){
        this.map = map2d;
    }
    public EnvironmentController(){}

    public void setMap(Map2D map){
        this.map = map;
    }

    public void loadMapFromCSVFile(String path){
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

        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public Map2D getMap() {
        return this.map;
    }
}

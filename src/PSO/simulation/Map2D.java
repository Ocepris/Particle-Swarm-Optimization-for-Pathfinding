package PSO.simulation;

import PSO.math.Vector2D;

public class Map2D {
    private int[][] intArray;
    private String name;

    public static int BORDER = 1;
    public static int GOAL = 2;
    public static int START = 3;

    private Vector2D start;
    private Vector2D goal;
    private Vector2D globalBestPosition;


    public Map2D(int[][] intArray){
        this.name = "default";
        this.intArray = intArray;
        calculateStartAndGoal();
    }

    private void calculateStartAndGoal()
    {
        Vector2D offset = new Vector2D(Simulation.BLOCK_SIZE /2,Simulation.BLOCK_SIZE /2);
        for(int y = 0; y < intArray.length; y++)
            for(int x = 0; x < intArray[y].length; x++)
            {
                if(intArray[y][x] == GOAL)
                {
                    goal = new Vector2D(x,y).mult(Simulation.BLOCK_SIZE).add(offset);

                }
                else if(intArray[y][x] == START)
                {
                    start = new Vector2D(x,y).mult(Simulation.BLOCK_SIZE).add(offset);
                    globalBestPosition = start.clone();
                }
            }
    }

    public void setValues(int[][] intArray){ this.intArray = intArray; calculateStartAndGoal(); }

    public int[][] getValues(){ return this.intArray; }

    public int getValueOf(int x, int y){ return this.intArray[y][x]; }
    public int getBlockAtCoordinates(double x, double y)
    {
        return getValueOf((int) x / Simulation.BLOCK_SIZE, (int) y / Simulation.BLOCK_SIZE);
    }

    public int getSizeX(){ return intArray[0].length; }
    public int getSizeY(){ return intArray.length; }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getGoal() {
        return goal;
    }

    public Vector2D getGlobalBest() {
        return globalBestPosition;
    }

    public void setGlobalBestPosition(Vector2D globalBestPosition) {
        this.globalBestPosition = globalBestPosition;
    }
}

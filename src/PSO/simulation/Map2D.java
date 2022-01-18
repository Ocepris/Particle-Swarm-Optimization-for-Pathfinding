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


    public Map2D(int[][] intArray){
        this.name = "default";
        this.intArray = intArray;
        calculateStartAndGoal();
    }

    private void calculateStartAndGoal()
    {
        for(int y = 0; y < intArray.length; y++)
            for(int x = 0; x < intArray[y].length; x++)
            {
                if(intArray[y][x] == GOAL)
                {
                    Vector2D offset = new Vector2D(Simulation.BLOCKSIZE/2,Simulation.BLOCKSIZE/2);
                    goal = new Vector2D(x,y).mult(Simulation.BLOCKSIZE).add(offset);

                }
                else if(intArray[y][x] == START)
                    start = new Vector2D(x,y).mult(Simulation.BLOCKSIZE);
            }
    }

    public void setValues(int[][] intArray){ this.intArray = intArray; calculateStartAndGoal(); }

    public int[][] getValues(){ return this.intArray; }

    public int getValueOf(int x, int y){ return this.intArray[y][x]; }

    public int getSizeX(){ return intArray[0].length; }
    public int getSizeY(){ return intArray.length; }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getGoal() {
        return goal;
    }
}

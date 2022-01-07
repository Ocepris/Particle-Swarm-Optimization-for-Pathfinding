package simulation;

public class Map2D {
    private int[][] intArray;
    private String name;

    public Map2D(int[][] intArray){
        this.name = "default";
        this.intArray = intArray;
    }

    public void setValues(int[][] intArray){ this.intArray = intArray; }

    public int[][] getValues(){ return this.intArray; }

    public int getValueOf(int x, int y){ return this.intArray[x][y]; }

    public int getSizeX(){ return intArray[0].length; }
    public int getSizeY(){ return intArray.length; }
}

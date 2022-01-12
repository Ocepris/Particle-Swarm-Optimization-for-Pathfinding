package math;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(){
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D direction)
    {
        this.x += direction.x;
        this.y += direction.y;
        return this;
    }

    public Vector2D mult(double val)
    {
        x *= val;
        y *= val;
        return this;
    }

    public double getLength()
    {
        return Math.sqrt(x*x + y*y);
    }

    public Vector2D normalize()
    {
        return this.mult(1 / getLength());
    }

    public double getDistance(Vector2D vec)
    {
        double a = vec.x - this.x;
        double b = vec.y - this.y;
        return Math.sqrt((a*a)+(b*b));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}

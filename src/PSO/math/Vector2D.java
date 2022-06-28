package PSO.math;

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
        if(getLength() != 0)
            return this.mult(1 / getLength());
        else
            return this;
    }

    public double getDistance(Vector2D vec)
    {
        double a = vec.x - this.x;
        double b = vec.y - this.y;
        return Math.sqrt((a*a)+(b*b));
    }

    public double slope(Vector2D vec)
    {
        Vector2D A;
        Vector2D B;

        if(this.getX() < vec.getX())
        {
            A = this;
            B = vec;
        }
        else
        {
            A = vec;
            B = this;
        }

        double diffx = B.getX() - A.getX();
        double diffy = B.getY() - A.getY();
        return diffy / diffx;
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

    public Vector2D setValues(double x, double y)
    {
        this.setX(x);
        this.setY(y);
        return this;
    }

    public Vector2D clone()
    {
        return new Vector2D(x,y);
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

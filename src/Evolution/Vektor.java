package Evolution;

public class Vektor
{
	private float x = 0;
	private float y = 0;

	public Vektor(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Vektor()
	{

	}

	public void add(Vektor vek)
	{
		x += vek.getX();
		y += vek.getY();
	}

	public static Vektor fromAngle(float angle)
	{
		return new Vektor((float) Math.cos(angle), (float) Math.sin(angle));
	}
	
	public float getDistance(Vektor vek)
	{
		return (float) (Math.sqrt(Math.pow((vek.x-x),2)+Math.pow((vek.y-y),2)));
	}

	public void limit(float limit)
	{
		if(x > limit)
			x = limit;
		if(y > limit)
			y = limit;
		if(x < -limit)
			x = -limit;
		if(y<-limit)
			y= -limit;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public void setY(float y)
	{
		this.y = y;
	}
	
	public void divide(float v)
	{
		x=x/v;
		y=y/v;
	}
	
	
@Override
public String toString()
{
	return ("x: "+x+"  y: "+y);
	}
}

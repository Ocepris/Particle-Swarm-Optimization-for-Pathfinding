package Evolution;

import java.awt.*;
import java.util.Random;

public class Brain
{
    Vektor[] directions;
    int step =0;
    Color color;
    @SuppressWarnings("unchecked")
    public Brain(int size)
    {
        directions = new Vektor[size];
        Random rnd = new Random();
        color = new Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));

        radomize();
    }

    public void radomize()
    {
        for(int i=0;i<directions.length;i++)
        {
            Random rnd = new Random();
            float randomAngle = (float) ((rnd.nextFloat()*2*Math.PI)-Math.PI);
            directions[i] = Vektor.fromAngle(randomAngle);
        }
    }

    public Brain clone()
    {
        Brain b = new Brain(directions.length);
        b.directions = directions.clone();

        return b;
    }

    public void mutate(Dot freundin)
    {
        float mutationRate =0.01f;
        Random rnd = new Random();
        for(int i=0;i<directions.length;i++)
        {

            if(rnd.nextFloat()<mutationRate)
            {
                float randomAngle = (float) ((rnd.nextFloat()*2*Math.PI)-Math.PI);
                directions[i] = Vektor.fromAngle(randomAngle);
            }

        }
    }
}
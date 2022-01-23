package IntersectionTester;

import PSO.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class PathOptimizer {

    public List<Vector2D> optimizePath(List<Vector2D> path)
    {

        for(int i = 0; i < path.size() -1; i++)
            for(int j = path.size() -1; j-1 > i+1; j--) {

                Vector2D v1 = path.get(i);
                Vector2D v2 = path.get(i + 1);
                Vector2D v3 = path.get(j - 1);
                Vector2D v4 = path.get(j);


                Vector2D intersectionPoint = intersect(v1, v2, v3, v4);
                if (intersectionPoint != null) {
                    ArrayList<Vector2D> newPath = new ArrayList<>();
                    for (int x = 0; x <= i; x++) {
                        newPath.add(path.get(x));
                    }
                    newPath.add(intersectionPoint);
                    for (int x = j; x < path.size(); x++) {
                        newPath.add(path.get(x));
                    }

                    return optimizePath(newPath);
                }
            }

        return path;
    }

    public List<Vector2D> getAllIntersection(List<Vector2D> path)
    {
        ArrayList<Vector2D> intersections = new ArrayList<>();
        for(int i = 0; i < path.size() -1; i++)
            for(int j = path.size() -1; j-1 > i+1; j--) {
                Vector2D v1 = path.get(i);
                Vector2D v2 = path.get(i + 1);
                Vector2D v3 = path.get(j - 1);
                Vector2D v4 = path.get(j);
                Vector2D intersectionPoint = intersect(v1, v2, v3, v4);
                if (intersectionPoint != null) {
                    intersections.add(intersectionPoint);
                }
            }

        return intersections;
    }

    public Vector2D intersect(Vector2D v1, Vector2D v2, Vector2D v3, Vector2D v4)
    {
        return intersect(v1.getX(),v1.getY(),v2.getX(),v2.getY(),v3.getX(),v3.getY(),v4.getX(),v4.getY());
    }

    public Vector2D intersect(double x1, double y1, double x2, double y2,
                              double x3, double y3, double x4, double y4) {
        double a1, a2, b1, b2, c1, c2;
        double r1, r2, r3, r4;
        double denom, offset, num;

        double intersectX;
        double intersectY;
        boolean isIntersecting = false;

        // Compute a1, b1, c1, where line joining points 1 and 2
        // is "a1 x + b1 y + c1 = 0".
        a1=y2-y1;
        b1=x1-x2;
        c1=(x2*y1)-(x1*y2);

        // Compute r3 and r4.
        r3=((a1*x3)+(b1*y3)+c1);
        r4=((a1*x4)+(b1*y4)+c1);

        // Check signs of r3 and r4. If both point 3 and point 4 lie on
        // same side of line 1, the line segments do not intersect.
        if ((r3!=0)&&(r4!=0)&&same_sign(r3, r4)) {
            isIntersecting=false;
            return null;
        }

        // Compute a2, b2, c2
        a2=y4-y3;
        b2=x3-x4;
        c2=(x4*y3)-(x3*y4);

        // Compute r1 and r2
        r1=(a2*x1)+(b2*y1)+c2;
        r2=(a2*x2)+(b2*y2)+c2;

        // Check signs of r1 and r2. If both point 1 and point 2 lie
        // on same side of second line segment, the line segments do
        // not intersect.
        if ((r1!=0)&&(r2!=0)&&(same_sign(r1, r2))) {
            isIntersecting=false;
            return null;
        }

        //Line segments intersect: compute intersection point.
        denom=(a1*b2)-(a2*b1);

        if (denom==0) {
            isIntersecting=false;
            return null;
        }

        if (denom<0) {
            offset=-denom/2;
        } else {
            offset=denom/2;
        }

        // The denom/2 is to get rounding instead of truncating. It
        // is added or subtracted to the numerator, depending upon the
        // sign of the numerator.

        num=(b1*c2)-(b2*c1);
        if (num<0) {
            intersectX=(num-offset)/denom;
        } else {
            intersectX=(num+offset)/denom;
        }

        num=(a2*c1)-(a1*c2);
        if (num<0) {
            intersectY=(num-offset)/denom;
        } else {
            intersectY=(num+offset)/denom;
        }

        // lines_intersect
        isIntersecting=true;
        return new Vector2D(intersectX,intersectY);
    }

    private static boolean same_sign(double a, double b) {
        return ((a*b)>=0);
    }
}

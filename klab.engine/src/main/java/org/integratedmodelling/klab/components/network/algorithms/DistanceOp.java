package org.integratedmodelling.klab.components.network.algorithms;


import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.util.LinearComponentExtracter;
import java.util.List;

/**
 * Static methods for fast distance calculation.
 * Most of the code comes from JTS library
 * @author Gilles Vuidel
 */
public class DistanceOp {

    /**
     * Computes the square distance from a point p to a line segment AB
     *
     * @param p the point to compute the distance for
     * @param A one point of the line
     * @param B another point of the line (must be different to A)
     * @return the distance from p to line segment AB
     */
    public static double distancePointLine(Coordinate p, Coordinate A, Coordinate B) {
        // if start==end, then use pt distance
        if (  A.equals(B) ) {
            return (p.y - A.y)*(p.y - A.y) + (p.x - A.x)*(p.x - A.x); //p.distance(A);
        }

        // otherwise use comp.graphics.algorithms Frequently Asked Questions method
        /*(1)     	      AC dot AB
                       r = ---------
                             ||AB||^2
                    r has the following meaning:
                    r=0 P = A
                    r=1 P = B
                    r<0 P is on the backward extension of AB
                    r>1 P is on the forward extension of AB
                    0<r<1 P is interior to AB
            */
        final double dx = (B.x - A.x);
        final double dy = (B.y - A.y);

        final double r = ( (p.x - A.x) * dx + (p.y - A.y) * dy ) /
                (dx * dx + dy * dy);


        if (r <= 0.0) {
            return (p.y - A.y)*(p.y - A.y) + (p.x - A.x)*(p.x - A.x); //p.distance(A);
        }
        if (r >= 1.0) {
            return (p.y - B.y)*(p.y - B.y) + (p.x - B.x)*(p.x - B.x); //p.distance(B);
        }


        final double x = A.x + r * dx;
        final double y = A.y + r * dy;
        return (p.x - x)*(p.x - x) + (p.y - y)*(p.y - y);
    }

    /**
    * Computes the distance from a point to a sequence
    * of line segments.
    *
    * @param p a point
    * @param line a sequence of contiguous line segments defined by their vertices
    * @return the minimum distance between the point and the line segments
    */
    public static double distancePointLine(Coordinate p, Coordinate[] line)	{
        if (line.length == 0) {
            throw new IllegalArgumentException("Line array must contain at least one vertex");
        }
        // this handles the case of length = 1
        double minDistance = (p.y - line[0].y)*(p.y - line[0].y) + (p.x - line[0].x)*(p.x - line[0].x); //p.distance(line[0]);
        for (int i = 0; i < line.length - 1; i++) {
            double dist = distancePointLine(p, line[i], line[i+1]);
            if (dist < minDistance) {
                minDistance = dist;
            }
        }
        return Math.sqrt(minDistance);
    }

    /**
     * Geometries must not contains points and geometries must not overlap
     * @param g1
     * @param g2
     * @return 2 coordinates
     */
    public static Coordinate [] nearestPoints(Geometry g1, Geometry g2) {
        /**
        * Geometries are not wholely inside, so compute distance from lines to lines
        */
       List lines0 = LinearComponentExtracter.getLines(g1);
       List lines1 = LinearComponentExtracter.getLines(g2);

       double minDistance = Double.MAX_VALUE;

       Coordinate [] coords = new Coordinate[2];

         for (Object lines01 : lines0) {
             LineString line0 = (LineString) lines01;
             for (Object lines11 : lines1) {
                 LineString line1 = (LineString) lines11;
                 minDistance = computeMinDistance(line0, line1, minDistance, coords);
             }
         }

       return coords;
    }

    private static double computeMinDistance(LineString line0, LineString line1,
            double minDistance, Coordinate [] nearestPoints) {

        Coordinate[] coord0 = line0.getCoordinates();
        Coordinate[] coord1 = line1.getCoordinates();

          // brute force approach!
        for (int i = 0; i < coord0.length - 1; i++) {
          for (int j = 0; j < coord1.length - 1; j++) {
            double dist = distanceLineLine(
                                            coord0[i], coord0[i + 1],
                                            coord1[j], coord1[j + 1] );
            if (dist < minDistance) {
              minDistance = dist;
              LineSegment seg0 = new LineSegment(coord0[i], coord0[i + 1]);
              LineSegment seg1 = new LineSegment(coord1[j], coord1[j + 1]);
              Coordinate [] locGeom = seg0.closestPoints(seg1);
              nearestPoints[0] = locGeom[0];
              nearestPoints[1] = locGeom[1];
            }

          }
        }

        return minDistance;
    }

    /**
    * Computes the square distance from a line segment AB to a line segment CD
    *
    * Note: NON-ROBUST!
    *
    * @param A a point of one line
    * @param B the second point of  (must be different to A)
    * @param C one point of the line
    * @param D another point of the line (must be different to A)
    */
    private static double distanceLineLine(Coordinate A, Coordinate B, Coordinate C, Coordinate D) {
        // check for zero-length segments
        if (  A.equals(B) ) {
            return distancePointLine(A,C,D);
        }
        if (  C.equals(D) ) {
            return distancePointLine(D,A,B);
        }

        // AB and CD are line segments
        /* from comp.graphics.algo

            Solving the above for r and s yields
                                    (Ay-Cy)(Dx-Cx)-(Ax-Cx)(Dy-Cy)
                       r = ----------------------------- (eqn 1)
                                    (Bx-Ax)(Dy-Cy)-(By-Ay)(Dx-Cx)

                            (Ay-Cy)(Bx-Ax)-(Ax-Cx)(By-Ay)
                    s = ----------------------------- (eqn 2)
                            (Bx-Ax)(Dy-Cy)-(By-Ay)(Dx-Cx)
            Let P be the position vector of the intersection point, then
                    P=A+r(B-A) or
                    Px=Ax+r(Bx-Ax)
                    Py=Ay+r(By-Ay)
            By examining the values of r & s, you can also determine some other
    limiting conditions:
                    If 0<=r<=1 & 0<=s<=1, intersection exists
                    r<0 or r>1 or s<0 or s>1 line segments do not intersect
                    If the denominator in eqn 1 is zero, AB & CD are parallel
                    If the numerator in eqn 1 is also zero, AB & CD are collinear.

            */
        double r_top = (A.y-C.y)*(D.x-C.x) - (A.x-C.x)*(D.y-C.y) ;
        double r_bot = (B.x-A.x)*(D.y-C.y) - (B.y-A.y)*(D.x-C.x) ;

        double s_top = (A.y-C.y)*(B.x-A.x) - (A.x-C.x)*(B.y-A.y);
        double s_bot = (B.x-A.x)*(D.y-C.y) - (B.y-A.y)*(D.x-C.x);

        if  ( (r_bot==0) || (s_bot == 0) ) {
          return
            Math.min(distancePointLine(A,C,D),
              Math.min(distancePointLine(B,C,D),
                Math.min(distancePointLine(C,A,B),
                  distancePointLine(D,A,B)    ) ) );

        }
        double s = s_top/s_bot;
        double r=  r_top/r_bot;

        if ((r < 0) || ( r > 1) || (s < 0) || (s > 1) )	{
          //no intersection
          return
            Math.min(distancePointLine(A,C,D),
              Math.min(distancePointLine(B,C,D),
                Math.min(distancePointLine(C,A,B),
                  distancePointLine(D,A,B)    ) ) );
        }
        return 0.0; //intersection exists
    }

}
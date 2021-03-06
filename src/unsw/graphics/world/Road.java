package unsw.graphics.world;

import java.util.List;

import unsw.graphics.geometry.Point2D;

/**
 * COMMENT: Comment Road 
 *
 * @author malcolmr
 */
public class Road {

    private List<Point2D> points;
    private float width;
    
    /**
     * Create a new road with the specified spine 
     *
     * @param width
     * @param spine
     */
    public Road(float width, List<Point2D> spine) {
        this.width = width;
        this.points = spine;
    }

    /**
     * The width of the road.
     * 
     * @return
     */
    public double width() {
        return width;
    }
    
    /**
     * Get the number of segments in the curve
     * 
     * @return
     */
    public int size() {
        return points.size() / 3;
    }

    /**
     * Get the specified control point.
     * 
     * @param i
     * @return
     */
    public Point2D controlPoint(int i) {
        return points.get(i);
    }
    
    /**
     * Get a point on the spine. The parameter t may vary from 0 to size().
     * Points on the kth segment take have parameters in the range (k, k+1).
     * 
     * @param t
     * @return
     */
    public Point2D point(float t) {
        int i = (int)Math.floor(t);
        //System.out.println("i is "+ i);
        
        t = t - i;
        //System.out.println("t is "+ t);
        i *= 3;
        //System.out.println("segment start is "+ i);
        Point2D p0 = points.get(i++);
        Point2D p1 = points.get(i++);
        Point2D p2 = points.get(i++);
        Point2D p3 = points.get(i++);
        

        float x = b(0, t) * p0.getX() + b(1, t) * p1.getX() + b(2, t) * p2.getX() + b(3, t) * p3.getX();
        float y = b(0, t) * p0.getY() + b(1, t) * p1.getY() + b(2, t) * p2.getY() + b(3, t) * p3.getY();        
        
        return new Point2D(x, y);
    }
    
    /**
     * Calculate the Bezier coefficients
     * 
     * @param i
     * @param t
     * @return
     */
    private float b(int i, float t) {
        
        switch(i) {
        
        case 0:
            return (1-t) * (1-t) * (1-t);

        case 1:
            return 3 * (1-t) * (1-t) * t;
            
        case 2:
            return 3 * (1-t) * t * t;

        case 3:
            return t * t * t;
        }
        
        // this should never happen
        throw new IllegalArgumentException("" + i);
    }
    
    
    /**
     * Calculate the Bezier coefficients
     * using Bernstein's formula
     * 
     * @param i
     * @param t
     * @return
     */
    private float b1(int i, float t) {
        
        switch(i) {
        
        case 0:
            return (1-t) * (1-t);

        case 1:
            return 2 * (1-t) * t;
            
        case 2:
            return 1 * t * t;

        }
        
        // this should never happen
        throw new IllegalArgumentException("" + i);
    }
    
    /**
     * Calculate the tangent at a given point t
     * 
     * @param t
     * @return
     */
    public Point2D tangent(float t) {
    	int i = (int)Math.floor(t);
        t = t - i;
        
        i *= 3;
        
        Point2D p0 = points.get(i++);
        Point2D p1 = points.get(i++);
        Point2D p2 = points.get(i++);
        Point2D p3 = points.get(i++);
        

        float x = 3 * (b1(0, t) * (p1.getX()-p0.getX()) + b1(1, t) * (p2.getX()-p1.getX()) + b1(2, t) * (p3.getX()-p2.getX()));
        float y = 3 * (b1(0, t) * (p1.getY()-p0.getY()) + b1(1, t) * (p2.getY()-p1.getY()) + b1(2, t) * (p3.getY()-p2.getY()));
        return new Point2D(x, y);
    }
    
    
    public static Point2D normalize(Point2D p) {
    	float length = (float)Math.sqrt((p.getX()*p.getX()) + (p.getY()*p.getY()));
    	
    	return new Point2D(p.getX()/length, p.getY()/length);
    }
    
}

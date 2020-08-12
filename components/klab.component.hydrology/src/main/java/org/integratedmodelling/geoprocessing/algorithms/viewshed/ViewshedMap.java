package org.integratedmodelling.geoprocessing.algorithms.viewshed;
import java.util.ArrayList;
import java.util.List;
import java.lang.ArrayIndexOutOfBoundsException;

/**
 * Using a modified version of Franklin and Ray's viewshed algorithm as explained
 * in http://www.cs.rpi.edu/~cutler/publications/andrade_geoinformatica.pdf
 * 
 * Essentially for each point, create a square/circular perimeter with radius r.
 * Trace a line (using Brensenham algorithm) to each point on the perimeter. Then
 * calculate the slope to each point, where an increasing or stable slope meant that
 * that point was visible to the observer. This algorithm is generous, in that if a
 * single ray makes a cell visible, the whole cell is visible.
 * 
 * The ViewshedMap is essentially a WeightsMap, so can be used accordingly for display purposes.
 * 
 * @author Alejandro Frias
 * @version July 2014
 *
 */
public class ViewshedMap {
    /** 
     * Flag for using circular perimeters instead of standard square.
     * Unsure which is more efficient. Preliminary tests were inconclusive. Better tests and bench marks needed.
     */
    public final static boolean USE_CIRCULAR_PERIMETER = true;
    
    private final WeightsMap _heightmap;
    private WeightsMap _viewshed;
    private int _lastUsedRadius;
    private int _lastUsedHeight;
    
    /**
     * Creates an empty viewshedmap that is ready for calculations.
     * 
     * @param map Needs a WeightsMap already parsed from the dtm file to set as the source height map
     */
    public ViewshedMap(WeightsMap map) {
        _heightmap = map;
        _viewshed = new WeightsMap(map.w(), map.h());
        _lastUsedRadius = 0;
        _lastUsedHeight = 0;
    }
    
    /**
     * Creates a ViewsheMap and calculates the viewshed count for every cell.
     * 
     * @param map The original height map
     * @param r The radius with each viewshed is calculated
     */
    public ViewshedMap(WeightsMap map, int r, int h) {
        this(map);
        _lastUsedRadius = r;
        _lastUsedHeight = h;
        calcViewshedWeights(r, h);
        
    }
    
    /**
     * Calculates the viewshed count for every cell on the map
     * 
     * @param r The radius of the viewshed
     * @param h The height of the observer (or UAV) above the ground
     */
    public void calcViewshedWeights(int r, int h) {
    	_lastUsedRadius = r;
    	_lastUsedHeight = h;
        for(int y = 0; y < _heightmap.h(); y++) {
            for(int x = 0; x < _heightmap.w(); x++) {
                _viewshed.setSource(x, y, calcViewshedCount(x, y, h, r));
            }
        }
        _viewshed.reset();
    }
    
    /**
     * Calculates the viewshed count for every cell in the region given
     * @param r The radius of the viewshed
     * @param h The height of the observer (or UAV) above the ground
     * @param cells 2D array of boolean indicating which cells to run the viewshed algorithm on
     */
    public void calcViewshedWeights(int r, int h, boolean[][] region) {
    	if (region.length == height() && region[0].length == width()) {
        	_lastUsedRadius = r;
        	_lastUsedHeight = h;
            for(int y = 0; y < region.length; y++) {
                for(int x = 0; x < region[0].length; x++) {
                    if(region[y][x]) _viewshed.setSource(x, y, calcViewshedCount(x, y, h, r));
                }
            }
    	} else {
    		String s = "Region dimensions does not match size of map";
    		System.out.println(s);
    		throw new ArrayIndexOutOfBoundsException(s);
    	}
    }
    
    /**
     * Calculates the viewshed as a 2D array of booleans, true for visible and false for not visible
     * by observer at point (x, y), at height h above the ground. The size of the viewshed indicated by the radius r.
     * 
     * 
     * 
     * @param x x position of observer
     * @param y y position of observer
     * @param h Height above ground (same units as source height map)
     * @param r Radius of viewshed
     * @return 2D array of booleans representing viewshed
     */
    public boolean[][] calcFullViewshed(int x, int y, int h, int r) {
    	_lastUsedRadius = r;
    	_lastUsedHeight = h;
    	
    	// Calculate the points tha make up perimeter of the viewshed
    	List<Point> perimeter;
        if(USE_CIRCULAR_PERIMETER) perimeter = calcCircularPerimeter(x, y, r); else perimeter = calcPerimeter(x, y, r);
        
        // Set the observer at height h above the ground at cell (x, y)
        Point observer = new Point(x, y, _heightmap.getSource(x, y) + h);
        
        // Grab the dimensions of the viewshed
        int[] corners = calcCorners(x, y, r);
        int x1 = corners[0];
        int x2 = corners[1];
        int y1 = corners[2];
        int y2 = corners[3];
        int pw = x2 - x1 + 1;
        int ph = y2 - y1 + 1;
        
        // Calculate the slope to each point
        double[][] slopes = new double[ph][pw];
        boolean[][] viewshed = new boolean[ph][pw];
        for (int i = 0; i < slopes.length; i++) {
            for (int j = 0; j < slopes[0].length; j++) {
                Point target = new Point(x1 + j, y1 + i, _heightmap.getSource(x1 + j, y1 + i));
                slopes[i][j] = calcSlope(observer, target);
                viewshed[i][j] = false;
            }
        }
        viewshed[y - y1][x - x1] = true;
        
        // For each point on the perimeter, trace a ray/line to it, and use the slopes to 
        // determine visibility of each cell
        for(Point p : perimeter) {
            List<Point> line = calcBrensenhamLine(x, y, p.x(), p.y());
            line.remove(0);
            double maxSlope = Double.NEGATIVE_INFINITY;
            for(Point l : line) {
            	double slope = slopes[l.y() - y1][l.x() - x1];
            	if(slope >= maxSlope) {
            		maxSlope = slope;
            		viewshed[l.y() - y1][l.x() - x1] = true;
            	}
            }
        }
        
    	return viewshed;
    }
    
    
    /**
     * @param x x position of observer
     * @param y y position of observer
     * @param h Height above ground (same units as source height map)
     * @param r Radius of viewshed
     * @return Number of visible cells in the viewshed.
     */
    public int calcViewshedCount(int x, int y, int h, int r) {
    	_lastUsedRadius = r;
    	_lastUsedHeight = h;
        int count = 0;
        for (boolean[] row : calcFullViewshed(x, y, h, r)) {
        	for (boolean bool : row) {
        		if(bool) count++;
        	}
        }
        return count;
    }
    
    /**
     * Returns the corners = [x1, x2, y1, y2] of a square perimeter with radius r
     * Top left = (x1,y1), etc.
     * Accounts for edges and 
     */
    public int[] calcCorners(int x, int y, int r) {
    	_lastUsedRadius = r;
        int[] corners = new int[4];
        if (x - r <= 0) corners[0] = 0 ; else corners[0] = x - r;
        if (x + r >= _heightmap.w())  corners[1] = _heightmap.w() - 1; else corners[1] = x + r;
        if (y - r <= 0) corners[2] = 0 ; else corners[2] = y - r;
        if (y + r >= _heightmap.h())  corners[3] = _heightmap.h() - 1; else corners[3] = y + r;
        
        return corners;
    }
    
    /**
     * Gets all t
     */
    /**
     * 
     * @param x X-position of observer
     * @param y X-position of observer
     * @param r Radius of viewshed
     * @return List of Points that make up the perimeter of a 2rx2r square centered at (x,y)
     */
    public List<Point> calcPerimeter(int x, int y, int r) {
    	_lastUsedRadius = r;
        List<Point> perimeter = new ArrayList<>();
        
        int[] corners = calcCorners(x, y, r);
        int x1 = corners[0];
        int x2 = corners[1];
        int y1 = corners[2];
        int y2 = corners[3];

        // top and bottom sides(with corners)
        for (int i = x1; i < x2 + 1; i++) {
            perimeter.add(new Point(i, y1, _heightmap.getSource(i, y1)));
            perimeter.add(new Point(i, y2, _heightmap.getSource(i, y2)));
        }
        
        // left and right sides (without corners)
        for (int j = y1 + 1; j < y2; j++) {
            perimeter.add(new Point(x1, j, _heightmap.getSource(x1, j)));
            perimeter.add(new Point(x2, j, _heightmap.getSource(x2, j)));
        }
        
        return perimeter;
    }
    
    /**
     *  Using a modified Bresenham algorithm for generating the points of a circle
     *  Added bounds detection. Currently duplicates certain points that are out of 
     *  bounds on both the x and y axis. using a set could eliminate this for a minimal
     *  speed up in certain special cases)
     *  
     *  Credit for implementation goes to:
     *  http://www.netgraphics.sk/bresenham-algorithm-for-a-circle
     *  
     * @param x1 X-Position of center of circle
     * @param y1 Y-Position of center of circle
     * @param r Radius of circle
     * @return List of Points on a circular perimeter around (x1, y1) with radius r
     */
    public List<Point> calcCircularPerimeter(int x1, int y1, int r) {
    	_lastUsedRadius = r;
    	
    	List<Point> perimeter = new ArrayList<>();
    	
    	int d1 = 3 - (2 * r);  
        int x = 0;  
      
        int y = r;  
        boolean rov=true;  
      
        // for one eights, while x is not >= y  
        while (rov){  
            if (x>=y){rov=false;}  
            if (d1 < 0) { d1 = d1 + (4 * x) + 6; }  
            else{ d1 = d1 + 4 * (x - y) + 10; // (1)  
                y = y - 1;
            }  
          
            
            int xplusx, xminusx, xplusy, xminusy, yplusy, yminusy, yplusx, yminusx;
            
            // bounds detection
            xplusx = Math.min(x1 + x, width() - 1);
            xminusx = Math.max(x1 - x, 0);
            xplusy = Math.min(x1 + y, width() - 1);
            xminusy = Math.max(x1 - y, 0);
            yplusy = Math.min(y1 + y, height() - 1);
            yminusy = Math.max(y1 - y, 0);
            yplusx = Math.min(y1 + x, height() - 1);
            yminusx = Math.max(y1 - x, 0);
            
            perimeter.add(new Point(xplusx,  yplusy,  _heightmap.getSource(xplusx,  yplusy)));  
            perimeter.add(new Point(xplusx,  yminusy, _heightmap.getSource(xplusx,  yminusy)));  
            perimeter.add(new Point(xminusx, yplusy,  _heightmap.getSource(xminusx, yplusy)));  
            perimeter.add(new Point(xminusx, yminusy, _heightmap.getSource(xminusx, yminusy)));  

            perimeter.add(new Point(xplusy,  yplusx,  _heightmap.getSource(xplusy,  yplusx)));  
            perimeter.add(new Point(xplusy,  yminusx, _heightmap.getSource(xplusy,  yminusx)));  
            perimeter.add(new Point(xminusy, yplusx,  _heightmap.getSource(xminusy, yplusx)));  
            perimeter.add(new Point(xminusy, yminusx, _heightmap.getSource(xminusy, yminusx)));  
            x++;  
        }  
    	
    	return perimeter;
    }
    

    /**
     * Credit for Brensenham line implementation to http://tech-algorithm.com/articles/drawing-line-using-bresenham-algorithm/
     * 
     * @param x X-Pos of start point (observer)
     * @param y Y-Pos of start point (observer)
     * @param x2 X-Pos of end point (target)
     * @param y2 Y-Pos of end point (target)
     * @return List of Points along the rasterized line from (x, y) to (x2, y2)
     */
    public List<Point> calcBrensenhamLine(int x, int y, int x2, int y2) {
        List<Point> line = new ArrayList<>();
        int w = x2 - x ;
        int h = y2 - y ;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
        if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
        if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
        if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
        int longest = Math.abs(w) ;
        int shortest = Math.abs(h) ;
        if (!(longest>shortest)) {
            longest = Math.abs(h) ;
            shortest = Math.abs(w) ;
            if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
            dx2 = 0 ;            
        }
        int numerator = longest >> 1 ;
        for (int i=0;i<=longest;i++) {
            line.add(new Point(x, y, _heightmap.getSource(x, y)));
            numerator += shortest ;
            if (!(numerator<longest)) {
                numerator -= longest ;
                x += dx1 ;
                y += dy1 ;
            } else {
                x += dx2 ;
                y += dy2 ;
            }
        }
        return line;
    }
    
    /**
     * Rise in Z-axis over change in XY-plane.
     * (z - z0)/sqrt((x - x0)^2 + (y - y0)^2)
     * 
     * @param observer (x, y, z) of observer
     * @param target (x, y, z) of target
     * @return Slope of line from observer to target
     */
    public double calcSlope(Point observer, Point target) {
        double deltaZ = target.z() - observer.z();
        double deltaXY = Math.sqrt(Math.pow(target.x() - observer.x(), 2) + Math.pow(target.y() - observer.y(), 2));
        
        return deltaZ/deltaXY;
    }
    
    /** 
     * @return The WeightsMap representing the viewshed counts calculated for each point
     */
    public WeightsMap getViewshedMap() {
        return _viewshed;
    }
    
    /** 
     * @return The WeightsMap made fromt he source height map
     */
    public WeightsMap getSourceHeightMap() {
        return _heightmap;
    }
    
    /**
     * @return The last used radius of the viewshed (for any kind of calculation)
     */
    public int r() {
    	return _lastUsedRadius;
    }
    
    /**
     * @return The last used height of the obseerver
     */
    public int h() {
    	return _lastUsedHeight;
    }
    
    /**
     * @return width of the map (in cells/# of points)
     */
    public int width() {
    	return _heightmap.w();
    }
    
    /**
     * @return height of the map (in cells/# of points)
     */
    public int height() {
    	return _heightmap.h();
    }
    
    /**
     * Contains some commented out tests for checking out the different functions.
     */
    public static void main(String[] args) throws Exception {
        String file = "test.dtm";
        System.out.println("Loading "+file);
        WeightsMap m = new WeightsMap(file);
        

        
        System.out.println("Width:\t" + m.w());
        System.out.println("Height:\t" + m.h());
        
        // Simple Brensenham Line test
        /*
        // ViewshedMap viewshed = new ViewshedMap(m);
        List<Point> line = viewshed.calcBrensenhamLine(0, 0, 5, 5);
        int x = 0;
        int y = 0;
        for(Point p : line) {
            System.out.println("Line Point: " + p);
            System.out.println("ActualPoint: " + new Point(x, y, viewshed.getViewshedMap().getSource(x,y)));
            x++;
            y++;
        }
        */
        
        // Simple perimeter check
        /*
        // ViewshedMap viewshed = new ViewshedMap(m);
        for(Point p : viewshed.calcPerimeter(1, 1, 1)) {
            System.out.println(p);
        }
        */
        /*
        // Simple Viewshed Count test
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                m.low(j, i);
            }
        }
        m.high(0, 1);
        m.high(1, 1);
        m.high(1, 0);
        
        ViewshedMap viewshed = new ViewshedMap(m);
        m.printMap();
        long start = System.currentTimeMillis();
        double count = viewshed.calcViewshedCount(0,0,0,5);
        long stop = System.currentTimeMillis();
        System.out.println("count " + count + " time " + (stop - start));
        */
        
        // Computaion time test
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                m.low(j, i);
            }
        }
        m.low(0,0);
        m.high(0, 1);
        m.high(1, 1);
        m.high(1, 0);
        m.writeMap("test.dtm");
        m.printMap();
        ViewshedMap viewshed = new ViewshedMap(m);
        int count = 0;
        for (boolean[] row : viewshed.calcFullViewshed(0, 0, 0, 5)) {
        	for (boolean bool : row) {
        		if(bool) count++;
        		System.out.print(bool + "\t");
        	}
        	System.out.print("\n");
        }
        System.out.println("COUNT: " + count);
        
        /*
        ViewshedMap viewshed = new ViewshedMap(m);
        
        long totalTime = 0;
        int x, y;
        int width = 5;
        int height = 5;
        int r = 500;
        double[][] counts = new double[m.h()][m.w()];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                x = i;
                y = j;
                long start = System.currentTimeMillis();
                double count = viewshed.calcViewshedCount(x,y,0,r);
                viewshed.getViewshedMap().setSource(x, y, count);
                long stop = System.currentTimeMillis();
                
                System.out.println("Time: " + (stop - start) +  " point (" + x + ", " + y + ") count=" + count);
                totalTime += (stop - start);
            }

        }
        
        System.out.println("Total Time: " + totalTime);
        System.out.println("Average per point: " + totalTime/(width * height));
        */
        

        
        
    }

}

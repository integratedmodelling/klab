package org.integratedmodelling.geoprocessing.algorithms.viewshed;

/**
 * Simple (x, y, z) point class
 * 
 * @author Alejandro Frias
 * @version July 2014
 */
public class Point {
	private int _x;
	private int _y;
	private double _z;
	
	public Point(int x, int y, double z) {
		_x = x;
		_y = y;
		_z = z;
	}
	
	public int x() {
		return _x;
	}
	
	public int y() {
		return _y;
	}
	
	public double z() {
		return _z;
	}
	
	@Override
	public String toString() {
		return "(" + _x + ", " + _y + ", " + _z + ")";
	}
	
	@Override
	public int hashCode() {
        return toString().hashCode();
    }

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point))
			return false;
		if (obj == this)
			return true;

		Point rhs = (Point) obj;
		return rhs.toString().equals(obj.toString());
    }
}

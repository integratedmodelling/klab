package org.integratedmodelling.owa;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/*
 * Interpolation with Bernstein polynomials.
 */

public class BernsteinInterpolator {
	
	private List<Point2D> values;
	private List<Point2D> vPoints;
	private List<Point2D> wPoints;
	private List<Point2D> skPoints;
	
	
	public BernsteinInterpolator(List<Point2D> values, IContextualizationScope scope) {
		this.values = values;
		setVWSKPoints(values, scope);
	}
	
	
	private Double sSlope(Point2D p0, Point2D p1) {
		return (p1.getY()-p0.getY())/(p1.getX()-p0.getX());
	}
	
	private Double mSlopeCaseOne(Point2D p1, Point2D p2, Double s1) {
		Double deltax = (p2.getY() - p1.getY() )/s1;
		Double cx = 0.5*(p1.getX() + deltax + p1.getX());
		
		return (p2.getY()-p1.getY())/(cx-p1.getX());
	}
	
	private Double mSlopeCaseTwo(Point2D p0, Point2D p1, Double m2) {
		Double deltax = (p1.getY() - p0.getY())/m2;
		Double cx = 0.5*(p1.getX() - deltax + p0.getX());
				
		return (p1.getY()-p0.getY())/(p1.getX()-cx);
	}
	
	private Point2D getZ(Point2D p0, Point2D p1, Double m0, Double m1) {
		Double xZ = (p1.getY() - m1 * p1.getX() - p0.getY() + m0 * p0.getX())/(m0-m1);
		Double yZ = p0.getY()- m0 * p0.getX() + m0*xZ;
		return new Point2D.Double(xZ,yZ);
				
	}
		
	private Boolean zInBoundingBox(Point2D p0, Point2D p1, Point2D z) {
		Boolean bool;
		if (z.getX() < p1.getX() && z.getX() > p0.getX() && z.getY() < p1.getY() && z.getY() > p0.getY()) {
			bool = true;
		} else {
			bool = false;
		}
		return bool;
	}
	
	private Point2D getV(Point2D p0, Point2D z, Double m0){
		Double vX = 0.5*(p0.getX() + z.getX());
		Double vY = p0.getY() + m0*( vX - p0.getX() );
		return new Point2D.Double(vX,vY);
	}
	
	private Point2D getW(Point2D p1, Point2D z, Double m1) {
		Double wX = 0.5*(p1.getX()+z.getX());
		Double wY = p1.getY() +  m1*( wX - p1.getX() );
		return new Point2D.Double(wX,wY);
	}
	
	private Point2D getSplineKnot(Point2D v, Point2D w, Point2D z) {
		Double alpha = (w.getY()-v.getY())/(w.getX()-v.getX());
		Double beta = v.getY() - alpha*v.getX();
		Double splineY = beta + alpha*z.getX();
		return new Point2D.Double(z.getX(),splineY);
	}
		
	private Double secondOrderBernsteinPolynomial(Point2D p0, Point2D p1, Point2D p2, Double x) {
		Double leftMember = p0.getY()*(p2.getX()-x)*(p2.getX()-x);
		Double centralMember = 2*p1.getY()*(x-p0.getX())*(p2.getX()-x);
		Double rightMember = p2.getY()*(x-p0.getX())*(x-p0.getX());
		Double denominator = ((p2.getX()-p0.getX())*(p2.getX()-p0.getX()));
		return (leftMember + centralMember + rightMember)/denominator; 
	}
	
	private List<Double> calculateSSlopes(List<Point2D> values) {
		// Calculate slopes S.
		List<Double> sSlopes = new ArrayList<>();
		
		// The first s slope is actually not defined. Its value is irrelevant for future calculations but sSlopes must have the same size than values.
		sSlopes.add(0.0);
		
		Double slope;
		for (Integer i=1;i<values.size();i++) {
			slope = sSlope( values.get(i), values.get(i-1) );
			sSlopes.add( slope );
		}
		return sSlopes;
	}
	
	private List<Double> calculateMSlopes(List<Point2D> values, List<Double> sSlopes){
		// Calculate slopes M.
		Double m;
		List<Double> mSlopes = new ArrayList<>();
		
		// To ensure that in the case all weights are equal the function is linear, first slope must be equal to the last and set to 1.0.
		mSlopes.add(1.0);
		
		// Calculation of the intermediate m slopes.
		for (Integer i=1; i<values.size()-1 ; i++) { 
			
			// TODO: check the effect of the inefficient recursion on running speed. See comment below for info.  
			m = calculateMSlopeAtPoint(values,sSlopes,i);
			mSlopes.add(m);
		}
		
		mSlopes.add(1.0);
		
		return mSlopes;
	}
	
	/*
	 * Recursive function for m calculation. It's inefficient if condition 2 is the most normal and if values is big. 
	 * Could be made more efficient by adding a calculatedMSlope cache to avoid multiple calculations of the same slope. 
	 * However, calculating slopes are basically a couple of sums and multiplications, thus if there are not lots of
	 * observations overhead shouldn't be large. 
	 * */
	private Double calculateMSlopeAtPoint(List<Point2D> values, List<Double> sSlopes, Integer index) {
		Double m=1.0;
		
		if ((index+1) < values.size()) {
			Double s1, s2;
			Point2D p0, p1, p2;
			p1 = values.get(index);
			s1 = sSlopes.get(index);
			p2 = values.get(index+1);
			s2 = sSlopes.get(index+1);
			
			if (s1*s2 <= 0.0) {
				m = 0.0;
			} else if(s1 > s2 && s2 > 0.0) {
				m = mSlopeCaseOne(p1,p2,s1);
			} else if(s2 > s1 && s1 > 0.0) {
				p0 = values.get(index-1);
				m = mSlopeCaseTwo(p0,p1, calculateMSlopeAtPoint(values,sSlopes,index+1) ); 
			}	
		} 
		// Else: the function returns 1.0 which is the border condition at the right.
		
		return m;
	}
	
	private void setVWSKPoints(List<Point2D> values, IContextualizationScope scope){
		
		// Calculate slopes S.
		List<Double> sSlopes = calculateSSlopes(values);
		// Calculate slopes M.
		List<Double> mSlopes = calculateMSlopes(values,sSlopes);
//		for(Integer i=0; i<mSlopes.size()+2;i++) {
//			scope.getMonitor().info("i = " + i + ", m = " + mSlopes.get(i) );
//		}
		
		// Calculate Z,V,W,SK points.
		List<Point2D> vPoints = new ArrayList<>();
		List<Point2D> wPoints = new ArrayList<>();
		List<Point2D> skPoints = new ArrayList<>();
		Point2D v,w,z,zOut;
		Double xZOut;
		for (Integer i=0; i<values.size()-1 ; i++) {
			z = getZ(values.get(i), values.get(i+1), mSlopes.get(i), mSlopes.get(i+1));
			//scope.getMonitor().info("i = " + i + ", z = " + z +", m0 = " + mSlopes.get(i) + ", m1 = " + mSlopes.get(i+1) + ", ow0 = " + values.get(i) + ", ow1 = " + values.get(i+1)   );
			if (zInBoundingBox(values.get(i),values.get(i+1),z)) {
				v = getV(values.get(i),z,mSlopes.get(i));
				vPoints.add(v);
				w = getW(values.get(i+1),z,mSlopes.get(i+1));
				wPoints.add(w);
				skPoints.add(getSplineKnot(v,w,z));
//				scope.getMonitor().info("In bounding box:  z = " + z +", v = " + v + ", w = " + w + ", sk = " + getSplineKnot(v,w,z)  );
			} else {
				xZOut = 0.5*(values.get(i).getX() + values.get(i+1).getX());
				zOut = new Point2D.Double(xZOut,0.0); // Y coordinate of Z is useless at this point.
				v = getV(values.get(i),zOut,mSlopes.get(i));
				vPoints.add(v);
				w = getW(values.get(i+1),zOut,mSlopes.get(i+1));
				wPoints.add(w);
				skPoints.add(getSplineKnot(v,w,zOut));
//				scope.getMonitor().info("Out of bounding box: z = " + z +", v = " + v + ", w = " + w + ", sk = " + getSplineKnot(v,w,zOut)  );
			}
		}
//		values.get(20);
		this.vPoints = vPoints;
		this.wPoints = wPoints;
		this.skPoints = skPoints;
	}
	
	private Integer findInterval(Double x, List<Point2D> values, IContextualizationScope scope) {
		Integer i=0;
		while ( x > values.get(i+1).getX() ) {
			i++;
		}
		return i;
	}
	
	public Double getInterpolatedValue(Double x,  IContextualizationScope scope) {
		// get interval of x with respect to the initial values.
		// then get subinterval between to define the points of interest.
		Integer interval = findInterval(x, values,scope);
		
		Double interp=0.0;
		Point2D p0,p1,p2;
		
		if (x < skPoints.get(interval).getX()) {
			p0 = values.get(interval);
			p1 = vPoints.get(interval);
			p2 = skPoints.get(interval);
			interp = secondOrderBernsteinPolynomial(p0,p1,p2,x);
//			scope.getMonitor().info("Condition 1: xVal = " + x + ", sk = " + p2 + ", v = " + p1 + ", val = " + p0 + ", interp = " + interp);
			
		} else { // Always entering here.
			p0 = skPoints.get(interval);
			p1 = wPoints.get(interval);
			p2 = values.get(interval+1);
			interp = secondOrderBernsteinPolynomial(p0,p1,p2,x);
//			scope.getMonitor().info("Condition2: xVal = " + x + ", sk = " + p0 + ", w = " + p1 + ", val = " + p2 + ", interp = " + interp);
		}
				
		return interp;
	}
	
	
	
}
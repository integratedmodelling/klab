//package org.integratedmodelling.engine.geospace.layers;
//
//import org.integratedmodelling.common.vocabulary.GeoNS;
//import org.integratedmodelling.engine.geospace.layers.SpatialNetwork.IndexedLine;
//
//import org.locationtech.jts.geom.Point;
//import org.locationtech.jts.linearref.LinearLocation;
//import org.locationtech.jts.linearref.LocationIndexedLine;
//
///**
// * Represents the insertion or exit from a known route. 
// * 
// * @author Ferd
// *
// */
//public class EntryPoint {
//    
//    private Point startingPoint;
//    private Point joinPoint;
//    private LocationIndexedLine way;
//    private LinearLocation joinLocation;
//    private double distance = Double.NaN;
//    private Object userData;
//    
//    public EntryPoint(Point start, Point join, LocationIndexedLine way, LinearLocation joinLocation) {
//        this.startingPoint = start;
//        this.joinPoint = join;
//        this.way = way;
//        this.joinLocation = joinLocation;
//        this.userData = ((IndexedLine) way).getGeometry().getUserData();
//    }
//    
//    public Point getJoinPoint() {
//        return this.joinPoint;
//    }
//    
//    public Point getStartingPoint() {
//        return this.startingPoint;
//    }
//    
//    public LocationIndexedLine getWay() {
//        return way;
//    }
//    
//    public LinearLocation getJoinLocation() {
//        return joinLocation;
//    }
//    
//    public Object getUserData() {
//        return this.userData;
//    }
//
//    public double getDistance() {
//        if (Double.isNaN(distance)) {
//            distance = GeoNS.getDistance(startingPoint.getCoordinate(), joinPoint.getCoordinate()) * 1000;
//        }
//        return distance;
//    }
//}
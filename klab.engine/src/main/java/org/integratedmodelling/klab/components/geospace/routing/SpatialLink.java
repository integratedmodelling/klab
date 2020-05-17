//package org.integratedmodelling.engine.geospace.layers;
//
//import org.integratedmodelling.common.configuration.KLAB;
//import org.integratedmodelling.common.vocabulary.GeoNS;
//import org.integratedmodelling.klab.api.observations.IDirectObservation;
//import org.jgrapht.graph.DefaultEdge;
//
//import com.vividsolutions.jts.geom.Coordinate;
//import com.vividsolutions.jts.geom.Geometry;
//import com.vividsolutions.jts.geom.LineString;
//
//public class SpatialLink extends DefaultEdge {
//
//    private static final long serialVersionUID = -1100494509742050549L;
//
//    IDirectObservation        subject;
//    Geometry                  geometry;
//    double                    length           = Double.NaN;
//
//    SpatialLink(IDirectObservation subject, Geometry geometry) {
//        this.subject = subject;
//        this.geometry = geometry;
//    }
//
//    public IDirectObservation getObservation() {
//        return subject;
//    }
//    
//    public double getLength() {
//
//        if (Double.isNaN(this.length)) {
//            this.length = 0;
//            if (geometry instanceof LineString) {
//                Coordinate[] coords = geometry.getCoordinates();
//                Coordinate coord = coords[0];
//                for (int i = 1; i < coords.length; i++) {
//                    length += GeoNS.getDistance(coord, coords[i]) * 1000;
//                    coord = coords[i];
//                }
//            } else {
//                KLAB.warn("asked for length of non-linear geometry in spatial link: unimplemented");
//            }
//        }
//
//        return this.length;
//    }
//}

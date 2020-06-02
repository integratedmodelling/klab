//package org.integratedmodelling.engine.geospace.layers;
//
//import com.vividsolutions.jts.geom.Coordinate;
//
//public class SpatialNode {
//    
//    Coordinate                location;
//
//    SpatialNode(Coordinate location) {
//        this.location = location;
//    }
//    
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((location == null) ? 0 : location.hashCode());
//        return result;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        SpatialNode other = (SpatialNode) obj;
//        if (location == null) {
//            if (other.location != null)
//                return false;
//        } else if (!location.equals(other.location))
//            return false;
//        return true;
//    }
//    
//    
//    
//}

package org.integratedmodelling.geoprocessing;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.Orientation;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;

@Component(id="org.integratedmodelling.geoprocessing", version=Version.CURRENT)
public class GeoprocessingComponent {

    public GeoprocessingComponent() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Util: return a list of all the cells that flow into the passed one.
     * 
     * @param cell
     * @param flowDirectionsD8
     * @return
     */
    public static List<Cell> getUpstreamCells(Cell cell, IState flowDirectionsD8) {
    	
    	List<Cell> ret = new ArrayList<>();

    	Cell neighbor = cell.getNeighbor(Orientation.NW);
    	if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 8) {
    		ret.add(neighbor);
    	}
    	neighbor = cell.getNeighbor(Orientation.N);
    	if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 7) {
    		ret.add(neighbor);
    	}
    	neighbor = cell.getNeighbor(Orientation.NE);
    	if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 6) {
    		ret.add(neighbor);
    	}
    	neighbor = cell.getNeighbor(Orientation.E);
    	if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 5) {
    		ret.add(neighbor);
    	}
    	neighbor = cell.getNeighbor(Orientation.SE);
    	if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 4) {
    		ret.add(neighbor);
    	}
    	neighbor = cell.getNeighbor(Orientation.S);
    	if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 3) {
    		ret.add(neighbor);
    	}
    	neighbor = cell.getNeighbor(Orientation.SW);
    	if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 2) {
    		ret.add(neighbor);
    	}
    	neighbor = cell.getNeighbor(Orientation.W);
    	if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 1) {
    		ret.add(neighbor);
    	}
    	return ret;
    }
    
}

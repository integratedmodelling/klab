package org.integratedmodelling.klab.contrib.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class MultiPoint extends Geometry {
    private final double[][] coordinates;
    private final double[] bbox;

    @JsonCreator
    public MultiPoint(@JsonProperty("coordinates") double [][] coordinates) {
        super();
        this.coordinates = coordinates;
        this.bbox = null;
    }

    public double[][] getCoordinates() {
        return coordinates;
    }

    public double[] getBbox() {
        return bbox;
    }
}

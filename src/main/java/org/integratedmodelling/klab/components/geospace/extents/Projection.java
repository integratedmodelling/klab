package org.integratedmodelling.klab.components.geospace.extents;

import org.integratedmodelling.klab.api.observations.scale.space.IProjection;

public class Projection implements IProjection {

    private String code;
    
    private Projection(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    /**
     * Obtain the projection corresponding to the passed EPSG (or other supported authority) code, in 
     * the format "EPSG:nnnn".
     * 
     * @param code
     * @return the projection, which may be invalid.
     */
    public static Projection fromCode(String code) {
        return new Projection(code);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Projection other = (Projection) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        return true;
    }
    
    
}

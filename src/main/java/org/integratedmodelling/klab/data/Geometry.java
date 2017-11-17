package org.integratedmodelling.klab.data;

import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;

public class Geometry implements IGeometry {

    private static final long serialVersionUID = 8430057200107796568L;

    public Geometry() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Parse a geometry from a KDL string.
     * 
     * @param string
     * @return
     */
    public static IGeometry parse(String string) {
        return null;
    }
    
    @Override
    public Iterator<IGeometry> iterator() {
        // TODO Auto-generated method stub
        
        return null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<IGeometry> getChildren() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IConcept> getDimensions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getDimensionCount(IConcept extent) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long[] getShape(IConcept extent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean[] isRegular(IConcept extent) {
        // TODO Auto-generated method stub
        return null;
    }

}

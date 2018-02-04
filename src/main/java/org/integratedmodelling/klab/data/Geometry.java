package org.integratedmodelling.klab.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.integratedmodelling.klab.api.data.IGeometry;

public class Geometry implements IGeometry {

    private static final long serialVersionUID = 8430057200107796568L;

    public static Geometry create(String geometry) {
        return makeGeometry(geometry, 0);
    }

    /*
     * dictionary for the IDs of any dimension types that are not space or time. 
     */
    private static Map<Character, Integer> dimDictionary = new HashMap<>();

    class DimensionImpl implements Dimension {

        private int     type;
        private boolean regular;
        private int     dimensionality;

        @Override
        public int getType() {
            return type;
        }

        @Override
        public boolean isRegular() {
            return regular;
        }

        @Override
        public int getDimensionality() {
            return dimensionality;
        }

    }

    private List<Dimension> dimensions  = new ArrayList<>();
    private Granularity     granularity = Granularity.SINGLE;
    private Geometry        child;

    @Override
    public Optional<IGeometry> getChild() {
        return child == null ? Optional.empty() : Optional.of(child);
    }

    @Override
    public List<Dimension> getDimensions() {
        return dimensions;
    }

    @Override
    public Granularity getGranularity() {
        return granularity;
    }

    private Geometry() {
    }

    /*
     * read the geometry defined starting at the i-th character
     */
    private static Geometry makeGeometry(String geometry, int i) {
        
        Geometry ret = new Geometry();
        for (int idx = i; idx < geometry.length(); idx++) {
            char c = geometry.charAt(idx);
            if (c == '*') {
                ret.granularity = Granularity.MULTIPLE;
            } else if (c >= 'A' && c <= 'z') {
                DimensionImpl dimensionality = ret.newDimension();
                if (c == 'S' || c == 's') {
                    dimensionality.type = SPACE;
                } else if (c == 'T' || c == 't') {
                    dimensionality.type = TIME;
                } else {
                    if (dimDictionary.containsKey(Character.toLowerCase(c))) {
                        dimensionality.type = dimDictionary.get(Character.toLowerCase(c));
                    } else {
                        int n = dimDictionary.size() + 2;
                        dimDictionary.put(Character.toLowerCase(c), n);
                        dimensionality.type = n;
                    }
                }
                
                dimensionality.regular = Character.isUpperCase(c);
                
                idx ++;
                if (geometry.charAt(idx) == '.') {
                    dimensionality.dimensionality = NONDIMENSIONAL;
                } else {
                    dimensionality.dimensionality = Integer.parseInt("" + geometry.charAt(idx));
                }
                
                ret.dimensions.add(dimensionality);

            } else if (c == ',') {
                ret.child = makeGeometry(geometry, idx + 1);
                break;
            }
        }
        return ret;
    }

    private DimensionImpl newDimension() {
        return new DimensionImpl();
    }
    
    
    public static void main(String[] args) {
        Geometry g1 = create("S2");
        Geometry g2 = create("*S2T1,*T1");
    }
}

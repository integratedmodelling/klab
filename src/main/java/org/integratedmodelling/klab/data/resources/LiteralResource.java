package org.integratedmodelling.klab.data.resources;

import java.util.HashMap;
import java.util.Map;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.raw.IRawObject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.Geometry;

public class LiteralResource extends AbstractResource {

    Object value;
    IGeometry geometry = Geometry.create("*");
    Map<String, Object> parameters = new HashMap<>();
    
    public LiteralResource(Object value) {
        super(value.toString());
        this.value = value;
    }

    private static final long serialVersionUID = 7943409920767158427L;

    @Override
    public String getAdapterType() {
        // TODO use a special adapter ID? Or just return a trivial adapter, given it must exist?
        return null;
    }

    @Override
    public IRawObject get(IScale scale, IMonitor monitor) {
        // TODO return the scalar value
        return null;
    }

    @Override
    public IGeometry getGeometry() {
        return geometry;
    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

}

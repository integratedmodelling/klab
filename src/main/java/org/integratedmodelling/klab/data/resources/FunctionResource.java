package org.integratedmodelling.klab.data.resources;

import java.util.Map;
import java.util.Optional;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.raw.IRawObject;
import org.integratedmodelling.klab.api.extensions.IPrototype;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;

public class FunctionResource extends AbstractResource {

    IKimFunctionCall functionCall;
    IGeometry geometry;
    
    public FunctionResource(String urn, IKimFunctionCall call) {
        super(urn);
        this.functionCall = call;
        IPrototype prototype = Extensions.INSTANCE.getServicePrototype(call.getName());
        if (prototype == null) {
            throw new KlabRuntimeException("function " + call.getName() + " is unknown");
        }
        this.geometry = prototype.getGeometry();
    }

    private static final long serialVersionUID = 7943409920767158427L;

    @Override
    public Optional<IResourceAdapter>  getAdapter() {
        // TODO use a special adapter ID? Or just return the adapter, given it must exist?
        return null;
    }

    @Override
    public IRawObject get(IScale scale, IMonitor monitor) {
        // TODO run the function
        return null;
    }

    @Override
    public IGeometry getGeometry() {
        return geometry;
    }

    @Override
    public Map<String, Object> getParameters() {
        return functionCall.getParameters();
    }

    @Override
    public IKimFunctionCall getComputation() {
      return functionCall;
    }

}

package org.integratedmodelling.klab.dataflow;

import java.util.List;

import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IPort;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;

public abstract class Actuator<T extends IArtifact> implements IActuator {

    String            name;
    String            namespace;
    Observable        newObservationType;
    String            newObservationUrn;
    Scale             scale;
    IKdlActuator.Type type;

    @Override
    public String getName() {
        return name;
    }

    public Actuator() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<IPort> getInputs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IPort> getOutputs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Scale getScale() {
        return scale;
    }

    public abstract T run(IMonitor monitor) throws KlabException;

    @SuppressWarnings("unchecked")
    public T compute(IMonitor monitor) throws KlabException {

        // TODO
        T ret = null;
        if (this.newObservationType != null) {
            ret = (T) Observations.INSTANCE
                    .createObservation(newObservationType, this.scale, this.namespace, monitor, null);
        }
        return ret;
    }

}

package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kdl.api.IKdlActuator.Type;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.ICountableObservation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow.Builder;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;

public class DataflowBuilder<T extends IArtifact> implements Builder {

    String                   name;
    Observable               newObservable;
    String                   newUrn;
    List<DataflowBuilder<?>> children = new ArrayList<>();
    Scale                    scale;
    IKdlActuator.Type        type;
    INamespace               namespace;
    Class<T>                 cls;

    public DataflowBuilder(String name, Class<T> type) {
        this.name = name;
        this.type = setType(type);
    }

    private Type setType(Class<T> type) {
        this.cls = type;
        // TODO Auto-generated method stub
        if (ICountableObservation.class.isAssignableFrom(type)) {
            return Type.OBJECT;
        } else if (IProcess.class.isAssignableFrom(type)) {
            return Type.PROCESS;
        } else if (!IState.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException("Cannot use " + type.getCanonicalName()
                    + " as the returned type for a k.LAB dataflow");
        }
        return null;
    }

    @Override
    public Builder instantiating(IObservable observable) {
        this.newObservable = (Observable) observable;
        if (IState.class.isAssignableFrom(this.cls)) {
            if (!observable.is(IKimConcept.Type.QUALITY)) {
                throw new IllegalArgumentException("Observable " + observable
                        + " is incompatible with the dataflow type");
            }
            switch (observable.getObservationType()) {
            case CLASSIFICATION:
                this.type = Type.CONCEPT;
                break;
            case QUANTIFICATION:
                this.type = Type.NUMBER;
                break;
            case VERIFICATION:
                this.type = Type.BOOLEAN;
                break;
            default:
                throw new IllegalArgumentException("Observable " + observable
                        + " is incompatible with the dataflow type");
            }
        } else if (!(observable.is(IKimConcept.Type.QUALITY) && this.type == Type.PROCESS)
                && !(observable.is(IKimConcept.Type.COUNTABLE) && this.type == Type.OBJECT)) {
            throw new IllegalArgumentException("Observable " + observable
                    + " is incompatible with the dataflow type");
        }
        return this;
    }

    @Override
    public Builder withScale(IScale scale) {
        this.scale = (Scale) scale;
        return this;
    }

    @Override
    public Builder add(String actuatorName, Class<? extends IArtifact> type) {
        @SuppressWarnings({ "rawtypes", "unchecked" })
        DataflowBuilder<?> builder = new DataflowBuilder(actuatorName, type);
        this.children.add(builder);
        return builder;
    }

    @Override
    public <K extends IArtifact> IDataflow<K> build() {
        Dataflow<K> ret = new Dataflow<K>();
        ret.name = this.name;
        ret.type = this.type;
        ret.scale = this.scale;
        ret.newObservationType = newObservable;
        ret.namespace = this.namespace == null ? null : this.namespace.getId();
        for (Builder builder : children) {
            ret.actuators.add(builder.build());
        }
        return ret;
    }

    @Override
    public Builder instantiating(IObservable observable, INamespace namespace) {
        this.namespace = namespace;
        return instantiating(observable);
    }

}

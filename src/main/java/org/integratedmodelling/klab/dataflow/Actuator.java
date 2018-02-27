package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;

public class Actuator<T extends IArtifact> implements IActuator {

    protected String               name;
    private String                 alias;
    private INamespace             namespace;
    private Observable             observable;
    private Scale                  scale;
    private IKdlActuator.Type      type;
    List<IActuator>                actuators           = new ArrayList<>();
    IMonitor                       monitor;
    Date                           creationTime        = new Date();
    private boolean                createObservation;

    /*
     * these are the specs from which the contextualizers are built: first the computation, then the
     * mediation. We keep them separated because the compiler needs to rearrange mediators and
     * references as needed. Then both get executed to produce the final list of contextualizers.
     */
    private List<IServiceCall> computationStrategy = new ArrayList<>();
    private List<IServiceCall> mediationStrategy   = new ArrayList<>();

    private Class<? extends T>     cls;

    @Override
    public String getName() {
        return name;
    }

    public Actuator(IMonitor monitor, Class<? extends T> cls) {
        this.cls = cls;
        this.monitor = monitor;
    }

    @Override
    public Scale getScale() {
        return scale;
    }

    @Override
    public List<IActuator> getActuators() {
        return actuators;
    }

    @Override
    public List<IActuator> getInputs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IActuator> getOutputs() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     * @param context
     * @param monitor
     * @return
     * @throws KlabException
     */
    @SuppressWarnings("unchecked")
    public T compute(DirectObservation context, IMonitor monitor) throws KlabException {

        // TODO
        T ret = null;
        if (this.isCreateObservation()) {
            ret = (T) Observations.INSTANCE.createObservation(getObservable(), this.getScale(), this
                    .getNamespace(), monitor, context);
        }

        return ret;
    }

    /**
     * Reconstruct or return the source code for this actuator.
     * 
     * @param offset
     * @return
     */
    protected String encode(int offset) {

        String ofs = StringUtils.repeat(" ", offset);
        String ret = ofs + getType().name().toLowerCase() + " "
                + (getObservable() == null ? getName() : getObservable().getLocalName());

        boolean hasBody = actuators.size() > 0 || getComputationStrategy().size() > 0
                || getMediationStrategy().size() > 0 || getObservable() != null;

        if (hasBody) {
            ret += encodeBody(offset, ofs);
        }

        return ret;

    }

    protected String encodeBody(int offset, String ofs) {
        String ret = " {\n";

        if (isCreateObservation()) {
            ret += ofs + "   " + "observe new " + getObservable().getDeclaration() + "\n";
        }

        for (IActuator actuator : actuators) {
            ret += ((Actuator<?>) actuator).encode(offset + 3) + "\n";
        }

        List<IServiceCall> computation = new ArrayList<>();
        computation.addAll(getComputationStrategy());
        computation.addAll(getMediationStrategy());

        for (int i = 0; i < computation.size(); i++) {
            ret += (i == 0 ? (ofs + "   compute") : ofs + "     ") + computation.get(i).getSourceCode()
                    + (i < computation.size() - 1 ? "," : "") + "\n";
        }
        ret += ofs + "}";

        if (getAlias() != null) {
            ret += " as " + getAlias();
        }

        if (getScale() != null && !getScale().isEmpty()) {
            List<IServiceCall> scaleSpecs = getScale().getKimSpecification();
            if (!scaleSpecs.isEmpty()) {
                ret += " over";
                for (int i = 0; i < scaleSpecs.size(); i++) {
                    ret += " " + scaleSpecs.get(i).getSourceCode()
                            + ((i < scaleSpecs.size() - 1) ? (",\n" + ofs + "      ") : "");
                }
            }
        }

        return ret;
    }

    public static <T extends IArtifact> Actuator<T> create(IMonitor monitor, Class<T> observationClass) {
        return new Actuator<T>(monitor, observationClass);
    }

    public Actuator<T> getReference() {
        return ActuatorReference.create(this, cls);
    }

    public List<IServiceCall> getComputationStrategy() {
        return computationStrategy;
    }

    public void setComputationStrategy(List<IServiceCall> computationStrategy) {
        this.computationStrategy = computationStrategy;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Observable getObservable() {
        return observable;
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
    }

    public INamespace getNamespace() {
        return namespace;
    }

    public void setNamespace(INamespace namespace) {
        this.namespace = namespace;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public IKdlActuator.Type getType() {
        return type;
    }

    public void setType(IKdlActuator.Type type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IServiceCall> getMediationStrategy() {
        return mediationStrategy;
    }

    public void setMediationStrategy(List<IServiceCall> mediationStrategy) {
        this.mediationStrategy = mediationStrategy;
    }

    public boolean isCreateObservation() {
        return createObservation;
    }

    public void setCreateObservation(boolean createObservation) {
        this.createObservation = createObservation;
    }

}

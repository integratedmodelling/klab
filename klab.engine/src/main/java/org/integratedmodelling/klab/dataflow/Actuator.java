package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.ObservedArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Coverage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.collections.Collections;

public class Actuator implements IActuator {

  protected String                  name;
  private String                    alias;
  private INamespace                namespace;
  private Observable                observable;
  protected Coverage                coverage;
  private IKdlActuator.Type         type;
  List<IActuator>                   actuators         = new ArrayList<>();
  IMonitor                          monitor;
  Date                              creationTime      = new Date();
  private boolean                   createsObservation;
  private boolean                   reference;
  private boolean                   exported;

  // this is only for the API
  private List<IComputableResource> computedResources = new ArrayList<>();
  // we store the annotations from the model to enable probes or other
  // non-semantic options
  private List<IKimAnnotation>      annotations       = new ArrayList<>();

  public void addComputation(IComputableResource resource) {
    computedResources.add(resource);
    IServiceCall serviceCall = Klab.INSTANCE.getRuntimeProvider().getServiceCall(resource);
    computationStrategy.add(new Pair<>(serviceCall, resource));
  }

  public void addMediation(IComputableResource resource, Actuator target) {
    ((ComputableResource) resource)
        .setTarget(target.getAlias() == null ? target.getName() : target.getAlias());
    ((ComputableResource) resource).setMediation(true);
    computedResources.add(resource);
    IServiceCall serviceCall = Klab.INSTANCE.getRuntimeProvider().getServiceCall(resource);
    mediationStrategy.add(new Pair<>(serviceCall, resource));
  }

  /**
   * the specs from which the contextualizers are built: first the computation, then the mediation.
   * We keep them separated because the compiler needs to rearrange mediators and references as
   * needed. Then both get executed to produce the final list of contextualizers.
   * 
   * Each list contains a service call and its local target name, null for the main observable.
   */
  private List<Pair<IServiceCall, IComputableResource>> computationStrategy = new ArrayList<>();
  private List<Pair<IServiceCall, IComputableResource>> mediationStrategy   = new ArrayList<>();

  private boolean                                       definesScale;

  @Override
  public String getName() {
    return name;
  }

  public Actuator(IMonitor monitor) {
    this.monitor = monitor;
  }

  @Override
  public List<IActuator> getActuators() {
    return actuators;
  }

  @Override
  public List<IActuator> getInputs() {
    List<IActuator> ret = new ArrayList<>();
    for (IActuator actuator : actuators) {
      if (((Actuator) actuator).isReference()) {
        ret.add(actuator);
      }
    }
    return ret;
  }

  @Override
  public List<IActuator> getOutputs() {
    List<IActuator> ret = new ArrayList<>();
    for (IActuator actuator : actuators) {
      if (((Actuator) actuator).exported) {
        ret.add(actuator);
      }
    }
    return ret;
  }

  /**
   * Compute the actuator.
   * 
   * @param target the artifact being computed. If this actuator handles an instantiation, the
   *        passed target is null and will be set to the first object in the result chain.
   * @param runtimeContext the runtime context
   * @return the finalized observation data. TODO when an instantiator returns no instances, should
   *         return an empty observation. Currently it returns null.
   * @throws KlabException
   */
  @SuppressWarnings("unchecked")
  public IArtifact compute(IArtifact target, IRuntimeContext runtimeContext) throws KlabException {

    /**
     * The contextualizer chain that implements the computation specified by IServiceCalls. These
     * may be first-class resolvers/instantiators or mediators, in order of execution.
     */
    List<Pair<IContextualizer, IComputableResource>> computation = new ArrayList<>();

    // localize names to this actuator's expectations; create non-semantic storage
    // if needed
    IRuntimeContext ctx = setupContext(target, runtimeContext, ITime.INITIALIZATION);
    for (Pair<IServiceCall, IComputableResource> service : Collections.join(computationStrategy,
        mediationStrategy)) {
      Object contextualizer = Extensions.INSTANCE.callFunction(service.getFirst(), ctx);
      if (!(contextualizer instanceof IContextualizer)) {
        throw new KlabValidationException(
            "function " + service.getFirst().getName() + " does not produce a contextualizer");
      }
      computation.add(new Pair<>((IContextualizer) contextualizer, service.getSecond()));
    }

    // this will be null if the actuator is for an instantiator
    IArtifact ret = target;

    // run it
    for (Pair<IContextualizer, IComputableResource> contextualizer : computation) {

      if (contextualizer.getFirst() instanceof IStateResolver) {
        /*
         * pass the distributed computation to the runtime provider for possible parallelization
         * instead of hard-coding a loop here.
         */
        ret = Klab.INSTANCE.getRuntimeProvider().distributeComputation(
            (IStateResolver) contextualizer.getFirst(), (IState) ret,
            addParameters(ctx, contextualizer.getSecond()),
            runtimeContext.getScale().at(ITime.INITIALIZATION));

      } else if (contextualizer.getFirst() instanceof IResolver) {
        ret = ((IResolver<IArtifact>) contextualizer.getFirst()).resolve(ret,
            addParameters(ctx, contextualizer.getSecond()));
      } else if (contextualizer.getFirst() instanceof IInstantiator) {
        for (IObjectArtifact object : ((IInstantiator) contextualizer.getFirst())
            .instantiate(this.observable, addParameters(ctx, contextualizer.getSecond()))) {
          if (ret == null) {
            ret = object;
          } else {
            ((ObservedArtifact) ret).chain(object);
          }
        }
        if (ret == null) {
          // return an empty observation for this observable, so we know we made the
          // observation.
          ret = Klab.INSTANCE.getRuntimeProvider().createEmptyObservation(this.observable,
              ctx.getScale());
        }
      }
    }

    // should never happen
    assert (ret != null);

    ctx.getProvenance().addArtifact(ret);

    /*
     * when computation is finished, pass the annotations to the context so it can decide what to do
     * with them.
     */
    for (IKimAnnotation annotation : annotations) {
      ctx.processAnnotation(annotation);
    }

    return ret;
  }

  /**
   * Set the call parameters, if any, into the context data so that they can be found by the
   * contextualizer.
   * 
   * @param ctx
   * @param second
   * @return
   */
  private IRuntimeContext addParameters(IRuntimeContext ctx, IComputableResource resource) {
    for (String name : resource.getParameters().keySet()) {
      ctx.set(name, resource.getParameters().get(name));
    }
    return ctx;
  }

  private IRuntimeContext setupContext(IArtifact target, IRuntimeContext runtimeContext,
      ILocator locator) {

    IRuntimeContext ret = runtimeContext.copy();
    ret.setTarget(target);
    ret.setScale(ret.getScale().at(locator));
    for (IActuator input : getInputs()) {
      if (ret.getArtifact(input.getName()) != null) {
        ret.rename(input.getName(), input.getAlias());
      }
    }
    return ret;
  }

  public String toString() {
    return "<" + getName()
        + ((getAlias() != null && !getAlias().equals(getName())) ? " as " + getAlias() : "") + " ["
        + (computationStrategy.size() + mediationStrategy.size()) + "]>";
  }

  /**
   * Reconstruct or return the source code for this actuator.
   * 
   * @param offset
   * @return
   */
  protected String encode(int offset) {
    String ofs = StringUtils.repeat(" ", offset);
    return ofs + (reference ? "import " : "") + getType().name().toLowerCase() + " " + getName()
        + encodeBody(offset, ofs);
  }

  protected String encodeBody(int offset, String ofs) {

    boolean hasBody = actuators.size() > 0 || computationStrategy.size() > 0
        || mediationStrategy.size() > 0 || createsObservation;

    String ret = "";

    if (hasBody) {

      ret = " {\n";

      for (IActuator actuator : actuators) {
        ret += ((Actuator) actuator).encode(offset + 3) + "\n";
      }

      int cout = mediationStrategy.size() + computationStrategy.size();
      int nout = 0;
      for (int i = 0; i < mediationStrategy.size(); i++) {
        ret += (nout == 0 ? (ofs + "   compute" + (cout < 2 ? " " : ("\n" + ofs + "     ")))
            : ofs + "     ")
            + (mediationStrategy.get(i).getSecond().getTarget() == null ? ""
                : (mediationStrategy.get(i).getSecond().getTarget() + " >> "))
            + mediationStrategy.get(i).getFirst().getSourceCode()
            + (nout < mediationStrategy.size() - 1 || computationStrategy.size() > 0 ? "," : "")
            + "\n";
        nout++;
      }
      for (int i = 0; i < computationStrategy.size(); i++) {
        ret += (nout == 0 ? (ofs + "   compute" + (cout < 2 ? " " : ("\n" + ofs + "      ")))
            : ofs + "     ")
            + computationStrategy.get(i).getFirst().getSourceCode()
            + (computationStrategy.get(i).getSecond().getTarget() == null ? ""
                : (" as " + computationStrategy.get(i).getSecond().getTarget()))
            + (nout < computationStrategy.size() - 1 ? "," : "") + "\n";
        nout++;
      }

      // UNCOMMENT TO OUTPUT SEMANTICS
      // if (observable != null) {
      // ret += ofs + " " + "semantics " + getObservable().getDeclaration() + "\n";
      // }

      ret += ofs + "}";
    }

    if (getAlias() != null && !getAlias().equals(getName())) {
      ret += " as " + getAlias();
    }

    if (definesScale && coverage != null && !coverage.isEmpty()) {
      List<IServiceCall> scaleSpecs = ((Scale) coverage).getKimSpecification();
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

  public static Actuator create(IMonitor monitor) {
    return new Actuator(monitor);
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

  public IKdlActuator.Type getType() {
    return type;
  }

  public void setType(IKdlActuator.Type type) {
    this.type = type;
  }

  public void setName(String name) {
    this.name = name;
  }

  // public boolean isCreateObservation() {
  // return createObservation;
  // }
  //
  public void setCreateObservation(boolean createObservation) {
    this.createsObservation = createObservation;
  }

  public void setReference(boolean reference) {
    this.reference = reference;
  }

  public boolean isReference() {
    return reference;
  }

  @Override
  public boolean isComputed() {
    return computationStrategy.size() > 0;
  }

  @Override
  public List<IComputableResource> getComputation() {
    return computedResources;
  }

  public void setDefinesScale(boolean definesScale) {
    this.definesScale = definesScale;
  }

  public boolean isStorageScalar(IScale scale) {
    // TODO inspect the computations and check if we have any local modifications
    return scale.size() == 1;
  }

  public boolean isStorageDynamic(IScale scale) {
    // TODO inspect the computations and the observable semantics; check if we have
    // any temporal
    // modifications
    return scale.isTemporallyDistributed();
  }

  public boolean isExported() {
    return exported;
  }

  public List<IKimAnnotation> getAnnotations() {
    return annotations;
  }

  // coverage in an actuator is only set when it covers a sub-scale compared to
  // that of resolution.
  // The same field is used in a dataflow to define the overall coverage.
  public void setCoverage(Coverage coverage) {
    this.coverage = coverage;
  }
}

package org.integratedmodelling.klab.resolution;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimStatement.Scope;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.model.Contextualization;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ObservationStrategy.Strategy;
import org.integratedmodelling.klab.rest.ModelReference;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.StringUtils;

/**
 * FIXME consider removing and letting a copy of Model be ranked - using the delegate has side
 * effects and requires ugly code when derived models are created. Also any new method that is not
 * explicitly overridden will operate from the wrong object.
 * 
 * @author Ferd
 */
public class RankedModel extends Model implements IRankedModel {

    String modelUrn;
    Model delegate;
    Map<String, Double> ranks;
    int priority = 0;

    private transient ModelReference modelData;

    public static RankedModel create(IRankedModel model, IModel delegate) {
        return new RankedModel((Model) delegate, model.getRanks(), model.getPriority());
    }

    public RankedModel(Model model) {
        this.delegate = model;
        this.modelUrn = model.getName();
        this.ranks = new HashMap<>();
    }

    public RankedModel(ModelReference model, Map<String, Double> ranks, int priority) {
        this.modelUrn = model.getUrn();
        this.modelData = model;
        this.ranks = ranks;
        this.priority = priority;
    }

    private RankedModel(Model model, Map<String, Double> ranks, int priority) {
        this.delegate = model;
        this.modelUrn = model.getName();
        this.ranks = ranks;
        this.priority = priority;
    }

    public Model getDelegate() {

        if (delegate == null) {

            /**
             * TODO handle dereification through modelData
             */

            IKimObject m = Resources.INSTANCE.getModelObject(modelUrn);
            if (m instanceof Model) {
                delegate = (Model) m;
            } else {
                throw new KlabInternalErrorException("cannot locate model resulting from kbox query: " + modelUrn);
            }
        }
        return delegate;
    }

    public RankedModel withPriority(int priority) {
    	this.priority = priority;
    	return this;
    }
    
    /**
     * Return the underlying model data. These may be ranked again in a different scale.
     * 
     * @return
     */
    public ModelReference getModelData() {
        return modelData;
    }

    public List<IKimObject> getChildren() {
        return getDelegate().getChildren();
    }

    public IKimModel getStatement() {
        return getDelegate().getStatement();
    }

    public List<IAnnotation> getAnnotations() {
        return getDelegate().getAnnotations();
    }

    public IMetadata getMetadata() {
        return getDelegate().getMetadata();
    }

    public boolean isDeprecated() {
        return getDelegate().isDeprecated();
    }

    public int hashCode() {
        return getDelegate().hashCode();
    }

    public void setDeprecated(boolean deprecated) {
        getDelegate().setDeprecated(deprecated);
    }

    public boolean equals(Object obj) {
        return getDelegate().equals(obj);
    }

    public List<IObservable> getObservables() {
        return getDelegate().getObservables();
    }

    public Map<String, IObservable> getAttributeObservables() {
        return getDelegate().getAttributeObservables();
    }

    public String getLocalNameFor(IObservable observable) {
        return getDelegate().getLocalNameFor(observable);
    }

    public boolean isResolved() {
        return getDelegate().isResolved();
    }

    public boolean isInstantiator() {
        return getDelegate().isInstantiator();
    }

    public boolean isReinterpreter() {
        return getDelegate().isReinterpreter();
    }

    public boolean isAvailable() {
        return getDelegate().isAvailable();
    }

    public Collection<IDocumentation> getDocumentation() {
        return getDelegate().getDocumentation();
    }

    public String getId() {
        return getDelegate().getId();
    }

    public String getName() {
        return getDelegate().getName();
    }

    public INamespace getNamespace() {
        return getDelegate().getNamespace();
    }

    public List<IObservable> getDependencies() {
        return getDelegate().getDependencies();
    }

    public void setDependencies(List<IObservable> dependencies) {
        getDelegate().setDependencies(dependencies);
    }

    public void setObservables(List<IObservable> observables) {
        getDelegate().setObservables(observables);
    }

    public void setAttributeObservables(Map<String, IObservable> attributeObservables) {
        getDelegate().setAttributeObservables(attributeObservables);
    }

    public void setNamespace(Namespace namespace) {
        getDelegate().setNamespace(namespace);
    }

    public Contextualization getContextualization() {
        return getDelegate().getContextualization();
    }

    public Scope getScope() {
        return getDelegate().getScope();
    }

    public Scale getCoverage(IMonitor monitor) throws KlabException {
        return getDelegate().getCoverage(monitor);
    }

    public String toString() {
        return getDelegate().toString()/* + "\n\n" + describeRanks() */;
    }

    public String describeRanks() {
        return describeRanks(0, 3);
    }

    private String describeRanks(int indent, int offset) {

        String ret = "";
        String filler = StringUtil.spaces(indent);

        ret += filler + StringUtils.rightPad(offset + ".", 4) + modelData.getName() + " ["
                + (modelData.getServerId() == null ? "local" : modelData.getServerId()) + "]\n";
        for (String s : ranks.keySet()) {
            ret += filler + "  " + StringUtils.rightPad(s, 25) + " " + ranks.get(s) + "\n";
        }

        return ret;
    }

    @Override
    public ICoverage getContextCoverage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Double> getRanks() {
        return ranks;
    }

    @Override
    public Observable getCompatibleOutput(Observable observable, IConcept context, IMonitor monitor) {
        return getDelegate().getCompatibleOutput(observable, context, monitor);
    }

    @Override
    public List<IContextualizable> getComputation() {
        return getDelegate().getComputation();
    }

    @Override
    public Observable getCompatibleInput(Observable observable) {
        return getDelegate().getCompatibleInput(observable);
    }

    @Override
    public List<IContextualizable> getResources() {
        return getDelegate().getResources();
    }

    @Override
    public IScale getNativeCoverage() {
        try {
            return getDelegate().getCoverage(Klab.INSTANCE.getRootMonitor());
        } catch (KlabException e) {
            Klab.INSTANCE.getRootMonitor().error(e);
        }
        return null;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean isDerived() {
        return delegate.isDerived();
    }

    @Override
    public Strategy getObservationStrategy() {
        return delegate.getObservationStrategy();
    }

    @Override
    public boolean isKnownDependency(String targetId) {
        return delegate.isKnownDependency(targetId);
    }

    @Override
    public boolean isKnownObservable(String targetId) {
        return delegate.isKnownObservable(targetId);
    }

    @Override
    public IObservable findDependency(String name) {
        return delegate.findDependency(name);
    }

    @Override
    public IObservable findDependency(IConcept concept) {
        return delegate.findDependency(concept);
    }

    @Override
    public IObservable findDependency(IObservable concept) {
        return delegate.findDependency(concept);
    }

    @Override
    public IObservable findOutput(IObservable concept) {
        return delegate.findOutput(concept);
    }

    @Override
    public IObservable findOutput(IConcept concept) {
        return delegate.findOutput(concept);
    }

    @Override
    public boolean isInactive() {
        return delegate.isInactive();
    }

    @Override
    public void setInactive(boolean inactive) {
        delegate.setInactive(inactive);
    }

    @Override
    public boolean isSemantic() {
        return delegate.isSemantic();
    }

    @Override
    public boolean isLearning() {
        return delegate.isLearning();
    }

    @Override
    public IGeometry getGeometry() {
        return delegate.getGeometry();
    }

    @Override
    public List<IObservable> getArchetypes() {
        return delegate.getArchetypes();
    }

    @Override
    public List<IObservable> getPredictors() {
        return delegate.getPredictors();
    }

    @Override
    public boolean learnsWithinArchetype() {
        return delegate.learnsWithinArchetype();
    }

    @Override
    public IObservable getArchetype() {
        return delegate.getArchetype();
    }

    @Override
    public boolean distributesLearning() {
        return delegate.distributesLearning();
    }

    @Override
    public boolean changesIn(Type dimension, IScale scale) {
        return delegate.changesIn(dimension, scale);
    }

    @Override
    public MergedResource getMergedResource() {
        return delegate.getMergedResource();
    }

    @Override
    public IViewModel getViewModel() {
        // TODO Auto-generated method stub
        return delegate.getViewModel();
    }

    @Override
    public Collection<IConcept> getRequiredTraits() {
        return delegate.getRequiredTraits();
    }

    @Override
    public boolean isAbstract() {
        return delegate.isAbstract();
    }

    @Override
    public Collection<IConcept> getAbstractTraits() {
        return delegate.getAbstractTraits();
    }

    @Override
    public org.integratedmodelling.klab.rest.DocumentationNode.Model getBean() {
        return delegate.getBean();
    }

    @Override
    public IObservable getObservableFor(String localName) {
        return delegate.getObservableFor(localName);
    }

}

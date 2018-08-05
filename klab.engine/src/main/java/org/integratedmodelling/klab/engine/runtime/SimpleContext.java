package org.integratedmodelling.klab.engine.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.IConfigurationDetector;
import org.integratedmodelling.klab.api.runtime.IEventBus;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CamelCase;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Trivial context that will only build simple hierarchies and observations.
 * Meant for quick visualization of non-semantic structures so far. May need to
 * be completed with all its parts to load previously contextualized
 * observations with relationships and other complex interactions. Borrows
 * observations from the default runtime.
 * 
 * @author ferdinando.villa
 *
 */
public class SimpleContext extends Parameters<String> implements IRuntimeContext {

    INamespace                    namespace  = null;
    IObservable                   observable = null;
    IScale                        scale      = null;
    IObservation                  target     = null;
    IMonitor                      monitor;
    SimpleContext                 parent;

    // these are shared among all children, created in root only and passed around
    Map<String, IArtifact>        artifacts;
    Map<String, IObservation>     observations;
    Graph<IArtifact, DefaultEdge> structure;
    ISubject                      rootSubject;

    /**
     * Root context. Don't use for any of the child ones.
     * 
     * @param observable
     * @param scale
     * @param monitor
     */
    public SimpleContext(IObservable observable, IScale scale, IMonitor monitor) {
        this.observable = observable;
        this.scale = scale;
        this.structure = new DefaultDirectedGraph<>(DefaultEdge.class);
        this.artifacts = new HashMap<>();
        this.observations = new HashMap<>();
        this.namespace = Namespaces.INSTANCE.getNamespace(observable.getType().getNamespace());
        this.monitor = monitor;
        this.target = this.rootSubject = new Subject(observable
                .getLocalName(), (Observable) observable, (Scale) scale, this);
        this.structure.addVertex(this.target);
        this.artifacts.put(this.getTargetName(), this.target);
        this.observations.put(this.target.getId(), this.target);
    }

    public SimpleContext(SimpleContext parent) {
        this.scale = parent.scale;
        this.structure = parent.structure;
        this.monitor = parent.monitor;
        this.namespace = parent.namespace;
        this.artifacts = parent.artifacts;
        this.observations = parent.observations;
        this.rootSubject = parent.rootSubject;
    }

    @Override
    public INamespace getNamespace() {
        return namespace;
    }

    @Override
    public IEventBus getEventBus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IScheduler<?> getScheduler() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IRelationship> getOutgoingRelationships(ISubject observation) {
        return new ArrayList<>();
    }

    @Override
    public Collection<IRelationship> getIncomingRelationships(ISubject observation) {
        return new ArrayList<>();
    }

    @Override
    public IArtifact getTargetArtifact() {
        return target;
    }

    @Override
    public IArtifact getArtifact(String localName) {
        return artifacts.get(localName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IArtifact> T getArtifact(String localName, Class<T> cls) {
        IArtifact ret = getArtifact(localName);
        if (ret != null && cls.isAssignableFrom(ret.getClass())) {
            return (T) ret;
        }
        return null;
    }

    @Override
    public <T extends IArtifact> Collection<Pair<String, T>> getArtifacts(Class<T> type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IMonitor getMonitor() {
        return monitor;
    }

    @Override
    public Type getArtifactType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IScale getScale() {
        return scale;
    }

    @Override
    public Collection<String> getInputs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> getOutputs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IObservable getSemantics(String identifier) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IObjectArtifact newObservation(IObservable observable, String name, IScale scale)
            throws KlabException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IObjectArtifact newRelationship(IObservable observable, String name, IScale scale, IObjectArtifact source, IObjectArtifact target) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IObservable getTargetSemantics() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTargetName() {
        return target instanceof IDirectObservation ? ((IDirectObservation) target).getName()
                : target.getObservable().getLocalName();
    }

    @Override
    public ISubject getSourceSubject(IRelationship relationship) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ISubject getTargetSubject(IRelationship relationship) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IDirectObservation getContextObservation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IDirectObservation getParentOf(IObservation observation) {
        for (DefaultEdge edge : this.structure.outgoingEdgesOf(observation)) {
            IArtifact source = this.structure.getEdgeTarget(edge);
            if (source instanceof IDirectObservation) {
                return (IDirectObservation) source;
            }
        }
        return null;
    }

    @Override
    public Collection<IObservation> getChildrenOf(IObservation observation) {
        return getChildren(observation, IObservation.class);
    }

    @Override
    public IRuntimeContext createChild(IScale scale, IActuator target, IResolutionScope scope, IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setData(String name, IArtifact data) {
        // TODO Auto-generated method stub

    }

    @Override
    public void set(String name, Object value) {
        // TODO Auto-generated method stub

    }

    @Override
    public IConfigurationDetector getConfigurationDetector() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRuntimeContext copy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void rename(String name, String alias) {
        // TODO Auto-generated method stub

    }

    @Override
    public void exportNetwork(String outFile) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setTarget(IArtifact target) {
        this.target = (IObservation) target;
    }

    @Override
    public void setScale(IScale geometry) {
        this.scale = geometry;
    }

    @Override
    public void processAnnotation(IAnnotation annotation) {
        // TODO Auto-generated method stub

    }

    @Override
    public Provenance getProvenance() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Graph<? extends IArtifact, ?> getStructure() {
        return structure;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IArtifact> Collection<T> getChildren(IArtifact artifact, Class<T> cls) {
        List<T> ret = new ArrayList<>();
        for (DefaultEdge edge : this.structure.incomingEdgesOf(artifact)) {
            IArtifact source = this.structure.getEdgeSource(edge);
            if (cls.isAssignableFrom(source.getClass())) {
                ret.add((T) source);
            }
        }
        return ret;
    }

    @Override
    public void link(IArtifact parent, IArtifact child) {
        this.structure.addVertex(child);
        this.structure.addEdge(child, parent);
    }

    /**
     * Return a child context that can be used to build the observation of the passed resource
     * in our scale. If the observable is null, create a non-semantic observable.
     * 
     * @param resource
     * @return
     */
    public SimpleContext getChild(IObservable observable, IResource resource) {
        if (resource.getType() == IArtifact.Type.OBJECT) {
            return this;
        }
        if (observable == null) {
            IConcept concept = OWL.INSTANCE.getNonsemanticPeer(observable == null
                    ? CamelCase.toUpperCamelCase(resource.getLocalName().replaceAll("\\-", "_"), '_')
                    : observable.getLocalName(), resource.getType());
            observable = Observable.promote(concept);
        }
        SimpleContext ret = new SimpleContext(this);
        IDataArtifact data = Klab.INSTANCE.getStorageProvider()
                .createStorage(resource.getType(), getScale(), this);
        ret.target = new State((Observable) observable, (Scale) scale, this, data);
        structure.addVertex(ret.target);
        structure.addEdge(ret.target, this.target);
        artifacts.put(observable.getLocalName(), ret.target);
        observations.put(ret.target.getId(), ret.target);
        return ret;
    }

    @Override
    public ISubject getRootSubject() {
        return rootSubject;
    }

    @Override
    public IObservation getObservation(String observationId) {
        return observations.get(observationId);
    }

}

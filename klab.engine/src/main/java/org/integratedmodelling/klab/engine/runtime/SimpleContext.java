package org.integratedmodelling.klab.engine.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
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
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Trivial context that will only build simple hierarchies and observations.
 * Meant for quick visualization of non-semantic structures so far. May need to
 * be completed with all its parts to load previously contextualized
 * observations with relationships and other complex interactions.
 * 
 * @author ferdinando.villa
 *
 */
public class SimpleContext implements IRuntimeContext {

	INamespace namespace = null;
	IObservable observable = null;
	IScale scale = null;
	IObservation target = null;
	Graph<IArtifact, DefaultEdge> structure;

	public SimpleContext(IObservable observable, IScale scale) {
		this.observable = observable;
		this.scale = scale;
		this.structure = new DefaultDirectedGraph<>(DefaultEdge.class);
		this.namespace = Namespaces.INSTANCE.getNamespace(observable.getType().getNamespace());
		this.target = new Subject(observable.getLocalName(), (Observable) observable, (Scale) scale, this);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IArtifact> T getArtifact(String localName, Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IArtifact> Collection<Pair<String, T>> getArtifacts(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMonitor getMonitor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getArtifactType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IScale getScale() {
		// TODO Auto-generated method stub
		return null;
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
	public IObjectArtifact newObservation(IObservable observable, String name, IScale scale) throws KlabException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IObjectArtifact newRelationship(IObservable observable, String name, IScale scale, IObjectArtifact source,
			IObjectArtifact target) {
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IObservation> getChildrenOf(IObservation observation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> K get(String name, Class<? extends K> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> K get(String name, K defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(String key, Class<?> cls) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsKey(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object put(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object remove(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISubject getRootSubject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IObservation getObservation(String observationId) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void setScale(IScale geometry) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IArtifact> Collection<T> getChildren(IArtifact artifact, Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void link(IArtifact parent, IArtifact child) {
		this.structure.addVertex(child);
		this.structure.addEdge(child, parent);
	}

}

package org.integratedmodelling.klab.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimStatement.Scope;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimNamespace;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Ontologies;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.api.knowledge.IAxiom;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Ontology;
import org.integratedmodelling.klab.scale.Scale;

public class Namespace extends KimObject implements INamespace {

	private String name;
	private Ontology ontology;
	private IProject project;
	private boolean internal = false;
	private boolean canonical = false;
	private boolean scenario = false;
	private boolean inactive = false;
	private long timestamp = 0l;
	private IConcept domain;

	List<IKimObject> objects = Collections.synchronizedList(new ArrayList<>());
	Map<String, IKimObject> objectsByName = Collections.synchronizedMap(new HashMap<>());
	Map<String, Object> symbolTable = Collections.synchronizedMap(new HashMap<>());
	List<IServiceCall> coveredExtents = new ArrayList<>();
	Map<String, IViewModel> knowledgeViews = Collections.synchronizedMap(new HashMap<>());

	/*
	 * for incremental building of the knowledge
	 */
	List<IAxiom> axioms = new ArrayList<>();
	private boolean publishable = true;
	private Scale coverage;
	private Scope scope = Scope.PUBLIC;

	public Namespace(IKimNamespace namespace) {
		super((KimNamespace) namespace);
		this.name = namespace.getName();
		this.scope = namespace.getScope();
		this.inactive = namespace.isInactive();
		this.scenario = namespace.isScenario();
		if (namespace.getDomain() != null) {
			this.domain = Concepts.INSTANCE.declare(namespace.getDomain());
		}
		this.ontology = Ontologies.INSTANCE.require(name);
		if (namespace.getProject() != null) {
			this.project = Resources.INSTANCE.retrieveOrCreate(namespace.getProject());
		}
		this.timestamp = namespace.getTimestamp();
		if (this.timestamp == 0) {
			this.timestamp = System.currentTimeMillis();
		}
		for (IServiceCall extent : namespace.getExtents()) {
			coveredExtents.add(extent);
		}
		setDeprecated(namespace.isDeprecated());
	}

	/*
	 * This is ONLY for OWL namespaces
	 */
	public Namespace(String id, @Nullable File file, Ontology ontology) {
		super(null);
		setStatement(new KimNamespace(id, file));
		this.name = id;
		this.ontology = ontology;
		this.internal = ontology.isInternal();
		this.timestamp = file == null ? System.currentTimeMillis() : file.lastModified();
	}

	public void addAxiom(IAxiom axiom) {
		this.axioms.add(axiom);
	}

	public void define() {
		this.ontology.define(this.axioms);
		this.axioms.clear();
	}

	@Override
	public IKimNamespace getStatement() {
		return (IKimNamespace) super.getStatement();
	}

	@Override
	public long getTimeStamp() {
		return timestamp;
	}

	@Override
	public IConcept getDomain() {
		return domain;
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public Collection<INamespace> getImportedNamespaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getTrainingNamespaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLookupNamespaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scale getCoverage(IMonitor monitor) {

		if (this.coverage == null) {
			if (getStatement().getExtents().size() == 0) {
				return null;
			}
			List<IExtent> extents = new ArrayList<>();
			for (IServiceCall extentFunction : getStatement().getExtents()) {
				Object extent = Extensions.INSTANCE.callFunction(extentFunction, monitor);
				if (!(extent instanceof IExtent)) {
					throw new KlabValidationException(
							"function " + extentFunction + " does not produce a valid extent");
				}
				extents.add((IExtent) extent);
			}
			this.coverage = Scale.create(extents);
		}
		return this.coverage;
	}

	@Override
	public boolean hasErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasWarnings() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<ICompileNotification> getCodeAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ontology getOntology() {
		return ontology;
	}

	@Override
	public Map<String, Object> getSymbolTable() {
		return symbolTable;
	}

	@Override
	public boolean isScenario() {
		return scenario;
	}

	@Override
	public IMetadata getResolutionCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getDisjointNamespaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getLocalFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scope getScope() {
		return this.scope;
	}

	@Override
	public boolean isInactive() {
		return inactive;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDocumentation getDocumentation() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInternal(boolean b) {
		this.internal = b;
	}

	@Override
	public boolean isInternal() {
		return this.internal;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<IKimObject> getObjects() {
		return objects;
	}

	/**
	 * Add each top-level object to the object list, and index it and all children
	 * by name.
	 * 
	 * @param object
	 */
	public void addObject(IKimObject object) {
		indexObject(object);
		this.objects.add(object);
	}

	private void indexObject(IKimObject object) {
		this.objectsByName.put(object.getId(), object);
		for (IKimObject child : object.getChildren()) {
			addObject(child);
		}
	}

	@Override
	public List<IKimObject> getAllObjects() {
		List<IKimObject> ret = new ArrayList<>();
		for (IKimObject object : objects) {
			addObjectToList(object, ret);
		}
		return ret;
	}

	private void addObjectToList(IKimObject object, List<IKimObject> ret) {
		ret.add(object);
		for (IKimObject child : object.getChildren()) {
			addObjectToList(child, ret);
		}
	}

	@Override
	public IKimObject getObject(String id) {
		return objectsByName.get(id);
	}

	@Override
	public boolean isCanonical() {
		return canonical;
	}

	public String toString() {
		return "[NS " + getName() + " (" + getObjects().size() + " objects)]";
	}

	@Override
	public String getId() {
		return name;
	}

	@Override
	public boolean isProjectKnowledge() {
		return getStatement().getFile().toString().endsWith("META-INF" + File.separator + "knowledge.kim");
	}

	@Override
	public boolean isPublishable() {
		return publishable;
	}

	@Override
	public INamespace getNamespace() {
		return this;
	}

	public void setPublishable(boolean b) {
		this.publishable = b;
	}

	@Override
	public Map<String, IViewModel> getKnowledgeViews() {
		return knowledgeViews;
	}

}

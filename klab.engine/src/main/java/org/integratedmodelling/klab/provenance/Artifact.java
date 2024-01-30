package org.integratedmodelling.klab.provenance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.data.Metadata;

import com.google.common.collect.Lists;

import groovy.lang.GroovyObjectSupport;
import groovy.transform.CompileStatic;

/**
 * All the provenance-related functions of IArtifact. Can be used as delegate
 * for those within any runtime provider that produces artifacts.
 * <p>
 * Artifacts in provenance graphs can also represent externally available
 * resources, expressing the lineage of the information retrieved through URNs.
 * The history is reconstructed based on the resource's metadata.
 * 
 * @author ferdinando.villa
 *
 */
@CompileStatic
public abstract class Artifact extends GroovyObjectSupport implements IArtifact {

	List<IAnnotation> annotations = new ArrayList<>();
	private List<IActivity> activities = new ArrayList<>();
	@Deprecated
	private Map<Class<?>, Object> peers = new HashMap<>();
	private boolean archetype;
	protected Metadata metadata = new Metadata();
	
	/*
	 * all observation data in a group share the same list; the pre-build object is
	 * thrown away at chain(). Saving the constructor call is not worth the
	 * additional logics.
	 */
	boolean empty;
	protected long timestamp = System.currentTimeMillis();
	private String generatorActivityId;
	
	public Artifact() {}
	
	/**
	 * Used only to create filtering wrappers for observations
	 * @param other
	 */
	protected Artifact(Artifact other) {
		this.annotations.addAll(other.annotations);
		this.activities.addAll(other.activities);
		this.peers.putAll(other.peers);
		this.archetype = other.archetype;
		this.metadata.putAll(other.metadata);
		this.empty = other.empty;
		this.generatorActivityId = other.generatorActivityId;
		this.timestamp = other.timestamp;
	}
	
	public void chain(IArtifact data) {
		throw new IllegalStateException("chain() should only be called on a group");
	}

	@Override
	public ValuePresentation getValuePresentation() {
		return ValuePresentation.VALUE;
	}
	
	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public Collection<IAnnotation> getAnnotations() {
		return annotations;
	}

	@Override
	public IProvenance getProvenance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		return empty;
	}

	@Override
	public String getUrn() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public IAgent getConsumer() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public IAgent getOwner() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Collection<IArtifact> getAntecedents() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Collection<IArtifact> getConsequents() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public IArtifact trace(IConcept concept) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Collection<IArtifact> collect(IConcept concept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IArtifact trace(IConcept role, IDirectObservation roleContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IArtifact> collect(IConcept role, IDirectObservation roleContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<IArtifact> iterator() {

		if (empty) {
			return new ArrayList<IArtifact>().iterator();
		}

		// List<IArtifact> list = new ArrayList<>(1 + (group == null ? 0 : (group.size()
		// - (idx < 0 ? 0 : idx))));
		// list.add(this);
		// if (group != null) {
		// for (int i = (idx < 0 ? 0 : idx); i < group.size(); i++) {
		// list.add(group.get(i));
		// }
		// }

		return Lists.newArrayList(this).iterator();
	}

	@Override
	public int groupSize() {
		return empty ? 0 : 1;
	}

	protected void setEmpty(boolean b) {
		this.empty = b;
	}

	public static IArtifact empty() {
		Artifact ret = new Artifact() {
			@Override
			public Type getType() {
				return Type.VOID;
			}

			@Override
			public String getId() {
				return "emptyArtifact";
			}

			@Override
			public IGeometry getGeometry() {
				return Geometry.empty();
			}

			@Override
			public IArtifact getGroupMember(int n) {
				return null;
			}

			@Override
			public long getLastUpdate() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public boolean hasChangedDuring(ITime time) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		ret.empty = true;
		return ret;
	}
	
	@Override
	public void release() {
//		System.out.println("RELEASING ARTIFACT - UNIMPLEMENTED!");
	}

	public Activity getGenerator() {
		return activities.size() > 0 ? (Activity)activities.get(0) : null;
	}
	
	public List<IActivity> getActions() {
		return activities;
	}

	public void setGenerator(Activity generator) {
		this.activities.add(generator);
		this.generatorActivityId = generator.getId();
	}

	/**
	 * Base implementation just looks for installed peers. Can be overridden to
	 * support translation to PODs or other objects.
	 */
	@Override
	public boolean is(Class<?> cls) {
        if (this instanceof DirectObservation) {
            Class< ? > clazz = ((DirectObservation) this).getOriginatingPattern().getClass();
            if (cls.isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
	}

	/**
	 * Base implementation just looks for installed peers. Can be overridden to
	 * support translation to PODs or other objects.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T as(Class<?> cls) {
        if (this instanceof DirectObservation) {
            return (T) ((DirectObservation)this).getOriginatingPattern();
        }
        return (T) peers.get(cls);
	}

	/*
	 * basic method to support the two above
	 */
//	public void addPeer(Object peer, Class<INetwork> class1) {
//		peers.put(class1, peer);
//	}
	
	public Collection<IArtifact> getChildArtifacts() {
		List<IArtifact> ret = new ArrayList<>();
		return ret;
	}

	public String getGeneratorActivityId() {
		return this.generatorActivityId;
	}

	@Override
	public boolean isArchetype() {
		return archetype;
	}

	public void setArchetype(boolean archetype) {
		this.archetype = archetype;
	}
	
	@Override
	public IMetadata getMetadata() {
		return metadata;
	}
	
	public abstract IArtifact getGroupMember(int n);

	
	public boolean hasChangedDuring(ITime time) {
		if (getLastUpdate() > time.getStart().getMilliseconds()) {
			return true;
		}
		if (this.getGeometry() instanceof IScale) {
			ITime myTime =  ((IScale)this.getGeometry()).getTime();
			return myTime.hasChangeDuring(time);
		}
		return false;
	}
}

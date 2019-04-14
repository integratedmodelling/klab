package org.integratedmodelling.klab.provenance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;

import com.google.common.collect.Lists;

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
public abstract class Artifact implements IArtifact {

	List<IAnnotation> annotations = new ArrayList<>();
	private Activity generator;
	private boolean notified = false;
	
	/*
	 * all observation data in a group share the same list; the pre-build object is
	 * thrown away at chain(). Saving the constructor call is not worth the
	 * additional logics.
	 */
	boolean empty;
	long timestamp = System.currentTimeMillis();


	public void chain(IArtifact data) {
		throw new IllegalStateException("chain() should only be called on a group");
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

	@Override
	public IAgent getConsumer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAgent getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IArtifact> getAntecedents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IArtifact> getConsequents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IArtifact trace(IConcept concept) {
		// TODO Auto-generated method stub
		return null;
	}

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
	public IGeometry getGeometry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMetadata getMetadata() {
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
		};
		ret.empty = true;
		return ret;
	}
	
	@Override
	public void release() {
		System.out.println("RELEASING ARTIFACT - UNIMPLEMENTED!");
	}

	public Activity getGenerator() {
		return generator;
	}

	public void setGenerator(Activity generator) {
		this.generator = generator;
	}

	public boolean isNotified() {
		return notified;
	}

	public void setNotified(boolean notified) {
		this.notified = notified;
	}
	

	@Override
	public boolean is(Class<?> cls) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T as(Class<?> cls) {
		// TODO Auto-generated method stub
		return null;
	}

}

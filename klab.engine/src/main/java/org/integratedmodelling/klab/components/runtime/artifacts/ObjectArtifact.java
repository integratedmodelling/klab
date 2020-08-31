package org.integratedmodelling.klab.components.runtime.artifacts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.utils.NameGenerator;

import com.google.common.collect.Lists;

/**
 * The dumb object artifact w/o any semantics. Used as a return value when
 * resolving resources outside of a semantic scope.
 * 
 * @author Ferd
 *
 */
public class ObjectArtifact extends Artifact implements IObjectArtifact {

	String id = "a" + NameGenerator.shortUUID();
	String name;
	IScale scale;
	List<IArtifact> group = null;
	List<IArtifact> children = new ArrayList<>();

	public ObjectArtifact(String name, IScale scale) {
		this.name = name;
		this.scale = scale;
	}

	public ObjectArtifact(String name, IScale scale, IMetadata metadata) {
		this.name = name;
		this.scale = scale;
		this.metadata.putAll(metadata);
	}

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IGeometry getGeometry() {
		return scale;
	}

	public void addChild(IArtifact child) {
		this.children.add(child);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IArtifact> Collection<T> getChildren(Class<T> cls) {
		List<T> ret = new ArrayList<>();
		for (IArtifact c : children) {
			if (cls.isAssignableFrom(c.getClass())) {
				ret.add((T) c);
			}
		}
		return ret;
	}

	@Override
	public Iterator<IArtifact> iterator() {
		return group == null ? Lists.newArrayList(this).iterator() : group.iterator();
	}

	@Override
	public int groupSize() {
		return isEmpty() ? 0 : group == null ? 1 : group.size();
	}

	@Override
	public void chain(IArtifact data) {
		if (this.group == null) {
			this.group = new ArrayList<>();
			this.group.add(this);
		}
		this.group.add(data);
	}

	@Override
	public IArtifact getGroupMember(int n) {
		return group == null ? (n == 0 ? this : null) : (group.size() > (n-1) ? group.get(n-1) : null);
	}

}

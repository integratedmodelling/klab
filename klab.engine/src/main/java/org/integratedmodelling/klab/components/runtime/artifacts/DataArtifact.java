package org.integratedmodelling.klab.components.runtime.artifacts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.utils.CollectionWrapper;
import org.integratedmodelling.klab.utils.Utils;

import com.google.common.collect.Lists;

/**
 * The dumb data artifact w/o any semantics. Used as a return value when
 * resolving resources outside of a semantic scope. Because we do not have
 * semantics, the ID is an actual name, as reported by the resolution of a
 * resource, rather than a unique identifier.
 * 
 * @author Ferd
 *
 */
public class DataArtifact extends Artifact implements IDataArtifact {

	CollectionWrapper data;
	Type type;
	String id;
	IScale scale;
	List<IArtifact> group = null;
	
	public DataArtifact(String name, Type type, IScale scale, Object data) {
		this.data = new CollectionWrapper(data);
		this.type = type;
		this.id = name;
		this.scale = scale;
	}

	public CollectionWrapper getData() {
		return data;
	}
	
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public IGeometry getGeometry() {
		return scale;
	}
	
	@Override
	public Object get(ILocator index) {
		Offset offset = index.as(Offset.class);
		return data.get(offset.linear);
	}

	@Override
	public <T> T get(ILocator index, Class<T> cls) {
		return Utils.asType(get(index), cls);
	}

	@Override
	public long set(ILocator index, Object value) {
		throw new IllegalStateException("read-only data access");
	}

	@Override
	public long size() {
		return data.size();
	}

	@Override
	public IDataKey getDataKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T aggregate(ILocator geometry, Class<? extends T> cls) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int groupSize() {
		return isEmpty() ? 0 : group == null ? 1 : group.size();
	}
	
	@Override
	public Iterator<IArtifact> iterator() {
		return group == null ? Lists.newArrayList(this).iterator() : group.iterator();
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

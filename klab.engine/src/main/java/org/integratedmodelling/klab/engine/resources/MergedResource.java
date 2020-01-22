package org.integratedmodelling.klab.engine.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * Built from a model statement with multiple URNs, either specifying models or
 * resources. The local resource catalog has a special table for these.
 * 
 * @author Ferd
 *
 */
public class MergedResource implements IResource {

	private static final long serialVersionUID = 85315842352320711L;

	String id;
	private long timestamp = new Date().getTime();
	private String urn; 
	private IScale scale;
	private IMetadata metadata = new Metadata();
	private Parameters<String> parameters = Parameters.create();
	private List<Attribute> attributes = new ArrayList<>();
	private List<Attribute> inputs = new ArrayList<>();
	private List<Attribute> outputs = new ArrayList<>();
	private Map<String, String> exports;
	private Type type;
	
	class ResourceSet {
		
	}
	
	// either one or the other is defined
	private NavigableMap<Long, ResourceSet> resources =  new TreeMap<>();
	
	/**
	 * Call with a model statement after verifying that more than one URN is 
	 * mentioned.
	 * 
	 * TODO honor any @interpolation annotation on the model or anything else
	 * defining how the resources are merged. (e.g. a default value when none
	 * responds - we could do the same with normal models actually).
	 * 
	 * TODO build a temporal index like in file-based states and use that to
	 * pick the (set of) resources @T.
	 * 
	 * @param statement
	 */
	public MergedResource(IKimModel statement) {
		this.urn = "local:merged:" + statement.getNamespace() + ":" + statement.getName();
		for (String urn : statement.getResourceUrns()) {
			// must be same type
			// geometries may overlap or complement in both time and space
			// if they are temporal with non-overlapping space, the type is PROCESS
			// add new piece to resources array and merge geometries
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public IProvenance getProvenance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public String getUrn() {
		return this.urn;
	}

	@Override
	public IGeometry getGeometry() {
		if (this.scale == null) {
			
		}
		return this.scale;
	}

	@Override
	public Version getVersion() {
		return Version.getCurrent();
	}

	@Override
	public String getAdapterType() {
		return "merged";
	}

	@Override
	public IMetadata getMetadata() {
		return this.metadata;
	}

	@Override
	public List<IResource> getHistory() {
		return new ArrayList<>();
	}

	@Override
	public IParameters<String> getParameters() {
		return parameters;
	}

	@Override
	public Collection<Attribute> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<Attribute> getInputs() {
		return inputs;
	}

	@Override
	public Collection<Attribute> getOutputs() {
		return outputs;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Map<String, String> getExports() {
		return exports;
	}

	@Override
	public long getResourceTimestamp() {
		return timestamp;
	}

	@Override
	public boolean isGranular() {
		return false;
	}

	@Override
	public Map<IGeometry, IResource> getGranules() {
		return null;
	}

	@Override
	public boolean hasErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getStatusMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLocalPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalProjectName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

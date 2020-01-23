package org.integratedmodelling.klab.engine.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.data.storage.ResourceCatalog;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.scale.Coverage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
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
	private Geometry geometry;
	private long timeStart = -1;
	private long timeEnd = -1;

	class ResourceSet {
		long start = -1;
		long end = -1;
		ICoverage coverage;
		// either one or the other is defined
		List<Pair<IResource, IModel>> resources = new ArrayList<>();
	}

	/*
	 * Indexed by start time, it's still possible for the closest layer to be
	 * undefined
	 */
	private NavigableMap<Long, ResourceSet> resources = new TreeMap<>();

	/**
	 * Call with a model statement after verifying that more than one URN is
	 * mentioned. The constructor automatically registers the resource with the
	 * local resource catalog.
	 * 
	 * TODO honor any @interpolation annotation on the model or anything else
	 * defining how the resources are merged. (e.g. a default value when none
	 * responds - we could do the same with normal models actually).
	 * 
	 * TODO build a temporal index like in file-based states and use that to pick
	 * the (set of) resources @T.
	 * 
	 * @param statement
	 */
	public MergedResource(IKimModel statement, IMonitor monitor) {
		this.urn = "local:merged:" + statement.getNamespace() + ":" + statement.getName();

		for (String urn : statement.getResourceUrns()) {

			IResource resource = null;
			Model model = null;
			IScale scale = null;

			if (urn.contains(":")) {
				resource = Resources.INSTANCE.resolveResource(urn);
				if (resource == null) {
					throw new KlabValidationException("URN " + urn + " does not specify a resource");
				}
				if (this.type != null && this.type != resource.getType()) {
					throw new KlabValidationException(
							"Cannot mix type " + this.type + " with " + resource.getType() + " from " + urn);
				}
				scale = Scale.create(resource.getGeometry());
				this.type = resource.getType();

			} else {
				// model object
				IKimObject mo = Resources.INSTANCE.getModelObject(urn);
				if (!(mo instanceof IModel)) {
					throw new KlabValidationException("URN " + urn + " does not specify a model");
				}
				model = (Model) mo;
				scale = ((Model) mo).getCoverage(monitor);
				if (this.type != null && this.type != model.getObservables().get(0).getArtifactType()) {
					throw new KlabValidationException("Cannot mix type " + this.type + " with "
							+ model.getObservables().get(0).getArtifactType() + " from " + urn);

				}
				this.type = model.getObservables().get(0).getArtifactType();
			}

			getResourceSet(scale).resources.add(new Pair<>(resource, model));

		}

		validate();

		((ResourceCatalog) Resources.INSTANCE.getLocalResourceCatalog()).addInlineResource(this);
	}

	private ResourceSet getResourceSet(IScale scale) {

		ResourceSet ret = null;
		ITime time = scale.getTime();
		if (!resources.isEmpty()) {
			if (time == null) {
				if (resources.size() == 1) {
					ret = resources.values().iterator().next();
				}
			} else {
				// find the resource set that contains the time interval; if none does, add a
				// new one
				for (ResourceSet set : resources.values()) {
					if (set.coverage.getTime() == null) {
						throw new KlabValidationException(
								"Cannot merge temporal resources with non-temporal resources");
					}
					if (time.getStart() != null && timeStart == time.getStart().getMilliseconds()) {
						ret = set;
						break;
					}
				}

			}
		}

		if (ret == null) {
			ret = new ResourceSet();
			ret.coverage = Coverage.full(scale);
			if (time != null) {
				ret.start = time.getStart() == null ? -1 : time.getStart().getMilliseconds();
				ret.end = time.getEnd() == null ? -1 : time.getEnd().getMilliseconds();
			}
			resources.put(ret.start, ret);
			if (timeStart < 0 || timeStart > ret.start) {
				timeStart = ret.start;
			}
			if (timeEnd < 0 || timeEnd < ret.end) {
				timeEnd = ret.end;
			}
		}

		return ret;
	}

	private void validate() {

		if (resources.size() > 1) {
			this.type = Type.PROCESS;
		}

		/*
		 * create the final geometry with a shape and a time extent
		 */
		IShape shape = null;

		for (Entry<Long, ResourceSet> key : resources.entrySet()) {
			ResourceSet set = key.getValue();
			if (shape == null && set.coverage.getSpace() != null) {
				shape = set.coverage.getSpace().getShape();
			} else {
				if (set.coverage.getSpace() == null) {
					throw new KlabValidationException("Cannot merge spatial resources with non-spatial resources");
				}
				shape = shape.union(set.coverage.getSpace().getShape());
			}
		}

		ITime time = null;
		if (timeStart > 0) {
			time = Time.create(ITime.Type.PHYSICAL, ITime.Resolution.Type.MILLISECOND, 1, new TimeInstant(timeStart),
					null, null);
		}
		this.scale = Scale.create(shape, time);
		this.geometry = ((Scale) this.scale).asGeometry();

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
		return this.geometry;
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

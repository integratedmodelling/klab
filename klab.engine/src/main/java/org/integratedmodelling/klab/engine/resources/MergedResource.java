package org.integratedmodelling.klab.engine.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IInspector;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.data.resources.ContextualizedResource;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.data.storage.ResourceCatalog;
import org.integratedmodelling.klab.documentation.Report;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.ResourceReference.AvailabilityReference;
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

	@SuppressWarnings("unused")
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
	private boolean logicalTime;
	private ITime.Resolution resolution;
	private List<String> urns = new ArrayList<>();
	private Map<String, Map<String, String>> resourceParameters = new HashMap<>();

	class ResourceSet {
		long start = -1;
		long end = -1;
		ICoverage coverage;
		List<Pair<IResource, Map<String, String>>> resources = new ArrayList<>();
		public Resolution resolution;
	}

	/*
	 * Indexed by start time, it's still possible for the closest layer to be
	 * undefined
	 */
	private NavigableMap<Long, ResourceSet> resources = new TreeMap<>();

	// private ITime resolutionTime;

	public MergedResource(MergedResource other) {
		this.id = other.id;
		this.timestamp = other.timestamp;
		this.urn = other.urn;
		this.metadata = other.metadata;
		this.parameters = other.parameters;
		this.attributes = other.attributes;
		this.inputs = other.inputs;
		this.outputs = other.outputs;
		this.type = other.type;
		this.exports = other.exports;
		this.geometry = other.geometry;
		this.timeStart = other.timeStart;
		this.timeEnd = other.timeEnd;
		this.logicalTime = other.logicalTime;
		this.resolution = other.resolution;
		this.resources.putAll(other.resources);
	}

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

			this.urns.add(urn);
			IResource resource = null;
			IScale scale = null;
			Urn uurn = new Urn(urn);

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
			this.metadata = resource.getMetadata();
			this.logicalTime = scale.getTime() != null && scale.getTime().isGeneric();
			if (this.logicalTime) {
				this.resolution = scale.getTime().getCoverageResolution();
			}
			
			resourceParameters.put(uurn.getUrn(), uurn.getParameters());

			getResourceSet(scale).resources.add(new Pair<>(resource, uurn.getParameters()));

		}

		validate();

		((ResourceCatalog) Resources.INSTANCE.getLocalResourceCatalog()).addInlineResource(this);
	}

	public MergedResource(List<String> urns, IRuntimeScope scope) {

		this.urn = "local:merged:" + scope.getNamespace() + ":" + scope.getTargetName();

		for (String urn : urns) {

			this.urns.add(urn);
			IResource resource = null;
			IScale scale = null;
			Urn uurn = new Urn(urn);

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

			this.logicalTime = scale.getTime() != null && scale.getTime().isGeneric();
			if (this.logicalTime) {
				this.resolution = scale.getTime().getCoverageResolution();
			}

			getResourceSet(scale).resources.add(new Pair<>(resource, uurn.getParameters()));

		}

		validate();

		// ((ResourceCatalog)
		// Resources.INSTANCE.getLocalResourceCatalog()).addInlineResource(this);
	}

	public List<String> getUrns() {
		return urns;
	}

	private ResourceSet getResourceSet(IScale scale) {

		ResourceSet ret = null;
		ITime time = scale.getTime();

		if (logicalTime && !time.isGeneric()) {
			throw new KlabValidationException(
					"Cannot merge resources in logical time with others with different temporal representation");
		}

		if (resolution != null && !time.getCoverageResolution().equals(resolution)) {
			throw new KlabValidationException(
					"Cannot merge resources in logical time and different coverage resolutions");
		}

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
					if (time.is(ITime.Type.LOGICAL) && time.getCoverageResolution() != null) {
						if (timeStart == time.getCoverageLocatorStart()) {
							ret = set;
							break;
						}
					} else if (time.getStart() != null && timeStart == time.getStart().getMilliseconds()) {
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

				if (time.is(ITime.Type.LOGICAL) && time.getCoverageResolution() != null) {
					ret.start = time.getCoverageLocatorStart();
					ret.end = time.getCoverageLocatorEnd();
					ret.resolution = time.getCoverageResolution();
				} else {
					ret.start = time.getStart() == null ? -1 : time.getStart().getMilliseconds();
					ret.end = time.getEnd() == null ? -1 : time.getEnd().getMilliseconds();
					ret.resolution = time.getResolution();
				}
			}

			if (ret.start == -1 && ret.end == -1) {
				throw new KlabValidationException("Resources that are merged must have temporal boundaries");
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

		/*
		 * default resolution if nothing can be understood from the data, which is very
		 * unlikely.
		 */
		ITime.Resolution.Type restype = ITime.Resolution.Type.MILLISECOND;

		Map<ITime.Resolution.Type, Integer> rcounts = new HashMap<>();

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
			if (set.resolution != null) {
				Integer count = rcounts.get(set.resolution.getType());
				if (count == null) {
					count = Integer.valueOf(1);
				} else {
					count = count + 1;
				}
				rcounts.put(set.resolution.getType(), count);
			}
		}

		/*
		 * TODO maybe also take the smallest multiplier instead of hard-coding it at 1.
		 */
		double resMultiplier = 1;
		if (rcounts.size() > 0) {
			restype = null;
			int lastc = 0;
			for (Entry<Resolution.Type, Integer> entry : rcounts.entrySet()) {
				if (restype == null) {
					restype = entry.getKey();
					lastc = entry.getValue();
				} else if (entry.getValue() > lastc) {
					restype = entry.getKey();
				}
			}
		}

		ITime time = null;
		if (timeStart > 0 && timeEnd > 0) {
			this.logicalTime = false;
			time = Time.create(ITime.Type.PHYSICAL, restype, resMultiplier, new TimeInstant(timeStart),
					timeEnd > 0 ? new TimeInstant(timeEnd) : null, null);
		} else {
			time = Time.create(ITime.Type.LOGICAL, restype, resMultiplier,
					timeStart > 0 ? new TimeInstant(timeStart) : null, timeEnd > 0 ? new TimeInstant(timeEnd) : null,
					null);
		}

		this.resolution = time.getResolution();
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
	public boolean isDynamic() {
		return resources.size() > 1; // TODO also if there is one and it's dynamic ||
										// resources.size() > 0 &&
										// resources.;
	}

	// @Override
	// public Map<IGeometry, IResource> getGranules() {
	// return null;
	// }

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

	/**
	 * Pick the specific resource(s) to use for the passed scale. TODO return a
	 * collection of resources and use the next to either fill in for any nodata in
	 * the previous, or add/average.
	 * 
	 * Return an empty collection if there is no new information to be added to the
	 * passed artifact. This should take into consideration having data to cover the
	 * specific timeslice we are being asked, checking that the artifact was not
	 * already contextualized up to the time limit of the last available resource.
	 * 
	 * TODO should take a storage parameter and return null when the resource has
	 * been already used to contextualize the latest step, to prevent superfluous
	 * data access.
	 * 
	 * @param scale
	 * @return
	 */
	public ContextualizedResource contextualize(IScale scale, IArtifact artifact, IContextualizationScope scope) {

		long locator = -1;
		ITime resolutionTime = scale.getTime();
		boolean isDynamic = artifact != null && artifact.getGeometry().getDimension(Dimension.Type.TIME) != null
				&& artifact.getGeometry().getDimension(Dimension.Type.TIME).size() > 1;

		if (resolutionTime != null && resolutionTime.getStart() != null) {
			if (resolutionTime.getTimeType() == ITime.Type.INITIALIZATION && isDynamic) {
				// shouldn't happen, but just in case.
				resolutionTime = Time.getPreviousExtent(resolutionTime);
			}
			locator = resolutionTime.getStart().getMilliseconds();
		}

		List<Pair<IResource, Map<String, String>>> ret = new ArrayList<>();
		if (resolutionTime == null && resources.size() > 0) {
			Entry<Long, ResourceSet> set = resources.floorEntry(-1L);
			if (set != null) {
				ret.addAll(set.getValue().resources);
			}
		} else {

			Entry<Long, ResourceSet> set = resources.floorEntry(locator);

			/*
			 * lenient check for unsuccessful initialization
			 */
			if (set == null && scale.getTime().is(ITime.Type.INITIALIZATION)) {
				locator = resolutionTime.getEnd().getMilliseconds();
				set = resources.floorEntry(locator);
				if (set == null) {
					set = resources.ceilingEntry(locator);
				}
			}

			if (set != null) {

				boolean ok = true;
				if (artifact != null && !(artifact instanceof RescalingState)) {
					/*
					 * if the artifact has already been contextualized up to this, don't add
					 * anything unless it's a partial, which we check by letting RescalingState
					 * through. Partials admit multiple contributions in the same timeslice, and any
					 * optimization must be done from upstream.
					 */
					/*
					 * FIXME FIXME check correctly for resources that were contextualized beyond
					 * this transition in previous contextualizations
					 */
					long seen = artifact.getLastUpdate();
					if (seen > set.getKey()) {
						ok = false;
					}
				}

				if (ok) {

					/*
					 * TODO for now only notifies the first resource
					 */
					scope.notifyInspector(scope, IInspector.Asset.RESOURCE, IInspector.Event.SELECTION,
							set.getValue().resources.get(0), artifact, scale.getTime());
					// add only used resources of MergedResource to report
					for (Pair<IResource, Map<String, String>> pr : set.getValue().resources) {
						((Report) scope.getReport()).include(pr.getFirst());
					}
					ret.addAll(set.getValue().resources);
				}
			}
		}
		return new ContextualizedResource(this, ret);
	}

	public String dump() {
		StringBuffer ret = new StringBuffer(1024);
		for (Long key : resources.keySet()) {
			ret.append(new Date(key) + ": " + resources.get(key).resources + "\n");
		}
		return ret.toString();
	}

	/**
	 * Check if all resources are online. Called as a special case by the resource
	 * service.
	 * 
	 * @return
	 */
	public boolean isOnline() {
		for (ResourceSet rs : resources.values()) {
			for (Pair<IResource, Map<String, String>> rr : rs.resources) {
				if (!Resources.INSTANCE.isResourceOnline(rr.getFirst())) {
					return false;
				}
			}
		}
		return true;

	}

	@Override
	public List<String> getDependencies() {
		return urns;
	}

	@Override
	public Collection<String> getCategorizables() {
		// TODO shouldn't be called as it's only for the editor
		return null;
	}

	public List<IResource> getResources() {
		List<IResource> ret = new ArrayList<>();
		for (ResourceSet rs : resources.values()) {
			for (Pair<IResource, Map<String, String>> pr : rs.resources) {
				ret.add(pr.getFirst());
			}
		}
		return ret;
	}

	@Override
	public AvailabilityReference getAvailability() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCodelists() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> getParameters(String urn2) {
		return resourceParameters.get(urn2);
	}

}

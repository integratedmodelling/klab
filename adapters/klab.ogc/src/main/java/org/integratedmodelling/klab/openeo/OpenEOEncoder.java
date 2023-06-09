package org.integratedmodelling.klab.openeo;

import java.io.File;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution.Type;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.dataflow.Flowchart;
import org.integratedmodelling.klab.dataflow.Flowchart.Element;
import org.integratedmodelling.klab.dataflow.FlowchartProvider;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.ogc.OpenEOAdapter;
import org.integratedmodelling.klab.openeo.OpenEO.OpenEOFuture;
import org.integratedmodelling.klab.openeo.OpenEO.Process;
import org.integratedmodelling.klab.openeo.OpenEO.ProcessNode;
import org.integratedmodelling.klab.raster.files.RasterEncoder;
import org.integratedmodelling.klab.raster.wcs.WcsEncoder;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;

public class OpenEOEncoder implements IResourceEncoder, FlowchartProvider {

	static Set<String> knownParameters;

	static {
		knownParameters = new HashSet<>();
		knownParameters.add("synchronous");
	}

	@Override
	public boolean isOnline(IResource resource, IMonitor monitor) {
		OpenEO service = OpenEOAdapter.getClient(resource.getParameters().get("serviceUrl").toString());
		if (service != null && service.isOnline()) {
			for (Object o : resource.getParameters().keySet()) {
				if ("?".equals(o)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public IResource contextualize(IResource resource, IScale scale, IArtifact targetObservation,
			Map<String, String> urnParameters, IContextualizationScope scope) {
		// TODO check - this should be able to handle time on its own, so no action
		// necessary
		return resource;
	}

	@Override
	public ICodelist categorize(IResource resource, String attribute, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			Builder builder, IContextualizationScope scope) {

		boolean synchronous = urnParameters.containsKey("synchronous")
				? Boolean.parseBoolean(urnParameters.get("synchronous"))
				: false;

		OpenEO service = OpenEOAdapter.getClient(resource.getParameters().get("serviceUrl").toString());
		if (service != null && service.isOnline()) {

			Parameters<String> arguments = Parameters.create();
			IScale rscal = Scale.create(resource.getGeometry());
			IScale scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);

			/*
			 * check for urn parameters that are recognized and match the type of the inputs
			 */
			for (String parameter : urnParameters.keySet()) {
				if (!knownParameters.contains(parameter)) {
					arguments.put(parameter, Utils.asPOD(urnParameters.get(parameter)));
				}
			}

			// resource has space: must specify space
			if (rscal.getSpace().isRegular() && scale.getSpace().size() > 1) {

				IGrid grid = ((Space) scale.getSpace()).getGrid();

				if (grid == null) {
					throw new KlabIllegalStateException("running a gridded OpenEO process in a non-grid context");
				}

				/*
				 * must have space.resolution and space.shape parameters
				 */
				if (resource.getParameters().containsKey("space.shape")
						&& resource.getParameters().containsKey("space.resolution")) {

					/*
					 * set GeoJSON shape and x,y resolution in parameters
					 */
					arguments.put(resource.getParameters().get("space.shape", String.class),
							((Shape) scale.getSpace().getShape()).asGeoJSON());

					List<Number> resolution = new ArrayList<>();
					resolution.add(grid.getCellWidth());
					resolution.add(grid.getCellHeight());

					arguments.put(resource.getParameters().get("space.resolution", String.class),
							grid.getCellWidth()/* resolution */);

				} else {
					throw new KlabIllegalStateException(
							"resource does not specify enough space parameters to contextualize");
				}

			} else if (rscal.getSpace() != null) {
				/*
				 * must have space.shape, set that and see what happens
				 */
				if (resource.getParameters().containsKey("space.shape")) {
					arguments.put(resource.getParameters().get("space.shape", String.class),
							((Shape) scale.getSpace().getShape()).asGeoJSON());
				} else {
					throw new KlabIllegalStateException(
							"resource does not specify enough space parameters to contextualize");
				}
			}

			if (scale.getSpace() != null && resource.getParameters().contains("space.projection")) {
				Object projectionData = scale.getSpace().getProjection().getSimpleSRS().startsWith("EPSG:")
						? Integer.parseInt(scale.getSpace().getProjection().getSimpleSRS().substring(5))
						: ((Projection) scale.getSpace().getProjection()).getWKTDefinition();
				arguments.put(resource.getParameters().get("space.projection", String.class), projectionData);
			}

			// resource is temporal: must specify extent
			if (rscal.getTime() != null) {
				/*
				 * must have either time.year or time.extent parameter
				 */
				if (resource.getParameters().containsKey("time.year")) {
					if (scale.getTime().getResolution().getType() == Type.YEAR
							&& scale.getTime().getResolution().getMultiplier() == 1) {
						arguments.put(resource.getParameters().get("time.year", String.class),
								scale.getTime().getStart().getYear());
					} else {
						throw new KlabUnsupportedFeatureException("non-yearly use of yearly OpenEO resource");
					}
				} else if (resource.getParameters().containsKey("time.extent")) {

					List<String> range = new ArrayList<>();
					range.add(scale.getTime().getStart().toRFC3339String());
					range.add(scale.getTime().getEnd().toRFC3339String());
					arguments.put(resource.getParameters().get("time.extent", String.class), range);

				} else {
					throw new KlabIllegalStateException(
							"resource does not specify enough temporal parameters to contextualize");
				}
			}

			List<Process> processes = new ArrayList<>();
			File processDefinition = new File(resource.getLocalPath() + File.separator + "process.json");
			if (processDefinition.isFile()) {
				processes.add(JsonUtils.load(processDefinition, Process.class));
			} else if (resource.getParameters().containsKey("namespace")) {
				try {
					Process process = JsonUtils.load(new URL(resource.getParameters().get("namespace", String.class)),
							Process.class);
					process.encodeSelf(resource.getParameters().get("namespace", String.class));
					processes.add(process);
				} catch (KlabIOException | MalformedURLException e) {
					// dio stracane
					throw new KlabIOException(e);
				}
			}

			if (synchronous) {

				RasterEncoder encoder = new RasterEncoder();
				try {
					service.runJob(resource.getParameters().get("processId", String.class), arguments,
							scope.getMonitor(), (input) -> {
								File outfile = WcsEncoder.getAdjustedCoverage(input, geometry);
								encoder.encodeFromCoverage(resource, urnParameters, encoder.readCoverage(outfile),
										geometry, builder, scope);
							}, processes.toArray(new Process[processes.size()]));

				} catch (Throwable t) {
					scope.getMonitor().error(t);
					throw t;
				}
			} else {

				OpenEOFuture job = service.submit(resource.getParameters().get("processId", String.class), arguments,
						scope.getMonitor(), processes.toArray(new Process[processes.size()]));

				try {
					Map<String, Object> results = job.get();

					if (job.isCancelled()) {
						scope.getMonitor().warn("job canceled");
					} else if (job.getError() != null) {
						scope.getMonitor().error(job.getError());
					} else {

						for (String key : results.keySet()) {
							Map<?, ?> result = (Map<?, ?>) results.get(key);
							if (result.containsKey("href") && result.containsKey("type")) {
								/*
								 * depending on the geometry, this may be of different types
								 */
								if (result.get("type").toString().contains("geotiff")) {
									File outfile = WcsEncoder.getAdjustedCoverage(result.get("href").toString(),
											geometry);
									RasterEncoder encoder = new RasterEncoder();
									encoder.encodeFromCoverage(resource, urnParameters, encoder.readCoverage(outfile),
											geometry, builder, scope);
									break;
								}
								// TODO handle other cases
							}
						}

					}
				} catch (InterruptedException | ExecutionException e) {
					throw new KlabInternalErrorException(e);
				}
			}

		} else {
			scope.getMonitor().warn("resource " + resource.getUrn() + " went offline");
		}

	}

	@Override
	public void listDetail(IResource resource, OutputStream stream, boolean verbose, IMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createFlowchart(IResource resource, Element element, Flowchart flowchart) {

		OpenEO service = OpenEOAdapter.getClient(resource.getParameters().get("serviceUrl").toString());
		Process process = service.getProcess(resource);
		if (process != null) {

			/*
			 * fix element name, label and description
			 */
			element.setDescription(process.getDescription());
			element.setName("openeo." + process.getId());
			element.setLabel("OpenEO UDP " + process.getId());

			/*
			 * create internal structure. First pass (make elements)
			 */
			Map<String, Element> children = new HashMap<>();
			Element output = null;
			for (String spermer : process.getProcess_graph().keySet()) {
				ProcessNode proc = process.getProcess_graph().get(spermer);
				Element procelem = element.newChild("openeo." + spermer);
				children.put(spermer, procelem);

				procelem.setLabel(proc.getProcess_id());
				procelem.setDescription(proc.getDescription());

				if (proc.isResult()) {
					System.out.println("PIRULÈRA l'output è " + proc.getProcess_id());
					output = procelem;
				}

				for (String arg : proc.getArguments().keySet()) {
					Object val = proc.getArguments().get(arg);
					if (val instanceof Map) {
						if (((Map) val).containsKey("from_node")) {
							// make input, link to other node's output
							System.out.println("GARGARUT gets " + arg + " from " + ((Map) val).get("from_node"));
						} else if (((Map) val).containsKey("from_parameter")) {
							// make input, link to containing node input, unless it's mapped to the context
							System.out.println("PIROLA uses parameter " + arg);
						} else {
							// make port and set the default as description
						}
					} else {
						// make named port with val as description
					}
				}
			}
			/*
			 * second pass (link them): from_node links elements, from_parameters links to
			 * inputs, result=true makes the output available
			 */
			for (String spermer : process.getProcess_graph().keySet()) {
				ProcessNode proc = process.getProcess_graph().get(spermer);
				Element procelem = element.newChild("openeo." + spermer);
				for (String arg : proc.getArguments().keySet()) {
					Object val = proc.getArguments().get(arg);
					if (val instanceof Map) {
						System.out.println(val);
					}
				}
			}
		}
	}

}

package org.integratedmodelling.klab.openeo;

import java.io.File;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.geotools.data.geojson.GeoJSONReader;
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
import org.integratedmodelling.klab.data.resources.ResourceBuilder;
import org.integratedmodelling.klab.dataflow.Flowchart;
import org.integratedmodelling.klab.dataflow.Flowchart.Element;
import org.integratedmodelling.klab.dataflow.Flowchart.ElementType;
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
import org.integratedmodelling.klab.stac.STACEncoder;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Utils;
import org.locationtech.jts.geom.Geometry;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class OpenEOEncoder implements IResourceEncoder, FlowchartProvider {

	static Set<String> knownParameters;

	static {
		knownParameters = new HashSet<>();
		knownParameters.add("synchronous");
	}

	@Override
	public boolean isOnline(IResource resource, IMonitor monitor) {
        OpenEO service = null;
        try {
            service = OpenEOAdapter.getClient(resource.getParameters().get("serviceUrl").toString());
        } catch (Exception e) {
            // we love you @inigo
            return false;
        }

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

		    JSONObject params = new JSONObject();
			IScale rscal = Scale.create(resource.getGeometry());
			IScale scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);

			/*
			 * check for urn parameters that are recognized and match the type of the inputs
			 */
			for (String parameter : urnParameters.keySet()) {
				if (!knownParameters.contains(parameter)) {
//					arguments.put(parameter, Utils.asPodOrList(urnParameters.get(parameter)));
				    if (parameter.equals("output_band_names")) {
				        params.put("output_band_names", urnParameters.get(parameter).split(","));
				    } else if (parameter.equals("onnx_models")) {
                        params.put("onnx_models", List.of(urnParameters.get(parameter).split(","))); // TODO fix si solo 1
				    } else {
				        params.put(parameter, Utils.asPOD(urnParameters.get(parameter)));
				    }
				}
			}

			// resource has space: must specify space
			if (rscal.getSpace().isRegular() && scale.getSpace().size() > 1) {

				IGrid grid = ((Space) scale.getSpace()).getGrid();

				if (grid == null) {
					throw new KlabIllegalStateException("running a gridded OpenEO process in a non-grid context");
				}

                if (urnParameters.containsKey("modelId") || true) {
                    String searchURL = "https://catalogue.weed.apex.esa.int/search";
                    JSONObject body = new JSONObject();
                    body.put("collections", new JSONArray().put("model-STAC"));
                    Map<String, Object> contextShape = ((Shape) scale.getSpace().getShape()).asGeoJSON();
                    body.put("intersects", contextShape);
                    int timeout = 50000;
                    HttpResponse<JsonNode> response = Unirest.post(searchURL).body(body).header("Content-Type", "application/json")
                            .connectTimeout(timeout).asJson();
                    if (!response.isSuccess()) {
                        throw new KlabIllegalStateException("ERROR AT READING MODELID");
                    }
                    
                    List< ? > features = response.getBody().getObject().getJSONArray("features").toList();
                    List< ? > potato = features.stream().filter(feature -> {
//                        String coordinates = ((JSONObject) feature).getJSONObject("properties").getJSONObject("proj:geometry").toString();
                        String coordinates = ((JSONObject) feature).getJSONObject("geometry").toString();
                        Geometry coordGeom = GeoJSONReader.parseGeometry(coordinates);
                        return coordGeom.intersects(GeoJSONReader.parseGeometry(new JSONObject(contextShape).toString()));
                    }).toList();
                        
                    System.out.println(potato);
//                        String modelID
                            
//                            getJSONObject(0).getJSONObject("properties").getString("modelID");
                }


				/*
				 * must have space.resolution and either space.shape or space.bbox parameters
				 */
				if (resource.getParameters().containsKey("space.resolution")) {
					List<Number> resolution = new ArrayList<>();
					resolution.add(grid.getCellWidth());
					resolution.add(grid.getCellHeight());

					params.put(resource.getParameters().get("space.resolution", String.class), 10);
//							grid.getCellWidth()/* resolution */);

                } else {
                    throw new KlabIllegalStateException(
                            "resource does not specify space.resolution parameter");
                }

				if (resource.getParameters().containsKey("space.shape") && !resource.getParameters().get("space.shape").equals("?")) {
                    /*
                     * set GeoJSON shape and x,y resolution in parameters
                     */
				    params.put(resource.getParameters().get("space.shape", String.class),
                            ((Shape) scale.getSpace().getShape()).asGeoJSON());

                } else if (resource.getParameters().containsKey("space.bbox") && !resource.getParameters().get("space.bbox").equals("?")) {
                    File jsonFile = new File("./src/main/resources/weed/alpha01bboxes.json");
                    JSONObject bbox = new JSONObject();
                    try {
                        JSONObject bboxes = new JSONObject(FileUtils.readFileToString(jsonFile));
                        bbox = bboxes.getJSONObject(urnParameters.get("bbox"));
                        if (bbox == null) {
                            throw new Exception("I know, I know... this is not beautiful.");
                        }
                        params.put("bbox", bbox);
                    } catch (Exception e) {
                        bbox.put("west", 4680000);
                        bbox.put("east", 4700000);
                        bbox.put("north", 3080000);
                        bbox.put("south", 3060000);
                        bbox.put("crs", "EPSG:3035");
                        params.put("bbox", bbox);
                    }
				} else {
					throw new KlabIllegalStateException(
							"resource does not specify enough space parameters to contextualize");
				}

			} else if (rscal.getSpace() != null) {
				/*
				 * must have space.shape, set that and see what happens
				 */
				if (resource.getParameters().containsKey("space.shape")) {
				    params.put(resource.getParameters().get("space.shape", String.class),
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
                params.put(resource.getParameters().get("space.projection", String.class), 3035);// projectionData);
			}

			// resource is temporal: must specify extent
			if (rscal.getTime() != null) {
				/*
				 * must have either time.year or time.extent parameter
				 */
				if (resource.getParameters().containsKey("time.year")) {
					if (scale.getTime().getResolution().getType() == Type.YEAR
							&& scale.getTime().getResolution().getMultiplier() == 1) {
					    params.put(resource.getParameters().get("time.year", String.class), 2021);
//								scale.getTime().getStart().getYear());
					} else {
						throw new KlabUnsupportedFeatureException("non-yearly use of yearly OpenEO resource");
					}
				} else if (resource.getParameters().containsKey("time.extent")) {

					List<String> range = new ArrayList<>();
					range.add(scale.getTime().getStart().toRFC3339String());
					range.add(scale.getTime().getEnd().toRFC3339String());
					params.put(resource.getParameters().get("time.extent", String.class), range);

				} else {
					throw new KlabIllegalStateException(
							"resource does not specify enough temporal parameters to contextualize");
				}
			}
			
//			if (resource.getParameters().contains("output_band_names")) {
//			    
//			}

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
			
            if (true) { // TODO test for Alpha02
                STACEncoder encoder = new STACEncoder();
                IResource stacResource = new ResourceBuilder("Test").withGeometry(scale).withAdapterType("STAC")
                        .withParameter("collection", "https://catalogue.weed.apex.esa.int/collections/inference-alpha2-prepared-v101")
//                        .withParameter("asset", "ALP")
                        .withParameter("asset", "alpha-2_proba-cube_year2024_32τNM42_EUNIS2021plus-EU-v1-2024-BOR_v101.tif")
                        .withParameter("updMagic", "")
                        .build();
                 encoder.getEncodedData(stacResource, Map.of(), geometry, builder, scope);
                return;
            }

			
			if (synchronous) {

				RasterEncoder encoder = new RasterEncoder();
				try {
					service.runJob(resource.getParameters().get("processId", String.class), params,
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
                // TODO test for Alpha02
				OpenEOFuture job = service.submit(resource.getParameters().get("processId", String.class), params,
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
									System.out.println(result.get("type").toString());
									File outfile = WcsEncoder.getAdjustedCoverage(result.get("href").toString(),
											geometry);
                                    //try {
                                    //    FileUtils.copyInputStreamToFile(new URL(result.get("href").toString()).openStream(), outfile);
                                    //} catch (IOException e) {
                                    //    throw new KlabIOException(e);
                                    //}
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
			element.setType(ElementType.ACTUATOR);

			if (false) {

				/*
				 * create internal structure. First pass (make elements)
				 */
				Map<String, ProcessNode> processes = flattenProcesses(process, element);
				Map<String, Element> children = new HashMap<>();

				Element output = null;
				for (String spermer : processes.keySet()) {
					ProcessNode proc = processes.get(spermer);
					Element procelem = element.newChild("openeo." + spermer);
					children.put(spermer, procelem);

					procelem.setLabel(proc.getProcess_id());
					procelem.setDescription(proc.getDescription());
					procelem.setType(ElementType.RESOURCE); // TODO unless internals are available,
															// should be RESOURCE but not green

					if (proc.isResult()) {
//                    System.out.println("PIRULÈRA l'output è " + proc.getProcess_id());
						output = procelem;
					}
				}

				for (String spermer : processes.keySet()) {
					ProcessNode proc = processes.get(spermer);
					Element procelem = children.get(spermer);
					if (procelem == output && element.getMainOutput() != null) {
						String out = procelem.getOrCreateOutput(spermer);
						flowchart.getConnections().add(new Pair<>(out, element.getMainOutput()));
					}
					for (String arg : proc.getArguments().keySet()) {
						for (Object val : getProcessArguments(proc, arg)) {
							if (val instanceof Map) {
								if (((Map<?, ?>) val).containsKey("from_node")) {
									// make input, link to other node's output
									Element from = children.get(((Map<?, ?>) val).get("from_node"));
									if (from != null) { // FIXME shouldn't happen
										String out = from.getOrCreateOutput(arg);
										String in = procelem.getOrCreateInput(arg);
										flowchart.getConnections().add(new Pair<>(out, in));
//                                if (from != null) {
//                                    System.out.println("GARGARUT " + spermer + "  gets " + arg + " from "
//                                            + ((Map<?, ?>) val).get("from_node"));
//                                }
									}
								} else if (((Map<?, ?>) val).containsKey("from_parameter")
								/*
								 * && !isContextParameter(resource, ((Map<?, ?>)
								 * val).get("from_parameter").toString())
								 */) {
									// make input, link to containing node input, unless it's mapped to
									// the
									// context
									String in = element.getOrCreateInput(arg);
									String out = procelem.getOrCreateInput(arg);
									flowchart.getConnections().add(new Pair<>(in, out));
//                                System.out.println("PIROLA " + spermer + " uses parameter "
//                                        + ((Map<?, ?>) val).containsKey("from_parameter"));
								} else {
									// make port and set the default as description
									String in = element.getOrCreateInput(arg);
									// String out = procelem.getOrCreateInput(arg);
									// flowchart.getConnections().add(new Pair<>(in, out));

//                                System.out.println("PUTANGA " + spermer + " inputs object " + arg + ": " + val);
								}
							} else {
								// make named port with val as description. This should have the
								// parameter's
								// name, for now just give it a random ID
								procelem.getOrCreateInput(arg);
//                            System.out.println("TORTELLO " + spermer + " inputs " + arg + " = " + val);
							}
						}
					}
				}
			}
		}
	}

	private Collection<Object> getProcessArguments(ProcessNode proc, String arg) {

		List<Object> ret = new ArrayList<>();
		Object o = proc.getArguments().get(arg);
		if (o instanceof Collection) {
			for (Object oo : ((Collection<?>) o)) {
				ret.add(oo);
			}
		} else if (o != null) {
			ret.add(o);
		}
		return ret;
	}

	private Map<String, ProcessNode> flattenProcesses(Process process, Element element) {
		Map<String, ProcessNode> ret = new HashMap<>();
		flattenProcesses(process.getProcess_graph(), null, ret);
		return ret;
	}

	private void flattenProcesses(Map<String, ProcessNode> processGraph, String prefix, Map<String, ProcessNode> ret) {
		for (String key : processGraph.keySet()) {
			ProcessNode node = processGraph.get(key);
			ret.put((prefix == null ? "" : prefix) + key, node);
			for (String arg : node.getArguments().keySet()) {
				Object borp = node.getArguments().get(arg);
				for (Object bop : (borp instanceof Collection ? ((Collection<?>) borp) : Collections.singleton(borp))) {
					if (bop instanceof Map && ((Map<?, ?>) bop).containsKey("process_graph")) {
						flattenProcesses(JsonUtils.convertMap((Map<?, ?>) bop, Process.class).getProcess_graph(), arg,
								ret);
					}
				}
			}
		}
	}

	private boolean isContextParameter(IResource resource, String string) {
		for (String key : resource.getParameters().keySet()) {
			if (string.equals(resource.getParameters().get(key))) {
				return key.startsWith("space.") || key.startsWith("time.");
			}
		}
		return false;
	}

}

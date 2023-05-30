package org.integratedmodelling.klab.openeo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.provenance.IActivity.Description;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.GeometryBuilder;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.data.resources.ResourceBuilder;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.ogc.OpenEOAdapter;
import org.integratedmodelling.klab.openeo.OpenEO.Parameter;
import org.integratedmodelling.klab.openeo.OpenEO.Process;
import org.integratedmodelling.klab.openeo.OpenEO.Schema;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

public class OpenEOResourceValidator implements IResourceValidator {

	@Override
	public Builder validate(String urn, URL url, IParameters<String> userData, IMonitor monitor) {

		OpenEO service = OpenEOAdapter.getClient(userData.get("serviceUrl", String.class));
		Builder ret = new ResourceBuilder(urn).withParameters(userData);

		/*
		 * default geometry is scalar, which should never be the case
		 */
		GeometryBuilder gBuilder = Geometry.builder();
		Geometry geometry = Geometry.empty();
		boolean geometryDefined = false;

		if (!service.isOnline()) {
			monitor.warn("OpenEO service " + userData.get("serviceUrl", String.class) + " offline or unauthenticated");
		}

		if (userData.containsKey("namespace") || url != null) {
			try {

				/*
				 * retrieve, validate and parse project
				 */
				Process process = JsonUtils.load(url == null ? new URL(userData.get("namespace", String.class)) : url,
						Process.class);

				if (service.isOnline()) {
					final Builder builder = ret;
					if (!service.validateProcess(process, null, (code, error) -> {
						if (!"ProcessParameterRequired".equals(code)) {
							builder.addError(code + ": " + error);
						}
					})) {
						monitor.warn("process " + process.getId() + " did not pass server validation");
					}
				} else {
					monitor.warn("service offline: process " + process.getId() + " not validated");
				}

				for (Parameter parameter : process.getParameters()) {
					Parameters<String> dimensions = getDimensionParameters(parameter);
					if (dimensions == null) {
						ret = ret.withDependency(parameter.getName(), translateType(parameter.schemata()), false,
								parameter.isOptional());
						// TODO check conventions!
					} else {
						ret = ret.withParameters(dimensions);
					}
				}

				/*
				 * TODO If process has the "self" link, store as namespace parameter. Should
				 * also handle the "latest-version" link.
				 */

				/*
				 * TODO geometry should be in the metadata. No OpenEO standard to express
				 * spatial/temporal coverage I think, and final coverage depends on the process
				 * even if the indivual datasets contain coverage. I don't see any way to add
				 * arbitrary metadata though.
				 * 
				 * Set geometryDefined = true if passed.
				 * 
				 * Another way (more general) would be to later drag/drop a GeoJSON file on the
				 * existing resource, probably with a standardized name, in addition to making
				 * the geospace editor interactive. Time can be edited directly in the editor.
				 */

				/*
				 * TODO analyze the process to see if there are potential intermediate products
				 * that could be exposed.
				 */

				/*
				 * Basic metadata
				 */
				if (process.getDescription() != null) {
					ret = ret.withMetadata(IMetadata.DC_COMMENT, process.getDescription());
				}
				if (process.getSummary() != null) {
					ret = ret.withMetadata(IMetadata.DC_LABEL, process.getSummary());
				}

				/*
				 * TODO find more metadata in the links: cite-as
				 */

				if (process.getReturns() != null) {
					ret = ret.withType(translateType(Collections.singleton(process.getReturns().getSchema())));
				} else {
					monitor.warn(
							"OpenEO process " + process.getId() + " does not declare a return type: number assumed");
					ret = ret.withType(Type.NUMBER);
				}

			} catch (KlabIOException | MalformedURLException e) {
				monitor.error("malformed namespace URL or contents");
				return null;
			}
		}

		if (!geometryDefined) {
			/*
			 * Try to infer as much as possible of the geometry from parameters.
			 * 
			 * TODO add ? as a value for all space/time hooks that we haven't resolved from
			 * the process definition
			 */
			if (((ResourceBuilder) ret).getParameters().containsKey("time.extent")) {
//				Pair<Long, Long> time = readTimeExtent(((ResourceBuilder) ret).getParameters().get("time.extent"));
//				gBuilder.time().covering(time.getFirst(), time.getSecond()).build();
				gBuilder.time().build();
			} else if (((ResourceBuilder) ret).getParameters().containsKey("time.year")) {
//				int year = Integer.parseInt(((ResourceBuilder) ret).getParameters().get("time.year").toString());
//				gBuilder.time().covering(TimeInstant.create(year).getMilliseconds(),
//						TimeInstant.create(year).plus(1, Time.resolution(1, Resolution.Type.YEAR)).getMilliseconds())
//						.build();
				gBuilder.time().build();
			} else {
				ret = ret.withParameter("time.extent", "?");
			}

			if (((ResourceBuilder) ret).getParameters().containsKey("space.resolution")) {
				// gBuilder.space().resolution(urn)
				gBuilder.space().regular().build();
			} else {
				ret = ret.withParameter("space.resolution", "?");
			}
			if (((ResourceBuilder) ret).getParameters().containsKey("space.shape")) {
				// s2 unless resolution is there; otherwise compute x/y grid size
				// if result explicit and of type object, then we're a vector resource
				gBuilder.space().build();
			} else {
				ret = ret.withParameter("space.shape", "?");
			}

			geometry = gBuilder.build();

		}

		if (geometry.isEmpty()) {
			geometry = Geometry.scalar();
		}

		if (geometry.isScalar()) {
			monitor.warn("no geometry metadata available: storing resource " + urn + " with scalar geometry");
		} else {
			/*
			 * TODO validate S/T specifications in parameters to match the declared
			 * geometry. Conventions could be "spatial_extent" (GeoJSON) for space and
			 * temporal_extent (list of two RFC 3339 time instants, [start end) ) for time,
			 * as in the standard processes.
			 */
		}

		return ret.withGeometry(geometry);
	}

//	private Pair<Long, Long> readTimeExtent(Object object) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	// TODO check conventions, handle more cases. Should probably only support
	// spatial_extent and
	// temporal_extent
	// TODO also check parameter type and subtype
	private Parameters<String> getDimensionParameters(Parameter parameter) {

		if ("resolution".equals(parameter.getName())) {
			return Parameters.create("space.resolution", parameter.getName());
		} else if ("spatial_extent".equals(parameter.getName()) || "geometry".equals(parameter.getName())) {
			return Parameters.create("space.shape", parameter.getName());
		} else if ("temporal_extent".equals(parameter.getName())) {
			return Parameters.create("time.extent", parameter.getName());
		} else if ("year".equals(parameter.getName())) {
			return Parameters.create("time.year", parameter.getName());
		} else if ("projection".equals(parameter.getName())) {
			return Parameters.create("space.projection", parameter.getName());
		}

		return null;
	}

	private Type translateType(Collection<Schema> schemata) {
		// TODO this only considers the first value, flexible schemata are BAD
		for (Schema schema : schemata) {
			switch (schema.getType()) {
			case "number":
				return Type.NUMBER;
			case "string":
				return Type.TEXT;
			case "boolean":
				return Type.BOOLEAN;
			case "null":
				return Type.VOID;
			case "array":
				return Type.LIST;
			case "object":
				return "geojson".equals(schema.getSubtype()) ? Type.SPATIALEXTENT : Type.OBJECT;
			}
		}
		return null;
	}

	@Override
	public IResource update(IResource resource, ResourceCRUDRequest updateData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Operation> getAllowedOperations(IResource resource) {
		return Collections.emptyList();
	}

	@Override
	public boolean isObservationAllowed(IResource resource, Map<String, String> urnParameters,
			Description description) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IResource performOperation(IResource resource, String operationName, IParameters<String> parameters,
			IResourceCatalog catalog, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canHandle(File resource, IParameters<String> parameters) {

		if (resource == null) {
			return false;
		}
		String extension = MiscUtilities.getFileExtension(resource);
		if (extension != null) {
			return extension.toLowerCase().equals("json");
		}
		return false;
	}

	@Override
	public Collection<File> getAllFilesForResource(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<? extends String, ? extends Object> describeResource(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

}

package org.integratedmodelling.tables;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;

/**
 * @author Ferd
 *
 */
public abstract class TableInterpreter implements ITableInterpreter {

	IGeometry mergeGeometry(IResource resource, IResource distributing) {

		/*
		 * space must create the union of all the represented shapes after joining
		 * through attribute
		 */

		return null;
	}

	@Override
	public void categorize(IResource resource, IParameters<String> parameters) {
		// TODO Auto-generated method stub
		ITable<?> table = this.getTable(resource, null);
		Set<Object> categories = new LinkedHashSet<>();
		for (Object o : table.asList(parameters.get("dimension"))) {
			categories.add(o);
		}
		File file = new File(((Resource) resource).getPath() + File.separator + "code_"
				+ parameters.get("categorization") + ".properties");
		try (Writer out = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
			int n = 1;
			for (Object o : categories) {
				out.write("category." + (n++) + "=" + o + "->\n");
			}
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

	@Override
	public IGeometry recomputeGeometry(IResource resource, Map<String, String> parameters) {

		IGeometry ret = resource.getGeometry();

		String spaceEncoding = ret.getDimension(Dimension.Type.SPACE) == null ? null
				: ret.getDimension(Dimension.Type.SPACE).encode();
		String timeEncoding = ret.getDimension(Dimension.Type.TIME) == null ? null
				: ret.getDimension(Dimension.Type.TIME).encode();

		ITable<?> table = null;
		if (parameters.containsKey("space.encoding")) {
			table = getTable(resource, null);
			spaceEncoding = null;
			String[] parts = parameters.get("space.encoding").split(Pattern.quote("->"));
			DimensionScanner<IShape> strategy = new DimensionScanner<>(parts, parameters, IShape.class);
			for (ISpace shape : strategy.scanExtents(table)) {
				// AHA get each shape, perform a union of the bounding boxes for now. Maybe later use a
				// parameter if a precise shape is wanted.
			}
			// TODO remove the piece below
			for (String part : parts) {
				if (Urns.INSTANCE.isUrn(part)) {
					IResource sres = Resources.INSTANCE.resolveResource(part);
					if (sres != null && sres.getGeometry().getDimension(Dimension.Type.SPACE) != null) {
						spaceEncoding = sres.getGeometry().getDimension(Dimension.Type.SPACE).encode();
						break;
					}
				}
			}
		}

		if (parameters.containsKey("time.encoding")) {
			timeEncoding = null;
			if (table == null) {
				table = getTable(resource, null);
			}
			String[] parts = parameters.get("time.encoding").split(Pattern.quote("->"));
			DimensionScanner<ITime> strategy = new DimensionScanner<>(parts, parameters, ITime.class);
			long start = 0, end = 0;
			ITime.Resolution resolution = null;
			for (ITime time : strategy.scanExtents(table)) {
				// unite the temporal boundaries, establish resolution
				if (start == 0 || time.getStart().getMilliseconds() < start) {
					start = time.getStart().getMilliseconds();
				}
				if (end == 0 || time.getEnd().getMilliseconds() > end) {
					end = time.getEnd().getMilliseconds();
				}
				if (resolution == null) {
					resolution = time.getResolution();
				}
			}
			
			// TODO rebuild the geometry using start, stop and resolution; infer steps if res implies more
			// than end-start
		}

		if (spaceEncoding != null || timeEncoding != null) {
			ret = Geometry
					.create((timeEncoding == null ? "" : timeEncoding) + (spaceEncoding == null ? "" : spaceEncoding));
		}

		return ret;
	}

}

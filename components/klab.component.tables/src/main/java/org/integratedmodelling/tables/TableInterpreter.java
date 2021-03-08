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
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.tables.adapter.TableAdapter;

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
	public void categorize(IResource resource, IParameters<String> parameters, IMonitor monitor) {
		// TODO Auto-generated method stub
		ITable<?> table = this.getTable(resource, null, monitor);
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
	public IGeometry recomputeGeometry(IResource resource, Map<String, String> parameters, IMonitor monitor) {

		IGeometry ret = resource.getGeometry();

		String spaceEncoding = ret.getDimension(Dimension.Type.SPACE) == null ? null
				: ret.getDimension(Dimension.Type.SPACE).encode();
		String timeEncoding = ret.getDimension(Dimension.Type.TIME) == null ? null
				: ret.getDimension(Dimension.Type.TIME).encode();

		ITable<?> table = null;

		if (parameters.containsKey("space.encoding") && !parameters.get("space.encoding").trim().isEmpty()) {

			@SuppressWarnings("unchecked")
			DimensionScanner<IShape> strategy = TableAdapter.runtimeData.get(resource.getUrn() + "_space",
					DimensionScanner.class);

			if (strategy == null) {

				table = TableAdapter.getOriginalTable(resource, false, monitor);
				spaceEncoding = null;
				String[] parts = parameters.get("space.encoding").split(Pattern.quote("->"));
				strategy = new DimensionScanner<>(resource, parts, IShape.class);
				for (ISpace shape : strategy.scanExtents(table)) {
					// AHA get each shape, perform a union of the bounding boxes for now. Maybe
					// later use a
					// parameter if a precise shape is wanted.
				}
				// TODO remove the piece below
				for (String part : parts) {
					if (Urns.INSTANCE.isUrn(part)) {
						IResource sres = Resources.INSTANCE.resolveResource(part);
						if (sres != null && sres.getGeometry().getDimension(Dimension.Type.SPACE) != null) {
							spaceEncoding = sres.getGeometry().getDimension(Dimension.Type.SPACE).encode();
							((Resource) resource).getDependencies().add(part);
							break;
						}
					}
				}

				strategy.encodedDimension = spaceEncoding;
				TableAdapter.runtimeData.put(resource.getUrn() + "_space", strategy);

			} else {
				spaceEncoding = strategy.encodedDimension;
			}
		}

		if (parameters.containsKey("time.encoding") && !parameters.get("time.encoding").trim().isEmpty()) {

			@SuppressWarnings("unchecked")
			DimensionScanner<ITime> strategy = TableAdapter.runtimeData.get(resource.getUrn() + "_time",
					DimensionScanner.class);

			if (strategy == null) {

				timeEncoding = null;
				if (table == null) {
					table = TableAdapter.getOriginalTable(resource, false, monitor);
				}
				String[] parts = parameters.get("time.encoding").split(Pattern.quote("->"));
				strategy = new DimensionScanner<>(resource, parts, ITime.class);
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

				strategy.encodedDimension = Time.create(new TimeInstant(start), new TimeInstant(end), resolution)
						.encode();
				timeEncoding = strategy.encodedDimension;

				TableAdapter.runtimeData.put(resource.getUrn() + "_time", strategy);

			} else {
				timeEncoding = strategy.encodedDimension;
			}
		}

		if (spaceEncoding != null || timeEncoding != null) {
			ret = Geometry
					.create((timeEncoding == null ? "" : timeEncoding) + (spaceEncoding == null ? "" : spaceEncoding));
		}

		return ret;
	}

}

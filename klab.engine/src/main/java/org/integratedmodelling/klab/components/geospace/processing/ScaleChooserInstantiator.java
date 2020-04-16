package org.integratedmodelling.klab.components.geospace.processing;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.data.encoding.VisitingDataBuilder;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Triple;

/**
 * Abstract instantiator that uses a list of increasingly detailed spatial
 * resources and chooses the one with extents that are most likely to incarnate
 * the object in the specific region. Any spatial URNs supplied should normally
 * have the intersect=false parameter to prevent breaking up shapes to fit the
 * context.
 * <p>
 * Resources must be supplied in a <b>sorted</b> list from coarser to finer
 * scaled.
 * 
 * @author Ferd
 *
 */
public abstract class ScaleChooserInstantiator implements IInstantiator {

	/**
	 * If true, always cover the context completely: either by choosing a larger
	 * object or by adding even small proportion coverage with potentially very
	 * large objects.
	 */
	private boolean whole = false;

	/**
	 * Strategy used to cover the context
	 */
	public static enum Strategy {

		/**
		 * take as many smaller watersheds as it takes to compute the requested coverage
		 */
		COVER,

		/**
		 * Switch to the largest watershed even if it's larger than the context.
		 */
		EXTEND
	}

	/**
	 * Minimum coverage of context to include in results, unless whole is true.
	 */
	private double minCoverage = 0.45;
	private Strategy strategy = Strategy.COVER;
	private int maxObjects = -1;
	private boolean boundingBox;
	private boolean alignGrid;
	private int bufferCells = 0;
	private int detail = 0;

	public void setBoundingBox(boolean boundingBox) {
		this.boundingBox = boundingBox;
	}

	public void setAlignGrid(boolean alignGrid) {
		this.alignGrid = alignGrid;
	}

	public ScaleChooserInstantiator() {
	}

	protected ScaleChooserInstantiator(boolean whole) {
		this.whole = whole;
	}

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

	public void setWhole(boolean whole) {
		this.whole = whole;
	}

	public void setMinCoverage(double minCoverage) {
		this.minCoverage = minCoverage;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public void setMaxObjects(int maxObjects) {
		this.maxObjects = maxObjects;
	}

	/**
	 * Return all URNs for the resources to choose from. Must be in coarse to
	 * fine-scale order.
	 * 
	 * @return
	 */
	public abstract String[] getResourceUrns();

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context)
			throws KlabException {

		if (bufferCells > 0 && boundingBox) {
			alignGrid = true;
		}

		Grid grid = null;
		if (context.getScale().getSpace() instanceof Space) {
			grid = (Grid) ((Space) context.getScale().getSpace()).getGrid();
		}

		if (context.getScale().getSpace() == null || context.getScale().getSpace().getDimensionality() < 2) {
			throw new IllegalStateException("scaling instantiator must be executed in a 2-dimensional spatial context");
		}

		Integer np = null;
		int n = 0;
		Integer chosen = null;
		for (String urn : getResourceUrns()) {

			VisitingDataBuilder builder = new VisitingDataBuilder();
			Resources.INSTANCE.getResourceData(urn, builder, context.getScale(), context.getMonitor());
			context.getMonitor().debug("#" + builder.getObjectCount() + " in " + urn);
			if (np != null) {
				if (builder.getObjectCount() > np) {
					if (chosen == null) {
						chosen = n;
					}
					break;
				}
			}
			np = builder.getObjectCount();
			n++;
		}

		context.getMonitor().debug("chosen level " + chosen);
		
		/*
		 * adjust: we have stopped BEFORE the number went up, so as a default we go to
		 * the next level TODO: this depends on the strategy
		 */
		if (strategy == Strategy.COVER && chosen < (getResourceUrns().length - 1)) {
			chosen++;
		}

		chosen += detail;
		
		context.getMonitor().debug("adjusted level " + chosen + ": " + getResourceUrns()[chosen]);

		List<IObjectArtifact> ret = new ArrayList<>();

		// keep name, scale and metadata for later use
		List<Triple<String, IScale, IMetadata>> tmp = new ArrayList<>();
		List<Triple<String, IScale, IMetadata>> keep = new ArrayList<>();

		if (chosen < getResourceUrns().length) {
			
			VisitingDataBuilder builder = new VisitingDataBuilder();
			Resources.INSTANCE.getResourceData(getResourceUrns()[chosen], builder, context.getScale(),
					context.getMonitor());
			
			for (int i = 0; i < builder.getObjectCount(); i++) {
				tmp.add(new Triple<>(builder.getObjectName(i), builder.getObjectScale(i),
						builder.getObjectMetadata(i)));
			}
			
			context.getMonitor().debug("Object pool contains " + tmp.size() + " objects");
		} else {
			context.getMonitor().warn("Context scale is too small to select any objects with these parameters");
		}

		/*
		 * choose according to criteria
		 */
		IShape shape = context.getScale().getSpace().getShape();
		double ctxarea = shape.getStandardizedArea();

		n = -1;
		for (Triple<String, IScale, IMetadata> data : tmp) {

			boolean ok = whole;
			n++;
			
			if (!ok) {

				/*
				 * choose those where cover >= min coverage
				 */
				IShape space = data.getSecond().getSpace().getShape();
				IShape commn = space.intersection(shape);
				double coverage = commn.getStandardizedArea() / space.getStandardizedArea();
				context.getMonitor().debug("object #" + n + " covers " + coverage);
				ok = coverage >= minCoverage;

				/*
				 * 
				 */

				if (ok) {
					keep.add(data);
				}

			} else {
				keep.add(data);
			}

		}

		if (maxObjects > 0 && keep.size() > maxObjects) {
			// sort keep by decreasing area proportion
			// remove anything left beyond maxObjects
		}

		context.getMonitor().debug("Scale-dependent instantiator selected " + keep.size() + " objects out of a pool of " + tmp.size());
		
		// make the objects
		for (Triple<String, IScale, IMetadata> data : keep) {

			IScale scale = data.getSecond();
			if (boundingBox) {
				IShape bbox = Shape.create((Envelope) scale.getSpace().getShape().getEnvelope());
				scale = Scale.substituteExtent(scale,
						grid == null ? bbox : Space.create((Shape) bbox, grid, alignGrid));
			}

			ret.add(context.newObservation(semantics, data.getFirst(), scale, data.getThird()));
		}

		return ret;
	}

	public void setBufferCells(int bufferCells) {
		this.bufferCells = bufferCells;
	}

	public void setDetail(int detail) {
		this.detail = detail;
	}

}

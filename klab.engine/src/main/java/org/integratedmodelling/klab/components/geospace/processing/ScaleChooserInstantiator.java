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
	private double minCoverage = 0.2;
	private Strategy strategy = Strategy.COVER;
	private int maxObjects = -1;
	private boolean boundingBox;
	private boolean alignGrid;
	private int bufferCells = 0;
	
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
	 * Return all URNs for the resources to choose from. Must be in coarse to fine-scale order.
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

		if (context.getScale().getSpace() == null || context.getScale().getSpace().getDimensionality() < 2) {
			throw new IllegalStateException("scaling instantiator must be executed in a 2-dimensional spatial context");
		}

		Integer np = null;
		int n = 0;
		for (String urn : getResourceUrns()) {

			VisitingDataBuilder builder = new VisitingDataBuilder();
			Resources.INSTANCE.getResourceData(urn, builder, context.getScale(), context.getMonitor());
			// TODO use the previous or the next according to strategy
			if (np != null) {
				if (builder.getObjectCount() > np) {
					break;
				}
			}
			np = builder.getObjectCount();
			n++;
		}

		List<IObjectArtifact> ret = new ArrayList<>();

		// keep name, scale and metadata for later use
		List<Triple<String, IScale, IMetadata>> tmp = new ArrayList<>();
		List<Triple<String, IScale, IMetadata>> keep = new ArrayList<>();

		if (n < getResourceUrns().length) {
			VisitingDataBuilder builder = new VisitingDataBuilder();
			Resources.INSTANCE.getResourceData(getResourceUrns()[n], builder, context.getScale(), context.getMonitor());
			for (int i = 0; i < builder.getObjectCount(); i++) {
				tmp.add(new Triple<>(builder.getObjectName(i), builder.getObjectScale(i),
						builder.getObjectMetadata(i)));
			}
		}

		/*
		 * choose according to criteria
		 */
		IShape shape = context.getScale().getSpace().getShape();
		double ctxarea = shape.getStandardizedArea();
		
		for (Triple<String, IScale, IMetadata> data : tmp) {

			boolean ok = whole;

			if (!ok) {

				/*
				 * choose those where cover >= min coverage
				 */
				IShape space = data.getSecond().getSpace().getShape();
				IShape commn = shape.intersection(space);
				ok = (commn.getStandardizedArea()/ctxarea) >= minCoverage;

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

		// make the objects
		for (Triple<String, IScale, IMetadata> data : keep) {
			
			IScale scale = data.getSecond();
			if (boundingBox) {
				scale = Scale.substituteExtent(scale, scale.getSpace().getShape().getBoundingExtent());
			}
			
			if (alignGrid) {
				// TODO!
			}
			
			ret.add(context.newObservation(semantics, data.getFirst(), scale, data.getThird()));
		}

		return ret;
	}

	public void setBufferCells(int bufferCells) {
		this.bufferCells = bufferCells;
	}

}

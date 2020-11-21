package org.integratedmodelling.klab.components.geospace.indexing;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.data.encoding.VisitingDataBuilder;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Triple;

public class ScalingResourceExtractor {

	public ScalingResourceExtractor(String[] resources) {
		this.resourceUrns = resources;
	}
	
	/**
	 * If true, always cover the context completen coverage with potentially very
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
	private int detail = 0;
	private String[] resourceUrns;
	private String nameAttribute;
	private String labelAttribute;

	public void setBoundingBox(boolean boundingBox) {
		this.boundingBox = boundingBox;
	}

	public ScalingResourceExtractor() {
	}
	
	public void setNameAttribute(String name) {
		this.nameAttribute = name;
	}

	public void setLabelAttribute(String name) {
		this.labelAttribute = name;
	}

	protected ScalingResourceExtractor(boolean whole) {
		this.whole = whole;
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

	public List<IShape> instantiate(IEnvelope envelope, IMonitor monitor) throws KlabException {

		IShape shape = envelope.asShape();
		Scale skale = Scale.create(shape);
		Integer np = null;
		int n = 0;
		Integer chosen = null;
		for (String urn : resourceUrns) {

			IKlabData data = Resources.INSTANCE.getResourceData(urn, new VisitingDataBuilder(), skale, monitor);
			monitor.debug("#" + data.getObjectCount() + " in " + urn);
			if (np != null) {
				if (data.getObjectCount() > np) {
					if (chosen == null) {
						chosen = n;
					}
					break;
				}
			}
			np = data.getObjectCount();
			n++;
		}

		List<IShape> ret = new ArrayList<>();

		if (chosen == null) {
			monitor.warn("scaling extractor: k.LAB resources did not respond or did not match the context");
			return ret;
		}

		monitor.debug("chosen level " + chosen);

		/*
		 * adjust: we have stopped BEFORE the number went up, so as a default we go to
		 * the next level TODO: this depends on the strategy
		 */
		if (strategy == Strategy.COVER && chosen < (resourceUrns.length - 1)) {
			chosen++;
		}

		chosen += detail;

		monitor.debug("adjusted level " + chosen + ": " + resourceUrns[chosen]);

		// keep name, scale and metadata for later use
		List<Triple<String, IScale, IMetadata>> tmp = new ArrayList<>();
		List<Triple<String, IScale, IMetadata>> keep = new ArrayList<>();

		if (chosen < resourceUrns.length) {

			IKlabData data = Resources.INSTANCE.getResourceData(resourceUrns[chosen], new VisitingDataBuilder(), skale,
					monitor);

			for (int i = 0; i < data.getObjectCount(); i++) {
				tmp.add(new Triple<>(data.getObjectName(i), data.getObjectScale(i), data.getObjectMetadata(i)));
			}

			monitor.debug("Object pool contains " + tmp.size() + " objects");

		} else {
			monitor.warn("Context scale is too small to select any objects with these parameters");
		}

		/*
		 * choose according to criteria
		 */
		double ctxarea = envelope.asShape().getStandardizedArea();

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
				monitor.debug("object #" + n + " covers " + coverage);
				ok = coverage >= minCoverage;
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

		monitor.debug(
				"Scaling extractor selected " + keep.size() + " objects out of a pool of " + tmp.size());

		// make the objects
		for (Triple<String, IScale, IMetadata> data : keep) {

			IScale scorop = data.getSecond();
			IShape sporop = scorop.getSpace().getShape();
			if (boundingBox) {
				sporop = Shape.create((Envelope) data.getSecond().getSpace().getShape().getEnvelope());
			}
			sporop.getMetadata().putAll(data.getThird());
			// TODO name/description!
			ret.add(sporop);
		}

		return ret;
	}

	public void setDetail(int detail) {
		this.detail = detail;
	}

}

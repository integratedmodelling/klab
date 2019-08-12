package org.integratedmodelling.klab.scale;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.utils.IPair;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.InstanceIdentifier;
import org.integratedmodelling.klab.utils.MultidimensionalCursor;
import org.integratedmodelling.klab.utils.NameGenerator;

public class Scale implements IScale {

	// private static AtomicLong counter = new AtomicLong(0);
	private transient String scaleId = NameGenerator.shortUUID();

	/**
	 * 
	 */
	private static final long serialVersionUID = -7855922162677333636L;

	/*
	 * the underlying geometry, only built when required
	 */
	private Geometry geometry = null;

	/**
	 * Mediators are created by extents and are used to implement views of a state
	 * that mediate values to another scale.
	 * <p>
	 * A mediator should be aware that the extents it mediates may have changed (it
	 * can use States.hasChanged() to inspect that) and be able to readjust if
	 * necessary. This will properly handle moving agents.
	 * <p>
	 * A <strong>conformant</strong> mediator is one where the mapping is
	 * offset-to-offset, i.e. no aggregation or distribution is necessary. This for
	 * example applies to grid-to-grid mediators where one grid is a simple subset
	 * of the other. Conformant mediators should be produced whenever possible, for
	 * example as a result of applying a scale resulting from a merge() of a subset
	 * on the original.
	 * <p>
	 * FIXME all mediators should be change listeners for the mediated scale, and
	 * rearrange the mediation strategy at each change as needed - we should
	 * subscribe them automatically.
	 * 
	 * @author ferdinando.villa
	 */
	public interface Mediator {

		/**
		 * The kind of aggregation that the mediation implies.
		 * 
		 * @return aggregation type
		 */
		Aggregation getAggregation();

		/**
		 * Apply the passed value to our scale and return the result.
		 * 
		 * @param value
		 * @param index
		 * @return a mediated object
		 */
		Object mediateTo(Object value, long index);

		/**
		 * Reduce the passed collection of pairs (value, weight) to one value according
		 * to aggregation strategy.
		 * 
		 * @param toReduce
		 * @param metadata a map to fill with any relevant statistics related to the
		 *                 aggregation (errors, uncertainty, boundaries, distributions,
		 *                 truth values etc) using the keys above.
		 * 
		 * @return the reduced value
		 */
		Object reduce(Collection<IPair<Object, Double>> toReduce, IMetadata metadata);
	}

	protected List<IExtent> extents = new ArrayList<>();
	protected long multiplicity = 0;
	protected int sIndex = -1;
	protected int tIndex = -1;
	protected ITime time = null;
	protected ISpace space = null;
	protected MultidimensionalCursor cursor;

	// this is copied to transitions so that we can quickly assess if two
	// transitions
	// come from the same scale.
	protected InstanceIdentifier identifier = new InstanceIdentifier();

	/*
	 * Next four are to support subscales built as views of another
	 */
	// originalCursor != null means we derive from a previous scale and are
	// representing
	// one slice of it...
	private MultidimensionalCursor originalCursor = null;
	// ... identified by this offset...
	private long sliceOffset = -1;
	// ... along this dimension
	private int sliceDimension = -1;
	// the originating scale. If size() == 1, we can locate directly in it using the
	// offset below.
	private Scale originalScale = null;
	// the offset in the original scale (only applies if originalScaleId > 0);
	long originalScaleOffset = -1;

	private boolean isInfiniteTime;

	// if not null, we're locating offsets of another scale
	private long[] locatedOffsets;

	@Override
	public boolean isInfiniteTime() {
		return isInfiniteTime;
	}

	protected Scale() {
	}

	protected Scale(Collection<IExtent> extents) {
		for (IExtent e : extents) {
			mergeExtent(e);
		}
		sort();
	}

	private Scale(IExtent[] topologies, MultidimensionalCursor cursor, int sliceExtentIndex, long sliceExtentOffset)
			throws KlabException {

		originalCursor = cursor;
		sliceDimension = sliceExtentIndex;
		sliceOffset = sliceExtentOffset;

		for (IExtent e : topologies) {
			mergeExtent(e);
		}
	}

	/**
	 * 1-sized scale localized to the position passed in the parent scale, and
	 * needing no sort. Used as the return value of a scale iterator going through
	 * all states.
	 * 
	 * @param scale
	 * @param offset
	 */
	public Scale(Scale scale, long offset) {
		this.originalScale = scale;
		this.multiplicity = 1;
		setLocatorsTo(offset);
	}

	/**
	 * Call ONLY on a scale locator created with the above constructor, to reset the
	 * offsets to the passed one.
	 * 
	 * @param offset
	 */
	public void setLocatorsTo(long offset) {

		this.originalScaleOffset = offset;
		this.locatedOffsets= this.originalScale.cursor.getElementIndexes(offset);
		
		for (int i = 0; i < this.originalScale.extents.size(); i++) {
			IExtent ext = this.originalScale.extents.get(i) instanceof Extent
					? ((Extent) this.originalScale.extents.get(i)).getExtent(this.locatedOffsets[i])
					: this.originalScale.extents.get(i);
			this.extents.add(ext);
			if (ext instanceof ISpace) {
				this.space = (ISpace) ext;
			} else if (ext instanceof ITime) {
				this.time = (ITime) ext;
			}
		}
	}

	/**
	 * Scale localizing one dimension to the position passed. If this determines a
	 * 1-sized scale, quickly set parent scale and offset in super so this can be
	 * used as a quick locator in it.
	 * 
	 * @param scale
	 * @param dimension
	 * @param offsets
	 */
	public Scale(Scale scale, Dimension.Type dimension, long... offsets) {

		// if only the passed dimension has size > 1, just set the offset and leave
		boolean simple = true;
		long[] pos = scale.cursor.getExtents();
		long expos = 0;
		int i = 0;
		for (IExtent extent : scale.extents) {
			if (extent.getType() != dimension && extent.size() > 1) {
				simple = false;
			}
			pos[i++] = extent.getType() == dimension ? (expos = ((AbstractExtent) extent).getOffset(offsets))
					: extent.size();
		}

		for (IExtent extent : scale.extents) {
			if (extent.getType() == dimension) {
				extents.add(((Extent) extent).getExtent(expos));
			} else {
				extents.add(extent);
			}
			if (extent.getType() == Dimension.Type.SPACE) {
				space = (ISpace) extents.get(extents.size() - 1);
			} else if (extent.getType() == Dimension.Type.TIME) {
				time = (ITime) extents.get(extents.size() - 1);
			}
		}

		if (simple) {
			this.originalScaleOffset = expos;
			this.originalScale = scale;
		}
	}

	/**
	 * Create a scale like the passed one, adding the passed extents or substituting
	 * existing ones of the same type.
	 * 
	 * @param scale
	 * @param extents
	 * @return a new scale
	 */
	public static Scale createLike(IScale scale, IExtent... extents) {
		List<IExtent> exts = new ArrayList<>();
		for (IExtent e : extents) {
			exts.add(e);
		}
		for (IExtent existing : scale.getExtents()) {
			boolean add = true;
			for (IExtent added : exts) {
				if (added.getType() == existing.getType()) {
					add = false;
					break;
				}
			}
			if (add) {
				exts.add(((AbstractExtent) existing).copy());
			}
		}
		return create(exts);
	}

	/**
	 * Like {@link #createLike(IScale, IExtent...)} with a collection.
	 * 
	 * @param scale
	 * @param extents
	 * @return a new scale
	 */
	public static Scale createLike(IScale scale, Collection<IExtent> extents) {
		return createLike(scale, extents.toArray(new IExtent[extents.size()]));
	}

	/**
	 * Create a scale from an array of extents.
	 * 
	 * TODO this should be able to create a ICoverage when extents are partially
	 * specified.
	 * 
	 * @param extents
	 * @return a new scale
	 */
	public static Scale create(IExtent... extents) {
		Scale ret = new Scale();
		if (extents != null) {
			for (IExtent e : extents) {
				ret.mergeExtent(e);
			}
		}
//		ret.sort();
		return ret;
	}

	/**
	 * Create from either another scale or a simpler geometry.
	 * 
	 * @param geometry
	 * @return a new scale
	 * @throw {@link IllegalArgumentException} if the argument is a geometry without
	 *        sufficient information to build a scale.
	 */
	public static Scale create(IGeometry geometry) {
		if (geometry instanceof Scale) {
			return createLike((Scale) geometry, new IExtent[] {});
		}
		List<IExtent> extents = new ArrayList<>();
		for (Dimension dimension : geometry.getDimensions()) {
			if (dimension.getType() == Type.SPACE) {
				extents.add(Space.create(dimension));
			} else if (dimension.getType() == Type.TIME) {
				extents.add(Time.create(dimension));
			}
			// TODO ELSE
		}
		return create(extents);
	}

	/**
	 * Create a scale from a collection of extents.
	 * 
	 * @param extents
	 * @return a new scale
	 */
	public static Scale create(Collection<IExtent> extents) {
		return create(extents.toArray(new IExtent[extents.size()]));
	}

	public List<IServiceCall> getKimSpecification() {
		List<IServiceCall> ret = new ArrayList<>();
		for (IExtent extent : extents) {
			ret.add(((AbstractExtent) extent).getKimSpecification());
		}
		return ret;
	}

	// /**
	// * Get an index to loop over one dimension (set as -1) given fixed position
	// for all others, only
	// * considering the sliceIndex-th part of the field from a total number of
	// slices = sliceNumber.
	// * Used for parallelization of loops.
	// *
	// * @param sliceIndex
	// * @param sliceNumber
	// * @param locators
	// *
	// * @return an iterator as requested
	// */
	// public final ICursor getCursor(int sliceIndex, int sliceNumber, Locator...
	// locators) {
	//
	// int variableDimension = -1;
	// long[] exts = new long[getExtentCount()];
	// Arrays.fill(exts, Extent.GENERIC_LOCATOR);
	// int i = 0;
	// for (IExtent e : extents) {
	// for (Locator o : locators) {
	// long n = ((Extent) e).locate(o);
	// if (n != Extent.INAPPROPRIATE_LOCATOR) {
	// exts[i] = n;
	// break;
	// }
	// }
	// i++;
	// }
	//
	// /*
	// *
	// */
	// int nm = 0;
	// for (i = 0; i < exts.length; i++) {
	// if (exts[i] == Extent.GENERIC_LOCATOR) {
	// nm++;
	// variableDimension = i;
	// }
	// }
	//
	// if (nm > 1) {
	// throw new KlabRuntimeException("cannot iterate a scale along more than one
	// dimensions");
	// }
	//
	// return new Cursor(extents,
	// cursor.getDimensionScanner(variableDimension, exts, sliceIndex, sliceNumber),
	// cursor,
	// variableDimension);
	// }

	// @Override
	// public final ICursor getCursor(Locator... locators) {
	//
	// int variableDimension = -1;
	// long[] exts = new long[getExtentCount()];
	// Arrays.fill(exts, Extent.GENERIC_LOCATOR);
	// int i = 0;
	// for (IExtent e : extents) {
	// for (Locator o : locators) {
	// long n = ((Extent) e).locate(o);
	// if (n != Extent.INAPPROPRIATE_LOCATOR) {
	// exts[i] = n;
	// break;
	// }
	// }
	// i++;
	// }
	//
	// /*
	// *
	// */
	// int nm = 0;
	// for (i = 0; i < exts.length; i++) {
	// if (exts[i] == Extent.GENERIC_LOCATOR) {
	// nm++;
	// variableDimension = i;
	// }
	// }
	//
	// if (nm == 0) {
	// return new Cursor(cursor.getElementOffset(exts));
	// }
	//
	// if (nm > 1) {
	// throw new KlabRuntimeException("cannot iterate a scale along more than one
	// dimensions");
	// }
	//
	// return new Cursor(extents, cursor.getDimensionScanner(variableDimension,
	// exts), cursor,
	// variableDimension);
	// }

	private class ScaleIterator implements Iterator<IScale> {

		long offset = 0;

		@Override
		public boolean hasNext() {
			return offset < size();
		}

		@Override
		public IScale next() {
			IScale ret = new Scale(Scale.this, offset);
			this.offset++;
			return ret;
		}
	}

	public long getExtentOffset(IExtent extent, long overallOffset) {
		int n = 0;
		boolean found = false;
		for (IExtent e : extents) {
			if (e.getType().equals(extent.getType())) {
				found = true;
				break;
			}
			n++;
		}
		if (!found) {
			throw new IllegalArgumentException("cannot locate extent " + extent.getType() + " in scale");
		}
		return Scale.this.cursor.getElementIndexes(overallOffset)[n];
	}

	@Override
	public boolean isTemporallyDistributed() {
		return getTime() != null && getTime().size() > 1;
	}

	@Override
	public boolean isSpatiallyDistributed() {
		return getSpace() != null && getSpace().size() > 1;
	}

	protected void sort() {

		ArrayList<IExtent> order = new ArrayList<>(extents);

		/*
		 * Is it fair to think that if two extent concepts have an ordering
		 * relationship, they should know about each other? So that we can implement the
		 * ordering as a relationship between extent observation classes? For now, all
		 * we care about is that time, if present, comes first.
		 */
		Collections.sort(order, new Comparator<IExtent>() {

			@Override
			public int compare(IExtent o1, IExtent o2) {
				// neg if o1 < o2
				boolean o1t = o1 instanceof ITime;
				boolean o2t = o2 instanceof ITime;
				if (o1t && !o2t) {
					return -1;
				}
				if (!o1t && o2t) {
					return 1;
				}
				return 0;
			}
		});

		multiplicity = 1L;
		int idx = 0;
		for (IExtent e : order) {

			if (e.getType() == Dimension.Type.TIME) {
				tIndex = idx;
				time = (ITime) e;
			} else if (e.getType() == Dimension.Type.SPACE) {
				sIndex = idx;
				space = (ISpace) e;
			}

			if (e.size() != Geometry.INFINITE_SIZE) {
				multiplicity *= e.size();
			} else {
				isInfiniteTime = true;
			}

			idx++;
		}

		// better safe than sorry. Only time can be infinite so this should be pretty
		// safe
		// as long as the comparator above works.
		if (isInfiniteTime && extents.get(0).size() != Geometry.INFINITE_SIZE) {
			throw new KlabInternalErrorException("internal error: infinite dimension is not the first in scale");
		}

		// recompute strided offsets for quick extent access
		cursor = new MultidimensionalCursor();
		long[] dims = new long[isInfiniteTime ? extents.size() - 1 : extents.size()];
		int n = 0;
		for (int i = isInfiniteTime ? 1 : 0; i < extents.size(); i++) {
			dims[n++] = extents.get(i).size();
		}
		cursor.defineDimensions(dims);
		extents = order;
		geometry = null;
	}

	@Override
	public int getExtentCount() {
		return extents.size();
	}

	@Override
	public ISpace getSpace() {
		return space;
	}

	@Override
	public ITime getTime() {
		return time;
	}

	public boolean isCovered(long offset) {
		long[] oofs = getExtentIndex(offset);
		for (int i = 0; i < getExtentCount(); i++) {
			if (!((Extent) extents.get(i)).isCovered(oofs[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean isConsistent() {
		for (int i = 0; i < getExtentCount(); i++) {
			if (!((Extent) extents.get(i)).isConsistent()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public long size() {
		return multiplicity;
	}

	@Override
	public boolean contains(IScale scale) throws KlabException {

		for (IExtent e : extents) {
			if (scale.getDimension(e.getType()) == null || !e.contains(((Scale) scale).getDimension(e.getType()))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean overlaps(IScale scale) throws KlabException {

		if (!hasSameExtents(scale)) {
			return false;
		}

		for (IExtent e : extents) {
			if (!e.overlaps(((Scale) scale).getDimension(e.getType()))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean intersects(IScale scale) throws KlabException {

		if (!hasSameExtents(scale)) {
			return false;
		}

		for (IExtent e : extents) {
			if (!e.intersects(((Scale) scale).getDimension(e.getType()))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Add a missing extent or use the custom merge() function to inherit the usable
	 * info from the passed one. Do not confuse this with the ones from ITopology.
	 * 
	 * @param extent
	 */
	public void mergeExtent(IExtent extent) {

		boolean merged = false;
		// int i = 0;
		for (IExtent e : extents) {
			if (e.getType().equals(extent.getType())) {
				merged = true;
				e.merge(extent);
				break;
			}
			// i++;
		}

		if (!merged) {
			// extents.add(i, merged);
			// ((AbstractExtent) merged).setScaleId(getScaleId());
			// } else {
			extents.add((AbstractExtent) extent);
			((AbstractExtent) extent).setScaleId(getScaleId());
		}

		sort();
	}

	private void mergeExtent(IExtent extent, LogicalConnector how) {

		IExtent merged = null;
		int i = 0;
		for (IExtent e : extents) {
			if (e.getType().equals(extent.getType())) {
				merged = e.merge(extent, how);
				break;
			}
			i++;
		}

		if (merged != null) {
			extents.add(i, merged);
		} else {
			extents.add(extent);
		}

		sort();
	}

	/**
	 * Return a collection of scales with multiplicity 1, one per each combination
	 * of the extent states we represent.
	 *
	 * @return disaggregated scales
	 * @throws KlabException
	 */
	public Collection<IScale> disaggregate() throws KlabException {

		ArrayList<IScale> ret = new ArrayList<>();

		long[] dims = new long[extents.size()];
		for (int i = 0; i < dims.length; i++) {
			dims[i] = extents.get(i).size();
		}

		MultidimensionalCursor cursor = new MultidimensionalCursor();
		cursor.defineDimensions(dims);

		for (int i = 0; i < cursor.getMultiplicity(); i++) {
			IExtent[] exts = new IExtent[dims.length];
			long[] idx = cursor.getElementIndexes(i);
			for (int j = 0; j < exts.length; j++) {
				exts[j] = ((Extent) extents.get(j)).getExtent(idx[j]);
			}
			ret.add(create(exts));
		}

		return ret;

	}

	/*
	 * quick access to "current" T state index for given offset - not in the API for
	 * now.
	 */
	public long getTimeIndex(long globalIndex) {
		return tIndex == -1 ? -1 : cursor.getElementIndexes(globalIndex)[tIndex];
	}

	/*
	 * quick access to "current" S state index for given offset - not in the API for
	 * now.
	 */
	public long getSpaceIndex(long globalIndex) {
		return sIndex == -1 ? -1 : cursor.getElementIndexes(globalIndex)[sIndex];
	}

	/*
	 * quick access to "current" arbitrary state index for given offset - not in the
	 * API for now.
	 */
	public long[] getExtentIndex(long globalIndex) {
		return cursor.getElementIndexes(globalIndex);
	}

	/*
	 * true if the passed scale has the same extents as we do.
	 */
	boolean hasSameExtents(IScale scale) {

		for (IExtent e : scale.getExtents()) {
			if (getDimension(e.getType()) == null) {
				return false;
			}
		}

		for (IExtent e : extents) {
			if (scale.getDimension(e.getType()) == null) {
				return false;
			}
		}
		return true;
	}

	/*
	 * true if the passed scale has the same extents as we do.
	 */
	boolean hasEqualExtents(IScale scale) {
		if (!hasSameExtents(scale)) {
			return false;
		}
		for (int i = 0; i < extents.size(); i++) {
			if (!((Scale) scale).extents.get(i).equals(extents.get(i))) {
				return false;
			}
		}
		return true;
	}

	public List<IExtent> getExtents() {
		return extents;
	}

	/**
	 * Return the proportion of coverage of the extent that is covered the least by
	 * the corresponding extent in the passed scale.
	 *
	 * @param context
	 * @return coverage
	 */
	public double getCoverage(IScale context) {
		// TODO Auto-generated method stub
		return 1.0;
	}

	/**
	 * Return the proportion of coverage that the passed scale would add to the
	 * coverage of our own extents.
	 *
	 * @param mcov
	 * @return additional coverage
	 */
	public double getAdditionalCoverage(Scale mcov) {
		// TODO Auto-generated method stub
		return 1.0;
	}

	@Override
	public Scale merge(ITopologicallyComparable<?> coverage, LogicalConnector how) {

		if (coverage instanceof Scale) {

			Scale other = (Scale) coverage;
			Scale ret = new Scale();
			ArrayList<IExtent> common = new ArrayList<>();
			HashSet<Dimension.Type> commonConcepts = new HashSet<>();

			for (IExtent e : extents) {
				if (other.getDimension(e.getType()) != null) {
					common.add(e);
					commonConcepts.add(e.getType());
				} else {
					ret.mergeExtent(e, how);
				}
			}

			for (IExtent e : other.getExtents()) {
				if (ret.getDimension(e.getType()) == null && !commonConcepts.contains(e.getType())) {
					ret.mergeExtent(e, how);
				}
			}

			for (IExtent e : common) {
				IExtent oext = other.getDimension(e.getType());
				IExtent merged = (IExtent) e.merge(oext, how);
				ret.mergeExtent(merged);
			}

			return ret;
		}

		throw new IllegalArgumentException("Scale merge() called with a non-scale parameter");

	}

	@Override
	public String toString() {
		String ss = "";
		for (IExtent e : extents) {
			ss += "<" + e.getType() + " # " + e.size() + ">";
		}
		return "Scale #" + extents.size() + " " + ss;
	}

	@Override
	public boolean isEmpty() {

		for (IExtent e : extents) {
			if (((AbstractExtent) e).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Take in another scale and complete what's left of our specs by merging in its
	 * details. E.g., we're a bounding box, we get a grid resolution without extent,
	 * and we become a grid in that bounding box. Will only be called during
	 * resolution, so the queries should have selected compatible scales, but throw
	 * an exception if anything is not compatible.
	 * 
	 * @param scale
	 * @return harmonized scale
	 * @throws KlabException
	 */
	public IScale harmonize(IScale scale) throws KlabException {
		// TODO Auto-generated method stub
		return scale;
	}

	/**
	 * Get a scale that has either a 1-dimensional extent for the passed concept or
	 * doesn't have the extent at all (if offset < 0). Ensure this scale remembers
	 * its offset and previous multiplicity along the extent's dimension so that it
	 * will respond properly to getOffset() below.
	 * 
	 * TODO Possible improvement: allow passing an IExtent instead of an int, to
	 * accommodate variable scales and ease the use of the API in some
	 * circumstances. That will require a getExtentOffset(IExtent) method.
	 * 
	 * @param extent
	 * @param offset
	 * @return the subscale
	 */
	public IScale getSubscale(Dimension.Type extent, long offset) {

		int oridx = -1;
		ArrayList<IExtent> exts = new ArrayList<>();
		for (int i = 0; i < extents.size(); i++) {
			if (extents.get(i).getType().equals(extent)) {
				oridx = i;
				continue;
			}
			exts.add(extents.get(i));
		}

		if (oridx < 0) {
			return this;
		}

		return new Scale(exts.toArray(new IExtent[exts.size()]), cursor, oridx, offset);
	}

	public long getOriginalOffset(long subscaleOffset) {

		if (originalCursor == null) {
			return subscaleOffset;
		}

		long[] slcofs = cursor.getElementIndexes(subscaleOffset);
		long[] orgofs = new long[originalCursor.getDimensionsCount()];
		int on = 0;
		for (int i = 0; i < orgofs.length; i++) {
			orgofs[i] = i == sliceDimension ? sliceOffset : slcofs[on++];
		}
		return originalCursor.getElementOffset(orgofs);
	}

	public static IScale substituteExtent(IScale scale, IExtent extent) throws KlabException {

		List<IExtent> exts = new ArrayList<>();
		for (IExtent e : scale.getExtents()) {
			if (e.getType().equals(extent.getType())) {
				exts.add(extent);
			} else {
				exts.add(e);
			}
		}
		return create(exts.toArray(new IExtent[exts.size()]));
	}

	public IScale removeExtent(Type extentType) {

		List<IExtent> exts = new ArrayList<>();
		for (IExtent e : getExtents()) {
			if (!e.getType().equals(extentType)) {
				exts.add(e);
			}
		}
		return create(exts.toArray(new IExtent[exts.size()]));
	};

	/**
	 * Return a new scale with the passed domains collapsed into a 1-multiplicity
	 * extent.
	 * 
	 * @param domains
	 * @return a new scale
	 * @throws KlabException
	 */
	public Scale collapse(Dimension.Type... domains) throws KlabException {
		ArrayList<IExtent> extents = new ArrayList<>();
		for (IExtent e : this.extents) {
			boolean found = domains == null || domains.length == 0;
			if (!found) {
				for (Dimension.Type d : domains) {
					if (e.getType().equals(d)) {
						found = true;
						break;
					}
				}
			}
			extents.add(found ? e.collapse() : ((AbstractExtent) e).copy());
		}
		return create(extents);
	}

	@Override
	public Iterator<IScale> iterator() {
		return new ScaleIterator();
	}

	@Override
	public IGeometry getChild() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Dimension> getDimensions() {
		List<Dimension> ret = new ArrayList<>();
		ret.addAll(extents);
		return ret;
	}

	@Override
	public Granularity getGranularity() {
		return Granularity.SINGLE;
	}

	@Override
	public boolean isScalar() {
		return size() == 1;
	}

	@Override
	public IScale at(ILocator locator) {
		if (locator.equals(ITime.INITIALIZATION)) {
			if (getTime() == null || getTime().isGeneric()) {
				// I want you just the way you are. If generic, it should already be compatible
				// by design.
				return this;
			} else {
				// relocate to non-generic time 0
				return substituteExtent(this, ((Time)getTime()).getExtent(0));
			}
		} else if (locator instanceof IExtent) {
			if (((AbstractExtent) locator).isOwnExtent(this)) {
				// guarantees no mediation needed
			} else {
				// if we don't have this extent, illegal arg (or just return this?)
				// mediation may be needed
			}
		} else if (locator instanceof IScale) {
			if (((Scale) locator).getScaleId().equals(getScaleId())) {
				return this;
			}
			if (((Scale) locator).hasSameExtents(this)) {
				List<IExtent> exts = new ArrayList<>();
				for (int i = 0; i < extents.size(); i++) {
					IExtent ours = extents.get(i);
					IExtent hers = ((Scale) locator).extents.get(i);
					if (!ours.contains(hers)) {
						return null;
					}
					exts.add(hers);
				}
				return new Scale(exts);
			}
			// all-around mediation possible
		} else {
			throw new IllegalArgumentException("cannot use " + locator + " as a scale locator");
		}
		return null;
	}

	public Scale minus(Type extent) {
		List<IExtent> exts = new ArrayList<>();
		for (IExtent ext : extents) {
			if (ext.getType() != extent) {
				exts.add(ext);
			}
		}
		return create(exts);
	}

//	@Override
	public long getOffset(ILocator index) {

		if (index instanceof Geometry) {
			index = Scale.create((IGeometry) index);
		}

		// CHECK not matching the original scale ID any more (too restrictive) - it's an
		// act of faith whether
		// the scale comes from the original or not at this point.
		if (index instanceof Scale) {

			if (((Scale) index).originalScaleOffset >= 0) {
				return ((Scale) index).originalScaleOffset;
			}

			// locate each extent
			long[] offsets = new long[extents.size()];
			int i = 0;
			for (IExtent extent : extents) {
				IExtent other = ((Scale) index).getDimension(extent.getType());
				offsets[i++] = other == null ? 0 : extent.getOffset(other);
			}

			return cursor.getElementOffset(offsets);
		}

		/*
		 * TODO other mediatable scale
		 */

		/*
		 * TODO single extent locator
		 */
		if (index instanceof IExtent) {
			IExtent mext = getOnlyMultipleExtent(((IExtent) index).getType());
			if (mext != null) {
				// offset is the extent's offset in its extent
				return mext.getOffset(index);
			}

			/*
			 * TODO
			 */
		}
//		if (index instanceof OffsetLocator) {
//			return ((OffsetLocator) index).getOffset();
//		}

		throw new IllegalArgumentException("cannot use " + index + " as a scale locator");
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	private IExtent getOnlyMultipleExtent(Type type) {
		IExtent ret = null;
		for (IExtent extent : extents) {
			if (extent.getType() != type && extent.size() > 1) {
				return null;
			}
			if (extent.getType() == type) {
				ret = extent;
			}
		}
		return ret;
	}

	@Override
	public IScale at(Dimension.Type dimension, long... offsets) {
		return new Scale(Scale.this, dimension, offsets);
	}

	@Override
	public IExtent getDimension(Type type) {

		Dimension dimension = asGeometry().getDimension(type);
		if (dimension == null) {
			return null;
		}
		for (IExtent extent : extents) {
			if (extent.getType() == type) {
				// ensure the extent has its basic geometry set
				((AbstractExtent) extent).setDimension(dimension);
				return extent;
			}
		}
		return null;
	}

	@Override
	public Iterable<ILocator> over(Type dimension) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long[] shape(Type dimension) {
		for (IExtent extent : extents) {
			if (extent.getType() == dimension) {
				return extent.shape();
			}
		}
		throw new IllegalArgumentException("this scale does not contain the dimension " + dimension);
	}

	@Override
	public String encode() {
		String ret = "";
		for (IExtent extent : extents) {
			ret += ((AbstractExtent) extent).encode();
		}
		return ret;
	}

	/**
	 * Create a full copy as an independent object. Use to convert a Coverage into a
	 * proper Scale when necessary.
	 * 
	 * @return
	 */
	public Scale copy() {
		return Scale.create(extents);
	}

	/**
	 * Return the scale as the underlying non-semantic Geometry
	 * 
	 * @return the fully specified geometry underlying this scale
	 */
	public Geometry asGeometry() {
		if (this.geometry == null) {
			this.geometry = Geometry.create(encode());
		}
		return this.geometry;
	}

//	@Override
//	public ILocator getLocator(long offset) {
//		return asGeometry().getLocator(offset);
//	}

//	public ILocator getLocator(int... offsets) {
//		return asGeometry().locate(offsets);
//	}

//	@Override
//	public long getOffset(long globalOffset, Type dimension) {
//		return asGeometry().getOffset(globalOffset, dimension);
//	}

	/**
	 * Return a scale that has enough resolution to demonstrate an observation at
	 * our extents, without making it too large to visualize or too small to be
	 * useful.
	 * 
	 * @return a new scale
	 */
	public Scale adaptForExample() {
		List<IExtent> exts = new ArrayList<>();
		for (IExtent extent : getExtents()) {
			if (extent instanceof ISpace) {
				Shape shape = (Shape) ((ISpace) extent).getShape();
				// make it a grid with a good res for visualization
				exts.add(Space.create(shape, (double) shape.getEnvelope().getResolutionForZoomLevel(50, 2).getFirst()));
			} else if (extent instanceof ITime) {
				if (extent.size() > 1) {
					extents.add(extent.iterator().next());
				} else {
					exts.add(extent);
				}
			} else {
				exts.add(extent);
			}
		}
		return new Scale(exts);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T as(Class<T> cls) {
		
		if (Long.class.isAssignableFrom(cls)) {
			return (T)Long.valueOf(originalScaleOffset);
		} else if (Long[].class.isAssignableFrom(cls)) {
			Long[] ret = new Long[extents.size()];
			int i = 0;
			for (IExtent e : getExtents()) {
				if (locatedOffsets != null) {
					ret[i] = locatedOffsets[i];
					i++;
				} else {
					ret[i] = -1l;
				}
			}
			return (T)ret;
		}
		
		for (IExtent extent : getExtents()) {
			T ret = extent.as(cls);
			if (ret != null) {
				return ret;
			}
		}
		return null;
	}

	String getScaleId() {
		return scaleId;
	}

	public Spliterator<Scale> spliterator(final IMonitor monitor) {
		return new SplIt(0, size(), monitor);
	}

	class SplIt implements Spliterator<Scale> {

		Scale splitGrid = Scale.this;
		long beginSplit;
		long endSplit = size();
		long offset;
		IMonitor monitor;
		Scale locator;

		public SplIt(long begin, long end, IMonitor monitor) {
			this.beginSplit = begin;
			this.endSplit = end;
			this.monitor = monitor;
			while (!isCovered(begin) && offset < endSplit) {
				begin++;
			}
			this.offset = begin;
			this.locator = new Scale(Scale.this, begin);
		}

		@Override
		public int characteristics() {
			// CHECK not SIZED | SUBSIZED because of nodata cells in grids
			return NONNULL | CONCURRENT | IMMUTABLE;
		}

		@Override
		public long estimateSize() {
			return endSplit - beginSplit;
		}

		@Override
		public boolean tryAdvance(Consumer<? super Scale> arg0) {
			if (monitor.isInterrupted()) {
				return false;
			}
			if (offset < endSplit) {
				this.locator.setLocatorsTo(offset);
				arg0.accept(locator);
				offset++;
				while (!isCovered(offset) && offset < endSplit) {
					offset++;
				}
				return true;
			}
			return false;
		}

		@Override
		public Spliterator<Scale> trySplit() {

			if (!Configuration.INSTANCE.parallelizeContextualization() || monitor.isInterrupted()) {
				return null;
			}
			if (estimateSize() > 16) {
				long midOfs = (endSplit - beginSplit) / 2;
				long end = this.endSplit;
				this.endSplit = beginSplit + midOfs;
				return new SplIt(beginSplit + midOfs, end, monitor);
			}
			return null;
		}
	}

	@Override
	public boolean isGeneric() {
		for (IExtent extent : getExtents()) {
			if (!extent.isGeneric()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Scale merge(IScale scale) {

		if (scale == this || hasEqualExtents(scale)) {
			return this;
		}

		if (scale instanceof Scale) {

			Scale other = (Scale) scale;
			Scale ret = new Scale();
			ArrayList<IExtent> common = new ArrayList<>();
			HashSet<Dimension.Type> commonConcepts = new HashSet<>();

			for (IExtent e : extents) {
				if (other.getDimension(e.getType()) != null) {
					common.add(e);
					commonConcepts.add(e.getType());
				} else {
					ret.mergeExtent(e);
				}
			}

			for (IExtent e : other.getExtents()) {
				if (ret.getDimension(e.getType()) == null && !commonConcepts.contains(e.getType())) {
					ret.mergeExtent(e);
				}
			}

			for (IExtent e : common) {
				IExtent oext = other.getDimension(e.getType());
				IExtent merged = (IExtent) e.merge(oext);
				ret.mergeExtent(merged);
			}

			ret.scaleId = this.scaleId;

			return ret;
		}

		throw new IllegalArgumentException("Scale merge() called with a non-scale parameter");
	}

}

package org.integratedmodelling.klab.scale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.Geometry.DimensionTarget;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.utils.MultidimensionalCursor;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Utils;

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

	/*
	 * Next are to support subscales built as views of another
	 */
	// the originating scale. If size() == 1, we can locate directly in it using the
	// offset below.
	private Scale parentScale = null;
	// the root scale, used to access the original extents (the parent may itself be
	// a sub-located scale). This is only filled when iterating.
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

	public Scale getParentScale() {
		return parentScale;
	}
	
	protected Scale(Collection<IExtent> extents) {
		for (IExtent e : extents) {
			mergeExtent(e);
		}
		sort();
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
		this.parentScale = this.originalScale = scale;
		while (this.originalScale.parentScale != null) {
			this.originalScale = this.originalScale.parentScale;
		}
		this.multiplicity = 1;
		setLocatorsTo(offset);
	}

	/**
	 * Scale locator localized to the passed offset(s).
	 * 
	 * @param scale
	 * @param offset
	 */
	public Scale(Scale scale, Offset offset) {
		this.parentScale = this.originalScale = scale;
		while (this.originalScale.parentScale != null) {
			this.originalScale = this.originalScale.parentScale;
		}
		this.multiplicity = 1;
		if (offset.scalar) {
			setLocatorsTo(offset.linear);
		} else {
			setLocatorsTo(offset.pos);
		}
	}

	/**
	 * Call ONLY on a scale locator created with the above constructor, to reset the
	 * offsets to the passed one. The offset scans ONE dimension.
	 * 
	 * TODO if supporting >1 dimensions (i.e. more than just time and space), we 
	 * will need to fully support the scanning using the mdcursor rather than the
	 * fast strategy adopted here.
	 * 
	 * @param offset the 0-n offset for an iteration, always moving from 0 to the
	 *               state count in the current localization.
	 */
	public void setLocatorsTo(long offset) {

		/*
		 * SO: allocate a vector of offsets, initialize at -1. Go up the originalScale
		 * until it's null, setting each offset to the linearOffset of the located one
		 * in it. The resulting cursor will locate the root-level extents.
		 * 
		 */

		/**
		 * Inherit the already located offsets from all parents
		 */
		this.locatedOffsets = new long[parentScale.extents.size()];
		IExtent[] fixed = new IExtent[parentScale.extents.size()];
		Arrays.fill(this.locatedOffsets, -1);
		Scale parent = parentScale;
		while (parent != null) {
			if (parent.locatedOffsets != null) {
				for (int i = 0; i < parent.locatedOffsets.length; i++) {
					if (this.locatedOffsets[i] < 0 && parent.locatedOffsets[i] >= 0) {
						this.locatedOffsets[i] = parent.locatedOffsets[i];
						fixed[i] = parent.extents.get(i);
					}
				}
			}
			parent = parent.parentScale;
		}
		
		/*
		 * If >1 offset is -1, set all but the last @0. This will wreak havoc if we ever have >2 
		 * extents.
		 */
		boolean multiple = false;
		for (int i = 0; i < locatedOffsets.length; i++) {
			boolean last = i == locatedOffsets.length - 1;
			if (locatedOffsets[i] < 0) {
				if (last) {
					locatedOffsets[i] = offset;
				} else {
					locatedOffsets[i] = 0;
					if (multiple) {
						throw new KlabUnimplementedException("Scanning of multiple dimensions in subscales is unsupported");
					}
					multiple = true;
				}
			}
		}
		
		this.originalScaleOffset = this.originalScale.cursor.getElementOffset(locatedOffsets);
//		
//		this.originalScaleOffset = offset;
//		this.locatedOffsets = this.parentScale.cursor.getElementIndexes(offset);
		this.extents.clear();

		for (int i = 0; i < this.locatedOffsets.length; i++) {
			IExtent ext = fixed[i];
			if (ext == null) {
				ext = ((AbstractExtent)this.originalScale.extents.get(i)).getExtent(this.locatedOffsets[i]);
			}
			extents.add(ext);
			if (ext instanceof ISpace) {
				this.space = (ISpace) ext;
			} else if (ext instanceof ITime) {
				this.time = (ITime) ext;
			}
		}

		
//		for (int i = 0; i < this.parentScale.extents.size(); i++) {
//			IExtent ext = this.parentScale.extents.get(i) instanceof Extent
//					? ((Extent) this.parentScale.extents.get(i)).getExtent(this.locatedOffsets[i])
//					: this.parentScale.extents.get(i);
//			this.extents.add(ext);
//			if (ext instanceof ISpace) {
//				this.space = (ISpace) ext;
//			} else if (ext instanceof ITime) {
//				this.time = (ITime) ext;
//			}
//		}

		sort();

	}

	/**
	 * Call ONLY on a scale locator created with the above constructor, to reset the
	 * offsets to the passed one.
	 * 
	 * @param offset
	 */
	public void setLocatorsTo(long[] offset) {

		this.locatedOffsets = offset;
		this.multiplicity = 1;
		this.extents.clear();
		for (int i = 0; i < this.parentScale.extents.size(); i++) {

			IExtent newExt = this.parentScale.extents.get(i);
			if (offset[i] < 0 && newExt instanceof Extent) {
				newExt = ((Extent) newExt).getExtent(offset[i]);
			}
			this.extents.add(newExt);
			this.multiplicity *= newExt.size();
			if (newExt instanceof ISpace) {
				this.space = (ISpace) newExt;
			} else if (newExt instanceof ITime) {
				this.time = (ITime) newExt;
			}
		}

		sort();
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

	private class ScaleIterator implements Iterator<ILocator> {

		long offset = 0;

		@Override
		public boolean hasNext() {
			return offset < size();
		}

		@Override
		public IScale next() {
			IScale ret = new Scale(Scale.this, offset);
			this.offset++;
			while (this.offset < size() && !isCovered(offset)) {
				this.offset++;
			}
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

	protected Scale sort() {

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

			((AbstractExtent) e).setGeometry(this);

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
		// safe and not at all sorry, as long as the comparator above works.
		if (isInfiniteTime && extents.get(0).size() != Geometry.INFINITE_SIZE) {
			throw new KlabInternalErrorException("internal error: infinite dimension is not the first in scale");
		}

		extents = order;

		// recompute strided offsets for quick extent access
		cursor = new MultidimensionalCursor();
		long[] dims = new long[isInfiniteTime ? extents.size() - 1 : extents.size()];
		int n = 0;
		for (int i = isInfiniteTime ? 1 : 0; i < extents.size(); i++) {
			dims[n++] = extents.get(i).size();
		}
		cursor.defineDimensions(dims);
		geometry = null;

		return this;
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
			if (!((AbstractExtent) extents.get(i)).isCovered(oofs[i])) {
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
			// if (extents.size()>2) { System.out.println("Merda"); } extents.add(i,
			// merged);
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

	public IExtent getExtent(Dimension.Type type) {
		for (IExtent e : extents) {
			if (e.getType() == type) {
				return e;
			}
		}
		return null;
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
			ss += e + " ";
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

	public static Scale substituteExtent(IScale scale, IExtent extent) throws KlabException {

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
	public Iterator<ILocator> iterator() {
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
	public IScale at(Object... locators) {

		Scale targetScale = this;

//		/*
//		 * Special handling of time initialization: use scale w/o time unless time is
//		 * generic.
//		 */
//		boolean hasTimeInitialization = false;
//		for (Object l : locators) {
//			if (l == Time.INITIALIZATION) {
//				hasTimeInitialization = true;
//				break;
//			}
//		}
//
//		if (locators != null && locators.length > 0 && hasTimeInitialization) {
//			// remove initializer and proceed with scale w/o time unless generic
//			if (getTime() == null) {
//				// I want you just the way you are. If generic, it should already be compatible
//				// by design.
//				targetScale = this;
//			} else {
//				// FIXME/CHECK probably will need an initialization that still holds the period
//				// and step.
//				// initialization scale will run the dataflow w/o time.
//				// Dependencies have already been resolved properly to tune the resource on
//				// init, but the contextualizer won't know the time from the passed context.
//				targetScale = substituteExtent(targetScale, Time.INITIALIZATION);
//			}
//
//			if (locators.length == 1) {
//				return targetScale;
//			}
//
//			// if continuing, we use the remaining locators on the target.
//			Object[] newLocators = new Object[locators.length - 1];
//			for (int i = 0, l = 0; i < locators.length; i++) {
//				if (locators[i] == Time.INITIALIZATION) {
//					continue;
//				}
//				newLocators[l++] = locators[i];
//			}
//			locators = newLocators;
//		}

		/*
		 * reinterpret through augmented version of Geometry.as(locators).
		 */
		return locate(targetScale, Geometry.separateTargets(locators));

	}

	/*
	 * This gets a series of locators that are guaranteed to have either: one
	 * geometry, a set of extents, or the same stuff that Geometry.as() gets, with
	 * the ability to also handle double coordinates in lieu of offsets.
	 */
	private static Scale locate(Scale scale, List<DimensionTarget> targets) {

		if (targets.isEmpty()) {
			return scale;
		}

		if (!Geometry.hasShape(scale)) {
			// not possible currently, but possibly with constraints later.
			throw new IllegalStateException("Geometry has no specified shape: cannot create locators");
		}

		/*
		 * dimension-specific targets cannot be combined with an overall targets.
		 */
		DimensionTarget overall = null;
		for (DimensionTarget target : targets) {
			if (target.offsets != null && target.type == null) {
				overall = target;
				break;
			}
		}

		if (overall != null && targets.size() > 1) {
			throw new IllegalStateException(
					"Geometry cannot be located with both dimension-specific and overall locators");
		}

		if (overall != null) {
			return new Scale(scale, new Offset(scale, overall.offsets));
		}

		Map<Dimension.Type, Object[]> extdef = new HashMap<>();

		for (DimensionTarget t : targets) {

			List<Pair<Dimension.Type, Object[]>> defs = new ArrayList<>();

			if (t.geometry instanceof IScale) {
				// FIXME CHECK!
				return (Scale) t.geometry;
			} else if (t.geometry instanceof IGeometry) {

				// parameters may specify a location
				for (Dimension dimension : t.geometry.getDimensions()) {
					Object[] o = getLocatorParameters(dimension);
					if (o != null) {
						defs.add(new Pair<>(dimension.getType(), o));
					}
				}

			} else if (t.extent instanceof IExtent) {
				defs.add(new Pair<>(t.extent.getType(), new Object[] { t.extent }));
			} else if (t.extent instanceof Dimension) {
				Object[] o = Geometry.getLocatorParameters(t.extent);
				if (o != null) {
					defs.add(new Pair<>(t.extent.getType(), o));
				}
			} else if (t.type != null) {
				if (t.offsets != null) {
					defs.add(new Pair<>(t.type, Utils.boxArray(t.offsets)));
				} else if (t.coordinates != null) {
					defs.add(new Pair<>(t.type, Utils.boxArray(t.coordinates)));
				} else if (t.otherLocators != null) {
					defs.add(new Pair<>(t.type, t.otherLocators));
				}
			}

			for (Pair<Type, Object[]> def : defs) {
				if (extdef.containsKey(def.getFirst())) {
					throw new IllegalArgumentException("Scale locator contains duplicate specifications for "
							+ def.getFirst().name().toLowerCase());
				}
				extdef.put(def.getFirst(), def.getSecond());
			}

		}

		if (!extdef.isEmpty()) {

			/*
			 * reprocess extents using IExtent.at()
			 */
			List<IExtent> exts = new ArrayList<>();
			for (IExtent e : scale.getExtents()) {
				if (extdef.containsKey(e.getType())) {
					// this will throw an exception if the parameters aren't recognized
					exts.add(e.at(extdef.get(e.getType())));
				} else {
					exts.add(e);
				}
			}
			Scale ret = create(exts.toArray(new IExtent[exts.size()]));

			/*
			 * setup the located offsets
			 */
			int i = 0;

			ret.locatedOffsets = new long[scale.getExtents().size()];
			ret.parentScale = scale;
			for (IExtent e : ret.getExtents()) {
				long located = ((AbstractExtent) e).getLocatedOffset();
				if (located < 0) {
					// TODO/FIXME this is wrong but will do for now. Initialization should be
					// preserved and only initialization should allow this behavior.
					if (e.getType() == Type.TIME && e.size() == 1) {
						located = 0;
					}
				}
				ret.locatedOffsets[i++] = located;
			}

			scale = ret;
		}

		return scale.sort();
	}

	/**
	 * Convert any parameters in the dimension to locators that we can use
	 * directly. If none are specific of the implementation, default to the
	 * result of the implementation in {@link Geometry}.
	 * 
	 * @param dimension
	 * @return
	 */
	public static Object[] getLocatorParameters(Dimension dimension) {

		if (dimension.getType() == Dimension.Type.TIME) {
			if (dimension.getParameters().containsKey(Geometry.PARAMETER_TIME_LOCATOR)) {
				Object value = dimension.getParameters().get(Geometry.PARAMETER_TIME_LOCATOR);
				if (value instanceof Long && ((Long)value) > 0) {
					return new Object[] { new TimeInstant((Long)value) };
				}
			}
		}
		
		return Geometry.getLocatorParameters(dimension);
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

	public long getOffset(ILocator index) {

		if (index instanceof Offset) {
			return ((Offset) index).linear;
		}

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
//				offsets[i++] = other == null ? 0 : extent.getOffset(other);
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
//				return mext.getOffset(index);
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
					extents.add((IExtent) extent.iterator().next());
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
	public <T extends ILocator> T as(Class<T> cls) {

		if (IScale.class.isAssignableFrom(cls)) {
			return (T) this;
		}

		if (Offset.class.isAssignableFrom(cls)) {
			// make an offset for this one and return it. If it's a scale locator, it will
			// be a scalar
			// offset.
			if (locatedOffsets != null) {
				return (T) new Offset(this, locatedOffsets);
			} else {
				return (T) new Offset(this);
			}
		}

		for (IExtent extent : getExtents()) {
			T ret = extent.as(cls);
			if (ret != null) {
				return ret;
			}
		}
		return null;
	}

	public String getScaleId() {
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

		if (scale == this || scale.isEmpty() || hasEqualExtents(scale)) {
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

	@Override
	public IGeometry getGeometry() {
		// TODO maybe just the original scale
		return parentScale == null ? null : parentScale.asGeometry();
	}

	public boolean isConformant(Scale scale) {
		// easy way to tell is if this is a subset of the passed one, although it gets
		// messy afterwards...
		if (scale.equals(this) || (this.parentScale != null && this.parentScale.equals(scale))) {
			return true;
		}

		// ...so let's do it another time.
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ILocator> Iterable<T> scan(Class<T> desiredLocatorClass, Object... dimensionIdentifiers) {
		if (dimensionIdentifiers != null && dimensionIdentifiers.length == 2) {
			// typical case: scanning a grid with no time or time == 1
			if (getSpace() != null && ((Space) getSpace()).getGrid() != null
					&& (getTime() == null || getTime().size() == 1)) {
				if (dimensionIdentifiers[1] instanceof Class
						&& IGrid.class.isAssignableFrom((Class<?>) dimensionIdentifiers[1])) {
					if (dimensionIdentifiers[0] == Offset.class) {
						return (Iterable<T>) new GridOffsetScanner();
					} else if (dimensionIdentifiers[0] instanceof Class
							&& Cell.class.isAssignableFrom((Class<?>) dimensionIdentifiers[1])) {
						return (Iterable<T>) new GridCellScanner();
					}
				}
			}
		}

		throw new IllegalArgumentException("Scale scan() called with non-supported arguments: use normal iteration");

	}

	public class GridCellScanner implements Iterable<Cell> {

		class It implements Iterator<Cell> {

			int offset = 0;

			@Override
			public boolean hasNext() {
				return offset < space.size();
			}

			@Override
			public Cell next() {
				return ((Space) getSpace()).getGrid().getCell(offset++);
			}

		}

		@Override
		public Iterator<Cell> iterator() {
			return new It();
		}

	}

	public class GridOffsetScanner implements Iterable<Offset> {

		ThreadLocal<Offset> shuttle = new ThreadLocal<>();

		class It implements Iterator<Offset> {

			int offset = 0;

			It() {
				shuttle.set(Offset.create("0", minus(Dimension.Type.TIME)));
			}

			@Override
			public boolean hasNext() {
				return offset < space.size();
			}

			@Override
			public Offset next() {
				shuttle.get().linear = offset;
				long[] xy = ((Space) getSpace()).getGrid().getXYOffsets(offset++);
				shuttle.get().pos = xy;
				return shuttle.get();
			}

		}

		@Override
		public Iterator<Offset> iterator() {
			return new It();
		}

	}

	@Override
	public IScale initialization() {
		return getTime() == null ? this : at(Time.initialization(getTime()));
	}

	public static IScale empty() {
		return create();
	}

	@Override
	public boolean is(String string) {
		return asGeometry().is(string);
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Scale && ((Scale) o).asGeometry().equals(asGeometry());
	}

	@Override
	public int hashCode() {
		return asGeometry().hashCode();
	}

	@Override
	public IScale adopt(IScale scale, IMonitor monitor) {

		if (scale == this || scale.isEmpty() || hasEqualExtents(scale)) {
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
				IExtent merged = (IExtent) e.adopt(oext, monitor);
				ret.mergeExtent(merged);
			}

			return ret;
		}

		throw new IllegalArgumentException("Scale adopt() called with a non-scale parameter");
	}

	public long[] getLocatedOffsets() {
		return locatedOffsets;
	}

	@Override
	public IScale without(Type dimension) {
		return removeExtent(dimension);
	}

}

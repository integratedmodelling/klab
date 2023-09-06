package org.integratedmodelling.klab.components.geospace.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.space.Orientation;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.utils.Pair;

import groovy.util.Expando;

/**
 * Proxy for a cell with a knowledge of the flow directions and the context and added methods, to be
 * used in Groovy expressions to wrap the regular 'space' variable. Used in the generic flow
 * accumulation contextualizer.
 * 
 * @author ferdinando.villa
 *
 */
public class ContributingCell extends Expando {

    private Cell delegate;
    // non-null if this is relative to the focal cell
    private Orientation orientation = null;
    // original d8 code
    int d8 = -1;

    /*
     * if we have this, we can compute upstream and downstream cells.
     */
    private IState flowdirections = null;
    private Map<String, IState> states = new HashMap<>();
    private boolean outlet = false;
    private ILocator locator = null;

    public ContributingCell(Cell cell) {
        this.delegate = cell;
    }

    public ContributingCell(Cell cell, int flowDirection, IState flowdirections, Map<String, IState> states, boolean isOutlet) {
        this.delegate = cell;
        this.d8 = flowDirection;
        this.flowdirections = flowdirections;
        this.states.putAll(states);
        this.outlet = isOutlet;
        this.locator = cell;
    }

    public ContributingCell(Cell cell, int flowDirection, IState flowdirections, Map<String, IState> states, boolean isOutlet,
            ILocator locator) {
        this.delegate = cell;
        this.d8 = flowDirection;
        this.flowdirections = flowdirections;
        this.states.putAll(states);
        this.outlet = isOutlet;
        this.locator = locator;
    }

    public ContributingCell(Cell cell, ContributingCell focal, Orientation orientation) {
        this.delegate = cell;
        this.orientation = orientation;
        this.locator = cell;
        this.flowdirections = focal.flowdirections;
        this.d8 = this.flowdirections.get(cell, Double.class).intValue();
        this.states.putAll(focal.states);
    }

    public ContributingCell(Cell cell, ContributingCell focal, Orientation orientation, ILocator locator) {
        this.delegate = cell;
        this.orientation = orientation;
        this.locator = locator;
        this.flowdirections = focal.flowdirections;
        this.d8 = this.flowdirections.get(locator, Double.class).intValue();
        this.states.putAll(focal.states);
    }

    public List<ContributingCell> getUpstream() {
        List<ContributingCell> ret = new ArrayList<>();
        if (flowdirections != null) {
            for (Pair<Cell, Orientation> upstream : Geospace.getUpstreamCellsWithOrientation(delegate, flowdirections,
                    locator instanceof IScale ? ((IScale) locator).getTime() : null, null)) {
                ret.add(locator instanceof IScale
                        ? new ContributingCell(upstream.getFirst(), this, upstream.getSecond(), locator)
                        : new ContributingCell(upstream.getFirst(), this, upstream.getSecond()));
            }
        }
        return ret;
    }

    public ContributingCell getDownstream() {
        if (flowdirections != null) {
            Pair<Cell, Orientation> downstream = Geospace.getDownstreamCellWithOrientation(delegate, flowdirections);
            if (downstream != null) {
                return locator instanceof IScale
                        ? new ContributingCell(downstream.getFirst(), this, downstream.getSecond(), locator)
                        : new ContributingCell(downstream.getFirst(), this, downstream.getSecond());
            }
        }
        return null;
    }

    public List<ContributingCell> getNeighborhood() {
        List<ContributingCell> ret = new ArrayList<>();
        for (Orientation orientation : Orientation.values()) {
            Cell neighbor = delegate.getNeighbor(orientation);
            if (neighbor != null) {
                ret.add(locator instanceof IScale
                        ? new ContributingCell(neighbor, this, orientation, locator)
                        : new ContributingCell(neighbor, this, orientation));
            }
        }
        return ret;
    }

    /**
     * This returns all the values we know at the focal cell, accessible to Groovy directly from dot
     * notation.
     * 
     * @param state
     * @return
     */
    public Object getProperty(String state) {

        switch(state) {
        case "upstream":
            return getUpstream();
        case "downstream":
            return getDownstream();
        case "neighborhood":
        case "neighbourhood":
            return getNeighborhood();
        case "opposite":
            return opposite();
        case "outlet":
            return outlet;
        case "d8":
            return getD8();
        case "d8pow":
            return getD8pow();
        case "orientation":
            return orientation;
        case "nw":
            return NW();
        case "sw":
            return SW();
        case "ne":
            return NE();
        case "se":
            return SE();
        case "n":
            return N();
        case "w":
            return W();
        case "s":
            return S();
        case "e":
            return E();
        }

        if (states.containsKey(state)) {
            return states.get(state).get(this.locator);
        }
        return null;
    }

    /**
     * Will return non-null only when this is the result of a neighborhood operation on another
     * ContributingCell and we're not at the wrong edge.
     * 
     * @return
     */
    public ContributingCell opposite() {
        if (orientation != null) {
            Cell opposite = delegate.getNeighbor(orientation.getOpposite());
            if (opposite != null) {
                return locator == null
                        ? new ContributingCell(opposite, this, orientation.getOpposite(), locator)
                        : new ContributingCell(opposite, this, orientation.getOpposite());
            }
        }
        return null;
    }

    public boolean contains(IExtent o) throws KlabException {
        return delegate.contains(o);
    }

    public long size() {
        return delegate.size();
    }

    public IProjection getProjection() {
        return delegate.getProjection();
    }

    public <T extends ILocator> T as(Class<T> cls) {
        return delegate.as(cls);
    }

    public double getStandardizedArea() {
        return delegate.getStandardizedArea();
    }

    /**
     * Return the D8 code using the standard scheme (1-8 counterclockwise from east).
     * 
     * @return
     */
    public int getD8() {
        return d8;
    }

    /**
     * Return the D8 code in powers of 2 (1 to 128 in powers of 2 clockwise from east) like silly
     * ArcGIS does.
     * 
     * @return
     */
    public int getD8pow() {
        return Geospace.getD8Pow(d8);
    }

    /**
     * Diagonal in meters.
     * 
     * @return
     */
    public double getDiagonal() {
        return Math.sqrt((getWidth() * getWidth()) + ((getHeight() * getHeight())));
    }

    /**
     * Return the actual flow direction from this cell.
     * 
     * @return
     */
    public Orientation getFlowdirection() {
        if (d8 >= 0) {
            return Geospace.getOrientation(d8);
        }
        return null;
    }

    public Cell getNeighbor(Orientation orientation) {
        return delegate.getNeighbor(orientation);
    }

    public IExtent collapse() {
        return delegate.collapse();
    }

    public IEnvelope getEnvelope() {
        return delegate.getEnvelope();
    }

    public boolean overlaps(IExtent o) throws KlabException {
        return delegate.overlaps(o);
    }

    public boolean intersects(IExtent o) throws KlabException {
        return delegate.intersects(o);
    }

    public IExtent merge(IExtent extent) {
        return delegate.mergeContext(extent);
    }

    public SpatialExtent getExtentDescriptor() {
        return delegate.getExtentDescriptor();
    }

    public double getStandardizedVolume() {
        return delegate.getStandardizedVolume();
    }

    public ContributingCell N() {
        Cell cell = delegate.N();
        if (cell == null) {
            return null;
        }
        return locator == null
                ? new ContributingCell(cell, this, Orientation.N, locator)
                : new ContributingCell(cell, this, Orientation.N);
    }

    public ContributingCell S() {
        Cell cell = delegate.S();
        if (cell == null) {
            return null;
        }
        return locator == null
                ? new ContributingCell(delegate.S(), this, Orientation.S, locator)
                : new ContributingCell(delegate.S(), this, Orientation.S);
    }

    public ContributingCell W() {
        Cell cell = delegate.W();
        if (cell == null) {
            return null;
        }
        return locator == null
                ? new ContributingCell(delegate.W(), this, Orientation.W, locator)
                : new ContributingCell(delegate.W(), this, Orientation.W);
    }

    public ContributingCell E() {
        Cell cell = delegate.E();
        if (cell == null) {
            return null;
        }
        return locator == null
                ? new ContributingCell(delegate.W(), this, Orientation.E, locator)
                : new ContributingCell(delegate.W(), this, Orientation.E);
    }

    public ContributingCell NE() {
        Cell cell = delegate.NE();
        if (cell == null) {
            return null;
        }
        return locator == null
                ? new ContributingCell(delegate.NE(), this, Orientation.NE, locator)
                : new ContributingCell(delegate.NE(), this, Orientation.NE);
    }

    public ContributingCell NW() {
        Cell cell = delegate.NW();
        if (cell == null) {
            return null;
        }
        return locator == null
                ? new ContributingCell(delegate.NW(), this, Orientation.NW, locator)
                : new ContributingCell(delegate.NW(), this, Orientation.NW);
    }

    public ContributingCell SE() {
        Cell cell = delegate.SE();
        if (cell == null) {
            return null;
        }
        return locator == null
                ? new ContributingCell(delegate.SE(), this, Orientation.SE, locator)
                : new ContributingCell(delegate.SE(), this, Orientation.SE);
    }

    public ContributingCell SW() {
        Cell cell = delegate.SW();
        if (cell == null) {
            return null;
        }
        return locator == null
                ? new ContributingCell(delegate.SW(), this, Orientation.SW, locator)
                : new ContributingCell(delegate.SW(), this, Orientation.SW);
    }

    public double getWidth() {
        return delegate.getStandardizedWidth();
    }

    public long getX() {
        return delegate.getX();
    }

    public long getY() {
        return delegate.getY();
    }

    public double getHeight() {
        return delegate.getStandardizedHeight();
    }

    public double getStandardizedDepth() {
        return delegate.getStandardizedDepth();
    }

    public double getStandardizedLength() {
        return delegate.getStandardizedLength();
    }

    public double getEast() {
        return delegate.getEast();
    }

    public double getWest() {
        return delegate.getWest();
    }

    public int getScaleRank() {
        return delegate.getScaleRank();
    }

    public double getSouth() {
        return delegate.getSouth();
    }

    public double getNorth() {
        return delegate.getNorth();
    }

    public Long getOffset() {
        return delegate.getOffsetInGrid();
    }

    public boolean isAdjacent(ContributingCell cell) {
        return delegate.isAdjacent(cell.delegate);
    }

    public double[] getCenter() {
        return delegate.getCenter();
    }

    public double getDistance(ISpace extent) {
        return delegate.getStandardizedDistance(extent);
    }

    public double getDistance(ContributingCell extent) {
        return delegate.getStandardizedDistance(extent.delegate);
    }

    public IShape getShape() {
        return delegate.getShape();
    }

    public IExtent merge(ITopologicallyComparable<?> other, LogicalConnector how) {
        return delegate.merge(other, how);
    }

    public IState getFlowdirections() {
        return flowdirections;
    }

    public void setFlowdirections(IState flowdirections) {
        this.flowdirections = flowdirections;
    }

}
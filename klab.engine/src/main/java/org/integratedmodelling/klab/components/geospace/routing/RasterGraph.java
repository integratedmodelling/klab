//package org.integratedmodelling.engine.geospace.layers;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
//import org.integratedmodelling.api.configurations.ISpatialNetwork;
//import org.integratedmodelling.api.configurations.ITrajectory;
//import org.integratedmodelling.api.modelling.IDirectObservation;
//import org.integratedmodelling.api.modelling.IState;
//import org.integratedmodelling.api.space.IGrid;
//import org.integratedmodelling.api.space.IGrid.Cell;
//import org.integratedmodelling.api.space.Orientation;
//import org.integratedmodelling.collections.ImmutableSet;
//import org.integratedmodelling.engine.geospace.extents.Grid;
//import org.integratedmodelling.engine.geospace.extents.SpaceExtent;
//import org.integratedmodelling.engine.geospace.layers.RasterGraph.CellConnection;
//import org.integratedmodelling.exceptions.KlabRuntimeException;
//import org.jgrapht.EdgeFactory;
//import org.jgrapht.Graph;
//
///**
// * A 'virtual' graph representing cells and connections between adjacent cells, aware of
// * the values of a set of raster states supplied and using access rules to establish
// * whether a connection can exist. Does not actually store the vertices or edges.
// * Implements all of JGraphT graph API so it can be used with JGraphT search algorithm.
// * 
// * @author Ferd
// *
// */
//public class RasterGraph extends AbstractLayer implements Graph<IGrid.Cell, CellConnection>, ISpatialNetwork {
//
//    public static abstract class Transition {
//
//        protected IGrid               grid;
//        protected Map<String, IState> states = new HashMap<>();
//
//        protected Object get(Cell cell, String state) {
//            return null;
//        }
//
//        public abstract boolean canMove(Cell from, Cell to);
//
//        public abstract double movingCost(Cell from, Cell to);
//
//    }
//
//    private SpaceExtent         space;
//    private Grid                grid;
//    private Map<String, IState> states    = new HashMap<>();
//
//    ConnectionSet               edgeSet   = new ConnectionSet();
//    CellSet                     vertexSet = new CellSet();
//    Transition                  transition;
//
//    public static class CellConnection {
//
//        int from;
//        int to;
//
//        public CellConnection(Cell from, Cell to) {
//            this.from = from.getOffsetInGrid();
//            this.to = to.getOffsetInGrid();
//        }
//
//        @Override
//        public int hashCode() {
//            final int prime = 31;
//            int result = 1;
//            result = prime * result + from;
//            result = prime * result + to;
//            return result;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj)
//                return true;
//            if (obj == null)
//                return false;
//            if (getClass() != obj.getClass())
//                return false;
//            CellConnection other = (CellConnection) obj;
//            if (from != other.from)
//                return false;
//            if (to != other.to)
//                return false;
//            return true;
//        }
//    }
//
//    public RasterGraph(Transition transitionHandler, Map<String, IState> states) {
//        this.transition = transitionHandler;
//        this.states = states;
//        if (states != null && states.size() > 0) {
//            this.grid = (Grid) states.values().iterator().next().getSpace().getGrid();
//        }
//        if (this.grid == null) {
//            throw new KlabRuntimeException("RasterGraph: cannot operate without at least a gridded state");
//        }
//        if (this.transition != null) {
//            this.transition.grid = this.grid;
//            this.transition.states = this.states;
//        }
//    }
//
//    @Override
//    public CellConnection addEdge(Cell arg0, Cell arg1) {
//        // TODO Auto-generated method stub
//        throw new KlabRuntimeException("addEdge called on immutable graph");
//    }
//
//    @Override
//    public boolean addEdge(Cell arg0, Cell arg1, CellConnection arg2) {
//        throw new KlabRuntimeException("addEdge called on immutable graph");
//    }
//
//    public static void main(String[] args) {
//        RasterGraph g = new RasterGraph(new Transition() {
//
//            @Override
//            public double movingCost(Cell from, Cell to) {
//                // TODO Auto-generated method stub
//                return 0;
//            }
//
//            @Override
//            public boolean canMove(Cell from, Cell to) {
//                // TODO Auto-generated method stub
//                return false;
//            }
//        }, new HashMap<>());
//    }
//
//    @Override
//    public boolean addVertex(Cell arg0) {
//        throw new KlabRuntimeException("addVertex called on immutable graph");
//    }
//
//    @Override
//    public boolean containsEdge(CellConnection arg0) {
//        return true;
//    }
//
//    @Override
//    public boolean containsEdge(Cell arg0, Cell arg1) {
//        return arg0.isAdjacent(arg1) && transition.canMove(arg0, arg1);
//    }
//
//    @Override
//    public boolean containsVertex(Cell arg0) {
//        return true;
//    }
//
//    @Override
//    public Set<CellConnection> edgeSet() {
//        return edgeSet;
//    }
//
//    @Override
//    public Set<CellConnection> edgesOf(Cell arg0) {
//        Set<CellConnection> ret = new HashSet<>();
//        for (Cell cell : arg0.getNeighbors()) {
//            if (containsEdge(cell, arg0)) {
//                ret.add(new CellConnection(cell, arg0));
//            }
//        }
//        return ret;
//    }
//
//    @Override
//    public Set<CellConnection> getAllEdges(Cell arg0, Cell arg1) {
//        Set<CellConnection> ret = new HashSet<>();
//        if (containsEdge(arg0, arg1)) {
//            ret.add(new CellConnection(arg0, arg1));
//        }
//        return ret;
//    }
//
//    @Override
//    public CellConnection getEdge(Cell arg0, Cell arg1) {
//        if (containsEdge(arg0, arg1)) {
//            return new CellConnection(arg0, arg1);
//        }
//        return null;
//    }
//
//    @Override
//    public EdgeFactory<Cell, CellConnection> getEdgeFactory() {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public Cell getEdgeSource(CellConnection arg0) {
//        return grid.getCell(arg0.from);
//    }
//
//    @Override
//    public Cell getEdgeTarget(CellConnection arg0) {
//        return grid.getCell(arg0.to);
//    }
//
//    @Override
//    public double getEdgeWeight(CellConnection arg0) {
//        return transition.movingCost(grid.getCell(arg0.from), grid.getCell(arg0.to));
//    }
//
//    @Override
//    public boolean removeAllEdges(Collection<? extends CellConnection> arg0) {
//        throw new KlabRuntimeException("removeAllEdges called on immutable graph");
//    }
//
//    @Override
//    public Set<CellConnection> removeAllEdges(Cell arg0, Cell arg1) {
//        throw new KlabRuntimeException("removeAllEdges called on immutable graph");
//    }
//
//    @Override
//    public boolean removeAllVertices(Collection<? extends Cell> arg0) {
//        throw new KlabRuntimeException("removeAllVertices called on immutable graph");
//    }
//
//    @Override
//    public boolean removeEdge(CellConnection arg0) {
//        throw new KlabRuntimeException("removeEdge called on immutable graph");
//    }
//
//    @Override
//    public CellConnection removeEdge(Cell arg0, Cell arg1) {
//        throw new KlabRuntimeException("removeEdge called on immutable graph");
//    }
//
//    @Override
//    public boolean removeVertex(Cell arg0) {
//        throw new KlabRuntimeException("removeVertex called on immutable graph");
//    }
//
//    @Override
//    public Set<Cell> vertexSet() {
//        return vertexSet;
//    }
//
//    class CellSet extends ImmutableSet<Cell> {
//
//        class It implements Iterator<Cell> {
//
//            int n = 0;
//
//            @Override
//            public boolean hasNext() {
//                return n < (grid.getCellCount() - 1);
//            }
//
//            @Override
//            public Cell next() {
//                return grid.getCell(n++);
//            }
//
//        }
//
//        static final long serialVersionUID = -3575733761330748647L;
//
//        @Override
//        public boolean contains(Object o) {
//            return o instanceof Cell;
//        }
//
//        @Override
//        public boolean containsAll(Collection<?> c) {
//            return c.isEmpty() || c.iterator().next() instanceof Cell;
//        }
//
//        @Override
//        public boolean isEmpty() {
//            return grid.getCellCount() == 0;
//        }
//
//        @Override
//        public Iterator<Cell> iterator() {
//            return new It();
//        }
//
//        @Override
//        public int size() {
//            return grid.getCellCount();
//        }
//
//    }
//
//    class ConnectionSet extends ImmutableSet<CellConnection> {
//
//        class It implements Iterator<CellConnection> {
//
//            int           total      = size();
//            int           ncell      = 0;
//            int           ncdir      = 0;
//            int           tcount     = 0;
//            Orientation[] directions = grid.getPossibleConnections(0, 0);
//
//            @Override
//            public boolean hasNext() {
//                return tcount < total - 1;
//            }
//
//            @Override
//            public CellConnection next() {
//
//                Cell cell = grid.getCell(ncell);
//                CellConnection ret = new CellConnection(cell, cell.getNeighbor(directions[ncdir++]));
//
//                if (ncdir == (directions.length - 1)) {
//                    ncdir = 0;
//                    ncell ++;
//                    int[] xy = grid.getXYOffsets(ncell);
//                    directions = grid.getPossibleConnections(xy[0], xy[1]);
//                }
//                
//                tcount ++;
//                
//                return ret;
//            }
//
//        }
//
//        @Override
//        public boolean contains(Object o) {
//            return o instanceof CellConnection;
//        }
//
//        @Override
//        public boolean containsAll(Collection<?> c) {
//            return c.isEmpty() || c.iterator().next() instanceof CellConnection;
//        }
//
//        @Override
//        public boolean isEmpty() {
//            return grid.getCellCount() < 2;
//        }
//
//        @Override
//        public Iterator<CellConnection> iterator() {
//            return new It();
//        }
//
//        @Override
//        public int size() {
//            return grid.getConnectionCount();
//        }
//
//    }
//
//    @Override
//    public ITrajectory route(IDirectObservation from, IDirectObservation to) {
//        // TODO Auto-generated method stub
////        List<EntryPoint> starts = snap(from);
////        List<EntryPoint> ends = snap(to);
////
////        if (starts.isEmpty() || ends.isEmpty()) {
////            return null;
////        }
////
////        for (EntryPoint end : ends) {
////            for (EntryPoint start : starts) {
////                
////                SpatialNode source = null;
////                SpatialNode destination = null;
////
////                List<?> sdata = start.getUserData() instanceof List
////                        ? (List<?>) start.getUserData() : null;
////                List<?> edata = end.getUserData() instanceof List
////                        ? (List<?>) end.getUserData() : null;
////
////                /*
////                 * determine direction to target and choose the nodes that go that way:
////                 */
////                if (sdata != null && sdata.size() > 1) {
////                    source = chooseEndpoint(sdata, start.getJoinPoint(), end.getJoinPoint(), true);
////                }
////                if (edata != null && edata.size() > 1) {
////                    destination = chooseEndpoint(edata, start.getJoinPoint(), end.getJoinPoint(), false);
////                }
////
////                /*
////                 * route using Dijkstra's algorithm
////                 */
////                if (source != null && destination != null) {
////                    List<SpatialLink> path = DijkstraShortestPath.findPathBetween(this, source, destination);
////                    if (path != null && !path.isEmpty()) {
////                        return new Trajectory(this, path, start, end);
////                    }
////                }
////
////            }
//        return null;
//    }
//
//}

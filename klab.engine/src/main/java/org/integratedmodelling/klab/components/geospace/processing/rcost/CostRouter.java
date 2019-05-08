package org.integratedmodelling.klab.components.geospace.processing.rcost;

import java.util.PriorityQueue;

import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;

public class CostRouter {

	IShape from;
	IShape to;
	IComputationContext context;
	IExpression costFunction;
	
    private final double resolution;
    private final int CON = 8;
    private final int [] X = new int[] {-1, 0, 0, +1, -1, -1, +1, +1};
    private final int [] Y = new int[] {0, -1, +1, 0, -1, +1, -1, +1};
    private final double [] COST = new double[] {1.0, 1.0, 1.0, 1.0, 1.4142136, 1.4142136, 1.4142136, 1.4142136};
    private final int [] IND, IND_ANTE;
//    private final boolean doublePrec;
	private ISpace space;
	private Grid grid;
    private PriorityQueue<Node> queue = new PriorityQueue<>();

    /**
     * Node representing a pixel for the PriorityQueue
     */
    private static class Node implements Comparable<Node>{
        private int ind;
        private double dist;

        public Node(int ind, double dist) {
            this.ind = ind;
            this.dist = dist;
        }

        @Override
        public final int compareTo(Node o) {
            return dist == o.dist ? 0 : dist < o.dist ? -1 : 1;
        }

        @Override
        public final boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            
            return this.ind == ((Node)obj).ind;
        }

        @Override
        public final int hashCode() {
            int hash = 7;
            hash = 43 * hash + this.ind;
            return hash;
        }

    }
	
	public CostRouter(IShape from, IShape to, IComputationContext context, IExpression costFunction) {

		this.space = context.getScale().getSpace();
		
		if (this.space == null || !(this.space instanceof Space) || ((Space)this.space).getGrid() == null) {
			throw new IllegalArgumentException("viewshed algorithm must be run on a gridded context");
		}

		this.grid = (Grid) ((Space)this.space).getGrid();
		this.resolution = this.grid.getCellWidth();
		int w = (int)grid.getXCells();
        IND = new int[] {-1, -w, +w, +1, -w-1, +w-1, -w+1, +w+1};
        IND_ANTE = new int[] {+1, +w, -w, -1, w+1, -w+1, +w-1, -w-1};
	}
	
}

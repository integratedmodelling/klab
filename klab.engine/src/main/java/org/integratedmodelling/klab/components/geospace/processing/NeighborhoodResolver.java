package org.integratedmodelling.klab.components.geospace.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;

public class NeighborhoodResolver implements IResolver<IState>, IExpression {

	class UpdateDescriptor {
		Descriptor descriptor;
		IExpression expression;
		String artifact;
	}

	private Descriptor valueDescriptor;
	private Descriptor selectDescriptor;
	private Aggregation aggregation;
	private Double radius;
	private Double cellradius;
	private Boolean circular;
	private Boolean skipEdges;

	private int hCells;
	private Grid grid;
	Map<IState, String> stateIdentifiers = new HashMap<>();
	private IComputationContext context;
	private boolean dataWarning;
	IDataArtifact valueCache = null;
	IExpression selectExpression = null;
	IExpression valueExpression = null;

	@Override
	public IGeometry getGeometry() {
		return Geometry.create("S2");
	}

	@Override
	public Type getType() {
		return Type.VALUE;
	}

	public NeighborhoodResolver() {
	}

	private NeighborhoodResolver(IParameters<String> parameters, IComputationContext context) {

		if (parameters.containsKey("select")) {
			Object expression = parameters.get("select");
			if (expression instanceof IKimExpression) {
				expression = ((IKimExpression) expression).getCode();
			}
			this.selectDescriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE)
					.describe(expression.toString(), context);
		}
		if (parameters.containsKey("aggregate")) {
			Object expression = parameters.get("aggregate");
			if (expression instanceof IKimExpression) {
				expression = ((IKimExpression) expression).getCode();
			}
			this.valueDescriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE)
					.describe(expression.toString(), context);
		}

		this.radius = parameters.get("radius", 0.0);
		this.cellradius = parameters.get("cellradius", 0.0);
		this.circular = parameters.get("circular", Boolean.FALSE);
		this.skipEdges = parameters.get("skipedges", Boolean.FALSE);
		String aggregation = parameters.get("aggregation", String.class);
		this.aggregation = parseAggregation(aggregation);

		List<?> upd = parameters.get("update", List.class);
		if (upd != null && upd.size() > 0) {

		}
	}

	private Aggregation parseAggregation(String aggregation) {

		if (aggregation != null) {
			switch (aggregation.toLowerCase()) {
			case "sum":
				return Aggregation.SUM;
			case "mean":
				return Aggregation.MEAN;
			case "std":
				return Aggregation.STD;
			case "min":
				return Aggregation.MIN;
			case "max":
				return Aggregation.MAX;
			case "dominant":
				return Aggregation.MAJORITY;
			case "any":
				return Aggregation.ANY_PRESENT;
			case "count":
				return Aggregation.COUNT;
			}
		}
		return null;
	}

	@Override
	public IState resolve(IState target, IComputationContext context) throws KlabException {

		this.context = context;
		this.grid = Space.extractGrid(target);
		if (grid == null) {
			throw new KlabValidationException("Neighborhood aggregations must be computed on a grid extent");
		}

		if (this.radius != 0) {
			this.radius = context.getScale().getSpace().getEnvelope().convertDistance(this.radius);
			hCells = (int) Math.ceil(this.radius / grid.getCellWidth());
		} else if (this.cellradius != null) {
			hCells = (int) Math.ceil(this.cellradius);
		} else {
			hCells = 1;
		}

		if (hCells <= 0) {
			context.getMonitor().warn("Neighborhood analysis: the neighborhood is too small: no action done");
			return target;
		}

		/*
		 * build offset mask for quick addressing
		 */
		int maskSize = hCells * 2 + 1;
		@SuppressWarnings("unchecked")
		Pair<Integer, Integer>[][] offsetMask = new Pair[maskSize][maskSize];
		int[] quadrant = new int[] { -1, 0, 1 };
		for (int xq : quadrant) {
			for (int yq : quadrant) {
				for (int x = 0; x < hCells + 1; x++) {
					for (int y = 0; y < hCells + 1; y++) {
						boolean ok = true;
						if (circular) {
							ok = Math.sqrt(hCells - x + .1) + Math.sqrt(hCells - y + .1) > Math.sqrt(hCells + .1);
						}
						if (ok) {
							int xofs = x * xq;
							int yofs = y * yq;
							offsetMask[xofs + hCells][yofs + hCells] = new Pair<>(yofs, xofs);
						}
					}
				}
			}
		}
		List<IState> sourceStates = new ArrayList<>();
		List<IState> selectStates = new ArrayList<>();


		boolean isLinear = true;

		if (valueDescriptor != null) {
			// check inputs and see if the expr is worth anything in this context
			for (String input : valueDescriptor.getIdentifiers()) {
				if (valueDescriptor.isScalar(input) && context.getArtifact(input, IState.class) != null) {
					IState state = context.getArtifact(input, IState.class);
					sourceStates.add(state);
					this.stateIdentifiers.put(state, input);
					if (input.equals("origin")) {
						isLinear = false;
					}
				}
			}
			if (sourceStates.isEmpty()) {
				throw new KlabResourceNotFoundException(
						"Neighborhood resolver: the value expression does not reference any known state");
			}
			valueExpression = valueDescriptor.compile();
		} else {
			throw new KlabValidationException(
					"An expression producing the value to aggregate must be provided as parameter 'aggregate'");
		}

		if (selectDescriptor != null) {
			// check inputs and see if the expr is worth anything in this context
			for (String input : selectDescriptor.getIdentifiers()) {
				if (selectDescriptor.isScalar(input) && context.getArtifact(input, IState.class) != null) {
					IState state = context.getArtifact(input, IState.class);
					selectStates.add(state);
					this.stateIdentifiers.put(state, input);
					if (input.equals("origin")) {
						isLinear = false;
					}
				}
			}
			if (selectStates.isEmpty()) {
				throw new KlabResourceNotFoundException(
						"Neighborhood resolver: the select expression does not reference any known state");
			}
			selectExpression = selectDescriptor.compile();
		}

		/*
		 * go for it
		 */

		if (isLinear) {
			context.getMonitor()
					.info("No contextual references: building pre-loaded value cache for neighborhood analysis");

			// working parallel version commented out
			StreamSupport.stream(grid.spliterator(context.getMonitor()), true).forEach((locator) -> {
//				 for (Cell locator : grid) {

				Object value = null;
				boolean evaluate = true;

//				if (context.getMonitor().isInterrupted()) {
//					break;
//				}

				if (selectExpression != null) {
					if (!evalStates(selectExpression, selectStates, locator, Boolean.class)) {
						evaluate = false;
					}
				}

				if (evaluate) {
					Object self = target.get(locator);
					value = evalStates(valueExpression, sourceStates, locator, Object.class, "self", self);
					if (value != null && valueCache == null) {
						valueCache = Klab.INSTANCE.getStorageProvider()
								.createStorage(Utils.getArtifactType(value.getClass()), target.getScale(), context);
					}
				}

				valueCache.set(locator, value);
			});
//		}
		}

		if (isLinear && valueCache == null) {
			context.getMonitor().info("No usable values in source data: skipping neighborhood analysis");
			return target;
		}

		long ncells = 0;
		context.getMonitor()
				.info("Neighborhood analysis starting with a " + maskSize + "x" + maskSize + " neighborhood");

		StreamSupport.stream(grid.spliterator(context.getMonitor()), true).forEach((locator) -> {
//		for (Cell locator : grid) {

//			if (context.getMonitor().isInterrupted()) {
//				break;
//			}

			List<Object> values = new ArrayList<>(maskSize * maskSize);
			for (Cell cell : getNeighborhood(locator, offsetMask)) {

				Object value = null;
				if (valueCache == null) {

					if (selectExpression != null) {
						if (!evalStates(selectExpression, selectStates, cell, Boolean.class, "origin", locator)) {
							continue;
						}
					}

					Object self = target.get(locator);
					value = evalStates(valueExpression, sourceStates, cell, Object.class, "origin", locator, "self",
							self);
				} else {
					value = valueCache.get(cell);
				}

				if (!(value == null || (value instanceof Number && Double.isNaN(((Number) value).doubleValue())))) {
					values.add(value);
				}
			}

			target.set(locator, aggregate(values));

//			ncells++;

//			if (ncells == 0 || (ncells % 10000) == 0) {
//				context.getMonitor().info(ncells + " cells done...");
//			}
		});
//		}
		if (valueCache != null) {
			valueCache.release();
		}

		return target;
	}

	private Object aggregate(List<Object> values) {

		if (aggregation == null) {
			for (Object value : values) {
				if (value instanceof Boolean) {
					aggregation = Aggregation.COUNT;
					break;
				} else if (value instanceof Number) {
					aggregation = Aggregation.MEAN;
					break;
				} else if (value != null) {
					aggregation = Aggregation.MAJORITY;
					break;
				}
			}
		}

		if (aggregation == null) {
			return null;
		}

		switch (aggregation) {
		case ANY_PRESENT:
			return values.size() > 0;
		case COUNT:
			return count(values);
		case MAJORITY:
			return dominant(values);
		case MAX:
			return max(values);
		case MEAN:
			return mean(values);
		case MIN:
			return min(values);
		case STD:
			return std(values);
		case SUM:
			return sum(values);
		default:
			break;
		}

		return null;
	}

	private Object sum(List<Object> values) {
		double sum = 0;
		for (Object value : values) {
			if (value instanceof Number) {
				sum += ((Number) value).doubleValue();
			} else if (!dataWarning) {
				dataWarning = true;
				context.getMonitor().warn(
						"neighborhood analysis: one or more values found to be of incompatible type during aggregation");
			}
		}
		return sum;
	}

	private Object std(List<Object> values) {
		double sum = 0;
		int n = 0;
		for (Object value : values) {
			if (value instanceof Number) {
				sum += ((Number) value).doubleValue();
				n++;
			} else if (!dataWarning) {
				dataWarning = true;
				context.getMonitor().warn(
						"neighborhood analysis: one or more values found to be of incompatible type during aggregation");
			}
		}
		double mean = sum / (double) n;
		double sd = 0;
		for (Object value : values) {
			if (value instanceof Number) {
				sd += Math.pow(((Number) value).doubleValue() - mean, 2);
			}
		}
		return Math.sqrt(sd / (double) n);
	}

	private Object min(List<Object> values) {
		Double min = null;
		for (Object value : values) {
			if (value instanceof Number) {
				if (min == null || min > ((Number) value).doubleValue()) {
					min = ((Number) value).doubleValue();
				}
			} else if (!dataWarning) {
				dataWarning = true;
				context.getMonitor().warn(
						"neighborhood analysis: one or more values found to be of incompatible type during aggregation");
			}
		}
		return min;
	}

	private Object mean(List<Object> values) {
		double sum = 0;
		int n = 0;
		for (Object value : values) {
			if (value instanceof Number) {
				sum += ((Number) value).doubleValue();
				n++;
			} else if (!dataWarning) {
				dataWarning = true;
				context.getMonitor().warn(
						"neighborhood analysis: one or more values found to be of incompatible type during aggregation");
			}
		}
		return sum / (double) n;
	}

	private Object max(List<Object> values) {
		Double max = null;
		for (Object value : values) {
			if (value instanceof Number) {
				if (max == null || max < ((Number) value).doubleValue()) {
					max = ((Number) value).doubleValue();
				}
			} else if (!dataWarning) {
				dataWarning = true;
				context.getMonitor().warn(
						"neighborhood analysis: one or more values found to be of incompatible type during aggregation");
			}
		}
		return max;
	}

	private Object dominant(List<Object> values) {

		Map<Object, Integer> vals = new HashMap<>();
		for (Object value : values) {
			if (vals.containsKey(value)) {
				vals.put(value, vals.get(value) + 1);
			} else {
				vals.put(value, 1);
			}
		}
		Object val = null;
		int n = 0;
		for (Object o : vals.keySet()) {
			if (val == null || vals.get(val) > n) {
				val = o;
				n = vals.get(val);
			}
		}
		return val;
	}

	private Object count(List<Object> values) {
		int n = 0;
		for (Object value : values) {
			if (value instanceof Boolean) {
				if ((Boolean) value) {
					n++;
				}
			} else if (value instanceof Number) {
				if (((Number) value).doubleValue() != 0) {
					n++;
				}
			} else {
				n++;
			}
		}
		return n;
	}

	private <T> T evalStates(IExpression expression, List<IState> states, ILocator where, Class<? extends T> cls,
			Object... parms) {

		Parameters<String> parameters = Parameters.create(parms);
		parameters.put("space", where);
		for (IState state : states) {
			Object o = state.get(where, Object.class);
			parameters.put(stateIdentifiers.get(state), o);
		}
		return Utils.asType(expression.eval(parameters, context), cls);
	}

	private List<Cell> getNeighborhood(Cell center, Pair<Integer, Integer>[][] offsetMask) {

		List<Cell> ret = new ArrayList<>();

		if (skipEdges && (center.getX() < hCells || center.getY() > (grid.getYCells() - hCells)
				|| center.getY() < hCells || center.getX() > (grid.getXCells() - hCells))) {
			return ret;
		}

		// System.out.println("Neighborhood of " + center.getX() + "/" + center.getY() +
		// ":");
		for (int x = 0; x < offsetMask[0].length; x++) {
			for (int y = 0; y < offsetMask.length; y++) {
				Pair<Integer, Integer> xy = offsetMask[x][y];
				Cell cell = xy == null ? null : center.getNeighbor(xy.getFirst(), xy.getSecond());
				if (cell != null) {
					ret.add(cell);
				}
				// System.out.print(StringUtils.center(cell == null ? "" : (cell.getX() + "/" +
				// cell.getY()), 10));
			}
			// System.out.println();
		}

		return ret;
	}

	@Override
	public NeighborhoodResolver eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		return new NeighborhoodResolver(parameters, context);
	}

	/**
	 * For fun if needed.
	 * 
	 * @param offsetMask
	 */
	public void printMask(Pair<Integer, Integer>[][] offsetMask) {

		for (int x = 0; x < offsetMask[0].length; x++) {
			for (int y = 0; y < offsetMask.length; y++) {
				// prints the mask
				String def = offsetMask[x][y] == null ? "   " : " X ";
				// prints the coordinates
				// String def = StringUtils.center(offsetMask[x][y] == null ? ""
				// : (offsetMask[x][y].getFirst() + "/" + offsetMask[x][y].getSecond()), 10);
				System.out.print(def);
			}
			System.out.println();
		}
	}

}

package org.integratedmodelling.klab.components.geospace.processing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.StreamSupport;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.AggregationUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;

public class NeighborhoodResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {

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

    private int hCells = -1;
    private Grid grid;
    Map<IState, String> stateIdentifiers = new HashMap<>();
    private IContextualizationScope context;
    IDataStorage<?> valueCache = null;
    IExpression selectExpression = null;
    IExpression valueExpression = null;
    LongAdder adder = new LongAdder();
    private Pair<Integer, Integer>[][] offsetMask;
    private int maskSize;

    @Override
    public Type getType() {
        return Type.VALUE;
    }

    public NeighborhoodResolver() {
    }

    private NeighborhoodResolver(IParameters<String> parameters, IContextualizationScope context) {

        IExpression.Scope expressionContext = context.getExpressionContext();

        // TODO should be artifact OBJECT type
        expressionContext.addKnownIdentifier("cell", IKimConcept.Type.COUNTABLE);
        expressionContext.addKnownIdentifier("origin", IKimConcept.Type.COUNTABLE);

        if (parameters.containsKey("select")) {
            Object expression = parameters.get("select");
            boolean forceScalar = false;
            if (expression instanceof IKimExpression) {
                forceScalar = ((IKimExpression) expression).isForcedScalar();
                expression = ((IKimExpression) expression).getCode();
            }
            this.selectDescriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE).describe(
                    expression.toString(), expressionContext.scalar(forceScalar ? Forcing.Always : Forcing.AsNeeded),
                    Extensions.options(false));
        }
        if (parameters.containsKey("aggregate")) {
            Object expression = parameters.get("aggregate");
            boolean forceScalar = false;
            if (expression instanceof IKimExpression) {
                forceScalar = ((IKimExpression) expression).isForcedScalar();
                expression = ((IKimExpression) expression).getCode();
            }
            this.valueDescriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE).describe(
                    expression.toString(), expressionContext.scalar(forceScalar ? Forcing.Always : Forcing.AsNeeded),
                    Extensions.options(false));
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
            switch(aggregation.toLowerCase()) {
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

    @SuppressWarnings("unchecked")
    @Override
    public IState resolve(IState target, IContextualizationScope context) throws KlabException {

        this.context = context;

        if (this.hCells < 0) {

            this.grid = Space.extractGrid(target);
            if (grid == null) {
                throw new KlabValidationException("Neighborhood aggregations must be computed on a grid extent");
            }

            if (this.radius != 0) {
                this.radius = context.getScale().getSpace().getEnvelope().metersToDistance(this.radius);
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
             * build offset mask for quick addressing TODO can use kernels from JAI tools
             */
            this.maskSize = hCells * 2 + 1;
            this.offsetMask = new Pair[maskSize][maskSize];
            int[] quadrant = new int[]{-1, 0, 1};
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

        }

        boolean isLinear = true;
        ArrayList<IState> sourceStates = new ArrayList<>();
        ArrayList<IState> selectStates = new ArrayList<>();

        if (valueDescriptor != null) {
            // check inputs and see if the expr is worth anything in this context
            for (String input : valueDescriptor.getIdentifiers()) {
                if (valueDescriptor.isScalar(input) && context.getArtifact(input, IState.class) != null) {
                    IState state = context.getArtifact(input, IState.class);
                    sourceStates.add(state);
                    this.stateIdentifiers.put(state, input);
                }
                if (input.equals("origin")) {
                    isLinear = false;
                }
            }
            if (sourceStates.isEmpty()) {
                throw new KlabResourceNotFoundException(
                        "Neighborhood resolver: the value expression does not reference any known state");
            }

            if (isLinear && valueDescriptor.getContextualizers().contains("origin")) {
                isLinear = false;
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
            if (isLinear && selectDescriptor.getContextualizers().contains("origin")) {
                isLinear = false;
            }
            selectExpression = selectDescriptor.compile();
        }

        /*
         * go for it
         */

        if (isLinear) {
            context.getMonitor().info("No contextual references: building pre-loaded value cache for neighborhood analysis");

            // working parallel version commented out
            StreamSupport.stream(((Scale) context.getScale()).spliterator(context.getMonitor()), true).forEach((locator) -> {
                // for (Cell locator : grid) {

                Object value = null;
                boolean evaluate = true;

                // if (context.getMonitor().isInterrupted()) {
                // break;
                // }

                if (selectExpression != null) {
                    if (!evalStates(selectExpression, selectStates, locator, Boolean.class)) {
                        evaluate = false;
                    }
                }

                if (evaluate) {
                    Object self = target.get(locator);
                    value = evalStates(valueExpression, sourceStates, locator, Object.class, "self", self);
                    if (value != null && valueCache == null) {
                        synchronized (NeighborhoodResolver.this) {
                            valueCache = (IDataStorage<?>) Klab.INSTANCE.getStorageProvider()
                                    .createStorage(Utils.getArtifactType(value.getClass()), target.getScale());
                        }
                    }
                }
                if (valueCache != null) {
                    valueCache.putObject(value, locator.getSpace());
                }
            });
            // }
        }

        // if (isLinear && valueCache == null) {
        // context.getMonitor().info("No usable values in source data: skipping
        // neighborhood analysis");
        // return target;
        // }

        long ncells = 0;
        context.getMonitor().info("Neighborhood analysis starting with a " + maskSize + "x" + maskSize + " neighborhood");

        StreamSupport.stream(((Scale) context.getScale()).spliterator(context.getMonitor()), true).forEach((locator) -> {
            // for (Cell locator : grid) {

            // if (context.getMonitor().isInterrupted()) {
            // break;
            // }

            List<Object> values = new ArrayList<>(maskSize * maskSize);
            for (Cell cell : getNeighborhood((Cell) locator.getSpace(), offsetMask)) {

                IScale cellLocator = Scale.create(locator.getTime(), cell);

                Object value = null;
                if (valueCache == null) {

                    if (selectExpression != null) {
                        if (!evalStates(selectExpression, selectStates, cellLocator, Boolean.class, "origin", locator)) {
                            continue;
                        }
                    }

                    Object self = target.get(locator);
                    value = evalStates(valueExpression, sourceStates, cellLocator, Object.class, "origin", locator, "self", self);
                } else {
                    value = valueCache.get(cell);
                }

                if (!(value == null || (value instanceof Number && Double.isNaN(((Number) value).doubleValue())))) {
                    values.add(value);
                }
            }

            target.set(locator, AggregationUtils.aggregate(values, aggregation, context.getMonitor()));

            // long cells = adder.longValue();
            // if (cells == 0 || (cells % 10000) == 0) {
            // context.getMonitor().info(adder.sum() + " cells done...");
            // }
            //
            // adder.add(1);

        });
        // }
        if (valueCache != null) {
            try {
                valueCache.close();
            } catch (IOException e) {
                // f'ock
            }
        }

        return target;
    }

    private <T> T evalStates(IExpression expression, List<IState> states, IScale where, Class<? extends T> cls, Object... parms) {

        Parameters<String> parameters = Parameters.create(parms);
        parameters.put("space", where.getSpace());
        parameters.put("radius", radius);
        parameters.put("cellradius", cellradius);

        if (parameters.get("origin") instanceof IScale) {
            parameters.put("origin", new ContributingCell((Cell) parameters.get("origin", IScale.class).getSpace()));
        }
        if (where.getSpace() instanceof Cell) {
            parameters.put("cell", new ContributingCell((Cell) where.getSpace()));
        }
//        for (IState state : states) {
//            Object o = state.get(where, Object.class);
//            parameters.put(stateIdentifiers.get(state), o);
//        }
        return Utils.asType(expression.eval(context, parameters, "scale", where), cls);
    }

    private List<Cell> getNeighborhood(Cell center, Pair<Integer, Integer>[][] offsetMask) {

        List<Cell> ret = new ArrayList<>();

        if (skipEdges && (center.getX() < hCells || center.getY() > (grid.getYCells() - hCells) || center.getY() < hCells
                || center.getX() > (grid.getXCells() - hCells))) {
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
    public NeighborhoodResolver eval(IContextualizationScope context, Object... parameters) throws KlabException {
        return new NeighborhoodResolver(Parameters.create(parameters), context);
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

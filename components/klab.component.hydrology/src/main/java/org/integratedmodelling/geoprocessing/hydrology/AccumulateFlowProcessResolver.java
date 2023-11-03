package org.integratedmodelling.geoprocessing.hydrology;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.processing.ContributingCell;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.data.storage.BasicFileMappedStorage;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.locationtech.jts.geom.Point;

/**
 * Generic flow accumulator resolver that will compute an arbitrary quality by following flow
 * directions from the outlets and executing arbitrary expressions.
 * 
 * @author ferdinando.villa
 *
 */
public class AccumulateFlowProcessResolver extends AbstractContextualizer implements IResolver<IProcess>, IExpression {

    private Descriptor accumulateDescriptor;
    private Descriptor distributeDescriptor;
    private IContextualizationScope context;

    public AccumulateFlowProcessResolver() {
    }

    public AccumulateFlowProcessResolver(IParameters<String> parameters, IContextualizationScope context) {

        this.context = context;
        if (parameters.containsKey("evaluate")) {
            Object expression = parameters.get("evaluate");
            boolean forceScalar = false;
            if (expression instanceof IKimExpression) {
                forceScalar = ((IKimExpression) expression).isForcedScalar();
                expression = ((IKimExpression) expression).getCode();
            }
            this.accumulateDescriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE).describe(
                    expression.toString(),
                    context.getExpressionContext().scalar(forceScalar ? Forcing.Always : Forcing.AsNeeded));
        }
        if (parameters.containsKey("distribute")) {
            boolean forceScalar = false;
            Object expression = parameters.get("distribute");
            if (expression instanceof IKimExpression) {
                forceScalar = ((IKimExpression) expression).isForcedScalar();
                expression = ((IKimExpression) expression).getCode();
            }
            this.distributeDescriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE).describe(
                    expression.toString(),
                    context.getExpressionContext().scalar(forceScalar ? Forcing.Always : Forcing.AsNeeded));
        }
        if (this.accumulateDescriptor == null && this.distributeDescriptor == null) {
            throw new IllegalArgumentException("flow accumulation resolver: no expression to evaluate");
        }
    }

    @Override
    public Type getType() {
        return Type.NUMBER;
    }

    @Override
    public IProcess resolve(IProcess process, IContextualizationScope context) throws KlabException {

        IState target = context.getState(Observables.INSTANCE.getDescribedType(process.getObservable().getType()), null);
        if (target == null) {
            throw new KlabValidationException("cannot find state changed by " + process.getObservable());
        }

        IState flowdirection = context.getArtifact("flow_directions_d8", IState.class);

        // IUnit tUnit = target.getObservable().getUnit();
        Grid grid = Space.extractGrid(target);

        if (grid == null) {
            throw new KlabValidationException("Runoff must be computed on a grid extent");
        }

        // if (tUnit != null && tUnit.equals(Units.INSTANCE.SQUARE_METERS)) {
        // tUnit = null;
        // }

        /*
         * synchronized in case we parallelize the walker, which we don't do at the moment (not sure
         * what happens with synchronous writes).
         */
        Map<String, IState> states = Collections.synchronizedMap(new HashMap<>());
        states.put("self", target);
        IExpression downstreamExpression = null;
        IExpression upstreamExpression = null;

        for (Pair<String, IState> a : context.getArtifacts(IState.class)) {
            // if (context.getScale().getTime() != null) {
            // a.setSecond(a.getSecond().at(context.getScale().getTime()));
            // }
            states.put(a.getFirst(), a.getSecond());
        }

        if (accumulateDescriptor != null) {
            // // check inputs and see if the expr is worth anything in this context
            // for (String input : accumulateDescriptor.getIdentifiers()) {
            // if (accumulateDescriptor.isScalar(input) && context.getArtifact(input,
            // IState.class)
            // != null) {
            // IState state = context.getArtifact(input, IState.class);
            // states.put(input, state);
            // }
            // }
            downstreamExpression = accumulateDescriptor.compile();
        }

        if (distributeDescriptor != null) {
            // check inputs and see if the expr is worth anything in this context
            // for (String input : distributeDescriptor.getIdentifiers()) {
            // if (distributeDescriptor.isScalar(input) && context.getArtifact(input,
            // IState.class)
            // != null) {
            // IState state = context.getArtifact(input, IState.class);
            // states.put(input, state);
            // }
            // }
            upstreamExpression = distributeDescriptor.compile();
        }

        try (BasicFileMappedStorage<Boolean> positionCache = new BasicFileMappedStorage<>(Boolean.class, grid.getCellCount())) {

            for (IArtifact artifact : context.getArtifact("stream_outlet")) {

                ISpace space = ((IObservation) artifact).getSpace();

                if (space == null) {
                    continue;
                }

                Point point = ((Shape) space.getShape()).getJTSGeometry().getCentroid();
                long xy = grid.getOffsetFromWorldCoordinates(point.getX(), point.getY());
                Cell start = grid.getCell(xy);
                compute(start, flowdirection, target, states, downstreamExpression, upstreamExpression, true,
                        target.get(Scale.create(context.getScale().getTime(), start)), positionCache, context);
            }
        }

        return process;
    }

    /*
     * Computation of runoff accumulates the runoff from upstream cells, ending at the outlet This
     * function is called with the outlet cell as parameter.
     */
    private Object compute(Cell cell, IState flowdirection, IState result, Map<String, IState> states,
            IExpression downstreamExpression, IExpression upstreamExpression, boolean isOutlet, Object previousValue,
            BasicFileMappedStorage<Boolean> positionCache, IContextualizationScope scope) {

        Object ret = previousValue;

        if (positionCache.get(cell.getOffsetInGrid())) {
            return ret;
        }

        positionCache.set(Boolean.TRUE, cell.getOffsetInGrid());

        Parameters<String> parameters = Parameters.create();
        parameters.put("current", previousValue);

        ILocator locator = cell;
        if (scope.getScale().getTime() != null) {
            locator = Scale.create(scope.getScale().getTime(), cell);
        }

        /*
         * collect values of all states @ self
         */
        for (String state : states.keySet()) {
            parameters.put(state, states.get(state).get(locator));
        }

        /*
         * build the cell descriptor to give us access to the neighborhood
         */
        parameters.put("cell", new ContributingCell(cell, ((Number) flowdirection.get(locator)).intValue(), flowdirection, states,
                isOutlet, locator));

        // call upstream before recursion, in upstream order
        if (upstreamExpression != null) {
            result.set(cell, (ret = upstreamExpression.eval(scope, parameters)));
        }

        List<Cell> upstreamCells = Geospace.getUpstreamCells(cell, flowdirection, context.getScale().getTime(), null);
        for (Cell upstream : upstreamCells) {
            compute(upstream, flowdirection, result, states, downstreamExpression, upstreamExpression, false, ret, positionCache,
                    scope);
        }

        /*
         * call downstream after recursion, i.e. in downstream order
         */
        if (downstreamExpression != null) {
            result.set(locator, (ret = downstreamExpression.eval(context, parameters)));
        }

        return ret;
    }

    @Override
    public Object eval(IContextualizationScope context, Object... parameters) throws KlabException {
        return new AccumulateFlowProcessResolver(Parameters.create(parameters), context);
    }
}

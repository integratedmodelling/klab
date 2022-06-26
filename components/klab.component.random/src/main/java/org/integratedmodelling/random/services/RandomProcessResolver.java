package org.integratedmodelling.random.services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.distribution.IntegerDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.processing.ContributingCell;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;
import org.integratedmodelling.random.adapters.RandomAdapter;

public class RandomProcessResolver extends AbstractContextualizer implements IResolver<IProcess>, IExpression {

    private static RandomAdapter adapter = new RandomAdapter();

    // for each configured output, holds either a distribution (integer or double), an expression,
    // or their k.IM peers, to be resolved to the actual generators on first use.
    Map<String, Object> generators = new HashMap<>();

    // private boolean initialized = false;
    //
    @Override
    public Type getType() {
        return Type.PROCESS;
    }

    @Override
    public IProcess resolve(IProcess ret, IContextualizationScope scope) throws KlabException {

        /*
         * for each output mentioned in the function as a parameter, find the correspondent
         * observation and fill it in according to the function passed.
         */
        for (String generator : generators.keySet()) {

            IArtifact artifact = scope.getArtifact(generator);
            if (artifact instanceof IState) {

                Object g = generators.get(generator);

                if (g instanceof IKimExpression) {
                    g = ((IKimExpression) g).getCode();
                    Descriptor descriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE)
                            .describe(g.toString(), scope.getExpressionContext(null));
                    generators.put(generator, descriptor.compile());

                } else if (g instanceof IServiceCall) {
                    List<String> args = new ArrayList<>();
                    args.add(((IServiceCall) g).getName());
                    if (((IServiceCall) g).getParameters().containsKey(IServiceCall.DEFAULT_PARAMETER_NAME)) {
                        Object p = ((IServiceCall) g).getParameters().get(IServiceCall.DEFAULT_PARAMETER_NAME);
                        if (p != null) {
                            if (p.getClass().isArray()) {
                                for (int i = 0; i < Array.getLength(p); i++) {
                                    args.add(Array.get(p, i).toString());
                                }
                            } else if (p instanceof Collection) {
                                for (Object z : (Collection<?>) p) {
                                    if (z != null) {
                                        args.add(z.toString());
                                    }
                                }
                            } else if (Utils.isPOD(p)) {
                                args.add(p.toString());
                            } else {
                                throw new KlabIllegalArgumentException("random process: can't use function parameter " + p);
                            }
                        }
                    }
                    g = adapter.getDistribution(args.toArray(new String[args.size()]));
                    generators.put(generator, g);
                } else if (g instanceof IKimConcept) {
                    g = Concepts.INSTANCE.declare((IKimConcept) g);
                    generators.put(generator, g);
                } else if (g instanceof IKimObservable) {
                    g = Observables.INSTANCE.declare((IKimObservable) g, scope.getMonitor()).getType();
                    generators.put(generator, g);
                }

                if (g instanceof RealDistribution) {

                    for (ILocator locator : scope.getScale()) {
                        Double value = ((RealDistribution) g).sample();
                        if (value < 0 && ((IState) artifact).getObservable().is(IKimConcept.Type.EXTENSIVE_PROPERTY)) {
                            value = 0.0;
                        }
                        ((IState) artifact).set(locator, value);
                    }

                } else if (g instanceof IntegerDistribution) {

                    for (ILocator locator : scope.getScale()) {
                        int value = ((IntegerDistribution) g).sample();
                        if (value < 0 && ((IState) artifact).getObservable().is(IKimConcept.Type.EXTENSIVE_PROPERTY)) {
                            value = 0;
                        }
                        ((IState) artifact).set(locator, value);
                    }

                } else if (g instanceof IExpression) {

                    for (ILocator locator : scope.getScale()) {
                        
                        Object value = evalStates((IExpression) g, (IScale) locator, scope);
                        if (value instanceof Number && ((Number)value).doubleValue() < 0 && ((IState) artifact).getObservable().is(IKimConcept.Type.EXTENSIVE_PROPERTY)) {
                            value = 0.0;
                        }
                        ((IState) artifact).set(locator, value);
                    }
                    
                } else if (g instanceof IConcept || g instanceof Number || g instanceof Boolean) {
                    for (ILocator locator : scope.getScale()) {
                        ((IState) artifact).set(locator, g);
                    }
                }

            } else if (artifact != null) {
                // TODO
                throw new KlabUnimplementedException("random process: generation of object artifacts is unimplemented");
            }
        }

        return ret;
    }

    private Object evalStates(IExpression expression, IScale where, IContextualizationScope scope) {

        Parameters<String> parameters = Parameters.create();
        parameters.put("space", where.getSpace());
        if (where.getSpace() instanceof Cell) {
            parameters.put("cell", new ContributingCell((Cell) where.getSpace()));
        }
        for (Pair<String, IState> st : scope.getArtifacts(IState.class)) {
            Object o = st.getSecond().get(where, Object.class);
            parameters.put(st.getFirst(), o);
        }
        return expression.eval(scope, parameters, "scale", where);
    }

    @Override
    public Object eval(IContextualizationScope context, Object...parameters) {
        RandomProcessResolver ret = new RandomProcessResolver();
        ret.generators.putAll(Parameters.create(parameters));
        return ret;
    }

}

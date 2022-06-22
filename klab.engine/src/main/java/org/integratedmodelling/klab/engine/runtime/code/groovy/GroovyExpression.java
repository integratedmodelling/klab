///*******************************************************************************
// * Copyright (C) 2007, 2015:
// * 
// * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
// * authors listed in @author annotations
// *
// * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
// * collaborative, integrated development of interoperable data and model components. For details,
// * see http://integratedmodelling.org.
// * 
// * This program is free software; you can redistribute it and/or modify it under the terms of the
// * Affero General Public License Version 3 or any later version.
// *
// * This program is distributed in the hope that it will be useful, but without any warranty; without
// * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
// * General Public License for more details.
// * 
// * You should have received a copy of the Affero General Public License along with this program; if
// * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
// * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
// *******************************************************************************/
//package org.integratedmodelling.klab.engine.runtime.code.groovy;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.integratedmodelling.kim.api.IParameters;
//import org.integratedmodelling.kim.api.IServiceCall;
//import org.integratedmodelling.kim.validation.KimNotification;
//import org.integratedmodelling.klab.Extensions;
//import org.integratedmodelling.klab.api.data.IGeometry;
//import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
//import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
//import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.knowledge.IObservable;
//import org.integratedmodelling.klab.api.model.IModel;
//import org.integratedmodelling.klab.api.model.INamespace;
//import org.integratedmodelling.klab.api.observations.IObservation;
//import org.integratedmodelling.klab.api.observations.scale.IExtent;
//import org.integratedmodelling.klab.api.observations.scale.IScale;
//import org.integratedmodelling.klab.api.provenance.IArtifact;
//import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
//import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
//import org.integratedmodelling.klab.engine.runtime.code.Expression;
//import org.integratedmodelling.klab.exceptions.KlabException;
//import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
//import org.integratedmodelling.klab.extensions.groovy.model.Concept;
//import org.integratedmodelling.klab.utils.Parameters;
//
//import groovy.lang.Binding;
//import groovy.lang.MissingPropertyException;
//import groovy.lang.Script;
//
//public class GroovyExpression extends Expression implements ILanguageExpression {
//
//    protected String code;
//    protected boolean negated = false;
//    protected Object object;
//    protected IServiceCall functionCall;
//    protected IModel model;
//    protected boolean isNull = false;
//    protected boolean isTrue = false;
//    protected boolean fubar = false;
//
//    private Set<String> defineIfAbsent = new HashSet<>();
//    private Set<String> overridingIds = new HashSet<>();
//    private Object[] overriding = null;
//
//    private Map<String, Concept> conceptCache = new HashMap<>();
//
//    // each thread gets its own instance of the script with bindings
//    private ThreadLocal<Boolean> initialized = new ThreadLocal<>();
//    private ThreadLocal<Script> script = new ThreadLocal<>();
//
//    /*
//     * either the Script or the compiled class are saved according to whether we want a thread-safe
//     * expression or not.
//     */
//    private Class<?> sclass = null;
//
//    IGeometry domain;
//    INamespace namespace;
//
//    private List<KimNotification> errors = new ArrayList<>();
//    private KlabGroovyShell shell = new KlabGroovyShell();
//    private String preprocessed = null;
//    private IRuntimeScope runtimeContext;
//    private Descriptor descriptor;
//
//    public GroovyExpression() {
//        initialized.set(Boolean.FALSE);
//    }
//
//    public boolean hasErrors() {
//        return errors.size() > 0;
//    }
//
//    public List<KimNotification> getErrors() {
//        return errors;
//    }
//
//    /*
//     * MUST be called in all situations.
//     */
//    public void initialize(Map<String, IObservable> inputs, Map<String, IObservable> outputs) {
//        compile(preprocess(code, inputs, outputs));
//        initialized.set(Boolean.TRUE);
//    }
//
//    GroovyExpression(String code, boolean preprocessed, ILanguageProcessor.Descriptor descriptor) {
//        initialized.set(Boolean.FALSE);
//        this.descriptor = descriptor;
//        this.code = code; // (code.startsWith("wrap()") ? code : ("wrap();\n\n" + code));
//        if (preprocessed) {
//            this.preprocessed = this.code;
//        }
//
//    }
//
//    private void compile(String code) {
//        try {
//            this.sclass = shell.parseToClass(code);
//        } catch (Throwable t) {
//            System.err.println("Groovy code won't parse: " + code);
//            this.fubar = true;
//        }
//    }
//
//    public Object eval(IParameters<String> parameters, IContextualizationScope scope) {
//
//        if (fubar) {
//            return null;
//        }
//
//        if (isTrue) {
//            return true;
//        }
//
//        if (isNull) {
//            return null;
//        }
//
//        boolean firstTime = false;
//        if (code != null) {
//
//            // initialized.get() == null happens when expressions are used in lookup tables
//            // or other code
//            // where the creating thread has
//            // finished. In this case we recycle them (TODO CHECK if this creates any
//            // problems).
//            if (initialized.get() == null || !initialized.get()) {
//                initialize(new HashMap<>(), new HashMap<>());
//                setupBindings(scope, parameters);
//                firstTime = true;
//            }
//
//            try {
//                Binding binding = script.get().getBinding();
//
//                @SuppressWarnings("unchecked")
//                Map<String, Object> artifactTable = (Map<String, Object>) binding.getVariable("_p");
//
//                if (overriding != null) {
//                    // caller has overridden some wrapped variables.
//                    for (int i = 0; i < overriding.length; i++) {
//                        if (overridingIds.contains(overriding[i])) {
//                            binding.setVariable("_j" + overriding[i], overriding[i + 1]);
//                        } else if (overriding[i + 1] instanceof IConcept) {
//                            binding.setVariable((String) overriding[i], getConceptPeer((IConcept) overriding[i + 1], binding));
//                        } else if (mustWrap(overriding[i + 1])) {
//                            if (firstTime) {
//                                Object wrapped = Wrapper.wrap(overriding[i + 1], (String) overriding[i], binding);
//                                overridingIds.add((String) overriding[i]);
//                                // also add to the artifact table unless it's there already.
//                                if (!artifactTable.containsKey(overriding[i])) {
//                                    artifactTable.put(overriding[i].toString(), wrapped);
//                                }
//                            }
//                            // set the overriding object in any case.
//                            binding.setVariable("_j" + overriding[i], overriding[i + 1]);
//                        } else if (overriding[i + 1] == null) {
//                            binding.setVariable((String) overriding[i], null);
//                        }
//                        i++;
//                    }
//                    // need to call override() another time if we want more overriding.
//                    overriding = null;
//                    firstTime = false;
//                }
//
//                for (String key : parameters.keySet()) {
//                    Object value = parameters.get(key);
//                    if (value instanceof IConcept) {
//                        // use cache to minimize the allocation of Groovy peers, which seems to be
//                        // very
//                        // costly.
//                        value = getConceptPeer((IConcept) value, binding);
//                    } else if (descriptor.getOptions().contains(CompilerOption.WrapParameters) && mustWrap(value)) {
//                        value = Wrapper.wrap(value, key, binding);
//                    }
//                    binding.setVariable(key, value);
//                }
//
//                /*
//                 * use the current scope and monitor
//                 */
//                binding.setVariable("_c", scope);
//                binding.setVariable("_monitor", scope.getMonitor());
//
//                IRuntimeScope rscope = (IRuntimeScope) scope;
//                if (rscope.getScale() != null && rscope.getScale().getTime() != null) {
//                    binding.setVariable("_jtime", rscope.getScale().getTime());
//                }
//
//                for (String v : defineIfAbsent) {
//                    if (!binding.hasVariable(v)) {
//                        binding.setVariable(v, Double.NaN);
//                    }
//                }
//                return unwrap(script.get().run());
//
//            } catch (MissingPropertyException e) {
//                String property = e.getProperty();
//                if (!defineIfAbsent.contains(property)) {
//                    scope.getMonitor().warn(
//                            "variable " + property + " undefined. Defining as numeric no-data (NaN) for subsequent evaluations.");
//                    defineIfAbsent.add(property);
//                }
//            } catch (Throwable t) {
//                throw new KlabException(t);
//            }
//        } else if (object != null) {
//            return object;
//        } else if (functionCall != null) {
//            return Extensions.INSTANCE.callFunction(functionCall, scope);
//        }
//        return null;
//    }
//
//    private Object getConceptPeer(IConcept value, Binding binding) {
//
//        Concept ret = conceptCache.get(value.toString());
//        if (ret == null) {
//            ret = new Concept(value, binding);
//            conceptCache.put(value.toString(), ret);
//        }
//        return ret;
//    }
//
//    /**
//     * True if this is an object we provide wrappers for. This intentionally does not include
//     * concepts, which are wrapped individually on-demand but the wrappers are cached on a
//     * per-thread basis.
//     * 
//     * @param object
//     * @return
//     */
//    private boolean mustWrap(Object object) {
//        return object instanceof IExtent || object instanceof IObservation || object instanceof IScale;
//    }
//
//    /**
//     * This only gets done once per thread. All wrappers (except for concepts) are created once and
//     * reused by swapping the wrapped object instead of the wrapper itself. This is to avoid
//     * creating wrappers from inside Groovy at every eval, which has proved extremely slow.
//     * <p>
//     * FIXME remove all wrappers and use GroovyObjectSupport on the original objects. For
//     * observations, the problem of context localization during local access needs to be solved
//     * before doing so.
//     * 
//     * @param scope
//     * @param parameters
//     */
//    private void setupBindings(IContextualizationScope scope, IParameters<String> parameters) {
//
//        Binding bindings = new Binding();
//
//        /*
//         * overridingIds are those vars that may change at each evaluation if they appear in the
//         * parameters. If so, we leave the wrapper as is but we change the object pointed to.
//         */
//        this.overridingIds.clear();
//        try {
//            script.set(shell.createFromClass(sclass, bindings));
//        } catch (Exception e) {
//            throw new KlabInternalErrorException(e);
//        }
//
//        if (scope.getScale() != null) {
//            bindings.setVariable(eid2j("scale"), scope.getScale());
//            overridingIds.add("scale");
//        }
//
//        bindings.setVariable("provenance", scope.getProvenance());
//        bindings.setVariable("structure", ((IRuntimeScope) scope).getStructure());
//        overridingIds.add("provenance");
//        overridingIds.add("structure");
//        if (scope.getSession().getState().getInspector() != null) {
//            bindings.setVariable("inspector", scope.getSession().getState().getInspector());
//            overridingIds.add("inspector");
//        }
//        
//        if (scope.getScale() != null && scope.getScale().getSpace() != null) {
//            Wrapper.wrap(scope.getScale().getSpace(), "space", bindings);
//            overridingIds.add("space");
//        }
//        if (scope.getScale() != null && scope.getScale().getTime() != null) {
//            Wrapper.wrap(scope.getScale().getTime(), "time", bindings);
//            overridingIds.add("time");
//        }
//
//        if (scope.getContextObservation() != null) {
//            // context is not overriddable
//            Wrapper.wrap(scope.getContextObservation(), "context", bindings);
//        }
//
//        /*
//         * Any artifacts used in non-scalar context goes into the _p map. We should rename it to
//         * _nonscalars or _artifacts just for clarity.
//         */
//        Map<String, Object> artifactTable = new HashMap<>();
//        for (String identifier : this.descriptor.getIdentifiers()) {
//            if (this.descriptor.isNonscalar(identifier)) {
//                IArtifact artifact = scope.getArtifact(identifier);
//                if (artifact != null) {
//                    artifactTable.put(identifier, Wrapper.wrap(artifact, identifier, bindings));
//                }
//            }
//        }
//
//        if (parameters.containsKey("self") && parameters.get("self") instanceof IObservation
//                && !artifactTable.containsKey("self")) {
//            artifactTable.put("self", Wrapper.wrap(parameters.get("self"), "self", bindings));
//        } else if (scope.getTargetArtifact() != null) {
//            artifactTable.put("target", Wrapper.wrap(scope.getTargetArtifact(), "self", bindings));
//        }
//
//        if (scope.getTargetSemantics() != null) {
//            bindings.setVariable("semantics", scope.getTargetSemantics());
//        }
//
//        bindings.setVariable("_p", artifactTable);
//        bindings.setVariable("_exp", this);
//        bindings.setVariable("_ns", scope.getNamespace());
//        bindings.setVariable("_c", scope);
//        bindings.setVariable("_monitor", scope.getMonitor());
//    }
//
//    private String preprocess(String code, Map<String, IObservable> inputs, Map<String, IObservable> outputs) {
//
//        if (this.preprocessed != null) {
//            return this.preprocessed;
//        }
//
//        Set<String> knownKeys = new HashSet<>();
//        if (inputs != null) {
//            knownKeys.addAll(inputs.keySet());
//        }
//        if (outputs != null) {
//            knownKeys.addAll(outputs.keySet());
//        }
//
//        GroovyExpressionPreprocessor processor = new GroovyExpressionPreprocessor(namespace, knownKeys, domain,
//                runtimeContext.getExpressionContext(), true, new HashSet<>());
//        this.preprocessed = processor.process(code);
//        this.errors.addAll(processor.getErrors());
//
//        return this.preprocessed;
//    }
//
//    public String toString() {
//        return code;
//    }
//
//    public void setNegated(boolean negate) {
//        negated = negate;
//    }
//
//    public boolean isNegated() {
//        return negated;
//    }
//
//    @Override
//    public Object eval(IContextualizationScope scope, Object... parameters) {
//        return eval(Parameters.create(parameters), scope);
//    }
//
//    @Override
//    public Object unwrap(Object value) {
//        if (value instanceof Wrapper) {
//            return ((Wrapper<?>) value).unwrap();
//        } else if (value instanceof Concept) {
//            return ((Concept) value).getConcept();
//        }
//        return value;
//    }
//
//    public static String eid2j(String id) {
//        return "_j" + id;
//    }
//
//    public static String jid2e(String id) {
//        return id.substring(2);
//    }
//
//    @Override
//    public String getLanguage() {
//        return GroovyProcessor.ID;
//    }
//
//    @Override
//    public ILanguageExpression override(Object... variables) {
//        this.overriding = variables;
//        return this;
//    }
//
//    @Override
//    public Collection<String> getIdentifiers() {
//        return descriptor.getIdentifiers();
//    }
//
//}

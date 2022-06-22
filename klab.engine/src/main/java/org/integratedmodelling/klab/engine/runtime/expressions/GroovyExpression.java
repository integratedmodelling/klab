/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.engine.runtime.expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.code.Expression;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.Parameters;

import groovy.lang.Binding;
import groovy.lang.MissingPropertyException;
import groovy.lang.Script;

public class GroovyExpression extends Expression implements ILanguageExpression {

    protected String code;
    protected boolean negated = false;
    protected Object object;
    protected IServiceCall functionCall;
    protected IModel model;
    protected boolean isNull = false;
    protected boolean isTrue = false;
    protected boolean fubar = false;

    private Set<String> defineIfAbsent = new HashSet<>();
    // each thread gets its own instance of the script with bindings
    private ThreadLocal<Boolean> initialized = new ThreadLocal<>();
    private ThreadLocal<Script> script = new ThreadLocal<>();

    /*
     * either the Script or the compiled class are saved according to whether we want a thread-safe
     * expression or not.
     */
    private Class<?> sclass = null;

    IGeometry domain;
    INamespace namespace;

    private List<KimNotification> errors = new ArrayList<>();
    private KlabGroovyShell shell = new KlabGroovyShell();
    private String preprocessed = null;
    private IRuntimeScope runtimeContext;
    private Descriptor descriptor;
    private Map<String, Object> variables;

    public GroovyExpression() {
        initialized.set(Boolean.FALSE);
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public List<KimNotification> getErrors() {
        return errors;
    }

    /*
     * MUST be called in all situations.
     */
    public void initialize(Map<String, IObservable> inputs, Map<String, IObservable> outputs) {
        compile(preprocess(code, inputs, outputs));
        initialized.set(Boolean.TRUE);
    }

    GroovyExpression(String code, boolean preprocessed, ILanguageProcessor.Descriptor descriptor) {
        initialized.set(Boolean.FALSE);
        this.descriptor = descriptor;
        this.variables = descriptor.getVariables();
        this.code = code;
        if (preprocessed) {
            this.preprocessed = this.code;
        }

    }

    private void compile(String code) {
        try {
            this.sclass = shell.parseToClass(code);
        } catch (Throwable t) {
            System.err.println("Groovy code won't parse: " + code);
            this.fubar = true;
        }
    }

    public Object eval(IParameters<String> parameters, IContextualizationScope scope) {

        if (fubar) {
            return null;
        }

        if (isTrue) {
            return true;
        }

        if (isNull) {
            return null;
        }

        if (code != null) {

            // initialized.get() == null happens when expressions are used in lookup tables
            // or other code
            // where the creating thread has
            // finished. In this case we recycle them (TODO CHECK if this creates any
            // problems).
            if (initialized.get() == null || !initialized.get()) {
                initialize(new HashMap<>(), new HashMap<>());
                setupBindings(scope, parameters);
            }

            try {
                Binding binding = script.get().getBinding();

                if (scope != null) {
                    if (scope.getScale() != null) {
                        binding.setVariable("scale", scope.getScale());
                    }

                    if (scope.getScale() != null && scope.getScale().getSpace() != null) {
                        binding.setVariable("space", scope.getScale().getSpace());
                    }

                    if (scope.getScale() != null && scope.getScale().getTime() != null) {
                        binding.setVariable("time", scope.getScale().getTime());
                    }

                    if (scope.getContextObservation() != null) {
                        binding.setVariable("context", scope.getContextObservation());
                    }

                    if (scope.getTargetSemantics() != null) {
                        binding.setVariable("semantics", scope.getTargetSemantics());
                    }

                    /*
                     * use the current scope and monitor
                     */
                    binding.setVariable("_c", scope);
                    binding.setVariable("_monitor", scope.getMonitor());
                }

                for (String v : defineIfAbsent) {
                    if (!binding.hasVariable(v)) {
                        binding.setVariable(v, Double.NaN);
                    }
                }
                return script.get().run();

            } catch (MissingPropertyException e) {
                String property = e.getProperty();
                if (!defineIfAbsent.contains(property)) {
                    scope.getMonitor().warn(
                            "variable " + property + " undefined. Defining as numeric no-data (NaN) for subsequent evaluations.");
                    defineIfAbsent.add(property);
                }
            } catch (Throwable t) {
                throw new KlabException(t);
            }
        } else if (object != null) {
            return object;
        } else if (functionCall != null) {
            return Extensions.INSTANCE.callFunction(functionCall, scope);
        }
        return null;
    }

    /**
     * This only gets done once per thread. Uses a new compiled class per thread and sets up the
     * bindings with any invariant objects. The remaining variables are set before each call.
     * 
     * @param scope
     * @param parameters
     */
    private void setupBindings(IContextualizationScope scope, IParameters<String> parameters) {

        Binding bindings = new Binding();

        /*
         * inherent variables have known values at the time of compilation.
         */
        if (variables != null) {
            for (String key : variables.keySet()) {
                bindings.setProperty(key, variables.get(key));
            }
        }

        if (scope != null) {
            bindings.setVariable("provenance", scope.getProvenance());
            bindings.setVariable("structure", ((IRuntimeScope) scope).getStructure());
            bindings.setVariable("_ns", scope.getNamespace());
            bindings.setVariable("_monitor", scope.getMonitor());
            if (scope.getSession().getState().getInspector() != null) {
                bindings.setVariable("inspector", scope.getSession().getState().getInspector());
            }
        }

        try {
            script.set(shell.createFromClass(sclass, bindings));
        } catch (Exception e) {
            throw new KlabInternalErrorException(e);
        }

    }

    private String preprocess(String code, Map<String, IObservable> inputs, Map<String, IObservable> outputs) {

        if (this.preprocessed != null) {
            return this.preprocessed;
        }
        
        GroovyExpressionPreprocessor processor = new GroovyExpressionPreprocessor(namespace,
                runtimeContext.getExpressionContext(), new HashSet<>());
        this.preprocessed = processor.process(code);
        this.errors.addAll(processor.getErrors());
        this.variables = processor.getVariables();

        return this.preprocessed;
    }

    public String toString() {
        return code;
    }

    public void setNegated(boolean negate) {
        negated = negate;
    }

    public boolean isNegated() {
        return negated;
    }

    @Override
    public Object eval(IContextualizationScope scope, Object... parameters) {
        return eval(Parameters.create(parameters), scope);
    }

    @Override
    public String getLanguage() {
        return GroovyProcessor.ID;
    }

    @Override
    public ILanguageExpression override(Object... variables) {
        // this.overriding = variables;
        return this;
    }

    @Override
    public Collection<String> getIdentifiers() {
        return descriptor.getIdentifiers();
    }

    @Override
    public Object unwrap(Object object) {
        return object;
    }

}

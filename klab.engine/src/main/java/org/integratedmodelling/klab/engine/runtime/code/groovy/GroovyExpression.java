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
package org.integratedmodelling.klab.engine.runtime.code.groovy;

import java.util.ArrayList;
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
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.engine.runtime.code.Expression;
import org.integratedmodelling.klab.exceptions.KlabException;

import groovy.lang.Binding;
import groovy.lang.MissingPropertyException;
import groovy.lang.Script;

public class GroovyExpression extends Expression {

	protected String code;
	protected boolean negated = false;
	protected Object object;
	protected IServiceCall functionCall;
	protected IModel model;
	protected boolean isNull = false;
	protected boolean isTrue = false;
	private boolean initialized = false;
	private Set<String> defineIfAbsent = new HashSet<>();

	// each thread gets its own instance of the script with bindings
	ThreadLocal<Script> script = new ThreadLocal<>();

	/*
	 * either the Script or the compiled class are saved according to whether we
	 * want a thread-safe expression or not.
	 */
	private Class<?> sclass = null;
	// Script script;

	IGeometry domain;
	INamespace namespace;

	private List<KimNotification> errors = new ArrayList<>();
	private KlabGroovyShell shell = new KlabGroovyShell();
	private String preprocessed = null;
	private IRuntimeContext runtimeContext;
	private Descriptor descriptor;

	public GroovyExpression() {
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
		initialized = true;
	}

	GroovyExpression(String code, boolean preprocessed, ILanguageProcessor.Descriptor descriptor) {
		this.descriptor = descriptor;
		this.code = (code.startsWith("wrap()") ? code : ("wrap();\n\n" + code));
		if (preprocessed) {
			this.preprocessed = this.code;
		}

	}

	private void compile(String code) {
		this.sclass = shell.parseToClass(code);
		// this.script = shell.parse(code);
	}

	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {

		if (isTrue) {
			return true;
		}

		if (isNull) {
			return null;
		}

		if (code != null) {

			if (!initialized) {
				initialize(new HashMap<>(), new HashMap<>());
			}

			try {
				if (script.get() == null) {
					System.out.println("Creating script for " + code);
					script.set(shell.createFromClass(sclass, new Binding()));
				}
				setBindings(script.get().getBinding(), context, parameters);
				return script.get().run();

			} catch (MissingPropertyException e) {
				String property = e.getProperty();
				if (!defineIfAbsent.contains(property)) {
					context.getMonitor().warn("variable " + property
							+ " undefined. Defining as numeric no-data (NaN) for subsequent evaluations.");
					defineIfAbsent.add(property);
				}
			} catch (Throwable t) {
				throw new KlabException(t);
			}
		} else if (object != null) {
			return object;
		} else if (functionCall != null) {
			return Extensions.INSTANCE.callFunction(functionCall, context);
		}
		return null;
	}

	private Binding setBindings(Binding binding, IComputationContext context, IParameters<String> parameters) {

		// predefine this if we have a target artifact and we haven't set it from the
		// outside, unless we're instantiating (TODO use a better check)
		if (!parameters.containsKey("self") && context.getTargetArtifact() != null
				&& !(context.getTargetArtifact() instanceof ObservationGroup)) {
			binding.setVariable("_self", context.getTargetArtifact());
		}

		for (String key : parameters.keySet()) {
			binding.setVariable(key, parameters.get(key));
		}

		for (String v : defineIfAbsent) {
			if (!binding.hasVariable(v)) {
				binding.setVariable(v, Double.NaN);
			}
		}

		/*
		 * add any artifact names used in a non-scalar context to the _p map, compiled
		 * in by the preprocessor.
		 */
		Map<String, Object> nonscalar = new HashMap<>();
		for (String identifier : this.descriptor.getIdentifiers()) {
			if (this.descriptor.isNonscalar(identifier)) {
				IArtifact artifact = context.getArtifact(identifier);
				if (artifact != null) {
					nonscalar.put(identifier, artifact);
				}
			}
		}
		if (parameters.containsKey("self") && parameters.get("self") instanceof IObservation
				&& !nonscalar.containsKey("self")) {
			nonscalar.put("self", parameters.get("self"));
		}

		binding.setVariable("_p", nonscalar);
		binding.setVariable("_ns", context.getNamespace());
		binding.setVariable("_c", context);
		binding.setVariable("_monitor", context.getMonitor());

		return binding;
	}

	private String preprocess(String code, Map<String, IObservable> inputs, Map<String, IObservable> outputs) {

		if (this.preprocessed != null) {
			return this.preprocessed;
		}

		Set<String> knownKeys = new HashSet<>();
		if (inputs != null) {
			knownKeys.addAll(inputs.keySet());
		}
		if (outputs != null) {
			knownKeys.addAll(outputs.keySet());
		}

		GroovyExpressionPreprocessor processor = new GroovyExpressionPreprocessor(namespace, knownKeys, domain,
				runtimeContext, true);
		this.preprocessed = processor.process(code);
		this.errors.addAll(processor.getErrors());

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

}

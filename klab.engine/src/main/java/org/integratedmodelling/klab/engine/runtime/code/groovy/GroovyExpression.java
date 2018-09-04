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

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
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
import org.integratedmodelling.klab.engine.runtime.code.Expression;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Path;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.MissingPropertyException;
import groovy.lang.Script;

public class GroovyExpression extends Expression {

	private static final String BASE_ACTION_CLASS = "org.integratedmodelling.klab.extensions.groovy.ActionBase";

	protected String code;
	protected boolean negated = false;
	protected Object object;
	protected IServiceCall functionCall;
	protected IModel model;
	protected boolean isNull = false;
	protected boolean isTrue = false;
	private boolean initialized = false;
	private Set<String> defineIfAbsent = new HashSet<>();

	Script script;
	IGeometry domain;
	INamespace namespace;

	private List<KimNotification> errors = new ArrayList<>();
	private CompilerConfiguration compiler = new CompilerConfiguration();
	private GroovyShell shell;
	private String preprocessed = null;

	private Descriptor descriptor;

	/*
	 * used by k.LAB to instantiate Groovy expressions. Will automatically add
	 * imports for any KimImport class.
	 */
	public GroovyExpression() {

		ImportCustomizer customizer = new ImportCustomizer();
		for (Class<?> cls : Extensions.INSTANCE.getKimImports()) {
			customizer.addImport(Path.getLast(cls.getCanonicalName(), '.'), cls.getCanonicalName());
		}
		compiler.addCompilationCustomizers(customizer);
	}

	public boolean hasErrors() {
		return errors.size() > 0;
	}

	public List<KimNotification> getErrors() {
		return errors;
	}

	/*
	 * used by Thinklab - when using the API use the String constructor. MUST be
	 * called in all cases.
	 */
	public void initialize(Map<String, IObservable> inputs, Map<String, IObservable> outputs) {
		compile(preprocess(code, inputs, outputs));
		initialized = true;
	}

	// /**
	// * Simple expression without context or receivers. NOT PREPROCESSED in the
	// context it's in.
	// *
	// * @param code
	// * @param inputs
	// * @param outputs
	// * @param domain
	// */
	// public GroovyExpression(String code, Map<String, IObservable> inputs,
	// Map<String, IObservable> outputs, IGeometry domain) {
	//
	// ImportCustomizer customizer = new ImportCustomizer();
	// for (Class<?> cls : Extensions.INSTANCE.getKimImports()) {
	// customizer.addImport(Path.getLast(cls.getCanonicalName(), '.'),
	// cls.getCanonicalName());
	// }
	// compiler.addCompilationCustomizers(customizer);
	//
	// this.code = (code.startsWith("wrap()") ? code : ("wrap();\n\n" + code));
	// this.domain = domain;
	// this.compiler.setScriptBaseClass(getBaseClass());
	// initialize(inputs, outputs);
	// }

	// /**
	// * Preprocess with the dependencies of the passed model preset in symbol
	// table.
	// *
	// * @param code
	// * @param model
	// */
	// GroovyExpression(String code, IModel model) {
	//
	// ImportCustomizer customizer = new ImportCustomizer();
	// for (Class<?> cls : Extensions.INSTANCE.getKimImports()) {
	// customizer.addImport(Path.getLast(cls.getCanonicalName(), '.'),
	// cls.getCanonicalName());
	// }
	// compiler.addCompilationCustomizers(customizer);
	//
	// this.code = (code.startsWith("wrap()") ? code : ("wrap();\n\n" + code));
	// Map<String, IObservable> inputs = new HashMap<>();
	// for (IObservable d : model.getDependencies()) {
	// inputs.put(d.getLocalName(), d);
	// }
	// initialize(inputs, null);
	// }

	// GroovyExpression(String code, INamespace namespace, IGeometry domain) {
	//
	// ImportCustomizer customizer = new ImportCustomizer();
	// for (Class<?> cls : Extensions.INSTANCE.getKimImports()) {
	// customizer.addImport(Path.getLast(cls.getCanonicalName(), '.'),
	// cls.getCanonicalName());
	// }
	// compiler.addCompilationCustomizers(customizer);
	// this.namespace = namespace;
	// this.code = (code.startsWith("wrap()") ? code : ("wrap();\n\n" + code));
	// this.domain = domain;
	// }

	// GroovyExpression(String code) {
	// ImportCustomizer customizer = new ImportCustomizer();
	// for (Class<?> cls : Extensions.INSTANCE.getKimImports()) {
	// customizer.addImport(Path.getLast(cls.getCanonicalName(), '.'),
	// cls.getCanonicalName());
	// }
	// compiler.addCompilationCustomizers(customizer);
	// this.code = (code.startsWith("wrap()") ? code : ("wrap();\n\n" + code));
	// }

	GroovyExpression(String code, boolean preprocessed, ILanguageProcessor.Descriptor descriptor) {

		ImportCustomizer customizer = new ImportCustomizer();
		for (Class<?> cls : Extensions.INSTANCE.getKimImports()) {
			customizer.addImport(Path.getLast(cls.getCanonicalName(), '.'), cls.getCanonicalName());
		}
		compiler.addCompilationCustomizers(customizer);
		this.descriptor = descriptor;
		this.code = (code.startsWith("wrap()") ? code : ("wrap();\n\n" + code));
		if (preprocessed) {
			this.preprocessed = this.code;
		}
	}

	private void compile(String code) {
		this.compiler.setScriptBaseClass(getBaseClass());
		this.shell = new GroovyShell(this.getClass().getClassLoader(), new Binding(), compiler);
		this.script = shell.parse(code);
	}

	protected String getBaseClass() {

		/*
		 * choose proper class according to domains so that the appropriate functions
		 * are supported.
		 */
		// if (domain != null) {
		//
		// if (domain.contains(KLAB.c(NS.SPACE_DOMAIN)) &&
		// domain.contains(KLAB.c(NS.TIME_DOMAIN))) {
		// return "org.integratedmodelling.thinklab.actions.SpatioTemporalActionScript";
		// } else if (domain.contains(KLAB.c(NS.SPACE_DOMAIN))) {
		// return "org.integratedmodelling.thinklab.actions.SpatialActionScript";
		// } else if (domain.contains(KLAB.c(NS.TIME_DOMAIN))) {
		// return "org.integratedmodelling.thinklab.actions.TemporalActionScript";
		// }
		// }
		return BASE_ACTION_CLASS;
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
				setBindings(script.getBinding(), context, parameters);
				return script.run();
			} catch (MissingPropertyException e) {
				String property = e.getProperty();
				context.getMonitor().warn("variable " + property
						+ " undefined. Defining as numeric no-data (NaN) for subsequent evaluations.");
				defineIfAbsent.add(property);
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

	private void setBindings(Binding binding, IComputationContext context, IParameters<String> parameters) {

		// predefine this if we have a target artifact and we haven't set it from the
		// outside
		if (!parameters.containsKey("self") && context.getTargetArtifact() != null) {
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

		Map<String, Object> nonscalar = new HashMap<>();

		/*
		 * add any artifact names used in a non-scalar context to the _p map, compiled
		 * in by the preprocessor.
		 */
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
		GroovyExpressionPreprocessor processor = new GroovyExpressionPreprocessor(namespace, knownKeys, domain);
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

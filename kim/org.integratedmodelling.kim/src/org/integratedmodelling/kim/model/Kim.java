/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.kim.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Issue;
import org.integratedmodelling.kim.api.IConceptDescriptor;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimMacro;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IKimWorkspace;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.kim.ClassifierRHS;
import org.integratedmodelling.kim.kim.ConceptDeclaration;
import org.integratedmodelling.kim.kim.Literal;
import org.integratedmodelling.kim.kim.MapEntry;
import org.integratedmodelling.kim.kim.Model;
import org.integratedmodelling.kim.kim.ModelBodyStatement;
import org.integratedmodelling.kim.kim.Namespace;
import org.integratedmodelling.kim.kim.ObservableSemantics;
import org.integratedmodelling.kim.kim.Quantity;
import org.integratedmodelling.kim.kim.Urn;
import org.integratedmodelling.kim.kim.Value;
import org.integratedmodelling.kim.utils.ParseHelper;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.kim.validation.KimValidator;
import org.integratedmodelling.klab.api.data.CRUDOperation;
import org.integratedmodelling.klab.api.data.TemplateValue;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.CompileInfo;
import org.integratedmodelling.klab.common.SemanticType;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.rest.CompileNotificationReference;
import org.integratedmodelling.klab.rest.NamespaceCompilationResult;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.klab.utils.StringUtil;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Parsing functions and interfaces. Holds the state of the knowledge base as
 * new concepts are declared, so that the appropriate highlighting can be
 * computed.
 * 
 * @author Ferd
 *
 */
public enum Kim {

	INSTANCE;

	private Validator validatorCallback = null;

	@Inject
	private IGrammarAccess grammarAccess;

	@Inject
	ParseHelper<Model> observableParser;

	public IKimObservable declare(String declaration) {
		try {
			ObservableSemantics parsed = observableParser.parse(declaration).getObservable();
			KimObservable interpreted = Kim.INSTANCE.declareObservable(parsed);
			return interpreted;
		} catch (Exception e) {
			// just return null
		}
		return null;
	}

	/**
	 * Call before keyword list can be obtained
	 * 
	 * @param injector
	 */
	public void setup(Injector injector) {
		injector.injectMembers(this);
	}

	/**
	 * Errors added externally to the validator that the validator must report.
	 */
	private Map<String, CompileInfo> compileInfo = Collections.synchronizedMap(new HashMap<>());

	/**
	 * This contains concept descriptors for all concepts encountered, including
	 * library ones and anything added externally, flattened to include children and
	 * indexed by name.
	 */
	private Map<String, Map<String, ConceptDescriptor>> namespaceRegister = new HashMap<>();

	/**
	 * Core concepts (observation ontology) are now explicitly declared for use in
	 * worldviews, and are validated through a callback.
	 */
	private Map<String, EnumSet<Type>> coreConcepts = new HashMap<>();
	private List<Notifier> notifiers = new ArrayList<>();
	private boolean initialBuildDone;

	/*
	 * All namespaces get registered here. They don't get removed currently, so they
	 * may be obsoleted.
	 */
	Map<String, IKimNamespace> namespaceRegistry = new HashMap<>();

	/**
	 * Specific for orphan namespace that are our of any projects. These only happen
	 * when standalone mode is used.
	 */
	Map<String, IKimNamespace> orphanNamespaceRegistry = new HashMap<>();

	/*
	 * Same with projects
	 */
	// Map<String, IKimProject> projectRegistry = new HashMap<>();
	// Map<File, IKimProject> projectRootRegistry = new HashMap<>();

	// workspaces may be pre-set by IDEs or other clients
	KimWorkspace worldview;
	KimWorkspace userWorkspace;
	Map<String, KimWorkspace> projectWorkspaces = new HashMap<>();
	Map<String, KimWorkspace> workspaces = new HashMap<>();

	/*
	 * flags for resources and URNs
	 */
	public static final int ALIVE = 0x000001;
	public static final int DEAD = 0x000002;
	public static final int ACCESSIBLE = 0x000004;
	public static final int KNOWN = 0x000008;
	public static final int ERROR = 0x000010;

	/**
	 * these can be installed to resolve any resource URI to the workspace root. For
	 * now, k.LAB requires all resources to be filesystem based.
	 */
	public static interface UriResolver {

		File resolveResourceUriToWorkspaceRootDirectory(URI uri);
	}

	private Map<String, UriResolver> uriResolvers = new HashMap<>();

	/**
	 * Set just before validating any resource. Accurate only as long as the
	 * resources are validated sequentially. Makes no sense after validation.
	 */
	private KimLoader currentLoader;

	private Collection<File> projectFiles;

	private Collection<File> worldviewFiles;

	public static class ConceptDescriptor implements IConceptDescriptor {

		private String name;
		private EnumSet<Type> flags = EnumSet.noneOf(Type.class);
		private String documentation;
		private IKimConceptStatement macro;

		public ConceptDescriptor() {
		}

		public ConceptDescriptor(String id, Type... flags) {
			this.name = id;
			this.flags.addAll(Arrays.asList(flags));
		}

		public ConceptDescriptor(String id, String documentation, Type... flags) {
			this.name = id;
			this.flags.addAll(Arrays.asList(flags));
			this.documentation = documentation;
		}

		public ConceptDescriptor(String id, EnumSet<Type> type, String string) {
			this.name = id;
			this.flags.addAll(type);
			this.documentation = string;
		}

		public ConceptDescriptor(String id, EnumSet<Type> type, IKimConceptStatement macro, String string) {
			this.name = id;
			this.flags.addAll(type);
			this.documentation = string;
			this.macro = macro;
		}

		@Override
		public EnumSet<Type> getFlags() {
			return flags;
		}

		@Override
		public boolean is(Type type) {
			return flags.contains(type);
		}

		@Override
		public IKimConceptStatement getMacro() {
			return macro;
		}

		@Override
		public boolean isUndefined() {
			return flags.isEmpty();
		}

		@Override
		public String getDocumentation() {
			return documentation;
		}

		public void setDocumentation(String documentation) {
			this.documentation = documentation;
		}

		@Override
		public String toString() {
			String ret = "<";
			if (this.isUndefined()) {
				ret += "UNDEFINED";
			} else {
				for (Type t : flags) {
					ret += (ret.length() == 1 ? "" : " ") + t.name();
				}
			}
			return ret + ">";
		}

		@Override
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static enum FunctionType {
		SPACE_DOMAIN, TIME_DOMAIN, TABULAR_DATA, SPATIAL_DATA, OBJECT_DATA, STATE_CONTEXTUALIZER,
		PROCESS_CONTEXTUALIZER, SUBJECT_CONTEXTUALIZER, EVENT_CONTEXTUALIZER, RELATIONSHIP_CONTEXTUALIZER,
		SUBJECT_INSTANTIATOR, EVENT_INSTANTIATOR, RELATIONSHIP_INSTANTIATOR, LITERAL_ATOMIC, LITERAL_LIST,
	}

	public static enum LiteralType {
		NUMBER, TEXT, BOOLEAN, LIST
	}

	/**
	 * One or more notifiers can be installed to listen to namespaces being created
	 * or redefined.
	 * 
	 * @author Ferd
	 *
	 */
	public static interface Notifier {

		/**
		 * Main method to notify that a new or updated namespace is available. The
		 * passed bean holds all the info required to register the namespace in the
		 * runtime.
		 * 
		 * @param namespace
		 * @return whatever object the namespace produced. Will be a INamespace in all
		 *         API-compliant implementations, but we avoid the dependency here and
		 *         make it any object.
		 */
		Object synchronizeNamespaceWithRuntime(IKimNamespace namespace);
	}

	public static class UrnDescriptor {

		private int flags = 0;
		private String documentation;
		List<Pair<String, IArtifact.Type>> dependencies = new ArrayList<>();

		public UrnDescriptor(int flags) {
			this.flags = flags;
		}

		public Collection<Pair<String, IArtifact.Type>> getDependencies() {
			return dependencies;
		}

		public void addDependency(String name, IArtifact.Type type) {
			dependencies.add(new Pair<>(name, type));
		}

		public UrnDescriptor(int flags, String documentation) {
			this.flags = flags;
			this.documentation = documentation;
		}

		public boolean isAlive() {
			return (flags & ALIVE) != 0;
		}

		public boolean isDead() {
			return (flags & DEAD) != 0;
		}

		public boolean isAccessible() {
			return (flags & ACCESSIBLE) != 0;
		}

		public boolean isKnown() {
			return (flags & KNOWN) != 0;
		}

		public String getDocumentation() {
			return documentation;
		}

		public void setAccessible() {
			this.flags |= ACCESSIBLE;
		}

		public void setOnline() {
			this.flags |= ALIVE;
		}

		public void setError() {
			this.flags |= ERROR;
		}

		public void setKnown() {
			this.flags |= KNOWN;
		}

	}

	public static interface Validator {

		public static final String OFFLINE = "__OFFLINE__";
		public static final String UNKNOWN_AUTHORITY = "__UNKNOWN_AUTHORITY__";

		/**
		 * Quickly check if the passed function name is known to the runtime.
		 * 
		 * @param functionName
		 * @return true if known
		 */
		boolean isFunctionKnown(String functionName);

		/**
		 * Quickly check if the passed annotation name is known to the runtime.
		 * 
		 * @param annotationName
		 * @return true if known
		 */
		boolean isAnnotationKnown(String annotationName);

		/**
		 * Return any errors, warnings and info messages caused by the passed function.
		 * Only called if {@link #isFunctionKnown(String)} returns true.
		 * 
		 * @param functionCall
		 * @param expectedType
		 * @return pairs of message + level, or an empty list if OK.
		 */
		List<Pair<String, Level>> validateFunction(IServiceCall functionCall, Set<IArtifact.Type> expectedType);

		/**
		 * Return any errors, warnings and info messages caused by the passed annotation
		 * or its usage. Only called if {@link #isAnnotationKnown(String)} returns true.
		 * 
		 * @param annotationCall
		 * @param target         the statement that the annotation describes
		 * @return pairs of message + level, or an empty list if OK.
		 */
		List<Pair<String, Level>> validateAnnotation(IServiceCall annotationCall, IKimStatement target);

		/**
		 * 
		 * @param functionId
		 * @return prototype or null
		 */
		IPrototype getFunctionPrototype(String functionId);

		/**
		 * 
		 * @param functionId
		 * @return prototype or null
		 */
		IPrototype getAnnotationPrototype(String functionId);

		/**
		 * Return readable information about an observable, optionally with <b> or
		 * <li>tags for display.
		 * 
		 * @param observable
		 * @param formatted
		 * @return
		 */
		String getObservableInformation(IKimObservable observable, boolean formatted);

		/**
		 * Return readable information about an observable, optionally with <b> or
		 * <li>tags for display.
		 * 
		 * @param observable
		 * @param formatted
		 * @return
		 */
		String getConceptInformation(IKimConcept observable, boolean formatted);

		/**
		 * Return readable information about an authority concept, optionally with
		 * formatting tags, along with a boolean that specifies whether the identity
		 * parsed correctly or not. Return the following special values if:
		 * <ul>
		 * <li>OFFLINE if the authority cannot be contacted (engine is off) but is
		 * potentially there;</li>
		 * <li>UNKNOWN_AUTHORITY if the authority is known not to exist.</li>
		 * </ul>
		 * If any of the above is returned, the boolean will be ignored.
		 * <p>
		 * 
		 * @param authority
		 * @param identity
		 * @param formatted
		 * @return
		 */
		Pair<String, Boolean> getIdentityInformation(String authority, String identity, boolean formatted);

		/**
		 * Return a descriptor for the passed URN. Never return null - if a URN is
		 * unknown, just say so.
		 * 
		 * @param urn
		 * @return a URN descriptor
		 */
		UrnDescriptor classifyUrn(String urn);

		/**
		 * If the core type is known, return the type of declarable this core type
		 * represents. If it is unknown or it's not declarable, return an empty typeset.
		 * 
		 * @param string
		 * @param statedType
		 * @return the type
		 */
		EnumSet<Type> classifyCoreType(String string, EnumSet<Type> statedType);

		/**
		 * Called whenever a 'is core <coretype>' statement is validated to establish
		 * the worldview peer of the passed core concept.
		 * 
		 * @param coreConcept
		 * @param worldviewConcept
		 */
		void createWorldviewPeerConcept(String coreConcept, String worldviewConcept);

	}

	private Kim() {
		workspaces.put("worldview", this.worldview = new KimWorkspace("worldview"));
		workspaces.put("workspace", this.userWorkspace = new KimWorkspace("workspace"));
	}

	public KimConcept declareConcept(ConceptDeclaration declaration) {
		return declaration == null ? null : KimConcept.normalize(declaration, null, true);
	}

	public KimConcept declareConcept(ConceptDeclaration declaration, IKimMacro macro) {
		return declaration == null ? null : KimConcept.normalize(declaration, null, true);
	}

	public KimObservable declareObservable(ObservableSemantics declaration) {
		return declaration == null ? null : KimObservable.normalize(declaration, null);
	}

	public KimConcept declareConcept(ConceptDeclaration declaration, IKimStatement parent) {
		return declaration == null ? null : KimConcept.normalize(declaration, parent, true);
	}

	public KimConcept declareConcept(ConceptDeclaration declaration, IKimMacro macro, IKimStatement parent) {
		return declaration == null ? null : KimConcept.normalize(declaration, parent, true);
	}

	public KimObservable declareObservable(ObservableSemantics declaration, IKimStatement parent) {
		return declaration == null ? null : KimObservable.normalize(declaration, parent);
	}

	public Set<String> getKimKeywords() {
		return GrammarUtil.getAllKeywords(grammarAccess.getGrammar());
	}

	public void addNotifier(Notifier notifier) {
		this.notifiers.add(notifier);
	}

	private static UrnDescriptor unvalidatedUrn = new UrnDescriptor(KNOWN | ACCESSIBLE, "Demo URN");

	private UrnDescriptor validUrn(String urn) {
		return new UrnDescriptor(KNOWN | ACCESSIBLE, urn);
	}

	public Number parseNumber(org.integratedmodelling.kim.kim.Number number) {
		ICompositeNode node = NodeModelUtils.findActualNodeFor(number);
		if (number.isExponential() || number.isDecimal()) {
			return Double.parseDouble(node.getText().trim());
		}
		if (number.isLong()) {
			String s = node.getText().trim();
			int nl = s.indexOf('l');
			return Long.parseLong(s.substring(0, nl));
		}
		return Integer.parseInt(node.getText().trim());
	}

	public Object parseValue(Value value, IKimNamespace namespace) {

		if (value == null) {
			System.out.println("AHO AGGIUSTAME EH");
			return null;
		}

		if (value.getQuantity() != null) {
			return parseQuantity(value.getQuantity(), namespace);
		} else if (value.getLiteral() != null) {
			return parseLiteral(value.getLiteral(), namespace);
		} else if (value.getDate() != null) {
			return new KimDate(value.getDate());
		} else if (value.getFunction() != null) {
			return new KimServiceCall(value.getFunction(), null);
		} else if (value.getId() != null) {
			Object id = value.getId();
			if (namespace != null) {
				Object did = namespace.getSymbolTable().get(id);
				if (did != null) {
					id = did;
				}
			}
			return id;
		} else if (value.getList() != null) {
			return parseList(value.getList(), namespace);
		} else if (value.getMap() != null) {
			return parseMap(value.getMap(), namespace);
		} else if (value.getTable() != null) {
			return new KimTable(value.getTable().getTable(), namespace);
		} else if (value.getConcept() != null) {
			if (value.getConcept() instanceof ObservableSemantics) {
				ObservableSemantics observable = (ObservableSemantics) value.getConcept();
				if (observable.getUnit() == null && observable.getCurrency() == null
						&& observable.getValueOperators().isEmpty() && !observable.isGeneric()
						&& !observable.isGlobal()) {
					return declareConcept(observable.getDeclaration());
				} else {
					return declareObservable(observable);
				}
			} else if (value.getConcept() instanceof ConceptDeclaration) {
				return declareConcept((ConceptDeclaration) value.getConcept());
			}
		} else if (value.getExpr() != null) {
			return new KimExpression(value.getExpr(), null);
		} else if (value.getOp() != null) {

			Number op = parseNumber(value.getExpression());

			if (value.getOp().isGe()) {
				return new Range(op.doubleValue(), null, false, true);
			} else if (value.getOp().isGt()) {
				return new Range(op.doubleValue(), null, true, true);
			} else if (value.getOp().isLe()) {
				return new Range(null, op.doubleValue(), true, false);
			} else if (value.getOp().isLt()) {
				return new Range(null, op.doubleValue(), true, true);
			} else if (value.getOp().isEq()) {
				return op.doubleValue();
			} else if (value.getOp().isNe()) {
				// exclusive x-x range means != x
				return new Range(op.doubleValue(), op.doubleValue(), true, true);
			}
		} else if (value.getTemplatevar() != null) {
			return new TemplateValue(value.getTemplatevar());
		}

		return null;
	}

	private Object parseQuantity(Quantity quantity, IKimNamespace namespace) {
		KimQuantity ret = new KimQuantity(quantity);
		ret.setValue(parseNumber(quantity.getValue()));
		if (quantity.getUnit() != null) {
			ICompositeNode node = NodeModelUtils.getNode(quantity.getUnit());
			ret.setUnit(node.getText().trim());
		} else if (quantity.getCurrency() != null) {
			ICompositeNode node = NodeModelUtils.getNode(quantity.getCurrency());
			ret.setCurrency(node.getText().trim());
		}
		return ret;
	}

	/*
	 * The k.IM map preserves order.
	 */
	public Map<?, ?> parseMap(org.integratedmodelling.kim.kim.Map map, IKimNamespace namespace) {
		Map<Object, Object> ret = new LinkedHashMap<>();
		for (MapEntry entry : map.getEntries()) {
			Object key = parseClassifierAsValue(entry.getClassifier(), null, namespace);
			Object value = parseValue(entry.getValue(), namespace);
			ret.put(key, value);
		}
		return ret;
	}

	/**
	 * Return the object to classify against, unless we have a quantifier or an
	 * operator, in which case an actual classifier is returned. This is used when a
	 * classifier is admitted as the syntax for a map or table key.
	 * 
	 * @param statement
	 * @param parent
	 * @param namespace
	 * @return
	 */
	public Object parseClassifierAsValue(ClassifierRHS statement, IKimStatement parent, IKimNamespace namespace) {

		if (statement.getBoolean() != null) {
			return statement.getBoolean().equals("true");
		}
		if (statement.getOp() != null) {
			Number op = Kim.INSTANCE.parseNumber(statement.getExpression());

			if (statement.getOp().isGe()) {
				return new Range(op.doubleValue(), null, false, true);
			} else if (statement.getOp().isGt()) {
				return new Range(op.doubleValue(), null, true, true);
			} else if (statement.getOp().isLe()) {
				return new Range(null, op.doubleValue(), true, false);
			} else if (statement.getOp().isLt()) {
				return new Range(null, op.doubleValue(), true, true);
			} else if (statement.getOp().isEq()) {
				return op.doubleValue();
			} else if (statement.getOp().isNe()) {
				// exclusive x-x range means != x
				return new Range(op.doubleValue(), op.doubleValue(), true, true);
			}
		}
		if (statement.getConcept() != null) {
			return declareConcept(statement.getConcept());
		}
		if (statement.getString() != null) {
			return statement.getString();
		}
		if (statement.getNum() != null) {
			return parseNumber(statement.getNum());
		}
		if (statement.getInt0() != null) {
			Number from = parseNumber(statement.getInt0());
			Number to = parseNumber(statement.getInt1());
			String lt = statement.getLeftLimit();
			String rt = statement.getRightLimit();
			if (lt == null)
				lt = "inclusive";
			if (rt == null)
				rt = "exclusive";
			return new Range(from.doubleValue(), to.doubleValue(), lt.equals("exclusive"), rt.equals("exclusive"));
		}
		if (statement.getId() != null) {
			return statement.getId();
		}
		// TODO

		// if (statement == null && matchedConcept != null) {
		// conceptMatch = matchedConcept;
		// return;
		// }
		//
		// if (statement.isAnything()) {
		// catchAnything = true;
		// return;
		// } else if (statement.getNum() != null) {
		// Number n = Kim.INSTANCE.parseNumber(statement.getNum());
		// this.numberMatch = n.doubleValue();
		// } else if (statement.getBoolean() != null) {
		// this.booleanMatch = statement.getBoolean().equals("true");
		// } else if (statement.getInt0() != null) {
		//
		// Number from = Kim.INSTANCE.parseNumber(statement.getInt0());
		// Number to = Kim.INSTANCE.parseNumber(statement.getInt1());
		// String lt = statement.getLeftLimit();
		// String rt = statement.getRightLimit();
		// if (lt == null)
		// lt = "inclusive";
		// if (rt == null)
		// rt = "exclusive";
		// this.intervalMatch = new Range(from.doubleValue(), to.doubleValue(), lt
		// .equals("exclusive"), rt.equals("exclusive"));
		//
		// } else if (statement.getOp() != null) {
		//
		// Range ni = null;
		// Number op = Kim.INSTANCE.parseNumber(statement.getExpression());
		//
		// if (statement.getOp().isGe()) {
		// ni = new Range(op.doubleValue(), null, false, true);
		// } else if (statement.getOp().isGt()) {
		// ni = new Range(op.doubleValue(), null, true, true);
		// } else if (statement.getOp().isLe()) {
		// ni = new Range(null, op.doubleValue(), true, false);
		// } else if (statement.getOp().isLt()) {
		// ni = new Range(null, op.doubleValue(), true, true);
		// } else if (statement.getOp().isEq()) {
		// numberMatch = op.doubleValue();
		// } else if (statement.getOp().isNe()) {
		// this.numberMatch = op.doubleValue();
		// this.negated = true;
		// }
		//
		// if (ni != null) {
		// this.intervalMatch = ni;
		// }
		//
		// } else if (statement.getNodata() != null) {
		// this.nullMatch = true;
		// } else if (statement.getSet() != null) {
		//
		// for (Object o : Kim.INSTANCE.parseList(statement.getSet(), namespace)) {
		// if (o instanceof Number) {
		// addClassifier(createNumberMatcher((Number) o));
		// } else if (o instanceof String) {
		// addClassifier(createStringMatcher((String) o));
		// } else if (o instanceof IConcept) {
		// addClassifier(createConceptMatcher((IConcept) o));
		// } else if (o == null) {
		// addClassifier(createNullMatcher());
		// } else if (o instanceof List<?>) {
		// addClassifier(createMultipleMatcher((List<?>) o));
		// }
		// }
		//
		// } else if (statement.getToResolve() != null &&
		// statement.getToResolve().size() > 0) {
		//
		// for (ConceptDeclaration cdu : statement.getToResolve()) {
		// if (conceptMatches == null) {
		// conceptMatches = new ArrayList<>();
		// }
		// conceptMatches.add(Kim.INSTANCE.declareConcept(cdu));
		// }
		//
		// } else if (statement.getConcept() != null) {
		//
		// conceptMatch = Kim.INSTANCE.declareConcept(statement.getConcept());
		//
		// } else if (statement.getString() != null) {
		//
		// this.stringMatch = statement.getString();
		//
		// } else if (statement.getExpr() != null) {
		//
		// this.expressionMatch = statement.getExpr();
		//
		// } else if (statement.isStar()) {
		//
		// catchAll = true;
		//
		// }

		return null;
	}

	public List<?> parseList(org.integratedmodelling.kim.kim.List list, IKimNamespace namespace) {
		List<Object> ret = new ArrayList<>();
		for (Value val : list.getContents()) {
			if (!(val.getLiteral() != null && val.getLiteral().isComma())) {
				ret.add(parseValue(val, namespace));
			}
		}
		return ret;
	}

	// public Parameters<String> parseMetadata(Metadata map, IKimNamespace
	// namespace) {
	// Map<String, Object> ret = new LinkedHashMap<>();
	// for (int i = 0; i < map.getIds().size(); i++) {
	// String key = map.getIds().get(i);
	// Object value = parseLiteralObject(map.getValues().get(i), namespace);
	// ret.put(key, value);
	// }
	// return new Parameters<>(ret);
	// }

	// private Object parseLiteralObject(EObject eObject, IKimNamespace namespace) {
	// if (eObject instanceof Literal) {
	// return parseLiteral((Literal) eObject, namespace);
	// } else if (eObject instanceof org.integratedmodelling.kim.kim.List) {
	// return parseList((org.integratedmodelling.kim.kim.List) eObject, namespace);
	// } /* ZIO TORDO
	// * else if (eObject instanceof Metadata) { return parseMetadata((Metadata)
	// * eObject, namespace); }
	// */
	// throw new KlabInternalErrorException("WRONG METADATA VALUE - THIS SHOULD NOT
	// HAPPEN");
	// }

	public Object parseLiteral(Literal literal, IKimNamespace namespace) {
		if (literal.getBoolean() != null) {
			return Boolean.parseBoolean(literal.getBoolean());
		} else if (literal.getNumber() != null) {
			return parseNumber(literal.getNumber());
		} else if (literal.getId() != null) {
			Object id = literal.getId();
			if (namespace != null) {
				Object did = namespace.getSymbolTable().get(id);
				if (did != null) {
					id = did;
				}
			}
			return id;
		} else if (literal.getString() != null) {
			return literal.getString();
		} else if (literal.getFrom() != null) {
			Range range = new Range();
			range.setLowerBound(parseNumber(literal.getFrom()).doubleValue());
			if (literal.getTo() != null) {
				range.setUpperBound(parseNumber(literal.getTo()).doubleValue());
			}
			return range;
		}
		return null;
	}

	public UrnDescriptor getUrnDescriptor(String urn) {

		UrnDescriptor ret = null;
		if (!urn.contains(":") || urn.startsWith("klab:")) {
			// FIXME this should validate model URNs when admissible, ensuring they are
			// declared
			return validUrn(urn);
		}
		if (validatorCallback != null) {
			ret = validatorCallback.classifyUrn(urn);
		}
		return ret == null ? unvalidatedUrn : ret;
	}

	public ConceptDescriptor getConceptDescriptor(String conceptId) {
		SemanticType st = new SemanticType(conceptId);
		Map<String, ConceptDescriptor> map = null;
		if (st.getNamespace() == null || st.getName() == null) {
			return null;
		}
		if (Character.isUpperCase(st.getNamespace().charAt(0))) {

			String term = st.getName();
			if (term.startsWith("'") || term.startsWith("\"")) {
				term = term.substring(1, term.length() - 1);
			}
			ConceptDescriptor cd = new ConceptDescriptor();
			cd.setName(conceptId);
			cd.getFlags().add(Type.AUTHORITY_IDENTITY);
			if (validatorCallback != null) {
				Pair<String, Boolean> desc = null;
				if (term != null && !term.isEmpty() && !st.getNamespace().isEmpty()) {
					desc = validatorCallback.getIdentityInformation(st.getNamespace(), term, true);
				}
				if (desc == null || Validator.OFFLINE.equals(desc.getFirst())) {
					cd.setDocumentation("Authority identity: connect to an engine to validate");
				} else if (!desc.getSecond() || Validator.UNKNOWN_AUTHORITY.equals(desc.getFirst())) {
					cd.setDocumentation(Validator.UNKNOWN_AUTHORITY.equals(desc.getFirst())
							? "The authority is unknown to the engine or the identifier is not admitted"
							: desc.getFirst());
					cd.getFlags().add(Type.NOTHING);
				} else {
					cd.setDocumentation(desc.getFirst());
					cd.getFlags().addAll(getType("identity", null));
				}
			}
			return cd;
		} else if (st.isCorrect()) {
			map = namespaceRegister.get(st.getNamespace());
		}
		return map == null ? null : map.get(st.getName());
	}

	public void setConceptDescriptor(String conceptId, ConceptDescriptor descriptor) {
		setConceptDescriptor(conceptId, descriptor, false);
	}

	public void setConceptDescriptor(String conceptId, ConceptDescriptor descriptor, boolean createNamespace) {
		SemanticType st = new SemanticType(conceptId);
		Map<String, ConceptDescriptor> map = namespaceRegister.get(st.getNamespace());
		if (map == null) {
			if (createNamespace) {
				map = initializeNamespaceRegisters(st.getNamespace());
			} else {
				throw new UnsupportedOperationException("namespace for " + conceptId + " is not registered");
			}
		}
		map.put(st.getName(), descriptor);
	}

	public Map<String, ConceptDescriptor> initializeNamespaceRegisters(String string) {
		Map<String, ConceptDescriptor> ret = new HashMap<>();
		namespaceRegister.put(string, ret);
		return ret;
	}

	public boolean hasErrors(EObject object) {
		ICompositeNode node = NodeModelUtils.getNode(object);
		return node == null || checkNodeForErrors(node);
	}

	private boolean checkNodeForErrors(INode node) {
		if (node.getSyntaxErrorMessage() != null) {
			return true;
		}
		if (node instanceof ICompositeNode) {
			for (INode child : ((ICompositeNode) node).getChildren()) {
				if (checkNodeForErrors(child)) {
					return true;
				}
			}
		}
		return false;
	}

	public EnumSet<Type> applyOperator(EnumSet<Type> original, Type... quality) {

		if (quality.length == 1 && quality[0] == Type.CHANGE) {
			// not a quality, just ignore everything
			return getType(UnarySemanticOperator.CHANGE, original);
		}

		// remove any direct observable flags, add the one specified and quality
		EnumSet<Type> ret = EnumSet.copyOf(original);
		ret.removeAll(IKimConcept.DIRECT_OBSERVABLE_TYPES);
		ret.removeAll(IKimConcept.ALL_TRAIT_TYPES);

		for (Type t : quality) {
			ret.add(t);
			if (t == Type.DISTANCE) {
				// this does not go in by itself
				ret.add(Type.INTENSIVE_PROPERTY);
			} else if (t == Type.MAGNITUDE) {
				// this neither
				ret.add(Type.SUBJECTIVE);
			}
		}

		ret.add(Type.QUALITY);
		ret.add(Type.OBSERVABLE);
		ret.add(Type.QUANTIFIABLE);

		return ret;
	}

	public void reportLibraryError(Issue issue) {
		// TODO use callback
		System.out.println(issue.toString());
	}

	public EnumSet<Type> getType(UnarySemanticOperator operator, Set<Type> original) {

        switch(operator) {
//        case ASSESSMENT:
//            // won't happen
//            break;
        case COUNT:
            return getType("count", original);
        case DISTANCE:
            return getType("distance", original);
        case MAGNITUDE:
            return getType("magnitude", original);
        case LEVEL:
            return getType("level", original);
        case CHANGE:
            return getType("change", original);
        case MONETARY_VALUE:
            return getType("money", original);
        case OCCURRENCE:
            return getType("occurrence", original);
        case PERCENTAGE:
            return getType("percentage", original);
        case PRESENCE:
            return getType("presence", original);
        case PROBABILITY:
            return getType("probability", original);
        case PROPORTION:
            return getType("proportion", original);
        case RATIO:
            return getType("ratio", original);
        case TYPE:
            return getType("class", original);
        case UNCERTAINTY:
            return getType("uncertainty", original);
        case VALUE:
            return getType("value", original);
        case RATE:
            return getType("rate", original);
        case CHANGED:
            return getType("changed", original);
        case NOT:
        default:
            break;
        }

		return null;
	}

	public EnumSet<Type> getType(String string, Set<Type> original) {

		if (string == null) {
			return EnumSet.noneOf(Type.class);
		}

		String id = string.toLowerCase();

		switch (id) {
		case "thing":
			return EnumSet.of(Type.SUBJECT, Type.DIRECT_OBSERVABLE, Type.COUNTABLE, Type.OBSERVABLE);
		case "class":
			return EnumSet.of(Type.CLASS, Type.QUALITY, Type.OBSERVABLE);
		case "level":
			return EnumSet.of(Type.CLASS, Type.QUALITY, Type.ORDERING, Type.OBSERVABLE);
		case "quantity":
			return EnumSet.of(Type.QUANTITY, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "quality":
			return EnumSet.of(Type.QUALITY, Type.OBSERVABLE);
		case "ordering":
			return EnumSet.of(Type.ORDERING, Type.TRAIT, Type.ATTRIBUTE, Type.PREDICATE);
		case "attribute":
			return EnumSet.of(Type.ATTRIBUTE, Type.TRAIT, Type.PREDICATE);
		case "type":
			return EnumSet.of(Type.CLASS, Type.QUALITY, Type.OBSERVABLE);
		case "identity":
			return EnumSet.of(Type.IDENTITY, Type.TRAIT, Type.PREDICATE);
		case "role":
			return EnumSet.of(Type.ROLE, Type.PREDICATE);
		case "realm":
			return EnumSet.of(Type.REALM, Type.TRAIT, Type.PREDICATE);
		case "domain":
			return EnumSet.of(Type.DOMAIN, Type.PREDICATE);
		case "energy":
			return EnumSet.of(Type.ENERGY, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "entropy":
			return EnumSet.of(Type.ENTROPY, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "length":
			return EnumSet.of(Type.LENGTH, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "mass":
			return EnumSet.of(Type.MASS, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "volume":
			return EnumSet.of(Type.VOLUME, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "weight":
			return EnumSet.of(Type.WEIGHT, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "magnitude":
			return EnumSet.of(Type.MAGNITUDE, Type.QUALITY, Type.OBSERVABLE, Type.SUBJECTIVE, Type.QUANTIFIABLE);
		case "monetary_value":
			return EnumSet.of(Type.MONETARY_VALUE, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "money":
			return EnumSet.of(Type.MONEY, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "duration":
			return EnumSet.of(Type.DURATION, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "area":
			return EnumSet.of(Type.AREA, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "presence":
			return EnumSet.of(Type.PRESENCE, Type.QUALITY, Type.OBSERVABLE);
		case "proportion":
			return EnumSet.of(Type.PROPORTION, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "percentage":
			return EnumSet.of(Type.PERCENTAGE, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "uncertainty":
			return EnumSet.of(Type.UNCERTAINTY, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "rate":
			EnumSet<Type> ret = EnumSet.of(Type.RATE, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
			if (original.contains(Type.EXTENSIVE_PROPERTY) || original.contains(Type.INTENSIVE_PROPERTY)) {
				ret.add(Type.INTENSIVE_PROPERTY);
			}
			return ret;
		case "acceleration":
			return EnumSet.of(Type.ACCELERATION, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE,
					Type.QUANTIFIABLE);
		case "priority":
			return EnumSet.of(Type.PRIORITY, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "value":
			return EnumSet.of(Type.VALUE, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "ratio":
			return EnumSet.of(Type.RATIO, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "count":
			return EnumSet.of(Type.NUMEROSITY, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "electric-potential":
			return EnumSet.of(Type.ELECTRIC_POTENTIAL, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE,
					Type.QUANTIFIABLE);
		case "charge":
			return EnumSet.of(Type.CHARGE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "resistance":
			return EnumSet.of(Type.RESISTANCE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE,
					Type.QUANTIFIABLE);
		case "amount":
			return EnumSet.of(Type.AMOUNT, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "resistivity":
			return EnumSet.of(Type.RESISTIVITY, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE,
					Type.QUANTIFIABLE);
		case "occurrence":
			return EnumSet.of(Type.OCCURRENCE, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "probability":
			return EnumSet.of(Type.PROBABILITY, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "pressure":
			return EnumSet.of(Type.PRESSURE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "angle":
			return EnumSet.of(Type.ANGLE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "velocity":
			return EnumSet.of(Type.VELOCITY, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "temperature":
			return EnumSet.of(Type.TEMPERATURE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE,
					Type.QUANTIFIABLE);
		case "viscosity":
			return EnumSet.of(Type.VISCOSITY, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE,
					Type.QUANTIFIABLE);
		case "distance":
			return EnumSet.of(Type.DISTANCE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "process":
			return EnumSet.of(Type.PROCESS, Type.DIRECT_OBSERVABLE, Type.OBSERVABLE);
		case "change":
			return EnumSet.of(Type.CHANGE, Type.PROCESS, Type.DIRECT_OBSERVABLE, Type.OBSERVABLE);
		case "changed":
			return EnumSet.of(Type.CHANGED, Type.EVENT, Type.DIRECT_OBSERVABLE, Type.OBSERVABLE);
		case "agent":
			return EnumSet.of(Type.AGENT, Type.DIRECT_OBSERVABLE, Type.COUNTABLE, Type.OBSERVABLE);
		case "event":
			return EnumSet.of(Type.EVENT, Type.DIRECT_OBSERVABLE, Type.COUNTABLE, Type.OBSERVABLE);
		case "relationship":
			return EnumSet.of(Type.RELATIONSHIP, Type.UNIDIRECTIONAL, Type.DIRECT_OBSERVABLE, Type.COUNTABLE,
					Type.OBSERVABLE);
		case "bond":
			return EnumSet.of(Type.RELATIONSHIP, Type.BIDIRECTIONAL, Type.DIRECT_OBSERVABLE, Type.COUNTABLE,
					Type.OBSERVABLE);
		case "configuration":
			return EnumSet.of(Type.CONFIGURATION);
		case "extent":
			return EnumSet.of(Type.EXTENT);
		case "observability":
			return EnumSet.of(Type.OBSERVABILITY, Type.DENIABLE, Type.TRAIT, Type.PREDICATE);
		}

		throw new UnsupportedOperationException("internal error: type " + string + " not handled");
	}

	static public String getTypeDescription(EnumSet<Type> type) {

		for (Type t : IKimConcept.QUALITY_TYPES) {
			if (type.contains(t)) {
				return t.name().toLowerCase();
			}
		}
		for (Type t : IKimConcept.TRAIT_TYPES) {
			if (type.contains(t)) {
				return t.name().toLowerCase();
			}
		}
		for (Type t : IKimConcept.DECLARABLE_TYPES) {
			if (type.contains(t)) {
				return t.name().toLowerCase();
			}
		}
		return "";
	}

	static public EnumSet<Type> intersection(Set<Type> a, Set<Type> b) {
		EnumSet<Type> x = EnumSet.copyOf(a);
		x.retainAll(b);
		return x;
	}

	static public boolean isCompatible(Set<Type> a, Set<Type> b) {
		EnumSet<Type> common = intersection(a, b);
		// one and only one common declarable must have left
		return intersection(common, IKimConcept.DECLARABLE_TYPES).size() == 1;
	}

	static public boolean isDeclarable(Set<Type> type) {
		return intersection(type, IKimConcept.DECLARABLE_TYPES).size() == 1;
	}

	/**
	 * Return the observable (modelable as it includes configurations) type that
	 * this concept incarnates in a model
	 * 
	 * @param type
	 * @return
	 */
	static public Type getModelableType(Set<Type> type) {
		return intersection(type, IKimConcept.MODELABLE_TYPES).size() == 1
				? intersection(type, IKimConcept.MODELABLE_TYPES).iterator().next()
				: null;
	}

	static public boolean hasCompatibleTypes(Set<Type> a, Set<Type> b) {
		EnumSet<Type> common = intersection(a, b);
		// one and only one common declarable must have left
		return common.size() > 0;
	}

	public void setUriResolver(String scheme, UriResolver resolver) {
		this.uriResolvers.put(scheme, resolver);
	}

	public UriResolver getUriResolver(String scheme) {
		return this.uriResolvers.get(scheme);
	}

	public KimNamespace getNamespace(EObject statement) {

		KimNamespace ret = null;
		Namespace namespace = KimValidator.getNamespace(statement);

		if (namespace != null) {

			String name = Kim.getNamespaceId(namespace);
			if (namespaceRegistry.containsKey(name)) {
				return (KimNamespace) namespaceRegistry.get(name);
			}

			KimProject project = getProjectForResource(namespace.eResource());
			if (project != null) {
				// it will register itself
				ret = new KimNamespace(namespace, project);
			} else if (namespace.isWorldviewBound()) {
				// projectless and with modified URI
				String uri = namespace.eResource().getURI().toString();
				ret = (KimNamespace) orphanNamespaceRegistry.get(uri);
				if (ret == null) {
					ret = new KimNamespace(uri, null);
					orphanNamespaceRegistry.put(uri, ret);
				}
			} else {

				String uri = namespace.eResource().getURI().toString();
				String projectName = getProjectName(uri);
				KimWorkspace workspace = projectName == null ? null : getWorkspaceForProject(projectName);
				if (projectName != null && workspace == null) {
					// haven't seen this project before: will make an educated guess and
					// assume this is the user workspace, as this happens when a validator
					// automatically loads an imported project from the IDE before we come in.
					File root = getProjectRoot(uri);
					if (root != null) {
						project = (KimProject) userWorkspace.overrideProject(projectName, root);
						ret = new KimNamespace(namespace, project);
						projectWorkspaces.put(projectName, userWorkspace);
					}
				} else {
					// a lone k.IM file - be nice and make a namespace, which will certainly
					// cause well-deserved trouble.
					ret = new KimNamespace(uri, null);
					orphanNamespaceRegistry.put(uri, ret);
				}

				/*
				 * FIXME this happens when importing a project because the validator is called
				 * before the project exists.
				 */
				if (ret /* still */ == null) {
					throw new KlabInternalErrorException(
							"cannot establish the project owning the namespace " + Kim.getNamespaceId(namespace));
				}
			}
		}

		return ret;
	}

	public void eraseOrphanNamespace(String uri) {
		orphanNamespaceRegistry.remove(uri);
	}

	/**
	 * @return all workspaces
	 */
	public Collection<KimWorkspace> getWorkspaces() {
		return workspaces.values();
	}

	/**
	 * Return a project by ID, looking up in all workspaces or (optionally) in the
	 * workspaces under a passed file root.
	 * 
	 * @param id            the project ID
	 * @param workspaceRoot the file root of the workspace. If the project is
	 *                      present in more than one workspace, the version in the
	 *                      passed root is returned. Can be null, if so the first
	 *                      instance of the project found is returned.
	 * @return the project or null
	 */
	public IKimProject getProject(String id) {

		KimWorkspace workspace = projectWorkspaces.get(id);
		return workspace == null ? null : workspace.getProject(id);
		// for (KimWorkspace workspace : getWorkspaces()) {
		// for (IKimProject project : workspace.getProjects()) {
		// if (project.getName().equals(id)) {
		// if (workspaceRoot == null) {
		// return project;
		// } else if (workspace.getRoot().equals(workspaceRoot) || ret == null) {
		// ret = project;
		// }
		// }
		// }
		// }
		// return ret;
	}

	/**
	 * Get the project name from the string form of any Xtext resource URI.
	 * 
	 * @param resourceURI
	 * @return
	 */
	public String getProjectName(String resourceURI) {

		String ret = null;
		try {
			URL url = new URL(resourceURI);
			String path = url.getPath();
			Properties properties = null;
			URL purl = null;
			while ((path = chopLastPathElement(path)) != null) {
				purl = new URL(url.getProtocol(), url.getAuthority(), url.getPort(),
						path + "/META-INF/klab.properties");
				try (InputStream is = purl.openStream()) {
					properties = new Properties();
					properties.load(is);
					break;
				} catch (IOException exception) {
					continue;
				}
			}
			if (properties != null) {
				ret = path.substring(path.lastIndexOf('/') + 1);
			}

		} catch (Exception e) {
			// just return null;
		}

		return ret;
	}

	/**
	 * Get the project name from the string form of any Xtext resource URI.
	 * 
	 * @param resourceURI
	 * @return
	 */
	public File getProjectRoot(String resourceURI) {

		File ret = null;
		try {
			URL url = new URL(resourceURI);
			String path = url.getPath();
			Properties properties = null;
			URL purl = null;
			while ((path = chopLastPathElement(path)) != null) {
				purl = new URL(url.getProtocol(), url.getAuthority(), url.getPort(),
						path + "/META-INF/klab.properties");
				try (InputStream is = purl.openStream()) {
					properties = new Properties();
					properties.load(is);
					break;
				} catch (IOException exception) {
					continue;
				}
			}
			if (properties != null) {
				URL furl = FileLocator.toFileURL(purl);
				if (furl != null) {
					ret = new File(furl.getFile()).getParentFile().getParentFile();
				}
			}

		} catch (Exception e) {
			// just return null;
		}

		return ret;
	}

	private String chopLastPathElement(String path) {
		int idx = path.lastIndexOf('/');
		if (idx <= 0) {
			return null;
		}
		return path.substring(0, idx);
	}

	public void removeNamespace(Namespace namespace) {

		String name = Kim.getNamespaceId(namespace);
		KimProject project = null;
		project = getProjectForResource(namespace.eResource());
		if (project != null) {
			project.removeNamespace(name);
		}
		namespaceRegistry.remove(name);
		orphanNamespaceRegistry.remove(name);
	}

	public void removeNamespaceConcepts(Namespace namespace) {
		String name = Kim.getNamespaceId(namespace);
		namespaceRegister.remove(name);
	}

	public boolean initialBuildDone() {
		return initialBuildDone;
	}

	public void setInitialBuildDone(boolean b) {
		initialBuildDone = b;
	}

	/**
	 * Validate a function call by returning a list of the correspondent
	 * notifications. If the list is empty, the call is valid. If null is returned,
	 * the function is unknown to the runtime.
	 * 
	 * @param call
	 * @param expectedReturnType
	 * @return a list of notifications, possibly empty, or null if the function is
	 *         unknown.
	 */
	public List<KimNotification> validateFunctionCall(IServiceCall call, Set<IArtifact.Type> expectedReturnType) {
		List<KimNotification> ret = new ArrayList<>();
		if (validatorCallback != null) {
			List<Pair<String, Level>> results = validatorCallback.validateFunction(call, expectedReturnType);
			if (results != null) {
				for (Pair<String, Level> result : results) {
					ret.add(new KimNotification(result.getFirst(), result.getSecond()));
				}
			}
		}
		return ret;
	}

	/**
	 * Validate a function call by returning a list of the correspondent
	 * notifications. If the list is empty, the call is valid. If null is returned,
	 * the function is unknown to the runtime.
	 * 
	 * @param call
	 * @param target
	 * @return a list of notifications, possibly empty, or null if the function is
	 *         unknown.
	 */
	public List<KimNotification> validateAnnotation(IKimAnnotation call, IKimStatement target) {
		List<KimNotification> ret = new ArrayList<>();
		if (validatorCallback != null) {
			List<Pair<String, Level>> results = validatorCallback.validateAnnotation(call, target);
			if (results != null) {
				for (Pair<String, Level> result : results) {
					ret.add(new KimNotification(result.getFirst(), result.getSecond()));
				}
			}
		}
		return ret;
	}

	/**
	 * True if the function name is known to the runtime
	 * 
	 * @param name
	 * @return true if known
	 */
	public boolean isFunctionKnown(String name) {
		return validatorCallback != null && validatorCallback.isFunctionKnown(name);
	}

	/**
	 * True if the function name is known to the runtime
	 * 
	 * @param name
	 * @return true if known
	 */
	public boolean isAnnotationKnown(String name) {
		return validatorCallback != null && validatorCallback.isAnnotationKnown(name);
	}

	/**
	 * Check a core type ID against a stated type and return the (possibly fixed)
	 * type if OK, 0 if type is incompatible.
	 * 
	 * @param string
	 * @param type
	 * @return the final type
	 */
	public EnumSet<Type> checkCoreConcept(String string, EnumSet<Type> type) {

		EnumSet<Type> ret = EnumSet.noneOf(Type.class);

		if (coreConcepts.containsKey(string)) {
			ret = coreConcepts.get(string);
		} else {
			/**
			 * use whatever type the callback returned. Without callback, we just take the
			 * first stated type as the truth.
			 */
			if (validatorCallback == null) {
				ret = type;
			} else {
				ret = validatorCallback.classifyCoreType(string, type);
			}
		}

		/*
		 * if no match between the "truth" and the requested type, return 0 which will
		 * cause an error.
		 */
		EnumSet<Type> copy = EnumSet.copyOf(ret);
		copy.retainAll(IKimConcept.DECLARABLE_TYPES);
		if (copy.size() != 1) {
			return EnumSet.noneOf(Type.class);
		}

		/*
		 * if OK and not seen before, record it so that we don't reuse it in a different
		 * way.
		 */
		if (coreConcepts.get(string) == null) {
			coreConcepts.put(string, EnumSet.copyOf(ret));
		}
		/*
		 * TODO return the type set through the callback
		 */
		return ret;
	}

	public void declareCoreConceptPeer(String worldviewConcept, String coreConcept) {
		if (validatorCallback != null) {
			validatorCallback.createWorldviewPeerConcept(coreConcept, worldviewConcept);
		}
	}

	public Validator getValidator() {
		return validatorCallback;
	}

	public void setValidator(Validator callback) {
		validatorCallback = callback;
	}

	public List<Notifier> getNotifiers() {
		return this.notifiers;
	}

	public KimObservable declareModelReference(ModelBodyStatement statement, String string) {

		KimNamespace namespace = getNamespace(statement);
		IKimModel model = null;
		if (string.startsWith(namespace.getName())) {
			string = Path.getLast(string, '.');
		}
		if (!string.contains(".")) {
			for (IKimScope object : namespace.getChildren()) {
				if (object instanceof IKimModel && ((IKimModel) object).getName().equals(string)) {
					model = (IKimModel) object;
				}
			}
		}
		if (model == null && namespace.getSymbolTable().containsKey(string)
				&& namespace.getSymbolTable().get(string) instanceof IKimModel) {
			model = (IKimModel) namespace.getSymbolTable().get(string);
		}
		if (model == null && StringUtil.countMatches(string, ":") >= 3) {
			// URN - TODO support it: add a new KimModel that only observers the URN.
		}
		return model == null || model.getObservables().size() == 0 ? null
				: (KimObservable) model.getObservables().get(0);
	}

	public KimObservable createNonSemanticObservable(String type, String name) {
		return new KimObservable(name, type);
	}

	public Type getFundamentalType(Set<Type> type) {

		EnumSet<Type> t = EnumSet.copyOf(type);
		t.retainAll(IKimConcept.DECLARABLE_TYPES);
		if (t.size() == 1) {
			return t.iterator().next();
		}
		t = EnumSet.copyOf(type);
		t.retainAll(IKimConcept.TRAIT_TYPES);
		if (t.size() == 1) {
			return t.iterator().next();
		}
		if (type.contains(IKimConcept.Type.ROLE)) {
			return IKimConcept.Type.ROLE;
		}
		return Type.NOTHING;
	}

	public void registerNamespace(KimNamespace kimNamespace) {
		this.namespaceRegistry.put(kimNamespace.getName(), kimNamespace);
	}

	public IKimNamespace getNamespace(String id) {
		return this.namespaceRegistry.get(id);
	}

	/**
	 * Get the project located in the passed root path. It must be pre-registered in
	 * a workspace.
	 * 
	 * @param root
	 * @return
	 */
	public IKimProject getProjectIn(File root) {
		KimWorkspace workspace = projectWorkspaces.get(MiscUtilities.getFileName(root));
		if (workspace != null) {
			return workspace.getProject(MiscUtilities.getFileName(root));
		}
		return null;
	}

	/**
	 * Simple check for the existence of k.LAB metadata to assess if a file is the
	 * root of a k.LAB project.
	 * 
	 * @param file
	 * @return
	 */
	public boolean isKimProject(File file) {
		File props = new File(file + File.separator + "META-INF" + File.separator + "klab.properties");
		return props.exists() && props.isFile();
	}

	/**
	 * Commodity for better semantics when dealing directly with typesets \
	 * 
	 * @param semantics
	 * @param type
	 * @return
	 */
	public boolean is(Set<IKimConcept.Type> semantics, IKimConcept.Type type) {
		return semantics.contains(type);
	}

	/**
	 * Quickly check if a typeset specifies a numeric quality.
	 * 
	 * @param semantics
	 * @param type
	 * @return
	 */
	public boolean isNumeric(Set<IKimConcept.Type> semantics) {
		EnumSet<IKimConcept.Type> set = EnumSet.copyOf(semantics);
		set.retainAll(IKimConcept.CONTINUOUS_QUALITY_TYPES);
		return !set.isEmpty();
	}

	/**
	 * Return the system name for the namespace. This will be the stated name for
	 * "regular" namespaces, adding "|" and a normalized, stable transformation of
	 * the resource URI if it's an anonymous/sidecar file/script/test. The
	 * normalization copies all path segments backwards until a project name is
	 * encountered or the path is finished, taking care of different URI prefixes
	 * between OSGI and the regular filesystem and removing any .kim extension.
	 * 
	 * TODO will need testing on MacOS to ensure proper behavior.
	 * 
	 * @param namespace
	 * @return the namespace ID
	 */
	public static String getNamespaceId(Namespace namespace) {
		String ret = namespace.getName();
		if (namespace.isWorldviewBound()) {
			String uri = "";
			String[] path = namespace.eResource().getURI().path().split("/");
			for (int i = path.length - 1; i >= 0; i--) {
				uri = path[i] + (uri.isEmpty() ? "" : "/") + uri;
				if (INSTANCE.getProject(path[i]) != null) {
					break;
				}
			}
			if (uri.endsWith(".kim")) {
				// these wreak havoc in finding model objects
				uri = uri.substring(0, uri.length() - 4);
			}
			ret += "|" + uri;
		}
		return ret;
	}

	/**
	 * 
	 * @return
	 */
	public Collection<IKimProject> getProjects() {
		List<IKimProject> ret = new ArrayList<>();
		for (KimWorkspace workspace : workspaces.values()) {
			ret.addAll(workspace.getProjects());
		}
		return ret;
	}

	/**
	 * Return a statement by name.
	 * 
	 * @param text
	 * @return
	 */
	public IKimStatement getStatement(String text) {
		String ns = null;
		String ob = null;
		if (text.contains(":")) {
			String[] nn = text.split(":");
			if (nn.length == 2) {
				ns = nn[0];
				ob = nn[1];
			}
		} else if (text.contains(".")) {
			ns = Path.getLeading(text, '.');
			ob = Path.getLast(text, '.');
		}

		if (ns != null && ob != null) {
			IKimNamespace namespace = getNamespace(ns);
			if (namespace != null) {
				return ((KimNamespace) namespace).getStatement(ob);
			}
		}
		return null;
	}

	/**
	 * Use the passed function call and prototype to create options for the passed
	 * command.
	 * 
	 * @param call
	 * @param prototype
	 * @param command   command to prepend to the options.
	 * @return the command line
	 */
	public String createCommandLine(IParameters<String> parameters, IPrototype prototype, String command) {
		return createCommandLine(parameters, prototype, command, null);
	}

	/**
	 * Use the passed function call and prototype to create options for the passed
	 * command. Arguments appear in the same order as in the KDL.
	 * 
	 * @param call
	 * @param prototype
	 * @param command        command to prepend to the options.
	 * @param callTranslator function to translate any service call argument (nested
	 *                       function) into a command argument. If null, any such
	 *                       arguments are ignored.
	 * @return the command line
	 */
	public String createCommandLine(IParameters<String> parameters, IPrototype prototype, String command,
			Function<IServiceCall, String> callTranslator) {
		String ret = "";
		for (Argument argument : prototype.listArguments()) {
			if (argument.getShortName() != null) {
				Object value = parameters.get(argument.getName());
				if (value != null) {
					if (value instanceof IServiceCall && callTranslator != null) {
						ret += (ret.isEmpty() ? "" : " ") + "-" + argument.getShortName() + " "
								+ callTranslator.apply((IServiceCall) value);
					} else if (argument.getType() == IArtifact.Type.BOOLEAN) {
						if (((Boolean) value)) {
							ret += (ret.isEmpty() ? "" : " ") + "-" + argument.getShortName();
						}
					} else {
						ret += (ret.isEmpty() ? "" : " ") + "-" + argument.getShortName() + " " + value.toString();
					}
				} else if (argument.getType() == IArtifact.Type.BOOLEAN && argument.getDefaultValue() != null) {
					// for now just add the default when it's a boolean argument and the default is
					// true
					if (Boolean.parseBoolean(argument.getDefaultValue().toString())) {
						ret += (ret.isEmpty() ? "" : " ") + "-" + argument.getShortName();
					}
				}
			}
		}
		return command == null ? ret : (command + " " + ret);

	}

	public void setCurrentLoader(KimLoader kimLoader) {
		this.currentLoader = kimLoader;
	}

	/**
	 * The loader currently loading a resource. ONLY usable during validation to
	 * access the dependency structure.
	 * 
	 * @return
	 */
	public KimLoader getCurrentLoader() {
		return this.currentLoader;
	}

	/**
	 * Called when there is a pre-defined collection of project files that may be
	 * located anywhere and would override projects in the worldview, to ensure that
	 * all projects are attributed to the proper workspace when workspaces are
	 * needed in validation of imports. This is the situation of a modeler using a
	 * IDE.
	 * 
	 * @param projectFiles
	 * @param worldviewFiles
	 */
	public void prebuildWorkspaces(Collection<File> projectFiles, Collection<File> worldviewFiles) {

		this.projectFiles = projectFiles;
		this.worldviewFiles = worldviewFiles;
		buildStandardWorkspaces();
	}

	private void buildStandardWorkspaces() {

		Map<String, File> wfiles = new HashMap<>();
		Map<String, File> ufiles = new HashMap<>();

		for (File file : this.worldviewFiles) {
			String pname = MiscUtilities.getFileName(file);
			wfiles.put(pname, file);
			projectWorkspaces.put(pname, worldview);
		}
		for (File file : this.projectFiles) {
			String pname = MiscUtilities.getFileName(file);
			// override worldview projects if they are in the user's workspace
			if (wfiles.containsKey(pname)) {
				wfiles.put(pname, file);
			} else {
				ufiles.put(pname, file);
				projectWorkspaces.put(pname, userWorkspace);
			}
		}

		for (File file : wfiles.values()) {
			worldview.addProjectPath(file);
		}
		for (File file : ufiles.values()) {
			userWorkspace.addProjectPath(file);
		}

		worldview.readProjects();
		userWorkspace.readProjects();
	}

	/**
	 * External managers such as an IDE must notify project-level actions to ensure
	 * proper handling.
	 * 
	 * @param projectDirectory
	 * @param operation
	 */
	public void notifyProjectOperation(File projectDirectory, CRUDOperation operation) {
		// TODO rearrange workspaces and reload as necessary after add/delete/close etc
		// TODO ensure that project ID -> workspace catalog is up to date
		switch (operation) {
		case COPY:
			break;
		case CREATE:
			break;
		case DELETE:
			break;
		case MOVE:
			break;
		case UPDATE:
			break;
		default:
			break;

		}
	}

	public KimWorkspace getWorkspaceForResource(Resource resource) {
		String projectName = getProjectName(resource.getURI().toString());
		return projectName == null ? null : getWorkspaceForProject(projectName);
	}

	public KimProject getProjectForResource(Resource resource) {
		String projectName = getProjectName(resource.getURI().toString());
		KimWorkspace workspace = projectName == null ? null : getWorkspaceForProject(projectName);
		return workspace == null ? null : workspace.getProject(projectName);
	}

	public KimWorkspace getWorkspaceForProject(String projectName) {
		return projectWorkspaces.get(projectName);
	}

	/**
	 * Called by API-generated workspace constructors after reading in all projects.
	 * Uses the name to attribute worldview and workspace fields.
	 * 
	 * @param kimWorkspace
	 */
	public void registerWorkspace(KimWorkspace kimWorkspace) {
		this.workspaces.put(kimWorkspace.getName(), kimWorkspace);
		for (IKimProject project : kimWorkspace.getProjects()) {
			this.projectWorkspaces.put(project.getName(), kimWorkspace);
		}
		if ("worldview".equals(kimWorkspace.getName())) {
			this.worldview = kimWorkspace;
		} else if ("workspace".equals(kimWorkspace.getName())) {
			this.userWorkspace = kimWorkspace;
		}
	}

	/**
	 * Called by k.LAB workspaces when they create a new project
	 * 
	 * @param projectName
	 * @param kimWorkspace
	 */
	public void registerProject(String projectName, IKimWorkspace kimWorkspace) {
		this.projectWorkspaces.put(projectName, (KimWorkspace) kimWorkspace);
	}

	public void unregisterProject(IKimProject project) {
		this.projectFiles.remove(project.getRoot());
		this.projectWorkspaces.remove(project.getName());
	}

	public void updateErrors(NamespaceCompilationResult report) {

		CompileInfo info = new CompileInfo();
		for (CompileNotificationReference notification : report.getNotifications()) {
			recordCompileNotification(notification, info);
		}
		compileInfo.put(report.getNamespaceId(), info);
	}

	private void recordCompileNotification(CompileNotificationReference inot, CompileInfo ci) {

		if (inot.getLevel() == Level.SEVERE.intValue()) {
			ci.getErrors().add(inot);
		} else if (inot.getLevel() == Level.WARNING.intValue()) {
			ci.getWarnings().add(inot);
		} else if (inot.getLevel() == Level.INFO.intValue()) {
			ci.getInfo().add(inot);
		}
	}

	public List<CompileNotificationReference> getWarnings(String namespaceId) {
		return compileInfo.containsKey(namespaceId) ? compileInfo.get(namespaceId).getWarnings() : new ArrayList<>();
	}

	public List<CompileNotificationReference> getErrors(String namespaceId) {
		return compileInfo.containsKey(namespaceId) ? compileInfo.get(namespaceId).getErrors() : new ArrayList<>();
	}

	public List<CompileNotificationReference> getNotificationsFor(String namespaceId, String statementUri) {

		List<CompileNotificationReference> ret = new ArrayList<>();
		CompileInfo info = compileInfo.get(namespaceId);
		if (info != null) {

			for (CompileNotificationReference notification : info.getErrors()) {
				if (notification.getStatementUrn() != null && statementUri.endsWith(notification.getStatementUrn())) {
					ret.add(notification);
				}
			}
			for (CompileNotificationReference notification : info.getWarnings()) {
				if (notification.getStatementUrn() != null && statementUri.endsWith(notification.getStatementUrn())) {
					ret.add(notification);
				}
			}
			for (CompileNotificationReference notification : info.getInfo()) {
				if (notification.getStatementUrn() != null && statementUri.endsWith(notification.getStatementUrn())) {
					ret.add(notification);
				}
			}
		}
		return ret;
	}

	public CompileInfo getCompileInfo(String name) {
		return compileInfo.get(name);
	}

	/**
	 * Take a concept that was made abstract by virtue of its base observable and
	 * evaluate if its traits or components should make it concrete. Return whether
	 * it stays abstract or not.
	 * <p>
	 * Rules so far:
	 * <ul>
	 * <li>If the concept is a semantic transformation (e.g. value of) an abstract
	 * concept, it becomes concrete;</li>
	 * <li>If the concept adopts a concrete identity or realm, and all identities
	 * and realms it adopts are concrete, it becomes concrete (FOR LATER: it should
	 * only do so if the trait is a required one, which requires analysis of the
	 * lineage or reasoning).</li>
	 * <li>If it has subsetting components and all of its subsetting components are
	 * concrete, it becomes concrete.</li>
	 * </ul>
	 * 
	 * @param concept
	 * @return true if the concept is truly abstract
	 */
	public boolean computeAbstractStatus(IKimConcept ret) {

		boolean isAbstract = ret.is(Type.ABSTRACT);

		if (ret.is(Type.TRAIT)) {
			return isAbstract;
		}

		if (ret.getSemanticModifier() != null) {
			isAbstract = false;
		}

		if (isAbstract) {

			boolean traitsOk = true;
			boolean haveDefiningTraits = false;
			for (IKimConcept trait : ret.getTraits()) {
				if ((trait.is(Type.IDENTITY) || trait.is(Type.REALM))) {
					haveDefiningTraits = true;
					if (trait.is(Type.ABSTRACT)) {
						traitsOk = false;
						break;
					}
				}
			}

			if (haveDefiningTraits && traitsOk) {
				isAbstract = false;
			}
		}

		if (/* still */ isAbstract) {

			boolean componentsOk = true;
			boolean haveDefiningComponents = false;
			for (IKimConcept subsetter : ((KimConcept) ret).getSemanticSubsetters()) {
				haveDefiningComponents = true;
				if (subsetter.is(Type.ABSTRACT)) {
					componentsOk = false;
					break;
				}
			}

			if (haveDefiningComponents && componentsOk) {
				isAbstract = false;
			}
		}

		return isAbstract;
	}

	public void closeProject(String projectId) {
		KimWorkspace workspace = projectWorkspaces.get(projectId);
		if (workspace != null) {
			IKimProject project = workspace.getProject(projectId);
			workspace.deleteProject(project);
		}
	}

	public boolean isKimFile(File file) {
		return file.toString().endsWith(".kim") || file.toString().endsWith(".tql");
	}

	public String getUrnValue(Urn urn) {
		if (urn.getName() != null) {
			return urn.getName();
		}
		if (urn.getStrings() != null) {
			StringBuffer sbuf = new StringBuffer(512);
			for (String s : urn.getStrings()) {
				sbuf.append(s);
			}
			return sbuf.toString();
		}
		return null;
	}

	/**
	 * Encode the value so that it can be understood in k.IM code.
	 * 
	 * @param value
	 * @return
	 */
	public String encodeValue(Object value) {
		if (value instanceof String) {
			return "'" + ((String) value).replace("'", "\\'") + "'";
		} else if (value instanceof IConcept) {
			return ((IConcept) value).getDefinition();
		} else if (value instanceof Range) {
			return ((Range) value).getKimCode();
		}
		return value == null ? "unknown" : value.toString();
	}

	public IKimConceptStatement getConceptStatement(IKimConcept observable) {
		IKimNamespace ns = getNamespace(observable.getNamespace());
		if (ns != null) {
			String name = observable.getName().contains(":") ? Path.getLast(observable.getName(), ':')
					: observable.getName();
			return ns.getStatement(name, IKimConceptStatement.class);
		}
		return null;
	}

}

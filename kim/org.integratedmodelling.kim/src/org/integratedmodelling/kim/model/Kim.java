/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.kim.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
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
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.kim.ClassifierRHS;
import org.integratedmodelling.kim.kim.ConceptDeclaration;
import org.integratedmodelling.kim.kim.Literal;
import org.integratedmodelling.kim.kim.MapEntry;
import org.integratedmodelling.kim.kim.Metadata;
import org.integratedmodelling.kim.kim.ModelBodyStatement;
import org.integratedmodelling.kim.kim.Namespace;
import org.integratedmodelling.kim.kim.ObservableSemantics;
import org.integratedmodelling.kim.kim.Value;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.kim.validation.KimValidator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.SemanticType;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.Range;

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

	/**
	 * This ID is used for a common project where all namespaces read from isolated
	 * files will be added.
	 */
	public final static String COMMON_PROJECT_ID = "klab.internal.common.project";

	private Validator validatorCallback = null;
	
	@Inject
	private IGrammarAccess grammarAccess;

	/**
	 * Call before keyword list can be obtained
	 * @param injector
	 */
	public void setup(Injector injector) {
		injector.injectMembers(this);
	}
	
//	/**
//	 * Known URN descriptors. Must be filled in from the outside.
//	 */
//	private Map<String, UrnDescriptor> urnDescriptors = new HashMap<>();

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

	/**
	 * Workspace for libraries. May be null. Loaded automatically at startup if
	 * present, with code generation only in the engine.
	 */
	private KimWorkspace libWorkspace;

	private List<Notifier> notifiers = new ArrayList<>();

	private boolean libraryInitialized;
	private boolean workspaceInited;
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
	Map<String, IKimProject> projectRegistry = new HashMap<>();
	Map<File, IKimProject> projectRootRegistry = new HashMap<>();

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

	public static class ConceptDescriptor implements IConceptDescriptor {

		private String name;
		private EnumSet<Type> flags = EnumSet.noneOf(Type.class);
		private String documentation;
		private IKimConceptStatement macro;

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
			this.flags = type;
			this.documentation = string;
		}

		public ConceptDescriptor(String id, EnumSet<Type> type, IKimConceptStatement macro, String string) {
			this.name = id;
			this.flags = type;
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
		SPACE_DOMAIN, TIME_DOMAIN, TABULAR_DATA, SPATIAL_DATA, OBJECT_DATA, STATE_CONTEXTUALIZER, PROCESS_CONTEXTUALIZER, SUBJECT_CONTEXTUALIZER, EVENT_CONTEXTUALIZER, RELATIONSHIP_CONTEXTUALIZER, SUBJECT_INSTANTIATOR, EVENT_INSTANTIATOR, RELATIONSHIP_INSTANTIATOR, LITERAL_ATOMIC, LITERAL_LIST,
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

		public UrnDescriptor(int flags) {
			this.flags = flags;
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
		 * @param target
		 *            the statement that the annotation describes
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
		 * Return a descriptor for the passed URN. Never return null - if a URN is
		 * unknown, just say so.
		 * 
		 * @param urn
		 * @return a URN descriptor
		 */
		UrnDescriptor classifyUrn(String urn);

		/**
		 * If the core type is known, return the type of declarable this core type
		 * represents. If it is unknown or it's not declarable, return 0.
		 * 
		 * @param string
		 * @param statedType
		 * @return the type
		 */
		EnumSet<Type> classifyCoreType(String string, EnumSet<Type> statedType);

	}

	public KimConcept declareConcept(ConceptDeclaration declaration) {
		return declaration == null ? null : KimConcept.normalize(declaration, null);
	}

	public KimConcept declareConcept(ConceptDeclaration declaration, IKimMacro macro) {
		return declaration == null ? null : KimConcept.normalize(declaration, null);
	}

	public KimObservable declareObservable(ObservableSemantics declaration) {
		return declaration == null ? null : KimObservable.normalize(declaration, null);
	}

	public KimConcept declareConcept(ConceptDeclaration declaration, IKimStatement parent) {
		return declaration == null ? null : KimConcept.normalize(declaration, parent);
	}

	public KimConcept declareConcept(ConceptDeclaration declaration, IKimMacro macro, IKimStatement parent) {
		return declaration == null ? null : KimConcept.normalize(declaration, parent);
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

	public Number parseNumber(org.integratedmodelling.kim.kim.Number number) {
		ICompositeNode node = NodeModelUtils.findActualNodeFor(number);
		if (number.isExponential() || number.isDecimal()) {
			return Double.parseDouble(node.getText().trim());
		}
		return Integer.parseInt(node.getText().trim());
	}

	public Object parseValue(Value value, IKimNamespace namespace) {

		if (value.getLiteral() != null) {
			return parseLiteral(value.getLiteral(), namespace);
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
			return declareConcept(value.getConcept());
		} else if (value.getExpr() != null) {
			return new KimExpression(value.getExpr(), null);
		}

		return null;
	}


	/*
	 * The k.IM map preserves order.
	 */
	private Map<?, ?> parseMap(org.integratedmodelling.kim.kim.Map map, IKimNamespace namespace) {
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
				// shouldn't happen, shouldn't throw
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
	
	public Parameters<String> parseMetadata(Metadata map, IKimNamespace namespace) {
		Map<String, Object> ret = new LinkedHashMap<>();
		for (int i = 0; i < map.getIds().size(); i++) {
			String key = map.getIds().get(i);
			Object value = parseLiteralObject(map.getValues().get(i), namespace);
			ret.put(key, value);
		}
		return new Parameters<>(ret);
	}

	private Object parseLiteralObject(EObject eObject, IKimNamespace namespace) {
		if (eObject instanceof Literal) {
			return parseLiteral((Literal) eObject, namespace);
		} else if (eObject instanceof org.integratedmodelling.kim.kim.List) {
			return parseList((org.integratedmodelling.kim.kim.List) eObject, namespace);
		} else if (eObject instanceof Metadata) {
			return parseMetadata((Metadata) eObject, namespace);
		}
		throw new KlabInternalErrorException("WRONG METADATA VALUE - THIS SHOULD NOT HAPPEN");
	}

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

		UrnDescriptor ret = null;;
		if (validatorCallback != null) {
			ret = validatorCallback.classifyUrn(urn);
		}
		return ret == null ? unvalidatedUrn : ret;
	}

	public ConceptDescriptor getConceptDescriptor(String conceptId) {
		SemanticType st = new SemanticType(conceptId);
		Map<String, ConceptDescriptor> map = null;
		if (st.isCorrect()) {
			map = namespaceRegister.get(st.getNamespace());
		}
		return map == null ? null : map.get(st.getName());
	}

//	public void setUrnDescriptor(String urn, UrnDescriptor descriptor) {
//		urnDescriptors.put(urn, descriptor);
//	}

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

	public boolean isLibraryInitialized() {
		return libraryInitialized;
	}

	public void setLibraryInitialized(boolean b) {
		libraryInitialized = b;
	}

	/**
	 * Get the workspace containing the worldview and any library.
	 *
	 * @param libname
	 *            the libname
	 * @param overridingProjects
	 *            the overriding projects
	 * @return the workspace
	 */
	public KimWorkspace getLibrary(String libname, File... overridingProjects) {

		if (!workspaceInited) {
			// TODO substitute with callback
			libWorkspace = new KimWorkspace(
					new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator + libname));
			workspaceInited = true;
		}
		return libWorkspace;
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

	public EnumSet<Type> makeQuality(EnumSet<Type> original, Type... quality) {
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

	public EnumSet<Type> getType(String string) {

		String id = string.toLowerCase();

		switch (id) {
		case "thing":
			return EnumSet.of(Type.SUBJECT, Type.DIRECT_OBSERVABLE, Type.COUNTABLE, Type.OBSERVABLE);
		case "class":
			return EnumSet.of(Type.CLASS, Type.QUALITY, Type.OBSERVABLE);
		case "quantity":
			return EnumSet.of(Type.QUANTITY, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "quality":
			return EnumSet.of(Type.QUALITY, Type.OBSERVABLE);
		case "ordering":
			return EnumSet.of(Type.ORDERING, Type.TRAIT, Type.ATTRIBUTE);
		case "attribute":
			return EnumSet.of(Type.ATTRIBUTE, Type.TRAIT);
		case "type":
			return EnumSet.of(Type.CLASS, Type.QUALITY, Type.OBSERVABLE);
		case "identity":
			return EnumSet.of(Type.IDENTITY, Type.TRAIT);
		case "role":
			return EnumSet.of(Type.ROLE);
		case "realm":
			return EnumSet.of(Type.REALM, Type.TRAIT);
		case "domain":
			return EnumSet.of(Type.DOMAIN);
		case "energy":
			return EnumSet.of(Type.ENERGY, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
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
		case "money":
			return EnumSet.of(Type.MONEY, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "duration":
			return EnumSet.of(Type.DURATION, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "area":
			return EnumSet.of(Type.AREA, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "presence":
			return EnumSet.of(Type.PRESENCE, Type.QUALITY, Type.OBSERVABLE);
		case "proportion":
			return EnumSet.of(Type.PROPORTION, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "percentage":
			return EnumSet.of(Type.PERCENTAGE, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "uncertainty":
			return EnumSet.of(Type.UNCERTAINTY, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "acceleration":
			return EnumSet.of(Type.ACCELERATION, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "priority":
			return EnumSet.of(Type.PRIORITY, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "value":
			return EnumSet.of(Type.VALUE, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "ratio":
			return EnumSet.of(Type.RATIO, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "count":
			return EnumSet.of(Type.NUMEROSITY, Type.QUALITY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "electric-potential":
			return EnumSet.of(Type.ELECTRIC_POTENTIAL, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "charge":
			return EnumSet.of(Type.CHARGE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "resistance":
			return EnumSet.of(Type.RESISTANCE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "amount":
			return EnumSet.of(Type.AMOUNT, Type.QUALITY, Type.EXTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "resistivity":
			return EnumSet.of(Type.RESISTIVITY, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
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
			return EnumSet.of(Type.TEMPERATURE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "viscosity":
			return EnumSet.of(Type.VISCOSITY, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "distance":
			return EnumSet.of(Type.DISTANCE, Type.QUALITY, Type.INTENSIVE_PROPERTY, Type.OBSERVABLE, Type.QUANTIFIABLE);
		case "process":
			return EnumSet.of(Type.PROCESS, Type.DIRECT_OBSERVABLE, Type.OBSERVABLE);
		case "assessment":
			return EnumSet.of(Type.ASSESSMENT, Type.PROCESS, Type.DIRECT_OBSERVABLE, Type.OBSERVABLE);
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
			return EnumSet.of(Type.OBSERVABILITY, Type.DENIABLE, Type.TRAIT);
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

	static public boolean isCompatible(EnumSet<Type> a, EnumSet<Type> b) {
		EnumSet<Type> common = intersection(a, b);
		// one and only one common declarable must have left
		return intersection(common, IKimConcept.DECLARABLE_TYPES).size() == 1;
	}

	public void setUriResolver(String scheme, UriResolver resolver) {
		this.uriResolvers.put(scheme, resolver);
	}

	public UriResolver getUriResolver(String scheme) {
		return this.uriResolvers.get(scheme);
	}

	public KimNamespace getNamespace(EObject statement, boolean createIfAbsent) {

		KimNamespace ret = null;
		Namespace namespace = KimValidator.getNamespace(statement);

		if (namespace != null) {

			String name = Kim.getNamespaceId(namespace);
			if (namespaceRegistry.containsKey(name)) {
				return (KimNamespace) namespaceRegistry.get(name);
			}

			KimProject project = null;
			KimWorkspace workspace = KimWorkspace.getWorkspaceForResource(namespace.eResource());
			if (workspace != null) {
				project = workspace.getProjectForResource(namespace.eResource());
			}
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
			}
		}

		return ret;
	}

	public void eraseOrphanNamespace(String uri) {
		orphanNamespaceRegistry.remove(uri);
	}

	/**
	 * 
	 * @return all workspaces
	 */
	public Collection<KimWorkspace> getWorkspaces() {
		return KimWorkspace.getWorkspaces();
	}

	/**
	 * Return a project by ID, looking up in all workspaces or (optionally) in the
	 * workspaces under a passed file root.
	 * 
	 * @param id
	 *            the project ID
	 * @param workspaceRoot
	 *            the file root of the workspace. If the project is present in more
	 *            than one workspace, the version in the passed root is returned.
	 *            Can be null, if so the first instance of the project found is
	 *            returned.
	 * @return the project or null
	 */
	public IKimProject getProject(String id, File workspaceRoot) {

		IKimProject ret = null;
		for (KimWorkspace workspace : getWorkspaces()) {
			for (IKimProject project : workspace.getProjects()) {
				if (project.getName().equals(id)) {
					if (workspaceRoot == null) {
						return project;
					} else if (workspace.getRoot().equals(workspaceRoot) || ret == null) {
						ret = project;
					}
				}
			}
		}
		return ret;
	}

	public void removeNamespace(Namespace namespace) {

		String name = Kim.getNamespaceId(namespace);
		KimProject project = null;
		KimWorkspace workspace = KimWorkspace.getWorkspaceForResource(namespace.eResource());
		project = workspace == null ? null : workspace.getProjectForResource(namespace.eResource());
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
		// TODO use callback to define inheritance from core concept
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

	// public void warn(String warning) {
	// // TODO redirect to a callback
	// System.err.println("WARNING: " + warning);
	// }
	//
	// public void error(String warning) {
	// // TODO redirect to a callback
	// System.err.println("ERROR: " + warning);
	// }
	//
	// public void info(String warning) {
	// // TODO redirect to a callback
	// System.out.println("INFO: " + warning);
	// }
	//
	// public void debug(String warning) {
	// // TODO redirect to a callback
	// System.out.println("DEBUG: " + warning);
	// }

	public KimObservable declareModelReference(ModelBodyStatement statement, String string) {

		KimNamespace namespace = getNamespace(statement, true);
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
		return model == null ? null : (KimObservable) model.getObservables().get(0);
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
		return null;
	}

	public void registerNamespace(KimNamespace kimNamespace) {
		this.namespaceRegistry.put(kimNamespace.getName(), kimNamespace);
	}

	public IKimNamespace getNamespace(String id) {
		return this.namespaceRegistry.get(id);
	}

	public void registerProject(KimProject kimProject) {
		this.projectRegistry.put(kimProject.getName(), kimProject);
		this.projectRootRegistry.put(kimProject.getRoot(), kimProject);
	}

	public IKimProject getProject(String id) {
		return this.projectRegistry.get(id);
	}

	public IKimProject getProjectIn(File root, boolean createIfAbsent) {
		IKimProject ret = this.projectRootRegistry.get(root);
		if (ret == null && createIfAbsent) {
			KimWorkspace workspace = KimWorkspace.getWorkspaceForProjectFile(root);
			String pname = root.toString().substring(root.toString().lastIndexOf(File.separator) + 1);
			ret = new KimProject(workspace, pname, root);
			this.projectRegistry.put(pname, ret);
			this.projectRootRegistry.put(root, ret);
		}
		return ret;
	}

	public boolean isKimProject(File file) {
		File props = new File(file + File.separator + "META-INF" + File.separator + "klab.properties");
		return props.exists() && props.isFile();
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
		return this.projectRegistry.values();
	}

}

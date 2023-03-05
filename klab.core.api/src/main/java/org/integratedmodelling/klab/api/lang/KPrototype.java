package org.integratedmodelling.klab.api.lang;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.collections.impl.Range;
import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.knowledge.KArtifact;

/**
 * A IPrototype defines a service API. In k.LAB prototypes apply to commands and
 * language functions so far, both of which can return one of these. Prototypes
 * are automatically created by the @Prototype annotation, which applies to
 * different types of objects and will create services, functions or commands
 * according to the object it applies to, and can be extracted from KDL dataflow
 * declarations (currently the preferred way to define APIs).
 * 
 * @author Ferd
 *
 */
public interface KPrototype extends Serializable {

	/**
	 * Descriptor for each argument.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	interface Argument {

		/**
		 * 
		 * @return the argument name
		 */
		String getName();

		/**
		 * Short name for options. May be computed automatically if not given (KDL has
		 * no semantic support for this).
		 *
		 * @return the argument short name
		 */
		String getShortName();

		/**
		 * 
		 * @return the description
		 */
		String getDescription();

		/**
		 * The distinction option/argument is only relevant in CLI usage. Options are
		 * always also optional.
		 * 
		 * @return true if option
		 */
		boolean isOption();

		/**
		 * 
		 * @return true if optional
		 */
		boolean isOptional();

		/**
		 * If true, the argument should be an expression returning the stated type
		 * rather than a literal.
		 * 
		 * @return
		 */
		boolean isExpression();

		/**
		 * If the argument has a set of IDs as possible values (getType() == Type.ENUM),
		 * return them here.
		 * 
		 * @return the IDs or an empty set.
		 */
		Set<String> getEnumValues();

		/**
		 * The default value for a parameter that is not passed.
		 * 
		 * @return default value (POD, list or {@link Range}) or null.
		 */
		Object getDefaultValue();

		/**
		 * 
		 * @return the type
		 */
		KArtifact.Type getType();

		/**
		 * Final arguments are set only once and mandatorily.
		 * 
		 * @return
		 */
		boolean isFinal();

		/**
		 * Arguments with the status of parameter are modifiable by the user in
		 * interactive workflows. The use of the modifiable assignment '?=' should be
		 * conditioned to the corresponding argument being a parameter.
		 * 
		 * @return
		 */
		boolean isParameter();

		/**
		 * A label for forms or other display. If not specified it should always be set
		 * from the parameter name.
		 * 
		 * @return
		 */
		String getLabel();

		/**
		 * If true, the argument matches an imported artifact of the specified type.
		 * 
		 * @return
		 */
		boolean isArtifact();

		/**
		 * If a unit was specified, return the textual value here. Can also be a currency.
		 * 
		 * @return
		 */
		String getUnit();
	}

	/**
	 * Name of the service or function.
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * Optional prototype label for display or provenance documentation.
	 * 
	 * @return the label
	 */
	String getLabel();

	/**
	 * Service description if provided; empty string otherwise.
	 * 
	 * @return the description. Never null.
	 */
	String getDescription();

	/**
	 * Stated type of the computed artifacts.
	 * 
	 * @return return type
	 */
	KArtifact.Type getType();

	/**
	 * Descriptor for the passed argument name.
	 * 
	 * @param argumentId
	 * @return the argument descriptor, or null.
	 */
	Argument getArgument(String argumentId);

	/**
	 * All argument descriptors in order of declaration.
	 * 
	 * @return all argument descriptors
	 */
	List<Argument> listArguments();

	/**
	 * Imports are arguments that are declared as 'import'. In prototype calls, they
	 * are not expected as parameters, but they are expected to be in the context
	 * (according to their optional status) of the service specified by the call.
	 * 
	 * @return imports, or an empty list.
	 */
	List<Argument> listImports();

	/**
	 * Exports are arguments that are declared as 'export'. In prototype calls, they
	 * are not expected as parameters, but they are expected to be produced into the
	 * context (according to their optional status) by the service specified by the
	 * call.
	 * 
	 * @return exports, or an empty list.
	 */
	List<Argument> listExports();

	/**
	 * In some instances, the inputs for the described computation cannot be
	 * assessed at declaration time, but will be exposed through annotation tags in
	 * the containing model. These can be declared using the k.DL annotation 'import
	 * <type> \@annotation', whose value (corresponding to the name of each
	 * annotation that can tag an input in the model) is returned in the collection.
	 * 
	 * @return names of any annotation that can be used to tag an input of the
	 *         computation produced by the contextualizer specified with this
	 *         prototype.
	 */
	Collection<Argument> listImportAnnotations();

	/**
	 * Like {@link #listImportAnnotations()} but for exports. Unused at the moment,
	 * but k.DL syntax supports the declaration and the parser will fill this in.
	 * 
	 * @return names of any annotation that can be used to tag an output of the
	 *         computation produced by the contextualizer specified with this
	 *         prototype.
	 */
	Collection<Argument> listExportAnnotations();

	/**
	 * Ensure that the arguments passed reflect the required arguments.
	 * 
	 * @param function
	 * @return the collection of errors, each being a pair of <message, level>, or
	 *         an empty collection if no errors occurred
	 * 
	 */
	List<Pair<String, Level>> validate(KServiceCall function);

	/**
	 * Full synopsis - expecting a multi-line string with full description of
	 * options, arguments and subcommands. Integer flags may be passed to affect the
	 * display (e.g. full, json, markdown, html); implementations are free to choose
	 * the flags.
	 * 
	 * @return longer description
	 */
	public String getSynopsis(Integer... flags);

	/**
	 * One-line short synopsis intended to document usage without descriptions.
	 * 
	 * @return short synopsis
	 */
	public String getShortSynopsis();

	/**
	 * Get the class of the object whose API the prototype specifies.
	 * 
	 * @return Java class of object returned
	 */
	public Class<?> getExecutorClass();

	/**
	 * If distributed, the service identified can be broadcast to multiple endpoints
	 * and the results can be merged.
	 * 
	 * @return true if service can be used as a map/reduce operator
	 */
	boolean isDistributed();

	/**
	 * True if the prototype specifies a service that produces an artifact (with the
	 * 'export' option before the type).
	 * 
	 * @return
	 */
	boolean isContextualizer();

	/**
	 * True if the prototype specifies a service that modifies an existing artifact
	 * (with 'filter' instead of 'export'). When filtering, an 'import' parameter is
	 * mandatory. The actual output must match the type of the input and the
	 * returned value may be the same input or a different one.
	 * 
	 * @return
	 */
	boolean isFilter();

	/**
	 * If the service specifications include a geometry for the result, return it
	 * here. Processors that do not produce data return an empty geometry.
	 * 
	 * @return the geometry, possibly empty, never null.
	 */
	KGeometry getGeometry();

	/**
	 * True if the entire prototype has been declared 'const', meaning it requires
	 * no inputs to be contextualized.
	 * 
	 * @return
	 */
	boolean isFinal();

}

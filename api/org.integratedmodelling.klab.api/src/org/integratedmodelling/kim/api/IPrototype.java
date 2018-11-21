package org.integratedmodelling.kim.api;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;

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
public interface IPrototype {


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
		Type getType();

		/**
		 * Final arguments are set only once and mandatorily.
		 * 
		 * @return
		 */
		boolean isFinal();
		
		/**
		 * If true, the argument matches an imported artifact of the specified type.
		 * 
		 * @return
		 */
		boolean isArtifact();
	}

	/**
	 * Name of the service or function.
	 * 
	 * @return the name
	 */
	String getName();

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
	IArtifact.Type getType();

	/**
	 * Descriptor for the passed argument name.
	 * 
	 * @param argumentId
	 * @return the argument descriptor, or null.
	 */
	Argument getArgument(String argumentId);

	/**
	 * All argument descriptors in order of declaration
	 * 
	 * @return all argument descriptors
	 */
	List<Argument> listArguments();

	/**
	 * Ensure that the arguments passed reflect the required arguments.
	 * 
	 * @param function
	 * @return the collection of errors, each being a pair of <message, level>, or
	 *         an empty collection if no errors occurred
	 * 
	 */
	List<Pair<String, Level>> validate(IServiceCall function);

	/**
	 * Full synopsis - expecting a multi-line string with full description of
	 * options, arguments and subcommands.
	 * 
	 * @return longer description
	 */
	public String getSynopsis();

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
	 * True if the prototype specifies a service that produces an artifact (with
	 * the 'export' option before the type).
	 * @return
	 */
	boolean isContextualizer();

	/**
	 * If the service specifications include a geometry for the result, return it
	 * here. Processors that do not produce data return an empty geometry.
	 * 
	 * @return the geometry, possibly empty, never null.
	 */
	IGeometry getGeometry();

	/**
	 * If this is not empty, the presence of any of the named parameter determines,
	 * for a function returning an extent, the multiplicity of that extent. Used to
	 * check extent dimensional correctness (e.g. in units) in calls at the client
	 * side, when the actual extent cannot be created. This is used for, e.g.,
	 * "grid" in spatial extents and "step" in temporal ones.
	 * 
	 * @return the extent parameters
	 */
	Collection<String> getExtentParameters();
}

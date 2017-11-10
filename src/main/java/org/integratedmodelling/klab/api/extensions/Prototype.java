/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.extensions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * All arguments have names that act as keywords that are local to the command.
 * They may be left unspecified and are matched to all mandatory arguments in
 * order of declaration. Optional arguments names are only matched when
 * introduced by -<short name> or --<long name>.
 * 
 * Arguments are string that may include additional specifications:
 * 
 * - single string = mandatory argument. If string contains two arguments
 * separated by | they're interpreted as short and full name, e.g.
 * 
 * "? o|output" // short names only useful with options
 * 
 * - optional flag is a ? or a # as a separate token: "? f|format" or "#
 * format". Use # for arguments and ? for options. It makes no difference in web
 * services, but the command line will be parsed differently.
 * 
 * - argument type is given separately in argTypes using the constants provided.
 * Instead of the type, a set of tokens separated by | can identify the admitted
 * options:
 * 
 * { Command.INT, " gif|png|jpg", Command.TEXT }
 * 
 * The arrays args(), argTypes() and argDescriptions() must have exactly the
 * same length.
 * 
 * Commands may have subcommands, in which case the first argument (unnamed) is
 * used for that. The subcommands are not declared in the annotation but
 * specified in the
 * 
 * @Execute method annotation on the executor class.
 * 
 * @author ferdinando.villa
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Prototype {

	// type constants. The user can also use known concepts as return types.
	public static final String NONE = "void";
	public static final String INT = "integer";
	public static final String FLOAT = "float";
	public static final String TEXT = "text";
	public static final String CONCEPT = "concept";
	public static final String KNOWLEDGE = "knowledge";
	public static final String LIST = "list";
	public static final String ENUM = "enumeration";
	public static final String BOOLEAN = "boolean";
	public static final String EXPRESSION = "expression";

	/**
	 * Command ID. Can have dots that disambiguate components. There is no
	 * automatic prefixing of the component ID.
	 * 
	 * @return command ID
	 */
	public String id();

	/**
	 * All arguments (mandatory and optional) and options, according to rules
	 * outlined above. Follow each argument declaration with its type: "arg",
	 * Command.Text, "arg2", Command.INT etc.
	 * 
	 * @return arguments
	 */
	public String[] args() default {};

	/**
	 * Alternative specification of arguments compared to args(): use triples,
	 * with <arg id> <type> <description> instead of a separate description
	 * parameter. Forces and eases the specification of description and will
	 * probably become the default in 1.0.
	 * 
	 * @since 0.9.10
	 * @return
	 */
	public String[] arguments() default {};

	/**
	 * Argument descriptions. Must correspond in size to args() or be absent.
	 * 
	 * @return description of arguments
	 */
	public String[] argDescriptions() default {};

	/**
	 * Method general description.
	 * 
	 * @return description
	 */
	String description() default "";

	/**
	 * Use same return types as the arg types, or any other concept. May return
	 * variable types, so more than one are allowed. Not validated upon return
	 * so far, but used to pre-validate calls when the prototype specifies a
	 * language function.
	 * 
	 * @return return types
	 */
	String[] returnTypes() default {};

	/**
	 * Distributed means that we can spawn this service on multiple endpoints
	 * and merge the results.
	 * 
	 * @return true if distributed
	 */
	boolean distributed() default false;

	/**
	 * When a prototype that defines a service in a component's API is
	 * published, the TQL language gets a peer function with the same name that
	 * calls the service on the available network.
	 */
	boolean published() default false;

	/**
	 * If this is passed, the presence of the named parameter determines, for 
	 * a function returning an extent, the multiplicity of that extent. Used to
	 * check extent dimensional correctness (e.g. in units) in calls at the 
	 * client side, when the actual extent cannot be created. This is
	 * used for, e.g., "grid" in spatial extents and "step" in temporal ones.
	 * 
	 * @return
	 */
	String[] extentParameters() default {};
	
	/**
	 * If given, the associated class (which must be a matching contextualizer)
	 * is automatically wired to the concept located by the passed constant.
	 * 
	 * @return true if automatically wired
	 */
	String autowire() default "";

}

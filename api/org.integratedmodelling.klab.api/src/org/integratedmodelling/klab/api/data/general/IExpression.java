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
package org.integratedmodelling.klab.api.data.general;

import java.util.Collection;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * Simple execution interface for expressions. A new expression is generated per
 * each call to the corresponding language statement, so each object can store
 * local data about its call context.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IExpression {

	// TODO still have to support these instead of passing flags to the compiler
	public enum CompilerOption {
		/**
		 * 
		 */
		IgnoreContext,
		/**
		 * Force scalar usage of all identifiers. Linked to prefixing the expression
		 * with # in k.IM.
		 */
		ForcedScalar,
		/**
		 * Translate identifiers like id@ctx into id["ctx"] instead of inserting the
		 * recontextualization hooks for states. This can be limited to 
		 */
		RecontextualizeAsMap
	}

	/**
	 * The context to compile an expression. If passed, it is used to establish the
	 * role of the identifiers, which may affect preprocessing.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	public interface Context {

		/**
		 * The expected return type, if known.
		 * 
		 * @return
		 */
		IKimConcept.Type getReturnType();

		/**
		 * Namespace of evaluation, if any.
		 * 
		 * @return
		 */
		INamespace getNamespace();

		/**
		 * All known identifiers at the time of evaluation.
		 * 
		 * @return
		 */
		Collection<String> getIdentifiers();

		/**
		 * All known identifiers of quality observations at the time of evaluation.
		 * 
		 * @return
		 */
		Collection<String> getStateIdentifiers();

		/**
		 * The type of the passed identifier.
		 * 
		 * @param identifier
		 * @return
		 */
		IKimConcept.Type getIdentifierType(String identifier);

		/**
		 * The scale of evaluation, or null.
		 * 
		 * @return
		 */
		IScale getScale();

		/**
		 * A monitor for notifications.
		 * 
		 * @return
		 */
		IMonitor getMonitor();
	}

	/**
	 * Execute the expression
	 *
	 * @param parameters from context or defined in a language call
	 * @param context    possibly empty, may be added to determine the result of the
	 *                   evaluation according to the calling context. The
	 *                   {@link IContextualizationScope#getMonitor() monitor in the
	 *                   context} will never be null and can be used to send
	 *                   messages or interrupt the computation.
	 * @return the result of evaluating the expression
	 * @throws org.integratedmodelling.klab.exceptions.KlabException TODO
	 */
	Object eval(IParameters<String> parameters, IContextualizationScope context);

}

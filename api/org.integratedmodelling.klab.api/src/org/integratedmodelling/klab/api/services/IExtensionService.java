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
package org.integratedmodelling.klab.api.services;

import java.util.Collection;

import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimSymbolDefinition;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.component.IComponent;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;

/**
 * This service manages extensions, i.e. components and services that can be
 * added to the system through plug-ins. It also provides the global dictionary
 * for service prototypes and manages their execution from service calls
 * (corresponding to functions in k.IM).
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IExtensionService {

	public static final String DEFAULT_EXPRESSION_LANGUAGE = "groovy";

	/**
	 * All components registered with the runtime, active or not.
	 *
	 * @return all components
	 */
	Collection<IComponent> getComponents();

	/**
	 * Return the prototype for the named service or function.
	 *
	 * @param service id
	 * @return a prototype, or null if the service is unknown.
	 */
	IPrototype getPrototype(String service);

	/**
	 * Any k.IM function call stated in k.IM and contained in a k.IM object is
	 * executed here.
	 *
	 * @param functionCall a {@link org.integratedmodelling.kim.api.IServiceCall}
	 *                     object.
	 * @param monitor      a
	 *                     {@link org.integratedmodelling.klab.api.runtime.monitoring.IMonitor}
	 *                     object.
	 * @return the return value of the function
	 * @throws KlabResourceNotFoundException                         if the function
	 *                                                               is unknown
	 * @throws org.integratedmodelling.klab.exceptions.KlabException if any
	 *                                                               exception was
	 *                                                               thrown during
	 *                                                               evaluation
	 */
	Object callFunction(IServiceCall functionCall, IMonitor monitor) throws KlabException;

	/**
	 * Get an instance of a language processor appropriate for the passed language.
	 * Only {@link #DEFAULT_EXPRESSION_LANGUAGE} is guaranteed to not return null.
	 * 
	 * @param language
	 * @return a language processor or null
	 */
	ILanguageProcessor getLanguageProcessor(String language);

	/**
	 * Return a specific component implementation. If active, it will have already
	 * been initialized.
	 *
	 * @param componentId    a {@link java.lang.String} object.
	 * @param requestedClass
	 * @return the component implementation, or null if unknown.
	 */
	<T> T getComponentImplementation(String componentId, Class<? extends T> requestedClass);

	/**
	 * Parse a string expression into the syntactic expression peer for the default language.
	 * 
	 * @param expression
	 * @return
	 */
	IKimExpression parse(String expression);
	
	/**
	 * Define statements that use a specific class will have their argument filtered
	 * by this method, so that the object can be processed appropriately. If the
	 * resulting object is a {@link IViewModel} it will be resolvable by the
	 * engine.
	 * 
	 * @param viewClass
	 * @param definition
	 * @return
	 */
	Object processDefinition(IKimSymbolDefinition statement, Object definition, INamespace namespace, IMonitor monitor);

}

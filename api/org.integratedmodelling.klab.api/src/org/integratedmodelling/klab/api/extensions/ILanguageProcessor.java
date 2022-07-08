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
package org.integratedmodelling.klab.api.extensions;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerOption;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

/**
 * A processor for a supported expression language to be used in k.IM expressions. The k.LAB
 * implementation must provide a default, accessible through
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface ILanguageProcessor {

    /**
     * 
     * @author Ferd
     *
     */
    interface Descriptor {

        /**
         * Return all identifiers detected.
         * 
         * @return set of identifiers
         */
        Collection<String> getIdentifiers();

        /**
         * Return all contextualizers encountered (in expressions such as "elevation@nw")
         * 
         * @return set of contextualizers
         */
        Collection<String> getContextualizers();

        /**
         * True if the expression contains scalar usage for one or more of the identifiers used in a
         * scalar fashion. This may be false even if the expression was compiled in scalar scope.
         * 
         * @return
         */
        boolean isScalar();

        /**
         * Return true if the expression contains scalar usage for the passed identifiers within a
         * transition (i.e. used alone or with locator semantics for space or other non-temporal
         * domain).
         * 
         * @param identifier identifiers representing states
         * 
         * @return true if the identifier is used in a scalar context.
         */
        boolean isScalar(String identifier);

        /**
         * Return true if the expression contains non-scalar usage for the passed identifiers within
         * a transition (i.e. used as an object, with methods called on it).
         * 
         * @param identifier identifiers representing states
         * 
         * @return true if the identifier is used in a scalar context.
         */
        boolean isNonscalar(String identifier);

        /**
         * Return true if the expression contains scalar usage for any of the passed identifiers
         * within a transition (i.e. used alone or with locator semantics for space or other
         * non-temporal domain).
         * 
         * @param stateIdentifiers identifiers representing states
         * 
         * @return true if any of the identifiers is used in a scalar context.
         */
        boolean isScalar(Collection<String> stateIdentifiers);

        /**
         * Return true if the expression contains non-scalar usage for any of the passed identifiers
         * within a transition (i.e. used as an object, with methods called on it).
         * 
         * @param stateIdentifiers identifiers representing states
         * 
         * @return true if any of the identifiers is used in a scalar context.
         */
        boolean isNonscalar(Collection<String> stateIdentifiers);

        /**
         * In order to avoid duplicated action, the descriptor alone must be enough to compile the
         * expression. If we have a valid descriptor the returned expression must be valid so no
         * exceptions are thrown unless the descriptor has errors, which causes an
         * IllegalArgumentException.
         * 
         * @return a compiled expression ready for execution in the context that produced the
         *         descriptor
         * @throws IllegalArgumentException if the descriptor has errors
         */
        ILanguageExpression compile();

        /**
         * 
         * @return
         */
        Collection<String> getIdentifiersInScalarScope();

        /**
         * 
         * @return
         */
        Collection<String> getIdentifiersInNonscalarScope();

        /**
         * If the expression was compiled with the {@link CompilerOption#RecontextualizeAsMap}
         * option, any identifier seen as id@ctx will have been turned into id["ctx"] and the id
         * plus all the keys will be available here.
         * 
         * @return
         */
        Map<String, Set<String>> getMapIdentifiers();

        /**
         * Return the set of options that were passed when this expression was compiled. May be
         * empty, never null.
         * 
         * @return
         */
        Collection<CompilerOption> getOptions();

        /**
         * Predefined variables that have been inserted in the code and whose value is known at the
         * time of compilation. Typically translations of k.IM identifiers and URNs into the
         * correspondent objects.
         * 
         * @return
         */
        Map<String, Object> getVariables();
    }

    /**
     * Compile the expression in the passed context, which may be null.
     *
     * @param expression a {@link java.lang.String} object.
     * @param context a {@link org.integratedmodelling.klab.api.runtime.IContextualizationScope}
     *        object.
     * @return the compiled expression
     * @throws org.integratedmodelling.klab.exceptions.KlabValidationException if compilation
     *         produces any errors
     */
    IExpression compile(String expression, IExpression.Scope scope, CompilerOption... options)
            throws KlabValidationException;

    /**
     * Preprocess an expression and return the descriptor. The context may be null, but the
     * expression is still assumed to be in k.LAB contextualization scope - i.e. no identifiers will
     * be recognized as known if the context is null.
     *
     * @param expression a {@link java.lang.String} object.
     * @param context a scope for the compilation
     * @param options Options for the compiler.
     * 
     * @return a preprocessed descriptor, which must be enough to produce an IExpression on request.
     * @throws org.integratedmodelling.klab.exceptions.KlabValidationException if the expression
     *         contains syntax of logical errors
     */
    Descriptor describe(String expression, IExpression.Scope scope, CompilerOption... options)
            throws KlabValidationException;

    /**
     * Assume that the passed expression evaluates to a boolean and produce the language equivalent
     * of its negation.
     *
     * @param expression a {@link java.lang.String} object.
     * @param forcedScalar if true, the expression will be forced to evaluate in scalar context
     *        independent of its content.
     *
     * @return another expression producing the opposite truth value as the original
     */
    String negate(String expression);

}

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
package org.integratedmodelling.klab.api.lang;

import java.util.Collection;

import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.knowledge.observation.scale.KScale;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KContextScope;
import org.integratedmodelling.klab.api.services.runtime.KChannel;

/**
 * Simple execution interface for expressions. A new expression is generated per each call to the
 * corresponding language statement, so each object can store local data about its call context.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface KExpression {

    public enum Forcing {
        AsNeeded, Always
    }

    public enum CompilerScope {
        /**
         * Execution is triggered by state-by-state logic, so specific subdivision of the context is
         * located at execution, and observation IDs point to state values insteaf of the entire
         * observation when the observation is a quality. Called very many times and concurrently.
         * Irrelevant unless Contextual is also passed.
         */
        Scalar,
        /**
         * Execution is observation-wise, so if states of qualities are needed, the correspondent
         * observations must be iterated within the expression. Irrelevant unless Contextual is also
         * passed.
         */
        Observation,
        /**
         * The expression will be executed in the scope of an observation, so all the auxiliary
         * variables are defined and the observations will be located to the space/time of
         * contextualization. If not passed, expression is normal Groovy code with the k.LAB
         * extensions for reasoning etc.
         */
        Contextual
    }

    // TODO still have to support these instead of passing flags to the compiler
    public enum CompilerOption {

        /**
         * Don't try to process code related to observations and just consider each variable with
         * its own name.
         */
        IgnoreContext,

        /**
         * Translate identifiers like id@ctx into id["ctx"] instead of inserting the
         * recontextualization hooks for states.
         */
        RecontextualizeAsMap,

        /**
         * Ignore the recontextualizations done with @. Passed when expressions are compiled as part
         * of documentation templates, which use @ for internal purposes.
         */
        IgnoreRecontextualization,

        /**
         * Skip k.LAB preprocessing altogether.
         */
        DoNotPreprocess,

        /**
         * Refer to quality values coming from states directly instead of compiling in a
         * state.get(scale) instruction. Values must be inserted in parameters at eval(). Use when
         * speed is critical - Groovy takes a long time dispatching the messages.
         */
        DirectQualityAccess
    }

    /**
     * The context to compile an expression. If passed, it is used to establish the role of the
     * identifiers, which may affect preprocessing.
     * 
     * @author ferdinando.villa
     *
     */
    public interface Scope {

        /**
         * The expected return type, if known.
         * 
         * @return
         */
        SemanticType getReturnType();

//        /**
//         * Namespace of evaluation, if any.
//         * 
//         * @return
//         */
//        INamespace getNamespace();

        /**
         * All known identifiers at the time of evaluation.
         * 
         * @return
         */
        Collection<String> getIdentifiers();

        /**
         * Add a scalar identifier that we want recognized at compilation. TODO this should use
         * IArtifact.Type
         */
        void addKnownIdentifier(String id, SemanticType type);

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
        SemanticType getIdentifierType(String identifier);

        /**
         * The scale of evaluation, or null.
         * 
         * @return
         */
        KScale getScale();

        /**
         * A monitoring channel for notifications.
         * 
         * @return
         */
        KChannel getMonitor();

        /**
         * The type of compilation we desire. This should automatically be set to Contextual if a
         * contextualization scope is passed.
         * 
         * @return
         */
        CompilerScope getCompilerScope();

        /**
         * Return a scope that will cause the execution of the expression to be scalar, i.e.
         * state-by-state within the context. The forcing passed defines the type of constraint: if
         * {@link Forcing#AsNeeded}, the expression will be scalar only if it mentions quality
         * variables in a scalar scope; if {@link Forcing#Always}, scalar behavior will be forced no
         * matter the statement.
         * 
         * @param forceScalar
         * @return
         */
        Scope scalar(Forcing forcing);

        /**
         * If the expression scope was created during contextualization, return the scope here.
         * 
         * @return
         */
        KContextScope getRuntimeScope();

        /**
         * If true, we have requested the expression to be evaluated in a scalar fashion no matter
         * what it says.
         * 
         * @return
         */
        boolean isForcedScalar();

    }

    /**
     * Execute the expression
     *
     * @param parameters from context or defined in a language call
     * @param scope possibly empty, may be added to determine the result of the evaluation according
     *        to the calling context. The {@link IContextualizationScope#getMonitor() monitor in the
     *        context} will never be null and can be used to send messages or interrupt the
     *        computation.
     * @param additionalParameters add key, value pairs for any additional parameter to add
     * @return the result of evaluating the expression
     * @throws org.integratedmodelling.klab.KException.KlabException TODO
     */
    Object eval(KContextScope scope, Object... additionalParameters);

}

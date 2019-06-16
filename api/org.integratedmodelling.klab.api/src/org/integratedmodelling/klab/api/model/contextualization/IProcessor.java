package org.integratedmodelling.klab.api.model.contextualization;

/**
 * Tag interface to define those contextualizers that modify an artifact rather than producing
 * it. Does not change the API of either instantiators or resolvers but the engine will validate
 * and take different paths according to the presence of this.
 * 
 * @author Ferd
 *
 */
public interface IProcessor {

}

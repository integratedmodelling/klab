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
package org.integratedmodelling.klab.api.resolution;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * The object that creates a ranking of whatever object is being used to represent a model according to 
 * the implementation. Extracts the criteria for ranking from the object and aggregates them into an
 * overall ranking used for comparison.
 * 
 * This class isn't directly used in any API methods so far, but it's in the public API for completeness and
 * to provide a vocabulary for the criteria that allow the ranking of alternative observation 
 * strategies (i.e. models).
 * 
 * Data structure returned from query with ranks computed at server side based on context; sorting
 * happens at request side after merge.
 * 
 *      lexical scope -> locality wrt context 
 *          100 = in observation scenario 
 *          50 = in same namespace as context 
 *          0 = non-private in other namespace 
 *      trait concordance -> in context 
 *          n = # of traits shared vs. n. of traits possible, normalized to 100
 *      scale coverage -> of scale in context (minimum of all extents? or one per extent?)
 *          0 = not scale-specific (outside scale will not be returned) 
 *          (1, 100] = (scale ^ object context) / scale 
 *      scale specificity -> total coverage of object wrt context (minimum of all extents?)
 *          <n> = scale / (object coverage) * 100
 *      inherency -> level wrt observable:
 *          100 = same thing-ness, specific inherency 
 *          66 = same thing-ness, non-specific inherency 
 *          33 = different thing-ness, mediatable inherency 
 *          0 = secondary observable obtained by running a process model
 *      evidence -> resolved/unresolved 
 *          100 = resolved from datasource 
 *          50 = computed, no dependencies 
 *          0 = unresolved
 *      network remoteness -> whether coming from remote KBox (added by kbox implementation)
 *          100 -> local
 *          0 -> remote
 *      scale coherency -> coherency of domains adopted by context vs. the object
 *          n = # of domains shared (based on the isSpatial/isTemporal fields) normalize to 100
 *      subjective concordance = multi-criteria ranking of user-defined metadata wrt default or namespace priorities
 *          n = chosen concordance metric normalized to 100
 *          
 * Clarifications for the inherency criterion:
 * 
 * same thing-ness, specific: 
 *      (type) OR (type according to trait) // Second one is a further spec for the classification observation type, 
 *       different inherent-ness + observation type + inherent type
 * 
 * // only do this with SUBJECT inherency, i.e. dependency has no inherency stated 
 * same thing-ness, non specific: 
 *      (type) OR (type according to trait) + observation type + (NO inherent type)
 * 
 * dereifying: 
 *      direct observation of <inherent type> where an attribute provides <ob type> of <type>
 *          
 *          
 * @author ferdinando.villa
 * @param <T> the type of model bean that is compared. Usually not the actual model as beans from the network
 *            need to be compared and building an actual model that won't be used may be expensive.
 *
 */
public interface IPrioritizer<T> extends Comparator<T> {

    /**
     * The default ranking strategy in the form that can be given in klab.properties for
     * overriding. 
     */
    public static final String DEFAULT_RANKING_STRATEGY =  "im:lexical-scope 1 " 
            + "im:evidence 4 "
            + "im:semantic-concordance 2 "
            + "im:trait-concordance 3 "
            + "im:scale-coverage 5 "
            + "im:subjective-concordance 6 "
            + "im:scale-specificity 7 "
            + "im:inherency 8 "
            + "im:scale-coherency 0 "
            + "im:network-remoteness 0 "
            + "im:reliability 100";

    public static final String DEFAULT_STRATEGY_PROPERTY_NAME = "klab.ranking.strategy";

    /**
     * These are the fields that will be directly available in the object returned, to allow
     * further ranking of subjective information and retrieval of the object if selected.
     */
    public static final String METADATA     = "metadata";
    public static final String OBJECT_NAME  = "name";
    public static final String PROJECT_NAME = "project";
    public static final String NAMESPACE_ID = "namespace";
    public static final String SERVER_ID    = "server";

    /**
     * Rank all data and return a map of the criteria computed.
     * 
     * @param model
     * @param context 
     * @return the criteria values for model in context
     */
    Map<String, Object> computeCriteria(T model, IResolutionScope context);

    /**
     * Get the computed ranks for the passed object, or null if they were not
     * computed.
     * 
     * @param md
     * @return ranks from object, if any
     */
    Map<String, Object> getRanks(T md);

    /**
     * List the keys of each criterion in the chosen ranking strategy, in order of 
     * importance.
     * 
     * @return criteria
     */
    List<String> listCriteria();

}

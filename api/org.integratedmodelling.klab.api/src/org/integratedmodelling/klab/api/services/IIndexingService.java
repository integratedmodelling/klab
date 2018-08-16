package org.integratedmodelling.klab.api.services;

import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;

/**
 * The service that supports intelligent, fast suggestions and search for
 * semantic content.
 * 
 * @author Ferd
 *
 */
public interface IIndexingService {

    /**
     * The context of the search, starting empty and built as new matches
     * are accepted.
     * 
     * @author Ferd
     *
     */
    public interface Context {

        boolean isEmpty();

        /**
         * Notify the choice of a match and return a new search context that can match anything after it.
         * 
         * @param match
         * @return the next context after accepting the choice. 
         */
        Context accept(Match match);

        /**
         * Pop the current context (which becomes invalid) and return the previous one, which becomes
         * current.
         * 
         * @return
         */
        Context previous();

        /**
         * Return true if the current context does not admit further search.
         * 
         * @return true if we cannot take more matches.
         */
        boolean isEnd();

        /**
         * Return the URN of the choice, another string that will enable its
         * reconstruction (such as a concept declaration), or null.
         * 
         * @return the chosen URN.
         */
        String getUrn();

        /**
         * True if context is not in error, i.e. can produce a valid match.
         * 
         * @return true if consistent
         */
        boolean isConsistent();
    }

    /**
     * Match from query.
     * 
     * @author Ferd
     *
     */
    public interface Match {

        public enum Type {
            CONCEPT,
            OPERATOR,
            OBSERVATION,
            MODEL,
            MODIFIER
        }

        /**
         * 
         * @return
         */
        String getId();

        /**
         * 
         * @return
         */
        String getName();

        /**
         * 
         * @return
         */
        String getDescription();

        /**
         * 
         * @return
         */
        float getScore();

        /**
         * 
         * @return
         */
        Type getMatchType();

        /**
         * If {@link #getMatchType()} returned {@link Type#CONCEPT}, return the full type of
         * the concept.
         * 
         * @return
         */
        Set<IKimConcept.Type> getConceptType();

        /**
         * Named indexable fields besides name and description.
         * 
         * @return
         */
        Map<String, String> getIndexableFields();
    }

    /**
     * Pass a search string and a context to define the possible matches.
     * 
     * @param currentTerm
     * @param context
     * @return a valid iterator for matches that will return the best matches first.
     *         Never null.
     */
    public Iterable<Match> query(String currentTerm, Context context);
}

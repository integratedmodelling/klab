package org.integratedmodelling.klab.engine.indexing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.integratedmodelling.klab.api.services.IIndexingService;
import org.integratedmodelling.klab.api.services.IIndexingService.Context;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.api.services.IIndexingService.Match.Type;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class SearchContext implements IIndexingService.Context {

    // these are in OR - anything matching any of these is acceptable. No constraints means everything is acceptable.
    private List<Constraint> constraints = new ArrayList<>();
    private Set<Type> constraintTypes = EnumSet.noneOf(Type.class);
    private SearchContext parent = null;

    public SearchContext() {
    }

    private SearchContext(SearchContext parent) {
        this.parent = parent;
    }

    class Condition {

        boolean match(Document document) {
            return true;
        }
    }

    class Constraint {

        // these are in AND
        List<Condition> conditions = new ArrayList<>();
        Type type;

        boolean match(Document document) {
            for (Condition condition : conditions) {
                if (!condition.match(document)) {
                    return false;
                }
            }
            return true;
        }

    }

    @Override
    public SearchContext accept(Match match) {
        SearchContext ret = new SearchContext(this);
        // TODO define constraints for the next match
        return ret;
    }

    public boolean isAllowed(Type type) {
        return constraintTypes.isEmpty() || constraintTypes.contains(type);
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public boolean isConsistent() {
        return false;
    }

    @Override
    public String getUrn() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEmpty() {
        // root context is empty
        return parent == null;
    }

    @Override
    public Context previous() {
        return parent;
    }

    public Query buildQuery(String currentTerm, Analyzer analyzer) {
        QueryParser parser = new QueryParser("name", analyzer);
        //      parser.setAllowLeadingWildcard(true);
        try {
            // hai voglia
            return parser.parse("name:" + currentTerm + "*");
        } catch (ParseException e) {
            throw new KlabValidationException(e);
        }  
    }

}

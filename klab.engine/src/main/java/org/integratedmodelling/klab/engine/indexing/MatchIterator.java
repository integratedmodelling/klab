package org.integratedmodelling.klab.engine.indexing;

import java.io.IOException;
import java.util.Set;

import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;

public class MatchIterator implements InputIterator {

    private Match currentMatch;

    @Override
    public BytesRef next() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<BytesRef> contexts() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasContexts() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasPayloads() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public BytesRef payload() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long weight() {
        // TODO Auto-generated method stub
        return 0;
    }

}

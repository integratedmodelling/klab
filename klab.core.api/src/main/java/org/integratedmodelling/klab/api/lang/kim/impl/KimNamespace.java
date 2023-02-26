package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.lang.KServiceCall;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;
import org.integratedmodelling.klab.api.lang.kim.KKimNamespace;
import org.integratedmodelling.klab.api.lang.kim.KKimStatement;

/**
 * The syntactic peer of a k.LAB namespace.
 * 
 * @author ferdinando.villa
 *
 */
public class KimNamespace extends KimStatement implements KKimNamespace {

    private static final long serialVersionUID = 6198296119075476515L;

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getImportedNamespaceIds() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> getDisjointNamespaces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends KKimStatement> T getStatement(String name, Class<T> cls) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getTimestamp() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<Pair<String, String>> getOwlImports() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Pair<String, List<String>>> getVocabularyImports() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getSymbolTable() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isInactive() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isScenario() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public KKimConcept getDomain() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getScriptId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTestCaseId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isWorldviewBound() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<KKimStatement> getAllStatements() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> getImportedNamespaceIds(boolean scanUsages) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KServiceCall> getExtents() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> getImportedIds() {
        // TODO Auto-generated method stub
        return null;
    }


}

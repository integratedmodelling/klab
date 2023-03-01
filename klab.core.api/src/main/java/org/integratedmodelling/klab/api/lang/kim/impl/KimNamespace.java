package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private String name;
    private Set<String> disjointNamespaces = new HashSet<>();
    private long timestamp;
    private List<Pair<String, String>> owlImports = new ArrayList<>();
    private List<Pair<String, List<String>>> vocabularyImports = new ArrayList<>();
    private boolean inactive;
    private boolean scenario;
    private KKimConcept domain;
    private String scriptId;
    private String testCaseId;
    private boolean worldviewBound;
    private List<KServiceCall> extents = new ArrayList<>();
    private Map<String, Object> defines = new HashMap<>();
    private List<KKimStatement> statements = new ArrayList<>();
    private Map<String, List<String>> imports = new HashMap<>();

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Collection<String> getDisjointNamespaces() {
        return this.disjointNamespaces;
    }

    @Override
    public long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public List<Pair<String, String>> getOwlImports() {
        return this.owlImports;
    }

    @Override
    public List<Pair<String, List<String>>> getVocabularyImports() {
        return this.vocabularyImports;
    }

    @Override
    public boolean isInactive() {
        return this.inactive;
    }

    @Override
    public boolean isScenario() {
        return this.scenario;
    }

    @Override
    public KKimConcept getDomain() {
        return this.domain;
    }

    @Override
    public String getScriptId() {
        return this.scriptId;
    }

    @Override
    public String getTestCaseId() {
        return this.testCaseId;
    }

    @Override
    public boolean isWorldviewBound() {
        return this.worldviewBound;
    }

    @Override
    public List<KServiceCall> getExtents() {
        return this.extents;
    }

    @Override
    public Map<String, Object> getDefines() {
        return this.defines;
    }

    @Override
    public List<KKimStatement> getStatements() {
        return this.statements;
    }

    @Override
    public Map<String, List<String>> getImports() {
        return this.imports;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisjointNamespaces(Set<String> disjointNamespaces) {
        this.disjointNamespaces = disjointNamespaces;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setOwlImports(List<Pair<String, String>> owlImports) {
        this.owlImports = owlImports;
    }

    public void setVocabularyImports(List<Pair<String, List<String>>> vocabularyImports) {
        this.vocabularyImports = vocabularyImports;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public void setScenario(boolean scenario) {
        this.scenario = scenario;
    }

    public void setDomain(KKimConcept domain) {
        this.domain = domain;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public void setWorldviewBound(boolean worldviewBound) {
        this.worldviewBound = worldviewBound;
    }

    public void setExtents(List<KServiceCall> extents) {
        this.extents = extents;
    }

    public void setDefines(Map<String, Object> defines) {
        this.defines = defines;
    }

    public void setStatements(List<KKimStatement> statements) {
        this.statements = statements;
    }

    public void setImports(Map<String, List<String>> imports) {
        this.imports = imports;
    }

}

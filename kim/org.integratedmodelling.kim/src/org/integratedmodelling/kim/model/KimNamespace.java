package org.integratedmodelling.kim.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimLoader;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimAcknowledgement;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.kim.Function;
import org.integratedmodelling.kim.kim.Namespace;
import org.integratedmodelling.kim.kim.OwlImport;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.common.SemanticType;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;

public class KimNamespace extends KimStatement implements IKimNamespace {

    private static final long serialVersionUID = 7273137353905996543L;

    private String name;
    private IKimProject project;
    private long timestamp = System.currentTimeMillis();
    private List<String> imported = new ArrayList<>();
    private boolean inactive = false;
    private boolean scenario = false;
    private List<Pair<String, String>> owlImports = new ArrayList<>();
    private Map<String, Object> symbolTable = new HashMap<>();
    private String scriptId;
    private String testCaseId;
    private boolean worldviewBound = false;
    private File file;
    private Scope scope = Scope.PUBLIC;
    private boolean annotationsScanned = false;
    private Set<String> importsScanned = null;
    private IKimLoader loader;
    private List<IServiceCall> extents = new ArrayList<>();
    private Map<String, IKimStatement> statementsByName = new HashMap<>();
    private IKimConcept domain;
    private List<String> disjointNamespaces = new ArrayList<>();

    private List<Pair<String, List<String>>> vocabularies = new ArrayList<>();

    public KimNamespace(Namespace namespace, KimProject project) {
        super(namespace, null);
        this.name = this.namespaceId = Kim.getNamespaceId(namespace);
        if (namespace.eResource().getURI().isFile()) {
            this.file = new File(namespace.eResource().getURI().toFileString());
            if (file.exists()) {
                this.timestamp = file.lastModified();
            }
        }
        if (this.timestamp /* still */ == 0) {
            this.timestamp = namespace.eResource().getTimeStamp();
        }
        this.project = project;
        if (namespace.getDisjointNamespaces() != null) {
            for (String s : namespace.getDisjointNamespaces()) {
                this.disjointNamespaces.add(s);
            }
        }
        project.addNamespace(this);
        this.worldviewBound = namespace.isWorldviewBound();
        this.domain = new KimConcept(namespace.getDomainConcept(), this);
        // worldview-bound anonymous namespaces are private by design.
        if (namespace.isPrivate()) {
            scope = namespace.isProjectPrivate() ? Scope.PROJECT : Scope.NAMESPACE;
        }
        if (namespace.isWorldviewBound()) {
            this.scope = Scope.NAMESPACE;
        }
        this.inactive = namespace.isInactive();
        this.scenario = namespace.isScenario();
        for (OwlImport imp : namespace.getOwlImports()) {
            if (imp.getUrn() != null) {
                List<String> imports = new ArrayList<>();
                if (imp.getSingle() != null) {
                    imports.add(imp.getSingle());
                } else {
                    for (Object o : Kim.INSTANCE.parseList(imp.getImports(), this)) {
                        imports.add(o.toString());
                    }
                }
                vocabularies.add(new Pair<>(imp.getUrn(), imports));
            } else {
                owlImports.add(new Pair<>(imp.getName(), imp.getPrefix()));
            }
        }

        for (Function extent : namespace.getCoverage()) {
            extents.add(new KimServiceCall(extent, this));
        }

        if (namespace.getMetadata() != null) {
            metadata = new KimMetadata(namespace.getMetadata(), this);
        }
        if (namespace.getDocstring() != null) {
            if (metadata == null) {
                metadata = new KimMetadata();
            }
            metadata.put(IMetadata.DC_COMMENT, namespace.getDocstring());
        }

        Kim.INSTANCE.registerNamespace(this);
    }

    @Override
    public Set<String> getImportedNamespaceIds(boolean scanUsages) {

        Set<String> ret = new HashSet<>();
        for (IKimNamespace imported : getImported()) {
            ret.add(imported.getName());
        }
        if (scanUsages) {
            if (importsScanned == null) {
                scanImports();
            }
            ret.addAll(importsScanned);
        }
        return ret;
    }

    private void scanImports() {

        importsScanned = new HashSet<>();

        visit(new DefaultVisitor(){

            @Override
            public void visitReference(String conceptName, EnumSet<Type> type, IKimConcept validParent) {
                SemanticType st = SemanticType.create(conceptName);
                if (st.isCorrect()) {
                    importsScanned.add(st.getNamespace());
                }
            }

        });
    }

    @Override
    public boolean isWorldviewBound() {
        return worldviewBound;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    public void setWorldviewBound(boolean isWorldviewBound) {
        this.worldviewBound = isWorldviewBound;
    }

    public KimNamespace(String id, File file) {
        this.name = this.namespaceId = id;
        this.file = file;
        // TODO resource URI from file
    }

    @Override
    public List<IKimStatement> getAllStatements() {
        List<IKimStatement> ret = new ArrayList<>();
        getAllStatements_(this, ret);
        return ret;
    }

    private void getAllStatements_(KimStatement statement, List<IKimStatement> ret) {
        if (!(statement instanceof IKimNamespace)) {
            ret.add(statement);
        }
        for (IKimScope scope : statement.getChildren()) {
            if (scope instanceof KimStatement) {
                getAllStatements_((KimStatement) scope, ret);
            }
        }
    }

    @Override
    protected String getStringRepresentation(int offset) {
        String ret = offset(offset) + "[namespace " + name + "]";
        for (IKimScope child : children) {
            ret += "\n" + ((KimScope) child).getStringRepresentation(offset + 3);
        }
        return ret;
    }

    public boolean isWorldviewRoot() {
        String wv = project.getDefinedWorldview();
        return wv != null && wv.equals(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IKimProject getProject() {
        return project;
    }

    public void setProject(IKimProject project) {
        this.project = project;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public List<IKimNamespace> getImported() {
        List<IKimNamespace> ret = new ArrayList<>();
        for (String s : imported) {
            IKimNamespace ns = Kim.INSTANCE.getNamespace(s);
            if (ns != null) {
                ret.add(ns);
            }
        }
        return ret;
    }

    @Override
    public Collection<String> getImportedIds() {
        return imported;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Pair<String, String>> getOwlImports() {
        return owlImports;
    }

    @Override
    public Map<String, Object> getSymbolTable() {
        return symbolTable;
    }

    public void setOwlImports(List<Pair<String, String>> owlImports) {
        this.owlImports = owlImports;
    }

    public void setSymbolTable(Map<String, Object> symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public boolean isInactive() {
        return inactive;
    }

    @Override
    public boolean isScenario() {
        return scenario;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public void setScenario(boolean scenario) {
        this.scenario = scenario;
    }

    @Override
    public String getScriptId() {
        if (!annotationsScanned) {
            scanAnnotations();
        }
        return scriptId;
    }

    @Override
    public String getTestCaseId() {
        if (!annotationsScanned) {
            scanAnnotations();
        }
        return testCaseId;
    }

    public void visit(Visitor visitor) {
        visitor.visitNamespace(this);
        for (IKimScope scope : getChildren()) {
            scope.visit(visitor);
        }
    }

    private void scanAnnotations() {
        annotationsScanned = true;
        for (IKimScope child : getChildren()) {
            if (child instanceof IKimAcknowledgement) {
                for (IKimAnnotation annotation : ((IKimAcknowledgement) child).getAnnotations()) {
                    if (annotation.getName().equals("run")) {
                        this.scriptId = annotation.getParameters().get("name", String.class);
                        if (this.scriptId == null) {
                            this.scriptId = MiscUtilities.getFileBaseName(this.resource);
                        }
                    } else if (annotation.getName().equals("test")) {
                        this.testCaseId = annotation.getParameters().get("name", String.class);
                        if (this.testCaseId == null) {
                            this.scriptId = MiscUtilities.getFileBaseName(this.resource);
                        }
                    }
                }
            }
        }
    }

    // @Override
    // public boolean isProjectKnowledge() {
    // return projectKnowledge;
    // }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public IKimLoader getLoader() {
        return loader;
    }

    public void setLoader(IKimLoader loader) {
        this.loader = loader;
    }

    @Override
    public List<IServiceCall> getExtents() {
        return extents;
    }

    @Override
    public void addChild(IKimScope child) {

        if (child instanceof IKimModel) {
            statementsByName.put(((IKimModel) child).getName(), (IKimStatement) child);
        } else if (child instanceof IKimConceptStatement || child instanceof IKimAcknowledgement) {
            addChildrenByName((IKimStatement) child);
        }
        if (child instanceof KimStatement) {
            ((KimStatement) child).setNamespace(this.name);
            if (((IKimStatement) child).isErrors()) {
                setErrors(true);
            }
            if (((IKimStatement) child).isWarnings()) {
                setWarnings(true);
            }
        }
        super.addChild(child);
    }

    private void addChildrenByName(IKimStatement child) {
        statementsByName.put(child instanceof IKimConceptStatement
                ? ((IKimConceptStatement) child).getName()
                : ((IKimAcknowledgement) child).getName(), (IKimStatement) child);
        for (IKimScope ch : child.getChildren()) {
            addChildrenByName((IKimStatement) ch);
        }
    }

    public void addImport(String string) {
        imported.add(string);
    }

    public IKimStatement getStatement(String id) {
        return statementsByName.get(id);
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public IKimConcept getDomain() {
        return domain;
    }

    @Override
    public List<Pair<String, List<String>>> getVocabularyImports() {
        return vocabularies;
    }

    @Override
    public Collection<String> getDisjointNamespaces() {
        return this.disjointNamespaces;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IKimStatement> T getStatement(String name, Class<T> cls) {
        // only "named" first-class objects are concept and model statements or defines
        for (IKimScope statement : getChildren()) {
            if (IKimConceptStatement.class.isAssignableFrom(cls) && statement instanceof IKimConceptStatement) {
                if (name.equals(((IKimConceptStatement) statement).getName())) {
                    return (T) statement;
                }
            } else if (IKimModel.class.isAssignableFrom(cls) && statement instanceof IKimModel) {
                if (name.equals(((IKimModel) statement).getName())) {
                    return (T) statement;
                }
            } else if (getSymbolTable().containsKey(name) && getSymbolTable().get(name).getClass().isAssignableFrom(cls)) {
                return (T) getSymbolTable().get(name);
            }
        }
        return null;
    }

}

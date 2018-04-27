package org.integratedmodelling.kim.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.kim.Namespace;
import org.integratedmodelling.kim.kim.OwlImport;
import org.integratedmodelling.klab.utils.Pair;

public class KimNamespace extends KimStatement implements IKimNamespace {

    private static final long          serialVersionUID = 7273137353905996543L;

    private String                     name;
    private IKimProject                project;
    private long                       timestamp;
    private List<IKimNamespace>        imported         = new ArrayList<>();
    private boolean                    isPrivate        = false;
    private boolean                    inactive        = false;
    private boolean                    scenario        = false;
    private List<Pair<String, String>> owlImports       = new ArrayList<>();
    private Map<String, Object>        symbolTable      = new HashMap<>();

    public KimNamespace(Namespace namespace, KimProject project) {
        super(namespace);
        this.name = KimProject.getNamespaceId(namespace);
        if (namespace.eResource().getURI().isFile()) {
          File file = new File(namespace.eResource().getURI().toFileString());
          if (file.exists()) {
            this.timestamp = file.lastModified();
          }
        }
        if (this.timestamp /* still */ == 0) {
          this.timestamp = namespace.eResource().getTimeStamp();
        }
        this.project = project;
        // worldview-bound anonymous namespaces are private by design.
        this.isPrivate = namespace.isPrivate() | namespace.isWorldviewBound();
        this.inactive = namespace.isInactive();
        this.scenario = namespace.isScenario();
        for (OwlImport imp : namespace.getOwlImports()) {
            owlImports.add(new Pair<>(imp.getName(), imp.getPrefix()));
        }
    }

    /**
     * Copy constructor. Shallow copy only as it's expected to build substitutes with full k.LAB semantics for
     * contextualization.
     * 
     * KEEP UPDATED WHEN FIELDS CHANGE.
     * 
     * @param namespace
     */
    protected KimNamespace(KimNamespace namespace) {
        super(namespace);
        this.name = namespace.name;
        this.project = namespace.project;
        this.timestamp = namespace.timestamp;
        this.imported = namespace.imported;
        this.isPrivate = namespace.isPrivate;
    }

    public KimNamespace(String id, File file) {
        this.name = id;
        // TODO resource URI from file
    }

    void addImport(IKimNamespace importedNamespace) {
        imported.add(importedNamespace);
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
        return imported;
    }

    public void setImported(List<IKimNamespace> imported) {
        this.imported = imported;
    }

    @Override
    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
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
}

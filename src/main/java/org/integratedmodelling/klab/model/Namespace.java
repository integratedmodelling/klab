package org.integratedmodelling.klab.model;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.model.KimNamespace;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class Namespace extends KimObject implements INamespace {

    private static final long serialVersionUID = -6469868584021658804L;
    
    private IOntology ontology;
    private boolean internal = false;

    public Namespace(IKimNamespace namespace) {
        super((KimNamespace)namespace);
    }
    
    public Namespace(String id, File file, IOntology ontology) {
        super(null);
        setStatement(new KimNamespace(id, file));
        this.ontology = ontology;
    }
    
    @Override
    public IKimNamespace getStatement() {
        return (IKimNamespace)super.getStatement();
    }

    @Override
    public long getTimeStamp() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public IConcept getDomain() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public IProject getProject() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<INamespace> getImportedNamespaces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getTrainingNamespaces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getLookupNamespaces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IScale getCoverage(IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasErrors() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasWarnings() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<ICompileNotification> getCodeAnnotations() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IOntology getOntology() {
        return ontology;
    }

    @Override
    public Map<String, Object> getSymbolTable() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isScenario() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IMetadata getResolutionCriteria() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> getDisjointNamespaces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File getLocalFile() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isPrivate() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isInactive() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isTainted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IDocumentation getDocumentation() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setInternal(boolean b) {
        this.internal = b;
    }
    
    @Override
    public boolean isInternal() {
        return this.internal;
    }
    
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IKimObject> getObjects() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IKimObject> getAllObjects() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IKimObject getObject(String id) {
        // TODO Auto-generated method stub
        return null;
    }


}

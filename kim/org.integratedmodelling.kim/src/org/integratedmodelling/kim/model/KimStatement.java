package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Issue;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IParameters;

/**
 * Captures the code aspects of a statement - define, concept, model and observation.
 * 
 * @author ferdinando.villa
 *
 */
public class KimStatement extends KimScope implements IKimStatement {

    private static final long serialVersionUID = -1426788695739147795L;

    // ACHTUNG if these are added to, ensure that the copy constructor is updated.
    protected int firstLine;
    protected int lastLine;
    protected int firstCharOffset;
    protected int lastCharOffset;
    protected int offset;
    protected String namespaceId;
    protected List<IKimAnnotation> annotations = new ArrayList<>();
    protected KimMetadata metadata;
    protected KimMetadata documentationMetadata;
    protected String resource;
    protected boolean deprecated = false;
    protected String deprecation = null;
    protected String sourceCode = null;
    protected IKimStatement parent = null;
    protected boolean errors = false;
    protected boolean warnings = false;

    EObject eObject;

    public KimStatement() {
    }

    public KimStatement(KimStatement statement) {
        this.firstCharOffset = statement.firstCharOffset;
        this.lastCharOffset = statement.lastCharOffset;
        this.firstLine = statement.firstLine;
        this.lastLine = statement.lastLine;
        this.offset = statement.offset;
        this.namespaceId = statement.namespaceId;
        this.resource = statement.resource;
        this.deprecated = statement.deprecated;
        this.deprecation = statement.deprecation;
        this.sourceCode = statement.sourceCode;
        this.errors = statement.errors;
        this.warnings = statement.warnings;
        this.metadata = statement.metadata;
    }
    
    public KimStatement(EObject statement, IKimStatement parent) {
        this.eObject = statement;
        if (statement != null) {
            setCode(statement);
        }
        this.parent = parent;
		IKimNamespace ns = findNamespace(parent);
		if (ns != null) {
			this.namespaceId = ns.getName();
		}
		if (statement != null) {
			this.uri = EcoreUtil.getURI(statement);
		}
    }

	protected static IKimNamespace findNamespace(IKimStatement statement) {

		if (statement == null) {
			return null;
		}
		if (statement instanceof IKimNamespace) {
			return (IKimNamespace) statement;
		}
		return findNamespace(statement.getParent());
	}
	
    protected void setCode(EObject statement) {
        this.eObject = statement;
        ICompositeNode node = NodeModelUtils.findActualNodeFor(statement);
        sourceCode = node.getText().trim();
        this.firstLine = node.getStartLine();
        this.lastLine = node.getEndLine();
        this.firstCharOffset = node.getOffset();
        this.lastCharOffset = node.getEndOffset();
        this.uri = EcoreUtil.getURI(statement);
        this.resource = statement.eResource() == null ? "" : statement.eResource().getURI().path();
    }

    public EObject getEObject() {
        return eObject;
    }

    @Override
    public IKimStatement getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return getLocationDescriptor();
    }

    @Override
    public String getLocationDescriptor() {
        return resource + " [" + firstLine + "-" + lastLine + "]: " + sourceCode;
    }

    @Override
    public int getFirstLine() {
        return firstLine;
    }

    @Override
    public int getLastLine() {
        return lastLine;
    }

    @Override
    public int getFirstCharOffset() {
        return firstCharOffset;
    }

    @Override
    public int getLastCharOffset() {
        return lastCharOffset;
    }

    @Override
    public String getSourceCode() {
        return sourceCode;
    }

    // reimplement in models to check for runtime conditions
    public boolean isActive() {
        return true;
    }

    @Override
    public List<IKimAnnotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<IKimAnnotation> annotations) {
        this.annotations = annotations;
    }

    @Override
    public IParameters<String> getMetadata() {
        return metadata == null ? new KimMetadata() : metadata;
    }

    public void setMetadata(KimMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public KimMetadata getDocumentationMetadata() {
        return documentationMetadata;
    }

    public void setDocumentation(KimMetadata documentation) {
        this.documentationMetadata = documentation;
    }

    @Override
    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    @Override
    public String getDeprecation() {
        return deprecation;
    }

    public void setDeprecation(String deprecation) {
        this.deprecation = deprecation;
    }

    public void setFirstLine(int firstLine) {
        this.firstLine = firstLine;
    }

    public void setLastLine(int lastLine) {
        this.lastLine = lastLine;
    }

    public void setFirstCharOffset(int firstCharOffset) {
        this.firstCharOffset = firstCharOffset;
    }

    public void setLastCharOffset(int lastCharOffset) {
        this.lastCharOffset = lastCharOffset;
    }

    @Override
    protected String getStringRepresentation(int offset) {
        return sourceCode;
    }

    @Override
    public String getResourceId() {
        return resource;
    }

    @Override
    public boolean isErrors() {
        return this.errors;
    }

    public void setErrors(boolean b) {
        this.errors = b;
    }

    @Override
    public boolean isWarnings() {
        return this.warnings;
    }

    public void setWarnings(boolean b) {
        this.warnings = b;
    }
    
    @Override
    public void addChild(IKimScope child) {
    	children.add(child);
    	if (child instanceof IKimStatement) {
    		if (((IKimStatement)child).isErrors()) {
    			errors = true;
    		}
    		if (((IKimStatement)child).isWarnings()) {
    			warnings = true;
    		}
    	}
    }
    
    @Override
    public String getNamespace() {
    	return namespaceId;
    }
    
    public void setNamespace(String namespace) {
    	this.namespaceId = namespace;
    }
    
    protected void visitMetadata(Visitor visitor) {
        
    }
    
    /**
     * Create a dummy statement uniquely to carry the line numbers for a compile notification.
     * 
     * @param issue
     * @return
     */
    public static IKimStatement createDummy(Issue issue) {
        KimStatement ret = new KimStatement(null, null);
        ret.setFirstLine(issue.getLineNumber() == null ? -1 : issue.getLineNumber());
        ret.setFirstCharOffset(issue.getOffset() == null ? -1 : issue.getOffset());
        ret.setLastCharOffset(issue.getOffset() == null ? -1 : issue.getOffset() + issue.getLength());
        return ret;
    }

}

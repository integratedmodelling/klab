package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Issue;
import org.integratedmodelling.kactors.api.IKActorsCodeStatement;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimMetadata;

public class KActorCodeStatement implements IKActorsCodeStatement {

    // ACHTUNG if these are added to, ensure that the copy constructor is updated.
    protected int firstLine;
    protected int lastLine;
    protected int firstCharOffset;
    protected int lastCharOffset;
    protected int offset;
    protected String namespaceId;
    protected List<IKimAnnotation> annotations = new ArrayList<>();
    protected String resource;
    protected boolean deprecated = false;
    protected String deprecation = null;
    protected String sourceCode = null;
    protected KActorCodeStatement parent = null;
    protected boolean errors = false;
    protected boolean warnings = false;

    EObject eObject;
	private URI uri;

    public KActorCodeStatement() {
    }

    public KActorCodeStatement(KActorCodeStatement statement) {
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
        this.uri = statement.uri;
    }
    
    public KActorCodeStatement(EObject statement, KActorCodeStatement parent) {
        this.eObject = statement;
        if (statement != null) {
            setCode(statement);
        }
        this.parent = parent;
//		IKimNamespace ns = findNamespace(parent);
//		if (ns != null) {
//			this.namespaceId = ns.getName();
//		}
		if (statement != null) {
			this.uri = EcoreUtil.getURI(statement);
		}
    }
	
    private void setCode(EObject statement) {
        this.eObject = statement;
        ICompositeNode node = NodeModelUtils.findActualNodeFor(statement);
        this.firstLine = node.getStartLine();
        this.lastLine = node.getEndLine();
        this.firstCharOffset = node.getOffset();
        this.lastCharOffset = node.getEndOffset();
        this.uri = EcoreUtil.getURI(statement);
        this.resource = statement.eResource() == null ? "" : statement.eResource().getURI().path();
        sourceCode = node.getText().trim();
	}
    
    EObject getEStatement() {
    	return this.eObject;
    }

	/**
     * Create a dummy statement uniquely to carry the line numbers for a compile notification.
     * 
     * @param issue
     * @return
     */
    public static KActorCodeStatement createDummy(Issue issue) {
        KActorCodeStatement ret = new KActorCodeStatement(null, null);
        ret.setFirstLine(issue.getLineNumber() == null ? -1 : issue.getLineNumber());
        ret.setFirstCharOffset(issue.getOffset() == null ? -1 : issue.getOffset());
        ret.setLastCharOffset(issue.getOffset() == null ? -1 : issue.getOffset() + issue.getLength());
        return ret;
    }

	private void setLastCharOffset(int i) {
		// TODO Auto-generated method stub
		
	}

	private void setFirstCharOffset(int i) {
		// TODO Auto-generated method stub
		
	}

	private void setFirstLine(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSourceCode() {
		return sourceCode;
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
	public List<IKimAnnotation> getAnnotations() {
		return annotations;
	}

	@Override
	public String getDeprecation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDeprecated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWarnings() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IKimMetadata getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}


}

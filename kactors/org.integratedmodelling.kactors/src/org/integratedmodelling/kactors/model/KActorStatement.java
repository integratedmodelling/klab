package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.validation.Issue;
import org.integratedmodelling.kactors.api.IKActorStatement;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.utils.Pair;

public class KActorStatement implements IKActorStatement {

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
    protected IKimStatement parent = null;
    protected boolean errors = false;
    protected boolean warnings = false;

    EObject eObject;
	private URI uri;

    public KActorStatement() {
    }

    public KActorStatement(KActorStatement statement) {
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
    
    public KActorStatement(EObject statement, IKimStatement parent) {
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
		// TODO Auto-generated method stub
		
	}

	/**
     * Create a dummy statement uniquely to carry the line numbers for a compile notification.
     * 
     * @param issue
     * @return
     */
    public static KActorStatement createDummy(Issue issue) {
        KActorStatement ret = new KActorStatement(null, null);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFirstLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLastLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFirstCharOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLastCharOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<IKimAnnotation> getAnnotations() {
		// TODO Auto-generated method stub
		return null;
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


}

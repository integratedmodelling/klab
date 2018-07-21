package org.integratedmodelling.klab.ide.navigator.model;

import java.net.URI;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimMetadata;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.model.KimStatement;

/**
 * Root class for tree elements that are linked to a k.IM statement.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class EKimObject extends ENavigatorItem implements IKimStatement {

	private static final long serialVersionUID = -3445237513834410884L;

	IKimStatement delegate_;

	EKimObject(String id, IKimStatement statement, ENavigatorItem parent) {
	    super(id, parent);
		this.delegate_ = statement;
	}

	public int getFirstLine() {
		return delegate_.getFirstLine();
	}

	public List<IKimScope> getChildren() {
		return delegate_.getChildren();
	}

	public String getLocationDescriptor() {
		return delegate_.getLocationDescriptor();
	}

	public int getLastLine() {
		return delegate_.getLastLine();
	}

	public int getFirstCharOffset() {
		return delegate_.getFirstCharOffset();
	}

	public int getLastCharOffset() {
		return delegate_.getLastCharOffset();
	}

	public List<IKimAnnotation> getAnnotations() {
		return delegate_.getAnnotations();
	}

	public IKimMetadata getMetadata() {
		return delegate_.getMetadata();
	}

	public IKimMetadata getDocumentationMetadata() {
		return delegate_.getDocumentationMetadata();
	}

	public boolean isDeprecated() {
		return delegate_.isDeprecated();
	}

	public String getDeprecation() {
		return delegate_.getDeprecation();
	}

	public String getSourceCode() {
		return delegate_.getSourceCode();
	}

	public IKimStatement getParent() {
		return delegate_.getParent();
	}

	@Override
	public URI getURI() {
		return delegate_.getURI();
	}
	
	@Override
	public ENavigatorItem getEParent() {
		return parent;
	}

    public EObject getEObject() {
        return delegate_ == null ? null : ((KimStatement)delegate_).getEObject();
    }


}

package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
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

	protected IKimStatement getKimStatement() {
		return this.delegate_;
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
		return delegate_ == null ? null : ((KimStatement) delegate_).getEObject();
	}

	@Override
	public String getResourceId() {
		return delegate_ == null ? null : delegate_.getResourceId();
	}

	public IFile getIFile() {

		org.eclipse.emf.common.util.URI uri = ((KimStatement) delegate_).getEObject().eResource().getURI();
		if (uri.toString().startsWith("platform:/resource/")) {
			String uriPath = uri.toString().substring("platform:/resource/".length());
			IResource ret = ResourcesPlugin.getWorkspace().getRoot().findMember(uriPath);
			if (ret instanceof IFile) {
				return (IFile) ret;
			}
		} else if (uri.toString().startsWith("file:/")) {
			try {
				IFile[] ret = ResourcesPlugin.getWorkspace().getRoot()
						.findFilesForLocationURI(new java.net.URI(uri.toString()));
				if (ret != null && ret.length > 0) {
					return ret[0];
				}
			} catch (URISyntaxException e) {
			}
		}
		return null;
	}

	public File getPhysicalFile() {
		IFile ifile = getIFile();
		return ifile == null ? null : ifile.getLocation().toFile();
	}

	public boolean isErrors() {
		return delegate_.isErrors();
	}

	public boolean isWarnings() {
		return delegate_.isWarnings();
	}

}

package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimAcknowledgement;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.model.KimStatement;
import org.integratedmodelling.klab.api.IStatement;

/**
 * Root class for tree elements that are linked to a k.IM statement.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class EKimObject extends ENavigatorItem implements IKimStatement {

	private static final long serialVersionUID = -3445237513834410884L;

	IStatement delegate_;

	EKimObject(String id, IStatement statement, ENavigatorItem parent) {
		super(id, parent);
		this.delegate_ = statement;
	}

	public IKimStatement getKimStatement() {
		return this.delegate_ instanceof IKimStatement ? (IKimStatement) this.delegate_ : null;
	}

	public int getFirstLine() {
		return delegate_.getFirstLine();
	}

	public List<IKimScope> getChildren() {
		return this.delegate_ instanceof IKimStatement ? ((IKimStatement) delegate_).getChildren()
				: new ArrayList<>() /* TODO */;
	}

	public String getLocationDescriptor() {
		return this.delegate_ instanceof IKimStatement ? ((IKimStatement) delegate_).getLocationDescriptor() : "DIO";
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

	public IParameters<String> getMetadata() {
		return this.delegate_ instanceof IKimStatement ? ((IKimStatement) delegate_).getMetadata() : null;
	}

	public IParameters<String> getDocumentationMetadata() {
		return this.delegate_ instanceof IKimStatement ? ((IKimStatement) delegate_).getDocumentationMetadata() : null;
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
		return this.delegate_ instanceof IKimStatement ? ((IKimStatement) delegate_).getParent() : null;
	}

	@Override
	public String getURI() {
		return this.delegate_ instanceof IKimStatement ? ((IKimStatement) delegate_).getURI() : "POPOA";
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
		return delegate_ == null ? null
				: (this.delegate_ instanceof IKimStatement ? ((IKimStatement) delegate_).getResourceId() : "ZAMPARON");
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

	public void visit(Visitor visitor) {
		if (this.delegate_ instanceof IKimStatement) {
			((IKimStatement) delegate_).visit(visitor);
		}
	}

	// create correspondent object without parent. Method in KimData also creates
	// parents.
	public static ENavigatorItem create(Object focus) {
		if (focus instanceof IKimConceptStatement) {
			return new EConcept(
					((IKimConceptStatement) focus).getNamespace() + ":" + ((IKimConceptStatement) focus).getName(),
					((IKimConceptStatement) focus), null, null);
		} else if (focus instanceof IKimModel) {
			return new EModel(((IKimModel) focus).getNamespace() + "." + ((IKimModel) focus).getName(),
					((IKimModel) focus), null);
		} else if (focus instanceof IKimAcknowledgement) {
			return new EAcknowledgement(((IKimAcknowledgement) focus).getNamespace() + "." + ((IKimAcknowledgement) focus).getName(),
					((IKimAcknowledgement) focus), null, null);
		} else if (focus instanceof IKimNamespace) {
			return new ENamespace((IKimNamespace) focus, null);
		} else if (focus instanceof IKimProject) {
			return new EProject((IKimProject) focus, null);
		}
		return null;
	}

}

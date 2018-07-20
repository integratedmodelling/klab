package org.integratedmodelling.klab.ide.navigator.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimMetadata;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.ide.utils.Eclipse;

/**
 * Root class for tree elements that are linked to a k.IM statement.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class EKimObject extends ENavigatorItem implements IKimStatement {

	private static final long serialVersionUID = -3445237513834410884L;

	IKimStatement delegate_;

	EKimObject(IKimStatement statement) {
		this.delegate_ = statement;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {

		// if (!(this instanceof ENamespace) && IMarker.class.isAssignableFrom(adapter))
		// {
		// // return (T)getNamespaceIFile(((ENamespace)this).delegate);
		// }

		if (IContainer.class == adapter) {
			// ehm.
		} else if (IProject.class.isAssignableFrom(adapter)) {
			// boh
		} else if (IResource.class.isAssignableFrom(adapter)) {
			if (this instanceof ENamespace) {
				return (T) Eclipse.INSTANCE.getNamespaceIFile(this);
			} else {
			}
		}

		// System.out.println("TRYING to adapt " + this + " to " +
		// adapter.getCanonicalName());

		return null;
	}

	public int getFirstLine() {
		return delegate_.getFirstLine();
	}

	public List<EKimObject> getEChildren() {
		List<EKimObject> ret = new ArrayList<>();
		for (IKimScope child : delegate_.getChildren()) {
			if (child instanceof IKimModel) {
				ret.add(new EModel((IKimModel) child));
			} else if (child instanceof IKimConceptStatement) {
				ret.add(new EConcept((IKimConceptStatement) child));
			} else if (child instanceof IKimObserver) {
				ret.add(new EObserver((IKimObserver) child));
			}
		}
		return ret;
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

	public static List<ENamespace> adapt(List<IKimNamespace> namespaces) {
		List<ENamespace> ret = new ArrayList<>();
		for (IKimNamespace ns : namespaces) {
			ret.add(new ENamespace(ns));
		}
		return ret;
	}

	@Override
	public EKimObject getEParent() {

		IKimStatement parent = delegate_.getParent();
		if (parent instanceof IKimConceptStatement) {
			return new EConcept((IKimConceptStatement) parent);
		}
		if (parent instanceof IKimNamespace) {
			return new ENamespace((IKimNamespace) parent);
		}
		if (parent instanceof IKimModel) {
			return new EModel((IKimModel) parent);
		}
		if (parent instanceof IKimObserver) {
			return new EObserver((IKimObserver) parent);
		}
		return null;
	}
//
//	@Override
//	public int hashCode() {
//		return (delegate_ == null || delegate_.getURI() == null) ? super.hashCode()
//				: delegate_.getURI().toString().hashCode();
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		return this == o || (delegate_ != null && o instanceof EKimObject
//				&& delegate_.getURI().toString().equals(((EKimObject) o).getURI().toString()));
//	}

}

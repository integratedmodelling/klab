package org.integratedmodelling.klab.ide.navigator.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimMetadata;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;

public abstract class EKimObject implements IAdaptable {

	IKimStatement delegate_;

	EKimObject(IKimStatement statement) {
		this.delegate_ = statement;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		System.out.println("this fuck is trying to adapt me, a " + getClass().getCanonicalName() + ", to a "
				+ adapter.getCanonicalName());
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

	public static List<ENamespace> adapt(List<IKimNamespace> namespaces) {
		List<ENamespace> ret = new ArrayList<>();
		for (IKimNamespace ns : namespaces) {
			ret.add(new ENamespace(ns));
		}
		return ret;
	}

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

}

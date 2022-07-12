package org.integratedmodelling.klab.ide.navigator.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimBehavior;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimAcknowledgement;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.model.Kim;

public class EAcknowledgement extends EKimObject implements IKimAcknowledgement {

	private static final long serialVersionUID = -5755690442793814545L;

	IKimAcknowledgement delegate;
	ENamespace namespace;

	public EAcknowledgement(String id, IKimAcknowledgement statement, ENavigatorItem parent, ENamespace namespace) {
		super(id, statement, parent);
		this.delegate = statement;
		this.namespace = namespace;
	}

	public IKimConcept.Type getCoreObservableType() {
		IKimConcept main = delegate.getObservable() != null ? delegate.getObservable().getMain() : null;
		Set<IKimConcept.Type> type = main == null
				? ((delegate.getObservable() != null) ? EnumSet.of(IKimConcept.Type.QUALITY) : null)
				: main.getType();
		return type == null ? null : Kim.INSTANCE.getFundamentalType(type);
	}

	public String getName() {
		return delegate.getName();
	}

	public IKimBehavior getBehavior() {
		return delegate.getBehavior();
	}

	public IKimObservable getObservable() {
		return delegate.getObservable();
	}

	public List<IKimAnnotation> getAnnotations() {
		return delegate.getAnnotations();
	}

	public List<IKimObservable> getStates() {
		return delegate.getStates();
	}

	@Override
	public String getDocstring() {
		return delegate.getDocstring();
	}

	@Override
	public ENavigatorItem[] getEChildren() {
		List<ENavigatorItem> ret = new ArrayList<>(delegate.getChildren().size());
		for (IKimScope child : delegate.getChildren()) {
			ret.add(new EAcknowledgement(namespace.getName() + "." + ((IKimAcknowledgement) child).getName(), (IKimAcknowledgement) child,
					this, namespace));
		}
		return ret.toArray(new ENavigatorItem[ret.size()]);
	}

	@Override
	public boolean hasEChildren() {
		return delegate.getChildren().size() > 0;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public String getUrn() {
		return delegate.getUrn();
	}

	@Override
	public String getNamespace() {
		return delegate.getNamespace();
	}

}

package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimLoader;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimAcknowledgement;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IKimSymbolDefinition;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.utils.Pair;

public class ENamespace extends EKimObject implements IKimNamespace {

	private static final long serialVersionUID = 7664920082549474716L;

	IKimNamespace delegate;

    ENamespace(IKimNamespace statement, ENavigatorItem parent) {
		super(statement.getName(), statement, parent);
		this.delegate = statement;
	}

	public String getName() {
		return delegate.getName();
	}

	public Scope getScope() {
		return delegate.getScope();
	}

	public List<IKimNamespace> getImported() {
		return delegate.getImported();
	}

	public long getTimestamp() {
		return delegate.getTimestamp();
	}

	public IKimProject getProject() {
		return delegate.getProject();
	}

	public List<Pair<String, String>> getOwlImports() {
		return delegate.getOwlImports();
	}

	public Map<String, Object> getSymbolTable() {
		return delegate.getSymbolTable();
	}

	public boolean isInactive() {
		return delegate.isInactive();
	}

	public boolean isScenario() {
		return delegate.isScenario();
	}

	@Override
	public boolean isWorldviewBound() {
		return delegate.isWorldviewBound();
	}
	
	@Override
	public ENavigatorItem[] getEChildren() {
		List<ENavigatorItem> ret = new ArrayList<>(delegate.getChildren().size());
		for (IKimScope child : delegate.getChildren()) {
			if (child instanceof IKimConceptStatement) {
				ret.add(new EConcept(delegate.getName() + ":" + ((IKimConceptStatement) child).getName(),
						(IKimConceptStatement) child, this, this));
			} else if (child instanceof IKimModel) {
				ret.add(new EModel(delegate.getName() + "." + ((IKimModel) child).getName(), (IKimModel) child, this));
			} else if (child instanceof IKimAcknowledgement) {
				ret.add(new EAcknowledgement(delegate.getName() + "." + ((IKimAcknowledgement) child).getName(), (IKimAcknowledgement) child,
						this, this));
			} else if (child instanceof IKimSymbolDefinition) {
				ret.add(new EDefinition((IKimSymbolDefinition) child, this));
			}
		}
		return ret.toArray(new ENavigatorItem[ret.size()]);
	}

	@Override
	public boolean hasEChildren() {
		return delegate.getChildren().size() > 0;
	}

	@Override
	public List<IKimStatement> getAllStatements() {
		return delegate.getAllStatements();
	}

	@Override
	public String getScriptId() {
		return delegate.getScriptId();
	}

	@Override
	public String getTestCaseId() {
		return delegate.getTestCaseId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		/*
		 * The hierarchy is either IContainer or IFile, but if I put IFile in the last
		 * condition and remove the others, the namespace isn't seen as a file. Leave
		 * like this although it looks weird.
		 */
		if (IContainer.class == adapter) {
			// ehm.
		} else if (IProject.class.isAssignableFrom(adapter)) {
			// boh
		} else if (IResource.class.isAssignableFrom(adapter)) {
			return (T) Eclipse.INSTANCE.getNamespaceIFile(this);
		}
		return null;
	}

	@Override
	public File getFile() {
		return delegate.getFile();
	}

	@Override
	public String getResourceId() {
		return delegate.getResourceId();
	}

	public void visit(Visitor visitor) {
		delegate.visit(visitor);
	}

	@Override
	public Collection<String> getImportedNamespaceIds(boolean scanUsages) {
		return delegate.getImportedNamespaceIds(scanUsages);
	}

	@Override
	public IKimLoader getLoader() {
		return delegate.getLoader();
	}

	@Override
	public List<IServiceCall> getExtents() {
		return delegate.getExtents();
	}

	@Override
	public String getNamespace() {
		return delegate.getNamespace();
	}

	@Override
	public Collection<String> getImportedIds() {
		return delegate.getImportedIds();
	}

	@Override
	public IKimConcept getDomain() {
		return delegate.getDomain();
	}

	@Override
	public List<Pair<String, List<String>>> getVocabularyImports() {
		return delegate.getVocabularyImports();
	}

    @Override
    public Collection<String> getDisjointNamespaces() {
        return delegate.getDisjointNamespaces();
    }

    @Override
    public <T extends IKimStatement> T getStatement(String name, Class<T> cls) {
        return delegate.getStatement(name, cls);
    }
}

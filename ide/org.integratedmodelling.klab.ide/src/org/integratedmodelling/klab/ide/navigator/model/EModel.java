package org.integratedmodelling.klab.ide.navigator.model;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimBehavior;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentable;

public class EModel extends EKimObject implements IKimModel, EDocumentable {

    private static final long serialVersionUID = -5791991801230456655L;

    IKimModel delegate;

    public EModel(String id, IKimModel statement, ENavigatorItem parent) {
        super(id, statement, parent);
        this.delegate = statement;
    }

    public IKimConcept.Type getCoreObservableType() {
    	
    	if (!isSemantic()) {
    		return IKimConcept.Type.QUALITY;
    	}
    	
        IKimConcept main = delegate.getObservables().size() > 0 ? delegate.getObservables().get(0).getMain() : null;
        Set<IKimConcept.Type> type = main == null
                ? ((delegate.getObservables().size() > 0
                        && delegate.getObservables().get(0).getNonSemanticType() != null)
                                ? EnumSet.of(IKimConcept.Type.QUALITY)
                                : null)
                : main.getType();
        return type == null ? null : Kim.INSTANCE.getFundamentalType(type);
    }

    public Optional<IKimConcept> getReinterpretingRole() {
        return delegate.getReinterpretingRole();
    }

    public IKimBehavior getBehavior() {
        return delegate.getBehavior();
    }

    public List<IKimObservable> getDependencies() {
        return delegate.getDependencies();
    }

    public List<IKimObservable> getObservables() {
        return delegate.getObservables();
    }

    public Scope getScope() {
        return delegate.getScope();
    }

    public Type getType() {
        return delegate.getType();
    }

    public List<String> getResourceUrns() {
        return delegate.getResourceUrns();
    }
    
    public boolean isLearningModel() {
        return delegate.isLearningModel();
    }

    public boolean isInterpreter() {
        return delegate.isInterpreter();
    }

    public boolean isAbstract() {
        return delegate.isAbstract();
    }

    public boolean isInactive() {
        return delegate.isInactive();
    }

    public boolean isInstantiator() {
        return delegate.isInstantiator();
    }

    public String getName() {
        return delegate.getName();
    }

    public Optional<Object> getInlineValue() {
        return delegate.getInlineValue();
    }

    public Optional<IServiceCall> getResourceFunction() {
        return delegate.getResourceFunction();
    }

    public List<IContextualizable> getContextualization() {
        return delegate.getContextualization();
    }

    @Override
    public String getDocstring() {
        return delegate.getDocstring();
    }

    @Override
    public ENavigatorItem[] getEChildren() {
        ENavigatorItem[] ret = new ENavigatorItem[delegate.getObservables().size() + delegate.getDependencies().size()];
        int i = 0;
        for (IKimObservable observable : delegate.getObservables()) {
        	ret[i++] = new EObservable(processDefinition(observable), observable, this);
        }
        for (IKimObservable observable : delegate.getDependencies()) {
        	ret[i++] = new EDependency(processDefinition(observable), observable, this);
        }
        return ret;
    }

    private String processDefinition(IKimObservable observable) {
		String ret = observable.getDefinition();
//		if (!ret.contains(" named ")) {
//			ret += " [" + observable.getCodeName() + "]";
//		}
		return ret;
	}

	@Override
    public boolean hasEChildren() {
        return delegate.getObservables().size() + delegate.getDependencies().size() > 0;
    }

    @Override
    public <T> T getAdapter(Class<T> adapter) {
        return null;
    }

	@Override
	public boolean isSemantic() {
		return delegate.isSemantic();
	}
	
	@Override
	public boolean isDocumented() {
		
		for (IKimAnnotation annotation : delegate_.getAnnotations()) {
			// TODO parameterize the annotations recognized
			if (annotation.getName().equals("documented")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDocId() {
		for (IKimAnnotation annotation : delegate_.getAnnotations()) {
			// TODO parameterize the annotations recognized
			if (annotation.getName().equals("documented")) {
				return annotation.getParameters().get(IKimAnnotation.DEFAULT_PARAMETER_NAME, String.class);
			}
		}
		return null;
	}

	@Override
	public String getNamespace() {
		return delegate.getNamespace();
	}

	@Override
	public boolean isResourceMerger() {
		return delegate.isResourceMerger();
	}

}

package org.integratedmodelling.klab.ide.navigator.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimRestriction;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.klab.utils.Pair;

public class EConcept extends EKimObject implements IKimConceptStatement {

	private static final long serialVersionUID = 7550076726614724351L;

	IKimConceptStatement delegate;
	ENamespace namespace;

	public EConcept(String id, IKimConceptStatement statement, ENavigatorItem parent, ENamespace namespace) {
		super(id, statement, parent);
		this.delegate = statement;
		this.namespace = namespace;
	}

	public EnumSet<Type> getType() {
		return delegate.getType();
	}

	public String getUpperConceptDefined() {
		return delegate.getUpperConceptDefined();
	}

	public String getAuthority() {
		return delegate.getAuthority();
	}

	public String getAuthorityTerm() {
		return delegate.getAuthorityTerm();
	}

	public String getAuthorityDefined() {
		return delegate.getAuthorityDefined();
	}

	public String getAuthorityRequired() {
		return delegate.getAuthorityRequired();
	}

	public List<IKimConcept> getQualitiesAffected() {
		return delegate.getQualitiesAffected();
	}

	public List<IKimConcept> getObservablesCreated() {
		return delegate.getObservablesCreated();
	}

//    public List<IKimConcept> getConstituentParticipants() {
//        return delegate.getConstituentParticipants();
//    }
//
//    public List<IKimConcept> getPartParticipants() {
//        return delegate.getPartParticipants();
//    }

	public List<IKimConcept> getTraitsConferred() {
		return delegate.getTraitsConferred();
	}

	public List<IKimConcept> getTraitsInherited() {
		return delegate.getTraitsInherited();
	}

	public List<IKimConcept> getRequiredExtents() {
		return delegate.getRequiredExtents();
	}

	public List<IKimConcept> getRequiredRealms() {
		return delegate.getRequiredRealms();
	}

	public List<IKimConcept> getRequiredAttributes() {
		return delegate.getRequiredAttributes();
	}

	public List<IKimConcept> getRequiredIdentities() {
		return delegate.getRequiredIdentities();
	}

//    public List<IKimConcept> getExposedTraits() {
//        return delegate.getExposedTraits();
//    }

	public List<IKimRestriction> getRestrictions() {
		return delegate.getRestrictions();
	}

	public boolean isAlias() {
		return delegate.isAlias();
	}

	public boolean isAbstract() {
		return delegate.isAbstract();
	}

	public String getNamespace() {
		return delegate.getNamespace();
	}

	public String getName() {
		return delegate.getName();
	}

	public boolean isMacro() {
		return delegate.isMacro();
	}

	public List<Pair<IKimConcept, DescriptionType>> getObservablesDescribed() {
		return delegate.getObservablesDescribed();
	}

	public void visit(Visitor visitor) {
		delegate.visit(visitor);
	}

//    public List<IKimObservable> getTraitsExposed() {
//        return delegate.getTraitsExposed();
//    }
//
//    public boolean isDefiningExposedTraits() {
//        return delegate.isDefiningExposedTraits();
//    }
//
//    public List<IKimConcept> getConfigurationParticipants() {
//        return delegate.getConfigurationParticipants();
//    }

	public List<ApplicableConcept> getSubjectsLinked() {
		return delegate.getSubjectsLinked();
	}

	public List<ApplicableConcept> getAppliesTo() {
		return delegate.getAppliesTo();
	}

	@Override
	public String getDocstring() {
		return delegate.getDocstring();
	}

	@Override
	public ENavigatorItem[] getEChildren() {
		List<ENavigatorItem> ret = new ArrayList<>(delegate.getChildren().size());
		for (IKimScope child : delegate.getChildren()) {
			ret.add(new EConcept(namespace.getName() + ":" + ((IKimConceptStatement) child).getName(),
					(IKimConceptStatement) child, this, namespace));
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
	public List<IKimConcept> getEmergenceTriggers() {
		return delegate.getEmergenceTriggers();
	}

    @Override
    public List<ParentConcept> getParents() {
        return delegate.getParents();
    }

}

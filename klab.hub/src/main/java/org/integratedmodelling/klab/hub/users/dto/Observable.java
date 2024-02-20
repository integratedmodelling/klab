package org.integratedmodelling.klab.hub.users.dto;


import javax.validation.constraints.NotBlank;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.rest.ObservableReference;
import org.springframework.data.annotation.TypeAlias;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@TypeAlias("Observable")
public class Observable {
	
	@NotBlank
	private String observable;
	
	private String label;
	
	@Enumerated(EnumType.STRING)
	@NotBlank
	private IKimConcept.Type semantics;
	
	private String description;
	
	private boolean separator;
	
	@Enumerated(EnumType.STRING)
	private ObservableState state;
	
	private String extendedDescription;

	public String getObservable() {
		return observable;
	}

	public void setObservable(String observable) {
		this.observable = observable;
	}

	public IKimConcept.Type getSemantics() {
		return semantics;
	}

	public void setSemantics(IKimConcept.Type semantics) {
		this.semantics = semantics;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSeparator() {
		return separator;
	}

	public void setSeparator(boolean separator) {
		this.separator = separator;
	}

	public String getExtendedDescription() {
		return extendedDescription;
	}

	public void setExtendedDescription(String extendedDescription) {
		this.extendedDescription = extendedDescription;
	}

	public ObservableReference getObservableReference() {
		ObservableReference obs = new ObservableReference();
		obs.setLabel(this.label);
		obs.setObservable(this.observable);
		obs.setSemantics(this.semantics);
		obs.setExtendedDescription(this.extendedDescription);
		obs.setDescription(this.description);
		obs.setSeparator(this.separator);
		if(this.state != null) {
			obs.setState(this.state.toString());
		}
		return obs;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ObservableState getState() {
		return state;
	}

	public void setState(ObservableState state) {
		this.state = state;
	}
}

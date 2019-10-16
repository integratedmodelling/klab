package org.integratedmodelling.klab.rest;

import org.integratedmodelling.kim.api.IKimConcept;

/**
 * A list of these is sent along with group identities to suggest a default set
 * of observables of likely interest. These should then be incorporated in user
 * history according to usage. A 'separator' type (holding only a description)
 * is provided to organize the matches in categories if wanted.
 * 
 * @author ferdinando.villa
 *
 */
public class ObservableReference {
	private String observable;
	private String label;
	private String description;
	private IKimConcept.Type semantics;
	private boolean separator;
	private String state;
	private String extendedDescription;

	public String getObservable() {
		return observable;
	}

	public void setObservable(String urn) {
		this.observable = urn;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public IKimConcept.Type getSemantics() {
		return semantics;
	}

	public void setSemantics(IKimConcept.Type semantics) {
		this.semantics = semantics;
	}

	public boolean isSeparator() {
		return separator;
	}

	public void setSeparator(boolean separator) {
		this.separator = separator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getExtendedDescription() {
		return extendedDescription;
	}

	public void setExtendedDescription(String extendedDescription) {
		this.extendedDescription = extendedDescription;
	}

}

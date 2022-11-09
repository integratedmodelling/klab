package org.integratedmodelling.mca.api;

import java.util.List;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface IStakeholder {

	IObservable getObservable();
	
	ISubject getSubject();

	/**
	 * Return all alternatives retained, making sure everything is initialized.
	 * 
	 * @return alternatives at transition HMM not sure
	 */
	List<IAlternative> getAlternatives(ILocator locator);

	void rankAlternatives(ILocator transition) throws KlabException;

	boolean canValue(IAlternative alternative);

	/**
	 * If this is true, there is only one (implicit) stakeholder and the view is an
	 * objective one, i.e. the stakeholder reflects the perspective of the running
	 * identity. Otherwise the stakeholder reflects a specific perspective and there
	 * are normally more than one.
	 * 
	 * @return
	 */
	boolean isObjective();
	
	String getName();

	double getWeight(ICriterion criterion);


}

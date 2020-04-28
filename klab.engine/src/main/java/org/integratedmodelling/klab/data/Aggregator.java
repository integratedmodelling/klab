package org.integratedmodelling.klab.data;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.utils.Triple;

/**
 * Class facilitating "intelligent" aggregation across multiple scales and units
 * of measurement, handling the translation of context in case of extensive
 * units with differently scaled inputs and outputs.
 * 
 * @author Ferd
 *
 */
public class Aggregator {

	List<Triple<Object, IObservable, ILocator>> addenda = new ArrayList<>();
	IObservable observable;

	public Aggregator(IObservable destinationObservable) {
		this.observable = destinationObservable;
	}

	public void add(Object value, IObservable observable, ILocator locator) {
		addenda.add(new Triple<>(value, observable, locator));
	}

	public Object get(ILocator locator) {
		return addenda.size() == 0 ? null : addenda.get(0).getFirst();
	}

	public Object getAndReset(ILocator locator) {
		Object ret = get(locator);
		addenda.clear();
		return ret;
	}

}

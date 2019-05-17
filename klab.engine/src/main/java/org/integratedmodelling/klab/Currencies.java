package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.services.ICurrencyService;
import org.integratedmodelling.klab.common.mediation.Currency;

public enum Currencies implements ICurrencyService {

	INSTANCE;

	private Currencies() {
		Services.INSTANCE.registerService(this, ICurrencyService.class);
	}

	@Override
	public Currency getCurrency(String string) {
		return Currency.create(string);
	}

}

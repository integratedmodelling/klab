package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.services.ICurrencyService;
import org.integratedmodelling.klab.common.mediation.Currency;

public enum Currencies implements ICurrencyService {

    INSTANCE;
    
    Currencies() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public Currency getCurrency(String string) {
      return null;
    }
    
}

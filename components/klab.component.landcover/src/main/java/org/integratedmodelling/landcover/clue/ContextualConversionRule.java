package org.integratedmodelling.landcover.clue;

import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

import nl.wur.iclue.parameter.conversion.Rule;

public class ContextualConversionRule extends Rule {

	IRuntimeScope context;
	IExpression expression;
	
	@Override
	public boolean canConvert(int currentAge) {
		// TODO Auto-generated method stub
		return false;
	}

}

package org.integratedmodelling.kactors.model;

import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Utils;

public class KActorsQuantity {

	private String unit;
	private String currency;
	private Number value;

	public KActorsQuantity() {
	}

	public Number getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setValue(Number value) {
		this.value = value;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public static KActorsQuantity parse(String string) {

		if (string != null && !string.isEmpty()) {
			String[] ss = string.split("\\s+");
			Object value = Utils.asPOD(ss[0]);
			if (value instanceof Number && ss.length == 2) {
				KActorsQuantity ret = new KActorsQuantity();
				ret.value = (Number) value;
				ret.unit = ss[1];
				return ret;
			}
		}
		
		throw new KlabValidationException("wrong string input for quantity: " + string);
	}

}

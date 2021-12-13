package org.integratedmodelling.kim.model;

import org.integratedmodelling.kim.api.IKimQuantity;
import org.integratedmodelling.kim.kim.Quantity;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Utils;

public class KimQuantity extends KimStatement implements IKimQuantity {

	private static final long serialVersionUID = 5553794797550026758L;

	private String unit;
	private String currency;
	private Number value;

	public KimQuantity(Quantity quantity) {
		super(quantity, null);
	}
	
	public KimQuantity() {
	}

	@Override
	public Number getValue() {
		return value;
	}

	@Override
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

	public static IKimQuantity parse(String string) {

		if (string != null && !string.isEmpty()) {
			int pindex = string.lastIndexOf('.');
			String[] ss = pindex > 0 ? new String[] { string.substring(0, pindex), string.substring(pindex + 1) }
					: new String[] { string };
			Object value = Utils.asPOD(ss[0]);
			if (value instanceof Number && ss.length == 2) {
				KimQuantity ret = new KimQuantity();
				ret.value = (Number) value;
				ret.unit = ss[1];
				return ret;
			}
		}

		throw new KlabValidationException("wrong string input for quantity: " + string);
	}

}

package org.integratedmodelling.kim.model;

import org.integratedmodelling.kim.api.IKimQuantity;
import org.integratedmodelling.kim.kim.Quantity;

public class KimQuantity extends KimStatement implements IKimQuantity {

	private static final long serialVersionUID = 5553794797550026758L;

	private String unit;
	private String currency;
	private Number value;
	
	public KimQuantity(Quantity quantity) {
		super(quantity, null);
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
	

}

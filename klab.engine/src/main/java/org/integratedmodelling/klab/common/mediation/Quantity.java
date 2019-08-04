package org.integratedmodelling.klab.common.mediation;

import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.utils.Range;

/**
 * Mimics the equivalent in jscience, adding currency and range mediators. Used to
 * translate the new nnn.unit expressions in k.IM.
 * 
 * @author Ferd
 *
 */
public class Quantity {

	Number value;
	IUnit unit;
	ICurrency currency;
	Range range;
	
	private Quantity() {}
	
	public static Quantity create(Number value, IUnit unit) {
		Quantity ret = new Quantity();
		ret.value = value;
		ret.unit = unit;
		return ret;
	}

	public static Quantity create(Number value, ICurrency currency) {
		Quantity ret = new Quantity();
		ret.value = value;
		ret.currency = currency;
		return ret;
	}

	public static Quantity create(Number value, Range range) {
		Quantity ret = new Quantity();
		ret.value = value;
		ret.range = range;
		return ret;
	}
	
	public static Quantity create(Quantity value, IUnit unit) {
		Quantity ret = new Quantity();
		ret.value = value.in(unit);
		ret.unit = unit;
		return ret;
	}

	public static Quantity create(Quantity value, ICurrency currency) {
		Quantity ret = new Quantity();
		ret.value = value.in(currency);
		ret.currency = currency;
		return ret;
	}

	public static Quantity create(Quantity value, Range range) {
		Quantity ret = new Quantity();
		ret.value = value.in(range);
		ret.range = range;
		return ret;
	}

	public double in(IUnit unit) {
		return unit.convert(value.doubleValue(), this.unit).doubleValue();
	}

	public double in(ICurrency currency) {
		return currency.convert(value.doubleValue(), this.currency).doubleValue();
	}

	public double in(Range range) {
		return range.convert(value.doubleValue(), this.range).doubleValue();
	}

}

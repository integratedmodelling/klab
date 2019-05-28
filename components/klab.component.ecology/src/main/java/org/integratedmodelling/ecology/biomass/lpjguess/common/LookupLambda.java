//package org.integratedmodelling.ecology.biomass.lpjguess.common;
//
//import org.integratedmodelling.ecology.biomass.lpjguess.drivers.LPJ;
//
//public class LookupLambda {
//
//	/*
//	 * TODO check - is this really necessary?
//	 */
//	public static final int LOOKUP_LAMBDA_MAXITEM = 130;
//
//	static class Item {
//
//		public double adtmm;
//		public double agd;
//		public double rd;
//		public int year = -1;
//		public int day;
//	}
//
//	private Item[] data = new Item[LOOKUP_LAMBDA_MAXITEM];
//	private int position;
//
//	public final void newsearch() {
//		position = 0;
//	}
//
//	public LookupLambda() {
//		for (int i = 0; i < LOOKUP_LAMBDA_MAXITEM; i++) {
//			data[i] = new Item();
//		}
//	}
//
//	public class Res {
//		public boolean ret;
//		public double agd;
//		public double adtmm;
//		public double rd;
//
//		Res(boolean r, double agd, double adtmm, double rd) {
//			this.ret = r;
//			this.agd = agd;
//			this.adtmm = adtmm;
//			this.rd = rd;
//		}
//	}
//
//	public final Res getdata(int year, int day, double adtmm, double agd, double rd) {
//
//		if (position >= LOOKUP_LAMBDA_MAXITEM) {
//			LPJ.fail("class Lookup_lambda: exceeded dimension of lookup table", null);
//		}
//
//		Item thisitem = data[position];
//		if (thisitem.year == year && thisitem.day == day) {
//			adtmm = thisitem.adtmm;
//			agd = thisitem.agd;
//			rd = thisitem.rd;
//			return new Res(true, agd, adtmm, rd);
//		}
//
//		return new Res(false, agd, adtmm, rd);
//	}
//
//	public final void setdata(int year, int day, double adtmm, double agd, double rd) {
//
//		if (position >= LOOKUP_LAMBDA_MAXITEM) {
//			LPJ.fail("class Lookup_lambda: exceeded dimension of lookup table", null);
//		}
//
//		Item thisitem = data[position];
//		thisitem.year = year;
//		thisitem.day = day;
//		thisitem.adtmm = adtmm;
//		thisitem.agd = agd;
//		thisitem.rd = rd;
//	}
//
//	public final boolean increase() {
//		position += position + 1;
//		if (position >= LOOKUP_LAMBDA_MAXITEM) {
//			return false;
//		}
//		return true;
//	}
//
//	public final boolean decrease() {
//		position += position + 2;
//		if (position >= LOOKUP_LAMBDA_MAXITEM) {
//			return false;
//		}
//		return true;
//	}
//}

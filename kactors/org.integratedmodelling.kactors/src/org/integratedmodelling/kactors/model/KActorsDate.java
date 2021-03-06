package org.integratedmodelling.kactors.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.integratedmodelling.kactors.kactors.Date;

/**
 * Date literal. Simple bean, no IKimStatement derivation for this one.
 * 
 * @author ferdinando.villa
 *
 */
public class KActorsDate {

	private int year;
	private int month;
	private int day;

	private int hour;
	private int min;
	private int sec;
	private int ms;

	private KActorsDate() {
	}

	public KActorsDate(Date date) {
		this.year = date.getYear() * (date.isBc() ? -1 : 1);
		this.day = date.getDay();
		this.month = date.getMonth();
		this.hour = date.getHour();
		this.min = date.getMin();
		this.sec = date.getSec();
		this.ms = date.getMs();
	}

	/**
	 * The date correspondent to the passed year.
	 * 
	 * @param year
	 * @return
	 */
	public static KActorsDate asDate(int year) {

		KActorsDate ret = new KActorsDate();
		ret.year = year;
		ret.month = 1;
		ret.day = 1;
		return ret;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getSec() {
		return sec;
	}

	public void setSec(int sec) {
		this.sec = sec;
	}

	public int getMs() {
		return ms;
	}

	public void setMs(int ms) {
		this.ms = ms;
	}

	public java.util.Date getDate() {
		java.util.Date ret = new GregorianCalendar(year, month, day, hour, min, sec).getTime();
		if (ms > 0) {
			ret = new java.util.Date(ret.getTime() + ms);
		}
		return ret;
	}
	
	public boolean isValid() {
		
		boolean ok = ms < 1000;

		if (ok) {
			Calendar cal = Calendar.getInstance();
			cal.setLenient(false);
			try {
				cal.set(year, month - 1, day, hour, min, sec);
				cal.getTime();
			} catch (Exception e) {
				ok = false;
			}
		}
		return ok;
	}

}

package org.integratedmodelling.klab.hub.stats.controllers;


import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(value = { "_id" })
public class GroupUsersByDate {

	private String _id;
	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int day;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int week;
	private int month;
	private int year;
	private int count;
	private String dateString = "NaN";
	private DateTime registered;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public DateTime getRegistered() {
		return registered;
	}
	public void setRegistered(DateTime registered) {
		this.registered = registered;
	}
}

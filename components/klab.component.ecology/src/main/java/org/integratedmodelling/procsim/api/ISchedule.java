package org.integratedmodelling.procsim.api;

public interface ISchedule {

	int julianDay();

	boolean isLastDayOfMonth();

	boolean isLastMonthOfYear();

	int dayofmonth();

	int year();

	int month();

	int ndaymonth();

	// boolean finished();

//	public void setTimestamp(TimeValue timestamp);
//
//	void setInitialTimestamp(TimeValue tv);

	boolean isFirstYearOfSimulation();

	boolean isFirstMonthOfSimulation();

	boolean isFirstDayOfSimulation();
	
	boolean isFirstDayOfYear();

	boolean isLastDayOfYear();

}

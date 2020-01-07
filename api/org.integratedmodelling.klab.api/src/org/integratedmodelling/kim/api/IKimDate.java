package org.integratedmodelling.kim.api;

import java.util.Date;

public interface IKimDate {

	int getYear();

	int getMonth();

	int getDay();

	int getHour();

	int getMin();

	int getSec();

	int getMs();

	Date getDate();

	boolean isValid();

}

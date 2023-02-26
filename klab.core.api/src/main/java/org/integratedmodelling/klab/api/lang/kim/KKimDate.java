package org.integratedmodelling.klab.api.lang.kim;

import java.io.Serializable;

public interface KKimDate extends Serializable {

	int getYear();

	int getMonth();

	int getDay();

	int getHour();

	int getMin();

	int getSec();

	int getMs();
	
	boolean isValid();

}

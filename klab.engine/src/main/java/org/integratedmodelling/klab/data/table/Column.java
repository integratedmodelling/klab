package org.integratedmodelling.klab.data.table;

import org.integratedmodelling.klab.api.data.DataType;

public interface Column {

	String getName();

	DataType getDataType();

	int getWidth();

	boolean isIndex();

}
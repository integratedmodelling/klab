package org.integratedmodelling.klab.components.network.algorithms;

import java.util.List;
import org.locationtech.jts.geom.Geometry;

public interface Feature {
	Object getAttribute(int var1);

	Object getAttribute(String var1);

	Class<?> getAttributeType(int var1);

	List<String> getAttributeNames();

	List<Object> getAttributes();

	Geometry getGeometry();

	Object getId();

	Class<?> getIdType();

	/*  */ void setAttribute(int paramInt, Object paramObject);

	/*  */
	/*  */ void setAttribute(String paramString, Object paramObject);

	/*  */
	/*  */ void setGeometry(Geometry paramGeometry);

	/*  */
	/*  */ void addAttribute(String paramString, Object paramObject);

	/*  */
	/*  */ void removeAttribute(int paramInt);
}

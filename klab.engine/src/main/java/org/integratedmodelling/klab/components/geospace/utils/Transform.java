package org.integratedmodelling.klab.components.geospace.utils;

public interface Transform<TSrc, TDst> {
	public TDst transform(TSrc src);
}
package org.integratedmodelling.klab.api.services;

public interface IRuntimeService {

    void info(Object o);

    void warn(Object o);

    void error(Object o);

    void debug(Object o);

    void warn(String string, Throwable e);

    void error(String string, Throwable e);

}

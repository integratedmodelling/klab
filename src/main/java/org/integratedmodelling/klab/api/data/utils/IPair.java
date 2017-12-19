package org.integratedmodelling.klab.api.data.utils;

import java.io.Serializable;

public interface IPair<T1, T2> extends Serializable {

    T1 getFirst();
    
    T2 getSecond();
}

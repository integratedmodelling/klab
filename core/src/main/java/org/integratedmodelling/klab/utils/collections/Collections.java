package org.integratedmodelling.klab.utils.collections;

import java.util.ArrayList;
import java.util.List;

public class Collections {

    /**
     * Pass any number of lists and return one with all the elements.
     * @param lists
     * @return a single list with the content of all those passed
     */
    @SafeVarargs
    public static <T> List<T> join(List<T>... lists) {
        List<T> ret = new ArrayList<>();
        if (lists != null) {
            for (List<T> list : lists) {
                ret.addAll(list);
            }
        }
        return ret;
    }
}

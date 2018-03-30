package org.integratedmodelling.klab.utils;

import org.integratedmodelling.kim.api.IKimScope;

public class NotificationUtils {
    
    public static String getMessage(Object ...objects) {

        StringBuffer ret = new StringBuffer(256);

        for (Object o : objects) {
            if (o instanceof String) {
                ret.append((ret.length() == 0 ? "" : " ") + o);
            } else if (o instanceof Throwable) {
                ret.append((ret.length() == 0 ? "" : " ") + ((Throwable)o).getLocalizedMessage());
            } else if (o instanceof IKimScope) {
                ret.insert(0, ((IKimScope)o).getLocationDescriptor() + ": ");
            }
            // TODO continue
        }
        
        return ret.toString();
    }

}

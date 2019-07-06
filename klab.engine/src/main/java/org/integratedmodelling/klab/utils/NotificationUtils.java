/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.utils;

import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.klab.api.runtime.rest.INotification;

// TODO: Auto-generated Javadoc
/**
 * The Class NotificationUtils.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class NotificationUtils {
    
    /**
     * Gets the message.
     *
     * @param objects the objects
     * @return the message
     */
    public static Pair<String, INotification.Type> getMessage(Object ...objects) {

        StringBuffer ret = new StringBuffer(256);
        INotification.Type ntype = null;
        
        for (Object o : objects) {
            if (o instanceof String) {
                ret.append((ret.length() == 0 ? "" : " ") + o);
            } else if (o instanceof Throwable) {
                ret.append((ret.length() == 0 ? "" : " ") + ((Throwable)o).getLocalizedMessage());
            } else if (o instanceof IKimScope) {
                ret.insert(0, ((IKimScope)o).getLocationDescriptor() + ": ");
            } else if (o instanceof INotification.Type) {
            	ntype = (INotification.Type)o;
            }
            // TODO continue
        }
        
        return new Pair<>(ret.toString(), ntype);
    }

}

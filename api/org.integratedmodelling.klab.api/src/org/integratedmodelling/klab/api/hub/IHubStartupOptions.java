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
package org.integratedmodelling.klab.api.hub;

import org.integratedmodelling.klab.api.engine.IStartupOptions;

/**
 * The Interface IHubStartupOptions.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IHubStartupOptions extends IStartupOptions {

	public static final String BANNER = "\r\n" + 
			" ____     _      _               ____   ______   \r\n" + 
			" \\ \\ \\   | |    | |        /\\   |  _ \\  \\ \\ \\ \\  \r\n" + 
			"  \\ \\ \\  | | __ | |       /  \\  | |_) |  \\ \\ \\ \\ \r\n" + 
			"   > > > | |/ / | |      / /\\ \\ |  _ <    > > > >\r\n" + 
			"  / / /  |   < _| |____ / ____ \\| |_) |  / / / / \r\n" + 
			" /_/_/   |_|\\_(_)______/_/    \\_\\____/  /_/_/_/  \r\n" + 
			" | |         | |                                 \r\n" + 
			" | |__  _   _| |__                               \r\n" + 
			" | '_ \\| | | | '_ \\                              \r\n" + 
			" | | | | |_| | |_) |                             \r\n" + 
			" |_| |_|\\__,_|_.__/                              \r\n" + 
			"                                                 \r\n" + 
			"                                                 \r\n" + 
			"";
	
    /**
     * Tied to -certResource, allows the certificate to be read from the classpath rather than from a file. This should be checked before 
     * {@link #getCertificateFile()} is called. For self-contained embedded servers.
     * 
     * @return the classpath resource with a certificate or null
     */
    String getCertificateResource();
   
    /**
     * Tied to -name: name of this hub, overriding the one in the certificate.
     * 
     * @return the name 
     */
    String getHubName();
   
}

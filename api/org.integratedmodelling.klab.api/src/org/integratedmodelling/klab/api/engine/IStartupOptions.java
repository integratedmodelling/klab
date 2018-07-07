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
package org.integratedmodelling.klab.api.engine;

import java.io.File;

/**
 * The Interface IStartupOptions.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IStartupOptions {

	
    /**
     * Tied to the <pre>-cert</pre> option, provides an alternate certificate file to use when initializing. The
     * default depends on the type of server and is specified in {@link ICertificate}.
     *
     * @return a {@link java.io.File} object.
     */
    File getCertificateFile();


    /**
     * Tied to the <pre>-dataDir</pre> option, provides an alternate work directory full path. The default is
     * <pre>~/.klab</pre>.
     *
     * @return a {@link java.io.File} object.
     */
    File getDataDirectory();


    /**
     * Tied to the <pre>-port</pre> option, establishes the REST communication port if we are implementing
     * REST communication. It also transparently links to the Spring application properties so it can be
     * specified either way.
     *
     * @return a int.
     */
    int getPort();


    /**
     * Tied to <pre>-help</pre> option. Print version and usage banner and exit.
     *
     * @return a boolean.
     */
    boolean isHelp();

    /**
     * Return all arguments that weren't parsed as predefined options.
     * 
     * @param additionalArguments any additional arguments we wish to add
     * @return any remaining arguments to pass to main()
     */
    String[] getArguments(String... additionalArguments);
    
}

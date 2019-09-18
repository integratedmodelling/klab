package org.integratedmodelling.tables;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;

@Component(id="org.integratedmodelling.table", version=Version.CURRENT)
public class TablesComponent {
	
	public static final String ID = "org.integratedmodelling.table";
	
	/**
	 * Formats supported so far. Correspond to 'format' parameter.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	enum Source {
		XLS,
		XLSX,
		OPENDAP,
		JDBC,
		CSV
	}

}

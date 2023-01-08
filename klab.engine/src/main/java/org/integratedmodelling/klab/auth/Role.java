/**
 * - BEGIN LICENSE: 4552165799761088680 -
 *
 * Copyright (C) 2014-2018 by:
 * - J. Luke Scott <luke@cron.works>
 * - Ferdinando Villa <ferdinando.villa@bc3research.org>
 * - any other authors listed in the @author annotations in source files
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Affero General Public License
 * Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Affero General Public License for more details.
 *
 * You should have received a copy of the Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * The license is also available at: https://www.gnu.org/licenses/agpl.html
 *
 * - END LICENSE -
 */
package org.integratedmodelling.klab.auth;

import org.springframework.security.core.GrantedAuthority;

/**
 * All user roles which should be "built in" to the system. Values are "full
 * stack": this system uses the same group name/id values throughout the
 * javascript > json > java > mongo path.
 *
 * Spring Security requires the ROLE_ prefix, unfortunately.
 *
 * @author luke
 */
public enum Role implements GrantedAuthority {

	ROLE_ADMINISTRATOR, ROLE_ENGINE, ROLE_SYSTEM;

	public static final String ADMINISTRATOR = "ROLE_ADMINISTRATOR";
	public static final String ENGINE = "ROLE_ENGINE";
	public static final String SYSTEM = "ROLE_SYSTEM";

	@Override
	public String getAuthority() {
		return name();
	}

	public String plainName() {
		return getAuthority().replaceFirst("ROLE_", "");
	}
}

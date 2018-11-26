/*******************************************************************************
 *  Copyright (C) 2007, 2014:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
/**
 * 
 */
package org.integratedmodelling.klab.components.time.extents;

import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class TemporalPrecision {

	public static final int MILLISECOND = 1;
	public static final int SECOND = 2;
	public static final int MINUTE = 3;
	public static final int HOUR = 4;
	public static final int DAY = 5;
	public static final int WEEK = 6;
	public static final int MONTH = 7;
	public static final int YEAR = 8;

	/**
	 * Check the unit expressed in the passed string (which must end with a known SI
	 * unit of time) and return the corresponding precision. If the unit is absent,
	 * precision is milliseconds; if the unit is present and unrecognized, an
	 * exception is thrown.
	 * 
	 * As a reminder, the units for millisecond, second, minute, hour, day, month
	 * and year are ms, s, min, h, day, month, year.
	 * 
	 * @param s
	 * @return precision
	 * @throws KlabValidationException
	 */
	public static int getPrecisionFromUnit(String s)  {

		int ret = MILLISECOND;
		s = s.trim();

		if (Character.isLetter(s.charAt(s.length() - 1))) {

			if (s.endsWith("s") && !s.endsWith("ms")) {
				ret = SECOND;
			} else if (s.endsWith("min")) {
				ret = MINUTE;
			} else if (s.endsWith("h")) {
				ret = HOUR;
			} else if (s.endsWith("day")) {
				ret = DAY;
			} else if (s.endsWith("week")) {
				ret = WEEK;
			} else if (s.endsWith("month")) {
				ret = MONTH;
			} else if (s.endsWith("year")) {
				ret = YEAR;
			} else if (!s.endsWith("ms")) {
				throw new KlabValidationException("time unit unrecognized in " + s);
			}
		}

		return ret;
	}

}
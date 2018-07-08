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
package org.integratedmodelling.klab.node.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.jose4j.jwt.NumericDate;

public class DateTimeUtil {

    public static final ZoneId UTC = ZoneId.of("UTC");

    public static LocalDateTime utcNow() {
        return utcLocal(ZonedDateTime.now());
    }

    public static LocalDateTime utcLocal(ZonedDateTime zoned) {
        ZonedDateTime utc = localToUtc(zoned);
        return utc.toLocalDateTime();
    }

    public static ZonedDateTime localToUtc(ZonedDateTime local) {
        return ZonedDateTime.ofInstant(local.toInstant(), UTC);
    }

    public static NumericDate localToUtcNumeric(ZonedDateTime local) {
        ZonedDateTime utc = localToUtc(local);
        NumericDate result = NumericDate.fromSeconds(utc.toEpochSecond());
        return result;
    }

    public static NumericDate utcLocalToUtcNumeric(LocalDateTime utcLocal) {
        NumericDate result = NumericDate.fromSeconds(utcLocal.toEpochSecond(ZoneOffset.UTC));
        return result;
    }

    public static ZonedDateTime utcToLocal(long issuedAtUtcMs) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(issuedAtUtcMs), UTC);
    }

    public static LocalDateTime utcMsToUtcLocal(long issuedAtUtcMs) {
        ZonedDateTime result = ZonedDateTime.ofInstant(Instant.ofEpochMilli(issuedAtUtcMs), UTC);
        return result.toLocalDateTime();
    }
}

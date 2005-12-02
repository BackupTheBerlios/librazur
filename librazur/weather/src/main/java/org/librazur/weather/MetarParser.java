/**
 * $Id: MetarParser.java,v 1.1 2005/12/02 11:11:11 romale Exp $
 *
 * Librazur
 * http://librazur.info
 * Copyright (c) 2005 Librazur
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.librazur.weather;


import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Parser for METAR records from the <tt>weather.noaa.gov</tt> server.
 */
class MetarParser {
    public Weather parse(URL url, Station station, String data)
            throws ParseException {
        final String[] lines = data.split("[\\r\\n\\u0085\\u2028\\u2029]");
        if (lines.length != 2) {
            // two lines are expected
            throw new ParseException(url);
        }

        // first line is the date
        final Date date;
        final DateFormat dateFormatter = new SimpleDateFormat(
                "yyyy/MM/dd HH:mm");
        try {
            date = dateFormatter.parse(lines[0]);
        } catch (Exception e) {
            throw new ParseException(url, 1, lines[0], e);
        }

        final Weather weather = new Weather(station, date);
        // TODO do implementation

        return weather;
    }
}

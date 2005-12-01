/**
 * $Id: GeoUtils.java,v 1.1 2005/12/01 23:50:25 romale Exp $
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


/**
 * Provides several methods to convert longitude and latitude values, from the
 * Degree-Minute-Second notation to the Decimal-Degree notation.
 */
final class GeoUtils {
    private GeoUtils() {
    }


    /**
     * Converts a DMS longitude value to a DD value.
     */
    public static double dmsLongitudeToDd(String str) {
        final double dd = dmsToDd(str);
        return str.toUpperCase().contains("W") ? -dd : dd;
    }


    /**
     * Converts a DMS latitude value to a DD value.
     */
    public static double dmsLatitudeToDd(String str) {
        final double dd = dmsToDd(str);
        return str.toUpperCase().contains("S") ? -dd : dd;
    }


    /**
     * Converts a Degree-Minute-Second value to a Decimal-Degree value. The
     * input value has to be of the form: <degree>[:minute[:second]][E|W|S|N].
     * You can use a different separator instead of ':', such as '.' or '-'.
     */
    private static double dmsToDd(String str) {
        final String newStr;
        final char lastChar = str.toUpperCase().charAt(str.length() - 1);
        if (lastChar == 'W' || lastChar == 'E' || lastChar == 'S'
                || lastChar == 'N') {
            newStr = str.substring(0, str.length() - 1);
        } else {
            newStr = str;
        }

        final String[] tokens = newStr.split("[:.-]");
        final double degrees = Integer.parseInt(tokens[0]);
        final double minutes = (tokens.length > 1 ? Integer.parseInt(tokens[1])
                : 0) / 60d;
        final double seconds = (tokens.length > 2 ? Integer.parseInt(tokens[2])
                : 0) / 3600d;

        return degrees + minutes + seconds;
    }
}

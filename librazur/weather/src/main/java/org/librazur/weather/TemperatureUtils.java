/**
 * $Id: TemperatureUtils.java,v 1.2 2005/12/02 09:15:44 romale Exp $
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
 * Provides several methods to convert temperature values from Fahrenheit to
 * Celsius, and vice-versa.
 */
final class TemperatureUtils {
    private TemperatureUtils() {
    }


    /**
     * Converts a value in Celsius to Fahrenheit.
     */
    public static double toFahrenheit(double celsius) {
        return 32 + 9 * celsius / 5d;
    }


    /**
     * Converts a value in Fahrenheit to Celsius.
     */
    public static double toCelsius(double fahrenheit) {
        return 5 * (fahrenheit - 32) / 9d;
    }
}

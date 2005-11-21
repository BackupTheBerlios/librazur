/**
 * $Id: NumberUtils.java,v 1.1 2005/11/21 01:30:15 romale Exp $
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

package org.librazur.ict.util;


import java.text.NumberFormat;


/**
 * Number utilities.
 */
public final class NumberUtils {
    public static String format(double value) {
        return getDoubleNumberFormat().format(value);
    }


    public static NumberFormat getDoubleNumberFormat() {
        final NumberFormat numberFormatter = NumberFormat.getInstance();
        numberFormatter.setMinimumFractionDigits(1);
        return numberFormatter;
    }
}
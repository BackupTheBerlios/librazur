/**
 * $Id: Quantity.java,v 1.2 2005/12/02 09:15:44 romale Exp $
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
 * Quantity, with an unit.
 */
public class Quantity {
    private final Unit unit;
    private final Number value;


    public Quantity(final Number value, final Unit unit) {
        if (value == null) {
            throw new NullPointerException("value");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        this.value = value;
        this.unit = unit;
    }


    public Unit getUnit() {
        return unit;
    }


    public Number getValue() {
        return value;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Quantity)) {
            return false;
        }
        final Quantity that = (Quantity) obj;
        return this.value.equals(that.value) && this.unit.equals(that.unit);
    }


    @Override
    public int hashCode() {
        return toString().hashCode();
    }


    @Override
    public String toString() {
        return value + " " + unit;
    }
}

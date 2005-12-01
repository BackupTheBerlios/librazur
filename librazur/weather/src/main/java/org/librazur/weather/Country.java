/**
 * $Id: Country.java,v 1.1 2005/12/01 23:50:25 romale Exp $
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


import org.librazur.util.StringUtils;


public class Country implements Comparable<Country> {
    private final String name;


    public Country(final String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("name");
        }
        this.name = name;
    }


    public String getName() {
        return name;
    }


    @Override
    public int hashCode() {
        return name.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Country)) {
            return false;
        }
        final Country that = (Country) obj;
        return this.name.equals(that.name);
    }


    public int compareTo(Country obj) {
        return name.compareTo(obj.name);
    }


    @Override
    public String toString() {
        return name.toString();
    }
}

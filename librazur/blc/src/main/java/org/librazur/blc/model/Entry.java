/**
 * $Id: Entry.java,v 1.2 2005/10/20 22:44:12 romale Exp $
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

package org.librazur.blc.model;


public class Entry {
    public static enum Type {
        IP, DOMAIN, URL
    }

    private String value;
    private Type type;


    public Entry() {
    }


    public Entry(final Type type, final String value) {
        setType(type);
        setValue(value);
    }


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        if (value == null) {
            throw new NullPointerException("value");
        }
        this.value = value;
    }


    public Type getType() {
        return type;
    }


    public void setType(Type type) {
        if (type == null) {
            throw new NullPointerException("type");
        }
        this.type = type;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Entry)) {
            return false;
        }
        return hashCode() == obj.hashCode();
    }


    @Override
    public int hashCode() {
        return (type.name() + "\n" + value).hashCode();
    }
}

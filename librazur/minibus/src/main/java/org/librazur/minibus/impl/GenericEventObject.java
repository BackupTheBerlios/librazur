/**
 * $Id: GenericEventObject.java,v 1.1 2005/10/26 09:56:43 romale Exp $
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

package org.librazur.minibus.impl;


import java.util.EventObject;


public class GenericEventObject extends EventObject {
    private final String id;
    private final Object argument;


    public GenericEventObject(final Object source, final String id,
            final Object argument) {
        super(source);
        this.id = id;
        this.argument = argument;
    }


    public GenericEventObject(final Object source, final String id) {
        this(source, id, null);
    }


    public Object getArgument() {
        return argument;
    }


    public String getId() {
        return id;
    }
}

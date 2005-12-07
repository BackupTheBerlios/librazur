/**
 * $Id: EventHandler.java,v 1.4 2005/12/07 15:00:17 romale Exp $
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

package org.librazur.minibus;


import java.util.EventListener;
import java.util.EventObject;


/**
 * Event handler.
 */
public interface EventHandler extends EventListener {
    /**
     * This method is called by the {@link Bus} when an event has been posted.
     * If an {@link EventObject} is returned, the {@link Bus} must repost it.
     * Otherwise, this method should return <code>null</code>.
     */
    EventObject onEvent(EventObject evt) throws Exception;
}

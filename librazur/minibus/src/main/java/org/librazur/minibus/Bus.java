/**
 * $Id: Bus.java,v 1.3 2005/12/07 15:00:16 romale Exp $
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


import java.util.EventObject;


/**
 * Event bus interface.
 */
public interface Bus {
    /**
     * Registers an {@link EventHandler} on the bus. If an event object class is
     * precised, the {@link EventHandler} would only receive event objects of
     * this class.
     */
    void register(EventHandler handler,
            Class<? extends EventObject>... evtClass);


    /**
     * Same as: <tt>post(evt, true)</tt>.
     */
    void post(EventObject evt);


    /**
     * Posts an event to all registered {@link EventHandler} instances. Any
     * error during event dispatching is handled by the registered
     * {@link ErrorHandler}. If an error occurs in synchronous mode, the event
     * is not dispatched to other {@link EventHandler} instances. In
     * asynchronous mode, an error doesn't prevent the other
     * {@link EventHandler} instances to be called. If an {@link EventHandler}
     * instance returns a non-<code>null</code> {@link EventObject}, the bus
     * must repost the event.
     */
    void post(EventObject evt, boolean asynchronous);


    /**
     * Clears all registered {@link EventHandler} instances.
     */
    void clear();


    /**
     * Sets an {@link ErrorHandler}.
     */
    void setErrorHandler(ErrorHandler handler);
}

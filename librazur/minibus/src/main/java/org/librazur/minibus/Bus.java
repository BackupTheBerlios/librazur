/**
 * $Id: Bus.java,v 1.2 2005/11/20 22:00:59 romale Exp $
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
     * Registers an <tt>EventHandler</tt> on the bus. If an event object class
     * is precised, the <tt>EventHandler</tt> would only receive event objects
     * of this class.
     */
    void register(EventHandler handler,
            Class<? extends EventObject>... evtClass);


    /**
     * Same as: <tt>post(evt, true)</tt>.
     */
    void post(EventObject evt);


    /**
     * Posts an event to all registered <tt>EventHandler</tt> instances. Any
     * error during event dispatching is handled by the registered
     * <tt>ErrorHandler</tt>. If an error occurs in synchronous mode, the
     * event is not dispatched to other <tt>EventHandler</tt> instances. In
     * asynchronous mode, an error doesn't prevent the other
     * <tt>EventHandler</tt> instances to be called. If an
     * <tt>EventHandler</tt> instance returns a non-<tt>null</tt> <tt>EventObject</tt>,
     * the bus must repost the event.
     */
    void post(EventObject evt, boolean asynchronous);


    /**
     * Clears all registered <tt>EventHandler</tt> instances.
     */
    void clear();


    /**
     * Sets an <tt>ErrorHandler</tt>.
     */
    void setErrorHandler(ErrorHandler handler);
}

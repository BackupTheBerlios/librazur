/**
 * $Id: DefaultBus.java,v 1.2 2005/10/30 18:45:17 romale Exp $
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
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.librazur.minibus.Bus;
import org.librazur.minibus.ErrorHandler;
import org.librazur.minibus.EventHandler;


public class DefaultBus implements Bus {
    private final ExecutorService executorService = Executors
            .newCachedThreadPool();
    private final Set<EventHandlerData> listeners = new HashSet<EventHandlerData>(
            1);
    private ErrorHandler errorHandler = new NullErrorHandler();


    public void register(EventHandler handler,
            Class<? extends EventObject>... evtClass) {
        if (evtClass == null || evtClass.length == 0) {
            listeners.add(new EventHandlerData(handler));
            return;
        }

        for (final Class<? extends EventObject> curEvtClass : evtClass) {
            final EventHandlerData data = new EventHandlerData(handler,
                    curEvtClass);
            listeners.add(data);
        }
    }


    public void post(EventObject evt, boolean asynchronous) {
        if (evt == null) {
            // nothing to post!
            return;
        }

        for (final EventHandlerData data : listeners) {
            if (data.eventClass != null
                    && !data.eventClass.isAssignableFrom(evt.getClass())) {
                continue;
            }

            if (asynchronous) {
                executorService
                        .submit(new PostEventCall(data.eventHandler, evt));
            } else {
                try {
                    final EventObject result = data.eventHandler.onEvent(evt);
                    if (result != null) {
                        // continue the chain
                        post(result, false);
                    }
                } catch (Exception e) {
                    errorHandler.onError(data.eventHandler, evt, e);
                    // an error in synchronous mode breaks the event posting
                    break;
                }
            }
        }
    }


    public void post(EventObject evt) {
        post(evt, true);
    }


    public void clear() {
        listeners.clear();
    }


    public void setErrorHandler(ErrorHandler handler) {
        this.errorHandler = handler == null ? new NullErrorHandler() : handler;
    }


    private class EventHandlerData {
        public final EventHandler eventHandler;
        public final Class<? extends EventObject> eventClass;


        public EventHandlerData(final EventHandler eventHandler) {
            this(eventHandler, null);
        }


        public EventHandlerData(final EventHandler eventHandler,
                final Class<? extends EventObject> eventClass) {
            if (eventHandler == null) {
                throw new NullPointerException("eventHandler");
            }
            this.eventHandler = eventHandler;
            this.eventClass = eventClass;
        }


        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof EventHandlerData)) {
                return false;
            }
            return hashCode() == obj.hashCode();
        }


        @Override
        public int hashCode() {
            return eventHandler.hashCode()
                    * (eventClass == null ? 1 : eventClass.hashCode());
        }
    }


    private class PostEventCall implements Callable<EventObject> {
        private final EventHandler eventHandler;
        private final EventObject eventObject;


        public PostEventCall(final EventHandler eventHandler,
                final EventObject eventObject) {
            this.eventHandler = eventHandler;
            this.eventObject = eventObject;
        }


        public EventObject call() throws Exception {
            EventObject result = null;
            try {
                result = eventHandler.onEvent(eventObject);
            } catch (Exception e) {
                errorHandler.onError(eventHandler, eventObject, e);
            }

            if (result != null) {
                post(result);
            }
            return null;
        }
    }
}

/**
 * $Id: AbstractBusTest.java,v 1.2 2005/11/20 22:00:19 romale Exp $
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

import junit.framework.TestCase;


public abstract class AbstractBusTest extends TestCase implements BusProvider {
    protected void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException ignore) {
        }
    }


    public void testSetErrorHandler() {
        final TestErrorHandler errorHandler = new TestErrorHandler();
        assertFalse("ErrorHandler is not initialized", errorHandler.getError());

        final Bus bus = getBus();
        bus.setErrorHandler(errorHandler);
        bus.register(new FaultyEventHandler());
        bus.post(new EventObject(this));

        sleep(2);
        assertTrue("ErrorHandler should have catched an error", errorHandler
                .getError());
    }


    public void testPost() {
        final SimpleEventHandler handler = new SimpleEventHandler();
        assertFalse("EventHandler is not initialized", handler.getCalled());

        final Bus bus = getBus();
        bus.register(handler);
        bus.post(new EventObject(this));

        sleep(2);
        assertTrue("EventHandler should have been called", handler.getCalled());
    }


    public void testPostSynchronous() {
        final SimpleEventHandler handler = new SimpleEventHandler();
        assertFalse("EventHandler is not initialized", handler.getCalled());

        final Bus bus = getBus();
        bus.register(handler);
        bus.post(new EventObject(this), false);

        assertTrue("EventHandler should have been called", handler.getCalled());
    }


    public void testClear() {
        final SimpleEventHandler handler = new SimpleEventHandler();
        assertFalse("EventHandler is not initialized", handler.getCalled());

        final Bus bus = getBus();
        bus.register(handler);
        bus.clear();
        bus.post(new EventObject(this));

        sleep(2);
        assertFalse("EventHandler shouldn't have been called", handler
                .getCalled());
    }


    private class TestErrorHandler implements ErrorHandler {
        private boolean error;


        public boolean getError() {
            return error;
        }


        public void onError(EventHandler source, EventObject evt, Throwable e) {
            error = true;
            e.printStackTrace();
        }
    }


    private class FaultyEventHandler implements EventHandler {
        public EventObject onEvent(EventObject evt) throws Exception {
            throw new Exception("Fault!");
        }
    }


    private class SimpleEventHandler implements EventHandler {
        private boolean called;


        public boolean getCalled() {
            return called;
        }


        public EventObject onEvent(EventObject evt) throws Exception {
            called = true;
            return null;
        }
    }
}

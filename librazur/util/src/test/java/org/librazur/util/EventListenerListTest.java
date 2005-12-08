/**
 * $Id: EventListenerListTest.java,v 1.1 2005/12/08 09:46:24 romale Exp $
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

package org.librazur.util;


import java.util.EventListener;
import java.util.EventObject;

import junit.framework.TestCase;


public class EventListenerListTest extends TestCase {
    public void testConstructor() {
        new EventListenerList<MockEventListener>(MockEventListener.class);
    }


    public void testFireEvent() {
        final EventListenerList<MockEventListener> ell = new EventListenerList<MockEventListener>(
                MockEventListener.class);
        final SimpleMockEventListener listener = new SimpleMockEventListener();
        assertFalse(listener.onEventCalled);
        assertNull(listener.eventObject);
        ell.add(listener);

        final EventObject evt = new MockEventObject(this);
        ell.fireEvent("onEvent", evt);

        assertTrue(listener.onEventCalled);
        assertNotNull(listener.eventObject);

        ell.remove(listener);
        listener.reset();
        assertFalse(listener.onEventCalled);
        assertNull(listener.eventObject);

        ell.fireEvent("onEvent", evt);
        assertFalse(listener.onEventCalled);
        assertNull(listener.eventObject);

        ell.add(listener);
        ell.clear();
        listener.reset();
        assertFalse(listener.onEventCalled);
        assertNull(listener.eventObject);

        ell.fireEvent("onEvent", evt);
        assertFalse(listener.onEventCalled);
        assertNull(listener.eventObject);

        ell.add(listener);
        listener.reset();
        assertFalse(listener.onEventCalled2);
        assertNull(listener.str);
        assertEquals(0, listener.i);

        final String str = "Hello";
        final int i = 10;
        ell.fireEvent("onEvent2", str, i);

        assertTrue(listener.onEventCalled2);
        assertEquals(str, listener.str);
        assertEquals(i, listener.i);
    }


    private interface MockEventListener extends EventListener {
        void onEvent(EventObject evt);


        void onEvent2(String str, int i);
    }


    private class MockEventObject extends EventObject {
        public MockEventObject(final Object source) {
            super(source);
        }
    }


    private class SimpleMockEventListener implements MockEventListener {
        public boolean onEventCalled;
        public EventObject eventObject;
        public boolean onEventCalled2;
        public String str;
        public int i;


        public SimpleMockEventListener() {
            reset();
        }


        public void onEvent(EventObject evt) {
            onEventCalled = true;
            eventObject = evt;
        }


        public void onEvent2(String str, int i) {
            onEventCalled2 = true;
            this.str = str;
            this.i = i;
        }


        public void reset() {
            onEventCalled = false;
            eventObject = null;

            onEventCalled2 = false;
            str = null;
            i = 0;
        }
    }
}

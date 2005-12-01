/**
 * $Id: StationMapFactoryTest.java,v 1.1 2005/12/01 23:50:25 romale Exp $
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


import java.net.URL;
import java.util.Map;


public class StationMapFactoryTest extends AbstractTest {
    public void testParse() throws Exception {
        final URL url = getClass().getResource("stations-test.dat");
        assertNotNull("Unable to locate test station file", url);

        final StationMapFactory factory = new StationMapFactory(url);
        final Map<String, Station> stations = factory.create();
        assertNotNull(stations);
        assertEquals(2, stations.size());
        assertStationsAreCorrect(stations);
    }


    public void testParseDefault() throws Exception {
        final StationMapFactory factory = new StationMapFactory();
        final Map<String, Station> stations = factory.create();
        assertNotNull(stations);
        assertEquals(6553, stations.size());
        assertStationsAreCorrect(stations);
    }


    private void assertStationsAreCorrect(Map<String, Station> stations) {
        final Station ktul = stations.get("KTUL");
        assertEquals("KTUL", ktul.getCode());
        assertEquals("United States", ktul.getCountry().getName());
        assertEquals("Tulsa, Tulsa International Airport", ktul.getLocation());
        assertDoubleEquals(36.197d, ktul.getLatitude());
        assertDoubleEquals(95.886d, ktul.getLongitude());

        final Station aymo = stations.get("AYMO");
        assertEquals("AYMO", aymo.getCode());
        assertEquals("Papua New Guinea", aymo.getCountry().getName());
        assertEquals("Manus Island/Momote", aymo.getLocation());
        assertDoubleEquals(2.061d, aymo.getLatitude());
        assertDoubleEquals(147.424d, aymo.getLongitude());
    }
}

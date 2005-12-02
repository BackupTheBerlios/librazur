/**
 * $Id: WeatherFetcherTest.java,v 1.2 2005/12/02 11:11:58 romale Exp $
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


public class WeatherFetcherTest extends AbstractTest {
    public void testFetchInvalidStation() throws Exception {
        try {
            new WeatherFetcher().fetch("ZZZZ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
        }
    }


    public void testFetch() throws Exception {
        final String stationCode = "LFTH";
        final Weather weather = new WeatherFetcher().fetch(stationCode);
        assertNotNull(weather);
        assertNotNull(weather.getDate());
        assertEquals(stationCode, weather.getStation().getCode());

        System.out.println("Got weather from " + weather.getStation().getCode()
                + " at " + weather.getDate());
    }
}

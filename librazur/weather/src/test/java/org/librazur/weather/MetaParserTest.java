/**
 * $Id: MetaParserTest.java,v 1.1 2005/12/02 11:11:11 romale Exp $
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

import org.librazur.util.IOUtils;


public class MetaParserTest extends AbstractTest {
    public void testParse() throws Exception {
        final URL url = getClass().getResource("metar-sample1.txt");
        final Station station = new Station("LFTH", new Country("France"));

        final String data = IOUtils.readString(url.openStream());
        final MetarParser parser = new MetarParser();
        final Weather weather = parser.parse(url, station, data);
        assertNotNull(weather);
        assertEquals(station.getCode(), weather.getStation().getCode());
    }
}

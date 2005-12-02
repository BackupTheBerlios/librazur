/**
 * $Id: WeatherFetcher.java,v 1.1 2005/12/02 09:17:58 romale Exp $
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


import java.util.Map;


public class WeatherFetcher {
    public Weather fetch(Station station) throws ParseException {
        return null;
    }


    public Weather fetch(String stationCode, StationMapFactory stationMapFactory)
            throws ParseException {
        final Map<String, Station> stations = stationMapFactory.create();
        final Station station = stations.get(stationCode);
        if (station == null) {
            throw new IllegalArgumentException(
                    "Unable to find station with code: " + stationCode);
        }
        return fetch(station);
    }


    public Weather fetch(String stationCode) throws ParseException {
        return fetch(stationCode, new StationMapFactory());
    }
}
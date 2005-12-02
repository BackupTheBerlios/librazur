/**
 * $Id: WeatherFetcher.java,v 1.2 2005/12/02 11:11:45 romale Exp $
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


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.librazur.util.FileUtils;
import org.librazur.util.IOUtils;


/**
 * Fetchs weather information from a meteo station.
 */
public class WeatherFetcher {
    private static final String BASE_URL = "http://weather.noaa.gov/pub/data/observations/metar/stations/";
    private final MetarParser parser = new MetarParser();


    /**
     * Fetchs from a meteo station.
     */
    public Weather fetch(Station station) throws IOException, ParseException {
        final File tempFile = File.createTempFile("weather-", ".tmp");
        tempFile.deleteOnExit();

        FileOutputStream tempOutput = null;
        try {
            final URL url = createStationUrl(station);
            tempOutput = new FileOutputStream(tempFile);
            IOUtils.copy(url.openStream(), tempOutput);
            tempOutput.flush();
            IOUtils.close(tempOutput);

            final String data = FileUtils.read(tempFile);
            return parse(url, station, data);
        } finally {
            IOUtils.close(tempOutput);
            tempFile.delete();
        }
    }


    /**
     * Fetchs from a meteo station, looked up by a station code taken from a
     * {@link StationMapFactory}.
     */
    public Weather fetch(String stationCode, StationMapFactory stationMapFactory)
            throws IOException, ParseException {
        final Map<String, Station> stations = stationMapFactory.create();
        final Station station = stations.get(stationCode);
        if (station == null) {
            throw new IllegalArgumentException(
                    "Unable to find station with code: " + stationCode);
        }
        return fetch(station);
    }


    /**
     * Fetchs from a station, looked up by a station code.
     */
    public Weather fetch(String stationCode) throws IOException, ParseException {
        return fetch(stationCode, new StationMapFactory());
    }


    /**
     * Creates an URL to a document containing meteo data for the specified
     * {@link Station}. This method is called by {@link fetch(Station)} in
     * order to retrieve weather information. You can override this method if
     * you want to retrieve weather information from an other location.
     */
    protected URL createStationUrl(Station station) throws IOException {
        return new URL(new URL(BASE_URL), station.getCode() + ".TXT");
    }


    /**
     * Parses data fetched from a remote meteo station. This method is called by
     * {@link fetch(Station)} after data has been retrieved from the meteo
     * station. This method allow to convert this data to {@link Weather}
     * instance.
     * 
     * @param fromUrl meteo station URL
     * @param station meteo station
     * @param data information from the meteo station to parse
     */
    protected Weather parse(URL fromUrl, Station station, String data)
            throws ParseException {
        return parser.parse(fromUrl, station, data);
    }
}

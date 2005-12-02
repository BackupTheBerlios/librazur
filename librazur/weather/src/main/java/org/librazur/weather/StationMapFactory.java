/**
 * $Id: StationMapFactory.java,v 1.3 2005/12/02 11:10:57 romale Exp $
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


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.librazur.util.IOUtils;
import org.librazur.util.StringUtils;


/**
 * Factory for creating maps with available {@link Station} instances.
 */
public class StationMapFactory {
    private static final String DEFAULT_STATION_RESOURCE = "stations.dat";
    private final URL url;
    private WeakReference<Map<String, Station>> stationsRef = new WeakReference<Map<String, Station>>(
            null);
    private long lastModified;


    /**
     * Builds a factory, creating stations from the specified location. If the
     * location is not specified (<code>null</code> value), a default
     * location is used.
     */
    public StationMapFactory(final URL url) {
        if (url == null) {
            this.url = getClass().getResource(DEFAULT_STATION_RESOURCE);
            if (this.url == null) {
                throw new IllegalStateException(
                        "Unable to locate default station resource: "
                                + DEFAULT_STATION_RESOURCE);
            }
        } else {
            this.url = url;
        }
    }


    /**
     * Default constructor. Same as: <code>new StationMapFactory(null)</code>.
     */
    public StationMapFactory() {
        this(null);
    }


    /**
     * Creates a map of available {@link Station} instances.
     */
    public Map<String, Station> create() throws ParseException {
        Map<String, Station> stations = null;

        int lineNumber = -1;
        String line = null;
        BufferedReader reader = null;

        try {
            final URLConnection conn = url.openConnection();
            final long connLastModified = conn.getLastModified();
            if (connLastModified > lastModified) {
                stations = stationsRef.get();
            }
            if (stations == null) {
                stations = new WeakHashMap<String, Station>();
                stationsRef = new WeakReference<Map<String, Station>>(stations);
                lastModified = connLastModified;

                reader = new BufferedReader(new InputStreamReader(conn
                        .getInputStream()));
                lineNumber = 1;

                final Map<String, Country> countries = new HashMap<String, Country>();

                for (; (line = reader.readLine()) != null; ++lineNumber) {
                    final String[] tokens = line.split(";");

                    final String code = StringUtils.trimToNull(tokens[0]);
                    final String countryName = StringUtils
                            .trimToNull(tokens[5]);
                    Country country = countries.get(countryName);
                    if (country == null) {
                        country = new Country(countryName);
                        countries.put(countryName, country);
                    }

                    final Station station = new Station(code, country);
                    station.setLatitude(GeoUtils.dmsLatitudeToDd(StringUtils
                            .trimToNull(tokens[7])));
                    station.setLongitude(GeoUtils.dmsLongitudeToDd(StringUtils
                            .trimToNull(tokens[8])));
                    station.setLocation(StringUtils.trimToNull(tokens[3]));

                    stations.put(code, station);
                }
            }
        } catch (Exception e) {
            throw new ParseException(url, lineNumber, line, e);
        } finally {
            IOUtils.close(reader);
        }

        return stations;
    }
}

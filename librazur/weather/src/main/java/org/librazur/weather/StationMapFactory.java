/**
 * $Id: StationMapFactory.java,v 1.1 2005/12/01 23:50:25 romale Exp $
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
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.librazur.util.IOUtils;
import org.librazur.util.StringUtils;


/**
 * Factory for creating maps with all available stations.
 */
public class StationMapFactory {
    private static final String DEFAULT_STATION_RESOURCE = "stations.dat";
    private final URL url;


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
     * Creates a map of available stations. A <code>RuntimeException</code>
     * may be thrown if any errors occured.
     */
    public Map<String, Station> create() {
        try {
            return doCreate();
        } catch (ParseException e) {
            throw new IllegalStateException("Error while creating station map",
                    e);
        }
    }


    private Map<String, Station> doCreate() throws ParseException {
        final Map<String, Station> stations = new HashMap<String, Station>();
        final Map<String, Country> countries = new HashMap<String, Country>();

        int lineNumber = 1;
        BufferedReader reader = null;
        String line = null;
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream()));

            for (; (line = reader.readLine()) != null; ++lineNumber) {
                final String[] tokens = line.split(";");

                final String code = StringUtils.trimToNull(tokens[0]);
                final String countryName = StringUtils.trimToNull(tokens[5]);
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
        } catch (Exception e) {
            final ParseException newExc = new ParseException(
                    "Error while parsing line " + lineNumber + ": " + line,
                    lineNumber);
            newExc.initCause(e);
            throw newExc;
        } finally {
            IOUtils.close(reader);
        }

        return stations;
    }
}

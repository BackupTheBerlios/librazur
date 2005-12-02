/**
 * $Id: Station.java,v 1.2 2005/12/02 09:15:44 romale Exp $
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


import org.librazur.util.StringUtils;


/**
 * Meteo station.
 */
public class Station implements Comparable<Station> {
    private final String code;
    private final Country country;
    private String location;
    private double latitude;
    private double longitude;


    public Station(final String code, final Country country) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("code");
        }
        if (country == null) {
            throw new NullPointerException("country");
        }
        this.code = code;
        this.country = country;
    }


    public String getCode() {
        return code;
    }


    public Country getCountry() {
        return country;
    }


    public String getLocation() {
        return location;
    }


    public void setLocation(String location) {
        this.location = location;
    }


    public double getLatitude() {
        return latitude;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public double getLongitude() {
        return longitude;
    }


    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    public int hashCode() {
        return toString().hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Station)) {
            return false;
        }
        final Station that = (Station) obj;
        return this.code.equals(that.code) && this.country.equals(that.country);
    }


    public int compareTo(Station obj) {
        return toString().compareTo(obj.toString());
    }


    @Override
    public String toString() {
        return code + "/" + country;
    }
}

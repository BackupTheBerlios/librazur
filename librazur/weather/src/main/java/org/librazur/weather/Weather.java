/**
 * $Id: Weather.java,v 1.1 2005/12/01 23:50:25 romale Exp $
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


import java.util.Date;


public class Weather implements Comparable<Weather> {
    public static enum CloudQuantity {
        FEW, SCATTERED, BROKEN, OVERCAST, CLEAR
    }

    private final Station station;
    private final Date date;
    private CloudQuantity cloudQuantity;
    private Quantity cloudHeight;
    private Quantity windSpeed;
    private Direction windDirection;
    private Quantity temperature;
    private Quantity dewPoint;
    private Quantity relativeHumidity;
    private Quantity pressure;


    public Weather(final Station station, final Date date) {
        if (station == null) {
            throw new NullPointerException("station");
        }
        if (date == null) {
            throw new NullPointerException("date");
        }
        this.station = station;
        this.date = date;
    }


    public CloudQuantity getCloudQuantity() {
        return cloudQuantity;
    }


    public void setCloudQuantity(CloudQuantity cloudQuantity) {
        this.cloudQuantity = cloudQuantity;
    }


    public Quantity getCloudHeight() {
        return cloudHeight;
    }


    public void setCloudHeight(Quantity cloudHeight) {
        this.cloudHeight = cloudHeight;
    }


    public Quantity getDewPoint() {
        return dewPoint;
    }


    public void setDewPoint(Quantity dewPoint) {
        this.dewPoint = dewPoint;
    }


    public Quantity getPressure() {
        return pressure;
    }


    public void setPressure(Quantity pressure) {
        this.pressure = pressure;
    }


    public Quantity getRelativeHumidity() {
        return relativeHumidity;
    }


    public void setRelativeHumidity(Quantity relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }


    public Quantity getTemperature() {
        return temperature;
    }


    public void setTemperature(Quantity temperature) {
        this.temperature = temperature;
    }


    public Quantity getWindSpeed() {
        return windSpeed;
    }


    public void setWindSpeed(Quantity windSpeed) {
        this.windSpeed = windSpeed;
    }


    public Direction getWindDirection() {
        return windDirection;
    }


    public void setWindDirection(Direction windDirection) {
        this.windDirection = windDirection;
    }


    public Date getDate() {
        return date;
    }


    public Station getStation() {
        return station;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Weather)) {
            return false;
        }
        final Weather that = (Weather) obj;
        return this.station.equals(that.station) && this.date.equals(that.date);
    }


    @Override
    public int hashCode() {
        return toString().hashCode();
    }


    @Override
    public String toString() {
        return "Weather at station \"" + station + "\" at " + date;
    }


    public int compareTo(Weather obj) {
        return toString().compareTo(obj.toString());
    }
}

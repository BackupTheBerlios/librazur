/**
 * $Id: DateUtils.java,v 1.6 2006/01/14 22:55:29 romale Exp $
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


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.librazur.util.test.Assert;


/**
 * Date utilities.
 * 
 * @since 1.0
 */
public final class DateUtils {
    private DateUtils() {
    }


    /**
     * Returns the first day of year of a date.
     */
    public static Date firstDayOfYear(Date date) {
        return truncate(date, Calendar.YEAR);
    }


    /**
     * Returns the last day of year of a date.
     */
    public static Date lastDayOfYear(Date date) {
        final Calendar cal = new GregorianCalendar();
        cal.setTime(truncate(date, Calendar.YEAR));
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);

        return cal.getTime();
    }


    /**
     * Returns the first day of month of a date.
     */
    public static Date firstDayOfMonth(Date date) {
        return truncate(date, Calendar.MONTH);
    }


    /**
     * Returns the last day of month of a date.
     */
    public static Date lastDayOfMonth(Date date) {
        final Calendar cal = new GregorianCalendar();
        cal.setTime(truncate(date, Calendar.MONTH));
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

        return cal.getTime();
    }


    /**
     * Returns a date at midnight.
     */
    public static Date midnight(Date date) {
        return truncate(date, Calendar.DATE);
    }


    /**
     * Truncates a date at a given field. Examples:
     * <ul>
     * <li><code>truncate(July 23, 2005, Calendar.MONTH)</code> = July 1st,
     * 2005</li>
     * <li><code>truncate(July 23, 2005 12:05, Calendar.HOUR)</code> = July
     * 1st, 2005 12:00</li>
     * </ul>
     */
    public static Date truncate(Date date, int field) {
        Assert.isNotNull("date", date);

        final Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        switch (field) {
            case Calendar.YEAR:
                cal.set(Calendar.MONTH, Calendar.JANUARY);
            case Calendar.MONTH:
                cal.set(Calendar.DATE, 1);
            case Calendar.DATE:
                cal.set(Calendar.HOUR_OF_DAY, 0);
            case Calendar.HOUR:
            case Calendar.HOUR_OF_DAY:
                cal.set(Calendar.MINUTE, 0);
            case Calendar.MINUTE:
                cal.set(Calendar.SECOND, 0);
            case Calendar.SECOND:
                cal.set(Calendar.MILLISECOND, 0);
            case Calendar.MILLISECOND:
                break;
            default:
                throw new IllegalArgumentException(
                        "Wrong value for field: must be one of "
                                + "Calendar.{YEAR,MONTH,DATE,HOUR_OF_DAY,MINUTE,SECOND,MILLISECOND}: "
                                + field);
        }

        return cal.getTime();
    }


    /**
     * Returns <code>true</code> if a date belongs to a leap year.
     * Implementation based on <a
     * href="http://en.wikipedia.org/wiki/Leap_Year">http://en.wikipedia.org/wiki/Leap_Year</a>.
     */
    public static boolean isLeapYear(Date date) {
        Assert.isNotNull("date", date);

        final Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        final int year = cal.get(Calendar.YEAR);
        return ((year % 400 == 0 || year % 100 != 0) && year % 4 == 0);
    }


    /**
     * Creates a new date.
     */
    public static Date date(int year, int month, int day, int hour, int min,
            int sec) {
        final Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }


    /**
     * Creates a new date, at midnight.
     */
    public static Date date(int year, int month, int day) {
        return date(year, month, day, 0, 0, 0);
    }
}

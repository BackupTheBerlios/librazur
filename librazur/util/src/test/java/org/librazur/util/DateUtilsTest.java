/**
 * $Id: DateUtilsTest.java,v 1.2 2005/10/20 22:44:31 romale Exp $
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

import junit.framework.TestCase;


public class DateUtilsTest extends TestCase {
    public void testMidnight() {
        final Date date = new Date();

        final Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        assertEquals(cal.getTime(), DateUtils.midnight(date));
    }


    public void testTruncate() {
        final Calendar refCal = new GregorianCalendar();
        refCal.set(Calendar.YEAR, 2005);
        refCal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        refCal.set(Calendar.DATE, 23);
        refCal.set(Calendar.HOUR_OF_DAY, 14);
        refCal.set(Calendar.MINUTE, 35);
        refCal.set(Calendar.SECOND, 43);
        refCal.set(Calendar.MILLISECOND, 2);

        Calendar cal = (Calendar) refCal.clone();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        assertEquals(cal.getTime().getTime(), DateUtils.truncate(
                refCal.getTime(), Calendar.MINUTE).getTime());

        cal = (Calendar) refCal.clone();
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        assertEquals(cal.getTime().getTime(), DateUtils.truncate(
                refCal.getTime(), Calendar.YEAR).getTime());

        try {
            DateUtils.truncate(refCal.getTime(), -1);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
        }
    }


    public void testIsLeapYear() {
        final Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, 2004);
        assertTrue(DateUtils.isLeapYear(cal.getTime()));

        cal.set(Calendar.YEAR, 1900);
        assertFalse(DateUtils.isLeapYear(cal.getTime()));

        cal.set(Calendar.YEAR, 2000);
        assertTrue(DateUtils.isLeapYear(cal.getTime()));
    }


    public void testDate() {
        final Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, 1997);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 12);
        cal.set(Calendar.HOUR_OF_DAY, 11);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 5);
        cal.set(Calendar.MILLISECOND, 0);

        assertEquals(cal.getTime(), DateUtils.date(1997, Calendar.DECEMBER, 12,
                11, 30, 5));

        final Calendar cal2 = (Calendar) cal.clone();
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);

        assertEquals(cal2.getTime(), DateUtils
                .date(1997, Calendar.DECEMBER, 12));
    }


    public void testFirstDayOfYear() {
        assertEquals(DateUtils.date(2005, Calendar.JANUARY, 1), DateUtils
                .firstDayOfYear(DateUtils.date(2005, Calendar.SEPTEMBER, 23)));
    }


    public void testLastDayOfYear() {
        assertEquals(DateUtils.date(2005, Calendar.DECEMBER, 31), DateUtils
                .lastDayOfYear(DateUtils.date(2005, Calendar.SEPTEMBER, 23)));
    }


    public void testFirstDayOfMonth() {
        assertEquals(DateUtils.date(2005, Calendar.SEPTEMBER, 1), DateUtils
                .firstDayOfMonth(DateUtils.date(2005, Calendar.SEPTEMBER, 23)));
    }


    public void testLastDayOfMonth() {
        assertEquals(DateUtils.date(2000, Calendar.FEBRUARY, 29), DateUtils
                .lastDayOfMonth(DateUtils.date(2000, Calendar.FEBRUARY, 23)));
    }
}

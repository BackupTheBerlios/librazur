/**
 * $Id: SqlUtils.java,v 1.3 2005/10/20 22:44:31 romale Exp $
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


import java.sql.Connection;
import java.sql.Statement;


/**
 * SQL utilities.
 */
public final class SqlUtils {
    private SqlUtils() {
    }


    /**
     * Quietly closes a SQL connection.
     */
    public static void close(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.close();
        } catch (Exception e) {
        }
    }


    /**
     * Quietly closes a SQL statement.
     */
    public static void close(Statement stmt) {
        if (stmt == null) {
            return;
        }
        try {
            stmt.close();
        } catch (Exception e) {
        }
    }


    /**
     * Quietly does a rollback on a connection.
     */
    public static void rollback(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.rollback();
        } catch (Exception e) {
        }
    }
}

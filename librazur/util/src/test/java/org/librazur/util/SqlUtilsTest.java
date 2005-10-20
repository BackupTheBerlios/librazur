/**
 * $Id: SqlUtilsTest.java,v 1.3 2005/10/20 22:44:31 romale Exp $
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
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

import org.easymock.MockControl;


public class SqlUtilsTest extends TestCase {
    public void testCloseConnection() {
        SqlUtils.close((Connection) null);

        final MockControl control = MockControl.createControl(Connection.class);
        final Connection mock = (Connection) control.getMock();
        try {
            mock.close();
        } catch (Exception ignore) {
        }
        control.replay();

        SqlUtils.close(mock);
        control.verify();

        control.reset();
        try {
            mock.close();
        } catch (Exception ignore) {
        }
        control.setThrowable(new SQLException("Boo!"));
        control.replay();

        SqlUtils.close(mock);
        control.verify();
    }


    public void testCloseStatement() {
        SqlUtils.close((Statement) null);

        final MockControl control = MockControl.createControl(Statement.class);
        final Statement mock = (Statement) control.getMock();
        try {
            mock.close();
        } catch (Exception ignore) {
        }
        control.replay();

        SqlUtils.close(mock);
        control.verify();

        control.reset();
        try {
            mock.close();
        } catch (Exception ignore) {
        }
        control.setThrowable(new SQLException("Boo!"));
        control.replay();

        SqlUtils.close(mock);
        control.verify();
    }


    public void testRollback() {
        SqlUtils.rollback(null);

        final MockControl control = MockControl.createControl(Connection.class);
        final Connection mock = (Connection) control.getMock();
        try {
            mock.rollback();
        } catch (Exception ignore) {
        }
        control.replay();

        SqlUtils.rollback(mock);
        control.verify();

        control.reset();
        try {
            mock.rollback();
        } catch (Exception ignore) {
        }
        control.setThrowable(new SQLException("Boo!"));
        control.replay();

        SqlUtils.rollback(mock);
        control.verify();
    }
}

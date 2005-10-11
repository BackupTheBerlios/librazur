/**
 * $Id: GtdListener.java,v 1.1 2005/10/11 21:21:23 romale Exp $
 *
 * Librazur
 * http://librazur.eu.org
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

package org.librazur.gtd.servlet.listener;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.gtd.GtdRuntimeException;
import org.librazur.gtd.UnknownUserException;
import org.librazur.gtd.model.User;
import org.librazur.gtd.service.GtdService;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class GtdListener implements ServletContextListener {
    private final Log log = LogFactory.getLog(getClass());


    public void contextInitialized(ServletContextEvent evt) {
        final WebApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(evt.getServletContext());
        initSchema(context);
    }


    public void contextDestroyed(ServletContextEvent evt) {
        final WebApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(evt.getServletContext());
        stopHsqldb(context);
    }


    private void stopHsqldb(WebApplicationContext context) {
        final DataSource dataSource;
        try {
            dataSource = (DataSource) context.getBean("dataSource");
        } catch (BeansException e) {
            log.error("Unable to get DataSource from context", e);
            return;
        }

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            final DatabaseMetaData meta = conn.getMetaData();

            final String driverName = meta.getDriverName();
            log.debug("Found JDBC driver: " + driverName);

            if (driverName.contains("HSQL")) {
                log.info("Hsqldb database was detected: "
                        + "closing using SHUTDOWN");
                conn.prepareStatement("SHUTDOWN").execute();
            }
        } catch (Exception e) {
            throw new GtdRuntimeException(
                    "Error while closing Hsqldb database", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }


    private void initSchema(WebApplicationContext context) {
        final GtdService gtdService = (GtdService) context
                .getBean("gtdService");

        final String adminLogin = "admin";
        final String adminPassword = "admin";

        try {
            gtdService.getUser(adminLogin);
        } catch (UnknownUserException e) {
            log.info("No admin user was detected: " + "creating new user '"
                    + adminLogin + "' with password '" + adminPassword + "'");
            final User admin = gtdService.createUser(adminLogin, adminPassword,
                    null, true);

            log.info("Creating new task for admin");
            gtdService.createTask(admin, "Urgent", "Set a new admin password");
        }
    }
}

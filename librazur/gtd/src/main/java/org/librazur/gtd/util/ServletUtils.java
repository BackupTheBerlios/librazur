/**
 * $Id: ServletUtils.java,v 1.2 2005/10/20 22:44:26 romale Exp $
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

package org.librazur.gtd.util;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


public final class ServletUtils {
    private ServletUtils() {
    }


    public static Long getLongParameter(HttpServletRequest req, String name)
            throws ServletException {
        final Long param;
        try {
            param = Long.parseLong(req.getParameter(name));
        } catch (Exception e) {
            throw new ServletException("Parameter " + name + " is required", e);
        }
        return param;
    }


    public static String getStringParameter(HttpServletRequest req, String name)
            throws ServletException {
        final String param = req.getParameter(name);
        if (name == null) {
            throw new ServletException("Parameter " + name + " is required");
        }
        return param;
    }
}

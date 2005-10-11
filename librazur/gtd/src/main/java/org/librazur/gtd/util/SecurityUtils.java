/**
 * $Id: SecurityUtils.java,v 1.1 2005/10/11 21:21:23 romale Exp $
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

package org.librazur.gtd.util;


import net.sf.acegisecurity.UserDetails;
import net.sf.acegisecurity.context.security.SecureContext;
import net.sf.acegisecurity.context.security.SecureContextUtils;


public final class SecurityUtils {
    private SecurityUtils() {
    }


    public static String getAuthenticatedUserName() {
        if (!isUserAuthenticated()) {
            return null;
        }

        final Object principal = SecureContextUtils.getSecureContext()
                .getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            final UserDetails details = (UserDetails) principal;
            return details.getUsername();
        }
        return principal.toString();
    }


    public static boolean isUserAuthenticated() {
        final SecureContext context;
        try {
            context = SecureContextUtils.getSecureContext();
        } catch (Exception e) {
            return false;
        }

        return context.getAuthentication() != null;
    }
}

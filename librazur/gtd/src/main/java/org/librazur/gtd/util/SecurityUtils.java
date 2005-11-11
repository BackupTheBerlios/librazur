/**
 * $Id: SecurityUtils.java,v 1.3 2005/11/11 15:04:27 romale Exp $
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


import net.sf.acegisecurity.UserDetails;
import net.sf.acegisecurity.context.SecurityContextHolder;


public final class SecurityUtils {
    private SecurityUtils() {
    }


    public static String getAuthenticatedUserName() {
        if (!isUserAuthenticated()) {
            return null;
        }

        final Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            final UserDetails details = (UserDetails) principal;
            return details.getUsername();
        }
        return principal.toString();
    }


    public static boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }
}

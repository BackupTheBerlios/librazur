/**
 * $Id: AcegiSecurityAuthenticationDao.java,v 1.2 2005/10/20 22:44:26 romale Exp $
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


import org.apache.commons.lang.StringUtils;
import org.librazur.gtd.model.User;
import org.librazur.gtd.model.dao.UserDao;
import org.springframework.dao.DataAccessException;

import net.sf.acegisecurity.GrantedAuthority;
import net.sf.acegisecurity.GrantedAuthorityImpl;
import net.sf.acegisecurity.UserDetails;
import net.sf.acegisecurity.providers.dao.AuthenticationDao;
import net.sf.acegisecurity.providers.dao.UsernameNotFoundException;


public class AcegiSecurityAuthenticationDao implements AuthenticationDao {
    private final UserDao userDao;


    public AcegiSecurityAuthenticationDao(final UserDao userDao) {
        if (userDao == null) {
            throw new NullPointerException("userDao");
        }
        this.userDao = userDao;
    }


    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException, DataAccessException {
        final User user = userDao.findByLogin(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Unable to find user: "
                    + userName);
        }

        final String login = user.getLogin();
        final String password = StringUtils.defaultString(user.getPassword(),
                StringUtils.EMPTY);
        final boolean enabled = true;
        final boolean accountNonExpired = true;
        final boolean credentialsNonExpired = true;
        final boolean accountNonLocked = true;

        final GrantedAuthority[] authorities;
        if (user.getAdmin()) {
            // hey! we have an admin here!
            authorities = new GrantedAuthority[] {
                    new GrantedAuthorityImpl("ROLE_USER"),
                    new GrantedAuthorityImpl("ROLE_ADMIN") };
        } else {
            authorities = new GrantedAuthority[] { new GrantedAuthorityImpl(
                    "ROLE_USER") };
        }

        return new net.sf.acegisecurity.providers.dao.User(login, password,
                enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
    }
}

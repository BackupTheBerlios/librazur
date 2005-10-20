/**
 * $Id: HibernateUserDao.java,v 1.2 2005/10/20 22:44:25 romale Exp $
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

package org.librazur.gtd.model.dao.impl;


import java.sql.SQLException;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.librazur.gtd.model.User;
import org.librazur.gtd.model.dao.UserDao;
import org.springframework.orm.hibernate3.HibernateCallback;


public class HibernateUserDao extends AbstractHibernateDao<User> implements
        UserDao {
    private final Log log = LogFactory.getLog(getClass());


    public HibernateUserDao() {
        super(User.class);
    }


    public User findByLogin(final String login) {
        return (User) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                final Collection<User> users = findByProperty("login", login);
                if (users.isEmpty()) {
                    return null;
                }
                if (users.size() > 1) {
                    log
                            .warn("More than one user with the same login: "
                                    + login);
                }

                return users.iterator().next();
            }
        });
    }


    public Integer getAdminUserCount() {
        return (Integer) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        return ((Number) session.createQuery(
                                "select count(*) from obj in class "
                                        + getModelClass().getName()
                                        + " where obj.admin = :admin")
                                .setParameter("admin", true).uniqueResult())
                                .intValue();
                    }
                });
    }
}

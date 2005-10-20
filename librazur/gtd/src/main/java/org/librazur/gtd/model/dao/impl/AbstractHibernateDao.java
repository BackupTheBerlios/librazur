/**
 * $Id: AbstractHibernateDao.java,v 1.2 2005/10/20 22:44:25 romale Exp $
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


import java.io.Serializable;
import java.util.Collection;

import org.librazur.gtd.model.dao.Dao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class AbstractHibernateDao<T> extends HibernateDaoSupport implements
        Dao<T> {
    private final Class<T> modelClass;


    public AbstractHibernateDao(final Class<T> modelClass) {
        if (modelClass == null) {
            throw new NullPointerException("modelClass");
        }
        this.modelClass = modelClass;
    }


    protected final Class<T> getModelClass() {
        return modelClass;
    }


    public void save(T obj) {
        getHibernateTemplate().saveOrUpdate(obj);
    }


    public void delete(T obj) {
        getHibernateTemplate().delete(obj);
    }


    @SuppressWarnings("unchecked")
    public T findById(Serializable id) {
        return (T) getHibernateTemplate().load(getModelClass(), id);
    }


    @SuppressWarnings("unchecked")
    public Collection<T> list() {
        return getHibernateTemplate().find(
                "from obj in class " + getModelClass().getName());
    }


    @SuppressWarnings("unchecked")
    protected Collection<T> findByProperty(String name, Object value) {
        return getHibernateTemplate().find(
                "from obj in class " + getModelClass().getName()
                        + " where obj." + name + " = ?", value);
    }
}

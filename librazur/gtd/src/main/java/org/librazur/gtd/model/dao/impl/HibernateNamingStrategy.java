/**
 * $Id: HibernateNamingStrategy.java,v 1.1 2005/10/11 21:21:23 romale Exp $
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

package org.librazur.gtd.model.dao.impl;


import org.hibernate.cfg.DefaultNamingStrategy;


public class HibernateNamingStrategy extends DefaultNamingStrategy {
    private String prefix = "gtd_";


    public String classToTableName(String className) {
        return prefix + super.classToTableName(className);
    }


    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}

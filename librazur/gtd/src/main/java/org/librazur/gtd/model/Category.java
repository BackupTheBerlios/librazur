/**
 * $Id: Category.java,v 1.2 2005/10/20 22:44:25 romale Exp $
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

package org.librazur.gtd.model;


import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratorType;
import javax.persistence.Id;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;


@Entity(access = AccessType.FIELD)
public class Category extends AbstractModelObject {
    @Id(generate = GeneratorType.AUTO)
    private Long id;

    @NotNull
    @Length(min = 1, max = 200)
    @Column(nullable = false, length = 200, unique = true)
    private String name;


    public Category() {
    }


    public Category(final String name) {
        setName(name);
    }


    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int hashCode() {
        return name.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Category)) {
            return false;
        }
        return hashCode() == obj.hashCode();
    }


    @Override
    public String toString() {
        return "Category[id=" + id + ", name=" + name + "]";
    }
}

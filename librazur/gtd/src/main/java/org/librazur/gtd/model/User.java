/**
 * $Id: User.java,v 1.1 2005/10/11 21:21:23 romale Exp $
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

package org.librazur.gtd.model;


import java.util.Collection;

import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratorType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;


@Entity(access = AccessType.FIELD)
public class User extends AbstractModelObject implements Comparable<User> {
    @NotNull
    @Length(min = 1, max = 32)
    @Id(generate = GeneratorType.NONE)
    @Column(length = 32)
    private String login;

    @NotNull
    @Length(min = 1, max = 200)
    @Column(length = 200, nullable = false)
    private String password;

    @Length(min = 0, max = 200)
    @Column(length = 200)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Boolean admin = Boolean.FALSE;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Collection<Task> tasks;


    public User() {
    }


    public User(final String login) {
        this.login = login;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getLogin() {
        return login;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Boolean getAdmin() {
        return admin;
    }


    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }


    public Collection<Task> getTasks() {
        return tasks;
    }


    public void setTasks(Collection<Task> tasks) {
        this.tasks = tasks;
    }


    @Override
    public int hashCode() {
        return login.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof User)) {
            return false;
        }
        return hashCode() == obj.hashCode();
    }


    @Override
    public String toString() {
        return "User[login=" + login + ", name=" + name + ", admin=" + admin
                + "]";
    }


    public int compareTo(User other) {
        return login.compareTo(other.login);
    }
}

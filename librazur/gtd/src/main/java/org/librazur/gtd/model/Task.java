/**
 * $Id: Task.java,v 1.1 2005/10/11 21:21:23 romale Exp $
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


import java.util.Date;

import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratorType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;


@Entity(access = AccessType.FIELD)
public class Task extends AbstractModelObject {
    @Id(generate = GeneratorType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @Column(nullable = false)
    private User user;

    @NotNull
    @Length(min = 1, max = 200)
    @Column(length = 200, nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Date created = new Date();

    private Date finished;

    @NotNull
    @Column(nullable = false)
    private Boolean done = Boolean.FALSE;

    @NotNull
    @Column(nullable = false)
    @ManyToOne
    private Category category;


    public Task() {
    }


    public Task(final Category category, final String title) {
        setCategory(category);
        setTitle(title);
    }


    public Date getCreated() {
        return created;
    }


    public Boolean getDone() {
        return done;
    }


    public void setDone(Boolean done) {
        this.done = done;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Long getId() {
        return id;
    }


    public Date getFinished() {
        return finished;
    }


    public void setFinished(Date finished) {
        this.finished = finished;
    }


    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }


    @Override
    public int hashCode() {
        return (created + title).hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Task)) {
            return false;
        }
        return hashCode() == obj.hashCode();
    }


    @Override
    public String toString() {
        return "Task[id=" + id + ", category=" + category + ", title=" + title
                + "]";
    }
}

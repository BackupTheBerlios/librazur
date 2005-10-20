/**
 * $Id: TaskForm.java,v 1.2 2005/10/20 22:44:26 romale Exp $
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

package org.librazur.gtd.servlet.action;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


public class TaskForm extends ActionForm {
    private String title;
    private Boolean done;
    private String category;


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
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


    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        final ActionErrors errors = new ActionErrors();
        if (StringUtils.trimToNull(category) == null) {
            errors.add("category", new ActionMessage(
                    "error.task.category.required"));
        }
        if (StringUtils.trimToNull(title) == null) {
            errors.add("title", new ActionMessage("error.task.title.required"));
        }

        return errors;
    }
}

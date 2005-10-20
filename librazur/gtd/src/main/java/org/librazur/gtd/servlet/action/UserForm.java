/**
 * $Id: UserForm.java,v 1.2 2005/10/20 22:44:25 romale Exp $
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


public class UserForm extends ActionForm {
    private String login;
    private String password;
    private String oldPassword;
    private String name;
    private Boolean admin = Boolean.FALSE;


    public String getLogin() {
        return login;
    }


    public void setLogin(String login) {
        this.login = login;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public Boolean getAdmin() {
        return admin;
    }


    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getOldPassword() {
        return oldPassword;
    }


    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }


    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        final ActionErrors errors = new ActionErrors();
        if (StringUtils.trimToNull(login) == null) {
            errors.add("login", new ActionMessage("error.user.login.required"));
        }

        return errors;
    }
}

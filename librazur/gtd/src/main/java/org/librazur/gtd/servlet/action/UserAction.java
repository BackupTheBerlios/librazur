/**
 * $Id: UserAction.java,v 1.2 2005/10/20 22:44:26 romale Exp $
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


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.MappingDispatchAction;
import org.librazur.gtd.LastAdminException;
import org.librazur.gtd.UnknownUserException;
import org.librazur.gtd.model.User;
import org.librazur.gtd.service.GtdService;
import org.librazur.gtd.util.SecurityUtils;
import org.librazur.gtd.util.ServletUtils;


public class UserAction extends MappingDispatchAction {
    private final Log log = LogFactory.getLog(getClass());
    private GtdService gtdService;


    public void setGtdService(GtdService gtdService) {
        this.gtdService = gtdService;
    }


    public ActionForward newUser(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //saveToken(req);

        return mapping.findForward(Targets.SUCCESS);
    }


    public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
        final UserForm userForm = (UserForm) form;
        
        final ActionMessages errors = new ActionMessages();

        /*
        if (!isTokenValid(req)) {
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "error.transaction.token"));
        }
        resetToken(req);
        */

        final String login = StringUtils.trimToNull(userForm.getLogin());
        final String password = StringUtils.trimToNull(userForm.getPassword());
        final String name = StringUtils.trimToNull(userForm.getName());
        final Boolean admin = userForm.getAdmin();

        // checking if the login already exists
        try {
            gtdService.getUser(login);
            // if everything is ok, we shouldn't go any further

            errors.add("login", new ActionMessage("error.user.login.exists",
                    login));
        } catch (UnknownUserException e) {
        }

        if (password == null) {
            errors.add("password", new ActionMessage(
                    "error.user.password.required"));
        }

        if (errors.isEmpty()) {
            log.info("Adding new user: " + login);
            try {
                gtdService.createUser(login, password, name, admin);
            } catch (Exception e) {
                log.error("Error while adding user: " + login, e);
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        "error.generic", e.getMessage()));
            }
        }

        if (!errors.isEmpty()) {
            final ActionMessages finalErrors = new ActionMessages();
            finalErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "error.user.creation"));
            finalErrors.add(errors);
            saveErrors(req, finalErrors);
            return mapping.findForward(Targets.FAILURE);
        }

        final ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                "message.user.created", login));
        saveMessages(req, msgs);

        return mapping.findForward(Targets.SUCCESS);
    }


    public ActionForward edit(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
        final User user = gtdService.getCurrentUser();

        // filling the UserForm with data from the current authenticated user
        final UserForm userForm = (UserForm) form;
        userForm.setLogin(user.getLogin());
        userForm.setName(user.getName());
        userForm.setAdmin(user.getAdmin());

        //saveToken(req);

        return mapping.findForward(Targets.SUCCESS);
    }


    public ActionForward update(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
        final UserForm userForm = (UserForm) form;

        final ActionMessages errors = new ActionMessages();

        /*
        if (!isTokenValid(req)) {
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "error.transaction.token"));
        }
        resetToken(req);
        */

        final String login = StringUtils.trimToNull(userForm.getLogin());
        final String oldPassword = StringUtils.trimToNull(userForm
                .getOldPassword());
        final String newPassword = StringUtils.trimToNull(userForm
                .getPassword());
        final User user = gtdService.getUser(login);
        boolean passwordToUpdate = false;
        if (oldPassword != null || newPassword != null) {
            log.info("Checking user password: " + login);

            if (!user.getPassword().equals(oldPassword)) {
                errors.add("oldPassword", new ActionMessage(
                        "error.user.password.invalid"));
            } else {
                // password is ok!
                passwordToUpdate = true;
            }
        }

        if (oldPassword != null && newPassword == null) {
            errors.add("password", new ActionMessage(
                    "error.user.password.required"));
        }

        if (errors.isEmpty()) {
            final String name = StringUtils.trimToNull(userForm.getName());

            log.info("Updating user: " + login);
            try {
                gtdService.updateUser(login, passwordToUpdate ? newPassword
                        : null, name, userForm.getAdmin());
            } catch (LastAdminException e) {
                log.error("Error while updating user: " + login, e);
                errors.add("admin", new ActionMessage(
                        "error.user.update.lastAdmin"));
            } catch (Exception e) {
                log.error("Error while updating user: " + login, e);
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        "error.generic", e.getMessage()));
            }
        }

        if (!errors.isEmpty()) {
            final ActionMessages finalErrors = new ActionMessages();
            finalErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "error.user.update"));
            finalErrors.add(errors);
            saveErrors(req, finalErrors);

            return mapping.findForward(Targets.FAILURE);
        }

        if (passwordToUpdate) {
            // we need the user to logoff if he changed his password:
            // so we invalidate his session
            req.getSession().invalidate();
            return mapping.findForward("logout");
        }

        final ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                "message.user.updated", login));
        saveMessages(req, msgs);

        return mapping.findForward(Targets.SUCCESS);
    }


    public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
        final String login = ServletUtils.getStringParameter(req, "login");
        if (login.equals(SecurityUtils.getAuthenticatedUserName())) {
            throw new ServletException("Unable to delete current user: "
                    + login);
        }

        log.info("Deleting user: " + login);
        try {
            gtdService.deleteUser(login);
        } catch (Exception e) {
            log.error("Error while deleting user: " + login, e);

            final ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "error.user.deletion", login));
            saveErrors(req, errors);
            return mapping.findForward(Targets.FAILURE);
        }

        final ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                "message.user.deleted", login));
        saveMessages(req, msgs);

        return mapping.findForward(Targets.SUCCESS);
    }


    public ActionForward listUsersToDelete(ActionMapping mapping,
            ActionForm form, HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        final Collection<User> users = gtdService.listUsers();
        if (log.isDebugEnabled()) {
            log.debug("Deletable users: " + users);
        }

        // we don't want the user to select himself for deletion
        if (log.isDebugEnabled()) {
            log.debug("Removing user " + gtdService.getCurrentUser().getLogin()
                    + " from deletable users");
        }
        users.remove(gtdService.getCurrentUser());
        if (log.isDebugEnabled()) {
            log.debug("Deletable users (without current user): " + users);
        }

        final List<User> sortedUsers = new ArrayList<User>(users);
        Collections.sort(sortedUsers);

        req.setAttribute("users", sortedUsers);

        return mapping.findForward(Targets.SUCCESS);
    }
}

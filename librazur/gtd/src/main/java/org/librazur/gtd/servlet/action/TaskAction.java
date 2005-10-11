/**
 * $Id: TaskAction.java,v 1.1 2005/10/11 21:21:23 romale Exp $
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

package org.librazur.gtd.servlet.action;


import java.util.Collection;

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
import org.librazur.gtd.model.Task;
import org.librazur.gtd.model.User;
import org.librazur.gtd.service.GtdService;
import org.librazur.gtd.util.SecurityUtils;
import org.librazur.gtd.util.ServletUtils;


public class TaskAction extends MappingDispatchAction {
    private final Log log = LogFactory.getLog(getClass());
    private GtdService gtdService;


    public void setGtdService(GtdService gtdService) {
        this.gtdService = gtdService;
    }


    public ActionForward list(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
        final Collection<Task> tasks = gtdService.listTasks();
        req.setAttribute("tasks", tasks);

        final User user = gtdService.getCurrentUser();
        req.setAttribute("user", user);

        return mapping.findForward(Targets.SUCCESS);
    }


    public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
        final TaskForm taskForm = (TaskForm) form;
        final String title = StringUtils.trimToNull(taskForm.getTitle());
        final String category = StringUtils.trimToNull(taskForm.getCategory());

        final ActionMessages errors = new ActionMessages();

        final String login = SecurityUtils.getAuthenticatedUserName();
        log.info("Adding task for user " + login + ": " + title);
        try {
            final User user = gtdService.getCurrentUser();
            gtdService.createTask(user, category, title);
        } catch (Exception e) {
            log.error("Error while adding task for user " + login + ": "
                    + title, e);

            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "error.generic", e));
        }

        if (!errors.isEmpty()) {
            final ActionMessages finalErrors = new ActionMessages();
            finalErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "error.task.creation"));
            finalErrors.add(errors);
            saveErrors(req, finalErrors);
            return mapping.findForward(Targets.FAILURE);
        }

        final ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                "message.task.created", title));
        saveMessages(req, msgs);
        
        return mapping.findForward(Targets.SUCCESS);
    }


    public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
        final Long taskId = ServletUtils.getLongParameter(req, "id");

        final String title;

        log.info("Deleting task: " + taskId);
        try {
            title = gtdService.getTask(taskId).getTitle();
            gtdService.deleteTask(taskId);
        } catch (Exception e) {
            final ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "error.task.deletion"));
            saveErrors(req, errors);
            return mapping.findForward(Targets.FAILURE);
        }

        final ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                "message.task.deleted", title));
        saveMessages(req, msgs);

        return mapping.findForward(Targets.SUCCESS);
    }


    public ActionForward setDone(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
        gtdService.setTaskDone(ServletUtils.getLongParameter(req, "id"));

        return mapping.findForward(Targets.SUCCESS);
    }


    public ActionForward setUndone(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
        gtdService.setTaskUndone(ServletUtils.getLongParameter(req, "id"));

        return mapping.findForward(Targets.SUCCESS);
    }
}

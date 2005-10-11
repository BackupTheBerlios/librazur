/**
 * $Id: HelloStrutsAction.java,v 1.1 2005/10/11 21:21:24 romale Exp $
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


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.librazur.gtd.service.HelloService;


public class HelloStrutsAction extends Action {
    private final Log log = LogFactory.getLog(getClass());
    private HelloService helloService;


    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }


    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
        final String userName = System.getProperty("user.name");
        log.info("Found user name: " + userName);
        req.setAttribute("text", helloService.hello(userName));

        return mapping.findForward(Targets.SUCCESS);
    }
}

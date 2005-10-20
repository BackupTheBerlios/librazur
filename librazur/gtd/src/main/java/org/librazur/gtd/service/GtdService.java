/**
 * $Id: GtdService.java,v 1.2 2005/10/20 22:44:26 romale Exp $
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

package org.librazur.gtd.service;


import java.util.Collection;

import org.librazur.gtd.model.Task;
import org.librazur.gtd.model.User;


public interface GtdService {
    Task createTask(User user, String categoryName, String title);


    void deleteTask(Long taskId);


    Task updateTask(Long taskId, String title);


    void setTaskDone(Long taskId);


    void setTaskUndone(Long taskId);


    Task getTask(Long taskId);


    Collection<Task> listTasks();


    User getCurrentUser();


    User getUser(String login);


    Collection<User> listUsers();


    User createUser(String login, String password, String name, boolean admin);


    User updateUser(String login, String newPassword, String name, boolean admin);


    void deleteUser(String login);
}

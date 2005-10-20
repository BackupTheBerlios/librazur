/**
 * $Id: GtdServiceImpl.java,v 1.2 2005/10/20 22:44:26 romale Exp $
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

package org.librazur.gtd.service.impl;


import java.util.Collection;
import java.util.Date;

import org.librazur.gtd.InvalidCredentialException;
import org.librazur.gtd.LastAdminException;
import org.librazur.gtd.RequiredAuthenticationException;
import org.librazur.gtd.UnknownUserException;
import org.librazur.gtd.model.Category;
import org.librazur.gtd.model.Task;
import org.librazur.gtd.model.User;
import org.librazur.gtd.model.dao.CategoryDao;
import org.librazur.gtd.model.dao.TaskDao;
import org.librazur.gtd.model.dao.UserDao;
import org.librazur.gtd.service.GtdService;
import org.librazur.gtd.util.SecurityUtils;


public class GtdServiceImpl implements GtdService {
    private final TaskDao taskDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;


    public GtdServiceImpl(final CategoryDao categoryDao, final TaskDao taskDao,
            final UserDao userDao) {
        if (categoryDao == null) {
            throw new NullPointerException("categoryDao");
        }
        if (taskDao == null) {
            throw new NullPointerException("taskDao");
        }
        if (userDao == null) {
            throw new NullPointerException("userDao");
        }
        this.categoryDao = categoryDao;
        this.taskDao = taskDao;
        this.userDao = userDao;
    }


    public Task createTask(User user, String categoryName, String title) {
        final Collection<Category> categories = categoryDao
                .findByName(categoryName);
        final Category category;
        if (categories.isEmpty()) {
            // creating new category
            category = new Category(categoryName);
            categoryDao.save(category);
        } else {
            // using existing category
            category = categories.iterator().next();
        }

        final Task task = new Task(category, title);
        task.setUser(user);
        taskDao.save(task);

        return task;
    }


    public void deleteTask(Long taskId) {
        final Task task = taskDao.findById(taskId);
        checkUserRights(task);
        taskDao.delete(task);
    }


    public Task updateTask(Long taskId, String title) {
        final Task task = taskDao.findById(taskId);
        checkUserRights(task);
        task.setTitle(title);
        taskDao.save(task);
        return task;
    }


    public Task getTask(Long taskId) {
        return taskDao.findById(taskId);
    }


    public void setTaskDone(Long taskId) {
        final Task task = taskDao.findById(taskId);
        checkUserRights(task);
        task.setDone(true);
        task.setFinished(new Date());
        taskDao.save(task);
    }


    public void setTaskUndone(Long taskId) {
        final Task task = taskDao.findById(taskId);
        checkUserRights(task);
        task.setDone(false);
        task.setFinished(null);
        taskDao.save(task);
    }


    public Collection<Task> listTasks() {
        return taskDao.findByUser(getCurrentUser());
    }


    private void checkUserRights(Task task) {
        final User currentUser = getCurrentUser();
        if (currentUser.getAdmin()) {
            // an admin can do anything!
            return;
        }
        if (!currentUser.equals(task.getUser())) {
            throw new InvalidCredentialException("User "
                    + currentUser.getLogin()
                    + " doesn't have the rights to manage the task "
                    + task.getId());
        }
    }


    public User getCurrentUser() {
        final String login = SecurityUtils.getAuthenticatedUserName();
        if (login == null) {
            throw new RequiredAuthenticationException(
                    "No user is currently authenticated");
        }

        return getUser(login);
    }


    public User createUser(String login, String password, String name,
            boolean admin) {
        final User user = new User(login);
        user.setPassword(password);
        user.setName(name);
        user.setAdmin(admin);
        userDao.save(user);
        return user;
    }


    public void deleteUser(String login) {
        final User user = getUser(login);
        userDao.delete(user);
    }


    public User getUser(String login) {
        final User user = userDao.findByLogin(login);
        if (user == null) {
            throw new UnknownUserException("Unknown user: " + login, login);
        }
        return user;
    }


    public User updateUser(String login, String password, String name,
            boolean admin) {
        final User user = getUser(login);

        if (password != null) {
            user.setPassword(password);
        }
        user.setName(name);

        if (!admin) {
            final int adminCount = userDao.getAdminUserCount();
            if (adminCount == 1 && user.getAdmin()) {
                throw new LastAdminException(
                        "The last administrator cannot become a regular user.");
            }
        }
        user.setAdmin(admin);
        userDao.save(user);
        return user;
    }


    public Collection<User> listUsers() {
        return userDao.list();
    }
}

/**
 * $Id: MainClassLauncher.java,v 1.1 2005/11/24 10:10:10 romale Exp $
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

package org.librazur.jar;


import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * Main class launcher.
 */
public class MainClassLauncher {
    private final Class mainClass;
    private WeakReference<Method> mainMethodRef;


    public MainClassLauncher(final Class mainClass) {
        if (mainClass == null) {
            throw new NullPointerException("mainClass");
        }
        this.mainClass = mainClass;
    }


    /**
     * Invokes the main method in the class specified in the constructor.
     */
    public void launch(String... args) throws IllegalAccessException,
            InvocationTargetException {
        getMainMethod().invoke(null, new Object[] { args });
    }


    /**
     * Returns the main method of the class specified in the constructor.
     */
    public Method getMainMethod() {
        Method mainMethod = null;
        if (mainMethodRef != null) {
            mainMethod = mainMethodRef.get();
        }

        if (mainMethod == null) {
            try {
                mainMethod = mainClass.getMethod("main",
                        new Class[] { String[].class });
                final int mod = mainMethod.getModifiers();
                if (!Modifier.isStatic(mod) || !Modifier.isPublic(mod)
                        || !Void.TYPE.equals(mainMethod.getReturnType())) {
                    throw new IllegalStateException(
                            "Main method is neither public or static, or does not return void");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Instance object has no valid main method: "
                                + mainClass.getName(), e);
            }

            mainMethodRef = new WeakReference<Method>(mainMethod);
        }

        return mainMethod;
    }
}

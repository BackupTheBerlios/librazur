/**
 * $Id: Resources.java,v 1.3 2005/12/07 16:00:19 romale Exp $
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

package org.librazur.blc;


import java.awt.Image;
import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.Icon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.util.StringUtils;
import org.librazur.util.cache.IconCache;
import org.librazur.util.cache.ImageCache;


/**
 * Resource utilities.
 */
public class Resources {
    private static final Log log = LogFactory.getLog(Resources.class);
    private static WeakReference<ResourceBundle> bundleRef = new WeakReference<ResourceBundle>(
            null);
    private static ImageCache imageCache = new ImageCache();
    private static IconCache iconCache = new IconCache(imageCache);


    private static ResourceBundle getBundle() {
        ResourceBundle bundle = bundleRef.get();
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(Resources.class.getPackage()
                    .getName()
                    + ".messages");
            bundleRef = new WeakReference<ResourceBundle>(bundle);
        }
        return bundle;
    }


    /**
     * Returns an message with arguments based on current locale.
     */
    public static String i18n(String key, Object... args) {
        if (key == null) {
            throw new NullPointerException("key");
        }

        final String msg;
        try {
            msg = getBundle().getString(key).trim();
        } catch (MissingResourceException e) {
            log.warn("Missing i18n key: " + key, e);
            return "???" + key + "???";
        }

        if (args.length == 0 || args == null) {
            return msg;
        }

        return MessageFormat.format(msg, args);
    }


    /**
     * Returns an {@link Icon} loaded from a resource looked up by a key.
     */
    public static Icon icon(String key) {
        return iconCache.get(i18n(key));
    }


    /**
     * Returns an {@link Image} loaded from a resource looked up by a key.
     */
    public static Image image(String key) {
        return imageCache.get(i18n(key));
    }


    /**
     * Returns the application version, or an empty string if unknown.
     */
    public static String version() {
        final Package pkg = BLC.class.getPackage();
        return StringUtils.defaultString(pkg.getImplementationVersion(), "");
    }
}

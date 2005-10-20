/**
 * $Id: BLC.java,v 1.2 2005/10/20 22:44:13 romale Exp $
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


import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.WeakHashMap;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.util.StringUtils;

import com.l2fprod.common.swing.JDirectoryChooser;


/**
 * Application service locator.
 */
public final class BLC {
    private static final Log log = LogFactory.getLog(BLC.class);
    private static final ResourceBundle bundle = ResourceBundle
            .getBundle(BLC.class.getPackage().getName() + ".Resources");
    private static final Map<String, Image> imageCache = new WeakHashMap<String, Image>();
    private static WeakReference<JFileChooser> fileChooserRef = new WeakReference<JFileChooser>(
            null);
    private static WeakReference<JDirectoryChooser> dirChooserRef = new WeakReference<JDirectoryChooser>(
            null);
    private static File lastDir = new File(System.getProperty("user.dir"));


    /**
     * Returns the application version, or an empty string if unknown.
     */
    public static String version() {
        final Package pkg = BLC.class.getPackage();
        return StringUtils.defaultString(pkg.getImplementationVersion(), "");
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
            msg = bundle.getString(key).trim();
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
     * Returns an image loaded from a resource looked up by a key.
     */
    public static Image image(String key) {
        final String urlPath = BLC.i18n(key);

        Image image = imageCache.get(urlPath);
        if (image != null) {
            return image;
        }

        final URL url = BLC.class.getResource(urlPath);
        if (url == null) {
            throw new IllegalStateException("Resource not found: " + urlPath);
        }

        log.debug("Loading image from: " + url);

        try {
            image = ImageIO.read(url);
            // the image is cached for reuse
            imageCache.put(urlPath, image);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to load image: " + key, e);
        }

        return image;
    }


    /**
     * Opens a dialog to select a file.
     */
    public static File selectFile(Component parent) {
        JFileChooser fc = fileChooserRef.get();
        if (fc == null) {
            fc = new JFileChooser(lastDir);
            fileChooserRef = new WeakReference<JFileChooser>(fc);
        }
        fc.setCurrentDirectory(lastDir);

        if (fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        lastDir = fc.getSelectedFile();

        return fc.getSelectedFile();
    }


    /**
     * Opens a dialog to select a directory.
     */
    public static File selectDir(Component parent) {
        JDirectoryChooser dc = dirChooserRef.get();
        if (dc == null) {
            dc = new JDirectoryChooser(lastDir);
            dirChooserRef = new WeakReference<JDirectoryChooser>(dc);
        }
        dc.setCurrentDirectory(lastDir);

        if (dc.showOpenDialog(parent) != JDirectoryChooser.APPROVE_OPTION) {
            return null;
        }
        lastDir = dc.getSelectedFile().getParentFile();

        return dc.getSelectedFile();
    }
}

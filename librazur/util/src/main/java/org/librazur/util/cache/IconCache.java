/**
 * $Id: IconCache.java,v 1.1 2005/10/26 09:09:42 romale Exp $
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

package org.librazur.util.cache;


import java.awt.Image;
import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 * Swing's <tt>Icon</tt> cache.
 */
public class IconCache extends ImageCache {
    private final Map<String, Icon> cache = new WeakHashMap<String, Icon>(1);


    /**
     * Gets an <tt>Icon</tt> from the image cache.
     * 
     * @return <tt>null</tt> if no image is available
     */
    public Icon getIcon(String key) {
        Icon icon = cache.get(key);
        if (icon == null) {
            final Image image = get(key);
            if (image == null) {
                return null;
            }
            icon = new ImageIcon(image);
            cache.put(key, icon);
        }
        return icon;
    }
}

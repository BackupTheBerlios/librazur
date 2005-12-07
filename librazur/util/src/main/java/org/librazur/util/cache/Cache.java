/**
 * $Id: Cache.java,v 1.3 2005/12/07 15:52:31 romale Exp $
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


/**
 * Generic cache object.
 * 
 * @since 1.2
 * @param <T> cached object type
 * @param <K> key type to reference the object
 */
public interface Cache<T, K> {
    /**
     * Gets an object from the cache.
     * 
     * @return <code>null</code> if the object couldn't be loaded from the
     *         cache
     */
    T get(K key);
}

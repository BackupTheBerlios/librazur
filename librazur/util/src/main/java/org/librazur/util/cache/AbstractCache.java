/**
 * $Id: AbstractCache.java,v 1.1 2005/10/26 09:09:42 romale Exp $
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


import java.util.Map;
import java.util.WeakHashMap;


/**
 * Abstract implementation of <tt>Cache</tt>. Concrete implementations should
 * extends this class and implements the <tt>load()</tt> method.
 */
public abstract class AbstractCache<T, K> implements Cache<T, K> {
    private final Map<K, T> cache = new WeakHashMap<K, T>(1);


    public final T get(K key) {
        T obj = cache.get(key);
        if (obj == null) {
            // the object is not in the cache: let's load it
            try {
                obj = load(key);
            } catch (Exception e) {
                return null;
            }
            if (obj == null) {
                // unable to load the object
                return null;
            }
            // put the loaded object in the cache
            cache.put(key, obj);
        }
        return obj;
    }


    /**
     * Loads an object.
     * 
     * @return <tt>null</tt> if the object couldn't be loaded
     */
    protected abstract T load(K key);
}

/**
 * $Id: ImageCache.java,v 1.3 2005/12/05 14:48:43 romale Exp $
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


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


/**
 * Image cache.
 * 
 * @since 1.2
 */
public class ImageCache extends AbstractClassPathCache<BufferedImage> {
    @Override
    protected BufferedImage loadFromClassPath(URL url) {
        final BufferedImage image;
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            // error while loading the image
            return null;
        }
        return image;
    }
}

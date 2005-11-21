/**
 * $Id: FileImageCache.java,v 1.1 2005/11/21 01:30:15 romale Exp $
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

package org.librazur.ict.util;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.librazur.util.cache.AbstractCache;


/**
 * Image cache loaded from files. Images are converted to RGB before they are
 * loaded.
 */
public class FileImageCache extends AbstractCache<BufferedImage, File> {
    @Override
    protected BufferedImage load(File file) {
        final BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Unable to load from image from file: "
                            + file.getAbsolutePath(), e);
        }

        return ImageUtils.toRGBImage(image);
    }
}

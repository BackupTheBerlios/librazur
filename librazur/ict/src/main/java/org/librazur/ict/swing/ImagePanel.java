/**
 * $Id: ImagePanel.java,v 1.1 2005/11/21 01:30:15 romale Exp $
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

package org.librazur.ict.swing;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.librazur.ict.Resources;
import org.librazur.ict.util.ImageUtils;


public class ImagePanel extends JPanel {
    private final JLabel imageLabel = new JLabel();
    private BufferedImage image;


    public ImagePanel() {
        super(new BorderLayout());
        init();
        reset();
    }


    public BufferedImage getImage() {
        return image;
    }


    public void setImage(BufferedImage image) {
        this.image = image;
        imageLabel.setIcon(new ImageIcon(image));

        final Dimension imageSize = new Dimension(image.getWidth(), image
                .getHeight());
        setMinimumSize(imageSize);
        setPreferredSize(imageSize);
        revalidate();
    }


    public void reset() {
        final BufferedImage image = ImageUtils.toRGBImage(Resources
                .image("image.start"));
        setImage(image);
    }


    private void init() {
        imageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        imageLabel.setVerticalAlignment(SwingConstants.TOP);

        add(imageLabel, BorderLayout.CENTER);
    }
}

/**
 * $Id: MainFrame.java,v 1.1 2005/11/21 01:30:15 romale Exp $
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
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.io.File;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import net.java.swingfx.waitwithstyle.PerformanceInfiniteProgressPanel;

import org.librazur.ict.ICT;
import org.librazur.ict.Resources;
import org.librazur.ict.swing.action.AboutAction;
import org.librazur.ict.swing.action.ApplyFilterAction;
import org.librazur.ict.swing.action.ConfigureFilterAction;
import org.librazur.ict.swing.action.CopyImageAction;
import org.librazur.ict.swing.action.OpenImageAction;
import org.librazur.ict.swing.action.ReloadImageAction;
import org.librazur.ict.util.BigDecimalKernel;
import org.librazur.ict.util.TransferableImage;


public class MainFrame extends JFrame {
    private File imageFile;
    private ImagePanel imagePanel;
    private BigDecimalKernel bigDecimalKernel;
    private PerformanceInfiniteProgressPanel infiniteProgressPanel;


    public MainFrame() {
        super();
        init();
    }


    public File getImageFile() {
        return imageFile;
    }


    public BigDecimalKernel getBigDecimalKernel() {
        return bigDecimalKernel;
    }


    public void setBigDecimalKernel(BigDecimalKernel kernel) {
        this.bigDecimalKernel = kernel;
    }


    public void setFreezing(final boolean freeze) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                infiniteProgressPanel.setVisible(freeze);
            }
        });
    }


    public void openImage(File file) {
        imageFile = file;
        imagePanel.setImage(ICT.getInstance().loadImage(imageFile));
    }


    public void reloadImage() {
        if (imageFile == null) {
            imagePanel.reset();
        } else {
            openImage(imageFile);
        }
    }


    public void filterImage() {
        final BufferedImage image = imagePanel.getImage();
        final BufferedImage newImage = new BufferedImage(image.getWidth(),
                image.getHeight(), BufferedImage.TYPE_INT_RGB);

        final ConvolveOp cop = new ConvolveOp(bigDecimalKernel.createKernel(),
                ConvolveOp.EDGE_NO_OP, null);
        cop.filter(image, newImage);
        imagePanel.setImage(newImage);
    }


    public void copyImage() {
        final Image image = imagePanel.getImage();
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new TransferableImage(image), null);
    }


    private void init() {
        infiniteProgressPanel = new PerformanceInfiniteProgressPanel(false);
        setGlassPane(infiniteProgressPanel);

        bigDecimalKernel = new BigDecimalKernel();

        setIconImage(Resources.image("app.icon"));
        setTitle(Resources.i18n("app"));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ICT.getInstance().stop();
            }
        });

        final JScrollPane scrollPane = new JScrollPane(createMainPanel());
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(createToolBar(), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        setContentPane(panel);
    }


    private JPanel createMainPanel() {
        imagePanel = new ImagePanel();

        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.add(imagePanel);

        return panel;
    }


    private JToolBar createToolBar() {
        final JToolBar toolBar = new JToolBar();
        toolBar.setBorderPainted(false);
        toolBar.setFloatable(false);

        toolBar.add(new OpenImageAction(this));
        toolBar.addSeparator();
        toolBar.add(new CopyImageAction(this));
        toolBar.addSeparator();
        toolBar.add(new ConfigureFilterAction(this));
        toolBar.add(new ReloadImageAction(this));
        toolBar.add(new ApplyFilterAction(this));
        toolBar.add(Box.createGlue());
        toolBar.add(new AboutAction(this));

        return toolBar;
    }
}

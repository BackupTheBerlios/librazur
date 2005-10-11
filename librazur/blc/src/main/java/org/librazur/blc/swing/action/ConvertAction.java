/**
 * $Id: ConvertAction.java,v 1.1 2005/10/11 21:17:40 romale Exp $
 *
 * Librazur
 * http://librazur.eu.org
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

package org.librazur.blc.swing.action;


import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.blc.BLC;
import org.librazur.blc.dumper.Dumper;
import org.librazur.blc.model.DumpFile;
import org.librazur.blc.model.Entry;
import org.librazur.blc.swing.MainFrame;
import org.librazur.blc.swing.ParsedFile;
import org.librazur.blc.swing.ParserTableModel;
import org.librazur.util.FileUtils;


public class ConvertAction extends AbstractAction {
    private final Log log = LogFactory.getLog(getClass());
    private final MainFrame frame;


    public ConvertAction(final MainFrame frame) {
        super();
        this.frame = frame;
        putValue(Action.NAME, BLC.i18n("convert"));
        putValue(Action.SMALL_ICON, new ImageIcon(BLC.image("convert.icon")));
    }


    public void actionPerformed(ActionEvent e) {
        // start the conversion in an other thread to not block the UI
        new ConvertThread().start();
    }


    private class ConvertThread extends Thread {
        public ConvertThread() {
            super("BLC Convert thread");
        }


        @Override
        public void run() {
            frame.setFreezing(true);

            try {
                convert(frame.getParserTableModel(), frame.getDumper(), frame
                        .getOutputDir());
            } catch (Exception e) {
                log.error("Error while converting", e);
            } finally {
                frame.setFreezing(false);
            }
        }
    }


    private void convert(ParserTableModel parserTableModel, Dumper dumper,
            String outputDir) throws Exception {
        final long start = System.currentTimeMillis();
        final Collection<Entry> entries = new LinkedHashSet<Entry>();

        for (final ParsedFile parsedFile : parserTableModel) {
            final URL file = parsedFile.getFile();
            log.info("Parsing from file: " + file);
            entries.addAll(parsedFile.getParser().parse(file.openStream()));
        }

        // dumping the output files
        final Collection<DumpFile> dumpFiles = dumper.dump(entries);
        final File dir = new File(outputDir);
        for (final DumpFile dumpFile : dumpFiles) {
            final File file = new File(dir, dumpFile.getFileName());
            log.info("Dumping to file: " + file.getPath());
            FileUtils.write(file, dumpFile.getContent());
        }

        final long end = System.currentTimeMillis();
        final long time = end - start;

        log.info("Conversion successfully finished in " + time + " ms");
    }
}

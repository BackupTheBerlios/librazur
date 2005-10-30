/**
 * $Id: BlackListConverter.java,v 1.3 2005/10/30 20:02:35 romale Exp $
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

package org.librazur.blc.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.blc.dumper.Dumper;
import org.librazur.blc.event.BlackListConvertedEvent;
import org.librazur.blc.model.*;
import org.librazur.blc.parser.Parser;
import org.librazur.minibus.BusProvider;
import org.librazur.util.FileUtils;
import org.librazur.util.IOUtils;


public class BlackListConverter {
    private final Log log = LogFactory.getLog(getClass());
    private final BusProvider busProvider;
    private final ParserFactory parserFactory;
    private final DumperFactory dumperFactory;


    public BlackListConverter(final BusProvider busProvider,
            final ParserFactory parserFactory, final DumperFactory dumperFactory) {
        this.busProvider = busProvider;
        this.parserFactory = parserFactory;
        this.dumperFactory = dumperFactory;
    }


    public void convert(Profile profile) throws IOException {
        try {
            final long start = System.currentTimeMillis();
            final Collection<Entry> entries = new LinkedHashSet<Entry>();

            for (final ParserSource source : profile.getParserSources()) {
                URL url = source.url;
                log.info("Parsing from URL: " + url);

                // copy the remote file to local disk for faster access
                if (!"file".equals(url.getProtocol())) {
                    url = copyRemoteFileToLocal(url);
                }
                final Parser parser = parserFactory
                        .createParser(source.parserClass);
                entries.addAll(parser.parse(url.openStream()));
            }

            // dumping the output files
            for (final DumperSink sink : profile.getDumperSinks()) {
                final Dumper dumper = dumperFactory
                        .createDumper(sink.dumperClass);
                final Collection<MemoryFile> dumpFiles = dumper.dump(entries);
                final File dir = sink.directory;
                for (final MemoryFile dumpFile : dumpFiles) {
                    final File file = new File(dir, dumpFile.getFileName());
                    final File parent = file.getParentFile();
                    if(parent != null) {
                        FileUtils.ensureDirectoryExists(parent);
                    }
                    file.createNewFile();

                    log.info("Dumping to file: " + file.getPath());
                    FileUtils.write(file, dumpFile.getContent());
                }
            }

            final long end = System.currentTimeMillis();
            final long time = end - start;
            log.info("Conversion successfully finished in " + time + " ms");
        } finally {
            busProvider.getBus().post(new BlackListConvertedEvent(this));
        }
    }


    private URL copyRemoteFileToLocal(URL url) throws IOException {
        log.info("Copying URL " + url + " to local repository");

        OutputStream output = null;
        File temp = null;
        try {
            temp = File.createTempFile("blc-", ".tmp");
            temp.deleteOnExit();
            output = new FileOutputStream(temp);
            IOUtils.copy(url.openStream(), output);

            return temp.toURI().toURL();
        } finally {
            IOUtils.close(output);
        }
    }
}

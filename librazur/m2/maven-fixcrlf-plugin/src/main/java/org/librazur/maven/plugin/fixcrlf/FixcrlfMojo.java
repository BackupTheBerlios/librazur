/**
 * $Id: FixcrlfMojo.java,v 1.1 2005/11/23 10:36:09 romale Exp $
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

package org.librazur.maven.plugin.fixcrlf;


import java.io.*;
import java.util.*;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.IOUtil;
import org.librazur.util.ChecksumUtils;
import org.librazur.util.StringUtils;


/**
 * Fixes end-of-lines in text files.
 * 
 * @goal fixcrlf
 * @description Fixes end-of-lines in text files
 */
public class FixcrlfMojo extends AbstractMojo {
    public static final String MAC_EOL = "mac";
    public static final String UNIX_EOL = "unix";
    public static final String DOS_EOL = "dos";
    public static final String DEFAULT_EOL = UNIX_EOL;
    public static final String DEFAULT_ENCODING = "ISO-8859-1";
    public static final String[] DEFAULT_INCLUDES = { "**/*.java", "**/*.css",
            "**/*.xml", "**/*.properties", "**/*.js", "**/*.txt" };
    public static final String[] DEFAULT_EXCLUDES = { "**/*.jpg", "**/*.png",
            "**/*.gif", "**/*.jar", "**/*.zip" };

    private final Set possibleEolValues = new HashSet();
    private final Map eolMap = new HashMap();

    /**
     * New file encoding. Default is ISO-8859-1.
     * 
     * @parameter expression="${fixcrlf.encoding}"
     */
    public String encoding = "ISO-8859-1";

    /**
     * Whether to add a missing EOL to the last line of a file. Default is true.
     * 
     * @parameter expression="${fixcrlf.fixlast}"
     */
    public boolean fixlast = true;

    /**
     * Specifies how end-of-lines (EOL) characters are to be handled. Possible
     * values are: mac, unix, dos. Default is unix.
     * 
     * @parameter expression="${fixcrlf.eol}"
     */
    public String eol = "unix";

    /**
     * Strip spaces at the end of lines. Default is true.
     */
    public boolean stripEndSpaces = true;

    /**
     * File masks to include. Default are : **\/*.java, **\/*.css, **\/*.js,
     * **\/*.xml, **\/*.properties, **\/*.txt.
     * 
     * @parameter
     */
    public List includes;

    /**
     * File masks to exclude. Default are : **\/*.jpg, **\/*.gif, **\/*.png,
     * **\/*.jar, **\/*.zip.
     * 
     * @parameter
     */
    public List excludes;

    /**
     * The project whose project files to fix EOL.
     * 
     * @parameter expression="${project}"
     * @required
     */
    public MavenProject project;


    public FixcrlfMojo() {
        possibleEolValues.add(MAC_EOL);
        possibleEolValues.add(UNIX_EOL);
        possibleEolValues.add(DOS_EOL);

        eolMap.put(MAC_EOL, "\r");
        eolMap.put(UNIX_EOL, "\n");
        eolMap.put(DOS_EOL, "\r\n");
    }


    public void execute() throws MojoExecutionException, MojoFailureException {
        if (eol == null || !possibleEolValues.contains(eol)) {
            eol = DEFAULT_EOL;
            getLog().warn("No EOL was specified: using " + eol + " as default");
        }

        if (encoding == null) {
            encoding = DEFAULT_ENCODING;
            getLog().warn(
                    "No file encoding was specified: using " + encoding
                            + " as default");
        }

        if (includes == null) {
            includes = Arrays.asList(DEFAULT_INCLUDES);
        }
        if (excludes == null) {
            excludes = Arrays.asList(DEFAULT_EXCLUDES);
        }

        getLog()
                .info(
                        "Fixing EOL to " + eol + " with encoding " + encoding
                                + ", strip end spaces "
                                + (stripEndSpaces ? "enabled" : "disabled")
                                + ", including " + includes + ", excluding "
                                + excludes);

        final List dirs = new ArrayList();
        dirs.addAll(project.getCompileSourceRoots());
        dirs.addAll(project.getTestCompileSourceRoots());

        final DirectoryScanner dirScan = new DirectoryScanner();
        dirScan.addDefaultExcludes();
        dirScan.setIncludes((String[]) includes.toArray(new String[includes
                .size()]));
        dirScan.setExcludes((String[]) excludes.toArray(new String[excludes
                .size()]));

        try {
            for (final Iterator i = dirs.iterator(); i.hasNext();) {
                final String baseDir = (String) i.next();

                final File baseDirFile = new File(baseDir);
                if (!baseDirFile.exists()) {
                    continue;
                }

                dirScan.setBasedir(baseDir);

                getLog().info("Scanning directory: " + baseDir);
                dirScan.scan();
                final String[] files = dirScan.getIncludedFiles();

                for (int j = 0; j < files.length; ++j) {
                    final File file = new File(baseDir, files[j]);
                    if (!file.exists() || !file.canRead()) {
                        continue;
                    }
                    getLog().info("Fixing EOL for file: " + file);
                    fixcrlf(file);
                }
            }
        } catch (IOException e) {
            getLog().error("Failed to fix end of lines", e);
            throw new MojoFailureException("Failed to fix end of lines");
        }
    }


    private void fixcrlf(File file) throws IOException {
        BufferedReader reader = null;
        Writer writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file)));

            // read input file
            final List lines = new ArrayList();
            for (String line; (line = reader.readLine()) != null;) {
                lines.add(stripEndSpaces ? StringUtils.stripEndSpaces(line)
                        : line);
            }

            // create temp file
            final File tempFile = File.createTempFile("maven-fixcrlf-plugin-",
                    ".tmp");
            getLog().debug(
                    "Using temp file " + tempFile.getPath() + " for file "
                            + file.getPath());
            tempFile.deleteOnExit();

            // dump new file into temp file
            writer = new OutputStreamWriter(new FileOutputStream(tempFile),
                    encoding);
            final int linesLen = lines.size();
            final int lastIndex = linesLen - 1;
            for (int i = 0; i < linesLen; ++i) {
                final String line = (String) lines.get(i);
                final String suffix = (String) eolMap.get(eol);
                writer.write(line);

                if (i != lastIndex | fixlast) {
                    writer.write(suffix);
                }
            }
            // close the temp file before copying it to the source file
            IOUtil.close(writer);

            // we compare the new file with the old one: if there is no
            // modifications, we don't need to copy over the old file
            final String tempMd5 = ChecksumUtils.md5Hex(new FileInputStream(
                    tempFile));
            final String oldMd5 = ChecksumUtils
                    .md5Hex(new FileInputStream(file));
            if (!tempMd5.equals(oldMd5)) {
                // file are different: we need to copy the temp file
                FileUtils.copyFile(tempFile, file);
            }

            tempFile.delete();
        } finally {
            // closing any opened resources
            IOUtil.close(reader);
            IOUtil.close(writer);
        }
    }
}

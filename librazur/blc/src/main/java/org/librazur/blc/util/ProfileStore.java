/**
 * $Id: ProfileStore.java,v 1.3 2005/11/28 16:04:32 romale Exp $
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


import java.io.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.blc.Constants;
import org.librazur.blc.model.Profile;
import org.librazur.util.IOUtils;

import com.thoughtworks.xstream.XStream;


public class ProfileStore {
    private final Log log = LogFactory.getLog(getClass());


    public Profile load(File file) {
        log.info("Loading profile from file: " + file.getPath());

        Reader reader = null;
        final Profile profile;
        try {
            reader = new BufferedReader(new FileReader(file));
            profile = (Profile) createXStream().fromXML(reader);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Error while loading profile from file: " + file.getPath(),
                    e);
        } finally {
            IOUtils.close(reader);
        }
        profile.setFilePath(file.getPath());

        return profile;
    }


    public void save(File file, Profile profile) {
        log.info("Saving profile '" + profile.getName() + "' to file: "
                + file.getPath());

        Writer writer = null;
        profile.setFilePath(file.getPath());
        try {
            final String charset = "ISO-8859-1";
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), charset));
            writer.append("<?xml version='1.0' encoding='").append(charset)
                    .append("'?>").append(Constants.LINE_SEPARATOR);
            createXStream().toXML(profile, writer);
            writer.flush();
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Error while saving profile to file: " + file.getPath(), e);
        } finally {
            IOUtils.close(writer);
        }
    }


    private XStream createXStream() {
        final XStream xstream = new XStream();

        return xstream;
    }
}

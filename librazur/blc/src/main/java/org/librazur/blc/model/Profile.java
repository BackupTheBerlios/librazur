/**
 * $Id: Profile.java,v 1.1 2005/10/26 16:35:40 romale Exp $
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

package org.librazur.blc.model;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Profile {
    private String name = "default";
    private File file;
    private List<ParserSource> parserSources = new ArrayList<ParserSource>(1);
    private List<DumperSink> dumperSinks = new ArrayList<DumperSink>(1);


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public List<DumperSink> getDumperSinks() {
        return dumperSinks;
    }


    public void setDumperSinks(List<DumperSink> sinks) {
        this.dumperSinks = sinks;
    }


    public List<ParserSource> getParserSources() {
        return parserSources;
    }


    public void setParserSources(List<ParserSource> sources) {
        this.parserSources = sources;
    }


    public File getFile() {
        return file;
    }


    public void setFile(File file) {
        this.file = file;
    }
}

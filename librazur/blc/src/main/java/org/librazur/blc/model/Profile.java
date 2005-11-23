/**
 * $Id: Profile.java,v 1.3 2005/11/23 11:02:07 romale Exp $
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


import java.util.ArrayList;
import java.util.List;


public class Profile {
    private String name = "default";
    private String filePath;
    private List<ParserSource> parserSources = new ArrayList<ParserSource>(1);
    private List<DumperSink> dumperSinks = new ArrayList<DumperSink>(1);


    public synchronized String getName() {
        return name;
    }


    public synchronized void setName(String name) {
        this.name = name;
    }


    public synchronized List<DumperSink> getDumperSinks() {
        return dumperSinks;
    }


    public synchronized void setDumperSinks(List<DumperSink> sinks) {
        this.dumperSinks = sinks;
    }


    public synchronized List<ParserSource> getParserSources() {
        return parserSources;
    }


    public synchronized void setParserSources(List<ParserSource> sources) {
        this.parserSources = sources;
    }


    public synchronized String getFilePath() {
        return filePath;
    }


    public synchronized void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

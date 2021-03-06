/**
 * $Id: MemoryFile.java,v 1.1 2005/10/26 16:35:40 romale Exp $
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


import java.nio.ByteBuffer;


public class MemoryFile {
    private ByteBuffer content;
    private String fileName;


    public MemoryFile() {
    }


    public MemoryFile(final String fileName, final ByteBuffer content) {
        setFileName(fileName);
        setContent(content);
    }


    public ByteBuffer getContent() {
        return content;
    }


    public void setContent(ByteBuffer content) {
        if (content == null) {
            throw new NullPointerException("content");
        }
        this.content = content;
    }


    public String getFileName() {
        return fileName;
    }


    public void setFileName(String fileName) {
        if (fileName == null) {
            throw new NullPointerException("fileName");
        }
        this.fileName = fileName;
    }


    @Override
    public int hashCode() {
        return (fileName + "\n" + content).hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof MemoryFile)) {
            return false;
        }
        return hashCode() == obj.hashCode();
    }
}

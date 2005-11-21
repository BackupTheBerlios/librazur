/**
 * $Id: BigDecimalKernel.java,v 1.1 2005/11/21 01:30:15 romale Exp $
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


import java.awt.image.Kernel;
import java.math.BigDecimal;


/**
 * Kernel implementation with BigDecimal components.
 */
public class BigDecimalKernel {
    private BigDecimal coefficient = BigDecimal.ONE;
    private BigDecimal[] data = new BigDecimal[] { BigDecimal.valueOf(1),
            BigDecimal.valueOf(0), BigDecimal.valueOf(0),
            BigDecimal.valueOf(0), BigDecimal.valueOf(1),
            BigDecimal.valueOf(0), BigDecimal.valueOf(0),
            BigDecimal.valueOf(0), BigDecimal.valueOf(1) };
    private int width = 3;
    private int height = 3;


    public Kernel createKernel() {
        final float[] kernelData = new float[data.length];
        for (int i = 0; i < kernelData.length; ++i) {
            kernelData[i] = coefficient.multiply(data[i]).floatValue();
        }
        return new Kernel(width, height, kernelData);
    }


    public BigDecimal getCoefficient() {
        return coefficient;
    }


    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }


    public BigDecimal[] getData() {
        return data;
    }


    public void setData(BigDecimal[] data) {
        this.data = data;
    }


    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public int getWidth() {
        return width;
    }


    public void setWidth(int width) {
        this.width = width;
    }
}

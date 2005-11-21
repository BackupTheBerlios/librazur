/**
 * $Id: BigDecimalTableModel.java,v 1.1 2005/11/21 01:30:15 romale Exp $
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


import java.math.BigDecimal;

import javax.swing.table.AbstractTableModel;


public class BigDecimalTableModel extends AbstractTableModel {
    private BigDecimal[][] numbers = new BigDecimal[0][0];
    private int rowCount;
    private int colCount;


    public BigDecimalTableModel(final int rowCount, final int colCount) {
        super();
        resize(rowCount, colCount);
    }


    public int getRowCount() {
        return rowCount;
    }


    public int getColumnCount() {
        return colCount;
    }


    public Object getValueAt(int row, int col) {
        return numbers[row][col];
    }


    @Override
    public void setValueAt(Object value, int row, int col) {
        if (value instanceof String) {
            final String str = ((String) value).trim();
            if (str.length() == 0) {
                value = BigDecimal.ZERO;
            } else {
                try {
                    value = new BigDecimal(str);
                } catch (NumberFormatException e) {
                    value = BigDecimal.ZERO;
                    e.printStackTrace();
                }
            }
        }
        numbers[row][col] = (BigDecimal) value;
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }


    public void resize(int newRowCount, int newColCount) {
        if (rowCount == newRowCount && colCount == newColCount) {
            return;
        }

        final BigDecimal[][] oldNumbers = numbers;
        numbers = new BigDecimal[newRowCount][newColCount];

        for (int row = 0; row < newRowCount; ++row) {
            for (int col = 0; col < newColCount; ++col) {
                numbers[row][col] = BigDecimal.ZERO;
            }
        }

        for (int row = 0; row < rowCount && row < newRowCount; ++row) {
            for (int col = 0; col < colCount && col < newColCount; ++col) {
                numbers[row][col] = oldNumbers[row][col];
            }
        }

        rowCount = newRowCount;
        colCount = newColCount;

        fireTableStructureChanged();
    }
}

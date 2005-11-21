/**
 * $Id: BigDecimalTableCellEditor.java,v 1.1 2005/11/21 01:30:15 romale Exp $
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


import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;


public class BigDecimalTableCellEditor extends DefaultCellEditor {
    public BigDecimalTableCellEditor() {
        super(new JTextField());
        setClickCountToStart(1);

        // when we click with the mouse the text is selected
        getComponent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) getComponent()).selectAll();
            };
        });
    }


    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        final JTextField tf = (JTextField) getComponent();

        final BigDecimal number = (BigDecimal) value;
        delegate.setValue(number);

        // set the value in the text field (we don't use a NumberFormat here
        // because BigDecimalTableModel expects a plain BigDecimal format for
        // input)
        if (number != null) {
            tf.setText(number.toPlainString());
        } else {
            tf.setText("0.0");
        }
        // calling this make the text field replace all the value instead of
        // appending it
        tf.selectAll();

        return tf;
    }
}

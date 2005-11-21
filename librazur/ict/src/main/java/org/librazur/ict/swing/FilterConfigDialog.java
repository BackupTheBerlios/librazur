/**
 * $Id: FilterConfigDialog.java,v 1.1 2005/11/21 01:30:15 romale Exp $
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


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.librazur.ict.Resources;
import org.librazur.ict.util.BigDecimalKernel;
import org.librazur.ict.util.NumberUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;


public class FilterConfigDialog extends JDialog {
    private final MainFrame frame;
    private JFormattedTextField kernelCoeffField;
    private JSpinner kernelWidthField;
    private JSpinner kernelHeightField;
    private JTable kernelTable;
    private JButton okButton;


    public FilterConfigDialog(final MainFrame frame) {
        super(frame);
        this.frame = frame;
        init();
    }


    private void init() {
        setTitle(Resources.i18n("dialog.filter"));
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(createMainPanel(), BorderLayout.CENTER);
        panel.add(createButtonPanel(), BorderLayout.SOUTH);
        panel.setBorder(Borders.DIALOG_BORDER);

        final Dimension dim = new Dimension(300, 200);
        panel.setMinimumSize(dim);
        panel.setPreferredSize(dim);

        setContentPane(panel);
    }


    private JPanel createButtonPanel() {
        okButton = new JButton(new OKAction());
        getRootPane().setDefaultButton(okButton);

        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panel.add(ButtonBarFactory.buildOKCancelBar(okButton, new JButton(
                new CancelAction())));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        return panel;
    }


    private void installNewTableModel(int rowCount, int colCount) {
        final TableCellEditor tableCellEditor = new BigDecimalTableCellEditor();
        kernelTable.setCellEditor(tableCellEditor);

        final BigDecimalTableModel model = (BigDecimalTableModel) kernelTable
                .getModel();
        model.resize(rowCount, colCount);

        final TableColumnModel columnModel = kernelTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); ++i) {
            final TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(10);
            column.setCellEditor(tableCellEditor);
        }

        kernelTable.invalidate();
        validate();
    }


    private JPanel createMainPanel() {
        final BigDecimalKernel bigDecimalKernel = frame.getBigDecimalKernel();
        final int kWidth = bigDecimalKernel.getWidth();
        final int kHeight = bigDecimalKernel.getHeight();

        final KernelSizeChangedListener kernelSizeChangedListener = new KernelSizeChangedListener();

        kernelWidthField = new JSpinner(new SpinnerNumberModel(kWidth, 1,
                Integer.MAX_VALUE, 1));
        ((JSpinner.NumberEditor) kernelWidthField.getEditor()).getTextField()
                .setColumns(2);
        kernelWidthField.addChangeListener(kernelSizeChangedListener);

        kernelHeightField = new JSpinner(new SpinnerNumberModel(kHeight, 1,
                Integer.MAX_VALUE, 1));
        ((JSpinner.NumberEditor) kernelHeightField.getEditor()).getTextField()
                .setColumns(2);
        kernelHeightField.addChangeListener(kernelSizeChangedListener);

        kernelTable = new JTable(new BigDecimalTableModel(1, 1));
        kernelTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        kernelTable.setCellSelectionEnabled(true);
        kernelTable.setDefaultRenderer(Object.class,
                new BigDecimalTableCellRenderer());
        kernelTable.setBorder(BorderFactory.createEtchedBorder());
        installNewTableModel(kHeight, kWidth);

        kernelCoeffField = new JFormattedTextField(NumberUtils
                .getDoubleNumberFormat());
        kernelCoeffField.setValue(bigDecimalKernel.getCoefficient());

        int index = 0;
        final BigDecimal[] kernelData = bigDecimalKernel.getData();
        for (int row = 0; row < kHeight; ++row) {
            for (int col = 0; col < kWidth; ++col) {
                kernelTable.setValueAt(kernelData[index++], row, col);
            }
        }

        final FormLayout formLayout = new FormLayout(
                "right:pref, 3dlu, default:grow, 3dlu, right:pref, 3dlu, default:grow",
                "p, 3dlu, p, 3dlu, p, 3dlu, top:p");

        final PanelBuilder builder = new PanelBuilder(formLayout);
        final CellConstraints cc = new CellConstraints();

        builder.addSeparator(Resources.i18n("dialog.filter.kernel"), cc.xywh(1,
                1, 7, 1));
        builder.addLabel(Resources.i18n("dialog.filter.kernel.width"), cc.xy(1,
                3));
        builder.add(kernelWidthField, cc.xy(3, 3));
        builder.addLabel(Resources.i18n("dialog.filter.kernel.height"), cc.xy(
                5, 3));
        builder.add(kernelHeightField, cc.xy(7, 3));
        builder.addLabel(Resources.i18n("dialog.filter.kernel.coeff"), cc.xy(1,
                5));
        builder.add(kernelCoeffField, cc.xy(3, 5));
        builder.addLabel(Resources.i18n("dialog.filter.kernel.data"), cc.xy(1,
                7));
        builder.add(kernelTable, cc.xywh(3, 7, 5, 1));

        return builder.getPanel();
    }


    private class OKAction extends AbstractAction {
        public OKAction() {
            super();
            putValue(Action.NAME, Resources.i18n("action.ok"));
            putValue(Action.SHORT_DESCRIPTION, Resources.i18n("action.ok.desc"));
            putValue(Action.SMALL_ICON, Resources.icon("action.ok.icon"));
        }


        public void actionPerformed(ActionEvent e) {
            final int width = ((Number) kernelWidthField.getValue()).intValue();
            final int height = ((Number) kernelHeightField.getValue())
                    .intValue();
            final float coeff = ((Number) kernelCoeffField.getValue())
                    .floatValue();

            final BigDecimal[] data = new BigDecimal[width * height];
            int index = 0;
            for (int row = 0; row < height; ++row) {
                for (int col = 0; col < width; ++col) {
                    data[index++] = (BigDecimal) kernelTable.getValueAt(row,
                            col);
                }
            }

            final BigDecimalKernel kernel = frame.getBigDecimalKernel();
            kernel.setCoefficient(BigDecimal.valueOf(coeff));
            kernel.setWidth(width);
            kernel.setHeight(height);
            kernel.setData(data);

            dispose();
        }
    }


    private class CancelAction extends AbstractAction {
        public CancelAction() {
            super();
            putValue(Action.NAME, Resources.i18n("action.cancel"));
            putValue(Action.SHORT_DESCRIPTION, Resources
                    .i18n("action.cancel.desc"));
            putValue(Action.SMALL_ICON, Resources.icon("action.cancel.icon"));
        }


        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }


    private class KernelSizeChangedListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            final int rowCount = ((Number) kernelHeightField.getValue())
                    .intValue();
            final int colCount = ((Number) kernelWidthField.getValue())
                    .intValue();

            boolean newSize = false;
            if (kernelWidthField.equals(e.getSource())) {
                if (colCount != kernelTable.getColumnCount()) {
                    newSize = true;
                }
            } else if (kernelHeightField.equals(e.getSource())) {
                if (rowCount != kernelTable.getRowCount()) {
                    newSize = true;
                }
            }

            if (newSize) {
                installNewTableModel(rowCount, colCount);
            }
        }
    }
}

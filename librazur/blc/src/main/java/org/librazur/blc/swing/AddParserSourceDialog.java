/**
 * $Id: AddParserSourceDialog.java,v 1.1 2005/10/26 16:35:40 romale Exp $
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

package org.librazur.blc.swing;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.blc.Resources;
import org.librazur.blc.event.AddingParserSourceEvent;
import org.librazur.blc.model.ParserSource;
import org.librazur.blc.parser.Parser;
import org.librazur.blc.util.FileChooserUtils;
import org.librazur.blc.util.ParserFactory;
import org.librazur.minibus.BusProvider;

import com.jeta.forms.components.panel.FormPanel;
import com.jgoodies.forms.factories.Borders;


public class AddParserSourceDialog extends JDialog {
    private final Log log = LogFactory.getLog(getClass());
    private final BusProvider busProvider;
    private final ParserFactory parserFactory;
    private FormPanel panel;
    private Action browseLocalFileAction;


    public AddParserSourceDialog(final Frame parent,
            final BusProvider busProvider, final ParserFactory parserFactory) {
        super(parent);
        this.busProvider = busProvider;
        this.parserFactory = parserFactory;
        init();
    }


    public void setFile(File file) {
        if (!file.exists()) {
            return;
        }
        panel.getTextComponent("localFileField").setText(file.getPath());
        panel.getRadioButton("localFileRadio").doClick();
    }


    private void init() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(false);
        setTitle(Resources.i18n("parser.add"));

        final JPanel formPanel = createFormPanel();
        formPanel.setBorder(Borders.DLU4_BORDER);
        final Dimension minSize = new Dimension(450, 250);
        formPanel.setMinimumSize(minSize);
        formPanel.setPreferredSize(minSize);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(formPanel, BorderLayout.CENTER);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(getOwner());
    }


    private JPanel createFormPanel() {
        try {
            panel = new FormPanel(getClass().getResourceAsStream(
                    "addparser.xml"));
        } catch (Exception e) {
            throw new IllegalStateException("Error while building form", e);
        }

        final JButton addButton = (JButton) panel.getButton("addButton");
        addButton.setAction(new AddAction());
        getRootPane().setDefaultButton(addButton);
        panel.getButton("cancelButton").setAction(new CancelAction());

        browseLocalFileAction = new BrowseLocalFileAction();
        panel.getButton("browseLocalFileButton").setAction(
                browseLocalFileAction);

        final JComboBox parserCombo = panel.getComboBox("parserCombo");
        initParserCombo(parserCombo);

        final JRadioButton remoteFileRadio = panel
                .getRadioButton("remoteFileRadio");
        final JRadioButton localFileRadio = panel
                .getRadioButton("localFileRadio");
        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(remoteFileRadio);
        buttonGroup.add(localFileRadio);

        remoteFileRadio.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED) {
                    return;
                }
                browseLocalFileAction.setEnabled(false);
                panel.getTextComponent("remoteFileField").setEnabled(true);
                panel.getTextComponent("localFileField").setEnabled(false);
                panel.getTextComponent("remoteFileField").selectAll();
                panel.getTextComponent("remoteFileField").requestFocus();
            }
        });
        localFileRadio.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED) {
                    return;
                }
                browseLocalFileAction.setEnabled(true);
                panel.getTextComponent("localFileField").setEnabled(true);
                panel.getTextComponent("remoteFileField").setEnabled(false);
                panel.getTextComponent("localFileField").selectAll();
                panel.getTextComponent("localFileField").requestFocus();
            }
        });
        remoteFileRadio.doClick();

        return panel;
    }


    private void initParserCombo(JComboBox combo) {
        final Collection<Parser> parsers = parserFactory.createAllParsers();
        final Parser[] parserArray = parsers
                .toArray(new Parser[parsers.size()]);
        Arrays.sort(parserArray);
        combo.setModel(new DefaultComboBoxModel(parserArray));
    }


    private class AddAction extends AbstractAction {
        public AddAction() {
            super();
            putValue(Action.NAME, Resources.i18n("action.add"));
            putValue(Action.SMALL_ICON, Resources.icon("action.add.icon"));
        }


        public void actionPerformed(ActionEvent evt) {
            final Parser parser = (Parser) panel.getComboBox("parserCombo")
                    .getSelectedItem();
            final URL url;
            try {
                if (panel.getRadioButton("remoteFileRadio").isSelected()) {
                    // remote
                    url = new URL(panel.getTextComponent("remoteFileField")
                            .getText().trim());
                } else {
                    // local
                    url = new File(panel.getTextComponent("localFileField")
                            .getText().trim()).toURI().toURL();
                }
            } catch (Exception e) {
                log.error("Error while adding parser", e);
                return;
            }

            dispose();
            final ParserSource source = new ParserSource(parser.getClass(), url);
            busProvider.getBus()
                    .post(new AddingParserSourceEvent(this, source));
        }
    }


    private class CancelAction extends AbstractAction {
        public CancelAction() {
            super();
            putValue(Action.NAME, Resources.i18n("action.cancel"));
            putValue(Action.SMALL_ICON, Resources.icon("action.cancel.icon"));
        }


        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }


    private class BrowseLocalFileAction extends AbstractAction {
        public BrowseLocalFileAction() {
            super();
            putValue(Action.NAME, Resources.i18n("action.browse"));
        }


        public void actionPerformed(ActionEvent e) {
            final File file = FileChooserUtils
                    .selectFile(AddParserSourceDialog.this);
            if (file == null) {
                return;
            }
            panel.getTextComponent("localFileField").setText(file.getPath());
        }
    }
}

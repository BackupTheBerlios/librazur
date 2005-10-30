/**
 * $Id: MainFrame.java,v 1.4 2005/10/30 20:03:33 romale Exp $
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Collection;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.java.swingfx.waitwithstyle.PerformanceInfiniteProgressPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.blc.Resources;
import org.librazur.blc.dumper.Dumper;
import org.librazur.blc.event.BlackListConvertedEvent;
import org.librazur.blc.event.ConvertingBlackListEvent;
import org.librazur.blc.event.ExitingApplicationEvent;
import org.librazur.blc.event.NoParserSourceEvent;
import org.librazur.blc.event.ParserSourceAddedEvent;
import org.librazur.blc.event.PreAddingParserSourceEvent;
import org.librazur.blc.event.PreRemovingParserSourceEvent;
import org.librazur.blc.event.PreSavingProfileEvent;
import org.librazur.blc.event.RemovingParserSourceEvent;
import org.librazur.blc.event.SavingProfileEvent;
import org.librazur.blc.event.SelectingDumperSinkEvent;
import org.librazur.blc.model.DumperSink;
import org.librazur.blc.swing.action.AddParserAction;
import org.librazur.blc.swing.action.BrowseOutputAction;
import org.librazur.blc.swing.action.ConvertAction;
import org.librazur.blc.swing.action.LoadProfileAction;
import org.librazur.blc.swing.action.NewProfileAction;
import org.librazur.blc.swing.action.RemoveParserAction;
import org.librazur.blc.swing.action.SaveProfileAction;
import org.librazur.blc.util.DumperFactory;
import org.librazur.blc.util.ParserFactory;
import org.librazur.minibus.BusProvider;
import org.librazur.minibus.EventHandler;

import com.jeta.forms.components.panel.FormPanel;
import com.jeta.forms.components.separator.TitledSeparator;
import com.jgoodies.forms.factories.Borders;


/**
 * Main frame.
 */
public class MainFrame extends JFrame {
    private final Log log = LogFactory.getLog(getClass());
    private final BusProvider busProvider;
    private final ParserFactory parserFactory;
    private final DumperFactory dumperFactory;
    private PerformanceInfiniteProgressPanel infiniteProgressPanel;
    private FormPanel panel;
    private Action removeParserAction;
    private Action convertAction;


    public MainFrame(final BusProvider busProvider,
            final ParserFactory parserFactory, final DumperFactory dumperFactory) {
        super();
        this.parserFactory = parserFactory;
        this.dumperFactory = dumperFactory;
        this.busProvider = busProvider;
        busProvider.getBus().register(new BusHandler());
        init();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                // when the frame is displayed, update the profile with the
                // selected dumper
                postNewDumperSink();
            }
        });
    }


    private JPanel createHeaderPanel() {
        final JLabel title = new JLabel("<html><b>" + Resources.i18n("blc")
                + " " + Resources.version() + "</b><br>"
                + Resources.i18n("blc.copyright"));
        title.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        final JLabel icon = new JLabel(Resources.icon("blc.icon"));
        icon.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        final JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.add(icon, BorderLayout.EAST);
        titlePanel.add(new JSeparator(), BorderLayout.SOUTH);
        titlePanel.setOpaque(true);
        titlePanel.setBackground(Color.WHITE);

        final JToolBar toolBar = new JToolBar();
        toolBar.add(new NewProfileAction(busProvider));
        toolBar.add(new LoadProfileAction(busProvider));
        toolBar.add(new SaveProfileAction(busProvider));
        toolBar.setFloatable(false);
        toolBar.setBorder(Borders.DLU4_BORDER);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(titlePanel, BorderLayout.CENTER);
        panel.add(toolBar, BorderLayout.SOUTH);

        return panel;
    }


    private void postNewDumperSink() {
        final Dumper dumper = (Dumper) panel.getComboBox("dumperCombo")
                .getSelectedItem();
        final File dir = new File(panel.getTextComponent("outputField")
                .getText());

        busProvider.getBus().post(
                new SelectingDumperSinkEvent(this, new DumperSink(dumper
                        .getClass(), dir)), false);
    }


    private void initDumperCombo(JComboBox combo) {
        final Collection<Dumper> dumpers = dumperFactory.createAllDumpers();
        combo.setModel(new DefaultComboBoxModel(dumpers
                .toArray(new Dumper[dumpers.size()])));
        combo.setRenderer(new DumperListCellRenderer());

        combo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED) {
                    return;
                }
                postNewDumperSink();
            }
        });
        combo.setSelectedIndex(0);
    }


    private JPanel createFormPanel() {
        try {
            panel = new FormPanel(getClass().getResourceAsStream("main.xml"));
        } catch (Exception e) {
            throw new IllegalStateException("Error while building form", e);
        }

        final ParserSourceTableModel tableModel = new ParserSourceTableModel(
                busProvider, parserFactory);
        final JTable parserTable = panel.getTable("parserTable");
        parserTable.setModel(tableModel);
        parserTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        parserTable.setTransferHandler(new FileTransferHandler(busProvider));
        parserTable.setDragEnabled(true);

        final JButton convertButton = (JButton) panel
                .getButton("convertButton");
        convertAction = new ConvertAction(busProvider);
        convertAction.setEnabled(false);
        convertButton.setAction(convertAction);
        getRootPane().setDefaultButton(convertButton);

        removeParserAction = new RemoveParserAction(busProvider);
        removeParserAction.setEnabled(false);
        panel.getButton("addParserButton").setAction(
                new AddParserAction(busProvider));
        panel.getButton("removeParserButton").setAction(removeParserAction);

        final JTextField outputField = panel.getTextField("outputField");
        try {
            outputField.setText(new File(System.getProperty("user.dir"))
                    .getCanonicalPath());
        } catch (Exception e) {
            log.warn("Error while setting the current directory", e);
        }
        outputField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent evt) {
            }


            public void insertUpdate(DocumentEvent evt) {
                postNewDumperSink();
            }


            public void removeUpdate(DocumentEvent evt) {
                postNewDumperSink();
            }
        });
        panel.getButton("browseOutputButton")
                .setAction(
                        new BrowseOutputAction(this, panel
                                .getTextField("outputField")));
        initDumperCombo(panel.getComboBox("dumperCombo"));

        // let's localize the UI!
        panel.getLabel("dumper.type").setText(Resources.i18n("dumper.type"));
        panel.getLabel("dumper.dir")
                .setText(Resources.i18n("output.directory"));

        final TitledSeparator parserSep = (TitledSeparator) panel
                .getComponentByName("parser");
        parserSep.setText(Resources.i18n("parser"));
        final TitledSeparator dumperSep = (TitledSeparator) panel
                .getComponentByName("dumper");
        dumperSep.setText(Resources.i18n("dumper"));

        return panel;
    }


    public void setFreezing(final boolean freeze) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                infiniteProgressPanel.setVisible(freeze);
            }
        });
    }


    private void init() {
        infiniteProgressPanel = new PerformanceInfiniteProgressPanel(false);
        setGlassPane(infiniteProgressPanel);

        final JPanel formPanel = createFormPanel();
        formPanel.setBorder(Borders.DLU4_BORDER);
        formPanel.setMinimumSize(new Dimension(400, 300));
        formPanel.setPreferredSize(formPanel.getMinimumSize());

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(createHeaderPanel(), BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);

        setTitle(Resources.i18n("blc"));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                busProvider.getBus().post(new ExitingApplicationEvent(this));
            }
        });
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setIconImage(Resources.image("blc.icon"));
    }


    private class BusHandler implements EventHandler {
        public EventObject onEvent(EventObject obj) throws Exception {
            if (obj instanceof PreSavingProfileEvent) {
                return preSavingProfile();
            }
            if (obj instanceof BlackListConvertedEvent) {
                return blackListConverted();
            }
            if (obj instanceof ConvertingBlackListEvent) {
                return convertingBlackList();
            }
            if (obj instanceof PreAddingParserSourceEvent) {
                return preAddingParserSource((PreAddingParserSourceEvent) obj);
            }
            if (obj instanceof NoParserSourceEvent) {
                return noParserSource();
            }
            if (obj instanceof ParserSourceAddedEvent) {
                return parserSourceAdded();
            }
            if (obj instanceof PreRemovingParserSourceEvent) {
                return preRemovingParserSource();
            }
            return null;
        }


        private EventObject preSavingProfile() {
            return new SavingProfileEvent(this);
        }


        private EventObject blackListConverted() {
            setFreezing(false);
            return null;
        }


        private EventObject convertingBlackList() {
            setFreezing(true);
            return null;
        }


        private EventObject preAddingParserSource(PreAddingParserSourceEvent evt) {
            final AddParserSourceDialog dialog = new AddParserSourceDialog(
                    MainFrame.this, busProvider, parserFactory);
            if (evt.getFile() != null) {
                dialog.setFile(evt.getFile());
            }
            dialog.setVisible(true);
            return null;
        }


        private synchronized EventObject parserSourceAdded() {
            convertAction.setEnabled(true);
            removeParserAction.setEnabled(true);
            return null;
        }


        private EventObject noParserSource() {
            convertAction.setEnabled(false);
            removeParserAction.setEnabled(false);
            return null;
        }


        private EventObject preRemovingParserSource() {
            return new RemovingParserSourceEvent(this, panel.getTable(
                    "parserTable").getSelectedRows());
        }
    }
}

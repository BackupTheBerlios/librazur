/**
 * $Id: MainFrame.java,v 1.2 2005/10/20 22:44:12 romale Exp $
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
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import net.java.swingfx.waitwithstyle.PerformanceInfiniteProgressPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.blc.BLC;
import org.librazur.blc.dumper.Dumper;
import org.librazur.blc.dumper.PrivoxyDumper;
import org.librazur.blc.parser.DomainParser;
import org.librazur.blc.parser.HostsParser;
import org.librazur.blc.parser.Parser;
import org.librazur.blc.parser.SquidGuardArchiveParser;
import org.librazur.blc.parser.URLParser;
import org.librazur.blc.swing.action.AddParserAction;
import org.librazur.blc.swing.action.BrowseOutputAction;
import org.librazur.blc.swing.action.ConvertAction;
import org.librazur.blc.swing.action.RemoveParserAction;

import com.jeta.forms.components.panel.FormPanel;
import com.jeta.forms.components.separator.TitledSeparator;
import com.jgoodies.forms.factories.Borders;


/**
 * Main frame.
 */
public class MainFrame extends JFrame {
    private final Log log = LogFactory.getLog(getClass());
    private ParserTableModel parserTableModel;
    private PerformanceInfiniteProgressPanel infiniteProgressPanel;
    private FormPanel panel;
    private Action removeParserAction;


    public MainFrame() {
        super();
        init();
    }


    private JPanel createHeaderPanel() {
        final JLabel title = new JLabel("<html><b>" + BLC.i18n("blc") + " "
                + BLC.version() + "</b><br>" + BLC.i18n("copyright"));
        title.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        final JLabel icon = new JLabel(new ImageIcon(BLC.image("blc.icon")));
        icon.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(title, BorderLayout.CENTER);
        panel.add(icon, BorderLayout.EAST);
        panel.add(new JSeparator(), BorderLayout.SOUTH);
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);

        return panel;
    }


    private void initDumperCombo(JComboBox combo) {
        combo.setModel(new DefaultComboBoxModel(
                new Dumper[] { new PrivoxyDumper() }));
        combo.setRenderer(new DumperListCellRenderer());
    }


    private JPanel createFormPanel() {
        try {
            panel = new FormPanel(getClass().getResourceAsStream("form.xml"));
        } catch (Exception e) {
            throw new IllegalStateException("Error while building form", e);
        }

        final JTable parserTable = panel.getTable("parserTable");
        parserTable.setModel(parserTableModel);
        parserTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        parserTable.setTransferHandler(new FileTransferHandler(this));
        parserTable.setDragEnabled(true);

        final JButton convertButton = (JButton) panel
                .getButton("convertButton");
        convertButton.setAction(new ConvertAction(this));
        getRootPane().setDefaultButton(convertButton);

        removeParserAction = new RemoveParserAction(parserTable,
                parserTableModel);
        removeParserAction.setEnabled(false);
        panel.getButton("addParserButton").setAction(new AddParserAction(this));
        panel.getButton("removeParserButton").setAction(removeParserAction);

        // let's localize the UI!

        try {
            panel.getTextField("outputField")
                    .setText(
                            new File(System.getProperty("user.dir"))
                                    .getCanonicalPath());
        } catch (Exception e) {
            log.warn("Error while setting the current directory", e);
        }
        panel.getButton("browseOutputButton")
                .setAction(
                        new BrowseOutputAction(this, panel
                                .getTextField("outputField")));
        initDumperCombo(panel.getComboBox("dumperCombo"));

        panel.getLabel("dumper.type").setText(BLC.i18n("dumper.type"));
        panel.getLabel("dumper.dir").setText(BLC.i18n("output.directory"));

        final TitledSeparator parserSep = (TitledSeparator) panel
                .getComponentByName("parser");
        parserSep.setText(BLC.i18n("parser"));
        final TitledSeparator dumperSep = (TitledSeparator) panel
                .getComponentByName("dumper");
        dumperSep.setText(BLC.i18n("dumper"));

        return panel;
    }


    public void addFileToParse(File file) {
        final URL url;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            log.error("Unable to convert file to URL: " + file.getPath(), e);
            return;
        }
        addFileToParse(url);
    }


    public void addFileToParse(URL file) {
        final Parser[] parsers = { new SquidGuardArchiveParser(),
                new HostsParser(), new DomainParser(), new URLParser() };
        final Parser parser = (Parser) JOptionPane.showInputDialog(this, BLC
                .i18n("add.parser.message"), BLC.i18n("add"),
                JOptionPane.QUESTION_MESSAGE, null, parsers, parsers[0]);
        if (parser == null) {
            return;
        }

        parserTableModel.add(new ParsedFile(parser, file));

        removeParserAction.setEnabled(true);
    }


    public synchronized void setFreezing(boolean freeze) {
        infiniteProgressPanel.setVisible(freeze);
    }


    private void init() {
        infiniteProgressPanel = new PerformanceInfiniteProgressPanel(false);
        setGlassPane(infiniteProgressPanel);
        parserTableModel = new ParserTableModel();

        final JPanel formPanel = createFormPanel();
        formPanel.setBorder(Borders.DLU4_BORDER);
        formPanel.setMinimumSize(new Dimension(400, 300));
        formPanel.setPreferredSize(formPanel.getMinimumSize());

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(createHeaderPanel(), BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);

        setTitle(BLC.i18n("blc"));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setIconImage(BLC.image("blc.icon"));
    }


    public ParserTableModel getParserTableModel() {
        return parserTableModel;
    }


    public Dumper getDumper() {
        return (Dumper) panel.getComboBox("dumperCombo").getSelectedItem();
    }


    public String getOutputDir() {
        return panel.getTextField("outputField").getText();
    }
}

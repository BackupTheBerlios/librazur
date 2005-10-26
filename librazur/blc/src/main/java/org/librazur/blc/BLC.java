/**
 * $Id: BLC.java,v 1.3 2005/10/26 16:35:40 romale Exp $
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

package org.librazur.blc;


import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Collection;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.blc.dumper.Dumper;
import org.librazur.blc.event.*;
import org.librazur.blc.model.ParserSource;
import org.librazur.blc.model.Profile;
import org.librazur.blc.parser.Parser;
import org.librazur.blc.swing.MainFrame;
import org.librazur.blc.swing.SplashScreenWindow;
import org.librazur.blc.util.BlackListConverter;
import org.librazur.blc.util.DumperFactory;
import org.librazur.blc.util.ParserFactory;
import org.librazur.blc.util.ProfileStore;
import org.librazur.minibus.Bus;
import org.librazur.minibus.BusProvider;
import org.librazur.minibus.ErrorHandler;
import org.librazur.minibus.EventHandler;
import org.librazur.minibus.impl.DefaultBus;
import org.librazur.util.StringUtils;
import org.librazur.util.cache.AbstractCache;


/**
 * Application service locator.
 */
public final class BLC implements ParserFactory, DumperFactory, BusProvider {
    private static final BLC INSTANCE = new BLC();
    private final Log log = LogFactory.getLog(getClass());
    private final Bus bus = new DefaultBus();
    private final DumperCache dumperCache = new DumperCache();
    private final ParserCache parserCache = new ParserCache();
    private final Set<Class<? extends Parser>> parserClasses = new HashSet<Class<? extends Parser>>(
            1);
    private final Set<Class<? extends Dumper>> dumperClasses = new HashSet<Class<? extends Dumper>>(
            1);
    private Profile profile = new Profile();
    private JFrame frame;


    private BLC() {
        final BusHandler busHandler = new BusHandler();
        bus.register(busHandler);
        bus.setErrorHandler(busHandler);

        initParserClasses(getClassNames("blc.parsers"));
        initDumperClasses(getClassNames("blc.dumpers"));
    }


    private Set<String> getClassNames(String key) {
        final Set<String> classNames = new HashSet<String>(1);

        final String[] values = Resources.i18n(key).split(",");
        for (int i = 0; i < values.length; ++i) {
            final String className = StringUtils.trimToNull(values[i]);
            if (className == null) {
                continue;
            }
            classNames.add(className);
        }
        return classNames;
    }


    @SuppressWarnings("unchecked")
    private void initParserClasses(Set<String> classNames) {
        for (final String className : classNames) {
            try {
                parserClasses.add((Class<? extends Parser>) Class
                        .forName(className));
            } catch (ClassNotFoundException e) {
                log.warn("Error registering parser class: " + className, e);
            }
        }
    }


    @SuppressWarnings("unchecked")
    private void initDumperClasses(Set<String> classNames) {
        for (final String className : classNames) {
            try {
                dumperClasses.add((Class<? extends Dumper>) Class
                        .forName(className));
            } catch (ClassNotFoundException e) {
                log.warn("Error registering dumper class: " + className, e);
            }
        }
    }


    public void start() {
        final SplashScreenWindow splash = new SplashScreenWindow(new Frame());
        splash.setVisible(true);

        log.info("Opening main frame");

        frame = new MainFrame(this, this, this);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent evt) {
                // once the main frame is displayed, the splashscreen is
                // disposed
                splash.dispose();
            }
        });
        getBus().post(new CreatingProfileEvent(this));
        frame.setVisible(true);
    }


    private void stop() {
        frame.dispose();
        frame = null;
        System.exit(0);
    }


    public static BLC getInstance() {
        return INSTANCE;
    }


    public Bus getBus() {
        return bus;
    }


    public Dumper createDumper(Class<? extends Dumper> clazz) {
        return dumperCache.get(clazz);
    }


    public Parser createParser(Class<? extends Parser> clazz) {
        return parserCache.get(clazz);
    }


    @SuppressWarnings("unchecked")
    public Collection<Dumper> createAllDumpers() {
        final Collection<Dumper> dumpers = new HashSet<Dumper>(dumperClasses
                .size());
        for (final Class<? extends Dumper> clazz : dumperClasses) {
            dumpers.add(dumperCache.get(clazz));
        }
        return dumpers;
    }


    @SuppressWarnings("unchecked")
    public Collection<Parser> createAllParsers() {
        final Collection<Parser> parsers = new HashSet<Parser>(parserClasses
                .size());
        for (final Class<? extends Parser> clazz : parserClasses) {
            parsers.add(parserCache.get(clazz));
        }
        return parsers;

    }


    private class BusHandler implements EventHandler, ErrorHandler {
        public void onError(EventHandler handler, EventObject evt, Throwable e) {
            log.error("Error while dispatching event " + evt + " to handler "
                    + handler, e);
        }


        public EventObject onEvent(EventObject obj) throws Exception {
            if (obj instanceof ProfileLoadedEvent) {
                return profileLoaded((ProfileLoadedEvent) obj);
            }
            if (obj instanceof CreatingProfileEvent) {
                return newProfile();
            }
            if (obj instanceof ExitingApplicationEvent) {
                return exiting();
            }
            if (obj instanceof RemovingParserSourceEvent) {
                return removingParserSource((RemovingParserSourceEvent) obj);
            }
            if (obj instanceof SavingProfileEvent) {
                return savingProfile((SavingProfileEvent) obj);
            }
            if (obj instanceof AddingParserSourceEvent) {
                return addingParserSourceEvent((AddingParserSourceEvent) obj);
            }
            if (obj instanceof ParserSourceRemovedEvent) {
                return parserSourceRemoved();
            }
            if (obj instanceof ConvertingBlackListEvent) {
                return convertingBlackList();
            }
            return null;
        }


        private EventObject profileLoaded(ProfileLoadedEvent evt) {
            profile = evt.getProfile();
            return null;
        }


        public EventObject loadingProfile(LoadingProfileEvent evt) {
            final ProfileStore store = new ProfileStore();
            profile = store.load(evt.getFile());
            return new ProfileLoadedEvent(this, profile);
        }


        private EventObject savingProfile(SavingProfileEvent evt) {
            final ProfileStore store = new ProfileStore();
            store.save(profile.getFile(), profile);
            return new ProfileSavedEvent(this, profile);
        }


        private EventObject exiting() {
            stop();
            return null;
        }


        private EventObject newProfile() {
            profile = new Profile();
            return new ProfileCreatedEvent(this, profile);
        }


        private EventObject removingParserSource(RemovingParserSourceEvent evt) {
            final int[] indexes = evt.getIndexes();
            final Set<ParserSource> sourcesToRemove = new HashSet<ParserSource>(
                    indexes.length);
            for (int i = 0; i < indexes.length; ++i) {
                final int index = indexes[i];
                sourcesToRemove.add(profile.getParserSources().get(index));
            }

            profile.getParserSources().removeAll(sourcesToRemove);

            return new ParserSourceRemovedEvent(this, sourcesToRemove);
        }


        private EventObject addingParserSourceEvent(AddingParserSourceEvent evt) {
            profile.getParserSources().add(evt.getParserSource());
            return new ParserSourceAddedEvent(this, evt.getParserSource());
        }


        private EventObject parserSourceRemoved() {
            if (profile.getParserSources().isEmpty()) {
                return new NoParserSourceEvent(this);
            }
            return null;
        }


        private EventObject convertingBlackList() throws IOException {
            final BlackListConverter converter = new BlackListConverter(
                    BLC.this, BLC.this, BLC.this);
            converter.convert(profile);

            return new BlackListConvertedEvent(this);
        }
    }


    private class DumperCache extends
            AbstractCache<Dumper, Class<? extends Dumper>> {
        @Override
        protected Dumper load(Class<? extends Dumper> clazz) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                log.error("Error while creating dumper: " + clazz.getName(), e);
                return null;
            }
        }
    }


    private class ParserCache extends
            AbstractCache<Parser, Class<? extends Parser>> {
        @Override
        protected Parser load(Class<? extends Parser> clazz) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                log.error("Error while creating parser: " + clazz.getName(), e);
                return null;
            }
        }
    }
}

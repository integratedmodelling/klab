/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.utils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.utils.Pair;

// TODO: Auto-generated Javadoc
/**
 * TODO API to review; actions and tests to be shifted to handler.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class DirectoryWatcher {

    private WatchService                      watcher    = null;
    private Map<WatchKey, Path>               keys       = new HashMap<>();
    private Map<Path, WatchKey>               keypaths   = new HashMap<>();
    
    private boolean isRelevant(Path file) {
        // TODO use configuration
        return true;
    }
    
    /*
     * do not make this private.
     */
    Map<Path, Pair<Long, WatchEvent.Kind<?>>> fileEvents = new HashMap<>();

    /*
     * One of these threads is started the first time a file is modified. It will start a
     * timer and monitor events for the file until the last event on the file is older
     * than a given time. At that point it will report the last file event to the project
     * manager.
     */
    class FileChangeActuator extends Thread {

        Path _file;
        long _idleTime;

        FileChangeActuator(Path file, long idleTime) {
            _file = file;
            _idleTime = idleTime;
        }

        @Override
        public void run() {

            for (;;) {

                long time = new Date().getTime();
                boolean oldEnough = false;

                synchronized (fileEvents) {
                    long delta = time - fileEvents.get(_file).getFirst();
                    oldEnough = delta >= _idleTime;
                }

                if (oldEnough) {
                    Kind<?> lastEvent = null;
                    synchronized (fileEvents) {
                        lastEvent = fileEvents.get(_file).getSecond();
                        fileEvents.remove(_file);
                    }
                    notifyFileEvent(_file, lastEvent);
                    return;
                }

                synchronized (fileEvents) {
                    fileEvents.get(_file).setFirst(time);
                }
                try {
                    Thread.sleep(_idleTime);
                } catch (InterruptedException e) {
                }
            }
        }
    }



    /**
     * Problem: on fast machines, resource info can be null due to asyncronicity, and namespaces do
     * not get notified. That does not happen when debugging.
     *
     * @param path the path
     * @param lastEvent the last event
     */
    public void notifyFileEvent(Path path, Kind<?> lastEvent) {

        // System.out.println("FE: init");

//        ResourceInfo rinfo = resourceInfo.get(path.toFile());
//        if (rinfo != null) {
//            // System.out.println("FE: rinfo != null");
//            if (rinfo.namespace != null) {
//                // System.out.println("FE: namespace != null");
//
//                for (IProjectLifecycleListener listener : listeners) {
//                    if (lastEvent.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
//                        listener.namespaceModified(rinfo.namespace
//                                .getId(), rinfo.project);
//                    } else if (lastEvent.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
//                        listener.namespaceDeleted(rinfo.namespace.getId(), rinfo.project);
//                    }
//                }
//            }
//        } else {
//
//            // System.out.println("FE: rinfo == null");
//
//            /*
//             * TODO may be a properties file event or a new namespace created.
//             */
//            IProject project = getProjectForPath(path);
//            if (project != null) {
//                for (IProjectLifecycleListener listener : listeners) {
//                    if (path.toString().contains("klab.properties")
//                            || path.toString().endsWith(".project")) {
//                        listener.projectPropertiesModified(project, path.toFile());
//                    } else {
//                        if (lastEvent.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
//                            listener.fileModified(project, path.toFile());
//                        } else if (lastEvent
//                                .equals(StandardWatchEventKinds.ENTRY_DELETE)) {
//                            listener.fileDeleted(project, path.toFile());
//                        } else if (lastEvent
//                                .equals(StandardWatchEventKinds.ENTRY_CREATE)) {
//                            listener.fileCreated(project, path.toFile());
//                        }
//                    }
//                }
//            }
//        }
    }
    
    class FileMonitor extends Thread {

        @Override
        public void run() {

            for (;;) {
                /*
                 * re-check every whole second if there is nothing to watch.
                 */
                // synchronized (keys) {
                if (keys.isEmpty()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {

                    WatchKey key;
                    try {
                        key = watcher.take();
                    } catch (InterruptedException x) {
                        /*
                         * TODO - continue? Signal an error? Does this happen?
                         */
                        return;
                    }

                    Path dir = keys.get(key);
                    if (dir == null) {
                        continue;
                    }

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        // TODO - handle OVERFLOW (?)
                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            continue;
                        }

                        // Context for directory entry event is the file name of
                        // entry
                        WatchEvent<Path> ev = cast(event);
                        Path name = ev.context();
                        Path child = dir.resolve(name);

                        if (!isRelevant(child)) {
                            continue;
                        }

                        // TODO use event
                        notifyFileEvent(event, child);

                        // if directory is created, and watching recursively,
                        // then
                        // register it and its sub-directories
                        if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                            try {
                                if (Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS)) {
                                    watchProjectDirectory(child);
                                }
                            } catch (IOException x) {
                                // don't see why this should happen
                            }
                        }
                    }

                    // reset key and remove from set if directory no longer
                    // accessible
                    boolean valid = key.reset();
                    if (!valid) {
                        keys.remove(key);

                        // all directories are inaccessible
                        if (keys.isEmpty()) {
                            break;
                        }
                    }
                    // }
                }
            }
        }

        private void notifyFileEvent(WatchEvent<?> event, Path child) {

            long time = new Date().getTime();

            synchronized (fileEvents) {
                boolean isNew = !fileEvents.containsKey(child);
                fileEvents.put(child, new Pair<Long, WatchEvent.Kind<?>>(time, event
                        .kind()));
                if (isNew) {
                    new FileChangeActuator(child, 500).start();
                }
            }

        }
    }

    
    /**
     * Instantiates a new directory watcher.
     */
    public DirectoryWatcher() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Register the given directory and all its sub-directories with the watch service.
     */
    private void watchProjectDirectory(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                watch(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Unregister the given directory and all its sub-directories with the watch service.
     * 
     * @throws IOException
     */
    private void unwatchProjectDirectory(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                try {
                    unwatch(dir);
                } catch (IOException e) {
                    //
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Register the given directory with the WatchService.
     *
     * @param dir the dir
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void watch(Path dir) throws IOException {

        WatchKey key = dir
                .register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        keys.put(key, dir);
        keypaths.put(dir, key);
    }

    /**
     * Unwatch.
     *
     * @param dir the dir
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void unwatch(Path dir) throws IOException {

        WatchKey key = keypaths.get(dir);
        if (key != null) {
            key.cancel();
            keypaths.remove(dir);
            keys.remove(key);
        }
    }
    
    @SuppressWarnings("unchecked")
    private static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }
}

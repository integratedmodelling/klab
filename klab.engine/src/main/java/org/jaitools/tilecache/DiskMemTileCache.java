/* 
 *  Copyright (c) 2009-2011, Michael Bedward. All rights reserved. 
 *   
 *  Redistribution and use in source and binary forms, with or without modification, 
 *  are permitted provided that the following conditions are met: 
 *   
 *  - Redistributions of source code must retain the above copyright notice, this  
 *    list of conditions and the following disclaimer. 
 *   
 *  - Redistributions in binary form must reproduce the above copyright notice, this 
 *    list of conditions and the following disclaimer in the documentation and/or 
 *    other materials provided with the distribution.   
 *   
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */   

package org.jaitools.tilecache;

import java.awt.Point;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.imagen.CachedTile;
import org.eclipse.imagen.PlanarImage;
import org.eclipse.imagen.TileCache;

import org.jaitools.CollectionFactory;
import org.jaitools.DaemonThreadFactory;


/**
 * This class implements ImageN {@linkplain org.eclipse.imagen.TileCache}. It can store
 * cached tiles on disk to allow applications to work with very large volumes of
 * tiled image data without being limited by available memory. A subset of tiles 
 * (by default, the most recently accessed) are cached in memory to reduce access
 * time.
 * <p>
 * 
 * The default behaviour is to cache newly added tiles into memory. If the cache
 * needs to free memory to accommodate a tile, it does so by removing lowest priority
 * tiles from memory and caching them to disk. Optionally, the user can specify
 * that newly added tiles are cached to disk immediately.
 * <p>
 * 
 * Unlike the standard ImageN {@code TileCache} implementation, resident tiles are cached
 * using strong references. This is to support the use of this class with
 * {@linkplain org.jaitools.tiledimage.DiskMemImage} as well as operations that need to
 * cache tiles that are expensive to create (e.g. output of a time-consuming analysis).
 * A disadvantage of this design is that when the cache is being used for easily
 * generated tiles it can end up unnecessarily holding memory that is more urgently
 * required by other parts of an application. To avoid this happening, the cache can
 * be set to auto-flush resident tiles at regular intervals.
 * <p>
 * 
 * <h4>Implementation note</h4>
 * Tile polling and auto-flushing of memory resident tiles (if enabled) both run
 * on low-priority background threads. These are marked as daemon threads to 
 * avoid these services blocking application shutdown.
 *
 * @author Michael Bedward
 * @author Simone Giannecchini, GeoSolutions SAS
 *
 * @since 1.0
 * @version $Id$
 * 
 * @see DiskCachedTile
 * @see TileAccessTimeComparator
 */
public class DiskMemTileCache extends Observable implements TileCache {
    
    private static final Logger LOGGER = Logger.getLogger("org.jaitools.tilecache");

    /**
     * The default memory capacity in bytes (64 * 2^20 = 64Mb)
     * @see #setMemoryCapacity(long)
     */
    public static final long DEFAULT_MEMORY_CAPACITY = 64L * 1024L * 1024L;

    /**
     * The default memory threshold value (0.75)
     * 
     * @see #setMemoryThreshold(float)
     */
    public static final float DEFAULT_MEMORY_THRESHOLD = 0.75F;

    /**
     * The default minimum period (2.5 seconds) of cache inactivity that 
     * must elapse before memory-resident tiles are automatically flushed.
     * 
     * @see #setAutoFlushMemoryInterval(long)
     */
    public static final long DEFAULT_AUTO_FLUSH_MEMORY_INTERVAL = 2500;
    
    /**
     * The default interval (2 seconds) for polling each tile to check if 
     * its owning image has been garbage collected.
     * 
     * @see #setTilePollingInterval(long) 
     */
    public static final long DEFAULT_TILE_POLLING_INTERVAL = 2000L;


    // @todo use ImageN ParameterList or some other ready-made class for this ?
    private static class ParamDesc {
        String key;
        Class<?> clazz;
        Object defaultValue;

        ParamDesc(String key, Class<?> clazz, Object defaultValue) {
            this.key = key; this.clazz = clazz; this.defaultValue = defaultValue;
        }

        boolean typeOK (Object value) {
            if (Number.class.isAssignableFrom(clazz)) {
                return Number.class.isAssignableFrom(value.getClass());

            } else {
                return clazz.isAssignableFrom(value.getClass());
            }
        }
    }

    /**
     * Key for the parameter controlling initial memory capacity of the
     * tile cache. This determines the maximum number of tiles that can
     * be resident concurrently. The value must be numeric and will be
     * treated as Long.
     * @see #setMemoryCapacity(long)
     * @see #DEFAULT_MEMORY_CAPACITY
     */
    public static final String KEY_INITIAL_MEMORY_CAPACITY = "memcapacity";

    /**
     * Key for the parameter controlling whether newly added tiles
     * are immediately cached to disk as well as in memory. The value
     * must be one Boolean. If the value is {@code Boolean.FALSE} (the default),
     * disk caching of tiles is deferred until required (ie. when
     * memory needs to be freed for other tiles).
     */
    public static final String KEY_ALWAYS_DISK_CACHE = "diskcache";

    /**
     * Key for the parameter controlling whether the cache will auto-flush
     * memory-resident tiles. The value must be Boolean. If the value is
     * {@code Boolean.TRUE}, auto-flushing of resident tiles will be enabled
     * when the cache is created. The default is {@code Boolean.FALSE}.
     * @see #setAutoFlushMemoryEnabled(boolean)
     */
    public static final String KEY_AUTO_FLUSH_MEMORY_ENABLED = "enableautoflush";

    /**
     * Key for the cache auto-flush interval parameter. The value must be numeric
     * and represents the interval, in milliseconds, between auto-flushes of
     * resident tiles. Values less than or equal to zero are ignored.
     * @see #setAutoFlushMemoryInterval(long)
     * @see #DEFAULT_AUTO_FLUSH_MEMORY_INTERVAL
     */
    public static final String KEY_AUTO_FLUSH_MEMORY_INTERVAL = "autoflushinterval";

    private static final Map<String, ParamDesc> paramDescriptors;
    static {
        ParamDesc desc;
        paramDescriptors = new HashMap<String, ParamDesc>();

        desc = new ParamDesc(KEY_INITIAL_MEMORY_CAPACITY, Number.class, DEFAULT_MEMORY_CAPACITY);
        paramDescriptors.put( desc.key, desc );

        desc = new ParamDesc(KEY_ALWAYS_DISK_CACHE, Boolean.class, Boolean.FALSE);
        paramDescriptors.put( desc.key, desc );

        desc = new ParamDesc(KEY_AUTO_FLUSH_MEMORY_ENABLED, Boolean.class, Boolean.FALSE);
        paramDescriptors.put( desc.key, desc );

        desc = new ParamDesc(KEY_AUTO_FLUSH_MEMORY_INTERVAL, Number.class, DEFAULT_AUTO_FLUSH_MEMORY_INTERVAL);
        paramDescriptors.put( desc.key, desc );
    }

    // maximum memory available for resident tiles
    private long memCapacity;

    // current memory used for resident tiles
    private long curMemory;

    /*
     * A value between 0.0 and 1.0 that may be used for memory control
     * if the param KEY_USE_MEMORY_THRESHOLD is TRUE.
     */
    private float memThreshold;

    private boolean writeNewTilesToDisk;

    /**
     * Map of all cached tiles.
     */
    protected Map<Object, DiskCachedTile> tiles;
    
    /**
     * Memory-resident tiles.
     */
    protected Map<Object, Raster> residentTiles;

    /**
     * A tile comparator used to determine the priority of tiles for
     * storage in memory.
     */
    private Comparator<CachedTile> comparator;

    /* List of tile references that is sorted into tile priority order when
     * required for memory swapping.
     * <p>
     * Implementation note: we use this in preference to a SortedSet or similar
     * because of the complications of using the remove(obj) method with a sorted
     * collection, where the comparator is used rather than the equals method.
     */
    
    /**
     * Tiles sorted according to the current tile priority comparator.
     */
    protected List<DiskCachedTile> sortedResidentTiles;

    // whether to send cache diagnostics to observers
    private boolean diagnosticsEnabled;
    
    // Lock for tile access
    private final ReentrantLock tileLock = new ReentrantLock();

    // Variables used for auto-flushing of resident tiles
    private ScheduledExecutorService flushService;
    private ScheduledFuture flushFuture;
    private long autoFlushInterval = DEFAULT_AUTO_FLUSH_MEMORY_INTERVAL;
    private AtomicBoolean okToFlush = new AtomicBoolean(false);
    
    // Variables used for polling each tile to check if its owning image has been 
    // garbage collected
    private final ScheduledExecutorService tilePollingService;
    private ScheduledFuture tilePollingFuture;
    private long tilePollingInterval = DEFAULT_TILE_POLLING_INTERVAL; 

    
    /**
     * Creates a new cache with all parameters set to their default values.
     */
    public DiskMemTileCache() {
        this(null);
    }

    /**
     * Creates a new cache.
     *
     * @param params an optional map of parameters (may be empty or {@code null})
     */
    public DiskMemTileCache(Map<String, Object> params) {
        if (params == null) {
            params = Collections.emptyMap();
        }

        diagnosticsEnabled = false;
        tiles = new HashMap<Object, DiskCachedTile>();
        residentTiles = CollectionFactory.map();
        curMemory = 0L;
        memThreshold = DEFAULT_MEMORY_THRESHOLD;

        Object o;
        ParamDesc desc;

        desc= paramDescriptors.get(KEY_INITIAL_MEMORY_CAPACITY);
        memCapacity = (Long)desc.defaultValue;
        o = params.get(desc.key);
        if (o != null) {
            if (desc.typeOK(o)) {
                memCapacity = ((Number)o).longValue();
            }
        }

        desc = paramDescriptors.get(KEY_ALWAYS_DISK_CACHE);
        writeNewTilesToDisk = (Boolean)desc.defaultValue;
        o = params.get(desc.key);
        if (o != null) {
            if (desc.typeOK(o)) {
                writeNewTilesToDisk = (Boolean)o;
            }
        }

        desc = paramDescriptors.get(KEY_AUTO_FLUSH_MEMORY_INTERVAL);
        autoFlushInterval = ((Number)desc.defaultValue).longValue();
        o = params.get(desc.key);
        if (o != null) {
            if (desc.typeOK(o)) {
                long lval = ((Number)o).longValue();
                if (lval > 0) {
                    autoFlushInterval = lval;
                }
            }
        }

        desc = paramDescriptors.get(KEY_AUTO_FLUSH_MEMORY_ENABLED);
        o = params.get(desc.key);
        if (o != null) {
            if (desc.typeOK(o)) {
                setAutoFlushMemoryEnabled((Boolean)o);
            }
        }

        comparator = new TileAccessTimeComparator();
        sortedResidentTiles = new ArrayList<DiskCachedTile>();

        tilePollingService = Executors.newSingleThreadScheduledExecutor(
                new DaemonThreadFactory(Thread.MIN_PRIORITY, "cache-polling"));
        
        startTilePolling();
    }

    /**
     * Adds a tile to the cache if not already present.
     *
     * @param owner the image that this tile belongs to
     * @param tileX the tile column
     * @param tileY the tile row
     * @param data the tile data
     */
    public void add(RenderedImage owner, int tileX, int tileY, Raster data) {
        add(owner, tileX, tileY, data, null);
    }
		 
    /**
     * Adds a tile to the cache if not already present.
     *
     * @param owner the image that this tile belongs to
     * @param tileX the tile column
     * @param tileY the tile row
     * @param data the tile data
     * @param tileCacheMetric optional tile cache metric (may be {@code null}
     */
    public void add(RenderedImage owner,
                int tileX,
                int tileY,
                Raster data,
                Object tileCacheMetric) {

        tileLock.lock();

        try {
            okToFlush.set(false);
            Object key = getTileId(owner, tileX, tileY);
            if (tiles.containsKey(key)) {
                // tile is already cached
                return;
            }

            DiskCachedTile tile = new DiskCachedTile(
                    key, owner, tileX, tileY, data, writeNewTilesToDisk, tileCacheMetric);
            tiles.put(key, tile);

            if ( makeResident(tile, data) ) {
                tile.setAction(DiskCachedTile.TileAction.ACTION_ADDED_RESIDENT);
            } else {
                tile.setAction(DiskCachedTile.TileAction.ACTION_ADDED);
            }

            if (diagnosticsEnabled) {
                setChanged();
                notifyObservers(tile);
            }

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Unable to cache this tile on disk", ex);
            
        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Removes a tile from the cache.
     * 
     * @param owner the image that this tile belongs to
     * @param tileX the tile column
     * @param tileY the tile row
     */
    public void remove(RenderedImage owner, int tileX, int tileY) {
        tileLock.lock();

        try {
            okToFlush.set(false);
            Object key = getTileId(owner, tileX, tileY);
            DiskCachedTile tile = tiles.get(key);

            if (tile == null) {
                return;
            }

            if (residentTiles.containsKey(key)) {
                try {
                    removeResidentTile(key, false);

                } catch (DiskCacheFailedException ex) {
                    /*
                     * It would be nicer to just throw this exception
                     * upwards but we can't in the overidden method
                     */
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }

            tile.deleteDiskCopy();

            tile.setAction(DiskCachedTile.TileAction.ACTION_REMOVED);
            if (diagnosticsEnabled) {
                setChanged();
                notifyObservers(tile);
            }

            tiles.remove(key);
            
        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Gets the specified tile from the cache if present. If the tile is
     * cached but not resident in memory it will be read from the cache's
     * disk storage and made resident.
     *
     * @param owner the image that the tile belongs to
     * @param tileX the tile column
     * @param tileY the tile row
     * @return the requested tile or {@code null} if the tile was not cached
     */
    public Raster getTile(RenderedImage owner, int tileX, int tileY) {
        tileLock.lock();

        try {
            okToFlush.set(false);
            Raster r = null;
            Object key = getTileId(owner, tileX, tileY);

            DiskCachedTile tile = tiles.get(key);
            if (tile != null) {

                // is the tile resident ?
                r = residentTiles.get(key);
                if (r == null) {
                    /*
                     * The tile is not resident. Attempt
                     * to read it from the disk.
                     */
                    r = tile.readData();
                    if (r == null) {
                        /* The tile was not cached on disk. It may have
                         * been resident only, and then flushed.
                         */
                        return null;
                    }

                    if (makeResident(tile, r)) {
                        tile.setAction(DiskCachedTile.TileAction.ACTION_RESIDENT);
                        if (diagnosticsEnabled) {
                            setChanged();
                            notifyObservers(tile);
                        }
                    }
                }

                tile.setAction(DiskCachedTile.TileAction.ACTION_ACCESSED);
                tile.setTileTimeStamp(System.currentTimeMillis());

                if (diagnosticsEnabled) {
                    setChanged();
                    notifyObservers(tile);
                }
            }

            return r;
        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Gets all cached tiles associated with the given image.
     * The tiles will be loaded into memory as space allows.
     * 
     * @param owner the image for which tiles are requested
     * @return an array of tile Rasters
     */
    public Raster[] getTiles(RenderedImage owner) {
        tileLock.lock();

        try {
            okToFlush.set(false);
            int minX = owner.getMinTileX();
            int minY = owner.getMinTileY();
            int numX = owner.getNumXTiles();
            int numY = owner.getNumYTiles();

            List<Object> keys = new ArrayList<Object>();
            for (int y = minY, ny = 0; ny < numY; y++, ny++) {
                for (int x = minX, nx = 0; nx < numX; x++, nx++) {
                    Object key = getTileId(owner, x, y);
                    if (tiles.containsKey(key)) {
                        keys.add(key);
                    }
                }
            }

            Raster[] rasters = new Raster[keys.size()];
            int k = 0;
            for (Object key : keys) {
                DiskCachedTile tile = tiles.get(key);
                Raster r = residentTiles.get(tile.getTileId());
                if (r == null) {
                    r = tile.readData();
                    makeResident(tile, r);
                }

                rasters[k++] = r;

                tile.setTileTimeStamp(System.currentTimeMillis());
                tile.setAction(DiskCachedTile.TileAction.ACTION_ACCESSED);
                if (diagnosticsEnabled) {
                    setChanged();
                    notifyObservers(tile);
                }
            }

            return rasters;
            
        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Removes all tiles that belong to the given image from the cache.
     * 
     * @param owner the image owning the tiles to be removed
     */
    public void removeTiles(RenderedImage owner) {
        tileLock.lock();
        try {
            for (int y = owner.getMinTileY(), ny = 0; ny < owner.getNumYTiles(); y++, ny++) {
                for (int x = owner.getMinTileX(), nx = 0; nx < owner.getNumXTiles(); x++, nx++) {
                    remove(owner, x, y);
                }
            }
        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Sets the interval between polling each tile to check if its owning image
     * has been garbage collected. Any such tiles are removed from the
     * cache.
     *
     * @param interval interval in milliseconds
     *        (values less than or equal to zero are ignored)
     */
    public void setTilePollingInterval(long interval) {
        if (interval > 0 && interval != tilePollingInterval) {
            stopTilePolling();
            tilePollingInterval = interval;
            startTilePolling();
        }
    }

    /**
     * Sets the interval between polling each tile to check if its owning image
     * has been garbage collected. Any such tiles are removed from the
     * cache.     *
     * @return interval in milliseconds
     */
    public long getTilePollingInterval() {
        return tilePollingInterval;
    }

    /**
     * Starts the tile polling task which calls {@link #removeNullTiles()}
     * at a fixed interval.
     */
    private void startTilePolling() {
        if (!isPollingTiles()) {
            tilePollingFuture = tilePollingService.scheduleAtFixedRate(
                    new Runnable() {
                        public void run() {
                            removeNullTiles();
                        }
                    }, 
                    tilePollingInterval, 
                    tilePollingInterval, 
                    TimeUnit.MILLISECONDS);
        }
    }
    
    /**
     * Stops the tile polling task.
     */
    private void stopTilePolling() {
        if (isPollingTiles()) {
            tilePollingFuture.cancel(true);
            tilePollingFuture = null;
        }
    }
    
    private boolean isPollingTiles() {
        return tilePollingFuture != null && !tilePollingFuture.isDone();
    }

    /**
     * Checks if any tiles have a {@code null} owner (e.g. owning image has been
     * garbage collected) and, if so, removes them from the cache.
     */
    private void removeNullTiles() {
        if (!tileLock.tryLock()) {  // jumps the queue of waiting threads
            return;
        }

        try {
            Set<Object> nullTileKeys = CollectionFactory.set();
            for (Object key : tiles.keySet()) {
                DiskCachedTile tile = tiles.get(key);
                if (tile.getOwner() == null) {
                    nullTileKeys.add(key);
                }
            }

            for (Object key : nullTileKeys) {
                DiskCachedTile tile = tiles.get(key);
                tile.deleteDiskCopy();
                if (residentTiles.containsKey(key)) {
                    residentTiles.remove(key);
                    sortedResidentTiles.remove(tile);
                    curMemory -= tile.getTileSize();
                }
                tiles.remove(key);
            }

        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Adds all tiles for the given image to the cache.
     * 
     * @param owner the image that the tiles belong to
     * @param tileIndices an array of Points specifying the column-row coordinates
     * of each tile
     * @param tiles tile data in the form of Raster objects
     * @param tileCacheMetric optional metric (may be {@code null})
     */
    public void addTiles(RenderedImage owner,
                     Point[] tileIndices,
                     Raster[] tiles,
                     Object tileCacheMetric) {

        if (tileIndices.length != tiles.length) {
            throw new IllegalArgumentException(
                    "tileIndices and tiles args must be the same length");
        }

        tileLock.lock();
        try {
            for (int i = 0; i < tiles.length; i++) {
                add(owner, tileIndices[i].x, tileIndices[i].y, tiles[i], tileCacheMetric);
            }
            
        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Gets the specified tiles for the given image.
     *
     * @param owner the image that the tiles belong to
     * @param tileIndices an array of Points specifying the column-row coordinates
     * of each tile
     * @return data for the requested tiles as Raster objects
     */
    public Raster[] getTiles(RenderedImage owner, Point[] tileIndices) {
        tileLock.lock();
        try {
            Raster[] r = null;
            
            if (tileIndices.length > 0) {
                r = new Raster[tileIndices.length];
                for (int i = 0; i < tileIndices.length; i++) {
                    r[i] = getTile(owner, tileIndices[i].x, tileIndices[i].y);
                }
            }

            return r;

        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Removes ALL tiles from the cache: all resident tiles will be
     * removed from memory and all files for disk-cached tiles will
     * be discarded.
     * <p>
     * The update action of each tile will be set to {@linkplain DiskCachedTile#ACTION_REMOVED}.
     */
    public void flush() {
        tileLock.lock();

        try {
            flushMemory();

            for (DiskCachedTile tile : tiles.values()) {
                tile.deleteDiskCopy();
                tile.setAction(DiskCachedTile.TileAction.ACTION_REMOVED);
                if (diagnosticsEnabled) {
                    setChanged();
                    notifyObservers(tile);
                }
            }
            tiles.clear();

        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Removes all resident tiles from memory. No rewriting of tile data
     * to disk is done.
     */
    public void flushMemory() {
        tileLock.lock();
        try {
            residentTiles.clear();
            sortedResidentTiles.clear();
            curMemory = 0;
            
        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Frees memory for resident tiles so that the fraction of memory occupied is
     * no more than the current value of the mamory threshold. 
     *
     * @see DiskMemTileCache#setMemoryThreshold(float)
     */
    public void memoryControl() {
        tileLock.lock();
        try {
            long maxUsed = (long) (memThreshold * memCapacity);
            long toFree = curMemory - maxUsed;
            if (toFree > 0) {
                defaultMemoryControl(toFree);
            }
        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Makes the requested amount of memory cache available, removing
     * resident tiles as necessary.
     *
     * @param memRequired memory requested (bytes)
     */
    private void defaultMemoryControl( long memRequired ) {
        if (memRequired > memCapacity) {
            // @todo something better than this...
            throw new RuntimeException("space required is greater than cache memory capacity");
        }

        /*
         * Remove one or more lowest priority tiles to free
         * space
         */
        Collections.sort(sortedResidentTiles, comparator);
        while (memCapacity - curMemory < memRequired && !sortedResidentTiles.isEmpty()) {
            Object key = sortedResidentTiles.get(sortedResidentTiles.size()-1).getTileId();

            try {
                removeResidentTile(key, true);
            } catch (DiskCacheFailedException ex) {
                /*
                 * It would be nicer to just throw this exception
                 * upwards be we can't in the overidden method
                 */
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }


    /**
     * Does nothing.
     * 
     * @deprecated Please do not use this method  
     */
    @Deprecated
    public void setTileCapacity(int arg0) {
    }

    /**
     * Always returns 0.
     * 
     * @deprecated Please do not use this method  
     */
    public int getTileCapacity() {
        return 0;
    }

    /**
     * Resets the memory capacity of the cache. Setting capacity to 0 will
     * flush all resident tiles from memory. Setting a capacity less than the
     * current capacity could cause some memory-resident tiles being
     * removed from memory.
     *
     * @param newCapacity requested memory capacity for resident tiles
     */
    public void setMemoryCapacity(long newCapacity) {
        tileLock.lock();

        try {
            okToFlush.set(false);
            if (newCapacity < 0) {
                throw new IllegalArgumentException("memory capacity must be >= 0");
            }

            long oldCapacity = memCapacity;
            memCapacity = newCapacity;

            if (newCapacity == 0) {
                flushMemory();

            } else if (newCapacity < oldCapacity && curMemory > newCapacity) {
                /*
                 * Note: we free memory here directly rather than using
                 * memoryControl or defaultMemoryControl methods because
                 * they will fail when memCapacity has been reduced
                 */
                Collections.sort(sortedResidentTiles, comparator);
                while (curMemory > newCapacity) {
                    Object key = sortedResidentTiles.get(sortedResidentTiles.size() - 1).getTileId();
                    try {
                        removeResidentTile(key, true);
                    } catch (DiskCacheFailedException ex) {
                        /*
                         * It would be nicer to just throw this exception
                         * upwards be we can't in the overidden method
                         */
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
            }
        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Gets the amount of memory, in bytes, allocated for storage of
     * resident tiles.
     *
     * @return resident tile memory capacity in bytes
     */
    public long getMemoryCapacity() {
        return memCapacity;
    }

    /**
     * Gets the amount of memory currently being used for storage of
     * memory-resident tiles.
     *
     * @return current memory use in bytes
     */
    public long getCurrentMemory() {
        return curMemory;
    }

    /**
     * Sets the memoryThreshold value to a floating point number that ranges from
     * 0.0 to 1.0. When the cache memory is full, the memory usage will be reduced
     * to this fraction of the total cache memory capacity. For example, a value
     * of .75 will cause 25% of the memory to be cleared, while retaining 75%.
     *
     * @param newThreshold the new memory threshold between 0 and 1
     * 
     * @throws IllegalArgumentException if the memoryThreshold is less than 0.0 or greater than 1.0
     */
    public void setMemoryThreshold(float newThreshold) {
        if (newThreshold < 0.0F) {
            memThreshold = 0.0F;
        } else if (newThreshold > 1.0F) {
            memThreshold = 1.0F;
        } else {
            memThreshold = newThreshold;
        }

        memoryControl();
    }

    /**
     * Returns the memory threshold, which is the fractional amount of cache memory
     * to retain during tile removal. This only applies if memory thresholding has
     * been enabled by passing the parameter {@linkplain #KEY_USE_MEMORY_THRESHOLD} to
     * the constructor with a value of {@code Boolean.TRUE}.
     *
     * @return the retained fraction of memory
     */
    public float getMemoryThreshold() {
        return memThreshold;
    }

    /**
     * Sets the comparator to use to assign memory-residence priority to
     * tiles. If {@code comp} is {@code null} the default comparator
     * ({@link TileAccessTimeComparator}) will be used.
     * 
     * @param comp the comparator or {@code null} for the default
     */
    public void setTileComparator(Comparator comp) {
        tileLock.lock();
        try {
            if (comp == null) {
                // switch to default comparator based on tile access time
                comparator = new TileAccessTimeComparator();
            } else {
                comparator = comp;
            }

            sortedResidentTiles = new ArrayList<DiskCachedTile>();
            for (Object key : residentTiles.keySet()) {
                sortedResidentTiles.addAll(tiles.values());
            }
            Collections.sort(sortedResidentTiles, comparator);
            
        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Gets the comparator currently used to assign memory-residence
     * priority to tiles.
     * 
     * @return the current comparator
     */
    public Comparator getTileComparator() {
        return comparator;
    }

    /**
     * Gets the total number of tiles currently in the cache.
     * 
     * @return number of cached tiles
     */
    public int getNumTiles() {
        return tiles.size();
    }

    /**
     * Gets the number of tiles currently residing in the
     * cache's memory storage.
     * 
     * @return number of memory-resident tiles
     */
    public int getNumResidentTiles() {
        return residentTiles.size();
    }

    /**
     * Checks whether a given tile is in this cache.
     * 
     * @param owner the owning image
     * @param tileX tile column
     * @param tileY tile row
     * @return {@code true} if the cache contains the tile; {@code false} otherwise
     */
    public boolean containsTile(RenderedImage owner, int tileX, int tileY) {
        Object key = getTileId(owner, tileX, tileY);
        return tiles.containsKey(key);
    }

    /**
     * Checks whether a given tile is in this cache's memory storage.
     *
     * @param owner the owning image
     * @param tileX tile column
     * @param tileY tile row
     * @return {@code true} if the tile is in cache memory; {@code false} otherwise
     */
    public boolean containsResidentTile(RenderedImage owner, int tileX, int tileY) {
        Object key = getTileId(owner, tileX, tileY);
        return residentTiles.containsKey(key);
    }

    /**
     * Informs the cache that a tile's data have changed. The tile should
     * be resident in memory as the result of a previous {@code getTile}
     * request. If this is the case and the tile was previously written to
     * disk, then the cache's disk copy of the tile will be refreshed.
     * <P>
     * If the tile is not resident in memory, for instance
     * because of memory swapping for other tile accesses, the disk copy
     * will not be refreshed and a {@code TileNotResidentException} is
     * thrown.
     *
     * @param owner the owning image
     * @param tileX tile column
     * @param tileY tile row
     * @throws TileNotResidentException if the tile is not resident
     * @throws DiskCacheFailedException if the tile is cached to disk but its data could
     *     not be updated
     */
    public void setTileChanged(RenderedImage owner, int tileX, int tileY)
            throws TileNotResidentException, DiskCacheFailedException {

        tileLock.lock();
        try {
            okToFlush.set(false);
            Object tileId = getTileId(owner, tileX, tileY);
            Raster r = residentTiles.get(tileId);
            if (r == null) {
                throw new TileNotResidentException(owner, tileX, tileY);
            }

            DiskCachedTile tile = tiles.get(tileId);
            if (tile.cachedToDisk()) {
                try {
                    tile.writeData(r);
                } catch (IOException ioEx) {
                    throw new DiskCacheFailedException(owner, tileX, tileY);
                }
            }
            
        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Enables or disables auto-flushing of memory resident with the
     * currently set minimum interval.
     *
     * @param enable {@code true} to enable auto-flushing; {@code false} to disable
     * @see #setAutoFlushMemoryInterval(long)
     */
    public final void setAutoFlushMemoryEnabled(boolean enable) {
        if (enable) {
            if (!isAutoFlushMemoryEnabled()) {
                if (flushService == null) {
                    flushService = Executors.newSingleThreadScheduledExecutor(
                            new DaemonThreadFactory(Thread.MIN_PRIORITY, "cache-flush"));
                }

                flushFuture = flushService.scheduleWithFixedDelay(
                        new Runnable() {
                            public void run() {
                                if (okToFlush.getAndSet(true)) {
                                    flushMemory();
                                }
                            }
                        }, 
                        autoFlushInterval, 
                        autoFlushInterval, 
                        TimeUnit.MILLISECONDS);
            }

        } else if (isAutoFlushMemoryEnabled()) {
            flushFuture.cancel(true);
        }
    }

    /**
     * Checks whether auto-flushing of memory-resident tiles is currently enabled.
     *
     * @return {@code true} if the cache is auto-flushing; {@code false} otherwise
     */
    public boolean isAutoFlushMemoryEnabled() {
        return (flushFuture != null && !flushFuture.isDone());
    }

    /**
     * Sets the minimum period of cache inactivity, in milliseconds, that must
     * elapse before automatically flushing memory-resident tiles.
     *
     * @param interval interval in milliseconds
     *        (values less than or equal to zero are ignored)
     */
    public void setAutoFlushMemoryInterval(long interval) {
        if (interval > 0 && interval != autoFlushInterval) {
            if (isAutoFlushMemoryEnabled()) {
                setAutoFlushMemoryEnabled(false);
            }
            autoFlushInterval = interval;
            setAutoFlushMemoryEnabled(true);
        }
    }
    
    /**
     * Gets the current auto-flush interval. This is the minimum period of 
     * cache inactivity, in milliseconds, that must elapse before 
     * automatically flushing tiles.
     *
     * @return interval in milliseconds
     */
    public long getAutoFlushMemoryInterval() {
        return autoFlushInterval;
    }

    /**
     * Enables or disables the publishing of cache messages to Observers.
     *
     * @param state {@code true} to publish diagnostic messages; {@code false} to suppress them
     */
    public void setDiagnostics(boolean state) {
        diagnosticsEnabled = state;
    }

    /**
     * Accepts a {@code DiskMemCacheVisitor} object and calls its
     * {@code visit} method for each tile in the cache.
     * 
     * @param visitor the visitor
     */
    public void accept(DiskMemTileCacheVisitor visitor) {
        tileLock.lock();
        try {
            okToFlush.set(false);
            for (Object key : tiles.keySet()) {
                visitor.visit(tiles.get(key), residentTiles.containsKey(key));
            }
        } finally {
            tileLock.unlock();
        }
    }

    /**
     * Adds a raster to those resident in memory.
     */
    private boolean makeResident(DiskCachedTile tile, Raster data) {
        if (tile.getTileSize() > memCapacity) {
            return false;
        }
        
        if (tile.getTileSize() > memCapacity - curMemory) {
            memoryControl();

            /*
             * It is possible that the threshold rule fails to
             * free enough memory for the tile
             */
            if (tile.getTileSize() > memCapacity - curMemory) {
                defaultMemoryControl(tile.getTileSize());
            }
        }
        
        residentTiles.put(tile.getTileId(), data);
        curMemory += tile.getTileSize();

        /*
         * We don't bother about sort order here. Instead, the list
         * will be sorted by tile priority when resident tiles are
         * being removed
         */
        sortedResidentTiles.add(tile);

        return true;
    }


    /**
     * Removes a tile from the cache's memory storage. This may be to free
     * space for other tiles, in which case {@code writeData} will be
     * set to {@code true} and, if the tile is writable, a request is made to write
     * its data to disk again. If the tile is being removed from the cache
     * entirely, this method will be called with {@code writeData} set
     * to {@code false}.
     *
     * @param tileId the tile's unique id
     * @param writeData if {@code true}, and the tile is writable, its data will be
     * written to disk again; otherwise no writing is done.
     */
    private void removeResidentTile(Object tileId, boolean writeData) throws DiskCacheFailedException {
        DiskCachedTile tile = tiles.get(tileId);
        Raster raster = residentTiles.remove(tileId);
        sortedResidentTiles.remove(tile);
        curMemory -= tile.getTileSize();

        /**
         * If the tile is writable, ie. its data are represented
         * by a WritableRaster, we cache it to disk
         */
        if (writeData && tile.isWritable()) {
            try {
                tile.writeData(raster);
            } catch (IOException ioEx) {
                throw new DiskCacheFailedException(tile.getOwner(), tile.getTileX(), tile.getTileY());
            }
        }

        tile.setAction(DiskCachedTile.TileAction.ACTION_NON_RESIDENT);
        if (diagnosticsEnabled) {
            setChanged();
            notifyObservers(tile);
        }
    }
    

    /**
     * Generates a unique ID for this tile. This uses the same technique as the
     * Sun memory cache implementation: putting the id of the owning image
     * into the upper bytes of a long or BigInteger value and the tile index into
     * the lower bytes.
     * @param owner the owning image
     * @param tileX tile column
     * @param tileY tile row
     * @return the ID as an Object which will be an instance of either Long or BigInteger
     */
    private Object getTileId(RenderedImage owner,
                              int tileX,
                              int tileY) {

        long tileId = tileY * (long)owner.getNumXTiles() + tileX;

        BigInteger imageId = null;

        if (owner instanceof PlanarImage) {
            imageId = (BigInteger)((PlanarImage)owner).getImageID();
        }

        if (imageId != null) {
            byte[] buf = imageId.toByteArray();
            int length = buf.length;
            byte[] buf1 = new byte[buf.length + 8];
            System.arraycopy(buf, 0, buf1, 0, length);
            for (int i = 7, j = 0; i >= 0; i--, j += 8)
                buf1[length++] = (byte)(tileId >> j);
            return new BigInteger(buf1);

        } else {
            tileId &= 0x00000000ffffffffL;
            return Long.valueOf(((long)owner.hashCode() << 32) | tileId);
        }
    }

}

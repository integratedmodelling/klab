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
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferDouble;
import java.awt.image.DataBufferFloat;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.DataBufferUShort;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import org.eclipse.imagen.CachedTile;

/**
 * A managed tile class for {@code DiskMemTileCache}. Represents an image tile
 * that can be cached on disk and/or in memory.
 *
 * @see DiskMemTileCache
 *
 * @author Michael Bedward
 * @author Simone Giannecchini, GeoSolutions SAS
 * @since 1.0
 * @version $Id$
 */
public final class DiskCachedTile implements CachedTile {

    private static final Logger LOGGER = Logger.getLogger("org.jaitools.tilecache");

    /**
     * Constants identifying tile actions.
     */
    public enum TileAction {

        /**
         * Value that will be returned by {@linkplain #getAction()} when
         * the tile has been added to the cache
         */
        ACTION_ADDED(0, "added to cache"),

        /**
         * Value that will be returned by {@linkplain #getAction()} when
         * the tile has been added to the cache and immediately loaded
         * into memory
         */
        ACTION_ADDED_RESIDENT(1, "added to cache and placed into memory"),

        /**
         * Value that will be returned by {@linkplain #getAction()} when
         * the tile becomes resident in memory
         */
        ACTION_RESIDENT(2, "placed into memory"),

        /**
         * Value that will be returned by {@linkplain #getAction()} when
         * the tile is removed from memory
         */
        ACTION_NON_RESIDENT(3, "removed from memory"),

        /**
         * Value that will be returned by {@linkplain #getAction()} when
         * the tile is removed from the cache entirely
         */
        ACTION_REMOVED(4, "removed from the cache"),

        /**
         * Value that will be returned by {@linkplain #getAction()} when
         * the tile is accessed via the cache
         */
        ACTION_ACCESSED(5, "accessed"),

        /**
         * Value that will be returned by {@linkplain #getAction()} when
         * the tile's raster has been garbage collected
         */
        ACTION_GARBAGE_COLLECTED(6, "garbage collected");

        /**
         * Map for the reverse lookup facility
         */
        private static final Map<Integer, TileAction> lookup;
        static {
            lookup = new HashMap<Integer, TileAction>();
            for (TileAction t : EnumSet.allOf(TileAction.class)) {
                lookup.put(t.getAction(), t);
            }
        }
        /** An int associated to this action.*/
        private final int action;
        /** Description of the action. */
        private final String desc;

        /**
         * Private constructor to have maximum control over the values we use for this action.
         * @param action an int associated to this action for interoperability with {@link CachedTile} interface.
         */
        private TileAction(final int action, final String desc) {
            this.action = action;
            this.desc = desc;
        }

        /**
         * Retrieves an int associated to this action for interoperability with {@link CachedTile} interface.
         * @return an int associated to this action for interoperability with {@link CachedTile} interface.
         */
        public int getAction() {
            return action;
        }

        /**
         * Retrieves a description of this action
         * @return a description of this action
         */
        public String getDescription() {
            return desc;
        }

        /**
         * Reverse lookup.
         * @param actionValue integer value of the action
         * @return the corresponding TileAction
         */
        public static TileAction get(int actionValue) {
            return lookup.get(actionValue);
        }

        /**
         * The default action.
         * @return the default action.
         */
        public static TileAction getDefault() {
            return ACTION_ACCESSED;
        }
    }

    /**
     * The prefix used for temporary cache files
     * data
     */
    public static final String FILE_PREFIX = "tile";

    /**
     * The suffix used for temporary cache files
     */
    public static final String FILE_SUFFIX = ".tmp";

    /**
     * The folder used to store tiles that are being newly cached to disk.
     * If {@code null} (the default) the system's default folder is used.
     * It is safe (though not necessarily sensible) to change this property
     * after some tiles have already been cached to disk.
     */
    private static File cacheFolder = null;
    private static final Object folderLock = new Object();

    private final Object id;
    private final WeakReference<RenderedImage> ownerRef;
    private final int tileX;
    private final int tileY;
    private final Object tileCacheMetric;
    private long timeStamp;
    private final int  numBanks;
    private final int  dataLen;
    private final long memorySize;
    private File file;
    private final Point location;
    private final boolean isWritable;

    private TileAction action =TileAction.getDefault();

    /**
     * Get the current cache folder. This is the folder in which
     * newly created temporary files holding tile data will be created.
     *
     * @return a new {@code File} object for the current cache folder
     *         or {@code null} if the default system folder is being
     *         used (ie. the folder corresponding to the {@code java.io.file}
     *         System property
     */
    public static File getCacheFolder() {
        File file = null;
        if (cacheFolder != null) {
            file = new File(cacheFolder.toURI());
        }

        return file;
    }

    /**
     * Set the current cache folder. This is the folder in which
     * newly created temporary files holding tile data will be created.
     * <p>
     * It is safe, though not necessarily sensible, to change the
     * cache folder while one or more caches are running.
     *
     * @param folder the new cache folder or {@code null} to use
     *        the System's default temporary file folder
     */
    public static void setCacheFolder(File folder) {
        synchronized(folderLock) {
            if (folder == null) {
                cacheFolder = null;
            } else {
                cacheFolder = new File(folder.toURI());
            }
        }
    }

    /**
     * Package-private constructor
     *
     * @param id the tile's unique identifier, allocated by the cache
     * @param owner the image to which this tile belongs
     * @param tileX the column index for this tile in the image's tile grid
     * @param tileY the row index for this tile in the image's tile grid
     * @param raster the image data for this tile
     * @param writeToFile if true, the tile's data will be cached to disk
     *        immediately; if false, disk caching is deferred
     * @param tileCacheMetric optional tile cache metric for use in scheduling
     *        (may be {@code null})
     *
     * @throws IOException if an attempt to write the tile to disk fails
     */
    DiskCachedTile(Object id,
                  RenderedImage owner,
                  int tileX,
                  int tileY,
                  Raster raster,
                  boolean writeToFile,
                  Object tileCacheMetric) throws IOException {

        if (owner == null || raster == null) {
            throw new IllegalArgumentException(
                    "All of owner, tile and file args must be non-null");
        }

        this.id = id;
        this.ownerRef = new WeakReference<RenderedImage>(owner);
        this.tileX = tileX;
        this.tileY = tileY;
        this.tileCacheMetric = tileCacheMetric;
        this.location = raster.getBounds().getLocation();
        this.isWritable = (raster instanceof WritableRaster);

        DataBuffer db = raster.getDataBuffer();
        numBanks = db.getNumBanks();
        dataLen = db.getSize();
        memorySize = DataBuffer.getDataTypeSize(db.getDataType()) / 8L * dataLen * numBanks;

        if (writeToFile ) {
            writeData(raster);
        }

        setTileTimeStamp(System.currentTimeMillis());
    }

    /**
     * Returns a string representation of this cached tile
     */
    @Override
    public String toString() {
        RenderedImage o = getOwner();
        String ostring = o == null ? "null" : o.toString();

        Raster t = getTile();
        String tstring = t == null ? "null" : t.toString();

        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
               ": owner = " + ostring +
               " tileX = " + Integer.toString(tileX) +
               " tileY = " + Integer.toString(tileY) +
               " tile = " + tstring +
               " id = " + ((id instanceof Long)? Long.toHexString(((Long)id).longValue()) : id.toString()) +
               " memorySize = " + Long.toString(memorySize) +
               " timeStamp = " + Long.toString(timeStamp) +
               " file = " + file.getPath();
    }

    /**
     * <b>Do not use this method.</b>
     * <p>
     * It is implemented to satisfy the CAchedTile interface but calling it will
     * provoke an UnsupportedOperationException.
     * <p>
     * To get a tile, use {@linkplain DiskBasedTileCache#getTile(java.awt.image.RenderedImage, int, int) }
     */
    public Raster getTile() {
        throw new UnsupportedOperationException("Can't get a tile directly from a DiskCachedTile object");
    }

    /**
     * Gets the image that owns this tile.
     * 
     * @return the owning image
     */
    public RenderedImage getOwner() {
        return ownerRef.get();
    }

    /**
     * Gets the last time of access for this tile.
     * 
     * @return the time stamp
     */
    public long getTileTimeStamp() {
        return timeStamp;
    }

    /**
     * Gets the tile cache metric for this tile.
     * 
     * @return the metric
     */
    public Object getTileCacheMetric() {
        return tileCacheMetric;
    }

    /**
     * Gets the tile size in bytes. This is actually the
     * size of the raster associated with this cached tile instance.
     * 
     * @return tile size in bytes
     */
    public long getTileSize() {
        return memorySize;
    }

    /**
     * Gets the most recent action for this tile. More information can be
     * retrieved by getting the corresponding {@linkplain DiskCachedTile.TileAction}
     * constant as shown here:
     * <pre><code>
     * int code = tile.getAction();
     * TileAction action = TileAction.get(code);
     * System.out.println("tile action: " + action.getDescription());
     * </code></pre>
     * 
     * @return the most recent action
     */
    public int getAction() {
        return action.ordinal();
    }

    /**
     * Queries if this tile has been cached to disk. This method is
     * a short-cut for: {@code getFile() != null}.
     * 
     * @return {@code true} if the tile is cached on disk; {@code false} otherwise
     */
    public boolean cachedToDisk() {
        return file != null;
    }

    /**
     * Gets this tile's disk cache file. Returns {@code null} if the tile has not
     * been cached to disk.
     * 
     * @return the disk cache file for this tile or {@code null}
     */
    public File getFile() {
        return file;
    }

    /**
     * Deletes this tile's disk cache file. If the file could not be deleted
     * a warning is logged.
     */
    public void deleteDiskCopy() {
        if (file != null) {
            if (!file.delete()) {
                LOGGER.log(Level.WARNING, 
                        "Unable to delete cached image tile file: {0}", file.getPath());
        }
    }
    }

    /**
     * Gets the unique ID for this tile. The ID is a combination of
     * either the JAI-generated unique ID of the owning image or the image's
     * hash key if a unique ID was not available plus the tile index.
     * The returned object will be either Long or BigInteger.
     * 
     * @return tile ID
     */
    public Object getTileId() {
        return id;
    }

    /**
     * Gets this tile's location in its parent image in pixel coordinates
     * 
     * @return tile origin expressed in parent image coordinates
     */
    public Point getLocation() {
        return new Point(location);
    }

    /**
     * Gets the X ordinate of this tile in the parent image tile grid.
     * 
     * @return tile grid X ordinate
     */
    public int getTileX() {
        return tileX;
    }

    /**
     * Gets the Y ordinate of this tile in the parent image tile grid.
     * 
     * @return tile grid Y ordinate
     */
    public int getTileY() {
        return tileY;
    }

    /**
     * Queries if this tile is writable.
     * 
     * @return {@code true} if writable; {@code false} otherwise
     */
    public boolean isWritable() {
        return isWritable;
    }

    /**
     * Package-private method called by the controlling {@linkplain DiskBasedTileCache}
     * object when the tile is added to, or removed from, the cache.
     */
    void setAction( TileAction action ) {
        this.action = action;
    }

    /**
     * Package-private method called by the controlling {@linkplain DiskBasedTileCache}
     * object when the tile is accessed
     */
    void setTileTimeStamp(long time) {
        this.timeStamp = time;
    }

    /**
     * Package-private method that reads data for the raster associated with this tile
     * from disk
     *
     * @return a new instance of Raster or WritableRaster
     */
    Raster readData() {
        ImageInputStream strm = null;
        DataBuffer dataBuf = null;
        RenderedImage img = ownerRef.get();
        Raster raster = null;

        if (file != null && img != null) {
            try {
                strm = ImageIO.createImageInputStream(file);

                switch (img.getSampleModel().getDataType()) {
                    case DataBuffer.TYPE_BYTE: {
                        byte[][] bankData = new byte[numBanks][dataLen];
                        for (int i = 0; i < numBanks; i++) {
                            int numRead = strm.read(bankData[i], 0, dataLen);
                            if (numRead < numBanks) {
                                throw new RuntimeException(
                                        "Cached tile file appears to be truncated");
                            }
                        }
                        dataBuf = new DataBufferByte(bankData, dataLen);
                    }
                    break;

                    case DataBuffer.TYPE_DOUBLE: {
                        double[][] bankData = new double[numBanks][dataLen];
                        for (int i = 0; i < numBanks; i++) {
                            strm.readFully(bankData[i], 0, dataLen);
                        }
                        dataBuf = new DataBufferDouble(bankData, dataLen);
                    }
                    break;

                    case DataBuffer.TYPE_FLOAT: {
                        float[][] bankData = new float[numBanks][dataLen];
                        for (int i = 0; i < numBanks; i++) {
                            strm.readFully(bankData[i], 0, dataLen);
                        }
                        dataBuf = new DataBufferFloat(bankData, dataLen);
                    }
                    break;

                    case DataBuffer.TYPE_INT: {
                        int[][] bankData = new int[numBanks][dataLen];
                        for (int i = 0; i < numBanks; i++) {
                            strm.readFully(bankData[i], 0, dataLen);
                        }
                        dataBuf = new DataBufferInt(bankData, dataLen);
                    }
                    break;

                    case DataBuffer.TYPE_SHORT: {
                        short[][] bankData = new short[numBanks][dataLen];
                        for (int i = 0; i < numBanks; i++) {
                            strm.readFully(bankData[i], 0, dataLen);
                        }
                        dataBuf = new DataBufferShort(bankData, dataLen);
                    }
                    break;

                    case DataBuffer.TYPE_USHORT: {
                        short[][] bankData = new short[numBanks][dataLen];
                        for (int i = 0; i < numBanks; i++) {
                            strm.readFully(bankData[i], 0, dataLen);
                        }
                        dataBuf = new DataBufferUShort(bankData, dataLen);
                    }
                    break;

                    default:
                        throw new UnsupportedOperationException("Unsupported image data type");
                }

            } catch (FileNotFoundException ex) {
                LOGGER.log(Level.SEVERE, "Failed to read image tile data", ex);
                return null;

            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Failed to read image tile data", ex);
                return null;
            }
            finally{
            	if(strm!=null)
            		try{
            			strm.close();
            		}catch (Throwable e) {
						// chew me
					}
            }
        }

        if (dataBuf != null) {
            if (isWritable) {
                raster = Raster.createWritableRaster(img.getSampleModel(), dataBuf, location);
            } else {
                raster = Raster.createRaster(img.getSampleModel(), dataBuf, location);
            }
        }

        return raster;
    }


    /**
     * Write data for the raster associated with this tile to
     * disk. This may be called by <code>DiskMemTileCache</code>
     * as well as be the tile itself.
     */
    void writeData(Raster raster) throws IOException {
        ImageOutputStream strm = null;
        DataBuffer dataBuf = raster.getDataBuffer();

        if (file == null) {
            // first time this tile has been written to disk
            file = createFile();
        }

        try {
            strm = ImageIO.createImageOutputStream(file);

            switch (dataBuf.getDataType()) {
                case DataBuffer.TYPE_BYTE:
                     {
                        byte[] bankData;
                        for (int i = 0; i < numBanks; i++) {
                            bankData = ((DataBufferByte) dataBuf).getData(i);
                            strm.write(bankData);
                        }
                    }
                    break;

                case DataBuffer.TYPE_DOUBLE:
                     {
                        double[] bankData;
                        for (int i = 0; i < numBanks; i++) {
                            bankData = ((DataBufferDouble) dataBuf).getData(i);
                            strm.writeDoubles(bankData, 0, dataLen);
                        }
                    }
                    break;

                case DataBuffer.TYPE_FLOAT:
                     {
                        float[] bankData;
                        for (int i = 0; i < numBanks; i++) {
                            bankData = ((DataBufferFloat) dataBuf).getData(i);
                            strm.writeFloats(bankData, 0, dataLen);
                        }
                    }
                    break;

                case DataBuffer.TYPE_INT:
                     {
                        int[] bankData;
                        for (int i = 0; i < numBanks; i++) {
                            bankData = ((DataBufferInt) dataBuf).getData(i);
                            strm.writeInts(bankData, 0, dataLen);
                        }
                    }
                    break;

                case DataBuffer.TYPE_SHORT:
                     {
                        short[] bankData;
                        for (int i = 0; i < numBanks; i++) {
                            bankData = ((DataBufferShort) dataBuf).getData(i);
                            strm.writeShorts(bankData, 0, dataLen);
                        }
                    }
                    break;

                case DataBuffer.TYPE_USHORT:
                    {
                        short[] bankData;
                        for (int i = 0; i < numBanks; i++) {
                            bankData = ((DataBufferUShort) dataBuf).getData(i);
                            strm.writeShorts(bankData, 0, dataLen);
                        }
                    }
                    break;

                default:
                    throw new UnsupportedOperationException("Unsupported image data type");
            }


        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Failed to write image tile data", ex);

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to write image tile data", ex);
        }
        finally{
        	if(strm!=null)
        		try{
        			strm.close();
        		}catch (Throwable e) {
					// chew me
				}
        }
    }

    /**
     * Create a file to cache the given tile on disk. Presently
     * this method does nothing more than delegate to File.createTempFile
     *
     * @throws java.io.IOException
     */
    private File createFile() throws IOException {
        synchronized(folderLock) {
            return File.createTempFile(FILE_PREFIX, FILE_SUFFIX, cacheFolder);
        }
    }

}

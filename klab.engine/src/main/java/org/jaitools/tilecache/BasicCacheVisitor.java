/* 
 *  Copyright (c) 2009, Michael Bedward. All rights reserved. 
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

import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A basic visitor class for <code>DiskMemTileCache</code> that can be used
 * to examine cache contents.
 * <p>
 * Example of use:
 * <pre><code>
 * // Query the cache for tiles that belong to a given image
 * // and that are currently resident in memory
 * DiskMemTileCache cache = ...
 * RenderedImage owner = ...
 *
 * Map<BasicCacheVisitor.Key, Object> filters = CollectionFactory.newMap();
 * filters.put(BasicCacheVisitor.Key.OWNER, owner);
 * filters.put(BasicCachevisitor.Key.RESIDENT, Boolean.TRUE);
 *
 * BasicCacheVisitor visitor = new BasicCacheVisitor();
 * visitor.setFilters(filters);
 * cache.accept(visitor);
 *
 * for (DiskCachedTile tile : visitor.getTiles()) {
 *     System.out.println(String.format("tile %d,%d", tile.getTileX(), tile.getTileY()));
 * }
 * </code></pre>
 *
 * @author Michael Bedward
 * @since 1.0
 * @version $Id$
 */
public class BasicCacheVisitor implements DiskMemTileCacheVisitor {

    /**
     * Defines keys to filter the tles visited
     */
    public enum Key {
        /**
         * Filter on the image that owns the tile. The value
         * provided for this key must be a RenderedImage.
         */
        OWNER(RenderedImage.class),

        /**
         * Filter on whether the tile is currently resident
         * in memory. The value provided for this key must
         * be of type Boolean.
         */
        RESIDENT(Boolean.class);


        private Class<?> clazz;

        private Key(Class<?> clazz) {
            this.clazz = clazz;
        }

        /**
         * Gets the value class.
         * 
         * @return the class
         */
        public Class<?> getValueClass() {
            return clazz;
        }
    }

    private List<DiskCachedTile> tiles = new ArrayList<DiskCachedTile>();
    private Map<Key, Object> filters = new HashMap<Key, Object>();


    /**
     * Set filter conditions. Any existing conditions are cleared.
     *
     * @param params a <code>Map</code> where each key is one of the constants
     * defined by <code>BasicCacheVisitor.Key</code> and each value is the
     * corresponding value to match tiles against
     */
    public void setFilters(Map<Key, Object> params) {
        filters.clear();
        for (Entry<Key, Object> e : params.entrySet()) {
            addFilter(e.getKey(), e.getValue());
        }
    }

    /**
     * Set a single filter condition. Any existing conditions
     * are cleared
     * 
     * @param key one of the constants defined by <code>BasicCacheVisitor.Key</code>
     * @param value corresponding value to match tiles against
     */
    public void setFilter(Key key, Object value) {
        filters.clear();
        addFilter(key, value);
    }

    /**
     * Clear all existing filter conditions
     */
    public void clearFilters() {
        filters.clear();
    }
    
    /**
     * Returns an unmodifiable collection of tiles retrieved from
     * the cache.
     * 
     * @return tiles retrieved from the cache
     */    
    public Collection<DiskCachedTile> getTiles() {
        return Collections.unmodifiableCollection(tiles);
    }


    /**
     * This method is called by the cache for each tile in turn. Tiles that
     * pass this visitor's filter conditions, if any, will be added to the
     * visitor's tile collection.
     *
     * @param tile the cached tile being visited
     * @param isResident set by the cache to indicate whether the tile is
     * currently resident in memory
     */
    public void visit(DiskCachedTile tile, boolean isResident) {
        if (filters.isEmpty()) {
            tiles.add(tile);

        } else {
            boolean pass = true;
            if (filters.containsKey(Key.OWNER)) {
                if ( !(tile.getOwner().equals(filters.get(Key.OWNER))) ) {
                    pass = false;
                }
            }

            if (pass && filters.containsKey(Key.RESIDENT)) {
                if ( ((Boolean)filters.get(Key.RESIDENT)).booleanValue() != isResident) {
                    pass = false;
                }
            }

            if (pass) {
                tiles.add(tile);
            }
        }
    }

    /**
     * Clear collected tile data. Useful if an instance of this
     * class makes multiple visits to a cache.
     */
    public void clear() {
        tiles.clear();
    }

    /**
     * Helper method for setFilters and setFilter
     * @param key
     * @param value
     */
    private void addFilter(Key key, Object value) {
        if (key.getValueClass().isAssignableFrom(value.getClass())) {
            filters.put(key, value);
        } else {
            throw new IllegalArgumentException(
                    String.format("Object of type %s cannot be used as value for %s",
                    value.getClass().getName(), key.toString()));
        }
    }

}

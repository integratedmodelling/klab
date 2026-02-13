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

package org.jaitools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Convenience methods to create generic collections
 * following the DRY (Don't Repeat Yourself) principle.
 * Examples of use:
 * <pre><code>
 * // simple
 * List&lt;Integer&gt; list1 = CollectionFactory.list();
 * 
 * // nested
 * List&lt;List&lt;String&gt;&gt; list2 = CollectionFactory.list();
 * 
 * // a multi-map
 * Map&lt;String, List&lt;Integer&gt;&gt; multiMap = CollectionFactory.map();
 * </code></pre>
 * 
 * @author Michael Bedward
 * @since 1.0
 * @version $Id$
 */
public class CollectionFactory {
    /**
     * Returns a new {@code Map} object. The returned
     * object is a {@link HashMap}.
     * <p>
     * Example of use:
     * <pre><code>
     * Map&lt;Foo, Bar&gt; myMap = CollectionFactory.map();
     * </code></pre>
     * 
     * @param <K> key type
     * @param <V> value type
     * @return a new Map&lt;K,V&gt; instance
     */
    public static <K, V> Map<K, V> map() {
        return new HashMap<K, V>();
    }

    /**
     * Returns a new {@code Map} object that maintains insertion
     * order. The returned object is a {@link LinkedHashMap}.
     * <p>
     * Example of use:
     * <pre><code>
     * Map&lt;Foo, Bar&gt; myMap = CollectionFactory.orderedMap();
     * </code></pre>
     *
     * @param <K> key type
     * @param <V> value type
     * @return a new Map&lt;K,V&gt; instance
     */
    public static <K, V> Map<K, V> orderedMap() {
        return new LinkedHashMap<K, V>();
    }

    /**
     * Returns a new {@code SortedMap} object. Key type {@code K} must
     * implement {@linkplain Comparable}. The returned object is a
     * {@link TreeMap}.
     * <p>
     * Example of use:
     * <pre><code>
     * Map&lt;Foo, Bar&gt; myMap = CollectionFactory.sortedMap();
     * </code></pre>
     * 
     * @param <K> key type
     * @param <V> value type
     * @return a new SortedMap&lt;K,V&gt; instance
     */
    public static <K, V> SortedMap<K, V> sortedMap() {
        return new TreeMap<K, V>();
    }
    
    /**
     * Returns a new {@code List} object. The returned object is
     * an {@link ArrayList}.
     * <p>
     * Example of use:
     * <pre><code>
     * List&lt;Foo&gt; myList = CollectionFactory.list();
     * </code></pre>
     * 
     * @param <T> element type
     * @return a new List&lt;T&gt; instance
     */
    public static <T> List<T> list() {
        return new ArrayList<T>();
    }
    
    /**
     * Returns a new {@code Stack} object.
     * <p>
     * Example of use:
     * <pre><code>
     * Stack&lt;Foo&gt; myStack = CollectionFactory.stack();
     * </code></pre>
     * 
     * @param <T> element type
     * @return a new Stack&lt;T&gt; instance
     */
    public static <T> Stack<T> stack() {
        return new Stack<T>();
    }

    /** 
     * Returns a new {@code Set} instance. The returned object is
     * a {@link HashSet}.
     * <p>
     * Example of use:
     * <pre><code>
     * Set&lt;Foo&gt; mySet = CollectionFactory.set();
     * </code></pre>
     * 
     * @param <T> element type
     * @return a new HashSet&lt;T&gt; instance
     */
    public static <T> Set<T> set() {
        return new HashSet<T>();
    }

    /**
     * Returns a new {@code Set} instance that maintains
     * the insertion order of its elements.
     * <p>
     * Example of use:
     * <pre><code>
     * Set&lt;Foo&gt; mySet = CollectionFactory.set();
     * </code></pre>
     *
     * @param <T> element type
     * @return a new LinkedHashSet&lt;T&gt; instance
     */
    public static <T> Set<T> orderedSet() {
        return new LinkedHashSet<T>();
    }

    /** 
     * Returns a new {@code SortedSet} instance. The type {@code T}
     * must implement {@linkplain Comparable}. The returned object
     * is a {@link TreeSet}.
     * <p>
     * Example of use:
     * <pre><code>
     * Set&lt;MyObject&gt; foo = CollectionFactory.sortedSet();
     * </code></pre>
     * 
     * @param <T> element type
     * @return a new TreeSet&lt;T&gt; instance
     */
    public static <T> SortedSet<T> sortedSet() {
        return new TreeSet<T>();
    }


}

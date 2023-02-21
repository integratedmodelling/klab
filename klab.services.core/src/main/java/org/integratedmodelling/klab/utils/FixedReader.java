//package org.integratedmodelling.klab.utils;
//
///*******************************************************************************
// *  Copyright (C) 2007, 2014:
// *  
// *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
// *    - integratedmodelling.org
// *    - any other authors listed in @author annotations
// *
// *    All rights reserved. This file is part of the k.LAB software suite,
// *    meant to enable modular, collaborative, integrated 
// *    development of interoperable data and model components. For
// *    details, see http://integratedmodelling.org.
// *    
// *    This program is free software; you can redistribute it and/or
// *    modify it under the terms of the Affero General Public License 
// *    Version 3 or any later version.
// *
// *    This program is distributed in the hope that it will be useful,
// *    but without any warranty; without even the implied warranty of
// *    merchantability or fitness for a particular purpose.  See the
// *    Affero General Public License for more details.
// *  
// *     You should have received a copy of the Affero General Public License
// *     along with this program; if not, write to the Free Software
// *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
// *     The license is also available at: https://www.gnu.org/licenses/agpl.html
// *******************************************************************************/
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.util.Iterator;
//import java.util.List;
//
//import org.integratedmodelling.klab.exceptions.KlabIOException;
//import org.integratedmodelling.klab.utils.collections.ImmutableList;
//
//
///**
// * Simple fixed format reader to ease parsing those files you get from
// * government agencies. Create one per line.
// * 
// * @author Ferd
// *
// */
//public class FixedReader {
//
//    int    _ofs   = 0;
//    int[]  _bounds;
//    String _data;
//    int    _start = -1;
//
//    private static class RecordList extends ImmutableList<FixedReader> {
//
//        class It implements Iterator<FixedReader> {
//
//            @Override
//            public boolean hasNext() {
//                try {
//                    return _in.ready();
//                } catch (IOException e) {
//                    return false;
//                }
//            }
//
//            @Override
//            public FixedReader next() {
//
//                try {
//                    while (_in.ready()) {
//                        String s = _in.readLine();
//                        if (!s.trim().isEmpty()) {
//                            return new FixedReader(_bounds, s);
//                        }
//                    }
//                    _in.close();
//                } catch (IOException e) {
//                    throw new KlabIOException(e);
//                }
//                return null;
//            }
//
//            @Override
//            public void remove() {
//            }
//
//        }
//
//        private BufferedReader _in;
//        private int[]          _bounds;
//
//        public RecordList(BufferedReader in, int[] fieldBoundaries) {
//            _bounds = fieldBoundaries;
//            _in = in;
//        }
//
//        @Override
//        public boolean contains(Object arg0) {
//            return false;
//        }
//
//        @Override
//        public FixedReader get(int arg0) {
//            // TODO Auto-generated method stub
//            return null;
//        }
//
//        @Override
//        public Iterator<FixedReader> iterator() {
//            return new It();
//        }
//
//        @Override
//        public int size() {
//            // unknown in advance
//            return -1;
//        }
//
//        @Override
//        public Object[] toArray() {
//            // oh come on
//            return null;
//        }
//
//        @Override
//        public <T> T[] toArray(T[] arg0) {
//            // stop it
//            return null;
//        }
//
//    }
//
//    public FixedReader(int[] fieldBoundaries, String string) {
//        _data = string;
//        _bounds = fieldBoundaries;
//    }
//
//    public String nextString() {
//        String ret = _ofs >= _bounds.length ? null : _data.substring(_bounds[_ofs], _bounds[_ofs + 1]);
//        if (ret != null)
//            _ofs++;
//        return ret;
//    }
//
//    public int nextInt() {
//        return Integer.parseInt(nextString().trim());
//    }
//
//    public double nextDouble() {
//        return Double.parseDouble(nextString().trim());
//    }
//
//    /**
//     * Use to stop using the encoded offsets and start reading from the current position for
//     * the given number of characters. DO NOT go back to the other functions after using this.
//     * @param n
//     * @return next string
//     */
//    public String nextString(int n) {
//        if (_start == -1)
//            _start = _bounds[_ofs];
//        String ret = _data.substring(_start, _start + n);
//        _start += n;
//        return ret;
//    }
//
//    public void reset() {
//        _start = -1;
//        _ofs = 0;
//    }
//
//    public int nextInt(int n) {
//        return Integer.parseInt(nextString(n).trim());
//    }
//
//    public double nextDouble(int n) {
//        return Double.parseDouble(nextString(n).trim());
//    }
//
//    /**
//     * Parse a file into a collection of records. Limited in use. The iterator for the
//     * list reuses the same object so it's efficient. You're expected
//     * to scan it once and fully, until hasNext() is false, otherwise it won't close 
//     * the file properly, and only use the foreach syntax on the result - 
//     * do not call size() or the usual list functions. 
//     * 
//     * @param file
//     * @param fieldBoundaries
//     * @return parsed file
//     * @throws KlabIOException 
//     */
//    public static List<FixedReader> parse(File file, int[] fieldBoundaries) throws KlabIOException {
//
//        BufferedReader in;
//        try {
//            in = new BufferedReader(new FileReader(file));
//            return new RecordList(in, fieldBoundaries);
//        } catch (Exception e) {
//            throw new KlabIOException(e);
//        }
//    }
//
//    public static List<FixedReader> parse(URL u, int[] fieldBoundaries) throws KlabIOException {
//
//        BufferedReader in;
//        InputStream is = null;
//        try {
////            URL u = new URL(url);
//            is = u.openStream();
//            in = new BufferedReader(new InputStreamReader(is), 40000000);
//            return new RecordList(in, fieldBoundaries);
//        } catch (Exception e) {
//            throw new KlabIOException(e);
//        }
//    }
//    
//    public static List<FixedReader> parse(String url, int[] fieldBoundaries) throws KlabIOException {
//
//        BufferedReader in;
//        InputStream is = null;
//        try {
//            URL u = new URL(url);
//            is = u.openStream();
//            in = new BufferedReader(new InputStreamReader(is), 40000000);
//            return new RecordList(in, fieldBoundaries);
//        } catch (Exception e) {
//            throw new KlabIOException(e);
//        }
//    }
//    
//}
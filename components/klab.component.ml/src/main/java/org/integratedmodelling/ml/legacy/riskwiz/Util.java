/*
 * Copyright (c) 2005, Regents of the University of California
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.  
 *
 * * Neither the name of the University of California, Berkeley nor
 *   the names of its contributors may be used to endorse or promote
 *   products derived from this software without specific prior 
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.integratedmodelling.ml.legacy.riskwiz;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;


/**
 * Provides common utilities to FOMIE programs.
 */
public class Util {

    /**
     * Initializes the random number generator using either the clock time 
     * or a fixed seed.  If the fixed seed is used, the behavior of the 
     * program will be repeatable across runs.
     *
     * @param randomize   set seed using clock time rather than fixed value
     */
    public static void initRandom(boolean randomize) {
        if (randomize) {
            System.out.println("Using clock time as random seed.");
            rand = new Random();
        } else {
            System.out.println("Using fixed random seed for repeatability.");
            rand = new Random(0xad527c2b74e10cb3L);
        }
    }

    /**
     * Uniformly sample from a set.
     */
    public static Object uniformSample(Set set) {
        if (set.isEmpty()) {
            return null;
        }

        int index = randInt(set.size());
        Iterator setIterator = set.iterator();

        for (int counter = 0; counter < index; ++counter, setIterator.next()) {
            ;
        }
        return setIterator.next();
    }

    /**
     * Returns a pseudorandom number uniformly distributed in the range [0, 1).
     * This method must not be called before initRandom() is called.  
     */
    public static double random() {
        return rand.nextDouble(); // null pointer exception if not initialized
    }

    /**
     * Returns a pseudorandom integer sampled uniformly from {0, ..., n-1}
     * Assumes n > 0
     */
    public static int randInt(int n) {
        return (int) Math.floor(rand.nextDouble() * n);
    }

    /**
     * Returns an integer in the range {0, ..., probs.length - 1}, 
     * according to the distribution specified by probs.  If probs 
     * has length 0, returns -1.
     */
    public static int sampleWithProbs(double[] probs) {
        double u = random();
        double cumProb = 0;

        for (int i = 0; i < probs.length - 1; ++i) {
            cumProb += probs[i];
            if (u < cumProb) { // use < because random() could return 0
                return i;
            }
        }

        return (probs.length - 1);
    }

    /**
     * Returns an integer in the range {0, ..., probs.length - 1}, 
     * according to the given weights.  If <code>weights</code> 
     * has length 0, returns -1.  
     */
    public static int sampleWithWeights(double[] weights, double sum) {
        double u = sum * random();
        double cumWeight = 0;

        for (int i = 0; i < weights.length - 1; ++i) {
            cumWeight += weights[i];
            if (u < cumWeight) { // use < because random() could return 0
                return i;
            }
        }

        return (weights.length - 1);
    }

    /**
     * Returns a list of min(<code>list.size()</code>, <code>n</code>) 
     * objects sampled without replacement from <code>list</code>.
     */
    public static List sampleWithoutReplacement(List list, int n) {
        List sampledObjs = new ArrayList();
        List sampledIndices = new LinkedList(); // sorted list of Integer

        n = Math.min(list.size(), n);

        for (int i = 0; i < n; ++i) {
            int index = randInt(list.size() - i);

            // find the index-th unsampled element of the list
            ListIterator iter = sampledIndices.listIterator();

            while (iter.hasNext()) {
                int alreadySampled = ((Integer) iter.next()).intValue();

                if (alreadySampled <= index) {
                    ++index; // don't count the already-sampled index
                } else {
                    iter.previous(); // so we can insert new index correctly
                    break;
                }
            }

            sampledObjs.add(list.get(index));
            iter.add(new Integer(index));
        }

        return sampledObjs;
    }

    /**
     * Randomly shuffles the given list, in a way such that all permutations 
     * are equally likely.
     */
    public static void shuffle(List list) {
        Collections.shuffle(list, rand);
    }

    /**
     * A sampler of nonnegative reals following an inverse polynomial
     * density <code>1/((x+\delta)^n), \delta > 0</code>.  The purpose
     * of <code>/delta</code> is to avoid 0 having infinite density.
     * For practical purposes, the sampler only returns numbers in
     * <code>[0, u)</code>, for <code>u</code> an upper limit given by
     * the user.  The class also lets the user declare how much mass
     * should be left beyond the upper limit; this should be a small
     * probability.  There is a constructor with a default mass beyond
     * upper limit set to 0.01.  Given n and these two parameters, the
     * class automatically sets \delta to a value that makes it so.
     * @author Rodrigo
     */
    public static class InversePolynomialSampler {

        /**
         * Creates sampler with mass beyond upper limit set to 0.01,
         * with <code>n</code> = 2..
         */
        public InversePolynomialSampler(double upperLimit) {
            constructor(2, upperLimit, 0.01);
        }

        /**
         * Creates sampler with mass beyond upper limit set to 0.01.
         */
        public InversePolynomialSampler(double n, double upperLimit) {
            constructor(n, upperLimit, 0.01);
        }

        public InversePolynomialSampler(double n, double upperLimit, 
                double massBeyondUpperLimit) {
            constructor(n, upperLimit, massBeyondUpperLimit);
        }

        private void constructor(double n,
                double upperLimit,
                double massBeyondUpperLimit) {
            this.n = n;
            this.massBeyondUpperLimit = massBeyondUpperLimit;
            this.upperLimit = upperLimit;
            delta = upperLimit 
                    / ((1 / Math.pow(massBeyondUpperLimit, 1 / (n - 1))) - 1);
        }

        public int nextSample() {
            int result;

            do {
                double roll = Util.random();

                result = (int) Math.floor(
                        delta / Math.pow(1 - roll, 1.0 / (n - 1)) - delta);
            } while (result >= upperLimit);
            return result;
        }

        private double n;
        private double upperLimit;
        private double massBeyondUpperLimit;
        private double delta;
    }

    /**
     * Returns log(n! / (n-m)!), that is, the log of the product of the first 
     * m factors in the factorial of n.  
     */
    public static double logPartialFactorial(int n, int m) {
        double sum = 0;

        for (int i = n; i > n - m; i--) {
            sum += Math.log(i);
        }
        return sum;
    }

    /**
     * Returns the factorial of n.
     */
    public static int factorial(int n) {
        int prod = 1;

        for (int i = 2; i <= n; i++) {
            prod *= i;
        }
        return prod;
    }

    /**
     * Returns the log of the factorial of n.  This may be faster than 
     * just calling Math.log(Util.factorial(n)).
     */
    public static double logFactorial(int n) {
        return logPartialFactorial(n, n);
    }

    /**
     * Returns (n choose k) = n! / (n-k)! k!.  
     */
    public static int choose(int n, int k) {
        int prod = 1;

        for (int i = n; i > n - k; --i) {
            prod *= i;
        }
        return (prod / factorial(k));
    }

    /**
     * Returns the number of ways to make k choices from a set of n bins, 
     * where the same bin may be chosen multiple times.  This is 
     * (n + k - 1) choose (n - 1).  
     */
    public static int multichoose(int n, int k) {
        return choose(n + k - 1, n - 1);
    }

    /**
     * Addition in the log domain.  Returns an approximation to 
     * ln(e^a + e^b).  Just doing it naively might lead to underflow if a 
     * and b are very negative.  Without loss of generality, let b<a .  
     * If a>-10, calculates it in the standard way.  Otherwise, rewrite
     * it as a + ln(1 + e^(b-a)) and approximate that by the
     * first-order Taylor expansion to be a + (e^(b-a)).  So if b
     * is much smaller than a, there will still be underflow in the
     * last term, but in that case, the error is small relative to the
     * final answer.
     */
    public static double logSum(double a, double b) {

        if (a > b) {
            if (b == Double.NEGATIVE_INFINITY) {
                return a;
            } else if (a > -10) {
                return Math.log(Math.exp(a) + Math.exp(b));
            } else {
                return a + Math.exp(b - a);
            }
        } else {
            if (a == Double.NEGATIVE_INFINITY) {
                return b;
            } else if (b > -10) {
                return Math.log(Math.exp(a) + Math.exp(b));
            } else {
                return b + Math.exp(a - b);
            }
        }
    }

    /**
     * Maximum difference that we are willing to ignore between two 
     * floating-point values.  For instance, the sum of some probabilities 
     * may not be exactly 1.0, but this may just be due to floating-point 
     * inaccuracies, so we may want to consider it "close enough" to 1.0.  
     */
    public static final double TOLERANCE = 1e-10;

    /**
     * Returns true if the two given values differ by no more than 
     * Util.TOLERANCE.
     */
    public static boolean withinTol(double x, double y) {
        return (Math.abs(x - y) <= Util.TOLERANCE);
    }

    /**
     * Returns true if <code>x</code> is greater than <code>y</code> by 
     * at least Util.TOLERANCE.
     */
    public static boolean signifGreaterThan(double x, double y) {
        return (x - y >= Util.TOLERANCE);
    }

    /**
     * Returns true if <code>x</code> is less than <code>y</code> by 
     * at least Util.TOLERANCE.
     */
    public static boolean signifLessThan(double x, double y) {
        return (y - x >= Util.TOLERANCE);
    }

    /**
     * Prints the error message and stack trace for the given exception, 
     * and exits the program, returning code 1.
     */
    public static void fatalError(Throwable e) {
        fatalError(e, true);
    }

    /**
     * Prints the error message for the given exception, and optionally 
     * prints a stack trace.  Then exits the program with return code 1.  
     */
    public static void fatalError(Throwable e, boolean trace) {
        System.err.println("Fatal error: " + e.getMessage());
        if (trace) {
            e.printStackTrace();
            Throwable cause = e.getCause();

            if (cause != null) {
                System.err.println("Cause: " + cause.getMessage());
                cause.printStackTrace();
            }
        } 

        System.exit(1);
    }

    /**
     * Prints error message and exits.
     *
     * @param msg the error message
     * 
     */
    public static void fatalError(String msg) {
        fatalError(msg, true);
    }

    /**
     * Prints error message, optionally prints stack trace, and exits.
     *
     * @param msg the error message
     * 
     * @param trace if true, print a stack trace
     */
    public static void fatalError(String msg, boolean trace) {
        System.err.println("Fatal error: " + msg);
        if (trace) {
            Thread.currentThread();
			Thread.dumpStack();
        }

        System.exit(1);
    }

    /**
     * Prints error message without printing stack trace, and exits.
     * @param msg the error message
     */
    public static void fatalErrorWithoutStack(String msg) {
        fatalError(msg, false);
    }

    /**
     * Returns true if the program should print extra status messages.  
     * This value is false unless it has been set to true using 
     * <code>setVerbose</code>.  
     */
    public static boolean verbose() {
        return verbose;
    }

    /**
     * Sets a flag indicating whether the program should print extra 
     * status messages.  If this message is not called, the flag defaults 
     * to false.  
     */
    public static void setVerbose(boolean v) {
        verbose = v;
    }

    /**
     * Given two substrings defined by "begin" and "end" indices in some 
     * original string, returns the index of the first character that is 
     * in either of these two strings, or 0 if both strings are empty.
     * 
     * @param begin1   index of first char in substring 1
     * @param end1     one plus index of last char in substring 1
     * @param begin2   index of first char in substring 2
     * @param end2     one plus index of last char in substring 2
     */
    public static int substringPairBegin(int begin1, int end1, 
            int begin2, int end2) {
        if (begin1 == end1) {
            if (begin2 == end2) {
                return 0;
            }
            return begin2;
        }
        if (begin2 == end2) {
            return begin1;
        }
        return Math.min(begin1, begin2);
    }

    /**
     * Returns the substring of <code>str</code> from 
     * <code>substringPairBegin(begin1, end1, begin2, end2)</code> to 
     * <code>substringPairEnd(begin1, end1, begin2, end2)</code>.
     */
    public static String spannedSubstring(String str, int begin1, int end1, 
            int begin2, int end2) {
        return str.substring(substringPairBegin(begin1, end1, begin2, end2), 
                substringPairEnd(begin1, end1, begin2, end2));
    }

    /**
     * Given two substrings defined by "begin" and "end" indices in some 
     * original string, returns one plus the index of the last character that 
     * is in one of these two strings, or 0 if both strings are empty.
     * 
     * @param begin1   index of first char in substring 1
     * @param end1     one plus index of last char in substring 1
     * @param begin2   index of first char in substring 2
     * @param end2     one plus index of last char in substring 2
     */
    public static int substringPairEnd(int begin1, int end1, 
            int begin2, int end2) {
        if (begin1 == end1) {
            if (begin2 == end2) {
                return 0;
            }
            return end2;
        }
        if (begin2 == end2) {
            return end1;
        }
        return Math.max(end1, end2);
    }

    /**
     * Returns the string formed by concatenating the two given strings, with 
     * a space in between if both strings are non-empty.  
     */
    public static String join(String str1, String str2) {
        if (str1.length() == 0) {
            return str2;
        } 
        if (str2.length() == 0) {
            return str1;
        }

        StringBuffer buf = new StringBuffer(str1);

        buf.append(' ');
        buf.append(str2);
        return buf.toString();
    }

    /**
     * Given a string, returns a version of that string where all letters 
     * have been converted to lower case, and all characters that are not 
     * letters or digits have been removed.
     */
    public static String normalize(String input) {
        StringBuffer output = new StringBuffer();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                output.append(Character.toLowerCase(c));
            }
        }
        return output.toString();
    }

    /**
     * Returns an unmodifiable list equal to the concatenation of the two 
     * given lists.
     */
    public static <T> List<T> concat(List<? extends T> list1, 
            List<? extends T> list2) {
        return new ConcatenationList<T>(list1, list2);
    }

    /**
     * Nested class for implementing the <code>concat</code> method.
     */
    private static class ConcatenationList<T> extends AbstractList<T> {
	
        ConcatenationList(List<? extends T> list1, List<? extends T> list2) {
            this.list1 = list1;
            this.list2 = list2;
        }

        @Override
		public int size() {
            return (list1.size() + list2.size());
        }

        @Override
		public T get(int index) {
            if (index < list1.size()) {
                return list1.get(index);
            } 
            return list2.get(index - list1.size());
        }

        private List<? extends T> list1;
        private List<? extends T> list2;
    }

    /**
     * Returns an unmodifiable collection equal to the union of the two given 
     * collections, which are assumed to be disjoint.  The iteration order 
     * is the iteration order of <code>s1</code> followed by the iteration 
     * order of <code>s2</code>.  
     */
    public static <T> Collection<T> disjointUnion(Collection<? extends T> s1, 
            Collection<? extends T> s2) {
        return new DisjointUnionCollection<T>(s1, s2);
    }

    /**
     * Nested class for implementing the <code>disjointUnion</code> method.
     */
    private static class DisjointUnionCollection<T> extends AbstractCollection<T> {
        DisjointUnionCollection(Collection<? extends T> s1, 
                Collection<? extends T> s2) {
            this.s1 = s1;
            this.s2 = s2;
        }

        @Override
		public int size() {
            return (s1.size() + s2.size());
        }

        @Override
		public boolean contains(Object o) {
            return (s1.contains(o) || s2.contains(o));
        }

        @Override
		public Iterator<T> iterator() {
            return new DisjointUnionIterator();
        }

        private class DisjointUnionIterator implements Iterator<T> {
            @Override
			public boolean hasNext() {
                return (s1iter.hasNext() || s2iter.hasNext());
            }

            @Override
			public T next() {
                if (s1iter.hasNext()) {
                    return s1iter.next();
                } else if (s2iter.hasNext()) {
                    return s2iter.next();
                }
                throw new NoSuchElementException();
            }

            @Override
			public void remove() {
                throw new UnsupportedOperationException(
                        "Can't remove from DisjointUnionSet.");
            }

            private Iterator<? extends T> s1iter = s1.iterator();
            private Iterator<? extends T> s2iter = s2.iterator();
        }

        private Collection<? extends T> s1;
        private Collection<? extends T> s2;
    }

    /**
     * Returns an unmodifiable set equal to the intersection of the two 
     * given sets.
     */
    public static <T> Set<T> intersection(Set<? extends T> s1, 
            Set<? extends T> s2) {
        return new IntersectionSet<T>(s1, s2);
    }

    /**
     * Nested class for implementing the <code>intersection</code> method.
     */
    private static class IntersectionSet<T> extends AbstractSet<T> {
	
        IntersectionSet(Set<? extends T> s1, Set<? extends T> s2) {
            this.s1 = s1;
            this.s2 = s2;
        }

        @Override
		public int size() {
            Set<? extends T> smaller = (s1.size() <= s2.size()) ? s1 : s2;
            Set<? extends T> larger = (smaller == s1) ? s2 : s1;

            int size = 0;

            for (T obj : smaller) {
                if (larger.contains(obj)) {
                    ++size;
                }
            }
            return size;
        }

        @Override
		public boolean contains(Object obj) {
            return (s1.contains(obj) && s2.contains(obj));
        }

        @Override
		public Iterator<T> iterator() {
            return new IntersectionSetIterator();
        }

        private class IntersectionSetIterator implements Iterator<T> {
            IntersectionSetIterator() {
                Set<? extends T> smaller = (s1.size() <= s2.size()) ? s1 : s2;
                Set<? extends T> larger = (smaller == s1) ? s2 : s1;

                smallerIter = smaller.iterator();
                nextObj = findNext();
            }

            @Override
			public boolean hasNext() {
                return (nextObj != null);
            }

            @Override
			public T next() {
                if (nextObj == null) {
                    throw new NoSuchElementException();
                }

                T toReturn = nextObj;

                nextObj = findNext();
                return toReturn;
            }

            @Override
			public void remove() {
                throw new UnsupportedOperationException(
                        "Tried to remove element from IntersectionSet.");
            }

            private T findNext() {
                while (smallerIter.hasNext()) {
                    T obj = smallerIter.next();

                    if (larger.contains(obj)) {
                        return obj;
                    }
                }

                return null;
            }

            private Iterator<? extends T> smallerIter;
            private Set<? extends T> larger;
            private T nextObj;
        }

        private Set<? extends T> s1;
        private Set<? extends T> s2;
    }

    // public static SetWithDistrib uniformDistrib(IndexedSet s) {
    // return new UniformDistrib(s);
    // }
    //
    // private static class UniformDistrib implements SetWithDistrib {
    // public UniformDistrib(IndexedSet s) {
    // this.s = s;
    // }

    public double getProb(Object o) {
        return (s.contains(o) ? (1.0 / s.size()) : 0);
    }

    public double getLogProb(Object o) {
        return Math.log(getProb(o));
    }

    public Object sample() {
        if (s.isEmpty()) {
            return null;
        }
        return s.get(Util.randInt(s.size()));
    }

    private IndexedSet s;
    
    /**
     * Returns the number of lines in the given file.  This is the number 
     * of times that BufferedReader's readLine method can be called on 
     * this file before it returns null.
     */
    public static int getNumLines(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int numLines = 0;

        while (reader.readLine() != null) {
            ++numLines;
        }
        return numLines;
    }

    /**
     * Returns an iterator over the integers in the range from
     * <code>lower</code> to <code>upper</code>, inclusive.  The
     * iterator returns integers in ascending order.  If
     * <code>lower</code> is greater than <code>upper</code>, the 
     * iterator has no elements.  
     *
     * @return Iterator over Integer
     */
    public static Iterator getIntegerRangeIterator(int lower, int upper) {
        return new IntRangeIterator(lower, upper);
    }

    /**
     * Nested class for implementing getIntegerRangeIterator.
     */
    private static class IntRangeIterator implements Iterator {
        IntRangeIterator(int lower, int upper) {
            this.upper = upper;
            if (lower <= upper) {
                nextInt = new Integer(lower);
            }
        }

        @Override
		public boolean hasNext() {
            return (nextInt != null);
        }

        @Override
		public Object next() {
            if (nextInt == null) {
                throw new NoSuchElementException();
            }

            Integer toReturn = nextInt;

            if (nextInt.intValue() < upper) {
                nextInt = new Integer(nextInt.intValue() + 1);
            } else {
                // Note that we don't increment nextInt in this case, 
                // so we won't get overflow if upper is Integer.MAX_VALUE.
                nextInt = null;
            }
            return toReturn;
        }

        @Override
		public void remove() {
            throw new UnsupportedOperationException(
                    "Can't remove from IntRangeIterator");
        }

        Integer nextInt = null;
        int upper;
    }

    /**
     * Returns an iterator over the integers greater than or equal to
     * <code>lower</code>, in ascending order.  This iterator uses the
     * mathematical rather than the computational notion of an
     * integer, so its <code>hasNext</code> method never returns
     * false, even when it has already iterated over
     * <code>Integer.MAX_VALUE</code>.  If this iterator's
     * <code>next</code> method is called enough times, it will
     * eventually throw an ArithmeticException indicating that the
     * next integer cannot be represented.
     *
     * @return Iterator over Integer
     */
    public static Iterator getAscendingIntegerIterator(int lower) {
        return new AscendingIntIterator(lower);
    }

    private static class AscendingIntIterator implements Iterator {
        AscendingIntIterator(int lower) {
            nextInt = new Integer(lower);
        }

        @Override
		public boolean hasNext() {
            return true;
        }

        @Override
		public Object next() {
            if (nextInt == null) {
                throw new ArithmeticException(
                        "Next integer in ascending order is not representable.");
            }

            Integer toReturn = nextInt;

            if (nextInt.intValue() < Integer.MAX_VALUE) {
                nextInt = new Integer(nextInt.intValue() + 1);
            } else {
                nextInt = null;
            }
            return toReturn;
        }

        @Override
		public void remove() {
            throw new UnsupportedOperationException(
                    "Can't remove from AscendingIntIterator");
        }

        Integer nextInt;
    }

    /**
     * Returns an iterator over the integers less than or equal to
     * <code>upper</code>, in descending order.  This iterator uses the
     * mathematical rather than the computational notion of an
     * integer, so its <code>hasNext</code> method never returns
     * false, even when it has already iterated over
     * <code>Integer.MIN_VALUE</code>.  If this iterator's
     * <code>next</code> method is called enough times, it will
     * eventually throw an ArithmeticException indicating that the
     * next integer cannot be represented.
     *
     * @return Iterator over Integer
     */
    public static Iterator getDescendingIntegerIterator(int upper) {
        return new DescendingIntIterator(upper);
    }

    private static class DescendingIntIterator implements Iterator {
        DescendingIntIterator(int upper) {
            nextInt = new Integer(upper);
        }

        @Override
		public boolean hasNext() {
            return true;
        }

        @Override
		public Object next() {
            if (nextInt == null) {
                throw new ArithmeticException(
                        "Next integer in descending order is not representable.");
            }

            Integer toReturn = nextInt;

            if (nextInt.intValue() > Integer.MIN_VALUE) {
                nextInt = new Integer(nextInt.intValue() - 1);
            } else {
                nextInt = null;
            }
            return toReturn;
        }

        @Override
		public void remove() {
            throw new UnsupportedOperationException(
                    "Can't remove from DescendingIntIterator");
        }

        Integer nextInt;
    }

    /**
     * Returns an iterator over all integers, in order by magnitude,
     * with positive integers coming before negative integers of the
     * same magnitude.  The iterator uses the mathematical rather than
     * computational notion of an integer, so its <code>hasNext</code>
     * method always returns true, even when
     * <code>Integer.MAX_VALUE</code> has already been returned (note
     * that <code>Integer.MAX_VALUE</code> has a smaller magnitude
     * than <code>Integer.MIN_VALUE</code>, so it will be reached
     * first).  If the iterator's <code>next</code> method is called
     * enough times, it will eventually throw an
     * <code>ArithmeticException</code> indicating that the next
     * integer is not representable.
     *
     * @return Iterator over Integer
     */
    public static Iterator getIntegerIterator() {
        return new IntIterator();
    }

    private static class IntIterator implements Iterator {
        IntIterator() {
            nextInt = new Integer(0);
        }

        @Override
		public boolean hasNext() {
            return true;
        }

        @Override
		public Object next() {
            if (nextInt == null) {
                throw new ArithmeticException(
                        "Next integer by magnitude is not representable.");
            }

            Integer toReturn = nextInt;
            int inverse = -nextInt.intValue();

            if (inverse >= 0) {
                // Next integer will be positive; increase magnitude
                if (inverse < Integer.MAX_VALUE) { // don't exceed MAX_VALUE
                    nextInt = new Integer(inverse + 1);
                } else {
                    nextInt = null;
                }
            } else {
                // Next integer will be negative; same magnitude as previous.  
                // Don't need to worry about MIN_VALUE here because 
                // its magnitude is >= MAX_VALUE.
                nextInt = new Integer(inverse);
            }
            return toReturn;
        }

        @Override
		public void remove() {
            throw new UnsupportedOperationException("Can't remove from IntSet.");
        }

        Integer nextInt;
    }

    // /**
    // * Returns the first element <code>x</code> in <code>c</code> such
    // * that <code>f(x) != null</code>, or <code>null</code> if there
    // * is no such element.
    // */
    // public static Object findFirst(Collection c, UnaryFunction f) {
    // Iterator i = c.iterator();
    // while (i.hasNext()) {
    // Object x = i.next();
    // if (f.evaluate(x) != null)
    // return x;
    // }
    // return null;
    // }
    //
    // /**
    // * Returns the first element <code>x</code> in <code>c</code>
    // * satisfying <code>p</code>, or <code>null</code> if there is no
    // * such element.
    // */
    // public static Object findFirst(Collection c, UnaryPredicate p) {
    // Iterator i = c.iterator();
    // while (i.hasNext()) {
    // Object x = i.next();
    // if (p.test(x))
    // return x;
    // }
    // return null;
    // }

    /**
     * Returns the first element in a collection (according to its iterator).
     * @throws java.util.NoSuchElementException if collection is empty.
     */
    public static Object getFirst(Collection c) {
        return c.iterator().next();
    }

    /**
     * Given a string <code>description</code>,
     * returns <code>description</code> if its length is no greater than 10,
     * or <code>description</code> + "..." otherwise.
     */
    public static String abbreviation(String description) {
        return "\"" + description.substring(0, 10) 
                + (description.length() > 10 ? "(...)" : "") + "\"";
    }

    /**
     * Returns the mean of a collection of objects assumed to be
     * {@link Number}s.
     */
    public static double mean(Collection values) {
        double sum = 0;

        for (Iterator it = values.iterator(); it.hasNext();) {
            sum += ((Number) it.next()).doubleValue();
        }
        return sum / values.size();
    }

    /**
     * Returns the variance of a collection of objects assumed to be
     * {@link Number}s.
     */
    public static double variance(Collection values) {
        double mean = mean(values);
        double sum = 0;

        for (Iterator it = values.iterator(); it.hasNext();) {
            sum += Math.pow(((Number) it.next()).doubleValue() - mean, 2);
        }
        return sum / values.size();
    }

    private static Random rand;
    private static boolean verbose = false;
}


package org.integratedmodelling.klab.services.reasoner.owl.obsolete;
///*******************************************************************************
// *  Copyright (C) 2007, 2015:
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
//package org.integratedmodelling.klab.services.reasoner.owl;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Map;
//import org.integratedmodelling.klab.api.knowledge.KConcept;
//
///** 
// * A helper class providing patterns to retrieve concepts that match conditions or have
// * external content associated across the subclass hierarchy. Conditions must be expressed as 
// * objects implementing the inner interface ConceptMatcher.
// * @author villa
// */
//public class ConceptVisitor<T> {
//
//    HashSet<KConcept> concepts = new HashSet<KConcept>();
//
//    public interface ConceptMatcher {
//        abstract boolean match(KConcept c);
//    }
//
//    private void allMatchesDownwards(Collection<T> coll, Map<String, T> where, ConceptMatcher matcher, KConcept start) {
//        T obj = matcher.match(start) ? where.get(start.toString()) : null;
//        if (obj != null)
//            coll.add(obj);
//        concepts.add(start);
//        synchronized (start) {
//            for (KConcept c : start.getChildren()) {
//                if (concepts.contains(c)) {
//                    // Thinklab.logger.error("circular dependency in children chain over " + c);
//                    continue;
//                }
//                allMatchesDownwards(coll, where, matcher, c);
//            }
//        }
//    }
//
//    private void allMatchesUpwards(Collection<T> coll, Map<String, T> where, ConceptMatcher matcher, KConcept start) {
//        T obj = matcher.match(start) ? where.get(start.toString()) : null;
//        if (obj != null)
//            coll.add(obj);
//        concepts.add(start);
//        synchronized (start) {
//            for (KConcept c : start.getParents()) {
//                if (concepts.contains(c)) {
//                    // Thinklab.logger.error("circular dependency in children chain over " + c);
//                    continue;
//                }
//                allMatchesUpwards(coll, where, matcher, c);
//            }
//        }
//    }
//
//    /** 
//     * Returns the first object of type T that matches the passed function object in parent hierarchy.
//     * @param matcher The match to search for
//     * @param start The starting concept
//     * @return The object we're looking for or null if not found. Multiple matches across multiple inheritance chains will
//     *         return the first match found.
//     */
//    public KConcept findMatchUpwards(ConceptMatcher matcher, KConcept start) {
//
//        KConcept ret = null;
//        synchronized (start) {
//
//            ret = matcher.match(start) ? start : null;
//
//            if (ret == null) {
//                concepts.add(start);
//                for (KConcept c : start.getParents()) {
//                    if (concepts.contains(c)) {
//                        // Thinklab.logger.error("circular dependency in children chain over " + c);
//                        continue;
//                    }
//                    if ((ret = findMatchUpwards(matcher, c)) != null)
//                        break;
//                }
//            }
//        }
//        return ret;
//    }
//
//    /** 
//     * Returns the first object of type T that matches the passed function object in children hierarchy.
//     * @param matcher The match to search for
//     * @param start The starting concept
//     * @return The object we're looking for or null if not found. Multiple matches across multiple inheritance chains will
//     *         return the first match found.
//     */
//    public KConcept findMatchDownwards(ConceptMatcher matcher, KConcept start) {
//
//        KConcept ret = null;
//        synchronized (start) {
//
//            ret = matcher.match(start) ? start : null;
//
//            if (ret == null) {
//                concepts.add(start);
//                for (KConcept c : start.getChildren()) {
//                    if (concepts.contains(c)) {
//                        // Thinklab.logger.error("circular dependency in children chain over " + c);
//                        continue;
//                    }
//                    if ((ret = findMatchDownwards(matcher, c)) != null)
//                        break;
//                }
//            }
//        }
//
//        return ret;
//    }
//
//    /** 
//     * Returns the object of type T that maps to the semantic type of the passed concept or any of its parents in passed map.
//     * @param where The map to search into
//     * @param start The starting concept
//     * @return The object we're looking for or null if not found. Multiple matches across multiple inheritance chains will
//     *         return the first match found.
//     */
//    public T findInMapUpwards(Map<String, T> where, KConcept start) {
//
//        T ret = where.get(start.toString());
//
//        if (ret == null) {
//            for (KConcept c : start.getParents()) {
//
//                concepts.add(start);
//                if (concepts.contains(c)) {
//                    // Thinklab.logger.error("circular dependency in parent chain over " + c);
//                    continue;
//                }
//                if ((ret = this.findInMapUpwards(where, c)) != null)
//                    break;
//            }
//        }
//
//        return ret;
//    }
//
//    /** 
//     * Returns the object of type T that maps to the semantic type of the passed concept or any of its children in passed map.
//     * @param where The map to search into
//     * @param start The starting concept
//     * @return The object we're looking for or null if not found. Multiple matches across multiple inheritance chains will
//     *         return the first match found.
//     */
//    public T findInMapDownwards(Map<String, T> where, KConcept start) {
//
//        T ret = where.get(start.toString());
//
//        if (ret == null) {
//            concepts.add(start);
//            for (KConcept c : start.getChildren()) {
//                if (concepts.contains(c)) {
//                    // Thinklab.logger.error("circular dependency in inheritance chain over " + c);
//                    continue;
//                }
//                if ((ret = this.findInMapDownwards(where, c)) != null)
//                    break;
//            }
//        }
//
//        return ret;
//    }
//
//    /** 
//     * Returns the object of type T that maps to the semantic type of the passed concept or any of its parents in passed map.
//     * @param where The map to search into
//     * @param start The starting concept
//     * @return The object we're looking for or null if not found. Multiple matches across multiple inheritance chains will
//     *         return the first match found.
//     */
//    public T findMatchingInMapUpwards(Map<String, T> where, ConceptMatcher matcher, KConcept start) {
//
//        T ret = matcher.match(start) ? where.get(start.toString()) : null;
//
//        if (ret == null) {
//            concepts.add(start);
//            for (KConcept c : start.getParents()) {
//                if (concepts.contains(c)) {
//                    // Thinklab.logger.error("circular dependency in inheritance chain over " + c);
//                    continue;
//                }
//                if ((ret = this.findMatchingInMapUpwards(where, matcher, c)) != null)
//                    break;
//            }
//        }
//
//        return ret;
//    }
//
//    /** 
//     * Returns the object of type T that maps to the semantic type of the passed concept or any of its children in passed map.
//     * @param where The map to search into
//     * @param start The starting concept
//     * @return The object we're looking for or null if not found. Multiple matches across multiple inheritance chains will
//     *         return the first match found.
//     */
//    public T findMatchingInMapDownwards(Map<String, T> where, ConceptMatcher matcher, KConcept start) {
//
//        T ret = matcher.match(start) ? where.get(start.toString()) : null;
//
//        if (ret == null) {
//            concepts.add(start);
//            for (KConcept c : start.getChildren()) {
//                if (concepts.contains(c)) {
//                    // Thinklab.logger.error("circular dependency in inheritance chain over " + c);
//                    continue;
//                }
//                if ((ret = this.findMatchingInMapDownwards(where, matcher, c)) != null)
//                    break;
//            }
//        }
//
//        return ret;
//    }
//
//    /** 
//     * Returns all objects of type T that maps to the semantic type of the passed concept or any of its children in passed map.
//     * @param where The map to search into
//     * @param start The starting concept
//     * @return The objects we're looking for. Multiple matches across multiple inheritance chains will
//     *         return the first match found. We return a List so the order can be maintained.
//     */
//    public ArrayList<T> findAllMatchesInMapDownwards(Map<String, T> where, ConceptMatcher matcher, KConcept start) {
//
//        ArrayList<T> ret = new ArrayList<T>();
//        allMatchesDownwards(ret, where, matcher, start);
//        return ret;
//    }
//
//    /** 
//     * Returns all objects of type T that map to the semantic type of the passed concept or any of its parents in passed map.
//     * @param where The map to search into
//     * @param start The starting concept
//     * @return The object we're looking for or null if not found. Multiple matches across multiple inheritance chains will
//     *         return the first match found. We return a List so the order can be maintained.
//     */
//    public ArrayList<T> findAllMatchesInMapUpwards(Map<String, T> where, ConceptMatcher matcher, KConcept start) {
//        ArrayList<T> ret = new ArrayList<T>();
//        allMatchesUpwards(ret, where, matcher, start);
//        return ret;
//    }
//
//}

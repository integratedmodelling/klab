/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.owl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.utils.Pair;

/**
 * A map indexed by concepts, whose get() method will select the entry that best
 * corresponds to the passed concept using reasoning instead of equality. If the
 * key concept has been explicitly inserted, the correspondent value is
 * returned; otherwise the hierarchy of the requested key is walked upstream and
 * any concept inserted that matches a parent is used as key.
 * 
 * @author Ferdinando
 *
 * @param <T>
 */
public class IntelligentMap<T> implements Map<IConcept, T> {

	HashMap<IConcept, T> _data = new HashMap<>();
	ArrayList<Pair<String, T>> _unresolved = new ArrayList<>();
	HashMap<IConcept, T> _cache = new HashMap<>();
	private T defaultValue = null;

	public IntelligentMap() {
	}

	/**
	 * Use this constructor if you want a default value to be returned on no match
	 * instead of null.
	 * 
	 * @param defaultValue
	 */
	public IntelligentMap(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	public T get(IConcept concept) {

		if (_data.containsKey(concept)) {
			return _data.get(concept); 
		}
		
		resolve();

		class Matcher<TYPE> implements ConceptVisitor.ConceptMatcher {

			Map<IConcept, TYPE> coll;
			TYPE ret = null;

			public Matcher(Map<IConcept, TYPE> c) {
				coll = c;
			}

			@Override
			public boolean match(IConcept c) {
				ret = coll.get(c);
				return (ret != null);
			}
		}

		Matcher<T> matcher = new Matcher<T>(_data);
		IConcept cms = new ConceptVisitor<T>().findMatchUpwards(matcher, concept);
		if (cms == null) {
			return defaultValue;
		}
		if (matcher.ret == null) {
			return defaultValue;
		}
		return matcher.ret;
	}

	/*
	 * resolve all those that are not unknown any more, leave the still unknown.
	 */
	private void resolve() {

		if (_unresolved.size() > 0) {
			ArrayList<Pair<String, T>> unresolved = new ArrayList<Pair<String, T>>();
			for (Pair<String, T> u : _unresolved) {
				IConcept concept = Concepts.INSTANCE.getConcept(u.getFirst());
				if (concept == null) {
					unresolved.add(u);
				} else {
					put(concept, u.getSecond());
				}
			}
			_unresolved = unresolved;
		}
	}

	@Override
	public T put(IConcept concept, T data) {
		_cache.put(concept, data);
		return _data.put(concept, data);
	}

	@Override
	public void clear() {
		_data.clear();
		_cache.clear();
		_unresolved.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return _data.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return _data.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<IConcept, T>> entrySet() {
		return _data.entrySet();
	}

	@Override
	public T get(Object key) {
		/*
		 * explicitly specified "literal" values requiring no reasoning are returned
		 * first, irrespective of their hierarchy.
		 */
		if (_cache.containsKey(key)) {
			return _cache.get(key);
		}
		T ret = get((IConcept) key);
		_cache.put((IConcept) key, ret);
		return ret;
	}

	@Override
	public boolean isEmpty() {
		return _data.isEmpty();
	}

	@Override
	public Set<IConcept> keySet() {
		return _data.keySet();
	}

	@Override
	public void putAll(Map<? extends IConcept, ? extends T> m) {
		_data.putAll(m);
	}

	@Override
	public T remove(Object key) {
		return _data.remove(key);
	}

	@Override
	public int size() {
		return _data.size();
	}

	@Override
	public Collection<T> values() {
		return _data.values();
	}

	/**
	 * This is for concepts that may be unknown at the time of insertion. We keep a
	 * list of still-unknown ones and we try to associate them at every get().
	 * 
	 * @param concept
	 * @param data
	 */
	public void put(String concept, T data) {
		// TODO Auto-generated method stub
		_unresolved.add(new Pair<String, T>(concept, data));
	}

}

/**
 * CPT.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Feb 6, 2008
 *
 * ----------------------------------------------------------------------------------
 * This file is part of RiskWiz.
 * 
 * RiskWiz is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * RiskWiz is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with the software; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * ----------------------------------------------------------------------------------
 * 
 * @copyright 2008 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      Feb 6, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.pfunction;

import java.util.List;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.Domain;
import org.integratedmodelling.ml.legacy.riskwiz.interpreter.RT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.Query;

/**
 * @author Sergey Krivov
 * 
 */
public class TabularFunction implements IFunction {

	protected int[] structure;

	protected int size;

	protected Vector<DiscreteDomain> domainProduct;

	private Object[] multiarray;

	public boolean isSingleValue = false;

	private Object singleValue;

	protected Domain domain;

	protected Vector<DiscreteDomain> parentsDomains;
	// protected Vector<DiscreteDomain> discParentDomains;
	// public boolean hasExpDomain = false;

	public TabularFunction() {
	}

	/**
	 * 
	 */
	public TabularFunction(DiscreteDomain domain, Vector<DiscreteDomain> parentDomains) {
		this.domain = domain;
		if (parentDomains != null) {
			this.parentsDomains = parentDomains;

		} else {
			this.parentsDomains = new Vector<DiscreteDomain>();

		}
		resetDomainProduct();
		resetMultiarray();
	}

	// public TabularFunction(DiscreteDomain domain,
	// Vector<DiscreteDomain> parentDomains) {
	// this.domain = domain;
	// if (parentDomains != null) {
	// this.parentsDomains = new Vector<Domain> ();
	// this.parentsDomains.addAll(parentDomains);
	//
	// } else {
	// this.parentsDomains = new Vector<Domain>();
	// }
	// resetDomainProduct();
	// resetMultiarray();
	// }

	protected void resetSize() {
		structure = new int[domainProduct.size()];
		int s = 1;

		for (int i = 0; i < structure.length; i++) {
			structure[i] = domainProduct.elementAt(i).getOrder();
			s *= domainProduct.elementAt(i).getOrder();
		}

		size = s;
	}

	public int[] getStructure() {
		return structure;
	}

	public int addr2index(int[] addr) {
		int index = 0;

		for (int i = 0; i < addr.length; i++) {
			index *= structure[i];
			index += addr[i];
		}
		return index;
	}

	/*
	 * ! Convert between a real address and a logical address [Div-modul] [very
	 * expensive] \param[in] realaddr the absolute/real address \return the logical
	 * address
	 */
	public int[] index2addr(int index) {
		int[] addr = new int[domainProduct.size()];
		int run = index;

		for (int i = domainProduct.size() - 1; i >= 0; i--) {
			addr[i] = run % structure[i];
			run = (run - addr[i]) / structure[i];
		}
		return addr;
	}

	/*
	 * ! Add one to an addr isomorphic to addOne(q) => index(addr2index(q)+1), but
	 * FASTER \param[in,out] addr the address
	 */
	public boolean addOne(int addr[]) {
		for (int k = addr.length - 1; k >= 0; k--) {
			addr[k]++;
			if (addr[k] >= structure[k]) {
				addr[k] = 0;
				if (k == 0) {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	public int size() {
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.IFunction#getDomain()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.IFunction#getDomain()
	 */
	@Override
	public Domain getDomain() {
		return domain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.integratedmodelling.riskwiz.pf.IFunction#setDomain(org.
	 * integratedmodelling.riskwiz.pt.DiscreteDomain)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.integratedmodelling.riskwiz.pf.IFunction#setDomain(org.
	 * integratedmodelling.riskwiz.pt.DiscreteDomain)
	 */
	@Override
	public void setDomain(Domain domain) {
		this.domain = domain;
		resetDomainProduct();
		resetMultiarray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.IFunction#getParentsDomains()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.IFunction#getParentsDomains()
	 */
	@Override
	public Vector<DiscreteDomain> getParentsDomains() {
		return parentsDomains;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.IFunction#setParentsDomains(java.util
	 * .Vector)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.IFunction#setParentsDomains(java.util
	 * .Vector)
	 */
	@Override
	public void setParentsDomains(Vector<? extends Domain> parentsDomains) {
		this.parentsDomains = (Vector<DiscreteDomain>) parentsDomains;
		resetDomainProduct();
		resetMultiarray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.integratedmodelling.riskwiz.pf.IFunction#addParentDomain(org.
	 * integratedmodelling.riskwiz.pt.DiscreteDomain)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.integratedmodelling.riskwiz.pf.IFunction#addParentDomain(org.
	 * integratedmodelling.riskwiz.pt.DiscreteDomain)
	 */
	@Override
	public void addParentDomain(Domain dom) {
		this.parentsDomains.add((DiscreteDomain) dom);
		resetDomainProduct();
		resetMultiarray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.integratedmodelling.riskwiz.pf.IFunction#removeParentDomain(org.
	 * integratedmodelling.riskwiz.pt.DiscreteDomain)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.integratedmodelling.riskwiz.pf.IFunction#removeParentDomain(org.
	 * integratedmodelling.riskwiz.pt.DiscreteDomain)
	 */
	@Override
	public void removeParentDomain(Domain dom) {
		this.parentsDomains.remove(dom);
		resetDomainProduct();
		resetMultiarray();
	}

	/*
	 * TODO order can change
	 */

	public void resetDomainProduct() {

		// discParentDomains = new Vector<DiscreteDomain>();

		this.domainProduct = new Vector<DiscreteDomain>();
		this.domainProduct.add((DiscreteDomain) domain);
		for (Domain dom : parentsDomains) {
			this.domainProduct.add((DiscreteDomain) dom);
			// discParentDomains.add((DiscreteDomain) dom);
		}

	}

	// public double querySuperSet(Query q){
	// return 0;
	// }
	//
	// public double querySubSet(Query q){
	// return 0;
	// }
	//

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.IFunction#toString()
	 */
	@Override
	public String toString() {
		// PrintValueCPFString printer = new PrintValueCPFString();
		String stream = "";
		int max = this.size();
		int tab = ((DiscreteDomain) this.getDomain()).getOrder();

		for (int j = 0; j < max / tab; j++) {
			for (int k = 0; k < tab; k++) {
				int index = k * max / tab + j;

				int[] aref = index2addr(index);
				String printEntry = "\nP(";

				for (int i = 0; i < domainProduct.size(); i++) {
					Domain dom = domainProduct.elementAt(i);
					String domName = dom.getName();

					printEntry += " " + dom.getName() + "=" + ((DiscreteDomain) dom).getState(aref[i]);
					if (i == 0 && domainProduct.size() > 1) {
						printEntry += " |";
					} else if (i < aref.length - 1) {
						printEntry += ",";
					}
				}
				printEntry += ")= " + RT.toString(this.getValue(index));
				stream += printEntry;

			}
		}
		return stream;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.IFunction#getDomainProduct()
	 */
	@Override
	public Vector<DiscreteDomain> getDomainProduct() {
		return domainProduct;
	}

	// public void setDomainProduct(Vector<DiscreteDomain> domainProduct) {
	// this.domainProduct = domainProduct;
	// }

	protected void resetMultiarray() {
		resetSize();
		multiarray = new Object[size()];

	}

	/*
	 * ! Get the sum of the values for a query, q[i] = k, k >= 0 -> specific value,
	 * -1 all values, per domain \param[in] query the query variable \return the
	 * value or sum of values
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.IFunction#getValue(int[])
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.ITabularFunction#getValue(int[])
	 */
	public Object getValue(int[] query) {

		for (int k = 0; k < query.length; k++) {
			if (query[k] == -1) {
				Object s = RT.parse(0);

				for (int j = 0; j < structure[k]; j++) {
					query[k] = j;
					Object p = getValue(query);

					s = RT.add(s, p);
				}
				query[k] = -1;
				return s;
			}
		}
		return multiarray[addr2index(query)];

	}

	private int[] arrayCopy(int[] query) {
		int[] out = new int[query.length];

		for (int i = 0; i < out.length; i++) {
			out[i] = query[i];
		}
		return out;
	}

	public Object getValue(int index) {
		return multiarray[index];
	}

	public Object getValue(Query q) {
		int[] query = getQueryProjectionStructure(q);

		return getValue(query);
	}

	@Override
	public Object getValue(List args) {
		int[] query = processQuery(args);

		return getValue(query);
	}

	public Object getSingleValue() {
		return singleValue;
	}

	public void setSingleValue(Object scalarValue) {
		this.singleValue = scalarValue;
	}

	protected int[] processQuery(List args) {

		int[] query = new int[parentsDomains.size()];

		for (int i = 0; i < parentsDomains.size(); i++) {
			DiscreteDomain pdom = parentsDomains.elementAt(i);

			query[i] = pdom.findState((String) args.get(i));
			// query[i]=(Integer)args.get(i); //pdom.findState((String) args.get(i));
		}
		return query;
	}

	private int[] getQueryProjectionStructure(Query q) {

		int[] struc = q.getProjectionStructure(domainProduct);

		return struc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.ITabularFunction#setValue(int[],
	 * java.lang.Object)
	 */
	public void setValue(int[] query, Object val) {
		for (int k = 0; k < query.length; k++) {
			if (query[k] == -1) {
				for (int j = 0; j < structure[k]; j++) {
					query[k] = j;
					setValue(query, val);
				}
				query[k] = -1;
				return;
			}
		}
		multiarray[addr2index(query)] = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.ITabularFunction#setValue(int,
	 * java.lang.Object)
	 */
	public void setValue(int index, Object val) {
		multiarray[index] = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.ITabularFunction#setValues(java.lang
	 * .Object[])
	 */
	public void setValues(Object[] vals) {
		if (vals.length != this.size()) {
			System.out.println("Wrong size of input array for ");
		}
		for (int i = 0; i < vals.length; i++) {
			this.setValue(i, vals[i]);
		}

	}

	public void setValues(Integer[] vals) {
		if (vals.length != this.size()) {
			System.out.println("Wrong size of input array for ");
		}
		for (int i = 0; i < vals.length; i++) {
			this.setValue(i, vals[i]);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.ITabularFunction#setValues(double[])
	 */
	public void setValues(double[] vals) {
		if (vals.length != this.size()) {
			System.out.println("Wrong size of input array for ");
		}

		for (int i = 0; i < vals.length; i++) {
			this.setValue(i, RT.parse(vals[i]));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.ITabularFunction#setValue(int[],
	 * double)
	 */
	public void setValue(int[] struc, double val) {

		this.setValue(struc, Double.valueOf(val));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.ITabularFunction#setValue(int,
	 * double)
	 */
	public void setValue(int index, double val) {

		this.setValue(index, Double.valueOf(val));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.ITabularFunction#setValues(java.lang
	 * .String[])
	 */
	public void setValues(String[] vals) {
		if (vals.length != this.size()) {
			System.out.println("Wrong size of input array for ");
			// System.out.println(domainProduct.get(0).getName());
			// System.out.println(contDomainProduct.get(0).getName());
		}

		for (int i = 0; i < vals.length; i++) {
			this.setValue(i, RT.parse(vals[i]));
		}

	}

	public void setValues(Vector<String> vals) {

		if (vals.size() != this.size()) {
			System.out.println("Wrong size of input array for " + vals.size() + "vs." + this.size());

			for (int i = 0; i < domainProduct.size(); i++) {
				System.out.println(domainProduct.get(i).getName());
			}
			// System.out.println(domainProduct.get(0).getName());
			// System.out.println(contDomainProduct.get(0).getName());

		}

		for (int i = 0; i < vals.size(); i++) {
			this.setValue(i, RT.parse(vals.elementAt(i)));

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.ITabularFunction#setValue(int[],
	 * java.lang.String)
	 */
	public void setValue(int[] struc, String val) {
		this.setValue(struc, RT.parse(val));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.ITabularFunction#setValue(int,
	 * java.lang.String)
	 */
	public void setValue(int index, String val) {
			this.setValue(index, RT.parse(val));
	}

//	public Object[] getValues() {
//		Object[] vals = new Node[this.size()];
//
//		for (int i = 0; i < this.size(); i++) {
//			vals[i] = this.getValue(i);
//		}
//		return vals;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.ITabularFunction#setAll(java.lang.
	 * Object)
	 */
	public void setAll(Object val) {

		if (isSingleValue) {
			singleValue = val;
		} else {

			for (int i = 0; i < multiarray.length; i++) {
				multiarray[i] = val;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.ITabularFunction#setAll(java.lang.
	 * String)
	 */
	public void setAll(String val) {
			Object n = RT.parse(val);
			setAll(n);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.riskwiz.pf.ITabularFunction#setAll(double)
	 */
	public void setAll(double val) {
			this.setAll(RT.parse(val));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.integratedmodelling.riskwiz.pf.ITabularFunction#setValue(org.
	 * integratedmodelling.riskwiz.pt.Query, java.lang.Object)
	 */
	public void setValue(Query q, Object val) {
		int[] struc = getQueryProjectionStructure(q);

		setValue(struc, val);
	}

	// public Vector<DiscreteDomain> getDiscreteParentsDomains() {
	//
	// return discParentDomains;
	//
	// }

}

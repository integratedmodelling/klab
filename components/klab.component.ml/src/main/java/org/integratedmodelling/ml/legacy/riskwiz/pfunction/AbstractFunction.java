/**
 * AbstractFunction.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: May 15, 2009
 *
 * ----------------------------------------------------------------------------------
 * This file is part of riskwiz-cvars.
 * 
 * riskwiz-cvars is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * riskwiz-cvars is distributed in the hope that it will be useful,
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
 * @copyright 2009 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      May 15, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.pfunction;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.Domain;


/**
 * @author Sergey Krivov
 *
 */
public abstract class AbstractFunction implements IExpressionFunction {
	
    protected Domain domain;

    protected Vector<Domain> parentsDomains;
    protected Vector<Domain> domainProduct;

    /**
     * 
     */
    public AbstractFunction() {// TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pfunction.IFunction#addParentDomain(org.integratedmodelling.riskwiz.domain.DiscreteDomain)
     */
    @Override
	public void addParentDomain(Domain dom) {
        this.parentsDomains.add(dom);
        resetDomainProduct();
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pfunction.IFunction#getDomain()
     */
    @Override
	public Domain getDomain() {		 
        return domain;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pfunction.IFunction#getParentsDomains()
     */
    @Override
	public Vector<Domain> getParentsDomains() {		 
        return parentsDomains;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pfunction.IFunction#removeParentDomain(org.integratedmodelling.riskwiz.domain.DiscreteDomain)
     */
    @Override
	public void removeParentDomain(Domain dom) {
        this.parentsDomains.remove(dom);
        resetDomainProduct();

    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pfunction.IFunction#setDomain(org.integratedmodelling.riskwiz.domain.DiscreteDomain)
     */
    @Override
	public void setDomain(Domain domain) {
        this.domain = domain;
        resetDomainProduct();

    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pfunction.IFunction#setParentsDomains(java.util.Vector)
     */
    @Override
	public void setParentsDomains(Vector<? extends Domain> parentsDomains) {
        this.parentsDomains = (Vector<Domain>) parentsDomains;
        resetDomainProduct();

    }
	
    @Override
	public Vector<Domain> getDomainProduct() {
        return domainProduct;
    }

    public void resetDomainProduct() {		 
        this.domainProduct = new Vector<Domain>();
        this.domainProduct.add(domain);
        for (Domain dom : parentsDomains) {
            this.domainProduct.add(dom);
			 
        }

    }
	
    // TODO
    @Override
	public Vector<String> getArguments() {
        return null;
    }
	 
    // TODO
    @Override
	public String getExpression() {
        return null;
    }

}

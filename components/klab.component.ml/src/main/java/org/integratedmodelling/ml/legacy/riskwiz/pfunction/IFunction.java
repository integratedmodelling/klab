package org.integratedmodelling.ml.legacy.riskwiz.pfunction;


import java.util.List;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.Domain;


public interface IFunction {

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pf.IFunction#getDomain()
     */
    public abstract Domain getDomain();

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pf.IFunction#setDomain(org.integratedmodelling.riskwiz.pt.DiscreteDomain)
     */
    public abstract void setDomain(Domain domain);

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pf.IFunction#getParentsDomains()
     */
    public abstract Vector<? extends Domain> getParentsDomains();

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pf.IFunction#setParentsDomains(java.util.Vector)
     */
    public abstract void setParentsDomains(Vector<? extends Domain> parentsDomains);

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pf.IFunction#addParentDomain(org.integratedmodelling.riskwiz.pt.DiscreteDomain)
     */
    public abstract void addParentDomain(Domain dom);

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pf.IFunction#removeParentDomain(org.integratedmodelling.riskwiz.pt.DiscreteDomain)
     */
    public abstract void removeParentDomain(Domain dom);

    // public abstract String toString();

    public abstract Vector<? extends Domain> getDomainProduct();

    // public abstract Object getValue(int[] query) throws ParseException;
	
    public abstract Object getValue(List args); 

}

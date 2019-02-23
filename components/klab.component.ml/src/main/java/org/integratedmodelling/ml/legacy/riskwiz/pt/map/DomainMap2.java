package org.integratedmodelling.ml.legacy.riskwiz.pt.map;


import java.util.Iterator;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;


public class DomainMap2 {
	
    protected Vector<DiscreteDomain>	domainProduct;
 
    protected int[]	mapSecond;
    protected int sizeFirst;
    protected int sizeSecond;

    /*
     * these constructors asume that there is one unique domain for each variable, thus if two vectors contain
     * same variable thay contain domain (objects) representing them.
     */
	

    public DomainMap2(Vector<? extends DiscreteDomain> first, Vector<? extends DiscreteDomain> second) {
        sizeFirst = first.size();
        sizeSecond = second.size();
        domainProduct = new Vector<DiscreteDomain>();
		
        domainProduct.addAll(first);
		
        for (Iterator iter = second.iterator(); iter.hasNext();) {
            DiscreteDomain domain = (DiscreteDomain) iter.next();

            // TODO attention! check for possible error
            if (!domainProduct.contains(domain)) {
                domainProduct.add(domain);
            }
			
        }
		
        mapSecond = new int[sizeSecond];
        for (int j = 0; j < mapSecond.length; j++) {
            mapSecond[j] = domainProduct.indexOf(second.elementAt(j));
        }
    }
	
    public int[] getProjectionFirst(int[] domainProductReference) {
        int[] firstSt = new int[sizeFirst];

        for (int i = 0; i < firstSt.length; i++) {
            firstSt[i] = domainProductReference[i];
        }
        return firstSt;
    }
	
    public int[] getProjectionSecond(int[] domainProductReference) {
        int[] secondSt = new int[sizeSecond];

        for (int i = 0; i < secondSt.length; i++) {
            secondSt[i] = domainProductReference[mapSecond[i]];
        }
        return secondSt;
    }

    public Vector<DiscreteDomain> getDomainProduct() {
        return domainProduct;
    }

}

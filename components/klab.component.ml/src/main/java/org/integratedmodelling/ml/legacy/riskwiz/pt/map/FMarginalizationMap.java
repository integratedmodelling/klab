package org.integratedmodelling.ml.legacy.riskwiz.pt.map;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;


public class FMarginalizationMap {
	
    protected Vector<DiscreteDomain> projectionsDomainProduct;
    protected Vector<DiscreteDomain> globalDomainProduct;

    protected int sizeProjectionSpace;

    protected int sizeGlobalSpace;

    protected int[]	mapProjection;
    protected int[] mapFiber;
	 
    private int[] indexMap;
	
    public Vector<DiscreteDomain> getProjectionDomainProduct() {
        return projectionsDomainProduct;
    }

    public int[] getIndexMap() {
        return indexMap;
    }

    public FMarginalizationMap(Vector<DiscreteDomain> domainProduct,
            Vector<DiscreteDomain> marginals) {
        globalDomainProduct = domainProduct;
        sizeGlobalSpace = domainProduct.size();
        sizeProjectionSpace = marginals.size();
        projectionsDomainProduct = marginals;

        initialize();
        compile();
    }
	
    public FMarginalizationMap(Vector<DiscreteDomain> domainProduct,
            DiscreteDomain  marginal) {
        globalDomainProduct = domainProduct;
        sizeGlobalSpace = domainProduct.size();
        sizeProjectionSpace = 1;
        projectionsDomainProduct = new Vector<DiscreteDomain>();
        projectionsDomainProduct.add(marginal);
        initialize();
        compile();
    }
	
    protected void initialize() {
		 
        mapProjection = new int[sizeProjectionSpace];
        for (int j = 0; j < mapProjection.length; j++) {
            mapProjection[j] = globalDomainProduct.indexOf(
                    projectionsDomainProduct.elementAt(j));
        }
		
        mapFiber = new int[sizeGlobalSpace];
        for (int i = 0; i < mapFiber.length; i++) {
            mapFiber[i] = projectionsDomainProduct.indexOf(
                    globalDomainProduct.elementAt(i));
			 
        }
		
    }
	
    public void compile() {
        PTMap ptMap = new PTMap(globalDomainProduct);
        PTMap margPtMap = new PTMap(projectionsDomainProduct);

        indexMap = new int[ptMap.size()];
        int[] productStructureIterator = ptMap.index2addr(0);		 
        boolean done = false;

        while (!done) {
            int index = ptMap.addr2index(productStructureIterator);
            int projectionIndex = margPtMap.addr2index(
                    this.getProjection(productStructureIterator));

            indexMap[index] = projectionIndex; 
            done = ptMap.addOne(productStructureIterator);
        }
    } 
	
    public int[] getProjection(int[] domainProductReference) {
        int[] projectionReference = new int[sizeProjectionSpace];

        for (int i = 0; i < projectionReference.length; i++) {
            projectionReference[i] = domainProductReference[mapProjection[i]];
        }
        return projectionReference;
    }
	
    /*
     * get description of superspace of one point in the projectionDomainProduct
     * -1 means that all the coordinate values for this dimension are part of
     * the fiber
     */

    public int[] getFiber(int[] projectionSpaceReference) {
        int[] fiberReference = new int[sizeGlobalSpace];

        for (int i = 0; i < fiberReference.length; i++) {
            if (mapFiber[i] == -1) {
                fiberReference[i] = -1;
            } else {
                fiberReference[i] = projectionSpaceReference[mapFiber[i]];
            }
        }
        return fiberReference;
    }
	
}

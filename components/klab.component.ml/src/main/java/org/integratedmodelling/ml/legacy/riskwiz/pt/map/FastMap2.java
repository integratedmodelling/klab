package org.integratedmodelling.ml.legacy.riskwiz.pt.map;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;


// map for fast multiplication, not coming any soon
public class FastMap2 extends DomainMap2 {
	
    private int[] indexMap1;
    private int[] indexMap2;
	
    // private PTMap ptMap;
    // private PTMap ptMap1;
    // private PTMap ptMap2;

    /**
     * @param first
     * @param second
     */
    public FastMap2(Vector<DiscreteDomain> first, Vector<DiscreteDomain> second) {
        super(first, second);
        compile(domainProduct, first, second);
    }

    public int[] getIndexMap1() {
        return indexMap1;
    }

    public int[] getIndexMap2() {
        return indexMap2;
    }
	 
    public void compile(Vector<DiscreteDomain> out, Vector<DiscreteDomain> first, Vector<DiscreteDomain> second) {
        PTMap ptMap = new PTMap(out);
        PTMap ptMap1 = new PTMap(first);
        PTMap ptMap2 = new PTMap(second);

        indexMap1 = new int[ptMap.size()];
        indexMap2 = new int[ptMap.size()];
		
        int[] productStructureIterator = ptMap.index2addr(0);		 
        boolean done = false;

        while (!done) {
            setIndexMapValues(productStructureIterator, ptMap, ptMap1, ptMap2); 
            done = ptMap.addOne(productStructureIterator);
        }
		
        // int[] productStructure = ptMap.getStructure();
        // int[] productStructureIterator = MultiarrayReferenceFactory.getProductStructureIterator(productStructure);
        //
        // iterateStructure(productStructure, productStructureIterator, 0, ptMap,
        // ptMap1, ptMap2 );

    }
	
    /*
     * A bit complex Recursive function that does simulated iteration through the output PT
     * multiarray using PTMap getting the appropriate arguments /projections from the two
     * argument PT multiarrays and  get index maps from those. Making the loop inside if else rather than vice versa
     * decreases number of boolen tests (code is longe
     */
    // public   void iterateStructure(int[] struc,
    // int[] productStructureIterator, int from, PTMap ptOut, PTMap ptm1, PTMap ptm2)  {
    // int newFrom = from + 1;
    // if (newFrom < struc.length) {
    // for (int i = 0; i <= struc[from]; i++) {
    // productStructureIterator[from] = i;
    // // main operation of filling in the content of index maps
    // setIndexMapValues(productStructureIterator, ptOut, ptm1, ptm2);
    //
    //
    // // recursive step
    // iterateStructure(struc, productStructureIterator, newFrom,
    // ptOut, ptm1, ptm2 );
    //
    // }
    // } else {
    // for (int i = 0; i <= struc[from]; i++) {
    // productStructureIterator[from] = i;
    // // main operation of filling in the content of index maps
    // setIndexMapValues(productStructureIterator, ptOut, ptm1, ptm2);
    //
    // // this was the last dimension to iterate over, no recursion
    // // further
    //
    // }
    //
    // }
    //
    // }
	
    private void setIndexMapValues(int[] productStructureIterator,
            PTMap ptOut, PTMap ptm1, PTMap ptm2) {
		
        int index = ptOut.addr2index(productStructureIterator);
        int index1 = ptm1.addr2index(
                this.getProjectionFirst(productStructureIterator));
        int index2 = ptm2.addr2index(
                this.getProjectionSecond(productStructureIterator));

        indexMap1[index] = index1;
        indexMap2[index] = index2;
		
        // int index1   = ptm1.addr2index(this.getProjectionFirst(productStructureIterator));
        // int index2 = ptm2.addr2index(this.getProjectionSecond(productStructureIterator));
        // int indexOut= ptOut.addr2index(productStructureIterator);
        // indexMap1[index1]=indexOut;
        // indexMap2[index2]=indexOut;
    }

}

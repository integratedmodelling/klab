package org.integratedmodelling.ml.legacy.riskwiz.domain;


import java.util.Vector;


public class DiscreteType extends Type {
	
    protected int		order = 0;
	
    public DiscreteType(String name) {
        super(name);
    }
	
    public DiscreteType(String name, int order) {
        super(name);
        this.order = order;
    }
	
    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pt.Domain#getOrder()
     */
	 
    public int getOrder() {		 
        return order;
    }
	
    public void setOrder(int order) {
        this.order = order;
    }
	
    public Vector<String> getStates() {
        Vector<String> values = new Vector<String>();

        for (int i = 0; i < order; i++) {
            values.add(String.valueOf(i));
        }
        return values;
    }
	
    public String getState(int i) {
        return String.valueOf(i);
    }
	
    public int findState(String val) {
        int i = Integer.valueOf(val);

        return (i < order) ? i : -1;
    }

}

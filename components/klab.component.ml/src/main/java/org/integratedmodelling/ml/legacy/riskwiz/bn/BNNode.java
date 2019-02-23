package org.integratedmodelling.ml.legacy.riskwiz.bn;


import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.ContinuousDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.Domain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.IntervalDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.LabelDomain;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.FunctionTableFactory;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.IFunction;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.NoisyT;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularCPD;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularDF;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularDetF;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularUF;
import org.integratedmodelling.ml.legacy.riskwiz.pt.CPT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.PT;
 

public class BNNode implements Comparable<BNNode> {	
	 
    public enum DomainType {
        discrete, labels, intervals, continuous
    }


    public enum NodeType {
        probabilistic, noisymax, deterministic, utility, decision
    }

    private DomainType domType;
    private NodeType nodeType;
	
    private Hashtable<String, Object> properties; 
    private int count; 
    
    private boolean isExpression = false;
    
    double marginalUtility;
    
    private int weight;
	
    private String name;
	
    private String comment;
	
    private IFunction function;
	
    private CPT discreteCPT;
	
    private NoisyT noisyT;
	
    int discretizationOrder;
    private DiscreteDomain  discretizedDomain;
 	
    private PT evidence = null;
    private PT marginal = null;
	
    /*************************************************
     * Auxiliary fields used in stochastic inference  * 
     **************************************************/
	
    private double[] samplesCounter;
    private Object currentSample;
    private int discretizedSample;
	
    Vector<BNNode> orderedParents;

    public Vector<BNNode> getOrderedParents() {
        return orderedParents;
    }

    public void setOrderedParents(Vector<BNNode> orderedParents) {
        this.orderedParents = orderedParents;
    }

    public int getDiscretizedSample() {
        return discretizedSample;
    }

    public void setDiscretizedSample(int discretizedSample) {
        this.discretizedSample = discretizedSample;
    }

    public Object getCurrentSample() {
        return currentSample;
    }

    public void setCurrentSample(Object currentSample) {
        this.currentSample = currentSample;
    }

    public double[] getSamplesCounter() {
        return samplesCounter;
    }

    public void initSamplesCounter() {
        this.samplesCounter = new double[getDiscretizedDomain().getOrder()];
    }
	
    /******** end of stochastic inference part    ******/

    public double getMarginalUtility() {
        return marginalUtility;
    }

    public void setMarginalUtility(double marginalUtility) {
        this.marginalUtility = marginalUtility;
    }
	
    public BNNode(DiscreteDomain dom) {
        init(dom, NodeType.probabilistic);
    }
	
    public BNNode(DiscreteDomain dom, NodeType nodeType) {
        init(dom, nodeType);
    }

    public BNNode(String name) {
        init(name, NodeType.probabilistic);
    }
	
    public BNNode(String name, NodeType nodeType) {
        init(name, nodeType);
    }	
	
    public BNNode(String name, double from, double to, int numberOfIntervals) {
        init(name, from, to, numberOfIntervals, NodeType.probabilistic);
    }
	
    public BNNode(String name, String[] labels) {
        init(name, labels, NodeType.probabilistic);
    }
	
    public BNNode(String name, int order) {
        init(name, order, NodeType.probabilistic);
    }
	
    public BNNode(String name, String[] labels, NodeType nodeType) {
        init(name, labels, nodeType);
    }
	
    public BNNode(String name, int order, NodeType nodeType) {
        init(name, order, nodeType);
    }
	
    public BNNode(String name, double from, double to, int numberOfIntervals, NodeType nodeType) {
        init(name, from, to, numberOfIntervals, nodeType);
    }
	
    protected void init(DiscreteDomain dom, NodeType nodeType) {
        this.name = dom.getName(); 
        this.nodeType = nodeType;
        properties = new Hashtable();		
        domType = DomainType.labels;
		 
        switch (nodeType) {
        case  probabilistic:
            this.function = new TabularCPD(dom, null);
            break;

        case  noisymax:
            this.function = new TabularCPD(dom, null);
            this.noisyT = new NoisyT(dom, null);
            break;

        case  deterministic:
            this.function = new TabularDetF(dom, null);
            break;			 

        case  utility:
            this.function = new TabularUF(dom, null);
            break;

        case  decision:
            this.function = new TabularDF(dom);
            break;

        default:
            break;
        }	
		
    } 

    protected void init(String name, NodeType nodeType) {
        this.name = name; 
        this.nodeType = nodeType;
        properties = new Hashtable();		
        domType = DomainType.labels;
        switch (nodeType) {
        case  probabilistic:
            this.function = FunctionTableFactory.createDefaultCPF(name);
            break;	

        case  deterministic:
            this.function = FunctionTableFactory.createDefaultDetF(name);
            break;	

        case  noisymax:
            this.function = FunctionTableFactory.createDefaultCPF(name);
            this.noisyT = new NoisyT((DiscreteDomain) this.getDomain(), null);
            break;

        case  utility:
            this.function = new TabularUF(name, null);
            break;

        case  decision:
            this.function = FunctionTableFactory.createDefaultDF(name);
            break;

        default:
            break;
        }	
		 
    }
	
    protected void init(String name, String[] labels, NodeType nodeType) {
        this.name = name; 
        this.nodeType = nodeType;
        properties = new Hashtable();
        domType = DomainType.labels;
        switch (nodeType) {
        case  probabilistic:
        case  deterministic:
            this.function = FunctionTableFactory.createCPF(name, labels);
            break;			

        case  decision:
            this.function = FunctionTableFactory.createDF(name, labels);
            break;

        case  utility:
            throw new UnsupportedOperationException(
                    "wrong constructor for Utility Node");

        default:
            break;
        }	
		
    }
	
    protected void init(String name, int order, NodeType nodeType) {
        this.name = name; 
        this.nodeType = nodeType;		
        domType = DomainType.discrete;
        properties = new Hashtable();
        switch (nodeType) {
        case  probabilistic:
        case  deterministic:
            this.function = FunctionTableFactory.createCPF(name, order);
            break;			

        case  decision:
            this.function = FunctionTableFactory.createDF(name, order);
            break;

        case  utility:
            throw new UnsupportedOperationException(
                    "wrong constructor for Utility Node");

        default:
            break;
        }
		
    }
	
    protected void init(String name, double from, double to, int numberOfIntervals, NodeType nodeType) {
        this.name = name; 
        this.nodeType = nodeType;
        domType = DomainType.intervals;
        properties = new Hashtable();
        switch (nodeType) {
        case  probabilistic:
        case  deterministic:
            this.function = FunctionTableFactory.createCPF(name, from, to,
                    numberOfIntervals);
            break;			

        case  decision:
            this.function = FunctionTableFactory.createDF(name, from, to,
                    numberOfIntervals);
            break;

        case  utility:
            throw new UnsupportedOperationException(
                    "wrong constructor for Utility Node");

        default:
            break;
        }		
    }
	
    public void setProperty(String name, Object val) {
        properties.put(name, val);
    }
	
    public Object getProperty(String name) {
        return properties.get(name);
    }
	
    public void clearProperty(String name) {
        properties.remove(name);
    }
	
    public Set<String> getPropertyNames() {
        return properties.keySet();
    }
	
    public boolean hasEvidence() {
        return (evidence != null);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public IFunction getFunction() {
        return function;
    }
	
    public Vector<? extends Domain> getDomainProduct() {
        return function.getDomainProduct();
    }
	
    public Vector<? extends Domain> getParentDomains() {
        return function.getParentsDomains();
    }
	
    public Domain getDomain() {
        return function.getDomain();
    }
	
    public DiscreteDomain getDiscretizedDomain() {
        return discretizedDomain;
    }

    // not safe at the moment, names of domain and node could missmatch
    public void setDomain(Domain  dom) {
        function.setDomain(dom);
        if (dom instanceof LabelDomain) {
            domType = DomainType.labels;
        } else if (dom instanceof IntervalDomain) {
            domType = DomainType.intervals;
        } else if (dom instanceof ContinuousDomain) {
            domType = DomainType.continuous;
        } else {
            domType = DomainType.discrete;
        }
        if (noisyT != null && dom instanceof DiscreteDomain) {
            noisyT.setDomain((DiscreteDomain) dom);
        }
    }
	
    public void setDiscretizedDomain(DiscreteDomain  dom) {
        discretizedDomain = dom;
    }
	
    public void setIntervalDomain(double from, double to, int numberOfIntervals) {
        DiscreteDomain dom = new IntervalDomain(this.name, from, to,
                numberOfIntervals);

        function.setDomain(dom);
        if (noisyT != null) {
            noisyT.setDomain(dom);
        }
    }
	
    public void setLabelDomain(String[] labels) {
        DiscreteDomain dom = new LabelDomain(this.name, labels);

        function.setDomain(dom);
        if (noisyT != null) {
            noisyT.setDomain(dom);
        }
    }
	
    public void setFunction(IFunction  f) {
        this.function = f;
    }
	
    // public void setValues(double[] vals) {		
    // this.function.setValues(vals);
    //
    // }
    //
    //
    //
    // public void setValue(int[] struc, double val) {
    // this.function.setValue(struc,val);
    //
    // }
    //
    // public void setValue(int index, double val) {
    // this.function.setValue(index,val);
    //
    // }
    //
    // public void setValues(String[] vals) {
    //
    // this.function.setValues(vals);
    //
    // }
    //
    // public void setValues(Vector<String> vals) {
    // this.function.setValues(vals);
    //
    //
    // } 
    //
    // public void setValue(int[] struc, String val) {
    // this.function.setValue(struc, val);
    //
    // }
    //
    // public void setValue(int index, String val) {
    // this.function.setValue(index, val);
    // }
    //
    // public Object[]   getValues( ) {
    // return this.function.getValues();
    // }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
		 
        if (getDomType() != DomainType.continuous) {
            return ((DiscreteDomain) getDomain()).getOrder();
        } else {
            return getDiscretizationOrder();
        }
		 
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
	
    public DomainType getDomType() {
        return domType;
    }
	 
    public void addParentNode(BNNode parentNode) {
        this.function.addParentDomain(parentNode.getDomain());
        if (this.noisyT != null) {
            // vector parentDomains is the same in both, noisyT knows that domain is added
            this.noisyT.addedParentDomain(
                    (DiscreteDomain) parentNode.getDomain());
        }
    }
	
    public void removeParentNode(BNNode parentNode) {
        this.function.removeParentDomain(parentNode.getDomain());
        if (this.noisyT != null) {
            this.noisyT.removedParentDomain(
                    (DiscreteDomain) parentNode.getDomain());
        }
    }

    public PT getEvidence() {
        // if(Setting.DEBUG){
        // assertNodeType(NodeType.probabilistic);
        // }
        return evidence;
    }

    public void setEvidence(PT mpt) {
        // if(Setting.DEBUG){
        // assertNodeType(NodeType.probabilistic);
        // }
        this.evidence = mpt;
    }
	
    public void retractEvidence() {
        // if(Setting.DEBUG){
        // assertNodeType(NodeType.probabilistic);
        // }
        this.evidence = null;
    }

    public final boolean isDecision() {
        return (this.nodeType == NodeType.decision);
    }
	
    public final boolean isUtility() {
        return (this.nodeType == NodeType.utility);
    }
	
    public final boolean isDeterministic() {
        return (this.nodeType == NodeType.deterministic);
    }
	
    public final boolean isProbabilistic() {
        return (this.nodeType == NodeType.probabilistic)
                || (this.nodeType == NodeType.noisymax);
    }
	
    public final boolean isNoisyMax() {
        return (this.nodeType == NodeType.noisymax);
    }
	
    public final boolean isNature() {
        return (this.nodeType == NodeType.probabilistic
                || this.nodeType == NodeType.deterministic
                || this.nodeType == NodeType.noisymax);
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public PT getMarginal() {
		 
        return marginal;
    }

    public void setMarginal(PT marginal) {	
		 
        this.marginal = marginal;
    }
	
    private void assertNodeType(NodeType nt) {
        if (nodeType != nt) {
            throw new UnsupportedOperationException(
                    nodeType + "is wrong NodeType");
        }
    }
	
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
	
    @Override
	public int compareTo(BNNode o) {
		 
        return (o.getCount() - this.getCount());
    }

    public int getDiscretizationOrder() {
        if (function.getDomain() instanceof ContinuousDomain) {
            return ((ContinuousDomain) function.getDomain()).getDiscretizationOrder();
        }
        return -1;
        // TODO exception
    }

    public void setDiscretizationOrder(int discretizationOrder) {
        if (function.getDomain() instanceof ContinuousDomain) {
            ((ContinuousDomain) function.getDomain()).setDiscretizationOrder(
                    discretizationOrder);
        }
    }

    public CPT getDiscreteCPT() {
        return discreteCPT;
    }

    public void setDiscreteCPT(CPT  cpt) {
        discreteCPT = cpt;
    }
	
    // public boolean hasContinuousVars(){
    //
    // if(this.function.getContDomainProduct()==null || this.function.getContDomainProduct().isEmpty()){
    // return false;
    // } else {
    // return true;
    // }
    // }

    public NoisyT getNoisyT() {
        return noisyT;
    }

    public void setNoisyT(NoisyT noisyT) {
        this.noisyT = noisyT;
    }

    // private void assertNodeType(NodeType nt1, NodeType nt2) {
    // if(nodeType!=nt1 && nodeType!=nt2){
    // throw new UnsupportedOperationException(nodeType+ "is wrong NodeType");
    // }
    // }

    public boolean isExpression() {
        return isExpression;
    }

    public void setExpression(boolean isExpression) {
        this.isExpression = isExpression;
    }
	 
}

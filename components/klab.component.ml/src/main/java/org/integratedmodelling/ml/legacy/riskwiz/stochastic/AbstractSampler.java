package org.integratedmodelling.ml.legacy.riskwiz.stochastic;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.discretizer.DomainDiscretizer;
import org.integratedmodelling.ml.legacy.riskwiz.domain.ContinuousDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.Domain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.IntervalDomain;
import org.integratedmodelling.ml.legacy.riskwiz.inference.IInference;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.IExpressionFunction;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.IFunction;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularFunction;
import org.integratedmodelling.ml.legacy.riskwiz.pt.PT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.TableFactory;
import org.jgrapht.Graph;
import org.jgrapht.traverse.TopologicalOrderIterator;


public abstract class AbstractSampler implements IInference {
	
    protected BeliefNetwork bn = null;
    protected int statespaceSize;
	
    boolean dirty = true; 
	
    public AbstractSampler(BeliefNetwork bn) {
        this.bn = bn;
        DomainDiscretizer.discretize(bn);
		 
    }
	
    public void setStatespeceSize() {
        statespaceSize = 1;
        Set<BNNode> nodes = bn.vertexSet();

        for (BNNode node : nodes) {
            statespaceSize *= node.getDiscretizedDomain().getOrder();
        }
    }
	
    public static Vector<BNNode> topologicalOrder(BeliefNetwork bn) {
		
        Vector<BNNode> vect = new Vector<BNNode>();

        for (TopologicalOrderIterator it = new TopologicalOrderIterator((Graph) bn); it.hasNext();) {
            BNNode node = (BNNode) it.next();

            vect.add(node);
			
        }
        return vect;
    }
	
    // fed to non tabular functions: expressions and java functions
    protected List getArguments(BNNode node) {
        List<Object>  args = new LinkedList<Object>();
        Vector<BNNode> parents = node.getOrderedParents();

        for (BNNode parentNode : parents) {
            if (parentNode.getDomain() instanceof IntervalDomain) {
                IntervalDomain idom = (IntervalDomain) parentNode.getDomain();
				
                // happens only in a bridge from discrete parents to continuous nodes
                args.add(
                        idom.getAvarage((Integer) parentNode.getCurrentSample()));
            } else {
                args.add(parentNode.getCurrentSample());
            }
        }
        return args;
    }
	
    // fed to  tabular functions
    protected List getDiscreteArguments(BNNode node) {
        List<Integer>  args = new LinkedList<Integer>();
        Vector<BNNode> parents = node.getOrderedParents();

        for (BNNode parentNode : parents) {
            args.add(parentNode.getDiscretizedSample());
        }
        return args;
    }
	
    // to fasten access during sampling we keep list of ordered parents
    // in each node. This helps to quickly find the arguments to be passed 
    // to the distribution/function sampled as in the two functions above
    public static void createOrderedParents(BeliefNetwork bn) {
        Set<BNNode> nodes = bn.vertexSet();

        for (BNNode bnode : nodes) {
            createNodeOrderedParents(bn, bnode);			
        }
		
    }
	
    public static void createNodeOrderedParents(BeliefNetwork bn, BNNode node) {
        Vector<BNNode> orderedParents = new  Vector<BNNode>();
        IFunction func = node.getFunction();

        if (func instanceof TabularFunction) {
            Vector<DiscreteDomain> pdomains = ((TabularFunction) func).getParentsDomains();

            for (DiscreteDomain dom : pdomains) {
                orderedParents.add(bn.getBeliefNode(dom.getName()));
            }
        } else if (node instanceof IExpressionFunction) {
            Vector<String> args = ((IExpressionFunction) func).getArguments();

            for (String arg : args) {
                orderedParents.add(bn.getBeliefNode(arg));
            }
        }		
		
        node.setOrderedParents(orderedParents);
    }
	
    public void initSamplesCounters() {
        Set<BNNode> nodes = bn.vertexSet();

        for (BNNode node : nodes) {
            node.initSamplesCounter();
        }
    }
	
    @Override
	public PT getEvidence(String nodeName) {
        BNNode node = bn.getBeliefNode(nodeName);
		 
        return node.getEvidence();
		  
    }
	
    public PT getBelief(String nodeName) {
        BNNode node = bn.getBeliefNode(nodeName);

        return getBelief(node);
		  
    }
	
    public PT getBelief(BNNode node) {
		 
        if (node.hasEvidence()) {
            return node.getEvidence();
        }  
        return node.getMarginal();
		  
    }

    @Override
	public PT getMarginal(String nodeName) {
        BNNode node = bn.getBeliefNode(nodeName);

        return node.getMarginal();
    }
	
    @Override
	public PT getMarginal(BNNode node) {		 
        return node.getMarginal();
    }
	
    @Override
	public void setEvidence(String nodeName, PT mpt) {
        BNNode node = bn.getBeliefNode(nodeName);

        if (node != null) {
            setEvidence(node, mpt);
        }
    }
	
    @Override
	public void setEvidence(BNNode node, PT mpt) {	 
		 
        node.setEvidence(mpt);		
        dirty = true;
    }
	
    // observation is a kind of evidence

    @Override
	public void setObservation(String nodeName, int valueIndex) {
        BNNode node = bn.getBeliefNode(nodeName);

        if (node != null) {
            setObservation(node, valueIndex);
        }
		
    }
	
    @Override
	public void setObservation(String nodeName, double value) {
        BNNode node = bn.getBeliefNode(nodeName);

        if (node != null) {
            setObservation(node, value);
        }
		
    }
	
    @Override
	public void setObservation(BNNode node, int valueIndex) {		 
        setEvidence(node,
                TableFactory.createObservation(node.getDiscretizedDomain(),
                valueIndex));		
    }
	
    @Override
	public void setObservation(BNNode node, double  value) {
        if (node.getDomType() == BNNode.DomainType.continuous) {
            int valueIndex = ((IntervalDomain) node.getDiscretizedDomain()).getStateIndex(
                    value);

            if (valueIndex > -1) {
                setEvidence(node,
                        TableFactory.createObservation(
                        node.getDiscretizedDomain(), valueIndex));
            } else {
                ; // throw exception
            }
        } else {
            ; // throw exception
        }
    }

    @Override
	public void setObservation(String nodeName, String value) {
        BNNode node = bn.getBeliefNode(nodeName);

        if (node != null) {
            setObservation(node, value);
        }
    }
	
    @Override
	public void setObservation(BNNode node, String value) {		 
        setEvidence(node,
                TableFactory.createObservation(node.getDiscretizedDomain(),
                value));		
    }
	
    @Override
	public void retractEvidence(String nodeName) {
        BNNode node = bn.getBeliefNode(nodeName);

        if (node != null) {
            retractEvidence(node);
        }
    }
	
    @Override
	public void retractEvidence(BNNode node) {
		 
        node.setEvidence(null);
        dirty = true;
    }
	
    public int mapToDiscreteDomain(BNNode node, Object aSample) {
        Domain dom = node.getDomain();
        int valueIndex;

        if (dom instanceof ContinuousDomain) {
            valueIndex = ((IntervalDomain) node.getDiscretizedDomain()).getStateIndex(
                    (Double) aSample);
			 
        } else {
            valueIndex = (Integer) aSample;
        }
		
        return valueIndex;
    }
	
}

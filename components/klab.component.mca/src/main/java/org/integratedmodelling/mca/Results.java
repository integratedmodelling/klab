package org.integratedmodelling.mca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.mca.MCA.CriterionDataType;

/**
 * Evamix results are grouped in an object of this kind.
 * 
 * @author Ferdinando Villa
 * 
 */
public class Results {

    private int                         num_criteria;
    private int                         num_alternatives;
    private int                         num_qual_criteria;
    private int                         num_quant_criteria;
    public double[][]           quant_impact_matrix;
    public double[][]           qual_impact_matrix;
    public double[][]           evamix_matrix;
    public double[]             evamix_scores;

    String[]                    alternative_names  = null;
    String[]                    criteria_names     = null;

    /**
     * if this is not null, the final scores and matrices do not contains
     * the original input criteria indexed by the column numbers in this
     * arra
     */
    public ArrayList<Integer>   degenerate_columns = null;
    private double[][]          input;
    private double[]            criteria_weights;
    private CriterionDataType[] criteria_types;
    private boolean[]           criteria_cost_benefit;
    
    private boolean isEmpty = false;
    
    public Results() {
        isEmpty = true;
    }

    public Results(double[][] input, double[] criteria_weights, CriterionDataType[] criteria_types,
            boolean[] criteria_cost_benefit) {

        this.input = input;
        this.criteria_weights = criteria_weights;
        this.criteria_types = criteria_types;
        this.criteria_cost_benefit = criteria_cost_benefit;
    }
    
    public boolean isEmpty() {
        return isEmpty;
    }

    public String dump() {

        StringBuffer buf = new StringBuffer();
        
        buf.append("# Criteria weights:\n");

        for (int i = 0; i < criteria_names.length; i++) {
            buf.append("\n* "
                    + criteria_names[i]
                    + " ("
                    + criteria_types[i]
                    + ", "
                    + (criteria_cost_benefit == null ? "benefit" : (criteria_cost_benefit[i] ? "benefit"
                            : "cost"))
                    + "): " + criteria_weights[i]);
        }

        buf.append("\n# Input data:\n");

        for (int i = 0; i < input.length; i++) {

            buf.append("\n* " + alternative_names[i] + ": ");
            for (int j = 0; j < input[0].length; j++) {
                buf.append(input[i][j] + " ");
            }
            buf.append("\n");
        }

        buf.append("\n# Final EVAMIX concordance scores:\n");

        for (int i = 0; i < evamix_scores.length; i++) {
            buf.append("\n* " + alternative_names[i] + ": " + evamix_scores[i]);
        }
        
        return buf.toString();
    }

    public void notifyDegenerateColumns(ArrayList<Integer> degenerateColumns) {
        degenerate_columns = degenerateColumns;
    }

    public void setCriteriaNames(String[] criteria_names) {
        this.criteria_names = criteria_names;
    }

    public void setAlternativeNames(String[] alternative_names) {
        this.alternative_names = alternative_names;
    }

    public int getNum_criteria() {
        return num_criteria;
    }

    public void setNum_criteria(int num_criteria) {
        this.num_criteria = num_criteria;
    }

    public int getNum_alternatives() {
        return num_alternatives;
    }

    public void setNum_alternatives(int num_alternatives) {
        this.num_alternatives = num_alternatives;
    }

    public int getNum_qual_criteria() {
        return num_qual_criteria;
    }

    public void setNum_qual_criteria(int num_qual_criteria) {
        this.num_qual_criteria = num_qual_criteria;
    }

    public int getNum_quant_criteria() {
        return num_quant_criteria;
    }

    public void setNum_quant_criteria(int num_quant_criteria) {
        this.num_quant_criteria = num_quant_criteria;
    }

    public Map<String, Double> getConcordances(boolean normalize) {
        
        Map<String,Double> ret = new HashMap<>();
        double[] scores = evamix_scores;
        if (normalize) {
            scores = NumberUtils.normalize(evamix_scores);
        }
        for (int i = 0; i < evamix_scores.length; i++) {
            ret.put(alternative_names[i], scores[i]);
        }
        return ret;
    }
}
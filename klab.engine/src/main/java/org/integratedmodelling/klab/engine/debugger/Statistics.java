package org.integratedmodelling.klab.engine.debugger;

import java.util.Objects;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.integratedmodelling.klab.Observations;

/**
 * Apache's summary statistics extended to handle nodata
 * 
 * @author Ferd
 *
 */
public class Statistics extends SummaryStatistics {

    private static final long serialVersionUID = 1578241098227493082L;

    private long totalCount;
    private long nodataCount;

    public long getNodataCount() {
        return nodataCount;
    }
    public void setNodataCount(long nodataCount) {
        this.nodataCount = nodataCount;
    }
    public long getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
   
    @Override
    public void addValue(double value) {
        totalCount ++;
        if (Observations.INSTANCE.isNodata(value)) {
            nodataCount ++;
        } else {
            super.addValue(value);
        }
    }
    @Override
    public String toString() {
        return super.toString() + 
                "nodata: " + nodataCount +
                "\nntotal: " + totalCount;
    }
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Statistics other = (Statistics) obj;
        return toString().equals(other.toString());
    }
    
    

}

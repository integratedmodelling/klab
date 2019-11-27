/*
 * Copyright 2014 Alterra, Wageningen UR
 * 
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl5
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package nl.wur.iclue.parameter;

/**
 * A focal filter is used to despeckle modeled land use projections
 * The focal filter has weights in a matrix form (e.g. 3x3, 5x5, or 7x7)
 * 
 * @author Peter Verweij
 */
public class FocalFilter {
    private final String ERROR = "Cannot create Focalfilter with block size %d. Block size should be at least 3 and uneven.";
    private final double[] DEFAULT_WEIGHTS = new double[] {1,1,1,1,1,1,1,1,1};
    private final double[][] weights;

//    public FocalFilter(double[][] weights) {
//        super();
//        this.weights = cloneWeights(weights);
//    }
    
    public FocalFilter(double[] weights) {
        super();
        if (weights == null)
            weights = DEFAULT_WEIGHTS;
        
        int blockSize = (int)Math.sqrt(weights.length);
        raiseExceptionIfInvalidBlockSize(blockSize);

        this.weights = new double[blockSize][blockSize];
        for (int row=0; row< blockSize; row++)
            for (int col=0; col< blockSize; col++)
                this.weights[row][col] = weights[row*blockSize+col];
    }
    
    public double getFocalWeight(boolean[][] v) {
        double result =0;
        for (int row=0; row< getBlockSize(); row++)
            for (int col=0; col< getBlockSize(); col++)
                if (v[row][col])
                    result += weights[row][col];
        
        return result/getSumWeights();
    }
    
    private double[][] cloneWeights(double[][] source) {
        int blockSize = source[0].length;
        raiseExceptionIfInvalidBlockSize(blockSize);
        
        double[][] result = new double[blockSize][blockSize];
        for (int row=0; row< blockSize; row++)
            System.arraycopy(source[row], 0, result[row], 0, blockSize);
        return result;
    }
    
    private void raiseExceptionIfInvalidBlockSize(int blockSize) {
        if ((blockSize<3) || (blockSize%2 != 1))
            throw new IllegalArgumentException(String.format(ERROR, blockSize));
    }
    
    public int getBlockSize() {
        return weights[0].length;
    }
    
    /**
     * 
     * @return a clone of the weights
     */
    public double[][] getWeights() {
        return cloneWeights(weights);
    }
    
    private double getSumWeights() {
        double sum = 0;
        
        int blockSize = getBlockSize();
        for (int row=0; row< blockSize; row++)
            for (int col=0; col< blockSize; col++)
                sum += weights[row][col];
        return sum;
    }
    
    private double getFraction(double weight) {
        double averageWeight = getSumWeights()/(Math.pow(getBlockSize(), 2));
        return weight/averageWeight;
    }

    
}

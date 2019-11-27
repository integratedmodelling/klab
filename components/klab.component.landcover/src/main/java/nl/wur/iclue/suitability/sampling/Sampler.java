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

package nl.wur.iclue.suitability.sampling;

import static nl.alterra.shared.datakind.IDataKind.Type.QUANTITATIVE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.datakind.DataKind;
import nl.alterra.shared.rasterdata.CellStack;
import nl.alterra.shared.rasterdata.RasterDataStack;
import nl.alterra.shared.utils.FileUtils;
import nl.alterra.shared.utils.log.Log;
import nl.alterra.shared.utils.log.LogTarget;
import nl.wur.iclue.parameter.SpatialDataset;

/**
 *
 * @author Johnny te Roller, Peter Verweij
 */
public class Sampler {
	
    private static final String LOG_SAMPLESET_CREATED    = "Samples created for '%s': in the land use (%d) and outside the land use (%d)";
    private static final String ERROR_INVALID_NROFVALUES = "Invalid number of driver values in sampleset for landuse %s";
    private static final int LANDUSE_INDEX = 0;
    private static String logToken;
    private static final String EXPORTFILE_NAME = FileUtils.getTempDir() + "SAMPLES_%s.csv";
    private static final String FILE_SAVED_AT   = "File saved as %s";
    
    public static void setLogToken(String logToken) {
        Sampler.logToken = logToken;
    }
    
    private static void log(String msg) {
        Log.log(null, msg, null, logToken);
    }
    
    public static SampleSet createSamplesForLanduse(Category landuse, RasterDataStack mapStack, List<SpatialDataset> drivers, Long cellCount, double nrOfSamples) {
        SampleSet result = new SampleSet();
        result.landuse = landuse;
        
        for (SpatialDataset ds: drivers)
            result.driverDataKinds.add(ds.getDatakind());
        
        // take half of total nrOfSamples, to get a balanced sampleset
        double sampleSizeHalf = nrOfSamples * 0.5; 
        
        Number[] values = new Number[1];
        values[0] = (Number)landuse.getValue();
        Long nrOfLanduse = mapStack.getNumberOfCellsMatchingTheFilter(values, true);

        double rateLU = nrOfLanduse / sampleSizeHalf;
        if (rateLU < 1) {
            sampleSizeHalf = nrOfLanduse;
            rateLU = 1;
        }
        double rateNLU = (cellCount - nrOfLanduse) / sampleSizeHalf;
        if (rateNLU < 1) {
            sampleSizeHalf = (cellCount - nrOfLanduse);
            rateNLU = 1;
            rateLU = nrOfLanduse / sampleSizeHalf;
        }
        
        int requestedLanduseOccurencesCount = 0;
        int otherLanduseOccurencesCount = 0;
        int requestedLanduseSamplesCount = 0;
        int otherLanduseSamplesCount = 0;
        for (CellStack cells: mapStack.getCellCursor()) {
            if (!mapStack.containsInputWithNodata(cells)) { 
                if (valueRepresentsLanduse(cells.inputValues[LANDUSE_INDEX], landuse)) {
                    requestedLanduseOccurencesCount++;
                    if ((requestedLanduseOccurencesCount > rateLU * requestedLanduseSamplesCount) && (requestedLanduseSamplesCount < sampleSizeHalf)) {
                        requestedLanduseSamplesCount++;
                        result.activeLanduseSamples.add(new Sample(removeLanduseFromCellStack(cells)));
                    }
                } else {
                    otherLanduseOccurencesCount++;
                    if ((otherLanduseOccurencesCount > rateNLU * otherLanduseSamplesCount) && (otherLanduseSamplesCount < sampleSizeHalf)) {
                        otherLanduseSamplesCount++;
                        result.otherLanduseSamples.add(new Sample(removeLanduseFromCellStack(cells)));
                    }
                }
            }
        }
        
        // log the result
        log(String.format(LOG_SAMPLESET_CREATED, 
                landuse.getCaption(), 
                result.activeLanduseSamples.size(), 
                result.otherLanduseSamples.size()));
        log(""); // add empty line for log readability
        
        return result;
    }
    
    
    private static Number[] removeLanduseFromCellStack(CellStack cells) {
        Number[] result = new Number[cells.inputValues.length-1];
        System.arraycopy(cells.inputValues, 1, result, 0, cells.inputValues.length-1);
        return result;
    }
    
    private static boolean valueRepresentsLanduse(Number value, Category landuse) {
        return ((Integer)landuse.getValue()) == value.intValue();
    }
    

    
    
    private static class Sample {
        Number[] driverValues;

        public Sample(Number[] driverValues) {
            this.driverValues = driverValues;
        }
    }
    
    /**
     * set of samples for a list of drivers
     * ASSUMPTION: indexing of drivers is equal to indexing of driver values in each sample
     */
    public static class SampleSet {
        private Category landuse;

        public Category getLanduse() {
            return landuse;
        }
        private List<DataKind> driverDataKinds = new ArrayList<>();
        private List<Sample> activeLanduseSamples = new ArrayList<>();
        private List<Sample> otherLanduseSamples = new ArrayList<>();
        private List<DataKind> highlyCorrelatedDriverDataKinds = new ArrayList<>();
        
        public List<Number> getAllSamplesForDriver(DataKind dk) {
            List<Number> result = new ArrayList<>();
            int driverIndex = driverDataKinds.indexOf(dk);
            if (driverIndex >= 0) {
                for (Sample sample: getAllSamples())
                    result.add(sample.driverValues[driverIndex]);
            }
            return result;
        }

        public List<DataKind> getDriverDataKinds() {
            return driverDataKinds;
        }

        public List<Sample> getActiveLanduseSamples() {
            return activeLanduseSamples;
        }

        public List<Sample> getOtherLanduseSamples() {
            return otherLanduseSamples;
        }

        public List<DataKind> getHighlyCorrelatedDriverDataKinds() {
            return highlyCorrelatedDriverDataKinds;
        }
        
//        public Number[][] toArray() {
//            Number[][] result = new Number[1+ driverDataKinds.size()][getNumberOfSamples()];
//            for (int i = 0; i < getActiveLanduseSamples().size(); i++)
//                result[0][i] = 1;
//            for (int i = getActiveLanduseSamples().size(); i < getNumberOfSamples(); i++)
//                result[0][i] = 0;
//            
//            int index = 1;
//            for (DataKind dk : driverDataKinds) {
//                List<Number> driverValues = getAllSamplesForDriver(dk);
//                for (int i = 0; i < driverValues.size(); i++) {
//                    result[index][i] = driverValues.get(i);
//                }
//                index++;
//            }
//            
//            return result;
//        }
//        
//        public void exportToFile() {
//            Number[][] result = toArray();
//            File f = new File(String.format("%sSample_%s.csv", "D:\\UserData\\projecten\\CLUE_project\\Logfiles\\", landuse.getCaption()));
//            f.delete();
//            String msg;
//            try {
//                try (FileWriter writer = new FileWriter(f, true)) {
//                    for (int i = 0; i < 1 + driverDataKinds.size(); i++) {
//                        if (i == 0)
//                            msg = landuse.getCaption();
//                        else
//                            msg = driverDataKinds.get(i-1).getCaption();
//                        
//                        for (int j = 0; j < getNumberOfSamples(); j++)
//                            msg = msg + "," + result[i][j];
//                        writer.append(msg+LogTarget.getNewLineSeparator());
//                    }
//                    //writer.flush();
//                }
//            } catch (IOException ex) {
//                throw new RuntimeException(ex.getMessage());
//            }
//        }
        
        /**
         * create new list containing all samples
         * @return 
         */
        public List<Sample> getAllSamples() {
            List<Sample> result = new ArrayList<>();
            result.addAll(activeLanduseSamples);
            result.addAll(otherLanduseSamples);
            return result;
        }
        
        /**
         * @return total number of samples
         */
        public int getNumberOfSamples() {
            return activeLanduseSamples.size() + otherLanduseSamples.size();
        }
        
        public int getNumberOfQuantitativeDrivers() {
            int result = 0;
            for (DataKind dk : this.driverDataKinds) {
                if (dk.getType() == QUANTITATIVE) {
                    result++;
                }
            }
            return result;
        }
        
        /**
         * returns string array with caption of all quantitative drivers 
         * with landuse as first element. 
         * @return
         */
        public String[] getAllQuantitativeDrivers() {
            String[] result = new String[1 + getNumberOfQuantitativeDrivers() ];
            
            result[0] = landuse.getCaption();
            for (DataKind dk : this.driverDataKinds) {
                if (dk.getType() == QUANTITATIVE) {
                    result[getIndexOfQuantitativeDriver(dk) + 1] = dk.getCaption();
                }
           }            

            return result;
        }
        
        public int getIndexOfQuantitativeDriver(DataKind datakind){
            int result = 0;
            for (int index = 0; index < this.driverDataKinds.size(); index++)
            {
                DataKind dk = this.driverDataKinds.get(index);
                if (datakind == dk)
                    return result;

                if (dk.getType() == QUANTITATIVE) {
                    result++;
                }
            }
            return -1;
        }
        
                         
//
// implemeted to remove highly correlated drivers. However not used (so far).
//        
        public SampleSet removeDrivers(List<DataKind> removeDrivers) 
        {
            SampleSet result = new SampleSet();
            
            result.landuse = this.landuse;
            result.driverDataKinds = new ArrayList<>();
            result.activeLanduseSamples = new ArrayList<>();
            result.otherLanduseSamples = new ArrayList<>();
                                  
            for (int index = 0; index < this.driverDataKinds.size(); index++)
            {
                DataKind dk = this.driverDataKinds.get(index);
                if (removeDrivers.indexOf(dk) < 0) 
                    result.driverDataKinds.add(dk);
            }    
            
            int nrOfDriverValues = result.driverDataKinds.size();
            for (Sample sample : this.activeLanduseSamples)
            {
                Number[] new_driverValues = new Number[nrOfDriverValues]; 
                List<Number> tmp = new ArrayList();
                
                for (int index = 0; index < this.driverDataKinds.size(); index++)
                {
                    DataKind dk = this.driverDataKinds.get(index);
                    if (removeDrivers.indexOf(dk) < 0) 
                        tmp.add(sample.driverValues[index]);            
                }
                if (tmp.size() != nrOfDriverValues) 
                    throw new RuntimeException(String.format(ERROR_INVALID_NROFVALUES, this.landuse.getCaption()));
                
                tmp.toArray(new_driverValues);
                result.activeLanduseSamples.add(new Sample(new_driverValues));              
            }
            
            for (Sample sample : this.otherLanduseSamples)
            {
                Number[] new_driverValues = new Number[nrOfDriverValues]; 
                List<Number> tmp = new ArrayList();
                
                for (int index = 0; index < this.driverDataKinds.size(); index++)
                {
                    DataKind dk = this.driverDataKinds.get(index);
                    if (removeDrivers.indexOf(dk) < 0) 
                        tmp.add(sample.driverValues[index]);            
                }
                if (tmp.size() != nrOfDriverValues) 
                    throw new RuntimeException(String.format(ERROR_INVALID_NROFVALUES, this.landuse.getCaption()));
                
                tmp.toArray(new_driverValues);
                result.otherLanduseSamples.add(new Sample(new_driverValues));              
            }

            return result;
        }
        
        public String saveToCsvFile() {
            String filename = String.format(EXPORTFILE_NAME, getLanduse().getCaption());
            File exportFile = new File(filename);
            
            String row = "";
            // header
            List<DataKind> drivers = getDriverDataKinds();
            for (int index = 0; index < drivers.size(); index++) {
                if (index == 0)
                    row = "landuseOfInterest ,"+drivers.get(index).getCaption();
                else
                    row += "," + drivers.get(index).getCaption();
            }
            StringToFile(exportFile, row);
            
            // all sample data
            for (Sample sample: getActiveLanduseSamples()) {
                for (int index = 0; index<drivers.size(); index++) {
                    Number driverValue = sample.driverValues[index];
                    if (index == 0)
                       row = "isLanduseOfInterest ,"+driverValue.toString();
                    else
                       row += "," + driverValue.toString();
                }
                StringToFile(exportFile, row);
            }
            for (Sample sample: getOtherLanduseSamples()) {
                for (int index = 0; index<drivers.size(); index++) {
                    Number driverValue = sample.driverValues[index];
                    if (index == 0)
                       row = "otherLanduse ,"+driverValue.toString();
                    else
                       row += "," + driverValue.toString();
                }
                StringToFile(exportFile, row);
            }
            
            return filename;
        }
        
        private void StringToFile(File aFile, String msg) {
        try {
                try (FileWriter writer = new FileWriter(aFile, true)) {
                    writer.append(msg+LogTarget.getNewLineSeparator());
                    //writer.flush();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        
    }
    
}

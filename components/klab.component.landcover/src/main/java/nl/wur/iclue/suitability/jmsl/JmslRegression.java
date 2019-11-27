///*
// * Copyright 2015 Alterra, Wageningen UR
// * 
// * Licensed under the EUPL, Version 1.1 or – as soon they
// * will be approved by the European Commission - subsequent
// * versions of the EUPL (the "Licence");
// * You may not use this work except in compliance with the
// * Licence.
// * You may obtain a copy of the Licence at:
// * 
// * http://ec.europa.eu/idabc/eupl5
// * 
// * Unless required by applicable law or agreed to in
// * writing, software distributed under the Licence is
// * distributed on an "AS IS" basis,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// * express or implied.
// * See the Licence for the specific language governing
// * permissions and limitations under the Licence.
// */
//package nl.wur.iclue.suitability.jmsl;
//
//import com.imsl.stat.Covariances;
//import com.imsl.stat.RegressorsForGLM;
//import com.imsl.stat.StepwiseRegression;
//import static com.imsl.stat.StepwiseRegression.BACKWARD_REGRESSION;
//import static com.imsl.stat.StepwiseRegression.FORWARD_REGRESSION;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import nl.wur.iclue.model.ui.CorrelationResults;
//import nl.wur.iclue.parameter.Landuses;
//import nl.wur.iclue.parameter.Landuses.Landuse;
//import nl.wur.iclue.parameter.SpatialDataset;
//import nl.wur.iclue.suitability.SuitabilityCalculator;
//import static nl.wur.iclue.suitability.SuitabilityCalculator.LOG_TOKEN;
//import nl.wur.iclue.suitability.function.SuitabilityFunction;
//import nl.wur.iclue.suitability.sampling.Sampler;
//import nl.alterra.shared.datakind.Category;
//import nl.alterra.shared.datakind.Clazz;
//import nl.alterra.shared.datakind.DataKind;
//import static nl.alterra.shared.datakind.IDataKind.Type.QUANTITATIVE;
//import nl.alterra.shared.rasterdata.CellStack;
//import nl.alterra.shared.rasterdata.RasterDataFactory;
//import nl.alterra.shared.rasterdata.RasterDataStack;
//import nl.alterra.shared.utils.FileUtils;
//import nl.alterra.shared.utils.log.Level;
//import nl.alterra.shared.utils.log.Log;
//import nl.alterra.shared.utils.log.LogTarget;
//
///**
// *
// * @author Johnny te Roller
// */
//public class JmslRegression extends SuitabilityCalculator{
//    private static final String STEPWISE_REGRESSION_EXPORT_FILENAME = "suitabilityFunction.prop";
//    public static final String STEPWISE_REGRESSION_CORRELATION_THRESHOLD = "CorrelationThreshold";
//    public static final String STEPWISE_REGRESSION_SAMPLE_AMOUNT_AS_PERCENTAGE_OF_TOTAL_AREA_ = "SampleSize";
//    private static final double DEFAULT_CORRELATION_THRESHOLD = 0.8; // when correlation is >.8 or <-.8 then parameter with the lowest correlation with landuse will be removed
//    private static final int DEFAULT_SAMPLE_COUNT_AS_PERCENTAGE_OF_TOTAL_AREA = 10;
//    private static final String LOGFILE_LANDUSE_HEADER = "***************** %s *****************\n";
//    private static final String SAMPLES_SAVED_AT = "Samples saved at '%s'";
//    
//    private static final String ERROR_CORRELATIONS = "Correlations halted because of the following exception: %s";
//    private static final String ERROR_UNSUPPORTED_DATAKIND = "Cannot do SPSS regression based on unsupported datakind. Only qualitative and quantitative data is supported";
//    private static final String ERROR_READ_REGRESSION_RESULTS_UNSUPPORTED_DATATYPE = "Cannot read data from regression results for unsupported level of measurement. Only qualitative and quantitative variables are supported.";
//    private static final String ERROR_LOG = "Error logging regression function";
//    private static final String ERROR_REGRESSION_NOT_POSSIBLE_FOR_ALL_LANDUSES = "Cannot perform regression. There are landuses defined without cells.";
//
//    private static final String MSG_CORRELATED_DRIVERS_FOR_LANDUSE = "Correlated drivers for landuse %s:";
//    private static final String MSG_CORRELATIONS = "(first driver is not used to find regression function because it is highest correlated to the landuse)";    
//    private static final String MSG_CORRELATED_WITH_LANDUSE = "Driver %s is highly correlated with landuse type %s and will not be used to find regression function.";
//    private static final String MSG_LOG_CORRELATED_DRIVERS = " - %s, %s -> %5.3f";
//
//    private final Map<String, String> suitabilityParams;
//    private final List<SpatialDataset> drivers;
//    private final Map<Category, SuitabilityFunction> landuseFunctions = new HashMap<>();
//    private final CorrelationResults correlationReportForm = new CorrelationResults();
//    private Clazz currentAdministrativeUnit = null;
//
//    public JmslRegression(List<DataKind> driverDatakinds, 
//            Landuses landuses,
//            List<SpatialDataset> drivers, 
//            Map<String, String> suitabilityParams) {
//        super(driverDatakinds, landuses);
//        this.suitabilityParams = suitabilityParams;
//        this.drivers = drivers;
//}
//
//    @Override
//    public void updateFromBaseline(SpatialDataset baseline, Clazz adminstrativeUnit) {
//        currentAdministrativeUnit = adminstrativeUnit;
//        int year = baseline.getYear();
//        RasterDataStack mapStack = RasterDataFactory.createStack(baseline.getRasterData());
//        mapStack.addInput(baseline.getRasterData()); 
//        for (SpatialDataset ds : drivers)
//            mapStack.addInput(ds.getMostRecentRasterData(year)); 
//
//        // get the number of cells without noData
//        Long celCount = mapStack.getNumberOfCellsWithData();                                
//        // get number of samples
//        double nrOfSamples = celCount * getSampleCountAsPercentageOfTotalArea(suitabilityParams) * 0.01; 
//        
//        Sampler.setLogToken(LOG_TOKEN);
//        
//        Log.log(Level.TRACE, String.format("CelCount = %d" , celCount), null, LOG_TOKEN);
//        Log.log(Level.TRACE, String.format("nrOfSamples = %10.2f" , nrOfSamples), null, LOG_TOKEN);
//        Log.log(Level.TRACE, "", null, LOG_TOKEN);
//
//        if (!isRegressionPossibleForAllNonStaticLanduses(getLanduses(), baseline.getRasterData().createValueCountTable()))
//            throw new RuntimeException(ERROR_REGRESSION_NOT_POSSIBLE_FOR_ALL_LANDUSES);
//        
//        // create regression functions
//        for (Landuses.Landuse landuse: getLanduses())
//            if (landuse.canChange()) {
//                Log.log(Level.TRACE, 
//                        String.format(LOGFILE_LANDUSE_HEADER, landuse.getCaption()),
//                                null, LOG_TOKEN);
//                
//                Sampler.SampleSet sampleSet = Sampler.createSamplesForLanduse((Category)landuse.getCategory(), mapStack, drivers, celCount, nrOfSamples);
//                Log.log(Level.TRACE,
//                        String.format(SAMPLES_SAVED_AT, sampleSet.saveToCsvFile()),
//                        null, LOG_TOKEN);
//                markHighCorrelatedDrivers(sampleSet, getCorrelationTreshhold(suitabilityParams)); 
//                createFunctionsByStepwiseRegression(sampleSet, landuseFunctions);
//                
//                Log.log(Level.TRACE, "", null, LOG_TOKEN); // empty aempty line for log readability
//            }       
//        
//    }
//    
//    private boolean isRegressionPossibleForAllNonStaticLanduses(Landuses landuses, Map<Integer, Integer> vat) {
//        for (Landuse lu: landuses)
//            if ((lu.canChange()) && (!vat.containsKey(lu.getCode())))
//                return false;
//        return true;
//    }
//    
//    @Override
//    public double getProbability(Landuses.Landuse landuseOfInterest, CellStack driverValues) {
//        SuitabilityFunction function = landuseFunctions.get(landuseOfInterest.getCategory());
//        return function.getProbability(getQuantitativeDriverValues(driverValues.inputValues), getQualitativeDriverValues(driverValues.inputValues));
//    }
//    
//
//    private double getSampleCountAsPercentageOfTotalArea(Map<String, String> suitabilityParams) {
//        if (suitabilityParams.containsKey(STEPWISE_REGRESSION_SAMPLE_AMOUNT_AS_PERCENTAGE_OF_TOTAL_AREA_))
//            return Double.parseDouble(suitabilityParams.get(STEPWISE_REGRESSION_SAMPLE_AMOUNT_AS_PERCENTAGE_OF_TOTAL_AREA_));
//        else
//            return DEFAULT_SAMPLE_COUNT_AS_PERCENTAGE_OF_TOTAL_AREA;
//    }
//    
//    private double getCorrelationTreshhold(Map<String, String> suitabilityParams) {
//        if (suitabilityParams.containsKey(STEPWISE_REGRESSION_CORRELATION_THRESHOLD))
//            return Double.parseDouble(suitabilityParams.get(STEPWISE_REGRESSION_CORRELATION_THRESHOLD));
//        else
//            return DEFAULT_CORRELATION_THRESHOLD;
//    }
//
//    private String getExportFileName() {       
//        return  FileUtils.getTempDir() + STEPWISE_REGRESSION_EXPORT_FILENAME;
//    }
//
//    private void markHighCorrelatedDrivers(Sampler.SampleSet sampleSet, double correlationTreshhold) {
//        // open window to visualize correlation results per landuse type
//        correlationReportForm.setVisible(true);
//
//        int _nrOfSamples = sampleSet.getNumberOfSamples();
//        
//        // get values for all drivers for regression. Each categorie of a qualitative driver will be a seperate driver
//        double landuseAndQuantitativeDriverValuesArray[][] = new double[_nrOfSamples][1 + sampleSet.getNumberOfQuantitativeDrivers()];
//        
//        int valueIndex = 0;
//        // first add landuse values first half 1 and second half 0.
//        for (int i = 0; i < Math.round(_nrOfSamples/2); i++)
//            landuseAndQuantitativeDriverValuesArray[i][valueIndex] = 1;
//        
//        valueIndex++;
//        // use for loop with index to ensure order of datakinds
//        for (int index = 0; index < sampleSet.getDriverDataKinds().size(); index++) {
//            DataKind datakind = sampleSet.getDriverDataKinds().get(index);
//            List<Number> driverValues = sampleSet.getAllSamplesForDriver(datakind);
//            
//            switch (datakind.getType()) {
//                case QUALITATIVE: {
//                    // correlation is not calculated for qualitative drivers
//                    break;
//                }
//                case QUANTITATIVE: {
//                    for (int i = 0; i < driverValues.size(); i++)
//                        landuseAndQuantitativeDriverValuesArray[i][valueIndex] = driverValues.get(i).doubleValue();
//                    valueIndex++;
//                    break;
//                }
//                default:
//                    throw new RuntimeException(ERROR_UNSUPPORTED_DATAKIND);
//            }
//        }
//
//        log(String.format(MSG_CORRELATED_DRIVERS_FOR_LANDUSE, sampleSet.getLanduse().getCaption()));
//        log(MSG_CORRELATIONS);
//        
//        Covariances co = new Covariances(landuseAndQuantitativeDriverValuesArray);
//        double[][] corValues;
//        try {
//            corValues = co.compute(Covariances.CORRELATION_MATRIX);
//            correlationReportForm.addTab(sampleSet.getLanduse().getCaption(), sampleSet.getAllQuantitativeDrivers(), corValues, correlationTreshhold);
//            readOutputAndMarkHighlyCorrelatedDrivers(sampleSet, corValues, correlationTreshhold);
//        } catch (Covariances.NonnegativeFreqException | Covariances.NonnegativeWeightException | Covariances.TooManyObsDeletedException | Covariances.MoreObsDelThanEnteredException | Covariances.DiffObsDeletedException ex) {
//            throw new RuntimeException(String.format(ERROR_CORRELATIONS, ex.getMessage()), ex);
//        }        
//    }
//    
//    private void readOutputAndMarkHighlyCorrelatedDrivers(Sampler.SampleSet sampleSet, double[][] correlationValues, double correlationThreshold)
//    {
//        for (DataKind dk1 : sampleSet.getDriverDataKinds()) 
//        {                          
//            // correlation nog possible for string variables. Qualitative drivers are defined as string variables.
//            if (dk1.getType() != QUANTITATIVE) continue;
//            
//            // no need to check when driver is already marked as highlycorrelated
//            if (sampleSet.getHighlyCorrelatedDriverDataKinds().indexOf(dk1) >= 0) continue;
//                
//            int index1 = sampleSet.getIndexOfQuantitativeDriver(dk1) + 1;
//            // first check correlation with landuse
//            if (Math.abs(correlationValues[0][index1]) > correlationThreshold)
//            {
//                sampleSet.getHighlyCorrelatedDriverDataKinds().add(dk1);
//                log(String.format(MSG_CORRELATED_WITH_LANDUSE, dk1.getCaption(), sampleSet.getLanduse().getCaption()));
//            }
//            else 
//                for (DataKind dk2 : sampleSet.getDriverDataKinds()) 
//                {
//                    // if dk2 is already marked so no further checks reguired
//                    if ((dk2.getType() != QUANTITATIVE) || (sampleSet.getHighlyCorrelatedDriverDataKinds().indexOf(dk2) >= 0))
//                        continue;
//
//                    int index2 = sampleSet.getIndexOfQuantitativeDriver(dk2) + 1;
//                    if ((!dk1.getCaption().equals(dk2.getCaption())) && ((Math.abs(correlationValues[index1][index2]) > correlationThreshold)))
//                    {
//                        double corrLanduse1 = Math.abs(correlationValues[0][index1]);    
//                        double corrLanduse2 = Math.abs(correlationValues[0][index2]);
//                        if (corrLanduse1 <= corrLanduse2)
//                        {
//                            sampleSet.getHighlyCorrelatedDriverDataKinds().add(dk1);
//                            log(String.format(MSG_LOG_CORRELATED_DRIVERS,  
//                                    dk1.getCaption(),  
//                                    dk2.getCaption(), 
//                                    correlationValues[index1][index2]));
//                            break; // no need to check correlation with rest of the drivers
//                        }
//                        else
//                        {
//                            sampleSet.getHighlyCorrelatedDriverDataKinds().add(dk2);
//                            log(String.format(MSG_LOG_CORRELATED_DRIVERS,  
//                                    dk2.getCaption(),  
//                                    dk1.getCaption(), 
//                                    correlationValues[index1][index2]));
//                        }
//                    }          
//                }      
//        }
//        log(""); // add empty line for log readability
//    }
//    private void createFunctionsByStepwiseRegression(Sampler.SampleSet sampleSet, Map<Category, SuitabilityFunction> landuseFunctions) {
//        int _nrOfSamples = sampleSet.getNumberOfSamples();
//        int _nrOfQualitativeDrivers = 0;
//        int _nrOfQuantitativeDrivers = 0;
//        
//        Map<String, List<Number>> QualitativeDriverCategorieValuesInSample = new HashMap<>();
//        
//        // get total number of drivers for regression. Each categorie of a qualitative driver will be a seperate driver
//        for (DataKind datakind: sampleSet.getDriverDataKinds()) {      
//            
//            List<Number> driverValues = sampleSet.getAllSamplesForDriver(datakind);
//            
//            switch (datakind.getType()) {
//                case QUALITATIVE: {
//                    List driverCategorieValuesInSample = new ArrayList<>();
//                    
//                    double driverValuesArray[][] = new double[_nrOfSamples][1];
//                    for (int i = 0; i < driverValues.size(); i++) {
//                        double val = driverValues.get(i).doubleValue();
//                        driverValuesArray[i][0] = val;
//                        if (!driverCategorieValuesInSample.contains(val))
//                            driverCategorieValuesInSample.add(val);
//                    }
//                    _nrOfQualitativeDrivers += new RegressorsForGLM(driverValuesArray, 1).getNumberOfRegressors();
//                    Collections.sort(driverCategorieValuesInSample);
//                    QualitativeDriverCategorieValuesInSample.put(datakind.getCaption(), driverCategorieValuesInSample);
//                    break;
//                }
//                case QUANTITATIVE: {
//                    if (sampleSet.getHighlyCorrelatedDriverDataKinds().indexOf(datakind) < 0)
//                       _nrOfQuantitativeDrivers++;
//                    break;
//                }
//                default:
//                    throw new RuntimeException(ERROR_UNSUPPORTED_DATAKIND);
//            }
//        }
//        
//        // get values for all drivers for regression. Each categorie of a qualitative driver will be a seperate driver
//        double allDriverValuesArray[][] = new double[_nrOfSamples][_nrOfQuantitativeDrivers + _nrOfQualitativeDrivers];
//        int valueIndex = 0;
//
//        // use for loop with index to ensure order of datakinds
//        for (int index = 0; index < sampleSet.getDriverDataKinds().size(); index++) {
//            DataKind datakind = sampleSet.getDriverDataKinds().get(index);
//            List<Number> driverValues = sampleSet.getAllSamplesForDriver(datakind);
//            
//            switch (datakind.getType()) {
//                case QUALITATIVE: {
//                    double qualitativeDriverValues[][] =  new double[_nrOfSamples][1];
//                    for (int i = 0; i < driverValues.size(); i++)
//                        qualitativeDriverValues[i][0] = driverValues.get(i).doubleValue();
//                    
//                    RegressorsForGLM r = new RegressorsForGLM(qualitativeDriverValues, 1);
//                    double regressors[][] = r.getRegressors(); 
//                    for (int i = 0; i < r.getNumberOfRegressors(); i++) {
//                        for (int j = 0; j < _nrOfSamples; j++) {
//                            allDriverValuesArray[j][valueIndex] = regressors[j][i];
//                        }
//                        valueIndex++;
//                    }
//                    break;
//                }
//                case QUANTITATIVE: {
//                    if (sampleSet.getHighlyCorrelatedDriverDataKinds().indexOf(datakind) < 0) {
//                        for (int i = 0; i < driverValues.size(); i++)
//                            allDriverValuesArray[i][valueIndex] = driverValues.get(i).doubleValue();
//                        valueIndex++;
//                    }
//                    break;
//                }
//                default:
//                    throw new RuntimeException(ERROR_UNSUPPORTED_DATAKIND);
//            }
//        }
//        
//        // value of landuse. First half is 1, second half is 0, acoording to the sampling.
//        double y[] = new double[_nrOfSamples];
//        for (int i = 0; i < Math.round(_nrOfSamples/2); i++)
//            y[i] = 1;
//        
//        // do the regression
//        try {
//            // do forward regression
//            StepwiseRegression stepwiseRegression = new StepwiseRegression(allDriverValuesArray, y);
//            stepwiseRegression.setMethod(FORWARD_REGRESSION);
//            stepwiseRegression.compute();
//            // create suitability function
//            SuitabilityFunction function_forward = buildFunctionFromResults(stepwiseRegression.getHistory(), 
//                                                                            stepwiseRegression.getCoefficientTTests(), 
//                                                                            stepwiseRegression.getIntercept(), 
//                                                                            sampleSet, QualitativeDriverCategorieValuesInSample);
//            
//            // do also backward regression for comparison
//            StepwiseRegression stepwiseRegression_backward = new StepwiseRegression(allDriverValuesArray, y);
//            stepwiseRegression_backward.setMethod(BACKWARD_REGRESSION);
//            stepwiseRegression_backward.compute();
//            // create suitability function
//            SuitabilityFunction function_backward = buildFunctionFromResults(stepwiseRegression_backward.getHistory(), 
//                                                                             stepwiseRegression_backward.getCoefficientTTests(), 
//                                                                             stepwiseRegression_backward.getIntercept(), 
//                                                                             sampleSet, QualitativeDriverCategorieValuesInSample);
//
//            // only use and save function from forward regression
//            landuseFunctions.put(sampleSet.getLanduse(), function_forward);
//            logRegressionFunction(sampleSet.getLanduse(), function_forward, function_backward, sampleSet.getDriverDataKinds());            
//            exportRegressionFunction(sampleSet.getLanduse(), function_forward, sampleSet.getDriverDataKinds());            
//            
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }              
//    }
//       
//    private void log(String msg) {
//        Log.log(null, msg, null, LOG_TOKEN);
//    }
//  
//    private String getQuantitativeDriverCoefficientsAsString(SuitabilityFunction function, int driverIndex) {
//        Double coef = function.getQuantitativeDriverCoefficient(driverIndex);
//        if ((coef != null) && (!coef.isNaN())) 
//            return String.format("%11.8f", coef);
//        else
//            return "       n.a.";
//    }
//    
//    private String getQuantitativeDriverPvalue(SuitabilityFunction function, int driverIndex) {
//        String metaInfo = function.getQuantitativeDriverMetaInfo(driverIndex);
//        if (!metaInfo.isEmpty()) {
//            Double pValue = Double.parseDouble(metaInfo);
//            return String.format("%11.8f", pValue);
//        }       
//        else
//            return "       n.a.";
//    }
//    
//    private String getQualitativeDriverCategoriesCoeffientAsString(Map<Category, Double> qualitativeDriverCategories, Category clz) {
//        if (qualitativeDriverCategories.containsKey(clz)) 
//            return String.format("%11.8f", qualitativeDriverCategories.get(clz), clz.getCaption());
//        else 
//            return "       n.a.";
//    }
//    
//    private String getQualitativeDriverCategoriesPvalue(Map<Category, String> qualitativeDriverCategoriesMetaInfo, Category clz) {
//        if (qualitativeDriverCategoriesMetaInfo.containsKey(clz)) {
//            String metaInfo = qualitativeDriverCategoriesMetaInfo.get(clz);
//            Double pValue = Double.parseDouble(metaInfo);
//            return String.format("%11.8f", pValue);
//        }
//        else 
//            return "       n.a.";
//    }
//    
//    private void logRegressionFunction(Category landuse, SuitabilityFunction function_forward, SuitabilityFunction function_backward, List<DataKind> dataKinds) {
//        log(String.format("Regressionfunction %s:", landuse.getCaption()));
//        log("   coef_forward coef_backward   pvalue_fwd pvalue_bwd  drivername");
//        
//        log(String.format("    %11.8f (%11.8f)                          %s", function_forward.getConstantFactor(), function_backward.getConstantFactor(),"Constant"));
//        
//        int indexOfQuantitativeDriver = 0;
//        for (DataKind dk: dataKinds) {
//            switch (dk.getType()) {
//                case QUANTITATIVE: {
//                    String coef_forward  = getQuantitativeDriverCoefficientsAsString(function_forward, indexOfQuantitativeDriver);
//                    String coef_backward = getQuantitativeDriverCoefficientsAsString(function_backward, indexOfQuantitativeDriver);
//                    String pvalue_forward  = getQuantitativeDriverPvalue(function_forward, indexOfQuantitativeDriver);
//                    String pvalue_backward = getQuantitativeDriverPvalue(function_backward, indexOfQuantitativeDriver).trim();
//                    log(String.format("    %11s (%11s) %11s (%10s) %s", coef_forward, coef_backward, pvalue_forward, pvalue_backward, dk.getCaption()));
//
//                    indexOfQuantitativeDriver++;
//                    break;
//                }
//                case QUALITATIVE: {
//                    log(String.format("%54s %s", "", dk.getCaption()));
//                    for (Clazz clz: dk.getClasses()) {
//                        String coef_forward  = getQualitativeDriverCategoriesCoeffientAsString(function_forward.getQualitativeDriverCategories(), (Category)clz);
//                        String coef_backward = getQualitativeDriverCategoriesCoeffientAsString(function_backward.getQualitativeDriverCategories(), (Category)clz);
//                        String pvalue_forward  = getQualitativeDriverCategoriesPvalue(function_forward.getQualitativeDriverCategoriesMetaInfo(), (Category)clz);
//                        String pvalue_backward = getQualitativeDriverCategoriesPvalue(function_backward.getQualitativeDriverCategoriesMetaInfo(), (Category)clz).trim();
//                        log(String.format("    %11s (%11s) %11s (%10s)  -> %s", coef_forward, coef_backward, pvalue_forward, pvalue_backward, clz.getCaption()));    
//                    }
//                    break;
//                }
//                default: throw new RuntimeException(ERROR_LOG);
//            }
//        }
//        
//        log(""); // ad empty line for log readability
//    }
//
//  
//    private Clazz getClazzOfCategorieValue(Long val, List<Clazz> classes) {
//       for (int i = 0; i < classes.size(); i++) {
//           Clazz c = classes.get(i);
//           if (c.getValueAsString().equals(val.toString()))
//               return c;
//       }
//       return null;
//    }
//    
//    private SuitabilityFunction buildFunctionFromResults( 
//                                          double[] history,  
//                                          StepwiseRegression.CoefficientTTests coefficientTTests, 
//                                          double intercept, 
//                                          Sampler.SampleSet sampleSet,
//                                          Map<String, List<Number>> qualitativeDriverCategorieValuesInSample)  throws Exception{
//        Double[]quantitativeDriverCoefficients = new Double[sampleSet.getNumberOfQuantitativeDrivers()];
//        String[] quantitativeDriverMetaInfo = new String[sampleSet.getNumberOfQuantitativeDrivers()];
//
//        Map<Category, Double> qualitativeDriverCategories = new HashMap<>();
//        Map<Category, String> qualitativeDriverCategoriesMetaInfo = new HashMap<>();
//        
//        int quantitativeDriverCoefficientsIndex = 0;
//        int jmslCoefIndex = 0;
//        
//        for (int index = 0; index < sampleSet.getDriverDataKinds().size(); index++) {
//            DataKind datakind = sampleSet.getDriverDataKinds().get(index);
//            switch (datakind.getType()) {
//                case QUANTITATIVE: {
//                    // highly correlated driver are not part of regression function_forward!
//                    if (sampleSet.getHighlyCorrelatedDriverDataKinds().indexOf(datakind) <  0) {
//                        if (history[jmslCoefIndex] > 0.0) {
//                            Double coef = coefficientTTests.getCoefficient(jmslCoefIndex);
//                            Double pValue = coefficientTTests.getPValue(jmslCoefIndex);
//                            quantitativeDriverCoefficients[quantitativeDriverCoefficientsIndex] = coef;
//                            quantitativeDriverMetaInfo[quantitativeDriverCoefficientsIndex++] = pValue.toString();
//                        }
//                        else {
//                            quantitativeDriverCoefficients[quantitativeDriverCoefficientsIndex] = null;
//                            quantitativeDriverMetaInfo[quantitativeDriverCoefficientsIndex++] = "";
//                        }
//                        jmslCoefIndex++;
//                    }        
//                    else {
//                        quantitativeDriverCoefficients[quantitativeDriverCoefficientsIndex] = null;
//                        quantitativeDriverMetaInfo[quantitativeDriverCoefficientsIndex++] = "";
//                    }
//                    break;
//                }
//                case QUALITATIVE: {
//                    List<Number> valueList = qualitativeDriverCategorieValuesInSample.get(datakind.getCaption());
//                    for (int i = 0; i < valueList.size(); i++) {
//                        Double coeficient = coefficientTTests.getCoefficient(jmslCoefIndex);
//                        Double pValue = coefficientTTests.getPValue(jmslCoefIndex);
//                        if (history[jmslCoefIndex] > 0.0) {
//                            Long val = Math.round((Double) valueList.get(i));                       
//                            Clazz category = getClazzOfCategorieValue(val, datakind.getClasses());
//                            if (category != null) {
//                                qualitativeDriverCategories.put((Category)category, coeficient);
//                                qualitativeDriverCategoriesMetaInfo.put((Category)category,  pValue.toString());
//                            }
//                        }
//                        jmslCoefIndex++;
//                    }
//                    break;
//                }
//                default: throw new RuntimeException(ERROR_READ_REGRESSION_RESULTS_UNSUPPORTED_DATATYPE);
//            }          
//        }
//        
//        SuitabilityFunction suitabilityFunction = new SuitabilityFunction(intercept, quantitativeDriverCoefficients, qualitativeDriverCategories);
//        suitabilityFunction.setQuantitativeDriverMetaInfo(quantitativeDriverMetaInfo);
//        return suitabilityFunction;
//    }
//
//    private void exportRegressionFunction(Category landuse, SuitabilityFunction function, List<DataKind> driverDataKinds) {
//        boolean APPEND = true;
//        String KEY_SUITABILITY_REGRESSION = "Suitability.FunctionDictionary";
//        String KEY_FUNCTIONCONSTANT       = "FunctionConstant";
//        String KEY_FUNCTIONCOEFFICIENT    = "FunctionCoefficient";
//        
//        File ExportFile = new File(getExportFileName());
//        try {
//            try (FileWriter writer = new FileWriter(ExportFile, APPEND)) {        
//                String msg = String.format("%s.%s.%s.%s=%.8f",  KEY_SUITABILITY_REGRESSION, 
//                                                                currentAdministrativeUnit.getCaption(), 
//                                                                landuse.getCaption(), 
//                                                                KEY_FUNCTIONCONSTANT, 
//                                                                function.getConstantFactor());               
//                writer.append(msg + LogTarget.getNewLineSeparator());
//                int indexOfQuantitativeDriver = 0;
//                for (DataKind dk: driverDataKinds) {
//                    switch (dk.getType()) {
//                        case QUANTITATIVE: {
//                            Double coef = function.getQuantitativeDriverCoefficient(indexOfQuantitativeDriver);
//                            if ((coef != null) && (!coef.isNaN())) {
//                                msg = String.format("%s.%s.%s.%s.%s=%.8f", KEY_SUITABILITY_REGRESSION, 
//                                                                           currentAdministrativeUnit.getCaption(),
//                                                                           landuse.getCaption(), 
//                                                                           KEY_FUNCTIONCOEFFICIENT,
//                                                                           dk.getCaption(),
//                                                                           coef);
//                                writer.append(msg + LogTarget.getNewLineSeparator());
//                            }
//                            indexOfQuantitativeDriver++;
//                            break;
//                        }
//                        case QUALITATIVE: {
//                            for (Clazz clz: dk.getClasses()) {
//                                if (function.getQualitativeDriverCategories().containsKey((Category)clz)) {
//                                    Double coef = function.getQualitativeDriverCategories().get((Category)clz);
//                                
//                                    msg = String.format("%s.%s.%s.%s.%s.class.%s=%.8f", KEY_SUITABILITY_REGRESSION, 
//                                                                                        currentAdministrativeUnit.getCaption(),
//                                                                                        landuse.getCaption(), 
//                                                                                        KEY_FUNCTIONCOEFFICIENT,
//                                                                                        dk.getCaption(),
//                                                                                        clz.getCaption(),
//                                                                                        coef);
//                                    writer.append(msg + LogTarget.getNewLineSeparator());
//                                }
//                            }
//                            break;
//                        }
//                        default: throw new RuntimeException(ERROR_LOG);
//                    }
//                }
//                writer.append(LogTarget.getNewLineSeparator());
//            }
//        } catch (IOException ex) {
//            throw new RuntimeException(ex.getMessage());
//        }      
//    }
//    
//}

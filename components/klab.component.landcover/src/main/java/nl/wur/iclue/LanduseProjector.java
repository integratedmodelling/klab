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

package nl.wur.iclue;

import java.io.File;
import java.io.IOException;
import nl.wur.iclue.model.CLUEModel;
import nl.wur.iclue.model.CLUEModel.Projections;
import nl.wur.iclue.model.probability.Probabilities;
import nl.wur.iclue.parameter.Parameters;
import nl.wur.iclue.persist.properties.ParameterBuilder;
import nl.wur.iclue.persist.properties.PropertiesFile;
import nl.wur.iclue.suitability.SuitabilityCalculator;
import nl.alterra.shared.utils.FileUtils;
import nl.alterra.shared.utils.log.FileLog;
import nl.alterra.shared.utils.log.Log;

/**
 *
 * @author Peter Verweij, Johnny te Roller
 */
public class LanduseProjector {

	private static final String logSuitabilityFile = "logStatistics.txt";
	private static final String logProbabilitiesFile = "logDemandWeights.csv";
	private static final String logIterationsFile = "logIterations.txt";

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		if ((args == null) || (args.length < 1)) {
			System.out.println("LanduseProjector: Cannot run. Require first argument refering to parameter file");
			return;
		}

		String paramFilename = args[0];
		if (!FileUtils.exists(paramFilename)) {
			System.out.println("LanduseProjector: Cannot run. Parameter file does not exist");
			return;
		}

		PropertiesFile paramFile;
		Parameters params = null;
		try {
			paramFile = PropertiesFile.createFromFile(paramFilename);
			params = ParameterBuilder.build(paramFile);
		} catch (IOException ex) {
			System.out.println("LanduseProjector: Cannot run. IO error building parameters");
			return;
		}

		// prepare output directory for file storage
		String outputRoot = getOutputRoot(paramFilename);
		File outputRootFile = new File(outputRoot);
		FileUtils.deleteDirectory(outputRootFile);
		if (!outputRootFile.mkdir()) {
			System.out.println("LanduseProjector: Cannot run. Cannot create output directory: " + outputRoot);
			return;
		}

		configureLogin();

		// run the model
		CLUEModel model = new CLUEModel(params, null);
		model.initializeSuitabilities();
		Projections projections = model.createLanduseProjections();

		System.out.println("Saving output maps...");
		if (saveResultMaps(outputRoot, params, projections))
			System.out.println("saved output to: " + outputRoot);
		else
			System.out.println("Saving output maps failed");
	}

	private static String getOutputRoot(String paramFileName) {
		File f = new File(paramFileName);
		File rootDir = f.getParentFile();

		return String.format("%s\\%s_output", rootDir.getAbsolutePath(), f.getName().replaceFirst("[.][^.]+$", ""));
	}

	private static boolean saveResultMaps(String outputRootDirectory, Parameters params, Projections projections) {
		// copy all projection ESRI maps
		String outputMapsDirectory = String.format("%s\\maps", outputRootDirectory);
		if (!new File(outputMapsDirectory).mkdir())
			return false;

//        for (int year = params.getBaseline().getYear(); year<= params.getTargetTime(); year++) {
//            if (!GridIo.gridCopy(projections.getLanduseProjection(year).getDataDefinition(), String.format("%s\\Landuse%d", outputMapsDirectory, year)))
//                return false;
//            if (!GridIo.gridCopy(projections.getAgeProjection(year).getDataDefinition(), String.format("%s\\Age%d", outputMapsDirectory, year)))
//                return false;
//        }

		return true;
	}

	private static void configureLogin() {
		Log.putTarget(SuitabilityCalculator.LOG_TOKEN, new FileLog());
		Log.putTarget(Probabilities.LOG_TOKEN, new FileLog(logProbabilitiesFile));
		Log.putTarget(CLUEModel.LOG_TOKEN, new FileLog(logIterationsFile));
	}

}

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

package nl.wur.iclue.suitability;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.landcover.clue.KLABSuitabilityCalculator;

//import nl.wur.iclue.suitability.jmsl.JmslRegression;
import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.datakind.Clazz;
import nl.alterra.shared.datakind.DataKind;
import nl.alterra.shared.rasterdata.CellStack;
import nl.alterra.shared.rasterdata.RasterData;
import nl.wur.iclue.parameter.Landuses;
import nl.wur.iclue.parameter.Landuses.Landuse;
import nl.wur.iclue.parameter.Parameters;
import nl.wur.iclue.parameter.SpatialDataset;
import nl.wur.iclue.parameter.SuitabilityParameters;
import nl.wur.iclue.suitability.function.SuitabilityFunction;
import nl.wur.iclue.suitability.functiondictionary.FunctionDictionary;
import nl.wur.iclue.suitability.maps.SuitabilityMaps;

/**
 *
 * @author Peter Verweij
 */
public class SuitabilityFactory {
	private static final String ERROR_UNKNOWN_METHOD = "Cannot create suitability calculator. Unknown method: %s";

	/*
	 * Previous code for STEPWISE_REGRESSION:
	 * 
	 * return new JmslRegression(driverDataKinds, landuses, params.getDrivers(),
	 * (Map<String, String>)suitabilityParams.getParameters())
	 * 
	 * Only compiles with commercial JMSL library, so screw it.
	 */

	@SuppressWarnings("unchecked")
	public static SuitabilityCalculator create(Parameters params) {
		// get all parameter datakinds
		List<DataKind> driverDataKinds = new ArrayList<>();
		for (SpatialDataset ds : params.getDrivers())
			driverDataKinds.add(ds.getDatakind());
		Landuses landuses = params.getLanduses();

		SuitabilityParameters suitabilityParams = params.getSuitabilityParameters();
		SuitabilityCalculationMethod method = suitabilityParams.getMethod();

		switch (method) {
		case CONSTANT:
			return new DefaultSuitabilityValue(driverDataKinds, landuses);
		case MAP:
			return new SuitabilityMaps(driverDataKinds, landuses,
					(Map<Landuse, RasterData>) suitabilityParams.getParameters());
		case BAYESIAN_STATISTICS:
			/* FV added: maps to any k.LAB resource, not just bayesian inference */
			return new KLABSuitabilityCalculator(driverDataKinds, landuses,
					(IResourceCalculator) suitabilityParams.getParameters());
		case FUNCTION_DICTIONARY:
			return new FunctionDictionary(driverDataKinds, landuses,
					(Map<String, Map<Category, SuitabilityFunction>>) suitabilityParams.getParameters());
		default:
			throw new RuntimeException(String.format(ERROR_UNKNOWN_METHOD, method.getCaption()));
		}

	}

	private static class DefaultSuitabilityValue extends SuitabilityCalculator {

		public DefaultSuitabilityValue(List<DataKind> driverDatakinds, Landuses landuses) {
			super(driverDatakinds, landuses);
		}

		@Override
		public double getProbability(Landuses.Landuse landuseOfInterest, CellStack driverValues) {
			return 0.8;
		}

		@Override
		public void updateFromBaseline(SpatialDataset baseline, Clazz adminstrativeUnit) {
			// void
		}

	}
}

package org.integratedmodelling.klab.components.geospace.services;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;

public class Space implements IExpression {

	private static Map<String, ISpace> shapeCache = new HashMap<>();

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {

		Shape shape = null;
		Double resolution = null;
		String urn = null;
		Projection projection = null;
		double simplifyFactor = Double.NaN;

		org.integratedmodelling.klab.components.geospace.extents.Space ret = null;

		if (parameters.containsKey("shape")) {
			shape = Shape.create(parameters.get("shape", String.class));
		}
		if (parameters.containsKey("grid")) {
			resolution = parseResolution(parameters.get("grid", String.class));
		}
		if (parameters.containsKey("urn")) {
			urn = parameters.get("urn", String.class);
		}
		if (parameters.containsKey("projection")) {
			projection = Projection.create(parameters.get("projection", String.class));
		}
		if (parameters.containsKey("simplify")) {
			simplifyFactor = parameters.get("simplify", Double.class);
		}

		if (shape != null) {
			if (resolution != null) {
				ret = org.integratedmodelling.klab.components.geospace.extents.Space.create(shape, resolution);
			} else {
				ret = org.integratedmodelling.klab.components.geospace.extents.Space.create(shape);
			}
			if (!Double.isNaN(simplifyFactor)) {
				ret.getShape().simplify(simplifyFactor);
			}
			
		} else if (urn != null) {

			ISpace space = shapeCache.get(urn);
			if (space == null) {
				Pair<IArtifact, IArtifact> artifact = Resources.INSTANCE.resolveResourceToArtifact(urn,
						context.getMonitor());
				if (artifact == null || artifact.getSecond().groupSize() < 1
						|| artifact.getSecond().getGeometry().getDimension(Type.SPACE) == null) {
					throw new IllegalArgumentException("urn " + urn + " does not resolve to a spatial object");
				}
				space = ((IScale) artifact.getSecond().iterator().next().getGeometry()).getSpace();
				shapeCache.put(urn, space);
			}

			if (projection != null) {
				space = space.getShape().transform(projection);
			}

			if (!Double.isNaN(simplifyFactor)) {
				((Shape) space.getShape()).simplify(simplifyFactor);
			}

			if (resolution == null) {
				return space;
			}

			ret = org.integratedmodelling.klab.components.geospace.extents.Space.create((Shape) space.getShape(),
					resolution);

		}

		return ret;
	}

	/**
	 * Parse a string like "1 km" and return the meters in it. Throw an exception if
	 * this cannot be parsed.
	 * 
	 * @param string
	 * @return the resolution in meters
	 * @throws KlabValidationException
	 */
	public static double parseResolution(String string) throws KlabValidationException {

		Pair<Double, String> pd = MiscUtilities.splitNumberFromString(string);

		if (pd.getFirst() == null || pd.getSecond() == null)
			throw new KlabValidationException("wrong resolution specification: " + string);

		IUnit uu = Units.INSTANCE.getUnit(pd.getSecond());
		IUnit mm = Units.INSTANCE.getUnit("m");
		return mm.convert(pd.getFirst().doubleValue(), uu).doubleValue();
	}

}

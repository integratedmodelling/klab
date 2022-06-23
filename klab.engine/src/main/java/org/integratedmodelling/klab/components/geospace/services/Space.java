package org.integratedmodelling.klab.components.geospace.services;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.IQuantity;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.mediation.Quantity;
import org.integratedmodelling.klab.components.geospace.extents.EnumeratedSpace;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

public class Space implements IExpression {

    private static Map<String, ISpace> shapeCache = new HashMap<>();

    @Override
    public Object eval(IContextualizationScope scope, Object...params) throws KlabException {

        Shape shape = null;
        Double resolution = null;
        String urn = null;
        Projection projection = null;
        double simplifyFactor = Double.NaN;
        boolean gridConstraint = false;

        Parameters<String> parameters = Parameters.create(params);
        org.integratedmodelling.klab.components.geospace.extents.Space ret = null;

        if (parameters.contains("identifier") || parameters.contains("authority")
                || parameters.contains("baseidentity")) {

            if (parameters.contains("identifier")) {
                IKimConcept concept = parameters.get("identifier", IKimConcept.class);
                if (concept != null) {
                    IConcept c = Concepts.INSTANCE.declare(concept);
                    if (c != null && c.is(IKimConcept.Type.IDENTITY)) {
                        return new EnumeratedSpace(c);
                    }
                }
                throw new KlabIllegalArgumentException(
                        "value of spatial identity unknown or illegal: " + parameters.get("identifier"));
            } else if (parameters.contains("authority")) {
                System.out.println("ZIBU");
            } else if (parameters.contains("baseidentity")) {
                System.out.println("ZOBU");
            }
        }

        if (parameters.containsKey("shape")) {
            shape = Shape.create(parameters.get("shape", String.class));
        }
        if (parameters.containsKey("grid")) {
            if (parameters.get("grid") != null) {
                resolution = parseResolution(parameters.get("grid"));
            } else {
                // it's a constraint
                gridConstraint = true;
            }
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
                ret = org.integratedmodelling.klab.components.geospace.extents.Space.create(shape,
                        resolution);
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
                        scope == null ? Klab.INSTANCE.getRootMonitor() : scope.getMonitor());
                if (artifact == null || artifact.getSecond().groupSize() < 1
                        || artifact.getSecond().getGeometry().getDimension(Type.SPACE) == null) {
                    throw new KlabIllegalArgumentException(
                            "urn " + urn + " does not resolve to a spatial object");
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

            ret = org.integratedmodelling.klab.components.geospace.extents.Space.create(
                    (Shape) space.getShape(),
                    resolution);

        }

        if (ret == null && gridConstraint) {
            ret = org.integratedmodelling.klab.components.geospace.extents.Space.constraint(shape, true);
        }

        return ret;
    }

    /**
     * Parse a string like "1 km" or a k.IM quantity ('1.km') and return the meters in it. Throw an
     * exception if this cannot be parsed.
     * 
     * @param string
     * @return the resolution in meters
     * @throws KlabValidationException
     */
    public static double parseResolution(Object spec) throws KlabValidationException {

        Pair<Double, String> pd = null;
        if (spec instanceof String) {
            pd = MiscUtilities.splitNumberFromString((String) spec);
        } else if (spec instanceof IQuantity && ((IQuantity) spec).getValue() != null
                && ((IQuantity) spec).getUnit() != null) {
            pd = new Pair<>(((IQuantity) spec).getValue().doubleValue(),
                    ((IQuantity) spec).getUnit().toString());
        } else if (spec instanceof Quantity) {
            pd = new Pair<>(((Quantity) spec).getValue().doubleValue(),
                    ((Quantity) spec).getCurrency() == null
                            ? ((Quantity) spec).getUnit().toString()
                            : ((Quantity) spec).getCurrency().toString());
        }

        if (pd == null || pd.getFirst() == null || pd.getSecond() == null)
            throw new KlabValidationException("wrong resolution specification: " + spec);

        IUnit uu = Units.INSTANCE.getUnit(pd.getSecond());
        IUnit mm = Units.INSTANCE.getUnit("m");
        return mm.convert(pd.getFirst().doubleValue(), uu).doubleValue();
    }

}

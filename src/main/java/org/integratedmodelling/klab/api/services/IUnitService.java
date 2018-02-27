package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IExtent;

public interface IUnitService {

	IUnit getUnit(String unitDef);

    IUnit addExtents(IUnit refUnit, Collection<ExtentDimension> extentDimensions);

    boolean isDensity(IUnit unit, IConcept extent);

    boolean isSpatialDensity(IUnit unit, IExtent space);

    boolean isUnitless(IUnit unit);

    IUnit getVolumeExtentUnit(IUnit unit);

    boolean isVolumeDensity(IUnit unit);

    IUnit getArealExtentUnit(IUnit unit);

    boolean isArealDensity(IUnit unit);

    IUnit getLengthExtentUnit(IUnit unit);

    boolean isLengthDensity(IUnit unit);

    IUnit getTimeExtentUnit(IUnit unit);

    boolean isRate(IUnit unit);

    IUnit getDefaultUnitFor(IConcept concept);

}

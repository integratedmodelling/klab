package org.integratedmodelling.klab.common.mediation;

import java.util.List;

import javax.measure.UnitConverter;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;

/**
 * Host the scale-driven recontextualization mechanism for other mediators.
 * 
 * @author Ferd
 *
 */
public abstract class AbstractMediator implements IValueMediator {

    public enum ExtentSize {

        SPACE_M("a in m"),
        SPACE_M2("area in m^2"),
        SPACE_M3("volume in m^3"),
        TIME_MS("time span in milliseconds");

        String description;

        ExtentSize(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

    }

    public enum Operation {
        MULTIPLY, DIVIDE
    }

    public static class Mediation {

        public UnitConverter converter;
        public ExtentSize extentSize;
        public Operation operation;
        public Double factor;
        public String description;

        @Override
        public String toString() {
            return description;
        }
    }

    /*
     * null operations == standard behavior; otherwise, the mediators contain the converter and will
     * take over
     */
    List<Mediation> mediators = null;
    /*
     * the value we were contextualized from. We only need it for reporting or debugging.
     */
    IValueMediator toConvert;

    public void setMediation(IValueMediator toConvert, List<Mediation> mediators) {
        this.toConvert = toConvert;
        this.mediators = mediators;
    }

    @Override
    public Number convert(Number value, ILocator locator) {

        if (Observations.INSTANCE.isNodata(value)) {
            return value;
        }

        /*
         * trivial cases: no context, intensive semantics, or original unit required no
         * transformation
         */
        if (mediators == null) {
            return this.convert(value, toConvert);
        }

        double val = value.doubleValue();
        for (Mediation mediator : mediators) {
            if (mediator.extentSize != null) {
                switch(mediator.extentSize) {
                case SPACE_M:
                    val = mediator.operation == Operation.MULTIPLY
                            ? val * getSpace(locator).getStandardizedLength()
                            : val / getSpace(locator).getStandardizedLength();
                    break;
                case SPACE_M2:
                    val = mediator.operation == Operation.MULTIPLY
                            ? val * getSpace(locator).getStandardizedArea()
                            : val / getSpace(locator).getStandardizedArea();
                    break;
                case SPACE_M3:
                    val = mediator.operation == Operation.MULTIPLY
                            ? val * getSpace(locator).getStandardizedVolume()
                            : val / getSpace(locator).getStandardizedVolume();
                    break;
                case TIME_MS:
                    val = mediator.operation == Operation.MULTIPLY
                            ? val * ((Time) getTime(locator)).getLength()
                            : val / ((Time) getTime(locator)).getLength();
                    break;
                }
            }
            if (mediator.converter != null) {
                val = mediator.converter.convert(val);
            }
            if (mediator.factor != null) {
                val *= mediator.factor;
            }
        }

        return val;
    }

    private ISpace getSpace(ILocator locator) {

        ISpace ret = null;
        if (locator instanceof IScale) {
            ret = ((IScale)locator).getSpace();
        } else if (locator instanceof ISpace) {
            ret = (ISpace) locator;
        }
        
        if (ret == null) {
            throw new KlabInternalErrorException("cannot find space locator when mediating over space");
        }
        
        return ret;
    }

    private ITime getTime(ILocator locator) {
        
        ITime ret = null;
        if (locator instanceof IScale) {
            ret = ((IScale)locator).getTime();
        } else if (locator instanceof ITime) {
            ret = (ITime) locator;
        }
        
        if (ret == null) {
            throw new KlabInternalErrorException("cannot find time locator when mediating over space");
        }
        
        return ret;
    }

}

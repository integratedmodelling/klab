/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.common.mediation;

import java.io.PrintStream;
import java.util.Collection;

import javax.measure.converter.UnitConverter;
import javax.measure.unit.Dimension;
import javax.measure.unit.ProductUnit;
import javax.measure.unit.UnitFormat;

import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.data.mediation.IValueMediator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;

public class Unit implements IUnit {

    javax.measure.unit.Unit<?> _unit;
//    String                     _modifier = null;
    int                        _startLine;
    int                        _endLine;
    String                     statement;

    public static IUnit        METERS    = new Unit("m");

    public Unit() {
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Unit && toString().equals(((Unit) o).toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public void parse(String string) {

        Pair<Double, String> pd = MiscUtilities.splitNumberFromString(string);

        this.statement = string;

        double factor = 1.0;
        if (pd.getFirst() != null) {
            factor = pd.getFirst();
        }

        /*
         * if we have a modifier, main unit must be a IModifiableUnit and we
         * process it independently.
         */
        String unit = pd.getSecond();

        if (unit.contains("@")) {
            int idat = unit.indexOf('@');
            int idsl = unit.indexOf('/');

            String pre = unit.substring(0, idat);
//            _modifier = idsl > 0 ? unit.substring(idat + 1, idsl) : unit.substring(idat + 1);
            unit = idsl > 0 ? (pre + unit.substring(idsl)) : pre;
        }
        try {
            _unit = (javax.measure.unit.Unit<?>) UnitFormat.getUCUMInstance().parseObject(unit);
        } catch (Exception e) {
            throw new KlabRuntimeException(e);
        }

//        if (_modifier != null) {
//
//            /*
//             * must be a modifiable unit
//             */
//            javax.measure.unit.Unit<?> uu = getPrimaryUnit(_unit);
//            if (!(uu instanceof IModifiableUnit)) {
//                throw new KlabRuntimeException("unit " + string
//                        + " has @ modifier but is not registered as modifiable");
//            }
//
//            /*
//             * validate the modifier
//             */
//            try {
//                ((IModifiableUnit) uu).validateModifier(_modifier);
//            } catch (KlabValidationException e) {
//                throw new KlabRuntimeException(e);
//            }
//        }

        if (factor != 1.0) {
            _unit = _unit.times(factor);
        }

    }

    public Unit(javax.measure.unit.Unit<?> unit) {
        _unit = unit;
        statement = unit.toString();
    }

    public Unit(String s) {
        parse(s);
    }

    public static double convert(double value, String unitFrom, String unitTo) {
        return unitFrom.equals(unitTo) ? value : new Unit(unitTo).convert(value, new Unit(unitFrom)).doubleValue();
    }
    
    static public void main(String[] a) {
        System.out.println(convert(120, "m", "mm"));
    }
    
    /* (non-Javadoc)
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#convert(double, org.integratedmodelling.thinklab.api.modelling.observation.IUnit)
     */
    @Override
    public Number convert(Number value, IValueMediator unit) {

        if (!(unit instanceof Unit)) {
            throw new KlabRuntimeException("illegal conversion " + this + " to " + unit);
        }

        UnitConverter converter = ((Unit) unit).getUnit().getConverterTo(_unit);
        double ret = converter.convert(value.doubleValue());

//        if (getPrimaryUnit() instanceof IModifiableUnit) {
//            if (((Unit) unit).getPrimaryUnit() instanceof IModifiableUnit) {
//
//                try {
//                    ret *= ((IModifiableUnit) (((Unit) unit).getPrimaryUnit()))
//                            .convert((IModifiableUnit) getPrimaryUnit(), ((Unit) unit)._modifier, _modifier);
//                } catch (KlabValidationException e) {
//                    throw new KlabRuntimeException(e);
//                }
//
//            } else {
//                throw new KlabRuntimeException(new KlabValidationException("unit " + this
//                        + " has modifiers and can only be converted into another modifiable unit"));
//            }
//        }
        return ret;
    }

    public javax.measure.unit.Unit<?> getUnit() {
        return _unit;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#isRate()
     */
    @Override
    public boolean isRate() {

        boolean ret = false;
        if (_unit instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) _unit;
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.unit.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(Dimension.TIME) && power == -1) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#getTimeExtentUnit()
     */
    @Override
    public IUnit getTimeExtentUnit() {

        if (_unit instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) _unit;
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.unit.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(Dimension.TIME) && power == -1) {
                    return new Unit(su);
                }
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#isLengthDensity()
     */
    @Override
    public boolean isLengthDensity() {
        boolean ret = false;
        if (_unit instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) _unit;
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.unit.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(Dimension.LENGTH) && power == -1) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#getLengthExtentUnit()
     */
    @Override
    public IUnit getLengthExtentUnit() {

        if (_unit instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) _unit;
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.unit.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(Dimension.LENGTH) && power == -1) {
                    return new Unit(su);
                }
            }
        }
        return null;
    }

    public static javax.measure.unit.Unit<?> getPrimaryUnit(javax.measure.unit.Unit<?> uu) {

        if (uu instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) uu;
            return pu.getUnit(0);
        }
        return uu;
    }

    public javax.measure.unit.Unit<?> getPrimaryUnit() {
        return getPrimaryUnit(_unit);
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#isArealDensity()
     */
    @Override
    public boolean isArealDensity() {
        boolean ret = false;
        if (_unit instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) _unit;
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.unit.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if ((su.getDimension().equals(Dimension.LENGTH.pow(2)) && power == -1)
                        || (su.getDimension().equals(Dimension.LENGTH) && power == -2)) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#getArealExtentUnit()
     */
    @Override
    public IUnit getArealExtentUnit() {

        if (_unit instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) _unit;
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.unit.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(Dimension.LENGTH.pow(2)) && power == -1) {
                    return new Unit(su);
                } else if (su.getDimension().equals(Dimension.LENGTH) && power == -2) {
                    return new Unit(su.pow(2));
                }
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#isVolumeDensity()
     */
    @Override
    public boolean isVolumeDensity() {
        boolean ret = false;
        if (_unit instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) _unit;
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.unit.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(Dimension.LENGTH.pow(3)) && power == -1
                        || (su.getDimension().equals(Dimension.LENGTH) && power == -3)) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#getVolumeExtentUnit()
     */
    @Override
    public IUnit getVolumeExtentUnit() {

        if (_unit instanceof ProductUnit<?>) {
            ProductUnit<?> pu = (ProductUnit<?>) _unit;
            for (int i = 0; i < pu.getUnitCount(); i++) {
                javax.measure.unit.Unit<?> su = pu.getUnit(i);
                int power = pu.getUnitPow(i);
                if (su.getDimension().equals(Dimension.LENGTH.pow(3)) && power == -1
                        || (su.getDimension().equals(Dimension.LENGTH) && power == -3)) {
                    return new Unit(su);
                }
            }
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.integratedmodelling.thinklab.modelling.units.IUnit#isUnitless()
     */
    @Override
    public boolean isUnitless() {

        boolean ret = false;

        if (_unit instanceof ProductUnit<?>) {

            // assume no unitless unit without a distribution
            ret = true;
            ProductUnit<?> pu = (ProductUnit<?>) _unit;
            for (int i = 0; i < pu.getUnitCount(); i++) {
                int power = pu.getUnitPow(i);
                if (power > 0) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    public void dump(PrintStream out) {

        out.println("unit " + _unit);

//        if (_modifier != null)
//            out.println("modifier: " + _modifier);

        out.println("is" + (isUnitless() ? " " : " not ") + "unitless");
        out.println("is" + (isRate() ? " " : " not ") + "a rate");
        out.println("is" + (isLengthDensity() ? " " : " not ") + "a lenght density");
        out.println("is" + (isArealDensity() ? " " : " not ") + "an areal density");
        out.println("is" + (isVolumeDensity() ? " " : " not ") + "a volumetric density");
    }

    @Override
    public String toString() {
        return statement;
    }

    @Override
    public boolean isSpatialDensity(IExtent space) {
        if (space instanceof ISpace) {
            switch (space.getDimensionSizes().length) {
            case 0:
                return false;
            case 1:
                return isLengthDensity();
            case 2:
                return isArealDensity();
            case 3:
                return isVolumeDensity();
            }
        }
        return false;
    }

    @Override
    public boolean isCompatible(IValueMediator other) {
        return other instanceof Unit &&
                ((Unit) other)._unit.isCompatible(_unit);
    }

	@Override
	public boolean isDensity(IConcept extent) {

		if (extent.is(Concepts.INSTANCE.c(NS.SPACE_DOMAIN))) {
			return isArealDensity() || isLengthDensity() || isVolumeDensity();
		}
		if (extent.is(Concepts.INSTANCE.c(NS.TIME_DOMAIN))) {
			return isRate();
		}
		return false;
	}

    public static IUnit addExtents(IUnit refUnit, Collection<ExtentDimension> extentDimensions) {
        Unit ret = (Unit)refUnit;

        for (ExtentDimension dim : extentDimensions) {
            switch (dim) {
            case AREAL:
                ret = new Unit(ret._unit.divide(new Unit("m^2")._unit));
                break;
            case CONCEPTUAL:
                break;
            case LINEAL:
                ret = new Unit(ret._unit.divide(new Unit("m")._unit));
                break;
            case PUNTAL:
                break;
            case TEMPORAL:
                ret = new Unit(ret._unit.divide(new Unit("s")._unit));
                break;
            case VOLUMETRIC:
                ret = new Unit(ret._unit.divide(new Unit("m^3")._unit));
                break;
            default:
                break;
            
            }
        }
        
        return ret;
    }
	
}

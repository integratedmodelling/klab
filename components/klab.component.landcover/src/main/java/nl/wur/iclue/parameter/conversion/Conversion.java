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
package nl.wur.iclue.parameter.conversion;

import nl.wur.iclue.parameter.Landuses.Landuse;

/**
 *
 * @author Peter Verweij, Johnny te Roller
 */
public class Conversion {
    private final static String ERROR_UNDEFINED_RULE = "Cannot determine if landuse conversion can take place. No rule defined for '%s' to '%s'";
    
    private Landuse fromLanduse;
    private Landuse toLanduse;
    private Rule rule;

    public Conversion() {
        this(null, null, new Always());
    }
    
    public Conversion(Landuse fromLanduse, Landuse toLanduse, Rule rule) {
        this.fromLanduse = fromLanduse;
        this.toLanduse = toLanduse;
        this.rule = rule;
    }
    
    public Landuse getFromLanduse() {
        return fromLanduse;
    }

    public void setFromLanduse(Landuse fromLanduse) {
        this.fromLanduse = fromLanduse;
    }

    public Landuse getToLanduse() {
        return toLanduse;
    }

    public void setToLanduse(Landuse toLanduse) {
        this.toLanduse = toLanduse;
    }

    public Rule getRule() {
        return this.rule;
    }
    
    public void setRule(Rule rule) {
        this.rule = rule;
    }
    
    public boolean canConvert(int currentAge) {
        if (rule == null) {
            String from = (fromLanduse == null) ? "undefined FROM landuse" : fromLanduse.getCaption();
            String to = (toLanduse == null) ? "undefined TO landuse": toLanduse.getCaption();
            throw new RuntimeException(String.format(ERROR_UNDEFINED_RULE, from, to));
        } else
            return rule.canConvert(currentAge);
    }
}

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
package nl.wur.iclue.parameter;

/**
 *
 * @author Peter Verweij, Johnny te Roller
 */
public enum EaseOfChange {
	
    CANNOT_CHANGE("Cannot change", 1.0),
    VERY_HARD("Very hard", .9),
    HARD("Hard", .6),
    EASY("Easy", .3),
    VERY_EASY("Very easy", .1);
    
    private final String caption;
    private final double weight; // value between 0..1

    private EaseOfChange(String caption, double weight) {
        this.caption = caption;
        this.weight = weight;
    }

    public String getCaption() {
        return caption;
    }
    
    public double getWeight() {
        return weight;
    }

    public static EaseOfChange FindByCaption(String caption) {
        for (EaseOfChange elem: EaseOfChange.values()) 
            if (elem.getCaption().equalsIgnoreCase(caption))
                return elem;
        return null;
    }

    @Override
    public String toString() {
        return getCaption();
    }

    
}

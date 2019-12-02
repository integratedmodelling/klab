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

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.alterra.shared.datakind.Category;
import nl.alterra.shared.datakind.Clazz;
import nl.alterra.shared.datakind.DataKind;
import nl.alterra.shared.datakind.IDataKind;
import nl.wur.iclue.parameter.Landuses.Landuse;


/**
 *
 * @author Peter Verweij
 */
public class Landuses implements Iterable<Landuse> {
	
    private static final String ERROR_NOT_NOMINAL = "Cannot create Landuses instantiation based on non-nominal DataKind";
    
    private final DataKind dataKind;
    private final List<Landuse> list;

    public Landuses() {
        dataKind = new DataKind();
        dataKind.setType(IDataKind.Type.QUALITATIVE);
        list = new ArrayList<>();
    }
    
    public Landuses(DataKind landuses) {
        if (!IDataKind.LevelOfMeasurement.NOMINAL.equals(landuses.getLevelOfMeasurement()))
            throw new RuntimeException(ERROR_NOT_NOMINAL);
        dataKind = landuses;
        
        list = new ArrayList<>();
        for (Clazz clz: landuses.getClasses()) 
            list.add(new Landuse((Category)clz));
    }

    public DataKind getDataKind() {
        return dataKind;
    }

    public boolean contains(Landuse landuse) {
        return list.contains(landuse);
    }

    public boolean contains(Clazz landuse) {
        return dataKind.getClasses().contains(landuse);
    }
    
    public Landuse addNew() {
        Category category = (Category)dataKind.addNew();
        Landuse landuse = new Landuse(category);
        list.add(landuse);
        return landuse;
    }

    public int size() {
        return dataKind.getClassCount();
    }
    
    public Landuse get(int index) {
        return list.get(index);
    }
    
    public Landuse findByCaption(String caption) {
        for (int i=0; i<size(); i++) {
            Landuse lu = get(i);
            if (lu.getCaption().equalsIgnoreCase(caption))
                return lu;
        }
        return null;
    }
    
    public Landuse findByValue(Number value) {
        for (int i=0; i<size(); i++) {
            Landuse lu = get(i);
            if (lu.getCategory().includes(value))
                return lu;
        }
        return null;
    }
    
    public Landuse findByClazz(Clazz clazz) {
        for (int i=0; i<size(); i++) {
            Landuse lu = get(i);
            if (lu.getCategory().equals(clazz))
                return lu;
        }
        return null;
    }
    
    @Override
    public Iterator<Landuse> iterator() {
        return new Iterator<Landuse>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public Landuse next() {
                return get(index++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); 
            }
        };
    }

    public String[] toCaptionArray() {
        String[] result = new String[size()];
        for (int i=0; i<size(); i++)
            result[i]=get(i).getCaption();
        return result;
    }
    
    
    
    public class Landuse {
        private final Category category;
        private EaseOfChange easeOfChange = EaseOfChange.CANNOT_CHANGE;
        private int initialAge = 0;

        private Landuse(Category category) {
            this.category = category;
        }
        
        public Category getCategory() {
            return category;
        }
        
        public String getCaption() {
            return category.getCaption();
        }
        
        public Landuse setCaption(String caption) {
            category.setCaption(caption);
            return this;
        }
        
        public int getCode() {
            return (Integer)category.getValue();
        }
        
        public Landuse setCode(int code) {
            category.setValue(code);
            return this;
        }
        
        public Color getColour() {
            return category.getSymbol().getColour();
        }
        
        public Landuse setColour(Color colour) {
            category.getSymbol().setColour(colour);
            return this;
        }
        
        public EaseOfChange getEaseOfChange() {
            return easeOfChange;
        }
        
        public Landuse setEaseOfChange(EaseOfChange easeOfChange) {
            this.easeOfChange = easeOfChange;
            return this;
        }
        
        public int getInitialAge() {
            return initialAge;
        }
        
        public Landuse setInitialAge(int age) {
            this.initialAge = age;
            return this;
        }
        
        public boolean canChange() {
            return !EaseOfChange.CANNOT_CHANGE.equals(getEaseOfChange());
        }

        @Override
        public String toString() {
            return getCaption();
        }
        
        
    }
    
}

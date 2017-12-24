/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
 * other authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable
 * modular, collaborative, integrated development of interoperable data and model
 * components. For details, see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for a
 * particular purpose. See the Affero General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA. The license is also available at:
 * https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimMetadata;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;

public class Metadata implements IMetadata {

    protected Map<String, Object> data = new HashMap<>();

    public Metadata() {
    }

    public Metadata(Map<String, Object> data) {
        this.data.putAll(data);
    }

    public Metadata(IKimMetadata data) {
        this.data.putAll(data.getData());
    }
    
    @Override
    public void put(String id, Object value) {
        data.put(id, value);
    }

    @Override
    public Collection<String> getKeys() {
        return data.keySet();
    }

    @Override
    public Object get(String string) {
        return data.get(string);
    }

    @Override
    public void merge(IMetadata md, boolean overwriteExisting) {
        if (overwriteExisting) {
            data.putAll(((Metadata) md).data);
        } else {
            for (String key : md.getKeys()) {
                if (!data.containsKey(key))
                    data.put(key, md.get(key));
            }
        }
    }

    @Override
    public String getString(String field) {
        Object o = data.get(field);
        return o != null ? o.toString() : null;
    }

    @Override
    public Integer getInt(String field) {
        Object o = data.get(field);
        return o != null && o instanceof Number ? ((Number) o).intValue()
                : (o instanceof String ? Integer.parseInt(o.toString()) : null);
    }

    @Override
    public Long getLong(String field) {
        Object o = data.get(field);
        return o instanceof Number ? ((Number) o).longValue()
                : (o instanceof String ? Long.parseLong(o.toString()) : null);
    }

    @Override
    public Double getDouble(String field) {
        Object o = data.get(field);
        try {
            if (o instanceof Number)
                return ((Number) o).doubleValue();
            if (o instanceof String)
                return Double.parseDouble(o.toString());
        } catch (Throwable e) {
            // just null
        }
        return null;
    }

    @Override
    public Float getFloat(String field) {
        Object o = data.get(field);
        return o != null && o instanceof Number ? ((Number) o).floatValue()
                : (o instanceof String ? Float.parseFloat(o.toString()) : null);
    }

    @Override
    public Boolean getBoolean(String field) {
        Object o = data.get(field);
        return o != null && o instanceof Boolean ? (Boolean) o
                : (o instanceof String ? Boolean.parseBoolean(o.toString()) : null);
    }

    @Override
    public IConcept getConcept(String field) {
        Object o = data.get(field);
        return o != null && o instanceof IConcept ? (IConcept) o
                : (o instanceof String ? Concepts.INSTANCE.declare(o.toString()) : null);
    }

    @Override
    public String getString(String field, String def) {
        Object o = data.get(field);
        return o != null ? o.toString() : def;
    }

    @Override
    public int getInt(String field, int def) {
        Object o = data.get(field);
        return o != null ? getInt(field) : def;
    }

    @Override
    public long getLong(String field, long def) {
        Object o = data.get(field);
        return o != null ? getLong(field) : def;
    }

    @Override
    public double getDouble(String field, double def) {
        Object o = data.get(field);
        return o != null ? getDouble(field) : def;
    }

    @Override
    public float getFloat(String field, float def) {
        Object o = data.get(field);
        return o != null ? getFloat(field) : def;
    }

    @Override
    public boolean getBoolean(String field, boolean def) {
        Object o = data.get(field);
        return o != null ? getBoolean(field) : def;
    }

    @Override
    public IConcept getConcept(String field, IConcept def) {
        Object o = data.get(field);
        return o != null ? getConcept(field) : def;
    }

    @Override
    public Collection<Object> getValues() {
        return data.values();
    }

    public void putAll(IMetadata metadata) {
        data.putAll(((Metadata) metadata).data);
    }

    @Override
    public void remove(String key) {
        data.remove(key);
    }

    @Override
    public Map<? extends String, ? extends Object> getDataAsMap() {
        return data;
    }

    @Override
    public boolean contains(String key) {
        return data.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

}

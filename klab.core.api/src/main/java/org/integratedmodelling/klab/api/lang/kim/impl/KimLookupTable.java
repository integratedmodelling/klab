package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.knowledge.KArtifact.Type;
import org.integratedmodelling.klab.api.lang.kim.KKimClassifier;
import org.integratedmodelling.klab.api.lang.kim.KKimLookupTable;
import org.integratedmodelling.klab.api.lang.kim.KKimTable;

public class KimLookupTable extends KimStatement implements KKimLookupTable {

    private static final long serialVersionUID = 1081054386576296191L;

    private Type lookupType;
    private List<Argument> arguments = new ArrayList<>();
    private KKimTable table;
    private boolean twoWay;
    private List<KKimClassifier> rowClassifiers = new ArrayList<>();
    private List<KKimClassifier> columnClassifiers = new ArrayList<>();
    private int lookupColumnIndex;

    @Override
    public Type getLookupType() {
        return lookupType;
    }

    @Override
    public List<Argument> getArguments() {
        return arguments;
    }

    @Override
    public KKimTable getTable() {
        return table;
    }

    @Override
    public boolean isTwoWay() {
        return twoWay;
    }

    @Override
    public List<KKimClassifier> getRowClassifiers() {
        return rowClassifiers;
    }

    @Override
    public List<KKimClassifier> getColumnClassifiers() {
        return columnClassifiers;
    }

    @Override
    public int getLookupColumnIndex() {
        return lookupColumnIndex;
    }

    public void setLookupType(Type lookupType) {
        this.lookupType = lookupType;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }

    public void setTable(KKimTable table) {
        this.table = table;
    }

    public void setTwoWay(boolean twoWay) {
        this.twoWay = twoWay;
    }

    public void setRowClassifiers(List<KKimClassifier> rowClassifiers) {
        this.rowClassifiers = rowClassifiers;
    }

    public void setColumnClassifiers(List<KKimClassifier> columnClassifiers) {
        this.columnClassifiers = columnClassifiers;
    }

    public void setLookupColumnIndex(int lookupColumnIndex) {
        this.lookupColumnIndex = lookupColumnIndex;
    }

}

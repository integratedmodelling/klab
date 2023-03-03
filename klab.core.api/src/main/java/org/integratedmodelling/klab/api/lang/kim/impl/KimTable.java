package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.lang.kim.KKimClassifier;
import org.integratedmodelling.klab.api.lang.kim.KKimTable;

public class KimTable extends KimStatement implements KKimTable {

    private static final long serialVersionUID = -8528877830924327222L;

    private List<String> headers = new ArrayList<>();
    private boolean twoWay;
    private List<KKimClassifier> columnClassifiers = new ArrayList<>();
    private int rowCount;
    private int columnCount;
    private List<KKimClassifier> rowClassifiers = new ArrayList<>();

    @Override
    public List<String> getHeaders() {
        return headers;
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
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public KKimClassifier[] row(int i) {
        return null;
    }

    @Override
    public List<KKimClassifier[]> rows() {
        return null;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void setTwoWay(boolean twoWay) {
        this.twoWay = twoWay;
    }

    public void setColumnClassifiers(List<KKimClassifier> columnClassifiers) {
        this.columnClassifiers = columnClassifiers;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public void setRowClassifiers(List<KKimClassifier> rowClassifiers) {
        this.rowClassifiers = rowClassifiers;
    }

}

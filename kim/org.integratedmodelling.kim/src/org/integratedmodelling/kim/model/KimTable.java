package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.kim.kim.ClassifierRHS;
import org.integratedmodelling.kim.kim.Table;
import org.integratedmodelling.kim.kim.TableRow;

public class KimTable extends KimStatement implements IKimTable {

	private static final long serialVersionUID = 7658475881371843918L;
	
    protected List<IKimClassifier[]> rows    = new ArrayList<>();
    protected List<String>             headers = null;

    public KimTable(Table statement, IKimStatement parent) {
        super(statement, parent);
        int ncols = -1;
        if (statement.getHeaders() != null) {
        	headers = new ArrayList<>(statement.getHeaders().getElements());
        	ncols = headers.size();
        }
        for (TableRow row : statement.getRows()) {
        	IKimClassifier[] rowElements = new IKimClassifier[ncols];
        	int i = 0;
        	for (ClassifierRHS classifier : row.getElements()) {
        		rowElements[i++] = new KimClassifier(classifier, false, null, this);
        	}
        	rows.add(rowElements);
        }
    }
    
    @Override
    public List<String> getHeaders() {
    	return headers;
	}
    
    @Override
    public int getColumnCount() {
    	return rows.size() > 0 ? rows.get(0).length : 0;
    }

    @Override
    public int getRowCount() {
    	return rows.size();
    }

	@Override
	public IKimClassifier[] getRow(int i) {
		return rows.get(i);
	}
}

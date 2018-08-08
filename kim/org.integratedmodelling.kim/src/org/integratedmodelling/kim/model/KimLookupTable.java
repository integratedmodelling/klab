package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.kim.ClassifierRHS;
import org.integratedmodelling.kim.kim.LookupTable;
import org.integratedmodelling.kim.kim.Table;
import org.integratedmodelling.kim.kim.TableRow;

public class KimLookupTable extends KimStatement implements IKimLookupTable {

    private static final long serialVersionUID = -8962809767778643579L;

    protected List<IKimClassifier[]> rows    = new ArrayList<>();
    protected List<String>             headers = null;
    List<String> arguments = new ArrayList<>();
    
    public KimLookupTable(Table statement, List<String> arguments, IKimStatement parent) {
        super(statement, parent);
        this.arguments.addAll(arguments);
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

    public KimLookupTable(LookupTable table, IKimStatement parent) {
        super(table, parent);
        // TODO Auto-generated constructor stub
    }

	@Override
	public IKimClassifier[] getRow(int i) {
		return rows.get(i);
	}

	@Override
	public List<String> getArguments() {
		return arguments;
	}

}

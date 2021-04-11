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

	protected List<IKimClassifier[]> rows = new ArrayList<>();
	protected List<String> headers = null;
	
	protected List<IKimClassifier> columnClassifiers = null;
    protected List<IKimClassifier> rowClassifiers = null;

	public KimTable(Table statement, IKimStatement parent) {
		super(statement, parent);
		int ncols = -1;
		if (statement.getHeaders() != null) {
			headers = new ArrayList<>(statement.getHeaders().getElements());
			ncols = headers.size();
		}
		if (statement.getColumnClassifiers() != null) {
		    for (ClassifierRHS cc : statement.getColumnClassifiers().getElements()) {
		        if (columnClassifiers == null) {
		            columnClassifiers = new ArrayList<>();
                    rowClassifiers = new ArrayList<>();
		        }
		        columnClassifiers.add(new KimClassifier(cc, false, null, this));
		    }
		}
		for (TableRow row : statement.getRows()) {
			if (ncols < 0) {
				ncols = row.getElements().size();
				if (columnClassifiers != null) {
				    ncols --;
				}
			}
			IKimClassifier[] rowElements = new IKimClassifier[ncols];
			int i = 0;
			try {
			    boolean first = true;
				for (ClassifierRHS classifier : row.getElements()) {
				    if (first && columnClassifiers != null) {
				        rowClassifiers.add(new KimClassifier(classifier, false, null, this));
				        first = false;
				    } else {
				        rowElements[i++] = new KimClassifier(classifier, false, null, this);
				    }
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				// do nothing - this happens only after the validator has already notified an
				// error
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

	@Override
	public List<IKimClassifier[]> getRows() {
		return rows;
	}

    @Override
    public boolean isTwoWay() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<IKimClassifier> getRowClassifiers() {
        return rowClassifiers;
    }

    @Override
    public List<IKimClassifier> getColumnClassifiers() {
        return columnClassifiers;
    }
}

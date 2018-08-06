package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.kim.LookupTable;
import org.integratedmodelling.kim.kim.Table;

public class KimLookupTable extends KimStatement implements IKimLookupTable {

    private static final long serialVersionUID = -8962809767778643579L;

    protected List<IKimClassifier[]> rows    = new ArrayList<>();
    protected List<String>             headers = null;
    
    public KimLookupTable(Table statement, List<String> arguments, IKimStatement parent) {
        super(statement, parent);
        if (statement.getHeaders() != null) {
        	
        }
    }
    
    public KimLookupTable(LookupTable table, IKimStatement parent) {
        super(table, parent);
        // TODO Auto-generated constructor stub
    }

}

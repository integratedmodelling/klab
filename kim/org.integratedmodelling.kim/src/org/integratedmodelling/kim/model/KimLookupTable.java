package org.integratedmodelling.kim.model;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IKimStatement;

public class KimLookupTable extends KimStatement implements IKimLookupTable {

    private static final long serialVersionUID = -8962809767778643579L;

    public KimLookupTable(EObject statement, IKimStatement parent) {
        super(statement, parent);
        // TODO Auto-generated constructor stub
    }

}

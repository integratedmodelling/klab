package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.kim.kim.LookupTableArgument;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;

public class KimLookupTable extends KimStatement implements IKimLookupTable {

    private static final long serialVersionUID = -8962809767778643579L;

    List<Argument> arguments = new ArrayList<>();
    IKimTable table;
    int searchColumn = -1;
    IArtifact.Type lookupType;
    String error;
    boolean twoWay = false;

    public KimLookupTable(IKimTable table, List<LookupTableArgument> arguments, boolean twoWay, IKimStatement parent) {
        
        super(((KimStatement) table).getEObject(), parent);
        this.table = table;
        this.twoWay = twoWay;
        boolean haveSearch = false;
        for (LookupTableArgument arg : arguments) {
            Argument a = new Argument();
            if (arg.getId() != null) {
                a.id = arg.getId();
                if ("?".equals(arg.getId())) {
                    haveSearch = true;
                }
                if (arg.getKey() != null) {
                    a.dimension = Argument.Dimension.valueOf(arg.getKey().toUpperCase());
                }
            } else if (arg.getConcept() != null) {
                a.concept = Kim.INSTANCE.declareConcept(arg.getConcept());
            }
            this.arguments.add(a);
        }
        int ncols = -1;
        // pad any needed argument with the most likely implied
        if (!twoWay) {
            
            while(arguments.size() < ncols) {
                Argument arg = new Argument();
                arg.id = (ncols == 2 && arguments.size() == 1 && !haveSearch) ? "?" : "*";
                this.arguments.add(arg);
            }
            for (int i = 0; i < arguments.size(); i++) {
                if ("?".equals(arguments.get(i).getId())) {
                    searchColumn = i;
                    break;
                }
            }

            // if no ? is given and the arguments are one less than the columns, the
            // last column is the search column
            if (searchColumn < 0 && arguments.size() == table.getColumnCount() - 1) {
                searchColumn = table.getColumnCount() - 1;
            }

            if (searchColumn >= 0) {
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (lookupType == null) {
                        lookupType = table.getRow(i)[searchColumn].getType();
                    } else if (table.getRow(i)[searchColumn].getType() != lookupType) {
                        this.error = "the type of the objects in the search column must be uniform";
                    }
                }
            }
        }
    }

    public String getError() {
        return error;
    }

    @Override
    public List<Argument> getArguments() {
        return arguments;
    }

    @Override
    public Type getLookupType() {
        return this.lookupType;
    }

    @Override
    public IKimTable getTable() {
        return this.table;
    }

    @Override
    public int getLookupColumnIndex() {
        return searchColumn;
    }

    @Override
    public boolean isTwoWay() {
        return twoWay;
    }

    @Override
    public List<IKimClassifier> getRowClassifiers() {
        return table.getRowClassifiers();
    }

    @Override
    public List<IKimClassifier> getColumnClassifiers() {
        return table.getColumnClassifiers();
    }

}

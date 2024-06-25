package org.integratedmodelling.klab.data.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimLookupTable;
import org.integratedmodelling.kim.api.IKimLookupTable.Argument.Dimension;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.data.general.IStructuredTable;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.data.classification.Classifier;
import org.integratedmodelling.klab.utils.Pair;

public class LookupTable implements ILookupTable {

    class ArgImpl implements Argument {

        String id;
        IConcept concept;
        public Dimension dimension;

        @Override
        public String getId() {
            return id;
        }

        @Override
        public IConcept getConcept() {
            return concept;
        }
    }

    StructuredTable<IClassifier> table;
    List<Argument> variables = new ArrayList<>();
    IArtifact.Type type;
    int searchIndex;

    Map<IConcept, Integer> key;
    Map<String, Object> cache = new HashMap<>();
    private boolean twoWay;
    List<IClassifier> columnClassifiers = new ArrayList<>();
    List<IClassifier> rowClassifiers = new ArrayList<>();

    class RowProxy {
        int row;

        RowProxy(int row) {
            this.row = row;
        }
    }

    public LookupTable(IKimLookupTable lookupTable) {

        this.table = StructuredTable.create(lookupTable.getTable());
        this.twoWay = lookupTable.isTwoWay();

        for(IKimLookupTable.Argument a : lookupTable.getArguments()) {
            ArgImpl aa = new ArgImpl();
            if (a.id != null) {
                aa.id = a.id;
            } else if (a.concept != null) {
                aa.concept = Concepts.INSTANCE.declare(a.concept);
            }
            aa.dimension = a.dimension;
            this.variables.add(aa);
        }
        this.searchIndex = lookupTable.getLookupColumnIndex();
        this.type = lookupTable.getLookupType();

        if (this.type == Type.CONCEPT) {
            this.key = new LinkedHashMap<>();
            for(int i = 0, conceptCounter = 0; i < table.getRowCount(); i++) {
                // Avoid counting multiple table row matches
                IConcept concept = (IConcept) table.getRow(i)[searchIndex].asValue(null);
                if (!this.key.containsKey(concept)) {
                    this.key.put(concept, conceptCounter++);
                }
            }
        }

        if (this.twoWay) {
            for(IKimClassifier classifier : lookupTable.getColumnClassifiers()) {
                columnClassifiers.add(new Classifier(classifier));
            }
            for(IKimClassifier classifier : lookupTable.getRowClassifiers()) {
                rowClassifiers.add(new Classifier(classifier));
            }
        }

    }

    /**
     * True if the table is functional, i.e. it matches one input to one output.
     * 
     * @return
     */
    public boolean isKey() {
        return key != null;
    }

    @Override
    public IStructuredTable<IClassifier> getTable() {
        return table;
    }

    @Override
    public int reverseLookup(Object value) {
        Integer ret = key.get(value);
        return ret == null ? -1 : ret;
    }

    @Override
    public int size() {
        return this.key.size();
    }

    @Override
    public int getResultColumn() {
        return searchIndex;
    }

    @Override
    public List<String> getLabels() {
        List<String> ret = new ArrayList<>();
        for(IConcept concept : key.keySet()) {
            ret.add(Concepts.INSTANCE.getDisplayName(concept));
        }
        return ret;
    }

    @Override
    public boolean isOrdered() {
        return key != null && ((IConcept) table.getRow(0)[searchIndex].asValue(null)).is(IKimConcept.Type.ORDERING);
    }

    @Override
    public List<Pair<Integer, String>> getAllValues() {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        for(Entry<IConcept, Integer> entry : key.entrySet()) {
            ret.add(new Pair<>(entry.getValue(), Concepts.INSTANCE.getDisplayName(entry.getKey())));
        }
        return ret;
    }

    @Override
    public List<Argument> getArguments() {
        return variables;
    }

    @Override
    public Object lookup(IParameters<String> parameters, IContextualizationScope scope, ILocator locator) {

        StringBuffer s = new StringBuffer(1024);
        Object[] values = new Object[variables.size()];
        int rowDimension = -1;

        for(int i = 0; i < variables.size(); i++) {

            if (i == searchIndex || (variables.get(i).getId() != null && variables.get(i).getId().charAt(0) == '*')) {
                continue;
            }
            if (twoWay && ((ArgImpl) variables.get(i)).dimension == Dimension.ROW) {
                rowDimension = i;
            }
            values[i] = variables.get(i).getId() != null
                    ? parameters.get(variables.get(i).getId())
                    : scope.localizePredicate(variables.get(i).getConcept());

            // normal situation during contextualization
            if (values[i] instanceof IState) {
                values[i] = ((IState) values[i]).get(locator);
            }

            s.append("|");
            s.append(values[i] == null ? "null" : values[i].toString());
        }
        String key = s.toString();

        Object ret = cache.get(key);
        if (ret == null) {

            int rind = 0;
            boolean storeProxy = false;
            boolean doNotCache = false;

            if (this.twoWay && rowDimension >= 0) {

                /*
                 * match the parameters to row and column, fail if match is not possible
                 * 
                 */
                Object value = values[rowDimension];
                for(int rowIndex = 0; rowIndex < rowClassifiers.size(); rowIndex++) {
                    if (rowClassifiers.get(rowIndex).classify(value, scope)) {
                        for(int colIndex = 0; colIndex < columnClassifiers.size(); colIndex++) {
                            if (columnClassifiers.get(colIndex).classify(values[rowDimension == 0 ? 1 : 0], scope)) {
                                if (table.getRow(rowIndex)[colIndex].isComputed()) {
                                    doNotCache = true;
                                }
                                ret = table.getRow(rowIndex)[colIndex].asValue(scope);
                                break;
                            }
                        }
                        break;
                    }
                }

            } else {

                for(IClassifier[] row : table.getRows()) {
                    boolean ok = true;
                    for(int i = 0; i < variables.size(); i++) {
                        if (i == searchIndex || (variables.get(i).getId() != null && variables.get(i).getId().charAt(0) == '*')) {
                            continue;
                        }
                        if (!row[i].classify(values[i], scope)) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        if (row[searchIndex].isComputed()) {
                            storeProxy = true;
                        }
                        ret = row[searchIndex].asValue(scope);
                        break;
                    }
                    rind++;
                }
            }

            if (!doNotCache) {
                cache.put(key, ret == null ? Optional.empty() : (storeProxy ? new RowProxy(rind) : ret));
            }

        } else if (ret instanceof RowProxy) {
            ret = table.getRows().get(((RowProxy) ret).row)[searchIndex].asValue(scope);
        } else {
            ret = ret instanceof Optional ? null : ret;
        }

        return ret;
    }

    @Override
    public IArtifact.Type getResultType() {
        return type;
    }

    @Override
    public Object lookup(int index) {
        return (IConcept) table.getRow(index)[searchIndex].asValue(null);
    }

    @Override
    public IConcept getConcept(int index) {
        for(Entry<IConcept, Integer> entry : key.entrySet()) {
            if (entry.getValue().equals(index)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Collection<IKimExpression> getUniqueExpressions() {
        return table.getExpressions();
    }

    @Override
    public List<String> getSerializedObjects() {
        List<String> ret = new ArrayList<>();
        for(IConcept concept : key.keySet()) {
            ret.add(concept.getDefinition());
        }
        return ret;
    }

    @Override
    public List<IConcept> getConcepts() {
        List<IConcept> ret = new ArrayList<>();
        for(IConcept concept : key.keySet()) {
            ret.add(concept);
        }
        return ret;
    }

    @Override
    public Object include(Object value) {
        if (!(value instanceof IConcept)) {
            throw new IllegalArgumentException("a table can only serve as a datakey for concepts");
        }
        if (!this.key.containsKey((IConcept) value)) {
            this.key.put((IConcept) value, this.key.size());
        }
        return value;
    }

    public boolean isTwoWay() {
        return twoWay;
    }

    @Override
    public IAuthority getAuthority() {
        // TODO Auto-generated method stub
        return null;
    }

}

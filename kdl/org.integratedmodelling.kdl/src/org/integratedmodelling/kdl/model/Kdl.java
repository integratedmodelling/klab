package org.integratedmodelling.kdl.model;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kdl.kdl.Literal;
import org.integratedmodelling.kdl.kdl.Model;
import org.integratedmodelling.kdl.kdl.Value;
import org.integratedmodelling.kdl.utils.Range;

import com.google.inject.Injector;

public enum Kdl {

    INSTANCE;

    public IKdlDataflow declare(Model dataflow) {
        return new KdlDataflow(dataflow);
    }

    /**
     * Call before keyword list can be obtained
     * 
     * @param injector
     */
    public void setup(Injector injector) {
        injector.injectMembers(this);
    }

    public Number parseNumber(org.integratedmodelling.kdl.kdl.Number number) {
        ICompositeNode node = NodeModelUtils.findActualNodeFor(number);
        if (number.isExponential() || number.isDecimal()) {
            return Double.parseDouble(node.getText().trim());
        }
        return Integer.parseInt(node.getText().trim());
    }

    public Object parseValue(Value value) {
        if (value.getLiteral() != null) {
            return parseLiteral(value.getLiteral());
        } else if (value.getFunction() != null) {
            if (value.getFunction().getParameters() == null) {
                return value.getFunction().getName();
            } else {
                // TODO!
            }
        } else if (value.getList() != null) {
            List<Object> ret = new ArrayList<>();
            for (Value val : value.getList().getContents()) {
                if (!(val.getLiteral() != null && val.getLiteral().isComma())) {
                    ret.add(parseValue(val));
                }
            }
            return ret;
        }

        return null;
    }

    private Object parseLiteral(Literal literal) {
        if (literal.getBoolean() != null) {
            return Boolean.parseBoolean(literal.getBoolean());
        } else if (literal.getNumber() != null) {
            return parseNumber(literal.getNumber());
        } else if (literal.getId() != null) {
            return literal.getId();
        } else if (literal.getString() != null) {
            return literal.getString();
        } else if (literal.getFrom() != null) {
            Range range = new Range();
            range.low = parseNumber(literal.getFrom()).doubleValue();
            if (literal.getTo() != null) {
                range.high = parseNumber(literal.getTo()).doubleValue();
            }
            return range;
        }
        return null;
    }
}

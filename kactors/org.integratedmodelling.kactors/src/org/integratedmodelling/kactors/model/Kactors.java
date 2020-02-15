package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKactorsApplication;
import org.integratedmodelling.kactors.kactors.Model;

public enum Kactors {

	INSTANCE;
	

	public IKactorsApplication declare(Model model) {
		return new KactorsApplication(model);
	}
    
//    public IKdlDataflow declare(Model dataflow) {
//        return new KdlDataflow(dataflow);
//    }
//
//    public Number parseNumber(org.integratedmodelling.kdl.kdl.Number number) {
//        ICompositeNode node = NodeModelUtils.findActualNodeFor(number);
//        if (number.isExponential() || number.isDecimal()) {
//            return Double.parseDouble(node.getText().trim());
//        } 
//        return Integer.parseInt(node.getText().trim());
//    }
//    
//    public Object parseValue(Value value) {
//        if (value.getLiteral() != null) {
//            return parseLiteral(value.getLiteral());
//        } else if (value.getFunction() != null) {
//        	if (value.getFunction().getParameters() == null) {
//        		return value.getFunction().getName();
//        	} else {
//        		// TODO!
//        	}
//        } else if (value.getList() != null) {
//            List<Object> ret = new ArrayList<>();
//            for (Value val : value.getList().getContents()) {
//                if (!(val.getLiteral() != null && val.getLiteral().isComma())) {
//                    ret.add(parseValue(val));
//                }
//            }
//            return ret;
//        } 
//        
//        return null;
//    }
//    
//    private Object parseLiteral(Literal literal) {
//        if (literal.getBoolean() != null) {
//            return Boolean.parseBoolean(literal.getBoolean());
//        } else if (literal.getNumber() != null) {
//            return parseNumber(literal.getNumber());
//        } else if (literal.getId() != null) {
//            return literal.getId();
//        } else if (literal.getString() != null) {
//            return literal.getString();
//        } else if (literal.getFrom() != null) {
//            Range range = new Range();
//            range.low = parseNumber(literal.getFrom()).doubleValue();
//            if (literal.getTo() != null) {
//                range.high = parseNumber(literal.getTo()).doubleValue();
//            }
//            return range;
//        }
//        return null;
//    }
}

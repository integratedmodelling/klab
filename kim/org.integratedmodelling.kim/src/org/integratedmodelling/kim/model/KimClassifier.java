package org.integratedmodelling.kim.model;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.kim.Classifier;
import org.integratedmodelling.kim.kim.ClassifierRHS;

public class KimClassifier extends KimStatement implements IKimClassifier {

    private static final long serialVersionUID = 2634906673844696880L;

    boolean catchAll = false;
    boolean negated = false;
    IKimConcept conceptMatch;
    
    // this produces a catch-all and should only be called when isOtherwise() == true
    public KimClassifier(Classifier statement, IKimStatement parent) {
    	super(statement, parent);
    	this.catchAll = true;
    }
    
    // this produces anything else
    public KimClassifier(ClassifierRHS statement, boolean negate, IKimConcept matchedConcept, IKimStatement parent) {
        super(statement, parent);

        negated = negate;
        
        if (statement == null && matchedConcept != null) {
            conceptMatch = matchedConcept;
            return;
        }
//
//        if (statement.isAnything()) {
//        	anythingMatch = true;
//        } else if (statement.getNum() != null) {
//            Number n = KIM.processNumber(statement.getNum());
//            numberMatch = n.doubleValue();
//
//        } else if (statement.getBoolean() != null) {
//
//            booleanMatch = statement.getBoolean().equals("true") ? 1 : 0;
//
//        } else if (statement.getInt0() != null) {
//
//            Number from = KIM.processNumber(statement.getInt0());
//            Number to = KIM.processNumber(statement.getInt1());
//            String lt = statement.getLeftLimit();
//            String rt = statement.getRightLimit();
//            if (lt == null)
//                lt = "inclusive";
//            if (rt == null)
//                rt = "exclusive";
//            intervalMatch = new NumericInterval(from.doubleValue(), to.doubleValue(), lt
//                    .equals("exclusive"), rt.equals("exclusive"));
//
//        } else if (statement.getOp() != null) {
//
//            NumericInterval ni = null;
//            Number op = KIM.processNumber(statement.getExpression());
//
//            if (statement.getOp().isGe()) {
//                ni = new NumericInterval(op.doubleValue(), null, false, true);
//            } else if (statement.getOp().isGt()) {
//                ni = new NumericInterval(op.doubleValue(), null, true, true);
//            } else if (statement.getOp().isLe()) {
//                ni = new NumericInterval(null, op.doubleValue(), true, false);
//            } else if (statement.getOp().isLt()) {
//                ni = new NumericInterval(null, op.doubleValue(), true, true);
//            } else if (statement.getOp().isEq()) {
//                numberMatch = op.doubleValue();
//            } else if (statement.getOp().isNe()) {
//                numberMatch = op.doubleValue();
//                negate();
//            }
//
//            if (ni != null) {
//                intervalMatch = ni;
//            }
//
//        } else if (statement.getNodata() != null) {
//
//            nullMatch = true;
//
//        } else if (statement.getSet() != null) {
//
//            for (Object o : KIM.nodeToList(statement.getSet())) {
//                if (o instanceof Number) {
//                    addClassifier(NumberMatcher((Number) o));
//                } else if (o instanceof String) {
//                    addClassifier(StringMatcher((String) o));
//                } else if (o instanceof IConcept) {
//                    addClassifier(ConceptMatcher((IConcept) o));
//                } else if (o == null) {
//                    addClassifier(NullMatcher());
//                } else if (o instanceof IList) {
//                    addClassifier((Classifier) Multiple((IList) o));
//                }
//            }
//
//        } else if (statement.getToResolve() != null && statement.getToResolve().size() > 0) {
//
//            for (ConceptDeclarationUnion cdu : statement.getToResolve()) {
//                List<IConcept> or = new ArrayList<>();
//                for (ConceptDeclaration cd : cdu.getConcept()) {
//                    IKnowledgeObject ko = new KIMKnowledge(context.get(KIMScope.Type.CONCEPT), cd, null);
//                    if (!ko.isNothing() && ko.isConcept()) {
//                        or.add(ko.getConcept());
//                    }
//                }
//                if (or.isEmpty()) {
//                    continue;
//                }
//                if (conceptMatches == null) {
//                    conceptMatches = new ArrayList<>();
//                }
//                conceptMatches.add(or);
//            }
//
//        } else if (statement.getConcept() != null) {
//
//            IKnowledgeObject ko = new KIMKnowledge(context.get(KIMScope.Type.CONCEPT), statement
//                    .getConcept());
//            if (!ko.isNothing() && ko.isConcept()) {
//                conceptMatch = ko.getConcept();
//            }
//        } else if (statement.getString() != null) {
//            stringMatch = statement.getString();
//        } else if (statement.getCode() != null) {
//            expressionMatch = new KIMExpression(context, statement, statement.getCode());
//        } else if (statement.isStar()) {
//            catchAll = true;
//        }
    }
}

package org.integratedmodelling.klab.common;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple expression syntactic tree builder that can be translated to code by defining a simple
 * translator. Operators are strings for flexibility. The object represents a node and the tree is
 * built through the arguments list.
 * 
 * 
 * 
 * @author Ferd
 *
 */
public class ExpressionST {

    public static class Translator {

        protected String toString(Object o) {
            return o.toString();
        }

        public String openParenthesis() {
            return "(";
        }

        public Object closeParenthesis() {
            return ")";
        }

    }

    // operator and arguments....
    protected String operator;
    // ...or just arguments, i.e. we're a parenthesized group. If arguments are ExpressionST,
    // recurse when compiling or executing.
    protected List<Object> arguments = new ArrayList<>();
    // supporting only prefix and infix for now
    protected boolean prefix;

    public static ExpressionST infix(String operator, Object... arguments) {
        ExpressionST ret = new ExpressionST();
        ret.operator = operator;
        if (arguments != null) {
            for (Object arg : arguments) {
                ret.arguments.add(arg);
            }
        }
        return ret;
    }

    public static ExpressionST prefix(String operator, Object... arguments) {
        ExpressionST ret = new ExpressionST();
        ret.operator = operator;
        ret.prefix = true;
        if (arguments != null) {
            for (Object arg : arguments) {
                ret.arguments.add(arg);
            }
        }
        return ret;
    }

    public ExpressionST set(String operator, Object... arguments) {
        this.operator = operator;
        if (arguments != null) {
            for (Object arg : arguments) {
                this.arguments.add(arg);
            }
        }
        return this;
    }

    public boolean noop() {
        return operator == null && arguments.isEmpty();
    }

    public String translate(Translator t) {
        StringBuffer ret = new StringBuffer(128);
        if (!noop()) {
            if (operator == null) {
                ret.append(t.openParenthesis());
            }
            if (operator != null) {
                if (prefix) {
                    ret.append(operator);
                    for (Object arg : arguments) {
                        if (arg instanceof ExpressionST) {
                            ret.append(" " + ((ExpressionST) arg).translate(t));
                        } else {
                            ret.append(" " + arg);
                        }
                    }
                } else {
                    int i = 0;
                    for (Object arg : arguments) {
                        if (arg instanceof ExpressionST) {
                            ret.append(((ExpressionST) arg).translate(t) + (i == arguments.size() - 1 ? "" : (" " + operator + " ")));
                        } else {
                            ret.append(arg + (i == arguments.size() - 1 ? "" : (" " + operator + " ")));
                        }
                        i++;
                    }
                }
            } else {
                for (Object arg : arguments) {
                    if (arg instanceof ExpressionST) {
                        ret.append(((ExpressionST) arg).translate(t));
                    } else {
                        ret.append(arg);
                    }
                }
                
            }

            if (operator == null) {
                ret.append(t.closeParenthesis());
            }
        }
        return ret.toString();
    }

    public static void main(String[] plop) {
        ExpressionST op = infix("<", "a", infix("+", 2, 10));
        System.out.println(op.translate(new Translator()));
    }
}

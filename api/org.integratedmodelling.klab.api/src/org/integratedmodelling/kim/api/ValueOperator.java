package org.integratedmodelling.kim.api;

/**
 * All the possible value operators. Duplicated in Modifier for fixable reasons.
 * 
 * Only operators that admit suffix use without an operand are "total", "averaged" and "summed".
 * 
 * @author ferdinando.villa
 *
 */
public enum ValueOperator {

    BY("by", "by", false, 1),
    DOWN_TO("down to", "down_to", false, 1),
    GREATER(">", "greater_than", false, 1),
    LESS("<", "less_than", false, 1),
    GREATEREQUAL(">=", "greater_or_equal_than", false, 1),
    LESSEQUAL("<=", "less_or_equal_than", false, 1),
    IS("=", "is", false, 1),
    SAMEAS("==", "same_as", false, 1),
    WITHOUT("without", "without", false, 1),
    TOTAL("total", "total", false, 0),
    // next 2 used as modifiers to alter the aggregation strategy from a 'by' or 'where'
    SUMMED("summed", "summed", true, 0),
    AVERAGED("averaged", "averaged", true, 0),
    WHERE("where", "where", false, 1),
    PLUS("plus", "plus", false, 1),
    MINUS("minus", "minus", false, 1),
    TIMES("times", "times", false, 1),
    OVER("over", "over", false, 1);

    public String declaration;
    public String textForm;
    public boolean isModifier;
    public int nArguments;

    ValueOperator(String declaration, String textForm, boolean modifier, int nArguments) {
        this.declaration = declaration;
        this.textForm = textForm;
        this.isModifier = modifier;
        this.nArguments = nArguments;
    }

    public static ValueOperator getOperator(String valueModifier) {
        if ("only".equals(valueModifier)) {
            // 'is' synonym for classifications
            valueModifier = "=";
        }
        for (ValueOperator m : ValueOperator.values()) {
            if (m.declaration.equals(valueModifier)) {
                return m;
            }
        }
        return null;
    }
}

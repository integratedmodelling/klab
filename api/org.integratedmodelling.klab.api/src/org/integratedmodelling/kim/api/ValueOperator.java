package org.integratedmodelling.kim.api;

/**
 * All the possible value operators. Duplicated in Modifier for
 * fixable reasons.
 * 
 * Only operator that admits suffix use without an operand is "total".
 * 
 * @author ferdinando.villa
 *
 */
public enum ValueOperator {
	
	BY("by", "by", false),
	DOWN_TO("down to", "down_to", false),
	GREATER(">", "greater_than", false),
	LESS("<", "less_than", false),
	GREATEREQUAL(">=", "greater_or_equal_than", false),
	LESSEQUAL("<=", "less_or_equal_than", false),
	IS("=", "is", false),
	SAMEAS("==", "same_as", false),
	WITHOUT("without", "without", false),
	TOTAL("total", "total", false),
	// next 2 used as modifiers to alter the aggregation strategy from a 'by' or 'where'
	SUMMED("summed", "summed", true),
	AVERAGED("averaged", "averaged", true),
	WHERE("where", "where", false),
	PLUS("plus", "plus", false),
	MINUS("minus", "minus", false),
	TIMES("times", "times", false),
	OVER("over", "over", false);
	
    public String declaration;
    public String textForm;
    public boolean isModifier;
    
	ValueOperator(String declaration, String textForm, boolean modifier) {
        this.declaration = declaration;
        this.textForm = textForm;
        this.isModifier = modifier;
    }

	public static ValueOperator getOperator(String valueModifier) {
		for (ValueOperator m : ValueOperator.values()) {
			if (m.declaration.equals(valueModifier)) {
				return m;
			}
		}
		return null;
	}
}

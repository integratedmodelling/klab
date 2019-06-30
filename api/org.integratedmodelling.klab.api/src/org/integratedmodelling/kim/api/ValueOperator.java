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
	
	BY("by", "by"),
	DOWN_TO("down to", "down_to"),
	GREATER(">", "greater_than"),
	LESS("<", "less_than"),
	GREATEREQUAL(">=", "greater_or_equal_than"),
	LESSEQUAL("<=", "less_or_equal_than"),
	IS("=", "is"),
	SAMEAS("==", "same_as"),
	WITHOUT("without", "without"),
	TOTAL("total", "total"),
	WHERE("where", "where"),
	PLUS("plus", "plus"),
	MINUS("minus", "minus"),
	TIMES("times", "times"),
	OVER("over", "over");
	
    public String declaration;
    public String textForm;
    

	ValueOperator(String declaration, String textForm) {
        this.declaration = declaration;
        this.textForm = textForm;
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

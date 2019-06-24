package org.integratedmodelling.kim.api;

/**
 * All the possible value operators. Duplicated in Modifier for
 * fixable reasons.
 * 
 * Only operator that does not admit an operand is "total".
 * 
 * @author ferdinando.villa
 *
 */
public enum ValueOperator {
	
	// Observable modifiers
	BY("by"),
	DOWN_TO("down to"),
	GREATER(">"),
	LESS("<"),
	GREATEREQUAL(">="),
	LESSEQUAL("<="),
	IS("="),
	SAMEAS("=="),
	WITHOUT("without"),
	TOTAL("total"),
	WHERE("where"),
	PLUS("plus"),
	MINUS("minus"),
	TIMES("times"),
	OVER("over");
	
    public String[] declaration;

	ValueOperator(String... decl) {
        this.declaration = decl;
    }

	public static ValueOperator getOperator(String valueModifier) {
		for (ValueOperator m : ValueOperator.values()) {
			if (m.declaration[0].equals(valueModifier)) {
				return m;
			}
		}
		return null;
	}
}

package org.integratedmodelling.kim.api;

public enum Modifier {

	// Concept modifiers
	WITHIN("within"),
	OF("of"),
	FOR("for"),
	WITH("with"),
	CAUSED_BY("caused by"),
	ADJACENT_TO("adjacent to"),
	CONTAINED_IN("contained in"),
	CONTAINING("containing"),
	CAUSING("causing"),
	DURING("during"),
    LINKING("linking"),
    TO("to"),
//	AS("as"),
	IN("in"),
	PER("per"),
	
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
	WHERE("where"),
	PLUS("plus"),
	MINUS("minus"),
	TIMES("times"),
	OVER("over");
	
    public String[] declaration;

	Modifier(String... decl) {
        this.declaration = decl;
    }

	public static Modifier getModifier(String valueModifier) {
		for (Modifier m : Modifier.values()) {
			if (m.declaration[0].equals(valueModifier)) {
				return m;
			}
		}
		return null;
	}

}

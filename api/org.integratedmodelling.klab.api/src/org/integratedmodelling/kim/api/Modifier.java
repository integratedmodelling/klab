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

}

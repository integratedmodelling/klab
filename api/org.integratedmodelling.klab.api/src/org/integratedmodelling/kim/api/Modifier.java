package org.integratedmodelling.kim.api;

public enum Modifier {

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
	BY("by"),
	DOWN_TO("down to"),
	AS("as"),
	IN("in"),
	PER("per");
	
    public String[] declaration;

	Modifier(String... decl) {
        this.declaration = decl;
    }

}

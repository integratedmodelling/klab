package org.integratedmodelling.kactors.ui.contentassist;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;

public class KactorsSyntaxHighlighter extends DefaultAntlrTokenToAttributeIdMapper {

	@Override
	protected String calculateId(String tokenName, int tokenType) {
		
        if (tokenName.equals("RULE_TAG")) {
            return KactorsHighlightingConfiguration.VOID_MODEL_ID;
        }
		return super.calculateId(tokenName, tokenType);
	}
	
}

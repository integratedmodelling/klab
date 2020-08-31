package org.integratedmodelling.kactors.ui.contentassist;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;

public class KactorsSyntaxHighlighter extends DefaultAntlrTokenToAttributeIdMapper {

	@Override
	protected String calculateId(String tokenName, int tokenType) {
		
        if (tokenName.equals("RULE_TAG")) {
            return KactorsHighlightingConfiguration.VOID_MODEL_ID;
        } else if (tokenName.equals("RULE_KEY")) {
            return KactorsHighlightingConfiguration.KEY_ID;
        } else if (tokenName.equals("RULE_EMBEDDEDTEXT")) {
            return KactorsHighlightingConfiguration.MARKDOWN_ID;
        } else if (tokenName.equals("RULE_OBSERVABLE")) {
//        	Kim.INSTANCE.de
        	// TODO parse it semantically and use the Kim concept descriptor
        	return KactorsHighlightingConfiguration.MARKDOWN_ID;
        }
		return super.calculateId(tokenName, tokenType);
	}
	
}

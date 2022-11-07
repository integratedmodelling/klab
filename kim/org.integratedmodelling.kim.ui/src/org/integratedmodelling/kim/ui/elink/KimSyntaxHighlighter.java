package org.integratedmodelling.kim.ui.elink;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;

public class KimSyntaxHighlighter extends DefaultAntlrTokenToAttributeIdMapper {

	@Override
	protected String calculateId(String tokenName, int tokenType) {
        if (tokenName.equals("RULE_URN")) {
            return KimHighlightingConfiguration.KNOWN_URN_ID;
        } else if (tokenName.equals("RULE_OPTION_KEY")) {
            return KimHighlightingConfiguration.UNKNOWN_ANNOTATION_ID;
        }
		return super.calculateId(tokenName, tokenType);
	}
	
}

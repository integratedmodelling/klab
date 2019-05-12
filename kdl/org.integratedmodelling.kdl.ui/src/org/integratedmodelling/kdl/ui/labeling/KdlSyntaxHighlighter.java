package org.integratedmodelling.kdl.ui.labeling;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;

public class KdlSyntaxHighlighter extends DefaultAntlrTokenToAttributeIdMapper {

	@Override
	protected String calculateId(String tokenName, int tokenType) {
        if (tokenName.equals("RULE_URN")) {
            return KdlHighlightingConfiguration.KNOWN_URN_ID;
        }
		return super.calculateId(tokenName, tokenType);
	}
	
}

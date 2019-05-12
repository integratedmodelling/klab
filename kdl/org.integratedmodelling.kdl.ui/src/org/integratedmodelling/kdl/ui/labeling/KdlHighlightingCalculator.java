package org.integratedmodelling.kdl.ui.labeling;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.integratedmodelling.kdl.kdl.Urn;
import org.integratedmodelling.kdl.services.KdlGrammarAccess;

import com.google.inject.Inject;

public class KdlHighlightingCalculator extends DefaultSemanticHighlightingCalculator {

    @Inject
    KdlGrammarAccess ga;

    @Override
    public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor, CancelIndicator cancelIndicator) {

        if (resource == null || resource.getParseResult() == null) {
            return;
        }

        // ensure that we only reconsider this when the node is for a position further
        // away than the
        // last, as the loop will return the same nodes multiple times.
        int start = -1;

        ICompositeNode rootNode = resource.getParseResult().getRootNode();

        for (INode node : rootNode.getAsTreeIterable()) {

            if (node.getGrammarElement() instanceof RuleCall) {

                AbstractRule rule = ((RuleCall) node.getGrammarElement()).getRule();
                EObject object = ((RuleCall) node.getGrammarElement()).eContainer();

                if (object instanceof Assignment && node.getOffset() > start) {

                    if (rule.getName().equals("Annotation")) {
                        acceptor.addPosition((start = node.getOffset()), node
                                .getLength(), KdlHighlightingConfiguration.ANNOTATION_ID);
                    } else if (rule.getName().equals("Function")) {
                        acceptor.addPosition((start = node.getOffset()), node
                                .getLength(), KdlHighlightingConfiguration.FUNCTION_ID);
                    } else if (rule.getName().equals("Number")) {
                        acceptor.addPosition((start = node.getOffset()), node
                                .getLength(), KdlHighlightingConfiguration.NUMBER_ID);
                    } else if (rule.getName().equals("VersionNumber")) {
                        acceptor.addPosition((start = node.getOffset()), node
                                .getLength(), KdlHighlightingConfiguration.VERSION_NUMBER_ID);
                    } else if (node.getSemanticElement() instanceof Urn) {

                        String text = node.getText().trim();
                        if (text.startsWith("'") || text.startsWith("\"")) {
                            text = text.substring(1, text.length() - 1);
                        }
                        if (!text.isEmpty()) {
                            acceptor.addPosition((start = node.getOffset()), node
                                    .getLength(), KdlHighlightingConfiguration.LIVE_URN_ID);
                        } else {
                            acceptor.addPosition((start = node.getOffset()), node
                                    .getLength(), KdlHighlightingConfiguration.UNKNOWN_URN_ID);
                        }
                    }
                }
            }
        }
        super.provideHighlightingFor(resource, acceptor, cancelIndicator);
    }

}

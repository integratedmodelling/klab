package org.integratedmodelling.kim.ui.contentassist;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import com.google.inject.Inject;

public class KimProposalAcceptor extends ICompletionProposalAcceptor.Delegate {

    @Inject
    public KimProposalAcceptor(ICompletionProposalAcceptor delegate) {
        super(delegate);
    }

    public void acceptKimProposal(ICompletionProposal proposal) {
        super.accept(proposal);
    }
    
    @Override
    public void accept(ICompletionProposal proposal) {
        // don't accept it
    }
}

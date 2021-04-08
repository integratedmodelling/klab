package org.integratedmodelling.klab.cli.commands.doc;

import java.util.LinkedHashSet;
import java.util.Set;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.documentation.DocumentationTree;
import org.integratedmodelling.klab.documentation.DocumentationTree.View;
import org.integratedmodelling.klab.documentation.Report;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.DocumentationNode;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.StringUtil;

/**
 * Print information about a unit, or an error if the unit is unrecognized.
 * 
 * @author Ferd
 *
 */
public class List implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) throws Exception {

        String ret = "";
        Set<View> views = new LinkedHashSet<>();
        ISubject cotx = session.getState().getCurrentContext();
        if (cotx == null) {
            return "No context, no documentation";
        }

        boolean json = call.getParameters().get("json", false);
        boolean verbose = call.getParameters().get("verbose", false);
        String format = call.getParameters().get("format", "html");

        IReport report = cotx.getScope().getReport();
        DocumentationTree docTree = ((Report) report).getDocumentationTree();
        for (Object o : call.getParameters().get("arguments", java.util.List.class)) {
            try {
                views.add(View.valueOf(o.toString().toUpperCase()));
            } catch (Throwable t) {
                throw new KlabValidationException("don't know how to display documentation view " + o);
            }
        }

        for (View view : views) {
            java.util.List<DocumentationNode> docs = docTree.getView(view, format);
            if (json) {
                ret = JsonUtils.printAsJson(docs);
            } else {
                ret = printGraph(docs, verbose);
            }
        }

        return ret;
    }

    private String printGraph(java.util.List<DocumentationNode> docs, boolean verbose) {
        String ret = "";
        for (DocumentationNode node : docs) {
            ret += printSection(node, verbose, 0);
        }
        return ret;
    }

    private String printSection(DocumentationNode node, boolean verbose, int level) {

        String filler = StringUtil.repeat('.', level * 3);
        String ret = filler + "[" + node.getType() + "]"
                + (node.getTitle() == null
                        ? (node.getBodyText() == null
                                ? ""
                                : (verbose
                                        ? ("\n" + StringUtil.indent(StringUtil.justifyLeft(node.getBodyText(), 60),
                                                level * 3 + 2))
                                        : (" " + StringUtil.abbreviate(node.getBodyText().trim(), 58))))
                        : (" " + node.getTitle()))
                + "\n";

        for (DocumentationNode cid : node.getChildren()) {
            ret += printSection(cid, verbose, level + 1);
        }
        return ret;
    }
}

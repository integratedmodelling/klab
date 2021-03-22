package org.integratedmodelling.klab.clitool.console.commands.doc;

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
import org.integratedmodelling.klab.rest.GraphReference;
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
            GraphReference<DocumentationNode> docs = docTree.getView(view);
            ret += "\n\n" + view + "\n\n" + printGraph(docs);
        }

        return ret;
    }

    private String printGraph(GraphReference<DocumentationNode> docs) {
        String ret = "";
        for (String id : docs.getRootObjectIds()) {
            ret += printSection(id, docs, 0);
        }
        return ret;
    }

    private String printSection(String id, GraphReference<DocumentationNode> docs, int level) {
        String ret = "";
        DocumentationNode node = docs.getObjects().get(id);
        String filler = StringUtil.spaces(level * 3);
        ret += filler + node.getTitle() + "\n";

        switch(node.getType()) {
        case Chart:
            break;
        case Citation:
            break;
        case Figure:
            break;
        case Model:
            break;
        case Paragraph:
            if (node.getSection() != null && node.getSection().getText() != null) {
                ret += StringUtil.indent(StringUtil.justifyLeft(node.getSection().getText(), 60), level * 3) + "\n";
            }
            break;
        case Reference:
            break;
        case Report:
            break;
        case Resource:
            break;
        case Section:
            if (node.getSection() != null && node.getSection().getText() != null) {
                ret += StringUtil.indent(StringUtil.justifyLeft(node.getSection().getText(), 60), level * 3) + "\n";
            }
            break;
        case Table:
            break;
        case View:
            break;
        default:
            break;

        }

        for (String cid : docs.incoming(id)) {
            ret += printSection(cid, docs, level + 1);
        }
        return ret;
    }
}

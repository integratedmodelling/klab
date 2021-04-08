package org.integratedmodelling.klab.cli.commands.reason;
//package org.integratedmodelling.klab.clitool.console.commands.reason;
//
//import java.io.StringWriter;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.integratedmodelling.aligment.AlignmentComponent;
//import org.integratedmodelling.kim.api.IServiceCall;
//import org.integratedmodelling.klab.Extensions;
//import org.integratedmodelling.klab.Namespaces;
//import org.integratedmodelling.klab.api.cli.ICommand;
//import org.integratedmodelling.klab.api.knowledge.IOntology;
//import org.integratedmodelling.klab.api.model.INamespace;
//import org.integratedmodelling.klab.api.runtime.ISession;
//import org.integratedmodelling.klab.exceptions.KlabValidationException;
//import org.integratedmodelling.klab.owl.OWL;
//
//public class Align implements ICommand {
//
//    @Override
//    public Object execute(IServiceCall call, ISession session) throws KlabValidationException {
//
//        AlignmentComponent aligner = Extensions.INSTANCE.getComponentImplementation("alignment",
//                AlignmentComponent.class);
//
//        Set<IOntology> ontologies = new HashSet<>();
//
//        for (Object o : call.getParameters().get("arguments", List.class)) {
//            INamespace namespace = Namespaces.INSTANCE.getNamespace(o.toString());
//            if (namespace != null) {
//                ontologies.add(namespace.getOntology());
//            } else {
//                // TODO this pollutes the internal k.LAB environment. We should use an ad-hoc
//                // OWL manager which imports the internal ones we want to use (which is a lot
//                // harder to do than this).
//                ontologies.add(OWL.INSTANCE.readOntology(o.toString()));
//            }
//        }
//        
//        StringWriter writer = new StringWriter(2048);
//        aligner.describeAlignments(ontologies, writer);
//        return writer.toString();
//    }
//
//}

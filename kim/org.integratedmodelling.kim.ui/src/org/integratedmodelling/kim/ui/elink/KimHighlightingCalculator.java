package org.integratedmodelling.kim.ui.elink;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.kim.Annotation;
import org.integratedmodelling.kim.kim.Concept;
import org.integratedmodelling.kim.kim.ConceptDeclaration;
import org.integratedmodelling.kim.kim.ConceptReference;
import org.integratedmodelling.kim.kim.ConceptStatementBody;
import org.integratedmodelling.kim.kim.DefinitionBody;
import org.integratedmodelling.kim.kim.Function;
import org.integratedmodelling.kim.kim.KimPackage;
import org.integratedmodelling.kim.kim.ModelStatement;
import org.integratedmodelling.kim.kim.Namespace;
import org.integratedmodelling.kim.kim.Urn;
import org.integratedmodelling.kim.kim.Value;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.ConceptDescriptor;
import org.integratedmodelling.kim.model.Kim.UrnDescriptor;
import org.integratedmodelling.kim.services.KimGrammarAccess;
import org.integratedmodelling.kim.validation.KimValidator;

import com.google.inject.Inject;

public class KimHighlightingCalculator extends DefaultSemanticHighlightingCalculator {

	@Inject
	KimGrammarAccess ga;

	@Override
	public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor,
			CancelIndicator cancelIndicator) {

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
						boolean known = false;
						if (node.getSemanticElement() instanceof Annotation) {
							known = Kim.INSTANCE
									.isAnnotationKnown(((Annotation) node.getSemanticElement()).getName().substring(1));
						}
						acceptor.addPosition((start = node.getOffset()), node.getLength(),
								known ? KimHighlightingConfiguration.KNOWN_ANNOTATION_ID
										: KimHighlightingConfiguration.UNKNOWN_ANNOTATION_ID);
					} else if (rule.getName().equals("Function")) {
						boolean known = false;
						if (node.getSemanticElement() instanceof Function) {
							known = Kim.INSTANCE.isFunctionKnown(((Function) node.getSemanticElement()).getName());
						}
						acceptor.addPosition((start = node.getOffset()), node.getLength(),
								known ? KimHighlightingConfiguration.DEFAULT_ID
										: KimHighlightingConfiguration.UNKNOWN_ANNOTATION_ID);
					} else if (rule.getName().equals("Number")) {
						acceptor.addPosition((start = node.getOffset()), node.getLength(),
								KimHighlightingConfiguration.NUMBER_ID);
					} else if (rule.getName().equals("VersionNumber")) {
						acceptor.addPosition((start = node.getOffset()), node.getLength(),
								KimHighlightingConfiguration.VERSION_NUMBER_ID);
					} else if (rule.getName().equals("ModelStatement")
							&& ((ModelStatement) node.getSemanticElement()).getBody() != null) {

						boolean inactive = node.getSemanticElement() instanceof ModelStatement
								&& ((ModelStatement) node.getSemanticElement()).isInactive();

//						if (!inactive && !((ModelStatement) node.getSemanticElement()).getBody().getUrns().isEmpty()) {
//							for (Urn urn : ((ModelStatement) node.getSemanticElement()).getBody().getUrns()) {
//								UrnDescriptor ud = Kim.INSTANCE.getUrnDescriptor(urn.getName());
//								if (ud == null || ud.isDead() || !ud.isAccessible()) {
//									inactive = true;
//								}
//							}
//						}
						if (inactive) {
							acceptor.addPosition((start = node.getOffset()), node.getLength(),
									KimHighlightingConfiguration.VOID_MODEL_ID);
						}
					} else if (node.getSemanticElement() instanceof Concept
							|| node.getSemanticElement() instanceof ConceptReference) {

						String text = null;
						EObject o = node.getSemanticElement();

						if (o instanceof ConceptReference && ((ConceptReference) o).getName() != null) {
							text = ((ConceptReference) o).getName();
						} else if (o instanceof Concept && ((Concept) o).getName() != null
								&& ((Concept) o).getName().getName() != null) {
							text = ((Concept) o).getName().getName();
						}

						if (text != null && !text.isEmpty()) {

							if (!text.contains(":")) {
								Namespace namespace = KimValidator.getNamespace(node.getSemanticElement());
								text = (namespace == null ? "UNDEFINED" : Kim.getNamespaceId(namespace)) + ":" + text;
							}

							ConceptDescriptor cdesc = Kim.INSTANCE.getConceptDescriptor(text);

							// Agh
							boolean isCore = node.getSemanticElement() instanceof Concept
									&& node.getSemanticElement().eContainer() instanceof ConceptDeclaration
									&& node.getSemanticElement().eContainer()
											.eContainer() instanceof ConceptStatementBody
									&& ((ConceptStatementBody) node.getSemanticElement().eContainer().eContainer())
											.isCoreConcept();

							if (isCore) {
								acceptor.addPosition((start = node.getOffset()), node.getLength(),
										KimHighlightingConfiguration.CORE_CONCEPT_ID);
							} else if (cdesc == null || cdesc.is(Type.NOTHING)) {
								acceptor.addPosition((start = node.getOffset()), node.getLength(),
										KimHighlightingConfiguration.DANGER_ID);
							} else if (cdesc.is(Type.OBSERVABLE)) {
								if (cdesc.is(Type.QUALITY)) {
									acceptor.addPosition((start = node.getOffset()), node.getLength(),
											cdesc.is(Type.ABSTRACT) ? KimHighlightingConfiguration.ABSTRACT_QUALITY_ID
													: KimHighlightingConfiguration.QUALITY_ID);
								} else if (cdesc.is(Type.SUBJECT) || cdesc.is(Type.AGENT)) {
									acceptor.addPosition((start = node.getOffset()), node.getLength(),
											cdesc.is(Type.ABSTRACT) ? KimHighlightingConfiguration.ABSTRACT_SUBJECT_ID
													: KimHighlightingConfiguration.SUBJECT_ID);
								} else if (cdesc.is(Type.EVENT)) {
									acceptor.addPosition((start = node.getOffset()), node.getLength(),
											cdesc.is(Type.ABSTRACT) ? KimHighlightingConfiguration.ABSTRACT_EVENT_ID
													: KimHighlightingConfiguration.EVENT_ID);
								} else if (cdesc.is(Type.PROCESS)) {
									acceptor.addPosition((start = node.getOffset()), node.getLength(),
											cdesc.is(Type.ABSTRACT) ? KimHighlightingConfiguration.ABSTRACT_PROCESS_ID
													: KimHighlightingConfiguration.PROCESS_ID);
								} else if (cdesc.is(Type.RELATIONSHIP)) {
									acceptor.addPosition((start = node.getOffset()), node.getLength(),
											cdesc.is(Type.ABSTRACT)
													? KimHighlightingConfiguration.ABSTRACT_RELATIONSHIP_ID
													: KimHighlightingConfiguration.RELATIONSHIP_ID);
								}
							} else if (cdesc.is(Type.TRAIT)) {
								acceptor.addPosition((start = node.getOffset()), node.getLength(),
										cdesc.is(Type.ABSTRACT) ? KimHighlightingConfiguration.ABSTRACT_TRAIT_ID
												: KimHighlightingConfiguration.TRAIT_ID);
							} else if (cdesc.is(Type.ROLE)) {
								acceptor.addPosition((start = node.getOffset()), node.getLength(),
										cdesc.is(Type.ABSTRACT) ? KimHighlightingConfiguration.ABSTRACT_ROLE_ID
												: KimHighlightingConfiguration.ROLE_ID);
							} else if (cdesc.is(Type.CONFIGURATION)) {
								acceptor.addPosition((start = node.getOffset()), node.getLength(),
										cdesc.is(Type.ABSTRACT) ? KimHighlightingConfiguration.ABSTRACT_CONFIGURATION_ID
												: KimHighlightingConfiguration.CONFIGURATION_ID);
							} else if (cdesc.is(Type.EXTENT)) {
								acceptor.addPosition((start = node.getOffset()), node.getLength(),
										cdesc.is(Type.ABSTRACT) ? KimHighlightingConfiguration.ABSTRACT_EXTENT_ID
												: KimHighlightingConfiguration.EXTENT_ID);
							}
						}
					} else if (node.getSemanticElement() instanceof Value
							&& ((Value) node.getSemanticElement()).getExpr() != null) {

						acceptor.addPosition((start = node.getOffset()), node.getLength(),
								KimHighlightingConfiguration.CODE_ID);

					} else if (node.getSemanticElement() instanceof Urn) {

						String text = node.getText().trim();
						if (text.startsWith("'") || text.startsWith("\"")) {
							text = text.substring(1, text.length() - 1);
						}
						if (!text.isEmpty()) {
							UrnDescriptor cdesc = Kim.INSTANCE.getUrnDescriptor(text);
							if (cdesc != null) {
								if (cdesc.isAlive()) {
									acceptor.addPosition((start = node.getOffset()), node.getLength(),
											KimHighlightingConfiguration.LIVE_URN_ID);
								} else if (cdesc.isDead()) {
									acceptor.addPosition((start = node.getOffset()), node.getLength(),
											KimHighlightingConfiguration.DEAD_URN_ID);
								} else if (!cdesc.isKnown()) {
									acceptor.addPosition((start = node.getOffset()), node.getLength(),
											KimHighlightingConfiguration.KNOWN_URN_ID);
								} else if (!cdesc.isAccessible()) {
									acceptor.addPosition((start = node.getOffset()), node.getLength(),
											KimHighlightingConfiguration.UNAUTHORIZED_URN_ID);
								} else {
									acceptor.addPosition((start = node.getOffset()), node.getLength(),
											KimHighlightingConfiguration.UNKNOWN_URN_ID);
								}
							} else {
								acceptor.addPosition((start = node.getOffset()), node.getLength(),
										KimHighlightingConfiguration.UNKNOWN_URN_ID);
							}
						}
					} else if (node.getSemanticElement() instanceof ConceptStatementBody
							&& ((ConceptStatementBody) node.getSemanticElement()).getName() != null) {

						List<INode> nodes = NodeModelUtils.findNodesForFeature(node.getSemanticElement(),
								KimPackage.Literals.CONCEPT_STATEMENT_BODY__NAME);
						if (nodes.size() == 1 && nodes.get(0).getOffset() > start) {
							acceptor.addPosition(start = nodes.get(0).getOffset(), nodes.get(0).getLength(),
									KimHighlightingConfiguration.DEFINITION_ID);
						}

					} else if (node.getSemanticElement() instanceof DefinitionBody
							&& ((DefinitionBody) node.getSemanticElement()).getName() != null) {

						List<INode> nodes = NodeModelUtils.findNodesForFeature(node.getSemanticElement(),
								KimPackage.Literals.DEFINITION_BODY__DEFINE_CLASS);
						if (nodes.size() == 1 && nodes.get(0).getOffset() > start) {
							acceptor.addPosition(start = nodes.get(0).getOffset(), nodes.get(0).getLength(),
									KimHighlightingConfiguration.DEFINITION_CLASS_ID);
						}
						nodes = NodeModelUtils.findNodesForFeature(node.getSemanticElement(),
								KimPackage.Literals.DEFINITION_BODY__NAME);
						if (nodes.size() == 1 && nodes.get(0).getOffset() > start) {
							acceptor.addPosition(start = nodes.get(0).getOffset(), nodes.get(0).getLength(),
									KimHighlightingConfiguration.DEFINITION_ID);
						}

					}
				}
			}
		}
		super.provideHighlightingFor(resource, acceptor, cancelIndicator);
	}

}

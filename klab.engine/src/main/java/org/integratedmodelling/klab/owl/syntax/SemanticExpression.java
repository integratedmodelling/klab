package org.integratedmodelling.klab.owl.syntax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.integratedmodelling.kim.api.BinarySemanticOperator;
import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.SemanticModifier;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.owl.syntax.SemanticScope.Constraint;
import org.integratedmodelling.klab.rest.StyledKimToken;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.Utils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * A semantic expression represented as a graph built one token at a time.
 * 
 * @author Ferd
 *
 */
public class SemanticExpression {

    public class SemanticToken {

        public IConcept concept;
        public IUnit unit;
        public ICurrency currency;
        public Object value;
        public Stack<SemanticToken> previous = new Stack<>();
        private SemanticScope scope;

        SemanticToken() {
        }

        SemanticToken(SemanticToken previous) {
            this.previous.push(previous);
        }

        public boolean isEmpty() {
            return concept == null && unit == null && currency == null && value == null;
        }

        @Override
        public String toString() {
            if (concept != null) {
                return "<" + concept + "> Admits: " + scope;
            } else if (unit != null) {
                return "<" + unit + "> Admits: " + scope;
            } else if (currency != null) {
                return "<" + currency + "> Admits: " + scope;
            } else if (value != null) {
                return "<" + value + "> Admits: " + scope;
            }

            return "<empty> " + scope;
        }

        /**
         * True if the content match the object.
         * 
         * @param o
         * @return
         */
        public boolean is(Object o) {
            return false;
        }

        public SemanticScope getScope() {
            return scope;
        }

        public SemanticToken getGroupParent() {
            for (SemanticLink link : graph.incomingEdgesOf(this)) {
                if (link.observableRole == ObservableRole.GROUP_OPEN) {
                    return graph.getEdgeSource(link);
                }
            }
            for (SemanticLink link : graph.incomingEdgesOf(this)) {
                SemanticToken ret = graph.getEdgeSource(link).getGroupParent();
                if (ret != null) {
                    return ret;
                }
            }
            return null;
        }

        /**
         * True if the context matches the role, i.e. we are linked to another node through it. The
         * link is checked upstream if the closest link is a group.
         * 
         * @param role
         * @return
         */
        public boolean isAs(ObservableRole role) {
            for (SemanticLink link : graph.incomingEdgesOf(this)) {
                if (link.is(ObservableRole.GROUP_OPEN) && role != ObservableRole.GROUP_OPEN) {
                    if (graph.getEdgeSource(link).isAs(role)) {
                        return true;
                    } else if (link.is(role)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public IObservable buildObservable() {
            String declaration = buildDeclaration();
            if (StringUtils.isBlank(declaration) || declaration.contains("?")) {
                return null;
            }
            try {
                return Observables.INSTANCE.declare(declaration);
            } catch (Throwable t) {
                //
            }
            return null;
        }

        public IConcept buildConcept() {
            IObservable observable = buildObservable();
            return observable == null ? null : observable.getType();
        }

        public String buildDeclaration() {
            List<StyledKimToken> ret = new ArrayList<>();
            collectStyledCode(this, ret);
            return joinTokens(ret);
        }

    }

    public class SemanticLink {

        public ObservableRole observableRole;
        public ValueOperator valueOperator;
        public UnarySemanticOperator unarySemanticOperator;
        public BinarySemanticOperator binarySemanticOperator;
        public SemanticModifier semanticModifier;
        public String syntacticElement;

        public boolean is(ObservableRole role) {
            return role.equals(this.observableRole);
        }

        @Override
        public String toString() {
            if (valueOperator != null) {
                return "[" + valueOperator + "]";
            } else if (unarySemanticOperator != null) {
                return "[" + unarySemanticOperator + "]";
            } else if (binarySemanticOperator != null) {
                return "[" + binarySemanticOperator + "]";
            } else if (semanticModifier != null) {
                return "[" + semanticModifier + "]";
            } else if (syntacticElement != null) {
                return "[" + syntacticElement + "]";
            } else if (observableRole != null) {
                return "[" + observableRole + "]";
            }
            return "FUCK";
        }

    }

    private SemanticToken head;
    private SemanticToken current;
    private String error = null;
    private Graph<SemanticToken, SemanticLink> graph = new DefaultDirectedGraph<>(SemanticLink.class);
    private Map<String, Object> data = new HashMap<>();

    private SemanticExpression() {
        this.current = this.head = new SemanticToken();
        this.current.scope = SemanticScope.root();
        this.graph.addVertex(this.head);
    }

    public String joinTokens(List<StyledKimToken> ret) {

        StringBuffer text = new StringBuffer(512);
        StyledKimToken previous = null;
        for (StyledKimToken token : ret) {
            if (token.isNeedsWhitespaceBefore() && text.length() > 0) {
                // there's probably a more readable way to say this
                if (!(previous != null && !previous.isNeedsWhitespaceAfter())) {
                    text.append(" ");
                }
            }
            int a = text.length();
            text.append(token.getValue());
            int b = text.length();
            if (token.getColor() != null && token.getFont() != null) {
                // StyleRange style = new StyleRange();
                // style.start = a;
                // style.length = b - a;
                // style.foreground = getColor(token.getColor());
                // style.fontStyle = getFont(token.getFont());
                // styles.add(style);
            }
            previous = token;
        }

        return text.toString();
    }

    public SemanticToken getCurrent() {
        return current;
    }

    public boolean isError() {
        return this.error != null;
    }

    public String getErrorAndReset() {
        String ret = error;
        this.error = null;
        return ret;
    }

    /**
     * Undo the last accepted token.
     * 
     * @return
     */
    public boolean undo() {

        if (this.current.previous.isEmpty()) {
            return false;
        }
        SemanticToken previous = this.current.previous.pop();
        if (this.current.previous.size() == 0) {
            this.graph.removeVertex(this.current);
        }
        this.current = previous;
        return true;
    }

    public boolean accept(Object token) {

        if (!current.scope.isAdmitted(token, this)) {
            this.error = current.scope.getErrorAndReset();
            return false;
        }

        /**
         * If we get here, the token is accepted and we just have to add a token, link it and make
         * it current, and adjust the undo stack.
         */
        SemanticToken added = new SemanticToken(this.current);
        SemanticLink link = new SemanticLink();

        if (token instanceof IConcept) {

            // TODO set concept and redefine constraints
            if (((IConcept) token).is(Type.OBSERVABLE)) {
                link.observableRole = ObservableRole.OBSERVABLE;
            } else if (((IConcept) token).is(Type.ROLE)) {
                link.observableRole = ObservableRole.ROLE;
            } else if (((IConcept) token).is(Type.PREDICATE)) {
                link.observableRole = ObservableRole.TRAIT;
            }
            added.concept = (IConcept) token;
            added.scope = SemanticScope.scope(added.concept, this);

        } else if (token instanceof ValueOperator) {

            link.valueOperator = (ValueOperator) token;
            added.scope = SemanticScope.scope((ValueOperator) token, this);

        } else if (token instanceof UnarySemanticOperator) {

            link.unarySemanticOperator = (UnarySemanticOperator) token;
            added.scope = SemanticScope.scope((UnarySemanticOperator) token, this);

        } else if (token instanceof BinarySemanticOperator) {

            link.binarySemanticOperator = (BinarySemanticOperator) token;
            added.scope = SemanticScope.scope((BinarySemanticOperator) token, this);

        } else if (token instanceof SemanticModifier) {

            link.semanticModifier = (SemanticModifier) token;
            added.scope = SemanticScope.scope((SemanticModifier) token, this);

        } else if (token instanceof IUnit) {

            // This case is for API use, won't be called from the session builder as units arrive as
            // strings
            link.syntacticElement = "in";
            added.unit = (IUnit) token;
            // TODO validate unit

        } else if (token instanceof ICurrency) {

            // This case is for API use, won't be called from the session builder as units arrive as
            // strings
            link.syntacticElement = "in";
            added.currency = (ICurrency) token;
            // TODO validate currency

        } else if (token instanceof String) {

            if ("(".equals(token)) {
            
                link.observableRole = ObservableRole.GROUP_OPEN;
                added.scope = new SemanticScope();

                /*
                 * TODO if under a WHERE, must have a quality and optional operator. This allows too
                 * much.
                 */

                added.scope.lexicalRealm.addAll(current.scope.getAdmittedLexicalInput());
                added.scope.lexicalRealm.remove(ObservableRole.GROUP_OPEN);
                added.scope.lexicalRealm.add(ObservableRole.GROUP_CLOSE);
                added.scope.logicalRealm.addAll(current.scope.getAdmittedLogicalInput());
                // open groups are always for observables, which are specified in the original
                // scope, so add predicates
                added.scope.logicalRealm.add(Constraint.of(Type.PREDICATE));

            } else if (")".equals(token)) {

                /*
                 * find the parent, push the current to the undo stack, revise the scope and throw
                 * away the local node. No new links are made.
                 */
                SemanticToken parent = this.current.getGroupParent();
                if (parent != null) {
                    parent.scope = SemanticScope.scope(parent.buildConcept(), this);
                }
                parent.previous.push(this.current);
                this.current = parent;
                
                System.out.println(this);
                
                return true;

            } else {
                // TODO according to context, it may be a unit or a currency, to be validated before
                // acceptance. This should be done in the session, not here.
            }

        } else {
            throw new KlabIllegalStateException("internal: semantic token was accepted but is not handled: " + token);
        }

        graph.addVertex(added);
        graph.addEdge(this.current, added, link);
        this.current = added;

        System.out.println(this);
        
        return true;
    }

    public SemanticToken getHead() {
        return head;
    }

    /**
     * Return all the components already defined within the boundaries of the observable being
     * defined in the current lexical context.
     * 
     * @return
     */
    public Set<ObservableRole> getRoles() {
        Set<ObservableRole> ret = EnumSet.noneOf(ObservableRole.class);
        // TODO
        return ret;
    }

    /**
     * Return all the components already defined within the boundaries of the observable being
     * defined in the current lexical context.
     * 
     * @return
     */
    public Set<Object> collect(ObservableRole role) {
        Set<Object> ret = new HashSet<>();
        SemanticToken start = getCurrentLexicalContext();

        return ret;
    }

    /**
     * Return the base traits already specified within the boundaries of the observable being
     * defined in the current lexical context.
     * 
     * @return
     */
    public Set<IConcept> getBaseTraits() {
        Set<IConcept> ret = new HashSet<>();
        return ret;
    }

    /**
     * Return the root token for the current lexical context - either root or the innermost group
     * reachable from current.
     * 
     * @return
     */
    public SemanticToken getCurrentLexicalContext() {

        SemanticToken ret = head;
        /*
         * it's always just one incoming, or zero for head, so no need to break anything. The
         * beginning of a new observable is always an empty token.
         */
        for (SemanticLink link : graph.incomingEdgesOf(current)) {
            if (graph.getEdgeSource(link).isEmpty()) {
                ret = graph.getEdgeSource(link);
            }
        }
        return ret;
    }

    public List<StyledKimToken> getStyledCode() {

        List<StyledKimToken> ret = new ArrayList<>();
        collectStyledCode(head, ret);
        return ret;

    }

    private int collectStyledCode(SemanticToken token, List<StyledKimToken> tokens) {

        int n = tokens.size();

        if (token.concept != null) {
            tokens.add(StyledKimToken.create(token.concept));
        } else if (token.unit != null) {
            tokens.add(StyledKimToken.create(token.unit));
        } else if (token.currency != null) {
            tokens.add(StyledKimToken.create(token.currency));
        } else if (token.value != null) {
            tokens.add(StyledKimToken.create(token.value));
        }

        List<SemanticLink> roles = new ArrayList<>();
        List<SemanticLink> traits = new ArrayList<>();
        // this will never be +1 but OK
        List<SemanticLink> observables = new ArrayList<>();

        for (SemanticLink link : graph.outgoingEdgesOf(token)) {
            if (link.observableRole == ObservableRole.TRAIT) {
                traits.add(link);
            } else if (link.observableRole == ObservableRole.ROLE) {
                roles.add(link);
            } else if (link.observableRole == ObservableRole.OBSERVABLE) {
                observables.add(link);
            }
        }

        for (SemanticLink link : traits) {
            collectStyledCode(graph.getEdgeTarget(link), tokens);
        }
        for (SemanticLink link : roles) {
            collectStyledCode(graph.getEdgeTarget(link), tokens);
        }
        for (SemanticLink link : observables) {
            collectStyledCode(graph.getEdgeTarget(link), tokens);
        }

        for (SemanticLink link : graph.outgoingEdgesOf(token)) {

            if (link.valueOperator != null) {

                tokens.add(StyledKimToken.create(link.valueOperator));
                if (collectStyledCode(graph.getEdgeTarget(link), tokens) == 0) {
                    tokens.add(StyledKimToken.unknown());
                }

            } else if (link.binarySemanticOperator != null) {

                tokens.add(StyledKimToken.create(link.binarySemanticOperator));
                if (collectStyledCode(graph.getEdgeTarget(link), tokens) == 0) {
                    tokens.add(StyledKimToken.unknown());
                }

            } else if (link.semanticModifier != null) {

                tokens.add(StyledKimToken.create(link.semanticModifier));
                if (collectStyledCode(graph.getEdgeTarget(link), tokens) == 0) {
                    tokens.add(StyledKimToken.unknown());
                }

            } else if (link.unarySemanticOperator != null) {

                tokens.add(StyledKimToken.create(link.unarySemanticOperator));
                if (collectStyledCode(graph.getEdgeTarget(link), tokens) == 0) {
                    tokens.add(StyledKimToken.unknown());
                }

            } else if (link.syntacticElement != null) {

                tokens.add(StyledKimToken.create(link.syntacticElement));
                if (collectStyledCode(graph.getEdgeTarget(link), tokens) == 0) {
                    tokens.add(StyledKimToken.unknown());
                }

            } else if (link.observableRole == ObservableRole.GROUP_OPEN) {
                tokens.add(StyledKimToken.create("("));
                if (collectStyledCode(graph.getEdgeTarget(link), tokens) == 0) {
                    tokens.add(StyledKimToken.unknown());
                }
                tokens.add(StyledKimToken.create(")"));
            }
        }

        return tokens.size() - n;
    }

    public Collection<? extends String> getErrors() {
        return this.error == null ? Collections.EMPTY_LIST : Collections.singleton(this.error);
    }

    public Type getObservableType() {
        IConcept concept = buildConcept();
        if (concept != null) {
            return Kim.INSTANCE.getFundamentalType(((Concept) concept).getTypeSet());
        }
        return null;
    }

    /**
     * User data for interactive tracking of contexts, match proposals etc.
     * 
     * @return
     */
    public <T> T getData(String key, Class<T> cls) {
        return Utils.asType(data.get(key), cls);
    }

    public void setData(String key, Object value) {
        this.data.put(key, value);
    }

    public static SemanticExpression create() {
        return new SemanticExpression();
    }

    public String dump() {
        return dump(head, 0);
    }

    private String dump(SemanticToken token, int level) {

        String ret = "";
        String spacer = StringUtil.spaces(level);
        ret += spacer + token + "\n";
        for (SemanticLink link : graph.outgoingEdgesOf(token)) {
            ret += spacer + "  \u2192 " + link + ":\n";
            ret += dump(graph.getEdgeTarget(link), level + 4);
        }
        return ret;
    }

    @Override
    public String toString() {
        return dump(head, 0);
    }

    public IObservable buildObservable() {
        return head.buildObservable();
    }

    public IConcept buildConcept() {
        return head.buildConcept();
    }

    public String buildDeclaration() {
        return head.buildDeclaration();
    }
}

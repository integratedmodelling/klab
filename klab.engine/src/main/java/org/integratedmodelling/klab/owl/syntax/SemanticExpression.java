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
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.rest.StyledKimToken;
import org.integratedmodelling.klab.utils.Utils;
import org.jgraph.graph.GraphCell;
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
        public SemanticToken group;
        public IUnit unit;
        public ICurrency currency;
        public Object value;

        // the scope for further linked tokens
        private SemanticScope scope;

        public boolean isEmpty() {
            return concept == null && unit == null && currency == null && value == null
                    && (group == null || group.isEmpty());
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

    }

    private SemanticToken head;
    private SemanticToken current;
    private String error = null;
    private Stack<SemanticToken> stack = new Stack<>();
    private Graph<SemanticToken, SemanticLink> graph = new DefaultDirectedGraph<>(SemanticLink.class);
    // user data to remember stuff in interactive use.

    private Map<String, Object> data = new HashMap<>();

    private SemanticExpression() {
        this.current = this.head = new SemanticToken();
        this.current.scope = SemanticScope.root();
        this.graph.addVertex(this.head);
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

        if (stack.isEmpty()) {
            return false;
        }

        this.graph.removeVertex(this.current);
        this.current = this.stack.pop();

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
        SemanticToken added = new SemanticToken();
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
            added.concept = (IConcept)token;

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

            } else if (")".equals(token)) {

            } else {
                // TODO according to context, it may be a unit or a currency, to be validated before
                // acceptance.
            }

        } else {
            throw new KlabIllegalStateException(
                    "internal: semantic token was accepted but is not handled: " + token);
        }

        graph.addVertex(added);
        graph.addEdge(this.current, added, link);
        this.stack.push(this.current);
        this.current = added;

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
        boolean openGroup = false;
        
        if (token.concept != null) {
            tokens.add(StyledKimToken.create(token.concept));
        } else if (token.group != null) {
            tokens.add(StyledKimToken.create("("));
            openGroup = true;
        } else if (token.unit != null) {
            tokens.add(StyledKimToken.create(token.unit));
        } else if (token.currency != null) {
            tokens.add(StyledKimToken.create(token.currency));
        } else if (token.value != null) {
            tokens.add(StyledKimToken.create(token.value));
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

            }
        }

        if (openGroup) {
            tokens.add(StyledKimToken.create(")"));
        }
        
        return tokens.size() - n;

        // State s = state.peek();
        //
        // if (s.lexicalScope == ObservableRole.GROUP_OPEN) {
        // ret.add(StyledKimToken.create("("));
        // }
        //
        // List<StyledKimToken> traits = new ArrayList<>();
        // List<StyledKimToken> roles = new ArrayList<>();
        // StyledKimToken observable = null;
        // for (ConceptHolder c : s.concepts) {
        // if (c.resolved().is(Type.TRAIT)) {
        // traits.add(StyledKimToken.create(c.resolved()));
        // } else if (c.resolved().is(Type.ROLE)) {
        // roles.add(StyledKimToken.create(c.resolved()));
        // } else if (c.resolved().is(Type.OBSERVABLE)) {
        // observable = StyledKimToken.create(c.resolved());
        // }
        // }
        //
        // if (!roles.isEmpty()) {
        // Collections.sort(roles, new Comparator<StyledKimToken>(){
        // @Override
        // public int compare(StyledKimToken o1, StyledKimToken o2) {
        // return o1.getValue().compareTo(o2.getValue());
        // }
        // });
        // ret.addAll(roles);
        // }
        //
        // if (!traits.isEmpty()) {
        // Collections.sort(traits, new Comparator<StyledKimToken>(){
        // @Override
        // public int compare(StyledKimToken o1, StyledKimToken o2) {
        // return o1.getValue().compareTo(o2.getValue());
        // }
        // });
        // ret.addAll(traits);
        // }
        //
        // if (s.unaryOperator != null) {
        // ret.add(StyledKimToken.create(s.unaryOperator));
        // if (s.unaryOperatorArgument.defines(Type.OBSERVABLE)) {
        // List<StyledKimToken> arg = s.unaryOperatorArgument.getStyledCode();
        // ret.addAll(arg);
        // } else {
        // ret.add(StyledKimToken.unknown());
        // }
        // if (s.comparisonTarget != null) {
        // ret.add(StyledKimToken.create(s.unaryOperator, true));
        // addStyledTokens(s.unaryOperator, s.comparisonTarget, ret, true);
        // }
        // }
        //
        // if (observable != null) {
        // ret.add(observable);
        // }
        //
        // addStyledTokens(SemanticModifier.OF, state.peek().inherent, ret, false);
        // addStyledTokens(SemanticModifier.WITHIN, state.peek().context, ret, false);
        // addStyledTokens(SemanticModifier.WITH, state.peek().compresent, ret, false);
        // addStyledTokens(SemanticModifier.CAUSED_BY, state.peek().causant, ret, false);
        // addStyledTokens(SemanticModifier.CAUSING, state.peek().caused, ret, false);
        // addStyledTokens(SemanticModifier.FOR, state.peek().goal, ret, false);
        // addStyledTokens(SemanticModifier.ADJACENT_TO, state.peek().adjacent, ret, false);
        //
        // // don't switch the next two
        // addStyledTokens(SemanticModifier.LINKING, state.peek().relationshipSource, ret, false);
        // addStyledTokens(SemanticModifier.TO, state.peek().relationshipTarget, ret, false);
        //
        // if (s.unit != null) {
        // ret.add(StyledKimToken.create("in"));
        // ret.add(StyledKimToken.create(s.unit));
        // } else if (s.currency != null) {
        // ret.add(StyledKimToken.create("in"));
        // ret.add(StyledKimToken.create(s.currency));
        // }
        //
        // if (s.valueOperators != null) {
        //
        // }
        //
        // if (s.lexicalScope == ObservableRole.GROUP_OPEN) {
        // ret.add(StyledKimToken.create(")"));
        // }

    }

    public Collection<? extends String> getErrors() {
        return this.error == null ? Collections.EMPTY_LIST : Collections.singleton(this.error);
    }

    public Type getObservableType() {
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

    // private void addStyledTokens(Object modifier, SemanticToken composer, List<StyledKimToken>
    // ret, boolean alternative) {
    //
    // if (composer == null) {
    // return;
    // }
    //
    // List<StyledKimToken> arg = composer.getStyledCode();
    // ret.add(StyledKimToken.create(modifier, alternative));
    //
    //// if (arg.size() > 1 || (arg.size() == 1 && !composer.defines(Type.OBSERVABLE))) {
    ////
    //// ret.addAll(arg);
    //// if (!composer.defines(Type.OBSERVABLE)) {
    //// ret.add(StyledKimToken.unknown());
    //// }
    ////
    //// } else if (arg.size() > 0) {
    //// ret.addAll(arg);
    //// } else {
    //// ret.add(StyledKimToken.unknown());
    //// }
    // }
}

/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.engine.runtime.expressions;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.groovy.antlr.parser.GroovyLexer;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerOption;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerScope;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IKnowledge;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.documentation.Documentation;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.owl.OWL;
import org.springframework.util.StringUtils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import groovyjarjarantlr.Token;
import groovyjarjarantlr.TokenStreamException;

/**
 * Smarter preprocessor to produce proper Groovy from k.LAB expressions.
 * 
 * Disables Groovy's slashy strings using a trick to prevent the infinitely more important division
 * operator from causing lexer errors.
 * 
 * Usage: create, call process(string), use errors and statistics as necessary. All statistics are
 * reset by another call to process().
 * 
 * Pass the catalog from the scope, the scale and options to specify the contextualization scope.
 * 
 * All states are referenced directly; if in scalar scope, the scale variable must be defined
 * locally and the reference to the state is processed into state.get(scale). Otherwise it's left as
 * is. Overall scale is taken from the context (context.scale).
 * 
 * Observables are parsed from #(...) forms, concepts are automatically recognized using pattern
 * matching. Unknown concepts are substituted with owl:Nothing and an error is logged.
 * 
 * @author Ferd
 *
 */
public class GroovyExpressionPreprocessor {

    private IGeometry domains;
    private INamespace namespace;
    private Set<String> knownIdentifiers;
    private Geometry inferredGeometry;
    Pattern extKnowledge = Pattern.compile("[a-z|\\.]+:[A-Za-z][A-Za-z0-9]*");

    final private static String opchars = "{}[]()+-*/%^&|<>~?:='\"";
    
    static class Lexer extends GroovyLexer {

        int previous = -1;

        public Lexer(Reader in) {
            super(in);
        }

        @Override
        public Token nextToken() throws TokenStreamException {
            Token t = super.nextToken();
            /*
             * cheat Groovy into thinking that it just saw an integer, so that it won't try to
             * interpret slashes as string separators.
             */
            lastSigTokenType = GroovyLexer.NUM_INT;
            return t;
        }
    }

    /*
     * what separates Groovy words and not necessarily k.LAB's.
     */
    static Set<String> delimiters;
    static Set<String> locators;

    static {
        delimiters = new HashSet<>();
        delimiters.add(":");
        delimiters.add(".");
        delimiters.add("-");
        
        locators = new HashSet<>();
        locators.add("n");
        locators.add("ne");
        locators.add("nw");
        locators.add("s");
        locators.add("se");
        locators.add("sw");
        locators.add("e");
        locators.add("w");
        locators.add("previous");
        locators.add("next");
    }

    /*
     * referenced IDs and knowledge resulting from preprocessing.
     */
    Set<String> identifiers = new HashSet<>();
    List<IKnowledge> knowledge = new ArrayList<>();
    List<KimNotification> errors = new ArrayList<>();
    private Set<String> objectIds = new HashSet<>();
    private Set<String> scalarIds = new HashSet<>();
    private Set<String> contextualizers = new HashSet<>();
    IExpression.Scope context;
    private CompilerScope compilerScope;
    private boolean recontextualizeAsMap;
    /*
     * if recontextualizeAsMap is passed, identifiers like id@ctx are translated as id["ctx"] and
     * the ID plus any key encountered are placed here.
     */
    private Map<String, Set<String>> mapIdentifiers = new HashMap<>();
    private boolean ignoreContext;
    private boolean ignored;

    enum TokenType {
        KNOWLEDGE, DEFINE, KNOWN_ID, UNKNOWN_ID, URN, LITERAL_NULL, RECONTEXTUALIZATION, KNOWN_MODEL_OBJECT, NEWLINE, INFERENCE, WHITESPACE
    }

    private static final String CONCEPT_PATTERN = "[a-z][a-z,\\.]+:([A-Z][a-z0-9]+)+";

    private BiMap<String, Object> inherentVariables = HashBiMap.create();
    private Pattern conceptPattern = Pattern.compile(CONCEPT_PATTERN);

    /*
     * ONLY for debugging! Remove at production
     */
    public GroovyExpressionPreprocessor() {
    }

    public GroovyExpressionPreprocessor(INamespace currentNamespace, Set<String> knownIdentifiers, IGeometry geometry,
            IExpression.Scope context, CompilerScope scope, Set<CompilerOption> options) {
        this.domains = geometry;
        this.namespace = currentNamespace;
        this.knownIdentifiers = knownIdentifiers;
        this.context = context;
        this.recontextualizeAsMap = options.contains(CompilerOption.RecontextualizeAsMap);
        this.ignoreContext = options.contains(CompilerOption.IgnoreContext);
        this.ignored = options.contains(CompilerOption.DoNotPreprocess);
    }

    public Geometry getInferredGeometry() {
        return inferredGeometry;
    }

    /**
     * Return the known identifiers that were used in a scalar context
     * 
     * @return all scalar identifiers
     */
    public Set<String> getScalarIdentifiers() {
        return scalarIds;
    }

    /**
     * Return the known identifiers that were used as objects
     * 
     * @return all object identifiers
     */
    public Set<String> getObjectIdentifiers() {
        return objectIds;
    }

    class TokenDescriptor {

        TokenType type;
        String token;

        // method in the next token - set by analyze(), used to check usage before
        // translation
        String method;
        boolean methodCall = false;

        public TokenDescriptor(TokenType type, String token) {
            this.type = type;
            this.token = token;
            if ("isa".equals(token) || "is".equals(token)) {
                this.type = TokenType.INFERENCE;
            }
        }

        public String translate() {
            return translate(context);
        }

        public String toString() {
            return this.token;
        }

        /**
         * Return the literal token value: only used for knowledge for now, so it should be either
         * an identifier (left alone) or a string (wrapped in quotation marks).
         */
        public String encode() {
            String ret = token;
            switch(type) {
            case KNOWLEDGE:
            case URN:
            case KNOWN_MODEL_OBJECT:
                ret = "\"" + token + "\"";
                break;
            case LITERAL_NULL:
                ret = "null";
                break;
            }
            return ret;
        }

        public String translate(IExpression.Scope context) {
            String ret = token;
            switch(type) {
            case INFERENCE:
                ret = "is".equals(ret) ? "<<" : ">>";
                break;
            case KNOWLEDGE:
                ret = translateKnowledge(ret);
                break;
            case DEFINE:
                ret = translateDefine(ret);
                break;
            case KNOWN_ID:
                // if we are translating for a quality context and the var is used with scalar
                // semantics, we just output the var name
                IKimConcept.Type type = getIdentifierType(ret, context);
                boolean canBeScalar = (type == IKimConcept.Type.QUALITY || type == IKimConcept.Type.TRAIT
                        || type == IKimConcept.Type.CLASS) || compilerScope != CompilerScope.Contextual;
                ret = translateParameter(ret, canBeScalar && scalarIds.contains(ret) && !this.methodCall);
                break;
            case URN:
                ret = translateUrn(ret);
                break;
            case KNOWN_MODEL_OBJECT:
                ret = translateModelObject(ret);
                break;
            case LITERAL_NULL:
                ret = "null";
                break;
            case UNKNOWN_ID:
                // TODO see if we need anything
            }
            return ret;
        }
    }

    List<TokenDescriptor> tokenize(String code) {

        List<TokenDescriptor> ret = new ArrayList<>();

        List<List<Token>> groups = new ArrayList<>();
        Lexer lexer = new Lexer(new StringReader(code));
        lexer.setWhitespaceIncluded(true);
        // String ret = "";
        // String remainder = "";

        try {
            lexer.consume();
            List<Token> acc = new ArrayList<>();
            boolean isSpecial = false;
            boolean wasSpecial = false;
            while(true) {

                Token token = lexer.nextToken();
                isSpecial = token != null && (token.getType() == GroovyLexer.IDENT || delimiters.contains(token.getText()));
                boolean isEof = token == null || token.getType() == Token.EOF_TYPE;
                if (!acc.isEmpty() && (!isSpecial || isEof || (isSpecial && !wasSpecial) || isRecognized(acc))) {
                    /*
                     * if last parsed token is a namespace and this is a colon, keep the same group
                     * TODO all this must be brought back to some level of sanity with a true state
                     * processor.
                     */
                    Token last = acc.get(acc.size() - 1);
                    boolean conceptPrefix = ":".equals(token.getText())
                            && Namespaces.INSTANCE.getNamespace(last.getText()) != null;
                    if (!conceptPrefix) {
                        groups.add(acc);
                        acc = new ArrayList<>();
                    }
                }
                if (isEof) {
                    break;
                }
                wasSpecial = isSpecial;
                acc.add(token);
            }

        } catch (Exception e) {
            throw new KlabInternalErrorException(e);
        }

        int line = -1;
        for (List<Token> group : groups) {

            String tk = join(group);
            int tline = lastLine(group);

            if (line < 0) {
                line = tline;
            } else if (tline > line) {
                line = tline;
                ret.add(new TokenDescriptor(TokenType.NEWLINE, "\n"));
            }

            ret.add(classify(tk));
        }

        return ret;
    }

    /**
     * Initial substitutions
     * 
     * @param code
     * @return
     */
    String preprocess(String code) {

        identifiers.clear();
        knowledge.clear();

        /*
         * mysterious, but if I don't do this, the lexer will cut off the first character.
         */
        code = " " + code + " ";

        /*
         * pre-substitute any \] with ] so that we can use the Groovy lexer without error. Don't
         * laugh at the pattern.
         */
        code = code.replaceAll("\\\\\\]", "]");

        // substitute all #(...) declarations with variables
        code = preprocessDeclarations(code);
        // ...then the remaining concepts outside of observable declarations
        code = preprocessConcepts(code);
//        code = preprocessContextualizations(code);

        return code;
    }

    public String process(String code) {

        code = preprocess(code);

        if (this.ignored) {
            return code;
        }

        List<TokenDescriptor> tokens = tokenize(code);
        analyze(tokens);
        return reconstruct(tokens);
    }

    /*
     * for debugging
     */
    public String processVerbose(String code) {

        code = preprocess(code);

        System.out.println("PREPROCESSED: " + code);

        if (this.ignored) {
            return code;
        }

        List<TokenDescriptor> tokens = tokenize(code);

        for (TokenDescriptor token : tokens) {
            System.out.println(" " + token);
        }

        analyze(tokens);
        return reconstruct(tokens);
    }

    private String preprocessDeclarations(String code) {

        StringBuffer ret = new StringBuffer(code.length());
        int level = -1;
        int ndecl = 0;
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            StringBuffer declaration = new StringBuffer(code.length());
            if (c == '#' && i < code.length() - 2 && code.charAt(i + 1) == '(') {
                i += 2;
                for (; i < code.length(); i++) {
                    if (code.charAt(i) == '(') {
                        level++;
                        declaration.append(code.charAt(i));
                    } else if (code.charAt(i) == ')') {
                        if (level == -1) {
                            break;
                        } else {
                            level--;
                            declaration.append(code.charAt(i));
                        }
                    } else {
                        declaration.append(code.charAt(i));
                    }
                }

                if (level >= 0) {
                    errors.add(new KimNotification("concept declaration: unexpected end of input: " + declaration.toString(),
                            Level.SEVERE));
                } else {
                    ret.append(encodeValue(Observables.INSTANCE.declare(declaration.toString())));
                }

            } else {
                ret.append(c);
            }
        }
        return ret.toString();
    }

    private String encodeValue(Object value) {
        if (this.inherentVariables.containsValue(value)) {
            return this.inherentVariables.inverse().get(value);
        }
        String code = "__V" + (this.inherentVariables.size() + 1);
        this.inherentVariables.put(code, value);
        return code;
    }

    private String preprocessConcepts(String code) {

        Matcher matcher = conceptPattern.matcher(code);
        return matcher.replaceAll((result) -> {
            String val = result.group();
            IConcept c = Concepts.INSTANCE.getConcept(val);
            if (c == null) {
                c = OWL.INSTANCE.getNothing();
                errors.add(new KimNotification("Concept " + val + " is not recognized", Level.SEVERE));
            }

            return encodeValue(c);
        });

    }

//    private String preprocessContextualizations(String code) {
//
//        /*
//         * HACK -- if the expression comes from the doc template compiler, we just ignore any @
//         * because they're probably directives. Properly parsing these means building a full parser
//         * just for preprocessing, which is not something we have manpower to do at the moment.
//         */
//        if (code.contains(Documentation.COMMENT_TEXT)) {
//            return code;
//        }
//
//        /*
//         * tokenize into op- or whitespace-separated tokens; record any two tokens separated by
//         * one @, substitute with a contextualize call
//         */
//        List<String> tokens = new ArrayList<>();
//        StringBuffer token = new StringBuffer(256);
//        for (int i = 0; i < code.length(); i++) {
//            char c = code.charAt(i);
//            if (opchars.indexOf(c) >= 0 || Character.isWhitespace(c)) {
//                if (token.length() > 0) {
//                    tokens.add(token.toString());
//                    token.setLength(0);
//                }
//                tokens.add(c + "");
//            } else {
//                token.append(c);
//            }
//        }
//        if (token.length() > 0) {
//            tokens.add(token.toString());
//        }
//
//        token.setLength(0);
//        for (String t : tokens) {
//            if (t.contains("@") && !(t.startsWith("@") || t.endsWith("@"))) {
//                t = t.trim();
//                String[] tt = t.split("@");
//
//                if (recontextualizeAsMap) {
//
//                    // using the dot-form also classifies the identifier usage as non-scalar later.
//                    token.append(tt[0] + "." + tt[1]);
//
//                    if (!this.mapIdentifiers.containsKey(tt[0])) {
//                        this.mapIdentifiers.put(tt[0], new HashSet<>());
//                    }
//                    this.mapIdentifiers.get(tt[0]).add(tt[1]);
//
//                } else {
//
//                    token.append(" _recontextualize(\"" + tt[0] + "\", \"" + tt[1] + "\")");
//
//                    /*
//                     * record contextualizers
//                     */
//                    String[] pp = tt[1].split(",");
//                    for (String p : pp) {
//                        while(Character.isDigit(p.charAt(p.length() - 1))) {
//                            p = p.substring(0, p.length() - 1);
//                        }
//                        contextualizers.add(p);
//                    }
//                }
//
//            } else {
//                token.append(t);
//            }
//        }
//
//        return token.toString();
//
//    }

    public IKimConcept.Type getIdentifierType(String identifier, IExpression.Scope context) {

        if (context == null) {
            return IKimConcept.Type.PRIORITY;
        }

        if (identifier.equals("self")) {
            return context.getReturnType();
        }
        IKimConcept.Type ret = context.getIdentifierType(identifier);
        if (ret != null) {
            return ret;
        }
        return IKimConcept.Type.OBSERVABLE;
    }

    private String reconstruct(List<TokenDescriptor> tokens) {

        String ret = "";

        /*
         * if we have a known quality id followed by @, translate it to state.getProxy(locator)
         * which can be further located reacting to the << operator, then translate the @ operator
         * to <<. After @ turn any naked identifier recognizable as a locator into a string, or
         * leave as is. If the @ does not follow a quality var, raise an error.
         */

        for (TokenDescriptor t : tokens) {
            ret += t.type == TokenType.NEWLINE ? "\n" : t.translate();
        }

        return ret;
    }

    private void analyze(List<TokenDescriptor> tokens) {

        TokenDescriptor current = null;
        for (TokenDescriptor t : tokens) {
            if (t.type == TokenType.KNOWN_ID) {
                current = t;
            } else if (t.type == TokenType.UNKNOWN_ID) {
                if (current != null && t.token.trim().startsWith(".")) {
                    current.methodCall = true;
                    current.method = t.token.trim().substring(1);
                }
                current = null;
            }
        }

        for (TokenDescriptor t : tokens) {
            if (t.type == TokenType.KNOWN_ID
                    || (t.type == TokenType.UNKNOWN_ID && context != null && context.getIdentifiers().contains(t.token.trim()))) {
                identifiers.add(t.token.trim());
                if (t.methodCall) {
                    this.objectIds.add(t.token.trim());
                } else {
                    this.scalarIds.add(t.token.trim());
                }
            }
        }
    }

    private int lastLine(List<Token> group) {
        int line = -1;
        for (Token t : group) {
            line = t.getLine();
        }
        return line;
    }

    private boolean isRecognized(List<Token> acc) {
        return classify(join(acc)).type != TokenType.UNKNOWN_ID;
    }

    private String join(List<Token> group) {
        String ret = "";
        for (Token t : group) {
            ret += getText(t);
        }
        return ret;
    }

    private String getText(Token t) {

        switch(t.getType()) {
        case GroovyLexer.STRING_LITERAL:
            String delimiter = getDelimiter(t);
            return delimiter + t.getText() + delimiter;
        }
        return t.getText();
    }

    private String getDelimiter(Token t) {
        return t.getText().contains("\n") ? "\"\"\"" : "\"";
    }

    private TokenDescriptor classify(String currentToken) {

        /*
         * known ones. Also ensure that "space" and "time" go through unmodified unless the domains
         * do not know them.
         */
        if (currentToken.equals("unknown")) {
            return new TokenDescriptor(TokenType.LITERAL_NULL, currentToken);
        }

        if ("@".equals(currentToken)) {
            return new TokenDescriptor(TokenType.RECONTEXTUALIZATION, currentToken);
        }

        if (knownIdentifiers != null && knownIdentifiers.contains(currentToken)) {
            return new TokenDescriptor(TokenType.KNOWN_ID, currentToken);
        }

        IKnowledge k = null;
        IKimObject o = null;

        if (currentToken.contains(":")) {
            if (StringUtils.countOccurrencesOf(currentToken, ":") == 3 && !currentToken.endsWith(":")
                    && !StringUtils.containsWhitespace(currentToken)) {
                return new TokenDescriptor(TokenType.URN, currentToken);
            } else if ((k = Concepts.INSTANCE.getConcept(currentToken)) != null) {
                return new TokenDescriptor(TokenType.KNOWLEDGE, k.toString());
            }
        }

        if (namespace != null) {
            if (namespace.getSymbolTable().get(currentToken) != null) {
                return new TokenDescriptor(TokenType.DEFINE, currentToken);
            }
            if ((k = namespace.getOntology().getConcept(currentToken)) != null) {
                return new TokenDescriptor(TokenType.KNOWLEDGE, k.toString());
            }
            if (!namespace.isProjectKnowledge() && namespace.getProject() != null
                    && namespace.getProject().getUserKnowledge() != null
                    && namespace.getProject().getUserKnowledge().getOntology().getConcept(currentToken) != null) {
                return new TokenDescriptor(TokenType.KNOWLEDGE,
                        namespace.getProject().getUserKnowledge().getOntology().getConcept(currentToken).toString());
            }
            if ((k = namespace.getOntology().getProperty(currentToken)) != null) {
                return new TokenDescriptor(TokenType.KNOWLEDGE, k.toString());
            }
            if ((o = namespace.getObject(currentToken)) != null) {
                return new TokenDescriptor(TokenType.KNOWN_MODEL_OBJECT, o.getName());
            }
        }

        if (currentToken.contains(".")) {
            if ((o = Resources.INSTANCE.getModelObject(currentToken)) != null) {
                return new TokenDescriptor(TokenType.KNOWN_MODEL_OBJECT, o.getName());
            }
        }

        if (compilerScope != CompilerScope.Contextual && isValidIdentifier(currentToken)) {
            return new TokenDescriptor(TokenType.KNOWN_ID, currentToken);
        }

        if (currentToken.trim().isEmpty()) {
            return new TokenDescriptor(TokenType.WHITESPACE, currentToken);
        }

        return new TokenDescriptor(TokenType.UNKNOWN_ID, currentToken);
    }

    /*
     * Used only when context is null to discriminate identifiers without a list of known ones. The
     * definition is quite restrictive.
     */
    private boolean isValidIdentifier(String token) {
        for (int i = 0; i < token.length(); i++) {
            char c = token.charAt(i);
            if ((c < 'a' || c > 'z') && !(c == '_')) {
                return false;
            }
        }
        return true;
    }

    private String translateModelObject(String o) {
        return "_mmanager.findModelObject(\"" + o + "\")";
    }

    private String translateDefine(String currentToken) {
        return "_ns.getSymbolTable().get(\"" + currentToken + "\")";
    }

    private String translateParameter(String currentToken, boolean isScalar) {
        boolean isMapIdentifier = mapIdentifiers.containsKey(currentToken);
        boolean includeLiterally = this.ignoreContext
                && (this.context == null || !this.context.getStateIdentifiers().contains(currentToken));
        return (isScalar || isMapIdentifier || includeLiterally) ? currentToken : "_p.get(\"" + currentToken + "\")";
    }

    private String translateKnowledge(String k) {
        return encodeValue(Concepts.INSTANCE.getConcept(k));
    }

    private String translateUrn(String k) {
        return "_getUrn(\"" + k + "\")";
    }

    public List<KimNotification> getErrors() {
        return errors;
    }

    /**
     * All the identifiers encountered. TODO return a structure to show how they have been used - if
     * they are followed by assignment operators, other ops or method calls.
     * 
     * @return identifiers encountered during preprocessing.
     */
    public Collection<String> getIdentifiers() {
        return identifiers;
    }

    /**
     * All contextualizers encountered, divided up and without distance locators.
     * 
     * @return
     */
    public Set<String> getContextualizers() {
        return contextualizers;
    }

    public static void main(String[] args) {
        GroovyExpressionPreprocessor processor = new GroovyExpressionPreprocessor();
        processor.processVerbose("elevation@nw");
    }

    public Map<String, Set<String>> getMapIdentifiers() {
        return mapIdentifiers;
    }

    public Map<String, Object> getVariables() {
        return inherentVariables;
    }
}

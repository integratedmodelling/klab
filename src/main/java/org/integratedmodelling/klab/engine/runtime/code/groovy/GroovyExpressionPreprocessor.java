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
package org.integratedmodelling.klab.engine.runtime.code.groovy;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.codehaus.groovy.antlr.parser.GroovyLexer;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.kim.model.Geometry;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IKnowledge;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.springframework.util.StringUtils;
import groovyjarjarantlr.Token;
import groovyjarjarantlr.TokenStreamException;

/**
 * Smarter preprocessor to produce proper Groovy from Thinklab expressions. Should remove all needs
 * to bananize code and produce error messages at compilation if unknown identifiers are used.
 * 
 * Disables Groovy's slashy strings using a trick to prevent the infinitely more important division
 * operator from causing lexer errors.
 * 
 * Usage: create, call process(string), use errors and statistics as necessary. All statistics are
 * reset by another call to process().
 *
 * Recognizes known structures without spaces:
 * 
 * name-name-name -> ID if known. any name -> local knowledge in namespace or define; n.n ->
 * namespace or model object if known; n:m -> foreign knowledge if known
 * 
 * TODO: support contextualized variables (using @ and still unidentified locators for time and
 * space neighborhoods)
 * 
 * Same structures will be left as in Groovy if unknown except:
 * 
 * legitimate concept names with no correspondence in known ontologies; identifiers that do not
 * correspond to known knowledge or identifiers.
 * 
 * These will generate errors that can be retrieved by getErrors() - no exceptions are thrown by
 * process() except on internal errors by Groovy lexer.
 * 
 * @author Ferd
 *
 */
public class GroovyExpressionPreprocessor {

  private IGeometry   domains;
  private INamespace  namespace;
  private Set<String> knownIdentifiers;
  private Geometry    inferredGeometry;
  Pattern             extKnowledge = Pattern.compile("[a-z|\\.]+:[A-Za-z][A-Za-z0-9]*");

  static class Lexer extends GroovyLexer {

    int previous = -1;

    public Lexer(Reader in) {
      super(in);
    }

    @Override
    public Token nextToken() throws TokenStreamException {
      // Token t = null;
      // try {
      // t = super.nextToken();
      // } catch (TokenStreamRecognitionException e) {
      // // fux - this happens only on some machines, reporting a 0xffff
      // // invalid char at the very end of parsing. Unsure if
      // // catching it will cause other issues. Basically this will
      // // stop parsing without warning.
      // return null;
      // }
      Token t = super.nextToken();
      // cheat Groovy into thinking that it just saw an integer, so that it won't
      // try
      // to interpret slashes as string separators.
      lastSigTokenType = GroovyLexer.NUM_INT;
      return t;
    }
  }

  /*
   * what separates Groovy words and not necessarily Thinklab's.
   */
  static Set<String> delimiters;

  static {
    delimiters = new HashSet<>();
    delimiters.add(":");
    delimiters.add(".");
    delimiters.add("-");
  }

  /*
   * referenced IDs and knowledge resulting from preprocessing.
   */
  Set<String>           identifiers        = new HashSet<>();
  List<IKnowledge>      knowledge          = new ArrayList<>();
  List<KimNotification> errors             = new ArrayList<>();
  private Set<String>   objectIds          = new HashSet<>();
  private Set<String>   scalarIds          = new HashSet<>();
  List<TokenDescriptor> tokens             = new ArrayList<>();

  static final int      KNOWLEDGE          = 1;
  static final int      DEFINE             = 2;
  static final int      KNOWN_ID           = 3;
  static final int      UNKNOWN_ID         = 4;
  static final int      URN                = 5;
  static final int      LITERAL_NULL       = 6;
  static final int      KNOWN_DOMAIN       = 7;
  static final int      KNOWN_MODEL_OBJECT = 8;

  public GroovyExpressionPreprocessor(INamespace currentNamespace, Set<String> knownIdentifiers,
      IGeometry geometry) {
    this.domains = geometry;
    this.namespace = currentNamespace;
    this.knownIdentifiers = knownIdentifiers;
  }

  public Geometry getInferredGeometry() {
    return inferredGeometry;
  }

  /**
   * Return the known identifiers that were used in a scalar context
   * 
   * @return
   */
  public Set<String> getScalarIdentifiers() {
    return scalarIds;
  }

  /**
   * Return the known identifiers that were used as objects
   * 
   * @return
   */
  public Set<String> getObjectIdentifiers() {
    return objectIds;
  }

  class TokenDescriptor {

    int     type;
    String  token;

    // method in the next token - set by analyze(), used to check usage before translation
    String  method;
    boolean methodCall = false;

    public TokenDescriptor(int type, String token) {
      this.type = type;
      this.token = token;
    }

    public String translate() {
      return translate(null);
    }

    public String toString() {
      return this.token;
    }

    public String translate(IRuntimeContext context) {
      String ret = token;
      switch (type) {
        case KNOWLEDGE:
          ret = translateKnowledge(ret);
          break;
        case DEFINE:
          ret = translateDefine(ret);
          break;
        case KNOWN_ID:
          // if we are translating for a quality context and the var is used with scalar
          // semantics, we just output the var name
          if (!(context != null && context.getArtifactType() == IKimConcept.Type.QUALITY
              && !methodCall)) {
            ret = translateParameter(ret);
          }
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
        case KNOWN_DOMAIN:
          // TODO see if we need anything
      }
      return ret;
    }
  }

  public String process(String code) {

    identifiers.clear();
    knowledge.clear();

    /*
     * mysterious, but if I don't do this, the lexer will cut off the first character.
     */
    code = " " + code + " ";

    // this.originalCode = code;

    /*
     * pre-substitute any \] with ] so that we can use the Groovy lexer without error. Don't laugh
     * at the pattern.
     */
    code = code.replaceAll("\\\\\\]", "]");

    List<List<Token>> groups = new ArrayList<>();
    Lexer lexer = new Lexer(new StringReader(code));
    lexer.setWhitespaceIncluded(true);
    String ret = "";
    // String remainder = "";

    try {
      lexer.consume();
      List<Token> acc = new ArrayList<>();
      boolean isSpecial = false;
      boolean wasSpecial = false;
      while (true) {

        Token token = lexer.nextToken();
        isSpecial = token != null
            && (token.getType() == GroovyLexer.IDENT || delimiters.contains(token.getText()));
        boolean isEof = token == null || token.getType() == Token.EOF_TYPE;
        if (!acc.isEmpty()
            && (!isSpecial || isEof || (isSpecial && !wasSpecial) || isRecognized(acc))) {
          groups.add(acc);
          acc = new ArrayList<>();
        }
        if (isEof) {
          break;
        }
        wasSpecial = isSpecial;
        acc.add(token);
      }

    } catch (Exception e) {
      throw new KlabRuntimeException(e);
    }

    int line = -1;
    for (List<Token> group : groups) {

      String tk = join(group);
      int tline = lastLine(group);

      if (line < 0) {
        line = tline;
      } else if (tline > line) {
        line = tline;
        ret += "\n";
      }

      //
      // if (!tk.trim().isEmpty()) {
      // System.out.println(tk);
      // }
      /*
       * if recognized, add its substituted value; else add as is. If it's an identifier and it's
       * not recognized, add an error to the list.
       */
      TokenDescriptor cls = classify(tk);

      /*
       * report on all unknown identifiers
       */
      if (cls.type == UNKNOWN_ID) {
        for (Token t : group) {
          if (t.getType() == GroovyLexer.IDENT) {
            // NAH - this also flags legitimate method calls, so no joy unless
            // we really parse the
            // thing.
            // errors.add(new CompileError("unknown identifier in expression:
            // " + t.getText(),
            // 0));
          }
        }
      }

      tokens.add(cls);
    }

    analyze(tokens);

    return reconstruct(tokens);
  }

  private String reconstruct(List<TokenDescriptor> tokens) {
    String ret = "";
    for (TokenDescriptor t : tokens) {
      ret += t.translate();
    }
    return ret;
  }

  private void analyze(List<TokenDescriptor> tokens) {

    TokenDescriptor current = null;
    for (TokenDescriptor t : tokens) {
      if (t.type == KNOWN_ID) {
        current = t;
      } else if (t.type == UNKNOWN_ID) {
        if (current != null && t.token.trim().startsWith(".")) {
          current.methodCall = true;
          current.method = t.token.trim().substring(1);
        }
        current = null;
      }
    }

    for (TokenDescriptor t : tokens) {
      if (t.type == KNOWN_ID) {
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
    return classify(join(acc)).type != UNKNOWN_ID;
  }

  private String join(List<Token> group) {
    String ret = "";
    for (Token t : group) {
      ret += getText(t);
    }
    return ret;
  }

  private String getText(Token t) {

    switch (t.getType()) {
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
     * known ones. Also ensure that "space" and "time" go through unmodified unless the domains do
     * not know them.
     */
    if (currentToken.equals("unknown")) {
      return new TokenDescriptor(LITERAL_NULL, currentToken);
    }

    if ((currentToken.equals("space")
        && (domains != null && domains.getDimension(Type.SPACE) != null))
        || (currentToken.equals("time")
            && (domains != null && domains.getDimension(Type.TIME) != null))) {
      return new TokenDescriptor(KNOWN_DOMAIN, currentToken);
    }

    if (knownIdentifiers != null && knownIdentifiers.contains(currentToken)) {
      return new TokenDescriptor(KNOWN_ID, currentToken);
    }

    IKnowledge k = null;
    IKimObject o = null;

    if (currentToken.contains(":")) {
      if (StringUtils.countOccurrencesOf(currentToken, ":") == 3 && !currentToken.endsWith(":")
          && !StringUtils.containsWhitespace(currentToken)) {
        return new TokenDescriptor(URN, currentToken);
      } else if ((k = Concepts.INSTANCE.getConcept(currentToken)) != null) {
        return new TokenDescriptor(KNOWLEDGE, k.toString());
      }
    }

    if (namespace != null) {
      if (namespace.getSymbolTable().get(currentToken) != null) {
        return new TokenDescriptor(DEFINE, currentToken);
      }
      if ((k = namespace.getOntology().getConcept(currentToken)) != null) {
        return new TokenDescriptor(KNOWLEDGE, k.toString());
      }
      if (!namespace.isProjectKnowledge() && namespace.getProject() != null
          && namespace.getProject().getUserKnowledge() != null && namespace.getProject()
              .getUserKnowledge().getOntology().getConcept(currentToken) != null) {
        return new TokenDescriptor(KNOWLEDGE, namespace.getProject().getUserKnowledge()
            .getOntology().getConcept(currentToken).toString());
      }
      if ((k = namespace.getOntology().getProperty(currentToken)) != null) {
        return new TokenDescriptor(KNOWLEDGE, k.toString());
      }
      if ((o = namespace.getObject(currentToken)) != null) {
        return new TokenDescriptor(KNOWN_MODEL_OBJECT, o.getName());
      }
    }

    if (currentToken.contains(".")) {
      if ((o = Resources.INSTANCE.getModelObject(currentToken)) != null) {
        return new TokenDescriptor(KNOWN_MODEL_OBJECT, o.getName());
      }
    }

    return new TokenDescriptor(UNKNOWN_ID, currentToken);
  }

  private String translateModelObject(String o) {
    return "_mmanager.findModelObject(\"" + o + "\")";
  }

  private String translateDefine(String currentToken) {
    return "_ns.getSymbolTable().get(\"" + currentToken + "\")";
  }

  private String translateParameter(String currentToken) {
    return "_p.get(\"" + currentToken + "\")";
  }

  private String translateKnowledge(String k) {
    return "_getConcept(\"" + k + "\")";
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
}

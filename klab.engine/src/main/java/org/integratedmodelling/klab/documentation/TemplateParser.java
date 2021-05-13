package org.integratedmodelling.klab.documentation;

import java.io.StringReader;

import org.integratedmodelling.contrib.jtopas.Flags;
import org.integratedmodelling.contrib.jtopas.ReaderSource;
import org.integratedmodelling.contrib.jtopas.StandardTokenizer;
import org.integratedmodelling.contrib.jtopas.StandardTokenizerProperties;
import org.integratedmodelling.contrib.jtopas.Token;
import org.integratedmodelling.contrib.jtopas.Tokenizer;
import org.integratedmodelling.contrib.jtopas.TokenizerException;
import org.integratedmodelling.contrib.jtopas.TokenizerProperties;
import org.integratedmodelling.klab.documentation.Documentation.DocumentationDirective;
import org.integratedmodelling.klab.documentation.Documentation.TemplateImpl;

/**
 * Auxiliary parser that handles annotations only. Used in contexts where the regular k.IM parser
 * cannot be used, such as in documentation template files.
 *
 * Use the static methods to parse a line or a source file. Recognizes:
 * 
 * Calls starting with @ and a function, parsed as is until closing parenthesis; Expressions within
 * matching brackets; All the rest (as Groovy Gstrings)
 * 
 * creates a list of these elements to add to Groovy actions before parsing.
 * 
 * @author ferdinando.villa
 *
 */
public class TemplateParser {

    static TokenizerProperties props;

    /*
     * Token identifiers
     */
    private static final int ANNOTATION = 1;
    private static final int OPEN_PARENTHESIS = 2;
    private static final int CLOSED_PARENTHESIS = 3;
    private static final int OPEN_BRACKET = 4;
    private static final int CLOSED_BRACKET = 5;

    static void initialize() {
        if (props == null) {
            props = new StandardTokenizerProperties();
            props.setParseFlags(Flags.F_RETURN_WHITESPACES);
            props.setSeparators(null);
            props.addPattern("@[A-Za-z][A-Za-z]*", ANNOTATION);
            props.addSpecialSequence("(", OPEN_PARENTHESIS);
            props.addSpecialSequence(")", CLOSED_PARENTHESIS);
            props.addSpecialSequence("[", OPEN_BRACKET);
            props.addSpecialSequence("]", CLOSED_BRACKET);
        }
    }

    /**
     * Call after reading an opening token such as an open parenthesis and read until the matching
     * closing token is read, allowing for more opening tokens. Return textual value of anything
     * between the tokens, excluding the tokens themselves.
     * 
     * @param tokenType
     * @return
     */
    public static String readToMatching(int openingToken, int closingToken, Tokenizer tokenizer, TemplateImpl template) {

        String ret = "";
        int level = 1;

        while(tokenizer.hasMoreTokens()) {

            Token token = tokenizer.nextToken();
            String value = token.getImage();

            if (token.getCompanion() != null) {
                if (token.getCompanion().equals(openingToken)) {
                    level++;
                } else if (token.getCompanion().equals(closingToken)) {
                    level--;
                    if (level == 0) {
                        break;
                    }
                } else if (token.getCompanion().equals(ANNOTATION)) {
                    // just substitute report call in expressions
                    ret += "_section." + value.substring(1);
                } else {
                    ret += value;
                }
            } else {
                ret += value;
            }
        }

        if (level > 0) {
            template.getErrors().add("unmatched " + (closingToken == CLOSED_BRACKET ? "bracket" : "parenthesis"));
        }

        return ret;
    }

    /**
     * Parse an annotation at the beginning of the line. Return the parsed annotation and the
     * remainder of the line after it.
     * 
     * @param line
     * @return
     */
    public static TemplateImpl parse(TemplateImpl ret, String line) {

        initialize();

        String currentAnnotation = null;
        String currentText = "";
        String innerAnnotation = null;

        try (Tokenizer tokenizer = new StandardTokenizer(props)) {
            tokenizer.setSource(new ReaderSource(new StringReader(line)));

            while(tokenizer.hasMoreTokens()) {

                Token token = tokenizer.nextToken();
                String value = token.getImage();

                if (innerAnnotation != null && token.getType() == Token.SPECIAL_SEQUENCE
                        && token.getCompanion().equals(OPEN_PARENTHESIS)) {
                    
                    ret.addCall(innerAnnotation, readToMatching(OPEN_PARENTHESIS, CLOSED_PARENTHESIS, tokenizer, ret));
                    innerAnnotation = null;
                    
                } else if (currentAnnotation != null) {

                    if (token.getCompanion() != null && !token.getCompanion().equals(OPEN_PARENTHESIS)) {
                        ret.getErrors().add("arguments in parentheses are expected after report calls");
                    }

                    ret.addText(currentText);
                    currentText = "";
                    ret.addCall(currentAnnotation, readToMatching(OPEN_PARENTHESIS, CLOSED_PARENTHESIS, tokenizer, ret));
                    currentAnnotation = null;

                } else {

                    switch(token.getType()) {
                    case Token.PATTERN:
                        if (token.getCompanion() != null && token.getCompanion().equals(ANNOTATION)) {
                            currentAnnotation = value;
                        }
                        break;
                    case Token.SPECIAL_SEQUENCE:
                        if (token.getCompanion() != null && token.getCompanion().equals(OPEN_BRACKET)
                                && !(!currentText.isEmpty() && currentText.endsWith("\\"))) {
                            ret.addText(currentText);
                            currentText = "";
                            ret.addCode(readToMatching(OPEN_BRACKET, CLOSED_BRACKET, tokenizer, ret));
                        } else {
                            currentText += value;
                        }
                        break;
                    default:
                        if (value.contains("@") && !value.endsWith("@")) {
                            // annotation without opening whitespace
                            String[] vv = value.split("@");
                            currentText += vv[0];
                            ret.addText(currentText);
                            currentText = "";
                            for (int i = 1; i < vv.length - 1; i++) {
                                readAnnotation(ret, vv[i]);
                            }
                            innerAnnotation = vv[vv.length - 1];
                        } else {
                            currentText += value;
                        }
                    }
                }
            }
        } catch (TokenizerException e) {
            ret.addError(e.getMessage());
        }

        if (!currentText.isEmpty()) {
            ret.addText(currentText);
        }

        return ret;
    }

    private static void readAnnotation(TemplateImpl ret, String string) {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args) {

        Documentation diobue = new Documentation();

        TemplateImpl template = diobue.parse("Zio petardo@cite(Diocan) porcavvia @cite(Madonna)@cite(Scurreggia)");

        // TemplateImpl template = diobue.parse("@section('Methods/Socio-environmental exchange')
        // \n" + "\n"
        // + " The default model for a social-environmental exchange was used. In this model, all
        // \n"
        // + " ecosystem service flows are gathered in the context and aggregated to provide \n"
        // + " figures. The model is not specific of any ES: flows are recognized based on the\n"
        // + " roles they play, and their throughput extracted independent of the specific ES\n"
        // + " being modeled.\n" + "\n"
        // + " [for (i in 1..10) { @separator(); @paragraph('ahaha'); }]\n" + "\n"
        // + " @separator(bold: true)\n" + "\n"
        // + " The model description should come after that of its inputs. Each independent\n"
        // + " piece of text is automatically wrapped in a paragraph.\n" + " ");

        for (DocumentationDirective section : template.getSections()) {
            System.out.println(section.getType() + "\n--------------------------------------\n" + section.getCode()
                    + "\n--------------------------------------");
        }
    }
}

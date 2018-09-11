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
import org.integratedmodelling.klab.api.documentation.IDocumentation.Template;
import org.integratedmodelling.klab.documentation.Documentation.TemplateImpl;

/**
 * Auxiliary parser that handles annotations only. Used in contexts where the
 * regular k.IM parser cannot be used, such as in documentation template files.
 *
 * Use the static methods to parse a line or a source file. Recognizes:
 * 
 * Calls starting with @ and a function, parsed as is until closing parenthesis;
 * Expressions within matching brackets; All the rest (as Groovy Gstrings)
 * 
 * creates a list of these elements to add to Groovy actions before parsing.
 * 
 * @author ferdinando.villa
 *
 */
public class TemplateParser {

	static TokenizerProperties props;
	// static Set<String> sectionAnnotations = new HashSet<>();

	static {
		// sectionAnnotations.add("title");
		// sectionAnnotations.add("quote");
		// sectionAnnotations.add("author");
		// sectionAnnotations.add("description");
		// sectionAnnotations.add("methods");
		// sectionAnnotations.add("results");
		// sectionAnnotations.add("reference");
		// sectionAnnotations.add("anchor");
	}

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
			// props.addPattern("##*", HEADER_MARKER);
			// props.addBlockComment("/*", "*/");
			// props.addLineComment("//");
			props.addSpecialSequence("(", OPEN_PARENTHESIS);
			props.addSpecialSequence(")", CLOSED_PARENTHESIS);
			props.addSpecialSequence("[", OPEN_BRACKET);
			props.addSpecialSequence("]", CLOSED_BRACKET);
			// props.addSpecialSequence("=", EQUAL_SIGN);
			// props.addSpecialSequence(",", COMMA);
			// props.addString("\"", "\"", "\\");
		}
	}

	/**
	 * Call after reading an opening token such as an open parenthesis and read
	 * until the matching closing token is read, allowing for more opening tokens.
	 * Return textual value of anything between the tokens, excluding the tokens
	 * themselves.
	 * 
	 * @param tokenType
	 * @return
	 */
	public static String readToMatching(int openingToken, int closingToken, Tokenizer tokenizer,
			TemplateImpl template) {

		String ret = "";
		int level = 1;

		while (tokenizer.hasMoreTokens()) {

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
					ret += "REPORT.get(self)." + value.substring(1);
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
	 * Parse an annotation at the beginning of the line. Return the parsed
	 * annotation and the remainder of the line after it.
	 * 
	 * @param line
	 * @return
	 */
	public static TemplateImpl parse(String line) {

		initialize();

		TemplateImpl ret = new TemplateImpl();

		String currentAnnotation = null;
		String currentText = "";

		try (Tokenizer tokenizer = new StandardTokenizer(props)) {
			tokenizer.setSource(new ReaderSource(new StringReader(line)));

			while (tokenizer.hasMoreTokens()) {

				Token token = tokenizer.nextToken();
				String value = token.getImage();

				if (currentAnnotation != null) {

					if (!token.getCompanion().equals(OPEN_PARENTHESIS)) {
						ret.getErrors().add("arguments in parentheses are expected after report calls");
					}

					ret.addText(currentText);
					currentText = "";
					ret.addCall(currentAnnotation,
							readToMatching(OPEN_PARENTHESIS, CLOSED_PARENTHESIS, tokenizer, ret));
					currentAnnotation = null;

				} else {

					switch (token.getType()) {
					case Token.PATTERN:
						if (token.getCompanion().equals(ANNOTATION)) {
							currentAnnotation = value;
						}
						break;
					case Token.SPECIAL_SEQUENCE:
						if (token.getCompanion().equals(OPEN_BRACKET)
								&& !(!currentText.isEmpty() && currentText.endsWith("\\"))) {
							ret.addText(currentText);
							currentText = "";
							ret.addCode(readToMatching(OPEN_BRACKET, CLOSED_BRACKET, tokenizer, ret));
						} else {
							currentText += value;
						}
						break;
					default:
						currentText += value;
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

	public static void main(String[] args) {

		Template template = parse("@section('Methods/Socio-environmental exchange') \n" + "\n"
				+ "                  The default model for a social-environmental exchange was used. In this model, all \n"
				+ "                  ecosystem service flows are gathered in the context and aggregated to provide \n"
				+ "                  figures. The model is not specific of any ES: flows are recognized based on the\n"
				+ "                  roles they play, and their throughput extracted independent of the specific ES\n"
				+ "                  being modeled.\n" + "\n"
				+ "                  [for (i in 1..10) { @separator(); @paragraph('ahaha'); }]\n" + "\n"
				+ "                  @separator(bold: true)\n" + "\n"
				+ "                  The model description should come after that of its inputs. Each independent\n"
				+ "                  piece of text is automatically wrapped in a paragraph.\n" + "                ");

//		System.out.println(((TemplateImpl) template).getActionCode());
	}
}

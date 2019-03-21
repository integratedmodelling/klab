//package org.integratedmodelling.klab.authorities.swb;
//
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.lang.StringUtils;
//
//
///**
// * Main WRB parser in charge of the conversion string -> WRB identity.
// * 
// * <pre>
// * Production rules for WRB grammar:
// * 
// * TERMINALS: 
// * 		Q (qualifier term, second level, from vocabulary)
// * 		S (specifier term, from vocabulary)
// * 		G (group term, first level, from vocabulary)
// * 
// * PRODUCTIONS:
// * 	
// * 		P := S?Q            // prefix
// * 		X := (S'-')?G'ic'   // group used as qualifier, with potential specifier
// * 		QualifierList := '(' P (',' P)* ')'? 
// * 		QualifiedGroupList := '(' X (',' X)* ')'
// * 		Model  := P* G (QualifierList? ~ QualifiedGroupList?)
// *  
// * </pre>
// * 
// * @author Ferd
// *
// */
//public class WRBParser {
//
//	static boolean initialized = false;
//	
//	/*
//	 * Some sample specifications
//	 * from SIS:SoilProfile at http://93.63.35.107:8080/geoserver (field should be WRB2006 but this is in xl_en_4952)
//	 */
//	static String[] samples = {
//            "Endoleptic Regosols",
//	        "Haplic Vertisols (Eutric, Endoskeletic)",
//	        "Haplic Cambisols (Eutric)",
//	        "Placic Phaeozems"
//	};
//	
//	/**
//	 * Parse a WRB string into the intermediate representation.
//	 * 
//	 * @param definition
//	 * @return
//	 * @throws KlabValidationException
//	 */
//	public static WRBIdentity parse(String definition) throws KlabValidationException {
//		
//        StandardTokenizerProperties properties = new StandardTokenizerProperties();
//        properties.setSeparators("(),");
//
//        try (Tokenizer tokenizer = new StandardTokenizer(properties)) {
//
//            tokenizer.setSource(new ReaderSource(new StringReader(definition)));
//            WRBIdentity ret = new WRBIdentity();
//            while (tokenizer.hasMoreTokens()) {
//                Token token = tokenizer.nextToken();
//                if (token.getType() == token.EOF) {
//                    break;
//                }
//                if (token.getImage().equals("(")) {
//                    ret.openSpecifierGroup();
//                } else if (token.getImage().equals(")")) {
//                    ret.closeSpecifierGroup();
//                } else if (!token.getImage().equals(",")) {
//                    ret.addToken(token.getImage());
//                }
//            }
//
//            ret.closeParsing();
//            
//            return ret;
//        }
//	}
//	
//	/**
//	 * Parse a (valid) intermediate representation into a fully qualified concept.
//	 * 
//	 * @param identity
//	 * @return
//	 */
//	public static IConcept conceptualize(WRBIdentity identity) {
//		
//		return null;
//	}
//	
//	/**
//	 * Chomp away specifiers from the front of the term until no more modifiers are
//	 * present, then present all together as a WRBConcept. No validation other than
//	 * term hunting.
//	 * 
//	 * NOTE: allows more specifiers but grammar allows only one. Left here for
//	 * validation - parser must check number of specifiers and can produce informative
//	 * error if invalid.
//	 * 
//	 * @param term
//	 * @return
//	 * @throws KlabValidationException 
//	 */
//	public static WRBIdentity.WRBConcept chomp(String term) throws KlabValidationException {
//
//		WRBIdentity.WRBConcept ret = new WRBConcept();
//		List<String> specs = new ArrayList<>();
//		
//		/*
//		 * FIXME only one spec please
//		 */
//		while (true) {
//			boolean found = false;
//			for (String s : WRBVocabulary.get().SpecifierTerms.keySet()) {
//				if (term.toUpperCase().startsWith(s.toUpperCase())) {
//					term = term.substring(s.length());
//					specs.add(s);
//					found = true;
//					break;
//				}
//			}
//			if (!found) {
//			    if (!WRBVocabulary.get().isQualifier(term)) {
//			        throw new KlabValidationException("qualifier " + StringUtils.capitalize(term.toLowerCase()) + " is unknown");
//			    }
//				ret.setMainConcept(term);
//				break;
//			}
//		}
//		
//		if (specs.size() > 0) {
//			ret.setSpecifiers(specs);
//		}
//		
//		return ret;
//	}
//	
//	/**
//	 * Return group correspondent to passed token, allowing case mismatch and arbitrary suffix.
//	 * 
//	 * @param token
//	 * @return
//	 */
//	public static String getGroupTerm(String token) {
//	    for (String s : WRBVocabulary.get().RSGTerms.keySet()) {
//	        if (token.toLowerCase().startsWith(s.toLowerCase())) {
//	            return s;
//	        }
//	    }
//	    return null;
//	}
//	
//	public static void main(String[] args) throws Exception {
//	    for (String d : samples) {
//	        System.out.println("Parsing '" + d + "':");
//	        WRBIdentity def = parse(d); 
//	        System.out.println("Reconstructed definition: " + def + "; concept ID: " + def.getShortId());
//	    }
//	}
//	
//		
//}

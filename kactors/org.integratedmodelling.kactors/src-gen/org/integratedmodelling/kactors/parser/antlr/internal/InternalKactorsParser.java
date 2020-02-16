package org.integratedmodelling.kactors.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.IUnorderedGroupHelper.UnorderedGroupState;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.integratedmodelling.kactors.services.KactorsGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalKactorsParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_LOWERCASE_ID", "RULE_ID", "RULE_STRING", "RULE_ARGVALUE", "RULE_OBSERVABLE", "RULE_EXPR", "RULE_ANNOTATION_ID", "RULE_INT", "RULE_SEPARATOR", "RULE_CAMELCASE_ID", "RULE_UPPERCASE_ID", "RULE_EMBEDDEDTEXT", "RULE_REGEXP", "RULE_UPPERCASE_PATH", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'actor'", "'import'", "','", "'worldview'", "'label'", "'description'", "'permissions'", "'author'", "'version'", "'created'", "'modified'", "'message'", "':'", "'('", "')'", "'=?'", "'='", "'urn:klab:'", "'#'", "'&'", "'{'", "'}'", "'true'", "'false'", "'inclusive'", "'exclusive'", "'to'", "'in'", "'unknown'", "'*'", "'{{'", "'}}'", "'|'", "'/'", "'.'", "'@'", "'>'", "'<'", "'!='", "'<='", "'>='", "';'", "'if'", "'else'", "'while'", "'do'", "'for'", "'->'", "'+'", "'-'", "'l'", "'e'", "'E'", "'AD'", "'CE'", "'BC'", "'^'"
    };
    public static final int T__50=50;
    public static final int RULE_EMBEDDEDTEXT=15;
    public static final int RULE_UPPERCASE_ID=14;
    public static final int T__59=59;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=5;
    public static final int RULE_CAMELCASE_ID=13;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=11;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=18;
    public static final int T__23=23;
    public static final int T__67=67;
    public static final int T__24=24;
    public static final int T__68=68;
    public static final int T__25=25;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_ARGVALUE=7;
    public static final int RULE_STRING=6;
    public static final int RULE_SEPARATOR=12;
    public static final int RULE_SL_COMMENT=19;
    public static final int RULE_OBSERVABLE=8;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__77=77;
    public static final int T__34=34;
    public static final int T__78=78;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__73=73;
    public static final int RULE_REGEXP=16;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__74=74;
    public static final int T__31=31;
    public static final int T__75=75;
    public static final int T__32=32;
    public static final int T__76=76;
    public static final int RULE_WS=20;
    public static final int RULE_ANY_OTHER=21;
    public static final int RULE_ANNOTATION_ID=10;
    public static final int T__48=48;
    public static final int RULE_LOWERCASE_ID=4;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int RULE_EXPR=9;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int RULE_UPPERCASE_PATH=17;
    public static final int T__43=43;

    // delegates
    // delegators


        public InternalKactorsParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalKactorsParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalKactorsParser.tokenNames; }
    public String getGrammarFileName() { return "InternalKactors.g"; }



    /*
      This grammar contains a lot of empty actions to work around a bug in ANTLR.
      Otherwise the ANTLR tool will create synpreds that cannot be compiled in some rare cases.
    */

     	private KactorsGrammarAccess grammarAccess;

        public InternalKactorsParser(TokenStream input, KactorsGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Model";
       	}

       	@Override
       	protected KactorsGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleModel"
    // InternalKactors.g:72:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalKactors.g:72:46: (iv_ruleModel= ruleModel EOF )
            // InternalKactors.g:73:2: iv_ruleModel= ruleModel EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getModelRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleModel=ruleModel();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleModel; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalKactors.g:79:1: ruleModel returns [EObject current=null] : ( () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )* ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        EObject lv_preamble_1_0 = null;

        EObject lv_definitions_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:85:2: ( ( () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )* ) )
            // InternalKactors.g:86:2: ( () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )* )
            {
            // InternalKactors.g:86:2: ( () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )* )
            // InternalKactors.g:87:3: () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )*
            {
            // InternalKactors.g:87:3: ()
            // InternalKactors.g:88:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getModelAccess().getModelAction_0(),
              					current);
              			
            }

            }

            // InternalKactors.g:97:3: ( (lv_preamble_1_0= rulePreamble ) )?
            int alt1=2;
            switch ( input.LA(1) ) {
                case 22:
                case 23:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                    {
                    alt1=1;
                    }
                    break;
                case RULE_ANNOTATION_ID:
                    {
                    int LA1_2 = input.LA(2);

                    if ( (synpred1_InternalKactors()) ) {
                        alt1=1;
                    }
                    }
                    break;
                case 33:
                    {
                    int LA1_3 = input.LA(2);

                    if ( (synpred1_InternalKactors()) ) {
                        alt1=1;
                    }
                    }
                    break;
                case EOF:
                    {
                    int LA1_4 = input.LA(2);

                    if ( (synpred1_InternalKactors()) ) {
                        alt1=1;
                    }
                    }
                    break;
            }

            switch (alt1) {
                case 1 :
                    // InternalKactors.g:98:4: (lv_preamble_1_0= rulePreamble )
                    {
                    // InternalKactors.g:98:4: (lv_preamble_1_0= rulePreamble )
                    // InternalKactors.g:99:5: lv_preamble_1_0= rulePreamble
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getModelAccess().getPreamblePreambleParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_3);
                    lv_preamble_1_0=rulePreamble();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getModelRule());
                      					}
                      					set(
                      						current,
                      						"preamble",
                      						lv_preamble_1_0,
                      						"org.integratedmodelling.kactors.Kactors.Preamble");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalKactors.g:116:3: ( (lv_definitions_2_0= ruleDefinition ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==RULE_ANNOTATION_ID||LA2_0==33) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalKactors.g:117:4: (lv_definitions_2_0= ruleDefinition )
            	    {
            	    // InternalKactors.g:117:4: (lv_definitions_2_0= ruleDefinition )
            	    // InternalKactors.g:118:5: lv_definitions_2_0= ruleDefinition
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getModelAccess().getDefinitionsDefinitionParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_3);
            	    lv_definitions_2_0=ruleDefinition();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getModelRule());
            	      					}
            	      					add(
            	      						current,
            	      						"definitions",
            	      						lv_definitions_2_0,
            	      						"org.integratedmodelling.kactors.Kactors.Definition");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRulePreamble"
    // InternalKactors.g:139:1: entryRulePreamble returns [EObject current=null] : iv_rulePreamble= rulePreamble EOF ;
    public final EObject entryRulePreamble() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePreamble = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getPreambleAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKactors.g:143:2: (iv_rulePreamble= rulePreamble EOF )
            // InternalKactors.g:144:2: iv_rulePreamble= rulePreamble EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPreambleRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePreamble=rulePreamble();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePreamble; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myUnorderedGroupState.restore();

        }
        return current;
    }
    // $ANTLR end "entryRulePreamble"


    // $ANTLR start "rulePreamble"
    // InternalKactors.g:153:1: rulePreamble returns [EObject current=null] : ( () (otherlv_1= 'actor' ( (lv_name_2_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) ;
    public final EObject rulePreamble() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token lv_label_11_1=null;
        Token lv_label_11_2=null;
        Token lv_label_11_3=null;
        Token otherlv_12=null;
        Token lv_description_13_0=null;
        Token otherlv_14=null;
        Token lv_permissions_15_0=null;
        Token otherlv_16=null;
        Token lv_authors_17_0=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token lv_createcomment_22_0=null;
        Token otherlv_23=null;
        Token lv_modcomment_25_0=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        AntlrDatatypeRuleToken lv_imports_5_0 = null;

        AntlrDatatypeRuleToken lv_imports_7_0 = null;

        AntlrDatatypeRuleToken lv_worldview_9_0 = null;

        AntlrDatatypeRuleToken lv_version_19_0 = null;

        EObject lv_created_21_0 = null;

        EObject lv_modified_24_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getPreambleAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKactors.g:162:2: ( ( () (otherlv_1= 'actor' ( (lv_name_2_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) )
            // InternalKactors.g:163:2: ( () (otherlv_1= 'actor' ( (lv_name_2_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            {
            // InternalKactors.g:163:2: ( () (otherlv_1= 'actor' ( (lv_name_2_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            // InternalKactors.g:164:3: () (otherlv_1= 'actor' ( (lv_name_2_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
            {
            // InternalKactors.g:164:3: ()
            // InternalKactors.g:165:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getPreambleAccess().getPreambleAction_0(),
              					current);
              			
            }

            }

            // InternalKactors.g:174:3: (otherlv_1= 'actor' ( (lv_name_2_0= rulePathName ) ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==22) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalKactors.g:175:4: otherlv_1= 'actor' ( (lv_name_2_0= rulePathName ) )
                    {
                    otherlv_1=(Token)match(input,22,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getPreambleAccess().getActorKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:179:4: ( (lv_name_2_0= rulePathName ) )
                    // InternalKactors.g:180:5: (lv_name_2_0= rulePathName )
                    {
                    // InternalKactors.g:180:5: (lv_name_2_0= rulePathName )
                    // InternalKactors.g:181:6: lv_name_2_0= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPreambleAccess().getNamePathNameParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_5);
                    lv_name_2_0=rulePathName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPreambleRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_2_0,
                      							"org.integratedmodelling.kactors.Kactors.PathName");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalKactors.g:199:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
            // InternalKactors.g:200:4: ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) )
            {
            // InternalKactors.g:200:4: ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) )
            // InternalKactors.g:201:5: ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* )
            {
            getUnorderedGroupHelper().enter(grammarAccess.getPreambleAccess().getUnorderedGroup_2());
            // InternalKactors.g:204:5: ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* )
            // InternalKactors.g:205:6: ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )*
            {
            // InternalKactors.g:205:6: ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )*
            loop9:
            do {
                int alt9=10;
                alt9 = dfa9.predict(input);
                switch (alt9) {
            	case 1 :
            	    // InternalKactors.g:206:4: ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) )
            	    {
            	    // InternalKactors.g:206:4: ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) )
            	    // InternalKactors.g:207:5: {...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
            	    }
            	    // InternalKactors.g:207:105: ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) )
            	    // InternalKactors.g:208:6: ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
            	    // InternalKactors.g:211:9: ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) )
            	    // InternalKactors.g:211:10: {...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:211:19: (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* )
            	    // InternalKactors.g:211:20: otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )*
            	    {
            	    otherlv_4=(Token)match(input,23,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_4, grammarAccess.getPreambleAccess().getImportKeyword_2_0_0());
            	      								
            	    }
            	    // InternalKactors.g:215:9: ( (lv_imports_5_0= rulePathName ) )
            	    // InternalKactors.g:216:10: (lv_imports_5_0= rulePathName )
            	    {
            	    // InternalKactors.g:216:10: (lv_imports_5_0= rulePathName )
            	    // InternalKactors.g:217:11: lv_imports_5_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_imports_5_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											add(
            	      												current,
            	      												"imports",
            	      												lv_imports_5_0,
            	      												"org.integratedmodelling.kactors.Kactors.PathName");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:234:9: (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )*
            	    loop4:
            	    do {
            	        int alt4=2;
            	        int LA4_0 = input.LA(1);

            	        if ( (LA4_0==24) ) {
            	            alt4=1;
            	        }


            	        switch (alt4) {
            	    	case 1 :
            	    	    // InternalKactors.g:235:10: otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) )
            	    	    {
            	    	    otherlv_6=(Token)match(input,24,FOLLOW_4); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      										newLeafNode(otherlv_6, grammarAccess.getPreambleAccess().getCommaKeyword_2_0_2_0());
            	    	      									
            	    	    }
            	    	    // InternalKactors.g:239:10: ( (lv_imports_7_0= rulePathName ) )
            	    	    // InternalKactors.g:240:11: (lv_imports_7_0= rulePathName )
            	    	    {
            	    	    // InternalKactors.g:240:11: (lv_imports_7_0= rulePathName )
            	    	    // InternalKactors.g:241:12: lv_imports_7_0= rulePathName
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      												newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_2_1_0());
            	    	      											
            	    	    }
            	    	    pushFollow(FOLLOW_6);
            	    	    lv_imports_7_0=rulePathName();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      												if (current==null) {
            	    	      													current = createModelElementForParent(grammarAccess.getPreambleRule());
            	    	      												}
            	    	      												add(
            	    	      													current,
            	    	      													"imports",
            	    	      													lv_imports_7_0,
            	    	      													"org.integratedmodelling.kactors.Kactors.PathName");
            	    	      												afterParserOrEnumRuleCall();
            	    	      											
            	    	    }

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop4;
            	        }
            	    } while (true);


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalKactors.g:265:4: ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:265:4: ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) )
            	    // InternalKactors.g:266:5: {...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
            	    }
            	    // InternalKactors.g:266:105: ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) )
            	    // InternalKactors.g:267:6: ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
            	    // InternalKactors.g:270:9: ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) )
            	    // InternalKactors.g:270:10: {...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:270:19: (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) )
            	    // InternalKactors.g:270:20: otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) )
            	    {
            	    otherlv_8=(Token)match(input,25,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_8, grammarAccess.getPreambleAccess().getWorldviewKeyword_2_1_0());
            	      								
            	    }
            	    // InternalKactors.g:274:9: ( (lv_worldview_9_0= rulePathName ) )
            	    // InternalKactors.g:275:10: (lv_worldview_9_0= rulePathName )
            	    {
            	    // InternalKactors.g:275:10: (lv_worldview_9_0= rulePathName )
            	    // InternalKactors.g:276:11: lv_worldview_9_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_2_1_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_worldview_9_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"worldview",
            	      												lv_worldview_9_0,
            	      												"org.integratedmodelling.kactors.Kactors.PathName");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalKactors.g:299:4: ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:299:4: ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) )
            	    // InternalKactors.g:300:5: {...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
            	    }
            	    // InternalKactors.g:300:105: ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:301:6: ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
            	    // InternalKactors.g:304:9: ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:304:10: {...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:304:19: (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) )
            	    // InternalKactors.g:304:20: otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) )
            	    {
            	    otherlv_10=(Token)match(input,26,FOLLOW_7); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_10, grammarAccess.getPreambleAccess().getLabelKeyword_2_2_0());
            	      								
            	    }
            	    // InternalKactors.g:308:9: ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) )
            	    // InternalKactors.g:309:10: ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:309:10: ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) )
            	    // InternalKactors.g:310:11: (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING )
            	    {
            	    // InternalKactors.g:310:11: (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING )
            	    int alt5=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_LOWERCASE_ID:
            	        {
            	        alt5=1;
            	        }
            	        break;
            	    case RULE_ID:
            	        {
            	        alt5=2;
            	        }
            	        break;
            	    case RULE_STRING:
            	        {
            	        alt5=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 5, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt5) {
            	        case 1 :
            	            // InternalKactors.g:311:12: lv_label_11_1= RULE_LOWERCASE_ID
            	            {
            	            lv_label_11_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_11_1, grammarAccess.getPreambleAccess().getLabelLOWERCASE_IDTerminalRuleCall_2_2_1_0_0());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_11_1,
            	              													"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
            	              											
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:326:12: lv_label_11_2= RULE_ID
            	            {
            	            lv_label_11_2=(Token)match(input,RULE_ID,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_11_2, grammarAccess.getPreambleAccess().getLabelIDTerminalRuleCall_2_2_1_0_1());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_11_2,
            	              													"org.eclipse.xtext.common.Terminals.ID");
            	              											
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalKactors.g:341:12: lv_label_11_3= RULE_STRING
            	            {
            	            lv_label_11_3=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_11_3, grammarAccess.getPreambleAccess().getLabelSTRINGTerminalRuleCall_2_2_1_0_2());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_11_3,
            	              													"org.eclipse.xtext.common.Terminals.STRING");
            	              											
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalKactors.g:364:4: ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:364:4: ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:365:5: {...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
            	    }
            	    // InternalKactors.g:365:105: ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:366:6: ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
            	    // InternalKactors.g:369:9: ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:369:10: {...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:369:19: (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) )
            	    // InternalKactors.g:369:20: otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) )
            	    {
            	    otherlv_12=(Token)match(input,27,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_12, grammarAccess.getPreambleAccess().getDescriptionKeyword_2_3_0());
            	      								
            	    }
            	    // InternalKactors.g:373:9: ( (lv_description_13_0= RULE_STRING ) )
            	    // InternalKactors.g:374:10: (lv_description_13_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:374:10: (lv_description_13_0= RULE_STRING )
            	    // InternalKactors.g:375:11: lv_description_13_0= RULE_STRING
            	    {
            	    lv_description_13_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_description_13_0, grammarAccess.getPreambleAccess().getDescriptionSTRINGTerminalRuleCall_2_3_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"description",
            	      												lv_description_13_0,
            	      												"org.eclipse.xtext.common.Terminals.STRING");
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 5 :
            	    // InternalKactors.g:397:4: ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:397:4: ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:398:5: {...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4)");
            	    }
            	    // InternalKactors.g:398:105: ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:399:6: ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4);
            	    // InternalKactors.g:402:9: ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:402:10: {...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:402:19: (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) )
            	    // InternalKactors.g:402:20: otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) )
            	    {
            	    otherlv_14=(Token)match(input,28,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_14, grammarAccess.getPreambleAccess().getPermissionsKeyword_2_4_0());
            	      								
            	    }
            	    // InternalKactors.g:406:9: ( (lv_permissions_15_0= RULE_STRING ) )
            	    // InternalKactors.g:407:10: (lv_permissions_15_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:407:10: (lv_permissions_15_0= RULE_STRING )
            	    // InternalKactors.g:408:11: lv_permissions_15_0= RULE_STRING
            	    {
            	    lv_permissions_15_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_permissions_15_0, grammarAccess.getPreambleAccess().getPermissionsSTRINGTerminalRuleCall_2_4_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"permissions",
            	      												lv_permissions_15_0,
            	      												"org.eclipse.xtext.common.Terminals.STRING");
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 6 :
            	    // InternalKactors.g:430:4: ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) )
            	    {
            	    // InternalKactors.g:430:4: ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) )
            	    // InternalKactors.g:431:5: {...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5)");
            	    }
            	    // InternalKactors.g:431:105: ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ )
            	    // InternalKactors.g:432:6: ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5);
            	    // InternalKactors.g:435:9: ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+
            	    int cnt6=0;
            	    loop6:
            	    do {
            	        int alt6=2;
            	        int LA6_0 = input.LA(1);

            	        if ( (LA6_0==29) ) {
            	            int LA6_2 = input.LA(2);

            	            if ( ((synpred12_InternalKactors()&&(true))) ) {
            	                alt6=1;
            	            }


            	        }


            	        switch (alt6) {
            	    	case 1 :
            	    	    // InternalKactors.g:435:10: {...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    	    }
            	    	    // InternalKactors.g:435:19: (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) )
            	    	    // InternalKactors.g:435:20: otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) )
            	    	    {
            	    	    otherlv_16=(Token)match(input,29,FOLLOW_8); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      									newLeafNode(otherlv_16, grammarAccess.getPreambleAccess().getAuthorKeyword_2_5_0());
            	    	      								
            	    	    }
            	    	    // InternalKactors.g:439:9: ( (lv_authors_17_0= RULE_STRING ) )
            	    	    // InternalKactors.g:440:10: (lv_authors_17_0= RULE_STRING )
            	    	    {
            	    	    // InternalKactors.g:440:10: (lv_authors_17_0= RULE_STRING )
            	    	    // InternalKactors.g:441:11: lv_authors_17_0= RULE_STRING
            	    	    {
            	    	    lv_authors_17_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      											newLeafNode(lv_authors_17_0, grammarAccess.getPreambleAccess().getAuthorsSTRINGTerminalRuleCall_2_5_1_0());
            	    	      										
            	    	    }
            	    	    if ( state.backtracking==0 ) {

            	    	      											if (current==null) {
            	    	      												current = createModelElement(grammarAccess.getPreambleRule());
            	    	      											}
            	    	      											addWithLastConsumed(
            	    	      												current,
            	    	      												"authors",
            	    	      												lv_authors_17_0,
            	    	      												"org.eclipse.xtext.common.Terminals.STRING");
            	    	      										
            	    	    }

            	    	    }


            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt6 >= 1 ) break loop6;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(6, input);
            	                throw eee;
            	        }
            	        cnt6++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 7 :
            	    // InternalKactors.g:463:4: ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:463:4: ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) )
            	    // InternalKactors.g:464:5: {...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6)");
            	    }
            	    // InternalKactors.g:464:105: ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) )
            	    // InternalKactors.g:465:6: ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6);
            	    // InternalKactors.g:468:9: ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) )
            	    // InternalKactors.g:468:10: {...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:468:19: (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) )
            	    // InternalKactors.g:468:20: otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) )
            	    {
            	    otherlv_18=(Token)match(input,30,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_18, grammarAccess.getPreambleAccess().getVersionKeyword_2_6_0());
            	      								
            	    }
            	    // InternalKactors.g:472:9: ( (lv_version_19_0= ruleVersionNumber ) )
            	    // InternalKactors.g:473:10: (lv_version_19_0= ruleVersionNumber )
            	    {
            	    // InternalKactors.g:473:10: (lv_version_19_0= ruleVersionNumber )
            	    // InternalKactors.g:474:11: lv_version_19_0= ruleVersionNumber
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_2_6_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_version_19_0=ruleVersionNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"version",
            	      												lv_version_19_0,
            	      												"org.integratedmodelling.kactors.Kactors.VersionNumber");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 8 :
            	    // InternalKactors.g:497:4: ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:497:4: ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:498:5: {...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7)");
            	    }
            	    // InternalKactors.g:498:105: ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:499:6: ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7);
            	    // InternalKactors.g:502:9: ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:502:10: {...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:502:19: (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? )
            	    // InternalKactors.g:502:20: otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )?
            	    {
            	    otherlv_20=(Token)match(input,31,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_20, grammarAccess.getPreambleAccess().getCreatedKeyword_2_7_0());
            	      								
            	    }
            	    // InternalKactors.g:506:9: ( (lv_created_21_0= ruleDate ) )
            	    // InternalKactors.g:507:10: (lv_created_21_0= ruleDate )
            	    {
            	    // InternalKactors.g:507:10: (lv_created_21_0= ruleDate )
            	    // InternalKactors.g:508:11: lv_created_21_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_2_7_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_created_21_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"created",
            	      												lv_created_21_0,
            	      												"org.integratedmodelling.kactors.Kactors.Date");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:525:9: ( (lv_createcomment_22_0= RULE_STRING ) )?
            	    int alt7=2;
            	    int LA7_0 = input.LA(1);

            	    if ( (LA7_0==RULE_STRING) ) {
            	        alt7=1;
            	    }
            	    switch (alt7) {
            	        case 1 :
            	            // InternalKactors.g:526:10: (lv_createcomment_22_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:526:10: (lv_createcomment_22_0= RULE_STRING )
            	            // InternalKactors.g:527:11: lv_createcomment_22_0= RULE_STRING
            	            {
            	            lv_createcomment_22_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_createcomment_22_0, grammarAccess.getPreambleAccess().getCreatecommentSTRINGTerminalRuleCall_2_7_2_0());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"createcomment",
            	              												lv_createcomment_22_0,
            	              												"org.eclipse.xtext.common.Terminals.STRING");
            	              										
            	            }

            	            }


            	            }
            	            break;

            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 9 :
            	    // InternalKactors.g:549:4: ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:549:4: ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:550:5: {...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8)");
            	    }
            	    // InternalKactors.g:550:105: ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:551:6: ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8);
            	    // InternalKactors.g:554:9: ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:554:10: {...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:554:19: (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? )
            	    // InternalKactors.g:554:20: otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )?
            	    {
            	    otherlv_23=(Token)match(input,32,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_23, grammarAccess.getPreambleAccess().getModifiedKeyword_2_8_0());
            	      								
            	    }
            	    // InternalKactors.g:558:9: ( (lv_modified_24_0= ruleDate ) )
            	    // InternalKactors.g:559:10: (lv_modified_24_0= ruleDate )
            	    {
            	    // InternalKactors.g:559:10: (lv_modified_24_0= ruleDate )
            	    // InternalKactors.g:560:11: lv_modified_24_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_2_8_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_modified_24_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"modified",
            	      												lv_modified_24_0,
            	      												"org.integratedmodelling.kactors.Kactors.Date");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:577:9: ( (lv_modcomment_25_0= RULE_STRING ) )?
            	    int alt8=2;
            	    int LA8_0 = input.LA(1);

            	    if ( (LA8_0==RULE_STRING) ) {
            	        alt8=1;
            	    }
            	    switch (alt8) {
            	        case 1 :
            	            // InternalKactors.g:578:10: (lv_modcomment_25_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:578:10: (lv_modcomment_25_0= RULE_STRING )
            	            // InternalKactors.g:579:11: lv_modcomment_25_0= RULE_STRING
            	            {
            	            lv_modcomment_25_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_modcomment_25_0, grammarAccess.getPreambleAccess().getModcommentSTRINGTerminalRuleCall_2_8_2_0());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"modcomment",
            	              												lv_modcomment_25_0,
            	              												"org.eclipse.xtext.common.Terminals.STRING");
            	              										
            	            }

            	            }


            	            }
            	            break;

            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            }


            }

            getUnorderedGroupHelper().leave(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {

            	myUnorderedGroupState.restore();

        }
        return current;
    }
    // $ANTLR end "rulePreamble"


    // $ANTLR start "entryRuleDefinition"
    // InternalKactors.g:615:1: entryRuleDefinition returns [EObject current=null] : iv_ruleDefinition= ruleDefinition EOF ;
    public final EObject entryRuleDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDefinition = null;


        try {
            // InternalKactors.g:615:51: (iv_ruleDefinition= ruleDefinition EOF )
            // InternalKactors.g:616:2: iv_ruleDefinition= ruleDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDefinition=ruleDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDefinition"


    // $ANTLR start "ruleDefinition"
    // InternalKactors.g:622:1: ruleDefinition returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'message' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) ) ) ;
    public final EObject ruleDefinition() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_4=null;
        EObject lv_annotations_0_0 = null;

        EObject lv_arguments_3_0 = null;

        EObject lv_body_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:628:2: ( ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'message' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) ) ) )
            // InternalKactors.g:629:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'message' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) ) )
            {
            // InternalKactors.g:629:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'message' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) ) )
            // InternalKactors.g:630:3: ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'message' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) )
            {
            // InternalKactors.g:630:3: ( (lv_annotations_0_0= ruleAnnotation ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==RULE_ANNOTATION_ID) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalKactors.g:631:4: (lv_annotations_0_0= ruleAnnotation )
            	    {
            	    // InternalKactors.g:631:4: (lv_annotations_0_0= ruleAnnotation )
            	    // InternalKactors.g:632:5: lv_annotations_0_0= ruleAnnotation
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getDefinitionAccess().getAnnotationsAnnotationParserRuleCall_0_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_11);
            	    lv_annotations_0_0=ruleAnnotation();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getDefinitionRule());
            	      					}
            	      					add(
            	      						current,
            	      						"annotations",
            	      						lv_annotations_0_0,
            	      						"org.integratedmodelling.kactors.Kactors.Annotation");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            otherlv_1=(Token)match(input,33,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getDefinitionAccess().getMessageKeyword_1());
              		
            }
            // InternalKactors.g:653:3: ( (lv_name_2_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:654:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:654:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:655:5: lv_name_2_0= RULE_LOWERCASE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_2_0, grammarAccess.getDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_2_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getDefinitionRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_2_0,
              						"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
              				
            }

            }


            }

            // InternalKactors.g:671:3: ( (lv_arguments_3_0= ruleArgumentDeclaration ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==35) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalKactors.g:672:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:672:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    // InternalKactors.g:673:5: lv_arguments_3_0= ruleArgumentDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getDefinitionAccess().getArgumentsArgumentDeclarationParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_13);
                    lv_arguments_3_0=ruleArgumentDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getDefinitionRule());
                      					}
                      					set(
                      						current,
                      						"arguments",
                      						lv_arguments_3_0,
                      						"org.integratedmodelling.kactors.Kactors.ArgumentDeclaration");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,34,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDefinitionAccess().getColonKeyword_4());
              		
            }
            // InternalKactors.g:694:3: ( (lv_body_5_0= ruleBody ) )
            // InternalKactors.g:695:4: (lv_body_5_0= ruleBody )
            {
            // InternalKactors.g:695:4: (lv_body_5_0= ruleBody )
            // InternalKactors.g:696:5: lv_body_5_0= ruleBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDefinitionAccess().getBodyBodyParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_body_5_0=ruleBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDefinitionRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_5_0,
              						"org.integratedmodelling.kactors.Kactors.Body");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDefinition"


    // $ANTLR start "entryRuleArgumentDeclaration"
    // InternalKactors.g:717:1: entryRuleArgumentDeclaration returns [EObject current=null] : iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF ;
    public final EObject entryRuleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgumentDeclaration = null;


        try {
            // InternalKactors.g:717:60: (iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF )
            // InternalKactors.g:718:2: iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArgumentDeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleArgumentDeclaration=ruleArgumentDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArgumentDeclaration; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArgumentDeclaration"


    // $ANTLR start "ruleArgumentDeclaration"
    // InternalKactors.g:724:1: ruleArgumentDeclaration returns [EObject current=null] : ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) ;
    public final EObject ruleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_ids_2_0=null;
        Token otherlv_3=null;
        Token lv_ids_4_0=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalKactors.g:730:2: ( ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) )
            // InternalKactors.g:731:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            {
            // InternalKactors.g:731:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            // InternalKactors.g:732:3: () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')'
            {
            // InternalKactors.g:732:3: ()
            // InternalKactors.g:733:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getArgumentDeclarationAccess().getArgumentDeclarationAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,35,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getArgumentDeclarationAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:746:3: ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==RULE_LOWERCASE_ID) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalKactors.g:747:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    {
                    // InternalKactors.g:747:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:748:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:748:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:749:6: lv_ids_2_0= RULE_LOWERCASE_ID
                    {
                    lv_ids_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_16); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_ids_2_0, grammarAccess.getArgumentDeclarationAccess().getIdsLOWERCASE_IDTerminalRuleCall_2_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getArgumentDeclarationRule());
                      						}
                      						addWithLastConsumed(
                      							current,
                      							"ids",
                      							lv_ids_2_0,
                      							"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      					
                    }

                    }


                    }

                    // InternalKactors.g:765:4: (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==24) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // InternalKactors.g:766:5: otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    {
                    	    otherlv_3=(Token)match(input,24,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getArgumentDeclarationAccess().getCommaKeyword_2_1_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:770:5: ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    // InternalKactors.g:771:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    {
                    	    // InternalKactors.g:771:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    // InternalKactors.g:772:7: lv_ids_4_0= RULE_LOWERCASE_ID
                    	    {
                    	    lv_ids_4_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_16); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							newLeafNode(lv_ids_4_0, grammarAccess.getArgumentDeclarationAccess().getIdsLOWERCASE_IDTerminalRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElement(grammarAccess.getArgumentDeclarationRule());
                    	      							}
                    	      							addWithLastConsumed(
                    	      								current,
                    	      								"ids",
                    	      								lv_ids_4_0,
                    	      								"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getArgumentDeclarationAccess().getRightParenthesisKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArgumentDeclaration"


    // $ANTLR start "entryRuleParameterList"
    // InternalKactors.g:798:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKactors.g:798:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKactors.g:799:2: iv_ruleParameterList= ruleParameterList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleParameterList=ruleParameterList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleParameterList; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameterList"


    // $ANTLR start "ruleParameterList"
    // InternalKactors.g:805:1: ruleParameterList returns [EObject current=null] : ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) ;
    public final EObject ruleParameterList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_pairs_0_0 = null;

        EObject lv_pairs_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:811:2: ( ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) )
            // InternalKactors.g:812:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            {
            // InternalKactors.g:812:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            // InternalKactors.g:813:3: ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            {
            // InternalKactors.g:813:3: ( (lv_pairs_0_0= ruleKeyValuePair ) )
            // InternalKactors.g:814:4: (lv_pairs_0_0= ruleKeyValuePair )
            {
            // InternalKactors.g:814:4: (lv_pairs_0_0= ruleKeyValuePair )
            // InternalKactors.g:815:5: lv_pairs_0_0= ruleKeyValuePair
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_17);
            lv_pairs_0_0=ruleKeyValuePair();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getParameterListRule());
              					}
              					add(
              						current,
              						"pairs",
              						lv_pairs_0_0,
              						"org.integratedmodelling.kactors.Kactors.KeyValuePair");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:832:3: ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==24) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalKactors.g:833:4: ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    {
            	    // InternalKactors.g:833:4: ( ( ',' )=>otherlv_1= ',' )
            	    // InternalKactors.g:834:5: ( ',' )=>otherlv_1= ','
            	    {
            	    otherlv_1=(Token)match(input,24,FOLLOW_18); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_1_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:840:4: ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    // InternalKactors.g:841:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    {
            	    // InternalKactors.g:841:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    // InternalKactors.g:842:6: lv_pairs_2_0= ruleKeyValuePair
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_17);
            	    lv_pairs_2_0=ruleKeyValuePair();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getParameterListRule());
            	      						}
            	      						add(
            	      							current,
            	      							"pairs",
            	      							lv_pairs_2_0,
            	      							"org.integratedmodelling.kactors.Kactors.KeyValuePair");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameterList"


    // $ANTLR start "entryRuleKeyValuePair"
    // InternalKactors.g:864:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKactors.g:864:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKactors.g:865:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getKeyValuePairRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleKeyValuePair=ruleKeyValuePair();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleKeyValuePair; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleKeyValuePair"


    // $ANTLR start "ruleKeyValuePair"
    // InternalKactors.g:871:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:877:2: ( ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKactors.g:878:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKactors.g:878:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            // InternalKactors.g:879:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKactors.g:879:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_LOWERCASE_ID) ) {
                int LA16_1 = input.LA(2);

                if ( ((LA16_1>=37 && LA16_1<=38)) ) {
                    alt16=1;
                }
            }
            switch (alt16) {
                case 1 :
                    // InternalKactors.g:880:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    {
                    // InternalKactors.g:880:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:881:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:881:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:882:6: lv_name_0_0= RULE_LOWERCASE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_0_0, grammarAccess.getKeyValuePairAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getKeyValuePairRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_0_0,
                      							"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      					
                    }

                    }


                    }

                    // InternalKactors.g:898:4: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==37) ) {
                        alt15=1;
                    }
                    else if ( (LA15_0==38) ) {
                        alt15=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 15, 0, input);

                        throw nvae;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalKactors.g:899:5: ( (lv_interactive_1_0= '=?' ) )
                            {
                            // InternalKactors.g:899:5: ( (lv_interactive_1_0= '=?' ) )
                            // InternalKactors.g:900:6: (lv_interactive_1_0= '=?' )
                            {
                            // InternalKactors.g:900:6: (lv_interactive_1_0= '=?' )
                            // InternalKactors.g:901:7: lv_interactive_1_0= '=?'
                            {
                            lv_interactive_1_0=(Token)match(input,37,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_interactive_1_0, grammarAccess.getKeyValuePairAccess().getInteractiveEqualsSignQuestionMarkKeyword_0_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getKeyValuePairRule());
                              							}
                              							setWithLastConsumed(current, "interactive", true, "=?");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:914:5: otherlv_2= '='
                            {
                            otherlv_2=(Token)match(input,38,FOLLOW_18); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_0_1_1());
                              				
                            }

                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalKactors.g:920:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:921:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:921:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:922:5: lv_value_3_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getKeyValuePairAccess().getValueValueParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_3_0=ruleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getKeyValuePairRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_3_0,
              						"org.integratedmodelling.kactors.Kactors.Value");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleKeyValuePair"


    // $ANTLR start "entryRuleValue"
    // InternalKactors.g:943:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKactors.g:943:46: (iv_ruleValue= ruleValue EOF )
            // InternalKactors.g:944:2: iv_ruleValue= ruleValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleValue=ruleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleValue"


    // $ANTLR start "ruleValue"
    // InternalKactors.g:950:1: ruleValue returns [EObject current=null] : ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) ) ;
    public final EObject ruleValue() throws RecognitionException {
        EObject current = null;

        Token lv_argvalue_0_0=null;
        Token lv_observable_6_0=null;
        Token lv_expression_7_0=null;
        EObject lv_literal_1_0 = null;

        AntlrDatatypeRuleToken lv_id_2_0 = null;

        AntlrDatatypeRuleToken lv_urn_3_0 = null;

        EObject lv_list_4_0 = null;

        EObject lv_map_5_0 = null;

        EObject lv_table_8_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:956:2: ( ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) ) )
            // InternalKactors.g:957:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )
            {
            // InternalKactors.g:957:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )
            int alt17=9;
            alt17 = dfa17.predict(input);
            switch (alt17) {
                case 1 :
                    // InternalKactors.g:958:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    {
                    // InternalKactors.g:958:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    // InternalKactors.g:959:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    {
                    // InternalKactors.g:959:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    // InternalKactors.g:960:5: lv_argvalue_0_0= RULE_ARGVALUE
                    {
                    lv_argvalue_0_0=(Token)match(input,RULE_ARGVALUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_argvalue_0_0, grammarAccess.getValueAccess().getArgvalueARGVALUETerminalRuleCall_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"argvalue",
                      						lv_argvalue_0_0,
                      						"org.integratedmodelling.kactors.Kactors.ARGVALUE");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:977:3: ( (lv_literal_1_0= ruleLiteral ) )
                    {
                    // InternalKactors.g:977:3: ( (lv_literal_1_0= ruleLiteral ) )
                    // InternalKactors.g:978:4: (lv_literal_1_0= ruleLiteral )
                    {
                    // InternalKactors.g:978:4: (lv_literal_1_0= ruleLiteral )
                    // InternalKactors.g:979:5: lv_literal_1_0= ruleLiteral
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getLiteralLiteralParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_literal_1_0=ruleLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"literal",
                      						lv_literal_1_0,
                      						"org.integratedmodelling.kactors.Kactors.Literal");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:997:3: ( (lv_id_2_0= rulePathName ) )
                    {
                    // InternalKactors.g:997:3: ( (lv_id_2_0= rulePathName ) )
                    // InternalKactors.g:998:4: (lv_id_2_0= rulePathName )
                    {
                    // InternalKactors.g:998:4: (lv_id_2_0= rulePathName )
                    // InternalKactors.g:999:5: lv_id_2_0= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getIdPathNameParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_id_2_0=rulePathName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"id",
                      						lv_id_2_0,
                      						"org.integratedmodelling.kactors.Kactors.PathName");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:1017:3: ( (lv_urn_3_0= ruleUrnId ) )
                    {
                    // InternalKactors.g:1017:3: ( (lv_urn_3_0= ruleUrnId ) )
                    // InternalKactors.g:1018:4: (lv_urn_3_0= ruleUrnId )
                    {
                    // InternalKactors.g:1018:4: (lv_urn_3_0= ruleUrnId )
                    // InternalKactors.g:1019:5: lv_urn_3_0= ruleUrnId
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getUrnUrnIdParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_urn_3_0=ruleUrnId();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"urn",
                      						lv_urn_3_0,
                      						"org.integratedmodelling.kactors.Kactors.UrnId");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:1037:3: ( (lv_list_4_0= ruleList ) )
                    {
                    // InternalKactors.g:1037:3: ( (lv_list_4_0= ruleList ) )
                    // InternalKactors.g:1038:4: (lv_list_4_0= ruleList )
                    {
                    // InternalKactors.g:1038:4: (lv_list_4_0= ruleList )
                    // InternalKactors.g:1039:5: lv_list_4_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getListListParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_list_4_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"list",
                      						lv_list_4_0,
                      						"org.integratedmodelling.kactors.Kactors.List");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:1057:3: ( (lv_map_5_0= ruleMap ) )
                    {
                    // InternalKactors.g:1057:3: ( (lv_map_5_0= ruleMap ) )
                    // InternalKactors.g:1058:4: (lv_map_5_0= ruleMap )
                    {
                    // InternalKactors.g:1058:4: (lv_map_5_0= ruleMap )
                    // InternalKactors.g:1059:5: lv_map_5_0= ruleMap
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getMapMapParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_map_5_0=ruleMap();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"map",
                      						lv_map_5_0,
                      						"org.integratedmodelling.kactors.Kactors.Map");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:1077:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:1077:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:1078:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:1078:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    // InternalKactors.g:1079:5: lv_observable_6_0= RULE_OBSERVABLE
                    {
                    lv_observable_6_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_observable_6_0, grammarAccess.getValueAccess().getObservableOBSERVABLETerminalRuleCall_6_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"observable",
                      						lv_observable_6_0,
                      						"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:1096:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:1096:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    // InternalKactors.g:1097:4: (lv_expression_7_0= RULE_EXPR )
                    {
                    // InternalKactors.g:1097:4: (lv_expression_7_0= RULE_EXPR )
                    // InternalKactors.g:1098:5: lv_expression_7_0= RULE_EXPR
                    {
                    lv_expression_7_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_expression_7_0, grammarAccess.getValueAccess().getExpressionEXPRTerminalRuleCall_7_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"expression",
                      						lv_expression_7_0,
                      						"org.integratedmodelling.kactors.Kactors.EXPR");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:1115:3: ( (lv_table_8_0= ruleLookupTable ) )
                    {
                    // InternalKactors.g:1115:3: ( (lv_table_8_0= ruleLookupTable ) )
                    // InternalKactors.g:1116:4: (lv_table_8_0= ruleLookupTable )
                    {
                    // InternalKactors.g:1116:4: (lv_table_8_0= ruleLookupTable )
                    // InternalKactors.g:1117:5: lv_table_8_0= ruleLookupTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getTableLookupTableParserRuleCall_8_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_table_8_0=ruleLookupTable();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getValueRule());
                      					}
                      					set(
                      						current,
                      						"table",
                      						lv_table_8_0,
                      						"org.integratedmodelling.kactors.Kactors.LookupTable");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleValue"


    // $ANTLR start "entryRuleAnnotation"
    // InternalKactors.g:1138:1: entryRuleAnnotation returns [EObject current=null] : iv_ruleAnnotation= ruleAnnotation EOF ;
    public final EObject entryRuleAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnnotation = null;


        try {
            // InternalKactors.g:1138:51: (iv_ruleAnnotation= ruleAnnotation EOF )
            // InternalKactors.g:1139:2: iv_ruleAnnotation= ruleAnnotation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAnnotationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAnnotation=ruleAnnotation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAnnotation; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAnnotation"


    // $ANTLR start "ruleAnnotation"
    // InternalKactors.g:1145:1: ruleAnnotation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) ;
    public final EObject ruleAnnotation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1151:2: ( ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) )
            // InternalKactors.g:1152:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            {
            // InternalKactors.g:1152:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            // InternalKactors.g:1153:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            {
            // InternalKactors.g:1153:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) )
            // InternalKactors.g:1154:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            {
            // InternalKactors.g:1154:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            // InternalKactors.g:1155:5: lv_name_0_0= RULE_ANNOTATION_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ANNOTATION_ID,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_0_0, grammarAccess.getAnnotationAccess().getNameANNOTATION_IDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getAnnotationRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_0_0,
              						"org.integratedmodelling.kactors.Kactors.ANNOTATION_ID");
              				
            }

            }


            }

            // InternalKactors.g:1171:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==35) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalKactors.g:1172:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,35,FOLLOW_21); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getAnnotationAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:1176:4: ( (lv_parameters_2_0= ruleParameterList ) )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==RULE_LOWERCASE_ID||(LA18_0>=RULE_STRING && LA18_0<=RULE_EXPR)||LA18_0==RULE_INT||LA18_0==35||LA18_0==39||LA18_0==42||(LA18_0>=44 && LA18_0<=45)||LA18_0==52||(LA18_0>=70 && LA18_0<=71)) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalKactors.g:1177:5: (lv_parameters_2_0= ruleParameterList )
                            {
                            // InternalKactors.g:1177:5: (lv_parameters_2_0= ruleParameterList )
                            // InternalKactors.g:1178:6: lv_parameters_2_0= ruleParameterList
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getAnnotationAccess().getParametersParameterListParserRuleCall_1_1_0());
                              					
                            }
                            pushFollow(FOLLOW_22);
                            lv_parameters_2_0=ruleParameterList();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElementForParent(grammarAccess.getAnnotationRule());
                              						}
                              						set(
                              							current,
                              							"parameters",
                              							lv_parameters_2_0,
                              							"org.integratedmodelling.kactors.Kactors.ParameterList");
                              						afterParserOrEnumRuleCall();
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_3=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getAnnotationAccess().getRightParenthesisKeyword_1_2());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnnotation"


    // $ANTLR start "entryRuleUrnId"
    // InternalKactors.g:1204:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKactors.g:1204:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKactors.g:1205:2: iv_ruleUrnId= ruleUrnId EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUrnIdRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUrnId=ruleUrnId();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUrnId.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUrnId"


    // $ANTLR start "ruleUrnId"
    // InternalKactors.g:1211:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) ;
    public final AntlrDatatypeRuleToken ruleUrnId() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_8=null;
        AntlrDatatypeRuleToken this_PathName_1 = null;

        AntlrDatatypeRuleToken this_PathName_3 = null;

        AntlrDatatypeRuleToken this_PathName_5 = null;

        AntlrDatatypeRuleToken this_Path_7 = null;

        AntlrDatatypeRuleToken this_VersionNumber_10 = null;

        AntlrDatatypeRuleToken this_Path_12 = null;

        AntlrDatatypeRuleToken this_UrnKvp_13 = null;

        AntlrDatatypeRuleToken this_Path_15 = null;

        AntlrDatatypeRuleToken this_UrnKvp_16 = null;



        	enterRule();

        try {
            // InternalKactors.g:1217:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) )
            // InternalKactors.g:1218:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            {
            // InternalKactors.g:1218:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            // InternalKactors.g:1219:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            {
            // InternalKactors.g:1219:3: (kw= 'urn:klab:' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==39) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalKactors.g:1220:4: kw= 'urn:klab:'
                    {
                    kw=(Token)match(input,39,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getUrnKlabKeyword_0());
                      			
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_13);
            this_PathName_1=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_1);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,34,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_13);
            this_PathName_3=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_3);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,34,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_4());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_13);
            this_PathName_5=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_5);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,34,FOLLOW_23); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            // InternalKactors.g:1271:3: (this_Path_7= rulePath | this_INT_8= RULE_INT )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_LOWERCASE_ID||LA21_0==RULE_UPPERCASE_ID) ) {
                alt21=1;
            }
            else if ( (LA21_0==RULE_INT) ) {
                alt21=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // InternalKactors.g:1272:4: this_Path_7= rulePath
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_7_0());
                      			
                    }
                    pushFollow(FOLLOW_24);
                    this_Path_7=rulePath();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_Path_7);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:1283:4: this_INT_8= RULE_INT
                    {
                    this_INT_8=(Token)match(input,RULE_INT,FOLLOW_24); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_8);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_8, grammarAccess.getUrnIdAccess().getINTTerminalRuleCall_7_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:1291:3: (kw= ':' this_VersionNumber_10= ruleVersionNumber )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==34) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalKactors.g:1292:4: kw= ':' this_VersionNumber_10= ruleVersionNumber
                    {
                    kw=(Token)match(input,34,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_8_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getVersionNumberParserRuleCall_8_1());
                      			
                    }
                    pushFollow(FOLLOW_25);
                    this_VersionNumber_10=ruleVersionNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_VersionNumber_10);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:1308:3: (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==40) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalKactors.g:1309:4: kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    {
                    kw=(Token)match(input,40,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getNumberSignKeyword_9_0());
                      			
                    }
                    // InternalKactors.g:1314:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )
                    int alt23=2;
                    alt23 = dfa23.predict(input);
                    switch (alt23) {
                        case 1 :
                            // InternalKactors.g:1315:5: this_Path_12= rulePath
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_1_0());
                              				
                            }
                            pushFollow(FOLLOW_27);
                            this_Path_12=rulePath();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_Path_12);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:1326:5: this_UrnKvp_13= ruleUrnKvp
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_1_1());
                              				
                            }
                            pushFollow(FOLLOW_27);
                            this_UrnKvp_13=ruleUrnKvp();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_UrnKvp_13);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:1337:4: (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( (LA25_0==41) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // InternalKactors.g:1338:5: kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    {
                    	    kw=(Token)match(input,41,FOLLOW_26); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(kw);
                    	      					newLeafNode(kw, grammarAccess.getUrnIdAccess().getAmpersandKeyword_9_2_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:1343:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    int alt24=2;
                    	    alt24 = dfa24.predict(input);
                    	    switch (alt24) {
                    	        case 1 :
                    	            // InternalKactors.g:1344:6: this_Path_15= rulePath
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_2_1_0());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_27);
                    	            this_Path_15=rulePath();

                    	            state._fsp--;
                    	            if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              						current.merge(this_Path_15);
                    	              					
                    	            }
                    	            if ( state.backtracking==0 ) {

                    	              						afterParserOrEnumRuleCall();
                    	              					
                    	            }

                    	            }
                    	            break;
                    	        case 2 :
                    	            // InternalKactors.g:1355:6: this_UrnKvp_16= ruleUrnKvp
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_2_1_1());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_27);
                    	            this_UrnKvp_16=ruleUrnKvp();

                    	            state._fsp--;
                    	            if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              						current.merge(this_UrnKvp_16);
                    	              					
                    	            }
                    	            if ( state.backtracking==0 ) {

                    	              						afterParserOrEnumRuleCall();
                    	              					
                    	            }

                    	            }
                    	            break;

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop25;
                        }
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUrnId"


    // $ANTLR start "entryRuleUrnKvp"
    // InternalKactors.g:1372:1: entryRuleUrnKvp returns [String current=null] : iv_ruleUrnKvp= ruleUrnKvp EOF ;
    public final String entryRuleUrnKvp() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnKvp = null;


        try {
            // InternalKactors.g:1372:46: (iv_ruleUrnKvp= ruleUrnKvp EOF )
            // InternalKactors.g:1373:2: iv_ruleUrnKvp= ruleUrnKvp EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUrnKvpRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUrnKvp=ruleUrnKvp();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUrnKvp.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUrnKvp"


    // $ANTLR start "ruleUrnKvp"
    // InternalKactors.g:1379:1: ruleUrnKvp returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) ;
    public final AntlrDatatypeRuleToken ruleUrnKvp() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;

        AntlrDatatypeRuleToken this_Path_2 = null;



        	enterRule();

        try {
            // InternalKactors.g:1385:2: ( (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) )
            // InternalKactors.g:1386:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            {
            // InternalKactors.g:1386:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            // InternalKactors.g:1387:3: this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnKvpAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_28);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,38,FOLLOW_23); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnKvpAccess().getEqualsSignKeyword_1());
              		
            }
            // InternalKactors.g:1402:3: (this_Path_2= rulePath | this_INT_3= RULE_INT )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==RULE_LOWERCASE_ID||LA27_0==RULE_UPPERCASE_ID) ) {
                alt27=1;
            }
            else if ( (LA27_0==RULE_INT) ) {
                alt27=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // InternalKactors.g:1403:4: this_Path_2= rulePath
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnKvpAccess().getPathParserRuleCall_2_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_Path_2=rulePath();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_Path_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:1414:4: this_INT_3= RULE_INT
                    {
                    this_INT_3=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_3);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_3, grammarAccess.getUrnKvpAccess().getINTTerminalRuleCall_2_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUrnKvp"


    // $ANTLR start "entryRuleList"
    // InternalKactors.g:1426:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKactors.g:1426:45: (iv_ruleList= ruleList EOF )
            // InternalKactors.g:1427:2: iv_ruleList= ruleList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleList=ruleList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleList; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleList"


    // $ANTLR start "ruleList"
    // InternalKactors.g:1433:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1439:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKactors.g:1440:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKactors.g:1440:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKactors.g:1441:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKactors.g:1441:3: ()
            // InternalKactors.g:1442:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getListAccess().getListAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,35,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getListAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:1455:3: ( (lv_contents_2_0= ruleValue ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==RULE_LOWERCASE_ID||(LA28_0>=RULE_STRING && LA28_0<=RULE_EXPR)||LA28_0==RULE_INT||LA28_0==35||LA28_0==39||LA28_0==42||(LA28_0>=44 && LA28_0<=45)||LA28_0==52||(LA28_0>=70 && LA28_0<=71)) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalKactors.g:1456:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKactors.g:1456:4: (lv_contents_2_0= ruleValue )
            	    // InternalKactors.g:1457:5: lv_contents_2_0= ruleValue
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getListAccess().getContentsValueParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_21);
            	    lv_contents_2_0=ruleValue();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getListRule());
            	      					}
            	      					add(
            	      						current,
            	      						"contents",
            	      						lv_contents_2_0,
            	      						"org.integratedmodelling.kactors.Kactors.Value");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

            otherlv_3=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getListAccess().getRightParenthesisKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleList"


    // $ANTLR start "entryRuleMap"
    // InternalKactors.g:1482:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // InternalKactors.g:1482:44: (iv_ruleMap= ruleMap EOF )
            // InternalKactors.g:1483:2: iv_ruleMap= ruleMap EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMapRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMap=ruleMap();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMap; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMap"


    // $ANTLR start "ruleMap"
    // InternalKactors.g:1489:1: ruleMap returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_entries_2_0 = null;

        EObject lv_entries_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1495:2: ( ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) )
            // InternalKactors.g:1496:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            {
            // InternalKactors.g:1496:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            // InternalKactors.g:1497:3: () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}'
            {
            // InternalKactors.g:1497:3: ()
            // InternalKactors.g:1498:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getMapAccess().getMapAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,42,FOLLOW_29); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:1511:3: ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==RULE_LOWERCASE_ID||LA30_0==RULE_STRING||LA30_0==RULE_OBSERVABLE||LA30_0==RULE_INT||LA30_0==38||(LA30_0>=44 && LA30_0<=45)||(LA30_0>=49 && LA30_0<=51)||(LA30_0>=58 && LA30_0<=62)||(LA30_0>=70 && LA30_0<=71)) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalKactors.g:1512:4: ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    {
                    // InternalKactors.g:1512:4: ( (lv_entries_2_0= ruleMapEntry ) )
                    // InternalKactors.g:1513:5: (lv_entries_2_0= ruleMapEntry )
                    {
                    // InternalKactors.g:1513:5: (lv_entries_2_0= ruleMapEntry )
                    // InternalKactors.g:1514:6: lv_entries_2_0= ruleMapEntry
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_30);
                    lv_entries_2_0=ruleMapEntry();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMapRule());
                      						}
                      						add(
                      							current,
                      							"entries",
                      							lv_entries_2_0,
                      							"org.integratedmodelling.kactors.Kactors.MapEntry");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:1531:4: ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    loop29:
                    do {
                        int alt29=2;
                        int LA29_0 = input.LA(1);

                        if ( (LA29_0==24) ) {
                            alt29=1;
                        }


                        switch (alt29) {
                    	case 1 :
                    	    // InternalKactors.g:1532:5: ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) )
                    	    {
                    	    // InternalKactors.g:1532:5: ( ( ',' )=>otherlv_3= ',' )
                    	    // InternalKactors.g:1533:6: ( ',' )=>otherlv_3= ','
                    	    {
                    	    otherlv_3=(Token)match(input,24,FOLLOW_31); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_3, grammarAccess.getMapAccess().getCommaKeyword_2_1_0_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKactors.g:1540:5: ( (lv_entries_4_0= ruleMapEntry ) )
                    	    // InternalKactors.g:1541:6: (lv_entries_4_0= ruleMapEntry )
                    	    {
                    	    // InternalKactors.g:1541:6: (lv_entries_4_0= ruleMapEntry )
                    	    // InternalKactors.g:1542:7: lv_entries_4_0= ruleMapEntry
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_30);
                    	    lv_entries_4_0=ruleMapEntry();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getMapRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"entries",
                    	      								lv_entries_4_0,
                    	      								"org.integratedmodelling.kactors.Kactors.MapEntry");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop29;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getMapAccess().getRightCurlyBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMap"


    // $ANTLR start "entryRuleMapEntry"
    // InternalKactors.g:1569:1: entryRuleMapEntry returns [EObject current=null] : iv_ruleMapEntry= ruleMapEntry EOF ;
    public final EObject entryRuleMapEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapEntry = null;


        try {
            // InternalKactors.g:1569:49: (iv_ruleMapEntry= ruleMapEntry EOF )
            // InternalKactors.g:1570:2: iv_ruleMapEntry= ruleMapEntry EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMapEntryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMapEntry=ruleMapEntry();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMapEntry; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMapEntry"


    // $ANTLR start "ruleMapEntry"
    // InternalKactors.g:1576:1: ruleMapEntry returns [EObject current=null] : ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleMapEntry() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifier_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1582:2: ( ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKactors.g:1583:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKactors.g:1583:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // InternalKactors.g:1584:3: ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalKactors.g:1584:3: ( (lv_classifier_0_0= ruleClassifier ) )
            // InternalKactors.g:1585:4: (lv_classifier_0_0= ruleClassifier )
            {
            // InternalKactors.g:1585:4: (lv_classifier_0_0= ruleClassifier )
            // InternalKactors.g:1586:5: lv_classifier_0_0= ruleClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMapEntryAccess().getClassifierClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_13);
            lv_classifier_0_0=ruleClassifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getMapEntryRule());
              					}
              					set(
              						current,
              						"classifier",
              						lv_classifier_0_0,
              						"org.integratedmodelling.kactors.Kactors.Classifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_1=(Token)match(input,34,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapEntryAccess().getColonKeyword_1());
              		
            }
            // InternalKactors.g:1607:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKactors.g:1608:4: (lv_value_2_0= ruleValue )
            {
            // InternalKactors.g:1608:4: (lv_value_2_0= ruleValue )
            // InternalKactors.g:1609:5: lv_value_2_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMapEntryAccess().getValueValueParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getMapEntryRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
              						"org.integratedmodelling.kactors.Kactors.Value");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMapEntry"


    // $ANTLR start "entryRuleClassifier"
    // InternalKactors.g:1630:1: entryRuleClassifier returns [EObject current=null] : iv_ruleClassifier= ruleClassifier EOF ;
    public final EObject entryRuleClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifier = null;


        try {
            // InternalKactors.g:1630:51: (iv_ruleClassifier= ruleClassifier EOF )
            // InternalKactors.g:1631:2: iv_ruleClassifier= ruleClassifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getClassifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleClassifier=ruleClassifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleClassifier; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleClassifier"


    // $ANTLR start "ruleClassifier"
    // InternalKactors.g:1637:1: ruleClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) ;
    public final EObject ruleClassifier() throws RecognitionException {
        EObject current = null;

        Token lv_boolean_0_0=null;
        Token lv_boolean_1_0=null;
        Token lv_leftLimit_3_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_rightLimit_7_0=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token lv_string_12_0=null;
        Token lv_observable_13_0=null;
        Token lv_id_14_0=null;
        Token lv_nodata_17_0=null;
        Token lv_star_18_0=null;
        EObject lv_int0_2_0 = null;

        EObject lv_int1_6_0 = null;

        EObject lv_num_9_0 = null;

        EObject lv_set_11_0 = null;

        EObject lv_op_15_0 = null;

        EObject lv_expression_16_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1643:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) )
            // InternalKactors.g:1644:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            {
            // InternalKactors.g:1644:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            int alt34=10;
            alt34 = dfa34.predict(input);
            switch (alt34) {
                case 1 :
                    // InternalKactors.g:1645:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:1645:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==44) ) {
                        alt31=1;
                    }
                    else if ( (LA31_0==45) ) {
                        alt31=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 31, 0, input);

                        throw nvae;
                    }
                    switch (alt31) {
                        case 1 :
                            // InternalKactors.g:1646:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:1646:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:1647:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:1647:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:1648:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_0_0, grammarAccess.getClassifierAccess().getBooleanTrueKeyword_0_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getClassifierRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_0_0, "true");
                              					
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:1661:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:1661:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:1662:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:1662:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:1663:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_1_0, grammarAccess.getClassifierAccess().getBooleanFalseKeyword_0_1_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getClassifierRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_1_0, "false");
                              					
                            }

                            }


                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:1677:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKactors.g:1677:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKactors.g:1678:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKactors.g:1678:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKactors.g:1679:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKactors.g:1679:5: (lv_int0_2_0= ruleNumber )
                    // InternalKactors.g:1680:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_int0_2_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRule());
                      						}
                      						set(
                      							current,
                      							"int0",
                      							lv_int0_2_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:1697:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt32=3;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==46) ) {
                        alt32=1;
                    }
                    else if ( (LA32_0==47) ) {
                        alt32=2;
                    }
                    switch (alt32) {
                        case 1 :
                            // InternalKactors.g:1698:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:1698:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKactors.g:1699:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKactors.g:1699:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKactors.g:1700:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,46,FOLLOW_33); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_leftLimit_3_0, grammarAccess.getClassifierAccess().getLeftLimitInclusiveKeyword_1_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getClassifierRule());
                              							}
                              							setWithLastConsumed(current, "leftLimit", lv_leftLimit_3_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:1713:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,47,FOLLOW_33); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:1718:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKactors.g:1719:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,48,FOLLOW_34); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKactors.g:1725:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKactors.g:1726:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKactors.g:1730:5: (lv_int1_6_0= ruleNumber )
                    // InternalKactors.g:1731:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_35);
                    lv_int1_6_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRule());
                      						}
                      						set(
                      							current,
                      							"int1",
                      							lv_int1_6_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:1748:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt33=3;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==46) ) {
                        alt33=1;
                    }
                    else if ( (LA33_0==47) ) {
                        alt33=2;
                    }
                    switch (alt33) {
                        case 1 :
                            // InternalKactors.g:1749:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:1749:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKactors.g:1750:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKactors.g:1750:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKactors.g:1751:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_rightLimit_7_0, grammarAccess.getClassifierAccess().getRightLimitInclusiveKeyword_1_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getClassifierRule());
                              							}
                              							setWithLastConsumed(current, "rightLimit", lv_rightLimit_7_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:1764:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_8, grammarAccess.getClassifierAccess().getExclusiveKeyword_1_4_1());
                              				
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:1771:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKactors.g:1771:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKactors.g:1772:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKactors.g:1772:4: (lv_num_9_0= ruleNumber )
                    // InternalKactors.g:1773:5: lv_num_9_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getClassifierAccess().getNumNumberParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_num_9_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getClassifierRule());
                      					}
                      					set(
                      						current,
                      						"num",
                      						lv_num_9_0,
                      						"org.integratedmodelling.kactors.Kactors.Number");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:1791:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKactors.g:1791:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKactors.g:1792:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,49,FOLLOW_36); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:1796:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKactors.g:1797:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKactors.g:1797:5: (lv_set_11_0= ruleList )
                    // InternalKactors.g:1798:6: lv_set_11_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getSetListParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_set_11_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRule());
                      						}
                      						set(
                      							current,
                      							"set",
                      							lv_set_11_0,
                      							"org.integratedmodelling.kactors.Kactors.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:1817:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:1817:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKactors.g:1818:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKactors.g:1818:4: (lv_string_12_0= RULE_STRING )
                    // InternalKactors.g:1819:5: lv_string_12_0= RULE_STRING
                    {
                    lv_string_12_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_string_12_0, grammarAccess.getClassifierAccess().getStringSTRINGTerminalRuleCall_4_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"string",
                      						lv_string_12_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:1836:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:1836:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:1837:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:1837:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    // InternalKactors.g:1838:5: lv_observable_13_0= RULE_OBSERVABLE
                    {
                    lv_observable_13_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_observable_13_0, grammarAccess.getClassifierAccess().getObservableOBSERVABLETerminalRuleCall_5_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"observable",
                      						lv_observable_13_0,
                      						"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:1855:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKactors.g:1855:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:1856:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:1856:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:1857:5: lv_id_14_0= RULE_LOWERCASE_ID
                    {
                    lv_id_14_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_id_14_0, grammarAccess.getClassifierAccess().getIdLOWERCASE_IDTerminalRuleCall_6_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"id",
                      						lv_id_14_0,
                      						"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:1874:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:1874:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    // InternalKactors.g:1875:4: ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) )
                    {
                    // InternalKactors.g:1875:4: ( (lv_op_15_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:1876:5: (lv_op_15_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:1876:5: (lv_op_15_0= ruleREL_OPERATOR )
                    // InternalKactors.g:1877:6: lv_op_15_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getOpREL_OPERATORParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_34);
                    lv_op_15_0=ruleREL_OPERATOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRule());
                      						}
                      						set(
                      							current,
                      							"op",
                      							lv_op_15_0,
                      							"org.integratedmodelling.kactors.Kactors.REL_OPERATOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:1894:4: ( (lv_expression_16_0= ruleNumber ) )
                    // InternalKactors.g:1895:5: (lv_expression_16_0= ruleNumber )
                    {
                    // InternalKactors.g:1895:5: (lv_expression_16_0= ruleNumber )
                    // InternalKactors.g:1896:6: lv_expression_16_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getExpressionNumberParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_expression_16_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassifierRule());
                      						}
                      						set(
                      							current,
                      							"expression",
                      							lv_expression_16_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:1915:3: ( (lv_nodata_17_0= 'unknown' ) )
                    {
                    // InternalKactors.g:1915:3: ( (lv_nodata_17_0= 'unknown' ) )
                    // InternalKactors.g:1916:4: (lv_nodata_17_0= 'unknown' )
                    {
                    // InternalKactors.g:1916:4: (lv_nodata_17_0= 'unknown' )
                    // InternalKactors.g:1917:5: lv_nodata_17_0= 'unknown'
                    {
                    lv_nodata_17_0=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_nodata_17_0, grammarAccess.getClassifierAccess().getNodataUnknownKeyword_8_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRule());
                      					}
                      					setWithLastConsumed(current, "nodata", lv_nodata_17_0, "unknown");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKactors.g:1930:3: ( (lv_star_18_0= '*' ) )
                    {
                    // InternalKactors.g:1930:3: ( (lv_star_18_0= '*' ) )
                    // InternalKactors.g:1931:4: (lv_star_18_0= '*' )
                    {
                    // InternalKactors.g:1931:4: (lv_star_18_0= '*' )
                    // InternalKactors.g:1932:5: lv_star_18_0= '*'
                    {
                    lv_star_18_0=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_star_18_0, grammarAccess.getClassifierAccess().getStarAsteriskKeyword_9_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getClassifierRule());
                      					}
                      					setWithLastConsumed(current, "star", true, "*");
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleClassifier"


    // $ANTLR start "entryRuleLookupTable"
    // InternalKactors.g:1948:1: entryRuleLookupTable returns [EObject current=null] : iv_ruleLookupTable= ruleLookupTable EOF ;
    public final EObject entryRuleLookupTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookupTable = null;


        try {
            // InternalKactors.g:1948:52: (iv_ruleLookupTable= ruleLookupTable EOF )
            // InternalKactors.g:1949:2: iv_ruleLookupTable= ruleLookupTable EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLookupTableRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLookupTable=ruleLookupTable();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLookupTable; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLookupTable"


    // $ANTLR start "ruleLookupTable"
    // InternalKactors.g:1955:1: ruleLookupTable returns [EObject current=null] : ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) ;
    public final EObject ruleLookupTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_table_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1961:2: ( ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) )
            // InternalKactors.g:1962:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            {
            // InternalKactors.g:1962:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            // InternalKactors.g:1963:3: () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}'
            {
            // InternalKactors.g:1963:3: ()
            // InternalKactors.g:1964:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getLookupTableAccess().getLookupTableAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,52,FOLLOW_37); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLookupTableAccess().getLeftCurlyBracketLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:1977:3: ( (lv_table_2_0= ruleTable ) )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==RULE_LOWERCASE_ID||LA35_0==RULE_STRING||(LA35_0>=RULE_OBSERVABLE && LA35_0<=RULE_EXPR)||LA35_0==RULE_INT||LA35_0==38||LA35_0==40||(LA35_0>=44 && LA35_0<=45)||(LA35_0>=49 && LA35_0<=51)||(LA35_0>=58 && LA35_0<=62)||(LA35_0>=70 && LA35_0<=71)) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalKactors.g:1978:4: (lv_table_2_0= ruleTable )
                    {
                    // InternalKactors.g:1978:4: (lv_table_2_0= ruleTable )
                    // InternalKactors.g:1979:5: lv_table_2_0= ruleTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLookupTableAccess().getTableTableParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_38);
                    lv_table_2_0=ruleTable();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getLookupTableRule());
                      					}
                      					set(
                      						current,
                      						"table",
                      						lv_table_2_0,
                      						"org.integratedmodelling.kactors.Kactors.Table");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getLookupTableAccess().getRightCurlyBracketRightCurlyBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLookupTable"


    // $ANTLR start "entryRuleTable"
    // InternalKactors.g:2004:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalKactors.g:2004:46: (iv_ruleTable= ruleTable EOF )
            // InternalKactors.g:2005:2: iv_ruleTable= ruleTable EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTableRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTable=ruleTable();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTable; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTable"


    // $ANTLR start "ruleTable"
    // InternalKactors.g:2011:1: ruleTable returns [EObject current=null] : ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token this_SEPARATOR_1=null;
        Token otherlv_3=null;
        EObject lv_headers_0_0 = null;

        EObject lv_rows_2_0 = null;

        EObject lv_rows_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2017:2: ( ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) )
            // InternalKactors.g:2018:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            {
            // InternalKactors.g:2018:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            // InternalKactors.g:2019:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            {
            // InternalKactors.g:2019:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?
            int alt36=2;
            alt36 = dfa36.predict(input);
            switch (alt36) {
                case 1 :
                    // InternalKactors.g:2020:4: ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR
                    {
                    // InternalKactors.g:2020:4: ( (lv_headers_0_0= ruleHeaderRow ) )
                    // InternalKactors.g:2021:5: (lv_headers_0_0= ruleHeaderRow )
                    {
                    // InternalKactors.g:2021:5: (lv_headers_0_0= ruleHeaderRow )
                    // InternalKactors.g:2022:6: lv_headers_0_0= ruleHeaderRow
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableAccess().getHeadersHeaderRowParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_39);
                    lv_headers_0_0=ruleHeaderRow();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableRule());
                      						}
                      						set(
                      							current,
                      							"headers",
                      							lv_headers_0_0,
                      							"org.integratedmodelling.kactors.Kactors.HeaderRow");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    this_SEPARATOR_1=(Token)match(input,RULE_SEPARATOR,FOLLOW_40); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SEPARATOR_1, grammarAccess.getTableAccess().getSEPARATORTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:2044:3: ( (lv_rows_2_0= ruleTableRow ) )
            // InternalKactors.g:2045:4: (lv_rows_2_0= ruleTableRow )
            {
            // InternalKactors.g:2045:4: (lv_rows_2_0= ruleTableRow )
            // InternalKactors.g:2046:5: lv_rows_2_0= ruleTableRow
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_17);
            lv_rows_2_0=ruleTableRow();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTableRule());
              					}
              					add(
              						current,
              						"rows",
              						lv_rows_2_0,
              						"org.integratedmodelling.kactors.Kactors.TableRow");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:2063:3: (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==24) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // InternalKactors.g:2064:4: otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) )
            	    {
            	    otherlv_3=(Token)match(input,24,FOLLOW_40); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getTableAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKactors.g:2068:4: ( (lv_rows_4_0= ruleTableRow ) )
            	    // InternalKactors.g:2069:5: (lv_rows_4_0= ruleTableRow )
            	    {
            	    // InternalKactors.g:2069:5: (lv_rows_4_0= ruleTableRow )
            	    // InternalKactors.g:2070:6: lv_rows_4_0= ruleTableRow
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_17);
            	    lv_rows_4_0=ruleTableRow();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getTableRule());
            	      						}
            	      						add(
            	      							current,
            	      							"rows",
            	      							lv_rows_4_0,
            	      							"org.integratedmodelling.kactors.Kactors.TableRow");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTable"


    // $ANTLR start "entryRuleHeaderRow"
    // InternalKactors.g:2092:1: entryRuleHeaderRow returns [EObject current=null] : iv_ruleHeaderRow= ruleHeaderRow EOF ;
    public final EObject entryRuleHeaderRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHeaderRow = null;


        try {
            // InternalKactors.g:2092:50: (iv_ruleHeaderRow= ruleHeaderRow EOF )
            // InternalKactors.g:2093:2: iv_ruleHeaderRow= ruleHeaderRow EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getHeaderRowRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleHeaderRow=ruleHeaderRow();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleHeaderRow; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleHeaderRow"


    // $ANTLR start "ruleHeaderRow"
    // InternalKactors.g:2099:1: ruleHeaderRow returns [EObject current=null] : ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) ;
    public final EObject ruleHeaderRow() throws RecognitionException {
        EObject current = null;

        Token lv_elements_0_1=null;
        Token lv_elements_0_2=null;
        Token otherlv_1=null;
        Token lv_elements_2_1=null;
        Token lv_elements_2_2=null;


        	enterRule();

        try {
            // InternalKactors.g:2105:2: ( ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) )
            // InternalKactors.g:2106:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            {
            // InternalKactors.g:2106:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            // InternalKactors.g:2107:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            {
            // InternalKactors.g:2107:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) )
            // InternalKactors.g:2108:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            {
            // InternalKactors.g:2108:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            // InternalKactors.g:2109:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            {
            // InternalKactors.g:2109:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==RULE_LOWERCASE_ID) ) {
                alt38=1;
            }
            else if ( (LA38_0==RULE_STRING) ) {
                alt38=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // InternalKactors.g:2110:6: lv_elements_0_1= RULE_LOWERCASE_ID
                    {
                    lv_elements_0_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_41); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_elements_0_1, grammarAccess.getHeaderRowAccess().getElementsLOWERCASE_IDTerminalRuleCall_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getHeaderRowRule());
                      						}
                      						addWithLastConsumed(
                      							current,
                      							"elements",
                      							lv_elements_0_1,
                      							"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:2125:6: lv_elements_0_2= RULE_STRING
                    {
                    lv_elements_0_2=(Token)match(input,RULE_STRING,FOLLOW_41); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_elements_0_2, grammarAccess.getHeaderRowAccess().getElementsSTRINGTerminalRuleCall_0_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getHeaderRowRule());
                      						}
                      						addWithLastConsumed(
                      							current,
                      							"elements",
                      							lv_elements_0_2,
                      							"org.eclipse.xtext.common.Terminals.STRING");
                      					
                    }

                    }
                    break;

            }


            }


            }

            // InternalKactors.g:2142:3: (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==54) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalKactors.g:2143:4: otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    {
            	    otherlv_1=(Token)match(input,54,FOLLOW_42); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getHeaderRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:2147:4: ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    // InternalKactors.g:2148:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:2148:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    // InternalKactors.g:2149:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    {
            	    // InternalKactors.g:2149:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    int alt39=2;
            	    int LA39_0 = input.LA(1);

            	    if ( (LA39_0==RULE_LOWERCASE_ID) ) {
            	        alt39=1;
            	    }
            	    else if ( (LA39_0==RULE_STRING) ) {
            	        alt39=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 39, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt39) {
            	        case 1 :
            	            // InternalKactors.g:2150:7: lv_elements_2_1= RULE_LOWERCASE_ID
            	            {
            	            lv_elements_2_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_41); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              							newLeafNode(lv_elements_2_1, grammarAccess.getHeaderRowAccess().getElementsLOWERCASE_IDTerminalRuleCall_1_1_0_0());
            	              						
            	            }
            	            if ( state.backtracking==0 ) {

            	              							if (current==null) {
            	              								current = createModelElement(grammarAccess.getHeaderRowRule());
            	              							}
            	              							addWithLastConsumed(
            	              								current,
            	              								"elements",
            	              								lv_elements_2_1,
            	              								"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
            	              						
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:2165:7: lv_elements_2_2= RULE_STRING
            	            {
            	            lv_elements_2_2=(Token)match(input,RULE_STRING,FOLLOW_41); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              							newLeafNode(lv_elements_2_2, grammarAccess.getHeaderRowAccess().getElementsSTRINGTerminalRuleCall_1_1_0_1());
            	              						
            	            }
            	            if ( state.backtracking==0 ) {

            	              							if (current==null) {
            	              								current = createModelElement(grammarAccess.getHeaderRowRule());
            	              							}
            	              							addWithLastConsumed(
            	              								current,
            	              								"elements",
            	              								lv_elements_2_2,
            	              								"org.eclipse.xtext.common.Terminals.STRING");
            	              						
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleHeaderRow"


    // $ANTLR start "entryRuleTableRow"
    // InternalKactors.g:2187:1: entryRuleTableRow returns [EObject current=null] : iv_ruleTableRow= ruleTableRow EOF ;
    public final EObject entryRuleTableRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableRow = null;


        try {
            // InternalKactors.g:2187:49: (iv_ruleTableRow= ruleTableRow EOF )
            // InternalKactors.g:2188:2: iv_ruleTableRow= ruleTableRow EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTableRowRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTableRow=ruleTableRow();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTableRow; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTableRow"


    // $ANTLR start "ruleTableRow"
    // InternalKactors.g:2194:1: ruleTableRow returns [EObject current=null] : ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) ;
    public final EObject ruleTableRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_elements_0_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2200:2: ( ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) )
            // InternalKactors.g:2201:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            {
            // InternalKactors.g:2201:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            // InternalKactors.g:2202:3: ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            {
            // InternalKactors.g:2202:3: ( (lv_elements_0_0= ruleTableClassifier ) )
            // InternalKactors.g:2203:4: (lv_elements_0_0= ruleTableClassifier )
            {
            // InternalKactors.g:2203:4: (lv_elements_0_0= ruleTableClassifier )
            // InternalKactors.g:2204:5: lv_elements_0_0= ruleTableClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_41);
            lv_elements_0_0=ruleTableClassifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTableRowRule());
              					}
              					add(
              						current,
              						"elements",
              						lv_elements_0_0,
              						"org.integratedmodelling.kactors.Kactors.TableClassifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:2221:3: (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==54) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalKactors.g:2222:4: otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) )
            	    {
            	    otherlv_1=(Token)match(input,54,FOLLOW_40); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:2226:4: ( (lv_elements_2_0= ruleTableClassifier ) )
            	    // InternalKactors.g:2227:5: (lv_elements_2_0= ruleTableClassifier )
            	    {
            	    // InternalKactors.g:2227:5: (lv_elements_2_0= ruleTableClassifier )
            	    // InternalKactors.g:2228:6: lv_elements_2_0= ruleTableClassifier
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_41);
            	    lv_elements_2_0=ruleTableClassifier();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getTableRowRule());
            	      						}
            	      						add(
            	      							current,
            	      							"elements",
            	      							lv_elements_2_0,
            	      							"org.integratedmodelling.kactors.Kactors.TableClassifier");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTableRow"


    // $ANTLR start "entryRuleTableClassifier"
    // InternalKactors.g:2250:1: entryRuleTableClassifier returns [EObject current=null] : iv_ruleTableClassifier= ruleTableClassifier EOF ;
    public final EObject entryRuleTableClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableClassifier = null;


        try {
            // InternalKactors.g:2250:56: (iv_ruleTableClassifier= ruleTableClassifier EOF )
            // InternalKactors.g:2251:2: iv_ruleTableClassifier= ruleTableClassifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTableClassifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTableClassifier=ruleTableClassifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTableClassifier; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTableClassifier"


    // $ANTLR start "ruleTableClassifier"
    // InternalKactors.g:2257:1: ruleTableClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) ;
    public final EObject ruleTableClassifier() throws RecognitionException {
        EObject current = null;

        Token lv_boolean_0_0=null;
        Token lv_boolean_1_0=null;
        Token lv_string_3_0=null;
        Token lv_observable_4_0=null;
        Token lv_leftLimit_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token lv_rightLimit_12_0=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token lv_expr_18_0=null;
        Token lv_nodata_19_0=null;
        Token lv_star_20_0=null;
        Token lv_anything_21_0=null;
        EObject lv_num_2_0 = null;

        EObject lv_op_5_0 = null;

        EObject lv_expression_6_0 = null;

        EObject lv_int0_7_0 = null;

        EObject lv_int1_11_0 = null;

        EObject lv_set_15_0 = null;

        EObject lv_quantity_16_0 = null;

        EObject lv_date_17_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2263:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) )
            // InternalKactors.g:2264:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            {
            // InternalKactors.g:2264:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            int alt45=13;
            alt45 = dfa45.predict(input);
            switch (alt45) {
                case 1 :
                    // InternalKactors.g:2265:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:2265:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==44) ) {
                        alt42=1;
                    }
                    else if ( (LA42_0==45) ) {
                        alt42=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 42, 0, input);

                        throw nvae;
                    }
                    switch (alt42) {
                        case 1 :
                            // InternalKactors.g:2266:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:2266:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:2267:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:2267:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:2268:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_0_0, grammarAccess.getTableClassifierAccess().getBooleanTrueKeyword_0_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getTableClassifierRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_0_0, "true");
                              					
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:2281:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:2281:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:2282:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:2282:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:2283:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_1_0, grammarAccess.getTableClassifierAccess().getBooleanFalseKeyword_0_1_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getTableClassifierRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_1_0, "false");
                              					
                            }

                            }


                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:2297:3: ( (lv_num_2_0= ruleNumber ) )
                    {
                    // InternalKactors.g:2297:3: ( (lv_num_2_0= ruleNumber ) )
                    // InternalKactors.g:2298:4: (lv_num_2_0= ruleNumber )
                    {
                    // InternalKactors.g:2298:4: (lv_num_2_0= ruleNumber )
                    // InternalKactors.g:2299:5: lv_num_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getTableClassifierAccess().getNumNumberParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_num_2_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      					}
                      					set(
                      						current,
                      						"num",
                      						lv_num_2_0,
                      						"org.integratedmodelling.kactors.Kactors.Number");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:2317:3: ( (lv_string_3_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:2317:3: ( (lv_string_3_0= RULE_STRING ) )
                    // InternalKactors.g:2318:4: (lv_string_3_0= RULE_STRING )
                    {
                    // InternalKactors.g:2318:4: (lv_string_3_0= RULE_STRING )
                    // InternalKactors.g:2319:5: lv_string_3_0= RULE_STRING
                    {
                    lv_string_3_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_string_3_0, grammarAccess.getTableClassifierAccess().getStringSTRINGTerminalRuleCall_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"string",
                      						lv_string_3_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:2336:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:2336:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:2337:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:2337:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    // InternalKactors.g:2338:5: lv_observable_4_0= RULE_OBSERVABLE
                    {
                    lv_observable_4_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_observable_4_0, grammarAccess.getTableClassifierAccess().getObservableOBSERVABLETerminalRuleCall_3_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"observable",
                      						lv_observable_4_0,
                      						"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:2355:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:2355:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    // InternalKactors.g:2356:4: ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) )
                    {
                    // InternalKactors.g:2356:4: ( (lv_op_5_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:2357:5: (lv_op_5_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:2357:5: (lv_op_5_0= ruleREL_OPERATOR )
                    // InternalKactors.g:2358:6: lv_op_5_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getOpREL_OPERATORParserRuleCall_4_0_0());
                      					
                    }
                    pushFollow(FOLLOW_34);
                    lv_op_5_0=ruleREL_OPERATOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"op",
                      							lv_op_5_0,
                      							"org.integratedmodelling.kactors.Kactors.REL_OPERATOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:2375:4: ( (lv_expression_6_0= ruleNumber ) )
                    // InternalKactors.g:2376:5: (lv_expression_6_0= ruleNumber )
                    {
                    // InternalKactors.g:2376:5: (lv_expression_6_0= ruleNumber )
                    // InternalKactors.g:2377:6: lv_expression_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getExpressionNumberParserRuleCall_4_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_expression_6_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"expression",
                      							lv_expression_6_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:2396:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    {
                    // InternalKactors.g:2396:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    // InternalKactors.g:2397:4: ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    {
                    // InternalKactors.g:2397:4: ( (lv_int0_7_0= ruleNumber ) )
                    // InternalKactors.g:2398:5: (lv_int0_7_0= ruleNumber )
                    {
                    // InternalKactors.g:2398:5: (lv_int0_7_0= ruleNumber )
                    // InternalKactors.g:2399:6: lv_int0_7_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt0NumberParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_int0_7_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"int0",
                      							lv_int0_7_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:2416:4: ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )?
                    int alt43=3;
                    int LA43_0 = input.LA(1);

                    if ( (LA43_0==46) ) {
                        alt43=1;
                    }
                    else if ( (LA43_0==47) ) {
                        alt43=2;
                    }
                    switch (alt43) {
                        case 1 :
                            // InternalKactors.g:2417:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:2417:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            // InternalKactors.g:2418:6: (lv_leftLimit_8_0= 'inclusive' )
                            {
                            // InternalKactors.g:2418:6: (lv_leftLimit_8_0= 'inclusive' )
                            // InternalKactors.g:2419:7: lv_leftLimit_8_0= 'inclusive'
                            {
                            lv_leftLimit_8_0=(Token)match(input,46,FOLLOW_33); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_leftLimit_8_0, grammarAccess.getTableClassifierAccess().getLeftLimitInclusiveKeyword_5_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getTableClassifierRule());
                              							}
                              							setWithLastConsumed(current, "leftLimit", lv_leftLimit_8_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:2432:5: otherlv_9= 'exclusive'
                            {
                            otherlv_9=(Token)match(input,47,FOLLOW_33); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_9, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_5_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:2437:4: ( ( 'to' )=>otherlv_10= 'to' )
                    // InternalKactors.g:2438:5: ( 'to' )=>otherlv_10= 'to'
                    {
                    otherlv_10=(Token)match(input,48,FOLLOW_34); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_10, grammarAccess.getTableClassifierAccess().getToKeyword_5_2());
                      				
                    }

                    }

                    // InternalKactors.g:2444:4: ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) )
                    // InternalKactors.g:2445:5: ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber )
                    {
                    // InternalKactors.g:2449:5: (lv_int1_11_0= ruleNumber )
                    // InternalKactors.g:2450:6: lv_int1_11_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt1NumberParserRuleCall_5_3_0());
                      					
                    }
                    pushFollow(FOLLOW_35);
                    lv_int1_11_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"int1",
                      							lv_int1_11_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:2467:4: ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    int alt44=3;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==46) ) {
                        alt44=1;
                    }
                    else if ( (LA44_0==47) ) {
                        alt44=2;
                    }
                    switch (alt44) {
                        case 1 :
                            // InternalKactors.g:2468:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:2468:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            // InternalKactors.g:2469:6: (lv_rightLimit_12_0= 'inclusive' )
                            {
                            // InternalKactors.g:2469:6: (lv_rightLimit_12_0= 'inclusive' )
                            // InternalKactors.g:2470:7: lv_rightLimit_12_0= 'inclusive'
                            {
                            lv_rightLimit_12_0=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_rightLimit_12_0, grammarAccess.getTableClassifierAccess().getRightLimitInclusiveKeyword_5_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getTableClassifierRule());
                              							}
                              							setWithLastConsumed(current, "rightLimit", lv_rightLimit_12_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:2483:5: otherlv_13= 'exclusive'
                            {
                            otherlv_13=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_13, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_5_4_1());
                              				
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:2490:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    {
                    // InternalKactors.g:2490:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    // InternalKactors.g:2491:4: otherlv_14= 'in' ( (lv_set_15_0= ruleList ) )
                    {
                    otherlv_14=(Token)match(input,49,FOLLOW_36); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getTableClassifierAccess().getInKeyword_6_0());
                      			
                    }
                    // InternalKactors.g:2495:4: ( (lv_set_15_0= ruleList ) )
                    // InternalKactors.g:2496:5: (lv_set_15_0= ruleList )
                    {
                    // InternalKactors.g:2496:5: (lv_set_15_0= ruleList )
                    // InternalKactors.g:2497:6: lv_set_15_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getSetListParserRuleCall_6_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_set_15_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"set",
                      							lv_set_15_0,
                      							"org.integratedmodelling.kactors.Kactors.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:2516:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    {
                    // InternalKactors.g:2516:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    // InternalKactors.g:2517:4: (lv_quantity_16_0= ruleQuantity )
                    {
                    // InternalKactors.g:2517:4: (lv_quantity_16_0= ruleQuantity )
                    // InternalKactors.g:2518:5: lv_quantity_16_0= ruleQuantity
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getTableClassifierAccess().getQuantityQuantityParserRuleCall_7_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_quantity_16_0=ruleQuantity();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      					}
                      					set(
                      						current,
                      						"quantity",
                      						lv_quantity_16_0,
                      						"org.integratedmodelling.kactors.Kactors.Quantity");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:2536:3: ( (lv_date_17_0= ruleDate ) )
                    {
                    // InternalKactors.g:2536:3: ( (lv_date_17_0= ruleDate ) )
                    // InternalKactors.g:2537:4: (lv_date_17_0= ruleDate )
                    {
                    // InternalKactors.g:2537:4: (lv_date_17_0= ruleDate )
                    // InternalKactors.g:2538:5: lv_date_17_0= ruleDate
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getTableClassifierAccess().getDateDateParserRuleCall_8_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_date_17_0=ruleDate();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      					}
                      					set(
                      						current,
                      						"date",
                      						lv_date_17_0,
                      						"org.integratedmodelling.kactors.Kactors.Date");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKactors.g:2556:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:2556:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    // InternalKactors.g:2557:4: (lv_expr_18_0= RULE_EXPR )
                    {
                    // InternalKactors.g:2557:4: (lv_expr_18_0= RULE_EXPR )
                    // InternalKactors.g:2558:5: lv_expr_18_0= RULE_EXPR
                    {
                    lv_expr_18_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_expr_18_0, grammarAccess.getTableClassifierAccess().getExprEXPRTerminalRuleCall_9_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"expr",
                      						lv_expr_18_0,
                      						"org.integratedmodelling.kactors.Kactors.EXPR");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 11 :
                    // InternalKactors.g:2575:3: ( (lv_nodata_19_0= 'unknown' ) )
                    {
                    // InternalKactors.g:2575:3: ( (lv_nodata_19_0= 'unknown' ) )
                    // InternalKactors.g:2576:4: (lv_nodata_19_0= 'unknown' )
                    {
                    // InternalKactors.g:2576:4: (lv_nodata_19_0= 'unknown' )
                    // InternalKactors.g:2577:5: lv_nodata_19_0= 'unknown'
                    {
                    lv_nodata_19_0=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_nodata_19_0, grammarAccess.getTableClassifierAccess().getNodataUnknownKeyword_10_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(current, "nodata", lv_nodata_19_0, "unknown");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 12 :
                    // InternalKactors.g:2590:3: ( (lv_star_20_0= '*' ) )
                    {
                    // InternalKactors.g:2590:3: ( (lv_star_20_0= '*' ) )
                    // InternalKactors.g:2591:4: (lv_star_20_0= '*' )
                    {
                    // InternalKactors.g:2591:4: (lv_star_20_0= '*' )
                    // InternalKactors.g:2592:5: lv_star_20_0= '*'
                    {
                    lv_star_20_0=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_star_20_0, grammarAccess.getTableClassifierAccess().getStarAsteriskKeyword_11_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(current, "star", true, "*");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 13 :
                    // InternalKactors.g:2605:3: ( (lv_anything_21_0= '#' ) )
                    {
                    // InternalKactors.g:2605:3: ( (lv_anything_21_0= '#' ) )
                    // InternalKactors.g:2606:4: (lv_anything_21_0= '#' )
                    {
                    // InternalKactors.g:2606:4: (lv_anything_21_0= '#' )
                    // InternalKactors.g:2607:5: lv_anything_21_0= '#'
                    {
                    lv_anything_21_0=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_anything_21_0, grammarAccess.getTableClassifierAccess().getAnythingNumberSignKeyword_12_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(current, "anything", true, "#");
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTableClassifier"


    // $ANTLR start "entryRuleQuantity"
    // InternalKactors.g:2623:1: entryRuleQuantity returns [EObject current=null] : iv_ruleQuantity= ruleQuantity EOF ;
    public final EObject entryRuleQuantity() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQuantity = null;


        try {
            // InternalKactors.g:2623:49: (iv_ruleQuantity= ruleQuantity EOF )
            // InternalKactors.g:2624:2: iv_ruleQuantity= ruleQuantity EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQuantityRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleQuantity=ruleQuantity();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQuantity; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQuantity"


    // $ANTLR start "ruleQuantity"
    // InternalKactors.g:2630:1: ruleQuantity returns [EObject current=null] : ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) ;
    public final EObject ruleQuantity() throws RecognitionException {
        EObject current = null;

        Token lv_over_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_0_0 = null;

        EObject lv_unit_3_0 = null;

        EObject lv_currency_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2636:2: ( ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) )
            // InternalKactors.g:2637:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            {
            // InternalKactors.g:2637:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            // InternalKactors.g:2638:3: ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            {
            // InternalKactors.g:2638:3: ( (lv_value_0_0= ruleNumber ) )
            // InternalKactors.g:2639:4: (lv_value_0_0= ruleNumber )
            {
            // InternalKactors.g:2639:4: (lv_value_0_0= ruleNumber )
            // InternalKactors.g:2640:5: lv_value_0_0= ruleNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getQuantityAccess().getValueNumberParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_43);
            lv_value_0_0=ruleNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getQuantityRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_0_0,
              						"org.integratedmodelling.kactors.Kactors.Number");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:2657:3: ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' )
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==55) ) {
                alt46=1;
            }
            else if ( (LA46_0==56) ) {
                alt46=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }
            switch (alt46) {
                case 1 :
                    // InternalKactors.g:2658:4: ( (lv_over_1_0= '/' ) )
                    {
                    // InternalKactors.g:2658:4: ( (lv_over_1_0= '/' ) )
                    // InternalKactors.g:2659:5: (lv_over_1_0= '/' )
                    {
                    // InternalKactors.g:2659:5: (lv_over_1_0= '/' )
                    // InternalKactors.g:2660:6: lv_over_1_0= '/'
                    {
                    lv_over_1_0=(Token)match(input,55,FOLLOW_44); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_over_1_0, grammarAccess.getQuantityAccess().getOverSolidusKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getQuantityRule());
                      						}
                      						setWithLastConsumed(current, "over", true, "/");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:2673:4: otherlv_2= '.'
                    {
                    otherlv_2=(Token)match(input,56,FOLLOW_44); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getQuantityAccess().getFullStopKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:2678:3: ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==EOF||LA47_0==RULE_LOWERCASE_ID||LA47_0==RULE_CAMELCASE_ID||LA47_0==24||LA47_0==35||LA47_0==51||(LA47_0>=53 && LA47_0<=55)||LA47_0==69||LA47_0==78) ) {
                alt47=1;
            }
            else if ( (LA47_0==RULE_UPPERCASE_ID) ) {
                int LA47_2 = input.LA(2);

                if ( (LA47_2==EOF||LA47_2==24||LA47_2==51||(LA47_2>=53 && LA47_2<=55)||LA47_2==69||LA47_2==78) ) {
                    alt47=1;
                }
                else if ( (LA47_2==57) ) {
                    alt47=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 47, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // InternalKactors.g:2679:4: ( (lv_unit_3_0= ruleUnit ) )
                    {
                    // InternalKactors.g:2679:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKactors.g:2680:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKactors.g:2680:5: (lv_unit_3_0= ruleUnit )
                    // InternalKactors.g:2681:6: lv_unit_3_0= ruleUnit
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getQuantityAccess().getUnitUnitParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_unit_3_0=ruleUnit();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getQuantityRule());
                      						}
                      						set(
                      							current,
                      							"unit",
                      							lv_unit_3_0,
                      							"org.integratedmodelling.kactors.Kactors.Unit");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:2699:4: ( (lv_currency_4_0= ruleCurrency ) )
                    {
                    // InternalKactors.g:2699:4: ( (lv_currency_4_0= ruleCurrency ) )
                    // InternalKactors.g:2700:5: (lv_currency_4_0= ruleCurrency )
                    {
                    // InternalKactors.g:2700:5: (lv_currency_4_0= ruleCurrency )
                    // InternalKactors.g:2701:6: lv_currency_4_0= ruleCurrency
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getQuantityAccess().getCurrencyCurrencyParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_currency_4_0=ruleCurrency();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getQuantityRule());
                      						}
                      						set(
                      							current,
                      							"currency",
                      							lv_currency_4_0,
                      							"org.integratedmodelling.kactors.Kactors.Currency");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQuantity"


    // $ANTLR start "entryRuleUnitElement"
    // InternalKactors.g:2723:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKactors.g:2723:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKactors.g:2724:2: iv_ruleUnitElement= ruleUnitElement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnitElementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnitElement=ruleUnitElement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnitElement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnitElement"


    // $ANTLR start "ruleUnitElement"
    // InternalKactors.g:2730:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) ;
    public final EObject ruleUnitElement() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_1=null;
        Token lv_id_0_2=null;
        Token lv_id_0_3=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_unit_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2736:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) )
            // InternalKactors.g:2737:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            {
            // InternalKactors.g:2737:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==RULE_LOWERCASE_ID||(LA49_0>=RULE_CAMELCASE_ID && LA49_0<=RULE_UPPERCASE_ID)) ) {
                alt49=1;
            }
            else if ( (LA49_0==35) ) {
                alt49=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // InternalKactors.g:2738:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    {
                    // InternalKactors.g:2738:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    // InternalKactors.g:2739:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKactors.g:2739:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    // InternalKactors.g:2740:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    {
                    // InternalKactors.g:2740:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    int alt48=3;
                    switch ( input.LA(1) ) {
                    case RULE_CAMELCASE_ID:
                        {
                        alt48=1;
                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        alt48=2;
                        }
                        break;
                    case RULE_UPPERCASE_ID:
                        {
                        alt48=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 48, 0, input);

                        throw nvae;
                    }

                    switch (alt48) {
                        case 1 :
                            // InternalKactors.g:2741:6: lv_id_0_1= RULE_CAMELCASE_ID
                            {
                            lv_id_0_1=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_id_0_1, grammarAccess.getUnitElementAccess().getIdCAMELCASE_IDTerminalRuleCall_0_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getUnitElementRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"id",
                              							lv_id_0_1,
                              							"org.integratedmodelling.kactors.Kactors.CAMELCASE_ID");
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:2756:6: lv_id_0_2= RULE_LOWERCASE_ID
                            {
                            lv_id_0_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_id_0_2, grammarAccess.getUnitElementAccess().getIdLOWERCASE_IDTerminalRuleCall_0_0_1());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getUnitElementRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"id",
                              							lv_id_0_2,
                              							"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                              					
                            }

                            }
                            break;
                        case 3 :
                            // InternalKactors.g:2771:6: lv_id_0_3= RULE_UPPERCASE_ID
                            {
                            lv_id_0_3=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_id_0_3, grammarAccess.getUnitElementAccess().getIdUPPERCASE_IDTerminalRuleCall_0_0_2());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getUnitElementRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"id",
                              							lv_id_0_3,
                              							"org.integratedmodelling.kactors.Kactors.UPPERCASE_ID");
                              					
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:2789:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    {
                    // InternalKactors.g:2789:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    // InternalKactors.g:2790:4: otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,35,FOLLOW_45); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:2794:4: ( (lv_unit_2_0= ruleUnit ) )
                    // InternalKactors.g:2795:5: (lv_unit_2_0= ruleUnit )
                    {
                    // InternalKactors.g:2795:5: (lv_unit_2_0= ruleUnit )
                    // InternalKactors.g:2796:6: lv_unit_2_0= ruleUnit
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getUnitElementAccess().getUnitUnitParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_22);
                    lv_unit_2_0=ruleUnit();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getUnitElementRule());
                      						}
                      						set(
                      							current,
                      							"unit",
                      							lv_unit_2_0,
                      							"org.integratedmodelling.kactors.Kactors.Unit");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_3=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getUnitElementAccess().getRightParenthesisKeyword_1_2());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnitElement"


    // $ANTLR start "entryRuleUnit"
    // InternalKactors.g:2822:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKactors.g:2822:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKactors.g:2823:2: iv_ruleUnit= ruleUnit EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnitRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnit=ruleUnit();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnit; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnit"


    // $ANTLR start "ruleUnit"
    // InternalKactors.g:2829:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2835:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:2836:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:2836:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:2837:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:2837:3: ()
            // InternalKactors.g:2838:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getUnitAccess().getUnitAction_0(),
              					current);
              			
            }

            }

            // InternalKactors.g:2847:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==RULE_LOWERCASE_ID||(LA50_0>=RULE_CAMELCASE_ID && LA50_0<=RULE_UPPERCASE_ID)||LA50_0==35) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // InternalKactors.g:2848:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKactors.g:2848:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKactors.g:2849:5: lv_root_1_0= ruleUnitElement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUnitAccess().getRootUnitElementParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_46);
                    lv_root_1_0=ruleUnitElement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getUnitRule());
                      					}
                      					set(
                      						current,
                      						"root",
                      						lv_root_1_0,
                      						"org.integratedmodelling.kactors.Kactors.UnitElement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalKactors.g:2866:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==51||LA51_0==55||LA51_0==78) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalKactors.g:2867:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:2867:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKactors.g:2868:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKactors.g:2874:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKactors.g:2875:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKactors.g:2875:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKactors.g:2876:7: lv_connectors_2_0= ruleUnitOp
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getUnitAccess().getConnectorsUnitOpEnumRuleCall_2_0_0_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_47);
            	    lv_connectors_2_0=ruleUnitOp();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getUnitRule());
            	      							}
            	      							add(
            	      								current,
            	      								"connectors",
            	      								lv_connectors_2_0,
            	      								"org.integratedmodelling.kactors.Kactors.UnitOp");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalKactors.g:2894:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKactors.g:2895:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:2895:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKactors.g:2896:6: lv_units_3_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getUnitAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_46);
            	    lv_units_3_0=ruleUnitElement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getUnitRule());
            	      						}
            	      						add(
            	      							current,
            	      							"units",
            	      							lv_units_3_0,
            	      							"org.integratedmodelling.kactors.Kactors.UnitElement");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnit"


    // $ANTLR start "entryRuleCurrency"
    // InternalKactors.g:2918:1: entryRuleCurrency returns [EObject current=null] : iv_ruleCurrency= ruleCurrency EOF ;
    public final EObject entryRuleCurrency() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCurrency = null;


        try {
            // InternalKactors.g:2918:49: (iv_ruleCurrency= ruleCurrency EOF )
            // InternalKactors.g:2919:2: iv_ruleCurrency= ruleCurrency EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCurrencyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleCurrency=ruleCurrency();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCurrency; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCurrency"


    // $ANTLR start "ruleCurrency"
    // InternalKactors.g:2925:1: ruleCurrency returns [EObject current=null] : ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleCurrency() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_year_2_0=null;
        Token otherlv_3=null;
        EObject lv_units_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2931:2: ( ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:2932:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:2932:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:2933:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:2933:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) )
            // InternalKactors.g:2934:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            {
            // InternalKactors.g:2934:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            // InternalKactors.g:2935:5: lv_id_0_0= RULE_UPPERCASE_ID
            {
            lv_id_0_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_48); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_id_0_0, grammarAccess.getCurrencyAccess().getIdUPPERCASE_IDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getCurrencyRule());
              					}
              					setWithLastConsumed(
              						current,
              						"id",
              						lv_id_0_0,
              						"org.integratedmodelling.kactors.Kactors.UPPERCASE_ID");
              				
            }

            }


            }

            // InternalKactors.g:2951:3: (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) )
            // InternalKactors.g:2952:4: otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) )
            {
            otherlv_1=(Token)match(input,57,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_1, grammarAccess.getCurrencyAccess().getCommercialAtKeyword_1_0());
              			
            }
            // InternalKactors.g:2956:4: ( (lv_year_2_0= RULE_INT ) )
            // InternalKactors.g:2957:5: (lv_year_2_0= RULE_INT )
            {
            // InternalKactors.g:2957:5: (lv_year_2_0= RULE_INT )
            // InternalKactors.g:2958:6: lv_year_2_0= RULE_INT
            {
            lv_year_2_0=(Token)match(input,RULE_INT,FOLLOW_49); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              						newLeafNode(lv_year_2_0, grammarAccess.getCurrencyAccess().getYearINTTerminalRuleCall_1_1_0());
              					
            }
            if ( state.backtracking==0 ) {

              						if (current==null) {
              							current = createModelElement(grammarAccess.getCurrencyRule());
              						}
              						setWithLastConsumed(
              							current,
              							"year",
              							lv_year_2_0,
              							"org.eclipse.xtext.common.Terminals.INT");
              					
            }

            }


            }


            }

            // InternalKactors.g:2975:3: ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==55) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // InternalKactors.g:2976:4: ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:2976:4: ( ( '/' )=>otherlv_3= '/' )
            	    // InternalKactors.g:2977:5: ( '/' )=>otherlv_3= '/'
            	    {
            	    otherlv_3=(Token)match(input,55,FOLLOW_47); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_3, grammarAccess.getCurrencyAccess().getSolidusKeyword_2_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:2983:4: ( (lv_units_4_0= ruleUnitElement ) )
            	    // InternalKactors.g:2984:5: (lv_units_4_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:2984:5: (lv_units_4_0= ruleUnitElement )
            	    // InternalKactors.g:2985:6: lv_units_4_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getCurrencyAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_49);
            	    lv_units_4_0=ruleUnitElement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getCurrencyRule());
            	      						}
            	      						add(
            	      							current,
            	      							"units",
            	      							lv_units_4_0,
            	      							"org.integratedmodelling.kactors.Kactors.UnitElement");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop52;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCurrency"


    // $ANTLR start "entryRuleREL_OPERATOR"
    // InternalKactors.g:3007:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKactors.g:3007:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKactors.g:3008:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getREL_OPERATORRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleREL_OPERATOR=ruleREL_OPERATOR();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleREL_OPERATOR; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleREL_OPERATOR"


    // $ANTLR start "ruleREL_OPERATOR"
    // InternalKactors.g:3014:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
    public final EObject ruleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        Token lv_gt_0_0=null;
        Token lv_lt_1_0=null;
        Token lv_eq_2_0=null;
        Token lv_ne_3_0=null;
        Token lv_le_4_0=null;
        Token lv_ge_5_0=null;


        	enterRule();

        try {
            // InternalKactors.g:3020:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKactors.g:3021:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKactors.g:3021:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt53=6;
            switch ( input.LA(1) ) {
            case 58:
                {
                alt53=1;
                }
                break;
            case 59:
                {
                alt53=2;
                }
                break;
            case 38:
                {
                alt53=3;
                }
                break;
            case 60:
                {
                alt53=4;
                }
                break;
            case 61:
                {
                alt53=5;
                }
                break;
            case 62:
                {
                alt53=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }

            switch (alt53) {
                case 1 :
                    // InternalKactors.g:3022:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKactors.g:3022:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKactors.g:3023:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKactors.g:3023:4: (lv_gt_0_0= '>' )
                    // InternalKactors.g:3024:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,58,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_gt_0_0, grammarAccess.getREL_OPERATORAccess().getGtGreaterThanSignKeyword_0_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "gt", true, ">");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3037:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKactors.g:3037:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKactors.g:3038:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKactors.g:3038:4: (lv_lt_1_0= '<' )
                    // InternalKactors.g:3039:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,59,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_lt_1_0, grammarAccess.getREL_OPERATORAccess().getLtLessThanSignKeyword_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "lt", true, "<");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:3052:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKactors.g:3052:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKactors.g:3053:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKactors.g:3053:4: (lv_eq_2_0= '=' )
                    // InternalKactors.g:3054:5: lv_eq_2_0= '='
                    {
                    lv_eq_2_0=(Token)match(input,38,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_eq_2_0, grammarAccess.getREL_OPERATORAccess().getEqEqualsSignKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "eq", true, "=");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:3067:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKactors.g:3067:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKactors.g:3068:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKactors.g:3068:4: (lv_ne_3_0= '!=' )
                    // InternalKactors.g:3069:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,60,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_ne_3_0, grammarAccess.getREL_OPERATORAccess().getNeExclamationMarkEqualsSignKeyword_3_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "ne", true, "!=");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:3082:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKactors.g:3082:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKactors.g:3083:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKactors.g:3083:4: (lv_le_4_0= '<=' )
                    // InternalKactors.g:3084:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,61,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_le_4_0, grammarAccess.getREL_OPERATORAccess().getLeLessThanSignEqualsSignKeyword_4_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "le", true, "<=");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:3097:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKactors.g:3097:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKactors.g:3098:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKactors.g:3098:4: (lv_ge_5_0= '>=' )
                    // InternalKactors.g:3099:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,62,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_ge_5_0, grammarAccess.getREL_OPERATORAccess().getGeGreaterThanSignEqualsSignKeyword_5_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getREL_OPERATORRule());
                      					}
                      					setWithLastConsumed(current, "ge", true, ">=");
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleREL_OPERATOR"


    // $ANTLR start "entryRuleLiteral"
    // InternalKactors.g:3115:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKactors.g:3115:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKactors.g:3116:2: iv_ruleLiteral= ruleLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleLiteral=ruleLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLiteral"


    // $ANTLR start "ruleLiteral"
    // InternalKactors.g:3122:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) ;
    public final EObject ruleLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_string_4_0=null;
        Token lv_boolean_6_1=null;
        Token lv_boolean_6_2=null;
        EObject lv_number_0_0 = null;

        EObject lv_from_1_0 = null;

        EObject lv_to_3_0 = null;

        EObject lv_date_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3128:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) )
            // InternalKactors.g:3129:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            {
            // InternalKactors.g:3129:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            int alt55=5;
            alt55 = dfa55.predict(input);
            switch (alt55) {
                case 1 :
                    // InternalKactors.g:3130:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3130:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKactors.g:3131:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKactors.g:3131:4: (lv_number_0_0= ruleNumber )
                    // InternalKactors.g:3132:5: lv_number_0_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLiteralAccess().getNumberNumberParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_number_0_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getLiteralRule());
                      					}
                      					set(
                      						current,
                      						"number",
                      						lv_number_0_0,
                      						"org.integratedmodelling.kactors.Kactors.Number");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3150:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:3150:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKactors.g:3151:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3151:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKactors.g:3152:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKactors.g:3152:5: (lv_from_1_0= ruleNumber )
                    // InternalKactors.g:3153:6: lv_from_1_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_33);
                    lv_from_1_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getLiteralRule());
                      						}
                      						set(
                      							current,
                      							"from",
                      							lv_from_1_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_2=(Token)match(input,48,FOLLOW_34); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:3174:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKactors.g:3175:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKactors.g:3175:5: (lv_to_3_0= ruleNumber )
                    // InternalKactors.g:3176:6: lv_to_3_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getToNumberParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_to_3_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getLiteralRule());
                      						}
                      						set(
                      							current,
                      							"to",
                      							lv_to_3_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:3195:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:3195:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKactors.g:3196:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKactors.g:3196:4: (lv_string_4_0= RULE_STRING )
                    // InternalKactors.g:3197:5: lv_string_4_0= RULE_STRING
                    {
                    lv_string_4_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_string_4_0, grammarAccess.getLiteralAccess().getStringSTRINGTerminalRuleCall_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getLiteralRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"string",
                      						lv_string_4_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:3214:3: ( (lv_date_5_0= ruleDate ) )
                    {
                    // InternalKactors.g:3214:3: ( (lv_date_5_0= ruleDate ) )
                    // InternalKactors.g:3215:4: (lv_date_5_0= ruleDate )
                    {
                    // InternalKactors.g:3215:4: (lv_date_5_0= ruleDate )
                    // InternalKactors.g:3216:5: lv_date_5_0= ruleDate
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLiteralAccess().getDateDateParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_date_5_0=ruleDate();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getLiteralRule());
                      					}
                      					set(
                      						current,
                      						"date",
                      						lv_date_5_0,
                      						"org.integratedmodelling.kactors.Kactors.Date");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:3234:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    {
                    // InternalKactors.g:3234:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    // InternalKactors.g:3235:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    {
                    // InternalKactors.g:3235:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    // InternalKactors.g:3236:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    {
                    // InternalKactors.g:3236:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    int alt54=2;
                    int LA54_0 = input.LA(1);

                    if ( (LA54_0==44) ) {
                        alt54=1;
                    }
                    else if ( (LA54_0==45) ) {
                        alt54=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 54, 0, input);

                        throw nvae;
                    }
                    switch (alt54) {
                        case 1 :
                            // InternalKactors.g:3237:6: lv_boolean_6_1= 'true'
                            {
                            lv_boolean_6_1=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_6_1, grammarAccess.getLiteralAccess().getBooleanTrueKeyword_4_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getLiteralRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_6_1, null);
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:3248:6: lv_boolean_6_2= 'false'
                            {
                            lv_boolean_6_2=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_boolean_6_2, grammarAccess.getLiteralAccess().getBooleanFalseKeyword_4_0_1());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getLiteralRule());
                              						}
                              						setWithLastConsumed(current, "boolean", lv_boolean_6_2, null);
                              					
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLiteral"


    // $ANTLR start "entryRuleBody"
    // InternalKactors.g:3265:1: entryRuleBody returns [EObject current=null] : iv_ruleBody= ruleBody EOF ;
    public final EObject entryRuleBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBody = null;


        try {
            // InternalKactors.g:3265:45: (iv_ruleBody= ruleBody EOF )
            // InternalKactors.g:3266:2: iv_ruleBody= ruleBody EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBody=ruleBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBody; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBody"


    // $ANTLR start "ruleBody"
    // InternalKactors.g:3272:1: ruleBody returns [EObject current=null] : ( () ( (lv_lists_1_0= ruleStatementList ) )* ) ;
    public final EObject ruleBody() throws RecognitionException {
        EObject current = null;

        EObject lv_lists_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3278:2: ( ( () ( (lv_lists_1_0= ruleStatementList ) )* ) )
            // InternalKactors.g:3279:2: ( () ( (lv_lists_1_0= ruleStatementList ) )* )
            {
            // InternalKactors.g:3279:2: ( () ( (lv_lists_1_0= ruleStatementList ) )* )
            // InternalKactors.g:3280:3: () ( (lv_lists_1_0= ruleStatementList ) )*
            {
            // InternalKactors.g:3280:3: ()
            // InternalKactors.g:3281:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getBodyAccess().getBodyAction_0(),
              					current);
              			
            }

            }

            // InternalKactors.g:3290:3: ( (lv_lists_1_0= ruleStatementList ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==RULE_LOWERCASE_ID||LA56_0==RULE_EMBEDDEDTEXT||LA56_0==35||LA56_0==64||(LA56_0>=66 && LA56_0<=68)) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // InternalKactors.g:3291:4: (lv_lists_1_0= ruleStatementList )
            	    {
            	    // InternalKactors.g:3291:4: (lv_lists_1_0= ruleStatementList )
            	    // InternalKactors.g:3292:5: lv_lists_1_0= ruleStatementList
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getBodyAccess().getListsStatementListParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_50);
            	    lv_lists_1_0=ruleStatementList();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getBodyRule());
            	      					}
            	      					add(
            	      						current,
            	      						"lists",
            	      						lv_lists_1_0,
            	      						"org.integratedmodelling.kactors.Kactors.StatementList");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBody"


    // $ANTLR start "entryRuleVerb"
    // InternalKactors.g:3313:1: entryRuleVerb returns [EObject current=null] : iv_ruleVerb= ruleVerb EOF ;
    public final EObject entryRuleVerb() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVerb = null;


        try {
            // InternalKactors.g:3313:45: (iv_ruleVerb= ruleVerb EOF )
            // InternalKactors.g:3314:2: iv_ruleVerb= ruleVerb EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVerbRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVerb=ruleVerb();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVerb; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVerb"


    // $ANTLR start "ruleVerb"
    // InternalKactors.g:3320:1: ruleVerb returns [EObject current=null] : ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_Group_4= ruleGroup ) ( (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) ) | otherlv_7= ';' )? ) ;
    public final EObject ruleVerb() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject this_Group_4 = null;

        EObject lv_actions_6_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3326:2: ( ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_Group_4= ruleGroup ) ( (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) ) | otherlv_7= ';' )? ) )
            // InternalKactors.g:3327:2: ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_Group_4= ruleGroup ) ( (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) ) | otherlv_7= ';' )? )
            {
            // InternalKactors.g:3327:2: ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_Group_4= ruleGroup ) ( (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) ) | otherlv_7= ';' )? )
            // InternalKactors.g:3328:3: ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_Group_4= ruleGroup ) ( (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) ) | otherlv_7= ';' )?
            {
            // InternalKactors.g:3328:3: ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_Group_4= ruleGroup )
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==RULE_LOWERCASE_ID) ) {
                alt59=1;
            }
            else if ( (LA59_0==35) ) {
                alt59=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }
            switch (alt59) {
                case 1 :
                    // InternalKactors.g:3329:4: ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
                    {
                    // InternalKactors.g:3329:4: ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
                    // InternalKactors.g:3330:5: ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
                    {
                    // InternalKactors.g:3330:5: ( (lv_name_0_0= rulePathName ) )
                    // InternalKactors.g:3331:6: (lv_name_0_0= rulePathName )
                    {
                    // InternalKactors.g:3331:6: (lv_name_0_0= rulePathName )
                    // InternalKactors.g:3332:7: lv_name_0_0= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getVerbAccess().getNamePathNameParserRuleCall_0_0_0_0());
                      						
                    }
                    pushFollow(FOLLOW_51);
                    lv_name_0_0=rulePathName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getVerbRule());
                      							}
                      							set(
                      								current,
                      								"name",
                      								lv_name_0_0,
                      								"org.integratedmodelling.kactors.Kactors.PathName");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }

                    // InternalKactors.g:3349:5: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
                    int alt58=2;
                    alt58 = dfa58.predict(input);
                    switch (alt58) {
                        case 1 :
                            // InternalKactors.g:3350:6: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                            {
                            otherlv_1=(Token)match(input,35,FOLLOW_21); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_1, grammarAccess.getVerbAccess().getLeftParenthesisKeyword_0_0_1_0());
                              					
                            }
                            // InternalKactors.g:3354:6: ( (lv_parameters_2_0= ruleParameterList ) )?
                            int alt57=2;
                            int LA57_0 = input.LA(1);

                            if ( (LA57_0==RULE_LOWERCASE_ID||(LA57_0>=RULE_STRING && LA57_0<=RULE_EXPR)||LA57_0==RULE_INT||LA57_0==35||LA57_0==39||LA57_0==42||(LA57_0>=44 && LA57_0<=45)||LA57_0==52||(LA57_0>=70 && LA57_0<=71)) ) {
                                alt57=1;
                            }
                            switch (alt57) {
                                case 1 :
                                    // InternalKactors.g:3355:7: (lv_parameters_2_0= ruleParameterList )
                                    {
                                    // InternalKactors.g:3355:7: (lv_parameters_2_0= ruleParameterList )
                                    // InternalKactors.g:3356:8: lv_parameters_2_0= ruleParameterList
                                    {
                                    if ( state.backtracking==0 ) {

                                      								newCompositeNode(grammarAccess.getVerbAccess().getParametersParameterListParserRuleCall_0_0_1_1_0());
                                      							
                                    }
                                    pushFollow(FOLLOW_22);
                                    lv_parameters_2_0=ruleParameterList();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElementForParent(grammarAccess.getVerbRule());
                                      								}
                                      								set(
                                      									current,
                                      									"parameters",
                                      									lv_parameters_2_0,
                                      									"org.integratedmodelling.kactors.Kactors.ParameterList");
                                      								afterParserOrEnumRuleCall();
                                      							
                                    }

                                    }


                                    }
                                    break;

                            }

                            otherlv_3=(Token)match(input,36,FOLLOW_52); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_3, grammarAccess.getVerbAccess().getRightParenthesisKeyword_0_0_1_2());
                              					
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3380:4: this_Group_4= ruleGroup
                    {
                    if ( state.backtracking==0 ) {

                      				/* */
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getVerbAccess().getGroupParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_52);
                    this_Group_4=ruleGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_Group_4;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:3392:3: ( (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) ) | otherlv_7= ';' )?
            int alt60=3;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==34) ) {
                alt60=1;
            }
            else if ( (LA60_0==63) ) {
                alt60=2;
            }
            switch (alt60) {
                case 1 :
                    // InternalKactors.g:3393:4: (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )
                    {
                    // InternalKactors.g:3393:4: (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )
                    // InternalKactors.g:3394:5: otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) )
                    {
                    otherlv_5=(Token)match(input,34,FOLLOW_53); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getVerbAccess().getColonKeyword_1_0_0());
                      				
                    }
                    // InternalKactors.g:3398:5: ( (lv_actions_6_0= ruleActions ) )
                    // InternalKactors.g:3399:6: (lv_actions_6_0= ruleActions )
                    {
                    // InternalKactors.g:3399:6: (lv_actions_6_0= ruleActions )
                    // InternalKactors.g:3400:7: lv_actions_6_0= ruleActions
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getVerbAccess().getActionsActionsParserRuleCall_1_0_1_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    lv_actions_6_0=ruleActions();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getVerbRule());
                      							}
                      							set(
                      								current,
                      								"actions",
                      								lv_actions_6_0,
                      								"org.integratedmodelling.kactors.Kactors.Actions");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3419:4: otherlv_7= ';'
                    {
                    otherlv_7=(Token)match(input,63,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getVerbAccess().getSemicolonKeyword_1_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVerb"


    // $ANTLR start "entryRuleGroup"
    // InternalKactors.g:3428:1: entryRuleGroup returns [EObject current=null] : iv_ruleGroup= ruleGroup EOF ;
    public final EObject entryRuleGroup() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGroup = null;


        try {
            // InternalKactors.g:3428:46: (iv_ruleGroup= ruleGroup EOF )
            // InternalKactors.g:3429:2: iv_ruleGroup= ruleGroup EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getGroupRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleGroup=ruleGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleGroup; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGroup"


    // $ANTLR start "ruleGroup"
    // InternalKactors.g:3435:1: ruleGroup returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_body_2_0= ruleBody ) )? otherlv_3= ')' ) ;
    public final EObject ruleGroup() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3441:2: ( ( () otherlv_1= '(' ( (lv_body_2_0= ruleBody ) )? otherlv_3= ')' ) )
            // InternalKactors.g:3442:2: ( () otherlv_1= '(' ( (lv_body_2_0= ruleBody ) )? otherlv_3= ')' )
            {
            // InternalKactors.g:3442:2: ( () otherlv_1= '(' ( (lv_body_2_0= ruleBody ) )? otherlv_3= ')' )
            // InternalKactors.g:3443:3: () otherlv_1= '(' ( (lv_body_2_0= ruleBody ) )? otherlv_3= ')'
            {
            // InternalKactors.g:3443:3: ()
            // InternalKactors.g:3444:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getGroupAccess().getGroupAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,35,FOLLOW_54); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getGroupAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:3457:3: ( (lv_body_2_0= ruleBody ) )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==RULE_LOWERCASE_ID||LA61_0==RULE_EMBEDDEDTEXT||LA61_0==35||LA61_0==64||(LA61_0>=66 && LA61_0<=68)) ) {
                alt61=1;
            }
            else if ( (LA61_0==36) ) {
                int LA61_2 = input.LA(2);

                if ( (synpred117_InternalKactors()) ) {
                    alt61=1;
                }
            }
            switch (alt61) {
                case 1 :
                    // InternalKactors.g:3458:4: (lv_body_2_0= ruleBody )
                    {
                    // InternalKactors.g:3458:4: (lv_body_2_0= ruleBody )
                    // InternalKactors.g:3459:5: lv_body_2_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getGroupAccess().getBodyBodyParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_22);
                    lv_body_2_0=ruleBody();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getGroupRule());
                      					}
                      					set(
                      						current,
                      						"body",
                      						lv_body_2_0,
                      						"org.integratedmodelling.kactors.Kactors.Body");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getGroupAccess().getRightParenthesisKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGroup"


    // $ANTLR start "entryRuleStatementList"
    // InternalKactors.g:3484:1: entryRuleStatementList returns [EObject current=null] : iv_ruleStatementList= ruleStatementList EOF ;
    public final EObject entryRuleStatementList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementList = null;


        try {
            // InternalKactors.g:3484:54: (iv_ruleStatementList= ruleStatementList EOF )
            // InternalKactors.g:3485:2: iv_ruleStatementList= ruleStatementList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStatementListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStatementList=ruleStatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStatementList; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStatementList"


    // $ANTLR start "ruleStatementList"
    // InternalKactors.g:3491:1: ruleStatementList returns [EObject current=null] : ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* ) ;
    public final EObject ruleStatementList() throws RecognitionException {
        EObject current = null;

        EObject lv_first_0_0 = null;

        EObject lv_next_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3497:2: ( ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* ) )
            // InternalKactors.g:3498:2: ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* )
            {
            // InternalKactors.g:3498:2: ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* )
            // InternalKactors.g:3499:3: ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )*
            {
            // InternalKactors.g:3499:3: ( (lv_first_0_0= ruleStatement ) )
            // InternalKactors.g:3500:4: (lv_first_0_0= ruleStatement )
            {
            // InternalKactors.g:3500:4: (lv_first_0_0= ruleStatement )
            // InternalKactors.g:3501:5: lv_first_0_0= ruleStatement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getStatementListAccess().getFirstStatementParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_17);
            lv_first_0_0=ruleStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getStatementListRule());
              					}
              					set(
              						current,
              						"first",
              						lv_first_0_0,
              						"org.integratedmodelling.kactors.Kactors.Statement");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:3518:3: ( (lv_next_1_0= ruleNextStatement ) )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==24) ) {
                    int LA62_2 = input.LA(2);

                    if ( (synpred118_InternalKactors()) ) {
                        alt62=1;
                    }


                }


                switch (alt62) {
            	case 1 :
            	    // InternalKactors.g:3519:4: (lv_next_1_0= ruleNextStatement )
            	    {
            	    // InternalKactors.g:3519:4: (lv_next_1_0= ruleNextStatement )
            	    // InternalKactors.g:3520:5: lv_next_1_0= ruleNextStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getStatementListAccess().getNextNextStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_17);
            	    lv_next_1_0=ruleNextStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getStatementListRule());
            	      					}
            	      					add(
            	      						current,
            	      						"next",
            	      						lv_next_1_0,
            	      						"org.integratedmodelling.kactors.Kactors.NextStatement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop62;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStatementList"


    // $ANTLR start "entryRuleStatement"
    // InternalKactors.g:3541:1: entryRuleStatement returns [EObject current=null] : iv_ruleStatement= ruleStatement EOF ;
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatement = null;


        try {
            // InternalKactors.g:3541:50: (iv_ruleStatement= ruleStatement EOF )
            // InternalKactors.g:3542:2: iv_ruleStatement= ruleStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStatement=ruleStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStatement"


    // $ANTLR start "ruleStatement"
    // InternalKactors.g:3548:1: ruleStatement returns [EObject current=null] : ( ( (lv_verb_0_0= ruleVerb ) ) | ( (lv_group_1_0= ruleGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) ) ;
    public final EObject ruleStatement() throws RecognitionException {
        EObject current = null;

        Token lv_text_2_0=null;
        EObject lv_verb_0_0 = null;

        EObject lv_group_1_0 = null;

        EObject lv_if_3_0 = null;

        EObject lv_while_4_0 = null;

        EObject lv_do_5_0 = null;

        EObject lv_for_6_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3554:2: ( ( ( (lv_verb_0_0= ruleVerb ) ) | ( (lv_group_1_0= ruleGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) ) )
            // InternalKactors.g:3555:2: ( ( (lv_verb_0_0= ruleVerb ) ) | ( (lv_group_1_0= ruleGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) )
            {
            // InternalKactors.g:3555:2: ( ( (lv_verb_0_0= ruleVerb ) ) | ( (lv_group_1_0= ruleGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) )
            int alt63=7;
            switch ( input.LA(1) ) {
            case RULE_LOWERCASE_ID:
                {
                alt63=1;
                }
                break;
            case 35:
                {
                int LA63_2 = input.LA(2);

                if ( (synpred119_InternalKactors()) ) {
                    alt63=1;
                }
                else if ( (synpred120_InternalKactors()) ) {
                    alt63=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 63, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_EMBEDDEDTEXT:
                {
                alt63=3;
                }
                break;
            case 64:
                {
                alt63=4;
                }
                break;
            case 66:
                {
                alt63=5;
                }
                break;
            case 67:
                {
                alt63=6;
                }
                break;
            case 68:
                {
                alt63=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }

            switch (alt63) {
                case 1 :
                    // InternalKactors.g:3556:3: ( (lv_verb_0_0= ruleVerb ) )
                    {
                    // InternalKactors.g:3556:3: ( (lv_verb_0_0= ruleVerb ) )
                    // InternalKactors.g:3557:4: (lv_verb_0_0= ruleVerb )
                    {
                    // InternalKactors.g:3557:4: (lv_verb_0_0= ruleVerb )
                    // InternalKactors.g:3558:5: lv_verb_0_0= ruleVerb
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getVerbVerbParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_verb_0_0=ruleVerb();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"verb",
                      						lv_verb_0_0,
                      						"org.integratedmodelling.kactors.Kactors.Verb");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3576:3: ( (lv_group_1_0= ruleGroup ) )
                    {
                    // InternalKactors.g:3576:3: ( (lv_group_1_0= ruleGroup ) )
                    // InternalKactors.g:3577:4: (lv_group_1_0= ruleGroup )
                    {
                    // InternalKactors.g:3577:4: (lv_group_1_0= ruleGroup )
                    // InternalKactors.g:3578:5: lv_group_1_0= ruleGroup
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getGroupGroupParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_group_1_0=ruleGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"group",
                      						lv_group_1_0,
                      						"org.integratedmodelling.kactors.Kactors.Group");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:3596:3: ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:3596:3: ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:3597:4: (lv_text_2_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:3597:4: (lv_text_2_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:3598:5: lv_text_2_0= RULE_EMBEDDEDTEXT
                    {
                    lv_text_2_0=(Token)match(input,RULE_EMBEDDEDTEXT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_text_2_0, grammarAccess.getStatementAccess().getTextEMBEDDEDTEXTTerminalRuleCall_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getStatementRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"text",
                      						lv_text_2_0,
                      						"org.integratedmodelling.kactors.Kactors.EMBEDDEDTEXT");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:3615:3: ( (lv_if_3_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:3615:3: ( (lv_if_3_0= ruleIfStatement ) )
                    // InternalKactors.g:3616:4: (lv_if_3_0= ruleIfStatement )
                    {
                    // InternalKactors.g:3616:4: (lv_if_3_0= ruleIfStatement )
                    // InternalKactors.g:3617:5: lv_if_3_0= ruleIfStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getIfIfStatementParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_if_3_0=ruleIfStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"if",
                      						lv_if_3_0,
                      						"org.integratedmodelling.kactors.Kactors.IfStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:3635:3: ( (lv_while_4_0= ruleWhileStatement ) )
                    {
                    // InternalKactors.g:3635:3: ( (lv_while_4_0= ruleWhileStatement ) )
                    // InternalKactors.g:3636:4: (lv_while_4_0= ruleWhileStatement )
                    {
                    // InternalKactors.g:3636:4: (lv_while_4_0= ruleWhileStatement )
                    // InternalKactors.g:3637:5: lv_while_4_0= ruleWhileStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getWhileWhileStatementParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_while_4_0=ruleWhileStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"while",
                      						lv_while_4_0,
                      						"org.integratedmodelling.kactors.Kactors.WhileStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:3655:3: ( (lv_do_5_0= ruleDoStatement ) )
                    {
                    // InternalKactors.g:3655:3: ( (lv_do_5_0= ruleDoStatement ) )
                    // InternalKactors.g:3656:4: (lv_do_5_0= ruleDoStatement )
                    {
                    // InternalKactors.g:3656:4: (lv_do_5_0= ruleDoStatement )
                    // InternalKactors.g:3657:5: lv_do_5_0= ruleDoStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getDoDoStatementParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_do_5_0=ruleDoStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"do",
                      						lv_do_5_0,
                      						"org.integratedmodelling.kactors.Kactors.DoStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:3675:3: ( (lv_for_6_0= ruleForStatement ) )
                    {
                    // InternalKactors.g:3675:3: ( (lv_for_6_0= ruleForStatement ) )
                    // InternalKactors.g:3676:4: (lv_for_6_0= ruleForStatement )
                    {
                    // InternalKactors.g:3676:4: (lv_for_6_0= ruleForStatement )
                    // InternalKactors.g:3677:5: lv_for_6_0= ruleForStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getForForStatementParserRuleCall_6_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_for_6_0=ruleForStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"for",
                      						lv_for_6_0,
                      						"org.integratedmodelling.kactors.Kactors.ForStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStatement"


    // $ANTLR start "entryRuleNextStatement"
    // InternalKactors.g:3698:1: entryRuleNextStatement returns [EObject current=null] : iv_ruleNextStatement= ruleNextStatement EOF ;
    public final EObject entryRuleNextStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNextStatement = null;


        try {
            // InternalKactors.g:3698:54: (iv_ruleNextStatement= ruleNextStatement EOF )
            // InternalKactors.g:3699:2: iv_ruleNextStatement= ruleNextStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNextStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleNextStatement=ruleNextStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNextStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNextStatement"


    // $ANTLR start "ruleNextStatement"
    // InternalKactors.g:3705:1: ruleNextStatement returns [EObject current=null] : (otherlv_0= ',' ( ( (lv_verb_1_0= ruleVerb ) ) | ( (lv_group_2_0= ruleGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) ) ) ;
    public final EObject ruleNextStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_text_3_0=null;
        EObject lv_verb_1_0 = null;

        EObject lv_group_2_0 = null;

        EObject lv_if_4_0 = null;

        EObject lv_while_5_0 = null;

        EObject lv_do_6_0 = null;

        EObject lv_for_7_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3711:2: ( (otherlv_0= ',' ( ( (lv_verb_1_0= ruleVerb ) ) | ( (lv_group_2_0= ruleGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) ) ) )
            // InternalKactors.g:3712:2: (otherlv_0= ',' ( ( (lv_verb_1_0= ruleVerb ) ) | ( (lv_group_2_0= ruleGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) ) )
            {
            // InternalKactors.g:3712:2: (otherlv_0= ',' ( ( (lv_verb_1_0= ruleVerb ) ) | ( (lv_group_2_0= ruleGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) ) )
            // InternalKactors.g:3713:3: otherlv_0= ',' ( ( (lv_verb_1_0= ruleVerb ) ) | ( (lv_group_2_0= ruleGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) )
            {
            otherlv_0=(Token)match(input,24,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getNextStatementAccess().getCommaKeyword_0());
              		
            }
            // InternalKactors.g:3717:3: ( ( (lv_verb_1_0= ruleVerb ) ) | ( (lv_group_2_0= ruleGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) )
            int alt64=7;
            switch ( input.LA(1) ) {
            case RULE_LOWERCASE_ID:
                {
                alt64=1;
                }
                break;
            case 35:
                {
                int LA64_2 = input.LA(2);

                if ( (synpred125_InternalKactors()) ) {
                    alt64=1;
                }
                else if ( (synpred126_InternalKactors()) ) {
                    alt64=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 64, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_EMBEDDEDTEXT:
                {
                alt64=3;
                }
                break;
            case 64:
                {
                alt64=4;
                }
                break;
            case 66:
                {
                alt64=5;
                }
                break;
            case 67:
                {
                alt64=6;
                }
                break;
            case 68:
                {
                alt64=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }

            switch (alt64) {
                case 1 :
                    // InternalKactors.g:3718:4: ( (lv_verb_1_0= ruleVerb ) )
                    {
                    // InternalKactors.g:3718:4: ( (lv_verb_1_0= ruleVerb ) )
                    // InternalKactors.g:3719:5: (lv_verb_1_0= ruleVerb )
                    {
                    // InternalKactors.g:3719:5: (lv_verb_1_0= ruleVerb )
                    // InternalKactors.g:3720:6: lv_verb_1_0= ruleVerb
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getVerbVerbParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_verb_1_0=ruleVerb();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"verb",
                      							lv_verb_1_0,
                      							"org.integratedmodelling.kactors.Kactors.Verb");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3738:4: ( (lv_group_2_0= ruleGroup ) )
                    {
                    // InternalKactors.g:3738:4: ( (lv_group_2_0= ruleGroup ) )
                    // InternalKactors.g:3739:5: (lv_group_2_0= ruleGroup )
                    {
                    // InternalKactors.g:3739:5: (lv_group_2_0= ruleGroup )
                    // InternalKactors.g:3740:6: lv_group_2_0= ruleGroup
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getGroupGroupParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_group_2_0=ruleGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"group",
                      							lv_group_2_0,
                      							"org.integratedmodelling.kactors.Kactors.Group");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:3758:4: ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:3758:4: ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:3759:5: (lv_text_3_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:3759:5: (lv_text_3_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:3760:6: lv_text_3_0= RULE_EMBEDDEDTEXT
                    {
                    lv_text_3_0=(Token)match(input,RULE_EMBEDDEDTEXT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_text_3_0, grammarAccess.getNextStatementAccess().getTextEMBEDDEDTEXTTerminalRuleCall_1_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getNextStatementRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"text",
                      							lv_text_3_0,
                      							"org.integratedmodelling.kactors.Kactors.EMBEDDEDTEXT");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:3777:4: ( (lv_if_4_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:3777:4: ( (lv_if_4_0= ruleIfStatement ) )
                    // InternalKactors.g:3778:5: (lv_if_4_0= ruleIfStatement )
                    {
                    // InternalKactors.g:3778:5: (lv_if_4_0= ruleIfStatement )
                    // InternalKactors.g:3779:6: lv_if_4_0= ruleIfStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getIfIfStatementParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_if_4_0=ruleIfStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"if",
                      							lv_if_4_0,
                      							"org.integratedmodelling.kactors.Kactors.IfStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:3797:4: ( (lv_while_5_0= ruleWhileStatement ) )
                    {
                    // InternalKactors.g:3797:4: ( (lv_while_5_0= ruleWhileStatement ) )
                    // InternalKactors.g:3798:5: (lv_while_5_0= ruleWhileStatement )
                    {
                    // InternalKactors.g:3798:5: (lv_while_5_0= ruleWhileStatement )
                    // InternalKactors.g:3799:6: lv_while_5_0= ruleWhileStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getWhileWhileStatementParserRuleCall_1_4_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_while_5_0=ruleWhileStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"while",
                      							lv_while_5_0,
                      							"org.integratedmodelling.kactors.Kactors.WhileStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:3817:4: ( (lv_do_6_0= ruleDoStatement ) )
                    {
                    // InternalKactors.g:3817:4: ( (lv_do_6_0= ruleDoStatement ) )
                    // InternalKactors.g:3818:5: (lv_do_6_0= ruleDoStatement )
                    {
                    // InternalKactors.g:3818:5: (lv_do_6_0= ruleDoStatement )
                    // InternalKactors.g:3819:6: lv_do_6_0= ruleDoStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getDoDoStatementParserRuleCall_1_5_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_do_6_0=ruleDoStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"do",
                      							lv_do_6_0,
                      							"org.integratedmodelling.kactors.Kactors.DoStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:3837:4: ( (lv_for_7_0= ruleForStatement ) )
                    {
                    // InternalKactors.g:3837:4: ( (lv_for_7_0= ruleForStatement ) )
                    // InternalKactors.g:3838:5: (lv_for_7_0= ruleForStatement )
                    {
                    // InternalKactors.g:3838:5: (lv_for_7_0= ruleForStatement )
                    // InternalKactors.g:3839:6: lv_for_7_0= ruleForStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getForForStatementParserRuleCall_1_6_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_for_7_0=ruleForStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"for",
                      							lv_for_7_0,
                      							"org.integratedmodelling.kactors.Kactors.ForStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNextStatement"


    // $ANTLR start "entryRuleIfStatement"
    // InternalKactors.g:3861:1: entryRuleIfStatement returns [EObject current=null] : iv_ruleIfStatement= ruleIfStatement EOF ;
    public final EObject entryRuleIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfStatement = null;


        try {
            // InternalKactors.g:3861:52: (iv_ruleIfStatement= ruleIfStatement EOF )
            // InternalKactors.g:3862:2: iv_ruleIfStatement= ruleIfStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIfStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleIfStatement=ruleIfStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIfStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIfStatement"


    // $ANTLR start "ruleIfStatement"
    // InternalKactors.g:3868:1: ruleIfStatement returns [EObject current=null] : (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? ) ;
    public final EObject ruleIfStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_expression_1_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_elseIfExpression_5_0=null;
        Token otherlv_7=null;
        EObject lv_body_2_0 = null;

        EObject lv_elseIfCall_6_0 = null;

        EObject lv_elseCall_8_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3874:2: ( (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? ) )
            // InternalKactors.g:3875:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? )
            {
            // InternalKactors.g:3875:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? )
            // InternalKactors.g:3876:3: otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )?
            {
            otherlv_0=(Token)match(input,64,FOLLOW_55); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getIfStatementAccess().getIfKeyword_0());
              		
            }
            // InternalKactors.g:3880:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:3881:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:3881:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:3882:5: lv_expression_1_0= RULE_EXPR
            {
            lv_expression_1_0=(Token)match(input,RULE_EXPR,FOLLOW_56); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_expression_1_0, grammarAccess.getIfStatementAccess().getExpressionEXPRTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getIfStatementRule());
              					}
              					setWithLastConsumed(
              						current,
              						"expression",
              						lv_expression_1_0,
              						"org.integratedmodelling.kactors.Kactors.EXPR");
              				
            }

            }


            }

            // InternalKactors.g:3898:3: ( (lv_body_2_0= ruleIfBody ) )
            // InternalKactors.g:3899:4: (lv_body_2_0= ruleIfBody )
            {
            // InternalKactors.g:3899:4: (lv_body_2_0= ruleIfBody )
            // InternalKactors.g:3900:5: lv_body_2_0= ruleIfBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getIfStatementAccess().getBodyIfBodyParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_57);
            lv_body_2_0=ruleIfBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getIfStatementRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_2_0,
              						"org.integratedmodelling.kactors.Kactors.IfBody");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:3917:3: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==65) ) {
                    int LA65_1 = input.LA(2);

                    if ( (synpred131_InternalKactors()) ) {
                        alt65=1;
                    }


                }


                switch (alt65) {
            	case 1 :
            	    // InternalKactors.g:3918:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) )
            	    {
            	    otherlv_3=(Token)match(input,65,FOLLOW_58); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getIfStatementAccess().getElseKeyword_3_0());
            	      			
            	    }
            	    otherlv_4=(Token)match(input,64,FOLLOW_55); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getIfStatementAccess().getIfKeyword_3_1());
            	      			
            	    }
            	    // InternalKactors.g:3926:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
            	    // InternalKactors.g:3927:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    {
            	    // InternalKactors.g:3927:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    // InternalKactors.g:3928:6: lv_elseIfExpression_5_0= RULE_EXPR
            	    {
            	    lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_56); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						newLeafNode(lv_elseIfExpression_5_0, grammarAccess.getIfStatementAccess().getElseIfExpressionEXPRTerminalRuleCall_3_2_0());
            	      					
            	    }
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElement(grammarAccess.getIfStatementRule());
            	      						}
            	      						addWithLastConsumed(
            	      							current,
            	      							"elseIfExpression",
            	      							lv_elseIfExpression_5_0,
            	      							"org.integratedmodelling.kactors.Kactors.EXPR");
            	      					
            	    }

            	    }


            	    }

            	    // InternalKactors.g:3944:4: ( (lv_elseIfCall_6_0= ruleIfBody ) )
            	    // InternalKactors.g:3945:5: (lv_elseIfCall_6_0= ruleIfBody )
            	    {
            	    // InternalKactors.g:3945:5: (lv_elseIfCall_6_0= ruleIfBody )
            	    // InternalKactors.g:3946:6: lv_elseIfCall_6_0= ruleIfBody
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getIfStatementAccess().getElseIfCallIfBodyParserRuleCall_3_3_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_57);
            	    lv_elseIfCall_6_0=ruleIfBody();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getIfStatementRule());
            	      						}
            	      						add(
            	      							current,
            	      							"elseIfCall",
            	      							lv_elseIfCall_6_0,
            	      							"org.integratedmodelling.kactors.Kactors.IfBody");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop65;
                }
            } while (true);

            // InternalKactors.g:3964:3: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==65) ) {
                int LA66_1 = input.LA(2);

                if ( (synpred132_InternalKactors()) ) {
                    alt66=1;
                }
            }
            switch (alt66) {
                case 1 :
                    // InternalKactors.g:3965:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) )
                    {
                    otherlv_7=(Token)match(input,65,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getIfStatementAccess().getElseKeyword_4_0());
                      			
                    }
                    // InternalKactors.g:3969:4: ( (lv_elseCall_8_0= ruleIfBody ) )
                    // InternalKactors.g:3970:5: (lv_elseCall_8_0= ruleIfBody )
                    {
                    // InternalKactors.g:3970:5: (lv_elseCall_8_0= ruleIfBody )
                    // InternalKactors.g:3971:6: lv_elseCall_8_0= ruleIfBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getIfStatementAccess().getElseCallIfBodyParserRuleCall_4_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_elseCall_8_0=ruleIfBody();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getIfStatementRule());
                      						}
                      						set(
                      							current,
                      							"elseCall",
                      							lv_elseCall_8_0,
                      							"org.integratedmodelling.kactors.Kactors.IfBody");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIfStatement"


    // $ANTLR start "entryRuleIfBody"
    // InternalKactors.g:3993:1: entryRuleIfBody returns [EObject current=null] : iv_ruleIfBody= ruleIfBody EOF ;
    public final EObject entryRuleIfBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfBody = null;


        try {
            // InternalKactors.g:3993:47: (iv_ruleIfBody= ruleIfBody EOF )
            // InternalKactors.g:3994:2: iv_ruleIfBody= ruleIfBody EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIfBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleIfBody=ruleIfBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIfBody; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIfBody"


    // $ANTLR start "ruleIfBody"
    // InternalKactors.g:4000:1: ruleIfBody returns [EObject current=null] : ( ( (lv_verb_0_0= ruleVerb ) ) | ( (lv_group_1_0= ruleGroup ) ) ) ;
    public final EObject ruleIfBody() throws RecognitionException {
        EObject current = null;

        EObject lv_verb_0_0 = null;

        EObject lv_group_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4006:2: ( ( ( (lv_verb_0_0= ruleVerb ) ) | ( (lv_group_1_0= ruleGroup ) ) ) )
            // InternalKactors.g:4007:2: ( ( (lv_verb_0_0= ruleVerb ) ) | ( (lv_group_1_0= ruleGroup ) ) )
            {
            // InternalKactors.g:4007:2: ( ( (lv_verb_0_0= ruleVerb ) ) | ( (lv_group_1_0= ruleGroup ) ) )
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==RULE_LOWERCASE_ID) ) {
                alt67=1;
            }
            else if ( (LA67_0==35) ) {
                int LA67_2 = input.LA(2);

                if ( (synpred133_InternalKactors()) ) {
                    alt67=1;
                }
                else if ( (true) ) {
                    alt67=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 67, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }
            switch (alt67) {
                case 1 :
                    // InternalKactors.g:4008:3: ( (lv_verb_0_0= ruleVerb ) )
                    {
                    // InternalKactors.g:4008:3: ( (lv_verb_0_0= ruleVerb ) )
                    // InternalKactors.g:4009:4: (lv_verb_0_0= ruleVerb )
                    {
                    // InternalKactors.g:4009:4: (lv_verb_0_0= ruleVerb )
                    // InternalKactors.g:4010:5: lv_verb_0_0= ruleVerb
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getIfBodyAccess().getVerbVerbParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_verb_0_0=ruleVerb();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getIfBodyRule());
                      					}
                      					set(
                      						current,
                      						"verb",
                      						lv_verb_0_0,
                      						"org.integratedmodelling.kactors.Kactors.Verb");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:4028:3: ( (lv_group_1_0= ruleGroup ) )
                    {
                    // InternalKactors.g:4028:3: ( (lv_group_1_0= ruleGroup ) )
                    // InternalKactors.g:4029:4: (lv_group_1_0= ruleGroup )
                    {
                    // InternalKactors.g:4029:4: (lv_group_1_0= ruleGroup )
                    // InternalKactors.g:4030:5: lv_group_1_0= ruleGroup
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getIfBodyAccess().getGroupGroupParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_group_1_0=ruleGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getIfBodyRule());
                      					}
                      					set(
                      						current,
                      						"group",
                      						lv_group_1_0,
                      						"org.integratedmodelling.kactors.Kactors.Group");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIfBody"


    // $ANTLR start "entryRuleWhileStatement"
    // InternalKactors.g:4051:1: entryRuleWhileStatement returns [EObject current=null] : iv_ruleWhileStatement= ruleWhileStatement EOF ;
    public final EObject entryRuleWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhileStatement = null;


        try {
            // InternalKactors.g:4051:55: (iv_ruleWhileStatement= ruleWhileStatement EOF )
            // InternalKactors.g:4052:2: iv_ruleWhileStatement= ruleWhileStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWhileStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleWhileStatement=ruleWhileStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWhileStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWhileStatement"


    // $ANTLR start "ruleWhileStatement"
    // InternalKactors.g:4058:1: ruleWhileStatement returns [EObject current=null] : (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) ) ;
    public final EObject ruleWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_expression_1_0=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4064:2: ( (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) ) )
            // InternalKactors.g:4065:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) )
            {
            // InternalKactors.g:4065:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) )
            // InternalKactors.g:4066:3: otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) )
            {
            otherlv_0=(Token)match(input,66,FOLLOW_55); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getWhileStatementAccess().getWhileKeyword_0());
              		
            }
            // InternalKactors.g:4070:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:4071:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:4071:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:4072:5: lv_expression_1_0= RULE_EXPR
            {
            lv_expression_1_0=(Token)match(input,RULE_EXPR,FOLLOW_56); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_expression_1_0, grammarAccess.getWhileStatementAccess().getExpressionEXPRTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getWhileStatementRule());
              					}
              					setWithLastConsumed(
              						current,
              						"expression",
              						lv_expression_1_0,
              						"org.integratedmodelling.kactors.Kactors.EXPR");
              				
            }

            }


            }

            // InternalKactors.g:4088:3: ( (lv_body_2_0= ruleIfBody ) )
            // InternalKactors.g:4089:4: (lv_body_2_0= ruleIfBody )
            {
            // InternalKactors.g:4089:4: (lv_body_2_0= ruleIfBody )
            // InternalKactors.g:4090:5: lv_body_2_0= ruleIfBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getWhileStatementAccess().getBodyIfBodyParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_body_2_0=ruleIfBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getWhileStatementRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_2_0,
              						"org.integratedmodelling.kactors.Kactors.IfBody");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWhileStatement"


    // $ANTLR start "entryRuleDoStatement"
    // InternalKactors.g:4111:1: entryRuleDoStatement returns [EObject current=null] : iv_ruleDoStatement= ruleDoStatement EOF ;
    public final EObject entryRuleDoStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDoStatement = null;


        try {
            // InternalKactors.g:4111:52: (iv_ruleDoStatement= ruleDoStatement EOF )
            // InternalKactors.g:4112:2: iv_ruleDoStatement= ruleDoStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDoStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDoStatement=ruleDoStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDoStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDoStatement"


    // $ANTLR start "ruleDoStatement"
    // InternalKactors.g:4118:1: ruleDoStatement returns [EObject current=null] : (otherlv_0= 'do' ( (lv_body_1_0= ruleIfBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) ;
    public final EObject ruleDoStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_expression_3_0=null;
        EObject lv_body_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4124:2: ( (otherlv_0= 'do' ( (lv_body_1_0= ruleIfBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) )
            // InternalKactors.g:4125:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleIfBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            {
            // InternalKactors.g:4125:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleIfBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            // InternalKactors.g:4126:3: otherlv_0= 'do' ( (lv_body_1_0= ruleIfBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) )
            {
            otherlv_0=(Token)match(input,67,FOLLOW_56); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getDoStatementAccess().getDoKeyword_0());
              		
            }
            // InternalKactors.g:4130:3: ( (lv_body_1_0= ruleIfBody ) )
            // InternalKactors.g:4131:4: (lv_body_1_0= ruleIfBody )
            {
            // InternalKactors.g:4131:4: (lv_body_1_0= ruleIfBody )
            // InternalKactors.g:4132:5: lv_body_1_0= ruleIfBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDoStatementAccess().getBodyIfBodyParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_59);
            lv_body_1_0=ruleIfBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDoStatementRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_1_0,
              						"org.integratedmodelling.kactors.Kactors.IfBody");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,66,FOLLOW_55); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getDoStatementAccess().getWhileKeyword_2());
              		
            }
            // InternalKactors.g:4153:3: ( (lv_expression_3_0= RULE_EXPR ) )
            // InternalKactors.g:4154:4: (lv_expression_3_0= RULE_EXPR )
            {
            // InternalKactors.g:4154:4: (lv_expression_3_0= RULE_EXPR )
            // InternalKactors.g:4155:5: lv_expression_3_0= RULE_EXPR
            {
            lv_expression_3_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_expression_3_0, grammarAccess.getDoStatementAccess().getExpressionEXPRTerminalRuleCall_3_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getDoStatementRule());
              					}
              					setWithLastConsumed(
              						current,
              						"expression",
              						lv_expression_3_0,
              						"org.integratedmodelling.kactors.Kactors.EXPR");
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDoStatement"


    // $ANTLR start "entryRuleForStatement"
    // InternalKactors.g:4175:1: entryRuleForStatement returns [EObject current=null] : iv_ruleForStatement= ruleForStatement EOF ;
    public final EObject entryRuleForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleForStatement = null;


        try {
            // InternalKactors.g:4175:53: (iv_ruleForStatement= ruleForStatement EOF )
            // InternalKactors.g:4176:2: iv_ruleForStatement= ruleForStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getForStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleForStatement=ruleForStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleForStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleForStatement"


    // $ANTLR start "ruleForStatement"
    // InternalKactors.g:4182:1: ruleForStatement returns [EObject current=null] : (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleIfBody ) ) ) ;
    public final EObject ruleForStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_id_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;

        EObject lv_body_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4188:2: ( (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleIfBody ) ) ) )
            // InternalKactors.g:4189:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleIfBody ) ) )
            {
            // InternalKactors.g:4189:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleIfBody ) ) )
            // InternalKactors.g:4190:3: otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleIfBody ) )
            {
            otherlv_0=(Token)match(input,68,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getForStatementAccess().getForKeyword_0());
              		
            }
            // InternalKactors.g:4194:3: ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==RULE_LOWERCASE_ID) ) {
                int LA68_1 = input.LA(2);

                if ( (LA68_1==49) ) {
                    alt68=1;
                }
            }
            switch (alt68) {
                case 1 :
                    // InternalKactors.g:4195:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in'
                    {
                    // InternalKactors.g:4195:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:4196:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:4196:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:4197:6: lv_id_1_0= RULE_LOWERCASE_ID
                    {
                    lv_id_1_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_id_1_0, grammarAccess.getForStatementAccess().getIdLOWERCASE_IDTerminalRuleCall_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getForStatementRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"id",
                      							lv_id_1_0,
                      							"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      					
                    }

                    }


                    }

                    otherlv_2=(Token)match(input,49,FOLLOW_18); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getForStatementAccess().getInKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:4218:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:4219:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:4219:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:4220:5: lv_value_3_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getForStatementAccess().getValueValueParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_56);
            lv_value_3_0=ruleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getForStatementRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_3_0,
              						"org.integratedmodelling.kactors.Kactors.Value");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:4237:3: ( (lv_body_4_0= ruleIfBody ) )
            // InternalKactors.g:4238:4: (lv_body_4_0= ruleIfBody )
            {
            // InternalKactors.g:4238:4: (lv_body_4_0= ruleIfBody )
            // InternalKactors.g:4239:5: lv_body_4_0= ruleIfBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getForStatementAccess().getBodyIfBodyParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_body_4_0=ruleIfBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getForStatementRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_4_0,
              						"org.integratedmodelling.kactors.Kactors.IfBody");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleForStatement"


    // $ANTLR start "entryRuleActions"
    // InternalKactors.g:4260:1: entryRuleActions returns [EObject current=null] : iv_ruleActions= ruleActions EOF ;
    public final EObject entryRuleActions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActions = null;


        try {
            // InternalKactors.g:4260:48: (iv_ruleActions= ruleActions EOF )
            // InternalKactors.g:4261:2: iv_ruleActions= ruleActions EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActionsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleActions=ruleActions();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActions; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleActions"


    // $ANTLR start "ruleActions"
    // InternalKactors.g:4267:1: ruleActions returns [EObject current=null] : ( ( (lv_statement_0_0= ruleStatement ) ) | (otherlv_1= '(' ( (lv_statements_2_0= ruleStatementList ) ) otherlv_3= ')' ) | ( (lv_match_4_0= ruleMatch ) ) | (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' ) ) ;
    public final EObject ruleActions() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        EObject lv_statement_0_0 = null;

        EObject lv_statements_2_0 = null;

        EObject lv_match_4_0 = null;

        EObject lv_matches_6_0 = null;

        EObject lv_matches_7_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4273:2: ( ( ( (lv_statement_0_0= ruleStatement ) ) | (otherlv_1= '(' ( (lv_statements_2_0= ruleStatementList ) ) otherlv_3= ')' ) | ( (lv_match_4_0= ruleMatch ) ) | (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' ) ) )
            // InternalKactors.g:4274:2: ( ( (lv_statement_0_0= ruleStatement ) ) | (otherlv_1= '(' ( (lv_statements_2_0= ruleStatementList ) ) otherlv_3= ')' ) | ( (lv_match_4_0= ruleMatch ) ) | (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' ) )
            {
            // InternalKactors.g:4274:2: ( ( (lv_statement_0_0= ruleStatement ) ) | (otherlv_1= '(' ( (lv_statements_2_0= ruleStatementList ) ) otherlv_3= ')' ) | ( (lv_match_4_0= ruleMatch ) ) | (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' ) )
            int alt70=4;
            alt70 = dfa70.predict(input);
            switch (alt70) {
                case 1 :
                    // InternalKactors.g:4275:3: ( (lv_statement_0_0= ruleStatement ) )
                    {
                    // InternalKactors.g:4275:3: ( (lv_statement_0_0= ruleStatement ) )
                    // InternalKactors.g:4276:4: (lv_statement_0_0= ruleStatement )
                    {
                    // InternalKactors.g:4276:4: (lv_statement_0_0= ruleStatement )
                    // InternalKactors.g:4277:5: lv_statement_0_0= ruleStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getStatementStatementParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_statement_0_0=ruleStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
                      					}
                      					set(
                      						current,
                      						"statement",
                      						lv_statement_0_0,
                      						"org.integratedmodelling.kactors.Kactors.Statement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:4295:3: (otherlv_1= '(' ( (lv_statements_2_0= ruleStatementList ) ) otherlv_3= ')' )
                    {
                    // InternalKactors.g:4295:3: (otherlv_1= '(' ( (lv_statements_2_0= ruleStatementList ) ) otherlv_3= ')' )
                    // InternalKactors.g:4296:4: otherlv_1= '(' ( (lv_statements_2_0= ruleStatementList ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,35,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:4300:4: ( (lv_statements_2_0= ruleStatementList ) )
                    // InternalKactors.g:4301:5: (lv_statements_2_0= ruleStatementList )
                    {
                    // InternalKactors.g:4301:5: (lv_statements_2_0= ruleStatementList )
                    // InternalKactors.g:4302:6: lv_statements_2_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionsAccess().getStatementsStatementListParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_22);
                    lv_statements_2_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionsRule());
                      						}
                      						set(
                      							current,
                      							"statements",
                      							lv_statements_2_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_3=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getActionsAccess().getRightParenthesisKeyword_1_2());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:4325:3: ( (lv_match_4_0= ruleMatch ) )
                    {
                    // InternalKactors.g:4325:3: ( (lv_match_4_0= ruleMatch ) )
                    // InternalKactors.g:4326:4: (lv_match_4_0= ruleMatch )
                    {
                    // InternalKactors.g:4326:4: (lv_match_4_0= ruleMatch )
                    // InternalKactors.g:4327:5: lv_match_4_0= ruleMatch
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_match_4_0=ruleMatch();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
                      					}
                      					set(
                      						current,
                      						"match",
                      						lv_match_4_0,
                      						"org.integratedmodelling.kactors.Kactors.Match");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:4345:3: (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' )
                    {
                    // InternalKactors.g:4345:3: (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' )
                    // InternalKactors.g:4346:4: otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')'
                    {
                    otherlv_5=(Token)match(input,35,FOLLOW_61); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:4350:4: ( (lv_matches_6_0= ruleMatch ) )
                    // InternalKactors.g:4351:5: (lv_matches_6_0= ruleMatch )
                    {
                    // InternalKactors.g:4351:5: (lv_matches_6_0= ruleMatch )
                    // InternalKactors.g:4352:6: lv_matches_6_0= ruleMatch
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_62);
                    lv_matches_6_0=ruleMatch();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionsRule());
                      						}
                      						add(
                      							current,
                      							"matches",
                      							lv_matches_6_0,
                      							"org.integratedmodelling.kactors.Kactors.Match");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:4369:4: ( (lv_matches_7_0= ruleMatch ) )*
                    loop69:
                    do {
                        int alt69=2;
                        int LA69_0 = input.LA(1);

                        if ( (LA69_0==RULE_LOWERCASE_ID||LA69_0==RULE_STRING||(LA69_0>=RULE_OBSERVABLE && LA69_0<=RULE_EXPR)||LA69_0==RULE_INT||LA69_0==RULE_CAMELCASE_ID||LA69_0==RULE_REGEXP||LA69_0==35||LA69_0==40||(LA69_0>=44 && LA69_0<=45)||(LA69_0>=49 && LA69_0<=51)||(LA69_0>=70 && LA69_0<=71)) ) {
                            alt69=1;
                        }


                        switch (alt69) {
                    	case 1 :
                    	    // InternalKactors.g:4370:5: (lv_matches_7_0= ruleMatch )
                    	    {
                    	    // InternalKactors.g:4370:5: (lv_matches_7_0= ruleMatch )
                    	    // InternalKactors.g:4371:6: lv_matches_7_0= ruleMatch
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_3_2_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_62);
                    	    lv_matches_7_0=ruleMatch();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getActionsRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"matches",
                    	      							lv_matches_7_0,
                    	      							"org.integratedmodelling.kactors.Kactors.Match");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop69;
                        }
                    } while (true);

                    otherlv_8=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getActionsAccess().getRightParenthesisKeyword_3_3());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActions"


    // $ANTLR start "entryRuleMatch"
    // InternalKactors.g:4397:1: entryRuleMatch returns [EObject current=null] : iv_ruleMatch= ruleMatch EOF ;
    public final EObject entryRuleMatch() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMatch = null;


        try {
            // InternalKactors.g:4397:46: (iv_ruleMatch= ruleMatch EOF )
            // InternalKactors.g:4398:2: iv_ruleMatch= ruleMatch EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMatchRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMatch=ruleMatch();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMatch; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMatch"


    // $ANTLR start "ruleMatch"
    // InternalKactors.g:4404:1: ruleMatch returns [EObject current=null] : ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( (lv_type_3_0= RULE_CAMELCASE_ID ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_regexp_6_0= RULE_REGEXP ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_observable_9_0= RULE_OBSERVABLE ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_literal_12_0= ruleLiteral ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_text_15_0= RULE_STRING ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_arguments_18_0= ruleArgumentDeclaration ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_int0_21_0= ruleNumber ) ) ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )? ( ( 'to' )=>otherlv_24= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) ) ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )? otherlv_28= '->' ( (lv_body_29_0= ruleStatementList ) ) ) | (otherlv_30= 'in' ( (lv_set_31_0= ruleList ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_quantity_34_0= ruleQuantity ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_date_37_0= ruleDate ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_expr_40_0= RULE_EXPR ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_nodata_43_0= 'unknown' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_star_46_0= '*' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_anything_49_0= '#' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) ) ;
    public final EObject ruleMatch() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_type_3_0=null;
        Token otherlv_4=null;
        Token lv_regexp_6_0=null;
        Token otherlv_7=null;
        Token lv_observable_9_0=null;
        Token otherlv_10=null;
        Token otherlv_13=null;
        Token lv_text_15_0=null;
        Token otherlv_16=null;
        Token otherlv_19=null;
        Token lv_leftLimit_22_0=null;
        Token otherlv_23=null;
        Token otherlv_24=null;
        Token lv_rightLimit_26_0=null;
        Token otherlv_27=null;
        Token otherlv_28=null;
        Token otherlv_30=null;
        Token otherlv_32=null;
        Token otherlv_35=null;
        Token otherlv_38=null;
        Token lv_expr_40_0=null;
        Token otherlv_41=null;
        Token lv_nodata_43_0=null;
        Token otherlv_44=null;
        Token lv_star_46_0=null;
        Token otherlv_47=null;
        Token lv_anything_49_0=null;
        Token otherlv_50=null;
        EObject lv_body_2_0 = null;

        EObject lv_body_5_0 = null;

        EObject lv_body_8_0 = null;

        EObject lv_body_11_0 = null;

        EObject lv_literal_12_0 = null;

        EObject lv_body_14_0 = null;

        EObject lv_body_17_0 = null;

        EObject lv_arguments_18_0 = null;

        EObject lv_body_20_0 = null;

        EObject lv_int0_21_0 = null;

        EObject lv_int1_25_0 = null;

        EObject lv_body_29_0 = null;

        EObject lv_set_31_0 = null;

        EObject lv_body_33_0 = null;

        EObject lv_quantity_34_0 = null;

        EObject lv_body_36_0 = null;

        EObject lv_date_37_0 = null;

        EObject lv_body_39_0 = null;

        EObject lv_body_42_0 = null;

        EObject lv_body_45_0 = null;

        EObject lv_body_48_0 = null;

        EObject lv_body_51_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4410:2: ( ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( (lv_type_3_0= RULE_CAMELCASE_ID ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_regexp_6_0= RULE_REGEXP ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_observable_9_0= RULE_OBSERVABLE ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_literal_12_0= ruleLiteral ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_text_15_0= RULE_STRING ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_arguments_18_0= ruleArgumentDeclaration ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_int0_21_0= ruleNumber ) ) ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )? ( ( 'to' )=>otherlv_24= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) ) ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )? otherlv_28= '->' ( (lv_body_29_0= ruleStatementList ) ) ) | (otherlv_30= 'in' ( (lv_set_31_0= ruleList ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_quantity_34_0= ruleQuantity ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_date_37_0= ruleDate ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_expr_40_0= RULE_EXPR ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_nodata_43_0= 'unknown' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_star_46_0= '*' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_anything_49_0= '#' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) ) )
            // InternalKactors.g:4411:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( (lv_type_3_0= RULE_CAMELCASE_ID ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_regexp_6_0= RULE_REGEXP ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_observable_9_0= RULE_OBSERVABLE ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_literal_12_0= ruleLiteral ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_text_15_0= RULE_STRING ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_arguments_18_0= ruleArgumentDeclaration ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_int0_21_0= ruleNumber ) ) ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )? ( ( 'to' )=>otherlv_24= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) ) ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )? otherlv_28= '->' ( (lv_body_29_0= ruleStatementList ) ) ) | (otherlv_30= 'in' ( (lv_set_31_0= ruleList ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_quantity_34_0= ruleQuantity ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_date_37_0= ruleDate ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_expr_40_0= RULE_EXPR ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_nodata_43_0= 'unknown' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_star_46_0= '*' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_anything_49_0= '#' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) )
            {
            // InternalKactors.g:4411:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( (lv_type_3_0= RULE_CAMELCASE_ID ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_regexp_6_0= RULE_REGEXP ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_observable_9_0= RULE_OBSERVABLE ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_literal_12_0= ruleLiteral ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_text_15_0= RULE_STRING ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_arguments_18_0= ruleArgumentDeclaration ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_int0_21_0= ruleNumber ) ) ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )? ( ( 'to' )=>otherlv_24= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) ) ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )? otherlv_28= '->' ( (lv_body_29_0= ruleStatementList ) ) ) | (otherlv_30= 'in' ( (lv_set_31_0= ruleList ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_quantity_34_0= ruleQuantity ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_date_37_0= ruleDate ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_expr_40_0= RULE_EXPR ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_nodata_43_0= 'unknown' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_star_46_0= '*' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_anything_49_0= '#' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) )
            int alt73=15;
            alt73 = dfa73.predict(input);
            switch (alt73) {
                case 1 :
                    // InternalKactors.g:4412:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:4412:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) )
                    // InternalKactors.g:4413:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:4413:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:4414:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:4414:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:4415:6: lv_id_0_0= RULE_LOWERCASE_ID
                    {
                    lv_id_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_id_0_0, grammarAccess.getMatchAccess().getIdLOWERCASE_IDTerminalRuleCall_0_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"id",
                      							true,
                      							"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_0_1());
                      			
                    }
                    // InternalKactors.g:4435:4: ( (lv_body_2_0= ruleStatementList ) )
                    // InternalKactors.g:4436:5: (lv_body_2_0= ruleStatementList )
                    {
                    // InternalKactors.g:4436:5: (lv_body_2_0= ruleStatementList )
                    // InternalKactors.g:4437:6: lv_body_2_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_0_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_2_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_2_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:4456:3: ( ( (lv_type_3_0= RULE_CAMELCASE_ID ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:4456:3: ( ( (lv_type_3_0= RULE_CAMELCASE_ID ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
                    // InternalKactors.g:4457:4: ( (lv_type_3_0= RULE_CAMELCASE_ID ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:4457:4: ( (lv_type_3_0= RULE_CAMELCASE_ID ) )
                    // InternalKactors.g:4458:5: (lv_type_3_0= RULE_CAMELCASE_ID )
                    {
                    // InternalKactors.g:4458:5: (lv_type_3_0= RULE_CAMELCASE_ID )
                    // InternalKactors.g:4459:6: lv_type_3_0= RULE_CAMELCASE_ID
                    {
                    lv_type_3_0=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_type_3_0, grammarAccess.getMatchAccess().getTypeCAMELCASE_IDTerminalRuleCall_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"type",
                      							true,
                      							"org.integratedmodelling.kactors.Kactors.CAMELCASE_ID");
                      					
                    }

                    }


                    }

                    otherlv_4=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:4479:4: ( (lv_body_5_0= ruleStatementList ) )
                    // InternalKactors.g:4480:5: (lv_body_5_0= ruleStatementList )
                    {
                    // InternalKactors.g:4480:5: (lv_body_5_0= ruleStatementList )
                    // InternalKactors.g:4481:6: lv_body_5_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_5_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_5_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:4500:3: ( ( (lv_regexp_6_0= RULE_REGEXP ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:4500:3: ( ( (lv_regexp_6_0= RULE_REGEXP ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) )
                    // InternalKactors.g:4501:4: ( (lv_regexp_6_0= RULE_REGEXP ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:4501:4: ( (lv_regexp_6_0= RULE_REGEXP ) )
                    // InternalKactors.g:4502:5: (lv_regexp_6_0= RULE_REGEXP )
                    {
                    // InternalKactors.g:4502:5: (lv_regexp_6_0= RULE_REGEXP )
                    // InternalKactors.g:4503:6: lv_regexp_6_0= RULE_REGEXP
                    {
                    lv_regexp_6_0=(Token)match(input,RULE_REGEXP,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_regexp_6_0, grammarAccess.getMatchAccess().getRegexpREGEXPTerminalRuleCall_2_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"regexp",
                      							true,
                      							"org.integratedmodelling.kactors.Kactors.REGEXP");
                      					
                    }

                    }


                    }

                    otherlv_7=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_2_1());
                      			
                    }
                    // InternalKactors.g:4523:4: ( (lv_body_8_0= ruleStatementList ) )
                    // InternalKactors.g:4524:5: (lv_body_8_0= ruleStatementList )
                    {
                    // InternalKactors.g:4524:5: (lv_body_8_0= ruleStatementList )
                    // InternalKactors.g:4525:6: lv_body_8_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_2_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_8_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_8_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:4544:3: ( ( (lv_observable_9_0= RULE_OBSERVABLE ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:4544:3: ( ( (lv_observable_9_0= RULE_OBSERVABLE ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) )
                    // InternalKactors.g:4545:4: ( (lv_observable_9_0= RULE_OBSERVABLE ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:4545:4: ( (lv_observable_9_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:4546:5: (lv_observable_9_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:4546:5: (lv_observable_9_0= RULE_OBSERVABLE )
                    // InternalKactors.g:4547:6: lv_observable_9_0= RULE_OBSERVABLE
                    {
                    lv_observable_9_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_observable_9_0, grammarAccess.getMatchAccess().getObservableOBSERVABLETerminalRuleCall_3_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"observable",
                      							true,
                      							"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
                      					
                    }

                    }


                    }

                    otherlv_10=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_3_1());
                      			
                    }
                    // InternalKactors.g:4567:4: ( (lv_body_11_0= ruleStatementList ) )
                    // InternalKactors.g:4568:5: (lv_body_11_0= ruleStatementList )
                    {
                    // InternalKactors.g:4568:5: (lv_body_11_0= ruleStatementList )
                    // InternalKactors.g:4569:6: lv_body_11_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_3_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_11_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_11_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:4588:3: ( ( (lv_literal_12_0= ruleLiteral ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:4588:3: ( ( (lv_literal_12_0= ruleLiteral ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
                    // InternalKactors.g:4589:4: ( (lv_literal_12_0= ruleLiteral ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:4589:4: ( (lv_literal_12_0= ruleLiteral ) )
                    // InternalKactors.g:4590:5: (lv_literal_12_0= ruleLiteral )
                    {
                    // InternalKactors.g:4590:5: (lv_literal_12_0= ruleLiteral )
                    // InternalKactors.g:4591:6: lv_literal_12_0= ruleLiteral
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_4_0_0());
                      					
                    }
                    pushFollow(FOLLOW_63);
                    lv_literal_12_0=ruleLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"literal",
                      							true,
                      							"org.integratedmodelling.kactors.Kactors.Literal");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_13=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_4_1());
                      			
                    }
                    // InternalKactors.g:4612:4: ( (lv_body_14_0= ruleStatementList ) )
                    // InternalKactors.g:4613:5: (lv_body_14_0= ruleStatementList )
                    {
                    // InternalKactors.g:4613:5: (lv_body_14_0= ruleStatementList )
                    // InternalKactors.g:4614:6: lv_body_14_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_4_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_14_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_14_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:4633:3: ( ( (lv_text_15_0= RULE_STRING ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:4633:3: ( ( (lv_text_15_0= RULE_STRING ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
                    // InternalKactors.g:4634:4: ( (lv_text_15_0= RULE_STRING ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:4634:4: ( (lv_text_15_0= RULE_STRING ) )
                    // InternalKactors.g:4635:5: (lv_text_15_0= RULE_STRING )
                    {
                    // InternalKactors.g:4635:5: (lv_text_15_0= RULE_STRING )
                    // InternalKactors.g:4636:6: lv_text_15_0= RULE_STRING
                    {
                    lv_text_15_0=(Token)match(input,RULE_STRING,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_text_15_0, grammarAccess.getMatchAccess().getTextSTRINGTerminalRuleCall_5_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"text",
                      							true,
                      							"org.eclipse.xtext.common.Terminals.STRING");
                      					
                    }

                    }


                    }

                    otherlv_16=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_5_1());
                      			
                    }
                    // InternalKactors.g:4656:4: ( (lv_body_17_0= ruleStatementList ) )
                    // InternalKactors.g:4657:5: (lv_body_17_0= ruleStatementList )
                    {
                    // InternalKactors.g:4657:5: (lv_body_17_0= ruleStatementList )
                    // InternalKactors.g:4658:6: lv_body_17_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_5_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_17_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_17_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:4677:3: ( ( (lv_arguments_18_0= ruleArgumentDeclaration ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:4677:3: ( ( (lv_arguments_18_0= ruleArgumentDeclaration ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
                    // InternalKactors.g:4678:4: ( (lv_arguments_18_0= ruleArgumentDeclaration ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:4678:4: ( (lv_arguments_18_0= ruleArgumentDeclaration ) )
                    // InternalKactors.g:4679:5: (lv_arguments_18_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:4679:5: (lv_arguments_18_0= ruleArgumentDeclaration )
                    // InternalKactors.g:4680:6: lv_arguments_18_0= ruleArgumentDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getArgumentsArgumentDeclarationParserRuleCall_6_0_0());
                      					
                    }
                    pushFollow(FOLLOW_63);
                    lv_arguments_18_0=ruleArgumentDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"arguments",
                      							lv_arguments_18_0,
                      							"org.integratedmodelling.kactors.Kactors.ArgumentDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_19=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_19, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_6_1());
                      			
                    }
                    // InternalKactors.g:4701:4: ( (lv_body_20_0= ruleStatementList ) )
                    // InternalKactors.g:4702:5: (lv_body_20_0= ruleStatementList )
                    {
                    // InternalKactors.g:4702:5: (lv_body_20_0= ruleStatementList )
                    // InternalKactors.g:4703:6: lv_body_20_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_6_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_20_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_20_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:4722:3: ( ( (lv_int0_21_0= ruleNumber ) ) ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )? ( ( 'to' )=>otherlv_24= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) ) ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )? otherlv_28= '->' ( (lv_body_29_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:4722:3: ( ( (lv_int0_21_0= ruleNumber ) ) ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )? ( ( 'to' )=>otherlv_24= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) ) ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )? otherlv_28= '->' ( (lv_body_29_0= ruleStatementList ) ) )
                    // InternalKactors.g:4723:4: ( (lv_int0_21_0= ruleNumber ) ) ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )? ( ( 'to' )=>otherlv_24= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) ) ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )? otherlv_28= '->' ( (lv_body_29_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:4723:4: ( (lv_int0_21_0= ruleNumber ) )
                    // InternalKactors.g:4724:5: (lv_int0_21_0= ruleNumber )
                    {
                    // InternalKactors.g:4724:5: (lv_int0_21_0= ruleNumber )
                    // InternalKactors.g:4725:6: lv_int0_21_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getInt0NumberParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_int0_21_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"int0",
                      							lv_int0_21_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:4742:4: ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )?
                    int alt71=3;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==46) ) {
                        alt71=1;
                    }
                    else if ( (LA71_0==47) ) {
                        alt71=2;
                    }
                    switch (alt71) {
                        case 1 :
                            // InternalKactors.g:4743:5: ( (lv_leftLimit_22_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4743:5: ( (lv_leftLimit_22_0= 'inclusive' ) )
                            // InternalKactors.g:4744:6: (lv_leftLimit_22_0= 'inclusive' )
                            {
                            // InternalKactors.g:4744:6: (lv_leftLimit_22_0= 'inclusive' )
                            // InternalKactors.g:4745:7: lv_leftLimit_22_0= 'inclusive'
                            {
                            lv_leftLimit_22_0=(Token)match(input,46,FOLLOW_33); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_leftLimit_22_0, grammarAccess.getMatchAccess().getLeftLimitInclusiveKeyword_7_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getMatchRule());
                              							}
                              							setWithLastConsumed(current, "leftLimit", lv_leftLimit_22_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:4758:5: otherlv_23= 'exclusive'
                            {
                            otherlv_23=(Token)match(input,47,FOLLOW_33); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_23, grammarAccess.getMatchAccess().getExclusiveKeyword_7_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:4763:4: ( ( 'to' )=>otherlv_24= 'to' )
                    // InternalKactors.g:4764:5: ( 'to' )=>otherlv_24= 'to'
                    {
                    otherlv_24=(Token)match(input,48,FOLLOW_34); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_24, grammarAccess.getMatchAccess().getToKeyword_7_2());
                      				
                    }

                    }

                    // InternalKactors.g:4770:4: ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) )
                    // InternalKactors.g:4771:5: ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber )
                    {
                    // InternalKactors.g:4775:5: (lv_int1_25_0= ruleNumber )
                    // InternalKactors.g:4776:6: lv_int1_25_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getInt1NumberParserRuleCall_7_3_0());
                      					
                    }
                    pushFollow(FOLLOW_64);
                    lv_int1_25_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"int1",
                      							lv_int1_25_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:4793:4: ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )?
                    int alt72=3;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==46) ) {
                        alt72=1;
                    }
                    else if ( (LA72_0==47) ) {
                        alt72=2;
                    }
                    switch (alt72) {
                        case 1 :
                            // InternalKactors.g:4794:5: ( (lv_rightLimit_26_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4794:5: ( (lv_rightLimit_26_0= 'inclusive' ) )
                            // InternalKactors.g:4795:6: (lv_rightLimit_26_0= 'inclusive' )
                            {
                            // InternalKactors.g:4795:6: (lv_rightLimit_26_0= 'inclusive' )
                            // InternalKactors.g:4796:7: lv_rightLimit_26_0= 'inclusive'
                            {
                            lv_rightLimit_26_0=(Token)match(input,46,FOLLOW_63); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_rightLimit_26_0, grammarAccess.getMatchAccess().getRightLimitInclusiveKeyword_7_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getMatchRule());
                              							}
                              							setWithLastConsumed(current, "rightLimit", lv_rightLimit_26_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:4809:5: otherlv_27= 'exclusive'
                            {
                            otherlv_27=(Token)match(input,47,FOLLOW_63); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_27, grammarAccess.getMatchAccess().getExclusiveKeyword_7_4_1());
                              				
                            }

                            }
                            break;

                    }

                    otherlv_28=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_28, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_7_5());
                      			
                    }
                    // InternalKactors.g:4818:4: ( (lv_body_29_0= ruleStatementList ) )
                    // InternalKactors.g:4819:5: (lv_body_29_0= ruleStatementList )
                    {
                    // InternalKactors.g:4819:5: (lv_body_29_0= ruleStatementList )
                    // InternalKactors.g:4820:6: lv_body_29_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_7_6_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_29_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_29_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:4839:3: (otherlv_30= 'in' ( (lv_set_31_0= ruleList ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:4839:3: (otherlv_30= 'in' ( (lv_set_31_0= ruleList ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) )
                    // InternalKactors.g:4840:4: otherlv_30= 'in' ( (lv_set_31_0= ruleList ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) )
                    {
                    otherlv_30=(Token)match(input,49,FOLLOW_36); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_30, grammarAccess.getMatchAccess().getInKeyword_8_0());
                      			
                    }
                    // InternalKactors.g:4844:4: ( (lv_set_31_0= ruleList ) )
                    // InternalKactors.g:4845:5: (lv_set_31_0= ruleList )
                    {
                    // InternalKactors.g:4845:5: (lv_set_31_0= ruleList )
                    // InternalKactors.g:4846:6: lv_set_31_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getSetListParserRuleCall_8_1_0());
                      					
                    }
                    pushFollow(FOLLOW_63);
                    lv_set_31_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"set",
                      							lv_set_31_0,
                      							"org.integratedmodelling.kactors.Kactors.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_32=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_32, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_8_2());
                      			
                    }
                    // InternalKactors.g:4867:4: ( (lv_body_33_0= ruleStatementList ) )
                    // InternalKactors.g:4868:5: (lv_body_33_0= ruleStatementList )
                    {
                    // InternalKactors.g:4868:5: (lv_body_33_0= ruleStatementList )
                    // InternalKactors.g:4869:6: lv_body_33_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_8_3_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_33_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_33_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKactors.g:4888:3: ( ( (lv_quantity_34_0= ruleQuantity ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:4888:3: ( ( (lv_quantity_34_0= ruleQuantity ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
                    // InternalKactors.g:4889:4: ( (lv_quantity_34_0= ruleQuantity ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:4889:4: ( (lv_quantity_34_0= ruleQuantity ) )
                    // InternalKactors.g:4890:5: (lv_quantity_34_0= ruleQuantity )
                    {
                    // InternalKactors.g:4890:5: (lv_quantity_34_0= ruleQuantity )
                    // InternalKactors.g:4891:6: lv_quantity_34_0= ruleQuantity
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getQuantityQuantityParserRuleCall_9_0_0());
                      					
                    }
                    pushFollow(FOLLOW_63);
                    lv_quantity_34_0=ruleQuantity();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"quantity",
                      							lv_quantity_34_0,
                      							"org.integratedmodelling.kactors.Kactors.Quantity");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_35=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_35, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_9_1());
                      			
                    }
                    // InternalKactors.g:4912:4: ( (lv_body_36_0= ruleStatementList ) )
                    // InternalKactors.g:4913:5: (lv_body_36_0= ruleStatementList )
                    {
                    // InternalKactors.g:4913:5: (lv_body_36_0= ruleStatementList )
                    // InternalKactors.g:4914:6: lv_body_36_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_9_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_36_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_36_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 11 :
                    // InternalKactors.g:4933:3: ( ( (lv_date_37_0= ruleDate ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:4933:3: ( ( (lv_date_37_0= ruleDate ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
                    // InternalKactors.g:4934:4: ( (lv_date_37_0= ruleDate ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:4934:4: ( (lv_date_37_0= ruleDate ) )
                    // InternalKactors.g:4935:5: (lv_date_37_0= ruleDate )
                    {
                    // InternalKactors.g:4935:5: (lv_date_37_0= ruleDate )
                    // InternalKactors.g:4936:6: lv_date_37_0= ruleDate
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getDateDateParserRuleCall_10_0_0());
                      					
                    }
                    pushFollow(FOLLOW_63);
                    lv_date_37_0=ruleDate();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"date",
                      							lv_date_37_0,
                      							"org.integratedmodelling.kactors.Kactors.Date");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_38=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_38, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_10_1());
                      			
                    }
                    // InternalKactors.g:4957:4: ( (lv_body_39_0= ruleStatementList ) )
                    // InternalKactors.g:4958:5: (lv_body_39_0= ruleStatementList )
                    {
                    // InternalKactors.g:4958:5: (lv_body_39_0= ruleStatementList )
                    // InternalKactors.g:4959:6: lv_body_39_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_10_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_39_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_39_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 12 :
                    // InternalKactors.g:4978:3: ( ( (lv_expr_40_0= RULE_EXPR ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:4978:3: ( ( (lv_expr_40_0= RULE_EXPR ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
                    // InternalKactors.g:4979:4: ( (lv_expr_40_0= RULE_EXPR ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:4979:4: ( (lv_expr_40_0= RULE_EXPR ) )
                    // InternalKactors.g:4980:5: (lv_expr_40_0= RULE_EXPR )
                    {
                    // InternalKactors.g:4980:5: (lv_expr_40_0= RULE_EXPR )
                    // InternalKactors.g:4981:6: lv_expr_40_0= RULE_EXPR
                    {
                    lv_expr_40_0=(Token)match(input,RULE_EXPR,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_expr_40_0, grammarAccess.getMatchAccess().getExprEXPRTerminalRuleCall_11_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"expr",
                      							lv_expr_40_0,
                      							"org.integratedmodelling.kactors.Kactors.EXPR");
                      					
                    }

                    }


                    }

                    otherlv_41=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_41, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_11_1());
                      			
                    }
                    // InternalKactors.g:5001:4: ( (lv_body_42_0= ruleStatementList ) )
                    // InternalKactors.g:5002:5: (lv_body_42_0= ruleStatementList )
                    {
                    // InternalKactors.g:5002:5: (lv_body_42_0= ruleStatementList )
                    // InternalKactors.g:5003:6: lv_body_42_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_11_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_42_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_42_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 13 :
                    // InternalKactors.g:5022:3: ( ( (lv_nodata_43_0= 'unknown' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:5022:3: ( ( (lv_nodata_43_0= 'unknown' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) )
                    // InternalKactors.g:5023:4: ( (lv_nodata_43_0= 'unknown' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:5023:4: ( (lv_nodata_43_0= 'unknown' ) )
                    // InternalKactors.g:5024:5: (lv_nodata_43_0= 'unknown' )
                    {
                    // InternalKactors.g:5024:5: (lv_nodata_43_0= 'unknown' )
                    // InternalKactors.g:5025:6: lv_nodata_43_0= 'unknown'
                    {
                    lv_nodata_43_0=(Token)match(input,50,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_nodata_43_0, grammarAccess.getMatchAccess().getNodataUnknownKeyword_12_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "nodata", lv_nodata_43_0, "unknown");
                      					
                    }

                    }


                    }

                    otherlv_44=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_44, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_12_1());
                      			
                    }
                    // InternalKactors.g:5041:4: ( (lv_body_45_0= ruleStatementList ) )
                    // InternalKactors.g:5042:5: (lv_body_45_0= ruleStatementList )
                    {
                    // InternalKactors.g:5042:5: (lv_body_45_0= ruleStatementList )
                    // InternalKactors.g:5043:6: lv_body_45_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_12_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_45_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_45_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 14 :
                    // InternalKactors.g:5062:3: ( ( (lv_star_46_0= '*' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:5062:3: ( ( (lv_star_46_0= '*' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) )
                    // InternalKactors.g:5063:4: ( (lv_star_46_0= '*' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:5063:4: ( (lv_star_46_0= '*' ) )
                    // InternalKactors.g:5064:5: (lv_star_46_0= '*' )
                    {
                    // InternalKactors.g:5064:5: (lv_star_46_0= '*' )
                    // InternalKactors.g:5065:6: lv_star_46_0= '*'
                    {
                    lv_star_46_0=(Token)match(input,51,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_star_46_0, grammarAccess.getMatchAccess().getStarAsteriskKeyword_13_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "star", true, "*");
                      					
                    }

                    }


                    }

                    otherlv_47=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_47, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_13_1());
                      			
                    }
                    // InternalKactors.g:5081:4: ( (lv_body_48_0= ruleStatementList ) )
                    // InternalKactors.g:5082:5: (lv_body_48_0= ruleStatementList )
                    {
                    // InternalKactors.g:5082:5: (lv_body_48_0= ruleStatementList )
                    // InternalKactors.g:5083:6: lv_body_48_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_13_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_48_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_48_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 15 :
                    // InternalKactors.g:5102:3: ( ( (lv_anything_49_0= '#' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:5102:3: ( ( (lv_anything_49_0= '#' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) )
                    // InternalKactors.g:5103:4: ( (lv_anything_49_0= '#' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:5103:4: ( (lv_anything_49_0= '#' ) )
                    // InternalKactors.g:5104:5: (lv_anything_49_0= '#' )
                    {
                    // InternalKactors.g:5104:5: (lv_anything_49_0= '#' )
                    // InternalKactors.g:5105:6: lv_anything_49_0= '#'
                    {
                    lv_anything_49_0=(Token)match(input,40,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_anything_49_0, grammarAccess.getMatchAccess().getAnythingNumberSignKeyword_14_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "anything", true, "#");
                      					
                    }

                    }


                    }

                    otherlv_50=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_50, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_14_1());
                      			
                    }
                    // InternalKactors.g:5121:4: ( (lv_body_51_0= ruleStatementList ) )
                    // InternalKactors.g:5122:5: (lv_body_51_0= ruleStatementList )
                    {
                    // InternalKactors.g:5122:5: (lv_body_51_0= ruleStatementList )
                    // InternalKactors.g:5123:6: lv_body_51_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_14_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_51_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_51_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMatch"


    // $ANTLR start "entryRuleNumber"
    // InternalKactors.g:5145:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKactors.g:5145:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKactors.g:5146:2: iv_ruleNumber= ruleNumber EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNumberRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleNumber=ruleNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNumber; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNumber"


    // $ANTLR start "ruleNumber"
    // InternalKactors.g:5152:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) ;
    public final EObject ruleNumber() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_negative_1_0=null;
        Token lv_real_2_0=null;
        Token lv_long_3_0=null;
        Token lv_decimal_4_0=null;
        Token lv_decimalPart_5_0=null;
        Token lv_exponential_6_1=null;
        Token lv_exponential_6_2=null;
        Token otherlv_7=null;
        Token lv_expNegative_8_0=null;
        Token lv_exp_9_0=null;


        	enterRule();

        try {
            // InternalKactors.g:5158:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) )
            // InternalKactors.g:5159:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            {
            // InternalKactors.g:5159:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            // InternalKactors.g:5160:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            {
            // InternalKactors.g:5160:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt74=3;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==70) ) {
                alt74=1;
            }
            else if ( (LA74_0==71) ) {
                alt74=2;
            }
            switch (alt74) {
                case 1 :
                    // InternalKactors.g:5161:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,70,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5166:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKactors.g:5166:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKactors.g:5167:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKactors.g:5167:5: (lv_negative_1_0= '-' )
                    // InternalKactors.g:5168:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,71,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_negative_1_0, grammarAccess.getNumberAccess().getNegativeHyphenMinusKeyword_0_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getNumberRule());
                      						}
                      						setWithLastConsumed(current, "negative", true, "-");
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalKactors.g:5181:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKactors.g:5182:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKactors.g:5186:4: (lv_real_2_0= RULE_INT )
            // InternalKactors.g:5187:5: lv_real_2_0= RULE_INT
            {
            lv_real_2_0=(Token)match(input,RULE_INT,FOLLOW_65); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_real_2_0, grammarAccess.getNumberAccess().getRealINTTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getNumberRule());
              					}
              					setWithLastConsumed(
              						current,
              						"real",
              						lv_real_2_0,
              						"org.eclipse.xtext.common.Terminals.INT");
              				
            }

            }


            }

            // InternalKactors.g:5203:3: ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==72) && (synpred162_InternalKactors())) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // InternalKactors.g:5204:4: ( ( 'l' ) )=> (lv_long_3_0= 'l' )
                    {
                    // InternalKactors.g:5208:4: (lv_long_3_0= 'l' )
                    // InternalKactors.g:5209:5: lv_long_3_0= 'l'
                    {
                    lv_long_3_0=(Token)match(input,72,FOLLOW_66); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_long_3_0, grammarAccess.getNumberAccess().getLongLKeyword_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getNumberRule());
                      					}
                      					setWithLastConsumed(current, "long", true, "l");
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalKactors.g:5221:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==56) ) {
                int LA76_1 = input.LA(2);

                if ( (LA76_1==RULE_INT) && (synpred163_InternalKactors())) {
                    alt76=1;
                }
            }
            switch (alt76) {
                case 1 :
                    // InternalKactors.g:5222:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5235:4: ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    // InternalKactors.g:5236:5: ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5236:5: ( (lv_decimal_4_0= '.' ) )
                    // InternalKactors.g:5237:6: (lv_decimal_4_0= '.' )
                    {
                    // InternalKactors.g:5237:6: (lv_decimal_4_0= '.' )
                    // InternalKactors.g:5238:7: lv_decimal_4_0= '.'
                    {
                    lv_decimal_4_0=(Token)match(input,56,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_decimal_4_0, grammarAccess.getNumberAccess().getDecimalFullStopKeyword_3_0_0_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getNumberRule());
                      							}
                      							setWithLastConsumed(current, "decimal", true, ".");
                      						
                    }

                    }


                    }

                    // InternalKactors.g:5250:5: ( (lv_decimalPart_5_0= RULE_INT ) )
                    // InternalKactors.g:5251:6: (lv_decimalPart_5_0= RULE_INT )
                    {
                    // InternalKactors.g:5251:6: (lv_decimalPart_5_0= RULE_INT )
                    // InternalKactors.g:5252:7: lv_decimalPart_5_0= RULE_INT
                    {
                    lv_decimalPart_5_0=(Token)match(input,RULE_INT,FOLLOW_67); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_decimalPart_5_0, grammarAccess.getNumberAccess().getDecimalPartINTTerminalRuleCall_3_0_1_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getNumberRule());
                      							}
                      							setWithLastConsumed(
                      								current,
                      								"decimalPart",
                      								lv_decimalPart_5_0,
                      								"org.eclipse.xtext.common.Terminals.INT");
                      						
                    }

                    }


                    }


                    }


                    }
                    break;

            }

            // InternalKactors.g:5270:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==73) && (synpred167_InternalKactors())) {
                alt79=1;
            }
            else if ( (LA79_0==74) && (synpred167_InternalKactors())) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // InternalKactors.g:5271:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5297:4: ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    // InternalKactors.g:5298:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5298:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) )
                    // InternalKactors.g:5299:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    {
                    // InternalKactors.g:5299:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    // InternalKactors.g:5300:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    {
                    // InternalKactors.g:5300:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    int alt77=2;
                    int LA77_0 = input.LA(1);

                    if ( (LA77_0==73) ) {
                        alt77=1;
                    }
                    else if ( (LA77_0==74) ) {
                        alt77=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 77, 0, input);

                        throw nvae;
                    }
                    switch (alt77) {
                        case 1 :
                            // InternalKactors.g:5301:8: lv_exponential_6_1= 'e'
                            {
                            lv_exponential_6_1=(Token)match(input,73,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_exponential_6_1, grammarAccess.getNumberAccess().getExponentialEKeyword_4_0_0_0_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getNumberRule());
                              								}
                              								setWithLastConsumed(current, "exponential", true, null);
                              							
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:5312:8: lv_exponential_6_2= 'E'
                            {
                            lv_exponential_6_2=(Token)match(input,74,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_exponential_6_2, grammarAccess.getNumberAccess().getExponentialEKeyword_4_0_0_0_1());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getNumberRule());
                              								}
                              								setWithLastConsumed(current, "exponential", true, null);
                              							
                            }

                            }
                            break;

                    }


                    }


                    }

                    // InternalKactors.g:5325:5: (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )?
                    int alt78=3;
                    int LA78_0 = input.LA(1);

                    if ( (LA78_0==70) ) {
                        alt78=1;
                    }
                    else if ( (LA78_0==71) ) {
                        alt78=2;
                    }
                    switch (alt78) {
                        case 1 :
                            // InternalKactors.g:5326:6: otherlv_7= '+'
                            {
                            otherlv_7=(Token)match(input,70,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getNumberAccess().getPlusSignKeyword_4_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:5331:6: ( (lv_expNegative_8_0= '-' ) )
                            {
                            // InternalKactors.g:5331:6: ( (lv_expNegative_8_0= '-' ) )
                            // InternalKactors.g:5332:7: (lv_expNegative_8_0= '-' )
                            {
                            // InternalKactors.g:5332:7: (lv_expNegative_8_0= '-' )
                            // InternalKactors.g:5333:8: lv_expNegative_8_0= '-'
                            {
                            lv_expNegative_8_0=(Token)match(input,71,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_expNegative_8_0, grammarAccess.getNumberAccess().getExpNegativeHyphenMinusKeyword_4_0_1_1_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getNumberRule());
                              								}
                              								setWithLastConsumed(current, "expNegative", true, "-");
                              							
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKactors.g:5346:5: ( (lv_exp_9_0= RULE_INT ) )
                    // InternalKactors.g:5347:6: (lv_exp_9_0= RULE_INT )
                    {
                    // InternalKactors.g:5347:6: (lv_exp_9_0= RULE_INT )
                    // InternalKactors.g:5348:7: lv_exp_9_0= RULE_INT
                    {
                    lv_exp_9_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_exp_9_0, grammarAccess.getNumberAccess().getExpINTTerminalRuleCall_4_0_2_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getNumberRule());
                      							}
                      							setWithLastConsumed(
                      								current,
                      								"exp",
                      								lv_exp_9_0,
                      								"org.eclipse.xtext.common.Terminals.INT");
                      						
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNumber"


    // $ANTLR start "entryRuleDate"
    // InternalKactors.g:5370:1: entryRuleDate returns [EObject current=null] : iv_ruleDate= ruleDate EOF ;
    public final EObject entryRuleDate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDate = null;


        try {
            // InternalKactors.g:5370:45: (iv_ruleDate= ruleDate EOF )
            // InternalKactors.g:5371:2: iv_ruleDate= ruleDate EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDateRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDate=ruleDate();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDate; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDate"


    // $ANTLR start "ruleDate"
    // InternalKactors.g:5377:1: ruleDate returns [EObject current=null] : ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) ;
    public final EObject ruleDate() throws RecognitionException {
        EObject current = null;

        Token lv_year_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token lv_bc_3_0=null;
        Token otherlv_4=null;
        Token lv_month_5_0=null;
        Token otherlv_6=null;
        Token lv_day_7_0=null;
        Token lv_hour_8_0=null;
        Token otherlv_9=null;
        Token lv_min_10_0=null;
        Token otherlv_11=null;
        Token lv_sec_12_0=null;
        Token otherlv_13=null;
        Token lv_ms_14_0=null;


        	enterRule();

        try {
            // InternalKactors.g:5383:2: ( ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) )
            // InternalKactors.g:5384:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            {
            // InternalKactors.g:5384:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            // InternalKactors.g:5385:3: ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            {
            // InternalKactors.g:5385:3: ( (lv_year_0_0= RULE_INT ) )
            // InternalKactors.g:5386:4: (lv_year_0_0= RULE_INT )
            {
            // InternalKactors.g:5386:4: (lv_year_0_0= RULE_INT )
            // InternalKactors.g:5387:5: lv_year_0_0= RULE_INT
            {
            lv_year_0_0=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_year_0_0, grammarAccess.getDateAccess().getYearINTTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getDateRule());
              					}
              					setWithLastConsumed(
              						current,
              						"year",
              						lv_year_0_0,
              						"org.eclipse.xtext.common.Terminals.INT");
              				
            }

            }


            }

            // InternalKactors.g:5403:3: (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )?
            int alt80=4;
            switch ( input.LA(1) ) {
                case 75:
                    {
                    alt80=1;
                    }
                    break;
                case 76:
                    {
                    alt80=2;
                    }
                    break;
                case 77:
                    {
                    alt80=3;
                    }
                    break;
            }

            switch (alt80) {
                case 1 :
                    // InternalKactors.g:5404:4: otherlv_1= 'AD'
                    {
                    otherlv_1=(Token)match(input,75,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getDateAccess().getADKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5409:4: otherlv_2= 'CE'
                    {
                    otherlv_2=(Token)match(input,76,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getDateAccess().getCEKeyword_1_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKactors.g:5414:4: ( (lv_bc_3_0= 'BC' ) )
                    {
                    // InternalKactors.g:5414:4: ( (lv_bc_3_0= 'BC' ) )
                    // InternalKactors.g:5415:5: (lv_bc_3_0= 'BC' )
                    {
                    // InternalKactors.g:5415:5: (lv_bc_3_0= 'BC' )
                    // InternalKactors.g:5416:6: lv_bc_3_0= 'BC'
                    {
                    lv_bc_3_0=(Token)match(input,77,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_bc_3_0, grammarAccess.getDateAccess().getBcBCKeyword_1_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDateRule());
                      						}
                      						setWithLastConsumed(current, "bc", true, "BC");
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,71,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDateAccess().getHyphenMinusKeyword_2());
              		
            }
            // InternalKactors.g:5433:3: ( (lv_month_5_0= RULE_INT ) )
            // InternalKactors.g:5434:4: (lv_month_5_0= RULE_INT )
            {
            // InternalKactors.g:5434:4: (lv_month_5_0= RULE_INT )
            // InternalKactors.g:5435:5: lv_month_5_0= RULE_INT
            {
            lv_month_5_0=(Token)match(input,RULE_INT,FOLLOW_69); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_month_5_0, grammarAccess.getDateAccess().getMonthINTTerminalRuleCall_3_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getDateRule());
              					}
              					setWithLastConsumed(
              						current,
              						"month",
              						lv_month_5_0,
              						"org.eclipse.xtext.common.Terminals.INT");
              				
            }

            }


            }

            otherlv_6=(Token)match(input,71,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getDateAccess().getHyphenMinusKeyword_4());
              		
            }
            // InternalKactors.g:5455:3: ( (lv_day_7_0= RULE_INT ) )
            // InternalKactors.g:5456:4: (lv_day_7_0= RULE_INT )
            {
            // InternalKactors.g:5456:4: (lv_day_7_0= RULE_INT )
            // InternalKactors.g:5457:5: lv_day_7_0= RULE_INT
            {
            lv_day_7_0=(Token)match(input,RULE_INT,FOLLOW_70); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_day_7_0, grammarAccess.getDateAccess().getDayINTTerminalRuleCall_5_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getDateRule());
              					}
              					setWithLastConsumed(
              						current,
              						"day",
              						lv_day_7_0,
              						"org.eclipse.xtext.common.Terminals.INT");
              				
            }

            }


            }

            // InternalKactors.g:5473:3: ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==RULE_INT) ) {
                int LA83_1 = input.LA(2);

                if ( (LA83_1==34) ) {
                    alt83=1;
                }
            }
            switch (alt83) {
                case 1 :
                    // InternalKactors.g:5474:4: ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    {
                    // InternalKactors.g:5474:4: ( (lv_hour_8_0= RULE_INT ) )
                    // InternalKactors.g:5475:5: (lv_hour_8_0= RULE_INT )
                    {
                    // InternalKactors.g:5475:5: (lv_hour_8_0= RULE_INT )
                    // InternalKactors.g:5476:6: lv_hour_8_0= RULE_INT
                    {
                    lv_hour_8_0=(Token)match(input,RULE_INT,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_hour_8_0, grammarAccess.getDateAccess().getHourINTTerminalRuleCall_6_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDateRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"hour",
                      							lv_hour_8_0,
                      							"org.eclipse.xtext.common.Terminals.INT");
                      					
                    }

                    }


                    }

                    otherlv_9=(Token)match(input,34,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getDateAccess().getColonKeyword_6_1());
                      			
                    }
                    // InternalKactors.g:5496:4: ( (lv_min_10_0= RULE_INT ) )
                    // InternalKactors.g:5497:5: (lv_min_10_0= RULE_INT )
                    {
                    // InternalKactors.g:5497:5: (lv_min_10_0= RULE_INT )
                    // InternalKactors.g:5498:6: lv_min_10_0= RULE_INT
                    {
                    lv_min_10_0=(Token)match(input,RULE_INT,FOLLOW_71); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_min_10_0, grammarAccess.getDateAccess().getMinINTTerminalRuleCall_6_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDateRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"min",
                      							lv_min_10_0,
                      							"org.eclipse.xtext.common.Terminals.INT");
                      					
                    }

                    }


                    }

                    // InternalKactors.g:5514:4: (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==34) ) {
                        alt82=1;
                    }
                    switch (alt82) {
                        case 1 :
                            // InternalKactors.g:5515:5: otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            {
                            otherlv_11=(Token)match(input,34,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_11, grammarAccess.getDateAccess().getColonKeyword_6_3_0());
                              				
                            }
                            // InternalKactors.g:5519:5: ( (lv_sec_12_0= RULE_INT ) )
                            // InternalKactors.g:5520:6: (lv_sec_12_0= RULE_INT )
                            {
                            // InternalKactors.g:5520:6: (lv_sec_12_0= RULE_INT )
                            // InternalKactors.g:5521:7: lv_sec_12_0= RULE_INT
                            {
                            lv_sec_12_0=(Token)match(input,RULE_INT,FOLLOW_72); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_sec_12_0, grammarAccess.getDateAccess().getSecINTTerminalRuleCall_6_3_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getDateRule());
                              							}
                              							setWithLastConsumed(
                              								current,
                              								"sec",
                              								lv_sec_12_0,
                              								"org.eclipse.xtext.common.Terminals.INT");
                              						
                            }

                            }


                            }

                            // InternalKactors.g:5537:5: (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            int alt81=2;
                            int LA81_0 = input.LA(1);

                            if ( (LA81_0==56) ) {
                                alt81=1;
                            }
                            switch (alt81) {
                                case 1 :
                                    // InternalKactors.g:5538:6: otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) )
                                    {
                                    otherlv_13=(Token)match(input,56,FOLLOW_9); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						newLeafNode(otherlv_13, grammarAccess.getDateAccess().getFullStopKeyword_6_3_2_0());
                                      					
                                    }
                                    // InternalKactors.g:5542:6: ( (lv_ms_14_0= RULE_INT ) )
                                    // InternalKactors.g:5543:7: (lv_ms_14_0= RULE_INT )
                                    {
                                    // InternalKactors.g:5543:7: (lv_ms_14_0= RULE_INT )
                                    // InternalKactors.g:5544:8: lv_ms_14_0= RULE_INT
                                    {
                                    lv_ms_14_0=(Token)match(input,RULE_INT,FOLLOW_2); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_ms_14_0, grammarAccess.getDateAccess().getMsINTTerminalRuleCall_6_3_2_1_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getDateRule());
                                      								}
                                      								setWithLastConsumed(
                                      									current,
                                      									"ms",
                                      									lv_ms_14_0,
                                      									"org.eclipse.xtext.common.Terminals.INT");
                                      							
                                    }

                                    }


                                    }


                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDate"


    // $ANTLR start "entryRulePathName"
    // InternalKactors.g:5567:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKactors.g:5567:48: (iv_rulePathName= rulePathName EOF )
            // InternalKactors.g:5568:2: iv_rulePathName= rulePathName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPathNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePathName=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePathName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePathName"


    // $ANTLR start "rulePathName"
    // InternalKactors.g:5574:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKactors.g:5580:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKactors.g:5581:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKactors.g:5581:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKactors.g:5582:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_72); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:5589:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==56) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    // InternalKactors.g:5590:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,56,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPathNameAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_72); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop84;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePathName"


    // $ANTLR start "entryRulePath"
    // InternalKactors.g:5607:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKactors.g:5607:44: (iv_rulePath= rulePath EOF )
            // InternalKactors.g:5608:2: iv_rulePath= rulePath EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPathRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePath=rulePath();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePath.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePath"


    // $ANTLR start "rulePath"
    // InternalKactors.g:5614:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token this_UPPERCASE_ID_1=null;
        Token kw=null;
        Token this_LOWERCASE_ID_4=null;
        Token this_UPPERCASE_ID_5=null;


        	enterRule();

        try {
            // InternalKactors.g:5620:2: ( ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) )
            // InternalKactors.g:5621:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            {
            // InternalKactors.g:5621:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            // InternalKactors.g:5622:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            {
            // InternalKactors.g:5622:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID )
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==RULE_LOWERCASE_ID) ) {
                alt85=1;
            }
            else if ( (LA85_0==RULE_UPPERCASE_ID) ) {
                alt85=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 85, 0, input);

                throw nvae;
            }
            switch (alt85) {
                case 1 :
                    // InternalKactors.g:5623:4: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_73); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5631:4: this_UPPERCASE_ID_1= RULE_UPPERCASE_ID
                    {
                    this_UPPERCASE_ID_1=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_73); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_UPPERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_UPPERCASE_ID_1, grammarAccess.getPathAccess().getUPPERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:5639:3: ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( ((LA88_0>=55 && LA88_0<=56)) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // InternalKactors.g:5640:4: (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    {
            	    // InternalKactors.g:5640:4: (kw= '.' | kw= '/' )
            	    int alt86=2;
            	    int LA86_0 = input.LA(1);

            	    if ( (LA86_0==56) ) {
            	        alt86=1;
            	    }
            	    else if ( (LA86_0==55) ) {
            	        alt86=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 86, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt86) {
            	        case 1 :
            	            // InternalKactors.g:5641:5: kw= '.'
            	            {
            	            kw=(Token)match(input,56,FOLLOW_26); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:5647:5: kw= '/'
            	            {
            	            kw=(Token)match(input,55,FOLLOW_26); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getSolidusKeyword_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalKactors.g:5653:4: (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    int alt87=2;
            	    int LA87_0 = input.LA(1);

            	    if ( (LA87_0==RULE_LOWERCASE_ID) ) {
            	        alt87=1;
            	    }
            	    else if ( (LA87_0==RULE_UPPERCASE_ID) ) {
            	        alt87=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 87, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt87) {
            	        case 1 :
            	            // InternalKactors.g:5654:5: this_LOWERCASE_ID_4= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_4=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_73); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_4, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:5662:5: this_UPPERCASE_ID_5= RULE_UPPERCASE_ID
            	            {
            	            this_UPPERCASE_ID_5=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_73); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_UPPERCASE_ID_5);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_UPPERCASE_ID_5, grammarAccess.getPathAccess().getUPPERCASE_IDTerminalRuleCall_1_1_1());
            	              				
            	            }

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop88;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePath"


    // $ANTLR start "entryRuleVersionNumber"
    // InternalKactors.g:5675:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKactors.g:5675:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKactors.g:5676:2: iv_ruleVersionNumber= ruleVersionNumber EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVersionNumberRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVersionNumber=ruleVersionNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVersionNumber.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVersionNumber"


    // $ANTLR start "ruleVersionNumber"
    // InternalKactors.g:5682:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
    public final AntlrDatatypeRuleToken ruleVersionNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_LOWERCASE_ID_6=null;
        Token this_UPPERCASE_ID_7=null;


        	enterRule();

        try {
            // InternalKactors.g:5688:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKactors.g:5689:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKactors.g:5689:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKactors.g:5690:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_74); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:5697:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==56) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // InternalKactors.g:5698:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,56,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_74); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKactors.g:5710:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt89=2;
                    int LA89_0 = input.LA(1);

                    if ( (LA89_0==56) ) {
                        alt89=1;
                    }
                    switch (alt89) {
                        case 1 :
                            // InternalKactors.g:5711:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,56,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_75); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(this_INT_4);
                              				
                            }
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_INT_4, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_2_1());
                              				
                            }

                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalKactors.g:5725:3: (kw= '-' )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==71) ) {
                int LA91_1 = input.LA(2);

                if ( (synpred184_InternalKactors()) ) {
                    alt91=1;
                }
            }
            switch (alt91) {
                case 1 :
                    // InternalKactors.g:5726:4: kw= '-'
                    {
                    kw=(Token)match(input,71,FOLLOW_76); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:5732:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt92=3;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==RULE_LOWERCASE_ID) ) {
                int LA92_1 = input.LA(2);

                if ( (synpred185_InternalKactors()) ) {
                    alt92=1;
                }
            }
            else if ( (LA92_0==RULE_UPPERCASE_ID) ) {
                alt92=2;
            }
            switch (alt92) {
                case 1 :
                    // InternalKactors.g:5733:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_6);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_6, grammarAccess.getVersionNumberAccess().getLOWERCASE_IDTerminalRuleCall_3_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5741:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
                    {
                    this_UPPERCASE_ID_7=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_UPPERCASE_ID_7);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_UPPERCASE_ID_7, grammarAccess.getVersionNumberAccess().getUPPERCASE_IDTerminalRuleCall_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVersionNumber"


    // $ANTLR start "ruleUnitOp"
    // InternalKactors.g:5753:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKactors.g:5759:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKactors.g:5760:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKactors.g:5760:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt93=3;
            switch ( input.LA(1) ) {
            case 55:
                {
                alt93=1;
                }
                break;
            case 78:
                {
                alt93=2;
                }
                break;
            case 51:
                {
                alt93=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 93, 0, input);

                throw nvae;
            }

            switch (alt93) {
                case 1 :
                    // InternalKactors.g:5761:3: (enumLiteral_0= '/' )
                    {
                    // InternalKactors.g:5761:3: (enumLiteral_0= '/' )
                    // InternalKactors.g:5762:4: enumLiteral_0= '/'
                    {
                    enumLiteral_0=(Token)match(input,55,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:5769:3: (enumLiteral_1= '^' )
                    {
                    // InternalKactors.g:5769:3: (enumLiteral_1= '^' )
                    // InternalKactors.g:5770:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,78,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:5777:3: (enumLiteral_2= '*' )
                    {
                    // InternalKactors.g:5777:3: (enumLiteral_2= '*' )
                    // InternalKactors.g:5778:4: enumLiteral_2= '*'
                    {
                    enumLiteral_2=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getSTAREnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_2, grammarAccess.getUnitOpAccess().getSTAREnumLiteralDeclaration_2());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnitOp"

    // $ANTLR start synpred1_InternalKactors
    public final void synpred1_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_preamble_1_0 = null;


        // InternalKactors.g:98:4: ( (lv_preamble_1_0= rulePreamble ) )
        // InternalKactors.g:98:4: (lv_preamble_1_0= rulePreamble )
        {
        // InternalKactors.g:98:4: (lv_preamble_1_0= rulePreamble )
        // InternalKactors.g:99:5: lv_preamble_1_0= rulePreamble
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getModelAccess().getPreamblePreambleParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_preamble_1_0=rulePreamble();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred1_InternalKactors

    // $ANTLR start synpred5_InternalKactors
    public final void synpred5_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_4=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_imports_5_0 = null;

        AntlrDatatypeRuleToken lv_imports_7_0 = null;


        // InternalKactors.g:206:4: ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) )
        // InternalKactors.g:206:4: ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) )
        {
        // InternalKactors.g:206:4: ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) )
        // InternalKactors.g:207:5: {...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred5_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
        }
        // InternalKactors.g:207:105: ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) )
        // InternalKactors.g:208:6: ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
        // InternalKactors.g:211:9: ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) )
        // InternalKactors.g:211:10: {...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred5_InternalKactors", "true");
        }
        // InternalKactors.g:211:19: (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* )
        // InternalKactors.g:211:20: otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )*
        {
        otherlv_4=(Token)match(input,23,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:215:9: ( (lv_imports_5_0= rulePathName ) )
        // InternalKactors.g:216:10: (lv_imports_5_0= rulePathName )
        {
        // InternalKactors.g:216:10: (lv_imports_5_0= rulePathName )
        // InternalKactors.g:217:11: lv_imports_5_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_1_0());
          										
        }
        pushFollow(FOLLOW_17);
        lv_imports_5_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:234:9: (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )*
        loop94:
        do {
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==24) ) {
                alt94=1;
            }


            switch (alt94) {
        	case 1 :
        	    // InternalKactors.g:235:10: otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) )
        	    {
        	    otherlv_6=(Token)match(input,24,FOLLOW_4); if (state.failed) return ;
        	    // InternalKactors.g:239:10: ( (lv_imports_7_0= rulePathName ) )
        	    // InternalKactors.g:240:11: (lv_imports_7_0= rulePathName )
        	    {
        	    // InternalKactors.g:240:11: (lv_imports_7_0= rulePathName )
        	    // InternalKactors.g:241:12: lv_imports_7_0= rulePathName
        	    {
        	    if ( state.backtracking==0 ) {

        	      												newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_2_1_0());
        	      											
        	    }
        	    pushFollow(FOLLOW_17);
        	    lv_imports_7_0=rulePathName();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    break loop94;
            }
        } while (true);


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred5_InternalKactors

    // $ANTLR start synpred6_InternalKactors
    public final void synpred6_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_8=null;
        AntlrDatatypeRuleToken lv_worldview_9_0 = null;


        // InternalKactors.g:265:4: ( ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) )
        // InternalKactors.g:265:4: ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) )
        {
        // InternalKactors.g:265:4: ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) )
        // InternalKactors.g:266:5: {...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred6_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
        }
        // InternalKactors.g:266:105: ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) )
        // InternalKactors.g:267:6: ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
        // InternalKactors.g:270:9: ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) )
        // InternalKactors.g:270:10: {...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred6_InternalKactors", "true");
        }
        // InternalKactors.g:270:19: (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) )
        // InternalKactors.g:270:20: otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) )
        {
        otherlv_8=(Token)match(input,25,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:274:9: ( (lv_worldview_9_0= rulePathName ) )
        // InternalKactors.g:275:10: (lv_worldview_9_0= rulePathName )
        {
        // InternalKactors.g:275:10: (lv_worldview_9_0= rulePathName )
        // InternalKactors.g:276:11: lv_worldview_9_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_2_1_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_worldview_9_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred6_InternalKactors

    // $ANTLR start synpred9_InternalKactors
    public final void synpred9_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_10=null;
        Token lv_label_11_1=null;
        Token lv_label_11_2=null;
        Token lv_label_11_3=null;

        // InternalKactors.g:299:4: ( ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) )
        // InternalKactors.g:299:4: ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) )
        {
        // InternalKactors.g:299:4: ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:300:5: {...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred9_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
        }
        // InternalKactors.g:300:105: ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:301:6: ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
        // InternalKactors.g:304:9: ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) )
        // InternalKactors.g:304:10: {...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred9_InternalKactors", "true");
        }
        // InternalKactors.g:304:19: (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) )
        // InternalKactors.g:304:20: otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) )
        {
        otherlv_10=(Token)match(input,26,FOLLOW_7); if (state.failed) return ;
        // InternalKactors.g:308:9: ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) )
        // InternalKactors.g:309:10: ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) )
        {
        // InternalKactors.g:309:10: ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) )
        // InternalKactors.g:310:11: (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING )
        {
        // InternalKactors.g:310:11: (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING )
        int alt95=3;
        switch ( input.LA(1) ) {
        case RULE_LOWERCASE_ID:
            {
            alt95=1;
            }
            break;
        case RULE_ID:
            {
            alt95=2;
            }
            break;
        case RULE_STRING:
            {
            alt95=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 95, 0, input);

            throw nvae;
        }

        switch (alt95) {
            case 1 :
                // InternalKactors.g:311:12: lv_label_11_1= RULE_LOWERCASE_ID
                {
                lv_label_11_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:326:12: lv_label_11_2= RULE_ID
                {
                lv_label_11_2=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 3 :
                // InternalKactors.g:341:12: lv_label_11_3= RULE_STRING
                {
                lv_label_11_3=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

                }
                break;

        }


        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred9_InternalKactors

    // $ANTLR start synpred10_InternalKactors
    public final void synpred10_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_12=null;
        Token lv_description_13_0=null;

        // InternalKactors.g:364:4: ( ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:364:4: ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:364:4: ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:365:5: {...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred10_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
        }
        // InternalKactors.g:365:105: ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:366:6: ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
        // InternalKactors.g:369:9: ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) )
        // InternalKactors.g:369:10: {...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred10_InternalKactors", "true");
        }
        // InternalKactors.g:369:19: (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) )
        // InternalKactors.g:369:20: otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) )
        {
        otherlv_12=(Token)match(input,27,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:373:9: ( (lv_description_13_0= RULE_STRING ) )
        // InternalKactors.g:374:10: (lv_description_13_0= RULE_STRING )
        {
        // InternalKactors.g:374:10: (lv_description_13_0= RULE_STRING )
        // InternalKactors.g:375:11: lv_description_13_0= RULE_STRING
        {
        lv_description_13_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred10_InternalKactors

    // $ANTLR start synpred11_InternalKactors
    public final void synpred11_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_14=null;
        Token lv_permissions_15_0=null;

        // InternalKactors.g:397:4: ( ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:397:4: ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:397:4: ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:398:5: {...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred11_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4)");
        }
        // InternalKactors.g:398:105: ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:399:6: ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4);
        // InternalKactors.g:402:9: ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) )
        // InternalKactors.g:402:10: {...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred11_InternalKactors", "true");
        }
        // InternalKactors.g:402:19: (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) )
        // InternalKactors.g:402:20: otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) )
        {
        otherlv_14=(Token)match(input,28,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:406:9: ( (lv_permissions_15_0= RULE_STRING ) )
        // InternalKactors.g:407:10: (lv_permissions_15_0= RULE_STRING )
        {
        // InternalKactors.g:407:10: (lv_permissions_15_0= RULE_STRING )
        // InternalKactors.g:408:11: lv_permissions_15_0= RULE_STRING
        {
        lv_permissions_15_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred11_InternalKactors

    // $ANTLR start synpred12_InternalKactors
    public final void synpred12_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        Token lv_authors_17_0=null;

        // InternalKactors.g:435:10: ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )
        // InternalKactors.g:435:10: {...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred12_InternalKactors", "true");
        }
        // InternalKactors.g:435:19: (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) )
        // InternalKactors.g:435:20: otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) )
        {
        otherlv_16=(Token)match(input,29,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:439:9: ( (lv_authors_17_0= RULE_STRING ) )
        // InternalKactors.g:440:10: (lv_authors_17_0= RULE_STRING )
        {
        // InternalKactors.g:440:10: (lv_authors_17_0= RULE_STRING )
        // InternalKactors.g:441:11: lv_authors_17_0= RULE_STRING
        {
        lv_authors_17_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred12_InternalKactors

    // $ANTLR start synpred13_InternalKactors
    public final void synpred13_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        Token lv_authors_17_0=null;

        // InternalKactors.g:430:4: ( ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) )
        // InternalKactors.g:430:4: ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) )
        {
        // InternalKactors.g:430:4: ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) )
        // InternalKactors.g:431:5: {...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5)");
        }
        // InternalKactors.g:431:105: ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ )
        // InternalKactors.g:432:6: ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5);
        // InternalKactors.g:435:9: ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+
        int cnt96=0;
        loop96:
        do {
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==29) && ((true))) {
                alt96=1;
            }


            switch (alt96) {
        	case 1 :
        	    // InternalKactors.g:435:10: {...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) )
        	    {
        	    if ( !((true)) ) {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        throw new FailedPredicateException(input, "synpred13_InternalKactors", "true");
        	    }
        	    // InternalKactors.g:435:19: (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) )
        	    // InternalKactors.g:435:20: otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) )
        	    {
        	    otherlv_16=(Token)match(input,29,FOLLOW_8); if (state.failed) return ;
        	    // InternalKactors.g:439:9: ( (lv_authors_17_0= RULE_STRING ) )
        	    // InternalKactors.g:440:10: (lv_authors_17_0= RULE_STRING )
        	    {
        	    // InternalKactors.g:440:10: (lv_authors_17_0= RULE_STRING )
        	    // InternalKactors.g:441:11: lv_authors_17_0= RULE_STRING
        	    {
        	    lv_authors_17_0=(Token)match(input,RULE_STRING,FOLLOW_77); if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt96 >= 1 ) break loop96;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(96, input);
                    throw eee;
            }
            cnt96++;
        } while (true);


        }


        }


        }
    }
    // $ANTLR end synpred13_InternalKactors

    // $ANTLR start synpred14_InternalKactors
    public final void synpred14_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_18=null;
        AntlrDatatypeRuleToken lv_version_19_0 = null;


        // InternalKactors.g:463:4: ( ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) )
        // InternalKactors.g:463:4: ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) )
        {
        // InternalKactors.g:463:4: ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) )
        // InternalKactors.g:464:5: {...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred14_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6)");
        }
        // InternalKactors.g:464:105: ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) )
        // InternalKactors.g:465:6: ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6);
        // InternalKactors.g:468:9: ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) )
        // InternalKactors.g:468:10: {...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred14_InternalKactors", "true");
        }
        // InternalKactors.g:468:19: (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) )
        // InternalKactors.g:468:20: otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) )
        {
        otherlv_18=(Token)match(input,30,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:472:9: ( (lv_version_19_0= ruleVersionNumber ) )
        // InternalKactors.g:473:10: (lv_version_19_0= ruleVersionNumber )
        {
        // InternalKactors.g:473:10: (lv_version_19_0= ruleVersionNumber )
        // InternalKactors.g:474:11: lv_version_19_0= ruleVersionNumber
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_2_6_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_version_19_0=ruleVersionNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred14_InternalKactors

    // $ANTLR start synpred16_InternalKactors
    public final void synpred16_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_20=null;
        Token lv_createcomment_22_0=null;
        EObject lv_created_21_0 = null;


        // InternalKactors.g:497:4: ( ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:497:4: ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:497:4: ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:498:5: {...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred16_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7)");
        }
        // InternalKactors.g:498:105: ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:499:6: ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7);
        // InternalKactors.g:502:9: ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) )
        // InternalKactors.g:502:10: {...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred16_InternalKactors", "true");
        }
        // InternalKactors.g:502:19: (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? )
        // InternalKactors.g:502:20: otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )?
        {
        otherlv_20=(Token)match(input,31,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:506:9: ( (lv_created_21_0= ruleDate ) )
        // InternalKactors.g:507:10: (lv_created_21_0= ruleDate )
        {
        // InternalKactors.g:507:10: (lv_created_21_0= ruleDate )
        // InternalKactors.g:508:11: lv_created_21_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_2_7_1_0());
          										
        }
        pushFollow(FOLLOW_78);
        lv_created_21_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:525:9: ( (lv_createcomment_22_0= RULE_STRING ) )?
        int alt97=2;
        int LA97_0 = input.LA(1);

        if ( (LA97_0==RULE_STRING) ) {
            alt97=1;
        }
        switch (alt97) {
            case 1 :
                // InternalKactors.g:526:10: (lv_createcomment_22_0= RULE_STRING )
                {
                // InternalKactors.g:526:10: (lv_createcomment_22_0= RULE_STRING )
                // InternalKactors.g:527:11: lv_createcomment_22_0= RULE_STRING
                {
                lv_createcomment_22_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

                }


                }
                break;

        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred16_InternalKactors

    // $ANTLR start synpred18_InternalKactors
    public final void synpred18_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_23=null;
        Token lv_modcomment_25_0=null;
        EObject lv_modified_24_0 = null;


        // InternalKactors.g:549:4: ( ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:549:4: ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:549:4: ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:550:5: {...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8)");
        }
        // InternalKactors.g:550:105: ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:551:6: ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8);
        // InternalKactors.g:554:9: ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) )
        // InternalKactors.g:554:10: {...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKactors", "true");
        }
        // InternalKactors.g:554:19: (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? )
        // InternalKactors.g:554:20: otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )?
        {
        otherlv_23=(Token)match(input,32,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:558:9: ( (lv_modified_24_0= ruleDate ) )
        // InternalKactors.g:559:10: (lv_modified_24_0= ruleDate )
        {
        // InternalKactors.g:559:10: (lv_modified_24_0= ruleDate )
        // InternalKactors.g:560:11: lv_modified_24_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_2_8_1_0());
          										
        }
        pushFollow(FOLLOW_78);
        lv_modified_24_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:577:9: ( (lv_modcomment_25_0= RULE_STRING ) )?
        int alt98=2;
        int LA98_0 = input.LA(1);

        if ( (LA98_0==RULE_STRING) ) {
            alt98=1;
        }
        switch (alt98) {
            case 1 :
                // InternalKactors.g:578:10: (lv_modcomment_25_0= RULE_STRING )
                {
                // InternalKactors.g:578:10: (lv_modcomment_25_0= RULE_STRING )
                // InternalKactors.g:579:11: lv_modcomment_25_0= RULE_STRING
                {
                lv_modcomment_25_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

                }


                }
                break;

        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred18_InternalKactors

    // $ANTLR start synpred53_InternalKactors
    public final void synpred53_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:1719:5: ( 'to' )
        // InternalKactors.g:1719:6: 'to'
        {
        match(input,48,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred53_InternalKactors

    // $ANTLR start synpred80_InternalKactors
    public final void synpred80_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:2438:5: ( 'to' )
        // InternalKactors.g:2438:6: 'to'
        {
        match(input,48,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred80_InternalKactors

    // $ANTLR start synpred106_InternalKactors
    public final void synpred106_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_number_0_0 = null;


        // InternalKactors.g:3130:3: ( ( (lv_number_0_0= ruleNumber ) ) )
        // InternalKactors.g:3130:3: ( (lv_number_0_0= ruleNumber ) )
        {
        // InternalKactors.g:3130:3: ( (lv_number_0_0= ruleNumber ) )
        // InternalKactors.g:3131:4: (lv_number_0_0= ruleNumber )
        {
        // InternalKactors.g:3131:4: (lv_number_0_0= ruleNumber )
        // InternalKactors.g:3132:5: lv_number_0_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getLiteralAccess().getNumberNumberParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_number_0_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred106_InternalKactors

    // $ANTLR start synpred109_InternalKactors
    public final void synpred109_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_date_5_0 = null;


        // InternalKactors.g:3214:3: ( ( (lv_date_5_0= ruleDate ) ) )
        // InternalKactors.g:3214:3: ( (lv_date_5_0= ruleDate ) )
        {
        // InternalKactors.g:3214:3: ( (lv_date_5_0= ruleDate ) )
        // InternalKactors.g:3215:4: (lv_date_5_0= ruleDate )
        {
        // InternalKactors.g:3215:4: (lv_date_5_0= ruleDate )
        // InternalKactors.g:3216:5: lv_date_5_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getLiteralAccess().getDateDateParserRuleCall_3_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_date_5_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred109_InternalKactors

    // $ANTLR start synpred113_InternalKactors
    public final void synpred113_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;


        // InternalKactors.g:3350:6: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )
        // InternalKactors.g:3350:6: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
        {
        otherlv_1=(Token)match(input,35,FOLLOW_21); if (state.failed) return ;
        // InternalKactors.g:3354:6: ( (lv_parameters_2_0= ruleParameterList ) )?
        int alt115=2;
        int LA115_0 = input.LA(1);

        if ( (LA115_0==RULE_LOWERCASE_ID||(LA115_0>=RULE_STRING && LA115_0<=RULE_EXPR)||LA115_0==RULE_INT||LA115_0==35||LA115_0==39||LA115_0==42||(LA115_0>=44 && LA115_0<=45)||LA115_0==52||(LA115_0>=70 && LA115_0<=71)) ) {
            alt115=1;
        }
        switch (alt115) {
            case 1 :
                // InternalKactors.g:3355:7: (lv_parameters_2_0= ruleParameterList )
                {
                // InternalKactors.g:3355:7: (lv_parameters_2_0= ruleParameterList )
                // InternalKactors.g:3356:8: lv_parameters_2_0= ruleParameterList
                {
                if ( state.backtracking==0 ) {

                  								newCompositeNode(grammarAccess.getVerbAccess().getParametersParameterListParserRuleCall_0_0_1_1_0());
                  							
                }
                pushFollow(FOLLOW_22);
                lv_parameters_2_0=ruleParameterList();

                state._fsp--;
                if (state.failed) return ;

                }


                }
                break;

        }

        otherlv_3=(Token)match(input,36,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred113_InternalKactors

    // $ANTLR start synpred117_InternalKactors
    public final void synpred117_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_body_2_0 = null;


        // InternalKactors.g:3458:4: ( (lv_body_2_0= ruleBody ) )
        // InternalKactors.g:3458:4: (lv_body_2_0= ruleBody )
        {
        // InternalKactors.g:3458:4: (lv_body_2_0= ruleBody )
        // InternalKactors.g:3459:5: lv_body_2_0= ruleBody
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getGroupAccess().getBodyBodyParserRuleCall_2_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_body_2_0=ruleBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred117_InternalKactors

    // $ANTLR start synpred118_InternalKactors
    public final void synpred118_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_next_1_0 = null;


        // InternalKactors.g:3519:4: ( (lv_next_1_0= ruleNextStatement ) )
        // InternalKactors.g:3519:4: (lv_next_1_0= ruleNextStatement )
        {
        // InternalKactors.g:3519:4: (lv_next_1_0= ruleNextStatement )
        // InternalKactors.g:3520:5: lv_next_1_0= ruleNextStatement
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementListAccess().getNextNextStatementParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_next_1_0=ruleNextStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred118_InternalKactors

    // $ANTLR start synpred119_InternalKactors
    public final void synpred119_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_0_0 = null;


        // InternalKactors.g:3556:3: ( ( (lv_verb_0_0= ruleVerb ) ) )
        // InternalKactors.g:3556:3: ( (lv_verb_0_0= ruleVerb ) )
        {
        // InternalKactors.g:3556:3: ( (lv_verb_0_0= ruleVerb ) )
        // InternalKactors.g:3557:4: (lv_verb_0_0= ruleVerb )
        {
        // InternalKactors.g:3557:4: (lv_verb_0_0= ruleVerb )
        // InternalKactors.g:3558:5: lv_verb_0_0= ruleVerb
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementAccess().getVerbVerbParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_verb_0_0=ruleVerb();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred119_InternalKactors

    // $ANTLR start synpred120_InternalKactors
    public final void synpred120_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_group_1_0 = null;


        // InternalKactors.g:3576:3: ( ( (lv_group_1_0= ruleGroup ) ) )
        // InternalKactors.g:3576:3: ( (lv_group_1_0= ruleGroup ) )
        {
        // InternalKactors.g:3576:3: ( (lv_group_1_0= ruleGroup ) )
        // InternalKactors.g:3577:4: (lv_group_1_0= ruleGroup )
        {
        // InternalKactors.g:3577:4: (lv_group_1_0= ruleGroup )
        // InternalKactors.g:3578:5: lv_group_1_0= ruleGroup
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementAccess().getGroupGroupParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_group_1_0=ruleGroup();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred120_InternalKactors

    // $ANTLR start synpred125_InternalKactors
    public final void synpred125_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_1_0 = null;


        // InternalKactors.g:3718:4: ( ( (lv_verb_1_0= ruleVerb ) ) )
        // InternalKactors.g:3718:4: ( (lv_verb_1_0= ruleVerb ) )
        {
        // InternalKactors.g:3718:4: ( (lv_verb_1_0= ruleVerb ) )
        // InternalKactors.g:3719:5: (lv_verb_1_0= ruleVerb )
        {
        // InternalKactors.g:3719:5: (lv_verb_1_0= ruleVerb )
        // InternalKactors.g:3720:6: lv_verb_1_0= ruleVerb
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getNextStatementAccess().getVerbVerbParserRuleCall_1_0_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_verb_1_0=ruleVerb();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred125_InternalKactors

    // $ANTLR start synpred126_InternalKactors
    public final void synpred126_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_group_2_0 = null;


        // InternalKactors.g:3738:4: ( ( (lv_group_2_0= ruleGroup ) ) )
        // InternalKactors.g:3738:4: ( (lv_group_2_0= ruleGroup ) )
        {
        // InternalKactors.g:3738:4: ( (lv_group_2_0= ruleGroup ) )
        // InternalKactors.g:3739:5: (lv_group_2_0= ruleGroup )
        {
        // InternalKactors.g:3739:5: (lv_group_2_0= ruleGroup )
        // InternalKactors.g:3740:6: lv_group_2_0= ruleGroup
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getNextStatementAccess().getGroupGroupParserRuleCall_1_1_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_group_2_0=ruleGroup();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred126_InternalKactors

    // $ANTLR start synpred131_InternalKactors
    public final void synpred131_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_elseIfExpression_5_0=null;
        EObject lv_elseIfCall_6_0 = null;


        // InternalKactors.g:3918:4: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )
        // InternalKactors.g:3918:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) )
        {
        otherlv_3=(Token)match(input,65,FOLLOW_58); if (state.failed) return ;
        otherlv_4=(Token)match(input,64,FOLLOW_55); if (state.failed) return ;
        // InternalKactors.g:3926:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
        // InternalKactors.g:3927:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        {
        // InternalKactors.g:3927:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        // InternalKactors.g:3928:6: lv_elseIfExpression_5_0= RULE_EXPR
        {
        lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_56); if (state.failed) return ;

        }


        }

        // InternalKactors.g:3944:4: ( (lv_elseIfCall_6_0= ruleIfBody ) )
        // InternalKactors.g:3945:5: (lv_elseIfCall_6_0= ruleIfBody )
        {
        // InternalKactors.g:3945:5: (lv_elseIfCall_6_0= ruleIfBody )
        // InternalKactors.g:3946:6: lv_elseIfCall_6_0= ruleIfBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getIfStatementAccess().getElseIfCallIfBodyParserRuleCall_3_3_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_elseIfCall_6_0=ruleIfBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred131_InternalKactors

    // $ANTLR start synpred132_InternalKactors
    public final void synpred132_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        EObject lv_elseCall_8_0 = null;


        // InternalKactors.g:3965:4: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )
        // InternalKactors.g:3965:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) )
        {
        otherlv_7=(Token)match(input,65,FOLLOW_56); if (state.failed) return ;
        // InternalKactors.g:3969:4: ( (lv_elseCall_8_0= ruleIfBody ) )
        // InternalKactors.g:3970:5: (lv_elseCall_8_0= ruleIfBody )
        {
        // InternalKactors.g:3970:5: (lv_elseCall_8_0= ruleIfBody )
        // InternalKactors.g:3971:6: lv_elseCall_8_0= ruleIfBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getIfStatementAccess().getElseCallIfBodyParserRuleCall_4_1_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_elseCall_8_0=ruleIfBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred132_InternalKactors

    // $ANTLR start synpred133_InternalKactors
    public final void synpred133_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_0_0 = null;


        // InternalKactors.g:4008:3: ( ( (lv_verb_0_0= ruleVerb ) ) )
        // InternalKactors.g:4008:3: ( (lv_verb_0_0= ruleVerb ) )
        {
        // InternalKactors.g:4008:3: ( (lv_verb_0_0= ruleVerb ) )
        // InternalKactors.g:4009:4: (lv_verb_0_0= ruleVerb )
        {
        // InternalKactors.g:4009:4: (lv_verb_0_0= ruleVerb )
        // InternalKactors.g:4010:5: lv_verb_0_0= ruleVerb
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getIfBodyAccess().getVerbVerbParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_verb_0_0=ruleVerb();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred133_InternalKactors

    // $ANTLR start synpred135_InternalKactors
    public final void synpred135_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_statement_0_0 = null;


        // InternalKactors.g:4275:3: ( ( (lv_statement_0_0= ruleStatement ) ) )
        // InternalKactors.g:4275:3: ( (lv_statement_0_0= ruleStatement ) )
        {
        // InternalKactors.g:4275:3: ( (lv_statement_0_0= ruleStatement ) )
        // InternalKactors.g:4276:4: (lv_statement_0_0= ruleStatement )
        {
        // InternalKactors.g:4276:4: (lv_statement_0_0= ruleStatement )
        // InternalKactors.g:4277:5: lv_statement_0_0= ruleStatement
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getActionsAccess().getStatementStatementParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_statement_0_0=ruleStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred135_InternalKactors

    // $ANTLR start synpred136_InternalKactors
    public final void synpred136_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_statements_2_0 = null;


        // InternalKactors.g:4295:3: ( (otherlv_1= '(' ( (lv_statements_2_0= ruleStatementList ) ) otherlv_3= ')' ) )
        // InternalKactors.g:4295:3: (otherlv_1= '(' ( (lv_statements_2_0= ruleStatementList ) ) otherlv_3= ')' )
        {
        // InternalKactors.g:4295:3: (otherlv_1= '(' ( (lv_statements_2_0= ruleStatementList ) ) otherlv_3= ')' )
        // InternalKactors.g:4296:4: otherlv_1= '(' ( (lv_statements_2_0= ruleStatementList ) ) otherlv_3= ')'
        {
        otherlv_1=(Token)match(input,35,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:4300:4: ( (lv_statements_2_0= ruleStatementList ) )
        // InternalKactors.g:4301:5: (lv_statements_2_0= ruleStatementList )
        {
        // InternalKactors.g:4301:5: (lv_statements_2_0= ruleStatementList )
        // InternalKactors.g:4302:6: lv_statements_2_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getActionsAccess().getStatementsStatementListParserRuleCall_1_1_0());
          					
        }
        pushFollow(FOLLOW_22);
        lv_statements_2_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_3=(Token)match(input,36,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred136_InternalKactors

    // $ANTLR start synpred137_InternalKactors
    public final void synpred137_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_match_4_0 = null;


        // InternalKactors.g:4325:3: ( ( (lv_match_4_0= ruleMatch ) ) )
        // InternalKactors.g:4325:3: ( (lv_match_4_0= ruleMatch ) )
        {
        // InternalKactors.g:4325:3: ( (lv_match_4_0= ruleMatch ) )
        // InternalKactors.g:4326:4: (lv_match_4_0= ruleMatch )
        {
        // InternalKactors.g:4326:4: (lv_match_4_0= ruleMatch )
        // InternalKactors.g:4327:5: lv_match_4_0= ruleMatch
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_2_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_match_4_0=ruleMatch();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred137_InternalKactors

    // $ANTLR start synpred143_InternalKactors
    public final void synpred143_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_13=null;
        EObject lv_literal_12_0 = null;

        EObject lv_body_14_0 = null;


        // InternalKactors.g:4588:3: ( ( ( (lv_literal_12_0= ruleLiteral ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) )
        // InternalKactors.g:4588:3: ( ( (lv_literal_12_0= ruleLiteral ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:4588:3: ( ( (lv_literal_12_0= ruleLiteral ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
        // InternalKactors.g:4589:4: ( (lv_literal_12_0= ruleLiteral ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) )
        {
        // InternalKactors.g:4589:4: ( (lv_literal_12_0= ruleLiteral ) )
        // InternalKactors.g:4590:5: (lv_literal_12_0= ruleLiteral )
        {
        // InternalKactors.g:4590:5: (lv_literal_12_0= ruleLiteral )
        // InternalKactors.g:4591:6: lv_literal_12_0= ruleLiteral
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_4_0_0());
          					
        }
        pushFollow(FOLLOW_63);
        lv_literal_12_0=ruleLiteral();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_13=(Token)match(input,69,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:4612:4: ( (lv_body_14_0= ruleStatementList ) )
        // InternalKactors.g:4613:5: (lv_body_14_0= ruleStatementList )
        {
        // InternalKactors.g:4613:5: (lv_body_14_0= ruleStatementList )
        // InternalKactors.g:4614:6: lv_body_14_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_4_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_14_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred143_InternalKactors

    // $ANTLR start synpred144_InternalKactors
    public final void synpred144_InternalKactors_fragment() throws RecognitionException {   
        Token lv_text_15_0=null;
        Token otherlv_16=null;
        EObject lv_body_17_0 = null;


        // InternalKactors.g:4633:3: ( ( ( (lv_text_15_0= RULE_STRING ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) )
        // InternalKactors.g:4633:3: ( ( (lv_text_15_0= RULE_STRING ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:4633:3: ( ( (lv_text_15_0= RULE_STRING ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
        // InternalKactors.g:4634:4: ( (lv_text_15_0= RULE_STRING ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) )
        {
        // InternalKactors.g:4634:4: ( (lv_text_15_0= RULE_STRING ) )
        // InternalKactors.g:4635:5: (lv_text_15_0= RULE_STRING )
        {
        // InternalKactors.g:4635:5: (lv_text_15_0= RULE_STRING )
        // InternalKactors.g:4636:6: lv_text_15_0= RULE_STRING
        {
        lv_text_15_0=(Token)match(input,RULE_STRING,FOLLOW_63); if (state.failed) return ;

        }


        }

        otherlv_16=(Token)match(input,69,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:4656:4: ( (lv_body_17_0= ruleStatementList ) )
        // InternalKactors.g:4657:5: (lv_body_17_0= ruleStatementList )
        {
        // InternalKactors.g:4657:5: (lv_body_17_0= ruleStatementList )
        // InternalKactors.g:4658:6: lv_body_17_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_5_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_17_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred144_InternalKactors

    // $ANTLR start synpred148_InternalKactors
    public final void synpred148_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:4764:5: ( 'to' )
        // InternalKactors.g:4764:6: 'to'
        {
        match(input,48,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred148_InternalKactors

    // $ANTLR start synpred152_InternalKactors
    public final void synpred152_InternalKactors_fragment() throws RecognitionException {   
        Token lv_leftLimit_22_0=null;
        Token otherlv_23=null;
        Token otherlv_24=null;
        Token lv_rightLimit_26_0=null;
        Token otherlv_27=null;
        Token otherlv_28=null;
        EObject lv_int0_21_0 = null;

        EObject lv_int1_25_0 = null;

        EObject lv_body_29_0 = null;


        // InternalKactors.g:4722:3: ( ( ( (lv_int0_21_0= ruleNumber ) ) ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )? ( ( 'to' )=>otherlv_24= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) ) ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )? otherlv_28= '->' ( (lv_body_29_0= ruleStatementList ) ) ) )
        // InternalKactors.g:4722:3: ( ( (lv_int0_21_0= ruleNumber ) ) ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )? ( ( 'to' )=>otherlv_24= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) ) ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )? otherlv_28= '->' ( (lv_body_29_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:4722:3: ( ( (lv_int0_21_0= ruleNumber ) ) ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )? ( ( 'to' )=>otherlv_24= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) ) ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )? otherlv_28= '->' ( (lv_body_29_0= ruleStatementList ) ) )
        // InternalKactors.g:4723:4: ( (lv_int0_21_0= ruleNumber ) ) ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )? ( ( 'to' )=>otherlv_24= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) ) ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )? otherlv_28= '->' ( (lv_body_29_0= ruleStatementList ) )
        {
        // InternalKactors.g:4723:4: ( (lv_int0_21_0= ruleNumber ) )
        // InternalKactors.g:4724:5: (lv_int0_21_0= ruleNumber )
        {
        // InternalKactors.g:4724:5: (lv_int0_21_0= ruleNumber )
        // InternalKactors.g:4725:6: lv_int0_21_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getInt0NumberParserRuleCall_7_0_0());
          					
        }
        pushFollow(FOLLOW_32);
        lv_int0_21_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:4742:4: ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )?
        int alt118=3;
        int LA118_0 = input.LA(1);

        if ( (LA118_0==46) ) {
            alt118=1;
        }
        else if ( (LA118_0==47) ) {
            alt118=2;
        }
        switch (alt118) {
            case 1 :
                // InternalKactors.g:4743:5: ( (lv_leftLimit_22_0= 'inclusive' ) )
                {
                // InternalKactors.g:4743:5: ( (lv_leftLimit_22_0= 'inclusive' ) )
                // InternalKactors.g:4744:6: (lv_leftLimit_22_0= 'inclusive' )
                {
                // InternalKactors.g:4744:6: (lv_leftLimit_22_0= 'inclusive' )
                // InternalKactors.g:4745:7: lv_leftLimit_22_0= 'inclusive'
                {
                lv_leftLimit_22_0=(Token)match(input,46,FOLLOW_33); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:4758:5: otherlv_23= 'exclusive'
                {
                otherlv_23=(Token)match(input,47,FOLLOW_33); if (state.failed) return ;

                }
                break;

        }

        // InternalKactors.g:4763:4: ( ( 'to' )=>otherlv_24= 'to' )
        // InternalKactors.g:4764:5: ( 'to' )=>otherlv_24= 'to'
        {
        otherlv_24=(Token)match(input,48,FOLLOW_34); if (state.failed) return ;

        }

        // InternalKactors.g:4770:4: ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) )
        // InternalKactors.g:4771:5: ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber )
        {
        // InternalKactors.g:4775:5: (lv_int1_25_0= ruleNumber )
        // InternalKactors.g:4776:6: lv_int1_25_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getInt1NumberParserRuleCall_7_3_0());
          					
        }
        pushFollow(FOLLOW_64);
        lv_int1_25_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:4793:4: ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )?
        int alt119=3;
        int LA119_0 = input.LA(1);

        if ( (LA119_0==46) ) {
            alt119=1;
        }
        else if ( (LA119_0==47) ) {
            alt119=2;
        }
        switch (alt119) {
            case 1 :
                // InternalKactors.g:4794:5: ( (lv_rightLimit_26_0= 'inclusive' ) )
                {
                // InternalKactors.g:4794:5: ( (lv_rightLimit_26_0= 'inclusive' ) )
                // InternalKactors.g:4795:6: (lv_rightLimit_26_0= 'inclusive' )
                {
                // InternalKactors.g:4795:6: (lv_rightLimit_26_0= 'inclusive' )
                // InternalKactors.g:4796:7: lv_rightLimit_26_0= 'inclusive'
                {
                lv_rightLimit_26_0=(Token)match(input,46,FOLLOW_63); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:4809:5: otherlv_27= 'exclusive'
                {
                otherlv_27=(Token)match(input,47,FOLLOW_63); if (state.failed) return ;

                }
                break;

        }

        otherlv_28=(Token)match(input,69,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:4818:4: ( (lv_body_29_0= ruleStatementList ) )
        // InternalKactors.g:4819:5: (lv_body_29_0= ruleStatementList )
        {
        // InternalKactors.g:4819:5: (lv_body_29_0= ruleStatementList )
        // InternalKactors.g:4820:6: lv_body_29_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_7_6_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_29_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred152_InternalKactors

    // $ANTLR start synpred154_InternalKactors
    public final void synpred154_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_35=null;
        EObject lv_quantity_34_0 = null;

        EObject lv_body_36_0 = null;


        // InternalKactors.g:4888:3: ( ( ( (lv_quantity_34_0= ruleQuantity ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) )
        // InternalKactors.g:4888:3: ( ( (lv_quantity_34_0= ruleQuantity ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:4888:3: ( ( (lv_quantity_34_0= ruleQuantity ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
        // InternalKactors.g:4889:4: ( (lv_quantity_34_0= ruleQuantity ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) )
        {
        // InternalKactors.g:4889:4: ( (lv_quantity_34_0= ruleQuantity ) )
        // InternalKactors.g:4890:5: (lv_quantity_34_0= ruleQuantity )
        {
        // InternalKactors.g:4890:5: (lv_quantity_34_0= ruleQuantity )
        // InternalKactors.g:4891:6: lv_quantity_34_0= ruleQuantity
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getQuantityQuantityParserRuleCall_9_0_0());
          					
        }
        pushFollow(FOLLOW_63);
        lv_quantity_34_0=ruleQuantity();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_35=(Token)match(input,69,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:4912:4: ( (lv_body_36_0= ruleStatementList ) )
        // InternalKactors.g:4913:5: (lv_body_36_0= ruleStatementList )
        {
        // InternalKactors.g:4913:5: (lv_body_36_0= ruleStatementList )
        // InternalKactors.g:4914:6: lv_body_36_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_9_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_36_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred154_InternalKactors

    // $ANTLR start synpred155_InternalKactors
    public final void synpred155_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_38=null;
        EObject lv_date_37_0 = null;

        EObject lv_body_39_0 = null;


        // InternalKactors.g:4933:3: ( ( ( (lv_date_37_0= ruleDate ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) )
        // InternalKactors.g:4933:3: ( ( (lv_date_37_0= ruleDate ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:4933:3: ( ( (lv_date_37_0= ruleDate ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
        // InternalKactors.g:4934:4: ( (lv_date_37_0= ruleDate ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) )
        {
        // InternalKactors.g:4934:4: ( (lv_date_37_0= ruleDate ) )
        // InternalKactors.g:4935:5: (lv_date_37_0= ruleDate )
        {
        // InternalKactors.g:4935:5: (lv_date_37_0= ruleDate )
        // InternalKactors.g:4936:6: lv_date_37_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getDateDateParserRuleCall_10_0_0());
          					
        }
        pushFollow(FOLLOW_63);
        lv_date_37_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_38=(Token)match(input,69,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:4957:4: ( (lv_body_39_0= ruleStatementList ) )
        // InternalKactors.g:4958:5: (lv_body_39_0= ruleStatementList )
        {
        // InternalKactors.g:4958:5: (lv_body_39_0= ruleStatementList )
        // InternalKactors.g:4959:6: lv_body_39_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_10_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_39_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred155_InternalKactors

    // $ANTLR start synpred161_InternalKactors
    public final void synpred161_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5182:4: ( ( RULE_INT ) )
        // InternalKactors.g:5182:5: ( RULE_INT )
        {
        // InternalKactors.g:5182:5: ( RULE_INT )
        // InternalKactors.g:5183:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred161_InternalKactors

    // $ANTLR start synpred162_InternalKactors
    public final void synpred162_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5204:4: ( ( 'l' ) )
        // InternalKactors.g:5204:5: ( 'l' )
        {
        // InternalKactors.g:5204:5: ( 'l' )
        // InternalKactors.g:5205:5: 'l'
        {
        match(input,72,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred162_InternalKactors

    // $ANTLR start synpred163_InternalKactors
    public final void synpred163_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5222:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5222:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5222:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKactors.g:5223:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKactors.g:5223:5: ( ( '.' ) )
        // InternalKactors.g:5224:6: ( '.' )
        {
        // InternalKactors.g:5224:6: ( '.' )
        // InternalKactors.g:5225:7: '.'
        {
        match(input,56,FOLLOW_9); if (state.failed) return ;

        }


        }

        // InternalKactors.g:5228:5: ( ( RULE_INT ) )
        // InternalKactors.g:5229:6: ( RULE_INT )
        {
        // InternalKactors.g:5229:6: ( RULE_INT )
        // InternalKactors.g:5230:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred163_InternalKactors

    // $ANTLR start synpred167_InternalKactors
    public final void synpred167_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5271:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5271:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5271:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKactors.g:5272:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKactors.g:5272:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKactors.g:5273:6: ( ( 'e' | 'E' ) )
        {
        // InternalKactors.g:5273:6: ( ( 'e' | 'E' ) )
        // InternalKactors.g:5274:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=73 && input.LA(1)<=74) ) {
            input.consume();
            state.errorRecovery=false;state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            throw mse;
        }


        }


        }

        // InternalKactors.g:5281:5: ( '+' | ( ( '-' ) ) )?
        int alt120=3;
        int LA120_0 = input.LA(1);

        if ( (LA120_0==70) ) {
            alt120=1;
        }
        else if ( (LA120_0==71) ) {
            alt120=2;
        }
        switch (alt120) {
            case 1 :
                // InternalKactors.g:5282:6: '+'
                {
                match(input,70,FOLLOW_9); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:5284:6: ( ( '-' ) )
                {
                // InternalKactors.g:5284:6: ( ( '-' ) )
                // InternalKactors.g:5285:7: ( '-' )
                {
                // InternalKactors.g:5285:7: ( '-' )
                // InternalKactors.g:5286:8: '-'
                {
                match(input,71,FOLLOW_9); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKactors.g:5290:5: ( ( RULE_INT ) )
        // InternalKactors.g:5291:6: ( RULE_INT )
        {
        // InternalKactors.g:5291:6: ( RULE_INT )
        // InternalKactors.g:5292:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred167_InternalKactors

    // $ANTLR start synpred184_InternalKactors
    public final void synpred184_InternalKactors_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKactors.g:5726:4: (kw= '-' )
        // InternalKactors.g:5726:4: kw= '-'
        {
        kw=(Token)match(input,71,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred184_InternalKactors

    // $ANTLR start synpred185_InternalKactors
    public final void synpred185_InternalKactors_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKactors.g:5733:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKactors.g:5733:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred185_InternalKactors

    // Delegated rules

    public final boolean synpred132_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred132_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred135_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred135_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred163_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred163_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred119_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred119_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred113_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred113_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred144_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred144_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred53_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred53_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred161_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred161_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred126_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred126_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred117_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred117_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred154_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred154_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred148_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred148_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred120_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred120_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred185_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred185_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred155_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred155_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred136_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred136_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred143_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred143_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred133_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred133_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred167_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred167_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred152_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred152_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred118_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred118_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred184_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred184_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred131_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred131_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred125_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred125_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred162_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred162_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred109_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred109_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred106_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred106_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred80_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred80_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred137_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred137_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA9 dfa9 = new DFA9(this);
    protected DFA17 dfa17 = new DFA17(this);
    protected DFA23 dfa23 = new DFA23(this);
    protected DFA24 dfa24 = new DFA24(this);
    protected DFA34 dfa34 = new DFA34(this);
    protected DFA36 dfa36 = new DFA36(this);
    protected DFA45 dfa45 = new DFA45(this);
    protected DFA55 dfa55 = new DFA55(this);
    protected DFA58 dfa58 = new DFA58(this);
    protected DFA70 dfa70 = new DFA70(this);
    protected DFA73 dfa73 = new DFA73(this);
    static final String dfa_1s = "\13\uffff";
    static final String dfa_2s = "\1\1\12\uffff";
    static final String dfa_3s = "\1\12\12\uffff";
    static final String dfa_4s = "\1\41\12\uffff";
    static final String dfa_5s = "\1\uffff\1\12\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11";
    static final String dfa_6s = "\1\0\12\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\14\uffff\1\2\1\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 205:6: ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA9_0 = input.LA(1);

                         
                        int index9_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA9_0==EOF||LA9_0==RULE_ANNOTATION_ID||LA9_0==33) ) {s = 1;}

                        else if ( LA9_0 == 23 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {s = 2;}

                        else if ( LA9_0 == 25 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {s = 3;}

                        else if ( LA9_0 == 26 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {s = 4;}

                        else if ( LA9_0 == 27 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {s = 5;}

                        else if ( LA9_0 == 28 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {s = 6;}

                        else if ( LA9_0 == 29 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {s = 7;}

                        else if ( LA9_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {s = 8;}

                        else if ( LA9_0 == 31 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {s = 9;}

                        else if ( LA9_0 == 32 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {s = 10;}

                         
                        input.seek(index9_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 9, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\15\uffff";
    static final String dfa_9s = "\3\uffff\1\13\10\uffff\1\13";
    static final String dfa_10s = "\1\4\2\uffff\1\4\6\uffff\1\4\1\uffff\1\4";
    static final String dfa_11s = "\1\107\2\uffff\1\107\6\uffff\1\4\1\uffff\1\107";
    static final String dfa_12s = "\1\uffff\1\1\1\2\1\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\uffff\1\3\1\uffff";
    static final String dfa_13s = "\15\uffff}>";
    static final String[] dfa_14s = {
            "\1\3\1\uffff\1\2\1\1\1\7\1\10\1\uffff\1\2\27\uffff\1\5\3\uffff\1\4\2\uffff\1\6\1\uffff\2\2\6\uffff\1\11\21\uffff\2\2",
            "",
            "",
            "\1\13\1\uffff\4\13\1\uffff\1\13\14\uffff\1\13\11\uffff\1\4\2\13\2\uffff\1\13\2\uffff\4\13\6\uffff\1\13\3\uffff\1\12\15\uffff\2\13",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\14",
            "",
            "\1\13\1\uffff\4\13\1\uffff\1\13\14\uffff\1\13\11\uffff\1\4\2\13\2\uffff\1\13\2\uffff\4\13\6\uffff\1\13\3\uffff\1\12\15\uffff\2\13"
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "957:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )";
        }
    }
    static final String dfa_15s = "\6\uffff";
    static final String dfa_16s = "\1\uffff\1\2\3\uffff\1\2";
    static final String dfa_17s = "\2\4\1\uffff\1\4\1\uffff\1\4";
    static final String dfa_18s = "\1\16\1\107\1\uffff\1\16\1\uffff\1\107";
    static final String dfa_19s = "\2\uffff\1\1\1\uffff\1\2\1\uffff";
    static final String dfa_20s = "\6\uffff}>";
    static final String[] dfa_21s = {
            "\1\1\11\uffff\1\2",
            "\1\2\1\uffff\4\2\1\uffff\1\2\14\uffff\1\2\12\uffff\2\2\1\uffff\1\4\1\2\1\uffff\5\2\6\uffff\1\2\2\uffff\1\2\1\3\15\uffff\2\2",
            "",
            "\1\5\11\uffff\1\2",
            "",
            "\1\2\1\uffff\4\2\1\uffff\1\2\14\uffff\1\2\12\uffff\2\2\1\uffff\1\4\1\2\1\uffff\5\2\6\uffff\1\2\2\uffff\1\2\1\3\15\uffff\2\2"
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final char[] dfa_18 = DFA.unpackEncodedStringToUnsignedChars(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[][] dfa_21 = unpackEncodedStringArray(dfa_21s);

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "1314:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )";
        }
    }

    class DFA24 extends DFA {

        public DFA24(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 24;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "1343:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )";
        }
    }
    static final String dfa_22s = "\26\uffff";
    static final String dfa_23s = "\4\uffff\1\21\7\uffff\1\21\5\uffff\1\21\2\uffff\1\21";
    static final String dfa_24s = "\1\4\1\uffff\2\13\1\42\7\uffff\1\42\3\13\2\uffff\1\42\2\13\1\42";
    static final String dfa_25s = "\1\107\1\uffff\2\13\1\112\7\uffff\1\112\1\13\2\107\2\uffff\1\112\2\13\1\60";
    static final String dfa_26s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\4\uffff\1\2\1\3\4\uffff";
    static final String dfa_27s = "\26\uffff}>";
    static final String[] dfa_28s = {
            "\1\10\1\uffff\1\6\1\uffff\1\7\2\uffff\1\4\32\uffff\1\11\5\uffff\2\1\3\uffff\1\5\1\12\1\13\6\uffff\5\11\7\uffff\1\2\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\21\13\uffff\3\20\7\uffff\1\15\17\uffff\1\14\1\16\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21\13\uffff\3\20\7\uffff\1\15\20\uffff\1\16\1\17",
            "\1\22",
            "\1\25\72\uffff\1\23\1\24",
            "\1\25\72\uffff\1\23\1\24",
            "",
            "",
            "\1\21\13\uffff\3\20\30\uffff\1\16\1\17",
            "\1\25",
            "\1\25",
            "\1\21\13\uffff\3\20"
    };

    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final char[] dfa_25 = DFA.unpackEncodedStringToUnsignedChars(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final short[][] dfa_28 = unpackEncodedStringArray(dfa_28s);

    class DFA34 extends DFA {

        public DFA34(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 34;
            this.eot = dfa_22;
            this.eof = dfa_23;
            this.min = dfa_24;
            this.max = dfa_25;
            this.accept = dfa_26;
            this.special = dfa_27;
            this.transition = dfa_28;
        }
        public String getDescription() {
            return "1644:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )";
        }
    }
    static final String dfa_29s = "\2\uffff\1\3\2\uffff\1\3";
    static final String dfa_30s = "\1\4\1\uffff\1\14\1\uffff\1\4\1\14";
    static final String dfa_31s = "\1\107\1\uffff\1\66\1\uffff\1\107\1\66";
    static final String dfa_32s = "\1\uffff\1\1\1\uffff\1\2\2\uffff";
    static final String[] dfa_33s = {
            "\1\1\1\uffff\1\2\1\uffff\2\3\1\uffff\1\3\32\uffff\1\3\1\uffff\1\3\3\uffff\2\3\3\uffff\3\3\6\uffff\5\3\7\uffff\2\3",
            "",
            "\1\1\13\uffff\1\3\34\uffff\1\3\1\4",
            "",
            "\1\1\1\uffff\1\5\1\uffff\2\3\1\uffff\1\3\32\uffff\1\3\1\uffff\1\3\3\uffff\2\3\3\uffff\3\3\6\uffff\5\3\7\uffff\2\3",
            "\1\1\13\uffff\1\3\34\uffff\1\3\1\4"
    };
    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final char[] dfa_30 = DFA.unpackEncodedStringToUnsignedChars(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final short[][] dfa_33 = unpackEncodedStringArray(dfa_33s);

    class DFA36 extends DFA {

        public DFA36(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 36;
            this.eot = dfa_15;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_20;
            this.transition = dfa_33;
        }
        public String getDescription() {
            return "2019:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?";
        }
    }
    static final String dfa_34s = "\32\uffff";
    static final String dfa_35s = "\4\uffff\1\22\10\uffff\2\22\1\23\6\uffff\1\22\2\uffff\1\22";
    static final String dfa_36s = "\1\6\1\uffff\2\13\1\30\10\uffff\2\30\1\4\2\13\4\uffff\1\30\2\13\1\30";
    static final String dfa_37s = "\1\107\1\uffff\2\13\1\115\10\uffff\2\112\1\116\2\107\4\uffff\1\112\2\13\1\70";
    static final String dfa_38s = "\1\uffff\1\1\3\uffff\1\3\1\4\1\5\1\7\1\12\1\13\1\14\1\15\5\uffff\1\2\1\10\1\11\1\6\4\uffff";
    static final String dfa_39s = "\32\uffff}>";
    static final String[] dfa_40s = {
            "\1\5\1\uffff\1\6\1\11\1\uffff\1\4\32\uffff\1\7\1\uffff\1\14\3\uffff\2\1\3\uffff\1\10\1\12\1\13\6\uffff\5\7\7\uffff\1\2\1\3",
            "",
            "\1\15",
            "\1\15",
            "\1\22\25\uffff\3\25\4\uffff\2\22\1\23\1\17\16\uffff\1\24\1\16\1\20\1\21\3\24",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\22\25\uffff\3\25\4\uffff\2\22\1\23\1\17\17\uffff\1\16\1\20\1\21",
            "\1\22\25\uffff\3\25\4\uffff\2\22\1\23\1\17\20\uffff\1\20\1\21",
            "\1\23\6\uffff\1\26\1\uffff\2\23\11\uffff\1\23\12\uffff\1\23\17\uffff\1\23\1\uffff\3\23\26\uffff\1\23",
            "\1\31\72\uffff\1\27\1\30",
            "\1\31\72\uffff\1\27\1\30",
            "",
            "",
            "",
            "",
            "\1\22\25\uffff\3\25\4\uffff\2\22\2\23\20\uffff\1\20\1\21",
            "\1\31",
            "\1\31",
            "\1\22\25\uffff\3\25\4\uffff\2\22\2\23"
    };

    static final short[] dfa_34 = DFA.unpackEncodedString(dfa_34s);
    static final short[] dfa_35 = DFA.unpackEncodedString(dfa_35s);
    static final char[] dfa_36 = DFA.unpackEncodedStringToUnsignedChars(dfa_36s);
    static final char[] dfa_37 = DFA.unpackEncodedStringToUnsignedChars(dfa_37s);
    static final short[] dfa_38 = DFA.unpackEncodedString(dfa_38s);
    static final short[] dfa_39 = DFA.unpackEncodedString(dfa_39s);
    static final short[][] dfa_40 = unpackEncodedStringArray(dfa_40s);

    class DFA45 extends DFA {

        public DFA45(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 45;
            this.eot = dfa_34;
            this.eof = dfa_35;
            this.min = dfa_36;
            this.max = dfa_37;
            this.accept = dfa_38;
            this.special = dfa_39;
            this.transition = dfa_40;
        }
        public String getDescription() {
            return "2264:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )";
        }
    }
    static final String dfa_41s = "\3\uffff\1\13\2\uffff\2\13\7\uffff\1\13\2\uffff\1\13\3\uffff";
    static final String dfa_42s = "\1\6\2\13\1\4\2\uffff\2\4\3\13\1\uffff\1\13\2\uffff\1\4\2\13\2\4\1\13\1\0";
    static final String dfa_43s = "\1\107\2\13\1\115\2\uffff\2\112\1\13\2\107\1\uffff\1\13\2\uffff\1\112\2\13\1\107\1\112\1\13\1\0";
    static final String dfa_44s = "\4\uffff\1\3\1\5\5\uffff\1\1\1\uffff\1\2\1\4\7\uffff";
    static final String dfa_45s = "\25\uffff\1\0}>";
    static final String[] dfa_46s = {
            "\1\4\4\uffff\1\3\40\uffff\2\5\30\uffff\1\1\1\2",
            "\1\6",
            "\1\6",
            "\1\13\1\uffff\4\13\1\uffff\1\13\14\uffff\1\13\12\uffff\2\13\2\uffff\1\13\2\uffff\4\13\2\uffff\1\15\3\uffff\1\13\3\uffff\1\10\14\uffff\2\13\1\14\1\7\1\11\1\12\3\16",
            "",
            "",
            "\1\13\1\uffff\4\13\1\uffff\1\13\14\uffff\1\13\12\uffff\2\13\2\uffff\1\13\2\uffff\4\13\2\uffff\1\15\3\uffff\1\13\3\uffff\1\10\14\uffff\3\13\1\7\1\11\1\12",
            "\1\13\1\uffff\4\13\1\uffff\1\13\14\uffff\1\13\12\uffff\2\13\2\uffff\1\13\2\uffff\4\13\2\uffff\1\15\3\uffff\1\13\3\uffff\1\10\14\uffff\3\13\1\uffff\1\11\1\12",
            "\1\17",
            "\1\22\72\uffff\1\20\1\21",
            "\1\22\72\uffff\1\20\1\21",
            "",
            "\1\23",
            "",
            "",
            "\1\13\1\uffff\4\13\1\uffff\1\13\14\uffff\1\13\12\uffff\2\13\2\uffff\1\13\2\uffff\4\13\2\uffff\1\15\3\uffff\1\13\20\uffff\3\13\1\uffff\1\11\1\12",
            "\1\22",
            "\1\22",
            "\1\13\1\uffff\4\13\1\uffff\1\13\14\uffff\1\13\12\uffff\2\13\2\uffff\1\13\2\uffff\4\13\2\uffff\1\15\3\uffff\1\13\20\uffff\3\13",
            "\1\13\1\uffff\4\13\1\uffff\1\13\27\uffff\2\13\2\uffff\1\13\2\uffff\1\13\1\uffff\2\13\2\uffff\1\13\3\uffff\1\13\3\uffff\1\13\15\uffff\1\13\1\24\3\13",
            "\1\25",
            "\1\uffff"
    };
    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final char[] dfa_42 = DFA.unpackEncodedStringToUnsignedChars(dfa_42s);
    static final char[] dfa_43 = DFA.unpackEncodedStringToUnsignedChars(dfa_43s);
    static final short[] dfa_44 = DFA.unpackEncodedString(dfa_44s);
    static final short[] dfa_45 = DFA.unpackEncodedString(dfa_45s);
    static final short[][] dfa_46 = unpackEncodedStringArray(dfa_46s);

    class DFA55 extends DFA {

        public DFA55(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 55;
            this.eot = dfa_22;
            this.eof = dfa_41;
            this.min = dfa_42;
            this.max = dfa_43;
            this.accept = dfa_44;
            this.special = dfa_45;
            this.transition = dfa_46;
        }
        public String getDescription() {
            return "3129:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA55_21 = input.LA(1);

                         
                        int index55_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred106_InternalKactors()) ) {s = 11;}

                        else if ( (synpred109_InternalKactors()) ) {s = 14;}

                         
                        input.seek(index55_21);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 55, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_47s = "\37\uffff";
    static final String dfa_48s = "\1\2\36\uffff";
    static final String dfa_49s = "\1\4\1\0\35\uffff";
    static final String dfa_50s = "\1\107\1\0\35\uffff";
    static final String dfa_51s = "\2\uffff\1\2\33\uffff\1\1";
    static final String dfa_52s = "\1\uffff\1\0\35\uffff}>";
    static final String[] dfa_53s = {
            "\1\2\1\uffff\1\2\1\uffff\4\2\1\uffff\1\2\1\uffff\2\2\7\uffff\1\2\10\uffff\2\2\1\1\1\2\3\uffff\1\2\3\uffff\2\2\3\uffff\3\2\13\uffff\6\2\1\uffff\2\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_47 = DFA.unpackEncodedString(dfa_47s);
    static final short[] dfa_48 = DFA.unpackEncodedString(dfa_48s);
    static final char[] dfa_49 = DFA.unpackEncodedStringToUnsignedChars(dfa_49s);
    static final char[] dfa_50 = DFA.unpackEncodedStringToUnsignedChars(dfa_50s);
    static final short[] dfa_51 = DFA.unpackEncodedString(dfa_51s);
    static final short[] dfa_52 = DFA.unpackEncodedString(dfa_52s);
    static final short[][] dfa_53 = unpackEncodedStringArray(dfa_53s);

    class DFA58 extends DFA {

        public DFA58(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 58;
            this.eot = dfa_47;
            this.eof = dfa_48;
            this.min = dfa_49;
            this.max = dfa_50;
            this.accept = dfa_51;
            this.special = dfa_52;
            this.transition = dfa_53;
        }
        public String getDescription() {
            return "3349:5: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA58_1 = input.LA(1);

                         
                        int index58_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred113_InternalKactors()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index58_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 58, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_54s = "\30\uffff";
    static final String dfa_55s = "\1\4\2\0\25\uffff";
    static final String dfa_56s = "\1\107\2\0\25\uffff";
    static final String dfa_57s = "\3\uffff\1\1\4\uffff\1\3\15\uffff\1\2\1\4";
    static final String dfa_58s = "\1\uffff\1\0\1\1\25\uffff}>";
    static final String[] dfa_59s = {
            "\1\1\1\uffff\1\10\1\uffff\2\10\1\uffff\1\10\1\uffff\1\10\1\uffff\1\3\1\10\22\uffff\1\2\4\uffff\1\10\3\uffff\2\10\3\uffff\3\10\14\uffff\1\3\1\uffff\3\3\1\uffff\2\10",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_54 = DFA.unpackEncodedString(dfa_54s);
    static final char[] dfa_55 = DFA.unpackEncodedStringToUnsignedChars(dfa_55s);
    static final char[] dfa_56 = DFA.unpackEncodedStringToUnsignedChars(dfa_56s);
    static final short[] dfa_57 = DFA.unpackEncodedString(dfa_57s);
    static final short[] dfa_58 = DFA.unpackEncodedString(dfa_58s);
    static final short[][] dfa_59 = unpackEncodedStringArray(dfa_59s);

    class DFA70 extends DFA {

        public DFA70(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 70;
            this.eot = dfa_54;
            this.eof = dfa_54;
            this.min = dfa_55;
            this.max = dfa_56;
            this.accept = dfa_57;
            this.special = dfa_58;
            this.transition = dfa_59;
        }
        public String getDescription() {
            return "4274:2: ( ( (lv_statement_0_0= ruleStatement ) ) | (otherlv_1= '(' ( (lv_statements_2_0= ruleStatementList ) ) otherlv_3= ')' ) | ( (lv_match_4_0= ruleMatch ) ) | (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA70_1 = input.LA(1);

                         
                        int index70_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred135_InternalKactors()) ) {s = 3;}

                        else if ( (synpred137_InternalKactors()) ) {s = 8;}

                         
                        input.seek(index70_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA70_2 = input.LA(1);

                         
                        int index70_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred135_InternalKactors()) ) {s = 3;}

                        else if ( (synpred136_InternalKactors()) ) {s = 22;}

                        else if ( (synpred137_InternalKactors()) ) {s = 8;}

                        else if ( (true) ) {s = 23;}

                         
                        input.seek(index70_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 70, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_60s = "\25\uffff";
    static final String dfa_61s = "\1\4\4\uffff\4\0\14\uffff";
    static final String dfa_62s = "\1\107\4\uffff\4\0\14\uffff";
    static final String dfa_63s = "\1\uffff\1\1\1\2\1\3\1\4\4\uffff\1\5\1\uffff\1\7\1\11\1\14\1\15\1\16\1\17\1\10\1\12\1\13\1\6";
    static final String dfa_64s = "\5\uffff\1\0\1\1\1\2\1\3\14\uffff}>";
    static final String[] dfa_65s = {
            "\1\1\1\uffff\1\10\1\uffff\1\4\1\15\1\uffff\1\7\1\uffff\1\2\2\uffff\1\3\22\uffff\1\13\4\uffff\1\20\3\uffff\2\11\3\uffff\1\14\1\16\1\17\22\uffff\1\5\1\6",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_60 = DFA.unpackEncodedString(dfa_60s);
    static final char[] dfa_61 = DFA.unpackEncodedStringToUnsignedChars(dfa_61s);
    static final char[] dfa_62 = DFA.unpackEncodedStringToUnsignedChars(dfa_62s);
    static final short[] dfa_63 = DFA.unpackEncodedString(dfa_63s);
    static final short[] dfa_64 = DFA.unpackEncodedString(dfa_64s);
    static final short[][] dfa_65 = unpackEncodedStringArray(dfa_65s);

    class DFA73 extends DFA {

        public DFA73(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 73;
            this.eot = dfa_60;
            this.eof = dfa_60;
            this.min = dfa_61;
            this.max = dfa_62;
            this.accept = dfa_63;
            this.special = dfa_64;
            this.transition = dfa_65;
        }
        public String getDescription() {
            return "4411:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( (lv_type_3_0= RULE_CAMELCASE_ID ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_regexp_6_0= RULE_REGEXP ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_observable_9_0= RULE_OBSERVABLE ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_literal_12_0= ruleLiteral ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_text_15_0= RULE_STRING ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_arguments_18_0= ruleArgumentDeclaration ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_int0_21_0= ruleNumber ) ) ( ( (lv_leftLimit_22_0= 'inclusive' ) ) | otherlv_23= 'exclusive' )? ( ( 'to' )=>otherlv_24= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_25_0= ruleNumber ) ) ( ( (lv_rightLimit_26_0= 'inclusive' ) ) | otherlv_27= 'exclusive' )? otherlv_28= '->' ( (lv_body_29_0= ruleStatementList ) ) ) | (otherlv_30= 'in' ( (lv_set_31_0= ruleList ) ) otherlv_32= '->' ( (lv_body_33_0= ruleStatementList ) ) ) | ( ( (lv_quantity_34_0= ruleQuantity ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_date_37_0= ruleDate ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_expr_40_0= RULE_EXPR ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_nodata_43_0= 'unknown' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_star_46_0= '*' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_anything_49_0= '#' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA73_5 = input.LA(1);

                         
                        int index73_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred143_InternalKactors()) ) {s = 9;}

                        else if ( (synpred152_InternalKactors()) ) {s = 17;}

                        else if ( (synpred154_InternalKactors()) ) {s = 18;}

                         
                        input.seek(index73_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA73_6 = input.LA(1);

                         
                        int index73_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred143_InternalKactors()) ) {s = 9;}

                        else if ( (synpred152_InternalKactors()) ) {s = 17;}

                        else if ( (synpred154_InternalKactors()) ) {s = 18;}

                         
                        input.seek(index73_6);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA73_7 = input.LA(1);

                         
                        int index73_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred143_InternalKactors()) ) {s = 9;}

                        else if ( (synpred152_InternalKactors()) ) {s = 17;}

                        else if ( (synpred154_InternalKactors()) ) {s = 18;}

                        else if ( (synpred155_InternalKactors()) ) {s = 19;}

                         
                        input.seek(index73_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA73_8 = input.LA(1);

                         
                        int index73_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred143_InternalKactors()) ) {s = 9;}

                        else if ( (synpred144_InternalKactors()) ) {s = 20;}

                         
                        input.seek(index73_8);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 73, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000200000402L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x00000001FE800002L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x00000001FF800002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000070L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00000001FE800042L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000200000400L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000C00000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000800008010L,0x000000000000001DL});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000001000000010L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000001001000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0010348800000BD0L,0x00000000000000C0L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000006000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0010349800000BD0L,0x00000000000000C0L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000004810L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000010400000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000004010L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x7C0E384000000950L,0x00000000000000C0L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000080001000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x7C0E304000000950L,0x00000000000000C0L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0001C00000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000800L,0x00000000000000C0L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000C00000000002L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x7C2E314000000B50L,0x00000000000000C0L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x7C0E314000000B50L,0x00000000000000C0L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0040000000000002L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0180000000000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0088000800006010L,0x0000000000004000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0088001800006010L,0x0000000000004000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0088000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000800006010L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0080000000000002L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000800008012L,0x000000000000001DL});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x8000000C00000002L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x8000000400000002L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x000E31080001AB50L,0x00000000000000DDL});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000001800008010L,0x000000000000001DL});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000800000010L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x000E310800012B50L,0x00000000000000C0L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x000E311800012B50L,0x00000000000000C0L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000C00000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0100000000000002L,0x0000000000000700L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0100000000000002L,0x0000000000000600L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000600L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000000L,0x0000000000003880L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0180000000000002L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0100000000004012L,0x0000000000000080L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000004012L,0x0000000000000080L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000004012L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000000042L});

}

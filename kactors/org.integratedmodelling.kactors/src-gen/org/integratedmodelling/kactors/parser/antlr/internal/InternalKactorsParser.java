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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_OBSERVABLE", "RULE_LOWERCASE_ID", "RULE_ID", "RULE_STRING", "RULE_EMBEDDEDTEXT", "RULE_EXPR", "RULE_CAMELCASE_ID", "RULE_REGEXP", "RULE_INT", "RULE_SEPARATOR", "RULE_ANNOTATION_ID", "RULE_ARGVALUE", "RULE_UPPERCASE_ID", "RULE_UPPERCASE_PATH", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'app'", "'job'", "'testcase'", "'user'", "'trait'", "'library'", "'behavior'", "'behaviour'", "'import'", "','", "'worldview'", "'observable'", "'label'", "'description'", "'permissions'", "'author'", "'version'", "'created'", "'modified'", "'action'", "':'", "'('", "')'", "'if'", "'else'", "'while'", "'do'", "'for'", "'in'", "'->'", "'true'", "'false'", "'inclusive'", "'exclusive'", "'to'", "'unknown'", "'*'", "'#'", "'urn:klab:'", "'&'", "'='", "'{'", "'}'", "'{{'", "'}}'", "'|'", "'/'", "'.'", "'=?'", "'@'", "'>'", "'<'", "'!='", "'<='", "'>='", "'+'", "'-'", "'l'", "'e'", "'E'", "'AD'", "'CE'", "'BC'", "'^'"
    };
    public static final int T__50=50;
    public static final int RULE_EMBEDDEDTEXT=8;
    public static final int RULE_UPPERCASE_ID=16;
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
    public static final int RULE_ID=6;
    public static final int RULE_CAMELCASE_ID=10;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=12;
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
    public static final int RULE_ARGVALUE=15;
    public static final int RULE_STRING=7;
    public static final int RULE_SEPARATOR=13;
    public static final int RULE_SL_COMMENT=19;
    public static final int RULE_OBSERVABLE=4;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__77=77;
    public static final int T__34=34;
    public static final int T__78=78;
    public static final int T__35=35;
    public static final int T__79=79;
    public static final int T__36=36;
    public static final int T__73=73;
    public static final int RULE_REGEXP=11;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__74=74;
    public static final int T__31=31;
    public static final int T__75=75;
    public static final int T__32=32;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=20;
    public static final int RULE_ANY_OTHER=21;
    public static final int RULE_ANNOTATION_ID=14;
    public static final int T__48=48;
    public static final int RULE_LOWERCASE_ID=5;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int RULE_EXPR=9;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__84=84;
    public static final int T__41=41;
    public static final int T__85=85;
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
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
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
                case 41:
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

                if ( (LA2_0==RULE_ANNOTATION_ID||LA2_0==41) ) {
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
    // InternalKactors.g:153:1: rulePreamble returns [EObject current=null] : ( () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' ) ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) ;
    public final EObject rulePreamble() throws RecognitionException {
        EObject current = null;

        Token lv_app_1_1=null;
        Token lv_app_1_2=null;
        Token lv_app_1_3=null;
        Token lv_user_2_0=null;
        Token lv_library_3_1=null;
        Token lv_library_3_2=null;
        Token lv_behavior_4_1=null;
        Token lv_behavior_4_2=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token lv_observable_14_0=null;
        Token otherlv_16=null;
        Token lv_label_17_1=null;
        Token lv_label_17_2=null;
        Token lv_label_17_3=null;
        Token otherlv_18=null;
        Token lv_description_19_0=null;
        Token otherlv_20=null;
        Token lv_permissions_21_0=null;
        Token otherlv_22=null;
        Token lv_authors_23_0=null;
        Token otherlv_24=null;
        Token otherlv_26=null;
        Token lv_createcomment_28_0=null;
        Token otherlv_29=null;
        Token lv_modcomment_31_0=null;
        AntlrDatatypeRuleToken lv_name_5_0 = null;

        AntlrDatatypeRuleToken lv_imports_8_0 = null;

        AntlrDatatypeRuleToken lv_imports_10_0 = null;

        AntlrDatatypeRuleToken lv_worldview_12_0 = null;

        EObject lv_observables_15_0 = null;

        AntlrDatatypeRuleToken lv_version_25_0 = null;

        EObject lv_created_27_0 = null;

        EObject lv_modified_30_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getPreambleAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKactors.g:162:2: ( ( () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' ) ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) )
            // InternalKactors.g:163:2: ( () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' ) ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            {
            // InternalKactors.g:163:2: ( () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' ) ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            // InternalKactors.g:164:3: () ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' ) ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
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

            // InternalKactors.g:174:3: ( ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' ) ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( ((LA7_0>=22 && LA7_0<=29)) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalKactors.g:175:4: ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' ) ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) )
                    {
                    // InternalKactors.g:175:4: ( ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' ) ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) )
                    int alt6=4;
                    switch ( input.LA(1) ) {
                    case 22:
                    case 23:
                    case 24:
                        {
                        alt6=1;
                        }
                        break;
                    case 25:
                        {
                        alt6=2;
                        }
                        break;
                    case 26:
                    case 27:
                        {
                        alt6=3;
                        }
                        break;
                    case 28:
                    case 29:
                        {
                        alt6=4;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 0, input);

                        throw nvae;
                    }

                    switch (alt6) {
                        case 1 :
                            // InternalKactors.g:176:5: ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' ) ) )
                            {
                            // InternalKactors.g:176:5: ( ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' ) ) )
                            // InternalKactors.g:177:6: ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' ) )
                            {
                            // InternalKactors.g:177:6: ( (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' ) )
                            // InternalKactors.g:178:7: (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' )
                            {
                            // InternalKactors.g:178:7: (lv_app_1_1= 'app' | lv_app_1_2= 'job' | lv_app_1_3= 'testcase' )
                            int alt3=3;
                            switch ( input.LA(1) ) {
                            case 22:
                                {
                                alt3=1;
                                }
                                break;
                            case 23:
                                {
                                alt3=2;
                                }
                                break;
                            case 24:
                                {
                                alt3=3;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 3, 0, input);

                                throw nvae;
                            }

                            switch (alt3) {
                                case 1 :
                                    // InternalKactors.g:179:8: lv_app_1_1= 'app'
                                    {
                                    lv_app_1_1=(Token)match(input,22,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_app_1_1, grammarAccess.getPreambleAccess().getAppAppKeyword_1_0_0_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getPreambleRule());
                                      								}
                                      								setWithLastConsumed(current, "app", true, null);
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKactors.g:190:8: lv_app_1_2= 'job'
                                    {
                                    lv_app_1_2=(Token)match(input,23,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_app_1_2, grammarAccess.getPreambleAccess().getAppJobKeyword_1_0_0_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getPreambleRule());
                                      								}
                                      								setWithLastConsumed(current, "app", true, null);
                                      							
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalKactors.g:201:8: lv_app_1_3= 'testcase'
                                    {
                                    lv_app_1_3=(Token)match(input,24,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_app_1_3, grammarAccess.getPreambleAccess().getAppTestcaseKeyword_1_0_0_0_2());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getPreambleRule());
                                      								}
                                      								setWithLastConsumed(current, "app", true, null);
                                      							
                                    }

                                    }
                                    break;

                            }


                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:215:5: ( (lv_user_2_0= 'user' ) )
                            {
                            // InternalKactors.g:215:5: ( (lv_user_2_0= 'user' ) )
                            // InternalKactors.g:216:6: (lv_user_2_0= 'user' )
                            {
                            // InternalKactors.g:216:6: (lv_user_2_0= 'user' )
                            // InternalKactors.g:217:7: lv_user_2_0= 'user'
                            {
                            lv_user_2_0=(Token)match(input,25,FOLLOW_4); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_user_2_0, grammarAccess.getPreambleAccess().getUserUserKeyword_1_0_1_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getPreambleRule());
                              							}
                              							setWithLastConsumed(current, "user", true, "user");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 3 :
                            // InternalKactors.g:230:5: ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) )
                            {
                            // InternalKactors.g:230:5: ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) )
                            // InternalKactors.g:231:6: ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) )
                            {
                            // InternalKactors.g:231:6: ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) )
                            // InternalKactors.g:232:7: (lv_library_3_1= 'trait' | lv_library_3_2= 'library' )
                            {
                            // InternalKactors.g:232:7: (lv_library_3_1= 'trait' | lv_library_3_2= 'library' )
                            int alt4=2;
                            int LA4_0 = input.LA(1);

                            if ( (LA4_0==26) ) {
                                alt4=1;
                            }
                            else if ( (LA4_0==27) ) {
                                alt4=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 4, 0, input);

                                throw nvae;
                            }
                            switch (alt4) {
                                case 1 :
                                    // InternalKactors.g:233:8: lv_library_3_1= 'trait'
                                    {
                                    lv_library_3_1=(Token)match(input,26,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_library_3_1, grammarAccess.getPreambleAccess().getLibraryTraitKeyword_1_0_2_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getPreambleRule());
                                      								}
                                      								setWithLastConsumed(current, "library", true, null);
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKactors.g:244:8: lv_library_3_2= 'library'
                                    {
                                    lv_library_3_2=(Token)match(input,27,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_library_3_2, grammarAccess.getPreambleAccess().getLibraryLibraryKeyword_1_0_2_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getPreambleRule());
                                      								}
                                      								setWithLastConsumed(current, "library", true, null);
                                      							
                                    }

                                    }
                                    break;

                            }


                            }


                            }


                            }
                            break;
                        case 4 :
                            // InternalKactors.g:258:5: ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) )
                            {
                            // InternalKactors.g:258:5: ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) )
                            // InternalKactors.g:259:6: ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) )
                            {
                            // InternalKactors.g:259:6: ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) )
                            // InternalKactors.g:260:7: (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' )
                            {
                            // InternalKactors.g:260:7: (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' )
                            int alt5=2;
                            int LA5_0 = input.LA(1);

                            if ( (LA5_0==28) ) {
                                alt5=1;
                            }
                            else if ( (LA5_0==29) ) {
                                alt5=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 5, 0, input);

                                throw nvae;
                            }
                            switch (alt5) {
                                case 1 :
                                    // InternalKactors.g:261:8: lv_behavior_4_1= 'behavior'
                                    {
                                    lv_behavior_4_1=(Token)match(input,28,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_behavior_4_1, grammarAccess.getPreambleAccess().getBehaviorBehaviorKeyword_1_0_3_0_0());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getPreambleRule());
                                      								}
                                      								setWithLastConsumed(current, "behavior", true, null);
                                      							
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalKactors.g:272:8: lv_behavior_4_2= 'behaviour'
                                    {
                                    lv_behavior_4_2=(Token)match(input,29,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_behavior_4_2, grammarAccess.getPreambleAccess().getBehaviorBehaviourKeyword_1_0_3_0_1());
                                      							
                                    }
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElement(grammarAccess.getPreambleRule());
                                      								}
                                      								setWithLastConsumed(current, "behavior", true, null);
                                      							
                                    }

                                    }
                                    break;

                            }


                            }


                            }


                            }
                            break;

                    }

                    // InternalKactors.g:286:4: ( (lv_name_5_0= rulePathName ) )
                    // InternalKactors.g:287:5: (lv_name_5_0= rulePathName )
                    {
                    // InternalKactors.g:287:5: (lv_name_5_0= rulePathName )
                    // InternalKactors.g:288:6: lv_name_5_0= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPreambleAccess().getNamePathNameParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_5);
                    lv_name_5_0=rulePathName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPreambleRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_5_0,
                      							"org.integratedmodelling.kactors.Kactors.PathName");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalKactors.g:306:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
            // InternalKactors.g:307:4: ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) )
            {
            // InternalKactors.g:307:4: ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) )
            // InternalKactors.g:308:5: ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* )
            {
            getUnorderedGroupHelper().enter(grammarAccess.getPreambleAccess().getUnorderedGroup_2());
            // InternalKactors.g:311:5: ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* )
            // InternalKactors.g:312:6: ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )*
            {
            // InternalKactors.g:312:6: ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )*
            loop14:
            do {
                int alt14=11;
                alt14 = dfa14.predict(input);
                switch (alt14) {
            	case 1 :
            	    // InternalKactors.g:313:4: ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) )
            	    {
            	    // InternalKactors.g:313:4: ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) )
            	    // InternalKactors.g:314:5: {...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
            	    }
            	    // InternalKactors.g:314:105: ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) )
            	    // InternalKactors.g:315:6: ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
            	    // InternalKactors.g:318:9: ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) )
            	    // InternalKactors.g:318:10: {...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:318:19: (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* )
            	    // InternalKactors.g:318:20: otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )*
            	    {
            	    otherlv_7=(Token)match(input,30,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_7, grammarAccess.getPreambleAccess().getImportKeyword_2_0_0());
            	      								
            	    }
            	    // InternalKactors.g:322:9: ( (lv_imports_8_0= rulePathName ) )
            	    // InternalKactors.g:323:10: (lv_imports_8_0= rulePathName )
            	    {
            	    // InternalKactors.g:323:10: (lv_imports_8_0= rulePathName )
            	    // InternalKactors.g:324:11: lv_imports_8_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_imports_8_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											add(
            	      												current,
            	      												"imports",
            	      												lv_imports_8_0,
            	      												"org.integratedmodelling.kactors.Kactors.PathName");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:341:9: (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )*
            	    loop8:
            	    do {
            	        int alt8=2;
            	        int LA8_0 = input.LA(1);

            	        if ( (LA8_0==31) ) {
            	            alt8=1;
            	        }


            	        switch (alt8) {
            	    	case 1 :
            	    	    // InternalKactors.g:342:10: otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) )
            	    	    {
            	    	    otherlv_9=(Token)match(input,31,FOLLOW_4); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      										newLeafNode(otherlv_9, grammarAccess.getPreambleAccess().getCommaKeyword_2_0_2_0());
            	    	      									
            	    	    }
            	    	    // InternalKactors.g:346:10: ( (lv_imports_10_0= rulePathName ) )
            	    	    // InternalKactors.g:347:11: (lv_imports_10_0= rulePathName )
            	    	    {
            	    	    // InternalKactors.g:347:11: (lv_imports_10_0= rulePathName )
            	    	    // InternalKactors.g:348:12: lv_imports_10_0= rulePathName
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      												newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_2_1_0());
            	    	      											
            	    	    }
            	    	    pushFollow(FOLLOW_6);
            	    	    lv_imports_10_0=rulePathName();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      												if (current==null) {
            	    	      													current = createModelElementForParent(grammarAccess.getPreambleRule());
            	    	      												}
            	    	      												add(
            	    	      													current,
            	    	      													"imports",
            	    	      													lv_imports_10_0,
            	    	      													"org.integratedmodelling.kactors.Kactors.PathName");
            	    	      												afterParserOrEnumRuleCall();
            	    	      											
            	    	    }

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop8;
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
            	    // InternalKactors.g:372:4: ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:372:4: ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) )
            	    // InternalKactors.g:373:5: {...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
            	    }
            	    // InternalKactors.g:373:105: ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) )
            	    // InternalKactors.g:374:6: ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
            	    // InternalKactors.g:377:9: ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) )
            	    // InternalKactors.g:377:10: {...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:377:19: (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) )
            	    // InternalKactors.g:377:20: otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) )
            	    {
            	    otherlv_11=(Token)match(input,32,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_11, grammarAccess.getPreambleAccess().getWorldviewKeyword_2_1_0());
            	      								
            	    }
            	    // InternalKactors.g:381:9: ( (lv_worldview_12_0= rulePathName ) )
            	    // InternalKactors.g:382:10: (lv_worldview_12_0= rulePathName )
            	    {
            	    // InternalKactors.g:382:10: (lv_worldview_12_0= rulePathName )
            	    // InternalKactors.g:383:11: lv_worldview_12_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_2_1_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_worldview_12_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"worldview",
            	      												lv_worldview_12_0,
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
            	    // InternalKactors.g:406:4: ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:406:4: ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) )
            	    // InternalKactors.g:407:5: {...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
            	    }
            	    // InternalKactors.g:407:105: ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) )
            	    // InternalKactors.g:408:6: ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
            	    // InternalKactors.g:411:9: ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) )
            	    // InternalKactors.g:411:10: {...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:411:19: (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) )
            	    // InternalKactors.g:411:20: otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) )
            	    {
            	    otherlv_13=(Token)match(input,33,FOLLOW_7); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_13, grammarAccess.getPreambleAccess().getObservableKeyword_2_2_0());
            	      								
            	    }
            	    // InternalKactors.g:415:9: ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) )
            	    int alt9=2;
            	    int LA9_0 = input.LA(1);

            	    if ( (LA9_0==RULE_OBSERVABLE) ) {
            	        alt9=1;
            	    }
            	    else if ( (LA9_0==43) ) {
            	        alt9=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 9, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt9) {
            	        case 1 :
            	            // InternalKactors.g:416:10: ( (lv_observable_14_0= RULE_OBSERVABLE ) )
            	            {
            	            // InternalKactors.g:416:10: ( (lv_observable_14_0= RULE_OBSERVABLE ) )
            	            // InternalKactors.g:417:11: (lv_observable_14_0= RULE_OBSERVABLE )
            	            {
            	            // InternalKactors.g:417:11: (lv_observable_14_0= RULE_OBSERVABLE )
            	            // InternalKactors.g:418:12: lv_observable_14_0= RULE_OBSERVABLE
            	            {
            	            lv_observable_14_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_observable_14_0, grammarAccess.getPreambleAccess().getObservableOBSERVABLETerminalRuleCall_2_2_1_0_0());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"observable",
            	              													lv_observable_14_0,
            	              													"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
            	              											
            	            }

            	            }


            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:435:10: ( (lv_observables_15_0= ruleList ) )
            	            {
            	            // InternalKactors.g:435:10: ( (lv_observables_15_0= ruleList ) )
            	            // InternalKactors.g:436:11: (lv_observables_15_0= ruleList )
            	            {
            	            // InternalKactors.g:436:11: (lv_observables_15_0= ruleList )
            	            // InternalKactors.g:437:12: lv_observables_15_0= ruleList
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getPreambleAccess().getObservablesListParserRuleCall_2_2_1_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_5);
            	            lv_observables_15_0=ruleList();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getPreambleRule());
            	              												}
            	              												set(
            	              													current,
            	              													"observables",
            	              													lv_observables_15_0,
            	              													"org.integratedmodelling.kactors.Kactors.List");
            	              												afterParserOrEnumRuleCall();
            	              											
            	            }

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
            	case 4 :
            	    // InternalKactors.g:461:4: ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:461:4: ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) )
            	    // InternalKactors.g:462:5: {...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
            	    }
            	    // InternalKactors.g:462:105: ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:463:6: ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
            	    // InternalKactors.g:466:9: ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:466:10: {...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:466:19: (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) )
            	    // InternalKactors.g:466:20: otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) )
            	    {
            	    otherlv_16=(Token)match(input,34,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_16, grammarAccess.getPreambleAccess().getLabelKeyword_2_3_0());
            	      								
            	    }
            	    // InternalKactors.g:470:9: ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) )
            	    // InternalKactors.g:471:10: ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:471:10: ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) )
            	    // InternalKactors.g:472:11: (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING )
            	    {
            	    // InternalKactors.g:472:11: (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING )
            	    int alt10=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_LOWERCASE_ID:
            	        {
            	        alt10=1;
            	        }
            	        break;
            	    case RULE_ID:
            	        {
            	        alt10=2;
            	        }
            	        break;
            	    case RULE_STRING:
            	        {
            	        alt10=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 10, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt10) {
            	        case 1 :
            	            // InternalKactors.g:473:12: lv_label_17_1= RULE_LOWERCASE_ID
            	            {
            	            lv_label_17_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_17_1, grammarAccess.getPreambleAccess().getLabelLOWERCASE_IDTerminalRuleCall_2_3_1_0_0());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_17_1,
            	              													"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
            	              											
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:488:12: lv_label_17_2= RULE_ID
            	            {
            	            lv_label_17_2=(Token)match(input,RULE_ID,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_17_2, grammarAccess.getPreambleAccess().getLabelIDTerminalRuleCall_2_3_1_0_1());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_17_2,
            	              													"org.eclipse.xtext.common.Terminals.ID");
            	              											
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalKactors.g:503:12: lv_label_17_3= RULE_STRING
            	            {
            	            lv_label_17_3=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_17_3, grammarAccess.getPreambleAccess().getLabelSTRINGTerminalRuleCall_2_3_1_0_2());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_17_3,
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
            	case 5 :
            	    // InternalKactors.g:526:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:526:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:527:5: {...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4)");
            	    }
            	    // InternalKactors.g:527:105: ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:528:6: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4);
            	    // InternalKactors.g:531:9: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:531:10: {...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:531:19: (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
            	    // InternalKactors.g:531:20: otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) )
            	    {
            	    otherlv_18=(Token)match(input,35,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_18, grammarAccess.getPreambleAccess().getDescriptionKeyword_2_4_0());
            	      								
            	    }
            	    // InternalKactors.g:535:9: ( (lv_description_19_0= RULE_STRING ) )
            	    // InternalKactors.g:536:10: (lv_description_19_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:536:10: (lv_description_19_0= RULE_STRING )
            	    // InternalKactors.g:537:11: lv_description_19_0= RULE_STRING
            	    {
            	    lv_description_19_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_description_19_0, grammarAccess.getPreambleAccess().getDescriptionSTRINGTerminalRuleCall_2_4_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"description",
            	      												lv_description_19_0,
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
            	    // InternalKactors.g:559:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:559:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:560:5: {...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5)");
            	    }
            	    // InternalKactors.g:560:105: ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:561:6: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5);
            	    // InternalKactors.g:564:9: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:564:10: {...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:564:19: (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
            	    // InternalKactors.g:564:20: otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) )
            	    {
            	    otherlv_20=(Token)match(input,36,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_20, grammarAccess.getPreambleAccess().getPermissionsKeyword_2_5_0());
            	      								
            	    }
            	    // InternalKactors.g:568:9: ( (lv_permissions_21_0= RULE_STRING ) )
            	    // InternalKactors.g:569:10: (lv_permissions_21_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:569:10: (lv_permissions_21_0= RULE_STRING )
            	    // InternalKactors.g:570:11: lv_permissions_21_0= RULE_STRING
            	    {
            	    lv_permissions_21_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_permissions_21_0, grammarAccess.getPreambleAccess().getPermissionsSTRINGTerminalRuleCall_2_5_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"permissions",
            	      												lv_permissions_21_0,
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
            	case 7 :
            	    // InternalKactors.g:592:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
            	    {
            	    // InternalKactors.g:592:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
            	    // InternalKactors.g:593:5: {...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6)");
            	    }
            	    // InternalKactors.g:593:105: ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
            	    // InternalKactors.g:594:6: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6);
            	    // InternalKactors.g:597:9: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
            	    int cnt11=0;
            	    loop11:
            	    do {
            	        int alt11=2;
            	        int LA11_0 = input.LA(1);

            	        if ( (LA11_0==37) ) {
            	            int LA11_2 = input.LA(2);

            	            if ( ((synpred21_InternalKactors()&&(true))) ) {
            	                alt11=1;
            	            }


            	        }


            	        switch (alt11) {
            	    	case 1 :
            	    	    // InternalKactors.g:597:10: {...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    	    }
            	    	    // InternalKactors.g:597:19: (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
            	    	    // InternalKactors.g:597:20: otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) )
            	    	    {
            	    	    otherlv_22=(Token)match(input,37,FOLLOW_9); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      									newLeafNode(otherlv_22, grammarAccess.getPreambleAccess().getAuthorKeyword_2_6_0());
            	    	      								
            	    	    }
            	    	    // InternalKactors.g:601:9: ( (lv_authors_23_0= RULE_STRING ) )
            	    	    // InternalKactors.g:602:10: (lv_authors_23_0= RULE_STRING )
            	    	    {
            	    	    // InternalKactors.g:602:10: (lv_authors_23_0= RULE_STRING )
            	    	    // InternalKactors.g:603:11: lv_authors_23_0= RULE_STRING
            	    	    {
            	    	    lv_authors_23_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      											newLeafNode(lv_authors_23_0, grammarAccess.getPreambleAccess().getAuthorsSTRINGTerminalRuleCall_2_6_1_0());
            	    	      										
            	    	    }
            	    	    if ( state.backtracking==0 ) {

            	    	      											if (current==null) {
            	    	      												current = createModelElement(grammarAccess.getPreambleRule());
            	    	      											}
            	    	      											addWithLastConsumed(
            	    	      												current,
            	    	      												"authors",
            	    	      												lv_authors_23_0,
            	    	      												"org.eclipse.xtext.common.Terminals.STRING");
            	    	      										
            	    	    }

            	    	    }


            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt11 >= 1 ) break loop11;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(11, input);
            	                throw eee;
            	        }
            	        cnt11++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 8 :
            	    // InternalKactors.g:625:4: ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:625:4: ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) )
            	    // InternalKactors.g:626:5: {...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7)");
            	    }
            	    // InternalKactors.g:626:105: ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) )
            	    // InternalKactors.g:627:6: ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7);
            	    // InternalKactors.g:630:9: ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) )
            	    // InternalKactors.g:630:10: {...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:630:19: (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) )
            	    // InternalKactors.g:630:20: otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) )
            	    {
            	    otherlv_24=(Token)match(input,38,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_24, grammarAccess.getPreambleAccess().getVersionKeyword_2_7_0());
            	      								
            	    }
            	    // InternalKactors.g:634:9: ( (lv_version_25_0= ruleVersionNumber ) )
            	    // InternalKactors.g:635:10: (lv_version_25_0= ruleVersionNumber )
            	    {
            	    // InternalKactors.g:635:10: (lv_version_25_0= ruleVersionNumber )
            	    // InternalKactors.g:636:11: lv_version_25_0= ruleVersionNumber
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_2_7_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_version_25_0=ruleVersionNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"version",
            	      												lv_version_25_0,
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
            	case 9 :
            	    // InternalKactors.g:659:4: ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:659:4: ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:660:5: {...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8)");
            	    }
            	    // InternalKactors.g:660:105: ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:661:6: ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8);
            	    // InternalKactors.g:664:9: ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:664:10: {...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:664:19: (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? )
            	    // InternalKactors.g:664:20: otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )?
            	    {
            	    otherlv_26=(Token)match(input,39,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_26, grammarAccess.getPreambleAccess().getCreatedKeyword_2_8_0());
            	      								
            	    }
            	    // InternalKactors.g:668:9: ( (lv_created_27_0= ruleDate ) )
            	    // InternalKactors.g:669:10: (lv_created_27_0= ruleDate )
            	    {
            	    // InternalKactors.g:669:10: (lv_created_27_0= ruleDate )
            	    // InternalKactors.g:670:11: lv_created_27_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_2_8_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_11);
            	    lv_created_27_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"created",
            	      												lv_created_27_0,
            	      												"org.integratedmodelling.kactors.Kactors.Date");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:687:9: ( (lv_createcomment_28_0= RULE_STRING ) )?
            	    int alt12=2;
            	    int LA12_0 = input.LA(1);

            	    if ( (LA12_0==RULE_STRING) ) {
            	        alt12=1;
            	    }
            	    switch (alt12) {
            	        case 1 :
            	            // InternalKactors.g:688:10: (lv_createcomment_28_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:688:10: (lv_createcomment_28_0= RULE_STRING )
            	            // InternalKactors.g:689:11: lv_createcomment_28_0= RULE_STRING
            	            {
            	            lv_createcomment_28_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_createcomment_28_0, grammarAccess.getPreambleAccess().getCreatecommentSTRINGTerminalRuleCall_2_8_2_0());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"createcomment",
            	              												lv_createcomment_28_0,
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
            	case 10 :
            	    // InternalKactors.g:711:4: ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:711:4: ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:712:5: {...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9)");
            	    }
            	    // InternalKactors.g:712:105: ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:713:6: ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9);
            	    // InternalKactors.g:716:9: ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:716:10: {...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:716:19: (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? )
            	    // InternalKactors.g:716:20: otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )?
            	    {
            	    otherlv_29=(Token)match(input,40,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_29, grammarAccess.getPreambleAccess().getModifiedKeyword_2_9_0());
            	      								
            	    }
            	    // InternalKactors.g:720:9: ( (lv_modified_30_0= ruleDate ) )
            	    // InternalKactors.g:721:10: (lv_modified_30_0= ruleDate )
            	    {
            	    // InternalKactors.g:721:10: (lv_modified_30_0= ruleDate )
            	    // InternalKactors.g:722:11: lv_modified_30_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_2_9_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_11);
            	    lv_modified_30_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"modified",
            	      												lv_modified_30_0,
            	      												"org.integratedmodelling.kactors.Kactors.Date");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:739:9: ( (lv_modcomment_31_0= RULE_STRING ) )?
            	    int alt13=2;
            	    int LA13_0 = input.LA(1);

            	    if ( (LA13_0==RULE_STRING) ) {
            	        alt13=1;
            	    }
            	    switch (alt13) {
            	        case 1 :
            	            // InternalKactors.g:740:10: (lv_modcomment_31_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:740:10: (lv_modcomment_31_0= RULE_STRING )
            	            // InternalKactors.g:741:11: lv_modcomment_31_0= RULE_STRING
            	            {
            	            lv_modcomment_31_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_modcomment_31_0, grammarAccess.getPreambleAccess().getModcommentSTRINGTerminalRuleCall_2_9_2_0());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"modcomment",
            	              												lv_modcomment_31_0,
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
            	    break loop14;
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
    // InternalKactors.g:777:1: entryRuleDefinition returns [EObject current=null] : iv_ruleDefinition= ruleDefinition EOF ;
    public final EObject entryRuleDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDefinition = null;


        try {
            // InternalKactors.g:777:51: (iv_ruleDefinition= ruleDefinition EOF )
            // InternalKactors.g:778:2: iv_ruleDefinition= ruleDefinition EOF
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
    // InternalKactors.g:784:1: ruleDefinition returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) ) ;
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
            // InternalKactors.g:790:2: ( ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) ) )
            // InternalKactors.g:791:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) )
            {
            // InternalKactors.g:791:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) )
            // InternalKactors.g:792:3: ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) )
            {
            // InternalKactors.g:792:3: ( (lv_annotations_0_0= ruleAnnotation ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==RULE_ANNOTATION_ID) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalKactors.g:793:4: (lv_annotations_0_0= ruleAnnotation )
            	    {
            	    // InternalKactors.g:793:4: (lv_annotations_0_0= ruleAnnotation )
            	    // InternalKactors.g:794:5: lv_annotations_0_0= ruleAnnotation
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getDefinitionAccess().getAnnotationsAnnotationParserRuleCall_0_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_12);
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
            	    break loop15;
                }
            } while (true);

            otherlv_1=(Token)match(input,41,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getDefinitionAccess().getActionKeyword_1());
              		
            }
            // InternalKactors.g:815:3: ( (lv_name_2_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:816:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:816:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:817:5: lv_name_2_0= RULE_LOWERCASE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_13); if (state.failed) return current;
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

            // InternalKactors.g:833:3: ( (lv_arguments_3_0= ruleArgumentDeclaration ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==43) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalKactors.g:834:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:834:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    // InternalKactors.g:835:5: lv_arguments_3_0= ruleArgumentDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getDefinitionAccess().getArgumentsArgumentDeclarationParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_14);
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

            otherlv_4=(Token)match(input,42,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDefinitionAccess().getColonKeyword_4());
              		
            }
            // InternalKactors.g:856:3: ( (lv_body_5_0= ruleMessageBody ) )
            // InternalKactors.g:857:4: (lv_body_5_0= ruleMessageBody )
            {
            // InternalKactors.g:857:4: (lv_body_5_0= ruleMessageBody )
            // InternalKactors.g:858:5: lv_body_5_0= ruleMessageBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDefinitionAccess().getBodyMessageBodyParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_body_5_0=ruleMessageBody();

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
              						"org.integratedmodelling.kactors.Kactors.MessageBody");
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
    // InternalKactors.g:879:1: entryRuleArgumentDeclaration returns [EObject current=null] : iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF ;
    public final EObject entryRuleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgumentDeclaration = null;


        try {
            // InternalKactors.g:879:60: (iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF )
            // InternalKactors.g:880:2: iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF
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
    // InternalKactors.g:886:1: ruleArgumentDeclaration returns [EObject current=null] : ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) ;
    public final EObject ruleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_ids_2_0=null;
        Token otherlv_3=null;
        Token lv_ids_4_0=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalKactors.g:892:2: ( ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) )
            // InternalKactors.g:893:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            {
            // InternalKactors.g:893:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            // InternalKactors.g:894:3: () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')'
            {
            // InternalKactors.g:894:3: ()
            // InternalKactors.g:895:4: 
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

            otherlv_1=(Token)match(input,43,FOLLOW_16); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getArgumentDeclarationAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:908:3: ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_LOWERCASE_ID) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalKactors.g:909:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    {
                    // InternalKactors.g:909:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:910:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:910:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:911:6: lv_ids_2_0= RULE_LOWERCASE_ID
                    {
                    lv_ids_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_17); if (state.failed) return current;
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

                    // InternalKactors.g:927:4: (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==31) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // InternalKactors.g:928:5: otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    {
                    	    otherlv_3=(Token)match(input,31,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getArgumentDeclarationAccess().getCommaKeyword_2_1_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:932:5: ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    // InternalKactors.g:933:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    {
                    	    // InternalKactors.g:933:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    // InternalKactors.g:934:7: lv_ids_4_0= RULE_LOWERCASE_ID
                    	    {
                    	    lv_ids_4_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_17); if (state.failed) return current;
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
                    	    break loop17;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleMessageBody"
    // InternalKactors.g:960:1: entryRuleMessageBody returns [EObject current=null] : iv_ruleMessageBody= ruleMessageBody EOF ;
    public final EObject entryRuleMessageBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageBody = null;


        try {
            // InternalKactors.g:960:52: (iv_ruleMessageBody= ruleMessageBody EOF )
            // InternalKactors.g:961:2: iv_ruleMessageBody= ruleMessageBody EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMessageBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMessageBody=ruleMessageBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMessageBody; 
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
    // $ANTLR end "entryRuleMessageBody"


    // $ANTLR start "ruleMessageBody"
    // InternalKactors.g:967:1: ruleMessageBody returns [EObject current=null] : ( () ( (lv_lists_1_0= ruleStatementList ) )* ) ;
    public final EObject ruleMessageBody() throws RecognitionException {
        EObject current = null;

        EObject lv_lists_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:973:2: ( ( () ( (lv_lists_1_0= ruleStatementList ) )* ) )
            // InternalKactors.g:974:2: ( () ( (lv_lists_1_0= ruleStatementList ) )* )
            {
            // InternalKactors.g:974:2: ( () ( (lv_lists_1_0= ruleStatementList ) )* )
            // InternalKactors.g:975:3: () ( (lv_lists_1_0= ruleStatementList ) )*
            {
            // InternalKactors.g:975:3: ()
            // InternalKactors.g:976:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getMessageBodyAccess().getBodyAction_0(),
              					current);
              			
            }

            }

            // InternalKactors.g:985:3: ( (lv_lists_1_0= ruleStatementList ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>=RULE_OBSERVABLE && LA19_0<=RULE_LOWERCASE_ID)||(LA19_0>=RULE_STRING && LA19_0<=RULE_EXPR)||LA19_0==RULE_INT||LA19_0==RULE_ARGVALUE||LA19_0==43||LA19_0==45||(LA19_0>=47 && LA19_0<=49)||(LA19_0>=52 && LA19_0<=53)||LA19_0==60||LA19_0==63||LA19_0==65||(LA19_0>=77 && LA19_0<=78)) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalKactors.g:986:4: (lv_lists_1_0= ruleStatementList )
            	    {
            	    // InternalKactors.g:986:4: (lv_lists_1_0= ruleStatementList )
            	    // InternalKactors.g:987:5: lv_lists_1_0= ruleStatementList
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getMessageBodyAccess().getListsStatementListParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_18);
            	    lv_lists_1_0=ruleStatementList();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getMessageBodyRule());
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
            	    break loop19;
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
    // $ANTLR end "ruleMessageBody"


    // $ANTLR start "entryRuleMessageCall"
    // InternalKactors.g:1008:1: entryRuleMessageCall returns [EObject current=null] : iv_ruleMessageCall= ruleMessageCall EOF ;
    public final EObject entryRuleMessageCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageCall = null;


        try {
            // InternalKactors.g:1008:52: (iv_ruleMessageCall= ruleMessageCall EOF )
            // InternalKactors.g:1009:2: iv_ruleMessageCall= ruleMessageCall EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMessageCallRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMessageCall=ruleMessageCall();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMessageCall; 
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
    // $ANTLR end "entryRuleMessageCall"


    // $ANTLR start "ruleMessageCall"
    // InternalKactors.g:1015:1: ruleMessageCall returns [EObject current=null] : ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? ) ;
    public final EObject ruleMessageCall() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject this_StatementGroup_4 = null;

        EObject lv_actions_6_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1021:2: ( ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? ) )
            // InternalKactors.g:1022:2: ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? )
            {
            // InternalKactors.g:1022:2: ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? )
            // InternalKactors.g:1023:3: ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )?
            {
            // InternalKactors.g:1023:3: ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==RULE_LOWERCASE_ID) ) {
                alt22=1;
            }
            else if ( (LA22_0==43) ) {
                alt22=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // InternalKactors.g:1024:4: ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
                    {
                    // InternalKactors.g:1024:4: ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
                    // InternalKactors.g:1025:5: ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
                    {
                    // InternalKactors.g:1025:5: ( (lv_name_0_0= rulePathName ) )
                    // InternalKactors.g:1026:6: (lv_name_0_0= rulePathName )
                    {
                    // InternalKactors.g:1026:6: (lv_name_0_0= rulePathName )
                    // InternalKactors.g:1027:7: lv_name_0_0= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getMessageCallAccess().getNamePathNameParserRuleCall_0_0_0_0());
                      						
                    }
                    pushFollow(FOLLOW_19);
                    lv_name_0_0=rulePathName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getMessageCallRule());
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

                    // InternalKactors.g:1044:5: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
                    int alt21=2;
                    alt21 = dfa21.predict(input);
                    switch (alt21) {
                        case 1 :
                            // InternalKactors.g:1045:6: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                            {
                            otherlv_1=(Token)match(input,43,FOLLOW_20); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_1, grammarAccess.getMessageCallAccess().getLeftParenthesisKeyword_0_0_1_0());
                              					
                            }
                            // InternalKactors.g:1049:6: ( (lv_parameters_2_0= ruleParameterList ) )?
                            int alt20=2;
                            int LA20_0 = input.LA(1);

                            if ( ((LA20_0>=RULE_OBSERVABLE && LA20_0<=RULE_LOWERCASE_ID)||LA20_0==RULE_STRING||LA20_0==RULE_EXPR||LA20_0==RULE_INT||LA20_0==RULE_ARGVALUE||LA20_0==43||(LA20_0>=52 && LA20_0<=53)||LA20_0==60||LA20_0==63||LA20_0==65||(LA20_0>=77 && LA20_0<=78)) ) {
                                alt20=1;
                            }
                            switch (alt20) {
                                case 1 :
                                    // InternalKactors.g:1050:7: (lv_parameters_2_0= ruleParameterList )
                                    {
                                    // InternalKactors.g:1050:7: (lv_parameters_2_0= ruleParameterList )
                                    // InternalKactors.g:1051:8: lv_parameters_2_0= ruleParameterList
                                    {
                                    if ( state.backtracking==0 ) {

                                      								newCompositeNode(grammarAccess.getMessageCallAccess().getParametersParameterListParserRuleCall_0_0_1_1_0());
                                      							
                                    }
                                    pushFollow(FOLLOW_21);
                                    lv_parameters_2_0=ruleParameterList();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								if (current==null) {
                                      									current = createModelElementForParent(grammarAccess.getMessageCallRule());
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

                            otherlv_3=(Token)match(input,44,FOLLOW_22); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_3, grammarAccess.getMessageCallAccess().getRightParenthesisKeyword_0_0_1_2());
                              					
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:1075:4: this_StatementGroup_4= ruleStatementGroup
                    {
                    if ( state.backtracking==0 ) {

                      				/* */
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getMessageCallAccess().getStatementGroupParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_22);
                    this_StatementGroup_4=ruleStatementGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_StatementGroup_4;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:1087:3: (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==42) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalKactors.g:1088:4: otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) )
                    {
                    otherlv_5=(Token)match(input,42,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getMessageCallAccess().getColonKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:1092:4: ( (lv_actions_6_0= ruleActions ) )
                    // InternalKactors.g:1093:5: (lv_actions_6_0= ruleActions )
                    {
                    // InternalKactors.g:1093:5: (lv_actions_6_0= ruleActions )
                    // InternalKactors.g:1094:6: lv_actions_6_0= ruleActions
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMessageCallAccess().getActionsActionsParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_actions_6_0=ruleActions();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMessageCallRule());
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
    // $ANTLR end "ruleMessageCall"


    // $ANTLR start "entryRuleStatementGroup"
    // InternalKactors.g:1116:1: entryRuleStatementGroup returns [EObject current=null] : iv_ruleStatementGroup= ruleStatementGroup EOF ;
    public final EObject entryRuleStatementGroup() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementGroup = null;


        try {
            // InternalKactors.g:1116:55: (iv_ruleStatementGroup= ruleStatementGroup EOF )
            // InternalKactors.g:1117:2: iv_ruleStatementGroup= ruleStatementGroup EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStatementGroupRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStatementGroup=ruleStatementGroup();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStatementGroup; 
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
    // $ANTLR end "entryRuleStatementGroup"


    // $ANTLR start "ruleStatementGroup"
    // InternalKactors.g:1123:1: ruleStatementGroup returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' ) ;
    public final EObject ruleStatementGroup() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1129:2: ( ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' ) )
            // InternalKactors.g:1130:2: ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' )
            {
            // InternalKactors.g:1130:2: ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' )
            // InternalKactors.g:1131:3: () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')'
            {
            // InternalKactors.g:1131:3: ()
            // InternalKactors.g:1132:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getStatementGroupAccess().getGroupAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,43,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getStatementGroupAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:1145:3: ( (lv_body_2_0= ruleMessageBody ) )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( ((LA24_0>=RULE_OBSERVABLE && LA24_0<=RULE_LOWERCASE_ID)||(LA24_0>=RULE_STRING && LA24_0<=RULE_EXPR)||LA24_0==RULE_INT||LA24_0==RULE_ARGVALUE||LA24_0==43||LA24_0==45||(LA24_0>=47 && LA24_0<=49)||(LA24_0>=52 && LA24_0<=53)||LA24_0==60||LA24_0==63||LA24_0==65||(LA24_0>=77 && LA24_0<=78)) ) {
                alt24=1;
            }
            else if ( (LA24_0==44) ) {
                int LA24_2 = input.LA(2);

                if ( (synpred37_InternalKactors()) ) {
                    alt24=1;
                }
            }
            switch (alt24) {
                case 1 :
                    // InternalKactors.g:1146:4: (lv_body_2_0= ruleMessageBody )
                    {
                    // InternalKactors.g:1146:4: (lv_body_2_0= ruleMessageBody )
                    // InternalKactors.g:1147:5: lv_body_2_0= ruleMessageBody
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementGroupAccess().getBodyMessageBodyParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_21);
                    lv_body_2_0=ruleMessageBody();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementGroupRule());
                      					}
                      					set(
                      						current,
                      						"body",
                      						lv_body_2_0,
                      						"org.integratedmodelling.kactors.Kactors.MessageBody");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getStatementGroupAccess().getRightParenthesisKeyword_3());
              		
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
    // $ANTLR end "ruleStatementGroup"


    // $ANTLR start "entryRuleStatementList"
    // InternalKactors.g:1172:1: entryRuleStatementList returns [EObject current=null] : iv_ruleStatementList= ruleStatementList EOF ;
    public final EObject entryRuleStatementList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementList = null;


        try {
            // InternalKactors.g:1172:54: (iv_ruleStatementList= ruleStatementList EOF )
            // InternalKactors.g:1173:2: iv_ruleStatementList= ruleStatementList EOF
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
    // InternalKactors.g:1179:1: ruleStatementList returns [EObject current=null] : ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* ) ;
    public final EObject ruleStatementList() throws RecognitionException {
        EObject current = null;

        EObject lv_first_0_0 = null;

        EObject lv_next_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1185:2: ( ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* ) )
            // InternalKactors.g:1186:2: ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* )
            {
            // InternalKactors.g:1186:2: ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* )
            // InternalKactors.g:1187:3: ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )*
            {
            // InternalKactors.g:1187:3: ( (lv_first_0_0= ruleStatement ) )
            // InternalKactors.g:1188:4: (lv_first_0_0= ruleStatement )
            {
            // InternalKactors.g:1188:4: (lv_first_0_0= ruleStatement )
            // InternalKactors.g:1189:5: lv_first_0_0= ruleStatement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getStatementListAccess().getFirstStatementParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_24);
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

            // InternalKactors.g:1206:3: ( (lv_next_1_0= ruleNextStatement ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==31) ) {
                    int LA25_2 = input.LA(2);

                    if ( (synpred38_InternalKactors()) ) {
                        alt25=1;
                    }


                }


                switch (alt25) {
            	case 1 :
            	    // InternalKactors.g:1207:4: (lv_next_1_0= ruleNextStatement )
            	    {
            	    // InternalKactors.g:1207:4: (lv_next_1_0= ruleNextStatement )
            	    // InternalKactors.g:1208:5: lv_next_1_0= ruleNextStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getStatementListAccess().getNextNextStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_24);
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
            	    break loop25;
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
    // InternalKactors.g:1229:1: entryRuleStatement returns [EObject current=null] : iv_ruleStatement= ruleStatement EOF ;
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatement = null;


        try {
            // InternalKactors.g:1229:50: (iv_ruleStatement= ruleStatement EOF )
            // InternalKactors.g:1230:2: iv_ruleStatement= ruleStatement EOF
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
    // InternalKactors.g:1236:1: ruleStatement returns [EObject current=null] : ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) ) ;
    public final EObject ruleStatement() throws RecognitionException {
        EObject current = null;

        Token lv_text_2_0=null;
        EObject lv_verb_0_0 = null;

        EObject lv_group_1_0 = null;

        EObject lv_if_3_0 = null;

        EObject lv_while_4_0 = null;

        EObject lv_do_5_0 = null;

        EObject lv_for_6_0 = null;

        EObject lv_value_7_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1242:2: ( ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) ) )
            // InternalKactors.g:1243:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) )
            {
            // InternalKactors.g:1243:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) )
            int alt26=8;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // InternalKactors.g:1244:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1244:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    // InternalKactors.g:1245:4: (lv_verb_0_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1245:4: (lv_verb_0_0= ruleMessageCall )
                    // InternalKactors.g:1246:5: lv_verb_0_0= ruleMessageCall
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getVerbMessageCallParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_verb_0_0=ruleMessageCall();

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
                      						"org.integratedmodelling.kactors.Kactors.MessageCall");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:1264:3: ( (lv_group_1_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1264:3: ( (lv_group_1_0= ruleStatementGroup ) )
                    // InternalKactors.g:1265:4: (lv_group_1_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1265:4: (lv_group_1_0= ruleStatementGroup )
                    // InternalKactors.g:1266:5: lv_group_1_0= ruleStatementGroup
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getGroupStatementGroupParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_group_1_0=ruleStatementGroup();

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
                      						"org.integratedmodelling.kactors.Kactors.StatementGroup");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:1284:3: ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:1284:3: ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:1285:4: (lv_text_2_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:1285:4: (lv_text_2_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:1286:5: lv_text_2_0= RULE_EMBEDDEDTEXT
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
                    // InternalKactors.g:1303:3: ( (lv_if_3_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:1303:3: ( (lv_if_3_0= ruleIfStatement ) )
                    // InternalKactors.g:1304:4: (lv_if_3_0= ruleIfStatement )
                    {
                    // InternalKactors.g:1304:4: (lv_if_3_0= ruleIfStatement )
                    // InternalKactors.g:1305:5: lv_if_3_0= ruleIfStatement
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
                    // InternalKactors.g:1323:3: ( (lv_while_4_0= ruleWhileStatement ) )
                    {
                    // InternalKactors.g:1323:3: ( (lv_while_4_0= ruleWhileStatement ) )
                    // InternalKactors.g:1324:4: (lv_while_4_0= ruleWhileStatement )
                    {
                    // InternalKactors.g:1324:4: (lv_while_4_0= ruleWhileStatement )
                    // InternalKactors.g:1325:5: lv_while_4_0= ruleWhileStatement
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
                    // InternalKactors.g:1343:3: ( (lv_do_5_0= ruleDoStatement ) )
                    {
                    // InternalKactors.g:1343:3: ( (lv_do_5_0= ruleDoStatement ) )
                    // InternalKactors.g:1344:4: (lv_do_5_0= ruleDoStatement )
                    {
                    // InternalKactors.g:1344:4: (lv_do_5_0= ruleDoStatement )
                    // InternalKactors.g:1345:5: lv_do_5_0= ruleDoStatement
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
                    // InternalKactors.g:1363:3: ( (lv_for_6_0= ruleForStatement ) )
                    {
                    // InternalKactors.g:1363:3: ( (lv_for_6_0= ruleForStatement ) )
                    // InternalKactors.g:1364:4: (lv_for_6_0= ruleForStatement )
                    {
                    // InternalKactors.g:1364:4: (lv_for_6_0= ruleForStatement )
                    // InternalKactors.g:1365:5: lv_for_6_0= ruleForStatement
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
                case 8 :
                    // InternalKactors.g:1383:3: ( (lv_value_7_0= ruleValue ) )
                    {
                    // InternalKactors.g:1383:3: ( (lv_value_7_0= ruleValue ) )
                    // InternalKactors.g:1384:4: (lv_value_7_0= ruleValue )
                    {
                    // InternalKactors.g:1384:4: (lv_value_7_0= ruleValue )
                    // InternalKactors.g:1385:5: lv_value_7_0= ruleValue
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getValueValueParserRuleCall_7_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_7_0=ruleValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"value",
                      						lv_value_7_0,
                      						"org.integratedmodelling.kactors.Kactors.Value");
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
    // InternalKactors.g:1406:1: entryRuleNextStatement returns [EObject current=null] : iv_ruleNextStatement= ruleNextStatement EOF ;
    public final EObject entryRuleNextStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNextStatement = null;


        try {
            // InternalKactors.g:1406:54: (iv_ruleNextStatement= ruleNextStatement EOF )
            // InternalKactors.g:1407:2: iv_ruleNextStatement= ruleNextStatement EOF
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
    // InternalKactors.g:1413:1: ruleNextStatement returns [EObject current=null] : (otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) ) ;
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

        EObject lv_value_8_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1419:2: ( (otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) ) )
            // InternalKactors.g:1420:2: (otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) )
            {
            // InternalKactors.g:1420:2: (otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) )
            // InternalKactors.g:1421:3: otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )
            {
            otherlv_0=(Token)match(input,31,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getNextStatementAccess().getCommaKeyword_0());
              		
            }
            // InternalKactors.g:1425:3: ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )
            int alt27=8;
            alt27 = dfa27.predict(input);
            switch (alt27) {
                case 1 :
                    // InternalKactors.g:1426:4: ( (lv_verb_1_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1426:4: ( (lv_verb_1_0= ruleMessageCall ) )
                    // InternalKactors.g:1427:5: (lv_verb_1_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1427:5: (lv_verb_1_0= ruleMessageCall )
                    // InternalKactors.g:1428:6: lv_verb_1_0= ruleMessageCall
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getVerbMessageCallParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_verb_1_0=ruleMessageCall();

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
                      							"org.integratedmodelling.kactors.Kactors.MessageCall");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:1446:4: ( (lv_group_2_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1446:4: ( (lv_group_2_0= ruleStatementGroup ) )
                    // InternalKactors.g:1447:5: (lv_group_2_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1447:5: (lv_group_2_0= ruleStatementGroup )
                    // InternalKactors.g:1448:6: lv_group_2_0= ruleStatementGroup
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getGroupStatementGroupParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_group_2_0=ruleStatementGroup();

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
                      							"org.integratedmodelling.kactors.Kactors.StatementGroup");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:1466:4: ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:1466:4: ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:1467:5: (lv_text_3_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:1467:5: (lv_text_3_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:1468:6: lv_text_3_0= RULE_EMBEDDEDTEXT
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
                    // InternalKactors.g:1485:4: ( (lv_if_4_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:1485:4: ( (lv_if_4_0= ruleIfStatement ) )
                    // InternalKactors.g:1486:5: (lv_if_4_0= ruleIfStatement )
                    {
                    // InternalKactors.g:1486:5: (lv_if_4_0= ruleIfStatement )
                    // InternalKactors.g:1487:6: lv_if_4_0= ruleIfStatement
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
                    // InternalKactors.g:1505:4: ( (lv_while_5_0= ruleWhileStatement ) )
                    {
                    // InternalKactors.g:1505:4: ( (lv_while_5_0= ruleWhileStatement ) )
                    // InternalKactors.g:1506:5: (lv_while_5_0= ruleWhileStatement )
                    {
                    // InternalKactors.g:1506:5: (lv_while_5_0= ruleWhileStatement )
                    // InternalKactors.g:1507:6: lv_while_5_0= ruleWhileStatement
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
                    // InternalKactors.g:1525:4: ( (lv_do_6_0= ruleDoStatement ) )
                    {
                    // InternalKactors.g:1525:4: ( (lv_do_6_0= ruleDoStatement ) )
                    // InternalKactors.g:1526:5: (lv_do_6_0= ruleDoStatement )
                    {
                    // InternalKactors.g:1526:5: (lv_do_6_0= ruleDoStatement )
                    // InternalKactors.g:1527:6: lv_do_6_0= ruleDoStatement
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
                    // InternalKactors.g:1545:4: ( (lv_for_7_0= ruleForStatement ) )
                    {
                    // InternalKactors.g:1545:4: ( (lv_for_7_0= ruleForStatement ) )
                    // InternalKactors.g:1546:5: (lv_for_7_0= ruleForStatement )
                    {
                    // InternalKactors.g:1546:5: (lv_for_7_0= ruleForStatement )
                    // InternalKactors.g:1547:6: lv_for_7_0= ruleForStatement
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
                case 8 :
                    // InternalKactors.g:1565:4: ( (lv_value_8_0= ruleValue ) )
                    {
                    // InternalKactors.g:1565:4: ( (lv_value_8_0= ruleValue ) )
                    // InternalKactors.g:1566:5: (lv_value_8_0= ruleValue )
                    {
                    // InternalKactors.g:1566:5: (lv_value_8_0= ruleValue )
                    // InternalKactors.g:1567:6: lv_value_8_0= ruleValue
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getValueValueParserRuleCall_1_7_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_8_0=ruleValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"value",
                      							lv_value_8_0,
                      							"org.integratedmodelling.kactors.Kactors.Value");
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
    // InternalKactors.g:1589:1: entryRuleIfStatement returns [EObject current=null] : iv_ruleIfStatement= ruleIfStatement EOF ;
    public final EObject entryRuleIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfStatement = null;


        try {
            // InternalKactors.g:1589:52: (iv_ruleIfStatement= ruleIfStatement EOF )
            // InternalKactors.g:1590:2: iv_ruleIfStatement= ruleIfStatement EOF
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
    // InternalKactors.g:1596:1: ruleIfStatement returns [EObject current=null] : (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? ) ;
    public final EObject ruleIfStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_expression_1_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_elseIfExpression_5_0=null;
        Token otherlv_7=null;
        EObject lv_body_2_0 = null;

        EObject lv_elseIfBody_6_0 = null;

        EObject lv_elseCall_8_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1602:2: ( (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? ) )
            // InternalKactors.g:1603:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? )
            {
            // InternalKactors.g:1603:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? )
            // InternalKactors.g:1604:3: otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )?
            {
            otherlv_0=(Token)match(input,45,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getIfStatementAccess().getIfKeyword_0());
              		
            }
            // InternalKactors.g:1608:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:1609:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:1609:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:1610:5: lv_expression_1_0= RULE_EXPR
            {
            lv_expression_1_0=(Token)match(input,RULE_EXPR,FOLLOW_15); if (state.failed) return current;
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

            // InternalKactors.g:1626:3: ( (lv_body_2_0= ruleStatementBody ) )
            // InternalKactors.g:1627:4: (lv_body_2_0= ruleStatementBody )
            {
            // InternalKactors.g:1627:4: (lv_body_2_0= ruleStatementBody )
            // InternalKactors.g:1628:5: lv_body_2_0= ruleStatementBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getIfStatementAccess().getBodyStatementBodyParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_26);
            lv_body_2_0=ruleStatementBody();

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
              						"org.integratedmodelling.kactors.Kactors.StatementBody");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:1645:3: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==46) ) {
                    int LA28_1 = input.LA(2);

                    if ( (synpred53_InternalKactors()) ) {
                        alt28=1;
                    }


                }


                switch (alt28) {
            	case 1 :
            	    // InternalKactors.g:1646:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) )
            	    {
            	    otherlv_3=(Token)match(input,46,FOLLOW_27); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getIfStatementAccess().getElseKeyword_3_0());
            	      			
            	    }
            	    otherlv_4=(Token)match(input,45,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getIfStatementAccess().getIfKeyword_3_1());
            	      			
            	    }
            	    // InternalKactors.g:1654:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
            	    // InternalKactors.g:1655:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    {
            	    // InternalKactors.g:1655:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    // InternalKactors.g:1656:6: lv_elseIfExpression_5_0= RULE_EXPR
            	    {
            	    lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_15); if (state.failed) return current;
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

            	    // InternalKactors.g:1672:4: ( (lv_elseIfBody_6_0= ruleStatementBody ) )
            	    // InternalKactors.g:1673:5: (lv_elseIfBody_6_0= ruleStatementBody )
            	    {
            	    // InternalKactors.g:1673:5: (lv_elseIfBody_6_0= ruleStatementBody )
            	    // InternalKactors.g:1674:6: lv_elseIfBody_6_0= ruleStatementBody
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getIfStatementAccess().getElseIfBodyStatementBodyParserRuleCall_3_3_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_26);
            	    lv_elseIfBody_6_0=ruleStatementBody();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getIfStatementRule());
            	      						}
            	      						add(
            	      							current,
            	      							"elseIfBody",
            	      							lv_elseIfBody_6_0,
            	      							"org.integratedmodelling.kactors.Kactors.StatementBody");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

            // InternalKactors.g:1692:3: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==46) ) {
                int LA29_1 = input.LA(2);

                if ( (synpred54_InternalKactors()) ) {
                    alt29=1;
                }
            }
            switch (alt29) {
                case 1 :
                    // InternalKactors.g:1693:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) )
                    {
                    otherlv_7=(Token)match(input,46,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getIfStatementAccess().getElseKeyword_4_0());
                      			
                    }
                    // InternalKactors.g:1697:4: ( (lv_elseCall_8_0= ruleStatementBody ) )
                    // InternalKactors.g:1698:5: (lv_elseCall_8_0= ruleStatementBody )
                    {
                    // InternalKactors.g:1698:5: (lv_elseCall_8_0= ruleStatementBody )
                    // InternalKactors.g:1699:6: lv_elseCall_8_0= ruleStatementBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getIfStatementAccess().getElseCallStatementBodyParserRuleCall_4_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_elseCall_8_0=ruleStatementBody();

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
                      							"org.integratedmodelling.kactors.Kactors.StatementBody");
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


    // $ANTLR start "entryRuleStatementBody"
    // InternalKactors.g:1721:1: entryRuleStatementBody returns [EObject current=null] : iv_ruleStatementBody= ruleStatementBody EOF ;
    public final EObject entryRuleStatementBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementBody = null;


        try {
            // InternalKactors.g:1721:54: (iv_ruleStatementBody= ruleStatementBody EOF )
            // InternalKactors.g:1722:2: iv_ruleStatementBody= ruleStatementBody EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStatementBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStatementBody=ruleStatementBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStatementBody; 
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
    // $ANTLR end "entryRuleStatementBody"


    // $ANTLR start "ruleStatementBody"
    // InternalKactors.g:1728:1: ruleStatementBody returns [EObject current=null] : ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) ) ;
    public final EObject ruleStatementBody() throws RecognitionException {
        EObject current = null;

        EObject lv_verb_0_0 = null;

        EObject lv_value_1_0 = null;

        EObject lv_group_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1734:2: ( ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) ) )
            // InternalKactors.g:1735:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )
            {
            // InternalKactors.g:1735:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )
            int alt30=3;
            alt30 = dfa30.predict(input);
            switch (alt30) {
                case 1 :
                    // InternalKactors.g:1736:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1736:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    // InternalKactors.g:1737:4: (lv_verb_0_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1737:4: (lv_verb_0_0= ruleMessageCall )
                    // InternalKactors.g:1738:5: lv_verb_0_0= ruleMessageCall
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementBodyAccess().getVerbMessageCallParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_verb_0_0=ruleMessageCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementBodyRule());
                      					}
                      					set(
                      						current,
                      						"verb",
                      						lv_verb_0_0,
                      						"org.integratedmodelling.kactors.Kactors.MessageCall");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:1756:3: ( (lv_value_1_0= ruleValue ) )
                    {
                    // InternalKactors.g:1756:3: ( (lv_value_1_0= ruleValue ) )
                    // InternalKactors.g:1757:4: (lv_value_1_0= ruleValue )
                    {
                    // InternalKactors.g:1757:4: (lv_value_1_0= ruleValue )
                    // InternalKactors.g:1758:5: lv_value_1_0= ruleValue
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementBodyAccess().getValueValueParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_1_0=ruleValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementBodyRule());
                      					}
                      					set(
                      						current,
                      						"value",
                      						lv_value_1_0,
                      						"org.integratedmodelling.kactors.Kactors.Value");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:1776:3: ( (lv_group_2_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1776:3: ( (lv_group_2_0= ruleStatementGroup ) )
                    // InternalKactors.g:1777:4: (lv_group_2_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1777:4: (lv_group_2_0= ruleStatementGroup )
                    // InternalKactors.g:1778:5: lv_group_2_0= ruleStatementGroup
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementBodyAccess().getGroupStatementGroupParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_group_2_0=ruleStatementGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementBodyRule());
                      					}
                      					set(
                      						current,
                      						"group",
                      						lv_group_2_0,
                      						"org.integratedmodelling.kactors.Kactors.StatementGroup");
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
    // $ANTLR end "ruleStatementBody"


    // $ANTLR start "entryRuleWhileStatement"
    // InternalKactors.g:1799:1: entryRuleWhileStatement returns [EObject current=null] : iv_ruleWhileStatement= ruleWhileStatement EOF ;
    public final EObject entryRuleWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhileStatement = null;


        try {
            // InternalKactors.g:1799:55: (iv_ruleWhileStatement= ruleWhileStatement EOF )
            // InternalKactors.g:1800:2: iv_ruleWhileStatement= ruleWhileStatement EOF
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
    // InternalKactors.g:1806:1: ruleWhileStatement returns [EObject current=null] : (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) ) ;
    public final EObject ruleWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_expression_1_0=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1812:2: ( (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) ) )
            // InternalKactors.g:1813:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) )
            {
            // InternalKactors.g:1813:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) )
            // InternalKactors.g:1814:3: otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) )
            {
            otherlv_0=(Token)match(input,47,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getWhileStatementAccess().getWhileKeyword_0());
              		
            }
            // InternalKactors.g:1818:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:1819:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:1819:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:1820:5: lv_expression_1_0= RULE_EXPR
            {
            lv_expression_1_0=(Token)match(input,RULE_EXPR,FOLLOW_15); if (state.failed) return current;
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

            // InternalKactors.g:1836:3: ( (lv_body_2_0= ruleStatementBody ) )
            // InternalKactors.g:1837:4: (lv_body_2_0= ruleStatementBody )
            {
            // InternalKactors.g:1837:4: (lv_body_2_0= ruleStatementBody )
            // InternalKactors.g:1838:5: lv_body_2_0= ruleStatementBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getWhileStatementAccess().getBodyStatementBodyParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_body_2_0=ruleStatementBody();

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
              						"org.integratedmodelling.kactors.Kactors.StatementBody");
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
    // InternalKactors.g:1859:1: entryRuleDoStatement returns [EObject current=null] : iv_ruleDoStatement= ruleDoStatement EOF ;
    public final EObject entryRuleDoStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDoStatement = null;


        try {
            // InternalKactors.g:1859:52: (iv_ruleDoStatement= ruleDoStatement EOF )
            // InternalKactors.g:1860:2: iv_ruleDoStatement= ruleDoStatement EOF
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
    // InternalKactors.g:1866:1: ruleDoStatement returns [EObject current=null] : (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) ;
    public final EObject ruleDoStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_expression_3_0=null;
        EObject lv_body_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1872:2: ( (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) )
            // InternalKactors.g:1873:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            {
            // InternalKactors.g:1873:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            // InternalKactors.g:1874:3: otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) )
            {
            otherlv_0=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getDoStatementAccess().getDoKeyword_0());
              		
            }
            // InternalKactors.g:1878:3: ( (lv_body_1_0= ruleStatementBody ) )
            // InternalKactors.g:1879:4: (lv_body_1_0= ruleStatementBody )
            {
            // InternalKactors.g:1879:4: (lv_body_1_0= ruleStatementBody )
            // InternalKactors.g:1880:5: lv_body_1_0= ruleStatementBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDoStatementAccess().getBodyStatementBodyParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_28);
            lv_body_1_0=ruleStatementBody();

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
              						"org.integratedmodelling.kactors.Kactors.StatementBody");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,47,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getDoStatementAccess().getWhileKeyword_2());
              		
            }
            // InternalKactors.g:1901:3: ( (lv_expression_3_0= RULE_EXPR ) )
            // InternalKactors.g:1902:4: (lv_expression_3_0= RULE_EXPR )
            {
            // InternalKactors.g:1902:4: (lv_expression_3_0= RULE_EXPR )
            // InternalKactors.g:1903:5: lv_expression_3_0= RULE_EXPR
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
    // InternalKactors.g:1923:1: entryRuleForStatement returns [EObject current=null] : iv_ruleForStatement= ruleForStatement EOF ;
    public final EObject entryRuleForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleForStatement = null;


        try {
            // InternalKactors.g:1923:53: (iv_ruleForStatement= ruleForStatement EOF )
            // InternalKactors.g:1924:2: iv_ruleForStatement= ruleForStatement EOF
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
    // InternalKactors.g:1930:1: ruleForStatement returns [EObject current=null] : (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) ) ;
    public final EObject ruleForStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_id_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;

        EObject lv_body_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1936:2: ( (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) ) )
            // InternalKactors.g:1937:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) )
            {
            // InternalKactors.g:1937:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) )
            // InternalKactors.g:1938:3: otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) )
            {
            otherlv_0=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getForStatementAccess().getForKeyword_0());
              		
            }
            // InternalKactors.g:1942:3: ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==RULE_LOWERCASE_ID) ) {
                int LA31_1 = input.LA(2);

                if ( (LA31_1==50) ) {
                    alt31=1;
                }
            }
            switch (alt31) {
                case 1 :
                    // InternalKactors.g:1943:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in'
                    {
                    // InternalKactors.g:1943:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:1944:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:1944:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:1945:6: lv_id_1_0= RULE_LOWERCASE_ID
                    {
                    lv_id_1_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_29); if (state.failed) return current;
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

                    otherlv_2=(Token)match(input,50,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getForStatementAccess().getInKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:1966:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:1967:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:1967:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:1968:5: lv_value_3_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getForStatementAccess().getValueValueParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_15);
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

            // InternalKactors.g:1985:3: ( (lv_body_4_0= ruleStatementBody ) )
            // InternalKactors.g:1986:4: (lv_body_4_0= ruleStatementBody )
            {
            // InternalKactors.g:1986:4: (lv_body_4_0= ruleStatementBody )
            // InternalKactors.g:1987:5: lv_body_4_0= ruleStatementBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getForStatementAccess().getBodyStatementBodyParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_body_4_0=ruleStatementBody();

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
              						"org.integratedmodelling.kactors.Kactors.StatementBody");
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
    // InternalKactors.g:2008:1: entryRuleActions returns [EObject current=null] : iv_ruleActions= ruleActions EOF ;
    public final EObject entryRuleActions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActions = null;


        try {
            // InternalKactors.g:2008:48: (iv_ruleActions= ruleActions EOF )
            // InternalKactors.g:2009:2: iv_ruleActions= ruleActions EOF
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
    // InternalKactors.g:2015:1: ruleActions returns [EObject current=null] : ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) ) ;
    public final EObject ruleActions() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject lv_match_0_0 = null;

        EObject lv_matches_2_0 = null;

        EObject lv_matches_3_0 = null;

        EObject lv_statement_5_0 = null;

        EObject lv_statements_7_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2021:2: ( ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) ) )
            // InternalKactors.g:2022:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )
            {
            // InternalKactors.g:2022:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )
            int alt33=4;
            alt33 = dfa33.predict(input);
            switch (alt33) {
                case 1 :
                    // InternalKactors.g:2023:3: ( (lv_match_0_0= ruleMatch ) )
                    {
                    // InternalKactors.g:2023:3: ( (lv_match_0_0= ruleMatch ) )
                    // InternalKactors.g:2024:4: (lv_match_0_0= ruleMatch )
                    {
                    // InternalKactors.g:2024:4: (lv_match_0_0= ruleMatch )
                    // InternalKactors.g:2025:5: lv_match_0_0= ruleMatch
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_match_0_0=ruleMatch();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
                      					}
                      					set(
                      						current,
                      						"match",
                      						lv_match_0_0,
                      						"org.integratedmodelling.kactors.Kactors.Match");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:2043:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
                    {
                    // InternalKactors.g:2043:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
                    // InternalKactors.g:2044:4: otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')'
                    {
                    otherlv_1=(Token)match(input,43,FOLLOW_30); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:2048:4: ( (lv_matches_2_0= ruleMatch ) )
                    // InternalKactors.g:2049:5: (lv_matches_2_0= ruleMatch )
                    {
                    // InternalKactors.g:2049:5: (lv_matches_2_0= ruleMatch )
                    // InternalKactors.g:2050:6: lv_matches_2_0= ruleMatch
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_31);
                    lv_matches_2_0=ruleMatch();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionsRule());
                      						}
                      						add(
                      							current,
                      							"matches",
                      							lv_matches_2_0,
                      							"org.integratedmodelling.kactors.Kactors.Match");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:2067:4: ( (lv_matches_3_0= ruleMatch ) )*
                    loop32:
                    do {
                        int alt32=2;
                        int LA32_0 = input.LA(1);

                        if ( ((LA32_0>=RULE_OBSERVABLE && LA32_0<=RULE_LOWERCASE_ID)||LA32_0==RULE_STRING||(LA32_0>=RULE_EXPR && LA32_0<=RULE_INT)||LA32_0==43||LA32_0==50||(LA32_0>=52 && LA32_0<=53)||(LA32_0>=57 && LA32_0<=59)||(LA32_0>=77 && LA32_0<=78)) ) {
                            alt32=1;
                        }


                        switch (alt32) {
                    	case 1 :
                    	    // InternalKactors.g:2068:5: (lv_matches_3_0= ruleMatch )
                    	    {
                    	    // InternalKactors.g:2068:5: (lv_matches_3_0= ruleMatch )
                    	    // InternalKactors.g:2069:6: lv_matches_3_0= ruleMatch
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_1_2_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_31);
                    	    lv_matches_3_0=ruleMatch();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getActionsRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"matches",
                    	      							lv_matches_3_0,
                    	      							"org.integratedmodelling.kactors.Kactors.Match");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop32;
                        }
                    } while (true);

                    otherlv_4=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getActionsAccess().getRightParenthesisKeyword_1_3());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:2092:3: ( (lv_statement_5_0= ruleStatement ) )
                    {
                    // InternalKactors.g:2092:3: ( (lv_statement_5_0= ruleStatement ) )
                    // InternalKactors.g:2093:4: (lv_statement_5_0= ruleStatement )
                    {
                    // InternalKactors.g:2093:4: (lv_statement_5_0= ruleStatement )
                    // InternalKactors.g:2094:5: lv_statement_5_0= ruleStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getStatementStatementParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_statement_5_0=ruleStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
                      					}
                      					set(
                      						current,
                      						"statement",
                      						lv_statement_5_0,
                      						"org.integratedmodelling.kactors.Kactors.Statement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:2112:3: (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' )
                    {
                    // InternalKactors.g:2112:3: (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' )
                    // InternalKactors.g:2113:4: otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')'
                    {
                    otherlv_6=(Token)match(input,43,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:2117:4: ( (lv_statements_7_0= ruleStatementList ) )
                    // InternalKactors.g:2118:5: (lv_statements_7_0= ruleStatementList )
                    {
                    // InternalKactors.g:2118:5: (lv_statements_7_0= ruleStatementList )
                    // InternalKactors.g:2119:6: lv_statements_7_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionsAccess().getStatementsStatementListParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_21);
                    lv_statements_7_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionsRule());
                      						}
                      						set(
                      							current,
                      							"statements",
                      							lv_statements_7_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_8=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getActionsAccess().getRightParenthesisKeyword_3_2());
                      			
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
    // InternalKactors.g:2145:1: entryRuleMatch returns [EObject current=null] : iv_ruleMatch= ruleMatch EOF ;
    public final EObject entryRuleMatch() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMatch = null;


        try {
            // InternalKactors.g:2145:46: (iv_ruleMatch= ruleMatch EOF )
            // InternalKactors.g:2146:2: iv_ruleMatch= ruleMatch EOF
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
    // InternalKactors.g:2152:1: ruleMatch returns [EObject current=null] : ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) ) ;
    public final EObject ruleMatch() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_boolean_3_1=null;
        Token lv_boolean_3_2=null;
        Token otherlv_4=null;
        Token lv_type_6_0=null;
        Token otherlv_7=null;
        Token lv_regexp_9_0=null;
        Token otherlv_10=null;
        Token lv_observable_12_0=null;
        Token otherlv_13=null;
        Token otherlv_16=null;
        Token lv_text_18_0=null;
        Token otherlv_19=null;
        Token otherlv_22=null;
        Token lv_leftLimit_25_0=null;
        Token otherlv_26=null;
        Token otherlv_27=null;
        Token lv_rightLimit_29_0=null;
        Token otherlv_30=null;
        Token otherlv_31=null;
        Token otherlv_33=null;
        Token otherlv_35=null;
        Token otherlv_38=null;
        Token otherlv_41=null;
        Token lv_expr_43_0=null;
        Token otherlv_44=null;
        Token lv_nodata_46_0=null;
        Token otherlv_47=null;
        Token lv_star_49_0=null;
        Token otherlv_50=null;
        Token lv_anything_52_0=null;
        Token otherlv_53=null;
        EObject lv_body_2_0 = null;

        EObject lv_body_5_0 = null;

        EObject lv_body_8_0 = null;

        EObject lv_body_11_0 = null;

        EObject lv_body_14_0 = null;

        EObject lv_literal_15_0 = null;

        EObject lv_body_17_0 = null;

        EObject lv_body_20_0 = null;

        EObject lv_arguments_21_0 = null;

        EObject lv_body_23_0 = null;

        EObject lv_int0_24_0 = null;

        EObject lv_int1_28_0 = null;

        EObject lv_body_32_0 = null;

        EObject lv_set_34_0 = null;

        EObject lv_body_36_0 = null;

        EObject lv_quantity_37_0 = null;

        EObject lv_body_39_0 = null;

        EObject lv_date_40_0 = null;

        EObject lv_body_42_0 = null;

        EObject lv_body_45_0 = null;

        EObject lv_body_48_0 = null;

        EObject lv_body_51_0 = null;

        EObject lv_body_54_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2158:2: ( ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) ) )
            // InternalKactors.g:2159:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) )
            {
            // InternalKactors.g:2159:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) )
            int alt37=16;
            alt37 = dfa37.predict(input);
            switch (alt37) {
                case 1 :
                    // InternalKactors.g:2160:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2160:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) )
                    // InternalKactors.g:2161:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2161:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:2162:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:2162:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:2163:6: lv_id_0_0= RULE_LOWERCASE_ID
                    {
                    lv_id_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_32); if (state.failed) return current;
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

                    otherlv_1=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_0_1());
                      			
                    }
                    // InternalKactors.g:2183:4: ( (lv_body_2_0= ruleStatementList ) )
                    // InternalKactors.g:2184:5: (lv_body_2_0= ruleStatementList )
                    {
                    // InternalKactors.g:2184:5: (lv_body_2_0= ruleStatementList )
                    // InternalKactors.g:2185:6: lv_body_2_0= ruleStatementList
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
                    // InternalKactors.g:2204:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2204:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
                    // InternalKactors.g:2205:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2205:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) )
                    // InternalKactors.g:2206:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
                    {
                    // InternalKactors.g:2206:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
                    // InternalKactors.g:2207:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
                    {
                    // InternalKactors.g:2207:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==52) ) {
                        alt34=1;
                    }
                    else if ( (LA34_0==53) ) {
                        alt34=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 34, 0, input);

                        throw nvae;
                    }
                    switch (alt34) {
                        case 1 :
                            // InternalKactors.g:2208:7: lv_boolean_3_1= 'true'
                            {
                            lv_boolean_3_1=(Token)match(input,52,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_boolean_3_1, grammarAccess.getMatchAccess().getBooleanTrueKeyword_1_0_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getMatchRule());
                              							}
                              							setWithLastConsumed(current, "boolean", lv_boolean_3_1, null);
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:2219:7: lv_boolean_3_2= 'false'
                            {
                            lv_boolean_3_2=(Token)match(input,53,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_boolean_3_2, grammarAccess.getMatchAccess().getBooleanFalseKeyword_1_0_0_1());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getMatchRule());
                              							}
                              							setWithLastConsumed(current, "boolean", lv_boolean_3_2, null);
                              						
                            }

                            }
                            break;

                    }


                    }


                    }

                    otherlv_4=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:2236:4: ( (lv_body_5_0= ruleStatementList ) )
                    // InternalKactors.g:2237:5: (lv_body_5_0= ruleStatementList )
                    {
                    // InternalKactors.g:2237:5: (lv_body_5_0= ruleStatementList )
                    // InternalKactors.g:2238:6: lv_body_5_0= ruleStatementList
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
                    // InternalKactors.g:2257:3: ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2257:3: ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) )
                    // InternalKactors.g:2258:4: ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2258:4: ( (lv_type_6_0= RULE_CAMELCASE_ID ) )
                    // InternalKactors.g:2259:5: (lv_type_6_0= RULE_CAMELCASE_ID )
                    {
                    // InternalKactors.g:2259:5: (lv_type_6_0= RULE_CAMELCASE_ID )
                    // InternalKactors.g:2260:6: lv_type_6_0= RULE_CAMELCASE_ID
                    {
                    lv_type_6_0=(Token)match(input,RULE_CAMELCASE_ID,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_type_6_0, grammarAccess.getMatchAccess().getTypeCAMELCASE_IDTerminalRuleCall_2_0_0());
                      					
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

                    otherlv_7=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_2_1());
                      			
                    }
                    // InternalKactors.g:2280:4: ( (lv_body_8_0= ruleStatementList ) )
                    // InternalKactors.g:2281:5: (lv_body_8_0= ruleStatementList )
                    {
                    // InternalKactors.g:2281:5: (lv_body_8_0= ruleStatementList )
                    // InternalKactors.g:2282:6: lv_body_8_0= ruleStatementList
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
                    // InternalKactors.g:2301:3: ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2301:3: ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) )
                    // InternalKactors.g:2302:4: ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2302:4: ( (lv_regexp_9_0= RULE_REGEXP ) )
                    // InternalKactors.g:2303:5: (lv_regexp_9_0= RULE_REGEXP )
                    {
                    // InternalKactors.g:2303:5: (lv_regexp_9_0= RULE_REGEXP )
                    // InternalKactors.g:2304:6: lv_regexp_9_0= RULE_REGEXP
                    {
                    lv_regexp_9_0=(Token)match(input,RULE_REGEXP,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_regexp_9_0, grammarAccess.getMatchAccess().getRegexpREGEXPTerminalRuleCall_3_0_0());
                      					
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

                    otherlv_10=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_3_1());
                      			
                    }
                    // InternalKactors.g:2324:4: ( (lv_body_11_0= ruleStatementList ) )
                    // InternalKactors.g:2325:5: (lv_body_11_0= ruleStatementList )
                    {
                    // InternalKactors.g:2325:5: (lv_body_11_0= ruleStatementList )
                    // InternalKactors.g:2326:6: lv_body_11_0= ruleStatementList
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
                    // InternalKactors.g:2345:3: ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2345:3: ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
                    // InternalKactors.g:2346:4: ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2346:4: ( (lv_observable_12_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:2347:5: (lv_observable_12_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:2347:5: (lv_observable_12_0= RULE_OBSERVABLE )
                    // InternalKactors.g:2348:6: lv_observable_12_0= RULE_OBSERVABLE
                    {
                    lv_observable_12_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_observable_12_0, grammarAccess.getMatchAccess().getObservableOBSERVABLETerminalRuleCall_4_0_0());
                      					
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

                    otherlv_13=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_4_1());
                      			
                    }
                    // InternalKactors.g:2368:4: ( (lv_body_14_0= ruleStatementList ) )
                    // InternalKactors.g:2369:5: (lv_body_14_0= ruleStatementList )
                    {
                    // InternalKactors.g:2369:5: (lv_body_14_0= ruleStatementList )
                    // InternalKactors.g:2370:6: lv_body_14_0= ruleStatementList
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
                    // InternalKactors.g:2389:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2389:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
                    // InternalKactors.g:2390:4: ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2390:4: ( (lv_literal_15_0= ruleLiteral ) )
                    // InternalKactors.g:2391:5: (lv_literal_15_0= ruleLiteral )
                    {
                    // InternalKactors.g:2391:5: (lv_literal_15_0= ruleLiteral )
                    // InternalKactors.g:2392:6: lv_literal_15_0= ruleLiteral
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_literal_15_0=ruleLiteral();

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

                    otherlv_16=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_5_1());
                      			
                    }
                    // InternalKactors.g:2413:4: ( (lv_body_17_0= ruleStatementList ) )
                    // InternalKactors.g:2414:5: (lv_body_17_0= ruleStatementList )
                    {
                    // InternalKactors.g:2414:5: (lv_body_17_0= ruleStatementList )
                    // InternalKactors.g:2415:6: lv_body_17_0= ruleStatementList
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
                    // InternalKactors.g:2434:3: ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2434:3: ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
                    // InternalKactors.g:2435:4: ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2435:4: ( (lv_text_18_0= RULE_STRING ) )
                    // InternalKactors.g:2436:5: (lv_text_18_0= RULE_STRING )
                    {
                    // InternalKactors.g:2436:5: (lv_text_18_0= RULE_STRING )
                    // InternalKactors.g:2437:6: lv_text_18_0= RULE_STRING
                    {
                    lv_text_18_0=(Token)match(input,RULE_STRING,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_text_18_0, grammarAccess.getMatchAccess().getTextSTRINGTerminalRuleCall_6_0_0());
                      					
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

                    otherlv_19=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_19, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_6_1());
                      			
                    }
                    // InternalKactors.g:2457:4: ( (lv_body_20_0= ruleStatementList ) )
                    // InternalKactors.g:2458:5: (lv_body_20_0= ruleStatementList )
                    {
                    // InternalKactors.g:2458:5: (lv_body_20_0= ruleStatementList )
                    // InternalKactors.g:2459:6: lv_body_20_0= ruleStatementList
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
                    // InternalKactors.g:2478:3: ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2478:3: ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) )
                    // InternalKactors.g:2479:4: ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2479:4: ( (lv_arguments_21_0= ruleArgumentDeclaration ) )
                    // InternalKactors.g:2480:5: (lv_arguments_21_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:2480:5: (lv_arguments_21_0= ruleArgumentDeclaration )
                    // InternalKactors.g:2481:6: lv_arguments_21_0= ruleArgumentDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getArgumentsArgumentDeclarationParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_arguments_21_0=ruleArgumentDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"arguments",
                      							lv_arguments_21_0,
                      							"org.integratedmodelling.kactors.Kactors.ArgumentDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_22=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_22, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_7_1());
                      			
                    }
                    // InternalKactors.g:2502:4: ( (lv_body_23_0= ruleStatementList ) )
                    // InternalKactors.g:2503:5: (lv_body_23_0= ruleStatementList )
                    {
                    // InternalKactors.g:2503:5: (lv_body_23_0= ruleStatementList )
                    // InternalKactors.g:2504:6: lv_body_23_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_7_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_23_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_23_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:2523:3: ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2523:3: ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) )
                    // InternalKactors.g:2524:4: ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2524:4: ( (lv_int0_24_0= ruleNumber ) )
                    // InternalKactors.g:2525:5: (lv_int0_24_0= ruleNumber )
                    {
                    // InternalKactors.g:2525:5: (lv_int0_24_0= ruleNumber )
                    // InternalKactors.g:2526:6: lv_int0_24_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getInt0NumberParserRuleCall_8_0_0());
                      					
                    }
                    pushFollow(FOLLOW_33);
                    lv_int0_24_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"int0",
                      							lv_int0_24_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:2543:4: ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )?
                    int alt35=3;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==54) ) {
                        alt35=1;
                    }
                    else if ( (LA35_0==55) ) {
                        alt35=2;
                    }
                    switch (alt35) {
                        case 1 :
                            // InternalKactors.g:2544:5: ( (lv_leftLimit_25_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:2544:5: ( (lv_leftLimit_25_0= 'inclusive' ) )
                            // InternalKactors.g:2545:6: (lv_leftLimit_25_0= 'inclusive' )
                            {
                            // InternalKactors.g:2545:6: (lv_leftLimit_25_0= 'inclusive' )
                            // InternalKactors.g:2546:7: lv_leftLimit_25_0= 'inclusive'
                            {
                            lv_leftLimit_25_0=(Token)match(input,54,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_leftLimit_25_0, grammarAccess.getMatchAccess().getLeftLimitInclusiveKeyword_8_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getMatchRule());
                              							}
                              							setWithLastConsumed(current, "leftLimit", lv_leftLimit_25_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:2559:5: otherlv_26= 'exclusive'
                            {
                            otherlv_26=(Token)match(input,55,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_26, grammarAccess.getMatchAccess().getExclusiveKeyword_8_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:2564:4: ( ( 'to' )=>otherlv_27= 'to' )
                    // InternalKactors.g:2565:5: ( 'to' )=>otherlv_27= 'to'
                    {
                    otherlv_27=(Token)match(input,56,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_27, grammarAccess.getMatchAccess().getToKeyword_8_2());
                      				
                    }

                    }

                    // InternalKactors.g:2571:4: ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) )
                    // InternalKactors.g:2572:5: ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber )
                    {
                    // InternalKactors.g:2576:5: (lv_int1_28_0= ruleNumber )
                    // InternalKactors.g:2577:6: lv_int1_28_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getInt1NumberParserRuleCall_8_3_0());
                      					
                    }
                    pushFollow(FOLLOW_36);
                    lv_int1_28_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"int1",
                      							lv_int1_28_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:2594:4: ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )?
                    int alt36=3;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==54) ) {
                        alt36=1;
                    }
                    else if ( (LA36_0==55) ) {
                        alt36=2;
                    }
                    switch (alt36) {
                        case 1 :
                            // InternalKactors.g:2595:5: ( (lv_rightLimit_29_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:2595:5: ( (lv_rightLimit_29_0= 'inclusive' ) )
                            // InternalKactors.g:2596:6: (lv_rightLimit_29_0= 'inclusive' )
                            {
                            // InternalKactors.g:2596:6: (lv_rightLimit_29_0= 'inclusive' )
                            // InternalKactors.g:2597:7: lv_rightLimit_29_0= 'inclusive'
                            {
                            lv_rightLimit_29_0=(Token)match(input,54,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_rightLimit_29_0, grammarAccess.getMatchAccess().getRightLimitInclusiveKeyword_8_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getMatchRule());
                              							}
                              							setWithLastConsumed(current, "rightLimit", lv_rightLimit_29_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:2610:5: otherlv_30= 'exclusive'
                            {
                            otherlv_30=(Token)match(input,55,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_30, grammarAccess.getMatchAccess().getExclusiveKeyword_8_4_1());
                              				
                            }

                            }
                            break;

                    }

                    otherlv_31=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_31, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_8_5());
                      			
                    }
                    // InternalKactors.g:2619:4: ( (lv_body_32_0= ruleStatementList ) )
                    // InternalKactors.g:2620:5: (lv_body_32_0= ruleStatementList )
                    {
                    // InternalKactors.g:2620:5: (lv_body_32_0= ruleStatementList )
                    // InternalKactors.g:2621:6: lv_body_32_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_8_6_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_32_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_32_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKactors.g:2640:3: (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2640:3: (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
                    // InternalKactors.g:2641:4: otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) )
                    {
                    otherlv_33=(Token)match(input,50,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_33, grammarAccess.getMatchAccess().getInKeyword_9_0());
                      			
                    }
                    // InternalKactors.g:2645:4: ( (lv_set_34_0= ruleList ) )
                    // InternalKactors.g:2646:5: (lv_set_34_0= ruleList )
                    {
                    // InternalKactors.g:2646:5: (lv_set_34_0= ruleList )
                    // InternalKactors.g:2647:6: lv_set_34_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getSetListParserRuleCall_9_1_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_set_34_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"set",
                      							lv_set_34_0,
                      							"org.integratedmodelling.kactors.Kactors.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_35=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_35, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_9_2());
                      			
                    }
                    // InternalKactors.g:2668:4: ( (lv_body_36_0= ruleStatementList ) )
                    // InternalKactors.g:2669:5: (lv_body_36_0= ruleStatementList )
                    {
                    // InternalKactors.g:2669:5: (lv_body_36_0= ruleStatementList )
                    // InternalKactors.g:2670:6: lv_body_36_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_9_3_0());
                      					
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
                    // InternalKactors.g:2689:3: ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2689:3: ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
                    // InternalKactors.g:2690:4: ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2690:4: ( (lv_quantity_37_0= ruleQuantity ) )
                    // InternalKactors.g:2691:5: (lv_quantity_37_0= ruleQuantity )
                    {
                    // InternalKactors.g:2691:5: (lv_quantity_37_0= ruleQuantity )
                    // InternalKactors.g:2692:6: lv_quantity_37_0= ruleQuantity
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getQuantityQuantityParserRuleCall_10_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_quantity_37_0=ruleQuantity();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"quantity",
                      							lv_quantity_37_0,
                      							"org.integratedmodelling.kactors.Kactors.Quantity");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_38=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_38, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_10_1());
                      			
                    }
                    // InternalKactors.g:2713:4: ( (lv_body_39_0= ruleStatementList ) )
                    // InternalKactors.g:2714:5: (lv_body_39_0= ruleStatementList )
                    {
                    // InternalKactors.g:2714:5: (lv_body_39_0= ruleStatementList )
                    // InternalKactors.g:2715:6: lv_body_39_0= ruleStatementList
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
                    // InternalKactors.g:2734:3: ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2734:3: ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
                    // InternalKactors.g:2735:4: ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2735:4: ( (lv_date_40_0= ruleDate ) )
                    // InternalKactors.g:2736:5: (lv_date_40_0= ruleDate )
                    {
                    // InternalKactors.g:2736:5: (lv_date_40_0= ruleDate )
                    // InternalKactors.g:2737:6: lv_date_40_0= ruleDate
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getDateDateParserRuleCall_11_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_date_40_0=ruleDate();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"date",
                      							lv_date_40_0,
                      							"org.integratedmodelling.kactors.Kactors.Date");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_41=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_41, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_11_1());
                      			
                    }
                    // InternalKactors.g:2758:4: ( (lv_body_42_0= ruleStatementList ) )
                    // InternalKactors.g:2759:5: (lv_body_42_0= ruleStatementList )
                    {
                    // InternalKactors.g:2759:5: (lv_body_42_0= ruleStatementList )
                    // InternalKactors.g:2760:6: lv_body_42_0= ruleStatementList
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
                    // InternalKactors.g:2779:3: ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2779:3: ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) )
                    // InternalKactors.g:2780:4: ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2780:4: ( (lv_expr_43_0= RULE_EXPR ) )
                    // InternalKactors.g:2781:5: (lv_expr_43_0= RULE_EXPR )
                    {
                    // InternalKactors.g:2781:5: (lv_expr_43_0= RULE_EXPR )
                    // InternalKactors.g:2782:6: lv_expr_43_0= RULE_EXPR
                    {
                    lv_expr_43_0=(Token)match(input,RULE_EXPR,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_expr_43_0, grammarAccess.getMatchAccess().getExprEXPRTerminalRuleCall_12_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"expr",
                      							lv_expr_43_0,
                      							"org.integratedmodelling.kactors.Kactors.EXPR");
                      					
                    }

                    }


                    }

                    otherlv_44=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_44, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_12_1());
                      			
                    }
                    // InternalKactors.g:2802:4: ( (lv_body_45_0= ruleStatementList ) )
                    // InternalKactors.g:2803:5: (lv_body_45_0= ruleStatementList )
                    {
                    // InternalKactors.g:2803:5: (lv_body_45_0= ruleStatementList )
                    // InternalKactors.g:2804:6: lv_body_45_0= ruleStatementList
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
                    // InternalKactors.g:2823:3: ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2823:3: ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) )
                    // InternalKactors.g:2824:4: ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2824:4: ( (lv_nodata_46_0= 'unknown' ) )
                    // InternalKactors.g:2825:5: (lv_nodata_46_0= 'unknown' )
                    {
                    // InternalKactors.g:2825:5: (lv_nodata_46_0= 'unknown' )
                    // InternalKactors.g:2826:6: lv_nodata_46_0= 'unknown'
                    {
                    lv_nodata_46_0=(Token)match(input,57,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_nodata_46_0, grammarAccess.getMatchAccess().getNodataUnknownKeyword_13_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "nodata", lv_nodata_46_0, "unknown");
                      					
                    }

                    }


                    }

                    otherlv_47=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_47, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_13_1());
                      			
                    }
                    // InternalKactors.g:2842:4: ( (lv_body_48_0= ruleStatementList ) )
                    // InternalKactors.g:2843:5: (lv_body_48_0= ruleStatementList )
                    {
                    // InternalKactors.g:2843:5: (lv_body_48_0= ruleStatementList )
                    // InternalKactors.g:2844:6: lv_body_48_0= ruleStatementList
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
                    // InternalKactors.g:2863:3: ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2863:3: ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) )
                    // InternalKactors.g:2864:4: ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2864:4: ( (lv_star_49_0= '*' ) )
                    // InternalKactors.g:2865:5: (lv_star_49_0= '*' )
                    {
                    // InternalKactors.g:2865:5: (lv_star_49_0= '*' )
                    // InternalKactors.g:2866:6: lv_star_49_0= '*'
                    {
                    lv_star_49_0=(Token)match(input,58,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_star_49_0, grammarAccess.getMatchAccess().getStarAsteriskKeyword_14_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "star", true, "*");
                      					
                    }

                    }


                    }

                    otherlv_50=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_50, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_14_1());
                      			
                    }
                    // InternalKactors.g:2882:4: ( (lv_body_51_0= ruleStatementList ) )
                    // InternalKactors.g:2883:5: (lv_body_51_0= ruleStatementList )
                    {
                    // InternalKactors.g:2883:5: (lv_body_51_0= ruleStatementList )
                    // InternalKactors.g:2884:6: lv_body_51_0= ruleStatementList
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
                case 16 :
                    // InternalKactors.g:2903:3: ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2903:3: ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) )
                    // InternalKactors.g:2904:4: ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2904:4: ( (lv_anything_52_0= '#' ) )
                    // InternalKactors.g:2905:5: (lv_anything_52_0= '#' )
                    {
                    // InternalKactors.g:2905:5: (lv_anything_52_0= '#' )
                    // InternalKactors.g:2906:6: lv_anything_52_0= '#'
                    {
                    lv_anything_52_0=(Token)match(input,59,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_anything_52_0, grammarAccess.getMatchAccess().getAnythingNumberSignKeyword_15_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "anything", true, "#");
                      					
                    }

                    }


                    }

                    otherlv_53=(Token)match(input,51,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_53, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_15_1());
                      			
                    }
                    // InternalKactors.g:2922:4: ( (lv_body_54_0= ruleStatementList ) )
                    // InternalKactors.g:2923:5: (lv_body_54_0= ruleStatementList )
                    {
                    // InternalKactors.g:2923:5: (lv_body_54_0= ruleStatementList )
                    // InternalKactors.g:2924:6: lv_body_54_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_15_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_54_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_54_0,
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


    // $ANTLR start "entryRuleUrnId"
    // InternalKactors.g:2946:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKactors.g:2946:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKactors.g:2947:2: iv_ruleUrnId= ruleUrnId EOF
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
    // InternalKactors.g:2953:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) ;
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
            // InternalKactors.g:2959:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) )
            // InternalKactors.g:2960:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            {
            // InternalKactors.g:2960:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            // InternalKactors.g:2961:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            {
            // InternalKactors.g:2961:3: (kw= 'urn:klab:' )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==60) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalKactors.g:2962:4: kw= 'urn:klab:'
                    {
                    kw=(Token)match(input,60,FOLLOW_4); if (state.failed) return current;
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
            pushFollow(FOLLOW_14);
            this_PathName_1=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_1);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,42,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_14);
            this_PathName_3=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_3);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,42,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_4());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_14);
            this_PathName_5=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_5);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,42,FOLLOW_37); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            // InternalKactors.g:3013:3: (this_Path_7= rulePath | this_INT_8= RULE_INT )
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==RULE_LOWERCASE_ID||LA39_0==RULE_UPPERCASE_ID) ) {
                alt39=1;
            }
            else if ( (LA39_0==RULE_INT) ) {
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
                    // InternalKactors.g:3014:4: this_Path_7= rulePath
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_7_0());
                      			
                    }
                    pushFollow(FOLLOW_38);
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
                    // InternalKactors.g:3025:4: this_INT_8= RULE_INT
                    {
                    this_INT_8=(Token)match(input,RULE_INT,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_8);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_8, grammarAccess.getUrnIdAccess().getINTTerminalRuleCall_7_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:3033:3: (kw= ':' this_VersionNumber_10= ruleVersionNumber )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==42) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalKactors.g:3034:4: kw= ':' this_VersionNumber_10= ruleVersionNumber
                    {
                    kw=(Token)match(input,42,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_8_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getVersionNumberParserRuleCall_8_1());
                      			
                    }
                    pushFollow(FOLLOW_39);
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

            // InternalKactors.g:3050:3: (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==59) ) {
                int LA44_1 = input.LA(2);

                if ( (LA44_1==RULE_LOWERCASE_ID||LA44_1==RULE_UPPERCASE_ID) ) {
                    alt44=1;
                }
            }
            switch (alt44) {
                case 1 :
                    // InternalKactors.g:3051:4: kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    {
                    kw=(Token)match(input,59,FOLLOW_40); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getNumberSignKeyword_9_0());
                      			
                    }
                    // InternalKactors.g:3056:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )
                    int alt41=2;
                    alt41 = dfa41.predict(input);
                    switch (alt41) {
                        case 1 :
                            // InternalKactors.g:3057:5: this_Path_12= rulePath
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_1_0());
                              				
                            }
                            pushFollow(FOLLOW_41);
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
                            // InternalKactors.g:3068:5: this_UrnKvp_13= ruleUrnKvp
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_1_1());
                              				
                            }
                            pushFollow(FOLLOW_41);
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

                    // InternalKactors.g:3079:4: (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    loop43:
                    do {
                        int alt43=2;
                        int LA43_0 = input.LA(1);

                        if ( (LA43_0==61) ) {
                            alt43=1;
                        }


                        switch (alt43) {
                    	case 1 :
                    	    // InternalKactors.g:3080:5: kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    {
                    	    kw=(Token)match(input,61,FOLLOW_40); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(kw);
                    	      					newLeafNode(kw, grammarAccess.getUrnIdAccess().getAmpersandKeyword_9_2_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:3085:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    int alt42=2;
                    	    alt42 = dfa42.predict(input);
                    	    switch (alt42) {
                    	        case 1 :
                    	            // InternalKactors.g:3086:6: this_Path_15= rulePath
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_2_1_0());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_41);
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
                    	            // InternalKactors.g:3097:6: this_UrnKvp_16= ruleUrnKvp
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_2_1_1());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_41);
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
                    	    break loop43;
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
    // InternalKactors.g:3114:1: entryRuleUrnKvp returns [String current=null] : iv_ruleUrnKvp= ruleUrnKvp EOF ;
    public final String entryRuleUrnKvp() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnKvp = null;


        try {
            // InternalKactors.g:3114:46: (iv_ruleUrnKvp= ruleUrnKvp EOF )
            // InternalKactors.g:3115:2: iv_ruleUrnKvp= ruleUrnKvp EOF
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
    // InternalKactors.g:3121:1: ruleUrnKvp returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) ;
    public final AntlrDatatypeRuleToken ruleUrnKvp() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;

        AntlrDatatypeRuleToken this_Path_2 = null;



        	enterRule();

        try {
            // InternalKactors.g:3127:2: ( (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) )
            // InternalKactors.g:3128:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            {
            // InternalKactors.g:3128:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            // InternalKactors.g:3129:3: this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnKvpAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_42);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,62,FOLLOW_37); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnKvpAccess().getEqualsSignKeyword_1());
              		
            }
            // InternalKactors.g:3144:3: (this_Path_2= rulePath | this_INT_3= RULE_INT )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==RULE_LOWERCASE_ID||LA45_0==RULE_UPPERCASE_ID) ) {
                alt45=1;
            }
            else if ( (LA45_0==RULE_INT) ) {
                alt45=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }
            switch (alt45) {
                case 1 :
                    // InternalKactors.g:3145:4: this_Path_2= rulePath
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
                    // InternalKactors.g:3156:4: this_INT_3= RULE_INT
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
    // InternalKactors.g:3168:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKactors.g:3168:45: (iv_ruleList= ruleList EOF )
            // InternalKactors.g:3169:2: iv_ruleList= ruleList EOF
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
    // InternalKactors.g:3175:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3181:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKactors.g:3182:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKactors.g:3182:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKactors.g:3183:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKactors.g:3183:3: ()
            // InternalKactors.g:3184:4: 
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

            otherlv_1=(Token)match(input,43,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getListAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:3197:3: ( (lv_contents_2_0= ruleValue ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( ((LA46_0>=RULE_OBSERVABLE && LA46_0<=RULE_LOWERCASE_ID)||LA46_0==RULE_STRING||LA46_0==RULE_EXPR||LA46_0==RULE_INT||LA46_0==RULE_ARGVALUE||LA46_0==43||(LA46_0>=52 && LA46_0<=53)||LA46_0==60||LA46_0==63||LA46_0==65||(LA46_0>=77 && LA46_0<=78)) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalKactors.g:3198:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKactors.g:3198:4: (lv_contents_2_0= ruleValue )
            	    // InternalKactors.g:3199:5: lv_contents_2_0= ruleValue
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getListAccess().getContentsValueParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_20);
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
            	    break loop46;
                }
            } while (true);

            otherlv_3=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:3224:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // InternalKactors.g:3224:44: (iv_ruleMap= ruleMap EOF )
            // InternalKactors.g:3225:2: iv_ruleMap= ruleMap EOF
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
    // InternalKactors.g:3231:1: ruleMap returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_entries_2_0 = null;

        EObject lv_entries_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3237:2: ( ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) )
            // InternalKactors.g:3238:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            {
            // InternalKactors.g:3238:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            // InternalKactors.g:3239:3: () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}'
            {
            // InternalKactors.g:3239:3: ()
            // InternalKactors.g:3240:4: 
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

            otherlv_1=(Token)match(input,63,FOLLOW_43); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:3253:3: ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( ((LA48_0>=RULE_OBSERVABLE && LA48_0<=RULE_LOWERCASE_ID)||LA48_0==RULE_STRING||LA48_0==RULE_INT||LA48_0==50||(LA48_0>=52 && LA48_0<=53)||(LA48_0>=57 && LA48_0<=58)||LA48_0==62||(LA48_0>=72 && LA48_0<=78)) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalKactors.g:3254:4: ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    {
                    // InternalKactors.g:3254:4: ( (lv_entries_2_0= ruleMapEntry ) )
                    // InternalKactors.g:3255:5: (lv_entries_2_0= ruleMapEntry )
                    {
                    // InternalKactors.g:3255:5: (lv_entries_2_0= ruleMapEntry )
                    // InternalKactors.g:3256:6: lv_entries_2_0= ruleMapEntry
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_44);
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

                    // InternalKactors.g:3273:4: ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    loop47:
                    do {
                        int alt47=2;
                        int LA47_0 = input.LA(1);

                        if ( (LA47_0==31) ) {
                            alt47=1;
                        }


                        switch (alt47) {
                    	case 1 :
                    	    // InternalKactors.g:3274:5: ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) )
                    	    {
                    	    // InternalKactors.g:3274:5: ( ( ',' )=>otherlv_3= ',' )
                    	    // InternalKactors.g:3275:6: ( ',' )=>otherlv_3= ','
                    	    {
                    	    otherlv_3=(Token)match(input,31,FOLLOW_45); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_3, grammarAccess.getMapAccess().getCommaKeyword_2_1_0_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKactors.g:3282:5: ( (lv_entries_4_0= ruleMapEntry ) )
                    	    // InternalKactors.g:3283:6: (lv_entries_4_0= ruleMapEntry )
                    	    {
                    	    // InternalKactors.g:3283:6: (lv_entries_4_0= ruleMapEntry )
                    	    // InternalKactors.g:3284:7: lv_entries_4_0= ruleMapEntry
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_44);
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
                    	    break loop47;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,64,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:3311:1: entryRuleMapEntry returns [EObject current=null] : iv_ruleMapEntry= ruleMapEntry EOF ;
    public final EObject entryRuleMapEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapEntry = null;


        try {
            // InternalKactors.g:3311:49: (iv_ruleMapEntry= ruleMapEntry EOF )
            // InternalKactors.g:3312:2: iv_ruleMapEntry= ruleMapEntry EOF
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
    // InternalKactors.g:3318:1: ruleMapEntry returns [EObject current=null] : ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleMapEntry() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifier_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3324:2: ( ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKactors.g:3325:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKactors.g:3325:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // InternalKactors.g:3326:3: ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalKactors.g:3326:3: ( (lv_classifier_0_0= ruleClassifier ) )
            // InternalKactors.g:3327:4: (lv_classifier_0_0= ruleClassifier )
            {
            // InternalKactors.g:3327:4: (lv_classifier_0_0= ruleClassifier )
            // InternalKactors.g:3328:5: lv_classifier_0_0= ruleClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMapEntryAccess().getClassifierClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_14);
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

            otherlv_1=(Token)match(input,42,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapEntryAccess().getColonKeyword_1());
              		
            }
            // InternalKactors.g:3349:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKactors.g:3350:4: (lv_value_2_0= ruleValue )
            {
            // InternalKactors.g:3350:4: (lv_value_2_0= ruleValue )
            // InternalKactors.g:3351:5: lv_value_2_0= ruleValue
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
    // InternalKactors.g:3372:1: entryRuleClassifier returns [EObject current=null] : iv_ruleClassifier= ruleClassifier EOF ;
    public final EObject entryRuleClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifier = null;


        try {
            // InternalKactors.g:3372:51: (iv_ruleClassifier= ruleClassifier EOF )
            // InternalKactors.g:3373:2: iv_ruleClassifier= ruleClassifier EOF
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
    // InternalKactors.g:3379:1: ruleClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) ;
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
            // InternalKactors.g:3385:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) )
            // InternalKactors.g:3386:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            {
            // InternalKactors.g:3386:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            int alt52=10;
            alt52 = dfa52.predict(input);
            switch (alt52) {
                case 1 :
                    // InternalKactors.g:3387:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:3387:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt49=2;
                    int LA49_0 = input.LA(1);

                    if ( (LA49_0==52) ) {
                        alt49=1;
                    }
                    else if ( (LA49_0==53) ) {
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
                            // InternalKactors.g:3388:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:3388:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:3389:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:3389:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:3390:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,52,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:3403:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:3403:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:3404:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:3404:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:3405:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3419:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKactors.g:3419:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKactors.g:3420:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKactors.g:3420:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKactors.g:3421:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKactors.g:3421:5: (lv_int0_2_0= ruleNumber )
                    // InternalKactors.g:3422:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_33);
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

                    // InternalKactors.g:3439:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt50=3;
                    int LA50_0 = input.LA(1);

                    if ( (LA50_0==54) ) {
                        alt50=1;
                    }
                    else if ( (LA50_0==55) ) {
                        alt50=2;
                    }
                    switch (alt50) {
                        case 1 :
                            // InternalKactors.g:3440:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:3440:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKactors.g:3441:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKactors.g:3441:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKactors.g:3442:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,54,FOLLOW_34); if (state.failed) return current;
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
                            // InternalKactors.g:3455:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,55,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:3460:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKactors.g:3461:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,56,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKactors.g:3467:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKactors.g:3468:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKactors.g:3472:5: (lv_int1_6_0= ruleNumber )
                    // InternalKactors.g:3473:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_46);
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

                    // InternalKactors.g:3490:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt51=3;
                    int LA51_0 = input.LA(1);

                    if ( (LA51_0==54) ) {
                        alt51=1;
                    }
                    else if ( (LA51_0==55) ) {
                        alt51=2;
                    }
                    switch (alt51) {
                        case 1 :
                            // InternalKactors.g:3491:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:3491:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKactors.g:3492:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKactors.g:3492:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKactors.g:3493:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:3506:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,55,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3513:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3513:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKactors.g:3514:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKactors.g:3514:4: (lv_num_9_0= ruleNumber )
                    // InternalKactors.g:3515:5: lv_num_9_0= ruleNumber
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
                    // InternalKactors.g:3533:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKactors.g:3533:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKactors.g:3534:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,50,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:3538:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKactors.g:3539:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKactors.g:3539:5: (lv_set_11_0= ruleList )
                    // InternalKactors.g:3540:6: lv_set_11_0= ruleList
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
                    // InternalKactors.g:3559:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:3559:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKactors.g:3560:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKactors.g:3560:4: (lv_string_12_0= RULE_STRING )
                    // InternalKactors.g:3561:5: lv_string_12_0= RULE_STRING
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
                    // InternalKactors.g:3578:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:3578:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:3579:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:3579:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    // InternalKactors.g:3580:5: lv_observable_13_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:3597:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKactors.g:3597:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:3598:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:3598:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:3599:5: lv_id_14_0= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:3616:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:3616:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    // InternalKactors.g:3617:4: ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3617:4: ( (lv_op_15_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:3618:5: (lv_op_15_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:3618:5: (lv_op_15_0= ruleREL_OPERATOR )
                    // InternalKactors.g:3619:6: lv_op_15_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getOpREL_OPERATORParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_35);
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

                    // InternalKactors.g:3636:4: ( (lv_expression_16_0= ruleNumber ) )
                    // InternalKactors.g:3637:5: (lv_expression_16_0= ruleNumber )
                    {
                    // InternalKactors.g:3637:5: (lv_expression_16_0= ruleNumber )
                    // InternalKactors.g:3638:6: lv_expression_16_0= ruleNumber
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
                    // InternalKactors.g:3657:3: ( (lv_nodata_17_0= 'unknown' ) )
                    {
                    // InternalKactors.g:3657:3: ( (lv_nodata_17_0= 'unknown' ) )
                    // InternalKactors.g:3658:4: (lv_nodata_17_0= 'unknown' )
                    {
                    // InternalKactors.g:3658:4: (lv_nodata_17_0= 'unknown' )
                    // InternalKactors.g:3659:5: lv_nodata_17_0= 'unknown'
                    {
                    lv_nodata_17_0=(Token)match(input,57,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3672:3: ( (lv_star_18_0= '*' ) )
                    {
                    // InternalKactors.g:3672:3: ( (lv_star_18_0= '*' ) )
                    // InternalKactors.g:3673:4: (lv_star_18_0= '*' )
                    {
                    // InternalKactors.g:3673:4: (lv_star_18_0= '*' )
                    // InternalKactors.g:3674:5: lv_star_18_0= '*'
                    {
                    lv_star_18_0=(Token)match(input,58,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:3690:1: entryRuleLookupTable returns [EObject current=null] : iv_ruleLookupTable= ruleLookupTable EOF ;
    public final EObject entryRuleLookupTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookupTable = null;


        try {
            // InternalKactors.g:3690:52: (iv_ruleLookupTable= ruleLookupTable EOF )
            // InternalKactors.g:3691:2: iv_ruleLookupTable= ruleLookupTable EOF
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
    // InternalKactors.g:3697:1: ruleLookupTable returns [EObject current=null] : ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) ;
    public final EObject ruleLookupTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_table_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3703:2: ( ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) )
            // InternalKactors.g:3704:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            {
            // InternalKactors.g:3704:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            // InternalKactors.g:3705:3: () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}'
            {
            // InternalKactors.g:3705:3: ()
            // InternalKactors.g:3706:4: 
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

            otherlv_1=(Token)match(input,65,FOLLOW_47); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLookupTableAccess().getLeftCurlyBracketLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:3719:3: ( (lv_table_2_0= ruleTable ) )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( ((LA53_0>=RULE_OBSERVABLE && LA53_0<=RULE_LOWERCASE_ID)||LA53_0==RULE_STRING||LA53_0==RULE_EXPR||LA53_0==RULE_INT||LA53_0==50||(LA53_0>=52 && LA53_0<=53)||(LA53_0>=57 && LA53_0<=59)||LA53_0==62||(LA53_0>=72 && LA53_0<=78)) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // InternalKactors.g:3720:4: (lv_table_2_0= ruleTable )
                    {
                    // InternalKactors.g:3720:4: (lv_table_2_0= ruleTable )
                    // InternalKactors.g:3721:5: lv_table_2_0= ruleTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLookupTableAccess().getTableTableParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_48);
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

            otherlv_3=(Token)match(input,66,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:3746:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalKactors.g:3746:46: (iv_ruleTable= ruleTable EOF )
            // InternalKactors.g:3747:2: iv_ruleTable= ruleTable EOF
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
    // InternalKactors.g:3753:1: ruleTable returns [EObject current=null] : ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token this_SEPARATOR_1=null;
        Token otherlv_3=null;
        EObject lv_headers_0_0 = null;

        EObject lv_rows_2_0 = null;

        EObject lv_rows_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3759:2: ( ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) )
            // InternalKactors.g:3760:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            {
            // InternalKactors.g:3760:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            // InternalKactors.g:3761:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            {
            // InternalKactors.g:3761:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?
            int alt54=2;
            alt54 = dfa54.predict(input);
            switch (alt54) {
                case 1 :
                    // InternalKactors.g:3762:4: ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR
                    {
                    // InternalKactors.g:3762:4: ( (lv_headers_0_0= ruleHeaderRow ) )
                    // InternalKactors.g:3763:5: (lv_headers_0_0= ruleHeaderRow )
                    {
                    // InternalKactors.g:3763:5: (lv_headers_0_0= ruleHeaderRow )
                    // InternalKactors.g:3764:6: lv_headers_0_0= ruleHeaderRow
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableAccess().getHeadersHeaderRowParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_49);
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

                    this_SEPARATOR_1=(Token)match(input,RULE_SEPARATOR,FOLLOW_50); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SEPARATOR_1, grammarAccess.getTableAccess().getSEPARATORTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:3786:3: ( (lv_rows_2_0= ruleTableRow ) )
            // InternalKactors.g:3787:4: (lv_rows_2_0= ruleTableRow )
            {
            // InternalKactors.g:3787:4: (lv_rows_2_0= ruleTableRow )
            // InternalKactors.g:3788:5: lv_rows_2_0= ruleTableRow
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_24);
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

            // InternalKactors.g:3805:3: (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==31) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalKactors.g:3806:4: otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) )
            	    {
            	    otherlv_3=(Token)match(input,31,FOLLOW_50); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getTableAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKactors.g:3810:4: ( (lv_rows_4_0= ruleTableRow ) )
            	    // InternalKactors.g:3811:5: (lv_rows_4_0= ruleTableRow )
            	    {
            	    // InternalKactors.g:3811:5: (lv_rows_4_0= ruleTableRow )
            	    // InternalKactors.g:3812:6: lv_rows_4_0= ruleTableRow
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_24);
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
            	    break loop55;
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
    // InternalKactors.g:3834:1: entryRuleHeaderRow returns [EObject current=null] : iv_ruleHeaderRow= ruleHeaderRow EOF ;
    public final EObject entryRuleHeaderRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHeaderRow = null;


        try {
            // InternalKactors.g:3834:50: (iv_ruleHeaderRow= ruleHeaderRow EOF )
            // InternalKactors.g:3835:2: iv_ruleHeaderRow= ruleHeaderRow EOF
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
    // InternalKactors.g:3841:1: ruleHeaderRow returns [EObject current=null] : ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) ;
    public final EObject ruleHeaderRow() throws RecognitionException {
        EObject current = null;

        Token lv_elements_0_1=null;
        Token lv_elements_0_2=null;
        Token otherlv_1=null;
        Token lv_elements_2_1=null;
        Token lv_elements_2_2=null;


        	enterRule();

        try {
            // InternalKactors.g:3847:2: ( ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) )
            // InternalKactors.g:3848:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            {
            // InternalKactors.g:3848:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            // InternalKactors.g:3849:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            {
            // InternalKactors.g:3849:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) )
            // InternalKactors.g:3850:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            {
            // InternalKactors.g:3850:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            // InternalKactors.g:3851:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            {
            // InternalKactors.g:3851:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==RULE_LOWERCASE_ID) ) {
                alt56=1;
            }
            else if ( (LA56_0==RULE_STRING) ) {
                alt56=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;
            }
            switch (alt56) {
                case 1 :
                    // InternalKactors.g:3852:6: lv_elements_0_1= RULE_LOWERCASE_ID
                    {
                    lv_elements_0_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_51); if (state.failed) return current;
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
                    // InternalKactors.g:3867:6: lv_elements_0_2= RULE_STRING
                    {
                    lv_elements_0_2=(Token)match(input,RULE_STRING,FOLLOW_51); if (state.failed) return current;
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

            // InternalKactors.g:3884:3: (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==67) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // InternalKactors.g:3885:4: otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    {
            	    otherlv_1=(Token)match(input,67,FOLLOW_52); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getHeaderRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:3889:4: ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    // InternalKactors.g:3890:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:3890:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    // InternalKactors.g:3891:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    {
            	    // InternalKactors.g:3891:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    int alt57=2;
            	    int LA57_0 = input.LA(1);

            	    if ( (LA57_0==RULE_LOWERCASE_ID) ) {
            	        alt57=1;
            	    }
            	    else if ( (LA57_0==RULE_STRING) ) {
            	        alt57=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 57, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt57) {
            	        case 1 :
            	            // InternalKactors.g:3892:7: lv_elements_2_1= RULE_LOWERCASE_ID
            	            {
            	            lv_elements_2_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_51); if (state.failed) return current;
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
            	            // InternalKactors.g:3907:7: lv_elements_2_2= RULE_STRING
            	            {
            	            lv_elements_2_2=(Token)match(input,RULE_STRING,FOLLOW_51); if (state.failed) return current;
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
            	    break loop58;
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
    // InternalKactors.g:3929:1: entryRuleTableRow returns [EObject current=null] : iv_ruleTableRow= ruleTableRow EOF ;
    public final EObject entryRuleTableRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableRow = null;


        try {
            // InternalKactors.g:3929:49: (iv_ruleTableRow= ruleTableRow EOF )
            // InternalKactors.g:3930:2: iv_ruleTableRow= ruleTableRow EOF
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
    // InternalKactors.g:3936:1: ruleTableRow returns [EObject current=null] : ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) ;
    public final EObject ruleTableRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_elements_0_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3942:2: ( ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) )
            // InternalKactors.g:3943:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            {
            // InternalKactors.g:3943:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            // InternalKactors.g:3944:3: ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            {
            // InternalKactors.g:3944:3: ( (lv_elements_0_0= ruleTableClassifier ) )
            // InternalKactors.g:3945:4: (lv_elements_0_0= ruleTableClassifier )
            {
            // InternalKactors.g:3945:4: (lv_elements_0_0= ruleTableClassifier )
            // InternalKactors.g:3946:5: lv_elements_0_0= ruleTableClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_51);
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

            // InternalKactors.g:3963:3: (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==67) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // InternalKactors.g:3964:4: otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) )
            	    {
            	    otherlv_1=(Token)match(input,67,FOLLOW_50); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:3968:4: ( (lv_elements_2_0= ruleTableClassifier ) )
            	    // InternalKactors.g:3969:5: (lv_elements_2_0= ruleTableClassifier )
            	    {
            	    // InternalKactors.g:3969:5: (lv_elements_2_0= ruleTableClassifier )
            	    // InternalKactors.g:3970:6: lv_elements_2_0= ruleTableClassifier
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_51);
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
            	    break loop59;
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
    // InternalKactors.g:3992:1: entryRuleTableClassifier returns [EObject current=null] : iv_ruleTableClassifier= ruleTableClassifier EOF ;
    public final EObject entryRuleTableClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableClassifier = null;


        try {
            // InternalKactors.g:3992:56: (iv_ruleTableClassifier= ruleTableClassifier EOF )
            // InternalKactors.g:3993:2: iv_ruleTableClassifier= ruleTableClassifier EOF
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
    // InternalKactors.g:3999:1: ruleTableClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) ;
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
            // InternalKactors.g:4005:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) )
            // InternalKactors.g:4006:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            {
            // InternalKactors.g:4006:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            int alt63=13;
            alt63 = dfa63.predict(input);
            switch (alt63) {
                case 1 :
                    // InternalKactors.g:4007:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:4007:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt60=2;
                    int LA60_0 = input.LA(1);

                    if ( (LA60_0==52) ) {
                        alt60=1;
                    }
                    else if ( (LA60_0==53) ) {
                        alt60=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 60, 0, input);

                        throw nvae;
                    }
                    switch (alt60) {
                        case 1 :
                            // InternalKactors.g:4008:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:4008:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:4009:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:4009:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:4010:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,52,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:4023:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:4023:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:4024:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:4024:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:4025:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:4039:3: ( (lv_num_2_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4039:3: ( (lv_num_2_0= ruleNumber ) )
                    // InternalKactors.g:4040:4: (lv_num_2_0= ruleNumber )
                    {
                    // InternalKactors.g:4040:4: (lv_num_2_0= ruleNumber )
                    // InternalKactors.g:4041:5: lv_num_2_0= ruleNumber
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
                    // InternalKactors.g:4059:3: ( (lv_string_3_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:4059:3: ( (lv_string_3_0= RULE_STRING ) )
                    // InternalKactors.g:4060:4: (lv_string_3_0= RULE_STRING )
                    {
                    // InternalKactors.g:4060:4: (lv_string_3_0= RULE_STRING )
                    // InternalKactors.g:4061:5: lv_string_3_0= RULE_STRING
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
                    // InternalKactors.g:4078:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:4078:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:4079:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:4079:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    // InternalKactors.g:4080:5: lv_observable_4_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:4097:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:4097:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    // InternalKactors.g:4098:4: ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4098:4: ( (lv_op_5_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:4099:5: (lv_op_5_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:4099:5: (lv_op_5_0= ruleREL_OPERATOR )
                    // InternalKactors.g:4100:6: lv_op_5_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getOpREL_OPERATORParserRuleCall_4_0_0());
                      					
                    }
                    pushFollow(FOLLOW_35);
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

                    // InternalKactors.g:4117:4: ( (lv_expression_6_0= ruleNumber ) )
                    // InternalKactors.g:4118:5: (lv_expression_6_0= ruleNumber )
                    {
                    // InternalKactors.g:4118:5: (lv_expression_6_0= ruleNumber )
                    // InternalKactors.g:4119:6: lv_expression_6_0= ruleNumber
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
                    // InternalKactors.g:4138:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    {
                    // InternalKactors.g:4138:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    // InternalKactors.g:4139:4: ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    {
                    // InternalKactors.g:4139:4: ( (lv_int0_7_0= ruleNumber ) )
                    // InternalKactors.g:4140:5: (lv_int0_7_0= ruleNumber )
                    {
                    // InternalKactors.g:4140:5: (lv_int0_7_0= ruleNumber )
                    // InternalKactors.g:4141:6: lv_int0_7_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt0NumberParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_33);
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

                    // InternalKactors.g:4158:4: ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )?
                    int alt61=3;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==54) ) {
                        alt61=1;
                    }
                    else if ( (LA61_0==55) ) {
                        alt61=2;
                    }
                    switch (alt61) {
                        case 1 :
                            // InternalKactors.g:4159:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4159:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            // InternalKactors.g:4160:6: (lv_leftLimit_8_0= 'inclusive' )
                            {
                            // InternalKactors.g:4160:6: (lv_leftLimit_8_0= 'inclusive' )
                            // InternalKactors.g:4161:7: lv_leftLimit_8_0= 'inclusive'
                            {
                            lv_leftLimit_8_0=(Token)match(input,54,FOLLOW_34); if (state.failed) return current;
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
                            // InternalKactors.g:4174:5: otherlv_9= 'exclusive'
                            {
                            otherlv_9=(Token)match(input,55,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_9, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_5_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:4179:4: ( ( 'to' )=>otherlv_10= 'to' )
                    // InternalKactors.g:4180:5: ( 'to' )=>otherlv_10= 'to'
                    {
                    otherlv_10=(Token)match(input,56,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_10, grammarAccess.getTableClassifierAccess().getToKeyword_5_2());
                      				
                    }

                    }

                    // InternalKactors.g:4186:4: ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) )
                    // InternalKactors.g:4187:5: ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber )
                    {
                    // InternalKactors.g:4191:5: (lv_int1_11_0= ruleNumber )
                    // InternalKactors.g:4192:6: lv_int1_11_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt1NumberParserRuleCall_5_3_0());
                      					
                    }
                    pushFollow(FOLLOW_46);
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

                    // InternalKactors.g:4209:4: ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    int alt62=3;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==54) ) {
                        alt62=1;
                    }
                    else if ( (LA62_0==55) ) {
                        alt62=2;
                    }
                    switch (alt62) {
                        case 1 :
                            // InternalKactors.g:4210:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4210:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            // InternalKactors.g:4211:6: (lv_rightLimit_12_0= 'inclusive' )
                            {
                            // InternalKactors.g:4211:6: (lv_rightLimit_12_0= 'inclusive' )
                            // InternalKactors.g:4212:7: lv_rightLimit_12_0= 'inclusive'
                            {
                            lv_rightLimit_12_0=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:4225:5: otherlv_13= 'exclusive'
                            {
                            otherlv_13=(Token)match(input,55,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:4232:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    {
                    // InternalKactors.g:4232:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    // InternalKactors.g:4233:4: otherlv_14= 'in' ( (lv_set_15_0= ruleList ) )
                    {
                    otherlv_14=(Token)match(input,50,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getTableClassifierAccess().getInKeyword_6_0());
                      			
                    }
                    // InternalKactors.g:4237:4: ( (lv_set_15_0= ruleList ) )
                    // InternalKactors.g:4238:5: (lv_set_15_0= ruleList )
                    {
                    // InternalKactors.g:4238:5: (lv_set_15_0= ruleList )
                    // InternalKactors.g:4239:6: lv_set_15_0= ruleList
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
                    // InternalKactors.g:4258:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    {
                    // InternalKactors.g:4258:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    // InternalKactors.g:4259:4: (lv_quantity_16_0= ruleQuantity )
                    {
                    // InternalKactors.g:4259:4: (lv_quantity_16_0= ruleQuantity )
                    // InternalKactors.g:4260:5: lv_quantity_16_0= ruleQuantity
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
                    // InternalKactors.g:4278:3: ( (lv_date_17_0= ruleDate ) )
                    {
                    // InternalKactors.g:4278:3: ( (lv_date_17_0= ruleDate ) )
                    // InternalKactors.g:4279:4: (lv_date_17_0= ruleDate )
                    {
                    // InternalKactors.g:4279:4: (lv_date_17_0= ruleDate )
                    // InternalKactors.g:4280:5: lv_date_17_0= ruleDate
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
                    // InternalKactors.g:4298:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:4298:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    // InternalKactors.g:4299:4: (lv_expr_18_0= RULE_EXPR )
                    {
                    // InternalKactors.g:4299:4: (lv_expr_18_0= RULE_EXPR )
                    // InternalKactors.g:4300:5: lv_expr_18_0= RULE_EXPR
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
                    // InternalKactors.g:4317:3: ( (lv_nodata_19_0= 'unknown' ) )
                    {
                    // InternalKactors.g:4317:3: ( (lv_nodata_19_0= 'unknown' ) )
                    // InternalKactors.g:4318:4: (lv_nodata_19_0= 'unknown' )
                    {
                    // InternalKactors.g:4318:4: (lv_nodata_19_0= 'unknown' )
                    // InternalKactors.g:4319:5: lv_nodata_19_0= 'unknown'
                    {
                    lv_nodata_19_0=(Token)match(input,57,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:4332:3: ( (lv_star_20_0= '*' ) )
                    {
                    // InternalKactors.g:4332:3: ( (lv_star_20_0= '*' ) )
                    // InternalKactors.g:4333:4: (lv_star_20_0= '*' )
                    {
                    // InternalKactors.g:4333:4: (lv_star_20_0= '*' )
                    // InternalKactors.g:4334:5: lv_star_20_0= '*'
                    {
                    lv_star_20_0=(Token)match(input,58,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:4347:3: ( (lv_anything_21_0= '#' ) )
                    {
                    // InternalKactors.g:4347:3: ( (lv_anything_21_0= '#' ) )
                    // InternalKactors.g:4348:4: (lv_anything_21_0= '#' )
                    {
                    // InternalKactors.g:4348:4: (lv_anything_21_0= '#' )
                    // InternalKactors.g:4349:5: lv_anything_21_0= '#'
                    {
                    lv_anything_21_0=(Token)match(input,59,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:4365:1: entryRuleQuantity returns [EObject current=null] : iv_ruleQuantity= ruleQuantity EOF ;
    public final EObject entryRuleQuantity() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQuantity = null;


        try {
            // InternalKactors.g:4365:49: (iv_ruleQuantity= ruleQuantity EOF )
            // InternalKactors.g:4366:2: iv_ruleQuantity= ruleQuantity EOF
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
    // InternalKactors.g:4372:1: ruleQuantity returns [EObject current=null] : ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) ;
    public final EObject ruleQuantity() throws RecognitionException {
        EObject current = null;

        Token lv_over_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_0_0 = null;

        EObject lv_unit_3_0 = null;

        EObject lv_currency_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4378:2: ( ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) )
            // InternalKactors.g:4379:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            {
            // InternalKactors.g:4379:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            // InternalKactors.g:4380:3: ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            {
            // InternalKactors.g:4380:3: ( (lv_value_0_0= ruleNumber ) )
            // InternalKactors.g:4381:4: (lv_value_0_0= ruleNumber )
            {
            // InternalKactors.g:4381:4: (lv_value_0_0= ruleNumber )
            // InternalKactors.g:4382:5: lv_value_0_0= ruleNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getQuantityAccess().getValueNumberParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_53);
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

            // InternalKactors.g:4399:3: ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' )
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==68) ) {
                alt64=1;
            }
            else if ( (LA64_0==69) ) {
                alt64=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }
            switch (alt64) {
                case 1 :
                    // InternalKactors.g:4400:4: ( (lv_over_1_0= '/' ) )
                    {
                    // InternalKactors.g:4400:4: ( (lv_over_1_0= '/' ) )
                    // InternalKactors.g:4401:5: (lv_over_1_0= '/' )
                    {
                    // InternalKactors.g:4401:5: (lv_over_1_0= '/' )
                    // InternalKactors.g:4402:6: lv_over_1_0= '/'
                    {
                    lv_over_1_0=(Token)match(input,68,FOLLOW_54); if (state.failed) return current;
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
                    // InternalKactors.g:4415:4: otherlv_2= '.'
                    {
                    otherlv_2=(Token)match(input,69,FOLLOW_54); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getQuantityAccess().getFullStopKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:4420:3: ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==EOF||LA65_0==RULE_LOWERCASE_ID||LA65_0==RULE_CAMELCASE_ID||LA65_0==31||LA65_0==43||LA65_0==51||LA65_0==58||(LA65_0>=66 && LA65_0<=68)||LA65_0==85) ) {
                alt65=1;
            }
            else if ( (LA65_0==RULE_UPPERCASE_ID) ) {
                int LA65_2 = input.LA(2);

                if ( (LA65_2==71) ) {
                    alt65=2;
                }
                else if ( (LA65_2==EOF||LA65_2==31||LA65_2==51||LA65_2==58||(LA65_2>=66 && LA65_2<=68)||LA65_2==85) ) {
                    alt65=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 65, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;
            }
            switch (alt65) {
                case 1 :
                    // InternalKactors.g:4421:4: ( (lv_unit_3_0= ruleUnit ) )
                    {
                    // InternalKactors.g:4421:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKactors.g:4422:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKactors.g:4422:5: (lv_unit_3_0= ruleUnit )
                    // InternalKactors.g:4423:6: lv_unit_3_0= ruleUnit
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
                    // InternalKactors.g:4441:4: ( (lv_currency_4_0= ruleCurrency ) )
                    {
                    // InternalKactors.g:4441:4: ( (lv_currency_4_0= ruleCurrency ) )
                    // InternalKactors.g:4442:5: (lv_currency_4_0= ruleCurrency )
                    {
                    // InternalKactors.g:4442:5: (lv_currency_4_0= ruleCurrency )
                    // InternalKactors.g:4443:6: lv_currency_4_0= ruleCurrency
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


    // $ANTLR start "entryRuleAnnotation"
    // InternalKactors.g:4465:1: entryRuleAnnotation returns [EObject current=null] : iv_ruleAnnotation= ruleAnnotation EOF ;
    public final EObject entryRuleAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnnotation = null;


        try {
            // InternalKactors.g:4465:51: (iv_ruleAnnotation= ruleAnnotation EOF )
            // InternalKactors.g:4466:2: iv_ruleAnnotation= ruleAnnotation EOF
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
    // InternalKactors.g:4472:1: ruleAnnotation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) ;
    public final EObject ruleAnnotation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4478:2: ( ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) )
            // InternalKactors.g:4479:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            {
            // InternalKactors.g:4479:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            // InternalKactors.g:4480:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            {
            // InternalKactors.g:4480:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) )
            // InternalKactors.g:4481:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            {
            // InternalKactors.g:4481:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            // InternalKactors.g:4482:5: lv_name_0_0= RULE_ANNOTATION_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ANNOTATION_ID,FOLLOW_55); if (state.failed) return current;
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

            // InternalKactors.g:4498:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==43) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // InternalKactors.g:4499:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,43,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getAnnotationAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:4503:4: ( (lv_parameters_2_0= ruleParameterList ) )?
                    int alt66=2;
                    int LA66_0 = input.LA(1);

                    if ( ((LA66_0>=RULE_OBSERVABLE && LA66_0<=RULE_LOWERCASE_ID)||LA66_0==RULE_STRING||LA66_0==RULE_EXPR||LA66_0==RULE_INT||LA66_0==RULE_ARGVALUE||LA66_0==43||(LA66_0>=52 && LA66_0<=53)||LA66_0==60||LA66_0==63||LA66_0==65||(LA66_0>=77 && LA66_0<=78)) ) {
                        alt66=1;
                    }
                    switch (alt66) {
                        case 1 :
                            // InternalKactors.g:4504:5: (lv_parameters_2_0= ruleParameterList )
                            {
                            // InternalKactors.g:4504:5: (lv_parameters_2_0= ruleParameterList )
                            // InternalKactors.g:4505:6: lv_parameters_2_0= ruleParameterList
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getAnnotationAccess().getParametersParameterListParserRuleCall_1_1_0());
                              					
                            }
                            pushFollow(FOLLOW_21);
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

                    otherlv_3=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleLiteral"
    // InternalKactors.g:4531:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKactors.g:4531:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKactors.g:4532:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKactors.g:4538:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) ;
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
            // InternalKactors.g:4544:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) )
            // InternalKactors.g:4545:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            {
            // InternalKactors.g:4545:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            int alt69=5;
            alt69 = dfa69.predict(input);
            switch (alt69) {
                case 1 :
                    // InternalKactors.g:4546:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4546:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKactors.g:4547:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKactors.g:4547:4: (lv_number_0_0= ruleNumber )
                    // InternalKactors.g:4548:5: lv_number_0_0= ruleNumber
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
                    // InternalKactors.g:4566:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:4566:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKactors.g:4567:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4567:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKactors.g:4568:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKactors.g:4568:5: (lv_from_1_0= ruleNumber )
                    // InternalKactors.g:4569:6: lv_from_1_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_34);
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

                    otherlv_2=(Token)match(input,56,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:4590:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKactors.g:4591:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKactors.g:4591:5: (lv_to_3_0= ruleNumber )
                    // InternalKactors.g:4592:6: lv_to_3_0= ruleNumber
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
                    // InternalKactors.g:4611:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:4611:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKactors.g:4612:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKactors.g:4612:4: (lv_string_4_0= RULE_STRING )
                    // InternalKactors.g:4613:5: lv_string_4_0= RULE_STRING
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
                    // InternalKactors.g:4630:3: ( (lv_date_5_0= ruleDate ) )
                    {
                    // InternalKactors.g:4630:3: ( (lv_date_5_0= ruleDate ) )
                    // InternalKactors.g:4631:4: (lv_date_5_0= ruleDate )
                    {
                    // InternalKactors.g:4631:4: (lv_date_5_0= ruleDate )
                    // InternalKactors.g:4632:5: lv_date_5_0= ruleDate
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
                    // InternalKactors.g:4650:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    {
                    // InternalKactors.g:4650:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    // InternalKactors.g:4651:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    {
                    // InternalKactors.g:4651:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    // InternalKactors.g:4652:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    {
                    // InternalKactors.g:4652:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    int alt68=2;
                    int LA68_0 = input.LA(1);

                    if ( (LA68_0==52) ) {
                        alt68=1;
                    }
                    else if ( (LA68_0==53) ) {
                        alt68=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 68, 0, input);

                        throw nvae;
                    }
                    switch (alt68) {
                        case 1 :
                            // InternalKactors.g:4653:6: lv_boolean_6_1= 'true'
                            {
                            lv_boolean_6_1=(Token)match(input,52,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:4664:6: lv_boolean_6_2= 'false'
                            {
                            lv_boolean_6_2=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleParameterList"
    // InternalKactors.g:4681:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKactors.g:4681:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKactors.g:4682:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKactors.g:4688:1: ruleParameterList returns [EObject current=null] : ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) ;
    public final EObject ruleParameterList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_pairs_0_0 = null;

        EObject lv_pairs_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4694:2: ( ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) )
            // InternalKactors.g:4695:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            {
            // InternalKactors.g:4695:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            // InternalKactors.g:4696:3: ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            {
            // InternalKactors.g:4696:3: ( (lv_pairs_0_0= ruleKeyValuePair ) )
            // InternalKactors.g:4697:4: (lv_pairs_0_0= ruleKeyValuePair )
            {
            // InternalKactors.g:4697:4: (lv_pairs_0_0= ruleKeyValuePair )
            // InternalKactors.g:4698:5: lv_pairs_0_0= ruleKeyValuePair
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_24);
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

            // InternalKactors.g:4715:3: ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( (LA70_0==31) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // InternalKactors.g:4716:4: ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    {
            	    // InternalKactors.g:4716:4: ( ( ',' )=>otherlv_1= ',' )
            	    // InternalKactors.g:4717:5: ( ',' )=>otherlv_1= ','
            	    {
            	    otherlv_1=(Token)match(input,31,FOLLOW_15); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_1_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:4723:4: ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    // InternalKactors.g:4724:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    {
            	    // InternalKactors.g:4724:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    // InternalKactors.g:4725:6: lv_pairs_2_0= ruleKeyValuePair
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_24);
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
            	    break loop70;
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
    // InternalKactors.g:4747:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKactors.g:4747:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKactors.g:4748:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKactors.g:4754:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4760:2: ( ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKactors.g:4761:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKactors.g:4761:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            // InternalKactors.g:4762:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKactors.g:4762:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==RULE_LOWERCASE_ID) ) {
                int LA72_1 = input.LA(2);

                if ( (LA72_1==62||LA72_1==70) ) {
                    alt72=1;
                }
            }
            switch (alt72) {
                case 1 :
                    // InternalKactors.g:4763:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    {
                    // InternalKactors.g:4763:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:4764:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:4764:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:4765:6: lv_name_0_0= RULE_LOWERCASE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_56); if (state.failed) return current;
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

                    // InternalKactors.g:4781:4: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==70) ) {
                        alt71=1;
                    }
                    else if ( (LA71_0==62) ) {
                        alt71=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 71, 0, input);

                        throw nvae;
                    }
                    switch (alt71) {
                        case 1 :
                            // InternalKactors.g:4782:5: ( (lv_interactive_1_0= '=?' ) )
                            {
                            // InternalKactors.g:4782:5: ( (lv_interactive_1_0= '=?' ) )
                            // InternalKactors.g:4783:6: (lv_interactive_1_0= '=?' )
                            {
                            // InternalKactors.g:4783:6: (lv_interactive_1_0= '=?' )
                            // InternalKactors.g:4784:7: lv_interactive_1_0= '=?'
                            {
                            lv_interactive_1_0=(Token)match(input,70,FOLLOW_15); if (state.failed) return current;
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
                            // InternalKactors.g:4797:5: otherlv_2= '='
                            {
                            otherlv_2=(Token)match(input,62,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_0_1_1());
                              				
                            }

                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalKactors.g:4803:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:4804:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:4804:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:4805:5: lv_value_3_0= ruleValue
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
    // InternalKactors.g:4826:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKactors.g:4826:46: (iv_ruleValue= ruleValue EOF )
            // InternalKactors.g:4827:2: iv_ruleValue= ruleValue EOF
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
    // InternalKactors.g:4833:1: ruleValue returns [EObject current=null] : ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) ) ;
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
            // InternalKactors.g:4839:2: ( ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) ) )
            // InternalKactors.g:4840:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )
            {
            // InternalKactors.g:4840:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )
            int alt73=9;
            alt73 = dfa73.predict(input);
            switch (alt73) {
                case 1 :
                    // InternalKactors.g:4841:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    {
                    // InternalKactors.g:4841:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    // InternalKactors.g:4842:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    {
                    // InternalKactors.g:4842:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    // InternalKactors.g:4843:5: lv_argvalue_0_0= RULE_ARGVALUE
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
                    // InternalKactors.g:4860:3: ( (lv_literal_1_0= ruleLiteral ) )
                    {
                    // InternalKactors.g:4860:3: ( (lv_literal_1_0= ruleLiteral ) )
                    // InternalKactors.g:4861:4: (lv_literal_1_0= ruleLiteral )
                    {
                    // InternalKactors.g:4861:4: (lv_literal_1_0= ruleLiteral )
                    // InternalKactors.g:4862:5: lv_literal_1_0= ruleLiteral
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
                    // InternalKactors.g:4880:3: ( (lv_id_2_0= rulePathName ) )
                    {
                    // InternalKactors.g:4880:3: ( (lv_id_2_0= rulePathName ) )
                    // InternalKactors.g:4881:4: (lv_id_2_0= rulePathName )
                    {
                    // InternalKactors.g:4881:4: (lv_id_2_0= rulePathName )
                    // InternalKactors.g:4882:5: lv_id_2_0= rulePathName
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
                    // InternalKactors.g:4900:3: ( (lv_urn_3_0= ruleUrnId ) )
                    {
                    // InternalKactors.g:4900:3: ( (lv_urn_3_0= ruleUrnId ) )
                    // InternalKactors.g:4901:4: (lv_urn_3_0= ruleUrnId )
                    {
                    // InternalKactors.g:4901:4: (lv_urn_3_0= ruleUrnId )
                    // InternalKactors.g:4902:5: lv_urn_3_0= ruleUrnId
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
                    // InternalKactors.g:4920:3: ( (lv_list_4_0= ruleList ) )
                    {
                    // InternalKactors.g:4920:3: ( (lv_list_4_0= ruleList ) )
                    // InternalKactors.g:4921:4: (lv_list_4_0= ruleList )
                    {
                    // InternalKactors.g:4921:4: (lv_list_4_0= ruleList )
                    // InternalKactors.g:4922:5: lv_list_4_0= ruleList
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
                    // InternalKactors.g:4940:3: ( (lv_map_5_0= ruleMap ) )
                    {
                    // InternalKactors.g:4940:3: ( (lv_map_5_0= ruleMap ) )
                    // InternalKactors.g:4941:4: (lv_map_5_0= ruleMap )
                    {
                    // InternalKactors.g:4941:4: (lv_map_5_0= ruleMap )
                    // InternalKactors.g:4942:5: lv_map_5_0= ruleMap
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
                    // InternalKactors.g:4960:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:4960:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:4961:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:4961:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    // InternalKactors.g:4962:5: lv_observable_6_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:4979:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:4979:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    // InternalKactors.g:4980:4: (lv_expression_7_0= RULE_EXPR )
                    {
                    // InternalKactors.g:4980:4: (lv_expression_7_0= RULE_EXPR )
                    // InternalKactors.g:4981:5: lv_expression_7_0= RULE_EXPR
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
                    // InternalKactors.g:4998:3: ( (lv_table_8_0= ruleLookupTable ) )
                    {
                    // InternalKactors.g:4998:3: ( (lv_table_8_0= ruleLookupTable ) )
                    // InternalKactors.g:4999:4: (lv_table_8_0= ruleLookupTable )
                    {
                    // InternalKactors.g:4999:4: (lv_table_8_0= ruleLookupTable )
                    // InternalKactors.g:5000:5: lv_table_8_0= ruleLookupTable
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


    // $ANTLR start "entryRuleUnitElement"
    // InternalKactors.g:5021:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKactors.g:5021:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKactors.g:5022:2: iv_ruleUnitElement= ruleUnitElement EOF
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
    // InternalKactors.g:5028:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) ;
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
            // InternalKactors.g:5034:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) )
            // InternalKactors.g:5035:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            {
            // InternalKactors.g:5035:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==RULE_LOWERCASE_ID||LA75_0==RULE_CAMELCASE_ID||LA75_0==RULE_UPPERCASE_ID) ) {
                alt75=1;
            }
            else if ( (LA75_0==43) ) {
                alt75=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 75, 0, input);

                throw nvae;
            }
            switch (alt75) {
                case 1 :
                    // InternalKactors.g:5036:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    {
                    // InternalKactors.g:5036:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    // InternalKactors.g:5037:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKactors.g:5037:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    // InternalKactors.g:5038:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    {
                    // InternalKactors.g:5038:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    int alt74=3;
                    switch ( input.LA(1) ) {
                    case RULE_CAMELCASE_ID:
                        {
                        alt74=1;
                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        alt74=2;
                        }
                        break;
                    case RULE_UPPERCASE_ID:
                        {
                        alt74=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 74, 0, input);

                        throw nvae;
                    }

                    switch (alt74) {
                        case 1 :
                            // InternalKactors.g:5039:6: lv_id_0_1= RULE_CAMELCASE_ID
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
                            // InternalKactors.g:5054:6: lv_id_0_2= RULE_LOWERCASE_ID
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
                            // InternalKactors.g:5069:6: lv_id_0_3= RULE_UPPERCASE_ID
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
                    // InternalKactors.g:5087:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    {
                    // InternalKactors.g:5087:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    // InternalKactors.g:5088:4: otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,43,FOLLOW_57); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:5092:4: ( (lv_unit_2_0= ruleUnit ) )
                    // InternalKactors.g:5093:5: (lv_unit_2_0= ruleUnit )
                    {
                    // InternalKactors.g:5093:5: (lv_unit_2_0= ruleUnit )
                    // InternalKactors.g:5094:6: lv_unit_2_0= ruleUnit
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getUnitElementAccess().getUnitUnitParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_21);
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

                    otherlv_3=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:5120:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKactors.g:5120:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKactors.g:5121:2: iv_ruleUnit= ruleUnit EOF
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
    // InternalKactors.g:5127:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5133:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:5134:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:5134:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:5135:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:5135:3: ()
            // InternalKactors.g:5136:4: 
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

            // InternalKactors.g:5145:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==RULE_LOWERCASE_ID||LA76_0==RULE_CAMELCASE_ID||LA76_0==RULE_UPPERCASE_ID||LA76_0==43) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // InternalKactors.g:5146:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKactors.g:5146:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKactors.g:5147:5: lv_root_1_0= ruleUnitElement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUnitAccess().getRootUnitElementParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_58);
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

            // InternalKactors.g:5164:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==58||LA77_0==68||LA77_0==85) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // InternalKactors.g:5165:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:5165:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKactors.g:5166:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKactors.g:5172:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKactors.g:5173:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKactors.g:5173:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKactors.g:5174:7: lv_connectors_2_0= ruleUnitOp
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getUnitAccess().getConnectorsUnitOpEnumRuleCall_2_0_0_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_59);
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

            	    // InternalKactors.g:5192:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKactors.g:5193:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:5193:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKactors.g:5194:6: lv_units_3_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getUnitAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_58);
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
            	    break loop77;
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
    // InternalKactors.g:5216:1: entryRuleCurrency returns [EObject current=null] : iv_ruleCurrency= ruleCurrency EOF ;
    public final EObject entryRuleCurrency() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCurrency = null;


        try {
            // InternalKactors.g:5216:49: (iv_ruleCurrency= ruleCurrency EOF )
            // InternalKactors.g:5217:2: iv_ruleCurrency= ruleCurrency EOF
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
    // InternalKactors.g:5223:1: ruleCurrency returns [EObject current=null] : ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleCurrency() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_year_2_0=null;
        Token otherlv_3=null;
        EObject lv_units_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5229:2: ( ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:5230:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:5230:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:5231:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:5231:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) )
            // InternalKactors.g:5232:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            {
            // InternalKactors.g:5232:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            // InternalKactors.g:5233:5: lv_id_0_0= RULE_UPPERCASE_ID
            {
            lv_id_0_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_60); if (state.failed) return current;
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

            // InternalKactors.g:5249:3: (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) )
            // InternalKactors.g:5250:4: otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) )
            {
            otherlv_1=(Token)match(input,71,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_1, grammarAccess.getCurrencyAccess().getCommercialAtKeyword_1_0());
              			
            }
            // InternalKactors.g:5254:4: ( (lv_year_2_0= RULE_INT ) )
            // InternalKactors.g:5255:5: (lv_year_2_0= RULE_INT )
            {
            // InternalKactors.g:5255:5: (lv_year_2_0= RULE_INT )
            // InternalKactors.g:5256:6: lv_year_2_0= RULE_INT
            {
            lv_year_2_0=(Token)match(input,RULE_INT,FOLLOW_61); if (state.failed) return current;
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

            // InternalKactors.g:5273:3: ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            loop78:
            do {
                int alt78=2;
                int LA78_0 = input.LA(1);

                if ( (LA78_0==68) ) {
                    alt78=1;
                }


                switch (alt78) {
            	case 1 :
            	    // InternalKactors.g:5274:4: ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:5274:4: ( ( '/' )=>otherlv_3= '/' )
            	    // InternalKactors.g:5275:5: ( '/' )=>otherlv_3= '/'
            	    {
            	    otherlv_3=(Token)match(input,68,FOLLOW_59); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_3, grammarAccess.getCurrencyAccess().getSolidusKeyword_2_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:5281:4: ( (lv_units_4_0= ruleUnitElement ) )
            	    // InternalKactors.g:5282:5: (lv_units_4_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:5282:5: (lv_units_4_0= ruleUnitElement )
            	    // InternalKactors.g:5283:6: lv_units_4_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getCurrencyAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_61);
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
            	    break loop78;
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
    // InternalKactors.g:5305:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKactors.g:5305:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKactors.g:5306:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
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
    // InternalKactors.g:5312:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
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
            // InternalKactors.g:5318:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKactors.g:5319:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKactors.g:5319:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt79=6;
            switch ( input.LA(1) ) {
            case 72:
                {
                alt79=1;
                }
                break;
            case 73:
                {
                alt79=2;
                }
                break;
            case 62:
                {
                alt79=3;
                }
                break;
            case 74:
                {
                alt79=4;
                }
                break;
            case 75:
                {
                alt79=5;
                }
                break;
            case 76:
                {
                alt79=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;
            }

            switch (alt79) {
                case 1 :
                    // InternalKactors.g:5320:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKactors.g:5320:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKactors.g:5321:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKactors.g:5321:4: (lv_gt_0_0= '>' )
                    // InternalKactors.g:5322:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,72,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5335:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKactors.g:5335:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKactors.g:5336:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKactors.g:5336:4: (lv_lt_1_0= '<' )
                    // InternalKactors.g:5337:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5350:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKactors.g:5350:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKactors.g:5351:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKactors.g:5351:4: (lv_eq_2_0= '=' )
                    // InternalKactors.g:5352:5: lv_eq_2_0= '='
                    {
                    lv_eq_2_0=(Token)match(input,62,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5365:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKactors.g:5365:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKactors.g:5366:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKactors.g:5366:4: (lv_ne_3_0= '!=' )
                    // InternalKactors.g:5367:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,74,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5380:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKactors.g:5380:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKactors.g:5381:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKactors.g:5381:4: (lv_le_4_0= '<=' )
                    // InternalKactors.g:5382:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5395:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKactors.g:5395:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKactors.g:5396:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKactors.g:5396:4: (lv_ge_5_0= '>=' )
                    // InternalKactors.g:5397:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleNumber"
    // InternalKactors.g:5413:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKactors.g:5413:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKactors.g:5414:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKactors.g:5420:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) ;
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
            // InternalKactors.g:5426:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) )
            // InternalKactors.g:5427:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            {
            // InternalKactors.g:5427:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            // InternalKactors.g:5428:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            {
            // InternalKactors.g:5428:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt80=3;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==77) ) {
                alt80=1;
            }
            else if ( (LA80_0==78) ) {
                alt80=2;
            }
            switch (alt80) {
                case 1 :
                    // InternalKactors.g:5429:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,77,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5434:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKactors.g:5434:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKactors.g:5435:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKactors.g:5435:5: (lv_negative_1_0= '-' )
                    // InternalKactors.g:5436:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,78,FOLLOW_10); if (state.failed) return current;
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

            // InternalKactors.g:5449:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKactors.g:5450:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKactors.g:5454:4: (lv_real_2_0= RULE_INT )
            // InternalKactors.g:5455:5: lv_real_2_0= RULE_INT
            {
            lv_real_2_0=(Token)match(input,RULE_INT,FOLLOW_62); if (state.failed) return current;
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

            // InternalKactors.g:5471:3: ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==79) && (synpred175_InternalKactors())) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // InternalKactors.g:5472:4: ( ( 'l' ) )=> (lv_long_3_0= 'l' )
                    {
                    // InternalKactors.g:5476:4: (lv_long_3_0= 'l' )
                    // InternalKactors.g:5477:5: lv_long_3_0= 'l'
                    {
                    lv_long_3_0=(Token)match(input,79,FOLLOW_63); if (state.failed) return current;
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

            // InternalKactors.g:5489:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==69) ) {
                int LA82_1 = input.LA(2);

                if ( (LA82_1==RULE_INT) && (synpred176_InternalKactors())) {
                    alt82=1;
                }
            }
            switch (alt82) {
                case 1 :
                    // InternalKactors.g:5490:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5503:4: ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    // InternalKactors.g:5504:5: ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5504:5: ( (lv_decimal_4_0= '.' ) )
                    // InternalKactors.g:5505:6: (lv_decimal_4_0= '.' )
                    {
                    // InternalKactors.g:5505:6: (lv_decimal_4_0= '.' )
                    // InternalKactors.g:5506:7: lv_decimal_4_0= '.'
                    {
                    lv_decimal_4_0=(Token)match(input,69,FOLLOW_10); if (state.failed) return current;
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

                    // InternalKactors.g:5518:5: ( (lv_decimalPart_5_0= RULE_INT ) )
                    // InternalKactors.g:5519:6: (lv_decimalPart_5_0= RULE_INT )
                    {
                    // InternalKactors.g:5519:6: (lv_decimalPart_5_0= RULE_INT )
                    // InternalKactors.g:5520:7: lv_decimalPart_5_0= RULE_INT
                    {
                    lv_decimalPart_5_0=(Token)match(input,RULE_INT,FOLLOW_64); if (state.failed) return current;
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

            // InternalKactors.g:5538:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==80) && (synpred180_InternalKactors())) {
                alt85=1;
            }
            else if ( (LA85_0==81) && (synpred180_InternalKactors())) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // InternalKactors.g:5539:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5565:4: ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    // InternalKactors.g:5566:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5566:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) )
                    // InternalKactors.g:5567:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    {
                    // InternalKactors.g:5567:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    // InternalKactors.g:5568:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    {
                    // InternalKactors.g:5568:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( (LA83_0==80) ) {
                        alt83=1;
                    }
                    else if ( (LA83_0==81) ) {
                        alt83=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 83, 0, input);

                        throw nvae;
                    }
                    switch (alt83) {
                        case 1 :
                            // InternalKactors.g:5569:8: lv_exponential_6_1= 'e'
                            {
                            lv_exponential_6_1=(Token)match(input,80,FOLLOW_35); if (state.failed) return current;
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
                            // InternalKactors.g:5580:8: lv_exponential_6_2= 'E'
                            {
                            lv_exponential_6_2=(Token)match(input,81,FOLLOW_35); if (state.failed) return current;
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

                    // InternalKactors.g:5593:5: (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )?
                    int alt84=3;
                    int LA84_0 = input.LA(1);

                    if ( (LA84_0==77) ) {
                        alt84=1;
                    }
                    else if ( (LA84_0==78) ) {
                        alt84=2;
                    }
                    switch (alt84) {
                        case 1 :
                            // InternalKactors.g:5594:6: otherlv_7= '+'
                            {
                            otherlv_7=(Token)match(input,77,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getNumberAccess().getPlusSignKeyword_4_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:5599:6: ( (lv_expNegative_8_0= '-' ) )
                            {
                            // InternalKactors.g:5599:6: ( (lv_expNegative_8_0= '-' ) )
                            // InternalKactors.g:5600:7: (lv_expNegative_8_0= '-' )
                            {
                            // InternalKactors.g:5600:7: (lv_expNegative_8_0= '-' )
                            // InternalKactors.g:5601:8: lv_expNegative_8_0= '-'
                            {
                            lv_expNegative_8_0=(Token)match(input,78,FOLLOW_10); if (state.failed) return current;
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

                    // InternalKactors.g:5614:5: ( (lv_exp_9_0= RULE_INT ) )
                    // InternalKactors.g:5615:6: (lv_exp_9_0= RULE_INT )
                    {
                    // InternalKactors.g:5615:6: (lv_exp_9_0= RULE_INT )
                    // InternalKactors.g:5616:7: lv_exp_9_0= RULE_INT
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
    // InternalKactors.g:5638:1: entryRuleDate returns [EObject current=null] : iv_ruleDate= ruleDate EOF ;
    public final EObject entryRuleDate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDate = null;


        try {
            // InternalKactors.g:5638:45: (iv_ruleDate= ruleDate EOF )
            // InternalKactors.g:5639:2: iv_ruleDate= ruleDate EOF
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
    // InternalKactors.g:5645:1: ruleDate returns [EObject current=null] : ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) ;
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
            // InternalKactors.g:5651:2: ( ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) )
            // InternalKactors.g:5652:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            {
            // InternalKactors.g:5652:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            // InternalKactors.g:5653:3: ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            {
            // InternalKactors.g:5653:3: ( (lv_year_0_0= RULE_INT ) )
            // InternalKactors.g:5654:4: (lv_year_0_0= RULE_INT )
            {
            // InternalKactors.g:5654:4: (lv_year_0_0= RULE_INT )
            // InternalKactors.g:5655:5: lv_year_0_0= RULE_INT
            {
            lv_year_0_0=(Token)match(input,RULE_INT,FOLLOW_65); if (state.failed) return current;
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

            // InternalKactors.g:5671:3: (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )?
            int alt86=4;
            switch ( input.LA(1) ) {
                case 82:
                    {
                    alt86=1;
                    }
                    break;
                case 83:
                    {
                    alt86=2;
                    }
                    break;
                case 84:
                    {
                    alt86=3;
                    }
                    break;
            }

            switch (alt86) {
                case 1 :
                    // InternalKactors.g:5672:4: otherlv_1= 'AD'
                    {
                    otherlv_1=(Token)match(input,82,FOLLOW_66); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getDateAccess().getADKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5677:4: otherlv_2= 'CE'
                    {
                    otherlv_2=(Token)match(input,83,FOLLOW_66); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getDateAccess().getCEKeyword_1_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKactors.g:5682:4: ( (lv_bc_3_0= 'BC' ) )
                    {
                    // InternalKactors.g:5682:4: ( (lv_bc_3_0= 'BC' ) )
                    // InternalKactors.g:5683:5: (lv_bc_3_0= 'BC' )
                    {
                    // InternalKactors.g:5683:5: (lv_bc_3_0= 'BC' )
                    // InternalKactors.g:5684:6: lv_bc_3_0= 'BC'
                    {
                    lv_bc_3_0=(Token)match(input,84,FOLLOW_66); if (state.failed) return current;
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

            otherlv_4=(Token)match(input,78,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDateAccess().getHyphenMinusKeyword_2());
              		
            }
            // InternalKactors.g:5701:3: ( (lv_month_5_0= RULE_INT ) )
            // InternalKactors.g:5702:4: (lv_month_5_0= RULE_INT )
            {
            // InternalKactors.g:5702:4: (lv_month_5_0= RULE_INT )
            // InternalKactors.g:5703:5: lv_month_5_0= RULE_INT
            {
            lv_month_5_0=(Token)match(input,RULE_INT,FOLLOW_66); if (state.failed) return current;
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

            otherlv_6=(Token)match(input,78,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getDateAccess().getHyphenMinusKeyword_4());
              		
            }
            // InternalKactors.g:5723:3: ( (lv_day_7_0= RULE_INT ) )
            // InternalKactors.g:5724:4: (lv_day_7_0= RULE_INT )
            {
            // InternalKactors.g:5724:4: (lv_day_7_0= RULE_INT )
            // InternalKactors.g:5725:5: lv_day_7_0= RULE_INT
            {
            lv_day_7_0=(Token)match(input,RULE_INT,FOLLOW_67); if (state.failed) return current;
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

            // InternalKactors.g:5741:3: ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==RULE_INT) ) {
                int LA89_1 = input.LA(2);

                if ( (LA89_1==42) ) {
                    alt89=1;
                }
            }
            switch (alt89) {
                case 1 :
                    // InternalKactors.g:5742:4: ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    {
                    // InternalKactors.g:5742:4: ( (lv_hour_8_0= RULE_INT ) )
                    // InternalKactors.g:5743:5: (lv_hour_8_0= RULE_INT )
                    {
                    // InternalKactors.g:5743:5: (lv_hour_8_0= RULE_INT )
                    // InternalKactors.g:5744:6: lv_hour_8_0= RULE_INT
                    {
                    lv_hour_8_0=(Token)match(input,RULE_INT,FOLLOW_14); if (state.failed) return current;
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

                    otherlv_9=(Token)match(input,42,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getDateAccess().getColonKeyword_6_1());
                      			
                    }
                    // InternalKactors.g:5764:4: ( (lv_min_10_0= RULE_INT ) )
                    // InternalKactors.g:5765:5: (lv_min_10_0= RULE_INT )
                    {
                    // InternalKactors.g:5765:5: (lv_min_10_0= RULE_INT )
                    // InternalKactors.g:5766:6: lv_min_10_0= RULE_INT
                    {
                    lv_min_10_0=(Token)match(input,RULE_INT,FOLLOW_22); if (state.failed) return current;
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

                    // InternalKactors.g:5782:4: (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    int alt88=2;
                    int LA88_0 = input.LA(1);

                    if ( (LA88_0==42) ) {
                        alt88=1;
                    }
                    switch (alt88) {
                        case 1 :
                            // InternalKactors.g:5783:5: otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            {
                            otherlv_11=(Token)match(input,42,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_11, grammarAccess.getDateAccess().getColonKeyword_6_3_0());
                              				
                            }
                            // InternalKactors.g:5787:5: ( (lv_sec_12_0= RULE_INT ) )
                            // InternalKactors.g:5788:6: (lv_sec_12_0= RULE_INT )
                            {
                            // InternalKactors.g:5788:6: (lv_sec_12_0= RULE_INT )
                            // InternalKactors.g:5789:7: lv_sec_12_0= RULE_INT
                            {
                            lv_sec_12_0=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
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

                            // InternalKactors.g:5805:5: (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            int alt87=2;
                            int LA87_0 = input.LA(1);

                            if ( (LA87_0==69) ) {
                                alt87=1;
                            }
                            switch (alt87) {
                                case 1 :
                                    // InternalKactors.g:5806:6: otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) )
                                    {
                                    otherlv_13=(Token)match(input,69,FOLLOW_10); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						newLeafNode(otherlv_13, grammarAccess.getDateAccess().getFullStopKeyword_6_3_2_0());
                                      					
                                    }
                                    // InternalKactors.g:5810:6: ( (lv_ms_14_0= RULE_INT ) )
                                    // InternalKactors.g:5811:7: (lv_ms_14_0= RULE_INT )
                                    {
                                    // InternalKactors.g:5811:7: (lv_ms_14_0= RULE_INT )
                                    // InternalKactors.g:5812:8: lv_ms_14_0= RULE_INT
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
    // InternalKactors.g:5835:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKactors.g:5835:48: (iv_rulePathName= rulePathName EOF )
            // InternalKactors.g:5836:2: iv_rulePathName= rulePathName EOF
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
    // InternalKactors.g:5842:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKactors.g:5848:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKactors.g:5849:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKactors.g:5849:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKactors.g:5850:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:5857:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( (LA90_0==69) ) {
                    alt90=1;
                }


                switch (alt90) {
            	case 1 :
            	    // InternalKactors.g:5858:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,69,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPathNameAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_68); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop90;
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
    // InternalKactors.g:5875:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKactors.g:5875:44: (iv_rulePath= rulePath EOF )
            // InternalKactors.g:5876:2: iv_rulePath= rulePath EOF
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
    // InternalKactors.g:5882:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token this_UPPERCASE_ID_1=null;
        Token kw=null;
        Token this_LOWERCASE_ID_4=null;
        Token this_UPPERCASE_ID_5=null;


        	enterRule();

        try {
            // InternalKactors.g:5888:2: ( ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) )
            // InternalKactors.g:5889:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            {
            // InternalKactors.g:5889:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            // InternalKactors.g:5890:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            {
            // InternalKactors.g:5890:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID )
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==RULE_LOWERCASE_ID) ) {
                alt91=1;
            }
            else if ( (LA91_0==RULE_UPPERCASE_ID) ) {
                alt91=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 91, 0, input);

                throw nvae;
            }
            switch (alt91) {
                case 1 :
                    // InternalKactors.g:5891:4: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5899:4: this_UPPERCASE_ID_1= RULE_UPPERCASE_ID
                    {
                    this_UPPERCASE_ID_1=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_UPPERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_UPPERCASE_ID_1, grammarAccess.getPathAccess().getUPPERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:5907:3: ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( ((LA94_0>=68 && LA94_0<=69)) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // InternalKactors.g:5908:4: (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    {
            	    // InternalKactors.g:5908:4: (kw= '.' | kw= '/' )
            	    int alt92=2;
            	    int LA92_0 = input.LA(1);

            	    if ( (LA92_0==69) ) {
            	        alt92=1;
            	    }
            	    else if ( (LA92_0==68) ) {
            	        alt92=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 92, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt92) {
            	        case 1 :
            	            // InternalKactors.g:5909:5: kw= '.'
            	            {
            	            kw=(Token)match(input,69,FOLLOW_40); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:5915:5: kw= '/'
            	            {
            	            kw=(Token)match(input,68,FOLLOW_40); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getSolidusKeyword_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalKactors.g:5921:4: (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    int alt93=2;
            	    int LA93_0 = input.LA(1);

            	    if ( (LA93_0==RULE_LOWERCASE_ID) ) {
            	        alt93=1;
            	    }
            	    else if ( (LA93_0==RULE_UPPERCASE_ID) ) {
            	        alt93=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 93, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt93) {
            	        case 1 :
            	            // InternalKactors.g:5922:5: this_LOWERCASE_ID_4= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_4=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_69); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_4, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:5930:5: this_UPPERCASE_ID_5= RULE_UPPERCASE_ID
            	            {
            	            this_UPPERCASE_ID_5=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_69); if (state.failed) return current;
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
            	    break loop94;
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
    // InternalKactors.g:5943:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKactors.g:5943:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKactors.g:5944:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalKactors.g:5950:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
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
            // InternalKactors.g:5956:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKactors.g:5957:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKactors.g:5957:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKactors.g:5958:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_70); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:5965:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==69) ) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // InternalKactors.g:5966:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,69,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_70); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKactors.g:5978:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt95=2;
                    int LA95_0 = input.LA(1);

                    if ( (LA95_0==69) ) {
                        alt95=1;
                    }
                    switch (alt95) {
                        case 1 :
                            // InternalKactors.g:5979:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,69,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_71); if (state.failed) return current;
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

            // InternalKactors.g:5993:3: (kw= '-' )?
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==78) ) {
                int LA97_1 = input.LA(2);

                if ( (synpred197_InternalKactors()) ) {
                    alt97=1;
                }
            }
            switch (alt97) {
                case 1 :
                    // InternalKactors.g:5994:4: kw= '-'
                    {
                    kw=(Token)match(input,78,FOLLOW_72); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:6000:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt98=3;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==RULE_LOWERCASE_ID) ) {
                int LA98_1 = input.LA(2);

                if ( (synpred198_InternalKactors()) ) {
                    alt98=1;
                }
            }
            else if ( (LA98_0==RULE_UPPERCASE_ID) ) {
                alt98=2;
            }
            switch (alt98) {
                case 1 :
                    // InternalKactors.g:6001:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:6009:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
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
    // InternalKactors.g:6021:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKactors.g:6027:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKactors.g:6028:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKactors.g:6028:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt99=3;
            switch ( input.LA(1) ) {
            case 68:
                {
                alt99=1;
                }
                break;
            case 85:
                {
                alt99=2;
                }
                break;
            case 58:
                {
                alt99=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                throw nvae;
            }

            switch (alt99) {
                case 1 :
                    // InternalKactors.g:6029:3: (enumLiteral_0= '/' )
                    {
                    // InternalKactors.g:6029:3: (enumLiteral_0= '/' )
                    // InternalKactors.g:6030:4: enumLiteral_0= '/'
                    {
                    enumLiteral_0=(Token)match(input,68,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:6037:3: (enumLiteral_1= '^' )
                    {
                    // InternalKactors.g:6037:3: (enumLiteral_1= '^' )
                    // InternalKactors.g:6038:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,85,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:6045:3: (enumLiteral_2= '*' )
                    {
                    // InternalKactors.g:6045:3: (enumLiteral_2= '*' )
                    // InternalKactors.g:6046:4: enumLiteral_2= '*'
                    {
                    enumLiteral_2=(Token)match(input,58,FOLLOW_2); if (state.failed) return current;
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

    // $ANTLR start synpred12_InternalKactors
    public final void synpred12_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_imports_8_0 = null;

        AntlrDatatypeRuleToken lv_imports_10_0 = null;


        // InternalKactors.g:313:4: ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) )
        // InternalKactors.g:313:4: ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) )
        {
        // InternalKactors.g:313:4: ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) )
        // InternalKactors.g:314:5: {...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred12_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
        }
        // InternalKactors.g:314:105: ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) )
        // InternalKactors.g:315:6: ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
        // InternalKactors.g:318:9: ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) )
        // InternalKactors.g:318:10: {...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred12_InternalKactors", "true");
        }
        // InternalKactors.g:318:19: (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* )
        // InternalKactors.g:318:20: otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )*
        {
        otherlv_7=(Token)match(input,30,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:322:9: ( (lv_imports_8_0= rulePathName ) )
        // InternalKactors.g:323:10: (lv_imports_8_0= rulePathName )
        {
        // InternalKactors.g:323:10: (lv_imports_8_0= rulePathName )
        // InternalKactors.g:324:11: lv_imports_8_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_1_0());
          										
        }
        pushFollow(FOLLOW_24);
        lv_imports_8_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:341:9: (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )*
        loop106:
        do {
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==31) ) {
                alt106=1;
            }


            switch (alt106) {
        	case 1 :
        	    // InternalKactors.g:342:10: otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) )
        	    {
        	    otherlv_9=(Token)match(input,31,FOLLOW_4); if (state.failed) return ;
        	    // InternalKactors.g:346:10: ( (lv_imports_10_0= rulePathName ) )
        	    // InternalKactors.g:347:11: (lv_imports_10_0= rulePathName )
        	    {
        	    // InternalKactors.g:347:11: (lv_imports_10_0= rulePathName )
        	    // InternalKactors.g:348:12: lv_imports_10_0= rulePathName
        	    {
        	    if ( state.backtracking==0 ) {

        	      												newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_2_1_0());
        	      											
        	    }
        	    pushFollow(FOLLOW_24);
        	    lv_imports_10_0=rulePathName();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    break loop106;
            }
        } while (true);


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred12_InternalKactors

    // $ANTLR start synpred13_InternalKactors
    public final void synpred13_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_11=null;
        AntlrDatatypeRuleToken lv_worldview_12_0 = null;


        // InternalKactors.g:372:4: ( ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) )
        // InternalKactors.g:372:4: ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) )
        {
        // InternalKactors.g:372:4: ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) )
        // InternalKactors.g:373:5: {...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
        }
        // InternalKactors.g:373:105: ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) )
        // InternalKactors.g:374:6: ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
        // InternalKactors.g:377:9: ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) )
        // InternalKactors.g:377:10: {...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKactors", "true");
        }
        // InternalKactors.g:377:19: (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) )
        // InternalKactors.g:377:20: otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) )
        {
        otherlv_11=(Token)match(input,32,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:381:9: ( (lv_worldview_12_0= rulePathName ) )
        // InternalKactors.g:382:10: (lv_worldview_12_0= rulePathName )
        {
        // InternalKactors.g:382:10: (lv_worldview_12_0= rulePathName )
        // InternalKactors.g:383:11: lv_worldview_12_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_2_1_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_worldview_12_0=rulePathName();

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
    // $ANTLR end synpred13_InternalKactors

    // $ANTLR start synpred15_InternalKactors
    public final void synpred15_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_13=null;
        Token lv_observable_14_0=null;
        EObject lv_observables_15_0 = null;


        // InternalKactors.g:406:4: ( ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) )
        // InternalKactors.g:406:4: ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) )
        {
        // InternalKactors.g:406:4: ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) )
        // InternalKactors.g:407:5: {...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred15_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
        }
        // InternalKactors.g:407:105: ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) )
        // InternalKactors.g:408:6: ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
        // InternalKactors.g:411:9: ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) )
        // InternalKactors.g:411:10: {...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred15_InternalKactors", "true");
        }
        // InternalKactors.g:411:19: (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) )
        // InternalKactors.g:411:20: otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) )
        {
        otherlv_13=(Token)match(input,33,FOLLOW_7); if (state.failed) return ;
        // InternalKactors.g:415:9: ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) )
        int alt107=2;
        int LA107_0 = input.LA(1);

        if ( (LA107_0==RULE_OBSERVABLE) ) {
            alt107=1;
        }
        else if ( (LA107_0==43) ) {
            alt107=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 107, 0, input);

            throw nvae;
        }
        switch (alt107) {
            case 1 :
                // InternalKactors.g:416:10: ( (lv_observable_14_0= RULE_OBSERVABLE ) )
                {
                // InternalKactors.g:416:10: ( (lv_observable_14_0= RULE_OBSERVABLE ) )
                // InternalKactors.g:417:11: (lv_observable_14_0= RULE_OBSERVABLE )
                {
                // InternalKactors.g:417:11: (lv_observable_14_0= RULE_OBSERVABLE )
                // InternalKactors.g:418:12: lv_observable_14_0= RULE_OBSERVABLE
                {
                lv_observable_14_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:435:10: ( (lv_observables_15_0= ruleList ) )
                {
                // InternalKactors.g:435:10: ( (lv_observables_15_0= ruleList ) )
                // InternalKactors.g:436:11: (lv_observables_15_0= ruleList )
                {
                // InternalKactors.g:436:11: (lv_observables_15_0= ruleList )
                // InternalKactors.g:437:12: lv_observables_15_0= ruleList
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getPreambleAccess().getObservablesListParserRuleCall_2_2_1_1_0());
                  											
                }
                pushFollow(FOLLOW_2);
                lv_observables_15_0=ruleList();

                state._fsp--;
                if (state.failed) return ;

                }


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
    // $ANTLR end synpred15_InternalKactors

    // $ANTLR start synpred18_InternalKactors
    public final void synpred18_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        Token lv_label_17_1=null;
        Token lv_label_17_2=null;
        Token lv_label_17_3=null;

        // InternalKactors.g:461:4: ( ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) )
        // InternalKactors.g:461:4: ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) )
        {
        // InternalKactors.g:461:4: ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:462:5: {...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
        }
        // InternalKactors.g:462:105: ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:463:6: ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
        // InternalKactors.g:466:9: ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) )
        // InternalKactors.g:466:10: {...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKactors", "true");
        }
        // InternalKactors.g:466:19: (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) )
        // InternalKactors.g:466:20: otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) )
        {
        otherlv_16=(Token)match(input,34,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:470:9: ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) )
        // InternalKactors.g:471:10: ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) )
        {
        // InternalKactors.g:471:10: ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) )
        // InternalKactors.g:472:11: (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING )
        {
        // InternalKactors.g:472:11: (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING )
        int alt108=3;
        switch ( input.LA(1) ) {
        case RULE_LOWERCASE_ID:
            {
            alt108=1;
            }
            break;
        case RULE_ID:
            {
            alt108=2;
            }
            break;
        case RULE_STRING:
            {
            alt108=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 108, 0, input);

            throw nvae;
        }

        switch (alt108) {
            case 1 :
                // InternalKactors.g:473:12: lv_label_17_1= RULE_LOWERCASE_ID
                {
                lv_label_17_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:488:12: lv_label_17_2= RULE_ID
                {
                lv_label_17_2=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 3 :
                // InternalKactors.g:503:12: lv_label_17_3= RULE_STRING
                {
                lv_label_17_3=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred18_InternalKactors

    // $ANTLR start synpred19_InternalKactors
    public final void synpred19_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_18=null;
        Token lv_description_19_0=null;

        // InternalKactors.g:526:4: ( ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:526:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:526:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:527:5: {...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred19_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4)");
        }
        // InternalKactors.g:527:105: ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:528:6: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4);
        // InternalKactors.g:531:9: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
        // InternalKactors.g:531:10: {...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred19_InternalKactors", "true");
        }
        // InternalKactors.g:531:19: (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
        // InternalKactors.g:531:20: otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) )
        {
        otherlv_18=(Token)match(input,35,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:535:9: ( (lv_description_19_0= RULE_STRING ) )
        // InternalKactors.g:536:10: (lv_description_19_0= RULE_STRING )
        {
        // InternalKactors.g:536:10: (lv_description_19_0= RULE_STRING )
        // InternalKactors.g:537:11: lv_description_19_0= RULE_STRING
        {
        lv_description_19_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred19_InternalKactors

    // $ANTLR start synpred20_InternalKactors
    public final void synpred20_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_20=null;
        Token lv_permissions_21_0=null;

        // InternalKactors.g:559:4: ( ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:559:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:559:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:560:5: {...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred20_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5)");
        }
        // InternalKactors.g:560:105: ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:561:6: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5);
        // InternalKactors.g:564:9: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
        // InternalKactors.g:564:10: {...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred20_InternalKactors", "true");
        }
        // InternalKactors.g:564:19: (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
        // InternalKactors.g:564:20: otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) )
        {
        otherlv_20=(Token)match(input,36,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:568:9: ( (lv_permissions_21_0= RULE_STRING ) )
        // InternalKactors.g:569:10: (lv_permissions_21_0= RULE_STRING )
        {
        // InternalKactors.g:569:10: (lv_permissions_21_0= RULE_STRING )
        // InternalKactors.g:570:11: lv_permissions_21_0= RULE_STRING
        {
        lv_permissions_21_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred20_InternalKactors

    // $ANTLR start synpred21_InternalKactors
    public final void synpred21_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_22=null;
        Token lv_authors_23_0=null;

        // InternalKactors.g:597:10: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )
        // InternalKactors.g:597:10: {...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred21_InternalKactors", "true");
        }
        // InternalKactors.g:597:19: (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        // InternalKactors.g:597:20: otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) )
        {
        otherlv_22=(Token)match(input,37,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:601:9: ( (lv_authors_23_0= RULE_STRING ) )
        // InternalKactors.g:602:10: (lv_authors_23_0= RULE_STRING )
        {
        // InternalKactors.g:602:10: (lv_authors_23_0= RULE_STRING )
        // InternalKactors.g:603:11: lv_authors_23_0= RULE_STRING
        {
        lv_authors_23_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred21_InternalKactors

    // $ANTLR start synpred22_InternalKactors
    public final void synpred22_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_22=null;
        Token lv_authors_23_0=null;

        // InternalKactors.g:592:4: ( ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) )
        // InternalKactors.g:592:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
        {
        // InternalKactors.g:592:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
        // InternalKactors.g:593:5: {...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred22_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6)");
        }
        // InternalKactors.g:593:105: ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
        // InternalKactors.g:594:6: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6);
        // InternalKactors.g:597:9: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
        int cnt109=0;
        loop109:
        do {
            int alt109=2;
            int LA109_0 = input.LA(1);

            if ( (LA109_0==37) && ((true))) {
                alt109=1;
            }


            switch (alt109) {
        	case 1 :
        	    // InternalKactors.g:597:10: {...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        	    {
        	    if ( !((true)) ) {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        throw new FailedPredicateException(input, "synpred22_InternalKactors", "true");
        	    }
        	    // InternalKactors.g:597:19: (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        	    // InternalKactors.g:597:20: otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) )
        	    {
        	    otherlv_22=(Token)match(input,37,FOLLOW_9); if (state.failed) return ;
        	    // InternalKactors.g:601:9: ( (lv_authors_23_0= RULE_STRING ) )
        	    // InternalKactors.g:602:10: (lv_authors_23_0= RULE_STRING )
        	    {
        	    // InternalKactors.g:602:10: (lv_authors_23_0= RULE_STRING )
        	    // InternalKactors.g:603:11: lv_authors_23_0= RULE_STRING
        	    {
        	    lv_authors_23_0=(Token)match(input,RULE_STRING,FOLLOW_73); if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt109 >= 1 ) break loop109;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(109, input);
                    throw eee;
            }
            cnt109++;
        } while (true);


        }


        }


        }
    }
    // $ANTLR end synpred22_InternalKactors

    // $ANTLR start synpred23_InternalKactors
    public final void synpred23_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_24=null;
        AntlrDatatypeRuleToken lv_version_25_0 = null;


        // InternalKactors.g:625:4: ( ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) )
        // InternalKactors.g:625:4: ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) )
        {
        // InternalKactors.g:625:4: ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) )
        // InternalKactors.g:626:5: {...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred23_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7)");
        }
        // InternalKactors.g:626:105: ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) )
        // InternalKactors.g:627:6: ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7);
        // InternalKactors.g:630:9: ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) )
        // InternalKactors.g:630:10: {...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred23_InternalKactors", "true");
        }
        // InternalKactors.g:630:19: (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) )
        // InternalKactors.g:630:20: otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) )
        {
        otherlv_24=(Token)match(input,38,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:634:9: ( (lv_version_25_0= ruleVersionNumber ) )
        // InternalKactors.g:635:10: (lv_version_25_0= ruleVersionNumber )
        {
        // InternalKactors.g:635:10: (lv_version_25_0= ruleVersionNumber )
        // InternalKactors.g:636:11: lv_version_25_0= ruleVersionNumber
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_2_7_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_version_25_0=ruleVersionNumber();

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
    // $ANTLR end synpred23_InternalKactors

    // $ANTLR start synpred25_InternalKactors
    public final void synpred25_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_26=null;
        Token lv_createcomment_28_0=null;
        EObject lv_created_27_0 = null;


        // InternalKactors.g:659:4: ( ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:659:4: ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:659:4: ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:660:5: {...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred25_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8)");
        }
        // InternalKactors.g:660:105: ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:661:6: ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8);
        // InternalKactors.g:664:9: ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) )
        // InternalKactors.g:664:10: {...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred25_InternalKactors", "true");
        }
        // InternalKactors.g:664:19: (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? )
        // InternalKactors.g:664:20: otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )?
        {
        otherlv_26=(Token)match(input,39,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:668:9: ( (lv_created_27_0= ruleDate ) )
        // InternalKactors.g:669:10: (lv_created_27_0= ruleDate )
        {
        // InternalKactors.g:669:10: (lv_created_27_0= ruleDate )
        // InternalKactors.g:670:11: lv_created_27_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_2_8_1_0());
          										
        }
        pushFollow(FOLLOW_74);
        lv_created_27_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:687:9: ( (lv_createcomment_28_0= RULE_STRING ) )?
        int alt110=2;
        int LA110_0 = input.LA(1);

        if ( (LA110_0==RULE_STRING) ) {
            alt110=1;
        }
        switch (alt110) {
            case 1 :
                // InternalKactors.g:688:10: (lv_createcomment_28_0= RULE_STRING )
                {
                // InternalKactors.g:688:10: (lv_createcomment_28_0= RULE_STRING )
                // InternalKactors.g:689:11: lv_createcomment_28_0= RULE_STRING
                {
                lv_createcomment_28_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred25_InternalKactors

    // $ANTLR start synpred27_InternalKactors
    public final void synpred27_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_29=null;
        Token lv_modcomment_31_0=null;
        EObject lv_modified_30_0 = null;


        // InternalKactors.g:711:4: ( ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:711:4: ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:711:4: ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:712:5: {...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred27_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9)");
        }
        // InternalKactors.g:712:105: ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:713:6: ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9);
        // InternalKactors.g:716:9: ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) )
        // InternalKactors.g:716:10: {...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred27_InternalKactors", "true");
        }
        // InternalKactors.g:716:19: (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? )
        // InternalKactors.g:716:20: otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )?
        {
        otherlv_29=(Token)match(input,40,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:720:9: ( (lv_modified_30_0= ruleDate ) )
        // InternalKactors.g:721:10: (lv_modified_30_0= ruleDate )
        {
        // InternalKactors.g:721:10: (lv_modified_30_0= ruleDate )
        // InternalKactors.g:722:11: lv_modified_30_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_2_9_1_0());
          										
        }
        pushFollow(FOLLOW_74);
        lv_modified_30_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:739:9: ( (lv_modcomment_31_0= RULE_STRING ) )?
        int alt111=2;
        int LA111_0 = input.LA(1);

        if ( (LA111_0==RULE_STRING) ) {
            alt111=1;
        }
        switch (alt111) {
            case 1 :
                // InternalKactors.g:740:10: (lv_modcomment_31_0= RULE_STRING )
                {
                // InternalKactors.g:740:10: (lv_modcomment_31_0= RULE_STRING )
                // InternalKactors.g:741:11: lv_modcomment_31_0= RULE_STRING
                {
                lv_modcomment_31_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred27_InternalKactors

    // $ANTLR start synpred34_InternalKactors
    public final void synpred34_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;


        // InternalKactors.g:1045:6: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )
        // InternalKactors.g:1045:6: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
        {
        otherlv_1=(Token)match(input,43,FOLLOW_20); if (state.failed) return ;
        // InternalKactors.g:1049:6: ( (lv_parameters_2_0= ruleParameterList ) )?
        int alt113=2;
        int LA113_0 = input.LA(1);

        if ( ((LA113_0>=RULE_OBSERVABLE && LA113_0<=RULE_LOWERCASE_ID)||LA113_0==RULE_STRING||LA113_0==RULE_EXPR||LA113_0==RULE_INT||LA113_0==RULE_ARGVALUE||LA113_0==43||(LA113_0>=52 && LA113_0<=53)||LA113_0==60||LA113_0==63||LA113_0==65||(LA113_0>=77 && LA113_0<=78)) ) {
            alt113=1;
        }
        switch (alt113) {
            case 1 :
                // InternalKactors.g:1050:7: (lv_parameters_2_0= ruleParameterList )
                {
                // InternalKactors.g:1050:7: (lv_parameters_2_0= ruleParameterList )
                // InternalKactors.g:1051:8: lv_parameters_2_0= ruleParameterList
                {
                if ( state.backtracking==0 ) {

                  								newCompositeNode(grammarAccess.getMessageCallAccess().getParametersParameterListParserRuleCall_0_0_1_1_0());
                  							
                }
                pushFollow(FOLLOW_21);
                lv_parameters_2_0=ruleParameterList();

                state._fsp--;
                if (state.failed) return ;

                }


                }
                break;

        }

        otherlv_3=(Token)match(input,44,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred34_InternalKactors

    // $ANTLR start synpred37_InternalKactors
    public final void synpred37_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_body_2_0 = null;


        // InternalKactors.g:1146:4: ( (lv_body_2_0= ruleMessageBody ) )
        // InternalKactors.g:1146:4: (lv_body_2_0= ruleMessageBody )
        {
        // InternalKactors.g:1146:4: (lv_body_2_0= ruleMessageBody )
        // InternalKactors.g:1147:5: lv_body_2_0= ruleMessageBody
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementGroupAccess().getBodyMessageBodyParserRuleCall_2_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_body_2_0=ruleMessageBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred37_InternalKactors

    // $ANTLR start synpred38_InternalKactors
    public final void synpred38_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_next_1_0 = null;


        // InternalKactors.g:1207:4: ( (lv_next_1_0= ruleNextStatement ) )
        // InternalKactors.g:1207:4: (lv_next_1_0= ruleNextStatement )
        {
        // InternalKactors.g:1207:4: (lv_next_1_0= ruleNextStatement )
        // InternalKactors.g:1208:5: lv_next_1_0= ruleNextStatement
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
    // $ANTLR end synpred38_InternalKactors

    // $ANTLR start synpred39_InternalKactors
    public final void synpred39_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_0_0 = null;


        // InternalKactors.g:1244:3: ( ( (lv_verb_0_0= ruleMessageCall ) ) )
        // InternalKactors.g:1244:3: ( (lv_verb_0_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1244:3: ( (lv_verb_0_0= ruleMessageCall ) )
        // InternalKactors.g:1245:4: (lv_verb_0_0= ruleMessageCall )
        {
        // InternalKactors.g:1245:4: (lv_verb_0_0= ruleMessageCall )
        // InternalKactors.g:1246:5: lv_verb_0_0= ruleMessageCall
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementAccess().getVerbMessageCallParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_verb_0_0=ruleMessageCall();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred39_InternalKactors

    // $ANTLR start synpred40_InternalKactors
    public final void synpred40_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_group_1_0 = null;


        // InternalKactors.g:1264:3: ( ( (lv_group_1_0= ruleStatementGroup ) ) )
        // InternalKactors.g:1264:3: ( (lv_group_1_0= ruleStatementGroup ) )
        {
        // InternalKactors.g:1264:3: ( (lv_group_1_0= ruleStatementGroup ) )
        // InternalKactors.g:1265:4: (lv_group_1_0= ruleStatementGroup )
        {
        // InternalKactors.g:1265:4: (lv_group_1_0= ruleStatementGroup )
        // InternalKactors.g:1266:5: lv_group_1_0= ruleStatementGroup
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementAccess().getGroupStatementGroupParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_group_1_0=ruleStatementGroup();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred40_InternalKactors

    // $ANTLR start synpred46_InternalKactors
    public final void synpred46_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_1_0 = null;


        // InternalKactors.g:1426:4: ( ( (lv_verb_1_0= ruleMessageCall ) ) )
        // InternalKactors.g:1426:4: ( (lv_verb_1_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1426:4: ( (lv_verb_1_0= ruleMessageCall ) )
        // InternalKactors.g:1427:5: (lv_verb_1_0= ruleMessageCall )
        {
        // InternalKactors.g:1427:5: (lv_verb_1_0= ruleMessageCall )
        // InternalKactors.g:1428:6: lv_verb_1_0= ruleMessageCall
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getNextStatementAccess().getVerbMessageCallParserRuleCall_1_0_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_verb_1_0=ruleMessageCall();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred46_InternalKactors

    // $ANTLR start synpred47_InternalKactors
    public final void synpred47_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_group_2_0 = null;


        // InternalKactors.g:1446:4: ( ( (lv_group_2_0= ruleStatementGroup ) ) )
        // InternalKactors.g:1446:4: ( (lv_group_2_0= ruleStatementGroup ) )
        {
        // InternalKactors.g:1446:4: ( (lv_group_2_0= ruleStatementGroup ) )
        // InternalKactors.g:1447:5: (lv_group_2_0= ruleStatementGroup )
        {
        // InternalKactors.g:1447:5: (lv_group_2_0= ruleStatementGroup )
        // InternalKactors.g:1448:6: lv_group_2_0= ruleStatementGroup
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getNextStatementAccess().getGroupStatementGroupParserRuleCall_1_1_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_group_2_0=ruleStatementGroup();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred47_InternalKactors

    // $ANTLR start synpred53_InternalKactors
    public final void synpred53_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_elseIfExpression_5_0=null;
        EObject lv_elseIfBody_6_0 = null;


        // InternalKactors.g:1646:4: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )
        // InternalKactors.g:1646:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) )
        {
        otherlv_3=(Token)match(input,46,FOLLOW_27); if (state.failed) return ;
        otherlv_4=(Token)match(input,45,FOLLOW_25); if (state.failed) return ;
        // InternalKactors.g:1654:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
        // InternalKactors.g:1655:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        {
        // InternalKactors.g:1655:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        // InternalKactors.g:1656:6: lv_elseIfExpression_5_0= RULE_EXPR
        {
        lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_15); if (state.failed) return ;

        }


        }

        // InternalKactors.g:1672:4: ( (lv_elseIfBody_6_0= ruleStatementBody ) )
        // InternalKactors.g:1673:5: (lv_elseIfBody_6_0= ruleStatementBody )
        {
        // InternalKactors.g:1673:5: (lv_elseIfBody_6_0= ruleStatementBody )
        // InternalKactors.g:1674:6: lv_elseIfBody_6_0= ruleStatementBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getIfStatementAccess().getElseIfBodyStatementBodyParserRuleCall_3_3_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_elseIfBody_6_0=ruleStatementBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred53_InternalKactors

    // $ANTLR start synpred54_InternalKactors
    public final void synpred54_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        EObject lv_elseCall_8_0 = null;


        // InternalKactors.g:1693:4: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )
        // InternalKactors.g:1693:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) )
        {
        otherlv_7=(Token)match(input,46,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:1697:4: ( (lv_elseCall_8_0= ruleStatementBody ) )
        // InternalKactors.g:1698:5: (lv_elseCall_8_0= ruleStatementBody )
        {
        // InternalKactors.g:1698:5: (lv_elseCall_8_0= ruleStatementBody )
        // InternalKactors.g:1699:6: lv_elseCall_8_0= ruleStatementBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getIfStatementAccess().getElseCallStatementBodyParserRuleCall_4_1_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_elseCall_8_0=ruleStatementBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred54_InternalKactors

    // $ANTLR start synpred55_InternalKactors
    public final void synpred55_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_0_0 = null;


        // InternalKactors.g:1736:3: ( ( (lv_verb_0_0= ruleMessageCall ) ) )
        // InternalKactors.g:1736:3: ( (lv_verb_0_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1736:3: ( (lv_verb_0_0= ruleMessageCall ) )
        // InternalKactors.g:1737:4: (lv_verb_0_0= ruleMessageCall )
        {
        // InternalKactors.g:1737:4: (lv_verb_0_0= ruleMessageCall )
        // InternalKactors.g:1738:5: lv_verb_0_0= ruleMessageCall
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementBodyAccess().getVerbMessageCallParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_verb_0_0=ruleMessageCall();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred55_InternalKactors

    // $ANTLR start synpred56_InternalKactors
    public final void synpred56_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_value_1_0 = null;


        // InternalKactors.g:1756:3: ( ( (lv_value_1_0= ruleValue ) ) )
        // InternalKactors.g:1756:3: ( (lv_value_1_0= ruleValue ) )
        {
        // InternalKactors.g:1756:3: ( (lv_value_1_0= ruleValue ) )
        // InternalKactors.g:1757:4: (lv_value_1_0= ruleValue )
        {
        // InternalKactors.g:1757:4: (lv_value_1_0= ruleValue )
        // InternalKactors.g:1758:5: lv_value_1_0= ruleValue
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementBodyAccess().getValueValueParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_value_1_0=ruleValue();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred56_InternalKactors

    // $ANTLR start synpred58_InternalKactors
    public final void synpred58_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_match_0_0 = null;


        // InternalKactors.g:2023:3: ( ( (lv_match_0_0= ruleMatch ) ) )
        // InternalKactors.g:2023:3: ( (lv_match_0_0= ruleMatch ) )
        {
        // InternalKactors.g:2023:3: ( (lv_match_0_0= ruleMatch ) )
        // InternalKactors.g:2024:4: (lv_match_0_0= ruleMatch )
        {
        // InternalKactors.g:2024:4: (lv_match_0_0= ruleMatch )
        // InternalKactors.g:2025:5: lv_match_0_0= ruleMatch
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_match_0_0=ruleMatch();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred58_InternalKactors

    // $ANTLR start synpred60_InternalKactors
    public final void synpred60_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_4=null;
        EObject lv_matches_2_0 = null;

        EObject lv_matches_3_0 = null;


        // InternalKactors.g:2043:3: ( (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) )
        // InternalKactors.g:2043:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
        {
        // InternalKactors.g:2043:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
        // InternalKactors.g:2044:4: otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')'
        {
        otherlv_1=(Token)match(input,43,FOLLOW_30); if (state.failed) return ;
        // InternalKactors.g:2048:4: ( (lv_matches_2_0= ruleMatch ) )
        // InternalKactors.g:2049:5: (lv_matches_2_0= ruleMatch )
        {
        // InternalKactors.g:2049:5: (lv_matches_2_0= ruleMatch )
        // InternalKactors.g:2050:6: lv_matches_2_0= ruleMatch
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_1_1_0());
          					
        }
        pushFollow(FOLLOW_31);
        lv_matches_2_0=ruleMatch();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:2067:4: ( (lv_matches_3_0= ruleMatch ) )*
        loop116:
        do {
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( ((LA116_0>=RULE_OBSERVABLE && LA116_0<=RULE_LOWERCASE_ID)||LA116_0==RULE_STRING||(LA116_0>=RULE_EXPR && LA116_0<=RULE_INT)||LA116_0==43||LA116_0==50||(LA116_0>=52 && LA116_0<=53)||(LA116_0>=57 && LA116_0<=59)||(LA116_0>=77 && LA116_0<=78)) ) {
                alt116=1;
            }


            switch (alt116) {
        	case 1 :
        	    // InternalKactors.g:2068:5: (lv_matches_3_0= ruleMatch )
        	    {
        	    // InternalKactors.g:2068:5: (lv_matches_3_0= ruleMatch )
        	    // InternalKactors.g:2069:6: lv_matches_3_0= ruleMatch
        	    {
        	    if ( state.backtracking==0 ) {

        	      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_1_2_0());
        	      					
        	    }
        	    pushFollow(FOLLOW_31);
        	    lv_matches_3_0=ruleMatch();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }
        	    break;

        	default :
        	    break loop116;
            }
        } while (true);

        otherlv_4=(Token)match(input,44,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred60_InternalKactors

    // $ANTLR start synpred61_InternalKactors
    public final void synpred61_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_statement_5_0 = null;


        // InternalKactors.g:2092:3: ( ( (lv_statement_5_0= ruleStatement ) ) )
        // InternalKactors.g:2092:3: ( (lv_statement_5_0= ruleStatement ) )
        {
        // InternalKactors.g:2092:3: ( (lv_statement_5_0= ruleStatement ) )
        // InternalKactors.g:2093:4: (lv_statement_5_0= ruleStatement )
        {
        // InternalKactors.g:2093:4: (lv_statement_5_0= ruleStatement )
        // InternalKactors.g:2094:5: lv_statement_5_0= ruleStatement
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getActionsAccess().getStatementStatementParserRuleCall_2_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_statement_5_0=ruleStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred61_InternalKactors

    // $ANTLR start synpred64_InternalKactors
    public final void synpred64_InternalKactors_fragment() throws RecognitionException {   
        Token lv_boolean_3_1=null;
        Token lv_boolean_3_2=null;
        Token otherlv_4=null;
        EObject lv_body_5_0 = null;


        // InternalKactors.g:2204:3: ( ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2204:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2204:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
        // InternalKactors.g:2205:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) )
        {
        // InternalKactors.g:2205:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) )
        // InternalKactors.g:2206:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
        {
        // InternalKactors.g:2206:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
        // InternalKactors.g:2207:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
        {
        // InternalKactors.g:2207:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
        int alt117=2;
        int LA117_0 = input.LA(1);

        if ( (LA117_0==52) ) {
            alt117=1;
        }
        else if ( (LA117_0==53) ) {
            alt117=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 117, 0, input);

            throw nvae;
        }
        switch (alt117) {
            case 1 :
                // InternalKactors.g:2208:7: lv_boolean_3_1= 'true'
                {
                lv_boolean_3_1=(Token)match(input,52,FOLLOW_32); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:2219:7: lv_boolean_3_2= 'false'
                {
                lv_boolean_3_2=(Token)match(input,53,FOLLOW_32); if (state.failed) return ;

                }
                break;

        }


        }


        }

        otherlv_4=(Token)match(input,51,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2236:4: ( (lv_body_5_0= ruleStatementList ) )
        // InternalKactors.g:2237:5: (lv_body_5_0= ruleStatementList )
        {
        // InternalKactors.g:2237:5: (lv_body_5_0= ruleStatementList )
        // InternalKactors.g:2238:6: lv_body_5_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_1_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_5_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred64_InternalKactors

    // $ANTLR start synpred68_InternalKactors
    public final void synpred68_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        EObject lv_literal_15_0 = null;

        EObject lv_body_17_0 = null;


        // InternalKactors.g:2389:3: ( ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2389:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2389:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
        // InternalKactors.g:2390:4: ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) )
        {
        // InternalKactors.g:2390:4: ( (lv_literal_15_0= ruleLiteral ) )
        // InternalKactors.g:2391:5: (lv_literal_15_0= ruleLiteral )
        {
        // InternalKactors.g:2391:5: (lv_literal_15_0= ruleLiteral )
        // InternalKactors.g:2392:6: lv_literal_15_0= ruleLiteral
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_5_0_0());
          					
        }
        pushFollow(FOLLOW_32);
        lv_literal_15_0=ruleLiteral();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_16=(Token)match(input,51,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2413:4: ( (lv_body_17_0= ruleStatementList ) )
        // InternalKactors.g:2414:5: (lv_body_17_0= ruleStatementList )
        {
        // InternalKactors.g:2414:5: (lv_body_17_0= ruleStatementList )
        // InternalKactors.g:2415:6: lv_body_17_0= ruleStatementList
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
    // $ANTLR end synpred68_InternalKactors

    // $ANTLR start synpred69_InternalKactors
    public final void synpred69_InternalKactors_fragment() throws RecognitionException {   
        Token lv_text_18_0=null;
        Token otherlv_19=null;
        EObject lv_body_20_0 = null;


        // InternalKactors.g:2434:3: ( ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2434:3: ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2434:3: ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
        // InternalKactors.g:2435:4: ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) )
        {
        // InternalKactors.g:2435:4: ( (lv_text_18_0= RULE_STRING ) )
        // InternalKactors.g:2436:5: (lv_text_18_0= RULE_STRING )
        {
        // InternalKactors.g:2436:5: (lv_text_18_0= RULE_STRING )
        // InternalKactors.g:2437:6: lv_text_18_0= RULE_STRING
        {
        lv_text_18_0=(Token)match(input,RULE_STRING,FOLLOW_32); if (state.failed) return ;

        }


        }

        otherlv_19=(Token)match(input,51,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2457:4: ( (lv_body_20_0= ruleStatementList ) )
        // InternalKactors.g:2458:5: (lv_body_20_0= ruleStatementList )
        {
        // InternalKactors.g:2458:5: (lv_body_20_0= ruleStatementList )
        // InternalKactors.g:2459:6: lv_body_20_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_6_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_20_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred69_InternalKactors

    // $ANTLR start synpred73_InternalKactors
    public final void synpred73_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:2565:5: ( 'to' )
        // InternalKactors.g:2565:6: 'to'
        {
        match(input,56,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred73_InternalKactors

    // $ANTLR start synpred77_InternalKactors
    public final void synpred77_InternalKactors_fragment() throws RecognitionException {   
        Token lv_leftLimit_25_0=null;
        Token otherlv_26=null;
        Token otherlv_27=null;
        Token lv_rightLimit_29_0=null;
        Token otherlv_30=null;
        Token otherlv_31=null;
        EObject lv_int0_24_0 = null;

        EObject lv_int1_28_0 = null;

        EObject lv_body_32_0 = null;


        // InternalKactors.g:2523:3: ( ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2523:3: ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2523:3: ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) )
        // InternalKactors.g:2524:4: ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) )
        {
        // InternalKactors.g:2524:4: ( (lv_int0_24_0= ruleNumber ) )
        // InternalKactors.g:2525:5: (lv_int0_24_0= ruleNumber )
        {
        // InternalKactors.g:2525:5: (lv_int0_24_0= ruleNumber )
        // InternalKactors.g:2526:6: lv_int0_24_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getInt0NumberParserRuleCall_8_0_0());
          					
        }
        pushFollow(FOLLOW_33);
        lv_int0_24_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:2543:4: ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )?
        int alt118=3;
        int LA118_0 = input.LA(1);

        if ( (LA118_0==54) ) {
            alt118=1;
        }
        else if ( (LA118_0==55) ) {
            alt118=2;
        }
        switch (alt118) {
            case 1 :
                // InternalKactors.g:2544:5: ( (lv_leftLimit_25_0= 'inclusive' ) )
                {
                // InternalKactors.g:2544:5: ( (lv_leftLimit_25_0= 'inclusive' ) )
                // InternalKactors.g:2545:6: (lv_leftLimit_25_0= 'inclusive' )
                {
                // InternalKactors.g:2545:6: (lv_leftLimit_25_0= 'inclusive' )
                // InternalKactors.g:2546:7: lv_leftLimit_25_0= 'inclusive'
                {
                lv_leftLimit_25_0=(Token)match(input,54,FOLLOW_34); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:2559:5: otherlv_26= 'exclusive'
                {
                otherlv_26=(Token)match(input,55,FOLLOW_34); if (state.failed) return ;

                }
                break;

        }

        // InternalKactors.g:2564:4: ( ( 'to' )=>otherlv_27= 'to' )
        // InternalKactors.g:2565:5: ( 'to' )=>otherlv_27= 'to'
        {
        otherlv_27=(Token)match(input,56,FOLLOW_35); if (state.failed) return ;

        }

        // InternalKactors.g:2571:4: ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) )
        // InternalKactors.g:2572:5: ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber )
        {
        // InternalKactors.g:2576:5: (lv_int1_28_0= ruleNumber )
        // InternalKactors.g:2577:6: lv_int1_28_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getInt1NumberParserRuleCall_8_3_0());
          					
        }
        pushFollow(FOLLOW_36);
        lv_int1_28_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:2594:4: ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )?
        int alt119=3;
        int LA119_0 = input.LA(1);

        if ( (LA119_0==54) ) {
            alt119=1;
        }
        else if ( (LA119_0==55) ) {
            alt119=2;
        }
        switch (alt119) {
            case 1 :
                // InternalKactors.g:2595:5: ( (lv_rightLimit_29_0= 'inclusive' ) )
                {
                // InternalKactors.g:2595:5: ( (lv_rightLimit_29_0= 'inclusive' ) )
                // InternalKactors.g:2596:6: (lv_rightLimit_29_0= 'inclusive' )
                {
                // InternalKactors.g:2596:6: (lv_rightLimit_29_0= 'inclusive' )
                // InternalKactors.g:2597:7: lv_rightLimit_29_0= 'inclusive'
                {
                lv_rightLimit_29_0=(Token)match(input,54,FOLLOW_32); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:2610:5: otherlv_30= 'exclusive'
                {
                otherlv_30=(Token)match(input,55,FOLLOW_32); if (state.failed) return ;

                }
                break;

        }

        otherlv_31=(Token)match(input,51,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2619:4: ( (lv_body_32_0= ruleStatementList ) )
        // InternalKactors.g:2620:5: (lv_body_32_0= ruleStatementList )
        {
        // InternalKactors.g:2620:5: (lv_body_32_0= ruleStatementList )
        // InternalKactors.g:2621:6: lv_body_32_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_8_6_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_32_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred77_InternalKactors

    // $ANTLR start synpred79_InternalKactors
    public final void synpred79_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_38=null;
        EObject lv_quantity_37_0 = null;

        EObject lv_body_39_0 = null;


        // InternalKactors.g:2689:3: ( ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2689:3: ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2689:3: ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
        // InternalKactors.g:2690:4: ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) )
        {
        // InternalKactors.g:2690:4: ( (lv_quantity_37_0= ruleQuantity ) )
        // InternalKactors.g:2691:5: (lv_quantity_37_0= ruleQuantity )
        {
        // InternalKactors.g:2691:5: (lv_quantity_37_0= ruleQuantity )
        // InternalKactors.g:2692:6: lv_quantity_37_0= ruleQuantity
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getQuantityQuantityParserRuleCall_10_0_0());
          					
        }
        pushFollow(FOLLOW_32);
        lv_quantity_37_0=ruleQuantity();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_38=(Token)match(input,51,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2713:4: ( (lv_body_39_0= ruleStatementList ) )
        // InternalKactors.g:2714:5: (lv_body_39_0= ruleStatementList )
        {
        // InternalKactors.g:2714:5: (lv_body_39_0= ruleStatementList )
        // InternalKactors.g:2715:6: lv_body_39_0= ruleStatementList
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
    // $ANTLR end synpred79_InternalKactors

    // $ANTLR start synpred80_InternalKactors
    public final void synpred80_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_41=null;
        EObject lv_date_40_0 = null;

        EObject lv_body_42_0 = null;


        // InternalKactors.g:2734:3: ( ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2734:3: ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2734:3: ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
        // InternalKactors.g:2735:4: ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) )
        {
        // InternalKactors.g:2735:4: ( (lv_date_40_0= ruleDate ) )
        // InternalKactors.g:2736:5: (lv_date_40_0= ruleDate )
        {
        // InternalKactors.g:2736:5: (lv_date_40_0= ruleDate )
        // InternalKactors.g:2737:6: lv_date_40_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getDateDateParserRuleCall_11_0_0());
          					
        }
        pushFollow(FOLLOW_32);
        lv_date_40_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_41=(Token)match(input,51,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2758:4: ( (lv_body_42_0= ruleStatementList ) )
        // InternalKactors.g:2759:5: (lv_body_42_0= ruleStatementList )
        {
        // InternalKactors.g:2759:5: (lv_body_42_0= ruleStatementList )
        // InternalKactors.g:2760:6: lv_body_42_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_11_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_42_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred80_InternalKactors

    // $ANTLR start synpred100_InternalKactors
    public final void synpred100_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:3461:5: ( 'to' )
        // InternalKactors.g:3461:6: 'to'
        {
        match(input,56,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred100_InternalKactors

    // $ANTLR start synpred127_InternalKactors
    public final void synpred127_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:4180:5: ( 'to' )
        // InternalKactors.g:4180:6: 'to'
        {
        match(input,56,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred127_InternalKactors

    // $ANTLR start synpred142_InternalKactors
    public final void synpred142_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_number_0_0 = null;


        // InternalKactors.g:4546:3: ( ( (lv_number_0_0= ruleNumber ) ) )
        // InternalKactors.g:4546:3: ( (lv_number_0_0= ruleNumber ) )
        {
        // InternalKactors.g:4546:3: ( (lv_number_0_0= ruleNumber ) )
        // InternalKactors.g:4547:4: (lv_number_0_0= ruleNumber )
        {
        // InternalKactors.g:4547:4: (lv_number_0_0= ruleNumber )
        // InternalKactors.g:4548:5: lv_number_0_0= ruleNumber
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
    // $ANTLR end synpred142_InternalKactors

    // $ANTLR start synpred145_InternalKactors
    public final void synpred145_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_date_5_0 = null;


        // InternalKactors.g:4630:3: ( ( (lv_date_5_0= ruleDate ) ) )
        // InternalKactors.g:4630:3: ( (lv_date_5_0= ruleDate ) )
        {
        // InternalKactors.g:4630:3: ( (lv_date_5_0= ruleDate ) )
        // InternalKactors.g:4631:4: (lv_date_5_0= ruleDate )
        {
        // InternalKactors.g:4631:4: (lv_date_5_0= ruleDate )
        // InternalKactors.g:4632:5: lv_date_5_0= ruleDate
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
    // $ANTLR end synpred145_InternalKactors

    // $ANTLR start synpred174_InternalKactors
    public final void synpred174_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5450:4: ( ( RULE_INT ) )
        // InternalKactors.g:5450:5: ( RULE_INT )
        {
        // InternalKactors.g:5450:5: ( RULE_INT )
        // InternalKactors.g:5451:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred174_InternalKactors

    // $ANTLR start synpred175_InternalKactors
    public final void synpred175_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5472:4: ( ( 'l' ) )
        // InternalKactors.g:5472:5: ( 'l' )
        {
        // InternalKactors.g:5472:5: ( 'l' )
        // InternalKactors.g:5473:5: 'l'
        {
        match(input,79,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred175_InternalKactors

    // $ANTLR start synpred176_InternalKactors
    public final void synpred176_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5490:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5490:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5490:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKactors.g:5491:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKactors.g:5491:5: ( ( '.' ) )
        // InternalKactors.g:5492:6: ( '.' )
        {
        // InternalKactors.g:5492:6: ( '.' )
        // InternalKactors.g:5493:7: '.'
        {
        match(input,69,FOLLOW_10); if (state.failed) return ;

        }


        }

        // InternalKactors.g:5496:5: ( ( RULE_INT ) )
        // InternalKactors.g:5497:6: ( RULE_INT )
        {
        // InternalKactors.g:5497:6: ( RULE_INT )
        // InternalKactors.g:5498:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred176_InternalKactors

    // $ANTLR start synpred180_InternalKactors
    public final void synpred180_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5539:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5539:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5539:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKactors.g:5540:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKactors.g:5540:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKactors.g:5541:6: ( ( 'e' | 'E' ) )
        {
        // InternalKactors.g:5541:6: ( ( 'e' | 'E' ) )
        // InternalKactors.g:5542:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=80 && input.LA(1)<=81) ) {
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

        // InternalKactors.g:5549:5: ( '+' | ( ( '-' ) ) )?
        int alt135=3;
        int LA135_0 = input.LA(1);

        if ( (LA135_0==77) ) {
            alt135=1;
        }
        else if ( (LA135_0==78) ) {
            alt135=2;
        }
        switch (alt135) {
            case 1 :
                // InternalKactors.g:5550:6: '+'
                {
                match(input,77,FOLLOW_10); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:5552:6: ( ( '-' ) )
                {
                // InternalKactors.g:5552:6: ( ( '-' ) )
                // InternalKactors.g:5553:7: ( '-' )
                {
                // InternalKactors.g:5553:7: ( '-' )
                // InternalKactors.g:5554:8: '-'
                {
                match(input,78,FOLLOW_10); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKactors.g:5558:5: ( ( RULE_INT ) )
        // InternalKactors.g:5559:6: ( RULE_INT )
        {
        // InternalKactors.g:5559:6: ( RULE_INT )
        // InternalKactors.g:5560:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred180_InternalKactors

    // $ANTLR start synpred197_InternalKactors
    public final void synpred197_InternalKactors_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKactors.g:5994:4: (kw= '-' )
        // InternalKactors.g:5994:4: kw= '-'
        {
        kw=(Token)match(input,78,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred197_InternalKactors

    // $ANTLR start synpred198_InternalKactors
    public final void synpred198_InternalKactors_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKactors.g:6001:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKactors.g:6001:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred198_InternalKactors

    // Delegated rules

    public final boolean synpred25_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred25_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred197_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred197_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred37_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred37_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred68_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred68_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred47_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred47_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred175_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred175_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred22_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred22_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred56_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred56_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred54_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred54_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred176_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred176_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred69_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred69_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred23_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred23_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred38_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred38_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred145_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred145_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred142_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred142_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred60_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred60_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred79_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred79_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred174_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred174_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred64_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred64_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred21_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred21_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred61_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred61_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred198_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred198_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred73_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred73_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred39_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred39_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred127_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred127_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred77_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred77_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred58_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred58_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred27_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred27_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred34_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred34_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred180_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred180_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred40_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred40_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred55_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred55_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred100_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred100_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred46_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred46_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA14 dfa14 = new DFA14(this);
    protected DFA21 dfa21 = new DFA21(this);
    protected DFA26 dfa26 = new DFA26(this);
    protected DFA27 dfa27 = new DFA27(this);
    protected DFA30 dfa30 = new DFA30(this);
    protected DFA33 dfa33 = new DFA33(this);
    protected DFA37 dfa37 = new DFA37(this);
    protected DFA41 dfa41 = new DFA41(this);
    protected DFA42 dfa42 = new DFA42(this);
    protected DFA52 dfa52 = new DFA52(this);
    protected DFA54 dfa54 = new DFA54(this);
    protected DFA63 dfa63 = new DFA63(this);
    protected DFA69 dfa69 = new DFA69(this);
    protected DFA73 dfa73 = new DFA73(this);
    static final String dfa_1s = "\14\uffff";
    static final String dfa_2s = "\1\1\13\uffff";
    static final String dfa_3s = "\1\16\13\uffff";
    static final String dfa_4s = "\1\51\13\uffff";
    static final String dfa_5s = "\1\uffff\1\13\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12";
    static final String dfa_6s = "\1\0\13\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\17\uffff\1\2\1\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\1",
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

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 312:6: ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA14_0 = input.LA(1);

                         
                        int index14_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA14_0==EOF||LA14_0==RULE_ANNOTATION_ID||LA14_0==41) ) {s = 1;}

                        else if ( LA14_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {s = 2;}

                        else if ( LA14_0 == 32 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {s = 3;}

                        else if ( LA14_0 == 33 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {s = 4;}

                        else if ( LA14_0 == 34 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {s = 5;}

                        else if ( LA14_0 == 35 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {s = 6;}

                        else if ( LA14_0 == 36 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {s = 7;}

                        else if ( LA14_0 == 37 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {s = 8;}

                        else if ( LA14_0 == 38 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {s = 9;}

                        else if ( LA14_0 == 39 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {s = 10;}

                        else if ( LA14_0 == 40 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9) ) {s = 11;}

                         
                        input.seek(index14_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 14, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\42\uffff";
    static final String dfa_9s = "\1\2\41\uffff";
    static final String dfa_10s = "\1\4\1\0\40\uffff";
    static final String dfa_11s = "\1\116\1\0\40\uffff";
    static final String dfa_12s = "\2\uffff\1\2\36\uffff\1\1";
    static final String dfa_13s = "\1\uffff\1\0\40\uffff}>";
    static final String[] dfa_14s = {
            "\2\2\1\uffff\6\2\1\uffff\2\2\17\uffff\1\2\11\uffff\2\2\1\1\7\2\1\uffff\2\2\3\uffff\4\2\2\uffff\1\2\1\uffff\1\2\13\uffff\2\2",
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
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "1044:5: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA21_1 = input.LA(1);

                         
                        int index21_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred34_InternalKactors()) ) {s = 33;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index21_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 21, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_15s = "\26\uffff";
    static final String dfa_16s = "\1\4\2\0\23\uffff";
    static final String dfa_17s = "\1\116\2\0\23\uffff";
    static final String dfa_18s = "\3\uffff\1\3\1\4\1\5\1\6\1\7\1\10\13\uffff\1\1\1\2";
    static final String dfa_19s = "\1\uffff\1\0\1\1\23\uffff}>";
    static final String[] dfa_20s = {
            "\1\10\1\1\1\uffff\1\10\1\3\1\10\2\uffff\1\10\2\uffff\1\10\33\uffff\1\2\1\uffff\1\4\1\uffff\1\5\1\6\1\7\2\uffff\2\10\6\uffff\1\10\2\uffff\1\10\1\uffff\1\10\13\uffff\2\10",
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
            ""
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = dfa_15;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "1243:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA26_1 = input.LA(1);

                         
                        int index26_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred39_InternalKactors()) ) {s = 20;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index26_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA26_2 = input.LA(1);

                         
                        int index26_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred39_InternalKactors()) ) {s = 20;}

                        else if ( (synpred40_InternalKactors()) ) {s = 21;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index26_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 26, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = dfa_15;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "1425:3: ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA27_1 = input.LA(1);

                         
                        int index27_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred46_InternalKactors()) ) {s = 20;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index27_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA27_2 = input.LA(1);

                         
                        int index27_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred46_InternalKactors()) ) {s = 20;}

                        else if ( (synpred47_InternalKactors()) ) {s = 21;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index27_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 27, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_21s = "\21\uffff";
    static final String dfa_22s = "\1\4\2\0\16\uffff";
    static final String dfa_23s = "\1\116\2\0\16\uffff";
    static final String dfa_24s = "\3\uffff\1\2\13\uffff\1\1\1\3";
    static final String dfa_25s = "\1\uffff\1\0\1\1\16\uffff}>";
    static final String[] dfa_26s = {
            "\1\3\1\1\1\uffff\1\3\1\uffff\1\3\2\uffff\1\3\2\uffff\1\3\33\uffff\1\2\10\uffff\2\3\6\uffff\1\3\2\uffff\1\3\1\uffff\1\3\13\uffff\2\3",
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
            ""
    };

    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final char[] dfa_22 = DFA.unpackEncodedStringToUnsignedChars(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final short[] dfa_24 = DFA.unpackEncodedString(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[][] dfa_26 = unpackEncodedStringArray(dfa_26s);

    class DFA30 extends DFA {

        public DFA30(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 30;
            this.eot = dfa_21;
            this.eof = dfa_21;
            this.min = dfa_22;
            this.max = dfa_23;
            this.accept = dfa_24;
            this.special = dfa_25;
            this.transition = dfa_26;
        }
        public String getDescription() {
            return "1735:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA30_1 = input.LA(1);

                         
                        int index30_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred55_InternalKactors()) ) {s = 15;}

                        else if ( (synpred56_InternalKactors()) ) {s = 3;}

                         
                        input.seek(index30_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA30_2 = input.LA(1);

                         
                        int index30_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred55_InternalKactors()) ) {s = 15;}

                        else if ( (synpred56_InternalKactors()) ) {s = 3;}

                        else if ( (true) ) {s = 16;}

                         
                        input.seek(index30_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 30, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_27s = "\34\uffff";
    static final String dfa_28s = "\1\4\3\0\2\uffff\6\0\1\uffff\1\0\16\uffff";
    static final String dfa_29s = "\1\116\3\0\2\uffff\6\0\1\uffff\1\0\16\uffff";
    static final String dfa_30s = "\4\uffff\1\1\14\uffff\1\3\10\uffff\1\2\1\4";
    static final String dfa_31s = "\1\uffff\1\0\1\1\1\2\2\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\uffff\1\11\16\uffff}>";
    static final String[] dfa_32s = {
            "\1\6\1\1\1\uffff\1\12\1\21\1\15\2\4\1\11\2\uffff\1\21\33\uffff\1\13\1\uffff\1\21\1\uffff\3\21\1\4\1\uffff\1\2\1\3\3\uffff\3\4\1\21\2\uffff\1\21\1\uffff\1\21\13\uffff\1\7\1\10",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
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
            ""
    };

    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final char[] dfa_28 = DFA.unpackEncodedStringToUnsignedChars(dfa_28s);
    static final char[] dfa_29 = DFA.unpackEncodedStringToUnsignedChars(dfa_29s);
    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final short[] dfa_31 = DFA.unpackEncodedString(dfa_31s);
    static final short[][] dfa_32 = unpackEncodedStringArray(dfa_32s);

    class DFA33 extends DFA {

        public DFA33(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 33;
            this.eot = dfa_27;
            this.eof = dfa_27;
            this.min = dfa_28;
            this.max = dfa_29;
            this.accept = dfa_30;
            this.special = dfa_31;
            this.transition = dfa_32;
        }
        public String getDescription() {
            return "2022:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA33_1 = input.LA(1);

                         
                        int index33_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_InternalKactors()) ) {s = 4;}

                        else if ( (synpred61_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA33_2 = input.LA(1);

                         
                        int index33_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_InternalKactors()) ) {s = 4;}

                        else if ( (synpred61_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA33_3 = input.LA(1);

                         
                        int index33_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_InternalKactors()) ) {s = 4;}

                        else if ( (synpred61_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA33_6 = input.LA(1);

                         
                        int index33_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_InternalKactors()) ) {s = 4;}

                        else if ( (synpred61_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA33_7 = input.LA(1);

                         
                        int index33_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_InternalKactors()) ) {s = 4;}

                        else if ( (synpred61_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_7);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA33_8 = input.LA(1);

                         
                        int index33_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_InternalKactors()) ) {s = 4;}

                        else if ( (synpred61_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_8);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA33_9 = input.LA(1);

                         
                        int index33_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_InternalKactors()) ) {s = 4;}

                        else if ( (synpred61_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_9);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA33_10 = input.LA(1);

                         
                        int index33_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_InternalKactors()) ) {s = 4;}

                        else if ( (synpred61_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_10);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA33_11 = input.LA(1);

                         
                        int index33_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_InternalKactors()) ) {s = 4;}

                        else if ( (synpred60_InternalKactors()) ) {s = 26;}

                        else if ( (synpred61_InternalKactors()) ) {s = 17;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index33_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA33_13 = input.LA(1);

                         
                        int index33_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_InternalKactors()) ) {s = 4;}

                        else if ( (synpred61_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index33_13);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 33, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_33s = "\27\uffff";
    static final String dfa_34s = "\1\4\1\uffff\2\0\3\uffff\4\0\14\uffff";
    static final String dfa_35s = "\1\116\1\uffff\2\0\3\uffff\4\0\14\uffff";
    static final String dfa_36s = "\1\uffff\1\1\2\uffff\1\3\1\4\1\5\4\uffff\1\10\1\12\1\15\1\16\1\17\1\20\1\2\1\6\1\11\1\13\1\14\1\7";
    static final String dfa_37s = "\2\uffff\1\0\1\1\3\uffff\1\2\1\3\1\4\1\5\14\uffff}>";
    static final String[] dfa_38s = {
            "\1\6\1\1\1\uffff\1\12\1\uffff\1\15\1\4\1\5\1\11\36\uffff\1\13\6\uffff\1\14\1\uffff\1\2\1\3\3\uffff\1\16\1\17\1\20\21\uffff\1\7\1\10",
            "",
            "\1\uffff",
            "\1\uffff",
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

    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final char[] dfa_34 = DFA.unpackEncodedStringToUnsignedChars(dfa_34s);
    static final char[] dfa_35 = DFA.unpackEncodedStringToUnsignedChars(dfa_35s);
    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final short[] dfa_37 = DFA.unpackEncodedString(dfa_37s);
    static final short[][] dfa_38 = unpackEncodedStringArray(dfa_38s);

    class DFA37 extends DFA {

        public DFA37(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 37;
            this.eot = dfa_33;
            this.eof = dfa_33;
            this.min = dfa_34;
            this.max = dfa_35;
            this.accept = dfa_36;
            this.special = dfa_37;
            this.transition = dfa_38;
        }
        public String getDescription() {
            return "2159:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA37_2 = input.LA(1);

                         
                        int index37_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred64_InternalKactors()) ) {s = 17;}

                        else if ( (synpred68_InternalKactors()) ) {s = 18;}

                         
                        input.seek(index37_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA37_3 = input.LA(1);

                         
                        int index37_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred64_InternalKactors()) ) {s = 17;}

                        else if ( (synpred68_InternalKactors()) ) {s = 18;}

                         
                        input.seek(index37_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA37_7 = input.LA(1);

                         
                        int index37_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred68_InternalKactors()) ) {s = 18;}

                        else if ( (synpred77_InternalKactors()) ) {s = 19;}

                        else if ( (synpred79_InternalKactors()) ) {s = 20;}

                         
                        input.seek(index37_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA37_8 = input.LA(1);

                         
                        int index37_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred68_InternalKactors()) ) {s = 18;}

                        else if ( (synpred77_InternalKactors()) ) {s = 19;}

                        else if ( (synpred79_InternalKactors()) ) {s = 20;}

                         
                        input.seek(index37_8);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA37_9 = input.LA(1);

                         
                        int index37_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred68_InternalKactors()) ) {s = 18;}

                        else if ( (synpred77_InternalKactors()) ) {s = 19;}

                        else if ( (synpred79_InternalKactors()) ) {s = 20;}

                        else if ( (synpred80_InternalKactors()) ) {s = 21;}

                         
                        input.seek(index37_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA37_10 = input.LA(1);

                         
                        int index37_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred68_InternalKactors()) ) {s = 18;}

                        else if ( (synpred69_InternalKactors()) ) {s = 22;}

                         
                        input.seek(index37_10);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 37, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_39s = "\6\uffff";
    static final String dfa_40s = "\1\uffff\1\2\3\uffff\1\2";
    static final String dfa_41s = "\1\5\1\4\1\uffff\1\5\1\uffff\1\4";
    static final String dfa_42s = "\1\20\1\116\1\uffff\1\20\1\uffff\1\116";
    static final String dfa_43s = "\2\uffff\1\1\1\uffff\1\2\1\uffff";
    static final String dfa_44s = "\6\uffff}>";
    static final String[] dfa_45s = {
            "\1\1\12\uffff\1\2",
            "\2\2\1\uffff\6\2\1\uffff\2\2\17\uffff\1\2\11\uffff\1\2\1\uffff\10\2\1\uffff\2\2\3\uffff\5\2\1\4\3\2\2\uffff\1\2\1\3\7\uffff\2\2",
            "",
            "\1\5\12\uffff\1\2",
            "",
            "\2\2\1\uffff\6\2\1\uffff\2\2\17\uffff\1\2\11\uffff\1\2\1\uffff\10\2\1\uffff\2\2\3\uffff\5\2\1\4\3\2\2\uffff\1\2\1\3\7\uffff\2\2"
    };

    static final short[] dfa_39 = DFA.unpackEncodedString(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final char[] dfa_41 = DFA.unpackEncodedStringToUnsignedChars(dfa_41s);
    static final char[] dfa_42 = DFA.unpackEncodedStringToUnsignedChars(dfa_42s);
    static final short[] dfa_43 = DFA.unpackEncodedString(dfa_43s);
    static final short[] dfa_44 = DFA.unpackEncodedString(dfa_44s);
    static final short[][] dfa_45 = unpackEncodedStringArray(dfa_45s);

    class DFA41 extends DFA {

        public DFA41(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 41;
            this.eot = dfa_39;
            this.eof = dfa_40;
            this.min = dfa_41;
            this.max = dfa_42;
            this.accept = dfa_43;
            this.special = dfa_44;
            this.transition = dfa_45;
        }
        public String getDescription() {
            return "3056:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )";
        }
    }

    class DFA42 extends DFA {

        public DFA42(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 42;
            this.eot = dfa_39;
            this.eof = dfa_40;
            this.min = dfa_41;
            this.max = dfa_42;
            this.accept = dfa_43;
            this.special = dfa_44;
            this.transition = dfa_45;
        }
        public String getDescription() {
            return "3085:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )";
        }
    }
    static final String dfa_46s = "\4\uffff\1\20\7\uffff\1\20\5\uffff\1\20\2\uffff\1\20";
    static final String dfa_47s = "\1\4\1\uffff\2\14\1\52\7\uffff\1\52\3\14\2\uffff\1\52\2\14\1\52";
    static final String dfa_48s = "\1\116\1\uffff\2\14\1\121\7\uffff\1\121\1\14\2\116\2\uffff\1\121\2\14\1\70";
    static final String dfa_49s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\4\uffff\1\3\1\2\4\uffff";
    static final String dfa_50s = "\26\uffff}>";
    static final String[] dfa_51s = {
            "\1\7\1\10\1\uffff\1\6\4\uffff\1\4\45\uffff\1\5\1\uffff\2\1\3\uffff\1\12\1\13\3\uffff\1\11\11\uffff\5\11\1\2\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\20\13\uffff\3\21\14\uffff\1\15\11\uffff\1\14\1\16\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\20\13\uffff\3\21\14\uffff\1\15\12\uffff\1\16\1\17",
            "\1\22",
            "\1\25\100\uffff\1\23\1\24",
            "\1\25\100\uffff\1\23\1\24",
            "",
            "",
            "\1\20\13\uffff\3\21\27\uffff\1\16\1\17",
            "\1\25",
            "\1\25",
            "\1\20\13\uffff\3\21"
    };
    static final short[] dfa_46 = DFA.unpackEncodedString(dfa_46s);
    static final char[] dfa_47 = DFA.unpackEncodedStringToUnsignedChars(dfa_47s);
    static final char[] dfa_48 = DFA.unpackEncodedStringToUnsignedChars(dfa_48s);
    static final short[] dfa_49 = DFA.unpackEncodedString(dfa_49s);
    static final short[] dfa_50 = DFA.unpackEncodedString(dfa_50s);
    static final short[][] dfa_51 = unpackEncodedStringArray(dfa_51s);

    class DFA52 extends DFA {

        public DFA52(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 52;
            this.eot = dfa_15;
            this.eof = dfa_46;
            this.min = dfa_47;
            this.max = dfa_48;
            this.accept = dfa_49;
            this.special = dfa_50;
            this.transition = dfa_51;
        }
        public String getDescription() {
            return "3386:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )";
        }
    }
    static final String dfa_52s = "\2\uffff\1\3\2\uffff\1\3";
    static final String dfa_53s = "\1\4\1\uffff\1\15\1\uffff\1\4\1\15";
    static final String dfa_54s = "\1\116\1\uffff\1\103\1\uffff\1\116\1\103";
    static final String dfa_55s = "\1\uffff\1\1\1\uffff\1\2\2\uffff";
    static final String[] dfa_56s = {
            "\1\3\1\1\1\uffff\1\2\1\uffff\1\3\2\uffff\1\3\45\uffff\1\3\1\uffff\2\3\3\uffff\3\3\2\uffff\1\3\11\uffff\7\3",
            "",
            "\1\1\21\uffff\1\3\42\uffff\1\3\1\4",
            "",
            "\1\3\1\1\1\uffff\1\5\1\uffff\1\3\2\uffff\1\3\45\uffff\1\3\1\uffff\2\3\3\uffff\3\3\2\uffff\1\3\11\uffff\7\3",
            "\1\1\21\uffff\1\3\42\uffff\1\3\1\4"
    };
    static final short[] dfa_52 = DFA.unpackEncodedString(dfa_52s);
    static final char[] dfa_53 = DFA.unpackEncodedStringToUnsignedChars(dfa_53s);
    static final char[] dfa_54 = DFA.unpackEncodedStringToUnsignedChars(dfa_54s);
    static final short[] dfa_55 = DFA.unpackEncodedString(dfa_55s);
    static final short[][] dfa_56 = unpackEncodedStringArray(dfa_56s);

    class DFA54 extends DFA {

        public DFA54(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 54;
            this.eot = dfa_39;
            this.eof = dfa_52;
            this.min = dfa_53;
            this.max = dfa_54;
            this.accept = dfa_55;
            this.special = dfa_44;
            this.transition = dfa_56;
        }
        public String getDescription() {
            return "3761:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?";
        }
    }
    static final String dfa_57s = "\32\uffff";
    static final String dfa_58s = "\4\uffff\1\25\10\uffff\1\25\1\uffff\1\25\1\24\5\uffff\1\25\2\uffff\1\25";
    static final String dfa_59s = "\1\4\1\uffff\2\14\1\37\10\uffff\1\37\1\uffff\1\37\1\5\2\14\3\uffff\1\37\2\14\1\37";
    static final String dfa_60s = "\1\116\1\uffff\2\14\1\124\10\uffff\1\121\1\uffff\1\121\1\125\2\116\3\uffff\1\121\2\14\1\105";
    static final String dfa_61s = "\1\uffff\1\1\3\uffff\1\3\1\4\1\5\1\7\1\12\1\13\1\14\1\15\1\uffff\1\11\4\uffff\1\6\1\10\1\2\4\uffff";
    static final String dfa_62s = "\32\uffff}>";
    static final String[] dfa_63s = {
            "\1\6\2\uffff\1\5\1\uffff\1\11\2\uffff\1\4\45\uffff\1\10\1\uffff\2\1\3\uffff\1\12\1\13\1\14\2\uffff\1\7\11\uffff\5\7\1\2\1\3",
            "",
            "\1\15",
            "\1\15",
            "\1\25\26\uffff\3\23\11\uffff\2\25\1\24\1\20\10\uffff\1\16\1\17\1\21\1\22\3\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\25\26\uffff\3\23\11\uffff\2\25\1\24\1\20\11\uffff\1\17\1\21\1\22",
            "",
            "\1\25\26\uffff\3\23\11\uffff\2\25\1\24\1\20\12\uffff\1\21\1\22",
            "\1\24\4\uffff\1\24\1\uffff\1\26\3\uffff\1\24\16\uffff\1\24\13\uffff\1\24\16\uffff\1\24\7\uffff\3\24\20\uffff\1\24",
            "\1\31\100\uffff\1\27\1\30",
            "\1\31\100\uffff\1\27\1\30",
            "",
            "",
            "",
            "\1\25\26\uffff\3\23\11\uffff\2\25\2\24\12\uffff\1\21\1\22",
            "\1\31",
            "\1\31",
            "\1\25\26\uffff\3\23\11\uffff\2\25\2\24"
    };

    static final short[] dfa_57 = DFA.unpackEncodedString(dfa_57s);
    static final short[] dfa_58 = DFA.unpackEncodedString(dfa_58s);
    static final char[] dfa_59 = DFA.unpackEncodedStringToUnsignedChars(dfa_59s);
    static final char[] dfa_60 = DFA.unpackEncodedStringToUnsignedChars(dfa_60s);
    static final short[] dfa_61 = DFA.unpackEncodedString(dfa_61s);
    static final short[] dfa_62 = DFA.unpackEncodedString(dfa_62s);
    static final short[][] dfa_63 = unpackEncodedStringArray(dfa_63s);

    class DFA63 extends DFA {

        public DFA63(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 63;
            this.eot = dfa_57;
            this.eof = dfa_58;
            this.min = dfa_59;
            this.max = dfa_60;
            this.accept = dfa_61;
            this.special = dfa_62;
            this.transition = dfa_63;
        }
        public String getDescription() {
            return "4006:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )";
        }
    }
    static final String dfa_64s = "\3\uffff\1\13\2\uffff\2\13\7\uffff\1\13\2\uffff\2\13\2\uffff";
    static final String dfa_65s = "\1\7\2\14\1\4\2\uffff\2\4\3\14\1\uffff\1\14\2\uffff\1\4\2\14\2\4\1\14\1\0";
    static final String dfa_66s = "\1\116\2\14\1\124\2\uffff\2\121\1\14\2\116\1\uffff\1\14\2\uffff\1\121\2\14\1\116\1\121\1\14\1\0";
    static final String dfa_67s = "\4\uffff\1\3\1\5\5\uffff\1\1\1\uffff\1\2\1\4\7\uffff";
    static final String dfa_68s = "\25\uffff\1\0}>";
    static final String[] dfa_69s = {
            "\1\4\4\uffff\1\3\47\uffff\2\5\27\uffff\1\1\1\2",
            "\1\6",
            "\1\6",
            "\2\13\1\uffff\6\13\1\uffff\2\13\17\uffff\1\13\11\uffff\1\13\1\uffff\13\13\2\uffff\1\15\4\13\2\uffff\3\13\3\uffff\1\10\7\uffff\1\13\1\14\1\7\1\11\1\12\3\16",
            "",
            "",
            "\2\13\1\uffff\6\13\1\uffff\2\13\17\uffff\1\13\11\uffff\1\13\1\uffff\13\13\2\uffff\1\15\4\13\2\uffff\3\13\3\uffff\1\10\7\uffff\2\13\1\7\1\11\1\12",
            "\2\13\1\uffff\6\13\1\uffff\2\13\17\uffff\1\13\11\uffff\1\13\1\uffff\13\13\2\uffff\1\15\4\13\2\uffff\3\13\3\uffff\1\10\7\uffff\2\13\1\uffff\1\11\1\12",
            "\1\17",
            "\1\22\100\uffff\1\20\1\21",
            "\1\22\100\uffff\1\20\1\21",
            "",
            "\1\23",
            "",
            "",
            "\2\13\1\uffff\6\13\1\uffff\2\13\17\uffff\1\13\11\uffff\1\13\1\uffff\13\13\2\uffff\1\15\4\13\2\uffff\3\13\13\uffff\2\13\1\uffff\1\11\1\12",
            "\1\22",
            "\1\22",
            "\2\13\1\uffff\6\13\1\uffff\2\13\17\uffff\1\13\11\uffff\1\13\1\uffff\13\13\2\uffff\1\15\4\13\2\uffff\3\13\13\uffff\2\13",
            "\2\13\1\uffff\6\13\1\uffff\2\13\17\uffff\1\13\11\uffff\1\13\1\uffff\22\13\2\uffff\1\13\1\uffff\1\13\2\uffff\2\13\7\uffff\1\13\1\24\3\13",
            "\1\25",
            "\1\uffff"
    };
    static final short[] dfa_64 = DFA.unpackEncodedString(dfa_64s);
    static final char[] dfa_65 = DFA.unpackEncodedStringToUnsignedChars(dfa_65s);
    static final char[] dfa_66 = DFA.unpackEncodedStringToUnsignedChars(dfa_66s);
    static final short[] dfa_67 = DFA.unpackEncodedString(dfa_67s);
    static final short[] dfa_68 = DFA.unpackEncodedString(dfa_68s);
    static final short[][] dfa_69 = unpackEncodedStringArray(dfa_69s);

    class DFA69 extends DFA {

        public DFA69(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 69;
            this.eot = dfa_15;
            this.eof = dfa_64;
            this.min = dfa_65;
            this.max = dfa_66;
            this.accept = dfa_67;
            this.special = dfa_68;
            this.transition = dfa_69;
        }
        public String getDescription() {
            return "4545:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA69_21 = input.LA(1);

                         
                        int index69_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred142_InternalKactors()) ) {s = 11;}

                        else if ( (synpred145_InternalKactors()) ) {s = 14;}

                         
                        input.seek(index69_21);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 69, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_70s = "\15\uffff";
    static final String dfa_71s = "\3\uffff\1\13\10\uffff\1\13";
    static final String dfa_72s = "\1\4\2\uffff\1\4\6\uffff\1\5\1\uffff\1\4";
    static final String dfa_73s = "\1\116\2\uffff\1\116\6\uffff\1\5\1\uffff\1\116";
    static final String dfa_74s = "\1\uffff\1\1\1\2\1\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\uffff\1\3\1\uffff";
    static final String dfa_75s = "\15\uffff}>";
    static final String[] dfa_76s = {
            "\1\7\1\3\1\uffff\1\2\1\uffff\1\10\2\uffff\1\2\2\uffff\1\1\33\uffff\1\5\10\uffff\2\2\6\uffff\1\4\2\uffff\1\6\1\uffff\1\11\13\uffff\2\2",
            "",
            "",
            "\2\13\1\uffff\6\13\1\uffff\2\13\17\uffff\1\13\11\uffff\1\13\1\4\10\13\1\uffff\2\13\3\uffff\4\13\2\uffff\3\13\3\uffff\1\12\7\uffff\2\13",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\14",
            "",
            "\2\13\1\uffff\6\13\1\uffff\2\13\17\uffff\1\13\11\uffff\1\13\1\4\10\13\1\uffff\2\13\3\uffff\4\13\2\uffff\3\13\3\uffff\1\12\7\uffff\2\13"
    };

    static final short[] dfa_70 = DFA.unpackEncodedString(dfa_70s);
    static final short[] dfa_71 = DFA.unpackEncodedString(dfa_71s);
    static final char[] dfa_72 = DFA.unpackEncodedStringToUnsignedChars(dfa_72s);
    static final char[] dfa_73 = DFA.unpackEncodedStringToUnsignedChars(dfa_73s);
    static final short[] dfa_74 = DFA.unpackEncodedString(dfa_74s);
    static final short[] dfa_75 = DFA.unpackEncodedString(dfa_75s);
    static final short[][] dfa_76 = unpackEncodedStringArray(dfa_76s);

    class DFA73 extends DFA {

        public DFA73(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 73;
            this.eot = dfa_70;
            this.eof = dfa_71;
            this.min = dfa_72;
            this.max = dfa_73;
            this.accept = dfa_74;
            this.special = dfa_75;
            this.transition = dfa_76;
        }
        public String getDescription() {
            return "4840:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000020000004002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x000001FF40000002L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x000001FFC0000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000080000000010L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x00000000000000E0L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x000001FF40000082L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000020000004000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x00000C0000000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x9033A800000093B0L,0x0000000000006002L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000100000000020L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000100080000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x9033A800000093B2L,0x0000000000006002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x00000C0000000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x9033B800000093B0L,0x0000000000006002L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x9E37A80000009FB0L,0x0000000000006002L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0E34080000001EB0L,0x0000000000006000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0E34180000001EB0L,0x0000000000006000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x01C0000000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000001000L,0x0000000000006000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x00C8000000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000011020L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0800040000000002L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000010020L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x46340000000010B0L,0x0000000000007F01L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000080000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x46340000000010B0L,0x0000000000007F00L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x00C0000000000002L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x4E340000000012B0L,0x0000000000007F04L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x4E340000000012B0L,0x0000000000007F00L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000008L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0400080000010420L,0x0000000000200010L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x4000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0400180000010420L,0x0000000000200010L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0400000000000002L,0x0000000000200010L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000080000010420L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000002L,0x0000000000038020L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000002L,0x0000000000030020L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000002L,0x0000000000030000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x00000000001C4000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000030L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000010022L,0x0000000000004020L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000010022L,0x0000000000004000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000010022L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000082L});

}

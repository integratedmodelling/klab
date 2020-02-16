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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_OBSERVABLE", "RULE_LOWERCASE_ID", "RULE_ID", "RULE_STRING", "RULE_EMBEDDEDTEXT", "RULE_EXPR", "RULE_CAMELCASE_ID", "RULE_REGEXP", "RULE_INT", "RULE_SEPARATOR", "RULE_ANNOTATION_ID", "RULE_ARGVALUE", "RULE_UPPERCASE_ID", "RULE_UPPERCASE_PATH", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'app'", "'user'", "'lib'", "'actor'", "'import'", "','", "'worldview'", "'observable'", "'label'", "'description'", "'permissions'", "'author'", "'version'", "'created'", "'modified'", "'message'", "':'", "'('", "')'", "';'", "'if'", "'else'", "'while'", "'do'", "'for'", "'in'", "'->'", "'true'", "'false'", "'inclusive'", "'exclusive'", "'to'", "'unknown'", "'*'", "'#'", "'urn:klab:'", "'&'", "'='", "'{'", "'}'", "'{{'", "'}}'", "'|'", "'/'", "'.'", "'=?'", "'@'", "'>'", "'<'", "'!='", "'<='", "'>='", "'+'", "'-'", "'l'", "'e'", "'E'", "'AD'", "'CE'", "'BC'", "'^'"
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
                case 24:
                case 25:
                case 26:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
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
                case 37:
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

                if ( (LA2_0==RULE_ANNOTATION_ID||LA2_0==37) ) {
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
    // InternalKactors.g:153:1: rulePreamble returns [EObject current=null] : ( () ( ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( (lv_lib_3_0= 'lib' ) ) | ( (lv_actor_4_0= 'actor' ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) ;
    public final EObject rulePreamble() throws RecognitionException {
        EObject current = null;

        Token lv_app_1_0=null;
        Token lv_user_2_0=null;
        Token lv_lib_3_0=null;
        Token lv_actor_4_0=null;
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
            // InternalKactors.g:162:2: ( ( () ( ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( (lv_lib_3_0= 'lib' ) ) | ( (lv_actor_4_0= 'actor' ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) )
            // InternalKactors.g:163:2: ( () ( ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( (lv_lib_3_0= 'lib' ) ) | ( (lv_actor_4_0= 'actor' ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            {
            // InternalKactors.g:163:2: ( () ( ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( (lv_lib_3_0= 'lib' ) ) | ( (lv_actor_4_0= 'actor' ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            // InternalKactors.g:164:3: () ( ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( (lv_lib_3_0= 'lib' ) ) | ( (lv_actor_4_0= 'actor' ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
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

            // InternalKactors.g:174:3: ( ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( (lv_lib_3_0= 'lib' ) ) | ( (lv_actor_4_0= 'actor' ) ) ) ( (lv_name_5_0= rulePathName ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0>=22 && LA4_0<=25)) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalKactors.g:175:4: ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( (lv_lib_3_0= 'lib' ) ) | ( (lv_actor_4_0= 'actor' ) ) ) ( (lv_name_5_0= rulePathName ) )
                    {
                    // InternalKactors.g:175:4: ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( (lv_lib_3_0= 'lib' ) ) | ( (lv_actor_4_0= 'actor' ) ) )
                    int alt3=4;
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
                    case 25:
                        {
                        alt3=4;
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
                            // InternalKactors.g:176:5: ( (lv_app_1_0= 'app' ) )
                            {
                            // InternalKactors.g:176:5: ( (lv_app_1_0= 'app' ) )
                            // InternalKactors.g:177:6: (lv_app_1_0= 'app' )
                            {
                            // InternalKactors.g:177:6: (lv_app_1_0= 'app' )
                            // InternalKactors.g:178:7: lv_app_1_0= 'app'
                            {
                            lv_app_1_0=(Token)match(input,22,FOLLOW_4); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_app_1_0, grammarAccess.getPreambleAccess().getAppAppKeyword_1_0_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getPreambleRule());
                              							}
                              							setWithLastConsumed(current, "app", true, "app");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:191:5: ( (lv_user_2_0= 'user' ) )
                            {
                            // InternalKactors.g:191:5: ( (lv_user_2_0= 'user' ) )
                            // InternalKactors.g:192:6: (lv_user_2_0= 'user' )
                            {
                            // InternalKactors.g:192:6: (lv_user_2_0= 'user' )
                            // InternalKactors.g:193:7: lv_user_2_0= 'user'
                            {
                            lv_user_2_0=(Token)match(input,23,FOLLOW_4); if (state.failed) return current;
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
                            // InternalKactors.g:206:5: ( (lv_lib_3_0= 'lib' ) )
                            {
                            // InternalKactors.g:206:5: ( (lv_lib_3_0= 'lib' ) )
                            // InternalKactors.g:207:6: (lv_lib_3_0= 'lib' )
                            {
                            // InternalKactors.g:207:6: (lv_lib_3_0= 'lib' )
                            // InternalKactors.g:208:7: lv_lib_3_0= 'lib'
                            {
                            lv_lib_3_0=(Token)match(input,24,FOLLOW_4); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_lib_3_0, grammarAccess.getPreambleAccess().getLibLibKeyword_1_0_2_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getPreambleRule());
                              							}
                              							setWithLastConsumed(current, "lib", true, "lib");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 4 :
                            // InternalKactors.g:221:5: ( (lv_actor_4_0= 'actor' ) )
                            {
                            // InternalKactors.g:221:5: ( (lv_actor_4_0= 'actor' ) )
                            // InternalKactors.g:222:6: (lv_actor_4_0= 'actor' )
                            {
                            // InternalKactors.g:222:6: (lv_actor_4_0= 'actor' )
                            // InternalKactors.g:223:7: lv_actor_4_0= 'actor'
                            {
                            lv_actor_4_0=(Token)match(input,25,FOLLOW_4); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_actor_4_0, grammarAccess.getPreambleAccess().getActorActorKeyword_1_0_3_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getPreambleRule());
                              							}
                              							setWithLastConsumed(current, "actor", true, "actor");
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    // InternalKactors.g:236:4: ( (lv_name_5_0= rulePathName ) )
                    // InternalKactors.g:237:5: (lv_name_5_0= rulePathName )
                    {
                    // InternalKactors.g:237:5: (lv_name_5_0= rulePathName )
                    // InternalKactors.g:238:6: lv_name_5_0= rulePathName
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

            // InternalKactors.g:256:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
            // InternalKactors.g:257:4: ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) )
            {
            // InternalKactors.g:257:4: ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) )
            // InternalKactors.g:258:5: ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* )
            {
            getUnorderedGroupHelper().enter(grammarAccess.getPreambleAccess().getUnorderedGroup_2());
            // InternalKactors.g:261:5: ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* )
            // InternalKactors.g:262:6: ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )*
            {
            // InternalKactors.g:262:6: ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )*
            loop11:
            do {
                int alt11=11;
                alt11 = dfa11.predict(input);
                switch (alt11) {
            	case 1 :
            	    // InternalKactors.g:263:4: ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) )
            	    {
            	    // InternalKactors.g:263:4: ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) )
            	    // InternalKactors.g:264:5: {...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
            	    }
            	    // InternalKactors.g:264:105: ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) )
            	    // InternalKactors.g:265:6: ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
            	    // InternalKactors.g:268:9: ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) )
            	    // InternalKactors.g:268:10: {...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:268:19: (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* )
            	    // InternalKactors.g:268:20: otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )*
            	    {
            	    otherlv_7=(Token)match(input,26,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_7, grammarAccess.getPreambleAccess().getImportKeyword_2_0_0());
            	      								
            	    }
            	    // InternalKactors.g:272:9: ( (lv_imports_8_0= rulePathName ) )
            	    // InternalKactors.g:273:10: (lv_imports_8_0= rulePathName )
            	    {
            	    // InternalKactors.g:273:10: (lv_imports_8_0= rulePathName )
            	    // InternalKactors.g:274:11: lv_imports_8_0= rulePathName
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

            	    // InternalKactors.g:291:9: (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )*
            	    loop5:
            	    do {
            	        int alt5=2;
            	        int LA5_0 = input.LA(1);

            	        if ( (LA5_0==27) ) {
            	            alt5=1;
            	        }


            	        switch (alt5) {
            	    	case 1 :
            	    	    // InternalKactors.g:292:10: otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) )
            	    	    {
            	    	    otherlv_9=(Token)match(input,27,FOLLOW_4); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      										newLeafNode(otherlv_9, grammarAccess.getPreambleAccess().getCommaKeyword_2_0_2_0());
            	    	      									
            	    	    }
            	    	    // InternalKactors.g:296:10: ( (lv_imports_10_0= rulePathName ) )
            	    	    // InternalKactors.g:297:11: (lv_imports_10_0= rulePathName )
            	    	    {
            	    	    // InternalKactors.g:297:11: (lv_imports_10_0= rulePathName )
            	    	    // InternalKactors.g:298:12: lv_imports_10_0= rulePathName
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
            	    	    break loop5;
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
            	    // InternalKactors.g:322:4: ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:322:4: ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) )
            	    // InternalKactors.g:323:5: {...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
            	    }
            	    // InternalKactors.g:323:105: ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) )
            	    // InternalKactors.g:324:6: ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
            	    // InternalKactors.g:327:9: ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) )
            	    // InternalKactors.g:327:10: {...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:327:19: (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) )
            	    // InternalKactors.g:327:20: otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) )
            	    {
            	    otherlv_11=(Token)match(input,28,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_11, grammarAccess.getPreambleAccess().getWorldviewKeyword_2_1_0());
            	      								
            	    }
            	    // InternalKactors.g:331:9: ( (lv_worldview_12_0= rulePathName ) )
            	    // InternalKactors.g:332:10: (lv_worldview_12_0= rulePathName )
            	    {
            	    // InternalKactors.g:332:10: (lv_worldview_12_0= rulePathName )
            	    // InternalKactors.g:333:11: lv_worldview_12_0= rulePathName
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
            	    // InternalKactors.g:356:4: ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:356:4: ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) )
            	    // InternalKactors.g:357:5: {...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
            	    }
            	    // InternalKactors.g:357:105: ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) )
            	    // InternalKactors.g:358:6: ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
            	    // InternalKactors.g:361:9: ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) )
            	    // InternalKactors.g:361:10: {...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:361:19: (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) )
            	    // InternalKactors.g:361:20: otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) )
            	    {
            	    otherlv_13=(Token)match(input,29,FOLLOW_7); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_13, grammarAccess.getPreambleAccess().getObservableKeyword_2_2_0());
            	      								
            	    }
            	    // InternalKactors.g:365:9: ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) )
            	    int alt6=2;
            	    int LA6_0 = input.LA(1);

            	    if ( (LA6_0==RULE_OBSERVABLE) ) {
            	        alt6=1;
            	    }
            	    else if ( (LA6_0==39) ) {
            	        alt6=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 6, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt6) {
            	        case 1 :
            	            // InternalKactors.g:366:10: ( (lv_observable_14_0= RULE_OBSERVABLE ) )
            	            {
            	            // InternalKactors.g:366:10: ( (lv_observable_14_0= RULE_OBSERVABLE ) )
            	            // InternalKactors.g:367:11: (lv_observable_14_0= RULE_OBSERVABLE )
            	            {
            	            // InternalKactors.g:367:11: (lv_observable_14_0= RULE_OBSERVABLE )
            	            // InternalKactors.g:368:12: lv_observable_14_0= RULE_OBSERVABLE
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
            	            // InternalKactors.g:385:10: ( (lv_observables_15_0= ruleList ) )
            	            {
            	            // InternalKactors.g:385:10: ( (lv_observables_15_0= ruleList ) )
            	            // InternalKactors.g:386:11: (lv_observables_15_0= ruleList )
            	            {
            	            // InternalKactors.g:386:11: (lv_observables_15_0= ruleList )
            	            // InternalKactors.g:387:12: lv_observables_15_0= ruleList
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
            	    // InternalKactors.g:411:4: ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:411:4: ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) )
            	    // InternalKactors.g:412:5: {...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
            	    }
            	    // InternalKactors.g:412:105: ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:413:6: ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
            	    // InternalKactors.g:416:9: ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:416:10: {...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:416:19: (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) )
            	    // InternalKactors.g:416:20: otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) )
            	    {
            	    otherlv_16=(Token)match(input,30,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_16, grammarAccess.getPreambleAccess().getLabelKeyword_2_3_0());
            	      								
            	    }
            	    // InternalKactors.g:420:9: ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) )
            	    // InternalKactors.g:421:10: ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:421:10: ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) )
            	    // InternalKactors.g:422:11: (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING )
            	    {
            	    // InternalKactors.g:422:11: (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING )
            	    int alt7=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_LOWERCASE_ID:
            	        {
            	        alt7=1;
            	        }
            	        break;
            	    case RULE_ID:
            	        {
            	        alt7=2;
            	        }
            	        break;
            	    case RULE_STRING:
            	        {
            	        alt7=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 7, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt7) {
            	        case 1 :
            	            // InternalKactors.g:423:12: lv_label_17_1= RULE_LOWERCASE_ID
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
            	            // InternalKactors.g:438:12: lv_label_17_2= RULE_ID
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
            	            // InternalKactors.g:453:12: lv_label_17_3= RULE_STRING
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
            	    // InternalKactors.g:476:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:476:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:477:5: {...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4)");
            	    }
            	    // InternalKactors.g:477:105: ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:478:6: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4);
            	    // InternalKactors.g:481:9: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:481:10: {...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:481:19: (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
            	    // InternalKactors.g:481:20: otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) )
            	    {
            	    otherlv_18=(Token)match(input,31,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_18, grammarAccess.getPreambleAccess().getDescriptionKeyword_2_4_0());
            	      								
            	    }
            	    // InternalKactors.g:485:9: ( (lv_description_19_0= RULE_STRING ) )
            	    // InternalKactors.g:486:10: (lv_description_19_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:486:10: (lv_description_19_0= RULE_STRING )
            	    // InternalKactors.g:487:11: lv_description_19_0= RULE_STRING
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
            	    // InternalKactors.g:509:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:509:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:510:5: {...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5)");
            	    }
            	    // InternalKactors.g:510:105: ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:511:6: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5);
            	    // InternalKactors.g:514:9: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:514:10: {...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:514:19: (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
            	    // InternalKactors.g:514:20: otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) )
            	    {
            	    otherlv_20=(Token)match(input,32,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_20, grammarAccess.getPreambleAccess().getPermissionsKeyword_2_5_0());
            	      								
            	    }
            	    // InternalKactors.g:518:9: ( (lv_permissions_21_0= RULE_STRING ) )
            	    // InternalKactors.g:519:10: (lv_permissions_21_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:519:10: (lv_permissions_21_0= RULE_STRING )
            	    // InternalKactors.g:520:11: lv_permissions_21_0= RULE_STRING
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
            	    // InternalKactors.g:542:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
            	    {
            	    // InternalKactors.g:542:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
            	    // InternalKactors.g:543:5: {...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6)");
            	    }
            	    // InternalKactors.g:543:105: ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
            	    // InternalKactors.g:544:6: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6);
            	    // InternalKactors.g:547:9: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
            	    int cnt8=0;
            	    loop8:
            	    do {
            	        int alt8=2;
            	        int LA8_0 = input.LA(1);

            	        if ( (LA8_0==33) ) {
            	            int LA8_2 = input.LA(2);

            	            if ( ((synpred17_InternalKactors()&&(true))) ) {
            	                alt8=1;
            	            }


            	        }


            	        switch (alt8) {
            	    	case 1 :
            	    	    // InternalKactors.g:547:10: {...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    	    }
            	    	    // InternalKactors.g:547:19: (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
            	    	    // InternalKactors.g:547:20: otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) )
            	    	    {
            	    	    otherlv_22=(Token)match(input,33,FOLLOW_9); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      									newLeafNode(otherlv_22, grammarAccess.getPreambleAccess().getAuthorKeyword_2_6_0());
            	    	      								
            	    	    }
            	    	    // InternalKactors.g:551:9: ( (lv_authors_23_0= RULE_STRING ) )
            	    	    // InternalKactors.g:552:10: (lv_authors_23_0= RULE_STRING )
            	    	    {
            	    	    // InternalKactors.g:552:10: (lv_authors_23_0= RULE_STRING )
            	    	    // InternalKactors.g:553:11: lv_authors_23_0= RULE_STRING
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
            	    	    if ( cnt8 >= 1 ) break loop8;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(8, input);
            	                throw eee;
            	        }
            	        cnt8++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 8 :
            	    // InternalKactors.g:575:4: ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:575:4: ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) )
            	    // InternalKactors.g:576:5: {...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7)");
            	    }
            	    // InternalKactors.g:576:105: ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) )
            	    // InternalKactors.g:577:6: ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7);
            	    // InternalKactors.g:580:9: ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) )
            	    // InternalKactors.g:580:10: {...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:580:19: (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) )
            	    // InternalKactors.g:580:20: otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) )
            	    {
            	    otherlv_24=(Token)match(input,34,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_24, grammarAccess.getPreambleAccess().getVersionKeyword_2_7_0());
            	      								
            	    }
            	    // InternalKactors.g:584:9: ( (lv_version_25_0= ruleVersionNumber ) )
            	    // InternalKactors.g:585:10: (lv_version_25_0= ruleVersionNumber )
            	    {
            	    // InternalKactors.g:585:10: (lv_version_25_0= ruleVersionNumber )
            	    // InternalKactors.g:586:11: lv_version_25_0= ruleVersionNumber
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
            	    // InternalKactors.g:609:4: ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:609:4: ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:610:5: {...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8)");
            	    }
            	    // InternalKactors.g:610:105: ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:611:6: ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8);
            	    // InternalKactors.g:614:9: ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:614:10: {...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:614:19: (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? )
            	    // InternalKactors.g:614:20: otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )?
            	    {
            	    otherlv_26=(Token)match(input,35,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_26, grammarAccess.getPreambleAccess().getCreatedKeyword_2_8_0());
            	      								
            	    }
            	    // InternalKactors.g:618:9: ( (lv_created_27_0= ruleDate ) )
            	    // InternalKactors.g:619:10: (lv_created_27_0= ruleDate )
            	    {
            	    // InternalKactors.g:619:10: (lv_created_27_0= ruleDate )
            	    // InternalKactors.g:620:11: lv_created_27_0= ruleDate
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

            	    // InternalKactors.g:637:9: ( (lv_createcomment_28_0= RULE_STRING ) )?
            	    int alt9=2;
            	    int LA9_0 = input.LA(1);

            	    if ( (LA9_0==RULE_STRING) ) {
            	        alt9=1;
            	    }
            	    switch (alt9) {
            	        case 1 :
            	            // InternalKactors.g:638:10: (lv_createcomment_28_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:638:10: (lv_createcomment_28_0= RULE_STRING )
            	            // InternalKactors.g:639:11: lv_createcomment_28_0= RULE_STRING
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
            	    // InternalKactors.g:661:4: ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:661:4: ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:662:5: {...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9)");
            	    }
            	    // InternalKactors.g:662:105: ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:663:6: ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9);
            	    // InternalKactors.g:666:9: ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:666:10: {...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:666:19: (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? )
            	    // InternalKactors.g:666:20: otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )?
            	    {
            	    otherlv_29=(Token)match(input,36,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_29, grammarAccess.getPreambleAccess().getModifiedKeyword_2_9_0());
            	      								
            	    }
            	    // InternalKactors.g:670:9: ( (lv_modified_30_0= ruleDate ) )
            	    // InternalKactors.g:671:10: (lv_modified_30_0= ruleDate )
            	    {
            	    // InternalKactors.g:671:10: (lv_modified_30_0= ruleDate )
            	    // InternalKactors.g:672:11: lv_modified_30_0= ruleDate
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

            	    // InternalKactors.g:689:9: ( (lv_modcomment_31_0= RULE_STRING ) )?
            	    int alt10=2;
            	    int LA10_0 = input.LA(1);

            	    if ( (LA10_0==RULE_STRING) ) {
            	        alt10=1;
            	    }
            	    switch (alt10) {
            	        case 1 :
            	            // InternalKactors.g:690:10: (lv_modcomment_31_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:690:10: (lv_modcomment_31_0= RULE_STRING )
            	            // InternalKactors.g:691:11: lv_modcomment_31_0= RULE_STRING
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
            	    break loop11;
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
    // InternalKactors.g:727:1: entryRuleDefinition returns [EObject current=null] : iv_ruleDefinition= ruleDefinition EOF ;
    public final EObject entryRuleDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDefinition = null;


        try {
            // InternalKactors.g:727:51: (iv_ruleDefinition= ruleDefinition EOF )
            // InternalKactors.g:728:2: iv_ruleDefinition= ruleDefinition EOF
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
    // InternalKactors.g:734:1: ruleDefinition returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'message' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) ) ;
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
            // InternalKactors.g:740:2: ( ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'message' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) ) )
            // InternalKactors.g:741:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'message' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) )
            {
            // InternalKactors.g:741:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'message' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) )
            // InternalKactors.g:742:3: ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'message' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) )
            {
            // InternalKactors.g:742:3: ( (lv_annotations_0_0= ruleAnnotation ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==RULE_ANNOTATION_ID) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalKactors.g:743:4: (lv_annotations_0_0= ruleAnnotation )
            	    {
            	    // InternalKactors.g:743:4: (lv_annotations_0_0= ruleAnnotation )
            	    // InternalKactors.g:744:5: lv_annotations_0_0= ruleAnnotation
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
            	    break loop12;
                }
            } while (true);

            otherlv_1=(Token)match(input,37,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getDefinitionAccess().getMessageKeyword_1());
              		
            }
            // InternalKactors.g:765:3: ( (lv_name_2_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:766:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:766:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:767:5: lv_name_2_0= RULE_LOWERCASE_ID
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

            // InternalKactors.g:783:3: ( (lv_arguments_3_0= ruleArgumentDeclaration ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==39) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalKactors.g:784:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:784:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    // InternalKactors.g:785:5: lv_arguments_3_0= ruleArgumentDeclaration
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

            otherlv_4=(Token)match(input,38,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDefinitionAccess().getColonKeyword_4());
              		
            }
            // InternalKactors.g:806:3: ( (lv_body_5_0= ruleMessageBody ) )
            // InternalKactors.g:807:4: (lv_body_5_0= ruleMessageBody )
            {
            // InternalKactors.g:807:4: (lv_body_5_0= ruleMessageBody )
            // InternalKactors.g:808:5: lv_body_5_0= ruleMessageBody
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
    // InternalKactors.g:829:1: entryRuleArgumentDeclaration returns [EObject current=null] : iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF ;
    public final EObject entryRuleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgumentDeclaration = null;


        try {
            // InternalKactors.g:829:60: (iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF )
            // InternalKactors.g:830:2: iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF
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
    // InternalKactors.g:836:1: ruleArgumentDeclaration returns [EObject current=null] : ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) ;
    public final EObject ruleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_ids_2_0=null;
        Token otherlv_3=null;
        Token lv_ids_4_0=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalKactors.g:842:2: ( ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) )
            // InternalKactors.g:843:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            {
            // InternalKactors.g:843:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            // InternalKactors.g:844:3: () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')'
            {
            // InternalKactors.g:844:3: ()
            // InternalKactors.g:845:4: 
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

            otherlv_1=(Token)match(input,39,FOLLOW_16); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getArgumentDeclarationAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:858:3: ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==RULE_LOWERCASE_ID) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalKactors.g:859:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    {
                    // InternalKactors.g:859:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:860:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:860:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:861:6: lv_ids_2_0= RULE_LOWERCASE_ID
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

                    // InternalKactors.g:877:4: (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==27) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // InternalKactors.g:878:5: otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    {
                    	    otherlv_3=(Token)match(input,27,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getArgumentDeclarationAccess().getCommaKeyword_2_1_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:882:5: ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    // InternalKactors.g:883:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    {
                    	    // InternalKactors.g:883:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    // InternalKactors.g:884:7: lv_ids_4_0= RULE_LOWERCASE_ID
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
                    	    break loop14;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:910:1: entryRuleMessageBody returns [EObject current=null] : iv_ruleMessageBody= ruleMessageBody EOF ;
    public final EObject entryRuleMessageBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageBody = null;


        try {
            // InternalKactors.g:910:52: (iv_ruleMessageBody= ruleMessageBody EOF )
            // InternalKactors.g:911:2: iv_ruleMessageBody= ruleMessageBody EOF
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
    // InternalKactors.g:917:1: ruleMessageBody returns [EObject current=null] : ( () ( (lv_lists_1_0= ruleStatementList ) )* ) ;
    public final EObject ruleMessageBody() throws RecognitionException {
        EObject current = null;

        EObject lv_lists_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:923:2: ( ( () ( (lv_lists_1_0= ruleStatementList ) )* ) )
            // InternalKactors.g:924:2: ( () ( (lv_lists_1_0= ruleStatementList ) )* )
            {
            // InternalKactors.g:924:2: ( () ( (lv_lists_1_0= ruleStatementList ) )* )
            // InternalKactors.g:925:3: () ( (lv_lists_1_0= ruleStatementList ) )*
            {
            // InternalKactors.g:925:3: ()
            // InternalKactors.g:926:4: 
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

            // InternalKactors.g:935:3: ( (lv_lists_1_0= ruleStatementList ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>=RULE_OBSERVABLE && LA16_0<=RULE_LOWERCASE_ID)||(LA16_0>=RULE_STRING && LA16_0<=RULE_EXPR)||LA16_0==RULE_INT||LA16_0==RULE_ARGVALUE||LA16_0==39||LA16_0==42||(LA16_0>=44 && LA16_0<=46)||(LA16_0>=49 && LA16_0<=50)||LA16_0==57||LA16_0==60||LA16_0==62||(LA16_0>=74 && LA16_0<=75)) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalKactors.g:936:4: (lv_lists_1_0= ruleStatementList )
            	    {
            	    // InternalKactors.g:936:4: (lv_lists_1_0= ruleStatementList )
            	    // InternalKactors.g:937:5: lv_lists_1_0= ruleStatementList
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
            	    break loop16;
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
    // InternalKactors.g:958:1: entryRuleMessageCall returns [EObject current=null] : iv_ruleMessageCall= ruleMessageCall EOF ;
    public final EObject entryRuleMessageCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageCall = null;


        try {
            // InternalKactors.g:958:52: (iv_ruleMessageCall= ruleMessageCall EOF )
            // InternalKactors.g:959:2: iv_ruleMessageCall= ruleMessageCall EOF
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
    // InternalKactors.g:965:1: ruleMessageCall returns [EObject current=null] : ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) ( (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) ) | otherlv_7= ';' )? ) ;
    public final EObject ruleMessageCall() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject this_StatementGroup_4 = null;

        EObject lv_actions_6_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:971:2: ( ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) ( (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) ) | otherlv_7= ';' )? ) )
            // InternalKactors.g:972:2: ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) ( (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) ) | otherlv_7= ';' )? )
            {
            // InternalKactors.g:972:2: ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) ( (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) ) | otherlv_7= ';' )? )
            // InternalKactors.g:973:3: ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) ( (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) ) | otherlv_7= ';' )?
            {
            // InternalKactors.g:973:3: ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==RULE_LOWERCASE_ID) ) {
                alt19=1;
            }
            else if ( (LA19_0==39) ) {
                alt19=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // InternalKactors.g:974:4: ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
                    {
                    // InternalKactors.g:974:4: ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
                    // InternalKactors.g:975:5: ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
                    {
                    // InternalKactors.g:975:5: ( (lv_name_0_0= rulePathName ) )
                    // InternalKactors.g:976:6: (lv_name_0_0= rulePathName )
                    {
                    // InternalKactors.g:976:6: (lv_name_0_0= rulePathName )
                    // InternalKactors.g:977:7: lv_name_0_0= rulePathName
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

                    // InternalKactors.g:994:5: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
                    int alt18=2;
                    alt18 = dfa18.predict(input);
                    switch (alt18) {
                        case 1 :
                            // InternalKactors.g:995:6: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                            {
                            otherlv_1=(Token)match(input,39,FOLLOW_20); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_1, grammarAccess.getMessageCallAccess().getLeftParenthesisKeyword_0_0_1_0());
                              					
                            }
                            // InternalKactors.g:999:6: ( (lv_parameters_2_0= ruleParameterList ) )?
                            int alt17=2;
                            int LA17_0 = input.LA(1);

                            if ( ((LA17_0>=RULE_OBSERVABLE && LA17_0<=RULE_LOWERCASE_ID)||LA17_0==RULE_STRING||LA17_0==RULE_EXPR||LA17_0==RULE_INT||LA17_0==RULE_ARGVALUE||LA17_0==39||(LA17_0>=49 && LA17_0<=50)||LA17_0==57||LA17_0==60||LA17_0==62||(LA17_0>=74 && LA17_0<=75)) ) {
                                alt17=1;
                            }
                            switch (alt17) {
                                case 1 :
                                    // InternalKactors.g:1000:7: (lv_parameters_2_0= ruleParameterList )
                                    {
                                    // InternalKactors.g:1000:7: (lv_parameters_2_0= ruleParameterList )
                                    // InternalKactors.g:1001:8: lv_parameters_2_0= ruleParameterList
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

                            otherlv_3=(Token)match(input,40,FOLLOW_22); if (state.failed) return current;
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
                    // InternalKactors.g:1025:4: this_StatementGroup_4= ruleStatementGroup
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

            // InternalKactors.g:1037:3: ( (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) ) | otherlv_7= ';' )?
            int alt20=3;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==38) ) {
                alt20=1;
            }
            else if ( (LA20_0==41) ) {
                alt20=2;
            }
            switch (alt20) {
                case 1 :
                    // InternalKactors.g:1038:4: (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )
                    {
                    // InternalKactors.g:1038:4: (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )
                    // InternalKactors.g:1039:5: otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) )
                    {
                    otherlv_5=(Token)match(input,38,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getMessageCallAccess().getColonKeyword_1_0_0());
                      				
                    }
                    // InternalKactors.g:1043:5: ( (lv_actions_6_0= ruleActions ) )
                    // InternalKactors.g:1044:6: (lv_actions_6_0= ruleActions )
                    {
                    // InternalKactors.g:1044:6: (lv_actions_6_0= ruleActions )
                    // InternalKactors.g:1045:7: lv_actions_6_0= ruleActions
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getMessageCallAccess().getActionsActionsParserRuleCall_1_0_1_0());
                      						
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


                    }
                    break;
                case 2 :
                    // InternalKactors.g:1064:4: otherlv_7= ';'
                    {
                    otherlv_7=(Token)match(input,41,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getMessageCallAccess().getSemicolonKeyword_1_1());
                      			
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
    // InternalKactors.g:1073:1: entryRuleStatementGroup returns [EObject current=null] : iv_ruleStatementGroup= ruleStatementGroup EOF ;
    public final EObject entryRuleStatementGroup() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementGroup = null;


        try {
            // InternalKactors.g:1073:55: (iv_ruleStatementGroup= ruleStatementGroup EOF )
            // InternalKactors.g:1074:2: iv_ruleStatementGroup= ruleStatementGroup EOF
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
    // InternalKactors.g:1080:1: ruleStatementGroup returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' ) ;
    public final EObject ruleStatementGroup() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1086:2: ( ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' ) )
            // InternalKactors.g:1087:2: ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' )
            {
            // InternalKactors.g:1087:2: ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' )
            // InternalKactors.g:1088:3: () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')'
            {
            // InternalKactors.g:1088:3: ()
            // InternalKactors.g:1089:4: 
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

            otherlv_1=(Token)match(input,39,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getStatementGroupAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:1102:3: ( (lv_body_2_0= ruleMessageBody ) )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( ((LA21_0>=RULE_OBSERVABLE && LA21_0<=RULE_LOWERCASE_ID)||(LA21_0>=RULE_STRING && LA21_0<=RULE_EXPR)||LA21_0==RULE_INT||LA21_0==RULE_ARGVALUE||LA21_0==39||LA21_0==42||(LA21_0>=44 && LA21_0<=46)||(LA21_0>=49 && LA21_0<=50)||LA21_0==57||LA21_0==60||LA21_0==62||(LA21_0>=74 && LA21_0<=75)) ) {
                alt21=1;
            }
            else if ( (LA21_0==40) ) {
                int LA21_2 = input.LA(2);

                if ( (synpred34_InternalKactors()) ) {
                    alt21=1;
                }
            }
            switch (alt21) {
                case 1 :
                    // InternalKactors.g:1103:4: (lv_body_2_0= ruleMessageBody )
                    {
                    // InternalKactors.g:1103:4: (lv_body_2_0= ruleMessageBody )
                    // InternalKactors.g:1104:5: lv_body_2_0= ruleMessageBody
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

            otherlv_3=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:1129:1: entryRuleStatementList returns [EObject current=null] : iv_ruleStatementList= ruleStatementList EOF ;
    public final EObject entryRuleStatementList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementList = null;


        try {
            // InternalKactors.g:1129:54: (iv_ruleStatementList= ruleStatementList EOF )
            // InternalKactors.g:1130:2: iv_ruleStatementList= ruleStatementList EOF
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
    // InternalKactors.g:1136:1: ruleStatementList returns [EObject current=null] : ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* ) ;
    public final EObject ruleStatementList() throws RecognitionException {
        EObject current = null;

        EObject lv_first_0_0 = null;

        EObject lv_next_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1142:2: ( ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* ) )
            // InternalKactors.g:1143:2: ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* )
            {
            // InternalKactors.g:1143:2: ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* )
            // InternalKactors.g:1144:3: ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )*
            {
            // InternalKactors.g:1144:3: ( (lv_first_0_0= ruleStatement ) )
            // InternalKactors.g:1145:4: (lv_first_0_0= ruleStatement )
            {
            // InternalKactors.g:1145:4: (lv_first_0_0= ruleStatement )
            // InternalKactors.g:1146:5: lv_first_0_0= ruleStatement
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

            // InternalKactors.g:1163:3: ( (lv_next_1_0= ruleNextStatement ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==27) ) {
                    int LA22_2 = input.LA(2);

                    if ( (synpred35_InternalKactors()) ) {
                        alt22=1;
                    }


                }


                switch (alt22) {
            	case 1 :
            	    // InternalKactors.g:1164:4: (lv_next_1_0= ruleNextStatement )
            	    {
            	    // InternalKactors.g:1164:4: (lv_next_1_0= ruleNextStatement )
            	    // InternalKactors.g:1165:5: lv_next_1_0= ruleNextStatement
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
            	    break loop22;
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
    // InternalKactors.g:1186:1: entryRuleStatement returns [EObject current=null] : iv_ruleStatement= ruleStatement EOF ;
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatement = null;


        try {
            // InternalKactors.g:1186:50: (iv_ruleStatement= ruleStatement EOF )
            // InternalKactors.g:1187:2: iv_ruleStatement= ruleStatement EOF
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
    // InternalKactors.g:1193:1: ruleStatement returns [EObject current=null] : ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) ) ;
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
            // InternalKactors.g:1199:2: ( ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) ) )
            // InternalKactors.g:1200:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) )
            {
            // InternalKactors.g:1200:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) )
            int alt23=8;
            alt23 = dfa23.predict(input);
            switch (alt23) {
                case 1 :
                    // InternalKactors.g:1201:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1201:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    // InternalKactors.g:1202:4: (lv_verb_0_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1202:4: (lv_verb_0_0= ruleMessageCall )
                    // InternalKactors.g:1203:5: lv_verb_0_0= ruleMessageCall
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
                    // InternalKactors.g:1221:3: ( (lv_group_1_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1221:3: ( (lv_group_1_0= ruleStatementGroup ) )
                    // InternalKactors.g:1222:4: (lv_group_1_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1222:4: (lv_group_1_0= ruleStatementGroup )
                    // InternalKactors.g:1223:5: lv_group_1_0= ruleStatementGroup
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
                    // InternalKactors.g:1241:3: ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:1241:3: ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:1242:4: (lv_text_2_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:1242:4: (lv_text_2_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:1243:5: lv_text_2_0= RULE_EMBEDDEDTEXT
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
                    // InternalKactors.g:1260:3: ( (lv_if_3_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:1260:3: ( (lv_if_3_0= ruleIfStatement ) )
                    // InternalKactors.g:1261:4: (lv_if_3_0= ruleIfStatement )
                    {
                    // InternalKactors.g:1261:4: (lv_if_3_0= ruleIfStatement )
                    // InternalKactors.g:1262:5: lv_if_3_0= ruleIfStatement
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
                    // InternalKactors.g:1280:3: ( (lv_while_4_0= ruleWhileStatement ) )
                    {
                    // InternalKactors.g:1280:3: ( (lv_while_4_0= ruleWhileStatement ) )
                    // InternalKactors.g:1281:4: (lv_while_4_0= ruleWhileStatement )
                    {
                    // InternalKactors.g:1281:4: (lv_while_4_0= ruleWhileStatement )
                    // InternalKactors.g:1282:5: lv_while_4_0= ruleWhileStatement
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
                    // InternalKactors.g:1300:3: ( (lv_do_5_0= ruleDoStatement ) )
                    {
                    // InternalKactors.g:1300:3: ( (lv_do_5_0= ruleDoStatement ) )
                    // InternalKactors.g:1301:4: (lv_do_5_0= ruleDoStatement )
                    {
                    // InternalKactors.g:1301:4: (lv_do_5_0= ruleDoStatement )
                    // InternalKactors.g:1302:5: lv_do_5_0= ruleDoStatement
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
                    // InternalKactors.g:1320:3: ( (lv_for_6_0= ruleForStatement ) )
                    {
                    // InternalKactors.g:1320:3: ( (lv_for_6_0= ruleForStatement ) )
                    // InternalKactors.g:1321:4: (lv_for_6_0= ruleForStatement )
                    {
                    // InternalKactors.g:1321:4: (lv_for_6_0= ruleForStatement )
                    // InternalKactors.g:1322:5: lv_for_6_0= ruleForStatement
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
                    // InternalKactors.g:1340:3: ( (lv_value_7_0= ruleValue ) )
                    {
                    // InternalKactors.g:1340:3: ( (lv_value_7_0= ruleValue ) )
                    // InternalKactors.g:1341:4: (lv_value_7_0= ruleValue )
                    {
                    // InternalKactors.g:1341:4: (lv_value_7_0= ruleValue )
                    // InternalKactors.g:1342:5: lv_value_7_0= ruleValue
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
    // InternalKactors.g:1363:1: entryRuleNextStatement returns [EObject current=null] : iv_ruleNextStatement= ruleNextStatement EOF ;
    public final EObject entryRuleNextStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNextStatement = null;


        try {
            // InternalKactors.g:1363:54: (iv_ruleNextStatement= ruleNextStatement EOF )
            // InternalKactors.g:1364:2: iv_ruleNextStatement= ruleNextStatement EOF
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
    // InternalKactors.g:1370:1: ruleNextStatement returns [EObject current=null] : (otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) ) ;
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
            // InternalKactors.g:1376:2: ( (otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) ) )
            // InternalKactors.g:1377:2: (otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) )
            {
            // InternalKactors.g:1377:2: (otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) )
            // InternalKactors.g:1378:3: otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )
            {
            otherlv_0=(Token)match(input,27,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getNextStatementAccess().getCommaKeyword_0());
              		
            }
            // InternalKactors.g:1382:3: ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )
            int alt24=8;
            alt24 = dfa24.predict(input);
            switch (alt24) {
                case 1 :
                    // InternalKactors.g:1383:4: ( (lv_verb_1_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1383:4: ( (lv_verb_1_0= ruleMessageCall ) )
                    // InternalKactors.g:1384:5: (lv_verb_1_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1384:5: (lv_verb_1_0= ruleMessageCall )
                    // InternalKactors.g:1385:6: lv_verb_1_0= ruleMessageCall
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
                    // InternalKactors.g:1403:4: ( (lv_group_2_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1403:4: ( (lv_group_2_0= ruleStatementGroup ) )
                    // InternalKactors.g:1404:5: (lv_group_2_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1404:5: (lv_group_2_0= ruleStatementGroup )
                    // InternalKactors.g:1405:6: lv_group_2_0= ruleStatementGroup
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
                    // InternalKactors.g:1423:4: ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:1423:4: ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:1424:5: (lv_text_3_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:1424:5: (lv_text_3_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:1425:6: lv_text_3_0= RULE_EMBEDDEDTEXT
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
                    // InternalKactors.g:1442:4: ( (lv_if_4_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:1442:4: ( (lv_if_4_0= ruleIfStatement ) )
                    // InternalKactors.g:1443:5: (lv_if_4_0= ruleIfStatement )
                    {
                    // InternalKactors.g:1443:5: (lv_if_4_0= ruleIfStatement )
                    // InternalKactors.g:1444:6: lv_if_4_0= ruleIfStatement
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
                    // InternalKactors.g:1462:4: ( (lv_while_5_0= ruleWhileStatement ) )
                    {
                    // InternalKactors.g:1462:4: ( (lv_while_5_0= ruleWhileStatement ) )
                    // InternalKactors.g:1463:5: (lv_while_5_0= ruleWhileStatement )
                    {
                    // InternalKactors.g:1463:5: (lv_while_5_0= ruleWhileStatement )
                    // InternalKactors.g:1464:6: lv_while_5_0= ruleWhileStatement
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
                    // InternalKactors.g:1482:4: ( (lv_do_6_0= ruleDoStatement ) )
                    {
                    // InternalKactors.g:1482:4: ( (lv_do_6_0= ruleDoStatement ) )
                    // InternalKactors.g:1483:5: (lv_do_6_0= ruleDoStatement )
                    {
                    // InternalKactors.g:1483:5: (lv_do_6_0= ruleDoStatement )
                    // InternalKactors.g:1484:6: lv_do_6_0= ruleDoStatement
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
                    // InternalKactors.g:1502:4: ( (lv_for_7_0= ruleForStatement ) )
                    {
                    // InternalKactors.g:1502:4: ( (lv_for_7_0= ruleForStatement ) )
                    // InternalKactors.g:1503:5: (lv_for_7_0= ruleForStatement )
                    {
                    // InternalKactors.g:1503:5: (lv_for_7_0= ruleForStatement )
                    // InternalKactors.g:1504:6: lv_for_7_0= ruleForStatement
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
                    // InternalKactors.g:1522:4: ( (lv_value_8_0= ruleValue ) )
                    {
                    // InternalKactors.g:1522:4: ( (lv_value_8_0= ruleValue ) )
                    // InternalKactors.g:1523:5: (lv_value_8_0= ruleValue )
                    {
                    // InternalKactors.g:1523:5: (lv_value_8_0= ruleValue )
                    // InternalKactors.g:1524:6: lv_value_8_0= ruleValue
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
    // InternalKactors.g:1546:1: entryRuleIfStatement returns [EObject current=null] : iv_ruleIfStatement= ruleIfStatement EOF ;
    public final EObject entryRuleIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfStatement = null;


        try {
            // InternalKactors.g:1546:52: (iv_ruleIfStatement= ruleIfStatement EOF )
            // InternalKactors.g:1547:2: iv_ruleIfStatement= ruleIfStatement EOF
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
    // InternalKactors.g:1553:1: ruleIfStatement returns [EObject current=null] : (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? ) ;
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
            // InternalKactors.g:1559:2: ( (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? ) )
            // InternalKactors.g:1560:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? )
            {
            // InternalKactors.g:1560:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? )
            // InternalKactors.g:1561:3: otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )?
            {
            otherlv_0=(Token)match(input,42,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getIfStatementAccess().getIfKeyword_0());
              		
            }
            // InternalKactors.g:1565:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:1566:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:1566:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:1567:5: lv_expression_1_0= RULE_EXPR
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

            // InternalKactors.g:1583:3: ( (lv_body_2_0= ruleStatementBody ) )
            // InternalKactors.g:1584:4: (lv_body_2_0= ruleStatementBody )
            {
            // InternalKactors.g:1584:4: (lv_body_2_0= ruleStatementBody )
            // InternalKactors.g:1585:5: lv_body_2_0= ruleStatementBody
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

            // InternalKactors.g:1602:3: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==43) ) {
                    int LA25_1 = input.LA(2);

                    if ( (synpred50_InternalKactors()) ) {
                        alt25=1;
                    }


                }


                switch (alt25) {
            	case 1 :
            	    // InternalKactors.g:1603:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) )
            	    {
            	    otherlv_3=(Token)match(input,43,FOLLOW_27); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getIfStatementAccess().getElseKeyword_3_0());
            	      			
            	    }
            	    otherlv_4=(Token)match(input,42,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getIfStatementAccess().getIfKeyword_3_1());
            	      			
            	    }
            	    // InternalKactors.g:1611:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
            	    // InternalKactors.g:1612:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    {
            	    // InternalKactors.g:1612:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    // InternalKactors.g:1613:6: lv_elseIfExpression_5_0= RULE_EXPR
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

            	    // InternalKactors.g:1629:4: ( (lv_elseIfBody_6_0= ruleStatementBody ) )
            	    // InternalKactors.g:1630:5: (lv_elseIfBody_6_0= ruleStatementBody )
            	    {
            	    // InternalKactors.g:1630:5: (lv_elseIfBody_6_0= ruleStatementBody )
            	    // InternalKactors.g:1631:6: lv_elseIfBody_6_0= ruleStatementBody
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
            	    break loop25;
                }
            } while (true);

            // InternalKactors.g:1649:3: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==43) ) {
                int LA26_1 = input.LA(2);

                if ( (synpred51_InternalKactors()) ) {
                    alt26=1;
                }
            }
            switch (alt26) {
                case 1 :
                    // InternalKactors.g:1650:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) )
                    {
                    otherlv_7=(Token)match(input,43,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getIfStatementAccess().getElseKeyword_4_0());
                      			
                    }
                    // InternalKactors.g:1654:4: ( (lv_elseCall_8_0= ruleStatementBody ) )
                    // InternalKactors.g:1655:5: (lv_elseCall_8_0= ruleStatementBody )
                    {
                    // InternalKactors.g:1655:5: (lv_elseCall_8_0= ruleStatementBody )
                    // InternalKactors.g:1656:6: lv_elseCall_8_0= ruleStatementBody
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
    // InternalKactors.g:1678:1: entryRuleStatementBody returns [EObject current=null] : iv_ruleStatementBody= ruleStatementBody EOF ;
    public final EObject entryRuleStatementBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementBody = null;


        try {
            // InternalKactors.g:1678:54: (iv_ruleStatementBody= ruleStatementBody EOF )
            // InternalKactors.g:1679:2: iv_ruleStatementBody= ruleStatementBody EOF
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
    // InternalKactors.g:1685:1: ruleStatementBody returns [EObject current=null] : ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) ) ;
    public final EObject ruleStatementBody() throws RecognitionException {
        EObject current = null;

        EObject lv_verb_0_0 = null;

        EObject lv_value_1_0 = null;

        EObject lv_group_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1691:2: ( ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) ) )
            // InternalKactors.g:1692:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )
            {
            // InternalKactors.g:1692:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )
            int alt27=3;
            alt27 = dfa27.predict(input);
            switch (alt27) {
                case 1 :
                    // InternalKactors.g:1693:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1693:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    // InternalKactors.g:1694:4: (lv_verb_0_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1694:4: (lv_verb_0_0= ruleMessageCall )
                    // InternalKactors.g:1695:5: lv_verb_0_0= ruleMessageCall
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
                    // InternalKactors.g:1713:3: ( (lv_value_1_0= ruleValue ) )
                    {
                    // InternalKactors.g:1713:3: ( (lv_value_1_0= ruleValue ) )
                    // InternalKactors.g:1714:4: (lv_value_1_0= ruleValue )
                    {
                    // InternalKactors.g:1714:4: (lv_value_1_0= ruleValue )
                    // InternalKactors.g:1715:5: lv_value_1_0= ruleValue
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
                    // InternalKactors.g:1733:3: ( (lv_group_2_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1733:3: ( (lv_group_2_0= ruleStatementGroup ) )
                    // InternalKactors.g:1734:4: (lv_group_2_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1734:4: (lv_group_2_0= ruleStatementGroup )
                    // InternalKactors.g:1735:5: lv_group_2_0= ruleStatementGroup
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
    // InternalKactors.g:1756:1: entryRuleWhileStatement returns [EObject current=null] : iv_ruleWhileStatement= ruleWhileStatement EOF ;
    public final EObject entryRuleWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhileStatement = null;


        try {
            // InternalKactors.g:1756:55: (iv_ruleWhileStatement= ruleWhileStatement EOF )
            // InternalKactors.g:1757:2: iv_ruleWhileStatement= ruleWhileStatement EOF
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
    // InternalKactors.g:1763:1: ruleWhileStatement returns [EObject current=null] : (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) ) ;
    public final EObject ruleWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_expression_1_0=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1769:2: ( (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) ) )
            // InternalKactors.g:1770:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) )
            {
            // InternalKactors.g:1770:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) )
            // InternalKactors.g:1771:3: otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) )
            {
            otherlv_0=(Token)match(input,44,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getWhileStatementAccess().getWhileKeyword_0());
              		
            }
            // InternalKactors.g:1775:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:1776:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:1776:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:1777:5: lv_expression_1_0= RULE_EXPR
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

            // InternalKactors.g:1793:3: ( (lv_body_2_0= ruleStatementBody ) )
            // InternalKactors.g:1794:4: (lv_body_2_0= ruleStatementBody )
            {
            // InternalKactors.g:1794:4: (lv_body_2_0= ruleStatementBody )
            // InternalKactors.g:1795:5: lv_body_2_0= ruleStatementBody
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
    // InternalKactors.g:1816:1: entryRuleDoStatement returns [EObject current=null] : iv_ruleDoStatement= ruleDoStatement EOF ;
    public final EObject entryRuleDoStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDoStatement = null;


        try {
            // InternalKactors.g:1816:52: (iv_ruleDoStatement= ruleDoStatement EOF )
            // InternalKactors.g:1817:2: iv_ruleDoStatement= ruleDoStatement EOF
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
    // InternalKactors.g:1823:1: ruleDoStatement returns [EObject current=null] : (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) ;
    public final EObject ruleDoStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_expression_3_0=null;
        EObject lv_body_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1829:2: ( (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) )
            // InternalKactors.g:1830:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            {
            // InternalKactors.g:1830:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            // InternalKactors.g:1831:3: otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) )
            {
            otherlv_0=(Token)match(input,45,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getDoStatementAccess().getDoKeyword_0());
              		
            }
            // InternalKactors.g:1835:3: ( (lv_body_1_0= ruleStatementBody ) )
            // InternalKactors.g:1836:4: (lv_body_1_0= ruleStatementBody )
            {
            // InternalKactors.g:1836:4: (lv_body_1_0= ruleStatementBody )
            // InternalKactors.g:1837:5: lv_body_1_0= ruleStatementBody
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

            otherlv_2=(Token)match(input,44,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getDoStatementAccess().getWhileKeyword_2());
              		
            }
            // InternalKactors.g:1858:3: ( (lv_expression_3_0= RULE_EXPR ) )
            // InternalKactors.g:1859:4: (lv_expression_3_0= RULE_EXPR )
            {
            // InternalKactors.g:1859:4: (lv_expression_3_0= RULE_EXPR )
            // InternalKactors.g:1860:5: lv_expression_3_0= RULE_EXPR
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
    // InternalKactors.g:1880:1: entryRuleForStatement returns [EObject current=null] : iv_ruleForStatement= ruleForStatement EOF ;
    public final EObject entryRuleForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleForStatement = null;


        try {
            // InternalKactors.g:1880:53: (iv_ruleForStatement= ruleForStatement EOF )
            // InternalKactors.g:1881:2: iv_ruleForStatement= ruleForStatement EOF
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
    // InternalKactors.g:1887:1: ruleForStatement returns [EObject current=null] : (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) ) ;
    public final EObject ruleForStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_id_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;

        EObject lv_body_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1893:2: ( (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) ) )
            // InternalKactors.g:1894:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) )
            {
            // InternalKactors.g:1894:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) )
            // InternalKactors.g:1895:3: otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) )
            {
            otherlv_0=(Token)match(input,46,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getForStatementAccess().getForKeyword_0());
              		
            }
            // InternalKactors.g:1899:3: ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==RULE_LOWERCASE_ID) ) {
                int LA28_1 = input.LA(2);

                if ( (LA28_1==47) ) {
                    alt28=1;
                }
            }
            switch (alt28) {
                case 1 :
                    // InternalKactors.g:1900:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in'
                    {
                    // InternalKactors.g:1900:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:1901:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:1901:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:1902:6: lv_id_1_0= RULE_LOWERCASE_ID
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

                    otherlv_2=(Token)match(input,47,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getForStatementAccess().getInKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:1923:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:1924:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:1924:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:1925:5: lv_value_3_0= ruleValue
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

            // InternalKactors.g:1942:3: ( (lv_body_4_0= ruleStatementBody ) )
            // InternalKactors.g:1943:4: (lv_body_4_0= ruleStatementBody )
            {
            // InternalKactors.g:1943:4: (lv_body_4_0= ruleStatementBody )
            // InternalKactors.g:1944:5: lv_body_4_0= ruleStatementBody
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
    // InternalKactors.g:1965:1: entryRuleActions returns [EObject current=null] : iv_ruleActions= ruleActions EOF ;
    public final EObject entryRuleActions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActions = null;


        try {
            // InternalKactors.g:1965:48: (iv_ruleActions= ruleActions EOF )
            // InternalKactors.g:1966:2: iv_ruleActions= ruleActions EOF
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
    // InternalKactors.g:1972:1: ruleActions returns [EObject current=null] : ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) ) ;
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
            // InternalKactors.g:1978:2: ( ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) ) )
            // InternalKactors.g:1979:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )
            {
            // InternalKactors.g:1979:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )
            int alt30=4;
            alt30 = dfa30.predict(input);
            switch (alt30) {
                case 1 :
                    // InternalKactors.g:1980:3: ( (lv_match_0_0= ruleMatch ) )
                    {
                    // InternalKactors.g:1980:3: ( (lv_match_0_0= ruleMatch ) )
                    // InternalKactors.g:1981:4: (lv_match_0_0= ruleMatch )
                    {
                    // InternalKactors.g:1981:4: (lv_match_0_0= ruleMatch )
                    // InternalKactors.g:1982:5: lv_match_0_0= ruleMatch
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
                    // InternalKactors.g:2000:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
                    {
                    // InternalKactors.g:2000:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
                    // InternalKactors.g:2001:4: otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')'
                    {
                    otherlv_1=(Token)match(input,39,FOLLOW_30); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:2005:4: ( (lv_matches_2_0= ruleMatch ) )
                    // InternalKactors.g:2006:5: (lv_matches_2_0= ruleMatch )
                    {
                    // InternalKactors.g:2006:5: (lv_matches_2_0= ruleMatch )
                    // InternalKactors.g:2007:6: lv_matches_2_0= ruleMatch
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

                    // InternalKactors.g:2024:4: ( (lv_matches_3_0= ruleMatch ) )*
                    loop29:
                    do {
                        int alt29=2;
                        int LA29_0 = input.LA(1);

                        if ( ((LA29_0>=RULE_OBSERVABLE && LA29_0<=RULE_LOWERCASE_ID)||LA29_0==RULE_STRING||(LA29_0>=RULE_EXPR && LA29_0<=RULE_INT)||LA29_0==39||LA29_0==47||(LA29_0>=49 && LA29_0<=50)||(LA29_0>=54 && LA29_0<=56)||(LA29_0>=74 && LA29_0<=75)) ) {
                            alt29=1;
                        }


                        switch (alt29) {
                    	case 1 :
                    	    // InternalKactors.g:2025:5: (lv_matches_3_0= ruleMatch )
                    	    {
                    	    // InternalKactors.g:2025:5: (lv_matches_3_0= ruleMatch )
                    	    // InternalKactors.g:2026:6: lv_matches_3_0= ruleMatch
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
                    	    break loop29;
                        }
                    } while (true);

                    otherlv_4=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getActionsAccess().getRightParenthesisKeyword_1_3());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:2049:3: ( (lv_statement_5_0= ruleStatement ) )
                    {
                    // InternalKactors.g:2049:3: ( (lv_statement_5_0= ruleStatement ) )
                    // InternalKactors.g:2050:4: (lv_statement_5_0= ruleStatement )
                    {
                    // InternalKactors.g:2050:4: (lv_statement_5_0= ruleStatement )
                    // InternalKactors.g:2051:5: lv_statement_5_0= ruleStatement
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
                    // InternalKactors.g:2069:3: (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' )
                    {
                    // InternalKactors.g:2069:3: (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' )
                    // InternalKactors.g:2070:4: otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')'
                    {
                    otherlv_6=(Token)match(input,39,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:2074:4: ( (lv_statements_7_0= ruleStatementList ) )
                    // InternalKactors.g:2075:5: (lv_statements_7_0= ruleStatementList )
                    {
                    // InternalKactors.g:2075:5: (lv_statements_7_0= ruleStatementList )
                    // InternalKactors.g:2076:6: lv_statements_7_0= ruleStatementList
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

                    otherlv_8=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:2102:1: entryRuleMatch returns [EObject current=null] : iv_ruleMatch= ruleMatch EOF ;
    public final EObject entryRuleMatch() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMatch = null;


        try {
            // InternalKactors.g:2102:46: (iv_ruleMatch= ruleMatch EOF )
            // InternalKactors.g:2103:2: iv_ruleMatch= ruleMatch EOF
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
    // InternalKactors.g:2109:1: ruleMatch returns [EObject current=null] : ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) ) ;
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
            // InternalKactors.g:2115:2: ( ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) ) )
            // InternalKactors.g:2116:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) )
            {
            // InternalKactors.g:2116:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) )
            int alt34=16;
            alt34 = dfa34.predict(input);
            switch (alt34) {
                case 1 :
                    // InternalKactors.g:2117:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2117:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) )
                    // InternalKactors.g:2118:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2118:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:2119:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:2119:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:2120:6: lv_id_0_0= RULE_LOWERCASE_ID
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

                    otherlv_1=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_0_1());
                      			
                    }
                    // InternalKactors.g:2140:4: ( (lv_body_2_0= ruleStatementList ) )
                    // InternalKactors.g:2141:5: (lv_body_2_0= ruleStatementList )
                    {
                    // InternalKactors.g:2141:5: (lv_body_2_0= ruleStatementList )
                    // InternalKactors.g:2142:6: lv_body_2_0= ruleStatementList
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
                    // InternalKactors.g:2161:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2161:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
                    // InternalKactors.g:2162:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2162:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) )
                    // InternalKactors.g:2163:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
                    {
                    // InternalKactors.g:2163:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
                    // InternalKactors.g:2164:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
                    {
                    // InternalKactors.g:2164:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==49) ) {
                        alt31=1;
                    }
                    else if ( (LA31_0==50) ) {
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
                            // InternalKactors.g:2165:7: lv_boolean_3_1= 'true'
                            {
                            lv_boolean_3_1=(Token)match(input,49,FOLLOW_32); if (state.failed) return current;
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
                            // InternalKactors.g:2176:7: lv_boolean_3_2= 'false'
                            {
                            lv_boolean_3_2=(Token)match(input,50,FOLLOW_32); if (state.failed) return current;
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

                    otherlv_4=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:2193:4: ( (lv_body_5_0= ruleStatementList ) )
                    // InternalKactors.g:2194:5: (lv_body_5_0= ruleStatementList )
                    {
                    // InternalKactors.g:2194:5: (lv_body_5_0= ruleStatementList )
                    // InternalKactors.g:2195:6: lv_body_5_0= ruleStatementList
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
                    // InternalKactors.g:2214:3: ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2214:3: ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) )
                    // InternalKactors.g:2215:4: ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2215:4: ( (lv_type_6_0= RULE_CAMELCASE_ID ) )
                    // InternalKactors.g:2216:5: (lv_type_6_0= RULE_CAMELCASE_ID )
                    {
                    // InternalKactors.g:2216:5: (lv_type_6_0= RULE_CAMELCASE_ID )
                    // InternalKactors.g:2217:6: lv_type_6_0= RULE_CAMELCASE_ID
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

                    otherlv_7=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_2_1());
                      			
                    }
                    // InternalKactors.g:2237:4: ( (lv_body_8_0= ruleStatementList ) )
                    // InternalKactors.g:2238:5: (lv_body_8_0= ruleStatementList )
                    {
                    // InternalKactors.g:2238:5: (lv_body_8_0= ruleStatementList )
                    // InternalKactors.g:2239:6: lv_body_8_0= ruleStatementList
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
                    // InternalKactors.g:2258:3: ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2258:3: ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) )
                    // InternalKactors.g:2259:4: ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2259:4: ( (lv_regexp_9_0= RULE_REGEXP ) )
                    // InternalKactors.g:2260:5: (lv_regexp_9_0= RULE_REGEXP )
                    {
                    // InternalKactors.g:2260:5: (lv_regexp_9_0= RULE_REGEXP )
                    // InternalKactors.g:2261:6: lv_regexp_9_0= RULE_REGEXP
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

                    otherlv_10=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_3_1());
                      			
                    }
                    // InternalKactors.g:2281:4: ( (lv_body_11_0= ruleStatementList ) )
                    // InternalKactors.g:2282:5: (lv_body_11_0= ruleStatementList )
                    {
                    // InternalKactors.g:2282:5: (lv_body_11_0= ruleStatementList )
                    // InternalKactors.g:2283:6: lv_body_11_0= ruleStatementList
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
                    // InternalKactors.g:2302:3: ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2302:3: ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
                    // InternalKactors.g:2303:4: ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2303:4: ( (lv_observable_12_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:2304:5: (lv_observable_12_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:2304:5: (lv_observable_12_0= RULE_OBSERVABLE )
                    // InternalKactors.g:2305:6: lv_observable_12_0= RULE_OBSERVABLE
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

                    otherlv_13=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_4_1());
                      			
                    }
                    // InternalKactors.g:2325:4: ( (lv_body_14_0= ruleStatementList ) )
                    // InternalKactors.g:2326:5: (lv_body_14_0= ruleStatementList )
                    {
                    // InternalKactors.g:2326:5: (lv_body_14_0= ruleStatementList )
                    // InternalKactors.g:2327:6: lv_body_14_0= ruleStatementList
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
                    // InternalKactors.g:2346:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2346:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
                    // InternalKactors.g:2347:4: ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2347:4: ( (lv_literal_15_0= ruleLiteral ) )
                    // InternalKactors.g:2348:5: (lv_literal_15_0= ruleLiteral )
                    {
                    // InternalKactors.g:2348:5: (lv_literal_15_0= ruleLiteral )
                    // InternalKactors.g:2349:6: lv_literal_15_0= ruleLiteral
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

                    otherlv_16=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_5_1());
                      			
                    }
                    // InternalKactors.g:2370:4: ( (lv_body_17_0= ruleStatementList ) )
                    // InternalKactors.g:2371:5: (lv_body_17_0= ruleStatementList )
                    {
                    // InternalKactors.g:2371:5: (lv_body_17_0= ruleStatementList )
                    // InternalKactors.g:2372:6: lv_body_17_0= ruleStatementList
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
                    // InternalKactors.g:2391:3: ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2391:3: ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
                    // InternalKactors.g:2392:4: ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2392:4: ( (lv_text_18_0= RULE_STRING ) )
                    // InternalKactors.g:2393:5: (lv_text_18_0= RULE_STRING )
                    {
                    // InternalKactors.g:2393:5: (lv_text_18_0= RULE_STRING )
                    // InternalKactors.g:2394:6: lv_text_18_0= RULE_STRING
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

                    otherlv_19=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_19, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_6_1());
                      			
                    }
                    // InternalKactors.g:2414:4: ( (lv_body_20_0= ruleStatementList ) )
                    // InternalKactors.g:2415:5: (lv_body_20_0= ruleStatementList )
                    {
                    // InternalKactors.g:2415:5: (lv_body_20_0= ruleStatementList )
                    // InternalKactors.g:2416:6: lv_body_20_0= ruleStatementList
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
                    // InternalKactors.g:2435:3: ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2435:3: ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) )
                    // InternalKactors.g:2436:4: ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2436:4: ( (lv_arguments_21_0= ruleArgumentDeclaration ) )
                    // InternalKactors.g:2437:5: (lv_arguments_21_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:2437:5: (lv_arguments_21_0= ruleArgumentDeclaration )
                    // InternalKactors.g:2438:6: lv_arguments_21_0= ruleArgumentDeclaration
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

                    otherlv_22=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_22, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_7_1());
                      			
                    }
                    // InternalKactors.g:2459:4: ( (lv_body_23_0= ruleStatementList ) )
                    // InternalKactors.g:2460:5: (lv_body_23_0= ruleStatementList )
                    {
                    // InternalKactors.g:2460:5: (lv_body_23_0= ruleStatementList )
                    // InternalKactors.g:2461:6: lv_body_23_0= ruleStatementList
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
                    // InternalKactors.g:2480:3: ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2480:3: ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) )
                    // InternalKactors.g:2481:4: ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2481:4: ( (lv_int0_24_0= ruleNumber ) )
                    // InternalKactors.g:2482:5: (lv_int0_24_0= ruleNumber )
                    {
                    // InternalKactors.g:2482:5: (lv_int0_24_0= ruleNumber )
                    // InternalKactors.g:2483:6: lv_int0_24_0= ruleNumber
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

                    // InternalKactors.g:2500:4: ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )?
                    int alt32=3;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==51) ) {
                        alt32=1;
                    }
                    else if ( (LA32_0==52) ) {
                        alt32=2;
                    }
                    switch (alt32) {
                        case 1 :
                            // InternalKactors.g:2501:5: ( (lv_leftLimit_25_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:2501:5: ( (lv_leftLimit_25_0= 'inclusive' ) )
                            // InternalKactors.g:2502:6: (lv_leftLimit_25_0= 'inclusive' )
                            {
                            // InternalKactors.g:2502:6: (lv_leftLimit_25_0= 'inclusive' )
                            // InternalKactors.g:2503:7: lv_leftLimit_25_0= 'inclusive'
                            {
                            lv_leftLimit_25_0=(Token)match(input,51,FOLLOW_34); if (state.failed) return current;
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
                            // InternalKactors.g:2516:5: otherlv_26= 'exclusive'
                            {
                            otherlv_26=(Token)match(input,52,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_26, grammarAccess.getMatchAccess().getExclusiveKeyword_8_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:2521:4: ( ( 'to' )=>otherlv_27= 'to' )
                    // InternalKactors.g:2522:5: ( 'to' )=>otherlv_27= 'to'
                    {
                    otherlv_27=(Token)match(input,53,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_27, grammarAccess.getMatchAccess().getToKeyword_8_2());
                      				
                    }

                    }

                    // InternalKactors.g:2528:4: ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) )
                    // InternalKactors.g:2529:5: ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber )
                    {
                    // InternalKactors.g:2533:5: (lv_int1_28_0= ruleNumber )
                    // InternalKactors.g:2534:6: lv_int1_28_0= ruleNumber
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

                    // InternalKactors.g:2551:4: ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )?
                    int alt33=3;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==51) ) {
                        alt33=1;
                    }
                    else if ( (LA33_0==52) ) {
                        alt33=2;
                    }
                    switch (alt33) {
                        case 1 :
                            // InternalKactors.g:2552:5: ( (lv_rightLimit_29_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:2552:5: ( (lv_rightLimit_29_0= 'inclusive' ) )
                            // InternalKactors.g:2553:6: (lv_rightLimit_29_0= 'inclusive' )
                            {
                            // InternalKactors.g:2553:6: (lv_rightLimit_29_0= 'inclusive' )
                            // InternalKactors.g:2554:7: lv_rightLimit_29_0= 'inclusive'
                            {
                            lv_rightLimit_29_0=(Token)match(input,51,FOLLOW_32); if (state.failed) return current;
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
                            // InternalKactors.g:2567:5: otherlv_30= 'exclusive'
                            {
                            otherlv_30=(Token)match(input,52,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_30, grammarAccess.getMatchAccess().getExclusiveKeyword_8_4_1());
                              				
                            }

                            }
                            break;

                    }

                    otherlv_31=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_31, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_8_5());
                      			
                    }
                    // InternalKactors.g:2576:4: ( (lv_body_32_0= ruleStatementList ) )
                    // InternalKactors.g:2577:5: (lv_body_32_0= ruleStatementList )
                    {
                    // InternalKactors.g:2577:5: (lv_body_32_0= ruleStatementList )
                    // InternalKactors.g:2578:6: lv_body_32_0= ruleStatementList
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
                    // InternalKactors.g:2597:3: (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2597:3: (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
                    // InternalKactors.g:2598:4: otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) )
                    {
                    otherlv_33=(Token)match(input,47,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_33, grammarAccess.getMatchAccess().getInKeyword_9_0());
                      			
                    }
                    // InternalKactors.g:2602:4: ( (lv_set_34_0= ruleList ) )
                    // InternalKactors.g:2603:5: (lv_set_34_0= ruleList )
                    {
                    // InternalKactors.g:2603:5: (lv_set_34_0= ruleList )
                    // InternalKactors.g:2604:6: lv_set_34_0= ruleList
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

                    otherlv_35=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_35, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_9_2());
                      			
                    }
                    // InternalKactors.g:2625:4: ( (lv_body_36_0= ruleStatementList ) )
                    // InternalKactors.g:2626:5: (lv_body_36_0= ruleStatementList )
                    {
                    // InternalKactors.g:2626:5: (lv_body_36_0= ruleStatementList )
                    // InternalKactors.g:2627:6: lv_body_36_0= ruleStatementList
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
                    // InternalKactors.g:2646:3: ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2646:3: ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
                    // InternalKactors.g:2647:4: ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2647:4: ( (lv_quantity_37_0= ruleQuantity ) )
                    // InternalKactors.g:2648:5: (lv_quantity_37_0= ruleQuantity )
                    {
                    // InternalKactors.g:2648:5: (lv_quantity_37_0= ruleQuantity )
                    // InternalKactors.g:2649:6: lv_quantity_37_0= ruleQuantity
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

                    otherlv_38=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_38, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_10_1());
                      			
                    }
                    // InternalKactors.g:2670:4: ( (lv_body_39_0= ruleStatementList ) )
                    // InternalKactors.g:2671:5: (lv_body_39_0= ruleStatementList )
                    {
                    // InternalKactors.g:2671:5: (lv_body_39_0= ruleStatementList )
                    // InternalKactors.g:2672:6: lv_body_39_0= ruleStatementList
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
                    // InternalKactors.g:2691:3: ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2691:3: ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
                    // InternalKactors.g:2692:4: ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2692:4: ( (lv_date_40_0= ruleDate ) )
                    // InternalKactors.g:2693:5: (lv_date_40_0= ruleDate )
                    {
                    // InternalKactors.g:2693:5: (lv_date_40_0= ruleDate )
                    // InternalKactors.g:2694:6: lv_date_40_0= ruleDate
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

                    otherlv_41=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_41, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_11_1());
                      			
                    }
                    // InternalKactors.g:2715:4: ( (lv_body_42_0= ruleStatementList ) )
                    // InternalKactors.g:2716:5: (lv_body_42_0= ruleStatementList )
                    {
                    // InternalKactors.g:2716:5: (lv_body_42_0= ruleStatementList )
                    // InternalKactors.g:2717:6: lv_body_42_0= ruleStatementList
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
                    // InternalKactors.g:2736:3: ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2736:3: ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) )
                    // InternalKactors.g:2737:4: ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2737:4: ( (lv_expr_43_0= RULE_EXPR ) )
                    // InternalKactors.g:2738:5: (lv_expr_43_0= RULE_EXPR )
                    {
                    // InternalKactors.g:2738:5: (lv_expr_43_0= RULE_EXPR )
                    // InternalKactors.g:2739:6: lv_expr_43_0= RULE_EXPR
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

                    otherlv_44=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_44, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_12_1());
                      			
                    }
                    // InternalKactors.g:2759:4: ( (lv_body_45_0= ruleStatementList ) )
                    // InternalKactors.g:2760:5: (lv_body_45_0= ruleStatementList )
                    {
                    // InternalKactors.g:2760:5: (lv_body_45_0= ruleStatementList )
                    // InternalKactors.g:2761:6: lv_body_45_0= ruleStatementList
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
                    // InternalKactors.g:2780:3: ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2780:3: ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) )
                    // InternalKactors.g:2781:4: ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2781:4: ( (lv_nodata_46_0= 'unknown' ) )
                    // InternalKactors.g:2782:5: (lv_nodata_46_0= 'unknown' )
                    {
                    // InternalKactors.g:2782:5: (lv_nodata_46_0= 'unknown' )
                    // InternalKactors.g:2783:6: lv_nodata_46_0= 'unknown'
                    {
                    lv_nodata_46_0=(Token)match(input,54,FOLLOW_32); if (state.failed) return current;
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

                    otherlv_47=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_47, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_13_1());
                      			
                    }
                    // InternalKactors.g:2799:4: ( (lv_body_48_0= ruleStatementList ) )
                    // InternalKactors.g:2800:5: (lv_body_48_0= ruleStatementList )
                    {
                    // InternalKactors.g:2800:5: (lv_body_48_0= ruleStatementList )
                    // InternalKactors.g:2801:6: lv_body_48_0= ruleStatementList
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
                    // InternalKactors.g:2820:3: ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2820:3: ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) )
                    // InternalKactors.g:2821:4: ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2821:4: ( (lv_star_49_0= '*' ) )
                    // InternalKactors.g:2822:5: (lv_star_49_0= '*' )
                    {
                    // InternalKactors.g:2822:5: (lv_star_49_0= '*' )
                    // InternalKactors.g:2823:6: lv_star_49_0= '*'
                    {
                    lv_star_49_0=(Token)match(input,55,FOLLOW_32); if (state.failed) return current;
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

                    otherlv_50=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_50, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_14_1());
                      			
                    }
                    // InternalKactors.g:2839:4: ( (lv_body_51_0= ruleStatementList ) )
                    // InternalKactors.g:2840:5: (lv_body_51_0= ruleStatementList )
                    {
                    // InternalKactors.g:2840:5: (lv_body_51_0= ruleStatementList )
                    // InternalKactors.g:2841:6: lv_body_51_0= ruleStatementList
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
                    // InternalKactors.g:2860:3: ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2860:3: ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) )
                    // InternalKactors.g:2861:4: ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2861:4: ( (lv_anything_52_0= '#' ) )
                    // InternalKactors.g:2862:5: (lv_anything_52_0= '#' )
                    {
                    // InternalKactors.g:2862:5: (lv_anything_52_0= '#' )
                    // InternalKactors.g:2863:6: lv_anything_52_0= '#'
                    {
                    lv_anything_52_0=(Token)match(input,56,FOLLOW_32); if (state.failed) return current;
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

                    otherlv_53=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_53, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_15_1());
                      			
                    }
                    // InternalKactors.g:2879:4: ( (lv_body_54_0= ruleStatementList ) )
                    // InternalKactors.g:2880:5: (lv_body_54_0= ruleStatementList )
                    {
                    // InternalKactors.g:2880:5: (lv_body_54_0= ruleStatementList )
                    // InternalKactors.g:2881:6: lv_body_54_0= ruleStatementList
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
    // InternalKactors.g:2903:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKactors.g:2903:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKactors.g:2904:2: iv_ruleUrnId= ruleUrnId EOF
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
    // InternalKactors.g:2910:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) ;
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
            // InternalKactors.g:2916:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) )
            // InternalKactors.g:2917:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            {
            // InternalKactors.g:2917:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            // InternalKactors.g:2918:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            {
            // InternalKactors.g:2918:3: (kw= 'urn:klab:' )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==57) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalKactors.g:2919:4: kw= 'urn:klab:'
                    {
                    kw=(Token)match(input,57,FOLLOW_4); if (state.failed) return current;
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
            kw=(Token)match(input,38,FOLLOW_4); if (state.failed) return current;
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
            kw=(Token)match(input,38,FOLLOW_4); if (state.failed) return current;
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
            kw=(Token)match(input,38,FOLLOW_37); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            // InternalKactors.g:2970:3: (this_Path_7= rulePath | this_INT_8= RULE_INT )
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==RULE_LOWERCASE_ID||LA36_0==RULE_UPPERCASE_ID) ) {
                alt36=1;
            }
            else if ( (LA36_0==RULE_INT) ) {
                alt36=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }
            switch (alt36) {
                case 1 :
                    // InternalKactors.g:2971:4: this_Path_7= rulePath
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
                    // InternalKactors.g:2982:4: this_INT_8= RULE_INT
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

            // InternalKactors.g:2990:3: (kw= ':' this_VersionNumber_10= ruleVersionNumber )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==38) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalKactors.g:2991:4: kw= ':' this_VersionNumber_10= ruleVersionNumber
                    {
                    kw=(Token)match(input,38,FOLLOW_10); if (state.failed) return current;
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

            // InternalKactors.g:3007:3: (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==56) ) {
                int LA41_1 = input.LA(2);

                if ( (LA41_1==RULE_LOWERCASE_ID||LA41_1==RULE_UPPERCASE_ID) ) {
                    alt41=1;
                }
            }
            switch (alt41) {
                case 1 :
                    // InternalKactors.g:3008:4: kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    {
                    kw=(Token)match(input,56,FOLLOW_40); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getNumberSignKeyword_9_0());
                      			
                    }
                    // InternalKactors.g:3013:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )
                    int alt38=2;
                    alt38 = dfa38.predict(input);
                    switch (alt38) {
                        case 1 :
                            // InternalKactors.g:3014:5: this_Path_12= rulePath
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
                            // InternalKactors.g:3025:5: this_UrnKvp_13= ruleUrnKvp
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

                    // InternalKactors.g:3036:4: (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    loop40:
                    do {
                        int alt40=2;
                        int LA40_0 = input.LA(1);

                        if ( (LA40_0==58) ) {
                            alt40=1;
                        }


                        switch (alt40) {
                    	case 1 :
                    	    // InternalKactors.g:3037:5: kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    {
                    	    kw=(Token)match(input,58,FOLLOW_40); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(kw);
                    	      					newLeafNode(kw, grammarAccess.getUrnIdAccess().getAmpersandKeyword_9_2_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:3042:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    int alt39=2;
                    	    alt39 = dfa39.predict(input);
                    	    switch (alt39) {
                    	        case 1 :
                    	            // InternalKactors.g:3043:6: this_Path_15= rulePath
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
                    	            // InternalKactors.g:3054:6: this_UrnKvp_16= ruleUrnKvp
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
                    	    break loop40;
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
    // InternalKactors.g:3071:1: entryRuleUrnKvp returns [String current=null] : iv_ruleUrnKvp= ruleUrnKvp EOF ;
    public final String entryRuleUrnKvp() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnKvp = null;


        try {
            // InternalKactors.g:3071:46: (iv_ruleUrnKvp= ruleUrnKvp EOF )
            // InternalKactors.g:3072:2: iv_ruleUrnKvp= ruleUrnKvp EOF
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
    // InternalKactors.g:3078:1: ruleUrnKvp returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) ;
    public final AntlrDatatypeRuleToken ruleUrnKvp() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;

        AntlrDatatypeRuleToken this_Path_2 = null;



        	enterRule();

        try {
            // InternalKactors.g:3084:2: ( (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) )
            // InternalKactors.g:3085:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            {
            // InternalKactors.g:3085:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            // InternalKactors.g:3086:3: this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT )
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
            kw=(Token)match(input,59,FOLLOW_37); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnKvpAccess().getEqualsSignKeyword_1());
              		
            }
            // InternalKactors.g:3101:3: (this_Path_2= rulePath | this_INT_3= RULE_INT )
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==RULE_LOWERCASE_ID||LA42_0==RULE_UPPERCASE_ID) ) {
                alt42=1;
            }
            else if ( (LA42_0==RULE_INT) ) {
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
                    // InternalKactors.g:3102:4: this_Path_2= rulePath
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
                    // InternalKactors.g:3113:4: this_INT_3= RULE_INT
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
    // InternalKactors.g:3125:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKactors.g:3125:45: (iv_ruleList= ruleList EOF )
            // InternalKactors.g:3126:2: iv_ruleList= ruleList EOF
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
    // InternalKactors.g:3132:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3138:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKactors.g:3139:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKactors.g:3139:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKactors.g:3140:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKactors.g:3140:3: ()
            // InternalKactors.g:3141:4: 
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

            otherlv_1=(Token)match(input,39,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getListAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:3154:3: ( (lv_contents_2_0= ruleValue ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( ((LA43_0>=RULE_OBSERVABLE && LA43_0<=RULE_LOWERCASE_ID)||LA43_0==RULE_STRING||LA43_0==RULE_EXPR||LA43_0==RULE_INT||LA43_0==RULE_ARGVALUE||LA43_0==39||(LA43_0>=49 && LA43_0<=50)||LA43_0==57||LA43_0==60||LA43_0==62||(LA43_0>=74 && LA43_0<=75)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalKactors.g:3155:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKactors.g:3155:4: (lv_contents_2_0= ruleValue )
            	    // InternalKactors.g:3156:5: lv_contents_2_0= ruleValue
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
            	    break loop43;
                }
            } while (true);

            otherlv_3=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:3181:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // InternalKactors.g:3181:44: (iv_ruleMap= ruleMap EOF )
            // InternalKactors.g:3182:2: iv_ruleMap= ruleMap EOF
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
    // InternalKactors.g:3188:1: ruleMap returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_entries_2_0 = null;

        EObject lv_entries_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3194:2: ( ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) )
            // InternalKactors.g:3195:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            {
            // InternalKactors.g:3195:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            // InternalKactors.g:3196:3: () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}'
            {
            // InternalKactors.g:3196:3: ()
            // InternalKactors.g:3197:4: 
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

            otherlv_1=(Token)match(input,60,FOLLOW_43); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:3210:3: ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( ((LA45_0>=RULE_OBSERVABLE && LA45_0<=RULE_LOWERCASE_ID)||LA45_0==RULE_STRING||LA45_0==RULE_INT||LA45_0==47||(LA45_0>=49 && LA45_0<=50)||(LA45_0>=54 && LA45_0<=55)||LA45_0==59||(LA45_0>=69 && LA45_0<=75)) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // InternalKactors.g:3211:4: ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    {
                    // InternalKactors.g:3211:4: ( (lv_entries_2_0= ruleMapEntry ) )
                    // InternalKactors.g:3212:5: (lv_entries_2_0= ruleMapEntry )
                    {
                    // InternalKactors.g:3212:5: (lv_entries_2_0= ruleMapEntry )
                    // InternalKactors.g:3213:6: lv_entries_2_0= ruleMapEntry
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

                    // InternalKactors.g:3230:4: ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    loop44:
                    do {
                        int alt44=2;
                        int LA44_0 = input.LA(1);

                        if ( (LA44_0==27) ) {
                            alt44=1;
                        }


                        switch (alt44) {
                    	case 1 :
                    	    // InternalKactors.g:3231:5: ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) )
                    	    {
                    	    // InternalKactors.g:3231:5: ( ( ',' )=>otherlv_3= ',' )
                    	    // InternalKactors.g:3232:6: ( ',' )=>otherlv_3= ','
                    	    {
                    	    otherlv_3=(Token)match(input,27,FOLLOW_45); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_3, grammarAccess.getMapAccess().getCommaKeyword_2_1_0_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKactors.g:3239:5: ( (lv_entries_4_0= ruleMapEntry ) )
                    	    // InternalKactors.g:3240:6: (lv_entries_4_0= ruleMapEntry )
                    	    {
                    	    // InternalKactors.g:3240:6: (lv_entries_4_0= ruleMapEntry )
                    	    // InternalKactors.g:3241:7: lv_entries_4_0= ruleMapEntry
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
                    	    break loop44;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,61,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:3268:1: entryRuleMapEntry returns [EObject current=null] : iv_ruleMapEntry= ruleMapEntry EOF ;
    public final EObject entryRuleMapEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapEntry = null;


        try {
            // InternalKactors.g:3268:49: (iv_ruleMapEntry= ruleMapEntry EOF )
            // InternalKactors.g:3269:2: iv_ruleMapEntry= ruleMapEntry EOF
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
    // InternalKactors.g:3275:1: ruleMapEntry returns [EObject current=null] : ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleMapEntry() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifier_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3281:2: ( ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKactors.g:3282:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKactors.g:3282:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // InternalKactors.g:3283:3: ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalKactors.g:3283:3: ( (lv_classifier_0_0= ruleClassifier ) )
            // InternalKactors.g:3284:4: (lv_classifier_0_0= ruleClassifier )
            {
            // InternalKactors.g:3284:4: (lv_classifier_0_0= ruleClassifier )
            // InternalKactors.g:3285:5: lv_classifier_0_0= ruleClassifier
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

            otherlv_1=(Token)match(input,38,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapEntryAccess().getColonKeyword_1());
              		
            }
            // InternalKactors.g:3306:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKactors.g:3307:4: (lv_value_2_0= ruleValue )
            {
            // InternalKactors.g:3307:4: (lv_value_2_0= ruleValue )
            // InternalKactors.g:3308:5: lv_value_2_0= ruleValue
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
    // InternalKactors.g:3329:1: entryRuleClassifier returns [EObject current=null] : iv_ruleClassifier= ruleClassifier EOF ;
    public final EObject entryRuleClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifier = null;


        try {
            // InternalKactors.g:3329:51: (iv_ruleClassifier= ruleClassifier EOF )
            // InternalKactors.g:3330:2: iv_ruleClassifier= ruleClassifier EOF
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
    // InternalKactors.g:3336:1: ruleClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) ;
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
            // InternalKactors.g:3342:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) )
            // InternalKactors.g:3343:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            {
            // InternalKactors.g:3343:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            int alt49=10;
            alt49 = dfa49.predict(input);
            switch (alt49) {
                case 1 :
                    // InternalKactors.g:3344:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:3344:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt46=2;
                    int LA46_0 = input.LA(1);

                    if ( (LA46_0==49) ) {
                        alt46=1;
                    }
                    else if ( (LA46_0==50) ) {
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
                            // InternalKactors.g:3345:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:3345:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:3346:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:3346:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:3347:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,49,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:3360:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:3360:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:3361:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:3361:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:3362:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3376:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKactors.g:3376:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKactors.g:3377:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKactors.g:3377:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKactors.g:3378:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKactors.g:3378:5: (lv_int0_2_0= ruleNumber )
                    // InternalKactors.g:3379:6: lv_int0_2_0= ruleNumber
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

                    // InternalKactors.g:3396:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt47=3;
                    int LA47_0 = input.LA(1);

                    if ( (LA47_0==51) ) {
                        alt47=1;
                    }
                    else if ( (LA47_0==52) ) {
                        alt47=2;
                    }
                    switch (alt47) {
                        case 1 :
                            // InternalKactors.g:3397:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:3397:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKactors.g:3398:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKactors.g:3398:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKactors.g:3399:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,51,FOLLOW_34); if (state.failed) return current;
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
                            // InternalKactors.g:3412:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,52,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:3417:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKactors.g:3418:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,53,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKactors.g:3424:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKactors.g:3425:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKactors.g:3429:5: (lv_int1_6_0= ruleNumber )
                    // InternalKactors.g:3430:6: lv_int1_6_0= ruleNumber
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

                    // InternalKactors.g:3447:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt48=3;
                    int LA48_0 = input.LA(1);

                    if ( (LA48_0==51) ) {
                        alt48=1;
                    }
                    else if ( (LA48_0==52) ) {
                        alt48=2;
                    }
                    switch (alt48) {
                        case 1 :
                            // InternalKactors.g:3448:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:3448:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKactors.g:3449:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKactors.g:3449:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKactors.g:3450:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:3463:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,52,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3470:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3470:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKactors.g:3471:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKactors.g:3471:4: (lv_num_9_0= ruleNumber )
                    // InternalKactors.g:3472:5: lv_num_9_0= ruleNumber
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
                    // InternalKactors.g:3490:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKactors.g:3490:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKactors.g:3491:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,47,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:3495:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKactors.g:3496:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKactors.g:3496:5: (lv_set_11_0= ruleList )
                    // InternalKactors.g:3497:6: lv_set_11_0= ruleList
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
                    // InternalKactors.g:3516:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:3516:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKactors.g:3517:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKactors.g:3517:4: (lv_string_12_0= RULE_STRING )
                    // InternalKactors.g:3518:5: lv_string_12_0= RULE_STRING
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
                    // InternalKactors.g:3535:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:3535:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:3536:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:3536:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    // InternalKactors.g:3537:5: lv_observable_13_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:3554:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKactors.g:3554:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:3555:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:3555:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:3556:5: lv_id_14_0= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:3573:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:3573:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    // InternalKactors.g:3574:4: ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3574:4: ( (lv_op_15_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:3575:5: (lv_op_15_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:3575:5: (lv_op_15_0= ruleREL_OPERATOR )
                    // InternalKactors.g:3576:6: lv_op_15_0= ruleREL_OPERATOR
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

                    // InternalKactors.g:3593:4: ( (lv_expression_16_0= ruleNumber ) )
                    // InternalKactors.g:3594:5: (lv_expression_16_0= ruleNumber )
                    {
                    // InternalKactors.g:3594:5: (lv_expression_16_0= ruleNumber )
                    // InternalKactors.g:3595:6: lv_expression_16_0= ruleNumber
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
                    // InternalKactors.g:3614:3: ( (lv_nodata_17_0= 'unknown' ) )
                    {
                    // InternalKactors.g:3614:3: ( (lv_nodata_17_0= 'unknown' ) )
                    // InternalKactors.g:3615:4: (lv_nodata_17_0= 'unknown' )
                    {
                    // InternalKactors.g:3615:4: (lv_nodata_17_0= 'unknown' )
                    // InternalKactors.g:3616:5: lv_nodata_17_0= 'unknown'
                    {
                    lv_nodata_17_0=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3629:3: ( (lv_star_18_0= '*' ) )
                    {
                    // InternalKactors.g:3629:3: ( (lv_star_18_0= '*' ) )
                    // InternalKactors.g:3630:4: (lv_star_18_0= '*' )
                    {
                    // InternalKactors.g:3630:4: (lv_star_18_0= '*' )
                    // InternalKactors.g:3631:5: lv_star_18_0= '*'
                    {
                    lv_star_18_0=(Token)match(input,55,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:3647:1: entryRuleLookupTable returns [EObject current=null] : iv_ruleLookupTable= ruleLookupTable EOF ;
    public final EObject entryRuleLookupTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookupTable = null;


        try {
            // InternalKactors.g:3647:52: (iv_ruleLookupTable= ruleLookupTable EOF )
            // InternalKactors.g:3648:2: iv_ruleLookupTable= ruleLookupTable EOF
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
    // InternalKactors.g:3654:1: ruleLookupTable returns [EObject current=null] : ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) ;
    public final EObject ruleLookupTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_table_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3660:2: ( ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) )
            // InternalKactors.g:3661:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            {
            // InternalKactors.g:3661:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            // InternalKactors.g:3662:3: () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}'
            {
            // InternalKactors.g:3662:3: ()
            // InternalKactors.g:3663:4: 
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

            otherlv_1=(Token)match(input,62,FOLLOW_47); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLookupTableAccess().getLeftCurlyBracketLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:3676:3: ( (lv_table_2_0= ruleTable ) )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( ((LA50_0>=RULE_OBSERVABLE && LA50_0<=RULE_LOWERCASE_ID)||LA50_0==RULE_STRING||LA50_0==RULE_EXPR||LA50_0==RULE_INT||LA50_0==47||(LA50_0>=49 && LA50_0<=50)||(LA50_0>=54 && LA50_0<=56)||LA50_0==59||(LA50_0>=69 && LA50_0<=75)) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // InternalKactors.g:3677:4: (lv_table_2_0= ruleTable )
                    {
                    // InternalKactors.g:3677:4: (lv_table_2_0= ruleTable )
                    // InternalKactors.g:3678:5: lv_table_2_0= ruleTable
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

            otherlv_3=(Token)match(input,63,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:3703:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalKactors.g:3703:46: (iv_ruleTable= ruleTable EOF )
            // InternalKactors.g:3704:2: iv_ruleTable= ruleTable EOF
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
    // InternalKactors.g:3710:1: ruleTable returns [EObject current=null] : ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token this_SEPARATOR_1=null;
        Token otherlv_3=null;
        EObject lv_headers_0_0 = null;

        EObject lv_rows_2_0 = null;

        EObject lv_rows_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3716:2: ( ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) )
            // InternalKactors.g:3717:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            {
            // InternalKactors.g:3717:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            // InternalKactors.g:3718:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            {
            // InternalKactors.g:3718:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?
            int alt51=2;
            alt51 = dfa51.predict(input);
            switch (alt51) {
                case 1 :
                    // InternalKactors.g:3719:4: ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR
                    {
                    // InternalKactors.g:3719:4: ( (lv_headers_0_0= ruleHeaderRow ) )
                    // InternalKactors.g:3720:5: (lv_headers_0_0= ruleHeaderRow )
                    {
                    // InternalKactors.g:3720:5: (lv_headers_0_0= ruleHeaderRow )
                    // InternalKactors.g:3721:6: lv_headers_0_0= ruleHeaderRow
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

            // InternalKactors.g:3743:3: ( (lv_rows_2_0= ruleTableRow ) )
            // InternalKactors.g:3744:4: (lv_rows_2_0= ruleTableRow )
            {
            // InternalKactors.g:3744:4: (lv_rows_2_0= ruleTableRow )
            // InternalKactors.g:3745:5: lv_rows_2_0= ruleTableRow
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

            // InternalKactors.g:3762:3: (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==27) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // InternalKactors.g:3763:4: otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) )
            	    {
            	    otherlv_3=(Token)match(input,27,FOLLOW_50); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getTableAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKactors.g:3767:4: ( (lv_rows_4_0= ruleTableRow ) )
            	    // InternalKactors.g:3768:5: (lv_rows_4_0= ruleTableRow )
            	    {
            	    // InternalKactors.g:3768:5: (lv_rows_4_0= ruleTableRow )
            	    // InternalKactors.g:3769:6: lv_rows_4_0= ruleTableRow
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
    // $ANTLR end "ruleTable"


    // $ANTLR start "entryRuleHeaderRow"
    // InternalKactors.g:3791:1: entryRuleHeaderRow returns [EObject current=null] : iv_ruleHeaderRow= ruleHeaderRow EOF ;
    public final EObject entryRuleHeaderRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHeaderRow = null;


        try {
            // InternalKactors.g:3791:50: (iv_ruleHeaderRow= ruleHeaderRow EOF )
            // InternalKactors.g:3792:2: iv_ruleHeaderRow= ruleHeaderRow EOF
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
    // InternalKactors.g:3798:1: ruleHeaderRow returns [EObject current=null] : ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) ;
    public final EObject ruleHeaderRow() throws RecognitionException {
        EObject current = null;

        Token lv_elements_0_1=null;
        Token lv_elements_0_2=null;
        Token otherlv_1=null;
        Token lv_elements_2_1=null;
        Token lv_elements_2_2=null;


        	enterRule();

        try {
            // InternalKactors.g:3804:2: ( ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) )
            // InternalKactors.g:3805:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            {
            // InternalKactors.g:3805:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            // InternalKactors.g:3806:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            {
            // InternalKactors.g:3806:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) )
            // InternalKactors.g:3807:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            {
            // InternalKactors.g:3807:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            // InternalKactors.g:3808:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            {
            // InternalKactors.g:3808:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==RULE_LOWERCASE_ID) ) {
                alt53=1;
            }
            else if ( (LA53_0==RULE_STRING) ) {
                alt53=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }
            switch (alt53) {
                case 1 :
                    // InternalKactors.g:3809:6: lv_elements_0_1= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:3824:6: lv_elements_0_2= RULE_STRING
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

            // InternalKactors.g:3841:3: (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==64) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalKactors.g:3842:4: otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    {
            	    otherlv_1=(Token)match(input,64,FOLLOW_52); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getHeaderRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:3846:4: ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    // InternalKactors.g:3847:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:3847:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    // InternalKactors.g:3848:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    {
            	    // InternalKactors.g:3848:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    int alt54=2;
            	    int LA54_0 = input.LA(1);

            	    if ( (LA54_0==RULE_LOWERCASE_ID) ) {
            	        alt54=1;
            	    }
            	    else if ( (LA54_0==RULE_STRING) ) {
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
            	            // InternalKactors.g:3849:7: lv_elements_2_1= RULE_LOWERCASE_ID
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
            	            // InternalKactors.g:3864:7: lv_elements_2_2= RULE_STRING
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
    // $ANTLR end "ruleHeaderRow"


    // $ANTLR start "entryRuleTableRow"
    // InternalKactors.g:3886:1: entryRuleTableRow returns [EObject current=null] : iv_ruleTableRow= ruleTableRow EOF ;
    public final EObject entryRuleTableRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableRow = null;


        try {
            // InternalKactors.g:3886:49: (iv_ruleTableRow= ruleTableRow EOF )
            // InternalKactors.g:3887:2: iv_ruleTableRow= ruleTableRow EOF
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
    // InternalKactors.g:3893:1: ruleTableRow returns [EObject current=null] : ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) ;
    public final EObject ruleTableRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_elements_0_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3899:2: ( ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) )
            // InternalKactors.g:3900:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            {
            // InternalKactors.g:3900:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            // InternalKactors.g:3901:3: ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            {
            // InternalKactors.g:3901:3: ( (lv_elements_0_0= ruleTableClassifier ) )
            // InternalKactors.g:3902:4: (lv_elements_0_0= ruleTableClassifier )
            {
            // InternalKactors.g:3902:4: (lv_elements_0_0= ruleTableClassifier )
            // InternalKactors.g:3903:5: lv_elements_0_0= ruleTableClassifier
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

            // InternalKactors.g:3920:3: (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==64) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // InternalKactors.g:3921:4: otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) )
            	    {
            	    otherlv_1=(Token)match(input,64,FOLLOW_50); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:3925:4: ( (lv_elements_2_0= ruleTableClassifier ) )
            	    // InternalKactors.g:3926:5: (lv_elements_2_0= ruleTableClassifier )
            	    {
            	    // InternalKactors.g:3926:5: (lv_elements_2_0= ruleTableClassifier )
            	    // InternalKactors.g:3927:6: lv_elements_2_0= ruleTableClassifier
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
    // $ANTLR end "ruleTableRow"


    // $ANTLR start "entryRuleTableClassifier"
    // InternalKactors.g:3949:1: entryRuleTableClassifier returns [EObject current=null] : iv_ruleTableClassifier= ruleTableClassifier EOF ;
    public final EObject entryRuleTableClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableClassifier = null;


        try {
            // InternalKactors.g:3949:56: (iv_ruleTableClassifier= ruleTableClassifier EOF )
            // InternalKactors.g:3950:2: iv_ruleTableClassifier= ruleTableClassifier EOF
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
    // InternalKactors.g:3956:1: ruleTableClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) ;
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
            // InternalKactors.g:3962:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) )
            // InternalKactors.g:3963:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            {
            // InternalKactors.g:3963:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            int alt60=13;
            alt60 = dfa60.predict(input);
            switch (alt60) {
                case 1 :
                    // InternalKactors.g:3964:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:3964:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt57=2;
                    int LA57_0 = input.LA(1);

                    if ( (LA57_0==49) ) {
                        alt57=1;
                    }
                    else if ( (LA57_0==50) ) {
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
                            // InternalKactors.g:3965:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:3965:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:3966:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:3966:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:3967:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,49,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:3980:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:3980:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:3981:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:3981:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:3982:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3996:3: ( (lv_num_2_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3996:3: ( (lv_num_2_0= ruleNumber ) )
                    // InternalKactors.g:3997:4: (lv_num_2_0= ruleNumber )
                    {
                    // InternalKactors.g:3997:4: (lv_num_2_0= ruleNumber )
                    // InternalKactors.g:3998:5: lv_num_2_0= ruleNumber
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
                    // InternalKactors.g:4016:3: ( (lv_string_3_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:4016:3: ( (lv_string_3_0= RULE_STRING ) )
                    // InternalKactors.g:4017:4: (lv_string_3_0= RULE_STRING )
                    {
                    // InternalKactors.g:4017:4: (lv_string_3_0= RULE_STRING )
                    // InternalKactors.g:4018:5: lv_string_3_0= RULE_STRING
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
                    // InternalKactors.g:4035:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:4035:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:4036:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:4036:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    // InternalKactors.g:4037:5: lv_observable_4_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:4054:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:4054:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    // InternalKactors.g:4055:4: ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4055:4: ( (lv_op_5_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:4056:5: (lv_op_5_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:4056:5: (lv_op_5_0= ruleREL_OPERATOR )
                    // InternalKactors.g:4057:6: lv_op_5_0= ruleREL_OPERATOR
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

                    // InternalKactors.g:4074:4: ( (lv_expression_6_0= ruleNumber ) )
                    // InternalKactors.g:4075:5: (lv_expression_6_0= ruleNumber )
                    {
                    // InternalKactors.g:4075:5: (lv_expression_6_0= ruleNumber )
                    // InternalKactors.g:4076:6: lv_expression_6_0= ruleNumber
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
                    // InternalKactors.g:4095:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    {
                    // InternalKactors.g:4095:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    // InternalKactors.g:4096:4: ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    {
                    // InternalKactors.g:4096:4: ( (lv_int0_7_0= ruleNumber ) )
                    // InternalKactors.g:4097:5: (lv_int0_7_0= ruleNumber )
                    {
                    // InternalKactors.g:4097:5: (lv_int0_7_0= ruleNumber )
                    // InternalKactors.g:4098:6: lv_int0_7_0= ruleNumber
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

                    // InternalKactors.g:4115:4: ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )?
                    int alt58=3;
                    int LA58_0 = input.LA(1);

                    if ( (LA58_0==51) ) {
                        alt58=1;
                    }
                    else if ( (LA58_0==52) ) {
                        alt58=2;
                    }
                    switch (alt58) {
                        case 1 :
                            // InternalKactors.g:4116:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4116:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            // InternalKactors.g:4117:6: (lv_leftLimit_8_0= 'inclusive' )
                            {
                            // InternalKactors.g:4117:6: (lv_leftLimit_8_0= 'inclusive' )
                            // InternalKactors.g:4118:7: lv_leftLimit_8_0= 'inclusive'
                            {
                            lv_leftLimit_8_0=(Token)match(input,51,FOLLOW_34); if (state.failed) return current;
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
                            // InternalKactors.g:4131:5: otherlv_9= 'exclusive'
                            {
                            otherlv_9=(Token)match(input,52,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_9, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_5_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:4136:4: ( ( 'to' )=>otherlv_10= 'to' )
                    // InternalKactors.g:4137:5: ( 'to' )=>otherlv_10= 'to'
                    {
                    otherlv_10=(Token)match(input,53,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_10, grammarAccess.getTableClassifierAccess().getToKeyword_5_2());
                      				
                    }

                    }

                    // InternalKactors.g:4143:4: ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) )
                    // InternalKactors.g:4144:5: ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber )
                    {
                    // InternalKactors.g:4148:5: (lv_int1_11_0= ruleNumber )
                    // InternalKactors.g:4149:6: lv_int1_11_0= ruleNumber
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

                    // InternalKactors.g:4166:4: ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    int alt59=3;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==51) ) {
                        alt59=1;
                    }
                    else if ( (LA59_0==52) ) {
                        alt59=2;
                    }
                    switch (alt59) {
                        case 1 :
                            // InternalKactors.g:4167:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4167:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            // InternalKactors.g:4168:6: (lv_rightLimit_12_0= 'inclusive' )
                            {
                            // InternalKactors.g:4168:6: (lv_rightLimit_12_0= 'inclusive' )
                            // InternalKactors.g:4169:7: lv_rightLimit_12_0= 'inclusive'
                            {
                            lv_rightLimit_12_0=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:4182:5: otherlv_13= 'exclusive'
                            {
                            otherlv_13=(Token)match(input,52,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:4189:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    {
                    // InternalKactors.g:4189:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    // InternalKactors.g:4190:4: otherlv_14= 'in' ( (lv_set_15_0= ruleList ) )
                    {
                    otherlv_14=(Token)match(input,47,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getTableClassifierAccess().getInKeyword_6_0());
                      			
                    }
                    // InternalKactors.g:4194:4: ( (lv_set_15_0= ruleList ) )
                    // InternalKactors.g:4195:5: (lv_set_15_0= ruleList )
                    {
                    // InternalKactors.g:4195:5: (lv_set_15_0= ruleList )
                    // InternalKactors.g:4196:6: lv_set_15_0= ruleList
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
                    // InternalKactors.g:4215:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    {
                    // InternalKactors.g:4215:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    // InternalKactors.g:4216:4: (lv_quantity_16_0= ruleQuantity )
                    {
                    // InternalKactors.g:4216:4: (lv_quantity_16_0= ruleQuantity )
                    // InternalKactors.g:4217:5: lv_quantity_16_0= ruleQuantity
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
                    // InternalKactors.g:4235:3: ( (lv_date_17_0= ruleDate ) )
                    {
                    // InternalKactors.g:4235:3: ( (lv_date_17_0= ruleDate ) )
                    // InternalKactors.g:4236:4: (lv_date_17_0= ruleDate )
                    {
                    // InternalKactors.g:4236:4: (lv_date_17_0= ruleDate )
                    // InternalKactors.g:4237:5: lv_date_17_0= ruleDate
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
                    // InternalKactors.g:4255:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:4255:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    // InternalKactors.g:4256:4: (lv_expr_18_0= RULE_EXPR )
                    {
                    // InternalKactors.g:4256:4: (lv_expr_18_0= RULE_EXPR )
                    // InternalKactors.g:4257:5: lv_expr_18_0= RULE_EXPR
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
                    // InternalKactors.g:4274:3: ( (lv_nodata_19_0= 'unknown' ) )
                    {
                    // InternalKactors.g:4274:3: ( (lv_nodata_19_0= 'unknown' ) )
                    // InternalKactors.g:4275:4: (lv_nodata_19_0= 'unknown' )
                    {
                    // InternalKactors.g:4275:4: (lv_nodata_19_0= 'unknown' )
                    // InternalKactors.g:4276:5: lv_nodata_19_0= 'unknown'
                    {
                    lv_nodata_19_0=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:4289:3: ( (lv_star_20_0= '*' ) )
                    {
                    // InternalKactors.g:4289:3: ( (lv_star_20_0= '*' ) )
                    // InternalKactors.g:4290:4: (lv_star_20_0= '*' )
                    {
                    // InternalKactors.g:4290:4: (lv_star_20_0= '*' )
                    // InternalKactors.g:4291:5: lv_star_20_0= '*'
                    {
                    lv_star_20_0=(Token)match(input,55,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:4304:3: ( (lv_anything_21_0= '#' ) )
                    {
                    // InternalKactors.g:4304:3: ( (lv_anything_21_0= '#' ) )
                    // InternalKactors.g:4305:4: (lv_anything_21_0= '#' )
                    {
                    // InternalKactors.g:4305:4: (lv_anything_21_0= '#' )
                    // InternalKactors.g:4306:5: lv_anything_21_0= '#'
                    {
                    lv_anything_21_0=(Token)match(input,56,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:4322:1: entryRuleQuantity returns [EObject current=null] : iv_ruleQuantity= ruleQuantity EOF ;
    public final EObject entryRuleQuantity() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQuantity = null;


        try {
            // InternalKactors.g:4322:49: (iv_ruleQuantity= ruleQuantity EOF )
            // InternalKactors.g:4323:2: iv_ruleQuantity= ruleQuantity EOF
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
    // InternalKactors.g:4329:1: ruleQuantity returns [EObject current=null] : ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) ;
    public final EObject ruleQuantity() throws RecognitionException {
        EObject current = null;

        Token lv_over_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_0_0 = null;

        EObject lv_unit_3_0 = null;

        EObject lv_currency_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4335:2: ( ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) )
            // InternalKactors.g:4336:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            {
            // InternalKactors.g:4336:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            // InternalKactors.g:4337:3: ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            {
            // InternalKactors.g:4337:3: ( (lv_value_0_0= ruleNumber ) )
            // InternalKactors.g:4338:4: (lv_value_0_0= ruleNumber )
            {
            // InternalKactors.g:4338:4: (lv_value_0_0= ruleNumber )
            // InternalKactors.g:4339:5: lv_value_0_0= ruleNumber
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

            // InternalKactors.g:4356:3: ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' )
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==65) ) {
                alt61=1;
            }
            else if ( (LA61_0==66) ) {
                alt61=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }
            switch (alt61) {
                case 1 :
                    // InternalKactors.g:4357:4: ( (lv_over_1_0= '/' ) )
                    {
                    // InternalKactors.g:4357:4: ( (lv_over_1_0= '/' ) )
                    // InternalKactors.g:4358:5: (lv_over_1_0= '/' )
                    {
                    // InternalKactors.g:4358:5: (lv_over_1_0= '/' )
                    // InternalKactors.g:4359:6: lv_over_1_0= '/'
                    {
                    lv_over_1_0=(Token)match(input,65,FOLLOW_54); if (state.failed) return current;
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
                    // InternalKactors.g:4372:4: otherlv_2= '.'
                    {
                    otherlv_2=(Token)match(input,66,FOLLOW_54); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getQuantityAccess().getFullStopKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:4377:3: ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==EOF||LA62_0==RULE_LOWERCASE_ID||LA62_0==RULE_CAMELCASE_ID||LA62_0==27||LA62_0==39||LA62_0==48||LA62_0==55||(LA62_0>=63 && LA62_0<=65)||LA62_0==82) ) {
                alt62=1;
            }
            else if ( (LA62_0==RULE_UPPERCASE_ID) ) {
                int LA62_2 = input.LA(2);

                if ( (LA62_2==EOF||LA62_2==27||LA62_2==48||LA62_2==55||(LA62_2>=63 && LA62_2<=65)||LA62_2==82) ) {
                    alt62=1;
                }
                else if ( (LA62_2==68) ) {
                    alt62=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 62, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }
            switch (alt62) {
                case 1 :
                    // InternalKactors.g:4378:4: ( (lv_unit_3_0= ruleUnit ) )
                    {
                    // InternalKactors.g:4378:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKactors.g:4379:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKactors.g:4379:5: (lv_unit_3_0= ruleUnit )
                    // InternalKactors.g:4380:6: lv_unit_3_0= ruleUnit
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
                    // InternalKactors.g:4398:4: ( (lv_currency_4_0= ruleCurrency ) )
                    {
                    // InternalKactors.g:4398:4: ( (lv_currency_4_0= ruleCurrency ) )
                    // InternalKactors.g:4399:5: (lv_currency_4_0= ruleCurrency )
                    {
                    // InternalKactors.g:4399:5: (lv_currency_4_0= ruleCurrency )
                    // InternalKactors.g:4400:6: lv_currency_4_0= ruleCurrency
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
    // InternalKactors.g:4422:1: entryRuleAnnotation returns [EObject current=null] : iv_ruleAnnotation= ruleAnnotation EOF ;
    public final EObject entryRuleAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnnotation = null;


        try {
            // InternalKactors.g:4422:51: (iv_ruleAnnotation= ruleAnnotation EOF )
            // InternalKactors.g:4423:2: iv_ruleAnnotation= ruleAnnotation EOF
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
    // InternalKactors.g:4429:1: ruleAnnotation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) ;
    public final EObject ruleAnnotation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4435:2: ( ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) )
            // InternalKactors.g:4436:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            {
            // InternalKactors.g:4436:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            // InternalKactors.g:4437:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            {
            // InternalKactors.g:4437:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) )
            // InternalKactors.g:4438:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            {
            // InternalKactors.g:4438:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            // InternalKactors.g:4439:5: lv_name_0_0= RULE_ANNOTATION_ID
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

            // InternalKactors.g:4455:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==39) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalKactors.g:4456:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,39,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getAnnotationAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:4460:4: ( (lv_parameters_2_0= ruleParameterList ) )?
                    int alt63=2;
                    int LA63_0 = input.LA(1);

                    if ( ((LA63_0>=RULE_OBSERVABLE && LA63_0<=RULE_LOWERCASE_ID)||LA63_0==RULE_STRING||LA63_0==RULE_EXPR||LA63_0==RULE_INT||LA63_0==RULE_ARGVALUE||LA63_0==39||(LA63_0>=49 && LA63_0<=50)||LA63_0==57||LA63_0==60||LA63_0==62||(LA63_0>=74 && LA63_0<=75)) ) {
                        alt63=1;
                    }
                    switch (alt63) {
                        case 1 :
                            // InternalKactors.g:4461:5: (lv_parameters_2_0= ruleParameterList )
                            {
                            // InternalKactors.g:4461:5: (lv_parameters_2_0= ruleParameterList )
                            // InternalKactors.g:4462:6: lv_parameters_2_0= ruleParameterList
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

                    otherlv_3=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:4488:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKactors.g:4488:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKactors.g:4489:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKactors.g:4495:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) ;
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
            // InternalKactors.g:4501:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) )
            // InternalKactors.g:4502:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            {
            // InternalKactors.g:4502:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            int alt66=5;
            alt66 = dfa66.predict(input);
            switch (alt66) {
                case 1 :
                    // InternalKactors.g:4503:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4503:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKactors.g:4504:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKactors.g:4504:4: (lv_number_0_0= ruleNumber )
                    // InternalKactors.g:4505:5: lv_number_0_0= ruleNumber
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
                    // InternalKactors.g:4523:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:4523:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKactors.g:4524:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4524:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKactors.g:4525:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKactors.g:4525:5: (lv_from_1_0= ruleNumber )
                    // InternalKactors.g:4526:6: lv_from_1_0= ruleNumber
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

                    otherlv_2=(Token)match(input,53,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:4547:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKactors.g:4548:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKactors.g:4548:5: (lv_to_3_0= ruleNumber )
                    // InternalKactors.g:4549:6: lv_to_3_0= ruleNumber
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
                    // InternalKactors.g:4568:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:4568:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKactors.g:4569:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKactors.g:4569:4: (lv_string_4_0= RULE_STRING )
                    // InternalKactors.g:4570:5: lv_string_4_0= RULE_STRING
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
                    // InternalKactors.g:4587:3: ( (lv_date_5_0= ruleDate ) )
                    {
                    // InternalKactors.g:4587:3: ( (lv_date_5_0= ruleDate ) )
                    // InternalKactors.g:4588:4: (lv_date_5_0= ruleDate )
                    {
                    // InternalKactors.g:4588:4: (lv_date_5_0= ruleDate )
                    // InternalKactors.g:4589:5: lv_date_5_0= ruleDate
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
                    // InternalKactors.g:4607:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    {
                    // InternalKactors.g:4607:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    // InternalKactors.g:4608:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    {
                    // InternalKactors.g:4608:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    // InternalKactors.g:4609:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    {
                    // InternalKactors.g:4609:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    int alt65=2;
                    int LA65_0 = input.LA(1);

                    if ( (LA65_0==49) ) {
                        alt65=1;
                    }
                    else if ( (LA65_0==50) ) {
                        alt65=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 65, 0, input);

                        throw nvae;
                    }
                    switch (alt65) {
                        case 1 :
                            // InternalKactors.g:4610:6: lv_boolean_6_1= 'true'
                            {
                            lv_boolean_6_1=(Token)match(input,49,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:4621:6: lv_boolean_6_2= 'false'
                            {
                            lv_boolean_6_2=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:4638:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKactors.g:4638:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKactors.g:4639:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKactors.g:4645:1: ruleParameterList returns [EObject current=null] : ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) ;
    public final EObject ruleParameterList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_pairs_0_0 = null;

        EObject lv_pairs_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4651:2: ( ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) )
            // InternalKactors.g:4652:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            {
            // InternalKactors.g:4652:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            // InternalKactors.g:4653:3: ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            {
            // InternalKactors.g:4653:3: ( (lv_pairs_0_0= ruleKeyValuePair ) )
            // InternalKactors.g:4654:4: (lv_pairs_0_0= ruleKeyValuePair )
            {
            // InternalKactors.g:4654:4: (lv_pairs_0_0= ruleKeyValuePair )
            // InternalKactors.g:4655:5: lv_pairs_0_0= ruleKeyValuePair
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

            // InternalKactors.g:4672:3: ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==27) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // InternalKactors.g:4673:4: ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    {
            	    // InternalKactors.g:4673:4: ( ( ',' )=>otherlv_1= ',' )
            	    // InternalKactors.g:4674:5: ( ',' )=>otherlv_1= ','
            	    {
            	    otherlv_1=(Token)match(input,27,FOLLOW_15); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_1_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:4680:4: ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    // InternalKactors.g:4681:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    {
            	    // InternalKactors.g:4681:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    // InternalKactors.g:4682:6: lv_pairs_2_0= ruleKeyValuePair
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
            	    break loop67;
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
    // InternalKactors.g:4704:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKactors.g:4704:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKactors.g:4705:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKactors.g:4711:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4717:2: ( ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKactors.g:4718:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKactors.g:4718:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            // InternalKactors.g:4719:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKactors.g:4719:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==RULE_LOWERCASE_ID) ) {
                int LA69_1 = input.LA(2);

                if ( (LA69_1==59||LA69_1==67) ) {
                    alt69=1;
                }
            }
            switch (alt69) {
                case 1 :
                    // InternalKactors.g:4720:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    {
                    // InternalKactors.g:4720:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:4721:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:4721:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:4722:6: lv_name_0_0= RULE_LOWERCASE_ID
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

                    // InternalKactors.g:4738:4: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    int alt68=2;
                    int LA68_0 = input.LA(1);

                    if ( (LA68_0==67) ) {
                        alt68=1;
                    }
                    else if ( (LA68_0==59) ) {
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
                            // InternalKactors.g:4739:5: ( (lv_interactive_1_0= '=?' ) )
                            {
                            // InternalKactors.g:4739:5: ( (lv_interactive_1_0= '=?' ) )
                            // InternalKactors.g:4740:6: (lv_interactive_1_0= '=?' )
                            {
                            // InternalKactors.g:4740:6: (lv_interactive_1_0= '=?' )
                            // InternalKactors.g:4741:7: lv_interactive_1_0= '=?'
                            {
                            lv_interactive_1_0=(Token)match(input,67,FOLLOW_15); if (state.failed) return current;
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
                            // InternalKactors.g:4754:5: otherlv_2= '='
                            {
                            otherlv_2=(Token)match(input,59,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_0_1_1());
                              				
                            }

                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalKactors.g:4760:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:4761:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:4761:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:4762:5: lv_value_3_0= ruleValue
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
    // InternalKactors.g:4783:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKactors.g:4783:46: (iv_ruleValue= ruleValue EOF )
            // InternalKactors.g:4784:2: iv_ruleValue= ruleValue EOF
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
    // InternalKactors.g:4790:1: ruleValue returns [EObject current=null] : ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) ) ;
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
            // InternalKactors.g:4796:2: ( ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) ) )
            // InternalKactors.g:4797:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )
            {
            // InternalKactors.g:4797:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )
            int alt70=9;
            alt70 = dfa70.predict(input);
            switch (alt70) {
                case 1 :
                    // InternalKactors.g:4798:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    {
                    // InternalKactors.g:4798:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    // InternalKactors.g:4799:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    {
                    // InternalKactors.g:4799:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    // InternalKactors.g:4800:5: lv_argvalue_0_0= RULE_ARGVALUE
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
                    // InternalKactors.g:4817:3: ( (lv_literal_1_0= ruleLiteral ) )
                    {
                    // InternalKactors.g:4817:3: ( (lv_literal_1_0= ruleLiteral ) )
                    // InternalKactors.g:4818:4: (lv_literal_1_0= ruleLiteral )
                    {
                    // InternalKactors.g:4818:4: (lv_literal_1_0= ruleLiteral )
                    // InternalKactors.g:4819:5: lv_literal_1_0= ruleLiteral
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
                    // InternalKactors.g:4837:3: ( (lv_id_2_0= rulePathName ) )
                    {
                    // InternalKactors.g:4837:3: ( (lv_id_2_0= rulePathName ) )
                    // InternalKactors.g:4838:4: (lv_id_2_0= rulePathName )
                    {
                    // InternalKactors.g:4838:4: (lv_id_2_0= rulePathName )
                    // InternalKactors.g:4839:5: lv_id_2_0= rulePathName
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
                    // InternalKactors.g:4857:3: ( (lv_urn_3_0= ruleUrnId ) )
                    {
                    // InternalKactors.g:4857:3: ( (lv_urn_3_0= ruleUrnId ) )
                    // InternalKactors.g:4858:4: (lv_urn_3_0= ruleUrnId )
                    {
                    // InternalKactors.g:4858:4: (lv_urn_3_0= ruleUrnId )
                    // InternalKactors.g:4859:5: lv_urn_3_0= ruleUrnId
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
                    // InternalKactors.g:4877:3: ( (lv_list_4_0= ruleList ) )
                    {
                    // InternalKactors.g:4877:3: ( (lv_list_4_0= ruleList ) )
                    // InternalKactors.g:4878:4: (lv_list_4_0= ruleList )
                    {
                    // InternalKactors.g:4878:4: (lv_list_4_0= ruleList )
                    // InternalKactors.g:4879:5: lv_list_4_0= ruleList
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
                    // InternalKactors.g:4897:3: ( (lv_map_5_0= ruleMap ) )
                    {
                    // InternalKactors.g:4897:3: ( (lv_map_5_0= ruleMap ) )
                    // InternalKactors.g:4898:4: (lv_map_5_0= ruleMap )
                    {
                    // InternalKactors.g:4898:4: (lv_map_5_0= ruleMap )
                    // InternalKactors.g:4899:5: lv_map_5_0= ruleMap
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
                    // InternalKactors.g:4917:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:4917:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:4918:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:4918:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    // InternalKactors.g:4919:5: lv_observable_6_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:4936:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:4936:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    // InternalKactors.g:4937:4: (lv_expression_7_0= RULE_EXPR )
                    {
                    // InternalKactors.g:4937:4: (lv_expression_7_0= RULE_EXPR )
                    // InternalKactors.g:4938:5: lv_expression_7_0= RULE_EXPR
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
                    // InternalKactors.g:4955:3: ( (lv_table_8_0= ruleLookupTable ) )
                    {
                    // InternalKactors.g:4955:3: ( (lv_table_8_0= ruleLookupTable ) )
                    // InternalKactors.g:4956:4: (lv_table_8_0= ruleLookupTable )
                    {
                    // InternalKactors.g:4956:4: (lv_table_8_0= ruleLookupTable )
                    // InternalKactors.g:4957:5: lv_table_8_0= ruleLookupTable
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
    // InternalKactors.g:4978:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKactors.g:4978:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKactors.g:4979:2: iv_ruleUnitElement= ruleUnitElement EOF
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
    // InternalKactors.g:4985:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) ;
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
            // InternalKactors.g:4991:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) )
            // InternalKactors.g:4992:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            {
            // InternalKactors.g:4992:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==RULE_LOWERCASE_ID||LA72_0==RULE_CAMELCASE_ID||LA72_0==RULE_UPPERCASE_ID) ) {
                alt72=1;
            }
            else if ( (LA72_0==39) ) {
                alt72=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }
            switch (alt72) {
                case 1 :
                    // InternalKactors.g:4993:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    {
                    // InternalKactors.g:4993:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    // InternalKactors.g:4994:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKactors.g:4994:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    // InternalKactors.g:4995:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    {
                    // InternalKactors.g:4995:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    int alt71=3;
                    switch ( input.LA(1) ) {
                    case RULE_CAMELCASE_ID:
                        {
                        alt71=1;
                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        alt71=2;
                        }
                        break;
                    case RULE_UPPERCASE_ID:
                        {
                        alt71=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 71, 0, input);

                        throw nvae;
                    }

                    switch (alt71) {
                        case 1 :
                            // InternalKactors.g:4996:6: lv_id_0_1= RULE_CAMELCASE_ID
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
                            // InternalKactors.g:5011:6: lv_id_0_2= RULE_LOWERCASE_ID
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
                            // InternalKactors.g:5026:6: lv_id_0_3= RULE_UPPERCASE_ID
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
                    // InternalKactors.g:5044:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    {
                    // InternalKactors.g:5044:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    // InternalKactors.g:5045:4: otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,39,FOLLOW_57); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:5049:4: ( (lv_unit_2_0= ruleUnit ) )
                    // InternalKactors.g:5050:5: (lv_unit_2_0= ruleUnit )
                    {
                    // InternalKactors.g:5050:5: (lv_unit_2_0= ruleUnit )
                    // InternalKactors.g:5051:6: lv_unit_2_0= ruleUnit
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

                    otherlv_3=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:5077:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKactors.g:5077:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKactors.g:5078:2: iv_ruleUnit= ruleUnit EOF
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
    // InternalKactors.g:5084:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5090:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:5091:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:5091:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:5092:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:5092:3: ()
            // InternalKactors.g:5093:4: 
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

            // InternalKactors.g:5102:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==RULE_LOWERCASE_ID||LA73_0==RULE_CAMELCASE_ID||LA73_0==RULE_UPPERCASE_ID||LA73_0==39) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // InternalKactors.g:5103:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKactors.g:5103:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKactors.g:5104:5: lv_root_1_0= ruleUnitElement
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

            // InternalKactors.g:5121:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop74:
            do {
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( (LA74_0==55||LA74_0==65||LA74_0==82) ) {
                    alt74=1;
                }


                switch (alt74) {
            	case 1 :
            	    // InternalKactors.g:5122:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:5122:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKactors.g:5123:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKactors.g:5129:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKactors.g:5130:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKactors.g:5130:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKactors.g:5131:7: lv_connectors_2_0= ruleUnitOp
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

            	    // InternalKactors.g:5149:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKactors.g:5150:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:5150:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKactors.g:5151:6: lv_units_3_0= ruleUnitElement
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
            	    break loop74;
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
    // InternalKactors.g:5173:1: entryRuleCurrency returns [EObject current=null] : iv_ruleCurrency= ruleCurrency EOF ;
    public final EObject entryRuleCurrency() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCurrency = null;


        try {
            // InternalKactors.g:5173:49: (iv_ruleCurrency= ruleCurrency EOF )
            // InternalKactors.g:5174:2: iv_ruleCurrency= ruleCurrency EOF
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
    // InternalKactors.g:5180:1: ruleCurrency returns [EObject current=null] : ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleCurrency() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_year_2_0=null;
        Token otherlv_3=null;
        EObject lv_units_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5186:2: ( ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:5187:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:5187:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:5188:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:5188:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) )
            // InternalKactors.g:5189:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            {
            // InternalKactors.g:5189:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            // InternalKactors.g:5190:5: lv_id_0_0= RULE_UPPERCASE_ID
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

            // InternalKactors.g:5206:3: (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) )
            // InternalKactors.g:5207:4: otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) )
            {
            otherlv_1=(Token)match(input,68,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_1, grammarAccess.getCurrencyAccess().getCommercialAtKeyword_1_0());
              			
            }
            // InternalKactors.g:5211:4: ( (lv_year_2_0= RULE_INT ) )
            // InternalKactors.g:5212:5: (lv_year_2_0= RULE_INT )
            {
            // InternalKactors.g:5212:5: (lv_year_2_0= RULE_INT )
            // InternalKactors.g:5213:6: lv_year_2_0= RULE_INT
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

            // InternalKactors.g:5230:3: ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            loop75:
            do {
                int alt75=2;
                int LA75_0 = input.LA(1);

                if ( (LA75_0==65) ) {
                    alt75=1;
                }


                switch (alt75) {
            	case 1 :
            	    // InternalKactors.g:5231:4: ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:5231:4: ( ( '/' )=>otherlv_3= '/' )
            	    // InternalKactors.g:5232:5: ( '/' )=>otherlv_3= '/'
            	    {
            	    otherlv_3=(Token)match(input,65,FOLLOW_59); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_3, grammarAccess.getCurrencyAccess().getSolidusKeyword_2_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:5238:4: ( (lv_units_4_0= ruleUnitElement ) )
            	    // InternalKactors.g:5239:5: (lv_units_4_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:5239:5: (lv_units_4_0= ruleUnitElement )
            	    // InternalKactors.g:5240:6: lv_units_4_0= ruleUnitElement
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
            	    break loop75;
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
    // InternalKactors.g:5262:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKactors.g:5262:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKactors.g:5263:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
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
    // InternalKactors.g:5269:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
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
            // InternalKactors.g:5275:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKactors.g:5276:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKactors.g:5276:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt76=6;
            switch ( input.LA(1) ) {
            case 69:
                {
                alt76=1;
                }
                break;
            case 70:
                {
                alt76=2;
                }
                break;
            case 59:
                {
                alt76=3;
                }
                break;
            case 71:
                {
                alt76=4;
                }
                break;
            case 72:
                {
                alt76=5;
                }
                break;
            case 73:
                {
                alt76=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }

            switch (alt76) {
                case 1 :
                    // InternalKactors.g:5277:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKactors.g:5277:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKactors.g:5278:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKactors.g:5278:4: (lv_gt_0_0= '>' )
                    // InternalKactors.g:5279:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,69,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5292:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKactors.g:5292:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKactors.g:5293:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKactors.g:5293:4: (lv_lt_1_0= '<' )
                    // InternalKactors.g:5294:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,70,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5307:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKactors.g:5307:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKactors.g:5308:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKactors.g:5308:4: (lv_eq_2_0= '=' )
                    // InternalKactors.g:5309:5: lv_eq_2_0= '='
                    {
                    lv_eq_2_0=(Token)match(input,59,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5322:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKactors.g:5322:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKactors.g:5323:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKactors.g:5323:4: (lv_ne_3_0= '!=' )
                    // InternalKactors.g:5324:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,71,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5337:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKactors.g:5337:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKactors.g:5338:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKactors.g:5338:4: (lv_le_4_0= '<=' )
                    // InternalKactors.g:5339:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,72,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5352:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKactors.g:5352:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKactors.g:5353:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKactors.g:5353:4: (lv_ge_5_0= '>=' )
                    // InternalKactors.g:5354:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:5370:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKactors.g:5370:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKactors.g:5371:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKactors.g:5377:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) ;
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
            // InternalKactors.g:5383:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) )
            // InternalKactors.g:5384:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            {
            // InternalKactors.g:5384:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            // InternalKactors.g:5385:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            {
            // InternalKactors.g:5385:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt77=3;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==74) ) {
                alt77=1;
            }
            else if ( (LA77_0==75) ) {
                alt77=2;
            }
            switch (alt77) {
                case 1 :
                    // InternalKactors.g:5386:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,74,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5391:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKactors.g:5391:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKactors.g:5392:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKactors.g:5392:5: (lv_negative_1_0= '-' )
                    // InternalKactors.g:5393:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,75,FOLLOW_10); if (state.failed) return current;
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

            // InternalKactors.g:5406:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKactors.g:5407:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKactors.g:5411:4: (lv_real_2_0= RULE_INT )
            // InternalKactors.g:5412:5: lv_real_2_0= RULE_INT
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

            // InternalKactors.g:5428:3: ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==76) && (synpred172_InternalKactors())) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // InternalKactors.g:5429:4: ( ( 'l' ) )=> (lv_long_3_0= 'l' )
                    {
                    // InternalKactors.g:5433:4: (lv_long_3_0= 'l' )
                    // InternalKactors.g:5434:5: lv_long_3_0= 'l'
                    {
                    lv_long_3_0=(Token)match(input,76,FOLLOW_63); if (state.failed) return current;
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

            // InternalKactors.g:5446:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==66) ) {
                int LA79_1 = input.LA(2);

                if ( (LA79_1==RULE_INT) && (synpred173_InternalKactors())) {
                    alt79=1;
                }
            }
            switch (alt79) {
                case 1 :
                    // InternalKactors.g:5447:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5460:4: ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    // InternalKactors.g:5461:5: ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5461:5: ( (lv_decimal_4_0= '.' ) )
                    // InternalKactors.g:5462:6: (lv_decimal_4_0= '.' )
                    {
                    // InternalKactors.g:5462:6: (lv_decimal_4_0= '.' )
                    // InternalKactors.g:5463:7: lv_decimal_4_0= '.'
                    {
                    lv_decimal_4_0=(Token)match(input,66,FOLLOW_10); if (state.failed) return current;
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

                    // InternalKactors.g:5475:5: ( (lv_decimalPart_5_0= RULE_INT ) )
                    // InternalKactors.g:5476:6: (lv_decimalPart_5_0= RULE_INT )
                    {
                    // InternalKactors.g:5476:6: (lv_decimalPart_5_0= RULE_INT )
                    // InternalKactors.g:5477:7: lv_decimalPart_5_0= RULE_INT
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

            // InternalKactors.g:5495:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==77) && (synpred177_InternalKactors())) {
                alt82=1;
            }
            else if ( (LA82_0==78) && (synpred177_InternalKactors())) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // InternalKactors.g:5496:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5522:4: ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    // InternalKactors.g:5523:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5523:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) )
                    // InternalKactors.g:5524:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    {
                    // InternalKactors.g:5524:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    // InternalKactors.g:5525:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    {
                    // InternalKactors.g:5525:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    int alt80=2;
                    int LA80_0 = input.LA(1);

                    if ( (LA80_0==77) ) {
                        alt80=1;
                    }
                    else if ( (LA80_0==78) ) {
                        alt80=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 80, 0, input);

                        throw nvae;
                    }
                    switch (alt80) {
                        case 1 :
                            // InternalKactors.g:5526:8: lv_exponential_6_1= 'e'
                            {
                            lv_exponential_6_1=(Token)match(input,77,FOLLOW_35); if (state.failed) return current;
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
                            // InternalKactors.g:5537:8: lv_exponential_6_2= 'E'
                            {
                            lv_exponential_6_2=(Token)match(input,78,FOLLOW_35); if (state.failed) return current;
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

                    // InternalKactors.g:5550:5: (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )?
                    int alt81=3;
                    int LA81_0 = input.LA(1);

                    if ( (LA81_0==74) ) {
                        alt81=1;
                    }
                    else if ( (LA81_0==75) ) {
                        alt81=2;
                    }
                    switch (alt81) {
                        case 1 :
                            // InternalKactors.g:5551:6: otherlv_7= '+'
                            {
                            otherlv_7=(Token)match(input,74,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getNumberAccess().getPlusSignKeyword_4_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:5556:6: ( (lv_expNegative_8_0= '-' ) )
                            {
                            // InternalKactors.g:5556:6: ( (lv_expNegative_8_0= '-' ) )
                            // InternalKactors.g:5557:7: (lv_expNegative_8_0= '-' )
                            {
                            // InternalKactors.g:5557:7: (lv_expNegative_8_0= '-' )
                            // InternalKactors.g:5558:8: lv_expNegative_8_0= '-'
                            {
                            lv_expNegative_8_0=(Token)match(input,75,FOLLOW_10); if (state.failed) return current;
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

                    // InternalKactors.g:5571:5: ( (lv_exp_9_0= RULE_INT ) )
                    // InternalKactors.g:5572:6: (lv_exp_9_0= RULE_INT )
                    {
                    // InternalKactors.g:5572:6: (lv_exp_9_0= RULE_INT )
                    // InternalKactors.g:5573:7: lv_exp_9_0= RULE_INT
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
    // InternalKactors.g:5595:1: entryRuleDate returns [EObject current=null] : iv_ruleDate= ruleDate EOF ;
    public final EObject entryRuleDate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDate = null;


        try {
            // InternalKactors.g:5595:45: (iv_ruleDate= ruleDate EOF )
            // InternalKactors.g:5596:2: iv_ruleDate= ruleDate EOF
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
    // InternalKactors.g:5602:1: ruleDate returns [EObject current=null] : ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) ;
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
            // InternalKactors.g:5608:2: ( ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) )
            // InternalKactors.g:5609:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            {
            // InternalKactors.g:5609:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            // InternalKactors.g:5610:3: ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            {
            // InternalKactors.g:5610:3: ( (lv_year_0_0= RULE_INT ) )
            // InternalKactors.g:5611:4: (lv_year_0_0= RULE_INT )
            {
            // InternalKactors.g:5611:4: (lv_year_0_0= RULE_INT )
            // InternalKactors.g:5612:5: lv_year_0_0= RULE_INT
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

            // InternalKactors.g:5628:3: (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )?
            int alt83=4;
            switch ( input.LA(1) ) {
                case 79:
                    {
                    alt83=1;
                    }
                    break;
                case 80:
                    {
                    alt83=2;
                    }
                    break;
                case 81:
                    {
                    alt83=3;
                    }
                    break;
            }

            switch (alt83) {
                case 1 :
                    // InternalKactors.g:5629:4: otherlv_1= 'AD'
                    {
                    otherlv_1=(Token)match(input,79,FOLLOW_66); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getDateAccess().getADKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5634:4: otherlv_2= 'CE'
                    {
                    otherlv_2=(Token)match(input,80,FOLLOW_66); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getDateAccess().getCEKeyword_1_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKactors.g:5639:4: ( (lv_bc_3_0= 'BC' ) )
                    {
                    // InternalKactors.g:5639:4: ( (lv_bc_3_0= 'BC' ) )
                    // InternalKactors.g:5640:5: (lv_bc_3_0= 'BC' )
                    {
                    // InternalKactors.g:5640:5: (lv_bc_3_0= 'BC' )
                    // InternalKactors.g:5641:6: lv_bc_3_0= 'BC'
                    {
                    lv_bc_3_0=(Token)match(input,81,FOLLOW_66); if (state.failed) return current;
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

            otherlv_4=(Token)match(input,75,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDateAccess().getHyphenMinusKeyword_2());
              		
            }
            // InternalKactors.g:5658:3: ( (lv_month_5_0= RULE_INT ) )
            // InternalKactors.g:5659:4: (lv_month_5_0= RULE_INT )
            {
            // InternalKactors.g:5659:4: (lv_month_5_0= RULE_INT )
            // InternalKactors.g:5660:5: lv_month_5_0= RULE_INT
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

            otherlv_6=(Token)match(input,75,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getDateAccess().getHyphenMinusKeyword_4());
              		
            }
            // InternalKactors.g:5680:3: ( (lv_day_7_0= RULE_INT ) )
            // InternalKactors.g:5681:4: (lv_day_7_0= RULE_INT )
            {
            // InternalKactors.g:5681:4: (lv_day_7_0= RULE_INT )
            // InternalKactors.g:5682:5: lv_day_7_0= RULE_INT
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

            // InternalKactors.g:5698:3: ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==RULE_INT) ) {
                int LA86_1 = input.LA(2);

                if ( (LA86_1==38) ) {
                    alt86=1;
                }
            }
            switch (alt86) {
                case 1 :
                    // InternalKactors.g:5699:4: ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    {
                    // InternalKactors.g:5699:4: ( (lv_hour_8_0= RULE_INT ) )
                    // InternalKactors.g:5700:5: (lv_hour_8_0= RULE_INT )
                    {
                    // InternalKactors.g:5700:5: (lv_hour_8_0= RULE_INT )
                    // InternalKactors.g:5701:6: lv_hour_8_0= RULE_INT
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

                    otherlv_9=(Token)match(input,38,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getDateAccess().getColonKeyword_6_1());
                      			
                    }
                    // InternalKactors.g:5721:4: ( (lv_min_10_0= RULE_INT ) )
                    // InternalKactors.g:5722:5: (lv_min_10_0= RULE_INT )
                    {
                    // InternalKactors.g:5722:5: (lv_min_10_0= RULE_INT )
                    // InternalKactors.g:5723:6: lv_min_10_0= RULE_INT
                    {
                    lv_min_10_0=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
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

                    // InternalKactors.g:5739:4: (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    int alt85=2;
                    int LA85_0 = input.LA(1);

                    if ( (LA85_0==38) ) {
                        alt85=1;
                    }
                    switch (alt85) {
                        case 1 :
                            // InternalKactors.g:5740:5: otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            {
                            otherlv_11=(Token)match(input,38,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_11, grammarAccess.getDateAccess().getColonKeyword_6_3_0());
                              				
                            }
                            // InternalKactors.g:5744:5: ( (lv_sec_12_0= RULE_INT ) )
                            // InternalKactors.g:5745:6: (lv_sec_12_0= RULE_INT )
                            {
                            // InternalKactors.g:5745:6: (lv_sec_12_0= RULE_INT )
                            // InternalKactors.g:5746:7: lv_sec_12_0= RULE_INT
                            {
                            lv_sec_12_0=(Token)match(input,RULE_INT,FOLLOW_69); if (state.failed) return current;
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

                            // InternalKactors.g:5762:5: (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            int alt84=2;
                            int LA84_0 = input.LA(1);

                            if ( (LA84_0==66) ) {
                                alt84=1;
                            }
                            switch (alt84) {
                                case 1 :
                                    // InternalKactors.g:5763:6: otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) )
                                    {
                                    otherlv_13=(Token)match(input,66,FOLLOW_10); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						newLeafNode(otherlv_13, grammarAccess.getDateAccess().getFullStopKeyword_6_3_2_0());
                                      					
                                    }
                                    // InternalKactors.g:5767:6: ( (lv_ms_14_0= RULE_INT ) )
                                    // InternalKactors.g:5768:7: (lv_ms_14_0= RULE_INT )
                                    {
                                    // InternalKactors.g:5768:7: (lv_ms_14_0= RULE_INT )
                                    // InternalKactors.g:5769:8: lv_ms_14_0= RULE_INT
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
    // InternalKactors.g:5792:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKactors.g:5792:48: (iv_rulePathName= rulePathName EOF )
            // InternalKactors.g:5793:2: iv_rulePathName= rulePathName EOF
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
    // InternalKactors.g:5799:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKactors.g:5805:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKactors.g:5806:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKactors.g:5806:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKactors.g:5807:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_69); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:5814:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop87:
            do {
                int alt87=2;
                int LA87_0 = input.LA(1);

                if ( (LA87_0==66) ) {
                    alt87=1;
                }


                switch (alt87) {
            	case 1 :
            	    // InternalKactors.g:5815:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,66,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPathNameAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_69); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop87;
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
    // InternalKactors.g:5832:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKactors.g:5832:44: (iv_rulePath= rulePath EOF )
            // InternalKactors.g:5833:2: iv_rulePath= rulePath EOF
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
    // InternalKactors.g:5839:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token this_UPPERCASE_ID_1=null;
        Token kw=null;
        Token this_LOWERCASE_ID_4=null;
        Token this_UPPERCASE_ID_5=null;


        	enterRule();

        try {
            // InternalKactors.g:5845:2: ( ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) )
            // InternalKactors.g:5846:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            {
            // InternalKactors.g:5846:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            // InternalKactors.g:5847:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            {
            // InternalKactors.g:5847:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID )
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==RULE_LOWERCASE_ID) ) {
                alt88=1;
            }
            else if ( (LA88_0==RULE_UPPERCASE_ID) ) {
                alt88=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }
            switch (alt88) {
                case 1 :
                    // InternalKactors.g:5848:4: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_70); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5856:4: this_UPPERCASE_ID_1= RULE_UPPERCASE_ID
                    {
                    this_UPPERCASE_ID_1=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_70); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_UPPERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_UPPERCASE_ID_1, grammarAccess.getPathAccess().getUPPERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:5864:3: ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( ((LA91_0>=65 && LA91_0<=66)) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // InternalKactors.g:5865:4: (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    {
            	    // InternalKactors.g:5865:4: (kw= '.' | kw= '/' )
            	    int alt89=2;
            	    int LA89_0 = input.LA(1);

            	    if ( (LA89_0==66) ) {
            	        alt89=1;
            	    }
            	    else if ( (LA89_0==65) ) {
            	        alt89=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 89, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt89) {
            	        case 1 :
            	            // InternalKactors.g:5866:5: kw= '.'
            	            {
            	            kw=(Token)match(input,66,FOLLOW_40); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:5872:5: kw= '/'
            	            {
            	            kw=(Token)match(input,65,FOLLOW_40); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getSolidusKeyword_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalKactors.g:5878:4: (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    int alt90=2;
            	    int LA90_0 = input.LA(1);

            	    if ( (LA90_0==RULE_LOWERCASE_ID) ) {
            	        alt90=1;
            	    }
            	    else if ( (LA90_0==RULE_UPPERCASE_ID) ) {
            	        alt90=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 90, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt90) {
            	        case 1 :
            	            // InternalKactors.g:5879:5: this_LOWERCASE_ID_4= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_4=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_70); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_4, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:5887:5: this_UPPERCASE_ID_5= RULE_UPPERCASE_ID
            	            {
            	            this_UPPERCASE_ID_5=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_70); if (state.failed) return current;
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
            	    break loop91;
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
    // InternalKactors.g:5900:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKactors.g:5900:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKactors.g:5901:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalKactors.g:5907:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
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
            // InternalKactors.g:5913:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKactors.g:5914:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKactors.g:5914:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKactors.g:5915:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_71); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:5922:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==66) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // InternalKactors.g:5923:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,66,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_71); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKactors.g:5935:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt92=2;
                    int LA92_0 = input.LA(1);

                    if ( (LA92_0==66) ) {
                        alt92=1;
                    }
                    switch (alt92) {
                        case 1 :
                            // InternalKactors.g:5936:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,66,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_72); if (state.failed) return current;
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

            // InternalKactors.g:5950:3: (kw= '-' )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==75) ) {
                int LA94_1 = input.LA(2);

                if ( (synpred194_InternalKactors()) ) {
                    alt94=1;
                }
            }
            switch (alt94) {
                case 1 :
                    // InternalKactors.g:5951:4: kw= '-'
                    {
                    kw=(Token)match(input,75,FOLLOW_73); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:5957:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt95=3;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==RULE_LOWERCASE_ID) ) {
                int LA95_1 = input.LA(2);

                if ( (synpred195_InternalKactors()) ) {
                    alt95=1;
                }
            }
            else if ( (LA95_0==RULE_UPPERCASE_ID) ) {
                alt95=2;
            }
            switch (alt95) {
                case 1 :
                    // InternalKactors.g:5958:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:5966:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
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
    // InternalKactors.g:5978:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKactors.g:5984:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKactors.g:5985:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKactors.g:5985:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt96=3;
            switch ( input.LA(1) ) {
            case 65:
                {
                alt96=1;
                }
                break;
            case 82:
                {
                alt96=2;
                }
                break;
            case 55:
                {
                alt96=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 96, 0, input);

                throw nvae;
            }

            switch (alt96) {
                case 1 :
                    // InternalKactors.g:5986:3: (enumLiteral_0= '/' )
                    {
                    // InternalKactors.g:5986:3: (enumLiteral_0= '/' )
                    // InternalKactors.g:5987:4: enumLiteral_0= '/'
                    {
                    enumLiteral_0=(Token)match(input,65,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:5994:3: (enumLiteral_1= '^' )
                    {
                    // InternalKactors.g:5994:3: (enumLiteral_1= '^' )
                    // InternalKactors.g:5995:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,82,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:6002:3: (enumLiteral_2= '*' )
                    {
                    // InternalKactors.g:6002:3: (enumLiteral_2= '*' )
                    // InternalKactors.g:6003:4: enumLiteral_2= '*'
                    {
                    enumLiteral_2=(Token)match(input,55,FOLLOW_2); if (state.failed) return current;
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

    // $ANTLR start synpred8_InternalKactors
    public final void synpred8_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_imports_8_0 = null;

        AntlrDatatypeRuleToken lv_imports_10_0 = null;


        // InternalKactors.g:263:4: ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) )
        // InternalKactors.g:263:4: ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) )
        {
        // InternalKactors.g:263:4: ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) )
        // InternalKactors.g:264:5: {...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred8_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
        }
        // InternalKactors.g:264:105: ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) )
        // InternalKactors.g:265:6: ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
        // InternalKactors.g:268:9: ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) )
        // InternalKactors.g:268:10: {...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred8_InternalKactors", "true");
        }
        // InternalKactors.g:268:19: (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* )
        // InternalKactors.g:268:20: otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )*
        {
        otherlv_7=(Token)match(input,26,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:272:9: ( (lv_imports_8_0= rulePathName ) )
        // InternalKactors.g:273:10: (lv_imports_8_0= rulePathName )
        {
        // InternalKactors.g:273:10: (lv_imports_8_0= rulePathName )
        // InternalKactors.g:274:11: lv_imports_8_0= rulePathName
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

        // InternalKactors.g:291:9: (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )*
        loop98:
        do {
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==27) ) {
                alt98=1;
            }


            switch (alt98) {
        	case 1 :
        	    // InternalKactors.g:292:10: otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) )
        	    {
        	    otherlv_9=(Token)match(input,27,FOLLOW_4); if (state.failed) return ;
        	    // InternalKactors.g:296:10: ( (lv_imports_10_0= rulePathName ) )
        	    // InternalKactors.g:297:11: (lv_imports_10_0= rulePathName )
        	    {
        	    // InternalKactors.g:297:11: (lv_imports_10_0= rulePathName )
        	    // InternalKactors.g:298:12: lv_imports_10_0= rulePathName
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
        	    break loop98;
            }
        } while (true);


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred8_InternalKactors

    // $ANTLR start synpred9_InternalKactors
    public final void synpred9_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_11=null;
        AntlrDatatypeRuleToken lv_worldview_12_0 = null;


        // InternalKactors.g:322:4: ( ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) )
        // InternalKactors.g:322:4: ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) )
        {
        // InternalKactors.g:322:4: ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) )
        // InternalKactors.g:323:5: {...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred9_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
        }
        // InternalKactors.g:323:105: ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) )
        // InternalKactors.g:324:6: ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
        // InternalKactors.g:327:9: ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) )
        // InternalKactors.g:327:10: {...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred9_InternalKactors", "true");
        }
        // InternalKactors.g:327:19: (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) )
        // InternalKactors.g:327:20: otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) )
        {
        otherlv_11=(Token)match(input,28,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:331:9: ( (lv_worldview_12_0= rulePathName ) )
        // InternalKactors.g:332:10: (lv_worldview_12_0= rulePathName )
        {
        // InternalKactors.g:332:10: (lv_worldview_12_0= rulePathName )
        // InternalKactors.g:333:11: lv_worldview_12_0= rulePathName
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
    // $ANTLR end synpred9_InternalKactors

    // $ANTLR start synpred11_InternalKactors
    public final void synpred11_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_13=null;
        Token lv_observable_14_0=null;
        EObject lv_observables_15_0 = null;


        // InternalKactors.g:356:4: ( ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) )
        // InternalKactors.g:356:4: ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) )
        {
        // InternalKactors.g:356:4: ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) )
        // InternalKactors.g:357:5: {...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred11_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
        }
        // InternalKactors.g:357:105: ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) )
        // InternalKactors.g:358:6: ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
        // InternalKactors.g:361:9: ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) )
        // InternalKactors.g:361:10: {...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred11_InternalKactors", "true");
        }
        // InternalKactors.g:361:19: (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) )
        // InternalKactors.g:361:20: otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) )
        {
        otherlv_13=(Token)match(input,29,FOLLOW_7); if (state.failed) return ;
        // InternalKactors.g:365:9: ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) )
        int alt99=2;
        int LA99_0 = input.LA(1);

        if ( (LA99_0==RULE_OBSERVABLE) ) {
            alt99=1;
        }
        else if ( (LA99_0==39) ) {
            alt99=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 99, 0, input);

            throw nvae;
        }
        switch (alt99) {
            case 1 :
                // InternalKactors.g:366:10: ( (lv_observable_14_0= RULE_OBSERVABLE ) )
                {
                // InternalKactors.g:366:10: ( (lv_observable_14_0= RULE_OBSERVABLE ) )
                // InternalKactors.g:367:11: (lv_observable_14_0= RULE_OBSERVABLE )
                {
                // InternalKactors.g:367:11: (lv_observable_14_0= RULE_OBSERVABLE )
                // InternalKactors.g:368:12: lv_observable_14_0= RULE_OBSERVABLE
                {
                lv_observable_14_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:385:10: ( (lv_observables_15_0= ruleList ) )
                {
                // InternalKactors.g:385:10: ( (lv_observables_15_0= ruleList ) )
                // InternalKactors.g:386:11: (lv_observables_15_0= ruleList )
                {
                // InternalKactors.g:386:11: (lv_observables_15_0= ruleList )
                // InternalKactors.g:387:12: lv_observables_15_0= ruleList
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
    // $ANTLR end synpred11_InternalKactors

    // $ANTLR start synpred14_InternalKactors
    public final void synpred14_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        Token lv_label_17_1=null;
        Token lv_label_17_2=null;
        Token lv_label_17_3=null;

        // InternalKactors.g:411:4: ( ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) )
        // InternalKactors.g:411:4: ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) )
        {
        // InternalKactors.g:411:4: ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:412:5: {...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred14_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
        }
        // InternalKactors.g:412:105: ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:413:6: ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
        // InternalKactors.g:416:9: ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) )
        // InternalKactors.g:416:10: {...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred14_InternalKactors", "true");
        }
        // InternalKactors.g:416:19: (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) )
        // InternalKactors.g:416:20: otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) )
        {
        otherlv_16=(Token)match(input,30,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:420:9: ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) )
        // InternalKactors.g:421:10: ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) )
        {
        // InternalKactors.g:421:10: ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) )
        // InternalKactors.g:422:11: (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING )
        {
        // InternalKactors.g:422:11: (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING )
        int alt100=3;
        switch ( input.LA(1) ) {
        case RULE_LOWERCASE_ID:
            {
            alt100=1;
            }
            break;
        case RULE_ID:
            {
            alt100=2;
            }
            break;
        case RULE_STRING:
            {
            alt100=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 100, 0, input);

            throw nvae;
        }

        switch (alt100) {
            case 1 :
                // InternalKactors.g:423:12: lv_label_17_1= RULE_LOWERCASE_ID
                {
                lv_label_17_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:438:12: lv_label_17_2= RULE_ID
                {
                lv_label_17_2=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 3 :
                // InternalKactors.g:453:12: lv_label_17_3= RULE_STRING
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
    // $ANTLR end synpred14_InternalKactors

    // $ANTLR start synpred15_InternalKactors
    public final void synpred15_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_18=null;
        Token lv_description_19_0=null;

        // InternalKactors.g:476:4: ( ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:476:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:476:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:477:5: {...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred15_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4)");
        }
        // InternalKactors.g:477:105: ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:478:6: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4);
        // InternalKactors.g:481:9: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
        // InternalKactors.g:481:10: {...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred15_InternalKactors", "true");
        }
        // InternalKactors.g:481:19: (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
        // InternalKactors.g:481:20: otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) )
        {
        otherlv_18=(Token)match(input,31,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:485:9: ( (lv_description_19_0= RULE_STRING ) )
        // InternalKactors.g:486:10: (lv_description_19_0= RULE_STRING )
        {
        // InternalKactors.g:486:10: (lv_description_19_0= RULE_STRING )
        // InternalKactors.g:487:11: lv_description_19_0= RULE_STRING
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
    // $ANTLR end synpred15_InternalKactors

    // $ANTLR start synpred16_InternalKactors
    public final void synpred16_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_20=null;
        Token lv_permissions_21_0=null;

        // InternalKactors.g:509:4: ( ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:509:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:509:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:510:5: {...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred16_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5)");
        }
        // InternalKactors.g:510:105: ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:511:6: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5);
        // InternalKactors.g:514:9: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
        // InternalKactors.g:514:10: {...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred16_InternalKactors", "true");
        }
        // InternalKactors.g:514:19: (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
        // InternalKactors.g:514:20: otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) )
        {
        otherlv_20=(Token)match(input,32,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:518:9: ( (lv_permissions_21_0= RULE_STRING ) )
        // InternalKactors.g:519:10: (lv_permissions_21_0= RULE_STRING )
        {
        // InternalKactors.g:519:10: (lv_permissions_21_0= RULE_STRING )
        // InternalKactors.g:520:11: lv_permissions_21_0= RULE_STRING
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
    // $ANTLR end synpred16_InternalKactors

    // $ANTLR start synpred17_InternalKactors
    public final void synpred17_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_22=null;
        Token lv_authors_23_0=null;

        // InternalKactors.g:547:10: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )
        // InternalKactors.g:547:10: {...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred17_InternalKactors", "true");
        }
        // InternalKactors.g:547:19: (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        // InternalKactors.g:547:20: otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) )
        {
        otherlv_22=(Token)match(input,33,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:551:9: ( (lv_authors_23_0= RULE_STRING ) )
        // InternalKactors.g:552:10: (lv_authors_23_0= RULE_STRING )
        {
        // InternalKactors.g:552:10: (lv_authors_23_0= RULE_STRING )
        // InternalKactors.g:553:11: lv_authors_23_0= RULE_STRING
        {
        lv_authors_23_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred17_InternalKactors

    // $ANTLR start synpred18_InternalKactors
    public final void synpred18_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_22=null;
        Token lv_authors_23_0=null;

        // InternalKactors.g:542:4: ( ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) )
        // InternalKactors.g:542:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
        {
        // InternalKactors.g:542:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
        // InternalKactors.g:543:5: {...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6)");
        }
        // InternalKactors.g:543:105: ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
        // InternalKactors.g:544:6: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6);
        // InternalKactors.g:547:9: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
        int cnt101=0;
        loop101:
        do {
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==33) && ((true))) {
                alt101=1;
            }


            switch (alt101) {
        	case 1 :
        	    // InternalKactors.g:547:10: {...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        	    {
        	    if ( !((true)) ) {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        throw new FailedPredicateException(input, "synpred18_InternalKactors", "true");
        	    }
        	    // InternalKactors.g:547:19: (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        	    // InternalKactors.g:547:20: otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) )
        	    {
        	    otherlv_22=(Token)match(input,33,FOLLOW_9); if (state.failed) return ;
        	    // InternalKactors.g:551:9: ( (lv_authors_23_0= RULE_STRING ) )
        	    // InternalKactors.g:552:10: (lv_authors_23_0= RULE_STRING )
        	    {
        	    // InternalKactors.g:552:10: (lv_authors_23_0= RULE_STRING )
        	    // InternalKactors.g:553:11: lv_authors_23_0= RULE_STRING
        	    {
        	    lv_authors_23_0=(Token)match(input,RULE_STRING,FOLLOW_74); if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt101 >= 1 ) break loop101;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(101, input);
                    throw eee;
            }
            cnt101++;
        } while (true);


        }


        }


        }
    }
    // $ANTLR end synpred18_InternalKactors

    // $ANTLR start synpred19_InternalKactors
    public final void synpred19_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_24=null;
        AntlrDatatypeRuleToken lv_version_25_0 = null;


        // InternalKactors.g:575:4: ( ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) )
        // InternalKactors.g:575:4: ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) )
        {
        // InternalKactors.g:575:4: ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) )
        // InternalKactors.g:576:5: {...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred19_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7)");
        }
        // InternalKactors.g:576:105: ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) )
        // InternalKactors.g:577:6: ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7);
        // InternalKactors.g:580:9: ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) )
        // InternalKactors.g:580:10: {...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred19_InternalKactors", "true");
        }
        // InternalKactors.g:580:19: (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) )
        // InternalKactors.g:580:20: otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) )
        {
        otherlv_24=(Token)match(input,34,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:584:9: ( (lv_version_25_0= ruleVersionNumber ) )
        // InternalKactors.g:585:10: (lv_version_25_0= ruleVersionNumber )
        {
        // InternalKactors.g:585:10: (lv_version_25_0= ruleVersionNumber )
        // InternalKactors.g:586:11: lv_version_25_0= ruleVersionNumber
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
    // $ANTLR end synpred19_InternalKactors

    // $ANTLR start synpred21_InternalKactors
    public final void synpred21_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_26=null;
        Token lv_createcomment_28_0=null;
        EObject lv_created_27_0 = null;


        // InternalKactors.g:609:4: ( ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:609:4: ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:609:4: ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:610:5: {...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred21_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8)");
        }
        // InternalKactors.g:610:105: ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:611:6: ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8);
        // InternalKactors.g:614:9: ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) )
        // InternalKactors.g:614:10: {...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred21_InternalKactors", "true");
        }
        // InternalKactors.g:614:19: (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? )
        // InternalKactors.g:614:20: otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )?
        {
        otherlv_26=(Token)match(input,35,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:618:9: ( (lv_created_27_0= ruleDate ) )
        // InternalKactors.g:619:10: (lv_created_27_0= ruleDate )
        {
        // InternalKactors.g:619:10: (lv_created_27_0= ruleDate )
        // InternalKactors.g:620:11: lv_created_27_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_2_8_1_0());
          										
        }
        pushFollow(FOLLOW_75);
        lv_created_27_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:637:9: ( (lv_createcomment_28_0= RULE_STRING ) )?
        int alt102=2;
        int LA102_0 = input.LA(1);

        if ( (LA102_0==RULE_STRING) ) {
            alt102=1;
        }
        switch (alt102) {
            case 1 :
                // InternalKactors.g:638:10: (lv_createcomment_28_0= RULE_STRING )
                {
                // InternalKactors.g:638:10: (lv_createcomment_28_0= RULE_STRING )
                // InternalKactors.g:639:11: lv_createcomment_28_0= RULE_STRING
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
    // $ANTLR end synpred21_InternalKactors

    // $ANTLR start synpred23_InternalKactors
    public final void synpred23_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_29=null;
        Token lv_modcomment_31_0=null;
        EObject lv_modified_30_0 = null;


        // InternalKactors.g:661:4: ( ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:661:4: ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:661:4: ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:662:5: {...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred23_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9)");
        }
        // InternalKactors.g:662:105: ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:663:6: ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9);
        // InternalKactors.g:666:9: ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) )
        // InternalKactors.g:666:10: {...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred23_InternalKactors", "true");
        }
        // InternalKactors.g:666:19: (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? )
        // InternalKactors.g:666:20: otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )?
        {
        otherlv_29=(Token)match(input,36,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:670:9: ( (lv_modified_30_0= ruleDate ) )
        // InternalKactors.g:671:10: (lv_modified_30_0= ruleDate )
        {
        // InternalKactors.g:671:10: (lv_modified_30_0= ruleDate )
        // InternalKactors.g:672:11: lv_modified_30_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_2_9_1_0());
          										
        }
        pushFollow(FOLLOW_75);
        lv_modified_30_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:689:9: ( (lv_modcomment_31_0= RULE_STRING ) )?
        int alt103=2;
        int LA103_0 = input.LA(1);

        if ( (LA103_0==RULE_STRING) ) {
            alt103=1;
        }
        switch (alt103) {
            case 1 :
                // InternalKactors.g:690:10: (lv_modcomment_31_0= RULE_STRING )
                {
                // InternalKactors.g:690:10: (lv_modcomment_31_0= RULE_STRING )
                // InternalKactors.g:691:11: lv_modcomment_31_0= RULE_STRING
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
    // $ANTLR end synpred23_InternalKactors

    // $ANTLR start synpred30_InternalKactors
    public final void synpred30_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;


        // InternalKactors.g:995:6: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )
        // InternalKactors.g:995:6: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
        {
        otherlv_1=(Token)match(input,39,FOLLOW_20); if (state.failed) return ;
        // InternalKactors.g:999:6: ( (lv_parameters_2_0= ruleParameterList ) )?
        int alt105=2;
        int LA105_0 = input.LA(1);

        if ( ((LA105_0>=RULE_OBSERVABLE && LA105_0<=RULE_LOWERCASE_ID)||LA105_0==RULE_STRING||LA105_0==RULE_EXPR||LA105_0==RULE_INT||LA105_0==RULE_ARGVALUE||LA105_0==39||(LA105_0>=49 && LA105_0<=50)||LA105_0==57||LA105_0==60||LA105_0==62||(LA105_0>=74 && LA105_0<=75)) ) {
            alt105=1;
        }
        switch (alt105) {
            case 1 :
                // InternalKactors.g:1000:7: (lv_parameters_2_0= ruleParameterList )
                {
                // InternalKactors.g:1000:7: (lv_parameters_2_0= ruleParameterList )
                // InternalKactors.g:1001:8: lv_parameters_2_0= ruleParameterList
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

        otherlv_3=(Token)match(input,40,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred30_InternalKactors

    // $ANTLR start synpred34_InternalKactors
    public final void synpred34_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_body_2_0 = null;


        // InternalKactors.g:1103:4: ( (lv_body_2_0= ruleMessageBody ) )
        // InternalKactors.g:1103:4: (lv_body_2_0= ruleMessageBody )
        {
        // InternalKactors.g:1103:4: (lv_body_2_0= ruleMessageBody )
        // InternalKactors.g:1104:5: lv_body_2_0= ruleMessageBody
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
    // $ANTLR end synpred34_InternalKactors

    // $ANTLR start synpred35_InternalKactors
    public final void synpred35_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_next_1_0 = null;


        // InternalKactors.g:1164:4: ( (lv_next_1_0= ruleNextStatement ) )
        // InternalKactors.g:1164:4: (lv_next_1_0= ruleNextStatement )
        {
        // InternalKactors.g:1164:4: (lv_next_1_0= ruleNextStatement )
        // InternalKactors.g:1165:5: lv_next_1_0= ruleNextStatement
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
    // $ANTLR end synpred35_InternalKactors

    // $ANTLR start synpred36_InternalKactors
    public final void synpred36_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_0_0 = null;


        // InternalKactors.g:1201:3: ( ( (lv_verb_0_0= ruleMessageCall ) ) )
        // InternalKactors.g:1201:3: ( (lv_verb_0_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1201:3: ( (lv_verb_0_0= ruleMessageCall ) )
        // InternalKactors.g:1202:4: (lv_verb_0_0= ruleMessageCall )
        {
        // InternalKactors.g:1202:4: (lv_verb_0_0= ruleMessageCall )
        // InternalKactors.g:1203:5: lv_verb_0_0= ruleMessageCall
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
    // $ANTLR end synpred36_InternalKactors

    // $ANTLR start synpred37_InternalKactors
    public final void synpred37_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_group_1_0 = null;


        // InternalKactors.g:1221:3: ( ( (lv_group_1_0= ruleStatementGroup ) ) )
        // InternalKactors.g:1221:3: ( (lv_group_1_0= ruleStatementGroup ) )
        {
        // InternalKactors.g:1221:3: ( (lv_group_1_0= ruleStatementGroup ) )
        // InternalKactors.g:1222:4: (lv_group_1_0= ruleStatementGroup )
        {
        // InternalKactors.g:1222:4: (lv_group_1_0= ruleStatementGroup )
        // InternalKactors.g:1223:5: lv_group_1_0= ruleStatementGroup
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
    // $ANTLR end synpred37_InternalKactors

    // $ANTLR start synpred43_InternalKactors
    public final void synpred43_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_1_0 = null;


        // InternalKactors.g:1383:4: ( ( (lv_verb_1_0= ruleMessageCall ) ) )
        // InternalKactors.g:1383:4: ( (lv_verb_1_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1383:4: ( (lv_verb_1_0= ruleMessageCall ) )
        // InternalKactors.g:1384:5: (lv_verb_1_0= ruleMessageCall )
        {
        // InternalKactors.g:1384:5: (lv_verb_1_0= ruleMessageCall )
        // InternalKactors.g:1385:6: lv_verb_1_0= ruleMessageCall
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
    // $ANTLR end synpred43_InternalKactors

    // $ANTLR start synpred44_InternalKactors
    public final void synpred44_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_group_2_0 = null;


        // InternalKactors.g:1403:4: ( ( (lv_group_2_0= ruleStatementGroup ) ) )
        // InternalKactors.g:1403:4: ( (lv_group_2_0= ruleStatementGroup ) )
        {
        // InternalKactors.g:1403:4: ( (lv_group_2_0= ruleStatementGroup ) )
        // InternalKactors.g:1404:5: (lv_group_2_0= ruleStatementGroup )
        {
        // InternalKactors.g:1404:5: (lv_group_2_0= ruleStatementGroup )
        // InternalKactors.g:1405:6: lv_group_2_0= ruleStatementGroup
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
    // $ANTLR end synpred44_InternalKactors

    // $ANTLR start synpred50_InternalKactors
    public final void synpred50_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_elseIfExpression_5_0=null;
        EObject lv_elseIfBody_6_0 = null;


        // InternalKactors.g:1603:4: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )
        // InternalKactors.g:1603:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) )
        {
        otherlv_3=(Token)match(input,43,FOLLOW_27); if (state.failed) return ;
        otherlv_4=(Token)match(input,42,FOLLOW_25); if (state.failed) return ;
        // InternalKactors.g:1611:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
        // InternalKactors.g:1612:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        {
        // InternalKactors.g:1612:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        // InternalKactors.g:1613:6: lv_elseIfExpression_5_0= RULE_EXPR
        {
        lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_15); if (state.failed) return ;

        }


        }

        // InternalKactors.g:1629:4: ( (lv_elseIfBody_6_0= ruleStatementBody ) )
        // InternalKactors.g:1630:5: (lv_elseIfBody_6_0= ruleStatementBody )
        {
        // InternalKactors.g:1630:5: (lv_elseIfBody_6_0= ruleStatementBody )
        // InternalKactors.g:1631:6: lv_elseIfBody_6_0= ruleStatementBody
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
    // $ANTLR end synpred50_InternalKactors

    // $ANTLR start synpred51_InternalKactors
    public final void synpred51_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        EObject lv_elseCall_8_0 = null;


        // InternalKactors.g:1650:4: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )
        // InternalKactors.g:1650:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) )
        {
        otherlv_7=(Token)match(input,43,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:1654:4: ( (lv_elseCall_8_0= ruleStatementBody ) )
        // InternalKactors.g:1655:5: (lv_elseCall_8_0= ruleStatementBody )
        {
        // InternalKactors.g:1655:5: (lv_elseCall_8_0= ruleStatementBody )
        // InternalKactors.g:1656:6: lv_elseCall_8_0= ruleStatementBody
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
    // $ANTLR end synpred51_InternalKactors

    // $ANTLR start synpred52_InternalKactors
    public final void synpred52_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_0_0 = null;


        // InternalKactors.g:1693:3: ( ( (lv_verb_0_0= ruleMessageCall ) ) )
        // InternalKactors.g:1693:3: ( (lv_verb_0_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1693:3: ( (lv_verb_0_0= ruleMessageCall ) )
        // InternalKactors.g:1694:4: (lv_verb_0_0= ruleMessageCall )
        {
        // InternalKactors.g:1694:4: (lv_verb_0_0= ruleMessageCall )
        // InternalKactors.g:1695:5: lv_verb_0_0= ruleMessageCall
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
    // $ANTLR end synpred52_InternalKactors

    // $ANTLR start synpred53_InternalKactors
    public final void synpred53_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_value_1_0 = null;


        // InternalKactors.g:1713:3: ( ( (lv_value_1_0= ruleValue ) ) )
        // InternalKactors.g:1713:3: ( (lv_value_1_0= ruleValue ) )
        {
        // InternalKactors.g:1713:3: ( (lv_value_1_0= ruleValue ) )
        // InternalKactors.g:1714:4: (lv_value_1_0= ruleValue )
        {
        // InternalKactors.g:1714:4: (lv_value_1_0= ruleValue )
        // InternalKactors.g:1715:5: lv_value_1_0= ruleValue
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
    // $ANTLR end synpred53_InternalKactors

    // $ANTLR start synpred55_InternalKactors
    public final void synpred55_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_match_0_0 = null;


        // InternalKactors.g:1980:3: ( ( (lv_match_0_0= ruleMatch ) ) )
        // InternalKactors.g:1980:3: ( (lv_match_0_0= ruleMatch ) )
        {
        // InternalKactors.g:1980:3: ( (lv_match_0_0= ruleMatch ) )
        // InternalKactors.g:1981:4: (lv_match_0_0= ruleMatch )
        {
        // InternalKactors.g:1981:4: (lv_match_0_0= ruleMatch )
        // InternalKactors.g:1982:5: lv_match_0_0= ruleMatch
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
    // $ANTLR end synpred55_InternalKactors

    // $ANTLR start synpred57_InternalKactors
    public final void synpred57_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_4=null;
        EObject lv_matches_2_0 = null;

        EObject lv_matches_3_0 = null;


        // InternalKactors.g:2000:3: ( (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) )
        // InternalKactors.g:2000:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
        {
        // InternalKactors.g:2000:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
        // InternalKactors.g:2001:4: otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')'
        {
        otherlv_1=(Token)match(input,39,FOLLOW_30); if (state.failed) return ;
        // InternalKactors.g:2005:4: ( (lv_matches_2_0= ruleMatch ) )
        // InternalKactors.g:2006:5: (lv_matches_2_0= ruleMatch )
        {
        // InternalKactors.g:2006:5: (lv_matches_2_0= ruleMatch )
        // InternalKactors.g:2007:6: lv_matches_2_0= ruleMatch
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

        // InternalKactors.g:2024:4: ( (lv_matches_3_0= ruleMatch ) )*
        loop108:
        do {
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( ((LA108_0>=RULE_OBSERVABLE && LA108_0<=RULE_LOWERCASE_ID)||LA108_0==RULE_STRING||(LA108_0>=RULE_EXPR && LA108_0<=RULE_INT)||LA108_0==39||LA108_0==47||(LA108_0>=49 && LA108_0<=50)||(LA108_0>=54 && LA108_0<=56)||(LA108_0>=74 && LA108_0<=75)) ) {
                alt108=1;
            }


            switch (alt108) {
        	case 1 :
        	    // InternalKactors.g:2025:5: (lv_matches_3_0= ruleMatch )
        	    {
        	    // InternalKactors.g:2025:5: (lv_matches_3_0= ruleMatch )
        	    // InternalKactors.g:2026:6: lv_matches_3_0= ruleMatch
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
        	    break loop108;
            }
        } while (true);

        otherlv_4=(Token)match(input,40,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred57_InternalKactors

    // $ANTLR start synpred58_InternalKactors
    public final void synpred58_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_statement_5_0 = null;


        // InternalKactors.g:2049:3: ( ( (lv_statement_5_0= ruleStatement ) ) )
        // InternalKactors.g:2049:3: ( (lv_statement_5_0= ruleStatement ) )
        {
        // InternalKactors.g:2049:3: ( (lv_statement_5_0= ruleStatement ) )
        // InternalKactors.g:2050:4: (lv_statement_5_0= ruleStatement )
        {
        // InternalKactors.g:2050:4: (lv_statement_5_0= ruleStatement )
        // InternalKactors.g:2051:5: lv_statement_5_0= ruleStatement
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
    // $ANTLR end synpred58_InternalKactors

    // $ANTLR start synpred61_InternalKactors
    public final void synpred61_InternalKactors_fragment() throws RecognitionException {   
        Token lv_boolean_3_1=null;
        Token lv_boolean_3_2=null;
        Token otherlv_4=null;
        EObject lv_body_5_0 = null;


        // InternalKactors.g:2161:3: ( ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2161:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2161:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
        // InternalKactors.g:2162:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) )
        {
        // InternalKactors.g:2162:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) )
        // InternalKactors.g:2163:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
        {
        // InternalKactors.g:2163:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
        // InternalKactors.g:2164:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
        {
        // InternalKactors.g:2164:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
        int alt109=2;
        int LA109_0 = input.LA(1);

        if ( (LA109_0==49) ) {
            alt109=1;
        }
        else if ( (LA109_0==50) ) {
            alt109=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 109, 0, input);

            throw nvae;
        }
        switch (alt109) {
            case 1 :
                // InternalKactors.g:2165:7: lv_boolean_3_1= 'true'
                {
                lv_boolean_3_1=(Token)match(input,49,FOLLOW_32); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:2176:7: lv_boolean_3_2= 'false'
                {
                lv_boolean_3_2=(Token)match(input,50,FOLLOW_32); if (state.failed) return ;

                }
                break;

        }


        }


        }

        otherlv_4=(Token)match(input,48,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2193:4: ( (lv_body_5_0= ruleStatementList ) )
        // InternalKactors.g:2194:5: (lv_body_5_0= ruleStatementList )
        {
        // InternalKactors.g:2194:5: (lv_body_5_0= ruleStatementList )
        // InternalKactors.g:2195:6: lv_body_5_0= ruleStatementList
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
    // $ANTLR end synpred61_InternalKactors

    // $ANTLR start synpred65_InternalKactors
    public final void synpred65_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        EObject lv_literal_15_0 = null;

        EObject lv_body_17_0 = null;


        // InternalKactors.g:2346:3: ( ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2346:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2346:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
        // InternalKactors.g:2347:4: ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) )
        {
        // InternalKactors.g:2347:4: ( (lv_literal_15_0= ruleLiteral ) )
        // InternalKactors.g:2348:5: (lv_literal_15_0= ruleLiteral )
        {
        // InternalKactors.g:2348:5: (lv_literal_15_0= ruleLiteral )
        // InternalKactors.g:2349:6: lv_literal_15_0= ruleLiteral
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

        otherlv_16=(Token)match(input,48,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2370:4: ( (lv_body_17_0= ruleStatementList ) )
        // InternalKactors.g:2371:5: (lv_body_17_0= ruleStatementList )
        {
        // InternalKactors.g:2371:5: (lv_body_17_0= ruleStatementList )
        // InternalKactors.g:2372:6: lv_body_17_0= ruleStatementList
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
    // $ANTLR end synpred65_InternalKactors

    // $ANTLR start synpred66_InternalKactors
    public final void synpred66_InternalKactors_fragment() throws RecognitionException {   
        Token lv_text_18_0=null;
        Token otherlv_19=null;
        EObject lv_body_20_0 = null;


        // InternalKactors.g:2391:3: ( ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2391:3: ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2391:3: ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
        // InternalKactors.g:2392:4: ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) )
        {
        // InternalKactors.g:2392:4: ( (lv_text_18_0= RULE_STRING ) )
        // InternalKactors.g:2393:5: (lv_text_18_0= RULE_STRING )
        {
        // InternalKactors.g:2393:5: (lv_text_18_0= RULE_STRING )
        // InternalKactors.g:2394:6: lv_text_18_0= RULE_STRING
        {
        lv_text_18_0=(Token)match(input,RULE_STRING,FOLLOW_32); if (state.failed) return ;

        }


        }

        otherlv_19=(Token)match(input,48,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2414:4: ( (lv_body_20_0= ruleStatementList ) )
        // InternalKactors.g:2415:5: (lv_body_20_0= ruleStatementList )
        {
        // InternalKactors.g:2415:5: (lv_body_20_0= ruleStatementList )
        // InternalKactors.g:2416:6: lv_body_20_0= ruleStatementList
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
    // $ANTLR end synpred66_InternalKactors

    // $ANTLR start synpred70_InternalKactors
    public final void synpred70_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:2522:5: ( 'to' )
        // InternalKactors.g:2522:6: 'to'
        {
        match(input,53,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred70_InternalKactors

    // $ANTLR start synpred74_InternalKactors
    public final void synpred74_InternalKactors_fragment() throws RecognitionException {   
        Token lv_leftLimit_25_0=null;
        Token otherlv_26=null;
        Token otherlv_27=null;
        Token lv_rightLimit_29_0=null;
        Token otherlv_30=null;
        Token otherlv_31=null;
        EObject lv_int0_24_0 = null;

        EObject lv_int1_28_0 = null;

        EObject lv_body_32_0 = null;


        // InternalKactors.g:2480:3: ( ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2480:3: ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2480:3: ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) )
        // InternalKactors.g:2481:4: ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) )
        {
        // InternalKactors.g:2481:4: ( (lv_int0_24_0= ruleNumber ) )
        // InternalKactors.g:2482:5: (lv_int0_24_0= ruleNumber )
        {
        // InternalKactors.g:2482:5: (lv_int0_24_0= ruleNumber )
        // InternalKactors.g:2483:6: lv_int0_24_0= ruleNumber
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

        // InternalKactors.g:2500:4: ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )?
        int alt110=3;
        int LA110_0 = input.LA(1);

        if ( (LA110_0==51) ) {
            alt110=1;
        }
        else if ( (LA110_0==52) ) {
            alt110=2;
        }
        switch (alt110) {
            case 1 :
                // InternalKactors.g:2501:5: ( (lv_leftLimit_25_0= 'inclusive' ) )
                {
                // InternalKactors.g:2501:5: ( (lv_leftLimit_25_0= 'inclusive' ) )
                // InternalKactors.g:2502:6: (lv_leftLimit_25_0= 'inclusive' )
                {
                // InternalKactors.g:2502:6: (lv_leftLimit_25_0= 'inclusive' )
                // InternalKactors.g:2503:7: lv_leftLimit_25_0= 'inclusive'
                {
                lv_leftLimit_25_0=(Token)match(input,51,FOLLOW_34); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:2516:5: otherlv_26= 'exclusive'
                {
                otherlv_26=(Token)match(input,52,FOLLOW_34); if (state.failed) return ;

                }
                break;

        }

        // InternalKactors.g:2521:4: ( ( 'to' )=>otherlv_27= 'to' )
        // InternalKactors.g:2522:5: ( 'to' )=>otherlv_27= 'to'
        {
        otherlv_27=(Token)match(input,53,FOLLOW_35); if (state.failed) return ;

        }

        // InternalKactors.g:2528:4: ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) )
        // InternalKactors.g:2529:5: ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber )
        {
        // InternalKactors.g:2533:5: (lv_int1_28_0= ruleNumber )
        // InternalKactors.g:2534:6: lv_int1_28_0= ruleNumber
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

        // InternalKactors.g:2551:4: ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )?
        int alt111=3;
        int LA111_0 = input.LA(1);

        if ( (LA111_0==51) ) {
            alt111=1;
        }
        else if ( (LA111_0==52) ) {
            alt111=2;
        }
        switch (alt111) {
            case 1 :
                // InternalKactors.g:2552:5: ( (lv_rightLimit_29_0= 'inclusive' ) )
                {
                // InternalKactors.g:2552:5: ( (lv_rightLimit_29_0= 'inclusive' ) )
                // InternalKactors.g:2553:6: (lv_rightLimit_29_0= 'inclusive' )
                {
                // InternalKactors.g:2553:6: (lv_rightLimit_29_0= 'inclusive' )
                // InternalKactors.g:2554:7: lv_rightLimit_29_0= 'inclusive'
                {
                lv_rightLimit_29_0=(Token)match(input,51,FOLLOW_32); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:2567:5: otherlv_30= 'exclusive'
                {
                otherlv_30=(Token)match(input,52,FOLLOW_32); if (state.failed) return ;

                }
                break;

        }

        otherlv_31=(Token)match(input,48,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2576:4: ( (lv_body_32_0= ruleStatementList ) )
        // InternalKactors.g:2577:5: (lv_body_32_0= ruleStatementList )
        {
        // InternalKactors.g:2577:5: (lv_body_32_0= ruleStatementList )
        // InternalKactors.g:2578:6: lv_body_32_0= ruleStatementList
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
    // $ANTLR end synpred74_InternalKactors

    // $ANTLR start synpred76_InternalKactors
    public final void synpred76_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_38=null;
        EObject lv_quantity_37_0 = null;

        EObject lv_body_39_0 = null;


        // InternalKactors.g:2646:3: ( ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2646:3: ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2646:3: ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
        // InternalKactors.g:2647:4: ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) )
        {
        // InternalKactors.g:2647:4: ( (lv_quantity_37_0= ruleQuantity ) )
        // InternalKactors.g:2648:5: (lv_quantity_37_0= ruleQuantity )
        {
        // InternalKactors.g:2648:5: (lv_quantity_37_0= ruleQuantity )
        // InternalKactors.g:2649:6: lv_quantity_37_0= ruleQuantity
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

        otherlv_38=(Token)match(input,48,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2670:4: ( (lv_body_39_0= ruleStatementList ) )
        // InternalKactors.g:2671:5: (lv_body_39_0= ruleStatementList )
        {
        // InternalKactors.g:2671:5: (lv_body_39_0= ruleStatementList )
        // InternalKactors.g:2672:6: lv_body_39_0= ruleStatementList
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
    // $ANTLR end synpred76_InternalKactors

    // $ANTLR start synpred77_InternalKactors
    public final void synpred77_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_41=null;
        EObject lv_date_40_0 = null;

        EObject lv_body_42_0 = null;


        // InternalKactors.g:2691:3: ( ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2691:3: ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2691:3: ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
        // InternalKactors.g:2692:4: ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) )
        {
        // InternalKactors.g:2692:4: ( (lv_date_40_0= ruleDate ) )
        // InternalKactors.g:2693:5: (lv_date_40_0= ruleDate )
        {
        // InternalKactors.g:2693:5: (lv_date_40_0= ruleDate )
        // InternalKactors.g:2694:6: lv_date_40_0= ruleDate
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

        otherlv_41=(Token)match(input,48,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2715:4: ( (lv_body_42_0= ruleStatementList ) )
        // InternalKactors.g:2716:5: (lv_body_42_0= ruleStatementList )
        {
        // InternalKactors.g:2716:5: (lv_body_42_0= ruleStatementList )
        // InternalKactors.g:2717:6: lv_body_42_0= ruleStatementList
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
    // $ANTLR end synpred77_InternalKactors

    // $ANTLR start synpred97_InternalKactors
    public final void synpred97_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:3418:5: ( 'to' )
        // InternalKactors.g:3418:6: 'to'
        {
        match(input,53,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred97_InternalKactors

    // $ANTLR start synpred124_InternalKactors
    public final void synpred124_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:4137:5: ( 'to' )
        // InternalKactors.g:4137:6: 'to'
        {
        match(input,53,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred124_InternalKactors

    // $ANTLR start synpred139_InternalKactors
    public final void synpred139_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_number_0_0 = null;


        // InternalKactors.g:4503:3: ( ( (lv_number_0_0= ruleNumber ) ) )
        // InternalKactors.g:4503:3: ( (lv_number_0_0= ruleNumber ) )
        {
        // InternalKactors.g:4503:3: ( (lv_number_0_0= ruleNumber ) )
        // InternalKactors.g:4504:4: (lv_number_0_0= ruleNumber )
        {
        // InternalKactors.g:4504:4: (lv_number_0_0= ruleNumber )
        // InternalKactors.g:4505:5: lv_number_0_0= ruleNumber
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
    // $ANTLR end synpred139_InternalKactors

    // $ANTLR start synpred142_InternalKactors
    public final void synpred142_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_date_5_0 = null;


        // InternalKactors.g:4587:3: ( ( (lv_date_5_0= ruleDate ) ) )
        // InternalKactors.g:4587:3: ( (lv_date_5_0= ruleDate ) )
        {
        // InternalKactors.g:4587:3: ( (lv_date_5_0= ruleDate ) )
        // InternalKactors.g:4588:4: (lv_date_5_0= ruleDate )
        {
        // InternalKactors.g:4588:4: (lv_date_5_0= ruleDate )
        // InternalKactors.g:4589:5: lv_date_5_0= ruleDate
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
    // $ANTLR end synpred142_InternalKactors

    // $ANTLR start synpred171_InternalKactors
    public final void synpred171_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5407:4: ( ( RULE_INT ) )
        // InternalKactors.g:5407:5: ( RULE_INT )
        {
        // InternalKactors.g:5407:5: ( RULE_INT )
        // InternalKactors.g:5408:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred171_InternalKactors

    // $ANTLR start synpred172_InternalKactors
    public final void synpred172_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5429:4: ( ( 'l' ) )
        // InternalKactors.g:5429:5: ( 'l' )
        {
        // InternalKactors.g:5429:5: ( 'l' )
        // InternalKactors.g:5430:5: 'l'
        {
        match(input,76,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred172_InternalKactors

    // $ANTLR start synpred173_InternalKactors
    public final void synpred173_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5447:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5447:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5447:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKactors.g:5448:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKactors.g:5448:5: ( ( '.' ) )
        // InternalKactors.g:5449:6: ( '.' )
        {
        // InternalKactors.g:5449:6: ( '.' )
        // InternalKactors.g:5450:7: '.'
        {
        match(input,66,FOLLOW_10); if (state.failed) return ;

        }


        }

        // InternalKactors.g:5453:5: ( ( RULE_INT ) )
        // InternalKactors.g:5454:6: ( RULE_INT )
        {
        // InternalKactors.g:5454:6: ( RULE_INT )
        // InternalKactors.g:5455:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred173_InternalKactors

    // $ANTLR start synpred177_InternalKactors
    public final void synpred177_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5496:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5496:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5496:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKactors.g:5497:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKactors.g:5497:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKactors.g:5498:6: ( ( 'e' | 'E' ) )
        {
        // InternalKactors.g:5498:6: ( ( 'e' | 'E' ) )
        // InternalKactors.g:5499:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=77 && input.LA(1)<=78) ) {
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

        // InternalKactors.g:5506:5: ( '+' | ( ( '-' ) ) )?
        int alt127=3;
        int LA127_0 = input.LA(1);

        if ( (LA127_0==74) ) {
            alt127=1;
        }
        else if ( (LA127_0==75) ) {
            alt127=2;
        }
        switch (alt127) {
            case 1 :
                // InternalKactors.g:5507:6: '+'
                {
                match(input,74,FOLLOW_10); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:5509:6: ( ( '-' ) )
                {
                // InternalKactors.g:5509:6: ( ( '-' ) )
                // InternalKactors.g:5510:7: ( '-' )
                {
                // InternalKactors.g:5510:7: ( '-' )
                // InternalKactors.g:5511:8: '-'
                {
                match(input,75,FOLLOW_10); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKactors.g:5515:5: ( ( RULE_INT ) )
        // InternalKactors.g:5516:6: ( RULE_INT )
        {
        // InternalKactors.g:5516:6: ( RULE_INT )
        // InternalKactors.g:5517:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred177_InternalKactors

    // $ANTLR start synpred194_InternalKactors
    public final void synpred194_InternalKactors_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKactors.g:5951:4: (kw= '-' )
        // InternalKactors.g:5951:4: kw= '-'
        {
        kw=(Token)match(input,75,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred194_InternalKactors

    // $ANTLR start synpred195_InternalKactors
    public final void synpred195_InternalKactors_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKactors.g:5958:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKactors.g:5958:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred195_InternalKactors

    // Delegated rules

    public final boolean synpred8_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred194_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred194_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred44_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred44_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred50_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred50_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred172_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred172_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred70_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred70_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred35_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred35_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred51_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred51_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred173_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred173_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred57_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred57_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred66_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred66_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred97_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred97_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred124_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred124_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred139_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred139_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred52_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred52_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred17_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred17_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred76_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred76_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred30_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred30_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred36_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred36_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred195_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred195_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred65_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred65_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred74_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred74_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred177_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred177_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred43_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred43_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred171_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred171_InternalKactors_fragment(); // can never throw exception
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


    protected DFA11 dfa11 = new DFA11(this);
    protected DFA18 dfa18 = new DFA18(this);
    protected DFA23 dfa23 = new DFA23(this);
    protected DFA24 dfa24 = new DFA24(this);
    protected DFA27 dfa27 = new DFA27(this);
    protected DFA30 dfa30 = new DFA30(this);
    protected DFA34 dfa34 = new DFA34(this);
    protected DFA38 dfa38 = new DFA38(this);
    protected DFA39 dfa39 = new DFA39(this);
    protected DFA49 dfa49 = new DFA49(this);
    protected DFA51 dfa51 = new DFA51(this);
    protected DFA60 dfa60 = new DFA60(this);
    protected DFA66 dfa66 = new DFA66(this);
    protected DFA70 dfa70 = new DFA70(this);
    static final String dfa_1s = "\14\uffff";
    static final String dfa_2s = "\1\1\13\uffff";
    static final String dfa_3s = "\1\16\13\uffff";
    static final String dfa_4s = "\1\45\13\uffff";
    static final String dfa_5s = "\1\uffff\1\13\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12";
    static final String dfa_6s = "\1\0\13\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\13\uffff\1\2\1\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\1",
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

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 262:6: ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA11_0 = input.LA(1);

                         
                        int index11_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA11_0==EOF||LA11_0==RULE_ANNOTATION_ID||LA11_0==37) ) {s = 1;}

                        else if ( LA11_0 == 26 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {s = 2;}

                        else if ( LA11_0 == 28 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {s = 3;}

                        else if ( LA11_0 == 29 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {s = 4;}

                        else if ( LA11_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {s = 5;}

                        else if ( LA11_0 == 31 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {s = 6;}

                        else if ( LA11_0 == 32 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {s = 7;}

                        else if ( LA11_0 == 33 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {s = 8;}

                        else if ( LA11_0 == 34 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {s = 9;}

                        else if ( LA11_0 == 35 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {s = 10;}

                        else if ( LA11_0 == 36 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9) ) {s = 11;}

                         
                        input.seek(index11_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 11, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\43\uffff";
    static final String dfa_9s = "\1\2\42\uffff";
    static final String dfa_10s = "\1\4\1\0\41\uffff";
    static final String dfa_11s = "\1\113\1\0\41\uffff";
    static final String dfa_12s = "\2\uffff\1\2\37\uffff\1\1";
    static final String dfa_13s = "\1\uffff\1\0\41\uffff}>";
    static final String[] dfa_14s = {
            "\2\2\1\uffff\6\2\1\uffff\2\2\13\uffff\1\2\11\uffff\2\2\1\1\10\2\1\uffff\2\2\3\uffff\4\2\2\uffff\1\2\1\uffff\1\2\13\uffff\2\2",
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

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "994:5: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA18_1 = input.LA(1);

                         
                        int index18_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred30_InternalKactors()) ) {s = 34;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index18_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 18, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_15s = "\26\uffff";
    static final String dfa_16s = "\1\4\2\0\23\uffff";
    static final String dfa_17s = "\1\113\2\0\23\uffff";
    static final String dfa_18s = "\3\uffff\1\3\1\4\1\5\1\6\1\7\1\10\13\uffff\1\1\1\2";
    static final String dfa_19s = "\1\uffff\1\0\1\1\23\uffff}>";
    static final String[] dfa_20s = {
            "\1\10\1\1\1\uffff\1\10\1\3\1\10\2\uffff\1\10\2\uffff\1\10\27\uffff\1\2\2\uffff\1\4\1\uffff\1\5\1\6\1\7\2\uffff\2\10\6\uffff\1\10\2\uffff\1\10\1\uffff\1\10\13\uffff\2\10",
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

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = dfa_15;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "1200:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA23_1 = input.LA(1);

                         
                        int index23_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred36_InternalKactors()) ) {s = 20;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index23_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA23_2 = input.LA(1);

                         
                        int index23_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred36_InternalKactors()) ) {s = 20;}

                        else if ( (synpred37_InternalKactors()) ) {s = 21;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index23_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 23, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA24 extends DFA {

        public DFA24(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 24;
            this.eot = dfa_15;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "1382:3: ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA24_1 = input.LA(1);

                         
                        int index24_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred43_InternalKactors()) ) {s = 20;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index24_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA24_2 = input.LA(1);

                         
                        int index24_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred43_InternalKactors()) ) {s = 20;}

                        else if ( (synpred44_InternalKactors()) ) {s = 21;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index24_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 24, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_21s = "\21\uffff";
    static final String dfa_22s = "\1\4\2\0\16\uffff";
    static final String dfa_23s = "\1\113\2\0\16\uffff";
    static final String dfa_24s = "\3\uffff\1\2\13\uffff\1\1\1\3";
    static final String dfa_25s = "\1\uffff\1\0\1\1\16\uffff}>";
    static final String[] dfa_26s = {
            "\1\3\1\1\1\uffff\1\3\1\uffff\1\3\2\uffff\1\3\2\uffff\1\3\27\uffff\1\2\11\uffff\2\3\6\uffff\1\3\2\uffff\1\3\1\uffff\1\3\13\uffff\2\3",
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

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = dfa_21;
            this.eof = dfa_21;
            this.min = dfa_22;
            this.max = dfa_23;
            this.accept = dfa_24;
            this.special = dfa_25;
            this.transition = dfa_26;
        }
        public String getDescription() {
            return "1692:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )";
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
                        if ( (synpred52_InternalKactors()) ) {s = 15;}

                        else if ( (synpred53_InternalKactors()) ) {s = 3;}

                         
                        input.seek(index27_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA27_2 = input.LA(1);

                         
                        int index27_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred52_InternalKactors()) ) {s = 15;}

                        else if ( (synpred53_InternalKactors()) ) {s = 3;}

                        else if ( (true) ) {s = 16;}

                         
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
    static final String dfa_27s = "\34\uffff";
    static final String dfa_28s = "\1\4\3\0\2\uffff\6\0\1\uffff\1\0\16\uffff";
    static final String dfa_29s = "\1\113\3\0\2\uffff\6\0\1\uffff\1\0\16\uffff";
    static final String dfa_30s = "\4\uffff\1\1\14\uffff\1\3\10\uffff\1\2\1\4";
    static final String dfa_31s = "\1\uffff\1\0\1\1\1\2\2\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\uffff\1\11\16\uffff}>";
    static final String[] dfa_32s = {
            "\1\6\1\1\1\uffff\1\12\1\21\1\15\2\4\1\11\2\uffff\1\21\27\uffff\1\13\2\uffff\1\21\1\uffff\3\21\1\4\1\uffff\1\2\1\3\3\uffff\3\4\1\21\2\uffff\1\21\1\uffff\1\21\13\uffff\1\7\1\10",
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

    class DFA30 extends DFA {

        public DFA30(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 30;
            this.eot = dfa_27;
            this.eof = dfa_27;
            this.min = dfa_28;
            this.max = dfa_29;
            this.accept = dfa_30;
            this.special = dfa_31;
            this.transition = dfa_32;
        }
        public String getDescription() {
            return "1979:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )";
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
                        if ( (synpred55_InternalKactors()) ) {s = 4;}

                        else if ( (synpred58_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index30_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA30_2 = input.LA(1);

                         
                        int index30_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred55_InternalKactors()) ) {s = 4;}

                        else if ( (synpred58_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index30_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA30_3 = input.LA(1);

                         
                        int index30_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred55_InternalKactors()) ) {s = 4;}

                        else if ( (synpred58_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index30_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA30_6 = input.LA(1);

                         
                        int index30_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred55_InternalKactors()) ) {s = 4;}

                        else if ( (synpred58_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index30_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA30_7 = input.LA(1);

                         
                        int index30_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred55_InternalKactors()) ) {s = 4;}

                        else if ( (synpred58_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index30_7);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA30_8 = input.LA(1);

                         
                        int index30_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred55_InternalKactors()) ) {s = 4;}

                        else if ( (synpred58_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index30_8);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA30_9 = input.LA(1);

                         
                        int index30_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred55_InternalKactors()) ) {s = 4;}

                        else if ( (synpred58_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index30_9);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA30_10 = input.LA(1);

                         
                        int index30_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred55_InternalKactors()) ) {s = 4;}

                        else if ( (synpred58_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index30_10);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA30_11 = input.LA(1);

                         
                        int index30_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred55_InternalKactors()) ) {s = 4;}

                        else if ( (synpred57_InternalKactors()) ) {s = 26;}

                        else if ( (synpred58_InternalKactors()) ) {s = 17;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index30_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA30_13 = input.LA(1);

                         
                        int index30_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred55_InternalKactors()) ) {s = 4;}

                        else if ( (synpred58_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index30_13);
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
    static final String dfa_33s = "\27\uffff";
    static final String dfa_34s = "\1\4\1\uffff\2\0\3\uffff\4\0\14\uffff";
    static final String dfa_35s = "\1\113\1\uffff\2\0\3\uffff\4\0\14\uffff";
    static final String dfa_36s = "\1\uffff\1\1\2\uffff\1\3\1\4\1\5\4\uffff\1\10\1\12\1\15\1\16\1\17\1\20\1\2\1\6\1\11\1\13\1\14\1\7";
    static final String dfa_37s = "\2\uffff\1\0\1\1\3\uffff\1\2\1\3\1\4\1\5\14\uffff}>";
    static final String[] dfa_38s = {
            "\1\6\1\1\1\uffff\1\12\1\uffff\1\15\1\4\1\5\1\11\32\uffff\1\13\7\uffff\1\14\1\uffff\1\2\1\3\3\uffff\1\16\1\17\1\20\21\uffff\1\7\1\10",
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

    class DFA34 extends DFA {

        public DFA34(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 34;
            this.eot = dfa_33;
            this.eof = dfa_33;
            this.min = dfa_34;
            this.max = dfa_35;
            this.accept = dfa_36;
            this.special = dfa_37;
            this.transition = dfa_38;
        }
        public String getDescription() {
            return "2116:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA34_2 = input.LA(1);

                         
                        int index34_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred61_InternalKactors()) ) {s = 17;}

                        else if ( (synpred65_InternalKactors()) ) {s = 18;}

                         
                        input.seek(index34_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA34_3 = input.LA(1);

                         
                        int index34_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred61_InternalKactors()) ) {s = 17;}

                        else if ( (synpred65_InternalKactors()) ) {s = 18;}

                         
                        input.seek(index34_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA34_7 = input.LA(1);

                         
                        int index34_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred65_InternalKactors()) ) {s = 18;}

                        else if ( (synpred74_InternalKactors()) ) {s = 19;}

                        else if ( (synpred76_InternalKactors()) ) {s = 20;}

                         
                        input.seek(index34_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA34_8 = input.LA(1);

                         
                        int index34_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred65_InternalKactors()) ) {s = 18;}

                        else if ( (synpred74_InternalKactors()) ) {s = 19;}

                        else if ( (synpred76_InternalKactors()) ) {s = 20;}

                         
                        input.seek(index34_8);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA34_9 = input.LA(1);

                         
                        int index34_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred65_InternalKactors()) ) {s = 18;}

                        else if ( (synpred74_InternalKactors()) ) {s = 19;}

                        else if ( (synpred76_InternalKactors()) ) {s = 20;}

                        else if ( (synpred77_InternalKactors()) ) {s = 21;}

                         
                        input.seek(index34_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA34_10 = input.LA(1);

                         
                        int index34_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred65_InternalKactors()) ) {s = 18;}

                        else if ( (synpred66_InternalKactors()) ) {s = 22;}

                         
                        input.seek(index34_10);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 34, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_39s = "\6\uffff";
    static final String dfa_40s = "\1\uffff\1\2\3\uffff\1\2";
    static final String dfa_41s = "\1\5\1\4\1\uffff\1\5\1\uffff\1\4";
    static final String dfa_42s = "\1\20\1\113\1\uffff\1\20\1\uffff\1\113";
    static final String dfa_43s = "\2\uffff\1\1\1\uffff\1\2\1\uffff";
    static final String dfa_44s = "\6\uffff}>";
    static final String[] dfa_45s = {
            "\1\1\12\uffff\1\2",
            "\2\2\1\uffff\6\2\1\uffff\2\2\13\uffff\1\2\11\uffff\1\2\1\uffff\2\2\1\uffff\6\2\1\uffff\2\2\3\uffff\5\2\1\4\3\2\2\uffff\1\2\1\3\7\uffff\2\2",
            "",
            "\1\5\12\uffff\1\2",
            "",
            "\2\2\1\uffff\6\2\1\uffff\2\2\13\uffff\1\2\11\uffff\1\2\1\uffff\2\2\1\uffff\6\2\1\uffff\2\2\3\uffff\5\2\1\4\3\2\2\uffff\1\2\1\3\7\uffff\2\2"
    };

    static final short[] dfa_39 = DFA.unpackEncodedString(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final char[] dfa_41 = DFA.unpackEncodedStringToUnsignedChars(dfa_41s);
    static final char[] dfa_42 = DFA.unpackEncodedStringToUnsignedChars(dfa_42s);
    static final short[] dfa_43 = DFA.unpackEncodedString(dfa_43s);
    static final short[] dfa_44 = DFA.unpackEncodedString(dfa_44s);
    static final short[][] dfa_45 = unpackEncodedStringArray(dfa_45s);

    class DFA38 extends DFA {

        public DFA38(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 38;
            this.eot = dfa_39;
            this.eof = dfa_40;
            this.min = dfa_41;
            this.max = dfa_42;
            this.accept = dfa_43;
            this.special = dfa_44;
            this.transition = dfa_45;
        }
        public String getDescription() {
            return "3013:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )";
        }
    }

    class DFA39 extends DFA {

        public DFA39(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 39;
            this.eot = dfa_39;
            this.eof = dfa_40;
            this.min = dfa_41;
            this.max = dfa_42;
            this.accept = dfa_43;
            this.special = dfa_44;
            this.transition = dfa_45;
        }
        public String getDescription() {
            return "3042:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )";
        }
    }
    static final String dfa_46s = "\4\uffff\1\20\7\uffff\1\20\5\uffff\1\20\2\uffff\1\20";
    static final String dfa_47s = "\1\4\1\uffff\2\14\1\46\7\uffff\1\46\3\14\2\uffff\1\46\2\14\1\46";
    static final String dfa_48s = "\1\113\1\uffff\2\14\1\116\7\uffff\1\116\1\14\2\113\2\uffff\1\116\2\14\1\65";
    static final String dfa_49s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\4\uffff\1\3\1\2\4\uffff";
    static final String dfa_50s = "\26\uffff}>";
    static final String[] dfa_51s = {
            "\1\7\1\10\1\uffff\1\6\4\uffff\1\4\42\uffff\1\5\1\uffff\2\1\3\uffff\1\12\1\13\3\uffff\1\11\11\uffff\5\11\1\2\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\20\14\uffff\3\21\14\uffff\1\15\11\uffff\1\14\1\16\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\20\14\uffff\3\21\14\uffff\1\15\12\uffff\1\16\1\17",
            "\1\22",
            "\1\25\75\uffff\1\23\1\24",
            "\1\25\75\uffff\1\23\1\24",
            "",
            "",
            "\1\20\14\uffff\3\21\27\uffff\1\16\1\17",
            "\1\25",
            "\1\25",
            "\1\20\14\uffff\3\21"
    };
    static final short[] dfa_46 = DFA.unpackEncodedString(dfa_46s);
    static final char[] dfa_47 = DFA.unpackEncodedStringToUnsignedChars(dfa_47s);
    static final char[] dfa_48 = DFA.unpackEncodedStringToUnsignedChars(dfa_48s);
    static final short[] dfa_49 = DFA.unpackEncodedString(dfa_49s);
    static final short[] dfa_50 = DFA.unpackEncodedString(dfa_50s);
    static final short[][] dfa_51 = unpackEncodedStringArray(dfa_51s);

    class DFA49 extends DFA {

        public DFA49(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 49;
            this.eot = dfa_15;
            this.eof = dfa_46;
            this.min = dfa_47;
            this.max = dfa_48;
            this.accept = dfa_49;
            this.special = dfa_50;
            this.transition = dfa_51;
        }
        public String getDescription() {
            return "3343:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )";
        }
    }
    static final String dfa_52s = "\2\uffff\1\3\2\uffff\1\3";
    static final String dfa_53s = "\1\4\1\uffff\1\15\1\uffff\1\4\1\15";
    static final String dfa_54s = "\1\113\1\uffff\1\100\1\uffff\1\113\1\100";
    static final String dfa_55s = "\1\uffff\1\1\1\uffff\1\2\2\uffff";
    static final String[] dfa_56s = {
            "\1\3\1\1\1\uffff\1\2\1\uffff\1\3\2\uffff\1\3\42\uffff\1\3\1\uffff\2\3\3\uffff\3\3\2\uffff\1\3\11\uffff\7\3",
            "",
            "\1\1\15\uffff\1\3\43\uffff\1\3\1\4",
            "",
            "\1\3\1\1\1\uffff\1\5\1\uffff\1\3\2\uffff\1\3\42\uffff\1\3\1\uffff\2\3\3\uffff\3\3\2\uffff\1\3\11\uffff\7\3",
            "\1\1\15\uffff\1\3\43\uffff\1\3\1\4"
    };
    static final short[] dfa_52 = DFA.unpackEncodedString(dfa_52s);
    static final char[] dfa_53 = DFA.unpackEncodedStringToUnsignedChars(dfa_53s);
    static final char[] dfa_54 = DFA.unpackEncodedStringToUnsignedChars(dfa_54s);
    static final short[] dfa_55 = DFA.unpackEncodedString(dfa_55s);
    static final short[][] dfa_56 = unpackEncodedStringArray(dfa_56s);

    class DFA51 extends DFA {

        public DFA51(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 51;
            this.eot = dfa_39;
            this.eof = dfa_52;
            this.min = dfa_53;
            this.max = dfa_54;
            this.accept = dfa_55;
            this.special = dfa_44;
            this.transition = dfa_56;
        }
        public String getDescription() {
            return "3718:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?";
        }
    }
    static final String dfa_57s = "\32\uffff";
    static final String dfa_58s = "\4\uffff\1\24\10\uffff\1\24\1\uffff\1\24\1\25\5\uffff\1\24\2\uffff\1\24";
    static final String dfa_59s = "\1\4\1\uffff\2\14\1\33\10\uffff\1\33\1\uffff\1\33\1\5\2\14\3\uffff\1\33\2\14\1\33";
    static final String dfa_60s = "\1\113\1\uffff\2\14\1\121\10\uffff\1\116\1\uffff\1\116\1\122\2\113\3\uffff\1\116\2\14\1\102";
    static final String dfa_61s = "\1\uffff\1\1\3\uffff\1\3\1\4\1\5\1\7\1\12\1\13\1\14\1\15\1\uffff\1\11\4\uffff\1\6\1\2\1\10\4\uffff";
    static final String dfa_62s = "\32\uffff}>";
    static final String[] dfa_63s = {
            "\1\6\2\uffff\1\5\1\uffff\1\11\2\uffff\1\4\42\uffff\1\10\1\uffff\2\1\3\uffff\1\12\1\13\1\14\2\uffff\1\7\11\uffff\5\7\1\2\1\3",
            "",
            "\1\15",
            "\1\15",
            "\1\24\27\uffff\3\23\11\uffff\2\24\1\25\1\20\10\uffff\1\16\1\17\1\21\1\22\3\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\24\27\uffff\3\23\11\uffff\2\24\1\25\1\20\11\uffff\1\17\1\21\1\22",
            "",
            "\1\24\27\uffff\3\23\11\uffff\2\24\1\25\1\20\12\uffff\1\21\1\22",
            "\1\25\4\uffff\1\25\1\uffff\1\26\3\uffff\1\25\12\uffff\1\25\13\uffff\1\25\17\uffff\1\25\7\uffff\3\25\20\uffff\1\25",
            "\1\31\75\uffff\1\27\1\30",
            "\1\31\75\uffff\1\27\1\30",
            "",
            "",
            "",
            "\1\24\27\uffff\3\23\11\uffff\2\24\2\25\12\uffff\1\21\1\22",
            "\1\31",
            "\1\31",
            "\1\24\27\uffff\3\23\11\uffff\2\24\2\25"
    };

    static final short[] dfa_57 = DFA.unpackEncodedString(dfa_57s);
    static final short[] dfa_58 = DFA.unpackEncodedString(dfa_58s);
    static final char[] dfa_59 = DFA.unpackEncodedStringToUnsignedChars(dfa_59s);
    static final char[] dfa_60 = DFA.unpackEncodedStringToUnsignedChars(dfa_60s);
    static final short[] dfa_61 = DFA.unpackEncodedString(dfa_61s);
    static final short[] dfa_62 = DFA.unpackEncodedString(dfa_62s);
    static final short[][] dfa_63 = unpackEncodedStringArray(dfa_63s);

    class DFA60 extends DFA {

        public DFA60(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 60;
            this.eot = dfa_57;
            this.eof = dfa_58;
            this.min = dfa_59;
            this.max = dfa_60;
            this.accept = dfa_61;
            this.special = dfa_62;
            this.transition = dfa_63;
        }
        public String getDescription() {
            return "3963:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )";
        }
    }
    static final String dfa_64s = "\3\uffff\1\13\2\uffff\2\13\7\uffff\1\13\2\uffff\2\13\2\uffff";
    static final String dfa_65s = "\1\7\2\14\1\4\2\uffff\2\4\3\14\1\uffff\1\14\2\uffff\1\4\2\14\2\4\1\14\1\0";
    static final String dfa_66s = "\1\113\2\14\1\121\2\uffff\2\116\1\14\2\113\1\uffff\1\14\2\uffff\1\116\2\14\1\113\1\116\1\14\1\0";
    static final String dfa_67s = "\4\uffff\1\3\1\5\5\uffff\1\1\1\uffff\1\2\1\4\7\uffff";
    static final String dfa_68s = "\25\uffff\1\0}>";
    static final String[] dfa_69s = {
            "\1\4\4\uffff\1\3\44\uffff\2\5\27\uffff\1\1\1\2",
            "\1\6",
            "\1\6",
            "\2\13\1\uffff\6\13\1\uffff\2\13\13\uffff\1\13\11\uffff\1\13\1\uffff\2\13\1\uffff\11\13\2\uffff\1\15\4\13\2\uffff\3\13\3\uffff\1\10\7\uffff\1\13\1\14\1\7\1\11\1\12\3\16",
            "",
            "",
            "\2\13\1\uffff\6\13\1\uffff\2\13\13\uffff\1\13\11\uffff\1\13\1\uffff\2\13\1\uffff\11\13\2\uffff\1\15\4\13\2\uffff\3\13\3\uffff\1\10\7\uffff\2\13\1\7\1\11\1\12",
            "\2\13\1\uffff\6\13\1\uffff\2\13\13\uffff\1\13\11\uffff\1\13\1\uffff\2\13\1\uffff\11\13\2\uffff\1\15\4\13\2\uffff\3\13\3\uffff\1\10\7\uffff\2\13\1\uffff\1\11\1\12",
            "\1\17",
            "\1\22\75\uffff\1\20\1\21",
            "\1\22\75\uffff\1\20\1\21",
            "",
            "\1\23",
            "",
            "",
            "\2\13\1\uffff\6\13\1\uffff\2\13\13\uffff\1\13\11\uffff\1\13\1\uffff\2\13\1\uffff\11\13\2\uffff\1\15\4\13\2\uffff\3\13\13\uffff\2\13\1\uffff\1\11\1\12",
            "\1\22",
            "\1\22",
            "\2\13\1\uffff\6\13\1\uffff\2\13\13\uffff\1\13\11\uffff\1\13\1\uffff\2\13\1\uffff\11\13\2\uffff\1\15\4\13\2\uffff\3\13\13\uffff\2\13",
            "\2\13\1\uffff\6\13\1\uffff\2\13\13\uffff\1\13\11\uffff\1\13\1\uffff\2\13\1\uffff\20\13\2\uffff\1\13\1\uffff\1\13\2\uffff\2\13\7\uffff\1\13\1\24\3\13",
            "\1\25",
            "\1\uffff"
    };
    static final short[] dfa_64 = DFA.unpackEncodedString(dfa_64s);
    static final char[] dfa_65 = DFA.unpackEncodedStringToUnsignedChars(dfa_65s);
    static final char[] dfa_66 = DFA.unpackEncodedStringToUnsignedChars(dfa_66s);
    static final short[] dfa_67 = DFA.unpackEncodedString(dfa_67s);
    static final short[] dfa_68 = DFA.unpackEncodedString(dfa_68s);
    static final short[][] dfa_69 = unpackEncodedStringArray(dfa_69s);

    class DFA66 extends DFA {

        public DFA66(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 66;
            this.eot = dfa_15;
            this.eof = dfa_64;
            this.min = dfa_65;
            this.max = dfa_66;
            this.accept = dfa_67;
            this.special = dfa_68;
            this.transition = dfa_69;
        }
        public String getDescription() {
            return "4502:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA66_21 = input.LA(1);

                         
                        int index66_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred139_InternalKactors()) ) {s = 11;}

                        else if ( (synpred142_InternalKactors()) ) {s = 14;}

                         
                        input.seek(index66_21);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 66, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_70s = "\15\uffff";
    static final String dfa_71s = "\3\uffff\1\13\10\uffff\1\13";
    static final String dfa_72s = "\1\4\2\uffff\1\4\6\uffff\1\5\1\uffff\1\4";
    static final String dfa_73s = "\1\113\2\uffff\1\113\6\uffff\1\5\1\uffff\1\113";
    static final String dfa_74s = "\1\uffff\1\1\1\2\1\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\uffff\1\3\1\uffff";
    static final String dfa_75s = "\15\uffff}>";
    static final String[] dfa_76s = {
            "\1\7\1\3\1\uffff\1\2\1\uffff\1\10\2\uffff\1\2\2\uffff\1\1\27\uffff\1\5\11\uffff\2\2\6\uffff\1\4\2\uffff\1\6\1\uffff\1\11\13\uffff\2\2",
            "",
            "",
            "\2\13\1\uffff\6\13\1\uffff\2\13\13\uffff\1\13\11\uffff\1\13\1\4\2\13\1\uffff\6\13\1\uffff\2\13\3\uffff\4\13\2\uffff\3\13\3\uffff\1\12\7\uffff\2\13",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\14",
            "",
            "\2\13\1\uffff\6\13\1\uffff\2\13\13\uffff\1\13\11\uffff\1\13\1\4\2\13\1\uffff\6\13\1\uffff\2\13\3\uffff\4\13\2\uffff\3\13\3\uffff\1\12\7\uffff\2\13"
    };

    static final short[] dfa_70 = DFA.unpackEncodedString(dfa_70s);
    static final short[] dfa_71 = DFA.unpackEncodedString(dfa_71s);
    static final char[] dfa_72 = DFA.unpackEncodedStringToUnsignedChars(dfa_72s);
    static final char[] dfa_73 = DFA.unpackEncodedStringToUnsignedChars(dfa_73s);
    static final short[] dfa_74 = DFA.unpackEncodedString(dfa_74s);
    static final short[] dfa_75 = DFA.unpackEncodedString(dfa_75s);
    static final short[][] dfa_76 = unpackEncodedStringArray(dfa_76s);

    class DFA70 extends DFA {

        public DFA70(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 70;
            this.eot = dfa_70;
            this.eof = dfa_71;
            this.min = dfa_72;
            this.max = dfa_73;
            this.accept = dfa_74;
            this.special = dfa_75;
            this.transition = dfa_76;
        }
        public String getDescription() {
            return "4797:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000002000004002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000001FF4000002L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000001FFC000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000008000000010L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x00000000000000E0L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000001FF4000082L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000002000004000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x000000C000000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x52067480000093B0L,0x0000000000000C00L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000010000000020L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000010008000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x52067480000093B2L,0x0000000000000C00L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x000002C000000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x52067580000093B0L,0x0000000000000C00L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000024000000002L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x53C6F48000009FB0L,0x0000000000000C00L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x01C6808000001EB0L,0x0000000000000C00L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x01C6818000001EB0L,0x0000000000000C00L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0038000000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000001000L,0x0000000000000C00L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0019000000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000011020L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0100004000000002L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000010020L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0400000000000002L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x28C68000000010B0L,0x0000000000000FE0L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x2000000008000000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x08C68000000010B0L,0x0000000000000FE0L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0018000000000002L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x89C68000000012B0L,0x0000000000000FE0L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x09C68000000012B0L,0x0000000000000FE0L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000006L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0080008000010420L,0x0000000000040002L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0800000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0080018000010420L,0x0000000000040002L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0080000000000002L,0x0000000000040002L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000008000010420L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000002L,0x0000000000007004L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000002L,0x0000000000006004L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000002L,0x0000000000006000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000000038800L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000006L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000010022L,0x0000000000000804L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000010022L,0x0000000000000800L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000010022L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000000082L});

}

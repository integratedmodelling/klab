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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_LOWERCASE_ID", "RULE_ID", "RULE_STRING", "RULE_ARGVALUE", "RULE_OBSERVABLE", "RULE_EXPR", "RULE_ANNOTATION_ID", "RULE_INT", "RULE_SEPARATOR", "RULE_CAMELCASE_ID", "RULE_UPPERCASE_ID", "RULE_EMBEDDEDTEXT", "RULE_REGEXP", "RULE_UPPERCASE_PATH", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'name'", "'worldview'", "'label'", "'description'", "'permissions'", "'author'", "'version'", "'created'", "'modified'", "'def'", "':'", "'('", "','", "')'", "'=?'", "'='", "'urn:klab:'", "'#'", "'&'", "'{'", "'}'", "'true'", "'false'", "'inclusive'", "'exclusive'", "'to'", "'in'", "'unknown'", "'*'", "'{{'", "'}}'", "'|'", "'/'", "'.'", "'@'", "'>'", "'<'", "'!='", "'<='", "'>='", "'if'", "'else'", "';'", "'->'", "'+'", "'-'", "'l'", "'e'", "'E'", "'AD'", "'CE'", "'BC'", "'^'"
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
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__73=73;
    public static final int RULE_REGEXP=16;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__74=74;
    public static final int T__31=31;
    public static final int T__32=32;
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
            int LA1_0 = input.LA(1);

            if ( ((LA1_0>=22 && LA1_0<=30)) ) {
                alt1=1;
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

                if ( (LA2_0==RULE_ANNOTATION_ID||LA2_0==31) ) {
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
        	grammarAccess.getPreambleAccess().getUnorderedGroup()
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
    // InternalKactors.g:153:1: rulePreamble returns [EObject current=null] : ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) ) )+ {...}?) ) ) ;
    public final EObject rulePreamble() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_label_6_1=null;
        Token lv_label_6_2=null;
        Token lv_label_6_3=null;
        Token otherlv_7=null;
        Token lv_description_8_0=null;
        Token otherlv_9=null;
        Token lv_permissions_10_0=null;
        Token otherlv_11=null;
        Token lv_authors_12_0=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_17=null;
        Token lv_modcomment_19_0=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        AntlrDatatypeRuleToken lv_worldview_4_0 = null;

        AntlrDatatypeRuleToken lv_version_14_0 = null;

        EObject lv_created_16_0 = null;

        EObject lv_modified_18_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getPreambleAccess().getUnorderedGroup()
        	);

        try {
            // InternalKactors.g:162:2: ( ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) ) )+ {...}?) ) ) )
            // InternalKactors.g:163:2: ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) ) )+ {...}?) ) )
            {
            // InternalKactors.g:163:2: ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) ) )+ {...}?) ) )
            // InternalKactors.g:164:3: ( ( ( ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) ) )+ {...}?) )
            {
            // InternalKactors.g:164:3: ( ( ( ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) ) )+ {...}?) )
            // InternalKactors.g:165:4: ( ( ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) ) )+ {...}?)
            {
            getUnorderedGroupHelper().enter(grammarAccess.getPreambleAccess().getUnorderedGroup());
            // InternalKactors.g:168:4: ( ( ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) ) )+ {...}?)
            // InternalKactors.g:169:5: ( ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) ) )+ {...}?
            {
            // InternalKactors.g:169:5: ( ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) ) )+
            int cnt6=0;
            loop6:
            do {
                int alt6=10;
                alt6 = dfa6.predict(input);
                switch (alt6) {
            	case 1 :
            	    // InternalKactors.g:170:3: ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:170:3: ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) )
            	    // InternalKactors.g:171:4: {...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 0)");
            	    }
            	    // InternalKactors.g:171:102: ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) )
            	    // InternalKactors.g:172:5: ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 0);
            	    // InternalKactors.g:175:8: ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) )
            	    // InternalKactors.g:175:9: {...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:175:18: (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) )
            	    // InternalKactors.g:175:19: otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) )
            	    {
            	    otherlv_1=(Token)match(input,22,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								newLeafNode(otherlv_1, grammarAccess.getPreambleAccess().getNameKeyword_0_0());
            	      							
            	    }
            	    // InternalKactors.g:179:8: ( (lv_name_2_0= rulePathName ) )
            	    // InternalKactors.g:180:9: (lv_name_2_0= rulePathName )
            	    {
            	    // InternalKactors.g:180:9: (lv_name_2_0= rulePathName )
            	    // InternalKactors.g:181:10: lv_name_2_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      										newCompositeNode(grammarAccess.getPreambleAccess().getNamePathNameParserRuleCall_0_1_0());
            	      									
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


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup());

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalKactors.g:204:3: ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:204:3: ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) )
            	    // InternalKactors.g:205:4: {...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 1)");
            	    }
            	    // InternalKactors.g:205:102: ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) )
            	    // InternalKactors.g:206:5: ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 1);
            	    // InternalKactors.g:209:8: ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) )
            	    // InternalKactors.g:209:9: {...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:209:18: (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) )
            	    // InternalKactors.g:209:19: otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) )
            	    {
            	    otherlv_3=(Token)match(input,23,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								newLeafNode(otherlv_3, grammarAccess.getPreambleAccess().getWorldviewKeyword_1_0());
            	      							
            	    }
            	    // InternalKactors.g:213:8: ( (lv_worldview_4_0= rulePathName ) )
            	    // InternalKactors.g:214:9: (lv_worldview_4_0= rulePathName )
            	    {
            	    // InternalKactors.g:214:9: (lv_worldview_4_0= rulePathName )
            	    // InternalKactors.g:215:10: lv_worldview_4_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      										newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_1_1_0());
            	      									
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_worldview_4_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      										if (current==null) {
            	      											current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      										}
            	      										set(
            	      											current,
            	      											"worldview",
            	      											lv_worldview_4_0,
            	      											"org.integratedmodelling.kactors.Kactors.PathName");
            	      										afterParserOrEnumRuleCall();
            	      									
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup());

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalKactors.g:238:3: ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:238:3: ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) )
            	    // InternalKactors.g:239:4: {...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 2)");
            	    }
            	    // InternalKactors.g:239:102: ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:240:5: ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 2);
            	    // InternalKactors.g:243:8: ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:243:9: {...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:243:18: (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) )
            	    // InternalKactors.g:243:19: otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) )
            	    {
            	    otherlv_5=(Token)match(input,24,FOLLOW_6); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								newLeafNode(otherlv_5, grammarAccess.getPreambleAccess().getLabelKeyword_2_0());
            	      							
            	    }
            	    // InternalKactors.g:247:8: ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) )
            	    // InternalKactors.g:248:9: ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:248:9: ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) )
            	    // InternalKactors.g:249:10: (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING )
            	    {
            	    // InternalKactors.g:249:10: (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING )
            	    int alt3=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_LOWERCASE_ID:
            	        {
            	        alt3=1;
            	        }
            	        break;
            	    case RULE_ID:
            	        {
            	        alt3=2;
            	        }
            	        break;
            	    case RULE_STRING:
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
            	            // InternalKactors.g:250:11: lv_label_6_1= RULE_LOWERCASE_ID
            	            {
            	            lv_label_6_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_label_6_1, grammarAccess.getPreambleAccess().getLabelLOWERCASE_IDTerminalRuleCall_2_1_0_0());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"label",
            	              												lv_label_6_1,
            	              												"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
            	              										
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:265:11: lv_label_6_2= RULE_ID
            	            {
            	            lv_label_6_2=(Token)match(input,RULE_ID,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_label_6_2, grammarAccess.getPreambleAccess().getLabelIDTerminalRuleCall_2_1_0_1());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"label",
            	              												lv_label_6_2,
            	              												"org.eclipse.xtext.common.Terminals.ID");
            	              										
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalKactors.g:280:11: lv_label_6_3= RULE_STRING
            	            {
            	            lv_label_6_3=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_label_6_3, grammarAccess.getPreambleAccess().getLabelSTRINGTerminalRuleCall_2_1_0_2());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"label",
            	              												lv_label_6_3,
            	              												"org.eclipse.xtext.common.Terminals.STRING");
            	              										
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup());

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalKactors.g:303:3: ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:303:3: ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:304:4: {...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 3)");
            	    }
            	    // InternalKactors.g:304:102: ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:305:5: ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 3);
            	    // InternalKactors.g:308:8: ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:308:9: {...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:308:18: (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) )
            	    // InternalKactors.g:308:19: otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) )
            	    {
            	    otherlv_7=(Token)match(input,25,FOLLOW_7); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								newLeafNode(otherlv_7, grammarAccess.getPreambleAccess().getDescriptionKeyword_3_0());
            	      							
            	    }
            	    // InternalKactors.g:312:8: ( (lv_description_8_0= RULE_STRING ) )
            	    // InternalKactors.g:313:9: (lv_description_8_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:313:9: (lv_description_8_0= RULE_STRING )
            	    // InternalKactors.g:314:10: lv_description_8_0= RULE_STRING
            	    {
            	    lv_description_8_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      										newLeafNode(lv_description_8_0, grammarAccess.getPreambleAccess().getDescriptionSTRINGTerminalRuleCall_3_1_0());
            	      									
            	    }
            	    if ( state.backtracking==0 ) {

            	      										if (current==null) {
            	      											current = createModelElement(grammarAccess.getPreambleRule());
            	      										}
            	      										setWithLastConsumed(
            	      											current,
            	      											"description",
            	      											lv_description_8_0,
            	      											"org.eclipse.xtext.common.Terminals.STRING");
            	      									
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup());

            	    }


            	    }


            	    }
            	    break;
            	case 5 :
            	    // InternalKactors.g:336:3: ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:336:3: ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:337:4: {...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 4)");
            	    }
            	    // InternalKactors.g:337:102: ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:338:5: ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 4);
            	    // InternalKactors.g:341:8: ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:341:9: {...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:341:18: (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) )
            	    // InternalKactors.g:341:19: otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) )
            	    {
            	    otherlv_9=(Token)match(input,26,FOLLOW_7); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								newLeafNode(otherlv_9, grammarAccess.getPreambleAccess().getPermissionsKeyword_4_0());
            	      							
            	    }
            	    // InternalKactors.g:345:8: ( (lv_permissions_10_0= RULE_STRING ) )
            	    // InternalKactors.g:346:9: (lv_permissions_10_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:346:9: (lv_permissions_10_0= RULE_STRING )
            	    // InternalKactors.g:347:10: lv_permissions_10_0= RULE_STRING
            	    {
            	    lv_permissions_10_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      										newLeafNode(lv_permissions_10_0, grammarAccess.getPreambleAccess().getPermissionsSTRINGTerminalRuleCall_4_1_0());
            	      									
            	    }
            	    if ( state.backtracking==0 ) {

            	      										if (current==null) {
            	      											current = createModelElement(grammarAccess.getPreambleRule());
            	      										}
            	      										setWithLastConsumed(
            	      											current,
            	      											"permissions",
            	      											lv_permissions_10_0,
            	      											"org.eclipse.xtext.common.Terminals.STRING");
            	      									
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup());

            	    }


            	    }


            	    }
            	    break;
            	case 6 :
            	    // InternalKactors.g:369:3: ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) )
            	    {
            	    // InternalKactors.g:369:3: ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) )
            	    // InternalKactors.g:370:4: {...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 5) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 5)");
            	    }
            	    // InternalKactors.g:370:102: ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ )
            	    // InternalKactors.g:371:5: ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 5);
            	    // InternalKactors.g:374:8: ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+
            	    int cnt4=0;
            	    loop4:
            	    do {
            	        int alt4=2;
            	        int LA4_0 = input.LA(1);

            	        if ( (LA4_0==27) ) {
            	            int LA4_2 = input.LA(2);

            	            if ( ((synpred10_InternalKactors()&&(true))) ) {
            	                alt4=1;
            	            }


            	        }


            	        switch (alt4) {
            	    	case 1 :
            	    	    // InternalKactors.g:374:9: {...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    	    }
            	    	    // InternalKactors.g:374:18: (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) )
            	    	    // InternalKactors.g:374:19: otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) )
            	    	    {
            	    	    otherlv_11=(Token)match(input,27,FOLLOW_7); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      								newLeafNode(otherlv_11, grammarAccess.getPreambleAccess().getAuthorKeyword_5_0());
            	    	      							
            	    	    }
            	    	    // InternalKactors.g:378:8: ( (lv_authors_12_0= RULE_STRING ) )
            	    	    // InternalKactors.g:379:9: (lv_authors_12_0= RULE_STRING )
            	    	    {
            	    	    // InternalKactors.g:379:9: (lv_authors_12_0= RULE_STRING )
            	    	    // InternalKactors.g:380:10: lv_authors_12_0= RULE_STRING
            	    	    {
            	    	    lv_authors_12_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      										newLeafNode(lv_authors_12_0, grammarAccess.getPreambleAccess().getAuthorsSTRINGTerminalRuleCall_5_1_0());
            	    	      									
            	    	    }
            	    	    if ( state.backtracking==0 ) {

            	    	      										if (current==null) {
            	    	      											current = createModelElement(grammarAccess.getPreambleRule());
            	    	      										}
            	    	      										addWithLastConsumed(
            	    	      											current,
            	    	      											"authors",
            	    	      											lv_authors_12_0,
            	    	      											"org.eclipse.xtext.common.Terminals.STRING");
            	    	      									
            	    	    }

            	    	    }


            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt4 >= 1 ) break loop4;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(4, input);
            	                throw eee;
            	        }
            	        cnt4++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup());

            	    }


            	    }


            	    }
            	    break;
            	case 7 :
            	    // InternalKactors.g:402:3: ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:402:3: ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) )
            	    // InternalKactors.g:403:4: {...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 6) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 6)");
            	    }
            	    // InternalKactors.g:403:102: ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) )
            	    // InternalKactors.g:404:5: ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 6);
            	    // InternalKactors.g:407:8: ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) )
            	    // InternalKactors.g:407:9: {...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:407:18: (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) )
            	    // InternalKactors.g:407:19: otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) )
            	    {
            	    otherlv_13=(Token)match(input,28,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								newLeafNode(otherlv_13, grammarAccess.getPreambleAccess().getVersionKeyword_6_0());
            	      							
            	    }
            	    // InternalKactors.g:411:8: ( (lv_version_14_0= ruleVersionNumber ) )
            	    // InternalKactors.g:412:9: (lv_version_14_0= ruleVersionNumber )
            	    {
            	    // InternalKactors.g:412:9: (lv_version_14_0= ruleVersionNumber )
            	    // InternalKactors.g:413:10: lv_version_14_0= ruleVersionNumber
            	    {
            	    if ( state.backtracking==0 ) {

            	      										newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_6_1_0());
            	      									
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_version_14_0=ruleVersionNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      										if (current==null) {
            	      											current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      										}
            	      										set(
            	      											current,
            	      											"version",
            	      											lv_version_14_0,
            	      											"org.integratedmodelling.kactors.Kactors.VersionNumber");
            	      										afterParserOrEnumRuleCall();
            	      									
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup());

            	    }


            	    }


            	    }
            	    break;
            	case 8 :
            	    // InternalKactors.g:436:3: ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:436:3: ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) )
            	    // InternalKactors.g:437:4: {...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 7) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 7)");
            	    }
            	    // InternalKactors.g:437:102: ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) )
            	    // InternalKactors.g:438:5: ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 7);
            	    // InternalKactors.g:441:8: ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) )
            	    // InternalKactors.g:441:9: {...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:441:18: (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) )
            	    // InternalKactors.g:441:19: otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) )
            	    {
            	    otherlv_15=(Token)match(input,29,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								newLeafNode(otherlv_15, grammarAccess.getPreambleAccess().getCreatedKeyword_7_0());
            	      							
            	    }
            	    // InternalKactors.g:445:8: ( (lv_created_16_0= ruleDate ) )
            	    // InternalKactors.g:446:9: (lv_created_16_0= ruleDate )
            	    {
            	    // InternalKactors.g:446:9: (lv_created_16_0= ruleDate )
            	    // InternalKactors.g:447:10: lv_created_16_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      										newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_7_1_0());
            	      									
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_created_16_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      										if (current==null) {
            	      											current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      										}
            	      										set(
            	      											current,
            	      											"created",
            	      											lv_created_16_0,
            	      											"org.integratedmodelling.kactors.Kactors.Date");
            	      										afterParserOrEnumRuleCall();
            	      									
            	    }

            	    }


            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup());

            	    }


            	    }


            	    }
            	    break;
            	case 9 :
            	    // InternalKactors.g:470:3: ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:470:3: ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:471:4: {...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 8) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 8)");
            	    }
            	    // InternalKactors.g:471:102: ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:472:5: ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 8);
            	    // InternalKactors.g:475:8: ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:475:9: {...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:475:18: (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? )
            	    // InternalKactors.g:475:19: otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )?
            	    {
            	    otherlv_17=(Token)match(input,30,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      								newLeafNode(otherlv_17, grammarAccess.getPreambleAccess().getModifiedKeyword_8_0());
            	      							
            	    }
            	    // InternalKactors.g:479:8: ( (lv_modified_18_0= ruleDate ) )
            	    // InternalKactors.g:480:9: (lv_modified_18_0= ruleDate )
            	    {
            	    // InternalKactors.g:480:9: (lv_modified_18_0= ruleDate )
            	    // InternalKactors.g:481:10: lv_modified_18_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      										newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_8_1_0());
            	      									
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_modified_18_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      										if (current==null) {
            	      											current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      										}
            	      										set(
            	      											current,
            	      											"modified",
            	      											lv_modified_18_0,
            	      											"org.integratedmodelling.kactors.Kactors.Date");
            	      										afterParserOrEnumRuleCall();
            	      									
            	    }

            	    }


            	    }

            	    // InternalKactors.g:498:8: ( (lv_modcomment_19_0= RULE_STRING ) )?
            	    int alt5=2;
            	    int LA5_0 = input.LA(1);

            	    if ( (LA5_0==RULE_STRING) ) {
            	        alt5=1;
            	    }
            	    switch (alt5) {
            	        case 1 :
            	            // InternalKactors.g:499:9: (lv_modcomment_19_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:499:9: (lv_modcomment_19_0= RULE_STRING )
            	            // InternalKactors.g:500:10: lv_modcomment_19_0= RULE_STRING
            	            {
            	            lv_modcomment_19_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              										newLeafNode(lv_modcomment_19_0, grammarAccess.getPreambleAccess().getModcommentSTRINGTerminalRuleCall_8_2_0());
            	              									
            	            }
            	            if ( state.backtracking==0 ) {

            	              										if (current==null) {
            	              											current = createModelElement(grammarAccess.getPreambleRule());
            	              										}
            	              										setWithLastConsumed(
            	              											current,
            	              											"modcomment",
            	              											lv_modcomment_19_0,
            	              											"org.eclipse.xtext.common.Terminals.STRING");
            	              									
            	            }

            	            }


            	            }
            	            break;

            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup());

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

            if ( ! getUnorderedGroupHelper().canLeave(grammarAccess.getPreambleAccess().getUnorderedGroup()) ) {
                if (state.backtracking>0) {state.failed=true; return current;}
                throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canLeave(grammarAccess.getPreambleAccess().getUnorderedGroup())");
            }

            }


            }

            getUnorderedGroupHelper().leave(grammarAccess.getPreambleAccess().getUnorderedGroup());

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
    // InternalKactors.g:536:1: entryRuleDefinition returns [EObject current=null] : iv_ruleDefinition= ruleDefinition EOF ;
    public final EObject entryRuleDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDefinition = null;


        try {
            // InternalKactors.g:536:51: (iv_ruleDefinition= ruleDefinition EOF )
            // InternalKactors.g:537:2: iv_ruleDefinition= ruleDefinition EOF
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
    // InternalKactors.g:543:1: ruleDefinition returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'def' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) ) ) ;
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
            // InternalKactors.g:549:2: ( ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'def' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) ) ) )
            // InternalKactors.g:550:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'def' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) ) )
            {
            // InternalKactors.g:550:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'def' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) ) )
            // InternalKactors.g:551:3: ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'def' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) )
            {
            // InternalKactors.g:551:3: ( (lv_annotations_0_0= ruleAnnotation ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==RULE_ANNOTATION_ID) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalKactors.g:552:4: (lv_annotations_0_0= ruleAnnotation )
            	    {
            	    // InternalKactors.g:552:4: (lv_annotations_0_0= ruleAnnotation )
            	    // InternalKactors.g:553:5: lv_annotations_0_0= ruleAnnotation
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getDefinitionAccess().getAnnotationsAnnotationParserRuleCall_0_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_10);
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
            	    break loop7;
                }
            } while (true);

            otherlv_1=(Token)match(input,31,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getDefinitionAccess().getDefKeyword_1());
              		
            }
            // InternalKactors.g:574:3: ( (lv_name_2_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:575:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:575:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:576:5: lv_name_2_0= RULE_LOWERCASE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_11); if (state.failed) return current;
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

            // InternalKactors.g:592:3: ( (lv_arguments_3_0= ruleArgumentDeclaration ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==33) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalKactors.g:593:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:593:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    // InternalKactors.g:594:5: lv_arguments_3_0= ruleArgumentDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getDefinitionAccess().getArgumentsArgumentDeclarationParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_12);
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

            otherlv_4=(Token)match(input,32,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDefinitionAccess().getColonKeyword_4());
              		
            }
            // InternalKactors.g:615:3: ( (lv_body_5_0= ruleBody ) )
            // InternalKactors.g:616:4: (lv_body_5_0= ruleBody )
            {
            // InternalKactors.g:616:4: (lv_body_5_0= ruleBody )
            // InternalKactors.g:617:5: lv_body_5_0= ruleBody
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
    // InternalKactors.g:638:1: entryRuleArgumentDeclaration returns [EObject current=null] : iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF ;
    public final EObject entryRuleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgumentDeclaration = null;


        try {
            // InternalKactors.g:638:60: (iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF )
            // InternalKactors.g:639:2: iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF
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
    // InternalKactors.g:645:1: ruleArgumentDeclaration returns [EObject current=null] : ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) ;
    public final EObject ruleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_ids_2_0=null;
        Token otherlv_3=null;
        Token lv_ids_4_0=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalKactors.g:651:2: ( ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) )
            // InternalKactors.g:652:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            {
            // InternalKactors.g:652:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            // InternalKactors.g:653:3: () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')'
            {
            // InternalKactors.g:653:3: ()
            // InternalKactors.g:654:4: 
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

            otherlv_1=(Token)match(input,33,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getArgumentDeclarationAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:667:3: ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_LOWERCASE_ID) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalKactors.g:668:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    {
                    // InternalKactors.g:668:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:669:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:669:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:670:6: lv_ids_2_0= RULE_LOWERCASE_ID
                    {
                    lv_ids_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_15); if (state.failed) return current;
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

                    // InternalKactors.g:686:4: (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==34) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // InternalKactors.g:687:5: otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    {
                    	    otherlv_3=(Token)match(input,34,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getArgumentDeclarationAccess().getCommaKeyword_2_1_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:691:5: ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    // InternalKactors.g:692:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    {
                    	    // InternalKactors.g:692:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    // InternalKactors.g:693:7: lv_ids_4_0= RULE_LOWERCASE_ID
                    	    {
                    	    lv_ids_4_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_15); if (state.failed) return current;
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
                    	    break loop9;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,35,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:719:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKactors.g:719:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKactors.g:720:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKactors.g:726:1: ruleParameterList returns [EObject current=null] : ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) ;
    public final EObject ruleParameterList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_pairs_0_0 = null;

        EObject lv_pairs_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:732:2: ( ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) )
            // InternalKactors.g:733:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            {
            // InternalKactors.g:733:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            // InternalKactors.g:734:3: ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            {
            // InternalKactors.g:734:3: ( (lv_pairs_0_0= ruleKeyValuePair ) )
            // InternalKactors.g:735:4: (lv_pairs_0_0= ruleKeyValuePair )
            {
            // InternalKactors.g:735:4: (lv_pairs_0_0= ruleKeyValuePair )
            // InternalKactors.g:736:5: lv_pairs_0_0= ruleKeyValuePair
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_16);
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

            // InternalKactors.g:753:3: ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==34) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalKactors.g:754:4: ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    {
            	    // InternalKactors.g:754:4: ( ( ',' )=>otherlv_1= ',' )
            	    // InternalKactors.g:755:5: ( ',' )=>otherlv_1= ','
            	    {
            	    otherlv_1=(Token)match(input,34,FOLLOW_17); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_1_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:761:4: ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    // InternalKactors.g:762:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    {
            	    // InternalKactors.g:762:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    // InternalKactors.g:763:6: lv_pairs_2_0= ruleKeyValuePair
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_16);
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
            	    break loop11;
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
    // InternalKactors.g:785:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKactors.g:785:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKactors.g:786:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKactors.g:792:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:798:2: ( ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKactors.g:799:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKactors.g:799:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            // InternalKactors.g:800:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKactors.g:800:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==RULE_LOWERCASE_ID) ) {
                int LA13_1 = input.LA(2);

                if ( ((LA13_1>=36 && LA13_1<=37)) ) {
                    alt13=1;
                }
            }
            switch (alt13) {
                case 1 :
                    // InternalKactors.g:801:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    {
                    // InternalKactors.g:801:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:802:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:802:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:803:6: lv_name_0_0= RULE_LOWERCASE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_18); if (state.failed) return current;
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

                    // InternalKactors.g:819:4: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==36) ) {
                        alt12=1;
                    }
                    else if ( (LA12_0==37) ) {
                        alt12=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 0, input);

                        throw nvae;
                    }
                    switch (alt12) {
                        case 1 :
                            // InternalKactors.g:820:5: ( (lv_interactive_1_0= '=?' ) )
                            {
                            // InternalKactors.g:820:5: ( (lv_interactive_1_0= '=?' ) )
                            // InternalKactors.g:821:6: (lv_interactive_1_0= '=?' )
                            {
                            // InternalKactors.g:821:6: (lv_interactive_1_0= '=?' )
                            // InternalKactors.g:822:7: lv_interactive_1_0= '=?'
                            {
                            lv_interactive_1_0=(Token)match(input,36,FOLLOW_17); if (state.failed) return current;
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
                            // InternalKactors.g:835:5: otherlv_2= '='
                            {
                            otherlv_2=(Token)match(input,37,FOLLOW_17); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_0_1_1());
                              				
                            }

                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalKactors.g:841:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:842:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:842:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:843:5: lv_value_3_0= ruleValue
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
    // InternalKactors.g:864:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKactors.g:864:46: (iv_ruleValue= ruleValue EOF )
            // InternalKactors.g:865:2: iv_ruleValue= ruleValue EOF
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
    // InternalKactors.g:871:1: ruleValue returns [EObject current=null] : ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_urn_3_0= ruleUrn ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) ) ;
    public final EObject ruleValue() throws RecognitionException {
        EObject current = null;

        Token lv_argvalue_0_0=null;
        Token lv_id_2_0=null;
        Token lv_observable_6_0=null;
        Token lv_expression_7_0=null;
        EObject lv_literal_1_0 = null;

        EObject lv_urn_3_0 = null;

        EObject lv_list_4_0 = null;

        EObject lv_map_5_0 = null;

        EObject lv_table_8_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:877:2: ( ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_urn_3_0= ruleUrn ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) ) )
            // InternalKactors.g:878:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_urn_3_0= ruleUrn ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )
            {
            // InternalKactors.g:878:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_urn_3_0= ruleUrn ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )
            int alt14=9;
            alt14 = dfa14.predict(input);
            switch (alt14) {
                case 1 :
                    // InternalKactors.g:879:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    {
                    // InternalKactors.g:879:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    // InternalKactors.g:880:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    {
                    // InternalKactors.g:880:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    // InternalKactors.g:881:5: lv_argvalue_0_0= RULE_ARGVALUE
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
                    // InternalKactors.g:898:3: ( (lv_literal_1_0= ruleLiteral ) )
                    {
                    // InternalKactors.g:898:3: ( (lv_literal_1_0= ruleLiteral ) )
                    // InternalKactors.g:899:4: (lv_literal_1_0= ruleLiteral )
                    {
                    // InternalKactors.g:899:4: (lv_literal_1_0= ruleLiteral )
                    // InternalKactors.g:900:5: lv_literal_1_0= ruleLiteral
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
                    // InternalKactors.g:918:3: ( (lv_id_2_0= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKactors.g:918:3: ( (lv_id_2_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:919:4: (lv_id_2_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:919:4: (lv_id_2_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:920:5: lv_id_2_0= RULE_LOWERCASE_ID
                    {
                    lv_id_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_id_2_0, grammarAccess.getValueAccess().getIdLOWERCASE_IDTerminalRuleCall_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"id",
                      						lv_id_2_0,
                      						"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:937:3: ( (lv_urn_3_0= ruleUrn ) )
                    {
                    // InternalKactors.g:937:3: ( (lv_urn_3_0= ruleUrn ) )
                    // InternalKactors.g:938:4: (lv_urn_3_0= ruleUrn )
                    {
                    // InternalKactors.g:938:4: (lv_urn_3_0= ruleUrn )
                    // InternalKactors.g:939:5: lv_urn_3_0= ruleUrn
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getUrnUrnParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_urn_3_0=ruleUrn();

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
                      						"org.integratedmodelling.kactors.Kactors.Urn");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:957:3: ( (lv_list_4_0= ruleList ) )
                    {
                    // InternalKactors.g:957:3: ( (lv_list_4_0= ruleList ) )
                    // InternalKactors.g:958:4: (lv_list_4_0= ruleList )
                    {
                    // InternalKactors.g:958:4: (lv_list_4_0= ruleList )
                    // InternalKactors.g:959:5: lv_list_4_0= ruleList
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
                    // InternalKactors.g:977:3: ( (lv_map_5_0= ruleMap ) )
                    {
                    // InternalKactors.g:977:3: ( (lv_map_5_0= ruleMap ) )
                    // InternalKactors.g:978:4: (lv_map_5_0= ruleMap )
                    {
                    // InternalKactors.g:978:4: (lv_map_5_0= ruleMap )
                    // InternalKactors.g:979:5: lv_map_5_0= ruleMap
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
                    // InternalKactors.g:997:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:997:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:998:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:998:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    // InternalKactors.g:999:5: lv_observable_6_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:1016:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:1016:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    // InternalKactors.g:1017:4: (lv_expression_7_0= RULE_EXPR )
                    {
                    // InternalKactors.g:1017:4: (lv_expression_7_0= RULE_EXPR )
                    // InternalKactors.g:1018:5: lv_expression_7_0= RULE_EXPR
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
                    // InternalKactors.g:1035:3: ( (lv_table_8_0= ruleLookupTable ) )
                    {
                    // InternalKactors.g:1035:3: ( (lv_table_8_0= ruleLookupTable ) )
                    // InternalKactors.g:1036:4: (lv_table_8_0= ruleLookupTable )
                    {
                    // InternalKactors.g:1036:4: (lv_table_8_0= ruleLookupTable )
                    // InternalKactors.g:1037:5: lv_table_8_0= ruleLookupTable
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


    // $ANTLR start "entryRuleUrn"
    // InternalKactors.g:1058:1: entryRuleUrn returns [EObject current=null] : iv_ruleUrn= ruleUrn EOF ;
    public final EObject entryRuleUrn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUrn = null;


        try {
            // InternalKactors.g:1058:44: (iv_ruleUrn= ruleUrn EOF )
            // InternalKactors.g:1059:2: iv_ruleUrn= ruleUrn EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUrnRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUrn=ruleUrn();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUrn; 
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
    // $ANTLR end "entryRuleUrn"


    // $ANTLR start "ruleUrn"
    // InternalKactors.g:1065:1: ruleUrn returns [EObject current=null] : ( ( (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING ) ) ) ;
    public final EObject ruleUrn() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_3=null;
        AntlrDatatypeRuleToken lv_name_0_1 = null;

        AntlrDatatypeRuleToken lv_name_0_2 = null;



        	enterRule();

        try {
            // InternalKactors.g:1071:2: ( ( ( (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING ) ) ) )
            // InternalKactors.g:1072:2: ( ( (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING ) ) )
            {
            // InternalKactors.g:1072:2: ( ( (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING ) ) )
            // InternalKactors.g:1073:3: ( (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING ) )
            {
            // InternalKactors.g:1073:3: ( (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING ) )
            // InternalKactors.g:1074:4: (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING )
            {
            // InternalKactors.g:1074:4: (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING )
            int alt15=3;
            alt15 = dfa15.predict(input);
            switch (alt15) {
                case 1 :
                    // InternalKactors.g:1075:5: lv_name_0_1= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUrnAccess().getNamePathNameParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_name_0_1=rulePathName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getUrnRule());
                      					}
                      					set(
                      						current,
                      						"name",
                      						lv_name_0_1,
                      						"org.integratedmodelling.kactors.Kactors.PathName");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:1091:5: lv_name_0_2= ruleUrnId
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUrnAccess().getNameUrnIdParserRuleCall_0_1());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_name_0_2=ruleUrnId();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getUrnRule());
                      					}
                      					set(
                      						current,
                      						"name",
                      						lv_name_0_2,
                      						"org.integratedmodelling.kactors.Kactors.UrnId");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }
                    break;
                case 3 :
                    // InternalKactors.g:1107:5: lv_name_0_3= RULE_STRING
                    {
                    lv_name_0_3=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_name_0_3, grammarAccess.getUrnAccess().getNameSTRINGTerminalRuleCall_0_2());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getUrnRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"name",
                      						lv_name_0_3,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }
                    break;

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
    // $ANTLR end "ruleUrn"


    // $ANTLR start "entryRuleAnnotation"
    // InternalKactors.g:1127:1: entryRuleAnnotation returns [EObject current=null] : iv_ruleAnnotation= ruleAnnotation EOF ;
    public final EObject entryRuleAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnnotation = null;


        try {
            // InternalKactors.g:1127:51: (iv_ruleAnnotation= ruleAnnotation EOF )
            // InternalKactors.g:1128:2: iv_ruleAnnotation= ruleAnnotation EOF
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
    // InternalKactors.g:1134:1: ruleAnnotation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) ;
    public final EObject ruleAnnotation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1140:2: ( ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) )
            // InternalKactors.g:1141:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            {
            // InternalKactors.g:1141:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            // InternalKactors.g:1142:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            {
            // InternalKactors.g:1142:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) )
            // InternalKactors.g:1143:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            {
            // InternalKactors.g:1143:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            // InternalKactors.g:1144:5: lv_name_0_0= RULE_ANNOTATION_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ANNOTATION_ID,FOLLOW_19); if (state.failed) return current;
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

            // InternalKactors.g:1160:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==33) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalKactors.g:1161:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,33,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getAnnotationAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:1165:4: ( (lv_parameters_2_0= ruleParameterList ) )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==RULE_LOWERCASE_ID||(LA16_0>=RULE_STRING && LA16_0<=RULE_EXPR)||LA16_0==RULE_INT||LA16_0==33||LA16_0==38||LA16_0==41||(LA16_0>=43 && LA16_0<=44)||LA16_0==51||(LA16_0>=66 && LA16_0<=67)) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // InternalKactors.g:1166:5: (lv_parameters_2_0= ruleParameterList )
                            {
                            // InternalKactors.g:1166:5: (lv_parameters_2_0= ruleParameterList )
                            // InternalKactors.g:1167:6: lv_parameters_2_0= ruleParameterList
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

                    otherlv_3=(Token)match(input,35,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:1193:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKactors.g:1193:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKactors.g:1194:2: iv_ruleUrnId= ruleUrnId EOF
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
    // InternalKactors.g:1200:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) ;
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
            // InternalKactors.g:1206:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) )
            // InternalKactors.g:1207:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            {
            // InternalKactors.g:1207:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            // InternalKactors.g:1208:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            {
            // InternalKactors.g:1208:3: (kw= 'urn:klab:' )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==38) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalKactors.g:1209:4: kw= 'urn:klab:'
                    {
                    kw=(Token)match(input,38,FOLLOW_4); if (state.failed) return current;
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
            pushFollow(FOLLOW_12);
            this_PathName_1=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_1);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,32,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_12);
            this_PathName_3=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_3);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,32,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_4());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnIdAccess().getPathNameParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_12);
            this_PathName_5=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_5);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,32,FOLLOW_22); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            // InternalKactors.g:1260:3: (this_Path_7= rulePath | this_INT_8= RULE_INT )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==RULE_LOWERCASE_ID||LA19_0==RULE_UPPERCASE_ID) ) {
                alt19=1;
            }
            else if ( (LA19_0==RULE_INT) ) {
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
                    // InternalKactors.g:1261:4: this_Path_7= rulePath
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_7_0());
                      			
                    }
                    pushFollow(FOLLOW_23);
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
                    // InternalKactors.g:1272:4: this_INT_8= RULE_INT
                    {
                    this_INT_8=(Token)match(input,RULE_INT,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_8);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_8, grammarAccess.getUrnIdAccess().getINTTerminalRuleCall_7_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:1280:3: (kw= ':' this_VersionNumber_10= ruleVersionNumber )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==32) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalKactors.g:1281:4: kw= ':' this_VersionNumber_10= ruleVersionNumber
                    {
                    kw=(Token)match(input,32,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_8_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getVersionNumberParserRuleCall_8_1());
                      			
                    }
                    pushFollow(FOLLOW_24);
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

            // InternalKactors.g:1297:3: (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==39) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalKactors.g:1298:4: kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    {
                    kw=(Token)match(input,39,FOLLOW_25); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getNumberSignKeyword_9_0());
                      			
                    }
                    // InternalKactors.g:1303:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )
                    int alt21=2;
                    alt21 = dfa21.predict(input);
                    switch (alt21) {
                        case 1 :
                            // InternalKactors.g:1304:5: this_Path_12= rulePath
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_1_0());
                              				
                            }
                            pushFollow(FOLLOW_26);
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
                            // InternalKactors.g:1315:5: this_UrnKvp_13= ruleUrnKvp
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_1_1());
                              				
                            }
                            pushFollow(FOLLOW_26);
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

                    // InternalKactors.g:1326:4: (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    loop23:
                    do {
                        int alt23=2;
                        int LA23_0 = input.LA(1);

                        if ( (LA23_0==40) ) {
                            alt23=1;
                        }


                        switch (alt23) {
                    	case 1 :
                    	    // InternalKactors.g:1327:5: kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    {
                    	    kw=(Token)match(input,40,FOLLOW_25); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(kw);
                    	      					newLeafNode(kw, grammarAccess.getUrnIdAccess().getAmpersandKeyword_9_2_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:1332:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    int alt22=2;
                    	    alt22 = dfa22.predict(input);
                    	    switch (alt22) {
                    	        case 1 :
                    	            // InternalKactors.g:1333:6: this_Path_15= rulePath
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_2_1_0());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_26);
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
                    	            // InternalKactors.g:1344:6: this_UrnKvp_16= ruleUrnKvp
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_2_1_1());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_26);
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
                    	    break loop23;
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
    // InternalKactors.g:1361:1: entryRuleUrnKvp returns [String current=null] : iv_ruleUrnKvp= ruleUrnKvp EOF ;
    public final String entryRuleUrnKvp() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnKvp = null;


        try {
            // InternalKactors.g:1361:46: (iv_ruleUrnKvp= ruleUrnKvp EOF )
            // InternalKactors.g:1362:2: iv_ruleUrnKvp= ruleUrnKvp EOF
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
    // InternalKactors.g:1368:1: ruleUrnKvp returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) ;
    public final AntlrDatatypeRuleToken ruleUrnKvp() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;

        AntlrDatatypeRuleToken this_Path_2 = null;



        	enterRule();

        try {
            // InternalKactors.g:1374:2: ( (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) )
            // InternalKactors.g:1375:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            {
            // InternalKactors.g:1375:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            // InternalKactors.g:1376:3: this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnKvpAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_27);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,37,FOLLOW_22); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnKvpAccess().getEqualsSignKeyword_1());
              		
            }
            // InternalKactors.g:1391:3: (this_Path_2= rulePath | this_INT_3= RULE_INT )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==RULE_LOWERCASE_ID||LA25_0==RULE_UPPERCASE_ID) ) {
                alt25=1;
            }
            else if ( (LA25_0==RULE_INT) ) {
                alt25=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // InternalKactors.g:1392:4: this_Path_2= rulePath
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
                    // InternalKactors.g:1403:4: this_INT_3= RULE_INT
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
    // InternalKactors.g:1415:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKactors.g:1415:45: (iv_ruleList= ruleList EOF )
            // InternalKactors.g:1416:2: iv_ruleList= ruleList EOF
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
    // InternalKactors.g:1422:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1428:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKactors.g:1429:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKactors.g:1429:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKactors.g:1430:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKactors.g:1430:3: ()
            // InternalKactors.g:1431:4: 
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

            otherlv_1=(Token)match(input,33,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getListAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:1444:3: ( (lv_contents_2_0= ruleValue ) )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==RULE_LOWERCASE_ID||(LA26_0>=RULE_STRING && LA26_0<=RULE_EXPR)||LA26_0==RULE_INT||LA26_0==33||LA26_0==38||LA26_0==41||(LA26_0>=43 && LA26_0<=44)||LA26_0==51||(LA26_0>=66 && LA26_0<=67)) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalKactors.g:1445:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKactors.g:1445:4: (lv_contents_2_0= ruleValue )
            	    // InternalKactors.g:1446:5: lv_contents_2_0= ruleValue
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
            	    break loop26;
                }
            } while (true);

            otherlv_3=(Token)match(input,35,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:1471:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // InternalKactors.g:1471:44: (iv_ruleMap= ruleMap EOF )
            // InternalKactors.g:1472:2: iv_ruleMap= ruleMap EOF
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
    // InternalKactors.g:1478:1: ruleMap returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_entries_2_0 = null;

        EObject lv_entries_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1484:2: ( ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) )
            // InternalKactors.g:1485:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            {
            // InternalKactors.g:1485:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            // InternalKactors.g:1486:3: () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}'
            {
            // InternalKactors.g:1486:3: ()
            // InternalKactors.g:1487:4: 
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

            otherlv_1=(Token)match(input,41,FOLLOW_28); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:1500:3: ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==RULE_LOWERCASE_ID||LA28_0==RULE_STRING||LA28_0==RULE_OBSERVABLE||LA28_0==RULE_INT||LA28_0==37||(LA28_0>=43 && LA28_0<=44)||(LA28_0>=48 && LA28_0<=50)||(LA28_0>=57 && LA28_0<=61)||(LA28_0>=66 && LA28_0<=67)) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalKactors.g:1501:4: ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    {
                    // InternalKactors.g:1501:4: ( (lv_entries_2_0= ruleMapEntry ) )
                    // InternalKactors.g:1502:5: (lv_entries_2_0= ruleMapEntry )
                    {
                    // InternalKactors.g:1502:5: (lv_entries_2_0= ruleMapEntry )
                    // InternalKactors.g:1503:6: lv_entries_2_0= ruleMapEntry
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_29);
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

                    // InternalKactors.g:1520:4: ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    loop27:
                    do {
                        int alt27=2;
                        int LA27_0 = input.LA(1);

                        if ( (LA27_0==34) ) {
                            alt27=1;
                        }


                        switch (alt27) {
                    	case 1 :
                    	    // InternalKactors.g:1521:5: ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) )
                    	    {
                    	    // InternalKactors.g:1521:5: ( ( ',' )=>otherlv_3= ',' )
                    	    // InternalKactors.g:1522:6: ( ',' )=>otherlv_3= ','
                    	    {
                    	    otherlv_3=(Token)match(input,34,FOLLOW_30); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_3, grammarAccess.getMapAccess().getCommaKeyword_2_1_0_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKactors.g:1529:5: ( (lv_entries_4_0= ruleMapEntry ) )
                    	    // InternalKactors.g:1530:6: (lv_entries_4_0= ruleMapEntry )
                    	    {
                    	    // InternalKactors.g:1530:6: (lv_entries_4_0= ruleMapEntry )
                    	    // InternalKactors.g:1531:7: lv_entries_4_0= ruleMapEntry
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_29);
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
                    	    break loop27;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:1558:1: entryRuleMapEntry returns [EObject current=null] : iv_ruleMapEntry= ruleMapEntry EOF ;
    public final EObject entryRuleMapEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapEntry = null;


        try {
            // InternalKactors.g:1558:49: (iv_ruleMapEntry= ruleMapEntry EOF )
            // InternalKactors.g:1559:2: iv_ruleMapEntry= ruleMapEntry EOF
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
    // InternalKactors.g:1565:1: ruleMapEntry returns [EObject current=null] : ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleMapEntry() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifier_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1571:2: ( ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKactors.g:1572:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKactors.g:1572:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // InternalKactors.g:1573:3: ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalKactors.g:1573:3: ( (lv_classifier_0_0= ruleClassifier ) )
            // InternalKactors.g:1574:4: (lv_classifier_0_0= ruleClassifier )
            {
            // InternalKactors.g:1574:4: (lv_classifier_0_0= ruleClassifier )
            // InternalKactors.g:1575:5: lv_classifier_0_0= ruleClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMapEntryAccess().getClassifierClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_12);
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

            otherlv_1=(Token)match(input,32,FOLLOW_17); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapEntryAccess().getColonKeyword_1());
              		
            }
            // InternalKactors.g:1596:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKactors.g:1597:4: (lv_value_2_0= ruleValue )
            {
            // InternalKactors.g:1597:4: (lv_value_2_0= ruleValue )
            // InternalKactors.g:1598:5: lv_value_2_0= ruleValue
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
    // InternalKactors.g:1619:1: entryRuleClassifier returns [EObject current=null] : iv_ruleClassifier= ruleClassifier EOF ;
    public final EObject entryRuleClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifier = null;


        try {
            // InternalKactors.g:1619:51: (iv_ruleClassifier= ruleClassifier EOF )
            // InternalKactors.g:1620:2: iv_ruleClassifier= ruleClassifier EOF
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
    // InternalKactors.g:1626:1: ruleClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) ;
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
            // InternalKactors.g:1632:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) )
            // InternalKactors.g:1633:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            {
            // InternalKactors.g:1633:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            int alt32=10;
            alt32 = dfa32.predict(input);
            switch (alt32) {
                case 1 :
                    // InternalKactors.g:1634:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:1634:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==43) ) {
                        alt29=1;
                    }
                    else if ( (LA29_0==44) ) {
                        alt29=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 29, 0, input);

                        throw nvae;
                    }
                    switch (alt29) {
                        case 1 :
                            // InternalKactors.g:1635:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:1635:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:1636:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:1636:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:1637:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:1650:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:1650:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:1651:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:1651:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:1652:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:1666:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKactors.g:1666:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKactors.g:1667:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKactors.g:1667:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKactors.g:1668:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKactors.g:1668:5: (lv_int0_2_0= ruleNumber )
                    // InternalKactors.g:1669:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_31);
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

                    // InternalKactors.g:1686:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt30=3;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==45) ) {
                        alt30=1;
                    }
                    else if ( (LA30_0==46) ) {
                        alt30=2;
                    }
                    switch (alt30) {
                        case 1 :
                            // InternalKactors.g:1687:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:1687:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKactors.g:1688:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKactors.g:1688:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKactors.g:1689:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,45,FOLLOW_32); if (state.failed) return current;
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
                            // InternalKactors.g:1702:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,46,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:1707:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKactors.g:1708:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,47,FOLLOW_33); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKactors.g:1714:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKactors.g:1715:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKactors.g:1719:5: (lv_int1_6_0= ruleNumber )
                    // InternalKactors.g:1720:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_34);
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

                    // InternalKactors.g:1737:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt31=3;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==45) ) {
                        alt31=1;
                    }
                    else if ( (LA31_0==46) ) {
                        alt31=2;
                    }
                    switch (alt31) {
                        case 1 :
                            // InternalKactors.g:1738:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:1738:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKactors.g:1739:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKactors.g:1739:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKactors.g:1740:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:1753:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:1760:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKactors.g:1760:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKactors.g:1761:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKactors.g:1761:4: (lv_num_9_0= ruleNumber )
                    // InternalKactors.g:1762:5: lv_num_9_0= ruleNumber
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
                    // InternalKactors.g:1780:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKactors.g:1780:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKactors.g:1781:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,48,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:1785:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKactors.g:1786:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKactors.g:1786:5: (lv_set_11_0= ruleList )
                    // InternalKactors.g:1787:6: lv_set_11_0= ruleList
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
                    // InternalKactors.g:1806:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:1806:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKactors.g:1807:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKactors.g:1807:4: (lv_string_12_0= RULE_STRING )
                    // InternalKactors.g:1808:5: lv_string_12_0= RULE_STRING
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
                    // InternalKactors.g:1825:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:1825:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:1826:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:1826:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    // InternalKactors.g:1827:5: lv_observable_13_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:1844:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKactors.g:1844:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:1845:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:1845:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:1846:5: lv_id_14_0= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:1863:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:1863:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    // InternalKactors.g:1864:4: ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) )
                    {
                    // InternalKactors.g:1864:4: ( (lv_op_15_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:1865:5: (lv_op_15_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:1865:5: (lv_op_15_0= ruleREL_OPERATOR )
                    // InternalKactors.g:1866:6: lv_op_15_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getOpREL_OPERATORParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_33);
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

                    // InternalKactors.g:1883:4: ( (lv_expression_16_0= ruleNumber ) )
                    // InternalKactors.g:1884:5: (lv_expression_16_0= ruleNumber )
                    {
                    // InternalKactors.g:1884:5: (lv_expression_16_0= ruleNumber )
                    // InternalKactors.g:1885:6: lv_expression_16_0= ruleNumber
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
                    // InternalKactors.g:1904:3: ( (lv_nodata_17_0= 'unknown' ) )
                    {
                    // InternalKactors.g:1904:3: ( (lv_nodata_17_0= 'unknown' ) )
                    // InternalKactors.g:1905:4: (lv_nodata_17_0= 'unknown' )
                    {
                    // InternalKactors.g:1905:4: (lv_nodata_17_0= 'unknown' )
                    // InternalKactors.g:1906:5: lv_nodata_17_0= 'unknown'
                    {
                    lv_nodata_17_0=(Token)match(input,49,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:1919:3: ( (lv_star_18_0= '*' ) )
                    {
                    // InternalKactors.g:1919:3: ( (lv_star_18_0= '*' ) )
                    // InternalKactors.g:1920:4: (lv_star_18_0= '*' )
                    {
                    // InternalKactors.g:1920:4: (lv_star_18_0= '*' )
                    // InternalKactors.g:1921:5: lv_star_18_0= '*'
                    {
                    lv_star_18_0=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:1937:1: entryRuleLookupTable returns [EObject current=null] : iv_ruleLookupTable= ruleLookupTable EOF ;
    public final EObject entryRuleLookupTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookupTable = null;


        try {
            // InternalKactors.g:1937:52: (iv_ruleLookupTable= ruleLookupTable EOF )
            // InternalKactors.g:1938:2: iv_ruleLookupTable= ruleLookupTable EOF
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
    // InternalKactors.g:1944:1: ruleLookupTable returns [EObject current=null] : ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) ;
    public final EObject ruleLookupTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_table_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1950:2: ( ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) )
            // InternalKactors.g:1951:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            {
            // InternalKactors.g:1951:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            // InternalKactors.g:1952:3: () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}'
            {
            // InternalKactors.g:1952:3: ()
            // InternalKactors.g:1953:4: 
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

            otherlv_1=(Token)match(input,51,FOLLOW_36); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLookupTableAccess().getLeftCurlyBracketLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:1966:3: ( (lv_table_2_0= ruleTable ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==RULE_LOWERCASE_ID||LA33_0==RULE_STRING||(LA33_0>=RULE_OBSERVABLE && LA33_0<=RULE_EXPR)||LA33_0==RULE_INT||LA33_0==37||LA33_0==39||(LA33_0>=43 && LA33_0<=44)||(LA33_0>=48 && LA33_0<=50)||(LA33_0>=57 && LA33_0<=61)||(LA33_0>=66 && LA33_0<=67)) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalKactors.g:1967:4: (lv_table_2_0= ruleTable )
                    {
                    // InternalKactors.g:1967:4: (lv_table_2_0= ruleTable )
                    // InternalKactors.g:1968:5: lv_table_2_0= ruleTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLookupTableAccess().getTableTableParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_37);
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

            otherlv_3=(Token)match(input,52,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:1993:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalKactors.g:1993:46: (iv_ruleTable= ruleTable EOF )
            // InternalKactors.g:1994:2: iv_ruleTable= ruleTable EOF
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
    // InternalKactors.g:2000:1: ruleTable returns [EObject current=null] : ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token this_SEPARATOR_1=null;
        Token otherlv_3=null;
        EObject lv_headers_0_0 = null;

        EObject lv_rows_2_0 = null;

        EObject lv_rows_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2006:2: ( ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) )
            // InternalKactors.g:2007:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            {
            // InternalKactors.g:2007:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            // InternalKactors.g:2008:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            {
            // InternalKactors.g:2008:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?
            int alt34=2;
            alt34 = dfa34.predict(input);
            switch (alt34) {
                case 1 :
                    // InternalKactors.g:2009:4: ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR
                    {
                    // InternalKactors.g:2009:4: ( (lv_headers_0_0= ruleHeaderRow ) )
                    // InternalKactors.g:2010:5: (lv_headers_0_0= ruleHeaderRow )
                    {
                    // InternalKactors.g:2010:5: (lv_headers_0_0= ruleHeaderRow )
                    // InternalKactors.g:2011:6: lv_headers_0_0= ruleHeaderRow
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableAccess().getHeadersHeaderRowParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_38);
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

                    this_SEPARATOR_1=(Token)match(input,RULE_SEPARATOR,FOLLOW_39); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SEPARATOR_1, grammarAccess.getTableAccess().getSEPARATORTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:2033:3: ( (lv_rows_2_0= ruleTableRow ) )
            // InternalKactors.g:2034:4: (lv_rows_2_0= ruleTableRow )
            {
            // InternalKactors.g:2034:4: (lv_rows_2_0= ruleTableRow )
            // InternalKactors.g:2035:5: lv_rows_2_0= ruleTableRow
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_16);
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

            // InternalKactors.g:2052:3: (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==34) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalKactors.g:2053:4: otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) )
            	    {
            	    otherlv_3=(Token)match(input,34,FOLLOW_39); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getTableAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKactors.g:2057:4: ( (lv_rows_4_0= ruleTableRow ) )
            	    // InternalKactors.g:2058:5: (lv_rows_4_0= ruleTableRow )
            	    {
            	    // InternalKactors.g:2058:5: (lv_rows_4_0= ruleTableRow )
            	    // InternalKactors.g:2059:6: lv_rows_4_0= ruleTableRow
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_16);
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
            	    break loop35;
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
    // InternalKactors.g:2081:1: entryRuleHeaderRow returns [EObject current=null] : iv_ruleHeaderRow= ruleHeaderRow EOF ;
    public final EObject entryRuleHeaderRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHeaderRow = null;


        try {
            // InternalKactors.g:2081:50: (iv_ruleHeaderRow= ruleHeaderRow EOF )
            // InternalKactors.g:2082:2: iv_ruleHeaderRow= ruleHeaderRow EOF
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
    // InternalKactors.g:2088:1: ruleHeaderRow returns [EObject current=null] : ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) ;
    public final EObject ruleHeaderRow() throws RecognitionException {
        EObject current = null;

        Token lv_elements_0_1=null;
        Token lv_elements_0_2=null;
        Token otherlv_1=null;
        Token lv_elements_2_1=null;
        Token lv_elements_2_2=null;


        	enterRule();

        try {
            // InternalKactors.g:2094:2: ( ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) )
            // InternalKactors.g:2095:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            {
            // InternalKactors.g:2095:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            // InternalKactors.g:2096:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            {
            // InternalKactors.g:2096:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) )
            // InternalKactors.g:2097:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            {
            // InternalKactors.g:2097:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            // InternalKactors.g:2098:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            {
            // InternalKactors.g:2098:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==RULE_LOWERCASE_ID) ) {
                alt36=1;
            }
            else if ( (LA36_0==RULE_STRING) ) {
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
                    // InternalKactors.g:2099:6: lv_elements_0_1= RULE_LOWERCASE_ID
                    {
                    lv_elements_0_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_40); if (state.failed) return current;
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
                    // InternalKactors.g:2114:6: lv_elements_0_2= RULE_STRING
                    {
                    lv_elements_0_2=(Token)match(input,RULE_STRING,FOLLOW_40); if (state.failed) return current;
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

            // InternalKactors.g:2131:3: (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==53) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalKactors.g:2132:4: otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    {
            	    otherlv_1=(Token)match(input,53,FOLLOW_41); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getHeaderRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:2136:4: ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    // InternalKactors.g:2137:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:2137:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    // InternalKactors.g:2138:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    {
            	    // InternalKactors.g:2138:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    int alt37=2;
            	    int LA37_0 = input.LA(1);

            	    if ( (LA37_0==RULE_LOWERCASE_ID) ) {
            	        alt37=1;
            	    }
            	    else if ( (LA37_0==RULE_STRING) ) {
            	        alt37=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 37, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt37) {
            	        case 1 :
            	            // InternalKactors.g:2139:7: lv_elements_2_1= RULE_LOWERCASE_ID
            	            {
            	            lv_elements_2_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_40); if (state.failed) return current;
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
            	            // InternalKactors.g:2154:7: lv_elements_2_2= RULE_STRING
            	            {
            	            lv_elements_2_2=(Token)match(input,RULE_STRING,FOLLOW_40); if (state.failed) return current;
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
            	    break loop38;
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
    // InternalKactors.g:2176:1: entryRuleTableRow returns [EObject current=null] : iv_ruleTableRow= ruleTableRow EOF ;
    public final EObject entryRuleTableRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableRow = null;


        try {
            // InternalKactors.g:2176:49: (iv_ruleTableRow= ruleTableRow EOF )
            // InternalKactors.g:2177:2: iv_ruleTableRow= ruleTableRow EOF
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
    // InternalKactors.g:2183:1: ruleTableRow returns [EObject current=null] : ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) ;
    public final EObject ruleTableRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_elements_0_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2189:2: ( ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) )
            // InternalKactors.g:2190:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            {
            // InternalKactors.g:2190:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            // InternalKactors.g:2191:3: ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            {
            // InternalKactors.g:2191:3: ( (lv_elements_0_0= ruleTableClassifier ) )
            // InternalKactors.g:2192:4: (lv_elements_0_0= ruleTableClassifier )
            {
            // InternalKactors.g:2192:4: (lv_elements_0_0= ruleTableClassifier )
            // InternalKactors.g:2193:5: lv_elements_0_0= ruleTableClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_40);
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

            // InternalKactors.g:2210:3: (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==53) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalKactors.g:2211:4: otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) )
            	    {
            	    otherlv_1=(Token)match(input,53,FOLLOW_39); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:2215:4: ( (lv_elements_2_0= ruleTableClassifier ) )
            	    // InternalKactors.g:2216:5: (lv_elements_2_0= ruleTableClassifier )
            	    {
            	    // InternalKactors.g:2216:5: (lv_elements_2_0= ruleTableClassifier )
            	    // InternalKactors.g:2217:6: lv_elements_2_0= ruleTableClassifier
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_40);
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
            	    break loop39;
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
    // InternalKactors.g:2239:1: entryRuleTableClassifier returns [EObject current=null] : iv_ruleTableClassifier= ruleTableClassifier EOF ;
    public final EObject entryRuleTableClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableClassifier = null;


        try {
            // InternalKactors.g:2239:56: (iv_ruleTableClassifier= ruleTableClassifier EOF )
            // InternalKactors.g:2240:2: iv_ruleTableClassifier= ruleTableClassifier EOF
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
    // InternalKactors.g:2246:1: ruleTableClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | ( (lv_quantity_10_0= ruleQuantity ) ) | ( (lv_date_11_0= ruleDate ) ) | (otherlv_12= 'in' ( (lv_set_13_0= ruleList ) ) ) | ( (lv_string_14_0= RULE_STRING ) ) | ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_16_0= ruleREL_OPERATOR ) ) ( (lv_expression_17_0= ruleNumber ) ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) ;
    public final EObject ruleTableClassifier() throws RecognitionException {
        EObject current = null;

        Token lv_boolean_0_0=null;
        Token lv_boolean_1_0=null;
        Token lv_leftLimit_3_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_rightLimit_7_0=null;
        Token otherlv_8=null;
        Token otherlv_12=null;
        Token lv_string_14_0=null;
        Token lv_observable_15_0=null;
        Token lv_expr_18_0=null;
        Token lv_nodata_19_0=null;
        Token lv_star_20_0=null;
        Token lv_anything_21_0=null;
        EObject lv_int0_2_0 = null;

        EObject lv_int1_6_0 = null;

        EObject lv_num_9_0 = null;

        EObject lv_quantity_10_0 = null;

        EObject lv_date_11_0 = null;

        EObject lv_set_13_0 = null;

        EObject lv_op_16_0 = null;

        EObject lv_expression_17_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2252:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | ( (lv_quantity_10_0= ruleQuantity ) ) | ( (lv_date_11_0= ruleDate ) ) | (otherlv_12= 'in' ( (lv_set_13_0= ruleList ) ) ) | ( (lv_string_14_0= RULE_STRING ) ) | ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_16_0= ruleREL_OPERATOR ) ) ( (lv_expression_17_0= ruleNumber ) ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) )
            // InternalKactors.g:2253:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | ( (lv_quantity_10_0= ruleQuantity ) ) | ( (lv_date_11_0= ruleDate ) ) | (otherlv_12= 'in' ( (lv_set_13_0= ruleList ) ) ) | ( (lv_string_14_0= RULE_STRING ) ) | ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_16_0= ruleREL_OPERATOR ) ) ( (lv_expression_17_0= ruleNumber ) ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            {
            // InternalKactors.g:2253:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | ( (lv_quantity_10_0= ruleQuantity ) ) | ( (lv_date_11_0= ruleDate ) ) | (otherlv_12= 'in' ( (lv_set_13_0= ruleList ) ) ) | ( (lv_string_14_0= RULE_STRING ) ) | ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_16_0= ruleREL_OPERATOR ) ) ( (lv_expression_17_0= ruleNumber ) ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            int alt43=13;
            alt43 = dfa43.predict(input);
            switch (alt43) {
                case 1 :
                    // InternalKactors.g:2254:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:2254:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==43) ) {
                        alt40=1;
                    }
                    else if ( (LA40_0==44) ) {
                        alt40=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 40, 0, input);

                        throw nvae;
                    }
                    switch (alt40) {
                        case 1 :
                            // InternalKactors.g:2255:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:2255:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:2256:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:2256:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:2257:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:2270:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:2270:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:2271:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:2271:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:2272:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:2286:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKactors.g:2286:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKactors.g:2287:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKactors.g:2287:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKactors.g:2288:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKactors.g:2288:5: (lv_int0_2_0= ruleNumber )
                    // InternalKactors.g:2289:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_31);
                    lv_int0_2_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
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

                    // InternalKactors.g:2306:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt41=3;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==45) ) {
                        alt41=1;
                    }
                    else if ( (LA41_0==46) ) {
                        alt41=2;
                    }
                    switch (alt41) {
                        case 1 :
                            // InternalKactors.g:2307:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:2307:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKactors.g:2308:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKactors.g:2308:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKactors.g:2309:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,45,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_leftLimit_3_0, grammarAccess.getTableClassifierAccess().getLeftLimitInclusiveKeyword_1_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getTableClassifierRule());
                              							}
                              							setWithLastConsumed(current, "leftLimit", lv_leftLimit_3_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:2322:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,46,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:2327:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKactors.g:2328:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,47,FOLLOW_33); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getTableClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKactors.g:2334:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKactors.g:2335:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKactors.g:2339:5: (lv_int1_6_0= ruleNumber )
                    // InternalKactors.g:2340:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_34);
                    lv_int1_6_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
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

                    // InternalKactors.g:2357:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt42=3;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==45) ) {
                        alt42=1;
                    }
                    else if ( (LA42_0==46) ) {
                        alt42=2;
                    }
                    switch (alt42) {
                        case 1 :
                            // InternalKactors.g:2358:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:2358:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKactors.g:2359:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKactors.g:2359:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKactors.g:2360:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_rightLimit_7_0, grammarAccess.getTableClassifierAccess().getRightLimitInclusiveKeyword_1_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getTableClassifierRule());
                              							}
                              							setWithLastConsumed(current, "rightLimit", lv_rightLimit_7_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:2373:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_8, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_1_4_1());
                              				
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:2380:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKactors.g:2380:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKactors.g:2381:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKactors.g:2381:4: (lv_num_9_0= ruleNumber )
                    // InternalKactors.g:2382:5: lv_num_9_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getTableClassifierAccess().getNumNumberParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_num_9_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getTableClassifierRule());
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
                    // InternalKactors.g:2400:3: ( (lv_quantity_10_0= ruleQuantity ) )
                    {
                    // InternalKactors.g:2400:3: ( (lv_quantity_10_0= ruleQuantity ) )
                    // InternalKactors.g:2401:4: (lv_quantity_10_0= ruleQuantity )
                    {
                    // InternalKactors.g:2401:4: (lv_quantity_10_0= ruleQuantity )
                    // InternalKactors.g:2402:5: lv_quantity_10_0= ruleQuantity
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getTableClassifierAccess().getQuantityQuantityParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_quantity_10_0=ruleQuantity();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      					}
                      					set(
                      						current,
                      						"quantity",
                      						lv_quantity_10_0,
                      						"org.integratedmodelling.kactors.Kactors.Quantity");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:2420:3: ( (lv_date_11_0= ruleDate ) )
                    {
                    // InternalKactors.g:2420:3: ( (lv_date_11_0= ruleDate ) )
                    // InternalKactors.g:2421:4: (lv_date_11_0= ruleDate )
                    {
                    // InternalKactors.g:2421:4: (lv_date_11_0= ruleDate )
                    // InternalKactors.g:2422:5: lv_date_11_0= ruleDate
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getTableClassifierAccess().getDateDateParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_date_11_0=ruleDate();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      					}
                      					set(
                      						current,
                      						"date",
                      						lv_date_11_0,
                      						"org.integratedmodelling.kactors.Kactors.Date");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:2440:3: (otherlv_12= 'in' ( (lv_set_13_0= ruleList ) ) )
                    {
                    // InternalKactors.g:2440:3: (otherlv_12= 'in' ( (lv_set_13_0= ruleList ) ) )
                    // InternalKactors.g:2441:4: otherlv_12= 'in' ( (lv_set_13_0= ruleList ) )
                    {
                    otherlv_12=(Token)match(input,48,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_12, grammarAccess.getTableClassifierAccess().getInKeyword_5_0());
                      			
                    }
                    // InternalKactors.g:2445:4: ( (lv_set_13_0= ruleList ) )
                    // InternalKactors.g:2446:5: (lv_set_13_0= ruleList )
                    {
                    // InternalKactors.g:2446:5: (lv_set_13_0= ruleList )
                    // InternalKactors.g:2447:6: lv_set_13_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getSetListParserRuleCall_5_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_set_13_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"set",
                      							lv_set_13_0,
                      							"org.integratedmodelling.kactors.Kactors.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:2466:3: ( (lv_string_14_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:2466:3: ( (lv_string_14_0= RULE_STRING ) )
                    // InternalKactors.g:2467:4: (lv_string_14_0= RULE_STRING )
                    {
                    // InternalKactors.g:2467:4: (lv_string_14_0= RULE_STRING )
                    // InternalKactors.g:2468:5: lv_string_14_0= RULE_STRING
                    {
                    lv_string_14_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_string_14_0, grammarAccess.getTableClassifierAccess().getStringSTRINGTerminalRuleCall_6_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"string",
                      						lv_string_14_0,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:2485:3: ( (lv_observable_15_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:2485:3: ( (lv_observable_15_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:2486:4: (lv_observable_15_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:2486:4: (lv_observable_15_0= RULE_OBSERVABLE )
                    // InternalKactors.g:2487:5: lv_observable_15_0= RULE_OBSERVABLE
                    {
                    lv_observable_15_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_observable_15_0, grammarAccess.getTableClassifierAccess().getObservableOBSERVABLETerminalRuleCall_7_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getTableClassifierRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"observable",
                      						lv_observable_15_0,
                      						"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:2504:3: ( ( (lv_op_16_0= ruleREL_OPERATOR ) ) ( (lv_expression_17_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:2504:3: ( ( (lv_op_16_0= ruleREL_OPERATOR ) ) ( (lv_expression_17_0= ruleNumber ) ) )
                    // InternalKactors.g:2505:4: ( (lv_op_16_0= ruleREL_OPERATOR ) ) ( (lv_expression_17_0= ruleNumber ) )
                    {
                    // InternalKactors.g:2505:4: ( (lv_op_16_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:2506:5: (lv_op_16_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:2506:5: (lv_op_16_0= ruleREL_OPERATOR )
                    // InternalKactors.g:2507:6: lv_op_16_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getOpREL_OPERATORParserRuleCall_8_0_0());
                      					
                    }
                    pushFollow(FOLLOW_33);
                    lv_op_16_0=ruleREL_OPERATOR();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"op",
                      							lv_op_16_0,
                      							"org.integratedmodelling.kactors.Kactors.REL_OPERATOR");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:2524:4: ( (lv_expression_17_0= ruleNumber ) )
                    // InternalKactors.g:2525:5: (lv_expression_17_0= ruleNumber )
                    {
                    // InternalKactors.g:2525:5: (lv_expression_17_0= ruleNumber )
                    // InternalKactors.g:2526:6: lv_expression_17_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getExpressionNumberParserRuleCall_8_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_expression_17_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTableClassifierRule());
                      						}
                      						set(
                      							current,
                      							"expression",
                      							lv_expression_17_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKactors.g:2545:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:2545:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    // InternalKactors.g:2546:4: (lv_expr_18_0= RULE_EXPR )
                    {
                    // InternalKactors.g:2546:4: (lv_expr_18_0= RULE_EXPR )
                    // InternalKactors.g:2547:5: lv_expr_18_0= RULE_EXPR
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
                    // InternalKactors.g:2564:3: ( (lv_nodata_19_0= 'unknown' ) )
                    {
                    // InternalKactors.g:2564:3: ( (lv_nodata_19_0= 'unknown' ) )
                    // InternalKactors.g:2565:4: (lv_nodata_19_0= 'unknown' )
                    {
                    // InternalKactors.g:2565:4: (lv_nodata_19_0= 'unknown' )
                    // InternalKactors.g:2566:5: lv_nodata_19_0= 'unknown'
                    {
                    lv_nodata_19_0=(Token)match(input,49,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:2579:3: ( (lv_star_20_0= '*' ) )
                    {
                    // InternalKactors.g:2579:3: ( (lv_star_20_0= '*' ) )
                    // InternalKactors.g:2580:4: (lv_star_20_0= '*' )
                    {
                    // InternalKactors.g:2580:4: (lv_star_20_0= '*' )
                    // InternalKactors.g:2581:5: lv_star_20_0= '*'
                    {
                    lv_star_20_0=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:2594:3: ( (lv_anything_21_0= '#' ) )
                    {
                    // InternalKactors.g:2594:3: ( (lv_anything_21_0= '#' ) )
                    // InternalKactors.g:2595:4: (lv_anything_21_0= '#' )
                    {
                    // InternalKactors.g:2595:4: (lv_anything_21_0= '#' )
                    // InternalKactors.g:2596:5: lv_anything_21_0= '#'
                    {
                    lv_anything_21_0=(Token)match(input,39,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:2612:1: entryRuleQuantity returns [EObject current=null] : iv_ruleQuantity= ruleQuantity EOF ;
    public final EObject entryRuleQuantity() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQuantity = null;


        try {
            // InternalKactors.g:2612:49: (iv_ruleQuantity= ruleQuantity EOF )
            // InternalKactors.g:2613:2: iv_ruleQuantity= ruleQuantity EOF
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
    // InternalKactors.g:2619:1: ruleQuantity returns [EObject current=null] : ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) ;
    public final EObject ruleQuantity() throws RecognitionException {
        EObject current = null;

        Token lv_over_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_0_0 = null;

        EObject lv_unit_3_0 = null;

        EObject lv_currency_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2625:2: ( ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) )
            // InternalKactors.g:2626:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            {
            // InternalKactors.g:2626:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            // InternalKactors.g:2627:3: ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            {
            // InternalKactors.g:2627:3: ( (lv_value_0_0= ruleNumber ) )
            // InternalKactors.g:2628:4: (lv_value_0_0= ruleNumber )
            {
            // InternalKactors.g:2628:4: (lv_value_0_0= ruleNumber )
            // InternalKactors.g:2629:5: lv_value_0_0= ruleNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getQuantityAccess().getValueNumberParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_42);
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

            // InternalKactors.g:2646:3: ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' )
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==54) ) {
                alt44=1;
            }
            else if ( (LA44_0==55) ) {
                alt44=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }
            switch (alt44) {
                case 1 :
                    // InternalKactors.g:2647:4: ( (lv_over_1_0= '/' ) )
                    {
                    // InternalKactors.g:2647:4: ( (lv_over_1_0= '/' ) )
                    // InternalKactors.g:2648:5: (lv_over_1_0= '/' )
                    {
                    // InternalKactors.g:2648:5: (lv_over_1_0= '/' )
                    // InternalKactors.g:2649:6: lv_over_1_0= '/'
                    {
                    lv_over_1_0=(Token)match(input,54,FOLLOW_43); if (state.failed) return current;
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
                    // InternalKactors.g:2662:4: otherlv_2= '.'
                    {
                    otherlv_2=(Token)match(input,55,FOLLOW_43); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getQuantityAccess().getFullStopKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:2667:3: ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==EOF||LA45_0==RULE_LOWERCASE_ID||LA45_0==RULE_CAMELCASE_ID||(LA45_0>=33 && LA45_0<=34)||LA45_0==50||(LA45_0>=52 && LA45_0<=54)||LA45_0==74) ) {
                alt45=1;
            }
            else if ( (LA45_0==RULE_UPPERCASE_ID) ) {
                int LA45_2 = input.LA(2);

                if ( (LA45_2==56) ) {
                    alt45=2;
                }
                else if ( (LA45_2==EOF||LA45_2==34||LA45_2==50||(LA45_2>=52 && LA45_2<=54)||LA45_2==74) ) {
                    alt45=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 45, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }
            switch (alt45) {
                case 1 :
                    // InternalKactors.g:2668:4: ( (lv_unit_3_0= ruleUnit ) )
                    {
                    // InternalKactors.g:2668:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKactors.g:2669:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKactors.g:2669:5: (lv_unit_3_0= ruleUnit )
                    // InternalKactors.g:2670:6: lv_unit_3_0= ruleUnit
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
                    // InternalKactors.g:2688:4: ( (lv_currency_4_0= ruleCurrency ) )
                    {
                    // InternalKactors.g:2688:4: ( (lv_currency_4_0= ruleCurrency ) )
                    // InternalKactors.g:2689:5: (lv_currency_4_0= ruleCurrency )
                    {
                    // InternalKactors.g:2689:5: (lv_currency_4_0= ruleCurrency )
                    // InternalKactors.g:2690:6: lv_currency_4_0= ruleCurrency
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
    // InternalKactors.g:2712:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKactors.g:2712:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKactors.g:2713:2: iv_ruleUnitElement= ruleUnitElement EOF
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
    // InternalKactors.g:2719:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) ;
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
            // InternalKactors.g:2725:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) )
            // InternalKactors.g:2726:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            {
            // InternalKactors.g:2726:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==RULE_LOWERCASE_ID||(LA47_0>=RULE_CAMELCASE_ID && LA47_0<=RULE_UPPERCASE_ID)) ) {
                alt47=1;
            }
            else if ( (LA47_0==33) ) {
                alt47=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // InternalKactors.g:2727:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    {
                    // InternalKactors.g:2727:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    // InternalKactors.g:2728:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKactors.g:2728:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    // InternalKactors.g:2729:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    {
                    // InternalKactors.g:2729:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    int alt46=3;
                    switch ( input.LA(1) ) {
                    case RULE_CAMELCASE_ID:
                        {
                        alt46=1;
                        }
                        break;
                    case RULE_LOWERCASE_ID:
                        {
                        alt46=2;
                        }
                        break;
                    case RULE_UPPERCASE_ID:
                        {
                        alt46=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 46, 0, input);

                        throw nvae;
                    }

                    switch (alt46) {
                        case 1 :
                            // InternalKactors.g:2730:6: lv_id_0_1= RULE_CAMELCASE_ID
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
                            // InternalKactors.g:2745:6: lv_id_0_2= RULE_LOWERCASE_ID
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
                            // InternalKactors.g:2760:6: lv_id_0_3= RULE_UPPERCASE_ID
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
                    // InternalKactors.g:2778:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    {
                    // InternalKactors.g:2778:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    // InternalKactors.g:2779:4: otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,33,FOLLOW_44); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:2783:4: ( (lv_unit_2_0= ruleUnit ) )
                    // InternalKactors.g:2784:5: (lv_unit_2_0= ruleUnit )
                    {
                    // InternalKactors.g:2784:5: (lv_unit_2_0= ruleUnit )
                    // InternalKactors.g:2785:6: lv_unit_2_0= ruleUnit
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

                    otherlv_3=(Token)match(input,35,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:2811:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKactors.g:2811:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKactors.g:2812:2: iv_ruleUnit= ruleUnit EOF
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
    // InternalKactors.g:2818:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2824:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:2825:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:2825:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:2826:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:2826:3: ()
            // InternalKactors.g:2827:4: 
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

            // InternalKactors.g:2836:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==RULE_LOWERCASE_ID||(LA48_0>=RULE_CAMELCASE_ID && LA48_0<=RULE_UPPERCASE_ID)||LA48_0==33) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalKactors.g:2837:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKactors.g:2837:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKactors.g:2838:5: lv_root_1_0= ruleUnitElement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUnitAccess().getRootUnitElementParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_45);
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

            // InternalKactors.g:2855:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==50||LA49_0==54||LA49_0==74) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalKactors.g:2856:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:2856:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKactors.g:2857:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKactors.g:2863:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKactors.g:2864:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKactors.g:2864:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKactors.g:2865:7: lv_connectors_2_0= ruleUnitOp
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getUnitAccess().getConnectorsUnitOpEnumRuleCall_2_0_0_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_46);
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

            	    // InternalKactors.g:2883:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKactors.g:2884:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:2884:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKactors.g:2885:6: lv_units_3_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getUnitAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_45);
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
            	    break loop49;
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
    // InternalKactors.g:2907:1: entryRuleCurrency returns [EObject current=null] : iv_ruleCurrency= ruleCurrency EOF ;
    public final EObject entryRuleCurrency() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCurrency = null;


        try {
            // InternalKactors.g:2907:49: (iv_ruleCurrency= ruleCurrency EOF )
            // InternalKactors.g:2908:2: iv_ruleCurrency= ruleCurrency EOF
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
    // InternalKactors.g:2914:1: ruleCurrency returns [EObject current=null] : ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleCurrency() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_year_2_0=null;
        Token otherlv_3=null;
        EObject lv_units_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2920:2: ( ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:2921:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:2921:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:2922:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:2922:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) )
            // InternalKactors.g:2923:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            {
            // InternalKactors.g:2923:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            // InternalKactors.g:2924:5: lv_id_0_0= RULE_UPPERCASE_ID
            {
            lv_id_0_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_47); if (state.failed) return current;
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

            // InternalKactors.g:2940:3: (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) )
            // InternalKactors.g:2941:4: otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) )
            {
            otherlv_1=(Token)match(input,56,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_1, grammarAccess.getCurrencyAccess().getCommercialAtKeyword_1_0());
              			
            }
            // InternalKactors.g:2945:4: ( (lv_year_2_0= RULE_INT ) )
            // InternalKactors.g:2946:5: (lv_year_2_0= RULE_INT )
            {
            // InternalKactors.g:2946:5: (lv_year_2_0= RULE_INT )
            // InternalKactors.g:2947:6: lv_year_2_0= RULE_INT
            {
            lv_year_2_0=(Token)match(input,RULE_INT,FOLLOW_48); if (state.failed) return current;
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

            // InternalKactors.g:2964:3: ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==54) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalKactors.g:2965:4: ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:2965:4: ( ( '/' )=>otherlv_3= '/' )
            	    // InternalKactors.g:2966:5: ( '/' )=>otherlv_3= '/'
            	    {
            	    otherlv_3=(Token)match(input,54,FOLLOW_46); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_3, grammarAccess.getCurrencyAccess().getSolidusKeyword_2_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:2972:4: ( (lv_units_4_0= ruleUnitElement ) )
            	    // InternalKactors.g:2973:5: (lv_units_4_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:2973:5: (lv_units_4_0= ruleUnitElement )
            	    // InternalKactors.g:2974:6: lv_units_4_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getCurrencyAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_48);
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
            	    break loop50;
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
    // InternalKactors.g:2996:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKactors.g:2996:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKactors.g:2997:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
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
    // InternalKactors.g:3003:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
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
            // InternalKactors.g:3009:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKactors.g:3010:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKactors.g:3010:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt51=6;
            switch ( input.LA(1) ) {
            case 57:
                {
                alt51=1;
                }
                break;
            case 58:
                {
                alt51=2;
                }
                break;
            case 37:
                {
                alt51=3;
                }
                break;
            case 59:
                {
                alt51=4;
                }
                break;
            case 60:
                {
                alt51=5;
                }
                break;
            case 61:
                {
                alt51=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }

            switch (alt51) {
                case 1 :
                    // InternalKactors.g:3011:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKactors.g:3011:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKactors.g:3012:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKactors.g:3012:4: (lv_gt_0_0= '>' )
                    // InternalKactors.g:3013:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,57,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3026:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKactors.g:3026:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKactors.g:3027:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKactors.g:3027:4: (lv_lt_1_0= '<' )
                    // InternalKactors.g:3028:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,58,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3041:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKactors.g:3041:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKactors.g:3042:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKactors.g:3042:4: (lv_eq_2_0= '=' )
                    // InternalKactors.g:3043:5: lv_eq_2_0= '='
                    {
                    lv_eq_2_0=(Token)match(input,37,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3056:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKactors.g:3056:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKactors.g:3057:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKactors.g:3057:4: (lv_ne_3_0= '!=' )
                    // InternalKactors.g:3058:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,59,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3071:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKactors.g:3071:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKactors.g:3072:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKactors.g:3072:4: (lv_le_4_0= '<=' )
                    // InternalKactors.g:3073:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,60,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3086:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKactors.g:3086:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKactors.g:3087:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKactors.g:3087:4: (lv_ge_5_0= '>=' )
                    // InternalKactors.g:3088:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,61,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:3104:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKactors.g:3104:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKactors.g:3105:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKactors.g:3111:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) ;
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
            // InternalKactors.g:3117:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) )
            // InternalKactors.g:3118:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            {
            // InternalKactors.g:3118:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            int alt53=5;
            alt53 = dfa53.predict(input);
            switch (alt53) {
                case 1 :
                    // InternalKactors.g:3119:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3119:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKactors.g:3120:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKactors.g:3120:4: (lv_number_0_0= ruleNumber )
                    // InternalKactors.g:3121:5: lv_number_0_0= ruleNumber
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
                    // InternalKactors.g:3139:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:3139:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKactors.g:3140:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3140:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKactors.g:3141:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKactors.g:3141:5: (lv_from_1_0= ruleNumber )
                    // InternalKactors.g:3142:6: lv_from_1_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
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

                    otherlv_2=(Token)match(input,47,FOLLOW_33); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:3163:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKactors.g:3164:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKactors.g:3164:5: (lv_to_3_0= ruleNumber )
                    // InternalKactors.g:3165:6: lv_to_3_0= ruleNumber
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
                    // InternalKactors.g:3184:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:3184:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKactors.g:3185:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKactors.g:3185:4: (lv_string_4_0= RULE_STRING )
                    // InternalKactors.g:3186:5: lv_string_4_0= RULE_STRING
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
                    // InternalKactors.g:3203:3: ( (lv_date_5_0= ruleDate ) )
                    {
                    // InternalKactors.g:3203:3: ( (lv_date_5_0= ruleDate ) )
                    // InternalKactors.g:3204:4: (lv_date_5_0= ruleDate )
                    {
                    // InternalKactors.g:3204:4: (lv_date_5_0= ruleDate )
                    // InternalKactors.g:3205:5: lv_date_5_0= ruleDate
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
                    // InternalKactors.g:3223:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    {
                    // InternalKactors.g:3223:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    // InternalKactors.g:3224:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    {
                    // InternalKactors.g:3224:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    // InternalKactors.g:3225:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    {
                    // InternalKactors.g:3225:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    int alt52=2;
                    int LA52_0 = input.LA(1);

                    if ( (LA52_0==43) ) {
                        alt52=1;
                    }
                    else if ( (LA52_0==44) ) {
                        alt52=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 52, 0, input);

                        throw nvae;
                    }
                    switch (alt52) {
                        case 1 :
                            // InternalKactors.g:3226:6: lv_boolean_6_1= 'true'
                            {
                            lv_boolean_6_1=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:3237:6: lv_boolean_6_2= 'false'
                            {
                            lv_boolean_6_2=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:3254:1: entryRuleBody returns [EObject current=null] : iv_ruleBody= ruleBody EOF ;
    public final EObject entryRuleBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBody = null;


        try {
            // InternalKactors.g:3254:45: (iv_ruleBody= ruleBody EOF )
            // InternalKactors.g:3255:2: iv_ruleBody= ruleBody EOF
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
    // InternalKactors.g:3261:1: ruleBody returns [EObject current=null] : ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) | ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')' ) ) ;
    public final EObject ruleBody() throws RecognitionException {
        EObject current = null;

        Token lv_isgroup_3_0=null;
        Token otherlv_6=null;
        EObject lv_list_1_0 = null;

        EObject lv_list_2_0 = null;

        EObject lv_group_4_0 = null;

        EObject lv_group_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3267:2: ( ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) | ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')' ) ) )
            // InternalKactors.g:3268:2: ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) | ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')' ) )
            {
            // InternalKactors.g:3268:2: ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) | ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')' ) )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==RULE_LOWERCASE_ID||LA57_0==RULE_EMBEDDEDTEXT||LA57_0==62) ) {
                alt57=1;
            }
            else if ( (LA57_0==33) ) {
                int LA57_4 = input.LA(2);

                if ( (synpred111_InternalKactors()) ) {
                    alt57=1;
                }
                else if ( (true) ) {
                    alt57=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 57, 4, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }
            switch (alt57) {
                case 1 :
                    // InternalKactors.g:3269:3: ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* )
                    {
                    // InternalKactors.g:3269:3: ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* )
                    // InternalKactors.g:3270:4: () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )*
                    {
                    // InternalKactors.g:3270:4: ()
                    // InternalKactors.g:3271:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					/* */
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getBodyAccess().getBodyAction_0_0(),
                      						current);
                      				
                    }

                    }

                    // InternalKactors.g:3280:4: ( (lv_list_1_0= ruleStatement ) )
                    // InternalKactors.g:3281:5: (lv_list_1_0= ruleStatement )
                    {
                    // InternalKactors.g:3281:5: (lv_list_1_0= ruleStatement )
                    // InternalKactors.g:3282:6: lv_list_1_0= ruleStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_49);
                    lv_list_1_0=ruleStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getBodyRule());
                      						}
                      						add(
                      							current,
                      							"list",
                      							lv_list_1_0,
                      							"org.integratedmodelling.kactors.Kactors.Statement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:3299:4: ( (lv_list_2_0= ruleStatement ) )*
                    loop54:
                    do {
                        int alt54=2;
                        switch ( input.LA(1) ) {
                        case RULE_LOWERCASE_ID:
                            {
                            int LA54_2 = input.LA(2);

                            if ( (synpred110_InternalKactors()) ) {
                                alt54=1;
                            }


                            }
                            break;
                        case RULE_EMBEDDEDTEXT:
                            {
                            int LA54_3 = input.LA(2);

                            if ( (synpred110_InternalKactors()) ) {
                                alt54=1;
                            }


                            }
                            break;
                        case 62:
                            {
                            int LA54_4 = input.LA(2);

                            if ( (synpred110_InternalKactors()) ) {
                                alt54=1;
                            }


                            }
                            break;
                        case 33:
                            {
                            int LA54_5 = input.LA(2);

                            if ( (synpred110_InternalKactors()) ) {
                                alt54=1;
                            }


                            }
                            break;

                        }

                        switch (alt54) {
                    	case 1 :
                    	    // InternalKactors.g:3300:5: (lv_list_2_0= ruleStatement )
                    	    {
                    	    // InternalKactors.g:3300:5: (lv_list_2_0= ruleStatement )
                    	    // InternalKactors.g:3301:6: lv_list_2_0= ruleStatement
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_2_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_49);
                    	    lv_list_2_0=ruleStatement();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getBodyRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"list",
                    	      							lv_list_2_0,
                    	      							"org.integratedmodelling.kactors.Kactors.Statement");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop54;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3320:3: ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')' )
                    {
                    // InternalKactors.g:3320:3: ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')' )
                    // InternalKactors.g:3321:4: ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')'
                    {
                    // InternalKactors.g:3321:4: ( (lv_isgroup_3_0= '(' ) )
                    // InternalKactors.g:3322:5: (lv_isgroup_3_0= '(' )
                    {
                    // InternalKactors.g:3322:5: (lv_isgroup_3_0= '(' )
                    // InternalKactors.g:3323:6: lv_isgroup_3_0= '('
                    {
                    lv_isgroup_3_0=(Token)match(input,33,FOLLOW_50); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_isgroup_3_0, grammarAccess.getBodyAccess().getIsgroupLeftParenthesisKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getBodyRule());
                      						}
                      						setWithLastConsumed(current, "isgroup", true, "(");
                      					
                    }

                    }


                    }

                    // InternalKactors.g:3335:4: ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )?
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==RULE_LOWERCASE_ID||LA56_0==RULE_EMBEDDEDTEXT||LA56_0==33||LA56_0==62) ) {
                        alt56=1;
                    }
                    switch (alt56) {
                        case 1 :
                            // InternalKactors.g:3336:5: ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )*
                            {
                            // InternalKactors.g:3336:5: ( (lv_group_4_0= ruleStatement ) )
                            // InternalKactors.g:3337:6: (lv_group_4_0= ruleStatement )
                            {
                            // InternalKactors.g:3337:6: (lv_group_4_0= ruleStatement )
                            // InternalKactors.g:3338:7: lv_group_4_0= ruleStatement
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getBodyAccess().getGroupStatementParserRuleCall_1_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_50);
                            lv_group_4_0=ruleStatement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getBodyRule());
                              							}
                              							add(
                              								current,
                              								"group",
                              								lv_group_4_0,
                              								"org.integratedmodelling.kactors.Kactors.Statement");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKactors.g:3355:5: ( (lv_group_5_0= ruleStatement ) )*
                            loop55:
                            do {
                                int alt55=2;
                                int LA55_0 = input.LA(1);

                                if ( (LA55_0==RULE_LOWERCASE_ID||LA55_0==RULE_EMBEDDEDTEXT||LA55_0==33||LA55_0==62) ) {
                                    alt55=1;
                                }


                                switch (alt55) {
                            	case 1 :
                            	    // InternalKactors.g:3356:6: (lv_group_5_0= ruleStatement )
                            	    {
                            	    // InternalKactors.g:3356:6: (lv_group_5_0= ruleStatement )
                            	    // InternalKactors.g:3357:7: lv_group_5_0= ruleStatement
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      							newCompositeNode(grammarAccess.getBodyAccess().getGroupStatementParserRuleCall_1_1_1_0());
                            	      						
                            	    }
                            	    pushFollow(FOLLOW_50);
                            	    lv_group_5_0=ruleStatement();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							if (current==null) {
                            	      								current = createModelElementForParent(grammarAccess.getBodyRule());
                            	      							}
                            	      							add(
                            	      								current,
                            	      								"group",
                            	      								lv_group_5_0,
                            	      								"org.integratedmodelling.kactors.Kactors.Statement");
                            	      							afterParserOrEnumRuleCall();
                            	      						
                            	    }

                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop55;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_6=(Token)match(input,35,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getBodyAccess().getRightParenthesisKeyword_1_2());
                      			
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
    // $ANTLR end "ruleBody"


    // $ANTLR start "entryRuleStatement"
    // InternalKactors.g:3384:1: entryRuleStatement returns [EObject current=null] : iv_ruleStatement= ruleStatement EOF ;
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatement = null;


        try {
            // InternalKactors.g:3384:50: (iv_ruleStatement= ruleStatement EOF )
            // InternalKactors.g:3385:2: iv_ruleStatement= ruleStatement EOF
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
    // InternalKactors.g:3391:1: ruleStatement returns [EObject current=null] : ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_2_0= ruleIfStatement ) ) | (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ) ) ;
    public final EObject ruleStatement() throws RecognitionException {
        EObject current = null;

        Token lv_text_1_0=null;
        Token otherlv_3=null;
        Token otherlv_6=null;
        EObject lv_call_0_0 = null;

        EObject lv_if_2_0 = null;

        EObject lv_group_4_0 = null;

        EObject lv_group_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3397:2: ( ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_2_0= ruleIfStatement ) ) | (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ) ) )
            // InternalKactors.g:3398:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_2_0= ruleIfStatement ) ) | (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ) )
            {
            // InternalKactors.g:3398:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_2_0= ruleIfStatement ) ) | (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ) )
            int alt59=4;
            switch ( input.LA(1) ) {
            case RULE_LOWERCASE_ID:
                {
                alt59=1;
                }
                break;
            case RULE_EMBEDDEDTEXT:
                {
                alt59=2;
                }
                break;
            case 62:
                {
                alt59=3;
                }
                break;
            case 33:
                {
                alt59=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }

            switch (alt59) {
                case 1 :
                    // InternalKactors.g:3399:3: ( (lv_call_0_0= ruleCall ) )
                    {
                    // InternalKactors.g:3399:3: ( (lv_call_0_0= ruleCall ) )
                    // InternalKactors.g:3400:4: (lv_call_0_0= ruleCall )
                    {
                    // InternalKactors.g:3400:4: (lv_call_0_0= ruleCall )
                    // InternalKactors.g:3401:5: lv_call_0_0= ruleCall
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getCallCallParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_call_0_0=ruleCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"call",
                      						lv_call_0_0,
                      						"org.integratedmodelling.kactors.Kactors.Call");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3419:3: ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:3419:3: ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:3420:4: (lv_text_1_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:3420:4: (lv_text_1_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:3421:5: lv_text_1_0= RULE_EMBEDDEDTEXT
                    {
                    lv_text_1_0=(Token)match(input,RULE_EMBEDDEDTEXT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_text_1_0, grammarAccess.getStatementAccess().getTextEMBEDDEDTEXTTerminalRuleCall_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getStatementRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"text",
                      						lv_text_1_0,
                      						"org.integratedmodelling.kactors.Kactors.EMBEDDEDTEXT");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:3438:3: ( (lv_if_2_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:3438:3: ( (lv_if_2_0= ruleIfStatement ) )
                    // InternalKactors.g:3439:4: (lv_if_2_0= ruleIfStatement )
                    {
                    // InternalKactors.g:3439:4: (lv_if_2_0= ruleIfStatement )
                    // InternalKactors.g:3440:5: lv_if_2_0= ruleIfStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getIfIfStatementParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_if_2_0=ruleIfStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"if",
                      						lv_if_2_0,
                      						"org.integratedmodelling.kactors.Kactors.IfStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:3458:3: (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' )
                    {
                    // InternalKactors.g:3458:3: (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' )
                    // InternalKactors.g:3459:4: otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')'
                    {
                    otherlv_3=(Token)match(input,33,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getStatementAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:3463:4: ( (lv_group_4_0= ruleStatement ) )
                    // InternalKactors.g:3464:5: (lv_group_4_0= ruleStatement )
                    {
                    // InternalKactors.g:3464:5: (lv_group_4_0= ruleStatement )
                    // InternalKactors.g:3465:6: lv_group_4_0= ruleStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getGroupStatementParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_50);
                    lv_group_4_0=ruleStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						add(
                      							current,
                      							"group",
                      							lv_group_4_0,
                      							"org.integratedmodelling.kactors.Kactors.Statement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:3482:4: ( (lv_group_5_0= ruleStatement ) )*
                    loop58:
                    do {
                        int alt58=2;
                        int LA58_0 = input.LA(1);

                        if ( (LA58_0==RULE_LOWERCASE_ID||LA58_0==RULE_EMBEDDEDTEXT||LA58_0==33||LA58_0==62) ) {
                            alt58=1;
                        }


                        switch (alt58) {
                    	case 1 :
                    	    // InternalKactors.g:3483:5: (lv_group_5_0= ruleStatement )
                    	    {
                    	    // InternalKactors.g:3483:5: (lv_group_5_0= ruleStatement )
                    	    // InternalKactors.g:3484:6: lv_group_5_0= ruleStatement
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getStatementAccess().getGroupStatementParserRuleCall_3_2_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_50);
                    	    lv_group_5_0=ruleStatement();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getStatementRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"group",
                    	      							lv_group_5_0,
                    	      							"org.integratedmodelling.kactors.Kactors.Statement");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop58;
                        }
                    } while (true);

                    otherlv_6=(Token)match(input,35,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getStatementAccess().getRightParenthesisKeyword_3_3());
                      			
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


    // $ANTLR start "entryRuleIfStatement"
    // InternalKactors.g:3510:1: entryRuleIfStatement returns [EObject current=null] : iv_ruleIfStatement= ruleIfStatement EOF ;
    public final EObject entryRuleIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfStatement = null;


        try {
            // InternalKactors.g:3510:52: (iv_ruleIfStatement= ruleIfStatement EOF )
            // InternalKactors.g:3511:2: iv_ruleIfStatement= ruleIfStatement EOF
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
    // InternalKactors.g:3517:1: ruleIfStatement returns [EObject current=null] : (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? ) ;
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
            // InternalKactors.g:3523:2: ( (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? ) )
            // InternalKactors.g:3524:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? )
            {
            // InternalKactors.g:3524:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? )
            // InternalKactors.g:3525:3: otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )?
            {
            otherlv_0=(Token)match(input,62,FOLLOW_51); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getIfStatementAccess().getIfKeyword_0());
              		
            }
            // InternalKactors.g:3529:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:3530:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:3530:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:3531:5: lv_expression_1_0= RULE_EXPR
            {
            lv_expression_1_0=(Token)match(input,RULE_EXPR,FOLLOW_13); if (state.failed) return current;
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

            // InternalKactors.g:3547:3: ( (lv_body_2_0= ruleIfBody ) )
            // InternalKactors.g:3548:4: (lv_body_2_0= ruleIfBody )
            {
            // InternalKactors.g:3548:4: (lv_body_2_0= ruleIfBody )
            // InternalKactors.g:3549:5: lv_body_2_0= ruleIfBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getIfStatementAccess().getBodyIfBodyParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_52);
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

            // InternalKactors.g:3566:3: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==63) ) {
                    int LA60_1 = input.LA(2);

                    if ( (synpred118_InternalKactors()) ) {
                        alt60=1;
                    }


                }


                switch (alt60) {
            	case 1 :
            	    // InternalKactors.g:3567:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) )
            	    {
            	    otherlv_3=(Token)match(input,63,FOLLOW_53); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getIfStatementAccess().getElseKeyword_3_0());
            	      			
            	    }
            	    otherlv_4=(Token)match(input,62,FOLLOW_51); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getIfStatementAccess().getIfKeyword_3_1());
            	      			
            	    }
            	    // InternalKactors.g:3575:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
            	    // InternalKactors.g:3576:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    {
            	    // InternalKactors.g:3576:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    // InternalKactors.g:3577:6: lv_elseIfExpression_5_0= RULE_EXPR
            	    {
            	    lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_13); if (state.failed) return current;
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

            	    // InternalKactors.g:3593:4: ( (lv_elseIfCall_6_0= ruleIfBody ) )
            	    // InternalKactors.g:3594:5: (lv_elseIfCall_6_0= ruleIfBody )
            	    {
            	    // InternalKactors.g:3594:5: (lv_elseIfCall_6_0= ruleIfBody )
            	    // InternalKactors.g:3595:6: lv_elseIfCall_6_0= ruleIfBody
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getIfStatementAccess().getElseIfCallIfBodyParserRuleCall_3_3_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_52);
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
            	    break loop60;
                }
            } while (true);

            // InternalKactors.g:3613:3: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==63) ) {
                int LA61_1 = input.LA(2);

                if ( (synpred119_InternalKactors()) ) {
                    alt61=1;
                }
            }
            switch (alt61) {
                case 1 :
                    // InternalKactors.g:3614:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) )
                    {
                    otherlv_7=(Token)match(input,63,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getIfStatementAccess().getElseKeyword_4_0());
                      			
                    }
                    // InternalKactors.g:3618:4: ( (lv_elseCall_8_0= ruleIfBody ) )
                    // InternalKactors.g:3619:5: (lv_elseCall_8_0= ruleIfBody )
                    {
                    // InternalKactors.g:3619:5: (lv_elseCall_8_0= ruleIfBody )
                    // InternalKactors.g:3620:6: lv_elseCall_8_0= ruleIfBody
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
    // InternalKactors.g:3642:1: entryRuleIfBody returns [EObject current=null] : iv_ruleIfBody= ruleIfBody EOF ;
    public final EObject entryRuleIfBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfBody = null;


        try {
            // InternalKactors.g:3642:47: (iv_ruleIfBody= ruleIfBody EOF )
            // InternalKactors.g:3643:2: iv_ruleIfBody= ruleIfBody EOF
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
    // InternalKactors.g:3649:1: ruleIfBody returns [EObject current=null] : ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) ) ;
    public final EObject ruleIfBody() throws RecognitionException {
        EObject current = null;

        EObject lv_call_0_0 = null;

        EObject lv_body_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3655:2: ( ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) ) )
            // InternalKactors.g:3656:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) )
            {
            // InternalKactors.g:3656:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) )
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==RULE_LOWERCASE_ID) ) {
                int LA62_1 = input.LA(2);

                if ( (synpred120_InternalKactors()) ) {
                    alt62=1;
                }
                else if ( (true) ) {
                    alt62=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 62, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA62_0==RULE_EMBEDDEDTEXT||LA62_0==33||LA62_0==62) ) {
                alt62=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }
            switch (alt62) {
                case 1 :
                    // InternalKactors.g:3657:3: ( (lv_call_0_0= ruleCall ) )
                    {
                    // InternalKactors.g:3657:3: ( (lv_call_0_0= ruleCall ) )
                    // InternalKactors.g:3658:4: (lv_call_0_0= ruleCall )
                    {
                    // InternalKactors.g:3658:4: (lv_call_0_0= ruleCall )
                    // InternalKactors.g:3659:5: lv_call_0_0= ruleCall
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getIfBodyAccess().getCallCallParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_call_0_0=ruleCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getIfBodyRule());
                      					}
                      					set(
                      						current,
                      						"call",
                      						lv_call_0_0,
                      						"org.integratedmodelling.kactors.Kactors.Call");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3677:3: ( (lv_body_1_0= ruleBody ) )
                    {
                    // InternalKactors.g:3677:3: ( (lv_body_1_0= ruleBody ) )
                    // InternalKactors.g:3678:4: (lv_body_1_0= ruleBody )
                    {
                    // InternalKactors.g:3678:4: (lv_body_1_0= ruleBody )
                    // InternalKactors.g:3679:5: lv_body_1_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getIfBodyAccess().getBodyBodyParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_1_0=ruleBody();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getIfBodyRule());
                      					}
                      					set(
                      						current,
                      						"body",
                      						lv_body_1_0,
                      						"org.integratedmodelling.kactors.Kactors.Body");
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


    // $ANTLR start "entryRuleCall"
    // InternalKactors.g:3700:1: entryRuleCall returns [EObject current=null] : iv_ruleCall= ruleCall EOF ;
    public final EObject entryRuleCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCall = null;


        try {
            // InternalKactors.g:3700:45: (iv_ruleCall= ruleCall EOF )
            // InternalKactors.g:3701:2: iv_ruleCall= ruleCall EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCallRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleCall=ruleCall();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCall; 
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
    // $ANTLR end "entryRuleCall"


    // $ANTLR start "ruleCall"
    // InternalKactors.g:3707:1: ruleCall returns [EObject current=null] : ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )? ) ;
    public final EObject ruleCall() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_parameters_2_0 = null;

        EObject lv_actions_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3713:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )? ) )
            // InternalKactors.g:3714:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )? )
            {
            // InternalKactors.g:3714:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )? )
            // InternalKactors.g:3715:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )?
            {
            // InternalKactors.g:3715:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:3716:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:3716:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:3717:5: lv_name_0_0= RULE_LOWERCASE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_54); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_0_0, grammarAccess.getCallAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getCallRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_0_0,
              						"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
              				
            }

            }


            }

            // InternalKactors.g:3733:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            int alt64=2;
            alt64 = dfa64.predict(input);
            switch (alt64) {
                case 1 :
                    // InternalKactors.g:3734:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,33,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getCallAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:3738:4: ( (lv_parameters_2_0= ruleParameterList ) )?
                    int alt63=2;
                    int LA63_0 = input.LA(1);

                    if ( (LA63_0==RULE_LOWERCASE_ID||(LA63_0>=RULE_STRING && LA63_0<=RULE_EXPR)||LA63_0==RULE_INT||LA63_0==33||LA63_0==38||LA63_0==41||(LA63_0>=43 && LA63_0<=44)||LA63_0==51||(LA63_0>=66 && LA63_0<=67)) ) {
                        alt63=1;
                    }
                    switch (alt63) {
                        case 1 :
                            // InternalKactors.g:3739:5: (lv_parameters_2_0= ruleParameterList )
                            {
                            // InternalKactors.g:3739:5: (lv_parameters_2_0= ruleParameterList )
                            // InternalKactors.g:3740:6: lv_parameters_2_0= ruleParameterList
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getCallAccess().getParametersParameterListParserRuleCall_1_1_0());
                              					
                            }
                            pushFollow(FOLLOW_21);
                            lv_parameters_2_0=ruleParameterList();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElementForParent(grammarAccess.getCallRule());
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

                    otherlv_3=(Token)match(input,35,FOLLOW_55); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getCallAccess().getRightParenthesisKeyword_1_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:3762:3: ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )?
            int alt65=3;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==32) ) {
                alt65=1;
            }
            else if ( (LA65_0==64) ) {
                alt65=2;
            }
            switch (alt65) {
                case 1 :
                    // InternalKactors.g:3763:4: (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) )
                    {
                    // InternalKactors.g:3763:4: (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) )
                    // InternalKactors.g:3764:5: otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) )
                    {
                    otherlv_4=(Token)match(input,32,FOLLOW_56); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_4, grammarAccess.getCallAccess().getColonKeyword_2_0_0());
                      				
                    }
                    // InternalKactors.g:3768:5: ( (lv_actions_5_0= ruleActions ) )
                    // InternalKactors.g:3769:6: (lv_actions_5_0= ruleActions )
                    {
                    // InternalKactors.g:3769:6: (lv_actions_5_0= ruleActions )
                    // InternalKactors.g:3770:7: lv_actions_5_0= ruleActions
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getCallAccess().getActionsActionsParserRuleCall_2_0_1_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    lv_actions_5_0=ruleActions();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getCallRule());
                      							}
                      							set(
                      								current,
                      								"actions",
                      								lv_actions_5_0,
                      								"org.integratedmodelling.kactors.Kactors.Actions");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3789:4: otherlv_6= ';'
                    {
                    otherlv_6=(Token)match(input,64,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getCallAccess().getSemicolonKeyword_2_1());
                      			
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
    // $ANTLR end "ruleCall"


    // $ANTLR start "entryRuleActions"
    // InternalKactors.g:3798:1: entryRuleActions returns [EObject current=null] : iv_ruleActions= ruleActions EOF ;
    public final EObject entryRuleActions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActions = null;


        try {
            // InternalKactors.g:3798:48: (iv_ruleActions= ruleActions EOF )
            // InternalKactors.g:3799:2: iv_ruleActions= ruleActions EOF
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
    // InternalKactors.g:3805:1: ruleActions returns [EObject current=null] : ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) | ( (lv_match_2_0= ruleMatch ) ) | (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' ) ) ;
    public final EObject ruleActions() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_6=null;
        EObject lv_call_0_0 = null;

        EObject lv_body_1_0 = null;

        EObject lv_match_2_0 = null;

        EObject lv_matches_4_0 = null;

        EObject lv_matches_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3811:2: ( ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) | ( (lv_match_2_0= ruleMatch ) ) | (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' ) ) )
            // InternalKactors.g:3812:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) | ( (lv_match_2_0= ruleMatch ) ) | (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' ) )
            {
            // InternalKactors.g:3812:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) | ( (lv_match_2_0= ruleMatch ) ) | (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' ) )
            int alt67=4;
            alt67 = dfa67.predict(input);
            switch (alt67) {
                case 1 :
                    // InternalKactors.g:3813:3: ( (lv_call_0_0= ruleCall ) )
                    {
                    // InternalKactors.g:3813:3: ( (lv_call_0_0= ruleCall ) )
                    // InternalKactors.g:3814:4: (lv_call_0_0= ruleCall )
                    {
                    // InternalKactors.g:3814:4: (lv_call_0_0= ruleCall )
                    // InternalKactors.g:3815:5: lv_call_0_0= ruleCall
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getCallCallParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_call_0_0=ruleCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
                      					}
                      					set(
                      						current,
                      						"call",
                      						lv_call_0_0,
                      						"org.integratedmodelling.kactors.Kactors.Call");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3833:3: ( (lv_body_1_0= ruleBody ) )
                    {
                    // InternalKactors.g:3833:3: ( (lv_body_1_0= ruleBody ) )
                    // InternalKactors.g:3834:4: (lv_body_1_0= ruleBody )
                    {
                    // InternalKactors.g:3834:4: (lv_body_1_0= ruleBody )
                    // InternalKactors.g:3835:5: lv_body_1_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getBodyBodyParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_1_0=ruleBody();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
                      					}
                      					set(
                      						current,
                      						"body",
                      						lv_body_1_0,
                      						"org.integratedmodelling.kactors.Kactors.Body");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:3853:3: ( (lv_match_2_0= ruleMatch ) )
                    {
                    // InternalKactors.g:3853:3: ( (lv_match_2_0= ruleMatch ) )
                    // InternalKactors.g:3854:4: (lv_match_2_0= ruleMatch )
                    {
                    // InternalKactors.g:3854:4: (lv_match_2_0= ruleMatch )
                    // InternalKactors.g:3855:5: lv_match_2_0= ruleMatch
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_match_2_0=ruleMatch();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
                      					}
                      					set(
                      						current,
                      						"match",
                      						lv_match_2_0,
                      						"org.integratedmodelling.kactors.Kactors.Match");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:3873:3: (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' )
                    {
                    // InternalKactors.g:3873:3: (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' )
                    // InternalKactors.g:3874:4: otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')'
                    {
                    otherlv_3=(Token)match(input,33,FOLLOW_57); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:3878:4: ( (lv_matches_4_0= ruleMatch ) )
                    // InternalKactors.g:3879:5: (lv_matches_4_0= ruleMatch )
                    {
                    // InternalKactors.g:3879:5: (lv_matches_4_0= ruleMatch )
                    // InternalKactors.g:3880:6: lv_matches_4_0= ruleMatch
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_58);
                    lv_matches_4_0=ruleMatch();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionsRule());
                      						}
                      						add(
                      							current,
                      							"matches",
                      							lv_matches_4_0,
                      							"org.integratedmodelling.kactors.Kactors.Match");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:3897:4: ( (lv_matches_5_0= ruleMatch ) )*
                    loop66:
                    do {
                        int alt66=2;
                        int LA66_0 = input.LA(1);

                        if ( (LA66_0==RULE_LOWERCASE_ID||LA66_0==RULE_STRING||LA66_0==RULE_OBSERVABLE||LA66_0==RULE_INT||LA66_0==RULE_REGEXP||LA66_0==33||(LA66_0>=43 && LA66_0<=44)||(LA66_0>=66 && LA66_0<=67)) ) {
                            alt66=1;
                        }


                        switch (alt66) {
                    	case 1 :
                    	    // InternalKactors.g:3898:5: (lv_matches_5_0= ruleMatch )
                    	    {
                    	    // InternalKactors.g:3898:5: (lv_matches_5_0= ruleMatch )
                    	    // InternalKactors.g:3899:6: lv_matches_5_0= ruleMatch
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_3_2_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_58);
                    	    lv_matches_5_0=ruleMatch();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getActionsRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"matches",
                    	      							lv_matches_5_0,
                    	      							"org.integratedmodelling.kactors.Kactors.Match");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop66;
                        }
                    } while (true);

                    otherlv_6=(Token)match(input,35,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getActionsAccess().getRightParenthesisKeyword_3_3());
                      			
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
    // InternalKactors.g:3925:1: entryRuleMatch returns [EObject current=null] : iv_ruleMatch= ruleMatch EOF ;
    public final EObject entryRuleMatch() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMatch = null;


        try {
            // InternalKactors.g:3925:46: (iv_ruleMatch= ruleMatch EOF )
            // InternalKactors.g:3926:2: iv_ruleMatch= ruleMatch EOF
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
    // InternalKactors.g:3932:1: ruleMatch returns [EObject current=null] : ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) ) ;
    public final EObject ruleMatch() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_regexp_3_0=null;
        Token otherlv_4=null;
        Token lv_observable_6_0=null;
        Token otherlv_7=null;
        Token otherlv_10=null;
        Token lv_text_12_0=null;
        Token otherlv_13=null;
        Token otherlv_16=null;
        EObject lv_body_2_0 = null;

        EObject lv_body_5_0 = null;

        EObject lv_body_8_0 = null;

        EObject lv_literal_9_0 = null;

        EObject lv_body_11_0 = null;

        EObject lv_body_14_0 = null;

        EObject lv_arguments_15_0 = null;

        EObject lv_body_17_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3938:2: ( ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) ) )
            // InternalKactors.g:3939:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) )
            {
            // InternalKactors.g:3939:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) )
            int alt68=6;
            alt68 = dfa68.predict(input);
            switch (alt68) {
                case 1 :
                    // InternalKactors.g:3940:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:3940:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) )
                    // InternalKactors.g:3941:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) )
                    {
                    // InternalKactors.g:3941:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:3942:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:3942:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:3943:6: lv_id_0_0= RULE_LOWERCASE_ID
                    {
                    lv_id_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_59); if (state.failed) return current;
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

                    otherlv_1=(Token)match(input,65,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_0_1());
                      			
                    }
                    // InternalKactors.g:3963:4: ( (lv_body_2_0= ruleBody ) )
                    // InternalKactors.g:3964:5: (lv_body_2_0= ruleBody )
                    {
                    // InternalKactors.g:3964:5: (lv_body_2_0= ruleBody )
                    // InternalKactors.g:3965:6: lv_body_2_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_0_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_2_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3984:3: ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:3984:3: ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) )
                    // InternalKactors.g:3985:4: ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) )
                    {
                    // InternalKactors.g:3985:4: ( (lv_regexp_3_0= RULE_REGEXP ) )
                    // InternalKactors.g:3986:5: (lv_regexp_3_0= RULE_REGEXP )
                    {
                    // InternalKactors.g:3986:5: (lv_regexp_3_0= RULE_REGEXP )
                    // InternalKactors.g:3987:6: lv_regexp_3_0= RULE_REGEXP
                    {
                    lv_regexp_3_0=(Token)match(input,RULE_REGEXP,FOLLOW_59); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_regexp_3_0, grammarAccess.getMatchAccess().getRegexpREGEXPTerminalRuleCall_1_0_0());
                      					
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

                    otherlv_4=(Token)match(input,65,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:4007:4: ( (lv_body_5_0= ruleBody ) )
                    // InternalKactors.g:4008:5: (lv_body_5_0= ruleBody )
                    {
                    // InternalKactors.g:4008:5: (lv_body_5_0= ruleBody )
                    // InternalKactors.g:4009:6: lv_body_5_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_5_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:4028:3: ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4028:3: ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) )
                    // InternalKactors.g:4029:4: ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) )
                    {
                    // InternalKactors.g:4029:4: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:4030:5: (lv_observable_6_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:4030:5: (lv_observable_6_0= RULE_OBSERVABLE )
                    // InternalKactors.g:4031:6: lv_observable_6_0= RULE_OBSERVABLE
                    {
                    lv_observable_6_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_59); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_observable_6_0, grammarAccess.getMatchAccess().getObservableOBSERVABLETerminalRuleCall_2_0_0());
                      					
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

                    otherlv_7=(Token)match(input,65,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_2_1());
                      			
                    }
                    // InternalKactors.g:4051:4: ( (lv_body_8_0= ruleBody ) )
                    // InternalKactors.g:4052:5: (lv_body_8_0= ruleBody )
                    {
                    // InternalKactors.g:4052:5: (lv_body_8_0= ruleBody )
                    // InternalKactors.g:4053:6: lv_body_8_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_2_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_8_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:4072:3: ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4072:3: ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) )
                    // InternalKactors.g:4073:4: ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) )
                    {
                    // InternalKactors.g:4073:4: ( (lv_literal_9_0= ruleLiteral ) )
                    // InternalKactors.g:4074:5: (lv_literal_9_0= ruleLiteral )
                    {
                    // InternalKactors.g:4074:5: (lv_literal_9_0= ruleLiteral )
                    // InternalKactors.g:4075:6: lv_literal_9_0= ruleLiteral
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_59);
                    lv_literal_9_0=ruleLiteral();

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

                    otherlv_10=(Token)match(input,65,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_3_1());
                      			
                    }
                    // InternalKactors.g:4096:4: ( (lv_body_11_0= ruleBody ) )
                    // InternalKactors.g:4097:5: (lv_body_11_0= ruleBody )
                    {
                    // InternalKactors.g:4097:5: (lv_body_11_0= ruleBody )
                    // InternalKactors.g:4098:6: lv_body_11_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_3_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_11_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:4117:3: ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4117:3: ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) )
                    // InternalKactors.g:4118:4: ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) )
                    {
                    // InternalKactors.g:4118:4: ( (lv_text_12_0= RULE_STRING ) )
                    // InternalKactors.g:4119:5: (lv_text_12_0= RULE_STRING )
                    {
                    // InternalKactors.g:4119:5: (lv_text_12_0= RULE_STRING )
                    // InternalKactors.g:4120:6: lv_text_12_0= RULE_STRING
                    {
                    lv_text_12_0=(Token)match(input,RULE_STRING,FOLLOW_59); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_text_12_0, grammarAccess.getMatchAccess().getTextSTRINGTerminalRuleCall_4_0_0());
                      					
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

                    otherlv_13=(Token)match(input,65,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_4_1());
                      			
                    }
                    // InternalKactors.g:4140:4: ( (lv_body_14_0= ruleBody ) )
                    // InternalKactors.g:4141:5: (lv_body_14_0= ruleBody )
                    {
                    // InternalKactors.g:4141:5: (lv_body_14_0= ruleBody )
                    // InternalKactors.g:4142:6: lv_body_14_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_4_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_14_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:4161:3: ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4161:3: ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) )
                    // InternalKactors.g:4162:4: ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) )
                    {
                    // InternalKactors.g:4162:4: ( (lv_arguments_15_0= ruleArgumentDeclaration ) )
                    // InternalKactors.g:4163:5: (lv_arguments_15_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:4163:5: (lv_arguments_15_0= ruleArgumentDeclaration )
                    // InternalKactors.g:4164:6: lv_arguments_15_0= ruleArgumentDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getArgumentsArgumentDeclarationParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_59);
                    lv_arguments_15_0=ruleArgumentDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"arguments",
                      							lv_arguments_15_0,
                      							"org.integratedmodelling.kactors.Kactors.ArgumentDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_16=(Token)match(input,65,FOLLOW_13); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_5_1());
                      			
                    }
                    // InternalKactors.g:4185:4: ( (lv_body_17_0= ruleBody ) )
                    // InternalKactors.g:4186:5: (lv_body_17_0= ruleBody )
                    {
                    // InternalKactors.g:4186:5: (lv_body_17_0= ruleBody )
                    // InternalKactors.g:4187:6: lv_body_17_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_5_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_17_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
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
    // InternalKactors.g:4209:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKactors.g:4209:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKactors.g:4210:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKactors.g:4216:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) ;
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
            // InternalKactors.g:4222:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) )
            // InternalKactors.g:4223:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            {
            // InternalKactors.g:4223:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            // InternalKactors.g:4224:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            {
            // InternalKactors.g:4224:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt69=3;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==66) ) {
                alt69=1;
            }
            else if ( (LA69_0==67) ) {
                alt69=2;
            }
            switch (alt69) {
                case 1 :
                    // InternalKactors.g:4225:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,66,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:4230:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKactors.g:4230:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKactors.g:4231:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKactors.g:4231:5: (lv_negative_1_0= '-' )
                    // InternalKactors.g:4232:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,67,FOLLOW_8); if (state.failed) return current;
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

            // InternalKactors.g:4245:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKactors.g:4246:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKactors.g:4250:4: (lv_real_2_0= RULE_INT )
            // InternalKactors.g:4251:5: lv_real_2_0= RULE_INT
            {
            lv_real_2_0=(Token)match(input,RULE_INT,FOLLOW_60); if (state.failed) return current;
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

            // InternalKactors.g:4267:3: ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==68) && (synpred137_InternalKactors())) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // InternalKactors.g:4268:4: ( ( 'l' ) )=> (lv_long_3_0= 'l' )
                    {
                    // InternalKactors.g:4272:4: (lv_long_3_0= 'l' )
                    // InternalKactors.g:4273:5: lv_long_3_0= 'l'
                    {
                    lv_long_3_0=(Token)match(input,68,FOLLOW_61); if (state.failed) return current;
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

            // InternalKactors.g:4285:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==55) ) {
                int LA71_1 = input.LA(2);

                if ( (LA71_1==RULE_INT) && (synpred138_InternalKactors())) {
                    alt71=1;
                }
            }
            switch (alt71) {
                case 1 :
                    // InternalKactors.g:4286:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:4299:4: ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    // InternalKactors.g:4300:5: ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) )
                    {
                    // InternalKactors.g:4300:5: ( (lv_decimal_4_0= '.' ) )
                    // InternalKactors.g:4301:6: (lv_decimal_4_0= '.' )
                    {
                    // InternalKactors.g:4301:6: (lv_decimal_4_0= '.' )
                    // InternalKactors.g:4302:7: lv_decimal_4_0= '.'
                    {
                    lv_decimal_4_0=(Token)match(input,55,FOLLOW_8); if (state.failed) return current;
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

                    // InternalKactors.g:4314:5: ( (lv_decimalPart_5_0= RULE_INT ) )
                    // InternalKactors.g:4315:6: (lv_decimalPart_5_0= RULE_INT )
                    {
                    // InternalKactors.g:4315:6: (lv_decimalPart_5_0= RULE_INT )
                    // InternalKactors.g:4316:7: lv_decimalPart_5_0= RULE_INT
                    {
                    lv_decimalPart_5_0=(Token)match(input,RULE_INT,FOLLOW_62); if (state.failed) return current;
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

            // InternalKactors.g:4334:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==69) && (synpred142_InternalKactors())) {
                alt74=1;
            }
            else if ( (LA74_0==70) && (synpred142_InternalKactors())) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // InternalKactors.g:4335:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:4361:4: ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    // InternalKactors.g:4362:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) )
                    {
                    // InternalKactors.g:4362:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) )
                    // InternalKactors.g:4363:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    {
                    // InternalKactors.g:4363:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    // InternalKactors.g:4364:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    {
                    // InternalKactors.g:4364:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==69) ) {
                        alt72=1;
                    }
                    else if ( (LA72_0==70) ) {
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
                            // InternalKactors.g:4365:8: lv_exponential_6_1= 'e'
                            {
                            lv_exponential_6_1=(Token)match(input,69,FOLLOW_33); if (state.failed) return current;
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
                            // InternalKactors.g:4376:8: lv_exponential_6_2= 'E'
                            {
                            lv_exponential_6_2=(Token)match(input,70,FOLLOW_33); if (state.failed) return current;
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

                    // InternalKactors.g:4389:5: (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )?
                    int alt73=3;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==66) ) {
                        alt73=1;
                    }
                    else if ( (LA73_0==67) ) {
                        alt73=2;
                    }
                    switch (alt73) {
                        case 1 :
                            // InternalKactors.g:4390:6: otherlv_7= '+'
                            {
                            otherlv_7=(Token)match(input,66,FOLLOW_8); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getNumberAccess().getPlusSignKeyword_4_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:4395:6: ( (lv_expNegative_8_0= '-' ) )
                            {
                            // InternalKactors.g:4395:6: ( (lv_expNegative_8_0= '-' ) )
                            // InternalKactors.g:4396:7: (lv_expNegative_8_0= '-' )
                            {
                            // InternalKactors.g:4396:7: (lv_expNegative_8_0= '-' )
                            // InternalKactors.g:4397:8: lv_expNegative_8_0= '-'
                            {
                            lv_expNegative_8_0=(Token)match(input,67,FOLLOW_8); if (state.failed) return current;
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

                    // InternalKactors.g:4410:5: ( (lv_exp_9_0= RULE_INT ) )
                    // InternalKactors.g:4411:6: (lv_exp_9_0= RULE_INT )
                    {
                    // InternalKactors.g:4411:6: (lv_exp_9_0= RULE_INT )
                    // InternalKactors.g:4412:7: lv_exp_9_0= RULE_INT
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
    // InternalKactors.g:4434:1: entryRuleDate returns [EObject current=null] : iv_ruleDate= ruleDate EOF ;
    public final EObject entryRuleDate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDate = null;


        try {
            // InternalKactors.g:4434:45: (iv_ruleDate= ruleDate EOF )
            // InternalKactors.g:4435:2: iv_ruleDate= ruleDate EOF
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
    // InternalKactors.g:4441:1: ruleDate returns [EObject current=null] : ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) ;
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
            // InternalKactors.g:4447:2: ( ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) )
            // InternalKactors.g:4448:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            {
            // InternalKactors.g:4448:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            // InternalKactors.g:4449:3: ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            {
            // InternalKactors.g:4449:3: ( (lv_year_0_0= RULE_INT ) )
            // InternalKactors.g:4450:4: (lv_year_0_0= RULE_INT )
            {
            // InternalKactors.g:4450:4: (lv_year_0_0= RULE_INT )
            // InternalKactors.g:4451:5: lv_year_0_0= RULE_INT
            {
            lv_year_0_0=(Token)match(input,RULE_INT,FOLLOW_63); if (state.failed) return current;
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

            // InternalKactors.g:4467:3: (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )?
            int alt75=4;
            switch ( input.LA(1) ) {
                case 71:
                    {
                    alt75=1;
                    }
                    break;
                case 72:
                    {
                    alt75=2;
                    }
                    break;
                case 73:
                    {
                    alt75=3;
                    }
                    break;
            }

            switch (alt75) {
                case 1 :
                    // InternalKactors.g:4468:4: otherlv_1= 'AD'
                    {
                    otherlv_1=(Token)match(input,71,FOLLOW_64); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getDateAccess().getADKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:4473:4: otherlv_2= 'CE'
                    {
                    otherlv_2=(Token)match(input,72,FOLLOW_64); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getDateAccess().getCEKeyword_1_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKactors.g:4478:4: ( (lv_bc_3_0= 'BC' ) )
                    {
                    // InternalKactors.g:4478:4: ( (lv_bc_3_0= 'BC' ) )
                    // InternalKactors.g:4479:5: (lv_bc_3_0= 'BC' )
                    {
                    // InternalKactors.g:4479:5: (lv_bc_3_0= 'BC' )
                    // InternalKactors.g:4480:6: lv_bc_3_0= 'BC'
                    {
                    lv_bc_3_0=(Token)match(input,73,FOLLOW_64); if (state.failed) return current;
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

            otherlv_4=(Token)match(input,67,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDateAccess().getHyphenMinusKeyword_2());
              		
            }
            // InternalKactors.g:4497:3: ( (lv_month_5_0= RULE_INT ) )
            // InternalKactors.g:4498:4: (lv_month_5_0= RULE_INT )
            {
            // InternalKactors.g:4498:4: (lv_month_5_0= RULE_INT )
            // InternalKactors.g:4499:5: lv_month_5_0= RULE_INT
            {
            lv_month_5_0=(Token)match(input,RULE_INT,FOLLOW_64); if (state.failed) return current;
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

            otherlv_6=(Token)match(input,67,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getDateAccess().getHyphenMinusKeyword_4());
              		
            }
            // InternalKactors.g:4519:3: ( (lv_day_7_0= RULE_INT ) )
            // InternalKactors.g:4520:4: (lv_day_7_0= RULE_INT )
            {
            // InternalKactors.g:4520:4: (lv_day_7_0= RULE_INT )
            // InternalKactors.g:4521:5: lv_day_7_0= RULE_INT
            {
            lv_day_7_0=(Token)match(input,RULE_INT,FOLLOW_65); if (state.failed) return current;
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

            // InternalKactors.g:4537:3: ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==RULE_INT) ) {
                int LA78_1 = input.LA(2);

                if ( (LA78_1==32) ) {
                    alt78=1;
                }
            }
            switch (alt78) {
                case 1 :
                    // InternalKactors.g:4538:4: ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    {
                    // InternalKactors.g:4538:4: ( (lv_hour_8_0= RULE_INT ) )
                    // InternalKactors.g:4539:5: (lv_hour_8_0= RULE_INT )
                    {
                    // InternalKactors.g:4539:5: (lv_hour_8_0= RULE_INT )
                    // InternalKactors.g:4540:6: lv_hour_8_0= RULE_INT
                    {
                    lv_hour_8_0=(Token)match(input,RULE_INT,FOLLOW_12); if (state.failed) return current;
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

                    otherlv_9=(Token)match(input,32,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getDateAccess().getColonKeyword_6_1());
                      			
                    }
                    // InternalKactors.g:4560:4: ( (lv_min_10_0= RULE_INT ) )
                    // InternalKactors.g:4561:5: (lv_min_10_0= RULE_INT )
                    {
                    // InternalKactors.g:4561:5: (lv_min_10_0= RULE_INT )
                    // InternalKactors.g:4562:6: lv_min_10_0= RULE_INT
                    {
                    lv_min_10_0=(Token)match(input,RULE_INT,FOLLOW_66); if (state.failed) return current;
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

                    // InternalKactors.g:4578:4: (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    int alt77=2;
                    int LA77_0 = input.LA(1);

                    if ( (LA77_0==32) ) {
                        alt77=1;
                    }
                    switch (alt77) {
                        case 1 :
                            // InternalKactors.g:4579:5: otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            {
                            otherlv_11=(Token)match(input,32,FOLLOW_8); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_11, grammarAccess.getDateAccess().getColonKeyword_6_3_0());
                              				
                            }
                            // InternalKactors.g:4583:5: ( (lv_sec_12_0= RULE_INT ) )
                            // InternalKactors.g:4584:6: (lv_sec_12_0= RULE_INT )
                            {
                            // InternalKactors.g:4584:6: (lv_sec_12_0= RULE_INT )
                            // InternalKactors.g:4585:7: lv_sec_12_0= RULE_INT
                            {
                            lv_sec_12_0=(Token)match(input,RULE_INT,FOLLOW_67); if (state.failed) return current;
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

                            // InternalKactors.g:4601:5: (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            int alt76=2;
                            int LA76_0 = input.LA(1);

                            if ( (LA76_0==55) ) {
                                alt76=1;
                            }
                            switch (alt76) {
                                case 1 :
                                    // InternalKactors.g:4602:6: otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) )
                                    {
                                    otherlv_13=(Token)match(input,55,FOLLOW_8); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						newLeafNode(otherlv_13, grammarAccess.getDateAccess().getFullStopKeyword_6_3_2_0());
                                      					
                                    }
                                    // InternalKactors.g:4606:6: ( (lv_ms_14_0= RULE_INT ) )
                                    // InternalKactors.g:4607:7: (lv_ms_14_0= RULE_INT )
                                    {
                                    // InternalKactors.g:4607:7: (lv_ms_14_0= RULE_INT )
                                    // InternalKactors.g:4608:8: lv_ms_14_0= RULE_INT
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
    // InternalKactors.g:4631:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKactors.g:4631:48: (iv_rulePathName= rulePathName EOF )
            // InternalKactors.g:4632:2: iv_rulePathName= rulePathName EOF
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
    // InternalKactors.g:4638:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKactors.g:4644:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKactors.g:4645:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKactors.g:4645:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKactors.g:4646:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:4653:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( (LA79_0==55) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // InternalKactors.g:4654:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,55,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPathNameAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_67); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop79;
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
    // InternalKactors.g:4671:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKactors.g:4671:44: (iv_rulePath= rulePath EOF )
            // InternalKactors.g:4672:2: iv_rulePath= rulePath EOF
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
    // InternalKactors.g:4678:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token this_UPPERCASE_ID_1=null;
        Token kw=null;
        Token this_LOWERCASE_ID_4=null;
        Token this_UPPERCASE_ID_5=null;


        	enterRule();

        try {
            // InternalKactors.g:4684:2: ( ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) )
            // InternalKactors.g:4685:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            {
            // InternalKactors.g:4685:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            // InternalKactors.g:4686:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            {
            // InternalKactors.g:4686:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID )
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==RULE_LOWERCASE_ID) ) {
                alt80=1;
            }
            else if ( (LA80_0==RULE_UPPERCASE_ID) ) {
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
                    // InternalKactors.g:4687:4: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:4695:4: this_UPPERCASE_ID_1= RULE_UPPERCASE_ID
                    {
                    this_UPPERCASE_ID_1=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_UPPERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_UPPERCASE_ID_1, grammarAccess.getPathAccess().getUPPERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:4703:3: ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( ((LA83_0>=54 && LA83_0<=55)) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // InternalKactors.g:4704:4: (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    {
            	    // InternalKactors.g:4704:4: (kw= '.' | kw= '/' )
            	    int alt81=2;
            	    int LA81_0 = input.LA(1);

            	    if ( (LA81_0==55) ) {
            	        alt81=1;
            	    }
            	    else if ( (LA81_0==54) ) {
            	        alt81=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 81, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt81) {
            	        case 1 :
            	            // InternalKactors.g:4705:5: kw= '.'
            	            {
            	            kw=(Token)match(input,55,FOLLOW_25); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:4711:5: kw= '/'
            	            {
            	            kw=(Token)match(input,54,FOLLOW_25); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getSolidusKeyword_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalKactors.g:4717:4: (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    int alt82=2;
            	    int LA82_0 = input.LA(1);

            	    if ( (LA82_0==RULE_LOWERCASE_ID) ) {
            	        alt82=1;
            	    }
            	    else if ( (LA82_0==RULE_UPPERCASE_ID) ) {
            	        alt82=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 82, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt82) {
            	        case 1 :
            	            // InternalKactors.g:4718:5: this_LOWERCASE_ID_4= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_4=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_68); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_4, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:4726:5: this_UPPERCASE_ID_5= RULE_UPPERCASE_ID
            	            {
            	            this_UPPERCASE_ID_5=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_68); if (state.failed) return current;
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
            	    break loop83;
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
    // InternalKactors.g:4739:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKactors.g:4739:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKactors.g:4740:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalKactors.g:4746:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
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
            // InternalKactors.g:4752:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKactors.g:4753:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKactors.g:4753:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKactors.g:4754:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_69); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:4761:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==55) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // InternalKactors.g:4762:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,55,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKactors.g:4774:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt84=2;
                    int LA84_0 = input.LA(1);

                    if ( (LA84_0==55) ) {
                        alt84=1;
                    }
                    switch (alt84) {
                        case 1 :
                            // InternalKactors.g:4775:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,55,FOLLOW_8); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_70); if (state.failed) return current;
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

            // InternalKactors.g:4789:3: (kw= '-' )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==67) ) {
                int LA86_1 = input.LA(2);

                if ( (synpred159_InternalKactors()) ) {
                    alt86=1;
                }
            }
            switch (alt86) {
                case 1 :
                    // InternalKactors.g:4790:4: kw= '-'
                    {
                    kw=(Token)match(input,67,FOLLOW_71); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:4796:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt87=3;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==RULE_LOWERCASE_ID) ) {
                int LA87_1 = input.LA(2);

                if ( (synpred160_InternalKactors()) ) {
                    alt87=1;
                }
            }
            else if ( (LA87_0==RULE_UPPERCASE_ID) ) {
                alt87=2;
            }
            switch (alt87) {
                case 1 :
                    // InternalKactors.g:4797:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:4805:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
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
    // InternalKactors.g:4817:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKactors.g:4823:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKactors.g:4824:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKactors.g:4824:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt88=3;
            switch ( input.LA(1) ) {
            case 54:
                {
                alt88=1;
                }
                break;
            case 74:
                {
                alt88=2;
                }
                break;
            case 50:
                {
                alt88=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }

            switch (alt88) {
                case 1 :
                    // InternalKactors.g:4825:3: (enumLiteral_0= '/' )
                    {
                    // InternalKactors.g:4825:3: (enumLiteral_0= '/' )
                    // InternalKactors.g:4826:4: enumLiteral_0= '/'
                    {
                    enumLiteral_0=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:4833:3: (enumLiteral_1= '^' )
                    {
                    // InternalKactors.g:4833:3: (enumLiteral_1= '^' )
                    // InternalKactors.g:4834:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,74,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:4841:3: (enumLiteral_2= '*' )
                    {
                    // InternalKactors.g:4841:3: (enumLiteral_2= '*' )
                    // InternalKactors.g:4842:4: enumLiteral_2= '*'
                    {
                    enumLiteral_2=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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

    // $ANTLR start synpred3_InternalKactors
    public final void synpred3_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;


        // InternalKactors.g:170:3: ( ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) ) )
        // InternalKactors.g:170:3: ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) )
        {
        // InternalKactors.g:170:3: ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) )
        // InternalKactors.g:171:4: {...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred3_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 0)");
        }
        // InternalKactors.g:171:102: ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) )
        // InternalKactors.g:172:5: ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 0);
        // InternalKactors.g:175:8: ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) )
        // InternalKactors.g:175:9: {...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred3_InternalKactors", "true");
        }
        // InternalKactors.g:175:18: (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) )
        // InternalKactors.g:175:19: otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) )
        {
        otherlv_1=(Token)match(input,22,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:179:8: ( (lv_name_2_0= rulePathName ) )
        // InternalKactors.g:180:9: (lv_name_2_0= rulePathName )
        {
        // InternalKactors.g:180:9: (lv_name_2_0= rulePathName )
        // InternalKactors.g:181:10: lv_name_2_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          										newCompositeNode(grammarAccess.getPreambleAccess().getNamePathNameParserRuleCall_0_1_0());
          									
        }
        pushFollow(FOLLOW_2);
        lv_name_2_0=rulePathName();

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
    // $ANTLR end synpred3_InternalKactors

    // $ANTLR start synpred4_InternalKactors
    public final void synpred4_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_worldview_4_0 = null;


        // InternalKactors.g:204:3: ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) ) )
        // InternalKactors.g:204:3: ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) )
        {
        // InternalKactors.g:204:3: ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) )
        // InternalKactors.g:205:4: {...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred4_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 1)");
        }
        // InternalKactors.g:205:102: ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) )
        // InternalKactors.g:206:5: ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 1);
        // InternalKactors.g:209:8: ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) )
        // InternalKactors.g:209:9: {...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred4_InternalKactors", "true");
        }
        // InternalKactors.g:209:18: (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) )
        // InternalKactors.g:209:19: otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) )
        {
        otherlv_3=(Token)match(input,23,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:213:8: ( (lv_worldview_4_0= rulePathName ) )
        // InternalKactors.g:214:9: (lv_worldview_4_0= rulePathName )
        {
        // InternalKactors.g:214:9: (lv_worldview_4_0= rulePathName )
        // InternalKactors.g:215:10: lv_worldview_4_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          										newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_1_1_0());
          									
        }
        pushFollow(FOLLOW_2);
        lv_worldview_4_0=rulePathName();

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
    // $ANTLR end synpred4_InternalKactors

    // $ANTLR start synpred7_InternalKactors
    public final void synpred7_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_5=null;
        Token lv_label_6_1=null;
        Token lv_label_6_2=null;
        Token lv_label_6_3=null;

        // InternalKactors.g:238:3: ( ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) ) )
        // InternalKactors.g:238:3: ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) )
        {
        // InternalKactors.g:238:3: ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:239:4: {...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred7_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 2)");
        }
        // InternalKactors.g:239:102: ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:240:5: ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 2);
        // InternalKactors.g:243:8: ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) )
        // InternalKactors.g:243:9: {...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred7_InternalKactors", "true");
        }
        // InternalKactors.g:243:18: (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) )
        // InternalKactors.g:243:19: otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) )
        {
        otherlv_5=(Token)match(input,24,FOLLOW_6); if (state.failed) return ;
        // InternalKactors.g:247:8: ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) )
        // InternalKactors.g:248:9: ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) )
        {
        // InternalKactors.g:248:9: ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) )
        // InternalKactors.g:249:10: (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING )
        {
        // InternalKactors.g:249:10: (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING )
        int alt89=3;
        switch ( input.LA(1) ) {
        case RULE_LOWERCASE_ID:
            {
            alt89=1;
            }
            break;
        case RULE_ID:
            {
            alt89=2;
            }
            break;
        case RULE_STRING:
            {
            alt89=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 89, 0, input);

            throw nvae;
        }

        switch (alt89) {
            case 1 :
                // InternalKactors.g:250:11: lv_label_6_1= RULE_LOWERCASE_ID
                {
                lv_label_6_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:265:11: lv_label_6_2= RULE_ID
                {
                lv_label_6_2=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 3 :
                // InternalKactors.g:280:11: lv_label_6_3= RULE_STRING
                {
                lv_label_6_3=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred7_InternalKactors

    // $ANTLR start synpred8_InternalKactors
    public final void synpred8_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        Token lv_description_8_0=null;

        // InternalKactors.g:303:3: ( ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:303:3: ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:303:3: ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:304:4: {...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred8_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 3)");
        }
        // InternalKactors.g:304:102: ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:305:5: ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 3);
        // InternalKactors.g:308:8: ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) )
        // InternalKactors.g:308:9: {...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred8_InternalKactors", "true");
        }
        // InternalKactors.g:308:18: (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) )
        // InternalKactors.g:308:19: otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) )
        {
        otherlv_7=(Token)match(input,25,FOLLOW_7); if (state.failed) return ;
        // InternalKactors.g:312:8: ( (lv_description_8_0= RULE_STRING ) )
        // InternalKactors.g:313:9: (lv_description_8_0= RULE_STRING )
        {
        // InternalKactors.g:313:9: (lv_description_8_0= RULE_STRING )
        // InternalKactors.g:314:10: lv_description_8_0= RULE_STRING
        {
        lv_description_8_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred8_InternalKactors

    // $ANTLR start synpred9_InternalKactors
    public final void synpred9_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_9=null;
        Token lv_permissions_10_0=null;

        // InternalKactors.g:336:3: ( ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:336:3: ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:336:3: ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:337:4: {...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 4) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred9_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 4)");
        }
        // InternalKactors.g:337:102: ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:338:5: ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 4);
        // InternalKactors.g:341:8: ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) )
        // InternalKactors.g:341:9: {...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred9_InternalKactors", "true");
        }
        // InternalKactors.g:341:18: (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) )
        // InternalKactors.g:341:19: otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) )
        {
        otherlv_9=(Token)match(input,26,FOLLOW_7); if (state.failed) return ;
        // InternalKactors.g:345:8: ( (lv_permissions_10_0= RULE_STRING ) )
        // InternalKactors.g:346:9: (lv_permissions_10_0= RULE_STRING )
        {
        // InternalKactors.g:346:9: (lv_permissions_10_0= RULE_STRING )
        // InternalKactors.g:347:10: lv_permissions_10_0= RULE_STRING
        {
        lv_permissions_10_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
        Token otherlv_11=null;
        Token lv_authors_12_0=null;

        // InternalKactors.g:374:9: ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )
        // InternalKactors.g:374:9: {...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred10_InternalKactors", "true");
        }
        // InternalKactors.g:374:18: (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) )
        // InternalKactors.g:374:19: otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) )
        {
        otherlv_11=(Token)match(input,27,FOLLOW_7); if (state.failed) return ;
        // InternalKactors.g:378:8: ( (lv_authors_12_0= RULE_STRING ) )
        // InternalKactors.g:379:9: (lv_authors_12_0= RULE_STRING )
        {
        // InternalKactors.g:379:9: (lv_authors_12_0= RULE_STRING )
        // InternalKactors.g:380:10: lv_authors_12_0= RULE_STRING
        {
        lv_authors_12_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred10_InternalKactors

    // $ANTLR start synpred11_InternalKactors
    public final void synpred11_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_11=null;
        Token lv_authors_12_0=null;

        // InternalKactors.g:369:3: ( ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) ) )
        // InternalKactors.g:369:3: ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) )
        {
        // InternalKactors.g:369:3: ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) )
        // InternalKactors.g:370:4: {...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 5) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred11_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 5)");
        }
        // InternalKactors.g:370:102: ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ )
        // InternalKactors.g:371:5: ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 5);
        // InternalKactors.g:374:8: ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+
        int cnt90=0;
        loop90:
        do {
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==27) && ((true))) {
                alt90=1;
            }


            switch (alt90) {
        	case 1 :
        	    // InternalKactors.g:374:9: {...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) )
        	    {
        	    if ( !((true)) ) {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        throw new FailedPredicateException(input, "synpred11_InternalKactors", "true");
        	    }
        	    // InternalKactors.g:374:18: (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) )
        	    // InternalKactors.g:374:19: otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) )
        	    {
        	    otherlv_11=(Token)match(input,27,FOLLOW_7); if (state.failed) return ;
        	    // InternalKactors.g:378:8: ( (lv_authors_12_0= RULE_STRING ) )
        	    // InternalKactors.g:379:9: (lv_authors_12_0= RULE_STRING )
        	    {
        	    // InternalKactors.g:379:9: (lv_authors_12_0= RULE_STRING )
        	    // InternalKactors.g:380:10: lv_authors_12_0= RULE_STRING
        	    {
        	    lv_authors_12_0=(Token)match(input,RULE_STRING,FOLLOW_72); if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt90 >= 1 ) break loop90;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(90, input);
                    throw eee;
            }
            cnt90++;
        } while (true);


        }


        }


        }
    }
    // $ANTLR end synpred11_InternalKactors

    // $ANTLR start synpred12_InternalKactors
    public final void synpred12_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_13=null;
        AntlrDatatypeRuleToken lv_version_14_0 = null;


        // InternalKactors.g:402:3: ( ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) ) )
        // InternalKactors.g:402:3: ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) )
        {
        // InternalKactors.g:402:3: ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) )
        // InternalKactors.g:403:4: {...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 6) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred12_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 6)");
        }
        // InternalKactors.g:403:102: ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) )
        // InternalKactors.g:404:5: ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 6);
        // InternalKactors.g:407:8: ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) )
        // InternalKactors.g:407:9: {...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred12_InternalKactors", "true");
        }
        // InternalKactors.g:407:18: (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) )
        // InternalKactors.g:407:19: otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) )
        {
        otherlv_13=(Token)match(input,28,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:411:8: ( (lv_version_14_0= ruleVersionNumber ) )
        // InternalKactors.g:412:9: (lv_version_14_0= ruleVersionNumber )
        {
        // InternalKactors.g:412:9: (lv_version_14_0= ruleVersionNumber )
        // InternalKactors.g:413:10: lv_version_14_0= ruleVersionNumber
        {
        if ( state.backtracking==0 ) {

          										newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_6_1_0());
          									
        }
        pushFollow(FOLLOW_2);
        lv_version_14_0=ruleVersionNumber();

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
    // $ANTLR end synpred12_InternalKactors

    // $ANTLR start synpred13_InternalKactors
    public final void synpred13_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_15=null;
        EObject lv_created_16_0 = null;


        // InternalKactors.g:436:3: ( ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) ) )
        // InternalKactors.g:436:3: ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) )
        {
        // InternalKactors.g:436:3: ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) )
        // InternalKactors.g:437:4: {...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 7) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 7)");
        }
        // InternalKactors.g:437:102: ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) )
        // InternalKactors.g:438:5: ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 7);
        // InternalKactors.g:441:8: ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) )
        // InternalKactors.g:441:9: {...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKactors", "true");
        }
        // InternalKactors.g:441:18: (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) )
        // InternalKactors.g:441:19: otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) )
        {
        otherlv_15=(Token)match(input,29,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:445:8: ( (lv_created_16_0= ruleDate ) )
        // InternalKactors.g:446:9: (lv_created_16_0= ruleDate )
        {
        // InternalKactors.g:446:9: (lv_created_16_0= ruleDate )
        // InternalKactors.g:447:10: lv_created_16_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          										newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_7_1_0());
          									
        }
        pushFollow(FOLLOW_2);
        lv_created_16_0=ruleDate();

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
        Token otherlv_17=null;
        Token lv_modcomment_19_0=null;
        EObject lv_modified_18_0 = null;


        // InternalKactors.g:470:3: ( ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:470:3: ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:470:3: ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:471:4: {...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 8) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred15_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 8)");
        }
        // InternalKactors.g:471:102: ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:472:5: ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup(), 8);
        // InternalKactors.g:475:8: ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) )
        // InternalKactors.g:475:9: {...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred15_InternalKactors", "true");
        }
        // InternalKactors.g:475:18: (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? )
        // InternalKactors.g:475:19: otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )?
        {
        otherlv_17=(Token)match(input,30,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:479:8: ( (lv_modified_18_0= ruleDate ) )
        // InternalKactors.g:480:9: (lv_modified_18_0= ruleDate )
        {
        // InternalKactors.g:480:9: (lv_modified_18_0= ruleDate )
        // InternalKactors.g:481:10: lv_modified_18_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          										newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_8_1_0());
          									
        }
        pushFollow(FOLLOW_73);
        lv_modified_18_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:498:8: ( (lv_modcomment_19_0= RULE_STRING ) )?
        int alt91=2;
        int LA91_0 = input.LA(1);

        if ( (LA91_0==RULE_STRING) ) {
            alt91=1;
        }
        switch (alt91) {
            case 1 :
                // InternalKactors.g:499:9: (lv_modcomment_19_0= RULE_STRING )
                {
                // InternalKactors.g:499:9: (lv_modcomment_19_0= RULE_STRING )
                // InternalKactors.g:500:10: lv_modcomment_19_0= RULE_STRING
                {
                lv_modcomment_19_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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

    // $ANTLR start synpred25_InternalKactors
    public final void synpred25_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_literal_1_0 = null;


        // InternalKactors.g:898:3: ( ( (lv_literal_1_0= ruleLiteral ) ) )
        // InternalKactors.g:898:3: ( (lv_literal_1_0= ruleLiteral ) )
        {
        // InternalKactors.g:898:3: ( (lv_literal_1_0= ruleLiteral ) )
        // InternalKactors.g:899:4: (lv_literal_1_0= ruleLiteral )
        {
        // InternalKactors.g:899:4: (lv_literal_1_0= ruleLiteral )
        // InternalKactors.g:900:5: lv_literal_1_0= ruleLiteral
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getLiteralLiteralParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_literal_1_0=ruleLiteral();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred25_InternalKactors

    // $ANTLR start synpred26_InternalKactors
    public final void synpred26_InternalKactors_fragment() throws RecognitionException {   
        Token lv_id_2_0=null;

        // InternalKactors.g:918:3: ( ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) )
        // InternalKactors.g:918:3: ( (lv_id_2_0= RULE_LOWERCASE_ID ) )
        {
        // InternalKactors.g:918:3: ( (lv_id_2_0= RULE_LOWERCASE_ID ) )
        // InternalKactors.g:919:4: (lv_id_2_0= RULE_LOWERCASE_ID )
        {
        // InternalKactors.g:919:4: (lv_id_2_0= RULE_LOWERCASE_ID )
        // InternalKactors.g:920:5: lv_id_2_0= RULE_LOWERCASE_ID
        {
        lv_id_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred26_InternalKactors

    // $ANTLR start synpred27_InternalKactors
    public final void synpred27_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_urn_3_0 = null;


        // InternalKactors.g:937:3: ( ( (lv_urn_3_0= ruleUrn ) ) )
        // InternalKactors.g:937:3: ( (lv_urn_3_0= ruleUrn ) )
        {
        // InternalKactors.g:937:3: ( (lv_urn_3_0= ruleUrn ) )
        // InternalKactors.g:938:4: (lv_urn_3_0= ruleUrn )
        {
        // InternalKactors.g:938:4: (lv_urn_3_0= ruleUrn )
        // InternalKactors.g:939:5: lv_urn_3_0= ruleUrn
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getUrnUrnParserRuleCall_3_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_urn_3_0=ruleUrn();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred27_InternalKactors

    // $ANTLR start synpred52_InternalKactors
    public final void synpred52_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:1708:5: ( 'to' )
        // InternalKactors.g:1708:6: 'to'
        {
        match(input,47,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred52_InternalKactors

    // $ANTLR start synpred75_InternalKactors
    public final void synpred75_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:2328:5: ( 'to' )
        // InternalKactors.g:2328:6: 'to'
        {
        match(input,47,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred75_InternalKactors

    // $ANTLR start synpred105_InternalKactors
    public final void synpred105_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_number_0_0 = null;


        // InternalKactors.g:3119:3: ( ( (lv_number_0_0= ruleNumber ) ) )
        // InternalKactors.g:3119:3: ( (lv_number_0_0= ruleNumber ) )
        {
        // InternalKactors.g:3119:3: ( (lv_number_0_0= ruleNumber ) )
        // InternalKactors.g:3120:4: (lv_number_0_0= ruleNumber )
        {
        // InternalKactors.g:3120:4: (lv_number_0_0= ruleNumber )
        // InternalKactors.g:3121:5: lv_number_0_0= ruleNumber
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
    // $ANTLR end synpred105_InternalKactors

    // $ANTLR start synpred108_InternalKactors
    public final void synpred108_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_date_5_0 = null;


        // InternalKactors.g:3203:3: ( ( (lv_date_5_0= ruleDate ) ) )
        // InternalKactors.g:3203:3: ( (lv_date_5_0= ruleDate ) )
        {
        // InternalKactors.g:3203:3: ( (lv_date_5_0= ruleDate ) )
        // InternalKactors.g:3204:4: (lv_date_5_0= ruleDate )
        {
        // InternalKactors.g:3204:4: (lv_date_5_0= ruleDate )
        // InternalKactors.g:3205:5: lv_date_5_0= ruleDate
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
    // $ANTLR end synpred108_InternalKactors

    // $ANTLR start synpred110_InternalKactors
    public final void synpred110_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_list_2_0 = null;


        // InternalKactors.g:3300:5: ( (lv_list_2_0= ruleStatement ) )
        // InternalKactors.g:3300:5: (lv_list_2_0= ruleStatement )
        {
        // InternalKactors.g:3300:5: (lv_list_2_0= ruleStatement )
        // InternalKactors.g:3301:6: lv_list_2_0= ruleStatement
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_list_2_0=ruleStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred110_InternalKactors

    // $ANTLR start synpred111_InternalKactors
    public final void synpred111_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_list_1_0 = null;

        EObject lv_list_2_0 = null;


        // InternalKactors.g:3269:3: ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) )
        // InternalKactors.g:3269:3: ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* )
        {
        // InternalKactors.g:3269:3: ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* )
        // InternalKactors.g:3270:4: () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )*
        {
        // InternalKactors.g:3270:4: ()
        // InternalKactors.g:3271:5: 
        {
        if ( state.backtracking==0 ) {

          					/* */
          				
        }

        }

        // InternalKactors.g:3280:4: ( (lv_list_1_0= ruleStatement ) )
        // InternalKactors.g:3281:5: (lv_list_1_0= ruleStatement )
        {
        // InternalKactors.g:3281:5: (lv_list_1_0= ruleStatement )
        // InternalKactors.g:3282:6: lv_list_1_0= ruleStatement
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_1_0());
          					
        }
        pushFollow(FOLLOW_49);
        lv_list_1_0=ruleStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:3299:4: ( (lv_list_2_0= ruleStatement ) )*
        loop108:
        do {
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==RULE_LOWERCASE_ID||LA108_0==RULE_EMBEDDEDTEXT||LA108_0==33||LA108_0==62) ) {
                alt108=1;
            }


            switch (alt108) {
        	case 1 :
        	    // InternalKactors.g:3300:5: (lv_list_2_0= ruleStatement )
        	    {
        	    // InternalKactors.g:3300:5: (lv_list_2_0= ruleStatement )
        	    // InternalKactors.g:3301:6: lv_list_2_0= ruleStatement
        	    {
        	    if ( state.backtracking==0 ) {

        	      						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_2_0());
        	      					
        	    }
        	    pushFollow(FOLLOW_49);
        	    lv_list_2_0=ruleStatement();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }
        	    break;

        	default :
        	    break loop108;
            }
        } while (true);


        }


        }
    }
    // $ANTLR end synpred111_InternalKactors

    // $ANTLR start synpred118_InternalKactors
    public final void synpred118_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_elseIfExpression_5_0=null;
        EObject lv_elseIfCall_6_0 = null;


        // InternalKactors.g:3567:4: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )
        // InternalKactors.g:3567:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) )
        {
        otherlv_3=(Token)match(input,63,FOLLOW_53); if (state.failed) return ;
        otherlv_4=(Token)match(input,62,FOLLOW_51); if (state.failed) return ;
        // InternalKactors.g:3575:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
        // InternalKactors.g:3576:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        {
        // InternalKactors.g:3576:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        // InternalKactors.g:3577:6: lv_elseIfExpression_5_0= RULE_EXPR
        {
        lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_13); if (state.failed) return ;

        }


        }

        // InternalKactors.g:3593:4: ( (lv_elseIfCall_6_0= ruleIfBody ) )
        // InternalKactors.g:3594:5: (lv_elseIfCall_6_0= ruleIfBody )
        {
        // InternalKactors.g:3594:5: (lv_elseIfCall_6_0= ruleIfBody )
        // InternalKactors.g:3595:6: lv_elseIfCall_6_0= ruleIfBody
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
    // $ANTLR end synpred118_InternalKactors

    // $ANTLR start synpred119_InternalKactors
    public final void synpred119_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        EObject lv_elseCall_8_0 = null;


        // InternalKactors.g:3614:4: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )
        // InternalKactors.g:3614:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) )
        {
        otherlv_7=(Token)match(input,63,FOLLOW_13); if (state.failed) return ;
        // InternalKactors.g:3618:4: ( (lv_elseCall_8_0= ruleIfBody ) )
        // InternalKactors.g:3619:5: (lv_elseCall_8_0= ruleIfBody )
        {
        // InternalKactors.g:3619:5: (lv_elseCall_8_0= ruleIfBody )
        // InternalKactors.g:3620:6: lv_elseCall_8_0= ruleIfBody
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
    // $ANTLR end synpred119_InternalKactors

    // $ANTLR start synpred120_InternalKactors
    public final void synpred120_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_call_0_0 = null;


        // InternalKactors.g:3657:3: ( ( (lv_call_0_0= ruleCall ) ) )
        // InternalKactors.g:3657:3: ( (lv_call_0_0= ruleCall ) )
        {
        // InternalKactors.g:3657:3: ( (lv_call_0_0= ruleCall ) )
        // InternalKactors.g:3658:4: (lv_call_0_0= ruleCall )
        {
        // InternalKactors.g:3658:4: (lv_call_0_0= ruleCall )
        // InternalKactors.g:3659:5: lv_call_0_0= ruleCall
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getIfBodyAccess().getCallCallParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_call_0_0=ruleCall();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred120_InternalKactors

    // $ANTLR start synpred122_InternalKactors
    public final void synpred122_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;


        // InternalKactors.g:3734:4: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )
        // InternalKactors.g:3734:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
        {
        otherlv_1=(Token)match(input,33,FOLLOW_20); if (state.failed) return ;
        // InternalKactors.g:3738:4: ( (lv_parameters_2_0= ruleParameterList ) )?
        int alt110=2;
        int LA110_0 = input.LA(1);

        if ( (LA110_0==RULE_LOWERCASE_ID||(LA110_0>=RULE_STRING && LA110_0<=RULE_EXPR)||LA110_0==RULE_INT||LA110_0==33||LA110_0==38||LA110_0==41||(LA110_0>=43 && LA110_0<=44)||LA110_0==51||(LA110_0>=66 && LA110_0<=67)) ) {
            alt110=1;
        }
        switch (alt110) {
            case 1 :
                // InternalKactors.g:3739:5: (lv_parameters_2_0= ruleParameterList )
                {
                // InternalKactors.g:3739:5: (lv_parameters_2_0= ruleParameterList )
                // InternalKactors.g:3740:6: lv_parameters_2_0= ruleParameterList
                {
                if ( state.backtracking==0 ) {

                  						newCompositeNode(grammarAccess.getCallAccess().getParametersParameterListParserRuleCall_1_1_0());
                  					
                }
                pushFollow(FOLLOW_21);
                lv_parameters_2_0=ruleParameterList();

                state._fsp--;
                if (state.failed) return ;

                }


                }
                break;

        }

        otherlv_3=(Token)match(input,35,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred122_InternalKactors

    // $ANTLR start synpred125_InternalKactors
    public final void synpred125_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_call_0_0 = null;


        // InternalKactors.g:3813:3: ( ( (lv_call_0_0= ruleCall ) ) )
        // InternalKactors.g:3813:3: ( (lv_call_0_0= ruleCall ) )
        {
        // InternalKactors.g:3813:3: ( (lv_call_0_0= ruleCall ) )
        // InternalKactors.g:3814:4: (lv_call_0_0= ruleCall )
        {
        // InternalKactors.g:3814:4: (lv_call_0_0= ruleCall )
        // InternalKactors.g:3815:5: lv_call_0_0= ruleCall
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getActionsAccess().getCallCallParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_call_0_0=ruleCall();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred125_InternalKactors

    // $ANTLR start synpred126_InternalKactors
    public final void synpred126_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_body_1_0 = null;


        // InternalKactors.g:3833:3: ( ( (lv_body_1_0= ruleBody ) ) )
        // InternalKactors.g:3833:3: ( (lv_body_1_0= ruleBody ) )
        {
        // InternalKactors.g:3833:3: ( (lv_body_1_0= ruleBody ) )
        // InternalKactors.g:3834:4: (lv_body_1_0= ruleBody )
        {
        // InternalKactors.g:3834:4: (lv_body_1_0= ruleBody )
        // InternalKactors.g:3835:5: lv_body_1_0= ruleBody
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getActionsAccess().getBodyBodyParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_body_1_0=ruleBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred126_InternalKactors

    // $ANTLR start synpred132_InternalKactors
    public final void synpred132_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_10=null;
        EObject lv_literal_9_0 = null;

        EObject lv_body_11_0 = null;


        // InternalKactors.g:4072:3: ( ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) )
        // InternalKactors.g:4072:3: ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) )
        {
        // InternalKactors.g:4072:3: ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) )
        // InternalKactors.g:4073:4: ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) )
        {
        // InternalKactors.g:4073:4: ( (lv_literal_9_0= ruleLiteral ) )
        // InternalKactors.g:4074:5: (lv_literal_9_0= ruleLiteral )
        {
        // InternalKactors.g:4074:5: (lv_literal_9_0= ruleLiteral )
        // InternalKactors.g:4075:6: lv_literal_9_0= ruleLiteral
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_3_0_0());
          					
        }
        pushFollow(FOLLOW_59);
        lv_literal_9_0=ruleLiteral();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_10=(Token)match(input,65,FOLLOW_13); if (state.failed) return ;
        // InternalKactors.g:4096:4: ( (lv_body_11_0= ruleBody ) )
        // InternalKactors.g:4097:5: (lv_body_11_0= ruleBody )
        {
        // InternalKactors.g:4097:5: (lv_body_11_0= ruleBody )
        // InternalKactors.g:4098:6: lv_body_11_0= ruleBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_3_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_11_0=ruleBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred132_InternalKactors

    // $ANTLR start synpred133_InternalKactors
    public final void synpred133_InternalKactors_fragment() throws RecognitionException {   
        Token lv_text_12_0=null;
        Token otherlv_13=null;
        EObject lv_body_14_0 = null;


        // InternalKactors.g:4117:3: ( ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) )
        // InternalKactors.g:4117:3: ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) )
        {
        // InternalKactors.g:4117:3: ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) )
        // InternalKactors.g:4118:4: ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) )
        {
        // InternalKactors.g:4118:4: ( (lv_text_12_0= RULE_STRING ) )
        // InternalKactors.g:4119:5: (lv_text_12_0= RULE_STRING )
        {
        // InternalKactors.g:4119:5: (lv_text_12_0= RULE_STRING )
        // InternalKactors.g:4120:6: lv_text_12_0= RULE_STRING
        {
        lv_text_12_0=(Token)match(input,RULE_STRING,FOLLOW_59); if (state.failed) return ;

        }


        }

        otherlv_13=(Token)match(input,65,FOLLOW_13); if (state.failed) return ;
        // InternalKactors.g:4140:4: ( (lv_body_14_0= ruleBody ) )
        // InternalKactors.g:4141:5: (lv_body_14_0= ruleBody )
        {
        // InternalKactors.g:4141:5: (lv_body_14_0= ruleBody )
        // InternalKactors.g:4142:6: lv_body_14_0= ruleBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_4_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_14_0=ruleBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred133_InternalKactors

    // $ANTLR start synpred136_InternalKactors
    public final void synpred136_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:4246:4: ( ( RULE_INT ) )
        // InternalKactors.g:4246:5: ( RULE_INT )
        {
        // InternalKactors.g:4246:5: ( RULE_INT )
        // InternalKactors.g:4247:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred136_InternalKactors

    // $ANTLR start synpred137_InternalKactors
    public final void synpred137_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:4268:4: ( ( 'l' ) )
        // InternalKactors.g:4268:5: ( 'l' )
        {
        // InternalKactors.g:4268:5: ( 'l' )
        // InternalKactors.g:4269:5: 'l'
        {
        match(input,68,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred137_InternalKactors

    // $ANTLR start synpred138_InternalKactors
    public final void synpred138_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:4286:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKactors.g:4286:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:4286:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKactors.g:4287:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKactors.g:4287:5: ( ( '.' ) )
        // InternalKactors.g:4288:6: ( '.' )
        {
        // InternalKactors.g:4288:6: ( '.' )
        // InternalKactors.g:4289:7: '.'
        {
        match(input,55,FOLLOW_8); if (state.failed) return ;

        }


        }

        // InternalKactors.g:4292:5: ( ( RULE_INT ) )
        // InternalKactors.g:4293:6: ( RULE_INT )
        {
        // InternalKactors.g:4293:6: ( RULE_INT )
        // InternalKactors.g:4294:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred138_InternalKactors

    // $ANTLR start synpred142_InternalKactors
    public final void synpred142_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:4335:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKactors.g:4335:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:4335:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKactors.g:4336:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKactors.g:4336:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKactors.g:4337:6: ( ( 'e' | 'E' ) )
        {
        // InternalKactors.g:4337:6: ( ( 'e' | 'E' ) )
        // InternalKactors.g:4338:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=69 && input.LA(1)<=70) ) {
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

        // InternalKactors.g:4345:5: ( '+' | ( ( '-' ) ) )?
        int alt111=3;
        int LA111_0 = input.LA(1);

        if ( (LA111_0==66) ) {
            alt111=1;
        }
        else if ( (LA111_0==67) ) {
            alt111=2;
        }
        switch (alt111) {
            case 1 :
                // InternalKactors.g:4346:6: '+'
                {
                match(input,66,FOLLOW_8); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:4348:6: ( ( '-' ) )
                {
                // InternalKactors.g:4348:6: ( ( '-' ) )
                // InternalKactors.g:4349:7: ( '-' )
                {
                // InternalKactors.g:4349:7: ( '-' )
                // InternalKactors.g:4350:8: '-'
                {
                match(input,67,FOLLOW_8); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKactors.g:4354:5: ( ( RULE_INT ) )
        // InternalKactors.g:4355:6: ( RULE_INT )
        {
        // InternalKactors.g:4355:6: ( RULE_INT )
        // InternalKactors.g:4356:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred142_InternalKactors

    // $ANTLR start synpred159_InternalKactors
    public final void synpred159_InternalKactors_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKactors.g:4790:4: (kw= '-' )
        // InternalKactors.g:4790:4: kw= '-'
        {
        kw=(Token)match(input,67,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred159_InternalKactors

    // $ANTLR start synpred160_InternalKactors
    public final void synpred160_InternalKactors_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKactors.g:4797:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKactors.g:4797:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred160_InternalKactors

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
    public final boolean synpred75_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred75_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred159_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred159_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred110_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred110_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred138_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred138_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred111_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred111_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred160_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred160_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred26_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred26_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred4_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred108_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred108_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred105_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred105_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred7_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred122_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred122_InternalKactors_fragment(); // can never throw exception
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


    protected DFA6 dfa6 = new DFA6(this);
    protected DFA14 dfa14 = new DFA14(this);
    protected DFA15 dfa15 = new DFA15(this);
    protected DFA21 dfa21 = new DFA21(this);
    protected DFA22 dfa22 = new DFA22(this);
    protected DFA32 dfa32 = new DFA32(this);
    protected DFA34 dfa34 = new DFA34(this);
    protected DFA43 dfa43 = new DFA43(this);
    protected DFA53 dfa53 = new DFA53(this);
    protected DFA64 dfa64 = new DFA64(this);
    protected DFA67 dfa67 = new DFA67(this);
    protected DFA68 dfa68 = new DFA68(this);
    static final String dfa_1s = "\13\uffff";
    static final String dfa_2s = "\1\1\12\uffff";
    static final String dfa_3s = "\1\12\12\uffff";
    static final String dfa_4s = "\1\37\12\uffff";
    static final String dfa_5s = "\1\uffff\1\12\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11";
    static final String dfa_6s = "\1\0\12\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\13\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\1",
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

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()+ loopback of 169:5: ( ({...}? => ( ({...}? => (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'label' ( ( (lv_label_6_1= RULE_LOWERCASE_ID | lv_label_6_2= RULE_ID | lv_label_6_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'description' ( (lv_description_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'permissions' ( (lv_permissions_10_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'author' ( (lv_authors_12_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_13= 'version' ( (lv_version_14_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_15= 'created' ( (lv_created_16_0= ruleDate ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_17= 'modified' ( (lv_modified_18_0= ruleDate ) ) ( (lv_modcomment_19_0= RULE_STRING ) )? ) ) ) ) )+";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA6_0 = input.LA(1);

                         
                        int index6_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA6_0==EOF||LA6_0==RULE_ANNOTATION_ID||LA6_0==31) ) {s = 1;}

                        else if ( LA6_0 == 22 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 0) ) {s = 2;}

                        else if ( LA6_0 == 23 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 1) ) {s = 3;}

                        else if ( LA6_0 == 24 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 2) ) {s = 4;}

                        else if ( LA6_0 == 25 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 3) ) {s = 5;}

                        else if ( LA6_0 == 26 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 4) ) {s = 6;}

                        else if ( LA6_0 == 27 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 5) ) {s = 7;}

                        else if ( LA6_0 == 28 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 6) ) {s = 8;}

                        else if ( LA6_0 == 29 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 7) ) {s = 9;}

                        else if ( LA6_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup(), 8) ) {s = 10;}

                         
                        input.seek(index6_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 6, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\14\uffff";
    static final String dfa_9s = "\1\4\2\uffff\2\0\7\uffff";
    static final String dfa_10s = "\1\103\2\uffff\2\0\7\uffff";
    static final String dfa_11s = "\1\uffff\1\1\1\2\2\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\3";
    static final String dfa_12s = "\3\uffff\1\1\1\0\7\uffff}>";
    static final String[] dfa_13s = {
            "\1\4\1\uffff\1\3\1\1\1\10\1\11\1\uffff\1\2\25\uffff\1\6\4\uffff\1\5\2\uffff\1\7\1\uffff\2\2\6\uffff\1\12\16\uffff\2\2",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[][] dfa_13 = unpackEncodedStringArray(dfa_13s);

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = dfa_8;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "878:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_urn_3_0= ruleUrn ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA14_4 = input.LA(1);

                         
                        int index14_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_InternalKactors()) ) {s = 11;}

                        else if ( (synpred27_InternalKactors()) ) {s = 5;}

                         
                        input.seek(index14_4);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA14_3 = input.LA(1);

                         
                        int index14_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred25_InternalKactors()) ) {s = 2;}

                        else if ( (synpred27_InternalKactors()) ) {s = 5;}

                         
                        input.seek(index14_3);
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
    static final String dfa_14s = "\7\uffff";
    static final String dfa_15s = "\1\uffff\1\5\4\uffff\1\5";
    static final String dfa_16s = "\2\4\2\uffff\1\4\1\uffff\1\4";
    static final String dfa_17s = "\1\46\1\103\2\uffff\1\4\1\uffff\1\103";
    static final String dfa_18s = "\2\uffff\1\2\1\3\1\uffff\1\1\1\uffff";
    static final String dfa_19s = "\7\uffff}>";
    static final String[] dfa_20s = {
            "\1\1\1\uffff\1\3\37\uffff\1\2",
            "\1\5\1\uffff\4\5\1\uffff\1\5\24\uffff\1\2\3\5\2\uffff\1\5\2\uffff\4\5\6\uffff\1\5\3\uffff\1\4\12\uffff\2\5",
            "",
            "",
            "\1\6",
            "",
            "\1\5\1\uffff\4\5\1\uffff\1\5\24\uffff\1\2\3\5\2\uffff\1\5\2\uffff\4\5\6\uffff\1\5\3\uffff\1\4\12\uffff\2\5"
    };

    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = dfa_14;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "1074:4: (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING )";
        }
    }
    static final String dfa_21s = "\6\uffff";
    static final String dfa_22s = "\1\uffff\1\2\3\uffff\1\2";
    static final String dfa_23s = "\2\4\1\uffff\1\4\1\uffff\1\4";
    static final String dfa_24s = "\1\16\1\103\1\uffff\1\16\1\uffff\1\103";
    static final String dfa_25s = "\2\uffff\1\1\1\uffff\1\2\1\uffff";
    static final String dfa_26s = "\6\uffff}>";
    static final String[] dfa_27s = {
            "\1\1\11\uffff\1\2",
            "\1\2\1\uffff\4\2\1\uffff\1\2\25\uffff\3\2\1\uffff\1\4\1\2\1\uffff\5\2\6\uffff\1\2\2\uffff\1\2\1\3\12\uffff\2\2",
            "",
            "\1\5\11\uffff\1\2",
            "",
            "\1\2\1\uffff\4\2\1\uffff\1\2\25\uffff\3\2\1\uffff\1\4\1\2\1\uffff\5\2\6\uffff\1\2\2\uffff\1\2\1\3\12\uffff\2\2"
    };

    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[][] dfa_27 = unpackEncodedStringArray(dfa_27s);

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = dfa_21;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "1303:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )";
        }
    }

    class DFA22 extends DFA {

        public DFA22(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 22;
            this.eot = dfa_21;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "1332:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )";
        }
    }
    static final String dfa_28s = "\26\uffff";
    static final String dfa_29s = "\4\uffff\1\21\7\uffff\1\21\5\uffff\1\21\2\uffff\1\21";
    static final String dfa_30s = "\1\4\1\uffff\2\13\1\40\7\uffff\1\40\3\13\2\uffff\1\40\2\13\1\40";
    static final String dfa_31s = "\1\103\1\uffff\2\13\1\106\7\uffff\1\106\1\13\2\103\2\uffff\1\106\2\13\1\57";
    static final String dfa_32s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\4\uffff\1\2\1\3\4\uffff";
    static final String dfa_33s = "\26\uffff}>";
    static final String[] dfa_34s = {
            "\1\10\1\uffff\1\6\1\uffff\1\7\2\uffff\1\4\31\uffff\1\11\5\uffff\2\1\3\uffff\1\5\1\12\1\13\6\uffff\5\11\4\uffff\1\2\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\21\14\uffff\3\20\7\uffff\1\15\14\uffff\1\14\1\16\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21\14\uffff\3\20\7\uffff\1\15\15\uffff\1\16\1\17",
            "\1\22",
            "\1\25\66\uffff\1\23\1\24",
            "\1\25\66\uffff\1\23\1\24",
            "",
            "",
            "\1\21\14\uffff\3\20\25\uffff\1\16\1\17",
            "\1\25",
            "\1\25",
            "\1\21\14\uffff\3\20"
    };

    static final short[] dfa_28 = DFA.unpackEncodedString(dfa_28s);
    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final char[] dfa_30 = DFA.unpackEncodedStringToUnsignedChars(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final short[][] dfa_34 = unpackEncodedStringArray(dfa_34s);

    class DFA32 extends DFA {

        public DFA32(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 32;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_34;
        }
        public String getDescription() {
            return "1633:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )";
        }
    }
    static final String dfa_35s = "\2\uffff\1\3\2\uffff\1\3";
    static final String dfa_36s = "\1\4\1\uffff\1\14\1\uffff\1\4\1\14";
    static final String dfa_37s = "\1\103\1\uffff\1\65\1\uffff\1\103\1\65";
    static final String dfa_38s = "\1\uffff\1\1\1\uffff\1\2\2\uffff";
    static final String[] dfa_39s = {
            "\1\1\1\uffff\1\2\1\uffff\2\3\1\uffff\1\3\31\uffff\1\3\1\uffff\1\3\3\uffff\2\3\3\uffff\3\3\6\uffff\5\3\4\uffff\2\3",
            "",
            "\1\1\25\uffff\1\3\21\uffff\1\3\1\4",
            "",
            "\1\1\1\uffff\1\5\1\uffff\2\3\1\uffff\1\3\31\uffff\1\3\1\uffff\1\3\3\uffff\2\3\3\uffff\3\3\6\uffff\5\3\4\uffff\2\3",
            "\1\1\25\uffff\1\3\21\uffff\1\3\1\4"
    };
    static final short[] dfa_35 = DFA.unpackEncodedString(dfa_35s);
    static final char[] dfa_36 = DFA.unpackEncodedStringToUnsignedChars(dfa_36s);
    static final char[] dfa_37 = DFA.unpackEncodedStringToUnsignedChars(dfa_37s);
    static final short[] dfa_38 = DFA.unpackEncodedString(dfa_38s);
    static final short[][] dfa_39 = unpackEncodedStringArray(dfa_39s);

    class DFA34 extends DFA {

        public DFA34(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 34;
            this.eot = dfa_21;
            this.eof = dfa_35;
            this.min = dfa_36;
            this.max = dfa_37;
            this.accept = dfa_38;
            this.special = dfa_26;
            this.transition = dfa_39;
        }
        public String getDescription() {
            return "2008:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?";
        }
    }
    static final String dfa_40s = "\32\uffff";
    static final String dfa_41s = "\4\uffff\1\22\10\uffff\2\22\1\25\6\uffff\1\22\2\uffff\1\22";
    static final String dfa_42s = "\1\6\1\uffff\2\13\1\42\10\uffff\2\42\1\4\2\13\4\uffff\1\42\2\13\1\42";
    static final String dfa_43s = "\1\103\1\uffff\2\13\1\111\10\uffff\2\106\1\112\2\103\4\uffff\1\106\2\13\1\67";
    static final String dfa_44s = "\1\uffff\1\1\3\uffff\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\5\uffff\1\3\1\5\1\2\1\4\4\uffff";
    static final String dfa_45s = "\32\uffff}>";
    static final String[] dfa_46s = {
            "\1\6\1\uffff\1\7\1\11\1\uffff\1\4\31\uffff\1\10\1\uffff\1\14\3\uffff\2\1\3\uffff\1\5\1\12\1\13\6\uffff\5\10\4\uffff\1\2\1\3",
            "",
            "\1\15",
            "\1\15",
            "\1\22\12\uffff\3\24\4\uffff\2\22\1\25\1\17\13\uffff\1\23\1\16\1\20\1\21\3\23",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\22\12\uffff\3\24\4\uffff\2\22\1\25\1\17\14\uffff\1\16\1\20\1\21",
            "\1\22\12\uffff\3\24\4\uffff\2\22\1\25\1\17\15\uffff\1\20\1\21",
            "\1\25\6\uffff\1\26\1\uffff\2\25\22\uffff\2\25\17\uffff\1\25\1\uffff\3\25\23\uffff\1\25",
            "\1\31\66\uffff\1\27\1\30",
            "\1\31\66\uffff\1\27\1\30",
            "",
            "",
            "",
            "",
            "\1\22\12\uffff\3\24\4\uffff\2\22\2\25\15\uffff\1\20\1\21",
            "\1\31",
            "\1\31",
            "\1\22\12\uffff\3\24\4\uffff\2\22\2\25"
    };

    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final char[] dfa_42 = DFA.unpackEncodedStringToUnsignedChars(dfa_42s);
    static final char[] dfa_43 = DFA.unpackEncodedStringToUnsignedChars(dfa_43s);
    static final short[] dfa_44 = DFA.unpackEncodedString(dfa_44s);
    static final short[] dfa_45 = DFA.unpackEncodedString(dfa_45s);
    static final short[][] dfa_46 = unpackEncodedStringArray(dfa_46s);

    class DFA43 extends DFA {

        public DFA43(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 43;
            this.eot = dfa_40;
            this.eof = dfa_41;
            this.min = dfa_42;
            this.max = dfa_43;
            this.accept = dfa_44;
            this.special = dfa_45;
            this.transition = dfa_46;
        }
        public String getDescription() {
            return "2253:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | ( (lv_quantity_10_0= ruleQuantity ) ) | ( (lv_date_11_0= ruleDate ) ) | (otherlv_12= 'in' ( (lv_set_13_0= ruleList ) ) ) | ( (lv_string_14_0= RULE_STRING ) ) | ( (lv_observable_15_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_16_0= ruleREL_OPERATOR ) ) ( (lv_expression_17_0= ruleNumber ) ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )";
        }
    }
    static final String dfa_47s = "\3\uffff\1\16\2\uffff\2\16\7\uffff\1\16\2\uffff\1\16\3\uffff";
    static final String dfa_48s = "\1\6\2\13\1\4\2\uffff\2\4\3\13\2\uffff\1\13\1\uffff\1\4\2\13\2\4\1\13\1\0";
    static final String dfa_49s = "\1\103\2\13\1\111\2\uffff\2\106\1\13\2\103\2\uffff\1\13\1\uffff\1\106\2\13\1\103\1\106\1\13\1\0";
    static final String dfa_50s = "\4\uffff\1\3\1\5\5\uffff\1\2\1\4\1\uffff\1\1\7\uffff";
    static final String dfa_51s = "\25\uffff\1\0}>";
    static final String[] dfa_52s = {
            "\1\4\4\uffff\1\3\37\uffff\2\5\25\uffff\1\1\1\2",
            "\1\6",
            "\1\6",
            "\1\16\1\uffff\4\16\1\uffff\1\16\25\uffff\3\16\2\uffff\1\16\2\uffff\4\16\2\uffff\1\13\3\uffff\1\16\3\uffff\1\10\11\uffff\2\16\1\15\1\7\1\11\1\12\3\14",
            "",
            "",
            "\1\16\1\uffff\4\16\1\uffff\1\16\25\uffff\3\16\2\uffff\1\16\2\uffff\4\16\2\uffff\1\13\3\uffff\1\16\3\uffff\1\10\11\uffff\3\16\1\7\1\11\1\12",
            "\1\16\1\uffff\4\16\1\uffff\1\16\25\uffff\3\16\2\uffff\1\16\2\uffff\4\16\2\uffff\1\13\3\uffff\1\16\3\uffff\1\10\11\uffff\3\16\1\uffff\1\11\1\12",
            "\1\17",
            "\1\22\66\uffff\1\20\1\21",
            "\1\22\66\uffff\1\20\1\21",
            "",
            "",
            "\1\23",
            "",
            "\1\16\1\uffff\4\16\1\uffff\1\16\25\uffff\3\16\2\uffff\1\16\2\uffff\4\16\2\uffff\1\13\3\uffff\1\16\15\uffff\3\16\1\uffff\1\11\1\12",
            "\1\22",
            "\1\22",
            "\1\16\1\uffff\4\16\1\uffff\1\16\25\uffff\3\16\2\uffff\1\16\2\uffff\4\16\2\uffff\1\13\3\uffff\1\16\15\uffff\3\16",
            "\1\16\1\uffff\4\16\1\uffff\1\16\25\uffff\1\16\1\uffff\1\16\2\uffff\1\16\2\uffff\1\16\1\uffff\2\16\2\uffff\1\16\3\uffff\1\16\3\uffff\1\16\12\uffff\1\16\1\24\3\16",
            "\1\25",
            "\1\uffff"
    };
    static final short[] dfa_47 = DFA.unpackEncodedString(dfa_47s);
    static final char[] dfa_48 = DFA.unpackEncodedStringToUnsignedChars(dfa_48s);
    static final char[] dfa_49 = DFA.unpackEncodedStringToUnsignedChars(dfa_49s);
    static final short[] dfa_50 = DFA.unpackEncodedString(dfa_50s);
    static final short[] dfa_51 = DFA.unpackEncodedString(dfa_51s);
    static final short[][] dfa_52 = unpackEncodedStringArray(dfa_52s);

    class DFA53 extends DFA {

        public DFA53(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 53;
            this.eot = dfa_28;
            this.eof = dfa_47;
            this.min = dfa_48;
            this.max = dfa_49;
            this.accept = dfa_50;
            this.special = dfa_51;
            this.transition = dfa_52;
        }
        public String getDescription() {
            return "3118:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA53_21 = input.LA(1);

                         
                        int index53_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred105_InternalKactors()) ) {s = 14;}

                        else if ( (synpred108_InternalKactors()) ) {s = 12;}

                         
                        input.seek(index53_21);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 53, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_53s = "\25\uffff";
    static final String dfa_54s = "\1\2\24\uffff";
    static final String dfa_55s = "\1\4\1\0\23\uffff";
    static final String dfa_56s = "\1\103\1\0\23\uffff";
    static final String dfa_57s = "\2\uffff\1\2\21\uffff\1\1";
    static final String dfa_58s = "\1\uffff\1\0\23\uffff}>";
    static final String[] dfa_59s = {
            "\1\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\3\uffff\2\2\16\uffff\2\2\1\1\1\uffff\1\2\7\uffff\2\2\21\uffff\3\2\1\uffff\2\2",
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

    static final short[] dfa_53 = DFA.unpackEncodedString(dfa_53s);
    static final short[] dfa_54 = DFA.unpackEncodedString(dfa_54s);
    static final char[] dfa_55 = DFA.unpackEncodedStringToUnsignedChars(dfa_55s);
    static final char[] dfa_56 = DFA.unpackEncodedStringToUnsignedChars(dfa_56s);
    static final short[] dfa_57 = DFA.unpackEncodedString(dfa_57s);
    static final short[] dfa_58 = DFA.unpackEncodedString(dfa_58s);
    static final short[][] dfa_59 = unpackEncodedStringArray(dfa_59s);

    class DFA64 extends DFA {

        public DFA64(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 64;
            this.eot = dfa_53;
            this.eof = dfa_54;
            this.min = dfa_55;
            this.max = dfa_56;
            this.accept = dfa_57;
            this.special = dfa_58;
            this.transition = dfa_59;
        }
        public String getDescription() {
            return "3733:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA64_1 = input.LA(1);

                         
                        int index64_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_InternalKactors()) ) {s = 20;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index64_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 64, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_60s = "\15\uffff";
    static final String dfa_61s = "\11\uffff\2\2\2\uffff";
    static final String dfa_62s = "\1\4\1\101\1\uffff\1\4\2\uffff\1\4\1\uffff\5\4";
    static final String dfa_63s = "\1\103\1\101\1\uffff\1\103\2\uffff\1\101\1\uffff\1\76\2\103\1\100\1\101";
    static final String dfa_64s = "\2\uffff\1\2\1\uffff\1\3\1\1\1\uffff\1\4\5\uffff";
    static final String dfa_65s = "\1\uffff\1\0\13\uffff}>";
    static final String[] dfa_66s = {
            "\1\1\1\uffff\1\4\1\uffff\1\4\2\uffff\1\4\3\uffff\1\2\1\4\20\uffff\1\3\11\uffff\2\4\21\uffff\1\2\3\uffff\2\4",
            "\1\4",
            "",
            "\1\6\1\uffff\1\7\1\uffff\1\7\2\uffff\1\7\3\uffff\1\2\1\7\20\uffff\1\10\1\uffff\1\11\7\uffff\2\7\21\uffff\1\2\3\uffff\2\7",
            "",
            "",
            "\1\2\12\uffff\1\2\20\uffff\2\2\1\4\1\12\32\uffff\1\2\1\uffff\1\2\1\7",
            "",
            "\1\13\12\uffff\1\2\21\uffff\1\2\1\uffff\1\7\32\uffff\1\2",
            "\1\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\3\uffff\2\2\16\uffff\1\2\1\uffff\1\2\1\uffff\1\2\7\uffff\2\2\21\uffff\2\2\1\uffff\1\4\2\2",
            "\1\2\1\uffff\1\2\1\uffff\1\2\1\uffff\2\2\3\uffff\2\2\16\uffff\1\2\1\uffff\1\2\1\uffff\1\2\7\uffff\2\2\21\uffff\2\2\1\uffff\1\4\2\2",
            "\1\2\12\uffff\1\2\20\uffff\2\2\1\7\1\14\32\uffff\1\2\1\uffff\1\2",
            "\1\2\12\uffff\1\2\21\uffff\1\2\1\uffff\1\2\32\uffff\1\2\2\uffff\1\7"
    };

    static final short[] dfa_60 = DFA.unpackEncodedString(dfa_60s);
    static final short[] dfa_61 = DFA.unpackEncodedString(dfa_61s);
    static final char[] dfa_62 = DFA.unpackEncodedStringToUnsignedChars(dfa_62s);
    static final char[] dfa_63 = DFA.unpackEncodedStringToUnsignedChars(dfa_63s);
    static final short[] dfa_64 = DFA.unpackEncodedString(dfa_64s);
    static final short[] dfa_65 = DFA.unpackEncodedString(dfa_65s);
    static final short[][] dfa_66 = unpackEncodedStringArray(dfa_66s);

    class DFA67 extends DFA {

        public DFA67(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 67;
            this.eot = dfa_60;
            this.eof = dfa_61;
            this.min = dfa_62;
            this.max = dfa_63;
            this.accept = dfa_64;
            this.special = dfa_65;
            this.transition = dfa_66;
        }
        public String getDescription() {
            return "3812:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) | ( (lv_match_2_0= ruleMatch ) ) | (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA67_1 = input.LA(1);

                         
                        int index67_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA67_1==65) ) {s = 4;}

                        else if ( (synpred125_InternalKactors()) ) {s = 5;}

                        else if ( (synpred126_InternalKactors()) ) {s = 2;}

                         
                        input.seek(index67_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 67, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_67s = "\1\4\6\uffff\1\0\4\uffff";
    static final String dfa_68s = "\1\103\6\uffff\1\0\4\uffff";
    static final String dfa_69s = "\1\uffff\1\1\1\2\1\3\1\4\5\uffff\1\6\1\5";
    static final String dfa_70s = "\7\uffff\1\0\4\uffff}>";
    static final String[] dfa_71s = {
            "\1\1\1\uffff\1\7\1\uffff\1\3\2\uffff\1\4\4\uffff\1\2\20\uffff\1\12\11\uffff\2\4\25\uffff\2\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            ""
    };
    static final char[] dfa_67 = DFA.unpackEncodedStringToUnsignedChars(dfa_67s);
    static final char[] dfa_68 = DFA.unpackEncodedStringToUnsignedChars(dfa_68s);
    static final short[] dfa_69 = DFA.unpackEncodedString(dfa_69s);
    static final short[] dfa_70 = DFA.unpackEncodedString(dfa_70s);
    static final short[][] dfa_71 = unpackEncodedStringArray(dfa_71s);

    class DFA68 extends DFA {

        public DFA68(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 68;
            this.eot = dfa_8;
            this.eof = dfa_8;
            this.min = dfa_67;
            this.max = dfa_68;
            this.accept = dfa_69;
            this.special = dfa_70;
            this.transition = dfa_71;
        }
        public String getDescription() {
            return "3939:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA68_7 = input.LA(1);

                         
                        int index68_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred132_InternalKactors()) ) {s = 4;}

                        else if ( (synpred133_InternalKactors()) ) {s = 11;}

                         
                        input.seek(index68_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 68, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000080000402L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x000000007FC00002L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000070L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x000000007FC00042L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000080000400L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000300000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x4000000200008010L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000800000010L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000C00000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x00081A4200000BD0L,0x000000000000000CL});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000003000000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x00081A4A00000BD0L,0x000000000000000CL});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000004810L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000008100000002L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000004010L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x3E071C2000000950L,0x000000000000000CL});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000040400000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x3E07182000000950L,0x000000000000000CL});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000E00000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000000800L,0x000000000000000CL});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000600000000002L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x3E1718A000000B50L,0x000000000000000CL});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x3E0718A000000B50L,0x000000000000000CL});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0020000000000002L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x00C0000000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0044000200006010L,0x0000000000000400L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0044000A00006010L,0x0000000000000400L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0044000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000200006010L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0040000000000002L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x4000000200008012L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x4000000A00008010L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000300000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000100000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x4000180200018950L,0x000000000000000CL});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000180200010950L,0x000000000000000CL});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000180A00010950L,0x000000000000000CL});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0080000000000002L,0x0000000000000070L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0080000000000002L,0x0000000000000060L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000060L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000388L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0080000000000002L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x00C0000000000002L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0080000000004012L,0x0000000000000008L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000004012L,0x0000000000000008L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000004012L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000000042L});

}

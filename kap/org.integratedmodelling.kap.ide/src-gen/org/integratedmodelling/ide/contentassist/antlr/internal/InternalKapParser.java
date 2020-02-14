package org.integratedmodelling.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.integratedmodelling.services.KapGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalKapParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_LOWERCASE_ID", "RULE_OBSERVABLE", "RULE_EXPR", "RULE_STRING", "RULE_EMBEDDEDTEXT", "RULE_REGEXP", "RULE_INT", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'='", "'true'", "'false'", "'+'", "'e'", "'E'", "'AD'", "'CE'", "'name'", "'def'", "':'", "'('", "')'", "','", "'to'", "';'", "'->'", "'-'", "'.'", "'=?'", "'l'", "'BC'"
    };
    public static final int RULE_STRING=7;
    public static final int RULE_SL_COMMENT=13;
    public static final int T__19=19;
    public static final int RULE_EMBEDDEDTEXT=8;
    public static final int RULE_OBSERVABLE=5;
    public static final int T__37=37;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int RULE_REGEXP=9;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_ID=11;
    public static final int RULE_WS=14;
    public static final int RULE_ANY_OTHER=15;
    public static final int T__26=26;
    public static final int RULE_LOWERCASE_ID=4;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=10;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=12;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int RULE_EXPR=6;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalKapParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalKapParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalKapParser.tokenNames; }
    public String getGrammarFileName() { return "InternalKap.g"; }


    	private KapGrammarAccess grammarAccess;

    	public void setGrammarAccess(KapGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleModel"
    // InternalKap.g:54:1: entryRuleModel : ruleModel EOF ;
    public final void entryRuleModel() throws RecognitionException {
        try {
            // InternalKap.g:55:1: ( ruleModel EOF )
            // InternalKap.g:56:1: ruleModel EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleModel();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalKap.g:63:1: ruleModel : ( ( rule__Model__Group__0 ) ) ;
    public final void ruleModel() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:67:2: ( ( ( rule__Model__Group__0 ) ) )
            // InternalKap.g:68:2: ( ( rule__Model__Group__0 ) )
            {
            // InternalKap.g:68:2: ( ( rule__Model__Group__0 ) )
            // InternalKap.g:69:3: ( rule__Model__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getGroup()); 
            }
            // InternalKap.g:70:3: ( rule__Model__Group__0 )
            // InternalKap.g:70:4: rule__Model__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Model__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRulePreamble"
    // InternalKap.g:79:1: entryRulePreamble : rulePreamble EOF ;
    public final void entryRulePreamble() throws RecognitionException {
        try {
            // InternalKap.g:80:1: ( rulePreamble EOF )
            // InternalKap.g:81:1: rulePreamble EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleRule()); 
            }
            pushFollow(FOLLOW_1);
            rulePreamble();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePreamble"


    // $ANTLR start "rulePreamble"
    // InternalKap.g:88:1: rulePreamble : ( ( rule__Preamble__Group__0 ) ) ;
    public final void rulePreamble() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:92:2: ( ( ( rule__Preamble__Group__0 ) ) )
            // InternalKap.g:93:2: ( ( rule__Preamble__Group__0 ) )
            {
            // InternalKap.g:93:2: ( ( rule__Preamble__Group__0 ) )
            // InternalKap.g:94:3: ( rule__Preamble__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getGroup()); 
            }
            // InternalKap.g:95:3: ( rule__Preamble__Group__0 )
            // InternalKap.g:95:4: rule__Preamble__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePreamble"


    // $ANTLR start "entryRuleDefinition"
    // InternalKap.g:104:1: entryRuleDefinition : ruleDefinition EOF ;
    public final void entryRuleDefinition() throws RecognitionException {
        try {
            // InternalKap.g:105:1: ( ruleDefinition EOF )
            // InternalKap.g:106:1: ruleDefinition EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleDefinition();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDefinition"


    // $ANTLR start "ruleDefinition"
    // InternalKap.g:113:1: ruleDefinition : ( ( rule__Definition__Group__0 ) ) ;
    public final void ruleDefinition() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:117:2: ( ( ( rule__Definition__Group__0 ) ) )
            // InternalKap.g:118:2: ( ( rule__Definition__Group__0 ) )
            {
            // InternalKap.g:118:2: ( ( rule__Definition__Group__0 ) )
            // InternalKap.g:119:3: ( rule__Definition__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getGroup()); 
            }
            // InternalKap.g:120:3: ( rule__Definition__Group__0 )
            // InternalKap.g:120:4: rule__Definition__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Definition__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDefinition"


    // $ANTLR start "entryRuleArgumentDeclaration"
    // InternalKap.g:129:1: entryRuleArgumentDeclaration : ruleArgumentDeclaration EOF ;
    public final void entryRuleArgumentDeclaration() throws RecognitionException {
        try {
            // InternalKap.g:130:1: ( ruleArgumentDeclaration EOF )
            // InternalKap.g:131:1: ruleArgumentDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleArgumentDeclaration();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleArgumentDeclaration"


    // $ANTLR start "ruleArgumentDeclaration"
    // InternalKap.g:138:1: ruleArgumentDeclaration : ( ( rule__ArgumentDeclaration__Group__0 ) ) ;
    public final void ruleArgumentDeclaration() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:142:2: ( ( ( rule__ArgumentDeclaration__Group__0 ) ) )
            // InternalKap.g:143:2: ( ( rule__ArgumentDeclaration__Group__0 ) )
            {
            // InternalKap.g:143:2: ( ( rule__ArgumentDeclaration__Group__0 ) )
            // InternalKap.g:144:3: ( rule__ArgumentDeclaration__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getGroup()); 
            }
            // InternalKap.g:145:3: ( rule__ArgumentDeclaration__Group__0 )
            // InternalKap.g:145:4: rule__ArgumentDeclaration__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleArgumentDeclaration"


    // $ANTLR start "entryRuleParameterList"
    // InternalKap.g:154:1: entryRuleParameterList : ruleParameterList EOF ;
    public final void entryRuleParameterList() throws RecognitionException {
        try {
            // InternalKap.g:155:1: ( ruleParameterList EOF )
            // InternalKap.g:156:1: ruleParameterList EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleParameterList();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleParameterList"


    // $ANTLR start "ruleParameterList"
    // InternalKap.g:163:1: ruleParameterList : ( ( rule__ParameterList__Group__0 ) ) ;
    public final void ruleParameterList() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:167:2: ( ( ( rule__ParameterList__Group__0 ) ) )
            // InternalKap.g:168:2: ( ( rule__ParameterList__Group__0 ) )
            {
            // InternalKap.g:168:2: ( ( rule__ParameterList__Group__0 ) )
            // InternalKap.g:169:3: ( rule__ParameterList__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getGroup()); 
            }
            // InternalKap.g:170:3: ( rule__ParameterList__Group__0 )
            // InternalKap.g:170:4: rule__ParameterList__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ParameterList__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleParameterList"


    // $ANTLR start "entryRuleKeyValuePair"
    // InternalKap.g:179:1: entryRuleKeyValuePair : ruleKeyValuePair EOF ;
    public final void entryRuleKeyValuePair() throws RecognitionException {
        try {
            // InternalKap.g:180:1: ( ruleKeyValuePair EOF )
            // InternalKap.g:181:1: ruleKeyValuePair EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleKeyValuePair();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleKeyValuePair"


    // $ANTLR start "ruleKeyValuePair"
    // InternalKap.g:188:1: ruleKeyValuePair : ( ( rule__KeyValuePair__Group__0 ) ) ;
    public final void ruleKeyValuePair() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:192:2: ( ( ( rule__KeyValuePair__Group__0 ) ) )
            // InternalKap.g:193:2: ( ( rule__KeyValuePair__Group__0 ) )
            {
            // InternalKap.g:193:2: ( ( rule__KeyValuePair__Group__0 ) )
            // InternalKap.g:194:3: ( rule__KeyValuePair__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getGroup()); 
            }
            // InternalKap.g:195:3: ( rule__KeyValuePair__Group__0 )
            // InternalKap.g:195:4: rule__KeyValuePair__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleKeyValuePair"


    // $ANTLR start "entryRuleValue"
    // InternalKap.g:204:1: entryRuleValue : ruleValue EOF ;
    public final void entryRuleValue() throws RecognitionException {
        try {
            // InternalKap.g:205:1: ( ruleValue EOF )
            // InternalKap.g:206:1: ruleValue EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleValue"


    // $ANTLR start "ruleValue"
    // InternalKap.g:213:1: ruleValue : ( ( rule__Value__Alternatives ) ) ;
    public final void ruleValue() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:217:2: ( ( ( rule__Value__Alternatives ) ) )
            // InternalKap.g:218:2: ( ( rule__Value__Alternatives ) )
            {
            // InternalKap.g:218:2: ( ( rule__Value__Alternatives ) )
            // InternalKap.g:219:3: ( rule__Value__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getAlternatives()); 
            }
            // InternalKap.g:220:3: ( rule__Value__Alternatives )
            // InternalKap.g:220:4: rule__Value__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Value__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleValue"


    // $ANTLR start "entryRuleLiteral"
    // InternalKap.g:229:1: entryRuleLiteral : ruleLiteral EOF ;
    public final void entryRuleLiteral() throws RecognitionException {
        try {
            // InternalKap.g:230:1: ( ruleLiteral EOF )
            // InternalKap.g:231:1: ruleLiteral EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleLiteral();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLiteral"


    // $ANTLR start "ruleLiteral"
    // InternalKap.g:238:1: ruleLiteral : ( ( rule__Literal__Alternatives ) ) ;
    public final void ruleLiteral() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:242:2: ( ( ( rule__Literal__Alternatives ) ) )
            // InternalKap.g:243:2: ( ( rule__Literal__Alternatives ) )
            {
            // InternalKap.g:243:2: ( ( rule__Literal__Alternatives ) )
            // InternalKap.g:244:3: ( rule__Literal__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getAlternatives()); 
            }
            // InternalKap.g:245:3: ( rule__Literal__Alternatives )
            // InternalKap.g:245:4: rule__Literal__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Literal__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLiteral"


    // $ANTLR start "entryRuleBody"
    // InternalKap.g:254:1: entryRuleBody : ruleBody EOF ;
    public final void entryRuleBody() throws RecognitionException {
        try {
            // InternalKap.g:255:1: ( ruleBody EOF )
            // InternalKap.g:256:1: ruleBody EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleBody"


    // $ANTLR start "ruleBody"
    // InternalKap.g:263:1: ruleBody : ( ( rule__Body__Alternatives ) ) ;
    public final void ruleBody() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:267:2: ( ( ( rule__Body__Alternatives ) ) )
            // InternalKap.g:268:2: ( ( rule__Body__Alternatives ) )
            {
            // InternalKap.g:268:2: ( ( rule__Body__Alternatives ) )
            // InternalKap.g:269:3: ( rule__Body__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getAlternatives()); 
            }
            // InternalKap.g:270:3: ( rule__Body__Alternatives )
            // InternalKap.g:270:4: rule__Body__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Body__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBody"


    // $ANTLR start "entryRuleStatement"
    // InternalKap.g:279:1: entryRuleStatement : ruleStatement EOF ;
    public final void entryRuleStatement() throws RecognitionException {
        try {
            // InternalKap.g:280:1: ( ruleStatement EOF )
            // InternalKap.g:281:1: ruleStatement EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStatement"


    // $ANTLR start "ruleStatement"
    // InternalKap.g:288:1: ruleStatement : ( ( rule__Statement__Alternatives ) ) ;
    public final void ruleStatement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:292:2: ( ( ( rule__Statement__Alternatives ) ) )
            // InternalKap.g:293:2: ( ( rule__Statement__Alternatives ) )
            {
            // InternalKap.g:293:2: ( ( rule__Statement__Alternatives ) )
            // InternalKap.g:294:3: ( rule__Statement__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getAlternatives()); 
            }
            // InternalKap.g:295:3: ( rule__Statement__Alternatives )
            // InternalKap.g:295:4: rule__Statement__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Statement__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStatement"


    // $ANTLR start "entryRuleCall"
    // InternalKap.g:304:1: entryRuleCall : ruleCall EOF ;
    public final void entryRuleCall() throws RecognitionException {
        try {
            // InternalKap.g:305:1: ( ruleCall EOF )
            // InternalKap.g:306:1: ruleCall EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleCall();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCall"


    // $ANTLR start "ruleCall"
    // InternalKap.g:313:1: ruleCall : ( ( rule__Call__Group__0 ) ) ;
    public final void ruleCall() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:317:2: ( ( ( rule__Call__Group__0 ) ) )
            // InternalKap.g:318:2: ( ( rule__Call__Group__0 ) )
            {
            // InternalKap.g:318:2: ( ( rule__Call__Group__0 ) )
            // InternalKap.g:319:3: ( rule__Call__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getGroup()); 
            }
            // InternalKap.g:320:3: ( rule__Call__Group__0 )
            // InternalKap.g:320:4: rule__Call__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Call__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCall"


    // $ANTLR start "entryRuleActions"
    // InternalKap.g:329:1: entryRuleActions : ruleActions EOF ;
    public final void entryRuleActions() throws RecognitionException {
        try {
            // InternalKap.g:330:1: ( ruleActions EOF )
            // InternalKap.g:331:1: ruleActions EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleActions();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleActions"


    // $ANTLR start "ruleActions"
    // InternalKap.g:338:1: ruleActions : ( ( rule__Actions__Alternatives ) ) ;
    public final void ruleActions() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:342:2: ( ( ( rule__Actions__Alternatives ) ) )
            // InternalKap.g:343:2: ( ( rule__Actions__Alternatives ) )
            {
            // InternalKap.g:343:2: ( ( rule__Actions__Alternatives ) )
            // InternalKap.g:344:3: ( rule__Actions__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getAlternatives()); 
            }
            // InternalKap.g:345:3: ( rule__Actions__Alternatives )
            // InternalKap.g:345:4: rule__Actions__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Actions__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleActions"


    // $ANTLR start "entryRuleMatch"
    // InternalKap.g:354:1: entryRuleMatch : ruleMatch EOF ;
    public final void entryRuleMatch() throws RecognitionException {
        try {
            // InternalKap.g:355:1: ( ruleMatch EOF )
            // InternalKap.g:356:1: ruleMatch EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleMatch();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMatch"


    // $ANTLR start "ruleMatch"
    // InternalKap.g:363:1: ruleMatch : ( ( rule__Match__Alternatives ) ) ;
    public final void ruleMatch() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:367:2: ( ( ( rule__Match__Alternatives ) ) )
            // InternalKap.g:368:2: ( ( rule__Match__Alternatives ) )
            {
            // InternalKap.g:368:2: ( ( rule__Match__Alternatives ) )
            // InternalKap.g:369:3: ( rule__Match__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getAlternatives()); 
            }
            // InternalKap.g:370:3: ( rule__Match__Alternatives )
            // InternalKap.g:370:4: rule__Match__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Match__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMatch"


    // $ANTLR start "entryRuleNumber"
    // InternalKap.g:379:1: entryRuleNumber : ruleNumber EOF ;
    public final void entryRuleNumber() throws RecognitionException {
        try {
            // InternalKap.g:380:1: ( ruleNumber EOF )
            // InternalKap.g:381:1: ruleNumber EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleNumber();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleNumber"


    // $ANTLR start "ruleNumber"
    // InternalKap.g:388:1: ruleNumber : ( ( rule__Number__Group__0 ) ) ;
    public final void ruleNumber() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:392:2: ( ( ( rule__Number__Group__0 ) ) )
            // InternalKap.g:393:2: ( ( rule__Number__Group__0 ) )
            {
            // InternalKap.g:393:2: ( ( rule__Number__Group__0 ) )
            // InternalKap.g:394:3: ( rule__Number__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getGroup()); 
            }
            // InternalKap.g:395:3: ( rule__Number__Group__0 )
            // InternalKap.g:395:4: rule__Number__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleNumber"


    // $ANTLR start "entryRuleDate"
    // InternalKap.g:404:1: entryRuleDate : ruleDate EOF ;
    public final void entryRuleDate() throws RecognitionException {
        try {
            // InternalKap.g:405:1: ( ruleDate EOF )
            // InternalKap.g:406:1: ruleDate EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleDate();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDate"


    // $ANTLR start "ruleDate"
    // InternalKap.g:413:1: ruleDate : ( ( rule__Date__Group__0 ) ) ;
    public final void ruleDate() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:417:2: ( ( ( rule__Date__Group__0 ) ) )
            // InternalKap.g:418:2: ( ( rule__Date__Group__0 ) )
            {
            // InternalKap.g:418:2: ( ( rule__Date__Group__0 ) )
            // InternalKap.g:419:3: ( rule__Date__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getGroup()); 
            }
            // InternalKap.g:420:3: ( rule__Date__Group__0 )
            // InternalKap.g:420:4: rule__Date__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Date__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDate"


    // $ANTLR start "rule__KeyValuePair__Alternatives_0_1"
    // InternalKap.g:428:1: rule__KeyValuePair__Alternatives_0_1 : ( ( ( rule__KeyValuePair__InteractiveAssignment_0_1_0 ) ) | ( '=' ) );
    public final void rule__KeyValuePair__Alternatives_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:432:1: ( ( ( rule__KeyValuePair__InteractiveAssignment_0_1_0 ) ) | ( '=' ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==35) ) {
                alt1=1;
            }
            else if ( (LA1_0==16) ) {
                alt1=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalKap.g:433:2: ( ( rule__KeyValuePair__InteractiveAssignment_0_1_0 ) )
                    {
                    // InternalKap.g:433:2: ( ( rule__KeyValuePair__InteractiveAssignment_0_1_0 ) )
                    // InternalKap.g:434:3: ( rule__KeyValuePair__InteractiveAssignment_0_1_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getKeyValuePairAccess().getInteractiveAssignment_0_1_0()); 
                    }
                    // InternalKap.g:435:3: ( rule__KeyValuePair__InteractiveAssignment_0_1_0 )
                    // InternalKap.g:435:4: rule__KeyValuePair__InteractiveAssignment_0_1_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__KeyValuePair__InteractiveAssignment_0_1_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getKeyValuePairAccess().getInteractiveAssignment_0_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:439:2: ( '=' )
                    {
                    // InternalKap.g:439:2: ( '=' )
                    // InternalKap.g:440:3: '='
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_0_1_1()); 
                    }
                    match(input,16,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_0_1_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Alternatives_0_1"


    // $ANTLR start "rule__Value__Alternatives"
    // InternalKap.g:449:1: rule__Value__Alternatives : ( ( ( rule__Value__LiteralAssignment_0 ) ) | ( ( rule__Value__IdAssignment_1 ) ) | ( ( rule__Value__ObservableAssignment_2 ) ) | ( ( rule__Value__ExpressionAssignment_3 ) ) );
    public final void rule__Value__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:453:1: ( ( ( rule__Value__LiteralAssignment_0 ) ) | ( ( rule__Value__IdAssignment_1 ) ) | ( ( rule__Value__ObservableAssignment_2 ) ) | ( ( rule__Value__ExpressionAssignment_3 ) ) )
            int alt2=4;
            switch ( input.LA(1) ) {
            case RULE_STRING:
            case RULE_INT:
            case 17:
            case 18:
            case 19:
            case 33:
                {
                alt2=1;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                alt2=2;
                }
                break;
            case RULE_OBSERVABLE:
                {
                alt2=3;
                }
                break;
            case RULE_EXPR:
                {
                alt2=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // InternalKap.g:454:2: ( ( rule__Value__LiteralAssignment_0 ) )
                    {
                    // InternalKap.g:454:2: ( ( rule__Value__LiteralAssignment_0 ) )
                    // InternalKap.g:455:3: ( rule__Value__LiteralAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getLiteralAssignment_0()); 
                    }
                    // InternalKap.g:456:3: ( rule__Value__LiteralAssignment_0 )
                    // InternalKap.g:456:4: rule__Value__LiteralAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Value__LiteralAssignment_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getLiteralAssignment_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:460:2: ( ( rule__Value__IdAssignment_1 ) )
                    {
                    // InternalKap.g:460:2: ( ( rule__Value__IdAssignment_1 ) )
                    // InternalKap.g:461:3: ( rule__Value__IdAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getIdAssignment_1()); 
                    }
                    // InternalKap.g:462:3: ( rule__Value__IdAssignment_1 )
                    // InternalKap.g:462:4: rule__Value__IdAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Value__IdAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getIdAssignment_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKap.g:466:2: ( ( rule__Value__ObservableAssignment_2 ) )
                    {
                    // InternalKap.g:466:2: ( ( rule__Value__ObservableAssignment_2 ) )
                    // InternalKap.g:467:3: ( rule__Value__ObservableAssignment_2 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getObservableAssignment_2()); 
                    }
                    // InternalKap.g:468:3: ( rule__Value__ObservableAssignment_2 )
                    // InternalKap.g:468:4: rule__Value__ObservableAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Value__ObservableAssignment_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getObservableAssignment_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalKap.g:472:2: ( ( rule__Value__ExpressionAssignment_3 ) )
                    {
                    // InternalKap.g:472:2: ( ( rule__Value__ExpressionAssignment_3 ) )
                    // InternalKap.g:473:3: ( rule__Value__ExpressionAssignment_3 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getExpressionAssignment_3()); 
                    }
                    // InternalKap.g:474:3: ( rule__Value__ExpressionAssignment_3 )
                    // InternalKap.g:474:4: rule__Value__ExpressionAssignment_3
                    {
                    pushFollow(FOLLOW_2);
                    rule__Value__ExpressionAssignment_3();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getExpressionAssignment_3()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__Alternatives"


    // $ANTLR start "rule__Literal__Alternatives"
    // InternalKap.g:482:1: rule__Literal__Alternatives : ( ( ( rule__Literal__NumberAssignment_0 ) ) | ( ( rule__Literal__Group_1__0 ) ) | ( ( rule__Literal__StringAssignment_2 ) ) | ( ( rule__Literal__DateAssignment_3 ) ) | ( ( rule__Literal__BooleanAssignment_4 ) ) );
    public final void rule__Literal__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:486:1: ( ( ( rule__Literal__NumberAssignment_0 ) ) | ( ( rule__Literal__Group_1__0 ) ) | ( ( rule__Literal__StringAssignment_2 ) ) | ( ( rule__Literal__DateAssignment_3 ) ) | ( ( rule__Literal__BooleanAssignment_4 ) ) )
            int alt3=5;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // InternalKap.g:487:2: ( ( rule__Literal__NumberAssignment_0 ) )
                    {
                    // InternalKap.g:487:2: ( ( rule__Literal__NumberAssignment_0 ) )
                    // InternalKap.g:488:3: ( rule__Literal__NumberAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getNumberAssignment_0()); 
                    }
                    // InternalKap.g:489:3: ( rule__Literal__NumberAssignment_0 )
                    // InternalKap.g:489:4: rule__Literal__NumberAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Literal__NumberAssignment_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getNumberAssignment_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:493:2: ( ( rule__Literal__Group_1__0 ) )
                    {
                    // InternalKap.g:493:2: ( ( rule__Literal__Group_1__0 ) )
                    // InternalKap.g:494:3: ( rule__Literal__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getGroup_1()); 
                    }
                    // InternalKap.g:495:3: ( rule__Literal__Group_1__0 )
                    // InternalKap.g:495:4: rule__Literal__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Literal__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getGroup_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKap.g:499:2: ( ( rule__Literal__StringAssignment_2 ) )
                    {
                    // InternalKap.g:499:2: ( ( rule__Literal__StringAssignment_2 ) )
                    // InternalKap.g:500:3: ( rule__Literal__StringAssignment_2 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getStringAssignment_2()); 
                    }
                    // InternalKap.g:501:3: ( rule__Literal__StringAssignment_2 )
                    // InternalKap.g:501:4: rule__Literal__StringAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Literal__StringAssignment_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getStringAssignment_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalKap.g:505:2: ( ( rule__Literal__DateAssignment_3 ) )
                    {
                    // InternalKap.g:505:2: ( ( rule__Literal__DateAssignment_3 ) )
                    // InternalKap.g:506:3: ( rule__Literal__DateAssignment_3 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getDateAssignment_3()); 
                    }
                    // InternalKap.g:507:3: ( rule__Literal__DateAssignment_3 )
                    // InternalKap.g:507:4: rule__Literal__DateAssignment_3
                    {
                    pushFollow(FOLLOW_2);
                    rule__Literal__DateAssignment_3();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getDateAssignment_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalKap.g:511:2: ( ( rule__Literal__BooleanAssignment_4 ) )
                    {
                    // InternalKap.g:511:2: ( ( rule__Literal__BooleanAssignment_4 ) )
                    // InternalKap.g:512:3: ( rule__Literal__BooleanAssignment_4 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getBooleanAssignment_4()); 
                    }
                    // InternalKap.g:513:3: ( rule__Literal__BooleanAssignment_4 )
                    // InternalKap.g:513:4: rule__Literal__BooleanAssignment_4
                    {
                    pushFollow(FOLLOW_2);
                    rule__Literal__BooleanAssignment_4();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getBooleanAssignment_4()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Alternatives"


    // $ANTLR start "rule__Literal__BooleanAlternatives_4_0"
    // InternalKap.g:521:1: rule__Literal__BooleanAlternatives_4_0 : ( ( 'true' ) | ( 'false' ) );
    public final void rule__Literal__BooleanAlternatives_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:525:1: ( ( 'true' ) | ( 'false' ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==17) ) {
                alt4=1;
            }
            else if ( (LA4_0==18) ) {
                alt4=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // InternalKap.g:526:2: ( 'true' )
                    {
                    // InternalKap.g:526:2: ( 'true' )
                    // InternalKap.g:527:3: 'true'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getBooleanTrueKeyword_4_0_0()); 
                    }
                    match(input,17,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getBooleanTrueKeyword_4_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:532:2: ( 'false' )
                    {
                    // InternalKap.g:532:2: ( 'false' )
                    // InternalKap.g:533:3: 'false'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getBooleanFalseKeyword_4_0_1()); 
                    }
                    match(input,18,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getBooleanFalseKeyword_4_0_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__BooleanAlternatives_4_0"


    // $ANTLR start "rule__Body__Alternatives"
    // InternalKap.g:542:1: rule__Body__Alternatives : ( ( ( rule__Body__Group_0__0 ) ) | ( ( rule__Body__Group_1__0 ) ) );
    public final void rule__Body__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:546:1: ( ( ( rule__Body__Group_0__0 ) ) | ( ( rule__Body__Group_1__0 ) ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RULE_LOWERCASE_ID||LA5_0==RULE_EMBEDDEDTEXT) ) {
                alt5=1;
            }
            else if ( (LA5_0==27) ) {
                int LA5_3 = input.LA(2);

                if ( (synpred10_InternalKap()) ) {
                    alt5=1;
                }
                else if ( (true) ) {
                    alt5=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 3, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalKap.g:547:2: ( ( rule__Body__Group_0__0 ) )
                    {
                    // InternalKap.g:547:2: ( ( rule__Body__Group_0__0 ) )
                    // InternalKap.g:548:3: ( rule__Body__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getBodyAccess().getGroup_0()); 
                    }
                    // InternalKap.g:549:3: ( rule__Body__Group_0__0 )
                    // InternalKap.g:549:4: rule__Body__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Body__Group_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getBodyAccess().getGroup_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:553:2: ( ( rule__Body__Group_1__0 ) )
                    {
                    // InternalKap.g:553:2: ( ( rule__Body__Group_1__0 ) )
                    // InternalKap.g:554:3: ( rule__Body__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getBodyAccess().getGroup_1()); 
                    }
                    // InternalKap.g:555:3: ( rule__Body__Group_1__0 )
                    // InternalKap.g:555:4: rule__Body__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Body__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getBodyAccess().getGroup_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Alternatives"


    // $ANTLR start "rule__Statement__Alternatives"
    // InternalKap.g:563:1: rule__Statement__Alternatives : ( ( ( rule__Statement__CallAssignment_0 ) ) | ( ( rule__Statement__TextAssignment_1 ) ) | ( ( rule__Statement__Group_2__0 ) ) );
    public final void rule__Statement__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:567:1: ( ( ( rule__Statement__CallAssignment_0 ) ) | ( ( rule__Statement__TextAssignment_1 ) ) | ( ( rule__Statement__Group_2__0 ) ) )
            int alt6=3;
            switch ( input.LA(1) ) {
            case RULE_LOWERCASE_ID:
                {
                alt6=1;
                }
                break;
            case RULE_EMBEDDEDTEXT:
                {
                alt6=2;
                }
                break;
            case 27:
                {
                alt6=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalKap.g:568:2: ( ( rule__Statement__CallAssignment_0 ) )
                    {
                    // InternalKap.g:568:2: ( ( rule__Statement__CallAssignment_0 ) )
                    // InternalKap.g:569:3: ( rule__Statement__CallAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getStatementAccess().getCallAssignment_0()); 
                    }
                    // InternalKap.g:570:3: ( rule__Statement__CallAssignment_0 )
                    // InternalKap.g:570:4: rule__Statement__CallAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Statement__CallAssignment_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getStatementAccess().getCallAssignment_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:574:2: ( ( rule__Statement__TextAssignment_1 ) )
                    {
                    // InternalKap.g:574:2: ( ( rule__Statement__TextAssignment_1 ) )
                    // InternalKap.g:575:3: ( rule__Statement__TextAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getStatementAccess().getTextAssignment_1()); 
                    }
                    // InternalKap.g:576:3: ( rule__Statement__TextAssignment_1 )
                    // InternalKap.g:576:4: rule__Statement__TextAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Statement__TextAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getStatementAccess().getTextAssignment_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKap.g:580:2: ( ( rule__Statement__Group_2__0 ) )
                    {
                    // InternalKap.g:580:2: ( ( rule__Statement__Group_2__0 ) )
                    // InternalKap.g:581:3: ( rule__Statement__Group_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getStatementAccess().getGroup_2()); 
                    }
                    // InternalKap.g:582:3: ( rule__Statement__Group_2__0 )
                    // InternalKap.g:582:4: rule__Statement__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Statement__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getStatementAccess().getGroup_2()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Alternatives"


    // $ANTLR start "rule__Actions__Alternatives"
    // InternalKap.g:590:1: rule__Actions__Alternatives : ( ( ( rule__Actions__BodyAssignment_0 ) ) | ( ( rule__Actions__MatchAssignment_1 ) ) | ( ( rule__Actions__Group_2__0 ) ) );
    public final void rule__Actions__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:594:1: ( ( ( rule__Actions__BodyAssignment_0 ) ) | ( ( rule__Actions__MatchAssignment_1 ) ) | ( ( rule__Actions__Group_2__0 ) ) )
            int alt7=3;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // InternalKap.g:595:2: ( ( rule__Actions__BodyAssignment_0 ) )
                    {
                    // InternalKap.g:595:2: ( ( rule__Actions__BodyAssignment_0 ) )
                    // InternalKap.g:596:3: ( rule__Actions__BodyAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getActionsAccess().getBodyAssignment_0()); 
                    }
                    // InternalKap.g:597:3: ( rule__Actions__BodyAssignment_0 )
                    // InternalKap.g:597:4: rule__Actions__BodyAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Actions__BodyAssignment_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getActionsAccess().getBodyAssignment_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:601:2: ( ( rule__Actions__MatchAssignment_1 ) )
                    {
                    // InternalKap.g:601:2: ( ( rule__Actions__MatchAssignment_1 ) )
                    // InternalKap.g:602:3: ( rule__Actions__MatchAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getActionsAccess().getMatchAssignment_1()); 
                    }
                    // InternalKap.g:603:3: ( rule__Actions__MatchAssignment_1 )
                    // InternalKap.g:603:4: rule__Actions__MatchAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Actions__MatchAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getActionsAccess().getMatchAssignment_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKap.g:607:2: ( ( rule__Actions__Group_2__0 ) )
                    {
                    // InternalKap.g:607:2: ( ( rule__Actions__Group_2__0 ) )
                    // InternalKap.g:608:3: ( rule__Actions__Group_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getActionsAccess().getGroup_2()); 
                    }
                    // InternalKap.g:609:3: ( rule__Actions__Group_2__0 )
                    // InternalKap.g:609:4: rule__Actions__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Actions__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getActionsAccess().getGroup_2()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Alternatives"


    // $ANTLR start "rule__Match__Alternatives"
    // InternalKap.g:617:1: rule__Match__Alternatives : ( ( ( rule__Match__Group_0__0 ) ) | ( ( rule__Match__Group_1__0 ) ) | ( ( rule__Match__Group_2__0 ) ) | ( ( rule__Match__Group_3__0 ) ) | ( ( rule__Match__Group_4__0 ) ) | ( ( rule__Match__Group_5__0 ) ) );
    public final void rule__Match__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:621:1: ( ( ( rule__Match__Group_0__0 ) ) | ( ( rule__Match__Group_1__0 ) ) | ( ( rule__Match__Group_2__0 ) ) | ( ( rule__Match__Group_3__0 ) ) | ( ( rule__Match__Group_4__0 ) ) | ( ( rule__Match__Group_5__0 ) ) )
            int alt8=6;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // InternalKap.g:622:2: ( ( rule__Match__Group_0__0 ) )
                    {
                    // InternalKap.g:622:2: ( ( rule__Match__Group_0__0 ) )
                    // InternalKap.g:623:3: ( rule__Match__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMatchAccess().getGroup_0()); 
                    }
                    // InternalKap.g:624:3: ( rule__Match__Group_0__0 )
                    // InternalKap.g:624:4: rule__Match__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Match__Group_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMatchAccess().getGroup_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:628:2: ( ( rule__Match__Group_1__0 ) )
                    {
                    // InternalKap.g:628:2: ( ( rule__Match__Group_1__0 ) )
                    // InternalKap.g:629:3: ( rule__Match__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMatchAccess().getGroup_1()); 
                    }
                    // InternalKap.g:630:3: ( rule__Match__Group_1__0 )
                    // InternalKap.g:630:4: rule__Match__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Match__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMatchAccess().getGroup_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKap.g:634:2: ( ( rule__Match__Group_2__0 ) )
                    {
                    // InternalKap.g:634:2: ( ( rule__Match__Group_2__0 ) )
                    // InternalKap.g:635:3: ( rule__Match__Group_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMatchAccess().getGroup_2()); 
                    }
                    // InternalKap.g:636:3: ( rule__Match__Group_2__0 )
                    // InternalKap.g:636:4: rule__Match__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Match__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMatchAccess().getGroup_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalKap.g:640:2: ( ( rule__Match__Group_3__0 ) )
                    {
                    // InternalKap.g:640:2: ( ( rule__Match__Group_3__0 ) )
                    // InternalKap.g:641:3: ( rule__Match__Group_3__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMatchAccess().getGroup_3()); 
                    }
                    // InternalKap.g:642:3: ( rule__Match__Group_3__0 )
                    // InternalKap.g:642:4: rule__Match__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Match__Group_3__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMatchAccess().getGroup_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalKap.g:646:2: ( ( rule__Match__Group_4__0 ) )
                    {
                    // InternalKap.g:646:2: ( ( rule__Match__Group_4__0 ) )
                    // InternalKap.g:647:3: ( rule__Match__Group_4__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMatchAccess().getGroup_4()); 
                    }
                    // InternalKap.g:648:3: ( rule__Match__Group_4__0 )
                    // InternalKap.g:648:4: rule__Match__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Match__Group_4__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMatchAccess().getGroup_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalKap.g:652:2: ( ( rule__Match__Group_5__0 ) )
                    {
                    // InternalKap.g:652:2: ( ( rule__Match__Group_5__0 ) )
                    // InternalKap.g:653:3: ( rule__Match__Group_5__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMatchAccess().getGroup_5()); 
                    }
                    // InternalKap.g:654:3: ( rule__Match__Group_5__0 )
                    // InternalKap.g:654:4: rule__Match__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Match__Group_5__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMatchAccess().getGroup_5()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Alternatives"


    // $ANTLR start "rule__Number__Alternatives_0"
    // InternalKap.g:662:1: rule__Number__Alternatives_0 : ( ( '+' ) | ( ( rule__Number__NegativeAssignment_0_1 ) ) );
    public final void rule__Number__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:666:1: ( ( '+' ) | ( ( rule__Number__NegativeAssignment_0_1 ) ) )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==19) ) {
                alt9=1;
            }
            else if ( (LA9_0==33) ) {
                alt9=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // InternalKap.g:667:2: ( '+' )
                    {
                    // InternalKap.g:667:2: ( '+' )
                    // InternalKap.g:668:3: '+'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNumberAccess().getPlusSignKeyword_0_0()); 
                    }
                    match(input,19,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNumberAccess().getPlusSignKeyword_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:673:2: ( ( rule__Number__NegativeAssignment_0_1 ) )
                    {
                    // InternalKap.g:673:2: ( ( rule__Number__NegativeAssignment_0_1 ) )
                    // InternalKap.g:674:3: ( rule__Number__NegativeAssignment_0_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNumberAccess().getNegativeAssignment_0_1()); 
                    }
                    // InternalKap.g:675:3: ( rule__Number__NegativeAssignment_0_1 )
                    // InternalKap.g:675:4: rule__Number__NegativeAssignment_0_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__NegativeAssignment_0_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNumberAccess().getNegativeAssignment_0_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Alternatives_0"


    // $ANTLR start "rule__Number__ExponentialAlternatives_4_0_0_0"
    // InternalKap.g:683:1: rule__Number__ExponentialAlternatives_4_0_0_0 : ( ( 'e' ) | ( 'E' ) );
    public final void rule__Number__ExponentialAlternatives_4_0_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:687:1: ( ( 'e' ) | ( 'E' ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==20) ) {
                alt10=1;
            }
            else if ( (LA10_0==21) ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalKap.g:688:2: ( 'e' )
                    {
                    // InternalKap.g:688:2: ( 'e' )
                    // InternalKap.g:689:3: 'e'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNumberAccess().getExponentialEKeyword_4_0_0_0_0()); 
                    }
                    match(input,20,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNumberAccess().getExponentialEKeyword_4_0_0_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:694:2: ( 'E' )
                    {
                    // InternalKap.g:694:2: ( 'E' )
                    // InternalKap.g:695:3: 'E'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNumberAccess().getExponentialEKeyword_4_0_0_0_1()); 
                    }
                    match(input,21,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNumberAccess().getExponentialEKeyword_4_0_0_0_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__ExponentialAlternatives_4_0_0_0"


    // $ANTLR start "rule__Number__Alternatives_4_0_1"
    // InternalKap.g:704:1: rule__Number__Alternatives_4_0_1 : ( ( '+' ) | ( ( rule__Number__ExpNegativeAssignment_4_0_1_1 ) ) );
    public final void rule__Number__Alternatives_4_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:708:1: ( ( '+' ) | ( ( rule__Number__ExpNegativeAssignment_4_0_1_1 ) ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==19) ) {
                alt11=1;
            }
            else if ( (LA11_0==33) ) {
                alt11=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalKap.g:709:2: ( '+' )
                    {
                    // InternalKap.g:709:2: ( '+' )
                    // InternalKap.g:710:3: '+'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNumberAccess().getPlusSignKeyword_4_0_1_0()); 
                    }
                    match(input,19,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNumberAccess().getPlusSignKeyword_4_0_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:715:2: ( ( rule__Number__ExpNegativeAssignment_4_0_1_1 ) )
                    {
                    // InternalKap.g:715:2: ( ( rule__Number__ExpNegativeAssignment_4_0_1_1 ) )
                    // InternalKap.g:716:3: ( rule__Number__ExpNegativeAssignment_4_0_1_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNumberAccess().getExpNegativeAssignment_4_0_1_1()); 
                    }
                    // InternalKap.g:717:3: ( rule__Number__ExpNegativeAssignment_4_0_1_1 )
                    // InternalKap.g:717:4: rule__Number__ExpNegativeAssignment_4_0_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__ExpNegativeAssignment_4_0_1_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNumberAccess().getExpNegativeAssignment_4_0_1_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Alternatives_4_0_1"


    // $ANTLR start "rule__Date__Alternatives_1"
    // InternalKap.g:725:1: rule__Date__Alternatives_1 : ( ( 'AD' ) | ( 'CE' ) | ( ( rule__Date__BcAssignment_1_2 ) ) );
    public final void rule__Date__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:729:1: ( ( 'AD' ) | ( 'CE' ) | ( ( rule__Date__BcAssignment_1_2 ) ) )
            int alt12=3;
            switch ( input.LA(1) ) {
            case 22:
                {
                alt12=1;
                }
                break;
            case 23:
                {
                alt12=2;
                }
                break;
            case 37:
                {
                alt12=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // InternalKap.g:730:2: ( 'AD' )
                    {
                    // InternalKap.g:730:2: ( 'AD' )
                    // InternalKap.g:731:3: 'AD'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDateAccess().getADKeyword_1_0()); 
                    }
                    match(input,22,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getDateAccess().getADKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:736:2: ( 'CE' )
                    {
                    // InternalKap.g:736:2: ( 'CE' )
                    // InternalKap.g:737:3: 'CE'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDateAccess().getCEKeyword_1_1()); 
                    }
                    match(input,23,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getDateAccess().getCEKeyword_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKap.g:742:2: ( ( rule__Date__BcAssignment_1_2 ) )
                    {
                    // InternalKap.g:742:2: ( ( rule__Date__BcAssignment_1_2 ) )
                    // InternalKap.g:743:3: ( rule__Date__BcAssignment_1_2 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDateAccess().getBcAssignment_1_2()); 
                    }
                    // InternalKap.g:744:3: ( rule__Date__BcAssignment_1_2 )
                    // InternalKap.g:744:4: rule__Date__BcAssignment_1_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Date__BcAssignment_1_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getDateAccess().getBcAssignment_1_2()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Alternatives_1"


    // $ANTLR start "rule__Model__Group__0"
    // InternalKap.g:752:1: rule__Model__Group__0 : rule__Model__Group__0__Impl rule__Model__Group__1 ;
    public final void rule__Model__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:756:1: ( rule__Model__Group__0__Impl rule__Model__Group__1 )
            // InternalKap.g:757:2: rule__Model__Group__0__Impl rule__Model__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__Model__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Model__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__0"


    // $ANTLR start "rule__Model__Group__0__Impl"
    // InternalKap.g:764:1: rule__Model__Group__0__Impl : ( () ) ;
    public final void rule__Model__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:768:1: ( ( () ) )
            // InternalKap.g:769:1: ( () )
            {
            // InternalKap.g:769:1: ( () )
            // InternalKap.g:770:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getModelAction_0()); 
            }
            // InternalKap.g:771:2: ()
            // InternalKap.g:771:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getModelAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__0__Impl"


    // $ANTLR start "rule__Model__Group__1"
    // InternalKap.g:779:1: rule__Model__Group__1 : rule__Model__Group__1__Impl rule__Model__Group__2 ;
    public final void rule__Model__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:783:1: ( rule__Model__Group__1__Impl rule__Model__Group__2 )
            // InternalKap.g:784:2: rule__Model__Group__1__Impl rule__Model__Group__2
            {
            pushFollow(FOLLOW_3);
            rule__Model__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Model__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__1"


    // $ANTLR start "rule__Model__Group__1__Impl"
    // InternalKap.g:791:1: rule__Model__Group__1__Impl : ( ( rule__Model__PreambleAssignment_1 )? ) ;
    public final void rule__Model__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:795:1: ( ( ( rule__Model__PreambleAssignment_1 )? ) )
            // InternalKap.g:796:1: ( ( rule__Model__PreambleAssignment_1 )? )
            {
            // InternalKap.g:796:1: ( ( rule__Model__PreambleAssignment_1 )? )
            // InternalKap.g:797:2: ( rule__Model__PreambleAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getPreambleAssignment_1()); 
            }
            // InternalKap.g:798:2: ( rule__Model__PreambleAssignment_1 )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==24) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalKap.g:798:3: rule__Model__PreambleAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Model__PreambleAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getPreambleAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__1__Impl"


    // $ANTLR start "rule__Model__Group__2"
    // InternalKap.g:806:1: rule__Model__Group__2 : rule__Model__Group__2__Impl ;
    public final void rule__Model__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:810:1: ( rule__Model__Group__2__Impl )
            // InternalKap.g:811:2: rule__Model__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Model__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__2"


    // $ANTLR start "rule__Model__Group__2__Impl"
    // InternalKap.g:817:1: rule__Model__Group__2__Impl : ( ( rule__Model__DefinitionsAssignment_2 )* ) ;
    public final void rule__Model__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:821:1: ( ( ( rule__Model__DefinitionsAssignment_2 )* ) )
            // InternalKap.g:822:1: ( ( rule__Model__DefinitionsAssignment_2 )* )
            {
            // InternalKap.g:822:1: ( ( rule__Model__DefinitionsAssignment_2 )* )
            // InternalKap.g:823:2: ( rule__Model__DefinitionsAssignment_2 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getDefinitionsAssignment_2()); 
            }
            // InternalKap.g:824:2: ( rule__Model__DefinitionsAssignment_2 )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==25) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalKap.g:824:3: rule__Model__DefinitionsAssignment_2
            	    {
            	    pushFollow(FOLLOW_4);
            	    rule__Model__DefinitionsAssignment_2();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getDefinitionsAssignment_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__2__Impl"


    // $ANTLR start "rule__Preamble__Group__0"
    // InternalKap.g:833:1: rule__Preamble__Group__0 : rule__Preamble__Group__0__Impl rule__Preamble__Group__1 ;
    public final void rule__Preamble__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:837:1: ( rule__Preamble__Group__0__Impl rule__Preamble__Group__1 )
            // InternalKap.g:838:2: rule__Preamble__Group__0__Impl rule__Preamble__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__Preamble__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Preamble__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group__0"


    // $ANTLR start "rule__Preamble__Group__0__Impl"
    // InternalKap.g:845:1: rule__Preamble__Group__0__Impl : ( 'name' ) ;
    public final void rule__Preamble__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:849:1: ( ( 'name' ) )
            // InternalKap.g:850:1: ( 'name' )
            {
            // InternalKap.g:850:1: ( 'name' )
            // InternalKap.g:851:2: 'name'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getNameKeyword_0()); 
            }
            match(input,24,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getNameKeyword_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group__0__Impl"


    // $ANTLR start "rule__Preamble__Group__1"
    // InternalKap.g:860:1: rule__Preamble__Group__1 : rule__Preamble__Group__1__Impl ;
    public final void rule__Preamble__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:864:1: ( rule__Preamble__Group__1__Impl )
            // InternalKap.g:865:2: rule__Preamble__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group__1"


    // $ANTLR start "rule__Preamble__Group__1__Impl"
    // InternalKap.g:871:1: rule__Preamble__Group__1__Impl : ( ( rule__Preamble__NameAssignment_1 ) ) ;
    public final void rule__Preamble__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:875:1: ( ( ( rule__Preamble__NameAssignment_1 ) ) )
            // InternalKap.g:876:1: ( ( rule__Preamble__NameAssignment_1 ) )
            {
            // InternalKap.g:876:1: ( ( rule__Preamble__NameAssignment_1 ) )
            // InternalKap.g:877:2: ( rule__Preamble__NameAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getNameAssignment_1()); 
            }
            // InternalKap.g:878:2: ( rule__Preamble__NameAssignment_1 )
            // InternalKap.g:878:3: rule__Preamble__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__NameAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getNameAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group__1__Impl"


    // $ANTLR start "rule__Definition__Group__0"
    // InternalKap.g:887:1: rule__Definition__Group__0 : rule__Definition__Group__0__Impl rule__Definition__Group__1 ;
    public final void rule__Definition__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:891:1: ( rule__Definition__Group__0__Impl rule__Definition__Group__1 )
            // InternalKap.g:892:2: rule__Definition__Group__0__Impl rule__Definition__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__Definition__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Definition__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__0"


    // $ANTLR start "rule__Definition__Group__0__Impl"
    // InternalKap.g:899:1: rule__Definition__Group__0__Impl : ( 'def' ) ;
    public final void rule__Definition__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:903:1: ( ( 'def' ) )
            // InternalKap.g:904:1: ( 'def' )
            {
            // InternalKap.g:904:1: ( 'def' )
            // InternalKap.g:905:2: 'def'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getDefKeyword_0()); 
            }
            match(input,25,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getDefKeyword_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__0__Impl"


    // $ANTLR start "rule__Definition__Group__1"
    // InternalKap.g:914:1: rule__Definition__Group__1 : rule__Definition__Group__1__Impl rule__Definition__Group__2 ;
    public final void rule__Definition__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:918:1: ( rule__Definition__Group__1__Impl rule__Definition__Group__2 )
            // InternalKap.g:919:2: rule__Definition__Group__1__Impl rule__Definition__Group__2
            {
            pushFollow(FOLLOW_6);
            rule__Definition__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Definition__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__1"


    // $ANTLR start "rule__Definition__Group__1__Impl"
    // InternalKap.g:926:1: rule__Definition__Group__1__Impl : ( ( rule__Definition__NameAssignment_1 ) ) ;
    public final void rule__Definition__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:930:1: ( ( ( rule__Definition__NameAssignment_1 ) ) )
            // InternalKap.g:931:1: ( ( rule__Definition__NameAssignment_1 ) )
            {
            // InternalKap.g:931:1: ( ( rule__Definition__NameAssignment_1 ) )
            // InternalKap.g:932:2: ( rule__Definition__NameAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getNameAssignment_1()); 
            }
            // InternalKap.g:933:2: ( rule__Definition__NameAssignment_1 )
            // InternalKap.g:933:3: rule__Definition__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Definition__NameAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getNameAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__1__Impl"


    // $ANTLR start "rule__Definition__Group__2"
    // InternalKap.g:941:1: rule__Definition__Group__2 : rule__Definition__Group__2__Impl rule__Definition__Group__3 ;
    public final void rule__Definition__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:945:1: ( rule__Definition__Group__2__Impl rule__Definition__Group__3 )
            // InternalKap.g:946:2: rule__Definition__Group__2__Impl rule__Definition__Group__3
            {
            pushFollow(FOLLOW_6);
            rule__Definition__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Definition__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__2"


    // $ANTLR start "rule__Definition__Group__2__Impl"
    // InternalKap.g:953:1: rule__Definition__Group__2__Impl : ( ( rule__Definition__ArgumentsAssignment_2 )? ) ;
    public final void rule__Definition__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:957:1: ( ( ( rule__Definition__ArgumentsAssignment_2 )? ) )
            // InternalKap.g:958:1: ( ( rule__Definition__ArgumentsAssignment_2 )? )
            {
            // InternalKap.g:958:1: ( ( rule__Definition__ArgumentsAssignment_2 )? )
            // InternalKap.g:959:2: ( rule__Definition__ArgumentsAssignment_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getArgumentsAssignment_2()); 
            }
            // InternalKap.g:960:2: ( rule__Definition__ArgumentsAssignment_2 )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==27) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalKap.g:960:3: rule__Definition__ArgumentsAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Definition__ArgumentsAssignment_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getArgumentsAssignment_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__2__Impl"


    // $ANTLR start "rule__Definition__Group__3"
    // InternalKap.g:968:1: rule__Definition__Group__3 : rule__Definition__Group__3__Impl rule__Definition__Group__4 ;
    public final void rule__Definition__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:972:1: ( rule__Definition__Group__3__Impl rule__Definition__Group__4 )
            // InternalKap.g:973:2: rule__Definition__Group__3__Impl rule__Definition__Group__4
            {
            pushFollow(FOLLOW_7);
            rule__Definition__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Definition__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__3"


    // $ANTLR start "rule__Definition__Group__3__Impl"
    // InternalKap.g:980:1: rule__Definition__Group__3__Impl : ( ':' ) ;
    public final void rule__Definition__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:984:1: ( ( ':' ) )
            // InternalKap.g:985:1: ( ':' )
            {
            // InternalKap.g:985:1: ( ':' )
            // InternalKap.g:986:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getColonKeyword_3()); 
            }
            match(input,26,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getColonKeyword_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__3__Impl"


    // $ANTLR start "rule__Definition__Group__4"
    // InternalKap.g:995:1: rule__Definition__Group__4 : rule__Definition__Group__4__Impl ;
    public final void rule__Definition__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:999:1: ( rule__Definition__Group__4__Impl )
            // InternalKap.g:1000:2: rule__Definition__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Definition__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__4"


    // $ANTLR start "rule__Definition__Group__4__Impl"
    // InternalKap.g:1006:1: rule__Definition__Group__4__Impl : ( ( rule__Definition__BodyAssignment_4 ) ) ;
    public final void rule__Definition__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1010:1: ( ( ( rule__Definition__BodyAssignment_4 ) ) )
            // InternalKap.g:1011:1: ( ( rule__Definition__BodyAssignment_4 ) )
            {
            // InternalKap.g:1011:1: ( ( rule__Definition__BodyAssignment_4 ) )
            // InternalKap.g:1012:2: ( rule__Definition__BodyAssignment_4 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getBodyAssignment_4()); 
            }
            // InternalKap.g:1013:2: ( rule__Definition__BodyAssignment_4 )
            // InternalKap.g:1013:3: rule__Definition__BodyAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__Definition__BodyAssignment_4();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getBodyAssignment_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__4__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group__0"
    // InternalKap.g:1022:1: rule__ArgumentDeclaration__Group__0 : rule__ArgumentDeclaration__Group__0__Impl rule__ArgumentDeclaration__Group__1 ;
    public final void rule__ArgumentDeclaration__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1026:1: ( rule__ArgumentDeclaration__Group__0__Impl rule__ArgumentDeclaration__Group__1 )
            // InternalKap.g:1027:2: rule__ArgumentDeclaration__Group__0__Impl rule__ArgumentDeclaration__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__ArgumentDeclaration__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__0"


    // $ANTLR start "rule__ArgumentDeclaration__Group__0__Impl"
    // InternalKap.g:1034:1: rule__ArgumentDeclaration__Group__0__Impl : ( () ) ;
    public final void rule__ArgumentDeclaration__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1038:1: ( ( () ) )
            // InternalKap.g:1039:1: ( () )
            {
            // InternalKap.g:1039:1: ( () )
            // InternalKap.g:1040:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getArgumentDeclarationAction_0()); 
            }
            // InternalKap.g:1041:2: ()
            // InternalKap.g:1041:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getArgumentDeclarationAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__0__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group__1"
    // InternalKap.g:1049:1: rule__ArgumentDeclaration__Group__1 : rule__ArgumentDeclaration__Group__1__Impl rule__ArgumentDeclaration__Group__2 ;
    public final void rule__ArgumentDeclaration__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1053:1: ( rule__ArgumentDeclaration__Group__1__Impl rule__ArgumentDeclaration__Group__2 )
            // InternalKap.g:1054:2: rule__ArgumentDeclaration__Group__1__Impl rule__ArgumentDeclaration__Group__2
            {
            pushFollow(FOLLOW_9);
            rule__ArgumentDeclaration__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__1"


    // $ANTLR start "rule__ArgumentDeclaration__Group__1__Impl"
    // InternalKap.g:1061:1: rule__ArgumentDeclaration__Group__1__Impl : ( '(' ) ;
    public final void rule__ArgumentDeclaration__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1065:1: ( ( '(' ) )
            // InternalKap.g:1066:1: ( '(' )
            {
            // InternalKap.g:1066:1: ( '(' )
            // InternalKap.g:1067:2: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getLeftParenthesisKeyword_1()); 
            }
            match(input,27,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getLeftParenthesisKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__1__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group__2"
    // InternalKap.g:1076:1: rule__ArgumentDeclaration__Group__2 : rule__ArgumentDeclaration__Group__2__Impl rule__ArgumentDeclaration__Group__3 ;
    public final void rule__ArgumentDeclaration__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1080:1: ( rule__ArgumentDeclaration__Group__2__Impl rule__ArgumentDeclaration__Group__3 )
            // InternalKap.g:1081:2: rule__ArgumentDeclaration__Group__2__Impl rule__ArgumentDeclaration__Group__3
            {
            pushFollow(FOLLOW_9);
            rule__ArgumentDeclaration__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__2"


    // $ANTLR start "rule__ArgumentDeclaration__Group__2__Impl"
    // InternalKap.g:1088:1: rule__ArgumentDeclaration__Group__2__Impl : ( ( rule__ArgumentDeclaration__Group_2__0 )? ) ;
    public final void rule__ArgumentDeclaration__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1092:1: ( ( ( rule__ArgumentDeclaration__Group_2__0 )? ) )
            // InternalKap.g:1093:1: ( ( rule__ArgumentDeclaration__Group_2__0 )? )
            {
            // InternalKap.g:1093:1: ( ( rule__ArgumentDeclaration__Group_2__0 )? )
            // InternalKap.g:1094:2: ( rule__ArgumentDeclaration__Group_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getGroup_2()); 
            }
            // InternalKap.g:1095:2: ( rule__ArgumentDeclaration__Group_2__0 )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_LOWERCASE_ID) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalKap.g:1095:3: rule__ArgumentDeclaration__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ArgumentDeclaration__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getGroup_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__2__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group__3"
    // InternalKap.g:1103:1: rule__ArgumentDeclaration__Group__3 : rule__ArgumentDeclaration__Group__3__Impl ;
    public final void rule__ArgumentDeclaration__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1107:1: ( rule__ArgumentDeclaration__Group__3__Impl )
            // InternalKap.g:1108:2: rule__ArgumentDeclaration__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__3"


    // $ANTLR start "rule__ArgumentDeclaration__Group__3__Impl"
    // InternalKap.g:1114:1: rule__ArgumentDeclaration__Group__3__Impl : ( ')' ) ;
    public final void rule__ArgumentDeclaration__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1118:1: ( ( ')' ) )
            // InternalKap.g:1119:1: ( ')' )
            {
            // InternalKap.g:1119:1: ( ')' )
            // InternalKap.g:1120:2: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getRightParenthesisKeyword_3()); 
            }
            match(input,28,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getRightParenthesisKeyword_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__3__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2__0"
    // InternalKap.g:1130:1: rule__ArgumentDeclaration__Group_2__0 : rule__ArgumentDeclaration__Group_2__0__Impl rule__ArgumentDeclaration__Group_2__1 ;
    public final void rule__ArgumentDeclaration__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1134:1: ( rule__ArgumentDeclaration__Group_2__0__Impl rule__ArgumentDeclaration__Group_2__1 )
            // InternalKap.g:1135:2: rule__ArgumentDeclaration__Group_2__0__Impl rule__ArgumentDeclaration__Group_2__1
            {
            pushFollow(FOLLOW_10);
            rule__ArgumentDeclaration__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2__0"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2__0__Impl"
    // InternalKap.g:1142:1: rule__ArgumentDeclaration__Group_2__0__Impl : ( ( rule__ArgumentDeclaration__IdsAssignment_2_0 ) ) ;
    public final void rule__ArgumentDeclaration__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1146:1: ( ( ( rule__ArgumentDeclaration__IdsAssignment_2_0 ) ) )
            // InternalKap.g:1147:1: ( ( rule__ArgumentDeclaration__IdsAssignment_2_0 ) )
            {
            // InternalKap.g:1147:1: ( ( rule__ArgumentDeclaration__IdsAssignment_2_0 ) )
            // InternalKap.g:1148:2: ( rule__ArgumentDeclaration__IdsAssignment_2_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getIdsAssignment_2_0()); 
            }
            // InternalKap.g:1149:2: ( rule__ArgumentDeclaration__IdsAssignment_2_0 )
            // InternalKap.g:1149:3: rule__ArgumentDeclaration__IdsAssignment_2_0
            {
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__IdsAssignment_2_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getIdsAssignment_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2__0__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2__1"
    // InternalKap.g:1157:1: rule__ArgumentDeclaration__Group_2__1 : rule__ArgumentDeclaration__Group_2__1__Impl ;
    public final void rule__ArgumentDeclaration__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1161:1: ( rule__ArgumentDeclaration__Group_2__1__Impl )
            // InternalKap.g:1162:2: rule__ArgumentDeclaration__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2__1"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2__1__Impl"
    // InternalKap.g:1168:1: rule__ArgumentDeclaration__Group_2__1__Impl : ( ( rule__ArgumentDeclaration__Group_2_1__0 )* ) ;
    public final void rule__ArgumentDeclaration__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1172:1: ( ( ( rule__ArgumentDeclaration__Group_2_1__0 )* ) )
            // InternalKap.g:1173:1: ( ( rule__ArgumentDeclaration__Group_2_1__0 )* )
            {
            // InternalKap.g:1173:1: ( ( rule__ArgumentDeclaration__Group_2_1__0 )* )
            // InternalKap.g:1174:2: ( rule__ArgumentDeclaration__Group_2_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getGroup_2_1()); 
            }
            // InternalKap.g:1175:2: ( rule__ArgumentDeclaration__Group_2_1__0 )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==29) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalKap.g:1175:3: rule__ArgumentDeclaration__Group_2_1__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__ArgumentDeclaration__Group_2_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getGroup_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2__1__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2_1__0"
    // InternalKap.g:1184:1: rule__ArgumentDeclaration__Group_2_1__0 : rule__ArgumentDeclaration__Group_2_1__0__Impl rule__ArgumentDeclaration__Group_2_1__1 ;
    public final void rule__ArgumentDeclaration__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1188:1: ( rule__ArgumentDeclaration__Group_2_1__0__Impl rule__ArgumentDeclaration__Group_2_1__1 )
            // InternalKap.g:1189:2: rule__ArgumentDeclaration__Group_2_1__0__Impl rule__ArgumentDeclaration__Group_2_1__1
            {
            pushFollow(FOLLOW_5);
            rule__ArgumentDeclaration__Group_2_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group_2_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2_1__0"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2_1__0__Impl"
    // InternalKap.g:1196:1: rule__ArgumentDeclaration__Group_2_1__0__Impl : ( ',' ) ;
    public final void rule__ArgumentDeclaration__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1200:1: ( ( ',' ) )
            // InternalKap.g:1201:1: ( ',' )
            {
            // InternalKap.g:1201:1: ( ',' )
            // InternalKap.g:1202:2: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getCommaKeyword_2_1_0()); 
            }
            match(input,29,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getCommaKeyword_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2_1__0__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2_1__1"
    // InternalKap.g:1211:1: rule__ArgumentDeclaration__Group_2_1__1 : rule__ArgumentDeclaration__Group_2_1__1__Impl ;
    public final void rule__ArgumentDeclaration__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1215:1: ( rule__ArgumentDeclaration__Group_2_1__1__Impl )
            // InternalKap.g:1216:2: rule__ArgumentDeclaration__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group_2_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2_1__1"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2_1__1__Impl"
    // InternalKap.g:1222:1: rule__ArgumentDeclaration__Group_2_1__1__Impl : ( ( rule__ArgumentDeclaration__IdsAssignment_2_1_1 ) ) ;
    public final void rule__ArgumentDeclaration__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1226:1: ( ( ( rule__ArgumentDeclaration__IdsAssignment_2_1_1 ) ) )
            // InternalKap.g:1227:1: ( ( rule__ArgumentDeclaration__IdsAssignment_2_1_1 ) )
            {
            // InternalKap.g:1227:1: ( ( rule__ArgumentDeclaration__IdsAssignment_2_1_1 ) )
            // InternalKap.g:1228:2: ( rule__ArgumentDeclaration__IdsAssignment_2_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getIdsAssignment_2_1_1()); 
            }
            // InternalKap.g:1229:2: ( rule__ArgumentDeclaration__IdsAssignment_2_1_1 )
            // InternalKap.g:1229:3: rule__ArgumentDeclaration__IdsAssignment_2_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__IdsAssignment_2_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getIdsAssignment_2_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2_1__1__Impl"


    // $ANTLR start "rule__ParameterList__Group__0"
    // InternalKap.g:1238:1: rule__ParameterList__Group__0 : rule__ParameterList__Group__0__Impl rule__ParameterList__Group__1 ;
    public final void rule__ParameterList__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1242:1: ( rule__ParameterList__Group__0__Impl rule__ParameterList__Group__1 )
            // InternalKap.g:1243:2: rule__ParameterList__Group__0__Impl rule__ParameterList__Group__1
            {
            pushFollow(FOLLOW_10);
            rule__ParameterList__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ParameterList__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group__0"


    // $ANTLR start "rule__ParameterList__Group__0__Impl"
    // InternalKap.g:1250:1: rule__ParameterList__Group__0__Impl : ( ( rule__ParameterList__PairsAssignment_0 ) ) ;
    public final void rule__ParameterList__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1254:1: ( ( ( rule__ParameterList__PairsAssignment_0 ) ) )
            // InternalKap.g:1255:1: ( ( rule__ParameterList__PairsAssignment_0 ) )
            {
            // InternalKap.g:1255:1: ( ( rule__ParameterList__PairsAssignment_0 ) )
            // InternalKap.g:1256:2: ( rule__ParameterList__PairsAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getPairsAssignment_0()); 
            }
            // InternalKap.g:1257:2: ( rule__ParameterList__PairsAssignment_0 )
            // InternalKap.g:1257:3: rule__ParameterList__PairsAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__ParameterList__PairsAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getPairsAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group__0__Impl"


    // $ANTLR start "rule__ParameterList__Group__1"
    // InternalKap.g:1265:1: rule__ParameterList__Group__1 : rule__ParameterList__Group__1__Impl ;
    public final void rule__ParameterList__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1269:1: ( rule__ParameterList__Group__1__Impl )
            // InternalKap.g:1270:2: rule__ParameterList__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ParameterList__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group__1"


    // $ANTLR start "rule__ParameterList__Group__1__Impl"
    // InternalKap.g:1276:1: rule__ParameterList__Group__1__Impl : ( ( rule__ParameterList__Group_1__0 )* ) ;
    public final void rule__ParameterList__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1280:1: ( ( ( rule__ParameterList__Group_1__0 )* ) )
            // InternalKap.g:1281:1: ( ( rule__ParameterList__Group_1__0 )* )
            {
            // InternalKap.g:1281:1: ( ( rule__ParameterList__Group_1__0 )* )
            // InternalKap.g:1282:2: ( rule__ParameterList__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getGroup_1()); 
            }
            // InternalKap.g:1283:2: ( rule__ParameterList__Group_1__0 )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==29) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalKap.g:1283:3: rule__ParameterList__Group_1__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__ParameterList__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group__1__Impl"


    // $ANTLR start "rule__ParameterList__Group_1__0"
    // InternalKap.g:1292:1: rule__ParameterList__Group_1__0 : rule__ParameterList__Group_1__0__Impl rule__ParameterList__Group_1__1 ;
    public final void rule__ParameterList__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1296:1: ( rule__ParameterList__Group_1__0__Impl rule__ParameterList__Group_1__1 )
            // InternalKap.g:1297:2: rule__ParameterList__Group_1__0__Impl rule__ParameterList__Group_1__1
            {
            pushFollow(FOLLOW_12);
            rule__ParameterList__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ParameterList__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group_1__0"


    // $ANTLR start "rule__ParameterList__Group_1__0__Impl"
    // InternalKap.g:1304:1: rule__ParameterList__Group_1__0__Impl : ( ( ',' ) ) ;
    public final void rule__ParameterList__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1308:1: ( ( ( ',' ) ) )
            // InternalKap.g:1309:1: ( ( ',' ) )
            {
            // InternalKap.g:1309:1: ( ( ',' ) )
            // InternalKap.g:1310:2: ( ',' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getCommaKeyword_1_0()); 
            }
            // InternalKap.g:1311:2: ( ',' )
            // InternalKap.g:1311:3: ','
            {
            match(input,29,FOLLOW_2); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getCommaKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group_1__0__Impl"


    // $ANTLR start "rule__ParameterList__Group_1__1"
    // InternalKap.g:1319:1: rule__ParameterList__Group_1__1 : rule__ParameterList__Group_1__1__Impl ;
    public final void rule__ParameterList__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1323:1: ( rule__ParameterList__Group_1__1__Impl )
            // InternalKap.g:1324:2: rule__ParameterList__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ParameterList__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group_1__1"


    // $ANTLR start "rule__ParameterList__Group_1__1__Impl"
    // InternalKap.g:1330:1: rule__ParameterList__Group_1__1__Impl : ( ( rule__ParameterList__PairsAssignment_1_1 ) ) ;
    public final void rule__ParameterList__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1334:1: ( ( ( rule__ParameterList__PairsAssignment_1_1 ) ) )
            // InternalKap.g:1335:1: ( ( rule__ParameterList__PairsAssignment_1_1 ) )
            {
            // InternalKap.g:1335:1: ( ( rule__ParameterList__PairsAssignment_1_1 ) )
            // InternalKap.g:1336:2: ( rule__ParameterList__PairsAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getPairsAssignment_1_1()); 
            }
            // InternalKap.g:1337:2: ( rule__ParameterList__PairsAssignment_1_1 )
            // InternalKap.g:1337:3: rule__ParameterList__PairsAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ParameterList__PairsAssignment_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getPairsAssignment_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group_1__1__Impl"


    // $ANTLR start "rule__KeyValuePair__Group__0"
    // InternalKap.g:1346:1: rule__KeyValuePair__Group__0 : rule__KeyValuePair__Group__0__Impl rule__KeyValuePair__Group__1 ;
    public final void rule__KeyValuePair__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1350:1: ( rule__KeyValuePair__Group__0__Impl rule__KeyValuePair__Group__1 )
            // InternalKap.g:1351:2: rule__KeyValuePair__Group__0__Impl rule__KeyValuePair__Group__1
            {
            pushFollow(FOLLOW_12);
            rule__KeyValuePair__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group__0"


    // $ANTLR start "rule__KeyValuePair__Group__0__Impl"
    // InternalKap.g:1358:1: rule__KeyValuePair__Group__0__Impl : ( ( rule__KeyValuePair__Group_0__0 )? ) ;
    public final void rule__KeyValuePair__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1362:1: ( ( ( rule__KeyValuePair__Group_0__0 )? ) )
            // InternalKap.g:1363:1: ( ( rule__KeyValuePair__Group_0__0 )? )
            {
            // InternalKap.g:1363:1: ( ( rule__KeyValuePair__Group_0__0 )? )
            // InternalKap.g:1364:2: ( rule__KeyValuePair__Group_0__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getGroup_0()); 
            }
            // InternalKap.g:1365:2: ( rule__KeyValuePair__Group_0__0 )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==RULE_LOWERCASE_ID) ) {
                int LA19_1 = input.LA(2);

                if ( (LA19_1==16||LA19_1==35) ) {
                    alt19=1;
                }
            }
            switch (alt19) {
                case 1 :
                    // InternalKap.g:1365:3: rule__KeyValuePair__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__KeyValuePair__Group_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getGroup_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group__0__Impl"


    // $ANTLR start "rule__KeyValuePair__Group__1"
    // InternalKap.g:1373:1: rule__KeyValuePair__Group__1 : rule__KeyValuePair__Group__1__Impl ;
    public final void rule__KeyValuePair__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1377:1: ( rule__KeyValuePair__Group__1__Impl )
            // InternalKap.g:1378:2: rule__KeyValuePair__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group__1"


    // $ANTLR start "rule__KeyValuePair__Group__1__Impl"
    // InternalKap.g:1384:1: rule__KeyValuePair__Group__1__Impl : ( ( rule__KeyValuePair__ValueAssignment_1 ) ) ;
    public final void rule__KeyValuePair__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1388:1: ( ( ( rule__KeyValuePair__ValueAssignment_1 ) ) )
            // InternalKap.g:1389:1: ( ( rule__KeyValuePair__ValueAssignment_1 ) )
            {
            // InternalKap.g:1389:1: ( ( rule__KeyValuePair__ValueAssignment_1 ) )
            // InternalKap.g:1390:2: ( rule__KeyValuePair__ValueAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getValueAssignment_1()); 
            }
            // InternalKap.g:1391:2: ( rule__KeyValuePair__ValueAssignment_1 )
            // InternalKap.g:1391:3: rule__KeyValuePair__ValueAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__ValueAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getValueAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group__1__Impl"


    // $ANTLR start "rule__KeyValuePair__Group_0__0"
    // InternalKap.g:1400:1: rule__KeyValuePair__Group_0__0 : rule__KeyValuePair__Group_0__0__Impl rule__KeyValuePair__Group_0__1 ;
    public final void rule__KeyValuePair__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1404:1: ( rule__KeyValuePair__Group_0__0__Impl rule__KeyValuePair__Group_0__1 )
            // InternalKap.g:1405:2: rule__KeyValuePair__Group_0__0__Impl rule__KeyValuePair__Group_0__1
            {
            pushFollow(FOLLOW_13);
            rule__KeyValuePair__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__Group_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group_0__0"


    // $ANTLR start "rule__KeyValuePair__Group_0__0__Impl"
    // InternalKap.g:1412:1: rule__KeyValuePair__Group_0__0__Impl : ( ( rule__KeyValuePair__NameAssignment_0_0 ) ) ;
    public final void rule__KeyValuePair__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1416:1: ( ( ( rule__KeyValuePair__NameAssignment_0_0 ) ) )
            // InternalKap.g:1417:1: ( ( rule__KeyValuePair__NameAssignment_0_0 ) )
            {
            // InternalKap.g:1417:1: ( ( rule__KeyValuePair__NameAssignment_0_0 ) )
            // InternalKap.g:1418:2: ( rule__KeyValuePair__NameAssignment_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getNameAssignment_0_0()); 
            }
            // InternalKap.g:1419:2: ( rule__KeyValuePair__NameAssignment_0_0 )
            // InternalKap.g:1419:3: rule__KeyValuePair__NameAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__NameAssignment_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getNameAssignment_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group_0__0__Impl"


    // $ANTLR start "rule__KeyValuePair__Group_0__1"
    // InternalKap.g:1427:1: rule__KeyValuePair__Group_0__1 : rule__KeyValuePair__Group_0__1__Impl ;
    public final void rule__KeyValuePair__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1431:1: ( rule__KeyValuePair__Group_0__1__Impl )
            // InternalKap.g:1432:2: rule__KeyValuePair__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__Group_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group_0__1"


    // $ANTLR start "rule__KeyValuePair__Group_0__1__Impl"
    // InternalKap.g:1438:1: rule__KeyValuePair__Group_0__1__Impl : ( ( rule__KeyValuePair__Alternatives_0_1 ) ) ;
    public final void rule__KeyValuePair__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1442:1: ( ( ( rule__KeyValuePair__Alternatives_0_1 ) ) )
            // InternalKap.g:1443:1: ( ( rule__KeyValuePair__Alternatives_0_1 ) )
            {
            // InternalKap.g:1443:1: ( ( rule__KeyValuePair__Alternatives_0_1 ) )
            // InternalKap.g:1444:2: ( rule__KeyValuePair__Alternatives_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getAlternatives_0_1()); 
            }
            // InternalKap.g:1445:2: ( rule__KeyValuePair__Alternatives_0_1 )
            // InternalKap.g:1445:3: rule__KeyValuePair__Alternatives_0_1
            {
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__Alternatives_0_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getAlternatives_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group_0__1__Impl"


    // $ANTLR start "rule__Literal__Group_1__0"
    // InternalKap.g:1454:1: rule__Literal__Group_1__0 : rule__Literal__Group_1__0__Impl rule__Literal__Group_1__1 ;
    public final void rule__Literal__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1458:1: ( rule__Literal__Group_1__0__Impl rule__Literal__Group_1__1 )
            // InternalKap.g:1459:2: rule__Literal__Group_1__0__Impl rule__Literal__Group_1__1
            {
            pushFollow(FOLLOW_14);
            rule__Literal__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Literal__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_1__0"


    // $ANTLR start "rule__Literal__Group_1__0__Impl"
    // InternalKap.g:1466:1: rule__Literal__Group_1__0__Impl : ( ( rule__Literal__FromAssignment_1_0 ) ) ;
    public final void rule__Literal__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1470:1: ( ( ( rule__Literal__FromAssignment_1_0 ) ) )
            // InternalKap.g:1471:1: ( ( rule__Literal__FromAssignment_1_0 ) )
            {
            // InternalKap.g:1471:1: ( ( rule__Literal__FromAssignment_1_0 ) )
            // InternalKap.g:1472:2: ( rule__Literal__FromAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getFromAssignment_1_0()); 
            }
            // InternalKap.g:1473:2: ( rule__Literal__FromAssignment_1_0 )
            // InternalKap.g:1473:3: rule__Literal__FromAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Literal__FromAssignment_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getFromAssignment_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_1__0__Impl"


    // $ANTLR start "rule__Literal__Group_1__1"
    // InternalKap.g:1481:1: rule__Literal__Group_1__1 : rule__Literal__Group_1__1__Impl rule__Literal__Group_1__2 ;
    public final void rule__Literal__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1485:1: ( rule__Literal__Group_1__1__Impl rule__Literal__Group_1__2 )
            // InternalKap.g:1486:2: rule__Literal__Group_1__1__Impl rule__Literal__Group_1__2
            {
            pushFollow(FOLLOW_15);
            rule__Literal__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Literal__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_1__1"


    // $ANTLR start "rule__Literal__Group_1__1__Impl"
    // InternalKap.g:1493:1: rule__Literal__Group_1__1__Impl : ( 'to' ) ;
    public final void rule__Literal__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1497:1: ( ( 'to' ) )
            // InternalKap.g:1498:1: ( 'to' )
            {
            // InternalKap.g:1498:1: ( 'to' )
            // InternalKap.g:1499:2: 'to'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getToKeyword_1_1()); 
            }
            match(input,30,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getToKeyword_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_1__1__Impl"


    // $ANTLR start "rule__Literal__Group_1__2"
    // InternalKap.g:1508:1: rule__Literal__Group_1__2 : rule__Literal__Group_1__2__Impl ;
    public final void rule__Literal__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1512:1: ( rule__Literal__Group_1__2__Impl )
            // InternalKap.g:1513:2: rule__Literal__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Literal__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_1__2"


    // $ANTLR start "rule__Literal__Group_1__2__Impl"
    // InternalKap.g:1519:1: rule__Literal__Group_1__2__Impl : ( ( rule__Literal__ToAssignment_1_2 ) ) ;
    public final void rule__Literal__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1523:1: ( ( ( rule__Literal__ToAssignment_1_2 ) ) )
            // InternalKap.g:1524:1: ( ( rule__Literal__ToAssignment_1_2 ) )
            {
            // InternalKap.g:1524:1: ( ( rule__Literal__ToAssignment_1_2 ) )
            // InternalKap.g:1525:2: ( rule__Literal__ToAssignment_1_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getToAssignment_1_2()); 
            }
            // InternalKap.g:1526:2: ( rule__Literal__ToAssignment_1_2 )
            // InternalKap.g:1526:3: rule__Literal__ToAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Literal__ToAssignment_1_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getToAssignment_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_1__2__Impl"


    // $ANTLR start "rule__Body__Group_0__0"
    // InternalKap.g:1535:1: rule__Body__Group_0__0 : rule__Body__Group_0__0__Impl rule__Body__Group_0__1 ;
    public final void rule__Body__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1539:1: ( rule__Body__Group_0__0__Impl rule__Body__Group_0__1 )
            // InternalKap.g:1540:2: rule__Body__Group_0__0__Impl rule__Body__Group_0__1
            {
            pushFollow(FOLLOW_7);
            rule__Body__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Body__Group_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0__0"


    // $ANTLR start "rule__Body__Group_0__0__Impl"
    // InternalKap.g:1547:1: rule__Body__Group_0__0__Impl : ( () ) ;
    public final void rule__Body__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1551:1: ( ( () ) )
            // InternalKap.g:1552:1: ( () )
            {
            // InternalKap.g:1552:1: ( () )
            // InternalKap.g:1553:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getBodyAction_0_0()); 
            }
            // InternalKap.g:1554:2: ()
            // InternalKap.g:1554:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getBodyAction_0_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0__0__Impl"


    // $ANTLR start "rule__Body__Group_0__1"
    // InternalKap.g:1562:1: rule__Body__Group_0__1 : rule__Body__Group_0__1__Impl rule__Body__Group_0__2 ;
    public final void rule__Body__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1566:1: ( rule__Body__Group_0__1__Impl rule__Body__Group_0__2 )
            // InternalKap.g:1567:2: rule__Body__Group_0__1__Impl rule__Body__Group_0__2
            {
            pushFollow(FOLLOW_16);
            rule__Body__Group_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Body__Group_0__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0__1"


    // $ANTLR start "rule__Body__Group_0__1__Impl"
    // InternalKap.g:1574:1: rule__Body__Group_0__1__Impl : ( ( rule__Body__ListAssignment_0_1 ) ) ;
    public final void rule__Body__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1578:1: ( ( ( rule__Body__ListAssignment_0_1 ) ) )
            // InternalKap.g:1579:1: ( ( rule__Body__ListAssignment_0_1 ) )
            {
            // InternalKap.g:1579:1: ( ( rule__Body__ListAssignment_0_1 ) )
            // InternalKap.g:1580:2: ( rule__Body__ListAssignment_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getListAssignment_0_1()); 
            }
            // InternalKap.g:1581:2: ( rule__Body__ListAssignment_0_1 )
            // InternalKap.g:1581:3: rule__Body__ListAssignment_0_1
            {
            pushFollow(FOLLOW_2);
            rule__Body__ListAssignment_0_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getListAssignment_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0__1__Impl"


    // $ANTLR start "rule__Body__Group_0__2"
    // InternalKap.g:1589:1: rule__Body__Group_0__2 : rule__Body__Group_0__2__Impl ;
    public final void rule__Body__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1593:1: ( rule__Body__Group_0__2__Impl )
            // InternalKap.g:1594:2: rule__Body__Group_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Body__Group_0__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0__2"


    // $ANTLR start "rule__Body__Group_0__2__Impl"
    // InternalKap.g:1600:1: rule__Body__Group_0__2__Impl : ( ( rule__Body__Group_0_2__0 )* ) ;
    public final void rule__Body__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1604:1: ( ( ( rule__Body__Group_0_2__0 )* ) )
            // InternalKap.g:1605:1: ( ( rule__Body__Group_0_2__0 )* )
            {
            // InternalKap.g:1605:1: ( ( rule__Body__Group_0_2__0 )* )
            // InternalKap.g:1606:2: ( rule__Body__Group_0_2__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getGroup_0_2()); 
            }
            // InternalKap.g:1607:2: ( rule__Body__Group_0_2__0 )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==31) ) {
                    int LA20_2 = input.LA(2);

                    if ( (synpred32_InternalKap()) ) {
                        alt20=1;
                    }


                }


                switch (alt20) {
            	case 1 :
            	    // InternalKap.g:1607:3: rule__Body__Group_0_2__0
            	    {
            	    pushFollow(FOLLOW_17);
            	    rule__Body__Group_0_2__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getGroup_0_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0__2__Impl"


    // $ANTLR start "rule__Body__Group_0_2__0"
    // InternalKap.g:1616:1: rule__Body__Group_0_2__0 : rule__Body__Group_0_2__0__Impl rule__Body__Group_0_2__1 ;
    public final void rule__Body__Group_0_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1620:1: ( rule__Body__Group_0_2__0__Impl rule__Body__Group_0_2__1 )
            // InternalKap.g:1621:2: rule__Body__Group_0_2__0__Impl rule__Body__Group_0_2__1
            {
            pushFollow(FOLLOW_7);
            rule__Body__Group_0_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Body__Group_0_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0_2__0"


    // $ANTLR start "rule__Body__Group_0_2__0__Impl"
    // InternalKap.g:1628:1: rule__Body__Group_0_2__0__Impl : ( ';' ) ;
    public final void rule__Body__Group_0_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1632:1: ( ( ';' ) )
            // InternalKap.g:1633:1: ( ';' )
            {
            // InternalKap.g:1633:1: ( ';' )
            // InternalKap.g:1634:2: ';'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getSemicolonKeyword_0_2_0()); 
            }
            match(input,31,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getSemicolonKeyword_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0_2__0__Impl"


    // $ANTLR start "rule__Body__Group_0_2__1"
    // InternalKap.g:1643:1: rule__Body__Group_0_2__1 : rule__Body__Group_0_2__1__Impl ;
    public final void rule__Body__Group_0_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1647:1: ( rule__Body__Group_0_2__1__Impl )
            // InternalKap.g:1648:2: rule__Body__Group_0_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Body__Group_0_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0_2__1"


    // $ANTLR start "rule__Body__Group_0_2__1__Impl"
    // InternalKap.g:1654:1: rule__Body__Group_0_2__1__Impl : ( ( rule__Body__ListAssignment_0_2_1 ) ) ;
    public final void rule__Body__Group_0_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1658:1: ( ( ( rule__Body__ListAssignment_0_2_1 ) ) )
            // InternalKap.g:1659:1: ( ( rule__Body__ListAssignment_0_2_1 ) )
            {
            // InternalKap.g:1659:1: ( ( rule__Body__ListAssignment_0_2_1 ) )
            // InternalKap.g:1660:2: ( rule__Body__ListAssignment_0_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getListAssignment_0_2_1()); 
            }
            // InternalKap.g:1661:2: ( rule__Body__ListAssignment_0_2_1 )
            // InternalKap.g:1661:3: rule__Body__ListAssignment_0_2_1
            {
            pushFollow(FOLLOW_2);
            rule__Body__ListAssignment_0_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getListAssignment_0_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0_2__1__Impl"


    // $ANTLR start "rule__Body__Group_1__0"
    // InternalKap.g:1670:1: rule__Body__Group_1__0 : rule__Body__Group_1__0__Impl rule__Body__Group_1__1 ;
    public final void rule__Body__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1674:1: ( rule__Body__Group_1__0__Impl rule__Body__Group_1__1 )
            // InternalKap.g:1675:2: rule__Body__Group_1__0__Impl rule__Body__Group_1__1
            {
            pushFollow(FOLLOW_18);
            rule__Body__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Body__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1__0"


    // $ANTLR start "rule__Body__Group_1__0__Impl"
    // InternalKap.g:1682:1: rule__Body__Group_1__0__Impl : ( ( rule__Body__IsgroupAssignment_1_0 ) ) ;
    public final void rule__Body__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1686:1: ( ( ( rule__Body__IsgroupAssignment_1_0 ) ) )
            // InternalKap.g:1687:1: ( ( rule__Body__IsgroupAssignment_1_0 ) )
            {
            // InternalKap.g:1687:1: ( ( rule__Body__IsgroupAssignment_1_0 ) )
            // InternalKap.g:1688:2: ( rule__Body__IsgroupAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getIsgroupAssignment_1_0()); 
            }
            // InternalKap.g:1689:2: ( rule__Body__IsgroupAssignment_1_0 )
            // InternalKap.g:1689:3: rule__Body__IsgroupAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Body__IsgroupAssignment_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getIsgroupAssignment_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1__0__Impl"


    // $ANTLR start "rule__Body__Group_1__1"
    // InternalKap.g:1697:1: rule__Body__Group_1__1 : rule__Body__Group_1__1__Impl rule__Body__Group_1__2 ;
    public final void rule__Body__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1701:1: ( rule__Body__Group_1__1__Impl rule__Body__Group_1__2 )
            // InternalKap.g:1702:2: rule__Body__Group_1__1__Impl rule__Body__Group_1__2
            {
            pushFollow(FOLLOW_18);
            rule__Body__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Body__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1__1"


    // $ANTLR start "rule__Body__Group_1__1__Impl"
    // InternalKap.g:1709:1: rule__Body__Group_1__1__Impl : ( ( rule__Body__Group_1_1__0 )? ) ;
    public final void rule__Body__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1713:1: ( ( ( rule__Body__Group_1_1__0 )? ) )
            // InternalKap.g:1714:1: ( ( rule__Body__Group_1_1__0 )? )
            {
            // InternalKap.g:1714:1: ( ( rule__Body__Group_1_1__0 )? )
            // InternalKap.g:1715:2: ( rule__Body__Group_1_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getGroup_1_1()); 
            }
            // InternalKap.g:1716:2: ( rule__Body__Group_1_1__0 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_LOWERCASE_ID||LA21_0==RULE_EMBEDDEDTEXT||LA21_0==27) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalKap.g:1716:3: rule__Body__Group_1_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Body__Group_1_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getGroup_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1__1__Impl"


    // $ANTLR start "rule__Body__Group_1__2"
    // InternalKap.g:1724:1: rule__Body__Group_1__2 : rule__Body__Group_1__2__Impl ;
    public final void rule__Body__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1728:1: ( rule__Body__Group_1__2__Impl )
            // InternalKap.g:1729:2: rule__Body__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Body__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1__2"


    // $ANTLR start "rule__Body__Group_1__2__Impl"
    // InternalKap.g:1735:1: rule__Body__Group_1__2__Impl : ( ')' ) ;
    public final void rule__Body__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1739:1: ( ( ')' ) )
            // InternalKap.g:1740:1: ( ')' )
            {
            // InternalKap.g:1740:1: ( ')' )
            // InternalKap.g:1741:2: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getRightParenthesisKeyword_1_2()); 
            }
            match(input,28,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getRightParenthesisKeyword_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1__2__Impl"


    // $ANTLR start "rule__Body__Group_1_1__0"
    // InternalKap.g:1751:1: rule__Body__Group_1_1__0 : rule__Body__Group_1_1__0__Impl rule__Body__Group_1_1__1 ;
    public final void rule__Body__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1755:1: ( rule__Body__Group_1_1__0__Impl rule__Body__Group_1_1__1 )
            // InternalKap.g:1756:2: rule__Body__Group_1_1__0__Impl rule__Body__Group_1_1__1
            {
            pushFollow(FOLLOW_16);
            rule__Body__Group_1_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Body__Group_1_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1_1__0"


    // $ANTLR start "rule__Body__Group_1_1__0__Impl"
    // InternalKap.g:1763:1: rule__Body__Group_1_1__0__Impl : ( ( rule__Body__GroupAssignment_1_1_0 ) ) ;
    public final void rule__Body__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1767:1: ( ( ( rule__Body__GroupAssignment_1_1_0 ) ) )
            // InternalKap.g:1768:1: ( ( rule__Body__GroupAssignment_1_1_0 ) )
            {
            // InternalKap.g:1768:1: ( ( rule__Body__GroupAssignment_1_1_0 ) )
            // InternalKap.g:1769:2: ( rule__Body__GroupAssignment_1_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getGroupAssignment_1_1_0()); 
            }
            // InternalKap.g:1770:2: ( rule__Body__GroupAssignment_1_1_0 )
            // InternalKap.g:1770:3: rule__Body__GroupAssignment_1_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Body__GroupAssignment_1_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getGroupAssignment_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1_1__0__Impl"


    // $ANTLR start "rule__Body__Group_1_1__1"
    // InternalKap.g:1778:1: rule__Body__Group_1_1__1 : rule__Body__Group_1_1__1__Impl ;
    public final void rule__Body__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1782:1: ( rule__Body__Group_1_1__1__Impl )
            // InternalKap.g:1783:2: rule__Body__Group_1_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Body__Group_1_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1_1__1"


    // $ANTLR start "rule__Body__Group_1_1__1__Impl"
    // InternalKap.g:1789:1: rule__Body__Group_1_1__1__Impl : ( ( rule__Body__Group_1_1_1__0 )* ) ;
    public final void rule__Body__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1793:1: ( ( ( rule__Body__Group_1_1_1__0 )* ) )
            // InternalKap.g:1794:1: ( ( rule__Body__Group_1_1_1__0 )* )
            {
            // InternalKap.g:1794:1: ( ( rule__Body__Group_1_1_1__0 )* )
            // InternalKap.g:1795:2: ( rule__Body__Group_1_1_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getGroup_1_1_1()); 
            }
            // InternalKap.g:1796:2: ( rule__Body__Group_1_1_1__0 )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==31) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalKap.g:1796:3: rule__Body__Group_1_1_1__0
            	    {
            	    pushFollow(FOLLOW_17);
            	    rule__Body__Group_1_1_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getGroup_1_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1_1__1__Impl"


    // $ANTLR start "rule__Body__Group_1_1_1__0"
    // InternalKap.g:1805:1: rule__Body__Group_1_1_1__0 : rule__Body__Group_1_1_1__0__Impl rule__Body__Group_1_1_1__1 ;
    public final void rule__Body__Group_1_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1809:1: ( rule__Body__Group_1_1_1__0__Impl rule__Body__Group_1_1_1__1 )
            // InternalKap.g:1810:2: rule__Body__Group_1_1_1__0__Impl rule__Body__Group_1_1_1__1
            {
            pushFollow(FOLLOW_7);
            rule__Body__Group_1_1_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Body__Group_1_1_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1_1_1__0"


    // $ANTLR start "rule__Body__Group_1_1_1__0__Impl"
    // InternalKap.g:1817:1: rule__Body__Group_1_1_1__0__Impl : ( ';' ) ;
    public final void rule__Body__Group_1_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1821:1: ( ( ';' ) )
            // InternalKap.g:1822:1: ( ';' )
            {
            // InternalKap.g:1822:1: ( ';' )
            // InternalKap.g:1823:2: ';'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getSemicolonKeyword_1_1_1_0()); 
            }
            match(input,31,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getSemicolonKeyword_1_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1_1_1__0__Impl"


    // $ANTLR start "rule__Body__Group_1_1_1__1"
    // InternalKap.g:1832:1: rule__Body__Group_1_1_1__1 : rule__Body__Group_1_1_1__1__Impl ;
    public final void rule__Body__Group_1_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1836:1: ( rule__Body__Group_1_1_1__1__Impl )
            // InternalKap.g:1837:2: rule__Body__Group_1_1_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Body__Group_1_1_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1_1_1__1"


    // $ANTLR start "rule__Body__Group_1_1_1__1__Impl"
    // InternalKap.g:1843:1: rule__Body__Group_1_1_1__1__Impl : ( ( rule__Body__GroupAssignment_1_1_1_1 ) ) ;
    public final void rule__Body__Group_1_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1847:1: ( ( ( rule__Body__GroupAssignment_1_1_1_1 ) ) )
            // InternalKap.g:1848:1: ( ( rule__Body__GroupAssignment_1_1_1_1 ) )
            {
            // InternalKap.g:1848:1: ( ( rule__Body__GroupAssignment_1_1_1_1 ) )
            // InternalKap.g:1849:2: ( rule__Body__GroupAssignment_1_1_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getGroupAssignment_1_1_1_1()); 
            }
            // InternalKap.g:1850:2: ( rule__Body__GroupAssignment_1_1_1_1 )
            // InternalKap.g:1850:3: rule__Body__GroupAssignment_1_1_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Body__GroupAssignment_1_1_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getGroupAssignment_1_1_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1_1_1__1__Impl"


    // $ANTLR start "rule__Statement__Group_2__0"
    // InternalKap.g:1859:1: rule__Statement__Group_2__0 : rule__Statement__Group_2__0__Impl rule__Statement__Group_2__1 ;
    public final void rule__Statement__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1863:1: ( rule__Statement__Group_2__0__Impl rule__Statement__Group_2__1 )
            // InternalKap.g:1864:2: rule__Statement__Group_2__0__Impl rule__Statement__Group_2__1
            {
            pushFollow(FOLLOW_5);
            rule__Statement__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Statement__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_2__0"


    // $ANTLR start "rule__Statement__Group_2__0__Impl"
    // InternalKap.g:1871:1: rule__Statement__Group_2__0__Impl : ( '(' ) ;
    public final void rule__Statement__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1875:1: ( ( '(' ) )
            // InternalKap.g:1876:1: ( '(' )
            {
            // InternalKap.g:1876:1: ( '(' )
            // InternalKap.g:1877:2: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getLeftParenthesisKeyword_2_0()); 
            }
            match(input,27,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getLeftParenthesisKeyword_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_2__0__Impl"


    // $ANTLR start "rule__Statement__Group_2__1"
    // InternalKap.g:1886:1: rule__Statement__Group_2__1 : rule__Statement__Group_2__1__Impl rule__Statement__Group_2__2 ;
    public final void rule__Statement__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1890:1: ( rule__Statement__Group_2__1__Impl rule__Statement__Group_2__2 )
            // InternalKap.g:1891:2: rule__Statement__Group_2__1__Impl rule__Statement__Group_2__2
            {
            pushFollow(FOLLOW_9);
            rule__Statement__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Statement__Group_2__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_2__1"


    // $ANTLR start "rule__Statement__Group_2__1__Impl"
    // InternalKap.g:1898:1: rule__Statement__Group_2__1__Impl : ( ( rule__Statement__GroupAssignment_2_1 ) ) ;
    public final void rule__Statement__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1902:1: ( ( ( rule__Statement__GroupAssignment_2_1 ) ) )
            // InternalKap.g:1903:1: ( ( rule__Statement__GroupAssignment_2_1 ) )
            {
            // InternalKap.g:1903:1: ( ( rule__Statement__GroupAssignment_2_1 ) )
            // InternalKap.g:1904:2: ( rule__Statement__GroupAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getGroupAssignment_2_1()); 
            }
            // InternalKap.g:1905:2: ( rule__Statement__GroupAssignment_2_1 )
            // InternalKap.g:1905:3: rule__Statement__GroupAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__Statement__GroupAssignment_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getGroupAssignment_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_2__1__Impl"


    // $ANTLR start "rule__Statement__Group_2__2"
    // InternalKap.g:1913:1: rule__Statement__Group_2__2 : rule__Statement__Group_2__2__Impl rule__Statement__Group_2__3 ;
    public final void rule__Statement__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1917:1: ( rule__Statement__Group_2__2__Impl rule__Statement__Group_2__3 )
            // InternalKap.g:1918:2: rule__Statement__Group_2__2__Impl rule__Statement__Group_2__3
            {
            pushFollow(FOLLOW_9);
            rule__Statement__Group_2__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Statement__Group_2__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_2__2"


    // $ANTLR start "rule__Statement__Group_2__2__Impl"
    // InternalKap.g:1925:1: rule__Statement__Group_2__2__Impl : ( ( rule__Statement__GroupAssignment_2_2 )* ) ;
    public final void rule__Statement__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1929:1: ( ( ( rule__Statement__GroupAssignment_2_2 )* ) )
            // InternalKap.g:1930:1: ( ( rule__Statement__GroupAssignment_2_2 )* )
            {
            // InternalKap.g:1930:1: ( ( rule__Statement__GroupAssignment_2_2 )* )
            // InternalKap.g:1931:2: ( rule__Statement__GroupAssignment_2_2 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getGroupAssignment_2_2()); 
            }
            // InternalKap.g:1932:2: ( rule__Statement__GroupAssignment_2_2 )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==RULE_LOWERCASE_ID) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalKap.g:1932:3: rule__Statement__GroupAssignment_2_2
            	    {
            	    pushFollow(FOLLOW_19);
            	    rule__Statement__GroupAssignment_2_2();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getGroupAssignment_2_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_2__2__Impl"


    // $ANTLR start "rule__Statement__Group_2__3"
    // InternalKap.g:1940:1: rule__Statement__Group_2__3 : rule__Statement__Group_2__3__Impl ;
    public final void rule__Statement__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1944:1: ( rule__Statement__Group_2__3__Impl )
            // InternalKap.g:1945:2: rule__Statement__Group_2__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Statement__Group_2__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_2__3"


    // $ANTLR start "rule__Statement__Group_2__3__Impl"
    // InternalKap.g:1951:1: rule__Statement__Group_2__3__Impl : ( ')' ) ;
    public final void rule__Statement__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1955:1: ( ( ')' ) )
            // InternalKap.g:1956:1: ( ')' )
            {
            // InternalKap.g:1956:1: ( ')' )
            // InternalKap.g:1957:2: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getRightParenthesisKeyword_2_3()); 
            }
            match(input,28,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getRightParenthesisKeyword_2_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_2__3__Impl"


    // $ANTLR start "rule__Call__Group__0"
    // InternalKap.g:1967:1: rule__Call__Group__0 : rule__Call__Group__0__Impl rule__Call__Group__1 ;
    public final void rule__Call__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1971:1: ( rule__Call__Group__0__Impl rule__Call__Group__1 )
            // InternalKap.g:1972:2: rule__Call__Group__0__Impl rule__Call__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__Call__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Call__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group__0"


    // $ANTLR start "rule__Call__Group__0__Impl"
    // InternalKap.g:1979:1: rule__Call__Group__0__Impl : ( ( rule__Call__NameAssignment_0 ) ) ;
    public final void rule__Call__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1983:1: ( ( ( rule__Call__NameAssignment_0 ) ) )
            // InternalKap.g:1984:1: ( ( rule__Call__NameAssignment_0 ) )
            {
            // InternalKap.g:1984:1: ( ( rule__Call__NameAssignment_0 ) )
            // InternalKap.g:1985:2: ( rule__Call__NameAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getNameAssignment_0()); 
            }
            // InternalKap.g:1986:2: ( rule__Call__NameAssignment_0 )
            // InternalKap.g:1986:3: rule__Call__NameAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__Call__NameAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getNameAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group__0__Impl"


    // $ANTLR start "rule__Call__Group__1"
    // InternalKap.g:1994:1: rule__Call__Group__1 : rule__Call__Group__1__Impl rule__Call__Group__2 ;
    public final void rule__Call__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1998:1: ( rule__Call__Group__1__Impl rule__Call__Group__2 )
            // InternalKap.g:1999:2: rule__Call__Group__1__Impl rule__Call__Group__2
            {
            pushFollow(FOLLOW_6);
            rule__Call__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Call__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group__1"


    // $ANTLR start "rule__Call__Group__1__Impl"
    // InternalKap.g:2006:1: rule__Call__Group__1__Impl : ( ( rule__Call__Group_1__0 )? ) ;
    public final void rule__Call__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2010:1: ( ( ( rule__Call__Group_1__0 )? ) )
            // InternalKap.g:2011:1: ( ( rule__Call__Group_1__0 )? )
            {
            // InternalKap.g:2011:1: ( ( rule__Call__Group_1__0 )? )
            // InternalKap.g:2012:2: ( rule__Call__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getGroup_1()); 
            }
            // InternalKap.g:2013:2: ( rule__Call__Group_1__0 )?
            int alt24=2;
            alt24 = dfa24.predict(input);
            switch (alt24) {
                case 1 :
                    // InternalKap.g:2013:3: rule__Call__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Call__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group__1__Impl"


    // $ANTLR start "rule__Call__Group__2"
    // InternalKap.g:2021:1: rule__Call__Group__2 : rule__Call__Group__2__Impl ;
    public final void rule__Call__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2025:1: ( rule__Call__Group__2__Impl )
            // InternalKap.g:2026:2: rule__Call__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Call__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group__2"


    // $ANTLR start "rule__Call__Group__2__Impl"
    // InternalKap.g:2032:1: rule__Call__Group__2__Impl : ( ( rule__Call__Group_2__0 )? ) ;
    public final void rule__Call__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2036:1: ( ( ( rule__Call__Group_2__0 )? ) )
            // InternalKap.g:2037:1: ( ( rule__Call__Group_2__0 )? )
            {
            // InternalKap.g:2037:1: ( ( rule__Call__Group_2__0 )? )
            // InternalKap.g:2038:2: ( rule__Call__Group_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getGroup_2()); 
            }
            // InternalKap.g:2039:2: ( rule__Call__Group_2__0 )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==26) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalKap.g:2039:3: rule__Call__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Call__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getGroup_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group__2__Impl"


    // $ANTLR start "rule__Call__Group_1__0"
    // InternalKap.g:2048:1: rule__Call__Group_1__0 : rule__Call__Group_1__0__Impl rule__Call__Group_1__1 ;
    public final void rule__Call__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2052:1: ( rule__Call__Group_1__0__Impl rule__Call__Group_1__1 )
            // InternalKap.g:2053:2: rule__Call__Group_1__0__Impl rule__Call__Group_1__1
            {
            pushFollow(FOLLOW_20);
            rule__Call__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Call__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_1__0"


    // $ANTLR start "rule__Call__Group_1__0__Impl"
    // InternalKap.g:2060:1: rule__Call__Group_1__0__Impl : ( '(' ) ;
    public final void rule__Call__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2064:1: ( ( '(' ) )
            // InternalKap.g:2065:1: ( '(' )
            {
            // InternalKap.g:2065:1: ( '(' )
            // InternalKap.g:2066:2: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getLeftParenthesisKeyword_1_0()); 
            }
            match(input,27,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getLeftParenthesisKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_1__0__Impl"


    // $ANTLR start "rule__Call__Group_1__1"
    // InternalKap.g:2075:1: rule__Call__Group_1__1 : rule__Call__Group_1__1__Impl rule__Call__Group_1__2 ;
    public final void rule__Call__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2079:1: ( rule__Call__Group_1__1__Impl rule__Call__Group_1__2 )
            // InternalKap.g:2080:2: rule__Call__Group_1__1__Impl rule__Call__Group_1__2
            {
            pushFollow(FOLLOW_20);
            rule__Call__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Call__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_1__1"


    // $ANTLR start "rule__Call__Group_1__1__Impl"
    // InternalKap.g:2087:1: rule__Call__Group_1__1__Impl : ( ( rule__Call__ParametersAssignment_1_1 )? ) ;
    public final void rule__Call__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2091:1: ( ( ( rule__Call__ParametersAssignment_1_1 )? ) )
            // InternalKap.g:2092:1: ( ( rule__Call__ParametersAssignment_1_1 )? )
            {
            // InternalKap.g:2092:1: ( ( rule__Call__ParametersAssignment_1_1 )? )
            // InternalKap.g:2093:2: ( rule__Call__ParametersAssignment_1_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getParametersAssignment_1_1()); 
            }
            // InternalKap.g:2094:2: ( rule__Call__ParametersAssignment_1_1 )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( ((LA26_0>=RULE_LOWERCASE_ID && LA26_0<=RULE_STRING)||LA26_0==RULE_INT||(LA26_0>=17 && LA26_0<=19)||LA26_0==33) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalKap.g:2094:3: rule__Call__ParametersAssignment_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Call__ParametersAssignment_1_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getParametersAssignment_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_1__1__Impl"


    // $ANTLR start "rule__Call__Group_1__2"
    // InternalKap.g:2102:1: rule__Call__Group_1__2 : rule__Call__Group_1__2__Impl ;
    public final void rule__Call__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2106:1: ( rule__Call__Group_1__2__Impl )
            // InternalKap.g:2107:2: rule__Call__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Call__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_1__2"


    // $ANTLR start "rule__Call__Group_1__2__Impl"
    // InternalKap.g:2113:1: rule__Call__Group_1__2__Impl : ( ')' ) ;
    public final void rule__Call__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2117:1: ( ( ')' ) )
            // InternalKap.g:2118:1: ( ')' )
            {
            // InternalKap.g:2118:1: ( ')' )
            // InternalKap.g:2119:2: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getRightParenthesisKeyword_1_2()); 
            }
            match(input,28,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getRightParenthesisKeyword_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_1__2__Impl"


    // $ANTLR start "rule__Call__Group_2__0"
    // InternalKap.g:2129:1: rule__Call__Group_2__0 : rule__Call__Group_2__0__Impl rule__Call__Group_2__1 ;
    public final void rule__Call__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2133:1: ( rule__Call__Group_2__0__Impl rule__Call__Group_2__1 )
            // InternalKap.g:2134:2: rule__Call__Group_2__0__Impl rule__Call__Group_2__1
            {
            pushFollow(FOLLOW_21);
            rule__Call__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Call__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_2__0"


    // $ANTLR start "rule__Call__Group_2__0__Impl"
    // InternalKap.g:2141:1: rule__Call__Group_2__0__Impl : ( ':' ) ;
    public final void rule__Call__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2145:1: ( ( ':' ) )
            // InternalKap.g:2146:1: ( ':' )
            {
            // InternalKap.g:2146:1: ( ':' )
            // InternalKap.g:2147:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getColonKeyword_2_0()); 
            }
            match(input,26,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getColonKeyword_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_2__0__Impl"


    // $ANTLR start "rule__Call__Group_2__1"
    // InternalKap.g:2156:1: rule__Call__Group_2__1 : rule__Call__Group_2__1__Impl ;
    public final void rule__Call__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2160:1: ( rule__Call__Group_2__1__Impl )
            // InternalKap.g:2161:2: rule__Call__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Call__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_2__1"


    // $ANTLR start "rule__Call__Group_2__1__Impl"
    // InternalKap.g:2167:1: rule__Call__Group_2__1__Impl : ( ( rule__Call__ActionsAssignment_2_1 ) ) ;
    public final void rule__Call__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2171:1: ( ( ( rule__Call__ActionsAssignment_2_1 ) ) )
            // InternalKap.g:2172:1: ( ( rule__Call__ActionsAssignment_2_1 ) )
            {
            // InternalKap.g:2172:1: ( ( rule__Call__ActionsAssignment_2_1 ) )
            // InternalKap.g:2173:2: ( rule__Call__ActionsAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getActionsAssignment_2_1()); 
            }
            // InternalKap.g:2174:2: ( rule__Call__ActionsAssignment_2_1 )
            // InternalKap.g:2174:3: rule__Call__ActionsAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__Call__ActionsAssignment_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getActionsAssignment_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_2__1__Impl"


    // $ANTLR start "rule__Actions__Group_2__0"
    // InternalKap.g:2183:1: rule__Actions__Group_2__0 : rule__Actions__Group_2__0__Impl rule__Actions__Group_2__1 ;
    public final void rule__Actions__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2187:1: ( rule__Actions__Group_2__0__Impl rule__Actions__Group_2__1 )
            // InternalKap.g:2188:2: rule__Actions__Group_2__0__Impl rule__Actions__Group_2__1
            {
            pushFollow(FOLLOW_22);
            rule__Actions__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Actions__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_2__0"


    // $ANTLR start "rule__Actions__Group_2__0__Impl"
    // InternalKap.g:2195:1: rule__Actions__Group_2__0__Impl : ( '(' ) ;
    public final void rule__Actions__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2199:1: ( ( '(' ) )
            // InternalKap.g:2200:1: ( '(' )
            {
            // InternalKap.g:2200:1: ( '(' )
            // InternalKap.g:2201:2: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getLeftParenthesisKeyword_2_0()); 
            }
            match(input,27,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getLeftParenthesisKeyword_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_2__0__Impl"


    // $ANTLR start "rule__Actions__Group_2__1"
    // InternalKap.g:2210:1: rule__Actions__Group_2__1 : rule__Actions__Group_2__1__Impl rule__Actions__Group_2__2 ;
    public final void rule__Actions__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2214:1: ( rule__Actions__Group_2__1__Impl rule__Actions__Group_2__2 )
            // InternalKap.g:2215:2: rule__Actions__Group_2__1__Impl rule__Actions__Group_2__2
            {
            pushFollow(FOLLOW_23);
            rule__Actions__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Actions__Group_2__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_2__1"


    // $ANTLR start "rule__Actions__Group_2__1__Impl"
    // InternalKap.g:2222:1: rule__Actions__Group_2__1__Impl : ( ( rule__Actions__MatchesAssignment_2_1 ) ) ;
    public final void rule__Actions__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2226:1: ( ( ( rule__Actions__MatchesAssignment_2_1 ) ) )
            // InternalKap.g:2227:1: ( ( rule__Actions__MatchesAssignment_2_1 ) )
            {
            // InternalKap.g:2227:1: ( ( rule__Actions__MatchesAssignment_2_1 ) )
            // InternalKap.g:2228:2: ( rule__Actions__MatchesAssignment_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getMatchesAssignment_2_1()); 
            }
            // InternalKap.g:2229:2: ( rule__Actions__MatchesAssignment_2_1 )
            // InternalKap.g:2229:3: rule__Actions__MatchesAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__Actions__MatchesAssignment_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getMatchesAssignment_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_2__1__Impl"


    // $ANTLR start "rule__Actions__Group_2__2"
    // InternalKap.g:2237:1: rule__Actions__Group_2__2 : rule__Actions__Group_2__2__Impl rule__Actions__Group_2__3 ;
    public final void rule__Actions__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2241:1: ( rule__Actions__Group_2__2__Impl rule__Actions__Group_2__3 )
            // InternalKap.g:2242:2: rule__Actions__Group_2__2__Impl rule__Actions__Group_2__3
            {
            pushFollow(FOLLOW_23);
            rule__Actions__Group_2__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Actions__Group_2__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_2__2"


    // $ANTLR start "rule__Actions__Group_2__2__Impl"
    // InternalKap.g:2249:1: rule__Actions__Group_2__2__Impl : ( ( rule__Actions__MatchesAssignment_2_2 )* ) ;
    public final void rule__Actions__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2253:1: ( ( ( rule__Actions__MatchesAssignment_2_2 )* ) )
            // InternalKap.g:2254:1: ( ( rule__Actions__MatchesAssignment_2_2 )* )
            {
            // InternalKap.g:2254:1: ( ( rule__Actions__MatchesAssignment_2_2 )* )
            // InternalKap.g:2255:2: ( rule__Actions__MatchesAssignment_2_2 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getMatchesAssignment_2_2()); 
            }
            // InternalKap.g:2256:2: ( rule__Actions__MatchesAssignment_2_2 )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( ((LA27_0>=RULE_LOWERCASE_ID && LA27_0<=RULE_OBSERVABLE)||LA27_0==RULE_STRING||(LA27_0>=RULE_REGEXP && LA27_0<=RULE_INT)||(LA27_0>=17 && LA27_0<=19)||LA27_0==27||LA27_0==33) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalKap.g:2256:3: rule__Actions__MatchesAssignment_2_2
            	    {
            	    pushFollow(FOLLOW_24);
            	    rule__Actions__MatchesAssignment_2_2();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getMatchesAssignment_2_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_2__2__Impl"


    // $ANTLR start "rule__Actions__Group_2__3"
    // InternalKap.g:2264:1: rule__Actions__Group_2__3 : rule__Actions__Group_2__3__Impl ;
    public final void rule__Actions__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2268:1: ( rule__Actions__Group_2__3__Impl )
            // InternalKap.g:2269:2: rule__Actions__Group_2__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Actions__Group_2__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_2__3"


    // $ANTLR start "rule__Actions__Group_2__3__Impl"
    // InternalKap.g:2275:1: rule__Actions__Group_2__3__Impl : ( ')' ) ;
    public final void rule__Actions__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2279:1: ( ( ')' ) )
            // InternalKap.g:2280:1: ( ')' )
            {
            // InternalKap.g:2280:1: ( ')' )
            // InternalKap.g:2281:2: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getRightParenthesisKeyword_2_3()); 
            }
            match(input,28,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getRightParenthesisKeyword_2_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_2__3__Impl"


    // $ANTLR start "rule__Match__Group_0__0"
    // InternalKap.g:2291:1: rule__Match__Group_0__0 : rule__Match__Group_0__0__Impl rule__Match__Group_0__1 ;
    public final void rule__Match__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2295:1: ( rule__Match__Group_0__0__Impl rule__Match__Group_0__1 )
            // InternalKap.g:2296:2: rule__Match__Group_0__0__Impl rule__Match__Group_0__1
            {
            pushFollow(FOLLOW_25);
            rule__Match__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_0__0"


    // $ANTLR start "rule__Match__Group_0__0__Impl"
    // InternalKap.g:2303:1: rule__Match__Group_0__0__Impl : ( ( rule__Match__IdAssignment_0_0 ) ) ;
    public final void rule__Match__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2307:1: ( ( ( rule__Match__IdAssignment_0_0 ) ) )
            // InternalKap.g:2308:1: ( ( rule__Match__IdAssignment_0_0 ) )
            {
            // InternalKap.g:2308:1: ( ( rule__Match__IdAssignment_0_0 ) )
            // InternalKap.g:2309:2: ( rule__Match__IdAssignment_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getIdAssignment_0_0()); 
            }
            // InternalKap.g:2310:2: ( rule__Match__IdAssignment_0_0 )
            // InternalKap.g:2310:3: rule__Match__IdAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Match__IdAssignment_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getIdAssignment_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_0__0__Impl"


    // $ANTLR start "rule__Match__Group_0__1"
    // InternalKap.g:2318:1: rule__Match__Group_0__1 : rule__Match__Group_0__1__Impl rule__Match__Group_0__2 ;
    public final void rule__Match__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2322:1: ( rule__Match__Group_0__1__Impl rule__Match__Group_0__2 )
            // InternalKap.g:2323:2: rule__Match__Group_0__1__Impl rule__Match__Group_0__2
            {
            pushFollow(FOLLOW_7);
            rule__Match__Group_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_0__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_0__1"


    // $ANTLR start "rule__Match__Group_0__1__Impl"
    // InternalKap.g:2330:1: rule__Match__Group_0__1__Impl : ( '->' ) ;
    public final void rule__Match__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2334:1: ( ( '->' ) )
            // InternalKap.g:2335:1: ( '->' )
            {
            // InternalKap.g:2335:1: ( '->' )
            // InternalKap.g:2336:2: '->'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_0_1()); 
            }
            match(input,32,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_0__1__Impl"


    // $ANTLR start "rule__Match__Group_0__2"
    // InternalKap.g:2345:1: rule__Match__Group_0__2 : rule__Match__Group_0__2__Impl ;
    public final void rule__Match__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2349:1: ( rule__Match__Group_0__2__Impl )
            // InternalKap.g:2350:2: rule__Match__Group_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Match__Group_0__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_0__2"


    // $ANTLR start "rule__Match__Group_0__2__Impl"
    // InternalKap.g:2356:1: rule__Match__Group_0__2__Impl : ( ( rule__Match__BodyAssignment_0_2 ) ) ;
    public final void rule__Match__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2360:1: ( ( ( rule__Match__BodyAssignment_0_2 ) ) )
            // InternalKap.g:2361:1: ( ( rule__Match__BodyAssignment_0_2 ) )
            {
            // InternalKap.g:2361:1: ( ( rule__Match__BodyAssignment_0_2 ) )
            // InternalKap.g:2362:2: ( rule__Match__BodyAssignment_0_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyAssignment_0_2()); 
            }
            // InternalKap.g:2363:2: ( rule__Match__BodyAssignment_0_2 )
            // InternalKap.g:2363:3: rule__Match__BodyAssignment_0_2
            {
            pushFollow(FOLLOW_2);
            rule__Match__BodyAssignment_0_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyAssignment_0_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_0__2__Impl"


    // $ANTLR start "rule__Match__Group_1__0"
    // InternalKap.g:2372:1: rule__Match__Group_1__0 : rule__Match__Group_1__0__Impl rule__Match__Group_1__1 ;
    public final void rule__Match__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2376:1: ( rule__Match__Group_1__0__Impl rule__Match__Group_1__1 )
            // InternalKap.g:2377:2: rule__Match__Group_1__0__Impl rule__Match__Group_1__1
            {
            pushFollow(FOLLOW_25);
            rule__Match__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_1__0"


    // $ANTLR start "rule__Match__Group_1__0__Impl"
    // InternalKap.g:2384:1: rule__Match__Group_1__0__Impl : ( ( rule__Match__RegexpAssignment_1_0 ) ) ;
    public final void rule__Match__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2388:1: ( ( ( rule__Match__RegexpAssignment_1_0 ) ) )
            // InternalKap.g:2389:1: ( ( rule__Match__RegexpAssignment_1_0 ) )
            {
            // InternalKap.g:2389:1: ( ( rule__Match__RegexpAssignment_1_0 ) )
            // InternalKap.g:2390:2: ( rule__Match__RegexpAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getRegexpAssignment_1_0()); 
            }
            // InternalKap.g:2391:2: ( rule__Match__RegexpAssignment_1_0 )
            // InternalKap.g:2391:3: rule__Match__RegexpAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Match__RegexpAssignment_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getRegexpAssignment_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_1__0__Impl"


    // $ANTLR start "rule__Match__Group_1__1"
    // InternalKap.g:2399:1: rule__Match__Group_1__1 : rule__Match__Group_1__1__Impl rule__Match__Group_1__2 ;
    public final void rule__Match__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2403:1: ( rule__Match__Group_1__1__Impl rule__Match__Group_1__2 )
            // InternalKap.g:2404:2: rule__Match__Group_1__1__Impl rule__Match__Group_1__2
            {
            pushFollow(FOLLOW_7);
            rule__Match__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_1__1"


    // $ANTLR start "rule__Match__Group_1__1__Impl"
    // InternalKap.g:2411:1: rule__Match__Group_1__1__Impl : ( '->' ) ;
    public final void rule__Match__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2415:1: ( ( '->' ) )
            // InternalKap.g:2416:1: ( '->' )
            {
            // InternalKap.g:2416:1: ( '->' )
            // InternalKap.g:2417:2: '->'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_1_1()); 
            }
            match(input,32,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_1__1__Impl"


    // $ANTLR start "rule__Match__Group_1__2"
    // InternalKap.g:2426:1: rule__Match__Group_1__2 : rule__Match__Group_1__2__Impl ;
    public final void rule__Match__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2430:1: ( rule__Match__Group_1__2__Impl )
            // InternalKap.g:2431:2: rule__Match__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Match__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_1__2"


    // $ANTLR start "rule__Match__Group_1__2__Impl"
    // InternalKap.g:2437:1: rule__Match__Group_1__2__Impl : ( ( rule__Match__BodyAssignment_1_2 ) ) ;
    public final void rule__Match__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2441:1: ( ( ( rule__Match__BodyAssignment_1_2 ) ) )
            // InternalKap.g:2442:1: ( ( rule__Match__BodyAssignment_1_2 ) )
            {
            // InternalKap.g:2442:1: ( ( rule__Match__BodyAssignment_1_2 ) )
            // InternalKap.g:2443:2: ( rule__Match__BodyAssignment_1_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyAssignment_1_2()); 
            }
            // InternalKap.g:2444:2: ( rule__Match__BodyAssignment_1_2 )
            // InternalKap.g:2444:3: rule__Match__BodyAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Match__BodyAssignment_1_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyAssignment_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_1__2__Impl"


    // $ANTLR start "rule__Match__Group_2__0"
    // InternalKap.g:2453:1: rule__Match__Group_2__0 : rule__Match__Group_2__0__Impl rule__Match__Group_2__1 ;
    public final void rule__Match__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2457:1: ( rule__Match__Group_2__0__Impl rule__Match__Group_2__1 )
            // InternalKap.g:2458:2: rule__Match__Group_2__0__Impl rule__Match__Group_2__1
            {
            pushFollow(FOLLOW_25);
            rule__Match__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_2__0"


    // $ANTLR start "rule__Match__Group_2__0__Impl"
    // InternalKap.g:2465:1: rule__Match__Group_2__0__Impl : ( ( rule__Match__ObservableAssignment_2_0 ) ) ;
    public final void rule__Match__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2469:1: ( ( ( rule__Match__ObservableAssignment_2_0 ) ) )
            // InternalKap.g:2470:1: ( ( rule__Match__ObservableAssignment_2_0 ) )
            {
            // InternalKap.g:2470:1: ( ( rule__Match__ObservableAssignment_2_0 ) )
            // InternalKap.g:2471:2: ( rule__Match__ObservableAssignment_2_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getObservableAssignment_2_0()); 
            }
            // InternalKap.g:2472:2: ( rule__Match__ObservableAssignment_2_0 )
            // InternalKap.g:2472:3: rule__Match__ObservableAssignment_2_0
            {
            pushFollow(FOLLOW_2);
            rule__Match__ObservableAssignment_2_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getObservableAssignment_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_2__0__Impl"


    // $ANTLR start "rule__Match__Group_2__1"
    // InternalKap.g:2480:1: rule__Match__Group_2__1 : rule__Match__Group_2__1__Impl rule__Match__Group_2__2 ;
    public final void rule__Match__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2484:1: ( rule__Match__Group_2__1__Impl rule__Match__Group_2__2 )
            // InternalKap.g:2485:2: rule__Match__Group_2__1__Impl rule__Match__Group_2__2
            {
            pushFollow(FOLLOW_7);
            rule__Match__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_2__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_2__1"


    // $ANTLR start "rule__Match__Group_2__1__Impl"
    // InternalKap.g:2492:1: rule__Match__Group_2__1__Impl : ( '->' ) ;
    public final void rule__Match__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2496:1: ( ( '->' ) )
            // InternalKap.g:2497:1: ( '->' )
            {
            // InternalKap.g:2497:1: ( '->' )
            // InternalKap.g:2498:2: '->'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_2_1()); 
            }
            match(input,32,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_2__1__Impl"


    // $ANTLR start "rule__Match__Group_2__2"
    // InternalKap.g:2507:1: rule__Match__Group_2__2 : rule__Match__Group_2__2__Impl ;
    public final void rule__Match__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2511:1: ( rule__Match__Group_2__2__Impl )
            // InternalKap.g:2512:2: rule__Match__Group_2__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Match__Group_2__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_2__2"


    // $ANTLR start "rule__Match__Group_2__2__Impl"
    // InternalKap.g:2518:1: rule__Match__Group_2__2__Impl : ( ( rule__Match__BodyAssignment_2_2 ) ) ;
    public final void rule__Match__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2522:1: ( ( ( rule__Match__BodyAssignment_2_2 ) ) )
            // InternalKap.g:2523:1: ( ( rule__Match__BodyAssignment_2_2 ) )
            {
            // InternalKap.g:2523:1: ( ( rule__Match__BodyAssignment_2_2 ) )
            // InternalKap.g:2524:2: ( rule__Match__BodyAssignment_2_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyAssignment_2_2()); 
            }
            // InternalKap.g:2525:2: ( rule__Match__BodyAssignment_2_2 )
            // InternalKap.g:2525:3: rule__Match__BodyAssignment_2_2
            {
            pushFollow(FOLLOW_2);
            rule__Match__BodyAssignment_2_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyAssignment_2_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_2__2__Impl"


    // $ANTLR start "rule__Match__Group_3__0"
    // InternalKap.g:2534:1: rule__Match__Group_3__0 : rule__Match__Group_3__0__Impl rule__Match__Group_3__1 ;
    public final void rule__Match__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2538:1: ( rule__Match__Group_3__0__Impl rule__Match__Group_3__1 )
            // InternalKap.g:2539:2: rule__Match__Group_3__0__Impl rule__Match__Group_3__1
            {
            pushFollow(FOLLOW_25);
            rule__Match__Group_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_3__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_3__0"


    // $ANTLR start "rule__Match__Group_3__0__Impl"
    // InternalKap.g:2546:1: rule__Match__Group_3__0__Impl : ( ( rule__Match__LiteralAssignment_3_0 ) ) ;
    public final void rule__Match__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2550:1: ( ( ( rule__Match__LiteralAssignment_3_0 ) ) )
            // InternalKap.g:2551:1: ( ( rule__Match__LiteralAssignment_3_0 ) )
            {
            // InternalKap.g:2551:1: ( ( rule__Match__LiteralAssignment_3_0 ) )
            // InternalKap.g:2552:2: ( rule__Match__LiteralAssignment_3_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getLiteralAssignment_3_0()); 
            }
            // InternalKap.g:2553:2: ( rule__Match__LiteralAssignment_3_0 )
            // InternalKap.g:2553:3: rule__Match__LiteralAssignment_3_0
            {
            pushFollow(FOLLOW_2);
            rule__Match__LiteralAssignment_3_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getLiteralAssignment_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_3__0__Impl"


    // $ANTLR start "rule__Match__Group_3__1"
    // InternalKap.g:2561:1: rule__Match__Group_3__1 : rule__Match__Group_3__1__Impl rule__Match__Group_3__2 ;
    public final void rule__Match__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2565:1: ( rule__Match__Group_3__1__Impl rule__Match__Group_3__2 )
            // InternalKap.g:2566:2: rule__Match__Group_3__1__Impl rule__Match__Group_3__2
            {
            pushFollow(FOLLOW_7);
            rule__Match__Group_3__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_3__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_3__1"


    // $ANTLR start "rule__Match__Group_3__1__Impl"
    // InternalKap.g:2573:1: rule__Match__Group_3__1__Impl : ( '->' ) ;
    public final void rule__Match__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2577:1: ( ( '->' ) )
            // InternalKap.g:2578:1: ( '->' )
            {
            // InternalKap.g:2578:1: ( '->' )
            // InternalKap.g:2579:2: '->'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_3_1()); 
            }
            match(input,32,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_3_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_3__1__Impl"


    // $ANTLR start "rule__Match__Group_3__2"
    // InternalKap.g:2588:1: rule__Match__Group_3__2 : rule__Match__Group_3__2__Impl ;
    public final void rule__Match__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2592:1: ( rule__Match__Group_3__2__Impl )
            // InternalKap.g:2593:2: rule__Match__Group_3__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Match__Group_3__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_3__2"


    // $ANTLR start "rule__Match__Group_3__2__Impl"
    // InternalKap.g:2599:1: rule__Match__Group_3__2__Impl : ( ( rule__Match__BodyAssignment_3_2 ) ) ;
    public final void rule__Match__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2603:1: ( ( ( rule__Match__BodyAssignment_3_2 ) ) )
            // InternalKap.g:2604:1: ( ( rule__Match__BodyAssignment_3_2 ) )
            {
            // InternalKap.g:2604:1: ( ( rule__Match__BodyAssignment_3_2 ) )
            // InternalKap.g:2605:2: ( rule__Match__BodyAssignment_3_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyAssignment_3_2()); 
            }
            // InternalKap.g:2606:2: ( rule__Match__BodyAssignment_3_2 )
            // InternalKap.g:2606:3: rule__Match__BodyAssignment_3_2
            {
            pushFollow(FOLLOW_2);
            rule__Match__BodyAssignment_3_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyAssignment_3_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_3__2__Impl"


    // $ANTLR start "rule__Match__Group_4__0"
    // InternalKap.g:2615:1: rule__Match__Group_4__0 : rule__Match__Group_4__0__Impl rule__Match__Group_4__1 ;
    public final void rule__Match__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2619:1: ( rule__Match__Group_4__0__Impl rule__Match__Group_4__1 )
            // InternalKap.g:2620:2: rule__Match__Group_4__0__Impl rule__Match__Group_4__1
            {
            pushFollow(FOLLOW_25);
            rule__Match__Group_4__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_4__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_4__0"


    // $ANTLR start "rule__Match__Group_4__0__Impl"
    // InternalKap.g:2627:1: rule__Match__Group_4__0__Impl : ( ( rule__Match__TextAssignment_4_0 ) ) ;
    public final void rule__Match__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2631:1: ( ( ( rule__Match__TextAssignment_4_0 ) ) )
            // InternalKap.g:2632:1: ( ( rule__Match__TextAssignment_4_0 ) )
            {
            // InternalKap.g:2632:1: ( ( rule__Match__TextAssignment_4_0 ) )
            // InternalKap.g:2633:2: ( rule__Match__TextAssignment_4_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getTextAssignment_4_0()); 
            }
            // InternalKap.g:2634:2: ( rule__Match__TextAssignment_4_0 )
            // InternalKap.g:2634:3: rule__Match__TextAssignment_4_0
            {
            pushFollow(FOLLOW_2);
            rule__Match__TextAssignment_4_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getTextAssignment_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_4__0__Impl"


    // $ANTLR start "rule__Match__Group_4__1"
    // InternalKap.g:2642:1: rule__Match__Group_4__1 : rule__Match__Group_4__1__Impl rule__Match__Group_4__2 ;
    public final void rule__Match__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2646:1: ( rule__Match__Group_4__1__Impl rule__Match__Group_4__2 )
            // InternalKap.g:2647:2: rule__Match__Group_4__1__Impl rule__Match__Group_4__2
            {
            pushFollow(FOLLOW_7);
            rule__Match__Group_4__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_4__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_4__1"


    // $ANTLR start "rule__Match__Group_4__1__Impl"
    // InternalKap.g:2654:1: rule__Match__Group_4__1__Impl : ( '->' ) ;
    public final void rule__Match__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2658:1: ( ( '->' ) )
            // InternalKap.g:2659:1: ( '->' )
            {
            // InternalKap.g:2659:1: ( '->' )
            // InternalKap.g:2660:2: '->'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_4_1()); 
            }
            match(input,32,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_4_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_4__1__Impl"


    // $ANTLR start "rule__Match__Group_4__2"
    // InternalKap.g:2669:1: rule__Match__Group_4__2 : rule__Match__Group_4__2__Impl ;
    public final void rule__Match__Group_4__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2673:1: ( rule__Match__Group_4__2__Impl )
            // InternalKap.g:2674:2: rule__Match__Group_4__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Match__Group_4__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_4__2"


    // $ANTLR start "rule__Match__Group_4__2__Impl"
    // InternalKap.g:2680:1: rule__Match__Group_4__2__Impl : ( ( rule__Match__BodyAssignment_4_2 ) ) ;
    public final void rule__Match__Group_4__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2684:1: ( ( ( rule__Match__BodyAssignment_4_2 ) ) )
            // InternalKap.g:2685:1: ( ( rule__Match__BodyAssignment_4_2 ) )
            {
            // InternalKap.g:2685:1: ( ( rule__Match__BodyAssignment_4_2 ) )
            // InternalKap.g:2686:2: ( rule__Match__BodyAssignment_4_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyAssignment_4_2()); 
            }
            // InternalKap.g:2687:2: ( rule__Match__BodyAssignment_4_2 )
            // InternalKap.g:2687:3: rule__Match__BodyAssignment_4_2
            {
            pushFollow(FOLLOW_2);
            rule__Match__BodyAssignment_4_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyAssignment_4_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_4__2__Impl"


    // $ANTLR start "rule__Match__Group_5__0"
    // InternalKap.g:2696:1: rule__Match__Group_5__0 : rule__Match__Group_5__0__Impl rule__Match__Group_5__1 ;
    public final void rule__Match__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2700:1: ( rule__Match__Group_5__0__Impl rule__Match__Group_5__1 )
            // InternalKap.g:2701:2: rule__Match__Group_5__0__Impl rule__Match__Group_5__1
            {
            pushFollow(FOLLOW_25);
            rule__Match__Group_5__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_5__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_5__0"


    // $ANTLR start "rule__Match__Group_5__0__Impl"
    // InternalKap.g:2708:1: rule__Match__Group_5__0__Impl : ( ( rule__Match__ArgumentsAssignment_5_0 ) ) ;
    public final void rule__Match__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2712:1: ( ( ( rule__Match__ArgumentsAssignment_5_0 ) ) )
            // InternalKap.g:2713:1: ( ( rule__Match__ArgumentsAssignment_5_0 ) )
            {
            // InternalKap.g:2713:1: ( ( rule__Match__ArgumentsAssignment_5_0 ) )
            // InternalKap.g:2714:2: ( rule__Match__ArgumentsAssignment_5_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getArgumentsAssignment_5_0()); 
            }
            // InternalKap.g:2715:2: ( rule__Match__ArgumentsAssignment_5_0 )
            // InternalKap.g:2715:3: rule__Match__ArgumentsAssignment_5_0
            {
            pushFollow(FOLLOW_2);
            rule__Match__ArgumentsAssignment_5_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getArgumentsAssignment_5_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_5__0__Impl"


    // $ANTLR start "rule__Match__Group_5__1"
    // InternalKap.g:2723:1: rule__Match__Group_5__1 : rule__Match__Group_5__1__Impl rule__Match__Group_5__2 ;
    public final void rule__Match__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2727:1: ( rule__Match__Group_5__1__Impl rule__Match__Group_5__2 )
            // InternalKap.g:2728:2: rule__Match__Group_5__1__Impl rule__Match__Group_5__2
            {
            pushFollow(FOLLOW_7);
            rule__Match__Group_5__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_5__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_5__1"


    // $ANTLR start "rule__Match__Group_5__1__Impl"
    // InternalKap.g:2735:1: rule__Match__Group_5__1__Impl : ( '->' ) ;
    public final void rule__Match__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2739:1: ( ( '->' ) )
            // InternalKap.g:2740:1: ( '->' )
            {
            // InternalKap.g:2740:1: ( '->' )
            // InternalKap.g:2741:2: '->'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_5_1()); 
            }
            match(input,32,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_5_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_5__1__Impl"


    // $ANTLR start "rule__Match__Group_5__2"
    // InternalKap.g:2750:1: rule__Match__Group_5__2 : rule__Match__Group_5__2__Impl ;
    public final void rule__Match__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2754:1: ( rule__Match__Group_5__2__Impl )
            // InternalKap.g:2755:2: rule__Match__Group_5__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Match__Group_5__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_5__2"


    // $ANTLR start "rule__Match__Group_5__2__Impl"
    // InternalKap.g:2761:1: rule__Match__Group_5__2__Impl : ( ( rule__Match__BodyAssignment_5_2 ) ) ;
    public final void rule__Match__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2765:1: ( ( ( rule__Match__BodyAssignment_5_2 ) ) )
            // InternalKap.g:2766:1: ( ( rule__Match__BodyAssignment_5_2 ) )
            {
            // InternalKap.g:2766:1: ( ( rule__Match__BodyAssignment_5_2 ) )
            // InternalKap.g:2767:2: ( rule__Match__BodyAssignment_5_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyAssignment_5_2()); 
            }
            // InternalKap.g:2768:2: ( rule__Match__BodyAssignment_5_2 )
            // InternalKap.g:2768:3: rule__Match__BodyAssignment_5_2
            {
            pushFollow(FOLLOW_2);
            rule__Match__BodyAssignment_5_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyAssignment_5_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_5__2__Impl"


    // $ANTLR start "rule__Number__Group__0"
    // InternalKap.g:2777:1: rule__Number__Group__0 : rule__Number__Group__0__Impl rule__Number__Group__1 ;
    public final void rule__Number__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2781:1: ( rule__Number__Group__0__Impl rule__Number__Group__1 )
            // InternalKap.g:2782:2: rule__Number__Group__0__Impl rule__Number__Group__1
            {
            pushFollow(FOLLOW_15);
            rule__Number__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__0"


    // $ANTLR start "rule__Number__Group__0__Impl"
    // InternalKap.g:2789:1: rule__Number__Group__0__Impl : ( ( rule__Number__Alternatives_0 )? ) ;
    public final void rule__Number__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2793:1: ( ( ( rule__Number__Alternatives_0 )? ) )
            // InternalKap.g:2794:1: ( ( rule__Number__Alternatives_0 )? )
            {
            // InternalKap.g:2794:1: ( ( rule__Number__Alternatives_0 )? )
            // InternalKap.g:2795:2: ( rule__Number__Alternatives_0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getAlternatives_0()); 
            }
            // InternalKap.g:2796:2: ( rule__Number__Alternatives_0 )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==19||LA28_0==33) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalKap.g:2796:3: rule__Number__Alternatives_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__Alternatives_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getAlternatives_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__0__Impl"


    // $ANTLR start "rule__Number__Group__1"
    // InternalKap.g:2804:1: rule__Number__Group__1 : rule__Number__Group__1__Impl rule__Number__Group__2 ;
    public final void rule__Number__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2808:1: ( rule__Number__Group__1__Impl rule__Number__Group__2 )
            // InternalKap.g:2809:2: rule__Number__Group__1__Impl rule__Number__Group__2
            {
            pushFollow(FOLLOW_26);
            rule__Number__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__1"


    // $ANTLR start "rule__Number__Group__1__Impl"
    // InternalKap.g:2816:1: rule__Number__Group__1__Impl : ( ( rule__Number__RealAssignment_1 ) ) ;
    public final void rule__Number__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2820:1: ( ( ( rule__Number__RealAssignment_1 ) ) )
            // InternalKap.g:2821:1: ( ( rule__Number__RealAssignment_1 ) )
            {
            // InternalKap.g:2821:1: ( ( rule__Number__RealAssignment_1 ) )
            // InternalKap.g:2822:2: ( rule__Number__RealAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getRealAssignment_1()); 
            }
            // InternalKap.g:2823:2: ( rule__Number__RealAssignment_1 )
            // InternalKap.g:2823:3: rule__Number__RealAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Number__RealAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getRealAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__1__Impl"


    // $ANTLR start "rule__Number__Group__2"
    // InternalKap.g:2831:1: rule__Number__Group__2 : rule__Number__Group__2__Impl rule__Number__Group__3 ;
    public final void rule__Number__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2835:1: ( rule__Number__Group__2__Impl rule__Number__Group__3 )
            // InternalKap.g:2836:2: rule__Number__Group__2__Impl rule__Number__Group__3
            {
            pushFollow(FOLLOW_26);
            rule__Number__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__2"


    // $ANTLR start "rule__Number__Group__2__Impl"
    // InternalKap.g:2843:1: rule__Number__Group__2__Impl : ( ( rule__Number__LongAssignment_2 )? ) ;
    public final void rule__Number__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2847:1: ( ( ( rule__Number__LongAssignment_2 )? ) )
            // InternalKap.g:2848:1: ( ( rule__Number__LongAssignment_2 )? )
            {
            // InternalKap.g:2848:1: ( ( rule__Number__LongAssignment_2 )? )
            // InternalKap.g:2849:2: ( rule__Number__LongAssignment_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getLongAssignment_2()); 
            }
            // InternalKap.g:2850:2: ( rule__Number__LongAssignment_2 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==36) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalKap.g:2850:3: rule__Number__LongAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__LongAssignment_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getLongAssignment_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__2__Impl"


    // $ANTLR start "rule__Number__Group__3"
    // InternalKap.g:2858:1: rule__Number__Group__3 : rule__Number__Group__3__Impl rule__Number__Group__4 ;
    public final void rule__Number__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2862:1: ( rule__Number__Group__3__Impl rule__Number__Group__4 )
            // InternalKap.g:2863:2: rule__Number__Group__3__Impl rule__Number__Group__4
            {
            pushFollow(FOLLOW_26);
            rule__Number__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__3"


    // $ANTLR start "rule__Number__Group__3__Impl"
    // InternalKap.g:2870:1: rule__Number__Group__3__Impl : ( ( rule__Number__Group_3__0 )? ) ;
    public final void rule__Number__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2874:1: ( ( ( rule__Number__Group_3__0 )? ) )
            // InternalKap.g:2875:1: ( ( rule__Number__Group_3__0 )? )
            {
            // InternalKap.g:2875:1: ( ( rule__Number__Group_3__0 )? )
            // InternalKap.g:2876:2: ( rule__Number__Group_3__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getGroup_3()); 
            }
            // InternalKap.g:2877:2: ( rule__Number__Group_3__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==34) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalKap.g:2877:3: rule__Number__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__Group_3__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getGroup_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__3__Impl"


    // $ANTLR start "rule__Number__Group__4"
    // InternalKap.g:2885:1: rule__Number__Group__4 : rule__Number__Group__4__Impl ;
    public final void rule__Number__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2889:1: ( rule__Number__Group__4__Impl )
            // InternalKap.g:2890:2: rule__Number__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__4"


    // $ANTLR start "rule__Number__Group__4__Impl"
    // InternalKap.g:2896:1: rule__Number__Group__4__Impl : ( ( rule__Number__Group_4__0 )? ) ;
    public final void rule__Number__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2900:1: ( ( ( rule__Number__Group_4__0 )? ) )
            // InternalKap.g:2901:1: ( ( rule__Number__Group_4__0 )? )
            {
            // InternalKap.g:2901:1: ( ( rule__Number__Group_4__0 )? )
            // InternalKap.g:2902:2: ( rule__Number__Group_4__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getGroup_4()); 
            }
            // InternalKap.g:2903:2: ( rule__Number__Group_4__0 )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( ((LA31_0>=20 && LA31_0<=21)) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalKap.g:2903:3: rule__Number__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__Group_4__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getGroup_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__4__Impl"


    // $ANTLR start "rule__Number__Group_3__0"
    // InternalKap.g:2912:1: rule__Number__Group_3__0 : rule__Number__Group_3__0__Impl ;
    public final void rule__Number__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2916:1: ( rule__Number__Group_3__0__Impl )
            // InternalKap.g:2917:2: rule__Number__Group_3__0__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_3__0"


    // $ANTLR start "rule__Number__Group_3__0__Impl"
    // InternalKap.g:2923:1: rule__Number__Group_3__0__Impl : ( ( rule__Number__Group_3_0__0 ) ) ;
    public final void rule__Number__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2927:1: ( ( ( rule__Number__Group_3_0__0 ) ) )
            // InternalKap.g:2928:1: ( ( rule__Number__Group_3_0__0 ) )
            {
            // InternalKap.g:2928:1: ( ( rule__Number__Group_3_0__0 ) )
            // InternalKap.g:2929:2: ( rule__Number__Group_3_0__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getGroup_3_0()); 
            }
            // InternalKap.g:2930:2: ( rule__Number__Group_3_0__0 )
            // InternalKap.g:2930:3: rule__Number__Group_3_0__0
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group_3_0__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getGroup_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_3__0__Impl"


    // $ANTLR start "rule__Number__Group_3_0__0"
    // InternalKap.g:2939:1: rule__Number__Group_3_0__0 : rule__Number__Group_3_0__0__Impl rule__Number__Group_3_0__1 ;
    public final void rule__Number__Group_3_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2943:1: ( rule__Number__Group_3_0__0__Impl rule__Number__Group_3_0__1 )
            // InternalKap.g:2944:2: rule__Number__Group_3_0__0__Impl rule__Number__Group_3_0__1
            {
            pushFollow(FOLLOW_27);
            rule__Number__Group_3_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group_3_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_3_0__0"


    // $ANTLR start "rule__Number__Group_3_0__0__Impl"
    // InternalKap.g:2951:1: rule__Number__Group_3_0__0__Impl : ( ( rule__Number__DecimalAssignment_3_0_0 ) ) ;
    public final void rule__Number__Group_3_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2955:1: ( ( ( rule__Number__DecimalAssignment_3_0_0 ) ) )
            // InternalKap.g:2956:1: ( ( rule__Number__DecimalAssignment_3_0_0 ) )
            {
            // InternalKap.g:2956:1: ( ( rule__Number__DecimalAssignment_3_0_0 ) )
            // InternalKap.g:2957:2: ( rule__Number__DecimalAssignment_3_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getDecimalAssignment_3_0_0()); 
            }
            // InternalKap.g:2958:2: ( rule__Number__DecimalAssignment_3_0_0 )
            // InternalKap.g:2958:3: rule__Number__DecimalAssignment_3_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Number__DecimalAssignment_3_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getDecimalAssignment_3_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_3_0__0__Impl"


    // $ANTLR start "rule__Number__Group_3_0__1"
    // InternalKap.g:2966:1: rule__Number__Group_3_0__1 : rule__Number__Group_3_0__1__Impl ;
    public final void rule__Number__Group_3_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2970:1: ( rule__Number__Group_3_0__1__Impl )
            // InternalKap.g:2971:2: rule__Number__Group_3_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group_3_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_3_0__1"


    // $ANTLR start "rule__Number__Group_3_0__1__Impl"
    // InternalKap.g:2977:1: rule__Number__Group_3_0__1__Impl : ( ( rule__Number__DecimalPartAssignment_3_0_1 ) ) ;
    public final void rule__Number__Group_3_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2981:1: ( ( ( rule__Number__DecimalPartAssignment_3_0_1 ) ) )
            // InternalKap.g:2982:1: ( ( rule__Number__DecimalPartAssignment_3_0_1 ) )
            {
            // InternalKap.g:2982:1: ( ( rule__Number__DecimalPartAssignment_3_0_1 ) )
            // InternalKap.g:2983:2: ( rule__Number__DecimalPartAssignment_3_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getDecimalPartAssignment_3_0_1()); 
            }
            // InternalKap.g:2984:2: ( rule__Number__DecimalPartAssignment_3_0_1 )
            // InternalKap.g:2984:3: rule__Number__DecimalPartAssignment_3_0_1
            {
            pushFollow(FOLLOW_2);
            rule__Number__DecimalPartAssignment_3_0_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getDecimalPartAssignment_3_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_3_0__1__Impl"


    // $ANTLR start "rule__Number__Group_4__0"
    // InternalKap.g:2993:1: rule__Number__Group_4__0 : rule__Number__Group_4__0__Impl ;
    public final void rule__Number__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2997:1: ( rule__Number__Group_4__0__Impl )
            // InternalKap.g:2998:2: rule__Number__Group_4__0__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group_4__0__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4__0"


    // $ANTLR start "rule__Number__Group_4__0__Impl"
    // InternalKap.g:3004:1: rule__Number__Group_4__0__Impl : ( ( rule__Number__Group_4_0__0 ) ) ;
    public final void rule__Number__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3008:1: ( ( ( rule__Number__Group_4_0__0 ) ) )
            // InternalKap.g:3009:1: ( ( rule__Number__Group_4_0__0 ) )
            {
            // InternalKap.g:3009:1: ( ( rule__Number__Group_4_0__0 ) )
            // InternalKap.g:3010:2: ( rule__Number__Group_4_0__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getGroup_4_0()); 
            }
            // InternalKap.g:3011:2: ( rule__Number__Group_4_0__0 )
            // InternalKap.g:3011:3: rule__Number__Group_4_0__0
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group_4_0__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getGroup_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4__0__Impl"


    // $ANTLR start "rule__Number__Group_4_0__0"
    // InternalKap.g:3020:1: rule__Number__Group_4_0__0 : rule__Number__Group_4_0__0__Impl rule__Number__Group_4_0__1 ;
    public final void rule__Number__Group_4_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3024:1: ( rule__Number__Group_4_0__0__Impl rule__Number__Group_4_0__1 )
            // InternalKap.g:3025:2: rule__Number__Group_4_0__0__Impl rule__Number__Group_4_0__1
            {
            pushFollow(FOLLOW_15);
            rule__Number__Group_4_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group_4_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4_0__0"


    // $ANTLR start "rule__Number__Group_4_0__0__Impl"
    // InternalKap.g:3032:1: rule__Number__Group_4_0__0__Impl : ( ( rule__Number__ExponentialAssignment_4_0_0 ) ) ;
    public final void rule__Number__Group_4_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3036:1: ( ( ( rule__Number__ExponentialAssignment_4_0_0 ) ) )
            // InternalKap.g:3037:1: ( ( rule__Number__ExponentialAssignment_4_0_0 ) )
            {
            // InternalKap.g:3037:1: ( ( rule__Number__ExponentialAssignment_4_0_0 ) )
            // InternalKap.g:3038:2: ( rule__Number__ExponentialAssignment_4_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getExponentialAssignment_4_0_0()); 
            }
            // InternalKap.g:3039:2: ( rule__Number__ExponentialAssignment_4_0_0 )
            // InternalKap.g:3039:3: rule__Number__ExponentialAssignment_4_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Number__ExponentialAssignment_4_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getExponentialAssignment_4_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4_0__0__Impl"


    // $ANTLR start "rule__Number__Group_4_0__1"
    // InternalKap.g:3047:1: rule__Number__Group_4_0__1 : rule__Number__Group_4_0__1__Impl rule__Number__Group_4_0__2 ;
    public final void rule__Number__Group_4_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3051:1: ( rule__Number__Group_4_0__1__Impl rule__Number__Group_4_0__2 )
            // InternalKap.g:3052:2: rule__Number__Group_4_0__1__Impl rule__Number__Group_4_0__2
            {
            pushFollow(FOLLOW_15);
            rule__Number__Group_4_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group_4_0__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4_0__1"


    // $ANTLR start "rule__Number__Group_4_0__1__Impl"
    // InternalKap.g:3059:1: rule__Number__Group_4_0__1__Impl : ( ( rule__Number__Alternatives_4_0_1 )? ) ;
    public final void rule__Number__Group_4_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3063:1: ( ( ( rule__Number__Alternatives_4_0_1 )? ) )
            // InternalKap.g:3064:1: ( ( rule__Number__Alternatives_4_0_1 )? )
            {
            // InternalKap.g:3064:1: ( ( rule__Number__Alternatives_4_0_1 )? )
            // InternalKap.g:3065:2: ( rule__Number__Alternatives_4_0_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getAlternatives_4_0_1()); 
            }
            // InternalKap.g:3066:2: ( rule__Number__Alternatives_4_0_1 )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==19||LA32_0==33) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalKap.g:3066:3: rule__Number__Alternatives_4_0_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__Alternatives_4_0_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getAlternatives_4_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4_0__1__Impl"


    // $ANTLR start "rule__Number__Group_4_0__2"
    // InternalKap.g:3074:1: rule__Number__Group_4_0__2 : rule__Number__Group_4_0__2__Impl ;
    public final void rule__Number__Group_4_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3078:1: ( rule__Number__Group_4_0__2__Impl )
            // InternalKap.g:3079:2: rule__Number__Group_4_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group_4_0__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4_0__2"


    // $ANTLR start "rule__Number__Group_4_0__2__Impl"
    // InternalKap.g:3085:1: rule__Number__Group_4_0__2__Impl : ( ( rule__Number__ExpAssignment_4_0_2 ) ) ;
    public final void rule__Number__Group_4_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3089:1: ( ( ( rule__Number__ExpAssignment_4_0_2 ) ) )
            // InternalKap.g:3090:1: ( ( rule__Number__ExpAssignment_4_0_2 ) )
            {
            // InternalKap.g:3090:1: ( ( rule__Number__ExpAssignment_4_0_2 ) )
            // InternalKap.g:3091:2: ( rule__Number__ExpAssignment_4_0_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getExpAssignment_4_0_2()); 
            }
            // InternalKap.g:3092:2: ( rule__Number__ExpAssignment_4_0_2 )
            // InternalKap.g:3092:3: rule__Number__ExpAssignment_4_0_2
            {
            pushFollow(FOLLOW_2);
            rule__Number__ExpAssignment_4_0_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getExpAssignment_4_0_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4_0__2__Impl"


    // $ANTLR start "rule__Date__Group__0"
    // InternalKap.g:3101:1: rule__Date__Group__0 : rule__Date__Group__0__Impl rule__Date__Group__1 ;
    public final void rule__Date__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3105:1: ( rule__Date__Group__0__Impl rule__Date__Group__1 )
            // InternalKap.g:3106:2: rule__Date__Group__0__Impl rule__Date__Group__1
            {
            pushFollow(FOLLOW_28);
            rule__Date__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__0"


    // $ANTLR start "rule__Date__Group__0__Impl"
    // InternalKap.g:3113:1: rule__Date__Group__0__Impl : ( ( rule__Date__YearAssignment_0 ) ) ;
    public final void rule__Date__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3117:1: ( ( ( rule__Date__YearAssignment_0 ) ) )
            // InternalKap.g:3118:1: ( ( rule__Date__YearAssignment_0 ) )
            {
            // InternalKap.g:3118:1: ( ( rule__Date__YearAssignment_0 ) )
            // InternalKap.g:3119:2: ( rule__Date__YearAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getYearAssignment_0()); 
            }
            // InternalKap.g:3120:2: ( rule__Date__YearAssignment_0 )
            // InternalKap.g:3120:3: rule__Date__YearAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__Date__YearAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getYearAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__0__Impl"


    // $ANTLR start "rule__Date__Group__1"
    // InternalKap.g:3128:1: rule__Date__Group__1 : rule__Date__Group__1__Impl rule__Date__Group__2 ;
    public final void rule__Date__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3132:1: ( rule__Date__Group__1__Impl rule__Date__Group__2 )
            // InternalKap.g:3133:2: rule__Date__Group__1__Impl rule__Date__Group__2
            {
            pushFollow(FOLLOW_28);
            rule__Date__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__1"


    // $ANTLR start "rule__Date__Group__1__Impl"
    // InternalKap.g:3140:1: rule__Date__Group__1__Impl : ( ( rule__Date__Alternatives_1 )? ) ;
    public final void rule__Date__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3144:1: ( ( ( rule__Date__Alternatives_1 )? ) )
            // InternalKap.g:3145:1: ( ( rule__Date__Alternatives_1 )? )
            {
            // InternalKap.g:3145:1: ( ( rule__Date__Alternatives_1 )? )
            // InternalKap.g:3146:2: ( rule__Date__Alternatives_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getAlternatives_1()); 
            }
            // InternalKap.g:3147:2: ( rule__Date__Alternatives_1 )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( ((LA33_0>=22 && LA33_0<=23)||LA33_0==37) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalKap.g:3147:3: rule__Date__Alternatives_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Date__Alternatives_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getAlternatives_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__1__Impl"


    // $ANTLR start "rule__Date__Group__2"
    // InternalKap.g:3155:1: rule__Date__Group__2 : rule__Date__Group__2__Impl rule__Date__Group__3 ;
    public final void rule__Date__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3159:1: ( rule__Date__Group__2__Impl rule__Date__Group__3 )
            // InternalKap.g:3160:2: rule__Date__Group__2__Impl rule__Date__Group__3
            {
            pushFollow(FOLLOW_27);
            rule__Date__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__2"


    // $ANTLR start "rule__Date__Group__2__Impl"
    // InternalKap.g:3167:1: rule__Date__Group__2__Impl : ( '-' ) ;
    public final void rule__Date__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3171:1: ( ( '-' ) )
            // InternalKap.g:3172:1: ( '-' )
            {
            // InternalKap.g:3172:1: ( '-' )
            // InternalKap.g:3173:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getHyphenMinusKeyword_2()); 
            }
            match(input,33,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getHyphenMinusKeyword_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__2__Impl"


    // $ANTLR start "rule__Date__Group__3"
    // InternalKap.g:3182:1: rule__Date__Group__3 : rule__Date__Group__3__Impl rule__Date__Group__4 ;
    public final void rule__Date__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3186:1: ( rule__Date__Group__3__Impl rule__Date__Group__4 )
            // InternalKap.g:3187:2: rule__Date__Group__3__Impl rule__Date__Group__4
            {
            pushFollow(FOLLOW_29);
            rule__Date__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__3"


    // $ANTLR start "rule__Date__Group__3__Impl"
    // InternalKap.g:3194:1: rule__Date__Group__3__Impl : ( ( rule__Date__MonthAssignment_3 ) ) ;
    public final void rule__Date__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3198:1: ( ( ( rule__Date__MonthAssignment_3 ) ) )
            // InternalKap.g:3199:1: ( ( rule__Date__MonthAssignment_3 ) )
            {
            // InternalKap.g:3199:1: ( ( rule__Date__MonthAssignment_3 ) )
            // InternalKap.g:3200:2: ( rule__Date__MonthAssignment_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getMonthAssignment_3()); 
            }
            // InternalKap.g:3201:2: ( rule__Date__MonthAssignment_3 )
            // InternalKap.g:3201:3: rule__Date__MonthAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Date__MonthAssignment_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getMonthAssignment_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__3__Impl"


    // $ANTLR start "rule__Date__Group__4"
    // InternalKap.g:3209:1: rule__Date__Group__4 : rule__Date__Group__4__Impl rule__Date__Group__5 ;
    public final void rule__Date__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3213:1: ( rule__Date__Group__4__Impl rule__Date__Group__5 )
            // InternalKap.g:3214:2: rule__Date__Group__4__Impl rule__Date__Group__5
            {
            pushFollow(FOLLOW_27);
            rule__Date__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__4"


    // $ANTLR start "rule__Date__Group__4__Impl"
    // InternalKap.g:3221:1: rule__Date__Group__4__Impl : ( '-' ) ;
    public final void rule__Date__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3225:1: ( ( '-' ) )
            // InternalKap.g:3226:1: ( '-' )
            {
            // InternalKap.g:3226:1: ( '-' )
            // InternalKap.g:3227:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getHyphenMinusKeyword_4()); 
            }
            match(input,33,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getHyphenMinusKeyword_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__4__Impl"


    // $ANTLR start "rule__Date__Group__5"
    // InternalKap.g:3236:1: rule__Date__Group__5 : rule__Date__Group__5__Impl rule__Date__Group__6 ;
    public final void rule__Date__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3240:1: ( rule__Date__Group__5__Impl rule__Date__Group__6 )
            // InternalKap.g:3241:2: rule__Date__Group__5__Impl rule__Date__Group__6
            {
            pushFollow(FOLLOW_27);
            rule__Date__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group__6();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__5"


    // $ANTLR start "rule__Date__Group__5__Impl"
    // InternalKap.g:3248:1: rule__Date__Group__5__Impl : ( ( rule__Date__DayAssignment_5 ) ) ;
    public final void rule__Date__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3252:1: ( ( ( rule__Date__DayAssignment_5 ) ) )
            // InternalKap.g:3253:1: ( ( rule__Date__DayAssignment_5 ) )
            {
            // InternalKap.g:3253:1: ( ( rule__Date__DayAssignment_5 ) )
            // InternalKap.g:3254:2: ( rule__Date__DayAssignment_5 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getDayAssignment_5()); 
            }
            // InternalKap.g:3255:2: ( rule__Date__DayAssignment_5 )
            // InternalKap.g:3255:3: rule__Date__DayAssignment_5
            {
            pushFollow(FOLLOW_2);
            rule__Date__DayAssignment_5();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getDayAssignment_5()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__5__Impl"


    // $ANTLR start "rule__Date__Group__6"
    // InternalKap.g:3263:1: rule__Date__Group__6 : rule__Date__Group__6__Impl ;
    public final void rule__Date__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3267:1: ( rule__Date__Group__6__Impl )
            // InternalKap.g:3268:2: rule__Date__Group__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Date__Group__6__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__6"


    // $ANTLR start "rule__Date__Group__6__Impl"
    // InternalKap.g:3274:1: rule__Date__Group__6__Impl : ( ( rule__Date__Group_6__0 )? ) ;
    public final void rule__Date__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3278:1: ( ( ( rule__Date__Group_6__0 )? ) )
            // InternalKap.g:3279:1: ( ( rule__Date__Group_6__0 )? )
            {
            // InternalKap.g:3279:1: ( ( rule__Date__Group_6__0 )? )
            // InternalKap.g:3280:2: ( rule__Date__Group_6__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getGroup_6()); 
            }
            // InternalKap.g:3281:2: ( rule__Date__Group_6__0 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==RULE_INT) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalKap.g:3281:3: rule__Date__Group_6__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Date__Group_6__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getGroup_6()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__6__Impl"


    // $ANTLR start "rule__Date__Group_6__0"
    // InternalKap.g:3290:1: rule__Date__Group_6__0 : rule__Date__Group_6__0__Impl rule__Date__Group_6__1 ;
    public final void rule__Date__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3294:1: ( rule__Date__Group_6__0__Impl rule__Date__Group_6__1 )
            // InternalKap.g:3295:2: rule__Date__Group_6__0__Impl rule__Date__Group_6__1
            {
            pushFollow(FOLLOW_30);
            rule__Date__Group_6__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group_6__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__0"


    // $ANTLR start "rule__Date__Group_6__0__Impl"
    // InternalKap.g:3302:1: rule__Date__Group_6__0__Impl : ( ( rule__Date__HourAssignment_6_0 ) ) ;
    public final void rule__Date__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3306:1: ( ( ( rule__Date__HourAssignment_6_0 ) ) )
            // InternalKap.g:3307:1: ( ( rule__Date__HourAssignment_6_0 ) )
            {
            // InternalKap.g:3307:1: ( ( rule__Date__HourAssignment_6_0 ) )
            // InternalKap.g:3308:2: ( rule__Date__HourAssignment_6_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getHourAssignment_6_0()); 
            }
            // InternalKap.g:3309:2: ( rule__Date__HourAssignment_6_0 )
            // InternalKap.g:3309:3: rule__Date__HourAssignment_6_0
            {
            pushFollow(FOLLOW_2);
            rule__Date__HourAssignment_6_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getHourAssignment_6_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__0__Impl"


    // $ANTLR start "rule__Date__Group_6__1"
    // InternalKap.g:3317:1: rule__Date__Group_6__1 : rule__Date__Group_6__1__Impl rule__Date__Group_6__2 ;
    public final void rule__Date__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3321:1: ( rule__Date__Group_6__1__Impl rule__Date__Group_6__2 )
            // InternalKap.g:3322:2: rule__Date__Group_6__1__Impl rule__Date__Group_6__2
            {
            pushFollow(FOLLOW_27);
            rule__Date__Group_6__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group_6__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__1"


    // $ANTLR start "rule__Date__Group_6__1__Impl"
    // InternalKap.g:3329:1: rule__Date__Group_6__1__Impl : ( ':' ) ;
    public final void rule__Date__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3333:1: ( ( ':' ) )
            // InternalKap.g:3334:1: ( ':' )
            {
            // InternalKap.g:3334:1: ( ':' )
            // InternalKap.g:3335:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getColonKeyword_6_1()); 
            }
            match(input,26,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getColonKeyword_6_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__1__Impl"


    // $ANTLR start "rule__Date__Group_6__2"
    // InternalKap.g:3344:1: rule__Date__Group_6__2 : rule__Date__Group_6__2__Impl rule__Date__Group_6__3 ;
    public final void rule__Date__Group_6__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3348:1: ( rule__Date__Group_6__2__Impl rule__Date__Group_6__3 )
            // InternalKap.g:3349:2: rule__Date__Group_6__2__Impl rule__Date__Group_6__3
            {
            pushFollow(FOLLOW_30);
            rule__Date__Group_6__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group_6__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__2"


    // $ANTLR start "rule__Date__Group_6__2__Impl"
    // InternalKap.g:3356:1: rule__Date__Group_6__2__Impl : ( ( rule__Date__MinAssignment_6_2 ) ) ;
    public final void rule__Date__Group_6__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3360:1: ( ( ( rule__Date__MinAssignment_6_2 ) ) )
            // InternalKap.g:3361:1: ( ( rule__Date__MinAssignment_6_2 ) )
            {
            // InternalKap.g:3361:1: ( ( rule__Date__MinAssignment_6_2 ) )
            // InternalKap.g:3362:2: ( rule__Date__MinAssignment_6_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getMinAssignment_6_2()); 
            }
            // InternalKap.g:3363:2: ( rule__Date__MinAssignment_6_2 )
            // InternalKap.g:3363:3: rule__Date__MinAssignment_6_2
            {
            pushFollow(FOLLOW_2);
            rule__Date__MinAssignment_6_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getMinAssignment_6_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__2__Impl"


    // $ANTLR start "rule__Date__Group_6__3"
    // InternalKap.g:3371:1: rule__Date__Group_6__3 : rule__Date__Group_6__3__Impl ;
    public final void rule__Date__Group_6__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3375:1: ( rule__Date__Group_6__3__Impl )
            // InternalKap.g:3376:2: rule__Date__Group_6__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Date__Group_6__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__3"


    // $ANTLR start "rule__Date__Group_6__3__Impl"
    // InternalKap.g:3382:1: rule__Date__Group_6__3__Impl : ( ( rule__Date__Group_6_3__0 )? ) ;
    public final void rule__Date__Group_6__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3386:1: ( ( ( rule__Date__Group_6_3__0 )? ) )
            // InternalKap.g:3387:1: ( ( rule__Date__Group_6_3__0 )? )
            {
            // InternalKap.g:3387:1: ( ( rule__Date__Group_6_3__0 )? )
            // InternalKap.g:3388:2: ( rule__Date__Group_6_3__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getGroup_6_3()); 
            }
            // InternalKap.g:3389:2: ( rule__Date__Group_6_3__0 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==26) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalKap.g:3389:3: rule__Date__Group_6_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Date__Group_6_3__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getGroup_6_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__3__Impl"


    // $ANTLR start "rule__Date__Group_6_3__0"
    // InternalKap.g:3398:1: rule__Date__Group_6_3__0 : rule__Date__Group_6_3__0__Impl rule__Date__Group_6_3__1 ;
    public final void rule__Date__Group_6_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3402:1: ( rule__Date__Group_6_3__0__Impl rule__Date__Group_6_3__1 )
            // InternalKap.g:3403:2: rule__Date__Group_6_3__0__Impl rule__Date__Group_6_3__1
            {
            pushFollow(FOLLOW_27);
            rule__Date__Group_6_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group_6_3__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3__0"


    // $ANTLR start "rule__Date__Group_6_3__0__Impl"
    // InternalKap.g:3410:1: rule__Date__Group_6_3__0__Impl : ( ':' ) ;
    public final void rule__Date__Group_6_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3414:1: ( ( ':' ) )
            // InternalKap.g:3415:1: ( ':' )
            {
            // InternalKap.g:3415:1: ( ':' )
            // InternalKap.g:3416:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getColonKeyword_6_3_0()); 
            }
            match(input,26,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getColonKeyword_6_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3__0__Impl"


    // $ANTLR start "rule__Date__Group_6_3__1"
    // InternalKap.g:3425:1: rule__Date__Group_6_3__1 : rule__Date__Group_6_3__1__Impl rule__Date__Group_6_3__2 ;
    public final void rule__Date__Group_6_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3429:1: ( rule__Date__Group_6_3__1__Impl rule__Date__Group_6_3__2 )
            // InternalKap.g:3430:2: rule__Date__Group_6_3__1__Impl rule__Date__Group_6_3__2
            {
            pushFollow(FOLLOW_31);
            rule__Date__Group_6_3__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group_6_3__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3__1"


    // $ANTLR start "rule__Date__Group_6_3__1__Impl"
    // InternalKap.g:3437:1: rule__Date__Group_6_3__1__Impl : ( ( rule__Date__SecAssignment_6_3_1 ) ) ;
    public final void rule__Date__Group_6_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3441:1: ( ( ( rule__Date__SecAssignment_6_3_1 ) ) )
            // InternalKap.g:3442:1: ( ( rule__Date__SecAssignment_6_3_1 ) )
            {
            // InternalKap.g:3442:1: ( ( rule__Date__SecAssignment_6_3_1 ) )
            // InternalKap.g:3443:2: ( rule__Date__SecAssignment_6_3_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getSecAssignment_6_3_1()); 
            }
            // InternalKap.g:3444:2: ( rule__Date__SecAssignment_6_3_1 )
            // InternalKap.g:3444:3: rule__Date__SecAssignment_6_3_1
            {
            pushFollow(FOLLOW_2);
            rule__Date__SecAssignment_6_3_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getSecAssignment_6_3_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3__1__Impl"


    // $ANTLR start "rule__Date__Group_6_3__2"
    // InternalKap.g:3452:1: rule__Date__Group_6_3__2 : rule__Date__Group_6_3__2__Impl ;
    public final void rule__Date__Group_6_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3456:1: ( rule__Date__Group_6_3__2__Impl )
            // InternalKap.g:3457:2: rule__Date__Group_6_3__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Date__Group_6_3__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3__2"


    // $ANTLR start "rule__Date__Group_6_3__2__Impl"
    // InternalKap.g:3463:1: rule__Date__Group_6_3__2__Impl : ( ( rule__Date__Group_6_3_2__0 )? ) ;
    public final void rule__Date__Group_6_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3467:1: ( ( ( rule__Date__Group_6_3_2__0 )? ) )
            // InternalKap.g:3468:1: ( ( rule__Date__Group_6_3_2__0 )? )
            {
            // InternalKap.g:3468:1: ( ( rule__Date__Group_6_3_2__0 )? )
            // InternalKap.g:3469:2: ( rule__Date__Group_6_3_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getGroup_6_3_2()); 
            }
            // InternalKap.g:3470:2: ( rule__Date__Group_6_3_2__0 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==34) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalKap.g:3470:3: rule__Date__Group_6_3_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Date__Group_6_3_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getGroup_6_3_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3__2__Impl"


    // $ANTLR start "rule__Date__Group_6_3_2__0"
    // InternalKap.g:3479:1: rule__Date__Group_6_3_2__0 : rule__Date__Group_6_3_2__0__Impl rule__Date__Group_6_3_2__1 ;
    public final void rule__Date__Group_6_3_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3483:1: ( rule__Date__Group_6_3_2__0__Impl rule__Date__Group_6_3_2__1 )
            // InternalKap.g:3484:2: rule__Date__Group_6_3_2__0__Impl rule__Date__Group_6_3_2__1
            {
            pushFollow(FOLLOW_27);
            rule__Date__Group_6_3_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group_6_3_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3_2__0"


    // $ANTLR start "rule__Date__Group_6_3_2__0__Impl"
    // InternalKap.g:3491:1: rule__Date__Group_6_3_2__0__Impl : ( '.' ) ;
    public final void rule__Date__Group_6_3_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3495:1: ( ( '.' ) )
            // InternalKap.g:3496:1: ( '.' )
            {
            // InternalKap.g:3496:1: ( '.' )
            // InternalKap.g:3497:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getFullStopKeyword_6_3_2_0()); 
            }
            match(input,34,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getFullStopKeyword_6_3_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3_2__0__Impl"


    // $ANTLR start "rule__Date__Group_6_3_2__1"
    // InternalKap.g:3506:1: rule__Date__Group_6_3_2__1 : rule__Date__Group_6_3_2__1__Impl ;
    public final void rule__Date__Group_6_3_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3510:1: ( rule__Date__Group_6_3_2__1__Impl )
            // InternalKap.g:3511:2: rule__Date__Group_6_3_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Date__Group_6_3_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3_2__1"


    // $ANTLR start "rule__Date__Group_6_3_2__1__Impl"
    // InternalKap.g:3517:1: rule__Date__Group_6_3_2__1__Impl : ( ( rule__Date__MsAssignment_6_3_2_1 ) ) ;
    public final void rule__Date__Group_6_3_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3521:1: ( ( ( rule__Date__MsAssignment_6_3_2_1 ) ) )
            // InternalKap.g:3522:1: ( ( rule__Date__MsAssignment_6_3_2_1 ) )
            {
            // InternalKap.g:3522:1: ( ( rule__Date__MsAssignment_6_3_2_1 ) )
            // InternalKap.g:3523:2: ( rule__Date__MsAssignment_6_3_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getMsAssignment_6_3_2_1()); 
            }
            // InternalKap.g:3524:2: ( rule__Date__MsAssignment_6_3_2_1 )
            // InternalKap.g:3524:3: rule__Date__MsAssignment_6_3_2_1
            {
            pushFollow(FOLLOW_2);
            rule__Date__MsAssignment_6_3_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getMsAssignment_6_3_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3_2__1__Impl"


    // $ANTLR start "rule__Model__PreambleAssignment_1"
    // InternalKap.g:3533:1: rule__Model__PreambleAssignment_1 : ( rulePreamble ) ;
    public final void rule__Model__PreambleAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3537:1: ( ( rulePreamble ) )
            // InternalKap.g:3538:2: ( rulePreamble )
            {
            // InternalKap.g:3538:2: ( rulePreamble )
            // InternalKap.g:3539:3: rulePreamble
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getPreamblePreambleParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            rulePreamble();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getPreamblePreambleParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__PreambleAssignment_1"


    // $ANTLR start "rule__Model__DefinitionsAssignment_2"
    // InternalKap.g:3548:1: rule__Model__DefinitionsAssignment_2 : ( ruleDefinition ) ;
    public final void rule__Model__DefinitionsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3552:1: ( ( ruleDefinition ) )
            // InternalKap.g:3553:2: ( ruleDefinition )
            {
            // InternalKap.g:3553:2: ( ruleDefinition )
            // InternalKap.g:3554:3: ruleDefinition
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getDefinitionsDefinitionParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleDefinition();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getDefinitionsDefinitionParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__DefinitionsAssignment_2"


    // $ANTLR start "rule__Preamble__NameAssignment_1"
    // InternalKap.g:3563:1: rule__Preamble__NameAssignment_1 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__Preamble__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3567:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:3568:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:3568:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:3569:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getNameLOWERCASE_IDTerminalRuleCall_1_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getNameLOWERCASE_IDTerminalRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__NameAssignment_1"


    // $ANTLR start "rule__Definition__NameAssignment_1"
    // InternalKap.g:3578:1: rule__Definition__NameAssignment_1 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__Definition__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3582:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:3583:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:3583:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:3584:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_1_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__NameAssignment_1"


    // $ANTLR start "rule__Definition__ArgumentsAssignment_2"
    // InternalKap.g:3593:1: rule__Definition__ArgumentsAssignment_2 : ( ruleArgumentDeclaration ) ;
    public final void rule__Definition__ArgumentsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3597:1: ( ( ruleArgumentDeclaration ) )
            // InternalKap.g:3598:2: ( ruleArgumentDeclaration )
            {
            // InternalKap.g:3598:2: ( ruleArgumentDeclaration )
            // InternalKap.g:3599:3: ruleArgumentDeclaration
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getArgumentsArgumentDeclarationParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleArgumentDeclaration();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getArgumentsArgumentDeclarationParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__ArgumentsAssignment_2"


    // $ANTLR start "rule__Definition__BodyAssignment_4"
    // InternalKap.g:3608:1: rule__Definition__BodyAssignment_4 : ( ruleBody ) ;
    public final void rule__Definition__BodyAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3612:1: ( ( ruleBody ) )
            // InternalKap.g:3613:2: ( ruleBody )
            {
            // InternalKap.g:3613:2: ( ruleBody )
            // InternalKap.g:3614:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getBodyBodyParserRuleCall_4_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getBodyBodyParserRuleCall_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__BodyAssignment_4"


    // $ANTLR start "rule__ArgumentDeclaration__IdsAssignment_2_0"
    // InternalKap.g:3623:1: rule__ArgumentDeclaration__IdsAssignment_2_0 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__ArgumentDeclaration__IdsAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3627:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:3628:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:3628:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:3629:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getIdsLOWERCASE_IDTerminalRuleCall_2_0_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getIdsLOWERCASE_IDTerminalRuleCall_2_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__IdsAssignment_2_0"


    // $ANTLR start "rule__ArgumentDeclaration__IdsAssignment_2_1_1"
    // InternalKap.g:3638:1: rule__ArgumentDeclaration__IdsAssignment_2_1_1 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__ArgumentDeclaration__IdsAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3642:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:3643:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:3643:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:3644:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getIdsLOWERCASE_IDTerminalRuleCall_2_1_1_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getIdsLOWERCASE_IDTerminalRuleCall_2_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__IdsAssignment_2_1_1"


    // $ANTLR start "rule__ParameterList__PairsAssignment_0"
    // InternalKap.g:3653:1: rule__ParameterList__PairsAssignment_0 : ( ruleKeyValuePair ) ;
    public final void rule__ParameterList__PairsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3657:1: ( ( ruleKeyValuePair ) )
            // InternalKap.g:3658:2: ( ruleKeyValuePair )
            {
            // InternalKap.g:3658:2: ( ruleKeyValuePair )
            // InternalKap.g:3659:3: ruleKeyValuePair
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleKeyValuePair();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__PairsAssignment_0"


    // $ANTLR start "rule__ParameterList__PairsAssignment_1_1"
    // InternalKap.g:3668:1: rule__ParameterList__PairsAssignment_1_1 : ( ruleKeyValuePair ) ;
    public final void rule__ParameterList__PairsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3672:1: ( ( ruleKeyValuePair ) )
            // InternalKap.g:3673:2: ( ruleKeyValuePair )
            {
            // InternalKap.g:3673:2: ( ruleKeyValuePair )
            // InternalKap.g:3674:3: ruleKeyValuePair
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleKeyValuePair();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__PairsAssignment_1_1"


    // $ANTLR start "rule__KeyValuePair__NameAssignment_0_0"
    // InternalKap.g:3683:1: rule__KeyValuePair__NameAssignment_0_0 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__KeyValuePair__NameAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3687:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:3688:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:3688:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:3689:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__NameAssignment_0_0"


    // $ANTLR start "rule__KeyValuePair__InteractiveAssignment_0_1_0"
    // InternalKap.g:3698:1: rule__KeyValuePair__InteractiveAssignment_0_1_0 : ( ( '=?' ) ) ;
    public final void rule__KeyValuePair__InteractiveAssignment_0_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3702:1: ( ( ( '=?' ) ) )
            // InternalKap.g:3703:2: ( ( '=?' ) )
            {
            // InternalKap.g:3703:2: ( ( '=?' ) )
            // InternalKap.g:3704:3: ( '=?' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getInteractiveEqualsSignQuestionMarkKeyword_0_1_0_0()); 
            }
            // InternalKap.g:3705:3: ( '=?' )
            // InternalKap.g:3706:4: '=?'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getInteractiveEqualsSignQuestionMarkKeyword_0_1_0_0()); 
            }
            match(input,35,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getInteractiveEqualsSignQuestionMarkKeyword_0_1_0_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getInteractiveEqualsSignQuestionMarkKeyword_0_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__InteractiveAssignment_0_1_0"


    // $ANTLR start "rule__KeyValuePair__ValueAssignment_1"
    // InternalKap.g:3717:1: rule__KeyValuePair__ValueAssignment_1 : ( ruleValue ) ;
    public final void rule__KeyValuePair__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3721:1: ( ( ruleValue ) )
            // InternalKap.g:3722:2: ( ruleValue )
            {
            // InternalKap.g:3722:2: ( ruleValue )
            // InternalKap.g:3723:3: ruleValue
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getValueValueParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getValueValueParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__ValueAssignment_1"


    // $ANTLR start "rule__Value__LiteralAssignment_0"
    // InternalKap.g:3732:1: rule__Value__LiteralAssignment_0 : ( ruleLiteral ) ;
    public final void rule__Value__LiteralAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3736:1: ( ( ruleLiteral ) )
            // InternalKap.g:3737:2: ( ruleLiteral )
            {
            // InternalKap.g:3737:2: ( ruleLiteral )
            // InternalKap.g:3738:3: ruleLiteral
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getLiteralLiteralParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleLiteral();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getLiteralLiteralParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__LiteralAssignment_0"


    // $ANTLR start "rule__Value__IdAssignment_1"
    // InternalKap.g:3747:1: rule__Value__IdAssignment_1 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__Value__IdAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3751:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:3752:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:3752:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:3753:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getIdLOWERCASE_IDTerminalRuleCall_1_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getIdLOWERCASE_IDTerminalRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__IdAssignment_1"


    // $ANTLR start "rule__Value__ObservableAssignment_2"
    // InternalKap.g:3762:1: rule__Value__ObservableAssignment_2 : ( RULE_OBSERVABLE ) ;
    public final void rule__Value__ObservableAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3766:1: ( ( RULE_OBSERVABLE ) )
            // InternalKap.g:3767:2: ( RULE_OBSERVABLE )
            {
            // InternalKap.g:3767:2: ( RULE_OBSERVABLE )
            // InternalKap.g:3768:3: RULE_OBSERVABLE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getObservableOBSERVABLETerminalRuleCall_2_0()); 
            }
            match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getObservableOBSERVABLETerminalRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__ObservableAssignment_2"


    // $ANTLR start "rule__Value__ExpressionAssignment_3"
    // InternalKap.g:3777:1: rule__Value__ExpressionAssignment_3 : ( RULE_EXPR ) ;
    public final void rule__Value__ExpressionAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3781:1: ( ( RULE_EXPR ) )
            // InternalKap.g:3782:2: ( RULE_EXPR )
            {
            // InternalKap.g:3782:2: ( RULE_EXPR )
            // InternalKap.g:3783:3: RULE_EXPR
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getExpressionEXPRTerminalRuleCall_3_0()); 
            }
            match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getExpressionEXPRTerminalRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__ExpressionAssignment_3"


    // $ANTLR start "rule__Literal__NumberAssignment_0"
    // InternalKap.g:3792:1: rule__Literal__NumberAssignment_0 : ( ruleNumber ) ;
    public final void rule__Literal__NumberAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3796:1: ( ( ruleNumber ) )
            // InternalKap.g:3797:2: ( ruleNumber )
            {
            // InternalKap.g:3797:2: ( ruleNumber )
            // InternalKap.g:3798:3: ruleNumber
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getNumberNumberParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleNumber();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getNumberNumberParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__NumberAssignment_0"


    // $ANTLR start "rule__Literal__FromAssignment_1_0"
    // InternalKap.g:3807:1: rule__Literal__FromAssignment_1_0 : ( ruleNumber ) ;
    public final void rule__Literal__FromAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3811:1: ( ( ruleNumber ) )
            // InternalKap.g:3812:2: ( ruleNumber )
            {
            // InternalKap.g:3812:2: ( ruleNumber )
            // InternalKap.g:3813:3: ruleNumber
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleNumber();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__FromAssignment_1_0"


    // $ANTLR start "rule__Literal__ToAssignment_1_2"
    // InternalKap.g:3822:1: rule__Literal__ToAssignment_1_2 : ( ruleNumber ) ;
    public final void rule__Literal__ToAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3826:1: ( ( ruleNumber ) )
            // InternalKap.g:3827:2: ( ruleNumber )
            {
            // InternalKap.g:3827:2: ( ruleNumber )
            // InternalKap.g:3828:3: ruleNumber
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getToNumberParserRuleCall_1_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleNumber();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getToNumberParserRuleCall_1_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ToAssignment_1_2"


    // $ANTLR start "rule__Literal__StringAssignment_2"
    // InternalKap.g:3837:1: rule__Literal__StringAssignment_2 : ( RULE_STRING ) ;
    public final void rule__Literal__StringAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3841:1: ( ( RULE_STRING ) )
            // InternalKap.g:3842:2: ( RULE_STRING )
            {
            // InternalKap.g:3842:2: ( RULE_STRING )
            // InternalKap.g:3843:3: RULE_STRING
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getStringSTRINGTerminalRuleCall_2_0()); 
            }
            match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getStringSTRINGTerminalRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__StringAssignment_2"


    // $ANTLR start "rule__Literal__DateAssignment_3"
    // InternalKap.g:3852:1: rule__Literal__DateAssignment_3 : ( ruleDate ) ;
    public final void rule__Literal__DateAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3856:1: ( ( ruleDate ) )
            // InternalKap.g:3857:2: ( ruleDate )
            {
            // InternalKap.g:3857:2: ( ruleDate )
            // InternalKap.g:3858:3: ruleDate
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getDateDateParserRuleCall_3_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleDate();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getDateDateParserRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__DateAssignment_3"


    // $ANTLR start "rule__Literal__BooleanAssignment_4"
    // InternalKap.g:3867:1: rule__Literal__BooleanAssignment_4 : ( ( rule__Literal__BooleanAlternatives_4_0 ) ) ;
    public final void rule__Literal__BooleanAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3871:1: ( ( ( rule__Literal__BooleanAlternatives_4_0 ) ) )
            // InternalKap.g:3872:2: ( ( rule__Literal__BooleanAlternatives_4_0 ) )
            {
            // InternalKap.g:3872:2: ( ( rule__Literal__BooleanAlternatives_4_0 ) )
            // InternalKap.g:3873:3: ( rule__Literal__BooleanAlternatives_4_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getBooleanAlternatives_4_0()); 
            }
            // InternalKap.g:3874:3: ( rule__Literal__BooleanAlternatives_4_0 )
            // InternalKap.g:3874:4: rule__Literal__BooleanAlternatives_4_0
            {
            pushFollow(FOLLOW_2);
            rule__Literal__BooleanAlternatives_4_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getBooleanAlternatives_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__BooleanAssignment_4"


    // $ANTLR start "rule__Body__ListAssignment_0_1"
    // InternalKap.g:3882:1: rule__Body__ListAssignment_0_1 : ( ruleStatement ) ;
    public final void rule__Body__ListAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3886:1: ( ( ruleStatement ) )
            // InternalKap.g:3887:2: ( ruleStatement )
            {
            // InternalKap.g:3887:2: ( ruleStatement )
            // InternalKap.g:3888:3: ruleStatement
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__ListAssignment_0_1"


    // $ANTLR start "rule__Body__ListAssignment_0_2_1"
    // InternalKap.g:3897:1: rule__Body__ListAssignment_0_2_1 : ( ruleStatement ) ;
    public final void rule__Body__ListAssignment_0_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3901:1: ( ( ruleStatement ) )
            // InternalKap.g:3902:2: ( ruleStatement )
            {
            // InternalKap.g:3902:2: ( ruleStatement )
            // InternalKap.g:3903:3: ruleStatement
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_2_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__ListAssignment_0_2_1"


    // $ANTLR start "rule__Body__IsgroupAssignment_1_0"
    // InternalKap.g:3912:1: rule__Body__IsgroupAssignment_1_0 : ( ( '(' ) ) ;
    public final void rule__Body__IsgroupAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3916:1: ( ( ( '(' ) ) )
            // InternalKap.g:3917:2: ( ( '(' ) )
            {
            // InternalKap.g:3917:2: ( ( '(' ) )
            // InternalKap.g:3918:3: ( '(' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getIsgroupLeftParenthesisKeyword_1_0_0()); 
            }
            // InternalKap.g:3919:3: ( '(' )
            // InternalKap.g:3920:4: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getIsgroupLeftParenthesisKeyword_1_0_0()); 
            }
            match(input,27,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getIsgroupLeftParenthesisKeyword_1_0_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getIsgroupLeftParenthesisKeyword_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__IsgroupAssignment_1_0"


    // $ANTLR start "rule__Body__GroupAssignment_1_1_0"
    // InternalKap.g:3931:1: rule__Body__GroupAssignment_1_1_0 : ( ruleStatement ) ;
    public final void rule__Body__GroupAssignment_1_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3935:1: ( ( ruleStatement ) )
            // InternalKap.g:3936:2: ( ruleStatement )
            {
            // InternalKap.g:3936:2: ( ruleStatement )
            // InternalKap.g:3937:3: ruleStatement
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getGroupStatementParserRuleCall_1_1_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getGroupStatementParserRuleCall_1_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__GroupAssignment_1_1_0"


    // $ANTLR start "rule__Body__GroupAssignment_1_1_1_1"
    // InternalKap.g:3946:1: rule__Body__GroupAssignment_1_1_1_1 : ( ruleStatement ) ;
    public final void rule__Body__GroupAssignment_1_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3950:1: ( ( ruleStatement ) )
            // InternalKap.g:3951:2: ( ruleStatement )
            {
            // InternalKap.g:3951:2: ( ruleStatement )
            // InternalKap.g:3952:3: ruleStatement
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getGroupStatementParserRuleCall_1_1_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getGroupStatementParserRuleCall_1_1_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__GroupAssignment_1_1_1_1"


    // $ANTLR start "rule__Statement__CallAssignment_0"
    // InternalKap.g:3961:1: rule__Statement__CallAssignment_0 : ( ruleCall ) ;
    public final void rule__Statement__CallAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3965:1: ( ( ruleCall ) )
            // InternalKap.g:3966:2: ( ruleCall )
            {
            // InternalKap.g:3966:2: ( ruleCall )
            // InternalKap.g:3967:3: ruleCall
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getCallCallParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleCall();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getCallCallParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__CallAssignment_0"


    // $ANTLR start "rule__Statement__TextAssignment_1"
    // InternalKap.g:3976:1: rule__Statement__TextAssignment_1 : ( RULE_EMBEDDEDTEXT ) ;
    public final void rule__Statement__TextAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3980:1: ( ( RULE_EMBEDDEDTEXT ) )
            // InternalKap.g:3981:2: ( RULE_EMBEDDEDTEXT )
            {
            // InternalKap.g:3981:2: ( RULE_EMBEDDEDTEXT )
            // InternalKap.g:3982:3: RULE_EMBEDDEDTEXT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getTextEMBEDDEDTEXTTerminalRuleCall_1_0()); 
            }
            match(input,RULE_EMBEDDEDTEXT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getTextEMBEDDEDTEXTTerminalRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__TextAssignment_1"


    // $ANTLR start "rule__Statement__GroupAssignment_2_1"
    // InternalKap.g:3991:1: rule__Statement__GroupAssignment_2_1 : ( ruleCall ) ;
    public final void rule__Statement__GroupAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3995:1: ( ( ruleCall ) )
            // InternalKap.g:3996:2: ( ruleCall )
            {
            // InternalKap.g:3996:2: ( ruleCall )
            // InternalKap.g:3997:3: ruleCall
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getGroupCallParserRuleCall_2_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleCall();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getGroupCallParserRuleCall_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__GroupAssignment_2_1"


    // $ANTLR start "rule__Statement__GroupAssignment_2_2"
    // InternalKap.g:4006:1: rule__Statement__GroupAssignment_2_2 : ( ruleCall ) ;
    public final void rule__Statement__GroupAssignment_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4010:1: ( ( ruleCall ) )
            // InternalKap.g:4011:2: ( ruleCall )
            {
            // InternalKap.g:4011:2: ( ruleCall )
            // InternalKap.g:4012:3: ruleCall
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getGroupCallParserRuleCall_2_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleCall();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getGroupCallParserRuleCall_2_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__GroupAssignment_2_2"


    // $ANTLR start "rule__Call__NameAssignment_0"
    // InternalKap.g:4021:1: rule__Call__NameAssignment_0 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__Call__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4025:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:4026:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:4026:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:4027:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__NameAssignment_0"


    // $ANTLR start "rule__Call__ParametersAssignment_1_1"
    // InternalKap.g:4036:1: rule__Call__ParametersAssignment_1_1 : ( ruleParameterList ) ;
    public final void rule__Call__ParametersAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4040:1: ( ( ruleParameterList ) )
            // InternalKap.g:4041:2: ( ruleParameterList )
            {
            // InternalKap.g:4041:2: ( ruleParameterList )
            // InternalKap.g:4042:3: ruleParameterList
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getParametersParameterListParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleParameterList();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getParametersParameterListParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__ParametersAssignment_1_1"


    // $ANTLR start "rule__Call__ActionsAssignment_2_1"
    // InternalKap.g:4051:1: rule__Call__ActionsAssignment_2_1 : ( ruleActions ) ;
    public final void rule__Call__ActionsAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4055:1: ( ( ruleActions ) )
            // InternalKap.g:4056:2: ( ruleActions )
            {
            // InternalKap.g:4056:2: ( ruleActions )
            // InternalKap.g:4057:3: ruleActions
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getActionsActionsParserRuleCall_2_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleActions();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getActionsActionsParserRuleCall_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__ActionsAssignment_2_1"


    // $ANTLR start "rule__Actions__BodyAssignment_0"
    // InternalKap.g:4066:1: rule__Actions__BodyAssignment_0 : ( ruleBody ) ;
    public final void rule__Actions__BodyAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4070:1: ( ( ruleBody ) )
            // InternalKap.g:4071:2: ( ruleBody )
            {
            // InternalKap.g:4071:2: ( ruleBody )
            // InternalKap.g:4072:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getBodyBodyParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getBodyBodyParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__BodyAssignment_0"


    // $ANTLR start "rule__Actions__MatchAssignment_1"
    // InternalKap.g:4081:1: rule__Actions__MatchAssignment_1 : ( ruleMatch ) ;
    public final void rule__Actions__MatchAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4085:1: ( ( ruleMatch ) )
            // InternalKap.g:4086:2: ( ruleMatch )
            {
            // InternalKap.g:4086:2: ( ruleMatch )
            // InternalKap.g:4087:3: ruleMatch
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleMatch();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__MatchAssignment_1"


    // $ANTLR start "rule__Actions__MatchesAssignment_2_1"
    // InternalKap.g:4096:1: rule__Actions__MatchesAssignment_2_1 : ( ruleMatch ) ;
    public final void rule__Actions__MatchesAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4100:1: ( ( ruleMatch ) )
            // InternalKap.g:4101:2: ( ruleMatch )
            {
            // InternalKap.g:4101:2: ( ruleMatch )
            // InternalKap.g:4102:3: ruleMatch
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_2_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleMatch();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__MatchesAssignment_2_1"


    // $ANTLR start "rule__Actions__MatchesAssignment_2_2"
    // InternalKap.g:4111:1: rule__Actions__MatchesAssignment_2_2 : ( ruleMatch ) ;
    public final void rule__Actions__MatchesAssignment_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4115:1: ( ( ruleMatch ) )
            // InternalKap.g:4116:2: ( ruleMatch )
            {
            // InternalKap.g:4116:2: ( ruleMatch )
            // InternalKap.g:4117:3: ruleMatch
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_2_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleMatch();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_2_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__MatchesAssignment_2_2"


    // $ANTLR start "rule__Match__IdAssignment_0_0"
    // InternalKap.g:4126:1: rule__Match__IdAssignment_0_0 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__Match__IdAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4130:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:4131:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:4131:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:4132:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getIdLOWERCASE_IDTerminalRuleCall_0_0_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getIdLOWERCASE_IDTerminalRuleCall_0_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__IdAssignment_0_0"


    // $ANTLR start "rule__Match__BodyAssignment_0_2"
    // InternalKap.g:4141:1: rule__Match__BodyAssignment_0_2 : ( ruleBody ) ;
    public final void rule__Match__BodyAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4145:1: ( ( ruleBody ) )
            // InternalKap.g:4146:2: ( ruleBody )
            {
            // InternalKap.g:4146:2: ( ruleBody )
            // InternalKap.g:4147:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_0_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__BodyAssignment_0_2"


    // $ANTLR start "rule__Match__RegexpAssignment_1_0"
    // InternalKap.g:4156:1: rule__Match__RegexpAssignment_1_0 : ( RULE_REGEXP ) ;
    public final void rule__Match__RegexpAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4160:1: ( ( RULE_REGEXP ) )
            // InternalKap.g:4161:2: ( RULE_REGEXP )
            {
            // InternalKap.g:4161:2: ( RULE_REGEXP )
            // InternalKap.g:4162:3: RULE_REGEXP
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getRegexpREGEXPTerminalRuleCall_1_0_0()); 
            }
            match(input,RULE_REGEXP,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getRegexpREGEXPTerminalRuleCall_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__RegexpAssignment_1_0"


    // $ANTLR start "rule__Match__BodyAssignment_1_2"
    // InternalKap.g:4171:1: rule__Match__BodyAssignment_1_2 : ( ruleBody ) ;
    public final void rule__Match__BodyAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4175:1: ( ( ruleBody ) )
            // InternalKap.g:4176:2: ( ruleBody )
            {
            // InternalKap.g:4176:2: ( ruleBody )
            // InternalKap.g:4177:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_1_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_1_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__BodyAssignment_1_2"


    // $ANTLR start "rule__Match__ObservableAssignment_2_0"
    // InternalKap.g:4186:1: rule__Match__ObservableAssignment_2_0 : ( RULE_OBSERVABLE ) ;
    public final void rule__Match__ObservableAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4190:1: ( ( RULE_OBSERVABLE ) )
            // InternalKap.g:4191:2: ( RULE_OBSERVABLE )
            {
            // InternalKap.g:4191:2: ( RULE_OBSERVABLE )
            // InternalKap.g:4192:3: RULE_OBSERVABLE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getObservableOBSERVABLETerminalRuleCall_2_0_0()); 
            }
            match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getObservableOBSERVABLETerminalRuleCall_2_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__ObservableAssignment_2_0"


    // $ANTLR start "rule__Match__BodyAssignment_2_2"
    // InternalKap.g:4201:1: rule__Match__BodyAssignment_2_2 : ( ruleBody ) ;
    public final void rule__Match__BodyAssignment_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4205:1: ( ( ruleBody ) )
            // InternalKap.g:4206:2: ( ruleBody )
            {
            // InternalKap.g:4206:2: ( ruleBody )
            // InternalKap.g:4207:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_2_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_2_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__BodyAssignment_2_2"


    // $ANTLR start "rule__Match__LiteralAssignment_3_0"
    // InternalKap.g:4216:1: rule__Match__LiteralAssignment_3_0 : ( ruleLiteral ) ;
    public final void rule__Match__LiteralAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4220:1: ( ( ruleLiteral ) )
            // InternalKap.g:4221:2: ( ruleLiteral )
            {
            // InternalKap.g:4221:2: ( ruleLiteral )
            // InternalKap.g:4222:3: ruleLiteral
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_3_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleLiteral();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_3_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__LiteralAssignment_3_0"


    // $ANTLR start "rule__Match__BodyAssignment_3_2"
    // InternalKap.g:4231:1: rule__Match__BodyAssignment_3_2 : ( ruleBody ) ;
    public final void rule__Match__BodyAssignment_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4235:1: ( ( ruleBody ) )
            // InternalKap.g:4236:2: ( ruleBody )
            {
            // InternalKap.g:4236:2: ( ruleBody )
            // InternalKap.g:4237:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_3_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_3_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__BodyAssignment_3_2"


    // $ANTLR start "rule__Match__TextAssignment_4_0"
    // InternalKap.g:4246:1: rule__Match__TextAssignment_4_0 : ( RULE_STRING ) ;
    public final void rule__Match__TextAssignment_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4250:1: ( ( RULE_STRING ) )
            // InternalKap.g:4251:2: ( RULE_STRING )
            {
            // InternalKap.g:4251:2: ( RULE_STRING )
            // InternalKap.g:4252:3: RULE_STRING
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getTextSTRINGTerminalRuleCall_4_0_0()); 
            }
            match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getTextSTRINGTerminalRuleCall_4_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__TextAssignment_4_0"


    // $ANTLR start "rule__Match__BodyAssignment_4_2"
    // InternalKap.g:4261:1: rule__Match__BodyAssignment_4_2 : ( ruleBody ) ;
    public final void rule__Match__BodyAssignment_4_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4265:1: ( ( ruleBody ) )
            // InternalKap.g:4266:2: ( ruleBody )
            {
            // InternalKap.g:4266:2: ( ruleBody )
            // InternalKap.g:4267:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_4_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_4_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__BodyAssignment_4_2"


    // $ANTLR start "rule__Match__ArgumentsAssignment_5_0"
    // InternalKap.g:4276:1: rule__Match__ArgumentsAssignment_5_0 : ( ruleArgumentDeclaration ) ;
    public final void rule__Match__ArgumentsAssignment_5_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4280:1: ( ( ruleArgumentDeclaration ) )
            // InternalKap.g:4281:2: ( ruleArgumentDeclaration )
            {
            // InternalKap.g:4281:2: ( ruleArgumentDeclaration )
            // InternalKap.g:4282:3: ruleArgumentDeclaration
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getArgumentsArgumentDeclarationParserRuleCall_5_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleArgumentDeclaration();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getArgumentsArgumentDeclarationParserRuleCall_5_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__ArgumentsAssignment_5_0"


    // $ANTLR start "rule__Match__BodyAssignment_5_2"
    // InternalKap.g:4291:1: rule__Match__BodyAssignment_5_2 : ( ruleBody ) ;
    public final void rule__Match__BodyAssignment_5_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4295:1: ( ( ruleBody ) )
            // InternalKap.g:4296:2: ( ruleBody )
            {
            // InternalKap.g:4296:2: ( ruleBody )
            // InternalKap.g:4297:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_5_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_5_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__BodyAssignment_5_2"


    // $ANTLR start "rule__Number__NegativeAssignment_0_1"
    // InternalKap.g:4306:1: rule__Number__NegativeAssignment_0_1 : ( ( '-' ) ) ;
    public final void rule__Number__NegativeAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4310:1: ( ( ( '-' ) ) )
            // InternalKap.g:4311:2: ( ( '-' ) )
            {
            // InternalKap.g:4311:2: ( ( '-' ) )
            // InternalKap.g:4312:3: ( '-' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getNegativeHyphenMinusKeyword_0_1_0()); 
            }
            // InternalKap.g:4313:3: ( '-' )
            // InternalKap.g:4314:4: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getNegativeHyphenMinusKeyword_0_1_0()); 
            }
            match(input,33,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getNegativeHyphenMinusKeyword_0_1_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getNegativeHyphenMinusKeyword_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__NegativeAssignment_0_1"


    // $ANTLR start "rule__Number__RealAssignment_1"
    // InternalKap.g:4325:1: rule__Number__RealAssignment_1 : ( RULE_INT ) ;
    public final void rule__Number__RealAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4329:1: ( ( RULE_INT ) )
            // InternalKap.g:4330:2: ( RULE_INT )
            {
            // InternalKap.g:4330:2: ( RULE_INT )
            // InternalKap.g:4331:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getRealINTTerminalRuleCall_1_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getRealINTTerminalRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__RealAssignment_1"


    // $ANTLR start "rule__Number__LongAssignment_2"
    // InternalKap.g:4340:1: rule__Number__LongAssignment_2 : ( ( 'l' ) ) ;
    public final void rule__Number__LongAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4344:1: ( ( ( 'l' ) ) )
            // InternalKap.g:4345:2: ( ( 'l' ) )
            {
            // InternalKap.g:4345:2: ( ( 'l' ) )
            // InternalKap.g:4346:3: ( 'l' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getLongLKeyword_2_0()); 
            }
            // InternalKap.g:4347:3: ( 'l' )
            // InternalKap.g:4348:4: 'l'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getLongLKeyword_2_0()); 
            }
            match(input,36,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getLongLKeyword_2_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getLongLKeyword_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__LongAssignment_2"


    // $ANTLR start "rule__Number__DecimalAssignment_3_0_0"
    // InternalKap.g:4359:1: rule__Number__DecimalAssignment_3_0_0 : ( ( '.' ) ) ;
    public final void rule__Number__DecimalAssignment_3_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4363:1: ( ( ( '.' ) ) )
            // InternalKap.g:4364:2: ( ( '.' ) )
            {
            // InternalKap.g:4364:2: ( ( '.' ) )
            // InternalKap.g:4365:3: ( '.' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getDecimalFullStopKeyword_3_0_0_0()); 
            }
            // InternalKap.g:4366:3: ( '.' )
            // InternalKap.g:4367:4: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getDecimalFullStopKeyword_3_0_0_0()); 
            }
            match(input,34,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getDecimalFullStopKeyword_3_0_0_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getDecimalFullStopKeyword_3_0_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__DecimalAssignment_3_0_0"


    // $ANTLR start "rule__Number__DecimalPartAssignment_3_0_1"
    // InternalKap.g:4378:1: rule__Number__DecimalPartAssignment_3_0_1 : ( RULE_INT ) ;
    public final void rule__Number__DecimalPartAssignment_3_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4382:1: ( ( RULE_INT ) )
            // InternalKap.g:4383:2: ( RULE_INT )
            {
            // InternalKap.g:4383:2: ( RULE_INT )
            // InternalKap.g:4384:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getDecimalPartINTTerminalRuleCall_3_0_1_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getDecimalPartINTTerminalRuleCall_3_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__DecimalPartAssignment_3_0_1"


    // $ANTLR start "rule__Number__ExponentialAssignment_4_0_0"
    // InternalKap.g:4393:1: rule__Number__ExponentialAssignment_4_0_0 : ( ( rule__Number__ExponentialAlternatives_4_0_0_0 ) ) ;
    public final void rule__Number__ExponentialAssignment_4_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4397:1: ( ( ( rule__Number__ExponentialAlternatives_4_0_0_0 ) ) )
            // InternalKap.g:4398:2: ( ( rule__Number__ExponentialAlternatives_4_0_0_0 ) )
            {
            // InternalKap.g:4398:2: ( ( rule__Number__ExponentialAlternatives_4_0_0_0 ) )
            // InternalKap.g:4399:3: ( rule__Number__ExponentialAlternatives_4_0_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getExponentialAlternatives_4_0_0_0()); 
            }
            // InternalKap.g:4400:3: ( rule__Number__ExponentialAlternatives_4_0_0_0 )
            // InternalKap.g:4400:4: rule__Number__ExponentialAlternatives_4_0_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Number__ExponentialAlternatives_4_0_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getExponentialAlternatives_4_0_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__ExponentialAssignment_4_0_0"


    // $ANTLR start "rule__Number__ExpNegativeAssignment_4_0_1_1"
    // InternalKap.g:4408:1: rule__Number__ExpNegativeAssignment_4_0_1_1 : ( ( '-' ) ) ;
    public final void rule__Number__ExpNegativeAssignment_4_0_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4412:1: ( ( ( '-' ) ) )
            // InternalKap.g:4413:2: ( ( '-' ) )
            {
            // InternalKap.g:4413:2: ( ( '-' ) )
            // InternalKap.g:4414:3: ( '-' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getExpNegativeHyphenMinusKeyword_4_0_1_1_0()); 
            }
            // InternalKap.g:4415:3: ( '-' )
            // InternalKap.g:4416:4: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getExpNegativeHyphenMinusKeyword_4_0_1_1_0()); 
            }
            match(input,33,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getExpNegativeHyphenMinusKeyword_4_0_1_1_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getExpNegativeHyphenMinusKeyword_4_0_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__ExpNegativeAssignment_4_0_1_1"


    // $ANTLR start "rule__Number__ExpAssignment_4_0_2"
    // InternalKap.g:4427:1: rule__Number__ExpAssignment_4_0_2 : ( RULE_INT ) ;
    public final void rule__Number__ExpAssignment_4_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4431:1: ( ( RULE_INT ) )
            // InternalKap.g:4432:2: ( RULE_INT )
            {
            // InternalKap.g:4432:2: ( RULE_INT )
            // InternalKap.g:4433:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getExpINTTerminalRuleCall_4_0_2_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getExpINTTerminalRuleCall_4_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__ExpAssignment_4_0_2"


    // $ANTLR start "rule__Date__YearAssignment_0"
    // InternalKap.g:4442:1: rule__Date__YearAssignment_0 : ( RULE_INT ) ;
    public final void rule__Date__YearAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4446:1: ( ( RULE_INT ) )
            // InternalKap.g:4447:2: ( RULE_INT )
            {
            // InternalKap.g:4447:2: ( RULE_INT )
            // InternalKap.g:4448:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getYearINTTerminalRuleCall_0_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getYearINTTerminalRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__YearAssignment_0"


    // $ANTLR start "rule__Date__BcAssignment_1_2"
    // InternalKap.g:4457:1: rule__Date__BcAssignment_1_2 : ( ( 'BC' ) ) ;
    public final void rule__Date__BcAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4461:1: ( ( ( 'BC' ) ) )
            // InternalKap.g:4462:2: ( ( 'BC' ) )
            {
            // InternalKap.g:4462:2: ( ( 'BC' ) )
            // InternalKap.g:4463:3: ( 'BC' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getBcBCKeyword_1_2_0()); 
            }
            // InternalKap.g:4464:3: ( 'BC' )
            // InternalKap.g:4465:4: 'BC'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getBcBCKeyword_1_2_0()); 
            }
            match(input,37,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getBcBCKeyword_1_2_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getBcBCKeyword_1_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__BcAssignment_1_2"


    // $ANTLR start "rule__Date__MonthAssignment_3"
    // InternalKap.g:4476:1: rule__Date__MonthAssignment_3 : ( RULE_INT ) ;
    public final void rule__Date__MonthAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4480:1: ( ( RULE_INT ) )
            // InternalKap.g:4481:2: ( RULE_INT )
            {
            // InternalKap.g:4481:2: ( RULE_INT )
            // InternalKap.g:4482:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getMonthINTTerminalRuleCall_3_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getMonthINTTerminalRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__MonthAssignment_3"


    // $ANTLR start "rule__Date__DayAssignment_5"
    // InternalKap.g:4491:1: rule__Date__DayAssignment_5 : ( RULE_INT ) ;
    public final void rule__Date__DayAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4495:1: ( ( RULE_INT ) )
            // InternalKap.g:4496:2: ( RULE_INT )
            {
            // InternalKap.g:4496:2: ( RULE_INT )
            // InternalKap.g:4497:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getDayINTTerminalRuleCall_5_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getDayINTTerminalRuleCall_5_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__DayAssignment_5"


    // $ANTLR start "rule__Date__HourAssignment_6_0"
    // InternalKap.g:4506:1: rule__Date__HourAssignment_6_0 : ( RULE_INT ) ;
    public final void rule__Date__HourAssignment_6_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4510:1: ( ( RULE_INT ) )
            // InternalKap.g:4511:2: ( RULE_INT )
            {
            // InternalKap.g:4511:2: ( RULE_INT )
            // InternalKap.g:4512:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getHourINTTerminalRuleCall_6_0_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getHourINTTerminalRuleCall_6_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__HourAssignment_6_0"


    // $ANTLR start "rule__Date__MinAssignment_6_2"
    // InternalKap.g:4521:1: rule__Date__MinAssignment_6_2 : ( RULE_INT ) ;
    public final void rule__Date__MinAssignment_6_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4525:1: ( ( RULE_INT ) )
            // InternalKap.g:4526:2: ( RULE_INT )
            {
            // InternalKap.g:4526:2: ( RULE_INT )
            // InternalKap.g:4527:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getMinINTTerminalRuleCall_6_2_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getMinINTTerminalRuleCall_6_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__MinAssignment_6_2"


    // $ANTLR start "rule__Date__SecAssignment_6_3_1"
    // InternalKap.g:4536:1: rule__Date__SecAssignment_6_3_1 : ( RULE_INT ) ;
    public final void rule__Date__SecAssignment_6_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4540:1: ( ( RULE_INT ) )
            // InternalKap.g:4541:2: ( RULE_INT )
            {
            // InternalKap.g:4541:2: ( RULE_INT )
            // InternalKap.g:4542:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getSecINTTerminalRuleCall_6_3_1_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getSecINTTerminalRuleCall_6_3_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__SecAssignment_6_3_1"


    // $ANTLR start "rule__Date__MsAssignment_6_3_2_1"
    // InternalKap.g:4551:1: rule__Date__MsAssignment_6_3_2_1 : ( RULE_INT ) ;
    public final void rule__Date__MsAssignment_6_3_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4555:1: ( ( RULE_INT ) )
            // InternalKap.g:4556:2: ( RULE_INT )
            {
            // InternalKap.g:4556:2: ( RULE_INT )
            // InternalKap.g:4557:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getMsINTTerminalRuleCall_6_3_2_1_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getMsINTTerminalRuleCall_6_3_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__MsAssignment_6_3_2_1"

    // $ANTLR start synpred10_InternalKap
    public final void synpred10_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:547:2: ( ( ( rule__Body__Group_0__0 ) ) )
        // InternalKap.g:547:2: ( ( rule__Body__Group_0__0 ) )
        {
        // InternalKap.g:547:2: ( ( rule__Body__Group_0__0 ) )
        // InternalKap.g:548:3: ( rule__Body__Group_0__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getBodyAccess().getGroup_0()); 
        }
        // InternalKap.g:549:3: ( rule__Body__Group_0__0 )
        // InternalKap.g:549:4: rule__Body__Group_0__0
        {
        pushFollow(FOLLOW_2);
        rule__Body__Group_0__0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred10_InternalKap

    // $ANTLR start synpred18_InternalKap
    public final void synpred18_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:640:2: ( ( ( rule__Match__Group_3__0 ) ) )
        // InternalKap.g:640:2: ( ( rule__Match__Group_3__0 ) )
        {
        // InternalKap.g:640:2: ( ( rule__Match__Group_3__0 ) )
        // InternalKap.g:641:3: ( rule__Match__Group_3__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getMatchAccess().getGroup_3()); 
        }
        // InternalKap.g:642:3: ( rule__Match__Group_3__0 )
        // InternalKap.g:642:4: rule__Match__Group_3__0
        {
        pushFollow(FOLLOW_2);
        rule__Match__Group_3__0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred18_InternalKap

    // $ANTLR start synpred19_InternalKap
    public final void synpred19_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:646:2: ( ( ( rule__Match__Group_4__0 ) ) )
        // InternalKap.g:646:2: ( ( rule__Match__Group_4__0 ) )
        {
        // InternalKap.g:646:2: ( ( rule__Match__Group_4__0 ) )
        // InternalKap.g:647:3: ( rule__Match__Group_4__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getMatchAccess().getGroup_4()); 
        }
        // InternalKap.g:648:3: ( rule__Match__Group_4__0 )
        // InternalKap.g:648:4: rule__Match__Group_4__0
        {
        pushFollow(FOLLOW_2);
        rule__Match__Group_4__0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred19_InternalKap

    // $ANTLR start synpred32_InternalKap
    public final void synpred32_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:1607:3: ( rule__Body__Group_0_2__0 )
        // InternalKap.g:1607:3: rule__Body__Group_0_2__0
        {
        pushFollow(FOLLOW_2);
        rule__Body__Group_0_2__0();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred32_InternalKap

    // Delegated rules

    public final boolean synpred19_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred32_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred32_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA8 dfa8 = new DFA8(this);
    protected DFA24 dfa24 = new DFA24(this);
    static final String dfa_1s = "\22\uffff";
    static final String dfa_2s = "\3\uffff\1\13\2\uffff\2\13\6\uffff\1\13\2\uffff\1\13";
    static final String dfa_3s = "\1\7\2\12\1\24\2\uffff\2\24\3\12\3\uffff\1\24\2\12\1\34";
    static final String dfa_4s = "\1\41\2\12\1\45\2\uffff\1\44\1\42\1\12\2\41\3\uffff\1\40\2\12\1\40";
    static final String dfa_5s = "\4\uffff\1\3\1\5\5\uffff\1\1\1\2\1\4\4\uffff";
    static final String dfa_6s = "\22\uffff}>";
    static final String[] dfa_7s = {
            "\1\4\2\uffff\1\3\6\uffff\2\5\1\1\15\uffff\1\2",
            "\1\6",
            "\1\6",
            "\1\11\1\12\2\15\4\uffff\2\13\1\14\1\uffff\1\13\1\15\1\10\1\uffff\1\7\1\15",
            "",
            "",
            "\1\11\1\12\6\uffff\2\13\1\14\1\uffff\1\13\1\uffff\1\10\1\uffff\1\7",
            "\1\11\1\12\6\uffff\2\13\1\14\1\uffff\1\13\1\uffff\1\10",
            "\1\16",
            "\1\21\10\uffff\1\17\15\uffff\1\20",
            "\1\21\10\uffff\1\17\15\uffff\1\20",
            "",
            "",
            "",
            "\1\11\1\12\6\uffff\2\13\1\14\1\uffff\1\13",
            "\1\21",
            "\1\21",
            "\2\13\1\14\1\uffff\1\13"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "482:1: rule__Literal__Alternatives : ( ( ( rule__Literal__NumberAssignment_0 ) ) | ( ( rule__Literal__Group_1__0 ) ) | ( ( rule__Literal__StringAssignment_2 ) ) | ( ( rule__Literal__DateAssignment_3 ) ) | ( ( rule__Literal__BooleanAssignment_4 ) ) );";
        }
    }
    static final String dfa_8s = "\14\uffff";
    static final String dfa_9s = "\1\uffff\1\2\6\uffff\2\2\2\uffff";
    static final String dfa_10s = "\2\4\1\uffff\1\4\1\uffff\1\4\1\uffff\4\4\1\34";
    static final String dfa_11s = "\2\41\1\uffff\1\41\1\uffff\1\40\1\uffff\1\34\2\41\1\35\1\40";
    static final String dfa_12s = "\2\uffff\1\1\1\uffff\1\2\1\uffff\1\3\5\uffff";
    static final String dfa_13s = "\14\uffff}>";
    static final String[] dfa_14s = {
            "\1\1\1\4\1\uffff\1\4\1\2\2\4\6\uffff\3\4\7\uffff\1\3\5\uffff\1\4",
            "\2\2\1\uffff\1\2\1\uffff\2\2\6\uffff\3\2\5\uffff\4\2\2\uffff\1\2\1\4\1\2",
            "",
            "\1\5\1\6\1\uffff\1\6\1\2\2\6\6\uffff\3\6\7\uffff\1\7\1\10\4\uffff\1\6",
            "",
            "\1\2\25\uffff\2\2\1\11\1\4\1\uffff\1\2\1\6",
            "",
            "\1\12\27\uffff\1\6",
            "\2\2\1\uffff\1\2\1\uffff\2\2\6\uffff\3\2\5\uffff\1\2\1\uffff\2\2\2\uffff\1\2\1\4\1\2",
            "\2\2\1\uffff\1\2\1\uffff\2\2\6\uffff\3\2\5\uffff\1\2\1\uffff\2\2\2\uffff\1\2\1\4\1\2",
            "\1\2\25\uffff\2\2\1\13\1\6",
            "\1\2\2\uffff\1\2\1\6"
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "590:1: rule__Actions__Alternatives : ( ( ( rule__Actions__BodyAssignment_0 ) ) | ( ( rule__Actions__MatchAssignment_1 ) ) | ( ( rule__Actions__Group_2__0 ) ) );";
        }
    }
    static final String dfa_15s = "\1\4\6\uffff\1\0\4\uffff";
    static final String dfa_16s = "\1\41\6\uffff\1\0\4\uffff";
    static final String dfa_17s = "\1\uffff\1\1\1\2\1\3\1\4\5\uffff\1\6\1\5";
    static final String dfa_18s = "\7\uffff\1\0\4\uffff}>";
    static final String[] dfa_19s = {
            "\1\1\1\3\1\uffff\1\7\1\uffff\1\2\1\4\6\uffff\3\4\7\uffff\1\12\5\uffff\1\4",
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
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[][] dfa_19 = unpackEncodedStringArray(dfa_19s);

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = dfa_8;
            this.eof = dfa_8;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "617:1: rule__Match__Alternatives : ( ( ( rule__Match__Group_0__0 ) ) | ( ( rule__Match__Group_1__0 ) ) | ( ( rule__Match__Group_2__0 ) ) | ( ( rule__Match__Group_3__0 ) ) | ( ( rule__Match__Group_4__0 ) ) | ( ( rule__Match__Group_5__0 ) ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA8_7 = input.LA(1);

                         
                        int index8_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_InternalKap()) ) {s = 4;}

                        else if ( (synpred19_InternalKap()) ) {s = 11;}

                         
                        input.seek(index8_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 8, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_20s = "\10\uffff";
    static final String dfa_21s = "\1\2\3\uffff\1\5\3\uffff";
    static final String dfa_22s = "\2\4\1\uffff\1\20\1\4\1\uffff\1\4\1\20";
    static final String dfa_23s = "\2\41\1\uffff\1\43\1\41\1\uffff\1\41\1\43";
    static final String dfa_24s = "\2\uffff\1\2\2\uffff\1\1\2\uffff";
    static final String dfa_25s = "\10\uffff}>";
    static final String[] dfa_26s = {
            "\2\2\1\uffff\1\2\1\uffff\2\2\6\uffff\3\2\5\uffff\2\2\1\1\1\2\2\uffff\1\2\1\uffff\1\2",
            "\1\3\3\5\2\uffff\1\5\6\uffff\3\5\10\uffff\1\4\4\uffff\1\5",
            "",
            "\1\5\13\uffff\1\4\1\6\5\uffff\1\5",
            "\2\5\1\uffff\1\5\1\uffff\2\5\6\uffff\3\5\5\uffff\4\5\2\uffff\1\5\1\2\1\5",
            "",
            "\1\7\3\5\2\uffff\1\5\6\uffff\3\5\15\uffff\1\5",
            "\1\5\13\uffff\1\4\1\6\5\uffff\1\5"
    };

    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final char[] dfa_22 = DFA.unpackEncodedStringToUnsignedChars(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final short[] dfa_24 = DFA.unpackEncodedString(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[][] dfa_26 = unpackEncodedStringArray(dfa_26s);

    class DFA24 extends DFA {

        public DFA24(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 24;
            this.eot = dfa_20;
            this.eof = dfa_21;
            this.min = dfa_22;
            this.max = dfa_23;
            this.accept = dfa_24;
            this.special = dfa_25;
            this.transition = dfa_26;
        }
        public String getDescription() {
            return "2013:2: ( rule__Call__Group_1__0 )?";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000003000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x000000000C000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000008000110L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000010000010L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x00000002000E04F0L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000800010000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000200080400L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000018000110L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x00000002100E04F0L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x00000002080E07B0L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x00000002080E06B0L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x00000002180E06B0L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x00000002080E06B2L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000001400300000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000002200C00000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000400000000L});

}
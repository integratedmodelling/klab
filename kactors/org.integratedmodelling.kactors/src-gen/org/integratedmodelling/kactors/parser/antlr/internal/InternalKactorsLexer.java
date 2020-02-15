package org.integratedmodelling.kactors.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalKactorsLexer extends Lexer {
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

    public InternalKactorsLexer() {;} 
    public InternalKactorsLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalKactorsLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalKactors.g"; }

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:11:7: ( 'name' )
            // InternalKactors.g:11:9: 'name'
            {
            match("name"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:12:7: ( 'import' )
            // InternalKactors.g:12:9: 'import'
            {
            match("import"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:13:7: ( ',' )
            // InternalKactors.g:13:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:14:7: ( 'worldview' )
            // InternalKactors.g:14:9: 'worldview'
            {
            match("worldview"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:15:7: ( 'label' )
            // InternalKactors.g:15:9: 'label'
            {
            match("label"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:16:7: ( 'description' )
            // InternalKactors.g:16:9: 'description'
            {
            match("description"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:17:7: ( 'permissions' )
            // InternalKactors.g:17:9: 'permissions'
            {
            match("permissions"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:18:7: ( 'author' )
            // InternalKactors.g:18:9: 'author'
            {
            match("author"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:19:7: ( 'version' )
            // InternalKactors.g:19:9: 'version'
            {
            match("version"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:20:7: ( 'created' )
            // InternalKactors.g:20:9: 'created'
            {
            match("created"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:21:7: ( 'modified' )
            // InternalKactors.g:21:9: 'modified'
            {
            match("modified"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:22:7: ( 'def' )
            // InternalKactors.g:22:9: 'def'
            {
            match("def"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:23:7: ( ':' )
            // InternalKactors.g:23:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:24:7: ( '(' )
            // InternalKactors.g:24:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:25:7: ( ')' )
            // InternalKactors.g:25:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:26:7: ( '=?' )
            // InternalKactors.g:26:9: '=?'
            {
            match("=?"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:27:7: ( '=' )
            // InternalKactors.g:27:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:28:7: ( 'urn:klab:' )
            // InternalKactors.g:28:9: 'urn:klab:'
            {
            match("urn:klab:"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:29:7: ( '#' )
            // InternalKactors.g:29:9: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:30:7: ( '&' )
            // InternalKactors.g:30:9: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:31:7: ( '{' )
            // InternalKactors.g:31:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:32:7: ( '}' )
            // InternalKactors.g:32:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:33:7: ( 'true' )
            // InternalKactors.g:33:9: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:34:7: ( 'false' )
            // InternalKactors.g:34:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:35:7: ( 'inclusive' )
            // InternalKactors.g:35:9: 'inclusive'
            {
            match("inclusive"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:36:7: ( 'exclusive' )
            // InternalKactors.g:36:9: 'exclusive'
            {
            match("exclusive"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:37:7: ( 'to' )
            // InternalKactors.g:37:9: 'to'
            {
            match("to"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:38:7: ( 'in' )
            // InternalKactors.g:38:9: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:39:7: ( 'unknown' )
            // InternalKactors.g:39:9: 'unknown'
            {
            match("unknown"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:40:7: ( '*' )
            // InternalKactors.g:40:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:41:7: ( '{{' )
            // InternalKactors.g:41:9: '{{'
            {
            match("{{"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:42:7: ( '}}' )
            // InternalKactors.g:42:9: '}}'
            {
            match("}}"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:43:7: ( '|' )
            // InternalKactors.g:43:9: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:44:7: ( '/' )
            // InternalKactors.g:44:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:45:7: ( '.' )
            // InternalKactors.g:45:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:46:7: ( '@' )
            // InternalKactors.g:46:9: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:47:7: ( '>' )
            // InternalKactors.g:47:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:48:7: ( '<' )
            // InternalKactors.g:48:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:49:7: ( '!=' )
            // InternalKactors.g:49:9: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:50:7: ( '<=' )
            // InternalKactors.g:50:9: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:51:7: ( '>=' )
            // InternalKactors.g:51:9: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:52:7: ( 'if' )
            // InternalKactors.g:52:9: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:53:7: ( 'else' )
            // InternalKactors.g:53:9: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:54:7: ( 'while' )
            // InternalKactors.g:54:9: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:55:7: ( 'do' )
            // InternalKactors.g:55:9: 'do'
            {
            match("do"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:56:7: ( 'for' )
            // InternalKactors.g:56:9: 'for'
            {
            match("for"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:57:7: ( ';' )
            // InternalKactors.g:57:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:58:7: ( '->' )
            // InternalKactors.g:58:9: '->'
            {
            match("->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:59:7: ( '+' )
            // InternalKactors.g:59:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:60:7: ( '-' )
            // InternalKactors.g:60:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:61:7: ( 'l' )
            // InternalKactors.g:61:9: 'l'
            {
            match('l'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:62:7: ( 'e' )
            // InternalKactors.g:62:9: 'e'
            {
            match('e'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:63:7: ( 'E' )
            // InternalKactors.g:63:9: 'E'
            {
            match('E'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "T__75"
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:64:7: ( 'AD' )
            // InternalKactors.g:64:9: 'AD'
            {
            match("AD"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__75"

    // $ANTLR start "T__76"
    public final void mT__76() throws RecognitionException {
        try {
            int _type = T__76;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:65:7: ( 'CE' )
            // InternalKactors.g:65:9: 'CE'
            {
            match("CE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__76"

    // $ANTLR start "T__77"
    public final void mT__77() throws RecognitionException {
        try {
            int _type = T__77;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:66:7: ( 'BC' )
            // InternalKactors.g:66:9: 'BC'
            {
            match("BC"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__77"

    // $ANTLR start "T__78"
    public final void mT__78() throws RecognitionException {
        try {
            int _type = T__78;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:67:7: ( '^' )
            // InternalKactors.g:67:9: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__78"

    // $ANTLR start "RULE_UPPERCASE_ID"
    public final void mRULE_UPPERCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_UPPERCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5588:19: ( 'A' .. 'Z' ( 'A' .. 'Z' | '_' )* )
            // InternalKactors.g:5588:21: 'A' .. 'Z' ( 'A' .. 'Z' | '_' )*
            {
            matchRange('A','Z'); 
            // InternalKactors.g:5588:30: ( 'A' .. 'Z' | '_' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='A' && LA1_0<='Z')||LA1_0=='_') ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalKactors.g:
            	    {
            	    if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_UPPERCASE_ID"

    // $ANTLR start "RULE_UPPERCASE_PATH"
    public final void mRULE_UPPERCASE_PATH() throws RecognitionException {
        try {
            int _type = RULE_UPPERCASE_PATH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5590:21: ( RULE_UPPERCASE_ID ( '.' RULE_UPPERCASE_ID )* )
            // InternalKactors.g:5590:23: RULE_UPPERCASE_ID ( '.' RULE_UPPERCASE_ID )*
            {
            mRULE_UPPERCASE_ID(); 
            // InternalKactors.g:5590:41: ( '.' RULE_UPPERCASE_ID )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='.') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalKactors.g:5590:42: '.' RULE_UPPERCASE_ID
            	    {
            	    match('.'); 
            	    mRULE_UPPERCASE_ID(); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_UPPERCASE_PATH"

    // $ANTLR start "RULE_CAMELCASE_ID"
    public final void mRULE_CAMELCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_CAMELCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5592:19: ( 'A' .. 'Z' ( 'A' .. 'z' | '0' .. '9' )* )
            // InternalKactors.g:5592:21: 'A' .. 'Z' ( 'A' .. 'z' | '0' .. '9' )*
            {
            matchRange('A','Z'); 
            // InternalKactors.g:5592:30: ( 'A' .. 'z' | '0' .. '9' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalKactors.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_CAMELCASE_ID"

    // $ANTLR start "RULE_LOWERCASE_ID"
    public final void mRULE_LOWERCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_LOWERCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5594:19: ( 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )* )
            // InternalKactors.g:5594:21: 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )*
            {
            matchRange('a','z'); 
            // InternalKactors.g:5594:30: ( 'a' .. 'z' | '0' .. '9' | '_' )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalKactors.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_LOWERCASE_ID"

    // $ANTLR start "RULE_ARGVALUE"
    public final void mRULE_ARGVALUE() throws RecognitionException {
        try {
            int _type = RULE_ARGVALUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5596:15: ( '$' ( '0' .. '9' )* )
            // InternalKactors.g:5596:17: '$' ( '0' .. '9' )*
            {
            match('$'); 
            // InternalKactors.g:5596:21: ( '0' .. '9' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalKactors.g:5596:22: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ARGVALUE"

    // $ANTLR start "RULE_EXPR"
    public final void mRULE_EXPR() throws RecognitionException {
        try {
            int _type = RULE_EXPR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5598:11: ( '[' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )* ']' )
            // InternalKactors.g:5598:13: '[' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )* ']'
            {
            match('['); 
            // InternalKactors.g:5598:17: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )*
            loop6:
            do {
                int alt6=3;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='\\') ) {
                    alt6=1;
                }
                else if ( ((LA6_0>='\u0000' && LA6_0<='[')||(LA6_0>='^' && LA6_0<='\uFFFF')) ) {
                    alt6=2;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalKactors.g:5598:18: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' )
            	    {
            	    match('\\'); 
            	    if ( (input.LA(1)>='\\' && input.LA(1)<=']')||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||(input.LA(1)>='t' && input.LA(1)<='u') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // InternalKactors.g:5598:58: ~ ( ( '\\\\' | ']' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='[')||(input.LA(1)>='^' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_EXPR"

    // $ANTLR start "RULE_EMBEDDEDTEXT"
    public final void mRULE_EMBEDDEDTEXT() throws RecognitionException {
        try {
            int _type = RULE_EMBEDDEDTEXT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5600:19: ( '%%%' ( ( ' ' )* '-' )* '\\r' ( options {greedy=false; } : . )* '%%%' ( ( ' ' )* '-' )* '\\r' )
            // InternalKactors.g:5600:21: '%%%' ( ( ' ' )* '-' )* '\\r' ( options {greedy=false; } : . )* '%%%' ( ( ' ' )* '-' )* '\\r'
            {
            match("%%%"); 

            // InternalKactors.g:5600:27: ( ( ' ' )* '-' )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==' '||LA8_0=='-') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalKactors.g:5600:28: ( ' ' )* '-'
            	    {
            	    // InternalKactors.g:5600:28: ( ' ' )*
            	    loop7:
            	    do {
            	        int alt7=2;
            	        int LA7_0 = input.LA(1);

            	        if ( (LA7_0==' ') ) {
            	            alt7=1;
            	        }


            	        switch (alt7) {
            	    	case 1 :
            	    	    // InternalKactors.g:5600:28: ' '
            	    	    {
            	    	    match(' '); 

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop7;
            	        }
            	    } while (true);

            	    match('-'); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            match('\r'); 
            // InternalKactors.g:5600:44: ( options {greedy=false; } : . )*
            loop9:
            do {
                int alt9=2;
                alt9 = dfa9.predict(input);
                switch (alt9) {
            	case 1 :
            	    // InternalKactors.g:5600:72: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            match("%%%"); 

            // InternalKactors.g:5600:82: ( ( ' ' )* '-' )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==' '||LA11_0=='-') ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalKactors.g:5600:83: ( ' ' )* '-'
            	    {
            	    // InternalKactors.g:5600:83: ( ' ' )*
            	    loop10:
            	    do {
            	        int alt10=2;
            	        int LA10_0 = input.LA(1);

            	        if ( (LA10_0==' ') ) {
            	            alt10=1;
            	        }


            	        switch (alt10) {
            	    	case 1 :
            	    	    // InternalKactors.g:5600:83: ' '
            	    	    {
            	    	    match(' '); 

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop10;
            	        }
            	    } while (true);

            	    match('-'); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            match('\r'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_EMBEDDEDTEXT"

    // $ANTLR start "RULE_REGEXP"
    public final void mRULE_REGEXP() throws RecognitionException {
        try {
            int _type = RULE_REGEXP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5602:13: ( '%' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' ) | ~ ( ( '\\\\' | '%' ) ) )* '%' )
            // InternalKactors.g:5602:15: '%' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' ) | ~ ( ( '\\\\' | '%' ) ) )* '%'
            {
            match('%'); 
            // InternalKactors.g:5602:19: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' ) | ~ ( ( '\\\\' | '%' ) ) )*
            loop12:
            do {
                int alt12=3;
                int LA12_0 = input.LA(1);

                if ( (LA12_0=='\\') ) {
                    alt12=1;
                }
                else if ( ((LA12_0>='\u0000' && LA12_0<='$')||(LA12_0>='&' && LA12_0<='[')||(LA12_0>=']' && LA12_0<='\uFFFF')) ) {
                    alt12=2;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalKactors.g:5602:20: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' )
            	    {
            	    match('\\'); 
            	    if ( input.LA(1)=='%'||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||(input.LA(1)>='t' && input.LA(1)<='u') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // InternalKactors.g:5602:60: ~ ( ( '\\\\' | '%' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='$')||(input.LA(1)>='&' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_REGEXP"

    // $ANTLR start "RULE_OBSERVABLE"
    public final void mRULE_OBSERVABLE() throws RecognitionException {
        try {
            int _type = RULE_OBSERVABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5604:17: ( '{' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' ) | ~ ( ( '\\\\' | '}' ) ) )* '}' )
            // InternalKactors.g:5604:19: '{' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' ) | ~ ( ( '\\\\' | '}' ) ) )* '}'
            {
            match('{'); 
            // InternalKactors.g:5604:23: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' ) | ~ ( ( '\\\\' | '}' ) ) )*
            loop13:
            do {
                int alt13=3;
                int LA13_0 = input.LA(1);

                if ( (LA13_0=='\\') ) {
                    alt13=1;
                }
                else if ( ((LA13_0>='\u0000' && LA13_0<='[')||(LA13_0>=']' && LA13_0<='|')||(LA13_0>='~' && LA13_0<='\uFFFF')) ) {
                    alt13=2;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalKactors.g:5604:24: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' )
            	    {
            	    match('\\'); 
            	    if ( input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||(input.LA(1)>='t' && input.LA(1)<='u') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // InternalKactors.g:5604:60: ~ ( ( '\\\\' | '}' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='|')||(input.LA(1)>='~' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_OBSERVABLE"

    // $ANTLR start "RULE_SEPARATOR"
    public final void mRULE_SEPARATOR() throws RecognitionException {
        try {
            int _type = RULE_SEPARATOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5606:16: ( '---' ( '-' )* )
            // InternalKactors.g:5606:18: '---' ( '-' )*
            {
            match("---"); 

            // InternalKactors.g:5606:24: ( '-' )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0=='-') ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalKactors.g:5606:24: '-'
            	    {
            	    match('-'); 

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SEPARATOR"

    // $ANTLR start "RULE_ANNOTATION_ID"
    public final void mRULE_ANNOTATION_ID() throws RecognitionException {
        try {
            int _type = RULE_ANNOTATION_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5608:20: ( '@' RULE_LOWERCASE_ID )
            // InternalKactors.g:5608:22: '@' RULE_LOWERCASE_ID
            {
            match('@'); 
            mRULE_LOWERCASE_ID(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANNOTATION_ID"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5610:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalKactors.g:5610:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalKactors.g:5610:11: ( '^' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='^') ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalKactors.g:5610:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalKactors.g:5610:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>='0' && LA16_0<='9')||(LA16_0>='A' && LA16_0<='Z')||LA16_0=='_'||(LA16_0>='a' && LA16_0<='z')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalKactors.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5612:10: ( ( '0' .. '9' )+ )
            // InternalKactors.g:5612:12: ( '0' .. '9' )+
            {
            // InternalKactors.g:5612:12: ( '0' .. '9' )+
            int cnt17=0;
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>='0' && LA17_0<='9')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalKactors.g:5612:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt17 >= 1 ) break loop17;
                        EarlyExitException eee =
                            new EarlyExitException(17, input);
                        throw eee;
                }
                cnt17++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5614:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // InternalKactors.g:5614:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // InternalKactors.g:5614:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0=='\"') ) {
                alt20=1;
            }
            else if ( (LA20_0=='\'') ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // InternalKactors.g:5614:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalKactors.g:5614:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop18:
                    do {
                        int alt18=3;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0=='\\') ) {
                            alt18=1;
                        }
                        else if ( ((LA18_0>='\u0000' && LA18_0<='!')||(LA18_0>='#' && LA18_0<='[')||(LA18_0>=']' && LA18_0<='\uFFFF')) ) {
                            alt18=2;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // InternalKactors.g:5614:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKactors.g:5614:28: ~ ( ( '\\\\' | '\"' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop18;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5614:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalKactors.g:5614:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop19:
                    do {
                        int alt19=3;
                        int LA19_0 = input.LA(1);

                        if ( (LA19_0=='\\') ) {
                            alt19=1;
                        }
                        else if ( ((LA19_0>='\u0000' && LA19_0<='&')||(LA19_0>='(' && LA19_0<='[')||(LA19_0>=']' && LA19_0<='\uFFFF')) ) {
                            alt19=2;
                        }


                        switch (alt19) {
                    	case 1 :
                    	    // InternalKactors.g:5614:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKactors.g:5614:61: ~ ( ( '\\\\' | '\\'' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop19;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5616:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalKactors.g:5616:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalKactors.g:5616:24: ( options {greedy=false; } : . )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0=='*') ) {
                    int LA21_1 = input.LA(2);

                    if ( (LA21_1=='/') ) {
                        alt21=2;
                    }
                    else if ( ((LA21_1>='\u0000' && LA21_1<='.')||(LA21_1>='0' && LA21_1<='\uFFFF')) ) {
                        alt21=1;
                    }


                }
                else if ( ((LA21_0>='\u0000' && LA21_0<=')')||(LA21_0>='+' && LA21_0<='\uFFFF')) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalKactors.g:5616:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5618:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalKactors.g:5618:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalKactors.g:5618:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>='\u0000' && LA22_0<='\t')||(LA22_0>='\u000B' && LA22_0<='\f')||(LA22_0>='\u000E' && LA22_0<='\uFFFF')) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalKactors.g:5618:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);

            // InternalKactors.g:5618:40: ( ( '\\r' )? '\\n' )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0=='\n'||LA24_0=='\r') ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalKactors.g:5618:41: ( '\\r' )? '\\n'
                    {
                    // InternalKactors.g:5618:41: ( '\\r' )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0=='\r') ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // InternalKactors.g:5618:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5620:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalKactors.g:5620:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalKactors.g:5620:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt25=0;
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( ((LA25_0>='\t' && LA25_0<='\n')||LA25_0=='\r'||LA25_0==' ') ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalKactors.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt25 >= 1 ) break loop25;
                        EarlyExitException eee =
                            new EarlyExitException(25, input);
                        throw eee;
                }
                cnt25++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5622:16: ( . )
            // InternalKactors.g:5622:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // InternalKactors.g:1:8: ( T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | RULE_UPPERCASE_ID | RULE_UPPERCASE_PATH | RULE_CAMELCASE_ID | RULE_LOWERCASE_ID | RULE_ARGVALUE | RULE_EXPR | RULE_EMBEDDEDTEXT | RULE_REGEXP | RULE_OBSERVABLE | RULE_SEPARATOR | RULE_ANNOTATION_ID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt26=75;
        alt26 = dfa26.predict(input);
        switch (alt26) {
            case 1 :
                // InternalKactors.g:1:10: T__22
                {
                mT__22(); 

                }
                break;
            case 2 :
                // InternalKactors.g:1:16: T__23
                {
                mT__23(); 

                }
                break;
            case 3 :
                // InternalKactors.g:1:22: T__24
                {
                mT__24(); 

                }
                break;
            case 4 :
                // InternalKactors.g:1:28: T__25
                {
                mT__25(); 

                }
                break;
            case 5 :
                // InternalKactors.g:1:34: T__26
                {
                mT__26(); 

                }
                break;
            case 6 :
                // InternalKactors.g:1:40: T__27
                {
                mT__27(); 

                }
                break;
            case 7 :
                // InternalKactors.g:1:46: T__28
                {
                mT__28(); 

                }
                break;
            case 8 :
                // InternalKactors.g:1:52: T__29
                {
                mT__29(); 

                }
                break;
            case 9 :
                // InternalKactors.g:1:58: T__30
                {
                mT__30(); 

                }
                break;
            case 10 :
                // InternalKactors.g:1:64: T__31
                {
                mT__31(); 

                }
                break;
            case 11 :
                // InternalKactors.g:1:70: T__32
                {
                mT__32(); 

                }
                break;
            case 12 :
                // InternalKactors.g:1:76: T__33
                {
                mT__33(); 

                }
                break;
            case 13 :
                // InternalKactors.g:1:82: T__34
                {
                mT__34(); 

                }
                break;
            case 14 :
                // InternalKactors.g:1:88: T__35
                {
                mT__35(); 

                }
                break;
            case 15 :
                // InternalKactors.g:1:94: T__36
                {
                mT__36(); 

                }
                break;
            case 16 :
                // InternalKactors.g:1:100: T__37
                {
                mT__37(); 

                }
                break;
            case 17 :
                // InternalKactors.g:1:106: T__38
                {
                mT__38(); 

                }
                break;
            case 18 :
                // InternalKactors.g:1:112: T__39
                {
                mT__39(); 

                }
                break;
            case 19 :
                // InternalKactors.g:1:118: T__40
                {
                mT__40(); 

                }
                break;
            case 20 :
                // InternalKactors.g:1:124: T__41
                {
                mT__41(); 

                }
                break;
            case 21 :
                // InternalKactors.g:1:130: T__42
                {
                mT__42(); 

                }
                break;
            case 22 :
                // InternalKactors.g:1:136: T__43
                {
                mT__43(); 

                }
                break;
            case 23 :
                // InternalKactors.g:1:142: T__44
                {
                mT__44(); 

                }
                break;
            case 24 :
                // InternalKactors.g:1:148: T__45
                {
                mT__45(); 

                }
                break;
            case 25 :
                // InternalKactors.g:1:154: T__46
                {
                mT__46(); 

                }
                break;
            case 26 :
                // InternalKactors.g:1:160: T__47
                {
                mT__47(); 

                }
                break;
            case 27 :
                // InternalKactors.g:1:166: T__48
                {
                mT__48(); 

                }
                break;
            case 28 :
                // InternalKactors.g:1:172: T__49
                {
                mT__49(); 

                }
                break;
            case 29 :
                // InternalKactors.g:1:178: T__50
                {
                mT__50(); 

                }
                break;
            case 30 :
                // InternalKactors.g:1:184: T__51
                {
                mT__51(); 

                }
                break;
            case 31 :
                // InternalKactors.g:1:190: T__52
                {
                mT__52(); 

                }
                break;
            case 32 :
                // InternalKactors.g:1:196: T__53
                {
                mT__53(); 

                }
                break;
            case 33 :
                // InternalKactors.g:1:202: T__54
                {
                mT__54(); 

                }
                break;
            case 34 :
                // InternalKactors.g:1:208: T__55
                {
                mT__55(); 

                }
                break;
            case 35 :
                // InternalKactors.g:1:214: T__56
                {
                mT__56(); 

                }
                break;
            case 36 :
                // InternalKactors.g:1:220: T__57
                {
                mT__57(); 

                }
                break;
            case 37 :
                // InternalKactors.g:1:226: T__58
                {
                mT__58(); 

                }
                break;
            case 38 :
                // InternalKactors.g:1:232: T__59
                {
                mT__59(); 

                }
                break;
            case 39 :
                // InternalKactors.g:1:238: T__60
                {
                mT__60(); 

                }
                break;
            case 40 :
                // InternalKactors.g:1:244: T__61
                {
                mT__61(); 

                }
                break;
            case 41 :
                // InternalKactors.g:1:250: T__62
                {
                mT__62(); 

                }
                break;
            case 42 :
                // InternalKactors.g:1:256: T__63
                {
                mT__63(); 

                }
                break;
            case 43 :
                // InternalKactors.g:1:262: T__64
                {
                mT__64(); 

                }
                break;
            case 44 :
                // InternalKactors.g:1:268: T__65
                {
                mT__65(); 

                }
                break;
            case 45 :
                // InternalKactors.g:1:274: T__66
                {
                mT__66(); 

                }
                break;
            case 46 :
                // InternalKactors.g:1:280: T__67
                {
                mT__67(); 

                }
                break;
            case 47 :
                // InternalKactors.g:1:286: T__68
                {
                mT__68(); 

                }
                break;
            case 48 :
                // InternalKactors.g:1:292: T__69
                {
                mT__69(); 

                }
                break;
            case 49 :
                // InternalKactors.g:1:298: T__70
                {
                mT__70(); 

                }
                break;
            case 50 :
                // InternalKactors.g:1:304: T__71
                {
                mT__71(); 

                }
                break;
            case 51 :
                // InternalKactors.g:1:310: T__72
                {
                mT__72(); 

                }
                break;
            case 52 :
                // InternalKactors.g:1:316: T__73
                {
                mT__73(); 

                }
                break;
            case 53 :
                // InternalKactors.g:1:322: T__74
                {
                mT__74(); 

                }
                break;
            case 54 :
                // InternalKactors.g:1:328: T__75
                {
                mT__75(); 

                }
                break;
            case 55 :
                // InternalKactors.g:1:334: T__76
                {
                mT__76(); 

                }
                break;
            case 56 :
                // InternalKactors.g:1:340: T__77
                {
                mT__77(); 

                }
                break;
            case 57 :
                // InternalKactors.g:1:346: T__78
                {
                mT__78(); 

                }
                break;
            case 58 :
                // InternalKactors.g:1:352: RULE_UPPERCASE_ID
                {
                mRULE_UPPERCASE_ID(); 

                }
                break;
            case 59 :
                // InternalKactors.g:1:370: RULE_UPPERCASE_PATH
                {
                mRULE_UPPERCASE_PATH(); 

                }
                break;
            case 60 :
                // InternalKactors.g:1:390: RULE_CAMELCASE_ID
                {
                mRULE_CAMELCASE_ID(); 

                }
                break;
            case 61 :
                // InternalKactors.g:1:408: RULE_LOWERCASE_ID
                {
                mRULE_LOWERCASE_ID(); 

                }
                break;
            case 62 :
                // InternalKactors.g:1:426: RULE_ARGVALUE
                {
                mRULE_ARGVALUE(); 

                }
                break;
            case 63 :
                // InternalKactors.g:1:440: RULE_EXPR
                {
                mRULE_EXPR(); 

                }
                break;
            case 64 :
                // InternalKactors.g:1:450: RULE_EMBEDDEDTEXT
                {
                mRULE_EMBEDDEDTEXT(); 

                }
                break;
            case 65 :
                // InternalKactors.g:1:468: RULE_REGEXP
                {
                mRULE_REGEXP(); 

                }
                break;
            case 66 :
                // InternalKactors.g:1:480: RULE_OBSERVABLE
                {
                mRULE_OBSERVABLE(); 

                }
                break;
            case 67 :
                // InternalKactors.g:1:496: RULE_SEPARATOR
                {
                mRULE_SEPARATOR(); 

                }
                break;
            case 68 :
                // InternalKactors.g:1:511: RULE_ANNOTATION_ID
                {
                mRULE_ANNOTATION_ID(); 

                }
                break;
            case 69 :
                // InternalKactors.g:1:530: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 70 :
                // InternalKactors.g:1:538: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 71 :
                // InternalKactors.g:1:547: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 72 :
                // InternalKactors.g:1:559: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 73 :
                // InternalKactors.g:1:575: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 74 :
                // InternalKactors.g:1:591: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 75 :
                // InternalKactors.g:1:599: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA9 dfa9 = new DFA9(this);
    protected DFA26 dfa26 = new DFA26(this);
    static final String DFA9_eotS =
        "\10\uffff";
    static final String DFA9_eofS =
        "\10\uffff";
    static final String DFA9_minS =
        "\2\0\1\uffff\4\0\1\uffff";
    static final String DFA9_maxS =
        "\2\uffff\1\uffff\4\uffff\1\uffff";
    static final String DFA9_acceptS =
        "\2\uffff\1\1\4\uffff\1\2";
    static final String DFA9_specialS =
        "\1\0\1\1\1\uffff\1\2\1\3\1\5\1\4\1\uffff}>";
    static final String[] DFA9_transitionS = {
            "\45\2\1\1\uffda\2",
            "\45\2\1\3\uffda\2",
            "",
            "\45\2\1\4\uffda\2",
            "\15\2\1\7\22\2\1\5\14\2\1\6\uffd2\2",
            "\40\2\1\5\14\2\1\6\uffd2\2",
            "\15\2\1\7\22\2\1\5\14\2\1\6\uffd2\2",
            ""
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "()* loopback of 5600:44: ( options {greedy=false; } : . )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA9_0 = input.LA(1);

                        s = -1;
                        if ( (LA9_0=='%') ) {s = 1;}

                        else if ( ((LA9_0>='\u0000' && LA9_0<='$')||(LA9_0>='&' && LA9_0<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA9_1 = input.LA(1);

                        s = -1;
                        if ( (LA9_1=='%') ) {s = 3;}

                        else if ( ((LA9_1>='\u0000' && LA9_1<='$')||(LA9_1>='&' && LA9_1<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA9_3 = input.LA(1);

                        s = -1;
                        if ( (LA9_3=='%') ) {s = 4;}

                        else if ( ((LA9_3>='\u0000' && LA9_3<='$')||(LA9_3>='&' && LA9_3<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA9_4 = input.LA(1);

                        s = -1;
                        if ( ((LA9_4>='\u0000' && LA9_4<='\f')||(LA9_4>='\u000E' && LA9_4<='\u001F')||(LA9_4>='!' && LA9_4<=',')||(LA9_4>='.' && LA9_4<='\uFFFF')) ) {s = 2;}

                        else if ( (LA9_4==' ') ) {s = 5;}

                        else if ( (LA9_4=='-') ) {s = 6;}

                        else if ( (LA9_4=='\r') ) {s = 7;}

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA9_6 = input.LA(1);

                        s = -1;
                        if ( ((LA9_6>='\u0000' && LA9_6<='\f')||(LA9_6>='\u000E' && LA9_6<='\u001F')||(LA9_6>='!' && LA9_6<=',')||(LA9_6>='.' && LA9_6<='\uFFFF')) ) {s = 2;}

                        else if ( (LA9_6=='\r') ) {s = 7;}

                        else if ( (LA9_6==' ') ) {s = 5;}

                        else if ( (LA9_6=='-') ) {s = 6;}

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA9_5 = input.LA(1);

                        s = -1;
                        if ( (LA9_5=='-') ) {s = 6;}

                        else if ( (LA9_5==' ') ) {s = 5;}

                        else if ( ((LA9_5>='\u0000' && LA9_5<='\u001F')||(LA9_5>='!' && LA9_5<=',')||(LA9_5>='.' && LA9_5<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 9, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA26_eotS =
        "\1\uffff\2\65\1\uffff\1\65\1\76\6\65\3\uffff\1\112\1\65\2\uffff\1\121\1\123\2\65\1\132\2\uffff\1\137\1\uffff\1\141\1\144\1\146\1\62\1\uffff\1\153\1\uffff\1\156\3\163\1\166\1\163\1\65\1\uffff\2\62\2\uffff\2\62\2\uffff\2\65\2\uffff\1\65\1\u0081\1\u0082\1\uffff\3\65\1\uffff\1\65\1\u0088\5\65\5\uffff\2\65\2\uffff\1\u0090\4\uffff\1\65\1\u0092\4\65\23\uffff\1\163\1\uffff\1\161\2\uffff\1\u0097\1\uffff\1\u0098\1\u0099\3\uffff\1\172\4\uffff\3\65\2\uffff\4\65\1\u00a2\1\uffff\7\65\1\uffff\1\65\1\uffff\1\65\1\u00ac\2\65\4\uffff\1\u00af\6\65\1\uffff\5\65\1\uffff\1\65\1\u00bc\1\65\1\uffff\1\65\1\u00bf\1\uffff\3\65\1\u00c3\1\u00c4\7\65\1\uffff\1\u00cc\1\65\1\uffff\1\u00ce\2\65\2\uffff\2\65\1\u00d3\4\65\1\uffff\1\65\1\uffff\4\65\1\uffff\1\u00dd\1\u00de\1\65\1\u00e0\5\65\2\uffff\1\u00e6\1\uffff\1\65\1\u00e8\1\u00e9\2\65\1\uffff\1\u00ec\2\uffff\2\65\1\uffff\1\u00ef\1\u00f0\2\uffff";
    static final String DFA26_eofS =
        "\u00f1\uffff";
    static final String DFA26_minS =
        "\1\0\2\60\1\uffff\10\60\3\uffff\1\77\1\60\2\uffff\1\0\1\175\3\60\2\uffff\1\52\1\uffff\1\141\3\75\1\uffff\1\55\1\uffff\4\56\1\101\1\56\1\60\1\uffff\2\0\2\uffff\2\0\2\uffff\2\60\2\uffff\3\60\1\uffff\3\60\1\uffff\7\60\5\uffff\2\60\2\uffff\1\0\4\uffff\6\60\23\uffff\1\56\1\uffff\1\60\2\uffff\1\56\1\uffff\2\56\3\uffff\1\45\4\uffff\3\60\2\uffff\5\60\1\uffff\7\60\1\uffff\1\60\1\uffff\4\60\4\uffff\7\60\1\uffff\5\60\1\uffff\3\60\1\uffff\2\60\1\uffff\14\60\1\uffff\2\60\1\uffff\3\60\2\uffff\7\60\1\uffff\1\60\1\uffff\4\60\1\uffff\11\60\2\uffff\1\60\1\uffff\5\60\1\uffff\1\60\2\uffff\2\60\1\uffff\2\60\2\uffff";
    static final String DFA26_maxS =
        "\1\uffff\2\172\1\uffff\10\172\3\uffff\1\77\1\172\2\uffff\1\uffff\1\175\3\172\2\uffff\1\57\1\uffff\1\172\3\75\1\uffff\1\76\1\uffff\7\172\1\uffff\2\uffff\2\uffff\2\uffff\2\uffff\2\172\2\uffff\3\172\1\uffff\3\172\1\uffff\7\172\5\uffff\2\172\2\uffff\1\uffff\4\uffff\6\172\23\uffff\1\172\1\uffff\1\172\2\uffff\1\172\1\uffff\2\172\3\uffff\1\45\4\uffff\3\172\2\uffff\5\172\1\uffff\7\172\1\uffff\1\172\1\uffff\4\172\4\uffff\7\172\1\uffff\5\172\1\uffff\3\172\1\uffff\2\172\1\uffff\14\172\1\uffff\2\172\1\uffff\3\172\2\uffff\7\172\1\uffff\1\172\1\uffff\4\172\1\uffff\11\172\2\uffff\1\172\1\uffff\5\172\1\uffff\1\172\2\uffff\2\172\1\uffff\2\172\2\uffff";
    static final String DFA26_acceptS =
        "\3\uffff\1\3\10\uffff\1\15\1\16\1\17\2\uffff\1\23\1\24\5\uffff\1\36\1\41\1\uffff\1\43\4\uffff\1\57\1\uffff\1\61\7\uffff\1\76\2\uffff\1\105\1\106\2\uffff\1\112\1\113\2\uffff\1\75\1\105\3\uffff\1\3\3\uffff\1\63\7\uffff\1\15\1\16\1\17\1\20\1\21\2\uffff\1\23\1\24\1\uffff\1\102\1\25\1\40\1\26\6\uffff\1\64\1\36\1\41\1\110\1\111\1\42\1\43\1\44\1\104\1\51\1\45\1\50\1\46\1\47\1\57\1\60\1\103\1\62\1\61\1\uffff\1\65\1\uffff\1\73\1\74\1\uffff\1\72\2\uffff\1\71\1\76\1\77\1\uffff\1\101\1\106\1\107\1\112\3\uffff\1\34\1\52\5\uffff\1\55\7\uffff\1\37\1\uffff\1\33\4\uffff\1\66\1\67\1\70\1\100\7\uffff\1\14\5\uffff\1\22\3\uffff\1\56\2\uffff\1\1\14\uffff\1\27\2\uffff\1\53\3\uffff\1\54\1\5\7\uffff\1\30\1\uffff\1\2\4\uffff\1\10\11\uffff\1\11\1\12\1\uffff\1\35\5\uffff\1\13\1\uffff\1\31\1\4\2\uffff\1\32\2\uffff\1\6\1\7";
    static final String DFA26_specialS =
        "\1\3\22\uffff\1\4\27\uffff\1\0\1\1\2\uffff\1\2\1\6\36\uffff\1\5\u00a1\uffff}>";
    static final String[] DFA26_transitionS = {
            "\11\62\2\61\2\62\1\61\22\62\1\61\1\37\1\57\1\21\1\52\1\54\1\22\1\60\1\15\1\16\1\30\1\42\1\3\1\41\1\33\1\32\12\56\1\14\1\40\1\36\1\17\1\35\1\62\1\34\1\44\1\46\1\45\1\50\1\43\25\50\1\53\2\62\1\47\1\55\1\62\1\10\1\51\1\12\1\6\1\27\1\26\2\51\1\2\2\51\1\5\1\13\1\1\1\51\1\7\3\51\1\25\1\20\1\11\1\4\3\51\1\23\1\31\1\24\uff82\62",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\1\63\31\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\5\64\1\71\6\64\1\67\1\70\14\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\7\64\1\74\6\64\1\73\13\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\1\75\31\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\77\11\64\1\100\13\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\101\25\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\24\64\1\102\5\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\103\25\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\21\64\1\104\10\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\16\64\1\105\13\64",
            "",
            "",
            "",
            "\1\111",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\15\64\1\114\3\64\1\113\10\64",
            "",
            "",
            "\173\120\1\117\uff84\120",
            "\1\122",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\16\64\1\125\2\64\1\124\10\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\1\126\15\64\1\127\13\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\13\64\1\131\13\64\1\130\2\64",
            "",
            "",
            "\1\135\4\uffff\1\136",
            "",
            "\32\142",
            "\1\143",
            "\1\145",
            "\1\147",
            "",
            "\1\152\20\uffff\1\151",
            "",
            "\1\160\1\uffff\12\157\7\uffff\32\155\4\161\1\155\1\161\32\157",
            "\1\160\1\uffff\12\157\7\uffff\3\155\1\162\26\155\4\161\1\155\1\161\32\157",
            "\1\160\1\uffff\12\157\7\uffff\4\155\1\164\25\155\4\161\1\155\1\161\32\157",
            "\1\160\1\uffff\12\157\7\uffff\2\155\1\165\27\155\4\161\1\155\1\161\32\157",
            "\32\66\4\uffff\1\66\1\uffff\32\66",
            "\1\160\1\uffff\12\157\7\uffff\32\155\4\161\1\155\1\161\32\157",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "",
            "\0\170",
            "\45\172\1\171\uffda\172",
            "",
            "",
            "\0\174",
            "\0\174",
            "",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\14\64\1\176\15\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\17\64\1\177\12\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\2\64\1\u0080\27\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\21\64\1\u0083\10\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\10\64\1\u0084\21\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\1\64\1\u0085\30\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\5\64\1\u0087\14\64\1\u0086\7\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\21\64\1\u0089\10\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\23\64\1\u008a\6\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\21\64\1\u008b\10\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\u008c\25\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\3\64\1\u008d\26\64",
            "",
            "",
            "",
            "",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\15\64\1\u008e\14\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\12\64\1\u008f\17\64",
            "",
            "",
            "\0\120",
            "",
            "",
            "",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\24\64\1\u0091\5\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\13\64\1\u0093\16\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\21\64\1\u0094\10\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\2\64\1\u0095\27\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\22\64\1\u0096\7\64",
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
            "\1\160\1\uffff\12\157\7\uffff\32\155\4\161\1\155\1\161\32\157",
            "",
            "\12\157\7\uffff\32\157\4\uffff\1\157\1\uffff\32\157",
            "",
            "",
            "\1\160\1\uffff\12\157\7\uffff\32\155\4\161\1\155\1\161\32\157",
            "",
            "\1\160\1\uffff\12\157\7\uffff\32\155\4\161\1\155\1\161\32\157",
            "\1\160\1\uffff\12\157\7\uffff\32\155\4\161\1\155\1\161\32\157",
            "",
            "",
            "",
            "\1\u009a",
            "",
            "",
            "",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\u009b\25\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\16\64\1\u009c\13\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\13\64\1\u009d\16\64",
            "",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\13\64\1\u009e\16\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\13\64\1\u009f\16\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\u00a0\25\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\2\64\1\u00a1\27\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\14\64\1\u00a3\15\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\7\64\1\u00a4\22\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\22\64\1\u00a5\7\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\1\u00a6\31\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\10\64\1\u00a7\21\64",
            "\12\64\1\u00a8\6\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\15\64\1\u00a9\14\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\u00aa\25\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\22\64\1\u00ab\7\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\13\64\1\u00ad\16\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\u00ae\25\64",
            "",
            "",
            "",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\21\64\1\u00b0\10\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\24\64\1\u00b1\5\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\3\64\1\u00b2\26\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\u00b3\25\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\13\64\1\u00b4\16\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\21\64\1\u00b5\10\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\10\64\1\u00b6\21\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\16\64\1\u00b7\13\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\10\64\1\u00b8\21\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\23\64\1\u00b9\6\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\5\64\1\u00ba\24\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\16\64\1\u00bb\13\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\u00bd\25\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\24\64\1\u00be\5\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\23\64\1\u00c0\6\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\22\64\1\u00c1\7\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\25\64\1\u00c2\4\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\10\64\1\u00c5\21\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\22\64\1\u00c6\7\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\21\64\1\u00c7\10\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\16\64\1\u00c8\13\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\u00c9\25\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\10\64\1\u00ca\21\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\26\64\1\u00cb\3\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\22\64\1\u00cd\7\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\10\64\1\u00cf\21\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\10\64\1\u00d0\21\64",
            "",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\17\64\1\u00d1\12\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\22\64\1\u00d2\7\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\15\64\1\u00d4\14\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\3\64\1\u00d5\26\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\u00d6\25\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\15\64\1\u00d7\14\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\10\64\1\u00d8\21\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\25\64\1\u00d9\4\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\u00da\25\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\23\64\1\u00db\6\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\10\64\1\u00dc\21\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\3\64\1\u00df\26\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\25\64\1\u00e1\4\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\u00e2\25\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\26\64\1\u00e3\3\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\10\64\1\u00e4\21\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\16\64\1\u00e5\13\64",
            "",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\4\64\1\u00e7\25\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\16\64\1\u00ea\13\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\15\64\1\u00eb\14\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\15\64\1\u00ed\14\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\22\64\1\u00ee\7\64",
            "",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\66\4\uffff\1\64\1\uffff\32\64",
            "",
            ""
    };

    static final short[] DFA26_eot = DFA.unpackEncodedString(DFA26_eotS);
    static final short[] DFA26_eof = DFA.unpackEncodedString(DFA26_eofS);
    static final char[] DFA26_min = DFA.unpackEncodedStringToUnsignedChars(DFA26_minS);
    static final char[] DFA26_max = DFA.unpackEncodedStringToUnsignedChars(DFA26_maxS);
    static final short[] DFA26_accept = DFA.unpackEncodedString(DFA26_acceptS);
    static final short[] DFA26_special = DFA.unpackEncodedString(DFA26_specialS);
    static final short[][] DFA26_transition;

    static {
        int numStates = DFA26_transitionS.length;
        DFA26_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA26_transition[i] = DFA.unpackEncodedString(DFA26_transitionS[i]);
        }
    }

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = DFA26_eot;
            this.eof = DFA26_eof;
            this.min = DFA26_min;
            this.max = DFA26_max;
            this.accept = DFA26_accept;
            this.special = DFA26_special;
            this.transition = DFA26_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | RULE_UPPERCASE_ID | RULE_UPPERCASE_PATH | RULE_CAMELCASE_ID | RULE_LOWERCASE_ID | RULE_ARGVALUE | RULE_EXPR | RULE_EMBEDDEDTEXT | RULE_REGEXP | RULE_OBSERVABLE | RULE_SEPARATOR | RULE_ANNOTATION_ID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA26_43 = input.LA(1);

                        s = -1;
                        if ( ((LA26_43>='\u0000' && LA26_43<='\uFFFF')) ) {s = 120;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA26_44 = input.LA(1);

                        s = -1;
                        if ( (LA26_44=='%') ) {s = 121;}

                        else if ( ((LA26_44>='\u0000' && LA26_44<='$')||(LA26_44>='&' && LA26_44<='\uFFFF')) ) {s = 122;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA26_47 = input.LA(1);

                        s = -1;
                        if ( ((LA26_47>='\u0000' && LA26_47<='\uFFFF')) ) {s = 124;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA26_0 = input.LA(1);

                        s = -1;
                        if ( (LA26_0=='n') ) {s = 1;}

                        else if ( (LA26_0=='i') ) {s = 2;}

                        else if ( (LA26_0==',') ) {s = 3;}

                        else if ( (LA26_0=='w') ) {s = 4;}

                        else if ( (LA26_0=='l') ) {s = 5;}

                        else if ( (LA26_0=='d') ) {s = 6;}

                        else if ( (LA26_0=='p') ) {s = 7;}

                        else if ( (LA26_0=='a') ) {s = 8;}

                        else if ( (LA26_0=='v') ) {s = 9;}

                        else if ( (LA26_0=='c') ) {s = 10;}

                        else if ( (LA26_0=='m') ) {s = 11;}

                        else if ( (LA26_0==':') ) {s = 12;}

                        else if ( (LA26_0=='(') ) {s = 13;}

                        else if ( (LA26_0==')') ) {s = 14;}

                        else if ( (LA26_0=='=') ) {s = 15;}

                        else if ( (LA26_0=='u') ) {s = 16;}

                        else if ( (LA26_0=='#') ) {s = 17;}

                        else if ( (LA26_0=='&') ) {s = 18;}

                        else if ( (LA26_0=='{') ) {s = 19;}

                        else if ( (LA26_0=='}') ) {s = 20;}

                        else if ( (LA26_0=='t') ) {s = 21;}

                        else if ( (LA26_0=='f') ) {s = 22;}

                        else if ( (LA26_0=='e') ) {s = 23;}

                        else if ( (LA26_0=='*') ) {s = 24;}

                        else if ( (LA26_0=='|') ) {s = 25;}

                        else if ( (LA26_0=='/') ) {s = 26;}

                        else if ( (LA26_0=='.') ) {s = 27;}

                        else if ( (LA26_0=='@') ) {s = 28;}

                        else if ( (LA26_0=='>') ) {s = 29;}

                        else if ( (LA26_0=='<') ) {s = 30;}

                        else if ( (LA26_0=='!') ) {s = 31;}

                        else if ( (LA26_0==';') ) {s = 32;}

                        else if ( (LA26_0=='-') ) {s = 33;}

                        else if ( (LA26_0=='+') ) {s = 34;}

                        else if ( (LA26_0=='E') ) {s = 35;}

                        else if ( (LA26_0=='A') ) {s = 36;}

                        else if ( (LA26_0=='C') ) {s = 37;}

                        else if ( (LA26_0=='B') ) {s = 38;}

                        else if ( (LA26_0=='^') ) {s = 39;}

                        else if ( (LA26_0=='D'||(LA26_0>='F' && LA26_0<='Z')) ) {s = 40;}

                        else if ( (LA26_0=='b'||(LA26_0>='g' && LA26_0<='h')||(LA26_0>='j' && LA26_0<='k')||LA26_0=='o'||(LA26_0>='q' && LA26_0<='s')||(LA26_0>='x' && LA26_0<='z')) ) {s = 41;}

                        else if ( (LA26_0=='$') ) {s = 42;}

                        else if ( (LA26_0=='[') ) {s = 43;}

                        else if ( (LA26_0=='%') ) {s = 44;}

                        else if ( (LA26_0=='_') ) {s = 45;}

                        else if ( ((LA26_0>='0' && LA26_0<='9')) ) {s = 46;}

                        else if ( (LA26_0=='\"') ) {s = 47;}

                        else if ( (LA26_0=='\'') ) {s = 48;}

                        else if ( ((LA26_0>='\t' && LA26_0<='\n')||LA26_0=='\r'||LA26_0==' ') ) {s = 49;}

                        else if ( ((LA26_0>='\u0000' && LA26_0<='\b')||(LA26_0>='\u000B' && LA26_0<='\f')||(LA26_0>='\u000E' && LA26_0<='\u001F')||LA26_0=='?'||(LA26_0>='\\' && LA26_0<=']')||LA26_0=='`'||(LA26_0>='~' && LA26_0<='\uFFFF')) ) {s = 50;}

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA26_19 = input.LA(1);

                        s = -1;
                        if ( (LA26_19=='{') ) {s = 79;}

                        else if ( ((LA26_19>='\u0000' && LA26_19<='z')||(LA26_19>='|' && LA26_19<='\uFFFF')) ) {s = 80;}

                        else s = 81;

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA26_79 = input.LA(1);

                        s = -1;
                        if ( ((LA26_79>='\u0000' && LA26_79<='\uFFFF')) ) {s = 80;}

                        else s = 144;

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA26_48 = input.LA(1);

                        s = -1;
                        if ( ((LA26_48>='\u0000' && LA26_48<='\uFFFF')) ) {s = 124;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 26, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}
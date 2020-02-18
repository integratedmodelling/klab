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
            // InternalKactors.g:11:7: ( 'app' )
            // InternalKactors.g:11:9: 'app'
            {
            match("app"); 


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
            // InternalKactors.g:12:7: ( 'user' )
            // InternalKactors.g:12:9: 'user'
            {
            match("user"); 


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
            // InternalKactors.g:13:7: ( 'trait' )
            // InternalKactors.g:13:9: 'trait'
            {
            match("trait"); 


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
            // InternalKactors.g:14:7: ( 'library' )
            // InternalKactors.g:14:9: 'library'
            {
            match("library"); 


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
            // InternalKactors.g:15:7: ( 'behavior' )
            // InternalKactors.g:15:9: 'behavior'
            {
            match("behavior"); 


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
            // InternalKactors.g:16:7: ( 'behaviour' )
            // InternalKactors.g:16:9: 'behaviour'
            {
            match("behaviour"); 


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
            // InternalKactors.g:17:7: ( 'import' )
            // InternalKactors.g:17:9: 'import'
            {
            match("import"); 


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
            // InternalKactors.g:18:7: ( ',' )
            // InternalKactors.g:18:9: ','
            {
            match(','); 

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
            // InternalKactors.g:19:7: ( 'worldview' )
            // InternalKactors.g:19:9: 'worldview'
            {
            match("worldview"); 


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
            // InternalKactors.g:20:7: ( 'observable' )
            // InternalKactors.g:20:9: 'observable'
            {
            match("observable"); 


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
            // InternalKactors.g:21:7: ( 'label' )
            // InternalKactors.g:21:9: 'label'
            {
            match("label"); 


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
            // InternalKactors.g:22:7: ( 'description' )
            // InternalKactors.g:22:9: 'description'
            {
            match("description"); 


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
            // InternalKactors.g:23:7: ( 'permissions' )
            // InternalKactors.g:23:9: 'permissions'
            {
            match("permissions"); 


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
            // InternalKactors.g:24:7: ( 'author' )
            // InternalKactors.g:24:9: 'author'
            {
            match("author"); 


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
            // InternalKactors.g:25:7: ( 'version' )
            // InternalKactors.g:25:9: 'version'
            {
            match("version"); 


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
            // InternalKactors.g:26:7: ( 'created' )
            // InternalKactors.g:26:9: 'created'
            {
            match("created"); 


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
            // InternalKactors.g:27:7: ( 'modified' )
            // InternalKactors.g:27:9: 'modified'
            {
            match("modified"); 


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
            // InternalKactors.g:28:7: ( 'action' )
            // InternalKactors.g:28:9: 'action'
            {
            match("action"); 


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
            // InternalKactors.g:29:7: ( ':' )
            // InternalKactors.g:29:9: ':'
            {
            match(':'); 

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
            // InternalKactors.g:30:7: ( '(' )
            // InternalKactors.g:30:9: '('
            {
            match('('); 

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
            // InternalKactors.g:31:7: ( ')' )
            // InternalKactors.g:31:9: ')'
            {
            match(')'); 

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
            // InternalKactors.g:32:7: ( 'if' )
            // InternalKactors.g:32:9: 'if'
            {
            match("if"); 


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
            // InternalKactors.g:33:7: ( 'else' )
            // InternalKactors.g:33:9: 'else'
            {
            match("else"); 


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
            // InternalKactors.g:34:7: ( 'while' )
            // InternalKactors.g:34:9: 'while'
            {
            match("while"); 


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
            // InternalKactors.g:35:7: ( 'do' )
            // InternalKactors.g:35:9: 'do'
            {
            match("do"); 


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
            // InternalKactors.g:36:7: ( 'for' )
            // InternalKactors.g:36:9: 'for'
            {
            match("for"); 


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
            // InternalKactors.g:37:7: ( 'in' )
            // InternalKactors.g:37:9: 'in'
            {
            match("in"); 


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
            // InternalKactors.g:38:7: ( '->' )
            // InternalKactors.g:38:9: '->'
            {
            match("->"); 


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
            // InternalKactors.g:39:7: ( 'true' )
            // InternalKactors.g:39:9: 'true'
            {
            match("true"); 


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
            // InternalKactors.g:40:7: ( 'false' )
            // InternalKactors.g:40:9: 'false'
            {
            match("false"); 


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
            // InternalKactors.g:41:7: ( 'inclusive' )
            // InternalKactors.g:41:9: 'inclusive'
            {
            match("inclusive"); 


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
            // InternalKactors.g:42:7: ( 'exclusive' )
            // InternalKactors.g:42:9: 'exclusive'
            {
            match("exclusive"); 


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
            // InternalKactors.g:43:7: ( 'to' )
            // InternalKactors.g:43:9: 'to'
            {
            match("to"); 


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
            // InternalKactors.g:44:7: ( 'unknown' )
            // InternalKactors.g:44:9: 'unknown'
            {
            match("unknown"); 


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
            // InternalKactors.g:45:7: ( '*' )
            // InternalKactors.g:45:9: '*'
            {
            match('*'); 

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
            // InternalKactors.g:46:7: ( '#' )
            // InternalKactors.g:46:9: '#'
            {
            match('#'); 

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
            // InternalKactors.g:47:7: ( 'urn:klab:' )
            // InternalKactors.g:47:9: 'urn:klab:'
            {
            match("urn:klab:"); 


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
            // InternalKactors.g:48:7: ( '&' )
            // InternalKactors.g:48:9: '&'
            {
            match('&'); 

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
            // InternalKactors.g:49:7: ( '=' )
            // InternalKactors.g:49:9: '='
            {
            match('='); 

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
            // InternalKactors.g:50:7: ( '{' )
            // InternalKactors.g:50:9: '{'
            {
            match('{'); 

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
            // InternalKactors.g:51:7: ( '}' )
            // InternalKactors.g:51:9: '}'
            {
            match('}'); 

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
            // InternalKactors.g:52:7: ( '{{' )
            // InternalKactors.g:52:9: '{{'
            {
            match("{{"); 


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
            // InternalKactors.g:53:7: ( '}}' )
            // InternalKactors.g:53:9: '}}'
            {
            match("}}"); 


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
            // InternalKactors.g:54:7: ( '|' )
            // InternalKactors.g:54:9: '|'
            {
            match('|'); 

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
            // InternalKactors.g:55:7: ( '/' )
            // InternalKactors.g:55:9: '/'
            {
            match('/'); 

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
            // InternalKactors.g:56:7: ( '.' )
            // InternalKactors.g:56:9: '.'
            {
            match('.'); 

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
            // InternalKactors.g:57:7: ( '=?' )
            // InternalKactors.g:57:9: '=?'
            {
            match("=?"); 


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
            // InternalKactors.g:58:7: ( '@' )
            // InternalKactors.g:58:9: '@'
            {
            match('@'); 

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
            // InternalKactors.g:59:7: ( '>' )
            // InternalKactors.g:59:9: '>'
            {
            match('>'); 

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
            // InternalKactors.g:60:7: ( '<' )
            // InternalKactors.g:60:9: '<'
            {
            match('<'); 

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
            // InternalKactors.g:61:7: ( '!=' )
            // InternalKactors.g:61:9: '!='
            {
            match("!="); 


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
            // InternalKactors.g:62:7: ( '<=' )
            // InternalKactors.g:62:9: '<='
            {
            match("<="); 


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
            // InternalKactors.g:63:7: ( '>=' )
            // InternalKactors.g:63:9: '>='
            {
            match(">="); 


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
            // InternalKactors.g:64:7: ( '+' )
            // InternalKactors.g:64:9: '+'
            {
            match('+'); 

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
            // InternalKactors.g:65:7: ( '-' )
            // InternalKactors.g:65:9: '-'
            {
            match('-'); 

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
            // InternalKactors.g:66:7: ( 'l' )
            // InternalKactors.g:66:9: 'l'
            {
            match('l'); 

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
            // InternalKactors.g:67:7: ( 'e' )
            // InternalKactors.g:67:9: 'e'
            {
            match('e'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__78"

    // $ANTLR start "T__79"
    public final void mT__79() throws RecognitionException {
        try {
            int _type = T__79;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:68:7: ( 'E' )
            // InternalKactors.g:68:9: 'E'
            {
            match('E'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__79"

    // $ANTLR start "T__80"
    public final void mT__80() throws RecognitionException {
        try {
            int _type = T__80;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:69:7: ( 'AD' )
            // InternalKactors.g:69:9: 'AD'
            {
            match("AD"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__80"

    // $ANTLR start "T__81"
    public final void mT__81() throws RecognitionException {
        try {
            int _type = T__81;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:70:7: ( 'CE' )
            // InternalKactors.g:70:9: 'CE'
            {
            match("CE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__81"

    // $ANTLR start "T__82"
    public final void mT__82() throws RecognitionException {
        try {
            int _type = T__82;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:71:7: ( 'BC' )
            // InternalKactors.g:71:9: 'BC'
            {
            match("BC"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__82"

    // $ANTLR start "T__83"
    public final void mT__83() throws RecognitionException {
        try {
            int _type = T__83;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:72:7: ( '^' )
            // InternalKactors.g:72:9: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__83"

    // $ANTLR start "RULE_UPPERCASE_ID"
    public final void mRULE_UPPERCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_UPPERCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:6031:19: ( 'A' .. 'Z' ( 'A' .. 'Z' | '_' )* )
            // InternalKactors.g:6031:21: 'A' .. 'Z' ( 'A' .. 'Z' | '_' )*
            {
            matchRange('A','Z'); 
            // InternalKactors.g:6031:30: ( 'A' .. 'Z' | '_' )*
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
            // InternalKactors.g:6033:21: ( RULE_UPPERCASE_ID ( '.' RULE_UPPERCASE_ID )* )
            // InternalKactors.g:6033:23: RULE_UPPERCASE_ID ( '.' RULE_UPPERCASE_ID )*
            {
            mRULE_UPPERCASE_ID(); 
            // InternalKactors.g:6033:41: ( '.' RULE_UPPERCASE_ID )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='.') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalKactors.g:6033:42: '.' RULE_UPPERCASE_ID
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
            // InternalKactors.g:6035:19: ( 'A' .. 'Z' ( 'A' .. 'z' | '0' .. '9' )* )
            // InternalKactors.g:6035:21: 'A' .. 'Z' ( 'A' .. 'z' | '0' .. '9' )*
            {
            matchRange('A','Z'); 
            // InternalKactors.g:6035:30: ( 'A' .. 'z' | '0' .. '9' )*
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
            // InternalKactors.g:6037:19: ( 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )* )
            // InternalKactors.g:6037:21: 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )*
            {
            matchRange('a','z'); 
            // InternalKactors.g:6037:30: ( 'a' .. 'z' | '0' .. '9' | '_' )*
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
            // InternalKactors.g:6039:15: ( '$' ( '0' .. '9' )* )
            // InternalKactors.g:6039:17: '$' ( '0' .. '9' )*
            {
            match('$'); 
            // InternalKactors.g:6039:21: ( '0' .. '9' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalKactors.g:6039:22: '0' .. '9'
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
            // InternalKactors.g:6041:11: ( '[' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )* ']' )
            // InternalKactors.g:6041:13: '[' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )* ']'
            {
            match('['); 
            // InternalKactors.g:6041:17: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )*
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
            	    // InternalKactors.g:6041:18: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' )
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
            	    // InternalKactors.g:6041:58: ~ ( ( '\\\\' | ']' ) )
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
            // InternalKactors.g:6043:19: ( '%%%' ( ( ' ' )* '-' )* '\\r' ( options {greedy=false; } : . )* '%%%' ( ( ' ' )* '-' )* )
            // InternalKactors.g:6043:21: '%%%' ( ( ' ' )* '-' )* '\\r' ( options {greedy=false; } : . )* '%%%' ( ( ' ' )* '-' )*
            {
            match("%%%"); 

            // InternalKactors.g:6043:27: ( ( ' ' )* '-' )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==' '||LA8_0=='-') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalKactors.g:6043:28: ( ' ' )* '-'
            	    {
            	    // InternalKactors.g:6043:28: ( ' ' )*
            	    loop7:
            	    do {
            	        int alt7=2;
            	        int LA7_0 = input.LA(1);

            	        if ( (LA7_0==' ') ) {
            	            alt7=1;
            	        }


            	        switch (alt7) {
            	    	case 1 :
            	    	    // InternalKactors.g:6043:28: ' '
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
            // InternalKactors.g:6043:44: ( options {greedy=false; } : . )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0=='%') ) {
                    int LA9_1 = input.LA(2);

                    if ( (LA9_1=='%') ) {
                        int LA9_3 = input.LA(3);

                        if ( (LA9_3=='%') ) {
                            alt9=2;
                        }
                        else if ( ((LA9_3>='\u0000' && LA9_3<='$')||(LA9_3>='&' && LA9_3<='\uFFFF')) ) {
                            alt9=1;
                        }


                    }
                    else if ( ((LA9_1>='\u0000' && LA9_1<='$')||(LA9_1>='&' && LA9_1<='\uFFFF')) ) {
                        alt9=1;
                    }


                }
                else if ( ((LA9_0>='\u0000' && LA9_0<='$')||(LA9_0>='&' && LA9_0<='\uFFFF')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalKactors.g:6043:72: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            match("%%%"); 

            // InternalKactors.g:6043:82: ( ( ' ' )* '-' )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==' '||LA11_0=='-') ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalKactors.g:6043:83: ( ' ' )* '-'
            	    {
            	    // InternalKactors.g:6043:83: ( ' ' )*
            	    loop10:
            	    do {
            	        int alt10=2;
            	        int LA10_0 = input.LA(1);

            	        if ( (LA10_0==' ') ) {
            	            alt10=1;
            	        }


            	        switch (alt10) {
            	    	case 1 :
            	    	    // InternalKactors.g:6043:83: ' '
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
            // InternalKactors.g:6045:13: ( '%' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' ) | ~ ( ( '\\\\' | '%' ) ) )* '%' )
            // InternalKactors.g:6045:15: '%' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' ) | ~ ( ( '\\\\' | '%' ) ) )* '%'
            {
            match('%'); 
            // InternalKactors.g:6045:19: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' ) | ~ ( ( '\\\\' | '%' ) ) )*
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
            	    // InternalKactors.g:6045:20: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' )
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
            	    // InternalKactors.g:6045:60: ~ ( ( '\\\\' | '%' ) )
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
            // InternalKactors.g:6047:17: ( '{' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' ) | ~ ( ( '\\\\' | '}' ) ) )* '}' )
            // InternalKactors.g:6047:19: '{' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' ) | ~ ( ( '\\\\' | '}' ) ) )* '}'
            {
            match('{'); 
            // InternalKactors.g:6047:23: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' ) | ~ ( ( '\\\\' | '}' ) ) )*
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
            	    // InternalKactors.g:6047:24: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' )
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
            	    // InternalKactors.g:6047:60: ~ ( ( '\\\\' | '}' ) )
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
            // InternalKactors.g:6049:16: ( '---' ( '-' )* )
            // InternalKactors.g:6049:18: '---' ( '-' )*
            {
            match("---"); 

            // InternalKactors.g:6049:24: ( '-' )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0=='-') ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalKactors.g:6049:24: '-'
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
            // InternalKactors.g:6051:20: ( '@' RULE_LOWERCASE_ID )
            // InternalKactors.g:6051:22: '@' RULE_LOWERCASE_ID
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
            // InternalKactors.g:6053:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalKactors.g:6053:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalKactors.g:6053:11: ( '^' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='^') ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalKactors.g:6053:11: '^'
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

            // InternalKactors.g:6053:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
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
            // InternalKactors.g:6055:10: ( ( '0' .. '9' )+ )
            // InternalKactors.g:6055:12: ( '0' .. '9' )+
            {
            // InternalKactors.g:6055:12: ( '0' .. '9' )+
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
            	    // InternalKactors.g:6055:13: '0' .. '9'
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
            // InternalKactors.g:6057:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // InternalKactors.g:6057:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // InternalKactors.g:6057:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
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
                    // InternalKactors.g:6057:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalKactors.g:6057:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
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
                    	    // InternalKactors.g:6057:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKactors.g:6057:28: ~ ( ( '\\\\' | '\"' ) )
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
                    // InternalKactors.g:6057:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalKactors.g:6057:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
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
                    	    // InternalKactors.g:6057:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKactors.g:6057:61: ~ ( ( '\\\\' | '\\'' ) )
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
            // InternalKactors.g:6059:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalKactors.g:6059:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalKactors.g:6059:24: ( options {greedy=false; } : . )*
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
            	    // InternalKactors.g:6059:52: .
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
            // InternalKactors.g:6061:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalKactors.g:6061:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalKactors.g:6061:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>='\u0000' && LA22_0<='\t')||(LA22_0>='\u000B' && LA22_0<='\f')||(LA22_0>='\u000E' && LA22_0<='\uFFFF')) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalKactors.g:6061:24: ~ ( ( '\\n' | '\\r' ) )
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

            // InternalKactors.g:6061:40: ( ( '\\r' )? '\\n' )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0=='\n'||LA24_0=='\r') ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalKactors.g:6061:41: ( '\\r' )? '\\n'
                    {
                    // InternalKactors.g:6061:41: ( '\\r' )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0=='\r') ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // InternalKactors.g:6061:41: '\\r'
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
            // InternalKactors.g:6063:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalKactors.g:6063:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalKactors.g:6063:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
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
            // InternalKactors.g:6065:16: ( . )
            // InternalKactors.g:6065:18: .
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
        // InternalKactors.g:1:8: ( T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | RULE_UPPERCASE_ID | RULE_UPPERCASE_PATH | RULE_CAMELCASE_ID | RULE_LOWERCASE_ID | RULE_ARGVALUE | RULE_EXPR | RULE_EMBEDDEDTEXT | RULE_REGEXP | RULE_OBSERVABLE | RULE_SEPARATOR | RULE_ANNOTATION_ID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt26=80;
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
                // InternalKactors.g:1:352: T__79
                {
                mT__79(); 

                }
                break;
            case 59 :
                // InternalKactors.g:1:358: T__80
                {
                mT__80(); 

                }
                break;
            case 60 :
                // InternalKactors.g:1:364: T__81
                {
                mT__81(); 

                }
                break;
            case 61 :
                // InternalKactors.g:1:370: T__82
                {
                mT__82(); 

                }
                break;
            case 62 :
                // InternalKactors.g:1:376: T__83
                {
                mT__83(); 

                }
                break;
            case 63 :
                // InternalKactors.g:1:382: RULE_UPPERCASE_ID
                {
                mRULE_UPPERCASE_ID(); 

                }
                break;
            case 64 :
                // InternalKactors.g:1:400: RULE_UPPERCASE_PATH
                {
                mRULE_UPPERCASE_PATH(); 

                }
                break;
            case 65 :
                // InternalKactors.g:1:420: RULE_CAMELCASE_ID
                {
                mRULE_CAMELCASE_ID(); 

                }
                break;
            case 66 :
                // InternalKactors.g:1:438: RULE_LOWERCASE_ID
                {
                mRULE_LOWERCASE_ID(); 

                }
                break;
            case 67 :
                // InternalKactors.g:1:456: RULE_ARGVALUE
                {
                mRULE_ARGVALUE(); 

                }
                break;
            case 68 :
                // InternalKactors.g:1:470: RULE_EXPR
                {
                mRULE_EXPR(); 

                }
                break;
            case 69 :
                // InternalKactors.g:1:480: RULE_EMBEDDEDTEXT
                {
                mRULE_EMBEDDEDTEXT(); 

                }
                break;
            case 70 :
                // InternalKactors.g:1:498: RULE_REGEXP
                {
                mRULE_REGEXP(); 

                }
                break;
            case 71 :
                // InternalKactors.g:1:510: RULE_OBSERVABLE
                {
                mRULE_OBSERVABLE(); 

                }
                break;
            case 72 :
                // InternalKactors.g:1:526: RULE_SEPARATOR
                {
                mRULE_SEPARATOR(); 

                }
                break;
            case 73 :
                // InternalKactors.g:1:541: RULE_ANNOTATION_ID
                {
                mRULE_ANNOTATION_ID(); 

                }
                break;
            case 74 :
                // InternalKactors.g:1:560: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 75 :
                // InternalKactors.g:1:568: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 76 :
                // InternalKactors.g:1:577: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 77 :
                // InternalKactors.g:1:589: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 78 :
                // InternalKactors.g:1:605: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 79 :
                // InternalKactors.g:1:621: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 80 :
                // InternalKactors.g:1:629: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA26 dfa26 = new DFA26(this);
    static final String DFA26_eotS =
        "\1\uffff\3\67\1\100\2\67\1\uffff\7\67\3\uffff\1\124\1\67\1\131\3\uffff\1\136\1\140\1\143\1\uffff\1\147\1\uffff\1\151\1\154\1\156\1\62\1\uffff\1\162\3\167\1\172\1\167\1\67\1\uffff\2\62\2\uffff\2\62\2\uffff\4\67\2\uffff\4\67\1\u008a\2\67\1\uffff\2\67\1\u008f\1\u0091\1\uffff\4\67\1\u0096\4\67\3\uffff\2\67\1\uffff\2\67\10\uffff\1\u009f\21\uffff\1\167\2\uffff\1\165\1\uffff\1\u00a0\1\uffff\1\u00a1\1\u00a2\3\uffff\1\176\4\uffff\1\u00a4\7\67\1\uffff\4\67\1\uffff\1\67\1\uffff\4\67\1\uffff\6\67\1\u00bb\1\67\6\uffff\2\67\1\u00bf\1\67\1\uffff\1\67\1\u00c2\15\67\1\u00d0\1\67\1\uffff\3\67\1\uffff\1\67\1\u00d6\1\uffff\1\67\1\u00d8\4\67\1\u00dd\6\67\1\uffff\1\67\1\u00e5\1\u00e6\1\u00e7\1\67\1\uffff\1\67\1\uffff\1\67\1\u00eb\2\67\1\uffff\7\67\3\uffff\1\u00f5\1\u00f6\1\67\1\uffff\5\67\1\u00fe\1\u00ff\2\67\2\uffff\1\u0102\6\67\2\uffff\1\u0109\1\67\1\uffff\1\u010b\1\u010c\1\u010d\3\67\1\uffff\1\u0111\3\uffff\1\u0112\2\67\2\uffff\1\u0115\1\u0116\2\uffff";
    static final String DFA26_eofS =
        "\u0117\uffff";
    static final String DFA26_minS =
        "\1\0\6\60\1\uffff\7\60\3\uffff\2\60\1\55\3\uffff\1\77\1\0\1\175\1\uffff\1\52\1\uffff\1\141\3\75\1\uffff\4\56\1\101\1\56\1\60\1\uffff\2\0\2\uffff\2\0\2\uffff\4\60\2\uffff\7\60\1\uffff\4\60\1\uffff\11\60\3\uffff\2\60\1\uffff\2\60\10\uffff\1\0\21\uffff\1\56\2\uffff\1\60\1\uffff\1\56\1\uffff\2\56\3\uffff\1\45\4\uffff\10\60\1\uffff\4\60\1\uffff\1\60\1\uffff\4\60\1\uffff\10\60\6\uffff\4\60\1\uffff\21\60\1\uffff\3\60\1\uffff\2\60\1\uffff\15\60\1\uffff\5\60\1\uffff\1\60\1\uffff\4\60\1\uffff\7\60\3\uffff\3\60\1\uffff\11\60\2\uffff\7\60\2\uffff\2\60\1\uffff\6\60\1\uffff\1\60\3\uffff\3\60\2\uffff\2\60\2\uffff";
    static final String DFA26_maxS =
        "\1\uffff\6\172\1\uffff\7\172\3\uffff\2\172\1\76\3\uffff\1\77\1\uffff\1\175\1\uffff\1\57\1\uffff\1\172\3\75\1\uffff\7\172\1\uffff\2\uffff\2\uffff\2\uffff\2\uffff\4\172\2\uffff\7\172\1\uffff\4\172\1\uffff\11\172\3\uffff\2\172\1\uffff\2\172\10\uffff\1\uffff\21\uffff\1\172\2\uffff\1\172\1\uffff\1\172\1\uffff\2\172\3\uffff\1\45\4\uffff\10\172\1\uffff\4\172\1\uffff\1\172\1\uffff\4\172\1\uffff\10\172\6\uffff\4\172\1\uffff\21\172\1\uffff\3\172\1\uffff\2\172\1\uffff\15\172\1\uffff\5\172\1\uffff\1\172\1\uffff\4\172\1\uffff\7\172\3\uffff\3\172\1\uffff\11\172\2\uffff\7\172\2\uffff\2\172\1\uffff\6\172\1\uffff\1\172\3\uffff\3\172\2\uffff\2\172\2\uffff";
    static final String DFA26_acceptS =
        "\7\uffff\1\10\7\uffff\1\23\1\24\1\25\3\uffff\1\43\1\44\1\46\3\uffff\1\54\1\uffff\1\56\4\uffff\1\66\7\uffff\1\103\2\uffff\1\112\1\113\2\uffff\1\117\1\120\4\uffff\1\102\1\112\7\uffff\1\70\4\uffff\1\10\11\uffff\1\23\1\24\1\25\2\uffff\1\71\2\uffff\1\34\1\110\1\67\1\43\1\44\1\46\1\57\1\47\1\uffff\1\50\1\107\1\53\1\51\1\54\1\115\1\116\1\55\1\56\1\60\1\111\1\65\1\61\1\64\1\62\1\63\1\66\1\uffff\1\72\1\100\1\uffff\1\101\1\uffff\1\77\2\uffff\1\76\1\103\1\104\1\uffff\1\106\1\113\1\114\1\117\10\uffff\1\41\4\uffff\1\26\1\uffff\1\33\4\uffff\1\31\10\uffff\1\52\1\73\1\74\1\75\1\105\1\1\4\uffff\1\45\21\uffff\1\32\3\uffff\1\2\2\uffff\1\35\15\uffff\1\27\5\uffff\1\3\1\uffff\1\13\4\uffff\1\30\7\uffff\1\36\1\16\1\22\3\uffff\1\7\11\uffff\1\42\1\4\7\uffff\1\17\1\20\2\uffff\1\5\6\uffff\1\21\1\uffff\1\6\1\37\1\11\3\uffff\1\40\1\12\2\uffff\1\14\1\15";
    static final String DFA26_specialS =
        "\1\0\30\uffff\1\1\21\uffff\1\4\1\5\2\uffff\1\6\1\3\56\uffff\1\2\u00b7\uffff}>";
    static final String[] DFA26_transitionS = {
            "\11\62\2\61\2\62\1\61\22\62\1\61\1\41\1\57\1\26\1\52\1\54\1\27\1\60\1\20\1\21\1\25\1\42\1\7\1\24\1\35\1\34\12\56\1\17\1\62\1\40\1\30\1\37\1\62\1\36\1\44\1\46\1\45\1\50\1\43\25\50\1\53\2\62\1\47\1\55\1\62\1\1\1\5\1\15\1\12\1\22\1\23\2\51\1\6\2\51\1\4\1\16\1\51\1\11\1\13\3\51\1\3\1\2\1\14\1\10\3\51\1\31\1\33\1\32\uff82\62",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\2\66\1\65\14\66\1\63\4\66\1\64\5\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\15\66\1\72\3\66\1\73\1\71\7\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\16\66\1\75\2\66\1\74\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\1\77\7\66\1\76\21\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\101\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\5\66\1\103\6\66\1\102\1\104\14\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\7\66\1\107\6\66\1\106\13\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\1\66\1\110\30\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\111\11\66\1\112\13\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\113\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\114\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\115\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\16\66\1\116\13\66",
            "",
            "",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\13\66\1\122\13\66\1\123\2\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\1\126\15\66\1\125\13\66",
            "\1\130\20\uffff\1\127",
            "",
            "",
            "",
            "\1\135",
            "\173\141\1\137\uff84\141",
            "\1\142",
            "",
            "\1\145\4\uffff\1\146",
            "",
            "\32\152",
            "\1\153",
            "\1\155",
            "\1\157",
            "",
            "\1\163\1\uffff\12\164\7\uffff\32\161\4\165\1\161\1\165\32\164",
            "\1\163\1\uffff\12\164\7\uffff\3\161\1\166\26\161\4\165\1\161\1\165\32\164",
            "\1\163\1\uffff\12\164\7\uffff\4\161\1\170\25\161\4\165\1\161\1\165\32\164",
            "\1\163\1\uffff\12\164\7\uffff\2\161\1\171\27\161\4\165\1\161\1\165\32\164",
            "\32\70\4\uffff\1\70\1\uffff\32\70",
            "\1\163\1\uffff\12\164\7\uffff\32\161\4\165\1\161\1\165\32\164",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "",
            "\0\174",
            "\45\176\1\175\uffda\176",
            "",
            "",
            "\0\u0080",
            "\0\u0080",
            "",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\17\66\1\u0082\12\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\23\66\1\u0083\6\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\23\66\1\u0084\6\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u0085\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\12\66\1\u0086\17\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\15\66\1\u0087\14\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u0088\23\66\1\u0089\5\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\1\66\1\u008b\30\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\1\66\1\u008c\30\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\7\66\1\u008d\22\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\17\66\1\u008e\12\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\2\66\1\u0090\27\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\u0092\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u0093\21\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\22\66\1\u0094\7\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\22\66\1\u0095\7\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\u0097\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\u0098\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u0099\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\3\66\1\u009a\26\66",
            "",
            "",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\22\66\1\u009b\7\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\2\66\1\u009c\27\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\u009d\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\13\66\1\u009e\16\66",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\0\141",
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
            "\1\163\1\uffff\12\164\7\uffff\32\161\4\165\1\161\1\165\32\164",
            "",
            "",
            "\12\164\7\uffff\32\164\4\uffff\1\164\1\uffff\32\164",
            "",
            "\1\163\1\uffff\12\164\7\uffff\32\161\4\165\1\161\1\165\32\164",
            "",
            "\1\163\1\uffff\12\164\7\uffff\32\161\4\165\1\161\1\165\32\164",
            "\1\163\1\uffff\12\164\7\uffff\32\161\4\165\1\161\1\165\32\164",
            "",
            "",
            "",
            "\1\u00a3",
            "",
            "",
            "",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\7\66\1\u00a5\22\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u00a6\21\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\u00a7\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\15\66\1\u00a8\14\66",
            "\12\66\1\u00a9\6\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u00aa\21\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u00ab\25\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\u00ac\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u00ad\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u00ae\31\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\16\66\1\u00af\13\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\13\66\1\u00b0\16\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\13\66\1\u00b1\16\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\13\66\1\u00b2\16\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u00b3\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\2\66\1\u00b4\27\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\14\66\1\u00b5\15\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\22\66\1\u00b6\7\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u00b7\31\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u00b8\21\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u00b9\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\13\66\1\u00ba\16\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\22\66\1\u00bc\7\66",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\16\66\1\u00bd\13\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\16\66\1\u00be\13\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\16\66\1\u00c0\13\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\23\66\1\u00c1\6\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u00c3\31\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\13\66\1\u00c4\16\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\25\66\1\u00c5\4\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\u00c6\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\24\66\1\u00c7\5\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\3\66\1\u00c8\26\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u00c9\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\u00ca\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\u00cb\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u00cc\21\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u00cd\21\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\23\66\1\u00ce\6\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\5\66\1\u00cf\24\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\24\66\1\u00d1\5\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u00d2\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\u00d3\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\15\66\1\u00d4\14\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\26\66\1\u00d5\3\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\u00d7\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u00d9\21\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\23\66\1\u00da\6\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\22\66\1\u00db\7\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\25\66\1\u00dc\4\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\25\66\1\u00de\4\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u00df\21\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\22\66\1\u00e0\7\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\16\66\1\u00e1\13\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u00e2\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u00e3\21\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\22\66\1\u00e4\7\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\15\66\1\u00e8\14\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\30\66\1\u00e9\1\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\16\66\1\u00ea\13\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u00ec\21\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u00ed\21\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u00ee\31\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\17\66\1\u00ef\12\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\22\66\1\u00f0\7\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\15\66\1\u00f1\14\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\3\66\1\u00f2\26\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u00f3\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u00f4\21\66",
            "",
            "",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\u00f7\2\66\1\u00f8\5\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\25\66\1\u00f9\4\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u00fa\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\1\66\1\u00fb\30\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\23\66\1\u00fc\6\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u00fd\21\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\3\66\1\u0100\26\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\25\66\1\u0101\4\66",
            "",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\21\66\1\u0103\10\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u0104\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\26\66\1\u0105\3\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\13\66\1\u0106\16\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\10\66\1\u0107\21\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\16\66\1\u0108\13\66",
            "",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u010a\25\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\4\66\1\u010e\25\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\16\66\1\u010f\13\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\15\66\1\u0110\14\66",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\15\66\1\u0113\14\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\22\66\1\u0114\7\66",
            "",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
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
            return "1:1: Tokens : ( T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | RULE_UPPERCASE_ID | RULE_UPPERCASE_PATH | RULE_CAMELCASE_ID | RULE_LOWERCASE_ID | RULE_ARGVALUE | RULE_EXPR | RULE_EMBEDDEDTEXT | RULE_REGEXP | RULE_OBSERVABLE | RULE_SEPARATOR | RULE_ANNOTATION_ID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA26_0 = input.LA(1);

                        s = -1;
                        if ( (LA26_0=='a') ) {s = 1;}

                        else if ( (LA26_0=='u') ) {s = 2;}

                        else if ( (LA26_0=='t') ) {s = 3;}

                        else if ( (LA26_0=='l') ) {s = 4;}

                        else if ( (LA26_0=='b') ) {s = 5;}

                        else if ( (LA26_0=='i') ) {s = 6;}

                        else if ( (LA26_0==',') ) {s = 7;}

                        else if ( (LA26_0=='w') ) {s = 8;}

                        else if ( (LA26_0=='o') ) {s = 9;}

                        else if ( (LA26_0=='d') ) {s = 10;}

                        else if ( (LA26_0=='p') ) {s = 11;}

                        else if ( (LA26_0=='v') ) {s = 12;}

                        else if ( (LA26_0=='c') ) {s = 13;}

                        else if ( (LA26_0=='m') ) {s = 14;}

                        else if ( (LA26_0==':') ) {s = 15;}

                        else if ( (LA26_0=='(') ) {s = 16;}

                        else if ( (LA26_0==')') ) {s = 17;}

                        else if ( (LA26_0=='e') ) {s = 18;}

                        else if ( (LA26_0=='f') ) {s = 19;}

                        else if ( (LA26_0=='-') ) {s = 20;}

                        else if ( (LA26_0=='*') ) {s = 21;}

                        else if ( (LA26_0=='#') ) {s = 22;}

                        else if ( (LA26_0=='&') ) {s = 23;}

                        else if ( (LA26_0=='=') ) {s = 24;}

                        else if ( (LA26_0=='{') ) {s = 25;}

                        else if ( (LA26_0=='}') ) {s = 26;}

                        else if ( (LA26_0=='|') ) {s = 27;}

                        else if ( (LA26_0=='/') ) {s = 28;}

                        else if ( (LA26_0=='.') ) {s = 29;}

                        else if ( (LA26_0=='@') ) {s = 30;}

                        else if ( (LA26_0=='>') ) {s = 31;}

                        else if ( (LA26_0=='<') ) {s = 32;}

                        else if ( (LA26_0=='!') ) {s = 33;}

                        else if ( (LA26_0=='+') ) {s = 34;}

                        else if ( (LA26_0=='E') ) {s = 35;}

                        else if ( (LA26_0=='A') ) {s = 36;}

                        else if ( (LA26_0=='C') ) {s = 37;}

                        else if ( (LA26_0=='B') ) {s = 38;}

                        else if ( (LA26_0=='^') ) {s = 39;}

                        else if ( (LA26_0=='D'||(LA26_0>='F' && LA26_0<='Z')) ) {s = 40;}

                        else if ( ((LA26_0>='g' && LA26_0<='h')||(LA26_0>='j' && LA26_0<='k')||LA26_0=='n'||(LA26_0>='q' && LA26_0<='s')||(LA26_0>='x' && LA26_0<='z')) ) {s = 41;}

                        else if ( (LA26_0=='$') ) {s = 42;}

                        else if ( (LA26_0=='[') ) {s = 43;}

                        else if ( (LA26_0=='%') ) {s = 44;}

                        else if ( (LA26_0=='_') ) {s = 45;}

                        else if ( ((LA26_0>='0' && LA26_0<='9')) ) {s = 46;}

                        else if ( (LA26_0=='\"') ) {s = 47;}

                        else if ( (LA26_0=='\'') ) {s = 48;}

                        else if ( ((LA26_0>='\t' && LA26_0<='\n')||LA26_0=='\r'||LA26_0==' ') ) {s = 49;}

                        else if ( ((LA26_0>='\u0000' && LA26_0<='\b')||(LA26_0>='\u000B' && LA26_0<='\f')||(LA26_0>='\u000E' && LA26_0<='\u001F')||LA26_0==';'||LA26_0=='?'||(LA26_0>='\\' && LA26_0<=']')||LA26_0=='`'||(LA26_0>='~' && LA26_0<='\uFFFF')) ) {s = 50;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA26_25 = input.LA(1);

                        s = -1;
                        if ( (LA26_25=='{') ) {s = 95;}

                        else if ( ((LA26_25>='\u0000' && LA26_25<='z')||(LA26_25>='|' && LA26_25<='\uFFFF')) ) {s = 97;}

                        else s = 96;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA26_95 = input.LA(1);

                        s = -1;
                        if ( ((LA26_95>='\u0000' && LA26_95<='\uFFFF')) ) {s = 97;}

                        else s = 159;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA26_48 = input.LA(1);

                        s = -1;
                        if ( ((LA26_48>='\u0000' && LA26_48<='\uFFFF')) ) {s = 128;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA26_43 = input.LA(1);

                        s = -1;
                        if ( ((LA26_43>='\u0000' && LA26_43<='\uFFFF')) ) {s = 124;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA26_44 = input.LA(1);

                        s = -1;
                        if ( (LA26_44=='%') ) {s = 125;}

                        else if ( ((LA26_44>='\u0000' && LA26_44<='$')||(LA26_44>='&' && LA26_44<='\uFFFF')) ) {s = 126;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA26_47 = input.LA(1);

                        s = -1;
                        if ( ((LA26_47>='\u0000' && LA26_47<='\uFFFF')) ) {s = 128;}

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
package org.integratedmodelling.kdl.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalKdlLexer extends Lexer {
    public static final int T__50=50;
    public static final int RULE_UPPERCASE_ID=8;
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
    public static final int RULE_ID=12;
    public static final int RULE_INT=10;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=15;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int RULE_EXPR=13;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int RULE_UPPERCASE_PATH=14;
    public static final int T__43=43;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int T__90=90;
    public static final int RULE_SHAPE=9;
    public static final int T__19=19;
    public static final int T__99=99;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int RULE_LOWERCASE_DASHID=5;
    public static final int RULE_CAMELCASE_ID=7;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_STRING=6;
    public static final int RULE_SL_COMMENT=16;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int EOF=-1;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=17;
    public static final int RULE_ANY_OTHER=18;
    public static final int RULE_ANNOTATION_ID=11;
    public static final int RULE_LOWERCASE_ID=4;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__84=84;
    public static final int T__104=104;
    public static final int T__85=85;
    public static final int T__103=103;
    public static final int T__86=86;
    public static final int T__87=87;

    // delegates
    // delegators

    public InternalKdlLexer() {;} 
    public InternalKdlLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalKdlLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalKdl.g"; }

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:11:7: ( 'number' )
            // InternalKdl.g:11:9: 'number'
            {
            match("number"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:12:7: ( 'boolean' )
            // InternalKdl.g:12:9: 'boolean'
            {
            match("boolean"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:13:7: ( 'text' )
            // InternalKdl.g:13:9: 'text'
            {
            match("text"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:14:7: ( 'list' )
            // InternalKdl.g:14:9: 'list'
            {
            match("list"); 


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
            // InternalKdl.g:15:7: ( 'enum' )
            // InternalKdl.g:15:9: 'enum'
            {
            match("enum"); 


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
            // InternalKdl.g:16:7: ( '*' )
            // InternalKdl.g:16:9: '*'
            {
            match('*'); 

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
            // InternalKdl.g:17:7: ( 'object' )
            // InternalKdl.g:17:9: 'object'
            {
            match("object"); 


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
            // InternalKdl.g:18:7: ( 'process' )
            // InternalKdl.g:18:9: 'process'
            {
            match("process"); 


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
            // InternalKdl.g:19:7: ( 'value' )
            // InternalKdl.g:19:9: 'value'
            {
            match("value"); 


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
            // InternalKdl.g:20:7: ( 'concept' )
            // InternalKdl.g:20:9: 'concept'
            {
            match("concept"); 


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
            // InternalKdl.g:21:7: ( 'extent' )
            // InternalKdl.g:21:9: 'extent'
            {
            match("extent"); 


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
            // InternalKdl.g:22:7: ( 'spatialextent' )
            // InternalKdl.g:22:9: 'spatialextent'
            {
            match("spatialextent"); 


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
            // InternalKdl.g:23:7: ( 'temporalextent' )
            // InternalKdl.g:23:9: 'temporalextent'
            {
            match("temporalextent"); 


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
            // InternalKdl.g:24:7: ( 'annotation' )
            // InternalKdl.g:24:9: 'annotation'
            {
            match("annotation"); 


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
            // InternalKdl.g:25:7: ( 'contextualizer' )
            // InternalKdl.g:25:9: 'contextualizer'
            {
            match("contextualizer"); 


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
            // InternalKdl.g:26:7: ( 'void' )
            // InternalKdl.g:26:9: 'void'
            {
            match("void"); 


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
            // InternalKdl.g:27:7: ( 'partition' )
            // InternalKdl.g:27:9: 'partition'
            {
            match("partition"); 


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
            // InternalKdl.g:28:7: ( 'models' )
            // InternalKdl.g:28:9: 'models'
            {
            match("models"); 


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
            // InternalKdl.g:29:7: ( 'concepts' )
            // InternalKdl.g:29:9: 'concepts'
            {
            match("concepts"); 


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
            // InternalKdl.g:30:7: ( 'observers' )
            // InternalKdl.g:30:9: 'observers'
            {
            match("observers"); 


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
            // InternalKdl.g:31:7: ( 'definitions' )
            // InternalKdl.g:31:9: 'definitions'
            {
            match("definitions"); 


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
            // InternalKdl.g:32:7: ( 'exclusive' )
            // InternalKdl.g:32:9: 'exclusive'
            {
            match("exclusive"); 


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
            // InternalKdl.g:33:7: ( 'true' )
            // InternalKdl.g:33:9: 'true'
            {
            match("true"); 


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
            // InternalKdl.g:34:7: ( 'false' )
            // InternalKdl.g:34:9: 'false'
            {
            match("false"); 


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
            // InternalKdl.g:35:7: ( '=' )
            // InternalKdl.g:35:9: '='
            {
            match('='); 

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
            // InternalKdl.g:36:7: ( '+' )
            // InternalKdl.g:36:9: '+'
            {
            match('+'); 

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
            // InternalKdl.g:37:7: ( 'e' )
            // InternalKdl.g:37:9: 'e'
            {
            match('e'); 

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
            // InternalKdl.g:38:7: ( 'E' )
            // InternalKdl.g:38:9: 'E'
            {
            match('E'); 

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
            // InternalKdl.g:39:7: ( '.' )
            // InternalKdl.g:39:9: '.'
            {
            match('.'); 

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
            // InternalKdl.g:40:7: ( '/' )
            // InternalKdl.g:40:9: '/'
            {
            match('/'); 

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
            // InternalKdl.g:41:7: ( '^' )
            // InternalKdl.g:41:9: '^'
            {
            match('^'); 

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
            // InternalKdl.g:42:7: ( '@dataflow' )
            // InternalKdl.g:42:9: '@dataflow'
            {
            match("@dataflow"); 


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
            // InternalKdl.g:43:7: ( '@var' )
            // InternalKdl.g:43:9: '@var'
            {
            match("@var"); 


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
            // InternalKdl.g:44:7: ( '@val' )
            // InternalKdl.g:44:9: '@val'
            {
            match("@val"); 


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
            // InternalKdl.g:45:7: ( '@author' )
            // InternalKdl.g:45:9: '@author'
            {
            match("@author"); 


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
            // InternalKdl.g:46:7: ( '@version' )
            // InternalKdl.g:46:9: '@version'
            {
            match("@version"); 


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
            // InternalKdl.g:47:7: ( '@klab' )
            // InternalKdl.g:47:9: '@klab'
            {
            match("@klab"); 


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
            // InternalKdl.g:48:7: ( '@worldview' )
            // InternalKdl.g:48:9: '@worldview'
            {
            match("@worldview"); 


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
            // InternalKdl.g:49:7: ( '@geometry' )
            // InternalKdl.g:49:9: '@geometry'
            {
            match("@geometry"); 


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
            // InternalKdl.g:50:7: ( '@endpoint' )
            // InternalKdl.g:50:9: '@endpoint'
            {
            match("@endpoint"); 


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
            // InternalKdl.g:51:7: ( '@namespace' )
            // InternalKdl.g:51:9: '@namespace'
            {
            match("@namespace"); 


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
            // InternalKdl.g:52:7: ( '@coverage' )
            // InternalKdl.g:52:9: '@coverage'
            {
            match("@coverage"); 


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
            // InternalKdl.g:53:7: ( ',' )
            // InternalKdl.g:53:9: ','
            {
            match(','); 

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
            // InternalKdl.g:54:7: ( '@context' )
            // InternalKdl.g:54:9: '@context'
            {
            match("@context"); 


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
            // InternalKdl.g:55:7: ( '(' )
            // InternalKdl.g:55:9: '('
            {
            match('('); 

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
            // InternalKdl.g:56:7: ( ')' )
            // InternalKdl.g:56:9: ')'
            {
            match(')'); 

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
            // InternalKdl.g:57:7: ( 'for' )
            // InternalKdl.g:57:9: 'for'
            {
            match("for"); 


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
            // InternalKdl.g:58:7: ( '{' )
            // InternalKdl.g:58:9: '{'
            {
            match('{'); 

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
            // InternalKdl.g:59:7: ( '}' )
            // InternalKdl.g:59:9: '}'
            {
            match('}'); 

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
            // InternalKdl.g:60:7: ( 'as' )
            // InternalKdl.g:60:9: 'as'
            {
            match("as"); 


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
            // InternalKdl.g:61:7: ( 'over' )
            // InternalKdl.g:61:9: 'over'
            {
            match("over"); 


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
            // InternalKdl.g:62:7: ( 'values' )
            // InternalKdl.g:62:9: 'values'
            {
            match("values"); 


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
            // InternalKdl.g:63:7: ( 'default' )
            // InternalKdl.g:63:9: 'default'
            {
            match("default"); 


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
            // InternalKdl.g:64:7: ( 'minimum' )
            // InternalKdl.g:64:9: 'minimum'
            {
            match("minimum"); 


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
            // InternalKdl.g:65:7: ( 'maximum' )
            // InternalKdl.g:65:9: 'maximum'
            {
            match("maximum"); 


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
            // InternalKdl.g:66:7: ( 'range' )
            // InternalKdl.g:66:9: 'range'
            {
            match("range"); 


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
            // InternalKdl.g:67:7: ( 'to' )
            // InternalKdl.g:67:9: 'to'
            {
            match("to"); 


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
            // InternalKdl.g:68:7: ( 'geometry' )
            // InternalKdl.g:68:9: 'geometry'
            {
            match("geometry"); 


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
            // InternalKdl.g:69:7: ( 'units' )
            // InternalKdl.g:69:9: 'units'
            {
            match("units"); 


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
            // InternalKdl.g:70:7: ( 'metadata' )
            // InternalKdl.g:70:9: 'metadata'
            {
            match("metadata"); 


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
            // InternalKdl.g:71:7: ( 'class' )
            // InternalKdl.g:71:9: 'class'
            {
            match("class"); 


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
            // InternalKdl.g:72:7: ( 'compute' )
            // InternalKdl.g:72:9: 'compute'
            {
            match("compute"); 


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
            // InternalKdl.g:73:7: ( 'in' )
            // InternalKdl.g:73:9: 'in'
            {
            match("in"); 


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
            // InternalKdl.g:74:7: ( 'urn:klab:' )
            // InternalKdl.g:74:9: 'urn:klab:'
            {
            match("urn:klab:"); 


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
            // InternalKdl.g:75:7: ( ':' )
            // InternalKdl.g:75:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__83"

    // $ANTLR start "T__84"
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:76:7: ( '#' )
            // InternalKdl.g:76:9: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__84"

    // $ANTLR start "T__85"
    public final void mT__85() throws RecognitionException {
        try {
            int _type = T__85;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:77:7: ( '{{' )
            // InternalKdl.g:77:9: '{{'
            {
            match("{{"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__85"

    // $ANTLR start "T__86"
    public final void mT__86() throws RecognitionException {
        try {
            int _type = T__86;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:78:7: ( '}}' )
            // InternalKdl.g:78:9: '}}'
            {
            match("}}"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__86"

    // $ANTLR start "T__87"
    public final void mT__87() throws RecognitionException {
        try {
            int _type = T__87;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:79:7: ( '|' )
            // InternalKdl.g:79:9: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__87"

    // $ANTLR start "T__88"
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:80:7: ( '>>' )
            // InternalKdl.g:80:9: '>>'
            {
            match(">>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__88"

    // $ANTLR start "T__89"
    public final void mT__89() throws RecognitionException {
        try {
            int _type = T__89;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:81:7: ( '-' )
            // InternalKdl.g:81:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__89"

    // $ANTLR start "T__90"
    public final void mT__90() throws RecognitionException {
        try {
            int _type = T__90;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:82:7: ( 'final' )
            // InternalKdl.g:82:9: 'final'
            {
            match("final"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__90"

    // $ANTLR start "T__91"
    public final void mT__91() throws RecognitionException {
        try {
            int _type = T__91;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:83:7: ( 'export' )
            // InternalKdl.g:83:9: 'export'
            {
            match("export"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__91"

    // $ANTLR start "T__92"
    public final void mT__92() throws RecognitionException {
        try {
            int _type = T__92;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:84:7: ( 'optional' )
            // InternalKdl.g:84:9: 'optional'
            {
            match("optional"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__92"

    // $ANTLR start "T__93"
    public final void mT__93() throws RecognitionException {
        try {
            int _type = T__93;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:85:7: ( 'import' )
            // InternalKdl.g:85:9: 'import'
            {
            match("import"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__93"

    // $ANTLR start "T__94"
    public final void mT__94() throws RecognitionException {
        try {
            int _type = T__94;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:86:7: ( 'multiple' )
            // InternalKdl.g:86:9: 'multiple'
            {
            match("multiple"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__94"

    // $ANTLR start "T__95"
    public final void mT__95() throws RecognitionException {
        try {
            int _type = T__95;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:87:7: ( 'parameter' )
            // InternalKdl.g:87:9: 'parameter'
            {
            match("parameter"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__95"

    // $ANTLR start "T__96"
    public final void mT__96() throws RecognitionException {
        try {
            int _type = T__96;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:88:7: ( 'input' )
            // InternalKdl.g:88:9: 'input'
            {
            match("input"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__96"

    // $ANTLR start "T__97"
    public final void mT__97() throws RecognitionException {
        try {
            int _type = T__97;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:89:7: ( 'inclusive' )
            // InternalKdl.g:89:9: 'inclusive'
            {
            match("inclusive"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__97"

    // $ANTLR start "T__98"
    public final void mT__98() throws RecognitionException {
        try {
            int _type = T__98;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:90:7: ( 'unknown' )
            // InternalKdl.g:90:9: 'unknown'
            {
            match("unknown"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__98"

    // $ANTLR start "T__99"
    public final void mT__99() throws RecognitionException {
        try {
            int _type = T__99;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:91:7: ( '=?' )
            // InternalKdl.g:91:9: '=?'
            {
            match("=?"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__99"

    // $ANTLR start "T__100"
    public final void mT__100() throws RecognitionException {
        try {
            int _type = T__100;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:92:8: ( '>' )
            // InternalKdl.g:92:10: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__100"

    // $ANTLR start "T__101"
    public final void mT__101() throws RecognitionException {
        try {
            int _type = T__101;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:93:8: ( '<' )
            // InternalKdl.g:93:10: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__101"

    // $ANTLR start "T__102"
    public final void mT__102() throws RecognitionException {
        try {
            int _type = T__102;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:94:8: ( '!=' )
            // InternalKdl.g:94:10: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__102"

    // $ANTLR start "T__103"
    public final void mT__103() throws RecognitionException {
        try {
            int _type = T__103;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:95:8: ( '<=' )
            // InternalKdl.g:95:10: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__103"

    // $ANTLR start "T__104"
    public final void mT__104() throws RecognitionException {
        try {
            int _type = T__104;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:96:8: ( '>=' )
            // InternalKdl.g:96:10: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__104"

    // $ANTLR start "RULE_ANNOTATION_ID"
    public final void mRULE_ANNOTATION_ID() throws RecognitionException {
        try {
            int _type = RULE_ANNOTATION_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:13044:20: ( '@' RULE_LOWERCASE_ID )
            // InternalKdl.g:13044:22: '@' RULE_LOWERCASE_ID
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

    // $ANTLR start "RULE_EXPR"
    public final void mRULE_EXPR() throws RecognitionException {
        try {
            int _type = RULE_EXPR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:13046:11: ( '[' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )* ']' )
            // InternalKdl.g:13046:13: '[' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )* ']'
            {
            match('['); 
            // InternalKdl.g:13046:17: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )*
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='\\') ) {
                    alt1=1;
                }
                else if ( ((LA1_0>='\u0000' && LA1_0<='[')||(LA1_0>='^' && LA1_0<='\uFFFF')) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalKdl.g:13046:18: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' )
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
            	    // InternalKdl.g:13046:58: ~ ( ( '\\\\' | ']' ) )
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
            	    break loop1;
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

    // $ANTLR start "RULE_LOWERCASE_ID"
    public final void mRULE_LOWERCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_LOWERCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:13048:19: ( 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )* )
            // InternalKdl.g:13048:21: 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )*
            {
            matchRange('a','z'); 
            // InternalKdl.g:13048:30: ( 'a' .. 'z' | '0' .. '9' | '_' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalKdl.g:
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
    // $ANTLR end "RULE_LOWERCASE_ID"

    // $ANTLR start "RULE_LOWERCASE_DASHID"
    public final void mRULE_LOWERCASE_DASHID() throws RecognitionException {
        try {
            int _type = RULE_LOWERCASE_DASHID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:13050:23: ( 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '-' )* )
            // InternalKdl.g:13050:25: 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '-' )*
            {
            matchRange('a','z'); 
            // InternalKdl.g:13050:34: ( 'a' .. 'z' | '0' .. '9' | '-' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='-'||(LA3_0>='0' && LA3_0<='9')||(LA3_0>='a' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalKdl.g:
            	    {
            	    if ( input.LA(1)=='-'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
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
    // $ANTLR end "RULE_LOWERCASE_DASHID"

    // $ANTLR start "RULE_SHAPE"
    public final void mRULE_SHAPE() throws RecognitionException {
        try {
            int _type = RULE_SHAPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:13052:12: ( ( '#' | ( '#' )? ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+ ) )
            // InternalKdl.g:13052:14: ( '#' | ( '#' )? ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+ )
            {
            // InternalKdl.g:13052:14: ( '#' | ( '#' )? ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+ )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='#') ) {
                int LA6_1 = input.LA(2);

                if ( ((LA6_1>='A' && LA6_1<='z')) ) {
                    alt6=2;
                }
                else {
                    alt6=1;}
            }
            else if ( ((LA6_0>='A' && LA6_0<='z')) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // InternalKdl.g:13052:15: '#'
                    {
                    match('#'); 

                    }
                    break;
                case 2 :
                    // InternalKdl.g:13052:19: ( '#' )? ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+
                    {
                    // InternalKdl.g:13052:19: ( '#' )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='#') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // InternalKdl.g:13052:19: '#'
                            {
                            match('#'); 

                            }
                            break;

                    }

                    // InternalKdl.g:13052:24: ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0>='A' && LA5_0<='z')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // InternalKdl.g:13052:25: 'A' .. 'z' ( '.' | '0' .. '3' )
                    	    {
                    	    matchRange('A','z'); 
                    	    if ( input.LA(1)=='.'||(input.LA(1)>='0' && input.LA(1)<='3') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt5 >= 1 ) break loop5;
                                EarlyExitException eee =
                                    new EarlyExitException(5, input);
                                throw eee;
                        }
                        cnt5++;
                    } while (true);


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
    // $ANTLR end "RULE_SHAPE"

    // $ANTLR start "RULE_UPPERCASE_ID"
    public final void mRULE_UPPERCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_UPPERCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:13054:19: ( 'A' .. 'Z' ( 'A' .. 'Z' )* )
            // InternalKdl.g:13054:21: 'A' .. 'Z' ( 'A' .. 'Z' )*
            {
            matchRange('A','Z'); 
            // InternalKdl.g:13054:30: ( 'A' .. 'Z' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='A' && LA7_0<='Z')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalKdl.g:13054:31: 'A' .. 'Z'
            	    {
            	    matchRange('A','Z'); 

            	    }
            	    break;

            	default :
            	    break loop7;
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
            // InternalKdl.g:13056:21: ( RULE_UPPERCASE_ID ( '.' RULE_UPPERCASE_ID )* )
            // InternalKdl.g:13056:23: RULE_UPPERCASE_ID ( '.' RULE_UPPERCASE_ID )*
            {
            mRULE_UPPERCASE_ID(); 
            // InternalKdl.g:13056:41: ( '.' RULE_UPPERCASE_ID )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0=='.') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalKdl.g:13056:42: '.' RULE_UPPERCASE_ID
            	    {
            	    match('.'); 
            	    mRULE_UPPERCASE_ID(); 

            	    }
            	    break;

            	default :
            	    break loop8;
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
            // InternalKdl.g:13058:19: ( 'A' .. 'Z' ( 'A' .. 'z' | '0' .. '9' )* )
            // InternalKdl.g:13058:21: 'A' .. 'Z' ( 'A' .. 'z' | '0' .. '9' )*
            {
            matchRange('A','Z'); 
            // InternalKdl.g:13058:30: ( 'A' .. 'z' | '0' .. '9' )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='0' && LA9_0<='9')||(LA9_0>='A' && LA9_0<='z')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalKdl.g:
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
            	    break loop9;
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

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:13060:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalKdl.g:13060:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalKdl.g:13060:11: ( '^' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='^') ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalKdl.g:13060:11: '^'
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

            // InternalKdl.g:13060:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='0' && LA11_0<='9')||(LA11_0>='A' && LA11_0<='Z')||LA11_0=='_'||(LA11_0>='a' && LA11_0<='z')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalKdl.g:
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
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:13062:10: ( ( '0' .. '9' )+ )
            // InternalKdl.g:13062:12: ( '0' .. '9' )+
            {
            // InternalKdl.g:13062:12: ( '0' .. '9' )+
            int cnt12=0;
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalKdl.g:13062:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
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
            // InternalKdl.g:13064:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // InternalKdl.g:13064:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // InternalKdl.g:13064:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\"') ) {
                alt15=1;
            }
            else if ( (LA15_0=='\'') ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // InternalKdl.g:13064:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalKdl.g:13064:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop13:
                    do {
                        int alt13=3;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0=='\\') ) {
                            alt13=1;
                        }
                        else if ( ((LA13_0>='\u0000' && LA13_0<='!')||(LA13_0>='#' && LA13_0<='[')||(LA13_0>=']' && LA13_0<='\uFFFF')) ) {
                            alt13=2;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // InternalKdl.g:13064:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKdl.g:13064:28: ~ ( ( '\\\\' | '\"' ) )
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
                    	    break loop13;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // InternalKdl.g:13064:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalKdl.g:13064:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop14:
                    do {
                        int alt14=3;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0=='\\') ) {
                            alt14=1;
                        }
                        else if ( ((LA14_0>='\u0000' && LA14_0<='&')||(LA14_0>='(' && LA14_0<='[')||(LA14_0>=']' && LA14_0<='\uFFFF')) ) {
                            alt14=2;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // InternalKdl.g:13064:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKdl.g:13064:61: ~ ( ( '\\\\' | '\\'' ) )
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
                    	    break loop14;
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
            // InternalKdl.g:13066:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalKdl.g:13066:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalKdl.g:13066:24: ( options {greedy=false; } : . )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0=='*') ) {
                    int LA16_1 = input.LA(2);

                    if ( (LA16_1=='/') ) {
                        alt16=2;
                    }
                    else if ( ((LA16_1>='\u0000' && LA16_1<='.')||(LA16_1>='0' && LA16_1<='\uFFFF')) ) {
                        alt16=1;
                    }


                }
                else if ( ((LA16_0>='\u0000' && LA16_0<=')')||(LA16_0>='+' && LA16_0<='\uFFFF')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalKdl.g:13066:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop16;
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
            // InternalKdl.g:13068:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalKdl.g:13068:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalKdl.g:13068:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>='\u0000' && LA17_0<='\t')||(LA17_0>='\u000B' && LA17_0<='\f')||(LA17_0>='\u000E' && LA17_0<='\uFFFF')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalKdl.g:13068:24: ~ ( ( '\\n' | '\\r' ) )
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
            	    break loop17;
                }
            } while (true);

            // InternalKdl.g:13068:40: ( ( '\\r' )? '\\n' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0=='\n'||LA19_0=='\r') ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalKdl.g:13068:41: ( '\\r' )? '\\n'
                    {
                    // InternalKdl.g:13068:41: ( '\\r' )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0=='\r') ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalKdl.g:13068:41: '\\r'
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
            // InternalKdl.g:13070:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalKdl.g:13070:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalKdl.g:13070:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt20=0;
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>='\t' && LA20_0<='\n')||LA20_0=='\r'||LA20_0==' ') ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalKdl.g:
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
            	    if ( cnt20 >= 1 ) break loop20;
                        EarlyExitException eee =
                            new EarlyExitException(20, input);
                        throw eee;
                }
                cnt20++;
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
            // InternalKdl.g:13072:16: ( . )
            // InternalKdl.g:13072:18: .
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
        // InternalKdl.g:1:8: ( T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | RULE_ANNOTATION_ID | RULE_EXPR | RULE_LOWERCASE_ID | RULE_LOWERCASE_DASHID | RULE_SHAPE | RULE_UPPERCASE_ID | RULE_UPPERCASE_PATH | RULE_CAMELCASE_ID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt21=101;
        alt21 = dfa21.predict(input);
        switch (alt21) {
            case 1 :
                // InternalKdl.g:1:10: T__19
                {
                mT__19(); 

                }
                break;
            case 2 :
                // InternalKdl.g:1:16: T__20
                {
                mT__20(); 

                }
                break;
            case 3 :
                // InternalKdl.g:1:22: T__21
                {
                mT__21(); 

                }
                break;
            case 4 :
                // InternalKdl.g:1:28: T__22
                {
                mT__22(); 

                }
                break;
            case 5 :
                // InternalKdl.g:1:34: T__23
                {
                mT__23(); 

                }
                break;
            case 6 :
                // InternalKdl.g:1:40: T__24
                {
                mT__24(); 

                }
                break;
            case 7 :
                // InternalKdl.g:1:46: T__25
                {
                mT__25(); 

                }
                break;
            case 8 :
                // InternalKdl.g:1:52: T__26
                {
                mT__26(); 

                }
                break;
            case 9 :
                // InternalKdl.g:1:58: T__27
                {
                mT__27(); 

                }
                break;
            case 10 :
                // InternalKdl.g:1:64: T__28
                {
                mT__28(); 

                }
                break;
            case 11 :
                // InternalKdl.g:1:70: T__29
                {
                mT__29(); 

                }
                break;
            case 12 :
                // InternalKdl.g:1:76: T__30
                {
                mT__30(); 

                }
                break;
            case 13 :
                // InternalKdl.g:1:82: T__31
                {
                mT__31(); 

                }
                break;
            case 14 :
                // InternalKdl.g:1:88: T__32
                {
                mT__32(); 

                }
                break;
            case 15 :
                // InternalKdl.g:1:94: T__33
                {
                mT__33(); 

                }
                break;
            case 16 :
                // InternalKdl.g:1:100: T__34
                {
                mT__34(); 

                }
                break;
            case 17 :
                // InternalKdl.g:1:106: T__35
                {
                mT__35(); 

                }
                break;
            case 18 :
                // InternalKdl.g:1:112: T__36
                {
                mT__36(); 

                }
                break;
            case 19 :
                // InternalKdl.g:1:118: T__37
                {
                mT__37(); 

                }
                break;
            case 20 :
                // InternalKdl.g:1:124: T__38
                {
                mT__38(); 

                }
                break;
            case 21 :
                // InternalKdl.g:1:130: T__39
                {
                mT__39(); 

                }
                break;
            case 22 :
                // InternalKdl.g:1:136: T__40
                {
                mT__40(); 

                }
                break;
            case 23 :
                // InternalKdl.g:1:142: T__41
                {
                mT__41(); 

                }
                break;
            case 24 :
                // InternalKdl.g:1:148: T__42
                {
                mT__42(); 

                }
                break;
            case 25 :
                // InternalKdl.g:1:154: T__43
                {
                mT__43(); 

                }
                break;
            case 26 :
                // InternalKdl.g:1:160: T__44
                {
                mT__44(); 

                }
                break;
            case 27 :
                // InternalKdl.g:1:166: T__45
                {
                mT__45(); 

                }
                break;
            case 28 :
                // InternalKdl.g:1:172: T__46
                {
                mT__46(); 

                }
                break;
            case 29 :
                // InternalKdl.g:1:178: T__47
                {
                mT__47(); 

                }
                break;
            case 30 :
                // InternalKdl.g:1:184: T__48
                {
                mT__48(); 

                }
                break;
            case 31 :
                // InternalKdl.g:1:190: T__49
                {
                mT__49(); 

                }
                break;
            case 32 :
                // InternalKdl.g:1:196: T__50
                {
                mT__50(); 

                }
                break;
            case 33 :
                // InternalKdl.g:1:202: T__51
                {
                mT__51(); 

                }
                break;
            case 34 :
                // InternalKdl.g:1:208: T__52
                {
                mT__52(); 

                }
                break;
            case 35 :
                // InternalKdl.g:1:214: T__53
                {
                mT__53(); 

                }
                break;
            case 36 :
                // InternalKdl.g:1:220: T__54
                {
                mT__54(); 

                }
                break;
            case 37 :
                // InternalKdl.g:1:226: T__55
                {
                mT__55(); 

                }
                break;
            case 38 :
                // InternalKdl.g:1:232: T__56
                {
                mT__56(); 

                }
                break;
            case 39 :
                // InternalKdl.g:1:238: T__57
                {
                mT__57(); 

                }
                break;
            case 40 :
                // InternalKdl.g:1:244: T__58
                {
                mT__58(); 

                }
                break;
            case 41 :
                // InternalKdl.g:1:250: T__59
                {
                mT__59(); 

                }
                break;
            case 42 :
                // InternalKdl.g:1:256: T__60
                {
                mT__60(); 

                }
                break;
            case 43 :
                // InternalKdl.g:1:262: T__61
                {
                mT__61(); 

                }
                break;
            case 44 :
                // InternalKdl.g:1:268: T__62
                {
                mT__62(); 

                }
                break;
            case 45 :
                // InternalKdl.g:1:274: T__63
                {
                mT__63(); 

                }
                break;
            case 46 :
                // InternalKdl.g:1:280: T__64
                {
                mT__64(); 

                }
                break;
            case 47 :
                // InternalKdl.g:1:286: T__65
                {
                mT__65(); 

                }
                break;
            case 48 :
                // InternalKdl.g:1:292: T__66
                {
                mT__66(); 

                }
                break;
            case 49 :
                // InternalKdl.g:1:298: T__67
                {
                mT__67(); 

                }
                break;
            case 50 :
                // InternalKdl.g:1:304: T__68
                {
                mT__68(); 

                }
                break;
            case 51 :
                // InternalKdl.g:1:310: T__69
                {
                mT__69(); 

                }
                break;
            case 52 :
                // InternalKdl.g:1:316: T__70
                {
                mT__70(); 

                }
                break;
            case 53 :
                // InternalKdl.g:1:322: T__71
                {
                mT__71(); 

                }
                break;
            case 54 :
                // InternalKdl.g:1:328: T__72
                {
                mT__72(); 

                }
                break;
            case 55 :
                // InternalKdl.g:1:334: T__73
                {
                mT__73(); 

                }
                break;
            case 56 :
                // InternalKdl.g:1:340: T__74
                {
                mT__74(); 

                }
                break;
            case 57 :
                // InternalKdl.g:1:346: T__75
                {
                mT__75(); 

                }
                break;
            case 58 :
                // InternalKdl.g:1:352: T__76
                {
                mT__76(); 

                }
                break;
            case 59 :
                // InternalKdl.g:1:358: T__77
                {
                mT__77(); 

                }
                break;
            case 60 :
                // InternalKdl.g:1:364: T__78
                {
                mT__78(); 

                }
                break;
            case 61 :
                // InternalKdl.g:1:370: T__79
                {
                mT__79(); 

                }
                break;
            case 62 :
                // InternalKdl.g:1:376: T__80
                {
                mT__80(); 

                }
                break;
            case 63 :
                // InternalKdl.g:1:382: T__81
                {
                mT__81(); 

                }
                break;
            case 64 :
                // InternalKdl.g:1:388: T__82
                {
                mT__82(); 

                }
                break;
            case 65 :
                // InternalKdl.g:1:394: T__83
                {
                mT__83(); 

                }
                break;
            case 66 :
                // InternalKdl.g:1:400: T__84
                {
                mT__84(); 

                }
                break;
            case 67 :
                // InternalKdl.g:1:406: T__85
                {
                mT__85(); 

                }
                break;
            case 68 :
                // InternalKdl.g:1:412: T__86
                {
                mT__86(); 

                }
                break;
            case 69 :
                // InternalKdl.g:1:418: T__87
                {
                mT__87(); 

                }
                break;
            case 70 :
                // InternalKdl.g:1:424: T__88
                {
                mT__88(); 

                }
                break;
            case 71 :
                // InternalKdl.g:1:430: T__89
                {
                mT__89(); 

                }
                break;
            case 72 :
                // InternalKdl.g:1:436: T__90
                {
                mT__90(); 

                }
                break;
            case 73 :
                // InternalKdl.g:1:442: T__91
                {
                mT__91(); 

                }
                break;
            case 74 :
                // InternalKdl.g:1:448: T__92
                {
                mT__92(); 

                }
                break;
            case 75 :
                // InternalKdl.g:1:454: T__93
                {
                mT__93(); 

                }
                break;
            case 76 :
                // InternalKdl.g:1:460: T__94
                {
                mT__94(); 

                }
                break;
            case 77 :
                // InternalKdl.g:1:466: T__95
                {
                mT__95(); 

                }
                break;
            case 78 :
                // InternalKdl.g:1:472: T__96
                {
                mT__96(); 

                }
                break;
            case 79 :
                // InternalKdl.g:1:478: T__97
                {
                mT__97(); 

                }
                break;
            case 80 :
                // InternalKdl.g:1:484: T__98
                {
                mT__98(); 

                }
                break;
            case 81 :
                // InternalKdl.g:1:490: T__99
                {
                mT__99(); 

                }
                break;
            case 82 :
                // InternalKdl.g:1:496: T__100
                {
                mT__100(); 

                }
                break;
            case 83 :
                // InternalKdl.g:1:503: T__101
                {
                mT__101(); 

                }
                break;
            case 84 :
                // InternalKdl.g:1:510: T__102
                {
                mT__102(); 

                }
                break;
            case 85 :
                // InternalKdl.g:1:517: T__103
                {
                mT__103(); 

                }
                break;
            case 86 :
                // InternalKdl.g:1:524: T__104
                {
                mT__104(); 

                }
                break;
            case 87 :
                // InternalKdl.g:1:531: RULE_ANNOTATION_ID
                {
                mRULE_ANNOTATION_ID(); 

                }
                break;
            case 88 :
                // InternalKdl.g:1:550: RULE_EXPR
                {
                mRULE_EXPR(); 

                }
                break;
            case 89 :
                // InternalKdl.g:1:560: RULE_LOWERCASE_ID
                {
                mRULE_LOWERCASE_ID(); 

                }
                break;
            case 90 :
                // InternalKdl.g:1:578: RULE_LOWERCASE_DASHID
                {
                mRULE_LOWERCASE_DASHID(); 

                }
                break;
            case 91 :
                // InternalKdl.g:1:600: RULE_SHAPE
                {
                mRULE_SHAPE(); 

                }
                break;
            case 92 :
                // InternalKdl.g:1:611: RULE_UPPERCASE_ID
                {
                mRULE_UPPERCASE_ID(); 

                }
                break;
            case 93 :
                // InternalKdl.g:1:629: RULE_UPPERCASE_PATH
                {
                mRULE_UPPERCASE_PATH(); 

                }
                break;
            case 94 :
                // InternalKdl.g:1:649: RULE_CAMELCASE_ID
                {
                mRULE_CAMELCASE_ID(); 

                }
                break;
            case 95 :
                // InternalKdl.g:1:667: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 96 :
                // InternalKdl.g:1:675: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 97 :
                // InternalKdl.g:1:684: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 98 :
                // InternalKdl.g:1:696: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 99 :
                // InternalKdl.g:1:712: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 100 :
                // InternalKdl.g:1:728: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 101 :
                // InternalKdl.g:1:736: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA21 dfa21 = new DFA21(this);
    static final String DFA21_eotS =
        "\1\uffff\4\65\1\100\1\uffff\11\65\1\130\1\uffff\1\135\1\uffff\1\143\1\144\1\60\3\uffff\1\163\1\165\4\65\1\uffff\1\175\1\uffff\1\u0081\1\uffff\1\u0084\2\60\1\65\1\u0088\1\70\1\60\1\uffff\2\60\2\uffff\2\65\1\uffff\1\65\1\uffff\1\65\2\uffff\3\65\1\u0095\3\65\2\uffff\13\65\1\u00a8\11\65\3\uffff\2\63\1\u0088\2\uffff\1\136\5\uffff\11\156\10\uffff\4\65\1\u00c7\1\65\12\uffff\1\63\2\uffff\1\63\3\uffff\3\65\1\70\4\65\1\uffff\22\65\1\uffff\7\65\1\u00ee\1\65\2\136\1\u00b5\1\uffff\12\156\7\65\1\uffff\1\65\1\u0087\2\uffff\3\65\1\u0107\1\65\1\u0109\1\u010a\1\u010b\5\65\1\u0111\5\65\1\u0117\16\65\1\uffff\1\65\1\63\1\156\1\u0128\1\u0129\11\156\4\65\1\uffff\5\65\1\uffff\1\65\3\uffff\5\65\1\uffff\4\65\1\u0147\1\uffff\3\65\1\u014b\11\65\1\u0155\1\u0156\1\156\2\uffff\2\156\1\u015a\6\156\1\u0161\1\65\1\u0163\1\65\1\u0165\2\65\1\u0168\2\65\1\u016b\1\65\1\u016d\1\u016e\5\65\1\u0174\1\uffff\3\65\1\uffff\2\65\1\u017a\6\65\2\uffff\3\156\1\uffff\6\156\1\uffff\1\65\1\uffff\1\65\1\uffff\1\65\1\u018d\1\uffff\1\u018e\1\65\1\uffff\1\65\2\uffff\2\65\1\u0193\2\65\1\uffff\1\u0197\1\65\1\u0199\2\65\1\uffff\1\u019c\1\u019d\3\65\1\u01a1\2\156\1\u01a4\6\156\1\65\1\u01ac\1\65\2\uffff\3\65\1\u01b1\1\uffff\2\65\1\u01b4\1\uffff\1\65\1\uffff\2\65\2\uffff\1\u01b8\1\u01b9\1\65\1\uffff\1\156\1\u01bc\1\uffff\5\156\1\u01c2\1\u01c3\1\uffff\2\65\1\u01c6\1\u01c7\1\uffff\1\u01c8\1\u01c9\1\uffff\3\65\2\uffff\1\65\1\u01ce\1\uffff\1\156\1\u01d0\1\u01d1\1\156\1\u01d3\2\uffff\1\u01d4\1\65\4\uffff\2\65\1\u01d8\1\65\1\uffff\1\u01da\2\uffff\1\u01db\2\uffff\3\65\1\uffff\1\u01df\2\uffff\3\65\1\uffff\2\65\1\u01e5\1\u01e6\1\u01e7\3\uffff";
    static final String DFA21_eofS =
        "\u01e8\uffff";
    static final String DFA21_minS =
        "\1\0\5\55\1\uffff\11\55\1\77\1\uffff\1\56\1\uffff\1\52\1\56\1\141\3\uffff\1\173\1\175\4\55\1\uffff\1\101\1\uffff\1\75\1\uffff\2\75\1\0\1\55\3\56\1\uffff\2\0\2\uffff\2\55\1\uffff\1\55\1\uffff\1\60\2\uffff\7\55\2\uffff\25\55\3\uffff\1\60\1\101\1\56\2\uffff\1\60\5\uffff\2\141\1\165\1\154\1\157\1\145\1\156\1\141\1\157\10\uffff\6\55\12\uffff\1\0\2\uffff\1\60\3\uffff\2\55\2\56\4\55\1\uffff\22\55\1\uffff\11\55\3\56\1\uffff\1\164\1\154\1\162\1\164\1\141\1\162\1\157\1\144\1\155\1\156\7\55\1\uffff\1\55\2\56\1\0\1\55\1\60\40\55\1\uffff\1\55\1\60\1\141\2\60\1\163\1\150\1\142\1\154\1\155\1\160\2\145\1\164\4\55\1\uffff\5\55\1\uffff\1\55\3\uffff\5\55\1\uffff\5\55\1\uffff\17\55\1\146\2\uffff\1\151\1\157\1\60\1\144\1\145\1\157\1\163\1\162\1\145\24\55\1\uffff\3\55\1\uffff\11\55\2\uffff\1\154\1\157\1\162\1\uffff\1\166\1\164\1\151\1\160\1\141\1\170\1\uffff\1\55\1\uffff\1\55\1\uffff\2\55\1\uffff\2\55\1\uffff\1\55\2\uffff\5\55\1\uffff\5\55\1\uffff\6\55\1\157\1\156\1\60\1\151\1\162\1\156\1\141\1\147\1\164\3\55\2\uffff\4\55\1\uffff\3\55\1\uffff\1\55\1\uffff\2\55\2\uffff\3\55\1\uffff\1\167\1\60\1\uffff\1\145\1\171\1\164\1\143\1\145\1\60\1\55\1\uffff\4\55\1\uffff\2\55\1\uffff\3\55\2\uffff\1\55\1\60\1\uffff\1\167\2\60\1\145\1\60\2\uffff\2\55\4\uffff\4\55\1\uffff\1\60\2\uffff\1\60\2\uffff\3\55\1\uffff\1\55\2\uffff\3\55\1\uffff\5\55\3\uffff";
    static final String DFA21_maxS =
        "\1\uffff\5\172\1\uffff\11\172\1\77\1\uffff\1\172\1\uffff\1\57\2\172\3\uffff\1\173\1\175\4\172\1\uffff\1\172\1\uffff\1\76\1\uffff\2\75\1\uffff\2\172\2\63\1\uffff\2\uffff\2\uffff\2\172\1\uffff\1\172\1\uffff\1\172\2\uffff\7\172\2\uffff\25\172\3\uffff\1\172\1\132\1\172\2\uffff\1\172\5\uffff\1\141\1\145\1\165\1\154\1\157\1\145\1\156\1\141\1\157\10\uffff\6\172\12\uffff\1\uffff\2\uffff\1\172\3\uffff\3\172\1\63\4\172\1\uffff\22\172\1\uffff\12\172\2\63\1\uffff\1\164\2\162\1\164\1\141\1\162\1\157\1\144\1\155\1\166\7\172\1\uffff\1\172\1\63\1\165\1\uffff\42\172\1\uffff\2\172\1\141\2\172\1\163\1\150\1\142\1\154\1\155\1\160\2\145\1\164\4\172\1\uffff\5\172\1\uffff\1\172\3\uffff\5\172\1\uffff\5\172\1\uffff\17\172\1\146\2\uffff\1\151\1\157\1\172\1\144\1\145\1\157\1\163\1\162\1\145\24\172\1\uffff\3\172\1\uffff\11\172\2\uffff\1\154\1\157\1\162\1\uffff\1\166\1\164\1\151\1\160\1\141\1\170\1\uffff\1\172\1\uffff\1\172\1\uffff\2\172\1\uffff\2\172\1\uffff\1\172\2\uffff\5\172\1\uffff\5\172\1\uffff\6\172\1\157\1\156\1\172\1\151\1\162\1\156\1\141\1\147\1\164\3\172\2\uffff\4\172\1\uffff\3\172\1\uffff\1\172\1\uffff\2\172\2\uffff\3\172\1\uffff\1\167\1\172\1\uffff\1\145\1\171\1\164\1\143\1\145\2\172\1\uffff\4\172\1\uffff\2\172\1\uffff\3\172\2\uffff\2\172\1\uffff\1\167\2\172\1\145\1\172\2\uffff\2\172\4\uffff\4\172\1\uffff\1\172\2\uffff\1\172\2\uffff\3\172\1\uffff\1\172\2\uffff\3\172\1\uffff\5\172\3\uffff";
    static final String DFA21_acceptS =
        "\6\uffff\1\6\12\uffff\1\32\1\uffff\1\35\3\uffff\1\53\1\55\1\56\6\uffff\1\101\1\uffff\1\105\1\uffff\1\107\7\uffff\1\140\2\uffff\1\144\1\145\2\uffff\1\133\1\uffff\1\131\1\uffff\1\132\1\137\7\uffff\1\33\1\6\25\uffff\1\121\1\31\1\32\3\uffff\1\34\1\136\1\uffff\1\35\1\142\1\143\1\36\1\37\11\uffff\1\127\1\53\1\55\1\56\1\103\1\60\1\104\1\61\6\uffff\1\101\1\102\1\105\1\106\1\126\1\122\1\107\1\125\1\123\1\124\1\uffff\1\130\1\134\1\uffff\1\140\1\141\1\144\10\uffff\1\71\22\uffff\1\62\14\uffff\1\135\21\uffff\1\77\46\uffff\1\57\22\uffff\1\100\5\uffff\1\3\1\uffff\1\27\1\4\1\5\5\uffff\1\63\5\uffff\1\20\20\uffff\1\41\1\42\35\uffff\1\11\3\uffff\1\75\11\uffff\1\30\1\110\3\uffff\1\45\6\uffff\1\70\1\uffff\1\73\1\uffff\1\116\2\uffff\1\1\2\uffff\1\13\1\uffff\1\111\1\7\5\uffff\1\64\5\uffff\1\22\22\uffff\1\113\1\2\4\uffff\1\10\3\uffff\1\12\1\uffff\1\76\2\uffff\1\66\1\67\3\uffff\1\65\2\uffff\1\43\7\uffff\1\120\4\uffff\1\112\2\uffff\1\23\3\uffff\1\74\1\114\2\uffff\1\44\5\uffff\1\54\1\72\2\uffff\1\26\1\24\1\21\1\115\4\uffff\1\40\1\uffff\1\47\1\50\1\uffff\1\52\1\117\3\uffff\1\16\1\uffff\1\46\1\51\3\uffff\1\25\5\uffff\1\14\1\15\1\17";
    static final String DFA21_specialS =
        "\1\5\46\uffff\1\3\5\uffff\1\2\1\4\127\uffff\1\0\104\uffff\1\1\u011c\uffff}>";
    static final String[] DFA21_transitionS = {
            "\11\60\2\57\2\60\1\57\22\60\1\57\1\46\1\55\1\41\3\60\1\56\1\30\1\31\1\6\1\21\1\27\1\44\1\23\1\24\12\54\1\40\1\60\1\45\1\20\1\43\1\60\1\26\4\51\1\22\25\51\1\47\2\53\1\25\1\52\1\53\1\14\1\2\1\12\1\16\1\5\1\17\1\35\1\50\1\37\2\50\1\4\1\15\1\1\1\7\1\10\1\50\1\34\1\13\1\3\1\36\1\11\4\50\1\32\1\42\1\33\uff82\60",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\24\64\1\61\5\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\16\64\1\71\13\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\72\11\64\1\74\2\64\1\73\10\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\75\21\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\76\11\64\1\77\2\64",
            "",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\64\1\102\15\64\1\104\5\64\1\103\4\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\106\20\64\1\105\10\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\107\15\64\1\110\13\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\112\2\64\1\111\13\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\17\64\1\113\12\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\114\4\64\1\115\7\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\120\3\64\1\121\3\64\1\117\5\64\1\116\5\64\1\122\5\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\123\25\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\124\7\64\1\126\5\64\1\125\13\64",
            "\1\127",
            "",
            "\1\133\1\uffff\4\132\6\137\7\uffff\32\134\4\136\1\137\1\136\32\137",
            "",
            "\1\141\4\uffff\1\142",
            "\1\63\1\uffff\4\63\15\uffff\32\70\4\uffff\1\70\1\uffff\32\70",
            "\1\147\1\156\1\155\1\145\1\153\1\156\1\152\3\156\1\150\2\156\1\154\7\156\1\146\1\151\3\156",
            "",
            "",
            "",
            "\1\162",
            "\1\164",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\166\31\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\167\25\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\170\3\64\1\171\10\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\14\64\1\173\1\172\14\64",
            "",
            "\72\63",
            "",
            "\1\u0080\1\177",
            "",
            "\1\u0083",
            "\1\u0085",
            "\56\u0087\1\u0086\1\u0087\4\u0086\uffcc\u0087",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\133\1\uffff\4\132\6\137\7\uffff\32\134\4\136\1\137\1\136\32\137",
            "\1\63\1\uffff\4\u0089",
            "\1\63\1\uffff\4\63",
            "",
            "\0\u008b",
            "\0\u008b",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\14\64\1\u008d\15\64",
            "\1\67\2\uffff\12\64\7\uffff\32\u0090\4\63\1\u008f\1\63\32\u008e",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "",
            "\12\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\16\64\1\u0091\13\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\14\64\1\u0093\12\64\1\u0092\2\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\24\64\1\u0094\5\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u0096\7\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\24\64\1\u0097\5\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\2\64\1\u0099\14\64\1\u009a\3\64\1\u0098\6\64",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\11\64\1\u009b\10\64\1\u009c\7\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u009d\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u009e\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\16\64\1\u009f\13\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\21\64\1\u00a0\10\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u00a1\16\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u00a2\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\14\64\1\u00a4\1\u00a3\14\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u00a5\31\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u00a6\31\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u00a7\14\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\3\64\1\u00a9\26\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u00aa\14\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\27\64\1\u00ab\2\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u00ac\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u00ad\16\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\5\64\1\u00ae\24\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u00af\16\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\21\64\1\u00b0\10\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u00b1\14\64",
            "",
            "",
            "",
            "\12\137\7\uffff\32\u00b2\4\u00b3\1\u00b2\1\u00b3\32\u00b2",
            "\32\u00b4",
            "\1\u00b5\1\uffff\12\137\7\uffff\32\134\4\136\1\137\1\136\32\137",
            "",
            "",
            "\12\137\7\uffff\32\137\4\uffff\1\137\1\uffff\32\137",
            "",
            "",
            "",
            "",
            "",
            "\1\u00b6",
            "\1\u00b7\3\uffff\1\u00b8",
            "\1\u00b9",
            "\1\u00ba",
            "\1\u00bb",
            "\1\u00bc",
            "\1\u00bd",
            "\1\u00be",
            "\1\u00bf",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u00c0\14\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\16\64\1\u00c1\13\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u00c2\1\64\1\u00c3\17\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u00c4\14\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\2\64\1\u00c6\14\64\1\u00c5\12\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\17\64\1\u00c8\12\64",
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
            "\101\u0087\33\u00cb\1\u00ca\1\u00c9\35\u00cb\uff85\u0087",
            "",
            "",
            "\12\70\7\uffff\32\u0090\4\uffff\1\u0090\1\uffff\32\u0090",
            "",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\64\1\u00cc\30\64",
            "\1\67\1\63\1\uffff\4\62\6\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\63\1\uffff\4\u00cd\6\66\7\uffff\32\70\4\uffff\1\66\1\uffff\32\66",
            "\1\63\1\uffff\4\u0089",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u00ce\16\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u00cf\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\17\64\1\u00d0\12\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u00d1\25\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u00d2\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\14\64\1\u00d3\15\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u00d4\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u00d5\16\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\16\64\1\u00d6\13\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u00d7\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u00d8\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\21\64\1\u00d9\10\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u00da\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\2\64\1\u00db\27\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u00dd\22\64\1\u00dc\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\24\64\1\u00de\5\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\3\64\1\u00df\26\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\2\64\1\u00e0\20\64\1\u00e1\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\17\64\1\u00e2\12\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u00e3\7\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u00e4\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\16\64\1\u00e5\13\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u00e6\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u00e7\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u00e8\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u00e9\31\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u00ea\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u00ec\7\64\1\u00eb\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u00ed\7\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u00ef\31\64",
            "\1\63\1\uffff\4\132\6\137\7\uffff\32\137\4\uffff\1\137\1\uffff\32\137",
            "\1\63\1\uffff\4\u00f0",
            "\1\133\1\uffff\4\63",
            "",
            "\1\u00f1",
            "\1\u00f3\5\uffff\1\u00f2",
            "\1\u00f4",
            "\1\u00f5",
            "\1\u00f6",
            "\1\u00f7",
            "\1\u00f8",
            "\1\u00f9",
            "\1\u00fa",
            "\1\u00fc\7\uffff\1\u00fb",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\6\64\1\u00fd\23\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\14\64\1\u00fe\15\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u00ff\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u0100\14\64",
            "\1\67\2\uffff\12\64\1\u0101\6\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\24\64\1\u0102\5\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u0103\16\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\16\64\1\u0104\13\64",
            "\1\63\1\uffff\4\63",
            "\1\63\1\uffff\4\63\50\uffff\2\u0087\4\uffff\1\u0087\3\uffff\1\u0087\7\uffff\1\u0087\3\uffff\1\u0087\1\uffff\2\u0087",
            "\56\u0087\1\u0086\1\u0087\4\u0086\uffcc\u0087",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u0105\25\64",
            "\12\66\7\uffff\32\u0090\4\63\1\u008f\1\63\32\u008f",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u0106\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\16\64\1\u0108\13\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u010c\14\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\24\64\1\u010d\5\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\21\64\1\u010e\10\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\2\64\1\u010f\27\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\21\64\1\u0110\10\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\16\64\1\u0112\13\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u0113\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u0114\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\14\64\1\u0115\15\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u0116\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u0118\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u0119\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\24\64\1\u011a\5\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u011b\7\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u011c\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u011d\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u011e\16\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\14\64\1\u011f\15\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\14\64\1\u0120\15\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\3\64\1\u0121\26\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u0122\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u0123\14\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\24\64\1\u0124\5\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u0125\25\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u0126\16\64",
            "\12\136\7\uffff\72\u00b3",
            "\1\u0127",
            "\12\156\45\uffff\1\156\1\uffff\32\156",
            "\12\156\45\uffff\1\156\1\uffff\32\156",
            "\1\u012a",
            "\1\u012b",
            "\1\u012c",
            "\1\u012d",
            "\1\u012e",
            "\1\u012f",
            "\1\u0130",
            "\1\u0131",
            "\1\u0132",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u0133\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u0134\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u0135\7\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\16\64\1\u0136\13\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u0137\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\24\64\1\u0138\5\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\21\64\1\u0139\10\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\21\64\1\u013a\10\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u013b\31\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\21\64\1\u013c\10\64",
            "",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u013d\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u013e\7\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u013f\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u0140\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\25\64\1\u0141\4\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u0142\14\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u0143\7\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u0144\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u0145\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u0146\7\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\17\64\1\u0148\12\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\27\64\1\u0149\2\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u014a\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u014c\31\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u014d\31\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u014e\7\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\24\64\1\u014f\5\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\24\64\1\u0150\5\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u0151\31\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\17\64\1\u0152\12\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u0153\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u0154\16\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\u0157",
            "",
            "",
            "\1\u0158",
            "\1\u0159",
            "\12\156\45\uffff\1\156\1\uffff\32\156",
            "\1\u015b",
            "\1\u015c",
            "\1\u015d",
            "\1\u015e",
            "\1\u015f",
            "\1\u0160",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u0162\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\26\64\1\u0164\3\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u0166\7\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u0167\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u0169\14\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u016a\31\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u016c\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u016f\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u0170\31\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u0171\7\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u0172\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u0173\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u0175\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u0176\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u0177\25\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u0178\16\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u0179\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\14\64\1\u017b\15\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\14\64\1\u017c\15\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u017d\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u017e\16\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u017f\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u0180\6\64",
            "",
            "",
            "\1\u0181",
            "\1\u0182",
            "\1\u0183",
            "",
            "\1\u0184",
            "\1\u0185",
            "\1\u0186",
            "\1\u0187",
            "\1\u0188",
            "\1\u0189",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\21\64\1\u018a\10\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u018b\14\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u018c\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u018f\16\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\25\64\1\u0190\4\64",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\21\64\1\u0191\10\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u0192\16\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\16\64\1\u0194\13\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u0195\25\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u0196\7\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\24\64\1\u0198\5\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u019a\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u019b\21\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u019e\31\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u019f\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u01a0\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\u01a2",
            "\1\u01a3",
            "\12\156\45\uffff\1\156\1\uffff\32\156",
            "\1\u01a5",
            "\1\u01a6",
            "\1\u01a7",
            "\1\u01a8",
            "\1\u01a9",
            "\1\u01aa",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\30\64\1\u01ab\1\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\25\64\1\u01ad\4\64",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u01ae\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u01af\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u01b0\7\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u01b2\14\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\21\64\1\u01b3\10\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\1\u01b5\31\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\27\64\1\u01b6\2\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\16\64\1\u01b7\13\64",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\16\64\1\u01ba\13\64",
            "",
            "\1\u01bb",
            "\12\156\45\uffff\1\156\1\uffff\32\156",
            "",
            "\1\u01bd",
            "\1\u01be",
            "\1\u01bf",
            "\1\u01c0",
            "\1\u01c1",
            "\12\156\45\uffff\1\156\1\uffff\32\156",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u01c4\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\27\64\1\u01c5\2\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\13\64\1\u01ca\16\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u01cb\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u01cc\14\64",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u01cd\14\64",
            "\12\156\45\uffff\1\156\1\uffff\32\156",
            "",
            "\1\u01cf",
            "\12\156\45\uffff\1\156\1\uffff\32\156",
            "\12\156\45\uffff\1\156\1\uffff\32\156",
            "\1\u01d2",
            "\12\156\45\uffff\1\156\1\uffff\32\156",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u01d5\6\64",
            "",
            "",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\10\64\1\u01d6\21\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u01d7\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\22\64\1\u01d9\7\64",
            "",
            "\12\156\45\uffff\1\156\1\uffff\32\156",
            "",
            "",
            "\12\156\45\uffff\1\156\1\uffff\32\156",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u01dc\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\31\64\1\u01dd",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u01de\14\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\15\64\1\u01e0\14\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\4\64\1\u01e1\25\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u01e2\6\64",
            "",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\23\64\1\u01e3\6\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\21\64\1\u01e4\10\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "\1\67\2\uffff\12\64\7\uffff\32\70\4\uffff\1\66\1\uffff\32\64",
            "",
            "",
            ""
    };

    static final short[] DFA21_eot = DFA.unpackEncodedString(DFA21_eotS);
    static final short[] DFA21_eof = DFA.unpackEncodedString(DFA21_eofS);
    static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars(DFA21_minS);
    static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars(DFA21_maxS);
    static final short[] DFA21_accept = DFA.unpackEncodedString(DFA21_acceptS);
    static final short[] DFA21_special = DFA.unpackEncodedString(DFA21_specialS);
    static final short[][] DFA21_transition;

    static {
        int numStates = DFA21_transitionS.length;
        DFA21_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
        }
    }

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = DFA21_eot;
            this.eof = DFA21_eof;
            this.min = DFA21_min;
            this.max = DFA21_max;
            this.accept = DFA21_accept;
            this.special = DFA21_special;
            this.transition = DFA21_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | RULE_ANNOTATION_ID | RULE_EXPR | RULE_LOWERCASE_ID | RULE_LOWERCASE_DASHID | RULE_SHAPE | RULE_UPPERCASE_ID | RULE_UPPERCASE_PATH | RULE_CAMELCASE_ID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA21_134 = input.LA(1);

                        s = -1;
                        if ( (LA21_134==']') ) {s = 201;}

                        else if ( (LA21_134=='\\') ) {s = 202;}

                        else if ( ((LA21_134>='A' && LA21_134<='[')||(LA21_134>='^' && LA21_134<='z')) ) {s = 203;}

                        else if ( ((LA21_134>='\u0000' && LA21_134<='@')||(LA21_134>='{' && LA21_134<='\uFFFF')) ) {s = 135;}

                        else s = 51;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA21_203 = input.LA(1);

                        s = -1;
                        if ( (LA21_203=='.'||(LA21_203>='0' && LA21_203<='3')) ) {s = 134;}

                        else if ( ((LA21_203>='\u0000' && LA21_203<='-')||LA21_203=='/'||(LA21_203>='4' && LA21_203<='\uFFFF')) ) {s = 135;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA21_45 = input.LA(1);

                        s = -1;
                        if ( ((LA21_45>='\u0000' && LA21_45<='\uFFFF')) ) {s = 139;}

                        else s = 48;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA21_39 = input.LA(1);

                        s = -1;
                        if ( (LA21_39=='.'||(LA21_39>='0' && LA21_39<='3')) ) {s = 134;}

                        else if ( ((LA21_39>='\u0000' && LA21_39<='-')||LA21_39=='/'||(LA21_39>='4' && LA21_39<='\uFFFF')) ) {s = 135;}

                        else s = 48;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA21_46 = input.LA(1);

                        s = -1;
                        if ( ((LA21_46>='\u0000' && LA21_46<='\uFFFF')) ) {s = 139;}

                        else s = 48;

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA21_0 = input.LA(1);

                        s = -1;
                        if ( (LA21_0=='n') ) {s = 1;}

                        else if ( (LA21_0=='b') ) {s = 2;}

                        else if ( (LA21_0=='t') ) {s = 3;}

                        else if ( (LA21_0=='l') ) {s = 4;}

                        else if ( (LA21_0=='e') ) {s = 5;}

                        else if ( (LA21_0=='*') ) {s = 6;}

                        else if ( (LA21_0=='o') ) {s = 7;}

                        else if ( (LA21_0=='p') ) {s = 8;}

                        else if ( (LA21_0=='v') ) {s = 9;}

                        else if ( (LA21_0=='c') ) {s = 10;}

                        else if ( (LA21_0=='s') ) {s = 11;}

                        else if ( (LA21_0=='a') ) {s = 12;}

                        else if ( (LA21_0=='m') ) {s = 13;}

                        else if ( (LA21_0=='d') ) {s = 14;}

                        else if ( (LA21_0=='f') ) {s = 15;}

                        else if ( (LA21_0=='=') ) {s = 16;}

                        else if ( (LA21_0=='+') ) {s = 17;}

                        else if ( (LA21_0=='E') ) {s = 18;}

                        else if ( (LA21_0=='.') ) {s = 19;}

                        else if ( (LA21_0=='/') ) {s = 20;}

                        else if ( (LA21_0=='^') ) {s = 21;}

                        else if ( (LA21_0=='@') ) {s = 22;}

                        else if ( (LA21_0==',') ) {s = 23;}

                        else if ( (LA21_0=='(') ) {s = 24;}

                        else if ( (LA21_0==')') ) {s = 25;}

                        else if ( (LA21_0=='{') ) {s = 26;}

                        else if ( (LA21_0=='}') ) {s = 27;}

                        else if ( (LA21_0=='r') ) {s = 28;}

                        else if ( (LA21_0=='g') ) {s = 29;}

                        else if ( (LA21_0=='u') ) {s = 30;}

                        else if ( (LA21_0=='i') ) {s = 31;}

                        else if ( (LA21_0==':') ) {s = 32;}

                        else if ( (LA21_0=='#') ) {s = 33;}

                        else if ( (LA21_0=='|') ) {s = 34;}

                        else if ( (LA21_0=='>') ) {s = 35;}

                        else if ( (LA21_0=='-') ) {s = 36;}

                        else if ( (LA21_0=='<') ) {s = 37;}

                        else if ( (LA21_0=='!') ) {s = 38;}

                        else if ( (LA21_0=='[') ) {s = 39;}

                        else if ( (LA21_0=='h'||(LA21_0>='j' && LA21_0<='k')||LA21_0=='q'||(LA21_0>='w' && LA21_0<='z')) ) {s = 40;}

                        else if ( ((LA21_0>='A' && LA21_0<='D')||(LA21_0>='F' && LA21_0<='Z')) ) {s = 41;}

                        else if ( (LA21_0=='_') ) {s = 42;}

                        else if ( ((LA21_0>='\\' && LA21_0<=']')||LA21_0=='`') ) {s = 43;}

                        else if ( ((LA21_0>='0' && LA21_0<='9')) ) {s = 44;}

                        else if ( (LA21_0=='\"') ) {s = 45;}

                        else if ( (LA21_0=='\'') ) {s = 46;}

                        else if ( ((LA21_0>='\t' && LA21_0<='\n')||LA21_0=='\r'||LA21_0==' ') ) {s = 47;}

                        else if ( ((LA21_0>='\u0000' && LA21_0<='\b')||(LA21_0>='\u000B' && LA21_0<='\f')||(LA21_0>='\u000E' && LA21_0<='\u001F')||(LA21_0>='$' && LA21_0<='&')||LA21_0==';'||LA21_0=='?'||(LA21_0>='~' && LA21_0<='\uFFFF')) ) {s = 48;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 21, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}
// Generated from D:\ShanSong\android\android-ftdi-usb2serial-driver-package\trunk\FootprintParser\grammar\FootprintParser.g4 by ANTLR 4.0

package cn.songshan99.FootprintParser;

import cn.songshan99.FootprintParser.ICFootprint;
import cn.songshan99.FootprintParser.ICFootprint.*;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FootprintParserLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__3=1, T__2=2, T__1=3, T__0=4, LINE_COMMENT=5, WS=6, HEX=7, INTEGER=8, 
		FLOATING=9, STRING=10, STRESC=11, T_PIN=12, T_PAD=13, T_ELEMENTLINE=14, 
		T_ELEMENTARC=15, T_ELEMENT=16, T_MARK=17, T_ATTRIBUTE=18, T_NM=19, T_UM=20, 
		T_MM=21, T_M=22, T_KM=23, T_UMIL=24, T_CMIL=25, T_MIL=26, T_IN=27;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"']'", "')'", "'['", "'('", "LINE_COMMENT", "WS", "HEX", "INTEGER", "FLOATING", 
		"STRING", "STRESC", "'Pin'", "'Pad'", "'ElementLine'", "'ElementArc'", 
		"'Element'", "'Mark'", "'Attribute'", "'nm'", "'um'", "'mm'", "'m'", "'km'", 
		"'umil'", "'cmil'", "'mil'", "'in'"
	};
	public static final String[] ruleNames = {
		"T__3", "T__2", "T__1", "T__0", "LINE_COMMENT", "WS", "HEX", "INTEGER", 
		"FLOATING", "STRING", "STRESC", "T_PIN", "T_PAD", "T_ELEMENTLINE", "T_ELEMENTARC", 
		"T_ELEMENT", "T_MARK", "T_ATTRIBUTE", "T_NM", "T_UM", "T_MM", "T_M", "T_KM", 
		"T_UMIL", "T_CMIL", "T_MIL", "T_IN"
	};


	public FootprintParserLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FootprintParser.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 4: LINE_COMMENT_action((RuleContext)_localctx, actionIndex); break;

		case 5: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1: _channel = HIDDEN;  break;
		}
	}
	private void LINE_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: _channel = HIDDEN;  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\35\u00d6\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b"+
		"\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27"+
		"\t\27\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\3\3"+
		"\3\3\4\3\4\3\5\3\5\3\6\3\6\7\6D\n\6\f\6\16\6G\13\6\3\6\5\6J\n\6\3\6\3"+
		"\6\5\6N\n\6\3\6\3\6\3\7\6\7S\n\7\r\7\16\7T\3\7\3\7\3\b\3\b\3\b\6\b\\\n"+
		"\b\r\b\16\b]\3\t\5\ta\n\t\3\t\3\t\7\te\n\t\f\t\16\th\13\t\3\t\5\tk\n\t"+
		"\3\n\3\n\3\n\7\np\n\n\f\n\16\ns\13\n\3\13\3\13\3\13\7\13x\n\13\f\13\16"+
		"\13{\13\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3"+
		"\27\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3"+
		"\33\3\33\3\33\3\33\3\34\3\34\3\34\2\35\3\3\1\5\4\1\7\5\1\t\6\1\13\7\2"+
		"\r\b\3\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37"+
		"\21\1!\22\1#\23\1%\24\1\'\25\1)\26\1+\27\1-\30\1/\31\1\61\32\1\63\33\1"+
		"\65\34\1\67\35\1\3\2\b\4\f\f\17\17\5\13\f\17\17\"\"\5\62;CHch\4--//\4"+
		"$$^^\n$$))^^ddhhppttvv\u00e0\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3"+
		"\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2"+
		"\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37"+
		"\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3"+
		"\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2"+
		"\67\3\2\2\2\39\3\2\2\2\5;\3\2\2\2\7=\3\2\2\2\t?\3\2\2\2\13A\3\2\2\2\r"+
		"R\3\2\2\2\17X\3\2\2\2\21j\3\2\2\2\23l\3\2\2\2\25t\3\2\2\2\27~\3\2\2\2"+
		"\31\u0081\3\2\2\2\33\u0085\3\2\2\2\35\u0089\3\2\2\2\37\u0095\3\2\2\2!"+
		"\u00a0\3\2\2\2#\u00a8\3\2\2\2%\u00ad\3\2\2\2\'\u00b7\3\2\2\2)\u00ba\3"+
		"\2\2\2+\u00bd\3\2\2\2-\u00c0\3\2\2\2/\u00c2\3\2\2\2\61\u00c5\3\2\2\2\63"+
		"\u00ca\3\2\2\2\65\u00cf\3\2\2\2\67\u00d3\3\2\2\29:\7_\2\2:\4\3\2\2\2;"+
		"<\7+\2\2<\6\3\2\2\2=>\7]\2\2>\b\3\2\2\2?@\7*\2\2@\n\3\2\2\2AE\7%\2\2B"+
		"D\n\2\2\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2FM\3\2\2\2GE\3\2\2\2"+
		"HJ\7\17\2\2IH\3\2\2\2IJ\3\2\2\2JK\3\2\2\2KN\7\f\2\2LN\7\1\2\2MI\3\2\2"+
		"\2ML\3\2\2\2NO\3\2\2\2OP\b\6\2\2P\f\3\2\2\2QS\t\3\2\2RQ\3\2\2\2ST\3\2"+
		"\2\2TR\3\2\2\2TU\3\2\2\2UV\3\2\2\2VW\b\7\3\2W\16\3\2\2\2XY\7\62\2\2Y["+
		"\7z\2\2Z\\\t\4\2\2[Z\3\2\2\2\\]\3\2\2\2][\3\2\2\2]^\3\2\2\2^\20\3\2\2"+
		"\2_a\t\5\2\2`_\3\2\2\2`a\3\2\2\2ab\3\2\2\2bf\4\63;\2ce\4\62;\2dc\3\2\2"+
		"\2eh\3\2\2\2fd\3\2\2\2fg\3\2\2\2gk\3\2\2\2hf\3\2\2\2ik\7\62\2\2j`\3\2"+
		"\2\2ji\3\2\2\2k\22\3\2\2\2lm\5\21\t\2mq\7\60\2\2np\4\62;\2on\3\2\2\2p"+
		"s\3\2\2\2qo\3\2\2\2qr\3\2\2\2r\24\3\2\2\2sq\3\2\2\2ty\7$\2\2ux\5\27\f"+
		"\2vx\n\6\2\2wu\3\2\2\2wv\3\2\2\2x{\3\2\2\2yw\3\2\2\2yz\3\2\2\2z|\3\2\2"+
		"\2{y\3\2\2\2|}\7$\2\2}\26\3\2\2\2~\177\7^\2\2\177\u0080\t\7\2\2\u0080"+
		"\30\3\2\2\2\u0081\u0082\7R\2\2\u0082\u0083\7k\2\2\u0083\u0084\7p\2\2\u0084"+
		"\32\3\2\2\2\u0085\u0086\7R\2\2\u0086\u0087\7c\2\2\u0087\u0088\7f\2\2\u0088"+
		"\34\3\2\2\2\u0089\u008a\7G\2\2\u008a\u008b\7n\2\2\u008b\u008c\7g\2\2\u008c"+
		"\u008d\7o\2\2\u008d\u008e\7g\2\2\u008e\u008f\7p\2\2\u008f\u0090\7v\2\2"+
		"\u0090\u0091\7N\2\2\u0091\u0092\7k\2\2\u0092\u0093\7p\2\2\u0093\u0094"+
		"\7g\2\2\u0094\36\3\2\2\2\u0095\u0096\7G\2\2\u0096\u0097\7n\2\2\u0097\u0098"+
		"\7g\2\2\u0098\u0099\7o\2\2\u0099\u009a\7g\2\2\u009a\u009b\7p\2\2\u009b"+
		"\u009c\7v\2\2\u009c\u009d\7C\2\2\u009d\u009e\7t\2\2\u009e\u009f\7e\2\2"+
		"\u009f \3\2\2\2\u00a0\u00a1\7G\2\2\u00a1\u00a2\7n\2\2\u00a2\u00a3\7g\2"+
		"\2\u00a3\u00a4\7o\2\2\u00a4\u00a5\7g\2\2\u00a5\u00a6\7p\2\2\u00a6\u00a7"+
		"\7v\2\2\u00a7\"\3\2\2\2\u00a8\u00a9\7O\2\2\u00a9\u00aa\7c\2\2\u00aa\u00ab"+
		"\7t\2\2\u00ab\u00ac\7m\2\2\u00ac$\3\2\2\2\u00ad\u00ae\7C\2\2\u00ae\u00af"+
		"\7v\2\2\u00af\u00b0\7v\2\2\u00b0\u00b1\7t\2\2\u00b1\u00b2\7k\2\2\u00b2"+
		"\u00b3\7d\2\2\u00b3\u00b4\7w\2\2\u00b4\u00b5\7v\2\2\u00b5\u00b6\7g\2\2"+
		"\u00b6&\3\2\2\2\u00b7\u00b8\7p\2\2\u00b8\u00b9\7o\2\2\u00b9(\3\2\2\2\u00ba"+
		"\u00bb\7w\2\2\u00bb\u00bc\7o\2\2\u00bc*\3\2\2\2\u00bd\u00be\7o\2\2\u00be"+
		"\u00bf\7o\2\2\u00bf,\3\2\2\2\u00c0\u00c1\7o\2\2\u00c1.\3\2\2\2\u00c2\u00c3"+
		"\7m\2\2\u00c3\u00c4\7o\2\2\u00c4\60\3\2\2\2\u00c5\u00c6\7w\2\2\u00c6\u00c7"+
		"\7o\2\2\u00c7\u00c8\7k\2\2\u00c8\u00c9\7n\2\2\u00c9\62\3\2\2\2\u00ca\u00cb"+
		"\7e\2\2\u00cb\u00cc\7o\2\2\u00cc\u00cd\7k\2\2\u00cd\u00ce\7n\2\2\u00ce"+
		"\64\3\2\2\2\u00cf\u00d0\7o\2\2\u00d0\u00d1\7k\2\2\u00d1\u00d2\7n\2\2\u00d2"+
		"\66\3\2\2\2\u00d3\u00d4\7k\2\2\u00d4\u00d5\7p\2\2\u00d58\3\2\2\2\16\2"+
		"EIMT]`fjqwy";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}
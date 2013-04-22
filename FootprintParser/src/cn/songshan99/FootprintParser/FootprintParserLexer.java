// Generated from D:\ShanSong\android\android-ftdi-usb2serial-driver-package\trunk\FootprintParser\grammar\FootprintParser.g4 by ANTLR 4.0

package cn.songshan99.FootprintParser;

import cn.songshan99.realicfootprint.ICFootprint;
import cn.songshan99.realicfootprint.ICFootprint.*;

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
		T__3=1, T__2=2, T__1=3, T__0=4, HEX=5, INTEGER=6, FLOATING=7, STRINGCHAR=8, 
		STRING=9, T_PIN=10, T_PAD=11, T_ELEMENTLINE=12, T_ELEMENTARC=13, T_ELEMENT=14, 
		T_MARK=15, T_ATTRIBUTE=16, T_NM=17, T_UM=18, T_MM=19, T_M=20, T_KM=21, 
		T_UMIL=22, T_CMIL=23, T_MIL=24, T_IN=25;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"']'", "')'", "'['", "'('", "HEX", "INTEGER", "FLOATING", "STRINGCHAR", 
		"STRING", "'Pin'", "'Pad'", "'ElementLine'", "'ElementArc'", "'Element'", 
		"'Mark'", "'Attribute'", "'nm'", "'um'", "'mm'", "'m'", "'km'", "'umil'", 
		"'cmil'", "'mil'", "'in'"
	};
	public static final String[] ruleNames = {
		"T__3", "T__2", "T__1", "T__0", "HEX", "INTEGER", "FLOATING", "STRINGCHAR", 
		"STRING", "T_PIN", "T_PAD", "T_ELEMENTLINE", "T_ELEMENTARC", "T_ELEMENT", 
		"T_MARK", "T_ATTRIBUTE", "T_NM", "T_UM", "T_MM", "T_M", "T_KM", "T_UMIL", 
		"T_CMIL", "T_MIL", "T_IN"
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

	public static final String _serializedATN =
		"\2\4\33\u00bc\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b"+
		"\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27"+
		"\t\27\4\30\t\30\4\31\t\31\4\32\t\32\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\6\6\6A\n\6\r\6\16\6B\3\7\5\7F\n\7\3\7\3\7\7\7J\n\7\f\7\16\7M"+
		"\13\7\3\7\5\7P\n\7\3\b\3\b\3\b\7\bU\n\b\f\b\16\bX\13\b\3\t\3\t\3\t\5\t"+
		"]\n\t\3\n\3\n\7\na\n\n\f\n\16\nd\13\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f"+
		"\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25"+
		"\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\3\31\3\32\3\32\3\32\2\33\3\3\1\5\4\1\7\5\1\t\6\1\13\7"+
		"\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37"+
		"\21\1!\22\1#\23\1%\24\1\'\25\1)\26\1+\27\1-\30\1/\31\1\61\32\1\63\33\1"+
		"\3\2\5\5\62;CHch\4--//\6\f\f\17\17$$^^\u00c2\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2"+
		"\2\3\65\3\2\2\2\5\67\3\2\2\2\79\3\2\2\2\t;\3\2\2\2\13=\3\2\2\2\rO\3\2"+
		"\2\2\17Q\3\2\2\2\21\\\3\2\2\2\23^\3\2\2\2\25g\3\2\2\2\27k\3\2\2\2\31o"+
		"\3\2\2\2\33{\3\2\2\2\35\u0086\3\2\2\2\37\u008e\3\2\2\2!\u0093\3\2\2\2"+
		"#\u009d\3\2\2\2%\u00a0\3\2\2\2\'\u00a3\3\2\2\2)\u00a6\3\2\2\2+\u00a8\3"+
		"\2\2\2-\u00ab\3\2\2\2/\u00b0\3\2\2\2\61\u00b5\3\2\2\2\63\u00b9\3\2\2\2"+
		"\65\66\7_\2\2\66\4\3\2\2\2\678\7+\2\28\6\3\2\2\29:\7]\2\2:\b\3\2\2\2;"+
		"<\7*\2\2<\n\3\2\2\2=>\7\62\2\2>@\7z\2\2?A\t\2\2\2@?\3\2\2\2AB\3\2\2\2"+
		"B@\3\2\2\2BC\3\2\2\2C\f\3\2\2\2DF\t\3\2\2ED\3\2\2\2EF\3\2\2\2FG\3\2\2"+
		"\2GK\4\63;\2HJ\4\62;\2IH\3\2\2\2JM\3\2\2\2KI\3\2\2\2KL\3\2\2\2LP\3\2\2"+
		"\2MK\3\2\2\2NP\7\62\2\2OE\3\2\2\2ON\3\2\2\2P\16\3\2\2\2QR\5\r\7\2RV\7"+
		"\60\2\2SU\4\62;\2TS\3\2\2\2UX\3\2\2\2VT\3\2\2\2VW\3\2\2\2W\20\3\2\2\2"+
		"XV\3\2\2\2Y]\n\4\2\2Z[\7^\2\2[]\13\2\2\2\\Y\3\2\2\2\\Z\3\2\2\2]\22\3\2"+
		"\2\2^b\7$\2\2_a\5\21\t\2`_\3\2\2\2ad\3\2\2\2b`\3\2\2\2bc\3\2\2\2ce\3\2"+
		"\2\2db\3\2\2\2ef\7$\2\2f\24\3\2\2\2gh\7R\2\2hi\7k\2\2ij\7p\2\2j\26\3\2"+
		"\2\2kl\7R\2\2lm\7c\2\2mn\7f\2\2n\30\3\2\2\2op\7G\2\2pq\7n\2\2qr\7g\2\2"+
		"rs\7o\2\2st\7g\2\2tu\7p\2\2uv\7v\2\2vw\7N\2\2wx\7k\2\2xy\7p\2\2yz\7g\2"+
		"\2z\32\3\2\2\2{|\7G\2\2|}\7n\2\2}~\7g\2\2~\177\7o\2\2\177\u0080\7g\2\2"+
		"\u0080\u0081\7p\2\2\u0081\u0082\7v\2\2\u0082\u0083\7C\2\2\u0083\u0084"+
		"\7t\2\2\u0084\u0085\7e\2\2\u0085\34\3\2\2\2\u0086\u0087\7G\2\2\u0087\u0088"+
		"\7n\2\2\u0088\u0089\7g\2\2\u0089\u008a\7o\2\2\u008a\u008b\7g\2\2\u008b"+
		"\u008c\7p\2\2\u008c\u008d\7v\2\2\u008d\36\3\2\2\2\u008e\u008f\7O\2\2\u008f"+
		"\u0090\7c\2\2\u0090\u0091\7t\2\2\u0091\u0092\7m\2\2\u0092 \3\2\2\2\u0093"+
		"\u0094\7C\2\2\u0094\u0095\7v\2\2\u0095\u0096\7v\2\2\u0096\u0097\7t\2\2"+
		"\u0097\u0098\7k\2\2\u0098\u0099\7d\2\2\u0099\u009a\7w\2\2\u009a\u009b"+
		"\7v\2\2\u009b\u009c\7g\2\2\u009c\"\3\2\2\2\u009d\u009e\7p\2\2\u009e\u009f"+
		"\7o\2\2\u009f$\3\2\2\2\u00a0\u00a1\7w\2\2\u00a1\u00a2\7o\2\2\u00a2&\3"+
		"\2\2\2\u00a3\u00a4\7o\2\2\u00a4\u00a5\7o\2\2\u00a5(\3\2\2\2\u00a6\u00a7"+
		"\7o\2\2\u00a7*\3\2\2\2\u00a8\u00a9\7m\2\2\u00a9\u00aa\7o\2\2\u00aa,\3"+
		"\2\2\2\u00ab\u00ac\7w\2\2\u00ac\u00ad\7o\2\2\u00ad\u00ae\7k\2\2\u00ae"+
		"\u00af\7n\2\2\u00af.\3\2\2\2\u00b0\u00b1\7e\2\2\u00b1\u00b2\7o\2\2\u00b2"+
		"\u00b3\7k\2\2\u00b3\u00b4\7n\2\2\u00b4\60\3\2\2\2\u00b5\u00b6\7o\2\2\u00b6"+
		"\u00b7\7k\2\2\u00b7\u00b8\7n\2\2\u00b8\62\3\2\2\2\u00b9\u00ba\7k\2\2\u00ba"+
		"\u00bb\7p\2\2\u00bb\64\3\2\2\2\n\2BEKOV\\b";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}
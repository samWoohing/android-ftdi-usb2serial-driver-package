// Generated from D:\ShanSong\android\android-ftdi-usb2serial-driver-package\trunk\FootprintParser\grammar\FootprintParser.g4 by ANTLR 4.0

package cn.songshan99.FootprintParser;

import cn.songshan99.realicfootprint.ICFootprint;
import cn.songshan99.realicfootprint.ICFootprint.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FootprintParserParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__3=1, T__2=2, T__1=3, T__0=4, LINE_COMMENT=5, WS=6, HEX=7, INTEGER=8, 
		FLOATING=9, STRING=10, STRESC=11, T_PIN=12, T_PAD=13, T_ELEMENTLINE=14, 
		T_ELEMENTARC=15, T_ELEMENT=16, T_MARK=17, T_ATTRIBUTE=18, T_NM=19, T_UM=20, 
		T_MM=21, T_M=22, T_KM=23, T_UMIL=24, T_CMIL=25, T_MIL=26, T_IN=27;
	public static final String[] tokenNames = {
		"<INVALID>", "']'", "')'", "'['", "'('", "LINE_COMMENT", "WS", "HEX", 
		"INTEGER", "FLOATING", "STRING", "STRESC", "'Pin'", "'Pad'", "'ElementLine'", 
		"'ElementArc'", "'Element'", "'Mark'", "'Attribute'", "'nm'", "'um'", 
		"'mm'", "'m'", "'km'", "'umil'", "'cmil'", "'mil'", "'in'"
	};
	public static final int
		RULE_element = 0, RULE_element_oldformat = 1, RULE_element_1_3_4_format = 2, 
		RULE_element_newformat = 3, RULE_element_1_7_format = 4, RULE_element_hi_format = 5, 
		RULE_elementdefinitions = 6, RULE_elementdefinition = 7, RULE_attribute = 8, 
		RULE_relementdefs = 9, RULE_relementdef = 10, RULE_pin_hi_format = 11, 
		RULE_pin_1_7_format = 12, RULE_pin_1_6_3_format = 13, RULE_pin_newformat = 14, 
		RULE_pin_oldformat = 15, RULE_pad_hi_format = 16, RULE_pad_1_7_format = 17, 
		RULE_pad_newformat = 18, RULE_pad = 19, RULE_flags = 20, RULE_number = 21, 
		RULE_measure = 22;
	public static final String[] ruleNames = {
		"element", "element_oldformat", "element_1_3_4_format", "element_newformat", 
		"element_1_7_format", "element_hi_format", "elementdefinitions", "elementdefinition", 
		"attribute", "relementdefs", "relementdef", "pin_hi_format", "pin_1_7_format", 
		"pin_1_6_3_format", "pin_newformat", "pin_oldformat", "pad_hi_format", 
		"pad_1_7_format", "pad_newformat", "pad", "flags", "number", "measure"
	};

	@Override
	public String getGrammarFileName() { return "FootprintParser.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public FootprintParserParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ElementContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Element_hi_formatContext element_hi_format() {
			return getRuleContext(Element_hi_formatContext.class,0);
		}
		public Element_1_3_4_formatContext element_1_3_4_format() {
			return getRuleContext(Element_1_3_4_formatContext.class,0);
		}
		public Element_oldformatContext element_oldformat() {
			return getRuleContext(Element_oldformatContext.class,0);
		}
		public Element_newformatContext element_newformat() {
			return getRuleContext(Element_newformatContext.class,0);
		}
		public Element_1_7_formatContext element_1_7_format() {
			return getRuleContext(Element_1_7_formatContext.class,0);
		}
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitElement(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_element);

			
			((ElementContext)_localctx).footprint =  new ICFootprint();

		try {
			setState(51);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(46); element_oldformat(_localctx.footprint);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(47); element_1_3_4_format(_localctx.footprint);
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(48); element_newformat(_localctx.footprint);
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(49); element_1_7_format(_localctx.footprint);
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(50); element_hi_format(_localctx.footprint);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Element_oldformatContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Token tx;
		public Token ty;
		public MeasureContext tdir;
		public MeasureContext tscale;
		public Token tflag;
		public TerminalNode INTEGER() { return getToken(FootprintParserParser.INTEGER, 0); }
		public ElementdefinitionsContext elementdefinitions() {
			return getRuleContext(ElementdefinitionsContext.class,0);
		}
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public TerminalNode STRING(int i) {
			return getToken(FootprintParserParser.STRING, i);
		}
		public TerminalNode T_ELEMENT() { return getToken(FootprintParserParser.T_ELEMENT, 0); }
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public List<TerminalNode> STRING() { return getTokens(FootprintParserParser.STRING); }
		public Element_oldformatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Element_oldformatContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_element_oldformat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterElement_oldformat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitElement_oldformat(this);
		}
	}

	public final Element_oldformatContext element_oldformat(ICFootprint footprint) throws RecognitionException {
		Element_oldformatContext _localctx = new Element_oldformatContext(_ctx, getState(), footprint);
		enterRule(_localctx, 2, RULE_element_oldformat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53); match(T_ELEMENT);
			setState(54); match(4);
			setState(55); ((Element_oldformatContext)_localctx).tx = match(STRING);
			setState(56); ((Element_oldformatContext)_localctx).ty = match(STRING);
			setState(57); ((Element_oldformatContext)_localctx).tdir = measure();
			setState(58); ((Element_oldformatContext)_localctx).tscale = measure();
			setState(59); ((Element_oldformatContext)_localctx).tflag = match(INTEGER);
			setState(60); match(2);
			setState(61); match(4);

			        
			        ICText ictext = new ICText();
				ictext.aX=Float.valueOf((((Element_oldformatContext)_localctx).tx!=null?((Element_oldformatContext)_localctx).tx.getText():null))*100;//TODO: should fetch the substring?
				ictext.aY=Float.valueOf((((Element_oldformatContext)_localctx).ty!=null?((Element_oldformatContext)_localctx).ty.getText():null))*100;
				ictext.dir = ((Element_oldformatContext)_localctx).tdir.value;
				ictext.scale = ((Element_oldformatContext)_localctx).tscale.value;
				ictext.flags = Integer.valueOf((((Element_oldformatContext)_localctx).tflag!=null?((Element_oldformatContext)_localctx).tflag.getText():null));
				_localctx.footprint.setmICText(ictext);
			    
			        Mark mark = new Mark(0, 0);
			    
			        _localctx.footprint.setmICText(ictext);
			        _localctx.footprint.setmMark(mark);
			        _localctx.footprint.setFlags(0);
			        _localctx.footprint.setmDesc("");
			        _localctx.footprint.setmName("");
			        _localctx.footprint.setmValue("");
			      
			setState(63); elementdefinitions(_localctx.footprint);
			setState(64); match(2);

			        _localctx.footprint.centerTheFootprint();
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Element_1_3_4_formatContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Token flag;
		public Token desc;
		public Token name;
		public MeasureContext tx;
		public MeasureContext ty;
		public MeasureContext tdir;
		public MeasureContext tscale;
		public Token tflag;
		public List<TerminalNode> INTEGER() { return getTokens(FootprintParserParser.INTEGER); }
		public ElementdefinitionsContext elementdefinitions() {
			return getRuleContext(ElementdefinitionsContext.class,0);
		}
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public TerminalNode STRING(int i) {
			return getToken(FootprintParserParser.STRING, i);
		}
		public TerminalNode T_ELEMENT() { return getToken(FootprintParserParser.T_ELEMENT, 0); }
		public TerminalNode INTEGER(int i) {
			return getToken(FootprintParserParser.INTEGER, i);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public List<TerminalNode> STRING() { return getTokens(FootprintParserParser.STRING); }
		public Element_1_3_4_formatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Element_1_3_4_formatContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_element_1_3_4_format; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterElement_1_3_4_format(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitElement_1_3_4_format(this);
		}
	}

	public final Element_1_3_4_formatContext element_1_3_4_format(ICFootprint footprint) throws RecognitionException {
		Element_1_3_4_formatContext _localctx = new Element_1_3_4_formatContext(_ctx, getState(), footprint);
		enterRule(_localctx, 4, RULE_element_1_3_4_format);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67); match(T_ELEMENT);
			setState(68); match(4);
			setState(69); ((Element_1_3_4_formatContext)_localctx).flag = match(INTEGER);
			setState(70); ((Element_1_3_4_formatContext)_localctx).desc = match(STRING);
			setState(71); ((Element_1_3_4_formatContext)_localctx).name = match(STRING);
			setState(72); ((Element_1_3_4_formatContext)_localctx).tx = measure();
			setState(73); ((Element_1_3_4_formatContext)_localctx).ty = measure();
			setState(74); ((Element_1_3_4_formatContext)_localctx).tdir = measure();
			setState(75); ((Element_1_3_4_formatContext)_localctx).tscale = measure();
			setState(76); ((Element_1_3_4_formatContext)_localctx).tflag = match(INTEGER);
			setState(77); match(2);
			setState(78); match(4);

			        String dc, nm, vl;
			        dc = (((Element_1_3_4_formatContext)_localctx).desc!=null?((Element_1_3_4_formatContext)_localctx).desc.getText():null).substring(1, (((Element_1_3_4_formatContext)_localctx).desc!=null?((Element_1_3_4_formatContext)_localctx).desc.getText():null).length() - 2);
			        nm = (((Element_1_3_4_formatContext)_localctx).name!=null?((Element_1_3_4_formatContext)_localctx).name.getText():null).substring(1, (((Element_1_3_4_formatContext)_localctx).name!=null?((Element_1_3_4_formatContext)_localctx).name.getText():null).length() - 2);
			        vl = "";
			        
			        ICText ictext = new ICText();
			        ictext.aX=((Element_1_3_4_formatContext)_localctx).tx.value*100;
				ictext.aY=((Element_1_3_4_formatContext)_localctx).ty.value*100;
				ictext.dir = ((Element_1_3_4_formatContext)_localctx).tdir.value;
				ictext.scale = ((Element_1_3_4_formatContext)_localctx).tscale.value;
				ictext.flags = Integer.valueOf((((Element_1_3_4_formatContext)_localctx).tflag!=null?((Element_1_3_4_formatContext)_localctx).tflag.getText():null));
			    
			        Mark mark = new Mark(0, 0);
			    
			        _localctx.footprint.setmICText(ictext);
			        _localctx.footprint.setmMark(mark);
			        _localctx.footprint.setFlags(Integer.valueOf((((Element_1_3_4_formatContext)_localctx).flag!=null?((Element_1_3_4_formatContext)_localctx).flag.getText():null)));
			        _localctx.footprint.setmDesc(dc);
			        _localctx.footprint.setmName(nm);
			        _localctx.footprint.setmValue(vl);
			      
			setState(80); elementdefinitions(_localctx.footprint);
			setState(81); match(2);

			        _localctx.footprint.centerTheFootprint();
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Element_newformatContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Token flag;
		public Token desc;
		public Token name;
		public Token value;
		public MeasureContext tx;
		public MeasureContext ty;
		public MeasureContext tdir;
		public MeasureContext tscale;
		public Token tflag;
		public List<TerminalNode> INTEGER() { return getTokens(FootprintParserParser.INTEGER); }
		public ElementdefinitionsContext elementdefinitions() {
			return getRuleContext(ElementdefinitionsContext.class,0);
		}
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public TerminalNode STRING(int i) {
			return getToken(FootprintParserParser.STRING, i);
		}
		public TerminalNode T_ELEMENT() { return getToken(FootprintParserParser.T_ELEMENT, 0); }
		public TerminalNode INTEGER(int i) {
			return getToken(FootprintParserParser.INTEGER, i);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public List<TerminalNode> STRING() { return getTokens(FootprintParserParser.STRING); }
		public Element_newformatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Element_newformatContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_element_newformat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterElement_newformat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitElement_newformat(this);
		}
	}

	public final Element_newformatContext element_newformat(ICFootprint footprint) throws RecognitionException {
		Element_newformatContext _localctx = new Element_newformatContext(_ctx, getState(), footprint);
		enterRule(_localctx, 6, RULE_element_newformat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84); match(T_ELEMENT);
			setState(85); match(4);
			setState(86); ((Element_newformatContext)_localctx).flag = match(INTEGER);
			setState(87); ((Element_newformatContext)_localctx).desc = match(STRING);
			setState(88); ((Element_newformatContext)_localctx).name = match(STRING);
			setState(89); ((Element_newformatContext)_localctx).value = match(STRING);
			setState(90); ((Element_newformatContext)_localctx).tx = measure();
			setState(91); ((Element_newformatContext)_localctx).ty = measure();
			setState(92); ((Element_newformatContext)_localctx).tdir = measure();
			setState(93); ((Element_newformatContext)_localctx).tscale = measure();
			setState(94); ((Element_newformatContext)_localctx).tflag = match(INTEGER);
			setState(95); match(2);
			setState(96); match(4);

			        String dc, nm, vl;
			        dc = (((Element_newformatContext)_localctx).desc!=null?((Element_newformatContext)_localctx).desc.getText():null).substring(1, (((Element_newformatContext)_localctx).desc!=null?((Element_newformatContext)_localctx).desc.getText():null).length() - 2);
			        nm = (((Element_newformatContext)_localctx).name!=null?((Element_newformatContext)_localctx).name.getText():null).substring(1, (((Element_newformatContext)_localctx).name!=null?((Element_newformatContext)_localctx).name.getText():null).length() - 2);
			        vl = (((Element_newformatContext)_localctx).value!=null?((Element_newformatContext)_localctx).value.getText():null).substring(1, (((Element_newformatContext)_localctx).value!=null?((Element_newformatContext)_localctx).value.getText():null).length() - 2);
			        
			        ICText ictext = new ICText();
			        ictext.aX=((Element_newformatContext)_localctx).tx.value*100;
				ictext.aY=((Element_newformatContext)_localctx).ty.value*100;
				ictext.dir = ((Element_newformatContext)_localctx).tdir.value;
				ictext.scale = ((Element_newformatContext)_localctx).tscale.value;
				ictext.flags = Integer.valueOf((((Element_newformatContext)_localctx).tflag!=null?((Element_newformatContext)_localctx).tflag.getText():null));
			    
			        Mark mark = new Mark(0, 0);
			    
			        _localctx.footprint.setmICText(ictext);
			        _localctx.footprint.setmMark(mark);
			        _localctx.footprint.setFlags(Integer.valueOf((((Element_newformatContext)_localctx).flag!=null?((Element_newformatContext)_localctx).flag.getText():null)));
			        _localctx.footprint.setmDesc(dc);
			        _localctx.footprint.setmName(nm);
			        _localctx.footprint.setmValue(vl);
			      
			setState(98); elementdefinitions(_localctx.footprint);
			setState(99); match(2);

			        _localctx.footprint.centerTheFootprint();
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Element_1_7_formatContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Token flag;
		public Token desc;
		public Token name;
		public Token value;
		public MeasureContext mx;
		public MeasureContext my;
		public MeasureContext tx;
		public MeasureContext ty;
		public NumberContext tdir;
		public NumberContext tscale;
		public Token tflag;
		public List<TerminalNode> INTEGER() { return getTokens(FootprintParserParser.INTEGER); }
		public RelementdefsContext relementdefs() {
			return getRuleContext(RelementdefsContext.class,0);
		}
		public NumberContext number(int i) {
			return getRuleContext(NumberContext.class,i);
		}
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public TerminalNode STRING(int i) {
			return getToken(FootprintParserParser.STRING, i);
		}
		public TerminalNode T_ELEMENT() { return getToken(FootprintParserParser.T_ELEMENT, 0); }
		public List<NumberContext> number() {
			return getRuleContexts(NumberContext.class);
		}
		public TerminalNode INTEGER(int i) {
			return getToken(FootprintParserParser.INTEGER, i);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public List<TerminalNode> STRING() { return getTokens(FootprintParserParser.STRING); }
		public Element_1_7_formatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Element_1_7_formatContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_element_1_7_format; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterElement_1_7_format(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitElement_1_7_format(this);
		}
	}

	public final Element_1_7_formatContext element_1_7_format(ICFootprint footprint) throws RecognitionException {
		Element_1_7_formatContext _localctx = new Element_1_7_formatContext(_ctx, getState(), footprint);
		enterRule(_localctx, 8, RULE_element_1_7_format);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102); match(T_ELEMENT);
			setState(103); match(4);
			setState(104); ((Element_1_7_formatContext)_localctx).flag = match(INTEGER);
			setState(105); ((Element_1_7_formatContext)_localctx).desc = match(STRING);
			setState(106); ((Element_1_7_formatContext)_localctx).name = match(STRING);
			setState(107); ((Element_1_7_formatContext)_localctx).value = match(STRING);
			setState(108); ((Element_1_7_formatContext)_localctx).mx = measure();
			setState(109); ((Element_1_7_formatContext)_localctx).my = measure();
			setState(110); ((Element_1_7_formatContext)_localctx).tx = measure();
			setState(111); ((Element_1_7_formatContext)_localctx).ty = measure();
			setState(112); ((Element_1_7_formatContext)_localctx).tdir = number();
			setState(113); ((Element_1_7_formatContext)_localctx).tscale = number();
			setState(114); ((Element_1_7_formatContext)_localctx).tflag = match(INTEGER);
			setState(115); match(2);
			setState(116); match(4);

			        String dc, nm, vl;
			        dc = (((Element_1_7_formatContext)_localctx).desc!=null?((Element_1_7_formatContext)_localctx).desc.getText():null).substring(1, (((Element_1_7_formatContext)_localctx).desc!=null?((Element_1_7_formatContext)_localctx).desc.getText():null).length() - 2);
			        nm = (((Element_1_7_formatContext)_localctx).name!=null?((Element_1_7_formatContext)_localctx).name.getText():null).substring(1, (((Element_1_7_formatContext)_localctx).name!=null?((Element_1_7_formatContext)_localctx).name.getText():null).length() - 2);
			        vl = (((Element_1_7_formatContext)_localctx).value!=null?((Element_1_7_formatContext)_localctx).value.getText():null).substring(1, (((Element_1_7_formatContext)_localctx).value!=null?((Element_1_7_formatContext)_localctx).value.getText():null).length() - 2);
			        
			        ICText ictext = new ICText();
			        ictext.aX=((Element_1_7_formatContext)_localctx).tx.value*100;
				ictext.aY=((Element_1_7_formatContext)_localctx).ty.value*100;
				ictext.dir = ((Element_1_7_formatContext)_localctx).tdir.value;
				ictext.scale = ((Element_1_7_formatContext)_localctx).tscale.value;
				ictext.flags = Integer.valueOf((((Element_1_7_formatContext)_localctx).tflag!=null?((Element_1_7_formatContext)_localctx).tflag.getText():null));
			    
			        Mark mark = new Mark(((Element_1_7_formatContext)_localctx).mx.value*100, ((Element_1_7_formatContext)_localctx).my.value*100);
			    
			        _localctx.footprint.setmICText(ictext);
			        _localctx.footprint.setmMark(mark);
			        _localctx.footprint.setFlags(Integer.valueOf((((Element_1_7_formatContext)_localctx).flag!=null?((Element_1_7_formatContext)_localctx).flag.getText():null)));
			        _localctx.footprint.setmDesc(dc);
			        _localctx.footprint.setmName(nm);
			        _localctx.footprint.setmValue(vl);
			      
			setState(118); relementdefs(_localctx.footprint);
			setState(119); match(2);

			        _localctx.footprint.centerTheFootprint();
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Element_hi_formatContext extends ParserRuleContext {
		public ICFootprint footprint;
		public FlagsContext flag;
		public Token desc;
		public Token name;
		public Token value;
		public MeasureContext mx;
		public MeasureContext my;
		public MeasureContext tx;
		public MeasureContext ty;
		public NumberContext tdir;
		public NumberContext tscale;
		public FlagsContext tflag;
		public RelementdefsContext relementdefs() {
			return getRuleContext(RelementdefsContext.class,0);
		}
		public List<FlagsContext> flags() {
			return getRuleContexts(FlagsContext.class);
		}
		public NumberContext number(int i) {
			return getRuleContext(NumberContext.class,i);
		}
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public TerminalNode STRING(int i) {
			return getToken(FootprintParserParser.STRING, i);
		}
		public TerminalNode T_ELEMENT() { return getToken(FootprintParserParser.T_ELEMENT, 0); }
		public FlagsContext flags(int i) {
			return getRuleContext(FlagsContext.class,i);
		}
		public List<NumberContext> number() {
			return getRuleContexts(NumberContext.class);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public List<TerminalNode> STRING() { return getTokens(FootprintParserParser.STRING); }
		public Element_hi_formatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Element_hi_formatContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_element_hi_format; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterElement_hi_format(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitElement_hi_format(this);
		}
	}

	public final Element_hi_formatContext element_hi_format(ICFootprint footprint) throws RecognitionException {
		Element_hi_formatContext _localctx = new Element_hi_formatContext(_ctx, getState(), footprint);
		enterRule(_localctx, 10, RULE_element_hi_format);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122); match(T_ELEMENT);
			setState(123); match(3);
			setState(124); ((Element_hi_formatContext)_localctx).flag = flags();
			setState(125); ((Element_hi_formatContext)_localctx).desc = match(STRING);
			setState(126); ((Element_hi_formatContext)_localctx).name = match(STRING);
			setState(127); ((Element_hi_formatContext)_localctx).value = match(STRING);
			setState(128); ((Element_hi_formatContext)_localctx).mx = measure();
			setState(129); ((Element_hi_formatContext)_localctx).my = measure();
			setState(130); ((Element_hi_formatContext)_localctx).tx = measure();
			setState(131); ((Element_hi_formatContext)_localctx).ty = measure();
			setState(132); ((Element_hi_formatContext)_localctx).tdir = number();
			setState(133); ((Element_hi_formatContext)_localctx).tscale = number();
			setState(134); ((Element_hi_formatContext)_localctx).tflag = flags();
			setState(135); match(1);
			setState(136); match(4);

			        String dc, nm, vl;
			        dc = (((Element_hi_formatContext)_localctx).desc!=null?((Element_hi_formatContext)_localctx).desc.getText():null).substring(1, (((Element_hi_formatContext)_localctx).desc!=null?((Element_hi_formatContext)_localctx).desc.getText():null).length() - 2);
			        nm = (((Element_hi_formatContext)_localctx).name!=null?((Element_hi_formatContext)_localctx).name.getText():null).substring(1, (((Element_hi_formatContext)_localctx).name!=null?((Element_hi_formatContext)_localctx).name.getText():null).length() - 2);
			        vl = (((Element_hi_formatContext)_localctx).value!=null?((Element_hi_formatContext)_localctx).value.getText():null).substring(1, (((Element_hi_formatContext)_localctx).value!=null?((Element_hi_formatContext)_localctx).value.getText():null).length() - 2);
			        
			        ICText ictext = new ICText();
			        ictext.aX=((Element_hi_formatContext)_localctx).tx.value;
				ictext.aY=((Element_hi_formatContext)_localctx).ty.value;
				ictext.dir = ((Element_hi_formatContext)_localctx).tdir.value;
				ictext.scale = ((Element_hi_formatContext)_localctx).tscale.value;
				ictext.flags = Integer.valueOf((((Element_hi_formatContext)_localctx).tflag!=null?_input.getText(((Element_hi_formatContext)_localctx).tflag.start,((Element_hi_formatContext)_localctx).tflag.stop):null));
			    
			        Mark mark = new Mark(((Element_hi_formatContext)_localctx).mx.value, ((Element_hi_formatContext)_localctx).my.value);
			    
			        _localctx.footprint.setmICText(ictext);
			        _localctx.footprint.setmMark(mark);
			        _localctx.footprint.setFlags(Integer.valueOf((((Element_hi_formatContext)_localctx).flag!=null?_input.getText(((Element_hi_formatContext)_localctx).flag.start,((Element_hi_formatContext)_localctx).flag.stop):null)));
			        _localctx.footprint.setmDesc(dc);
			        _localctx.footprint.setmName(nm);
			        _localctx.footprint.setmValue(vl);
			      
			setState(138); relementdefs(_localctx.footprint);
			setState(139); match(2);

				_localctx.footprint.centerTheFootprint();
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementdefinitionsContext extends ParserRuleContext {
		public ICFootprint footprint;
		public int totalPadPinNum;
		public int totalDraftLineNum;
		public ElementdefinitionContext val;
		public ElementdefinitionContext elementdefinition(int i) {
			return getRuleContext(ElementdefinitionContext.class,i);
		}
		public List<ElementdefinitionContext> elementdefinition() {
			return getRuleContexts(ElementdefinitionContext.class);
		}
		public ElementdefinitionsContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ElementdefinitionsContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_elementdefinitions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterElementdefinitions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitElementdefinitions(this);
		}
	}

	public final ElementdefinitionsContext elementdefinitions(ICFootprint footprint) throws RecognitionException {
		ElementdefinitionsContext _localctx = new ElementdefinitionsContext(_ctx, getState(), footprint);
		enterRule(_localctx, 12, RULE_elementdefinitions);

			((ElementdefinitionsContext)_localctx).totalPadPinNum =  0;
			((ElementdefinitionsContext)_localctx).totalDraftLineNum =  0;

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(142); ((ElementdefinitionsContext)_localctx).val = elementdefinition(_localctx.footprint);

				        _localctx.footprint.addPinOrPadOrDraftLine(((ElementdefinitionsContext)_localctx).val.obj);
				       
				       
				}
				}
				setState(147); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T_PIN) | (1L << T_PAD) | (1L << T_ELEMENTLINE) | (1L << T_ELEMENTARC) | (1L << T_MARK) | (1L << T_ATTRIBUTE))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementdefinitionContext extends ParserRuleContext {
		public ICFootprint footprint;
		public PinOrPadOrDraftLine obj;
		public Pin_1_6_3_formatContext npin;
		public Pin_newformatContext npin1;
		public Pin_oldformatContext npin2;
		public Pad_newformatContext npad;
		public PadContext npad1;
		public MeasureContext x1;
		public MeasureContext y1;
		public MeasureContext x2;
		public MeasureContext y2;
		public MeasureContext th;
		public MeasureContext x;
		public MeasureContext y;
		public MeasureContext w;
		public MeasureContext h;
		public NumberContext strt_ang;
		public NumberContext diff_ang;
		public TerminalNode T_MARK() { return getToken(FootprintParserParser.T_MARK, 0); }
		public PadContext pad() {
			return getRuleContext(PadContext.class,0);
		}
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public NumberContext number(int i) {
			return getRuleContext(NumberContext.class,i);
		}
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public List<NumberContext> number() {
			return getRuleContexts(NumberContext.class);
		}
		public Pin_1_6_3_formatContext pin_1_6_3_format() {
			return getRuleContext(Pin_1_6_3_formatContext.class,0);
		}
		public Pin_newformatContext pin_newformat() {
			return getRuleContext(Pin_newformatContext.class,0);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public Pin_oldformatContext pin_oldformat() {
			return getRuleContext(Pin_oldformatContext.class,0);
		}
		public TerminalNode T_ELEMENTARC() { return getToken(FootprintParserParser.T_ELEMENTARC, 0); }
		public TerminalNode T_ELEMENTLINE() { return getToken(FootprintParserParser.T_ELEMENTLINE, 0); }
		public Pad_newformatContext pad_newformat() {
			return getRuleContext(Pad_newformatContext.class,0);
		}
		public ElementdefinitionContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ElementdefinitionContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_elementdefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterElementdefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitElementdefinition(this);
		}
	}

	public final ElementdefinitionContext elementdefinition(ICFootprint footprint) throws RecognitionException {
		ElementdefinitionContext _localctx = new ElementdefinitionContext(_ctx, getState(), footprint);
		enterRule(_localctx, 14, RULE_elementdefinition);
		try {
			setState(224);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(149); ((ElementdefinitionContext)_localctx).npin = pin_1_6_3_format(_localctx.footprint);
				((ElementdefinitionContext)_localctx).obj =  ((ElementdefinitionContext)_localctx).npin.newpin;
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(152); ((ElementdefinitionContext)_localctx).npin1 = pin_newformat(_localctx.footprint);
				((ElementdefinitionContext)_localctx).obj =  ((ElementdefinitionContext)_localctx).npin1.newpin;
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(155); ((ElementdefinitionContext)_localctx).npin2 = pin_oldformat(_localctx.footprint);
				((ElementdefinitionContext)_localctx).obj =  ((ElementdefinitionContext)_localctx).npin2.newpin;
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(158); ((ElementdefinitionContext)_localctx).npad = pad_newformat(_localctx.footprint);
				((ElementdefinitionContext)_localctx).obj =  ((ElementdefinitionContext)_localctx).npad.newpad;
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(161); ((ElementdefinitionContext)_localctx).npad1 = pad(_localctx.footprint);
				((ElementdefinitionContext)_localctx).obj =  ((ElementdefinitionContext)_localctx).npad1.newpad;
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(164); match(T_ELEMENTLINE);
				setState(165); match(3);
				setState(166); ((ElementdefinitionContext)_localctx).x1 = measure();
				setState(167); ((ElementdefinitionContext)_localctx).y1 = measure();
				setState(168); ((ElementdefinitionContext)_localctx).x2 = measure();
				setState(169); ((ElementdefinitionContext)_localctx).y2 = measure();
				setState(170); ((ElementdefinitionContext)_localctx).th = measure();
				setState(171); match(1);

				        float mx,my;
				        mx=_localctx.footprint.getmMark().getaX();
				        my=_localctx.footprint.getmMark().getaY();
				        ((ElementdefinitionContext)_localctx).obj =  new ElementLine(((ElementdefinitionContext)_localctx).x1.value+mx, ((ElementdefinitionContext)_localctx).y1.value+my, ((ElementdefinitionContext)_localctx).x2.value+mx, ((ElementdefinitionContext)_localctx).y2.value+my, ((ElementdefinitionContext)_localctx).th.value);
				      
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(174); match(T_ELEMENTLINE);
				setState(175); match(4);
				setState(176); ((ElementdefinitionContext)_localctx).x1 = measure();
				setState(177); ((ElementdefinitionContext)_localctx).y1 = measure();
				setState(178); ((ElementdefinitionContext)_localctx).x2 = measure();
				setState(179); ((ElementdefinitionContext)_localctx).y2 = measure();
				setState(180); ((ElementdefinitionContext)_localctx).th = measure();
				setState(181); match(2);

				        float mx,my;
				        mx=_localctx.footprint.getmMark().getaX();
				        my=_localctx.footprint.getmMark().getaY();
					((ElementdefinitionContext)_localctx).obj =  new ElementLine(((ElementdefinitionContext)_localctx).x1.value*100+mx, ((ElementdefinitionContext)_localctx).y1.value*100+my, ((ElementdefinitionContext)_localctx).x2.value*100+mx, ((ElementdefinitionContext)_localctx).y2.value*100+my, ((ElementdefinitionContext)_localctx).th.value*100);
				      
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(184); match(T_ELEMENTARC);
				setState(185); match(3);
				setState(186); ((ElementdefinitionContext)_localctx).x = measure();
				setState(187); ((ElementdefinitionContext)_localctx).y = measure();
				setState(188); ((ElementdefinitionContext)_localctx).w = measure();
				setState(189); ((ElementdefinitionContext)_localctx).h = measure();
				setState(190); ((ElementdefinitionContext)_localctx).strt_ang = number();
				setState(191); ((ElementdefinitionContext)_localctx).diff_ang = number();
				setState(192); ((ElementdefinitionContext)_localctx).th = measure();
				setState(193); match(1);

				        float mx,my;
				        mx=_localctx.footprint.getmMark().getaX();
				        my=_localctx.footprint.getmMark().getaY();
				        ((ElementdefinitionContext)_localctx).obj =  new ElementArc(((ElementdefinitionContext)_localctx).x.value+mx, ((ElementdefinitionContext)_localctx).y.value+my, ((ElementdefinitionContext)_localctx).w.value, ((ElementdefinitionContext)_localctx).h.value, ((ElementdefinitionContext)_localctx).strt_ang.value, ((ElementdefinitionContext)_localctx).diff_ang.value, ((ElementdefinitionContext)_localctx).th.value);
				      
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(196); match(T_ELEMENTARC);
				setState(197); match(4);
				setState(198); ((ElementdefinitionContext)_localctx).x = measure();
				setState(199); ((ElementdefinitionContext)_localctx).y = measure();
				setState(200); ((ElementdefinitionContext)_localctx).w = measure();
				setState(201); ((ElementdefinitionContext)_localctx).h = measure();
				setState(202); ((ElementdefinitionContext)_localctx).strt_ang = number();
				setState(203); ((ElementdefinitionContext)_localctx).diff_ang = number();
				setState(204); ((ElementdefinitionContext)_localctx).th = measure();
				setState(205); match(2);

				        float mx,my;
				        mx=_localctx.footprint.getmMark().getaX();
				        my=_localctx.footprint.getmMark().getaY();
				        ((ElementdefinitionContext)_localctx).obj =  new ElementArc(((ElementdefinitionContext)_localctx).x.value*100+mx, ((ElementdefinitionContext)_localctx).y.value*100+my, ((ElementdefinitionContext)_localctx).w.value*100, ((ElementdefinitionContext)_localctx).h.value*100, ((ElementdefinitionContext)_localctx).strt_ang.value, ((ElementdefinitionContext)_localctx).diff_ang.value, ((ElementdefinitionContext)_localctx).th.value*100);
				      
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(208); match(T_MARK);
				setState(209); match(3);
				setState(210); ((ElementdefinitionContext)_localctx).x = measure();
				setState(211); ((ElementdefinitionContext)_localctx).y = measure();
				setState(212); match(1);

				        ((ElementdefinitionContext)_localctx).obj =  new Mark(((ElementdefinitionContext)_localctx).x.value, ((ElementdefinitionContext)_localctx).y.value);
				      
				}
				break;

			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(215); match(T_MARK);
				setState(216); match(4);
				setState(217); ((ElementdefinitionContext)_localctx).x = measure();
				setState(218); ((ElementdefinitionContext)_localctx).y = measure();
				setState(219); match(2);

				        ((ElementdefinitionContext)_localctx).obj =  new Mark(((ElementdefinitionContext)_localctx).x.value*100, ((ElementdefinitionContext)_localctx).y.value*100);
				      
				}
				break;

			case 12:
				enterOuterAlt(_localctx, 12);
				{
				 
				setState(223); attribute();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeContext extends ParserRuleContext {
		public TerminalNode STRING(int i) {
			return getToken(FootprintParserParser.STRING, i);
		}
		public TerminalNode T_ATTRIBUTE() { return getToken(FootprintParserParser.T_ATTRIBUTE, 0); }
		public List<TerminalNode> STRING() { return getTokens(FootprintParserParser.STRING); }
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitAttribute(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_attribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226); match(T_ATTRIBUTE);
			setState(227); match(4);
			setState(228); match(STRING);
			setState(229); match(STRING);
			setState(230); match(2);

			        
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelementdefsContext extends ParserRuleContext {
		public ICFootprint footprint;
		public int totalPadPinNum;
		public int totalDraftLineNum;
		public RelementdefContext val;
		public RelementdefContext relementdef(int i) {
			return getRuleContext(RelementdefContext.class,i);
		}
		public List<RelementdefContext> relementdef() {
			return getRuleContexts(RelementdefContext.class);
		}
		public RelementdefsContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public RelementdefsContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_relementdefs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterRelementdefs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitRelementdefs(this);
		}
	}

	public final RelementdefsContext relementdefs(ICFootprint footprint) throws RecognitionException {
		RelementdefsContext _localctx = new RelementdefsContext(_ctx, getState(), footprint);
		enterRule(_localctx, 18, RULE_relementdefs);

			((RelementdefsContext)_localctx).totalPadPinNum =  0;
			((RelementdefsContext)_localctx).totalDraftLineNum =  0;

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(233); ((RelementdefsContext)_localctx).val = relementdef(_localctx.footprint);

				        _localctx.footprint.addPinOrPadOrDraftLine(((RelementdefsContext)_localctx).val.obj);
				       
				       
				}
				}
				setState(238); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T_PIN) | (1L << T_PAD) | (1L << T_ELEMENTLINE) | (1L << T_ELEMENTARC) | (1L << T_ATTRIBUTE))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelementdefContext extends ParserRuleContext {
		public ICFootprint footprint;
		public PinOrPadOrDraftLine obj;
		public Pin_1_7_formatContext npin;
		public Pin_hi_formatContext npin1;
		public Pad_1_7_formatContext npad;
		public Pad_hi_formatContext npad1;
		public MeasureContext x1;
		public MeasureContext y1;
		public MeasureContext x2;
		public MeasureContext y2;
		public MeasureContext th;
		public MeasureContext x;
		public MeasureContext y;
		public MeasureContext w;
		public MeasureContext h;
		public NumberContext strt_ang;
		public NumberContext diff_ang;
		public Pin_1_7_formatContext pin_1_7_format() {
			return getRuleContext(Pin_1_7_formatContext.class,0);
		}
		public NumberContext number(int i) {
			return getRuleContext(NumberContext.class,i);
		}
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public Pad_hi_formatContext pad_hi_format() {
			return getRuleContext(Pad_hi_formatContext.class,0);
		}
		public Pad_1_7_formatContext pad_1_7_format() {
			return getRuleContext(Pad_1_7_formatContext.class,0);
		}
		public TerminalNode T_ELEMENTARC() { return getToken(FootprintParserParser.T_ELEMENTARC, 0); }
		public Pin_hi_formatContext pin_hi_format() {
			return getRuleContext(Pin_hi_formatContext.class,0);
		}
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public List<NumberContext> number() {
			return getRuleContexts(NumberContext.class);
		}
		public TerminalNode T_ELEMENTLINE() { return getToken(FootprintParserParser.T_ELEMENTLINE, 0); }
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public RelementdefContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public RelementdefContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_relementdef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterRelementdef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitRelementdef(this);
		}
	}

	public final RelementdefContext relementdef(ICFootprint footprint) throws RecognitionException {
		RelementdefContext _localctx = new RelementdefContext(_ctx, getState(), footprint);
		enterRule(_localctx, 20, RULE_relementdef);
		try {
			setState(298);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(240); ((RelementdefContext)_localctx).npin = pin_1_7_format(_localctx.footprint);
				((RelementdefContext)_localctx).obj =  ((RelementdefContext)_localctx).npin.newpin;
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(243); ((RelementdefContext)_localctx).npin1 = pin_hi_format(_localctx.footprint);
				((RelementdefContext)_localctx).obj =  ((RelementdefContext)_localctx).npin1.newpin;
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(246); ((RelementdefContext)_localctx).npad = pad_1_7_format(_localctx.footprint);
				((RelementdefContext)_localctx).obj =  ((RelementdefContext)_localctx).npad.newpad;
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(249); ((RelementdefContext)_localctx).npad1 = pad_hi_format(_localctx.footprint);
				((RelementdefContext)_localctx).obj =  ((RelementdefContext)_localctx).npad1.newpad;
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(252); match(T_ELEMENTLINE);
				setState(253); match(3);
				setState(254); ((RelementdefContext)_localctx).x1 = measure();
				setState(255); ((RelementdefContext)_localctx).y1 = measure();
				setState(256); ((RelementdefContext)_localctx).x2 = measure();
				setState(257); ((RelementdefContext)_localctx).y2 = measure();
				setState(258); ((RelementdefContext)_localctx).th = measure();
				setState(259); match(1);

				        float mx,my;
				        mx=_localctx.footprint.getmMark().getaX();
				        my=_localctx.footprint.getmMark().getaY();
				        ((RelementdefContext)_localctx).obj =  new ElementLine(((RelementdefContext)_localctx).x1.value+mx, ((RelementdefContext)_localctx).y1.value+my, ((RelementdefContext)_localctx).x2.value+mx, ((RelementdefContext)_localctx).y2.value+my, ((RelementdefContext)_localctx).th.value);
				      
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(262); match(T_ELEMENTLINE);
				setState(263); match(4);
				setState(264); ((RelementdefContext)_localctx).x1 = measure();
				setState(265); ((RelementdefContext)_localctx).y1 = measure();
				setState(266); ((RelementdefContext)_localctx).x2 = measure();
				setState(267); ((RelementdefContext)_localctx).y2 = measure();
				setState(268); ((RelementdefContext)_localctx).th = measure();
				setState(269); match(2);

				        float mx,my;
				        mx=_localctx.footprint.getmMark().getaX();
				        my=_localctx.footprint.getmMark().getaY();
					((RelementdefContext)_localctx).obj =  new ElementLine(((RelementdefContext)_localctx).x1.value*100+mx, ((RelementdefContext)_localctx).y1.value*100+my, ((RelementdefContext)_localctx).x2.value*100+mx, ((RelementdefContext)_localctx).y2.value*100+my, ((RelementdefContext)_localctx).th.value*100);
				      
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(272); match(T_ELEMENTARC);
				setState(273); match(3);
				setState(274); ((RelementdefContext)_localctx).x = measure();
				setState(275); ((RelementdefContext)_localctx).y = measure();
				setState(276); ((RelementdefContext)_localctx).w = measure();
				setState(277); ((RelementdefContext)_localctx).h = measure();
				setState(278); ((RelementdefContext)_localctx).strt_ang = number();
				setState(279); ((RelementdefContext)_localctx).diff_ang = number();
				setState(280); ((RelementdefContext)_localctx).th = measure();
				setState(281); match(1);

				        float mx,my;
				        mx=_localctx.footprint.getmMark().getaX();
				        my=_localctx.footprint.getmMark().getaY();
				        ((RelementdefContext)_localctx).obj =  new ElementArc(((RelementdefContext)_localctx).x.value+mx, ((RelementdefContext)_localctx).y.value+my, ((RelementdefContext)_localctx).w.value, ((RelementdefContext)_localctx).h.value, ((RelementdefContext)_localctx).strt_ang.value, ((RelementdefContext)_localctx).diff_ang.value, ((RelementdefContext)_localctx).th.value);
				      
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(284); match(T_ELEMENTARC);
				setState(285); match(4);
				setState(286); ((RelementdefContext)_localctx).x = measure();
				setState(287); ((RelementdefContext)_localctx).y = measure();
				setState(288); ((RelementdefContext)_localctx).w = measure();
				setState(289); ((RelementdefContext)_localctx).h = measure();
				setState(290); ((RelementdefContext)_localctx).strt_ang = number();
				setState(291); ((RelementdefContext)_localctx).diff_ang = number();
				setState(292); ((RelementdefContext)_localctx).th = measure();
				setState(293); match(2);

				        float mx,my;
				        mx=_localctx.footprint.getmMark().getaX();
				        my=_localctx.footprint.getmMark().getaY();
				        ((RelementdefContext)_localctx).obj =  new ElementArc(((RelementdefContext)_localctx).x.value*100+mx, ((RelementdefContext)_localctx).y.value*100+my, ((RelementdefContext)_localctx).w.value*100, ((RelementdefContext)_localctx).h.value*100, ((RelementdefContext)_localctx).strt_ang.value, ((RelementdefContext)_localctx).diff_ang.value, ((RelementdefContext)_localctx).th.value*100);
				      
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				  
				setState(297); attribute();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Pin_hi_formatContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Pin newpin;
		public MeasureContext x;
		public MeasureContext y;
		public MeasureContext th;
		public MeasureContext cl;
		public MeasureContext mk;
		public MeasureContext dr;
		public Token nm;
		public Token pn;
		public FlagsContext fl;
		public TerminalNode T_PIN() { return getToken(FootprintParserParser.T_PIN, 0); }
		public FlagsContext flags() {
			return getRuleContext(FlagsContext.class,0);
		}
		public TerminalNode STRING(int i) {
			return getToken(FootprintParserParser.STRING, i);
		}
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public List<TerminalNode> STRING() { return getTokens(FootprintParserParser.STRING); }
		public Pin_hi_formatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Pin_hi_formatContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_pin_hi_format; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterPin_hi_format(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitPin_hi_format(this);
		}
	}

	public final Pin_hi_formatContext pin_hi_format(ICFootprint footprint) throws RecognitionException {
		Pin_hi_formatContext _localctx = new Pin_hi_formatContext(_ctx, getState(), footprint);
		enterRule(_localctx, 22, RULE_pin_hi_format);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300); match(T_PIN);
			setState(301); match(3);
			setState(302); ((Pin_hi_formatContext)_localctx).x = measure();
			setState(303); ((Pin_hi_formatContext)_localctx).y = measure();
			setState(304); ((Pin_hi_formatContext)_localctx).th = measure();
			setState(305); ((Pin_hi_formatContext)_localctx).cl = measure();
			setState(306); ((Pin_hi_formatContext)_localctx).mk = measure();
			setState(307); ((Pin_hi_formatContext)_localctx).dr = measure();
			setState(308); ((Pin_hi_formatContext)_localctx).nm = match(STRING);
			setState(309); ((Pin_hi_formatContext)_localctx).pn = match(STRING);
			setState(310); ((Pin_hi_formatContext)_localctx).fl = flags();
			setState(311); match(1);

			        ((Pin_hi_formatContext)_localctx).newpin =  new Pin(  ((Pin_hi_formatContext)_localctx).x.value,
			                            ((Pin_hi_formatContext)_localctx).y.value,
			                            ((Pin_hi_formatContext)_localctx).th.value,
			                            ((Pin_hi_formatContext)_localctx).cl.value,
			                            ((Pin_hi_formatContext)_localctx).mk.value,
			                            ((Pin_hi_formatContext)_localctx).dr.value,
			                            (((Pin_hi_formatContext)_localctx).nm!=null?((Pin_hi_formatContext)_localctx).nm.getText():null).substring(1, (((Pin_hi_formatContext)_localctx).nm!=null?((Pin_hi_formatContext)_localctx).nm.getText():null).length() - 2),
			                            (((Pin_hi_formatContext)_localctx).pn!=null?((Pin_hi_formatContext)_localctx).pn.getText():null).substring(1, (((Pin_hi_formatContext)_localctx).pn!=null?((Pin_hi_formatContext)_localctx).pn.getText():null).length() - 2),
			                            ((Pin_hi_formatContext)_localctx).fl.value);	
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Pin_1_7_formatContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Pin newpin;
		public MeasureContext x;
		public MeasureContext y;
		public MeasureContext th;
		public MeasureContext cl;
		public MeasureContext mk;
		public MeasureContext dr;
		public Token nm;
		public Token pn;
		public Token fl;
		public TerminalNode INTEGER() { return getToken(FootprintParserParser.INTEGER, 0); }
		public TerminalNode T_PIN() { return getToken(FootprintParserParser.T_PIN, 0); }
		public TerminalNode STRING(int i) {
			return getToken(FootprintParserParser.STRING, i);
		}
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public List<TerminalNode> STRING() { return getTokens(FootprintParserParser.STRING); }
		public Pin_1_7_formatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Pin_1_7_formatContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_pin_1_7_format; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterPin_1_7_format(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitPin_1_7_format(this);
		}
	}

	public final Pin_1_7_formatContext pin_1_7_format(ICFootprint footprint) throws RecognitionException {
		Pin_1_7_formatContext _localctx = new Pin_1_7_formatContext(_ctx, getState(), footprint);
		enterRule(_localctx, 24, RULE_pin_1_7_format);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314); match(T_PIN);
			setState(315); match(4);
			setState(316); ((Pin_1_7_formatContext)_localctx).x = measure();
			setState(317); ((Pin_1_7_formatContext)_localctx).y = measure();
			setState(318); ((Pin_1_7_formatContext)_localctx).th = measure();
			setState(319); ((Pin_1_7_formatContext)_localctx).cl = measure();
			setState(320); ((Pin_1_7_formatContext)_localctx).mk = measure();
			setState(321); ((Pin_1_7_formatContext)_localctx).dr = measure();
			setState(322); ((Pin_1_7_formatContext)_localctx).nm = match(STRING);
			setState(323); ((Pin_1_7_formatContext)_localctx).pn = match(STRING);
			setState(324); ((Pin_1_7_formatContext)_localctx).fl = match(INTEGER);
			setState(325); match(2);

			        ((Pin_1_7_formatContext)_localctx).newpin =  new Pin(  ((Pin_1_7_formatContext)_localctx).x.value*100,
			                            ((Pin_1_7_formatContext)_localctx).y.value*100,
			                            ((Pin_1_7_formatContext)_localctx).th.value*100,
			                            ((Pin_1_7_formatContext)_localctx).cl.value*100,
			                            ((Pin_1_7_formatContext)_localctx).mk.value*100,
			                            ((Pin_1_7_formatContext)_localctx).dr.value*100,
			                            (((Pin_1_7_formatContext)_localctx).nm!=null?((Pin_1_7_formatContext)_localctx).nm.getText():null).substring(1, (((Pin_1_7_formatContext)_localctx).nm!=null?((Pin_1_7_formatContext)_localctx).nm.getText():null).length() - 2),
			                            (((Pin_1_7_formatContext)_localctx).pn!=null?((Pin_1_7_formatContext)_localctx).pn.getText():null).substring(1, (((Pin_1_7_formatContext)_localctx).pn!=null?((Pin_1_7_formatContext)_localctx).pn.getText():null).length() - 2),
			                            Integer.valueOf((((Pin_1_7_formatContext)_localctx).fl!=null?((Pin_1_7_formatContext)_localctx).fl.getText():null)));
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Pin_1_6_3_formatContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Pin newpin;
		public MeasureContext x;
		public MeasureContext y;
		public MeasureContext th;
		public MeasureContext dr;
		public Token nm;
		public Token pn;
		public Token fl;
		public TerminalNode INTEGER() { return getToken(FootprintParserParser.INTEGER, 0); }
		public TerminalNode T_PIN() { return getToken(FootprintParserParser.T_PIN, 0); }
		public TerminalNode STRING(int i) {
			return getToken(FootprintParserParser.STRING, i);
		}
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public List<TerminalNode> STRING() { return getTokens(FootprintParserParser.STRING); }
		public Pin_1_6_3_formatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Pin_1_6_3_formatContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_pin_1_6_3_format; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterPin_1_6_3_format(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitPin_1_6_3_format(this);
		}
	}

	public final Pin_1_6_3_formatContext pin_1_6_3_format(ICFootprint footprint) throws RecognitionException {
		Pin_1_6_3_formatContext _localctx = new Pin_1_6_3_formatContext(_ctx, getState(), footprint);
		enterRule(_localctx, 26, RULE_pin_1_6_3_format);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(328); match(T_PIN);
			setState(329); match(4);
			setState(330); ((Pin_1_6_3_formatContext)_localctx).x = measure();
			setState(331); ((Pin_1_6_3_formatContext)_localctx).y = measure();
			setState(332); ((Pin_1_6_3_formatContext)_localctx).th = measure();
			setState(333); ((Pin_1_6_3_formatContext)_localctx).dr = measure();
			setState(334); ((Pin_1_6_3_formatContext)_localctx).nm = match(STRING);
			setState(335); ((Pin_1_6_3_formatContext)_localctx).pn = match(STRING);
			setState(336); ((Pin_1_6_3_formatContext)_localctx).fl = match(INTEGER);
			setState(337); match(2);

			        ((Pin_1_6_3_formatContext)_localctx).newpin =  new Pin(  ((Pin_1_6_3_formatContext)_localctx).x.value*100,
			                            ((Pin_1_6_3_formatContext)_localctx).y.value*100,
			                            ((Pin_1_6_3_formatContext)_localctx).th.value*100,
			                            0.0f,
			                            0.0f,
			                            ((Pin_1_6_3_formatContext)_localctx).dr.value*100,
			                            (((Pin_1_6_3_formatContext)_localctx).nm!=null?((Pin_1_6_3_formatContext)_localctx).nm.getText():null).substring(1, (((Pin_1_6_3_formatContext)_localctx).nm!=null?((Pin_1_6_3_formatContext)_localctx).nm.getText():null).length() - 2),
			                            (((Pin_1_6_3_formatContext)_localctx).pn!=null?((Pin_1_6_3_formatContext)_localctx).pn.getText():null).substring(1, (((Pin_1_6_3_formatContext)_localctx).pn!=null?((Pin_1_6_3_formatContext)_localctx).pn.getText():null).length() - 2),
			                            Integer.valueOf((((Pin_1_6_3_formatContext)_localctx).fl!=null?((Pin_1_6_3_formatContext)_localctx).fl.getText():null)));	
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Pin_newformatContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Pin newpin;
		public MeasureContext x;
		public MeasureContext y;
		public MeasureContext th;
		public MeasureContext dr;
		public Token nm;
		public Token fl;
		public TerminalNode INTEGER() { return getToken(FootprintParserParser.INTEGER, 0); }
		public TerminalNode T_PIN() { return getToken(FootprintParserParser.T_PIN, 0); }
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public TerminalNode STRING() { return getToken(FootprintParserParser.STRING, 0); }
		public Pin_newformatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Pin_newformatContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_pin_newformat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterPin_newformat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitPin_newformat(this);
		}
	}

	public final Pin_newformatContext pin_newformat(ICFootprint footprint) throws RecognitionException {
		Pin_newformatContext _localctx = new Pin_newformatContext(_ctx, getState(), footprint);
		enterRule(_localctx, 28, RULE_pin_newformat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(340); match(T_PIN);
			setState(341); match(4);
			setState(342); ((Pin_newformatContext)_localctx).x = measure();
			setState(343); ((Pin_newformatContext)_localctx).y = measure();
			setState(344); ((Pin_newformatContext)_localctx).th = measure();
			setState(345); ((Pin_newformatContext)_localctx).dr = measure();
			setState(346); ((Pin_newformatContext)_localctx).nm = match(STRING);
			setState(347); ((Pin_newformatContext)_localctx).fl = match(INTEGER);
			setState(348); match(2);

			        ((Pin_newformatContext)_localctx).newpin =  new Pin(  ((Pin_newformatContext)_localctx).x.value*100,
			                            ((Pin_newformatContext)_localctx).y.value*100,
			                            ((Pin_newformatContext)_localctx).th.value*100,
			                            0.0f,
			                            0.0f,
			                            ((Pin_newformatContext)_localctx).dr.value*100,
			                            (((Pin_newformatContext)_localctx).nm!=null?((Pin_newformatContext)_localctx).nm.getText():null).substring(1, (((Pin_newformatContext)_localctx).nm!=null?((Pin_newformatContext)_localctx).nm.getText():null).length() - 2),
			                            "",
			                            Integer.valueOf((((Pin_newformatContext)_localctx).fl!=null?((Pin_newformatContext)_localctx).fl.getText():null)));
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Pin_oldformatContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Pin newpin;
		public MeasureContext x;
		public MeasureContext y;
		public MeasureContext th;
		public Token nm;
		public Token fl;
		public TerminalNode INTEGER() { return getToken(FootprintParserParser.INTEGER, 0); }
		public TerminalNode T_PIN() { return getToken(FootprintParserParser.T_PIN, 0); }
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public TerminalNode STRING() { return getToken(FootprintParserParser.STRING, 0); }
		public Pin_oldformatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Pin_oldformatContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_pin_oldformat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterPin_oldformat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitPin_oldformat(this);
		}
	}

	public final Pin_oldformatContext pin_oldformat(ICFootprint footprint) throws RecognitionException {
		Pin_oldformatContext _localctx = new Pin_oldformatContext(_ctx, getState(), footprint);
		enterRule(_localctx, 30, RULE_pin_oldformat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(351); match(T_PIN);
			setState(352); match(4);
			setState(353); ((Pin_oldformatContext)_localctx).x = measure();
			setState(354); ((Pin_oldformatContext)_localctx).y = measure();
			setState(355); ((Pin_oldformatContext)_localctx).th = measure();
			setState(356); ((Pin_oldformatContext)_localctx).nm = match(STRING);
			setState(357); ((Pin_oldformatContext)_localctx).fl = match(INTEGER);
			setState(358); match(2);

			        ((Pin_oldformatContext)_localctx).newpin =  new Pin(  ((Pin_oldformatContext)_localctx).x.value*100,
			                            ((Pin_oldformatContext)_localctx).y.value*100,
			                            ((Pin_oldformatContext)_localctx).th.value*100,
			                            0.0f,
			                            0.0f,
			                            ((Pin_oldformatContext)_localctx).th.value*40,
			                            (((Pin_oldformatContext)_localctx).nm!=null?((Pin_oldformatContext)_localctx).nm.getText():null).substring(1, (((Pin_oldformatContext)_localctx).nm!=null?((Pin_oldformatContext)_localctx).nm.getText():null).length() - 2),
			                            "",
			                            Integer.valueOf((((Pin_oldformatContext)_localctx).fl!=null?((Pin_oldformatContext)_localctx).fl.getText():null)));
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Pad_hi_formatContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Pad newpad;
		public MeasureContext x1;
		public MeasureContext y1;
		public MeasureContext x2;
		public MeasureContext y2;
		public MeasureContext th;
		public MeasureContext cl;
		public MeasureContext mk;
		public Token nm;
		public Token pn;
		public FlagsContext fl;
		public TerminalNode T_PAD() { return getToken(FootprintParserParser.T_PAD, 0); }
		public FlagsContext flags() {
			return getRuleContext(FlagsContext.class,0);
		}
		public TerminalNode STRING(int i) {
			return getToken(FootprintParserParser.STRING, i);
		}
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public List<TerminalNode> STRING() { return getTokens(FootprintParserParser.STRING); }
		public Pad_hi_formatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Pad_hi_formatContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_pad_hi_format; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterPad_hi_format(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitPad_hi_format(this);
		}
	}

	public final Pad_hi_formatContext pad_hi_format(ICFootprint footprint) throws RecognitionException {
		Pad_hi_formatContext _localctx = new Pad_hi_formatContext(_ctx, getState(), footprint);
		enterRule(_localctx, 32, RULE_pad_hi_format);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(361); match(T_PAD);
			setState(362); match(3);
			setState(363); ((Pad_hi_formatContext)_localctx).x1 = measure();
			setState(364); ((Pad_hi_formatContext)_localctx).y1 = measure();
			setState(365); ((Pad_hi_formatContext)_localctx).x2 = measure();
			setState(366); ((Pad_hi_formatContext)_localctx).y2 = measure();
			setState(367); ((Pad_hi_formatContext)_localctx).th = measure();
			setState(368); ((Pad_hi_formatContext)_localctx).cl = measure();
			setState(369); ((Pad_hi_formatContext)_localctx).mk = measure();
			setState(370); ((Pad_hi_formatContext)_localctx).nm = match(STRING);
			setState(371); ((Pad_hi_formatContext)_localctx).pn = match(STRING);
			setState(372); ((Pad_hi_formatContext)_localctx).fl = flags();
			setState(373); match(1);

			        ((Pad_hi_formatContext)_localctx).newpad =  new Pad(  ((Pad_hi_formatContext)_localctx).x1.value,
			                            ((Pad_hi_formatContext)_localctx).y1.value,
			                            ((Pad_hi_formatContext)_localctx).x2.value,
			                            ((Pad_hi_formatContext)_localctx).y2.value,
			                            ((Pad_hi_formatContext)_localctx).th.value,
			                            ((Pad_hi_formatContext)_localctx).cl.value,
			                            ((Pad_hi_formatContext)_localctx).mk.value,
			                            (((Pad_hi_formatContext)_localctx).nm!=null?((Pad_hi_formatContext)_localctx).nm.getText():null).substring(1, (((Pad_hi_formatContext)_localctx).nm!=null?((Pad_hi_formatContext)_localctx).nm.getText():null).length() - 2),
			                            (((Pad_hi_formatContext)_localctx).pn!=null?((Pad_hi_formatContext)_localctx).pn.getText():null).substring(1, (((Pad_hi_formatContext)_localctx).pn!=null?((Pad_hi_formatContext)_localctx).pn.getText():null).length() - 2),
			                            ((Pad_hi_formatContext)_localctx).fl.value);
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Pad_1_7_formatContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Pad newpad;
		public MeasureContext x1;
		public MeasureContext y1;
		public MeasureContext x2;
		public MeasureContext y2;
		public MeasureContext th;
		public MeasureContext cl;
		public MeasureContext mk;
		public Token nm;
		public Token pn;
		public Token fl;
		public TerminalNode INTEGER() { return getToken(FootprintParserParser.INTEGER, 0); }
		public TerminalNode T_PAD() { return getToken(FootprintParserParser.T_PAD, 0); }
		public TerminalNode STRING(int i) {
			return getToken(FootprintParserParser.STRING, i);
		}
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public List<TerminalNode> STRING() { return getTokens(FootprintParserParser.STRING); }
		public Pad_1_7_formatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Pad_1_7_formatContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_pad_1_7_format; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterPad_1_7_format(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitPad_1_7_format(this);
		}
	}

	public final Pad_1_7_formatContext pad_1_7_format(ICFootprint footprint) throws RecognitionException {
		Pad_1_7_formatContext _localctx = new Pad_1_7_formatContext(_ctx, getState(), footprint);
		enterRule(_localctx, 34, RULE_pad_1_7_format);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(376); match(T_PAD);
			setState(377); match(4);
			setState(378); ((Pad_1_7_formatContext)_localctx).x1 = measure();
			setState(379); ((Pad_1_7_formatContext)_localctx).y1 = measure();
			setState(380); ((Pad_1_7_formatContext)_localctx).x2 = measure();
			setState(381); ((Pad_1_7_formatContext)_localctx).y2 = measure();
			setState(382); ((Pad_1_7_formatContext)_localctx).th = measure();
			setState(383); ((Pad_1_7_formatContext)_localctx).cl = measure();
			setState(384); ((Pad_1_7_formatContext)_localctx).mk = measure();
			setState(385); ((Pad_1_7_formatContext)_localctx).nm = match(STRING);
			setState(386); ((Pad_1_7_formatContext)_localctx).pn = match(STRING);
			setState(387); ((Pad_1_7_formatContext)_localctx).fl = match(INTEGER);
			setState(388); match(2);

			        ((Pad_1_7_formatContext)_localctx).newpad =  new Pad(  ((Pad_1_7_formatContext)_localctx).x1.value*100,
			                            ((Pad_1_7_formatContext)_localctx).y1.value*100,
			                            ((Pad_1_7_formatContext)_localctx).x2.value*100,
			                            ((Pad_1_7_formatContext)_localctx).y2.value*100,
			                            ((Pad_1_7_formatContext)_localctx).th.value*100,
			                            ((Pad_1_7_formatContext)_localctx).cl.value*100,
			                            ((Pad_1_7_formatContext)_localctx).mk.value*100,
			                            (((Pad_1_7_formatContext)_localctx).nm!=null?((Pad_1_7_formatContext)_localctx).nm.getText():null).substring(1, (((Pad_1_7_formatContext)_localctx).nm!=null?((Pad_1_7_formatContext)_localctx).nm.getText():null).length() - 2),
			                            (((Pad_1_7_formatContext)_localctx).pn!=null?((Pad_1_7_formatContext)_localctx).pn.getText():null).substring(1, (((Pad_1_7_formatContext)_localctx).pn!=null?((Pad_1_7_formatContext)_localctx).pn.getText():null).length() - 2),
			                            Integer.valueOf((((Pad_1_7_formatContext)_localctx).fl!=null?((Pad_1_7_formatContext)_localctx).fl.getText():null)));
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Pad_newformatContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Pad newpad;
		public MeasureContext x1;
		public MeasureContext y1;
		public MeasureContext x2;
		public MeasureContext y2;
		public MeasureContext th;
		public Token nm;
		public Token pn;
		public Token fl;
		public TerminalNode INTEGER() { return getToken(FootprintParserParser.INTEGER, 0); }
		public TerminalNode T_PAD() { return getToken(FootprintParserParser.T_PAD, 0); }
		public TerminalNode STRING(int i) {
			return getToken(FootprintParserParser.STRING, i);
		}
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public List<TerminalNode> STRING() { return getTokens(FootprintParserParser.STRING); }
		public Pad_newformatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Pad_newformatContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_pad_newformat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterPad_newformat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitPad_newformat(this);
		}
	}

	public final Pad_newformatContext pad_newformat(ICFootprint footprint) throws RecognitionException {
		Pad_newformatContext _localctx = new Pad_newformatContext(_ctx, getState(), footprint);
		enterRule(_localctx, 36, RULE_pad_newformat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(391); match(T_PAD);
			setState(392); match(4);
			setState(393); ((Pad_newformatContext)_localctx).x1 = measure();
			setState(394); ((Pad_newformatContext)_localctx).y1 = measure();
			setState(395); ((Pad_newformatContext)_localctx).x2 = measure();
			setState(396); ((Pad_newformatContext)_localctx).y2 = measure();
			setState(397); ((Pad_newformatContext)_localctx).th = measure();
			setState(398); ((Pad_newformatContext)_localctx).nm = match(STRING);
			setState(399); ((Pad_newformatContext)_localctx).pn = match(STRING);
			setState(400); ((Pad_newformatContext)_localctx).fl = match(INTEGER);
			setState(401); match(2);

			        ((Pad_newformatContext)_localctx).newpad =  new Pad(  ((Pad_newformatContext)_localctx).x1.value*100,
			                            ((Pad_newformatContext)_localctx).y1.value*100,
			                            ((Pad_newformatContext)_localctx).x2.value*100,
			                            ((Pad_newformatContext)_localctx).y2.value*100,
			                            ((Pad_newformatContext)_localctx).th.value*100,
			                            0.0f,
			                            0.0f,
			                            (((Pad_newformatContext)_localctx).nm!=null?((Pad_newformatContext)_localctx).nm.getText():null).substring(1, (((Pad_newformatContext)_localctx).nm!=null?((Pad_newformatContext)_localctx).nm.getText():null).length() - 2),
			                            (((Pad_newformatContext)_localctx).pn!=null?((Pad_newformatContext)_localctx).pn.getText():null).substring(1, (((Pad_newformatContext)_localctx).pn!=null?((Pad_newformatContext)_localctx).pn.getText():null).length() - 2),
			                            Integer.valueOf((((Pad_newformatContext)_localctx).fl!=null?((Pad_newformatContext)_localctx).fl.getText():null)));
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PadContext extends ParserRuleContext {
		public ICFootprint footprint;
		public Pad newpad;
		public MeasureContext x1;
		public MeasureContext y1;
		public MeasureContext x2;
		public MeasureContext y2;
		public MeasureContext th;
		public Token nm;
		public Token fl;
		public TerminalNode INTEGER() { return getToken(FootprintParserParser.INTEGER, 0); }
		public TerminalNode T_PAD() { return getToken(FootprintParserParser.T_PAD, 0); }
		public List<MeasureContext> measure() {
			return getRuleContexts(MeasureContext.class);
		}
		public MeasureContext measure(int i) {
			return getRuleContext(MeasureContext.class,i);
		}
		public TerminalNode STRING() { return getToken(FootprintParserParser.STRING, 0); }
		public PadContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public PadContext(ParserRuleContext parent, int invokingState, ICFootprint footprint) {
			super(parent, invokingState);
			this.footprint = footprint;
		}
		@Override public int getRuleIndex() { return RULE_pad; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterPad(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitPad(this);
		}
	}

	public final PadContext pad(ICFootprint footprint) throws RecognitionException {
		PadContext _localctx = new PadContext(_ctx, getState(), footprint);
		enterRule(_localctx, 38, RULE_pad);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(404); match(T_PAD);
			setState(405); match(4);
			setState(406); ((PadContext)_localctx).x1 = measure();
			setState(407); ((PadContext)_localctx).y1 = measure();
			setState(408); ((PadContext)_localctx).x2 = measure();
			setState(409); ((PadContext)_localctx).y2 = measure();
			setState(410); ((PadContext)_localctx).th = measure();
			setState(411); ((PadContext)_localctx).nm = match(STRING);
			setState(412); ((PadContext)_localctx).fl = match(INTEGER);
			setState(413); match(2);

			        ((PadContext)_localctx).newpad =  new Pad(  ((PadContext)_localctx).x1.value*100,
			                            ((PadContext)_localctx).y1.value*100,
			                            ((PadContext)_localctx).x2.value*100,
			                            ((PadContext)_localctx).y2.value*100,
			                            ((PadContext)_localctx).th.value*100,
			                            0.0f,
			                            0.0f,
			                            (((PadContext)_localctx).nm!=null?((PadContext)_localctx).nm.getText():null).substring(1, (((PadContext)_localctx).nm!=null?((PadContext)_localctx).nm.getText():null).length() - 2),
			                            "",
			                            Integer.valueOf((((PadContext)_localctx).fl!=null?((PadContext)_localctx).fl.getText():null)));
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FlagsContext extends ParserRuleContext {
		public int value;
		public Token INTEGER;
		public Token st;
		public TerminalNode INTEGER() { return getToken(FootprintParserParser.INTEGER, 0); }
		public TerminalNode STRING() { return getToken(FootprintParserParser.STRING, 0); }
		public FlagsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_flags; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterFlags(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitFlags(this);
		}
	}

	public final FlagsContext flags() throws RecognitionException {
		FlagsContext _localctx = new FlagsContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_flags);
		try {
			setState(420);
			switch (_input.LA(1)) {
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(416); ((FlagsContext)_localctx).INTEGER = match(INTEGER);
				 ((FlagsContext)_localctx).value =  Integer.valueOf((((FlagsContext)_localctx).INTEGER!=null?((FlagsContext)_localctx).INTEGER.getText():null)); 
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(418); ((FlagsContext)_localctx).st = match(STRING);
				 _localctx.value = ICFootprint.stringToFlags((((FlagsContext)_localctx).st!=null?((FlagsContext)_localctx).st.getText():null).substring(1, (((FlagsContext)_localctx).st!=null?((FlagsContext)_localctx).st.getText():null).length() - 2)) 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public float value;
		public Token FLOATING;
		public Token INTEGER;
		public TerminalNode INTEGER() { return getToken(FootprintParserParser.INTEGER, 0); }
		public TerminalNode FLOATING() { return getToken(FootprintParserParser.FLOATING, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitNumber(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_number);
		try {
			setState(426);
			switch (_input.LA(1)) {
			case FLOATING:
				enterOuterAlt(_localctx, 1);
				{
				setState(422); ((NumberContext)_localctx).FLOATING = match(FLOATING);
				 ((NumberContext)_localctx).value =  Float.valueOf((((NumberContext)_localctx).FLOATING!=null?((NumberContext)_localctx).FLOATING.getText():null)); 
				}
				break;
			case INTEGER:
				enterOuterAlt(_localctx, 2);
				{
				setState(424); ((NumberContext)_localctx).INTEGER = match(INTEGER);
				 ((NumberContext)_localctx).value =  Float.valueOf((((NumberContext)_localctx).INTEGER!=null?((NumberContext)_localctx).INTEGER.getText():null)); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MeasureContext extends ParserRuleContext {
		public float value;
		public NumberContext r;
		public TerminalNode T_UM() { return getToken(FootprintParserParser.T_UM, 0); }
		public TerminalNode T_NM() { return getToken(FootprintParserParser.T_NM, 0); }
		public TerminalNode T_UMIL() { return getToken(FootprintParserParser.T_UMIL, 0); }
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public TerminalNode T_M() { return getToken(FootprintParserParser.T_M, 0); }
		public TerminalNode T_MIL() { return getToken(FootprintParserParser.T_MIL, 0); }
		public TerminalNode T_KM() { return getToken(FootprintParserParser.T_KM, 0); }
		public TerminalNode T_CMIL() { return getToken(FootprintParserParser.T_CMIL, 0); }
		public TerminalNode T_IN() { return getToken(FootprintParserParser.T_IN, 0); }
		public TerminalNode T_MM() { return getToken(FootprintParserParser.T_MM, 0); }
		public MeasureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_measure; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).enterMeasure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FootprintParserListener ) ((FootprintParserListener)listener).exitMeasure(this);
		}
	}

	public final MeasureContext measure() throws RecognitionException {
		MeasureContext _localctx = new MeasureContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_measure);
		try {
			setState(467);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(428); ((MeasureContext)_localctx).r = number();
				 ((MeasureContext)_localctx).value =  ((MeasureContext)_localctx).r.value;
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(431); ((MeasureContext)_localctx).r = number();
				setState(432); match(T_UMIL);
				 ((MeasureContext)_localctx).value =  ICFootprint.CentiMil.toCentiMil(((MeasureContext)_localctx).r.value,ICFootprint.CentiMil.UNIT_UMIL);/*umil*/ 
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(435); ((MeasureContext)_localctx).r = number();
				setState(436); match(T_CMIL);
				 ((MeasureContext)_localctx).value =  ICFootprint.CentiMil.toCentiMil(((MeasureContext)_localctx).r.value,ICFootprint.CentiMil.UNIT_CMIL);/*centimil*/ 
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(439); ((MeasureContext)_localctx).r = number();
				setState(440); match(T_MIL);
				 ((MeasureContext)_localctx).value =  ICFootprint.CentiMil.toCentiMil(((MeasureContext)_localctx).r.value,ICFootprint.CentiMil.UNIT_MIL);/*mil*/ 
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(443); ((MeasureContext)_localctx).r = number();
				setState(444); match(T_IN);
				 ((MeasureContext)_localctx).value =  ICFootprint.CentiMil.toCentiMil(((MeasureContext)_localctx).r.value,ICFootprint.CentiMil.UNIT_INCH);/*inch*/ 
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(447); ((MeasureContext)_localctx).r = number();
				setState(448); match(T_NM);
				 ((MeasureContext)_localctx).value =  ICFootprint.CentiMil.toCentiMil(((MeasureContext)_localctx).r.value,ICFootprint.CentiMil.UNIT_NM);/*nm*/ 
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(451); ((MeasureContext)_localctx).r = number();
				setState(452); match(T_UM);
				 ((MeasureContext)_localctx).value =  ICFootprint.CentiMil.toCentiMil(((MeasureContext)_localctx).r.value,ICFootprint.CentiMil.UNIT_UM);/*um*/ 
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(455); ((MeasureContext)_localctx).r = number();
				setState(456); match(T_MM);
				 ((MeasureContext)_localctx).value =  ICFootprint.CentiMil.toCentiMil(((MeasureContext)_localctx).r.value,ICFootprint.CentiMil.UNIT_MM);/*mm*/ 
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(459); ((MeasureContext)_localctx).r = number();
				setState(460); match(T_M);
				 ((MeasureContext)_localctx).value =  ICFootprint.CentiMil.toCentiMil(((MeasureContext)_localctx).r.value,ICFootprint.CentiMil.UNIT_M);/*m*/ 
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(463); ((MeasureContext)_localctx).r = number();
				setState(464); match(T_KM);
				 ((MeasureContext)_localctx).value =  ICFootprint.CentiMil.toCentiMil(((MeasureContext)_localctx).r.value,ICFootprint.CentiMil.UNIT_KM);/*km*/ 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\2\3\35\u01d8\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b"+
		"\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t"+
		"\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t"+
		"\27\4\30\t\30\3\2\3\2\3\2\3\2\3\2\5\2\66\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\6\b\u0094"+
		"\n\b\r\b\16\b\u0095\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00e3\n\t\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\13\3\13\3\13\6\13\u00ef\n\13\r\13\16\13\u00f0\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\5\f\u012d\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3"+
		"\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\5\26\u01a7\n\26"+
		"\3\27\3\27\3\27\3\27\5\27\u01ad\n\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\5\30\u01d6\n\30\3\30\2\31\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\34\36 \"$&(*,.\2\2\u01e4\2\65\3\2\2\2\4\67\3\2\2\2\6E\3\2\2"+
		"\2\bV\3\2\2\2\nh\3\2\2\2\f|\3\2\2\2\16\u0093\3\2\2\2\20\u00e2\3\2\2\2"+
		"\22\u00e4\3\2\2\2\24\u00ee\3\2\2\2\26\u012c\3\2\2\2\30\u012e\3\2\2\2\32"+
		"\u013c\3\2\2\2\34\u014a\3\2\2\2\36\u0156\3\2\2\2 \u0161\3\2\2\2\"\u016b"+
		"\3\2\2\2$\u017a\3\2\2\2&\u0189\3\2\2\2(\u0196\3\2\2\2*\u01a6\3\2\2\2,"+
		"\u01ac\3\2\2\2.\u01d5\3\2\2\2\60\66\5\4\3\2\61\66\5\6\4\2\62\66\5\b\5"+
		"\2\63\66\5\n\6\2\64\66\5\f\7\2\65\60\3\2\2\2\65\61\3\2\2\2\65\62\3\2\2"+
		"\2\65\63\3\2\2\2\65\64\3\2\2\2\66\3\3\2\2\2\678\7\22\2\289\7\6\2\29:\7"+
		"\f\2\2:;\7\f\2\2;<\5.\30\2<=\5.\30\2=>\7\n\2\2>?\7\4\2\2?@\7\6\2\2@A\b"+
		"\3\1\2AB\5\16\b\2BC\7\4\2\2CD\b\3\1\2D\5\3\2\2\2EF\7\22\2\2FG\7\6\2\2"+
		"GH\7\n\2\2HI\7\f\2\2IJ\7\f\2\2JK\5.\30\2KL\5.\30\2LM\5.\30\2MN\5.\30\2"+
		"NO\7\n\2\2OP\7\4\2\2PQ\7\6\2\2QR\b\4\1\2RS\5\16\b\2ST\7\4\2\2TU\b\4\1"+
		"\2U\7\3\2\2\2VW\7\22\2\2WX\7\6\2\2XY\7\n\2\2YZ\7\f\2\2Z[\7\f\2\2[\\\7"+
		"\f\2\2\\]\5.\30\2]^\5.\30\2^_\5.\30\2_`\5.\30\2`a\7\n\2\2ab\7\4\2\2bc"+
		"\7\6\2\2cd\b\5\1\2de\5\16\b\2ef\7\4\2\2fg\b\5\1\2g\t\3\2\2\2hi\7\22\2"+
		"\2ij\7\6\2\2jk\7\n\2\2kl\7\f\2\2lm\7\f\2\2mn\7\f\2\2no\5.\30\2op\5.\30"+
		"\2pq\5.\30\2qr\5.\30\2rs\5,\27\2st\5,\27\2tu\7\n\2\2uv\7\4\2\2vw\7\6\2"+
		"\2wx\b\6\1\2xy\5\24\13\2yz\7\4\2\2z{\b\6\1\2{\13\3\2\2\2|}\7\22\2\2}~"+
		"\7\5\2\2~\177\5*\26\2\177\u0080\7\f\2\2\u0080\u0081\7\f\2\2\u0081\u0082"+
		"\7\f\2\2\u0082\u0083\5.\30\2\u0083\u0084\5.\30\2\u0084\u0085\5.\30\2\u0085"+
		"\u0086\5.\30\2\u0086\u0087\5,\27\2\u0087\u0088\5,\27\2\u0088\u0089\5*"+
		"\26\2\u0089\u008a\7\3\2\2\u008a\u008b\7\6\2\2\u008b\u008c\b\7\1\2\u008c"+
		"\u008d\5\24\13\2\u008d\u008e\7\4\2\2\u008e\u008f\b\7\1\2\u008f\r\3\2\2"+
		"\2\u0090\u0091\5\20\t\2\u0091\u0092\b\b\1\2\u0092\u0094\3\2\2\2\u0093"+
		"\u0090\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0093\3\2\2\2\u0095\u0096\3\2"+
		"\2\2\u0096\17\3\2\2\2\u0097\u0098\5\34\17\2\u0098\u0099\b\t\1\2\u0099"+
		"\u00e3\3\2\2\2\u009a\u009b\5\36\20\2\u009b\u009c\b\t\1\2\u009c\u00e3\3"+
		"\2\2\2\u009d\u009e\5 \21\2\u009e\u009f\b\t\1\2\u009f\u00e3\3\2\2\2\u00a0"+
		"\u00a1\5&\24\2\u00a1\u00a2\b\t\1\2\u00a2\u00e3\3\2\2\2\u00a3\u00a4\5("+
		"\25\2\u00a4\u00a5\b\t\1\2\u00a5\u00e3\3\2\2\2\u00a6\u00a7\7\20\2\2\u00a7"+
		"\u00a8\7\5\2\2\u00a8\u00a9\5.\30\2\u00a9\u00aa\5.\30\2\u00aa\u00ab\5."+
		"\30\2\u00ab\u00ac\5.\30\2\u00ac\u00ad\5.\30\2\u00ad\u00ae\7\3\2\2\u00ae"+
		"\u00af\b\t\1\2\u00af\u00e3\3\2\2\2\u00b0\u00b1\7\20\2\2\u00b1\u00b2\7"+
		"\6\2\2\u00b2\u00b3\5.\30\2\u00b3\u00b4\5.\30\2\u00b4\u00b5\5.\30\2\u00b5"+
		"\u00b6\5.\30\2\u00b6\u00b7\5.\30\2\u00b7\u00b8\7\4\2\2\u00b8\u00b9\b\t"+
		"\1\2\u00b9\u00e3\3\2\2\2\u00ba\u00bb\7\21\2\2\u00bb\u00bc\7\5\2\2\u00bc"+
		"\u00bd\5.\30\2\u00bd\u00be\5.\30\2\u00be\u00bf\5.\30\2\u00bf\u00c0\5."+
		"\30\2\u00c0\u00c1\5,\27\2\u00c1\u00c2\5,\27\2\u00c2\u00c3\5.\30\2\u00c3"+
		"\u00c4\7\3\2\2\u00c4\u00c5\b\t\1\2\u00c5\u00e3\3\2\2\2\u00c6\u00c7\7\21"+
		"\2\2\u00c7\u00c8\7\6\2\2\u00c8\u00c9\5.\30\2\u00c9\u00ca\5.\30\2\u00ca"+
		"\u00cb\5.\30\2\u00cb\u00cc\5.\30\2\u00cc\u00cd\5,\27\2\u00cd\u00ce\5,"+
		"\27\2\u00ce\u00cf\5.\30\2\u00cf\u00d0\7\4\2\2\u00d0\u00d1\b\t\1\2\u00d1"+
		"\u00e3\3\2\2\2\u00d2\u00d3\7\23\2\2\u00d3\u00d4\7\5\2\2\u00d4\u00d5\5"+
		".\30\2\u00d5\u00d6\5.\30\2\u00d6\u00d7\7\3\2\2\u00d7\u00d8\b\t\1\2\u00d8"+
		"\u00e3\3\2\2\2\u00d9\u00da\7\23\2\2\u00da\u00db\7\6\2\2\u00db\u00dc\5"+
		".\30\2\u00dc\u00dd\5.\30\2\u00dd\u00de\7\4\2\2\u00de\u00df\b\t\1\2\u00df"+
		"\u00e3\3\2\2\2\u00e0\u00e1\b\t\1\2\u00e1\u00e3\5\22\n\2\u00e2\u0097\3"+
		"\2\2\2\u00e2\u009a\3\2\2\2\u00e2\u009d\3\2\2\2\u00e2\u00a0\3\2\2\2\u00e2"+
		"\u00a3\3\2\2\2\u00e2\u00a6\3\2\2\2\u00e2\u00b0\3\2\2\2\u00e2\u00ba\3\2"+
		"\2\2\u00e2\u00c6\3\2\2\2\u00e2\u00d2\3\2\2\2\u00e2\u00d9\3\2\2\2\u00e2"+
		"\u00e0\3\2\2\2\u00e3\21\3\2\2\2\u00e4\u00e5\7\24\2\2\u00e5\u00e6\7\6\2"+
		"\2\u00e6\u00e7\7\f\2\2\u00e7\u00e8\7\f\2\2\u00e8\u00e9\7\4\2\2\u00e9\u00ea"+
		"\b\n\1\2\u00ea\23\3\2\2\2\u00eb\u00ec\5\26\f\2\u00ec\u00ed\b\13\1\2\u00ed"+
		"\u00ef\3\2\2\2\u00ee\u00eb\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00ee\3\2"+
		"\2\2\u00f0\u00f1\3\2\2\2\u00f1\25\3\2\2\2\u00f2\u00f3\5\32\16\2\u00f3"+
		"\u00f4\b\f\1\2\u00f4\u012d\3\2\2\2\u00f5\u00f6\5\30\r\2\u00f6\u00f7\b"+
		"\f\1\2\u00f7\u012d\3\2\2\2\u00f8\u00f9\5$\23\2\u00f9\u00fa\b\f\1\2\u00fa"+
		"\u012d\3\2\2\2\u00fb\u00fc\5\"\22\2\u00fc\u00fd\b\f\1\2\u00fd\u012d\3"+
		"\2\2\2\u00fe\u00ff\7\20\2\2\u00ff\u0100\7\5\2\2\u0100\u0101\5.\30\2\u0101"+
		"\u0102\5.\30\2\u0102\u0103\5.\30\2\u0103\u0104\5.\30\2\u0104\u0105\5."+
		"\30\2\u0105\u0106\7\3\2\2\u0106\u0107\b\f\1\2\u0107\u012d\3\2\2\2\u0108"+
		"\u0109\7\20\2\2\u0109\u010a\7\6\2\2\u010a\u010b\5.\30\2\u010b\u010c\5"+
		".\30\2\u010c\u010d\5.\30\2\u010d\u010e\5.\30\2\u010e\u010f\5.\30\2\u010f"+
		"\u0110\7\4\2\2\u0110\u0111\b\f\1\2\u0111\u012d\3\2\2\2\u0112\u0113\7\21"+
		"\2\2\u0113\u0114\7\5\2\2\u0114\u0115\5.\30\2\u0115\u0116\5.\30\2\u0116"+
		"\u0117\5.\30\2\u0117\u0118\5.\30\2\u0118\u0119\5,\27\2\u0119\u011a\5,"+
		"\27\2\u011a\u011b\5.\30\2\u011b\u011c\7\3\2\2\u011c\u011d\b\f\1\2\u011d"+
		"\u012d\3\2\2\2\u011e\u011f\7\21\2\2\u011f\u0120\7\6\2\2\u0120\u0121\5"+
		".\30\2\u0121\u0122\5.\30\2\u0122\u0123\5.\30\2\u0123\u0124\5.\30\2\u0124"+
		"\u0125\5,\27\2\u0125\u0126\5,\27\2\u0126\u0127\5.\30\2\u0127\u0128\7\4"+
		"\2\2\u0128\u0129\b\f\1\2\u0129\u012d\3\2\2\2\u012a\u012b\b\f\1\2\u012b"+
		"\u012d\5\22\n\2\u012c\u00f2\3\2\2\2\u012c\u00f5\3\2\2\2\u012c\u00f8\3"+
		"\2\2\2\u012c\u00fb\3\2\2\2\u012c\u00fe\3\2\2\2\u012c\u0108\3\2\2\2\u012c"+
		"\u0112\3\2\2\2\u012c\u011e\3\2\2\2\u012c\u012a\3\2\2\2\u012d\27\3\2\2"+
		"\2\u012e\u012f\7\16\2\2\u012f\u0130\7\5\2\2\u0130\u0131\5.\30\2\u0131"+
		"\u0132\5.\30\2\u0132\u0133\5.\30\2\u0133\u0134\5.\30\2\u0134\u0135\5."+
		"\30\2\u0135\u0136\5.\30\2\u0136\u0137\7\f\2\2\u0137\u0138\7\f\2\2\u0138"+
		"\u0139\5*\26\2\u0139\u013a\7\3\2\2\u013a\u013b\b\r\1\2\u013b\31\3\2\2"+
		"\2\u013c\u013d\7\16\2\2\u013d\u013e\7\6\2\2\u013e\u013f\5.\30\2\u013f"+
		"\u0140\5.\30\2\u0140\u0141\5.\30\2\u0141\u0142\5.\30\2\u0142\u0143\5."+
		"\30\2\u0143\u0144\5.\30\2\u0144\u0145\7\f\2\2\u0145\u0146\7\f\2\2\u0146"+
		"\u0147\7\n\2\2\u0147\u0148\7\4\2\2\u0148\u0149\b\16\1\2\u0149\33\3\2\2"+
		"\2\u014a\u014b\7\16\2\2\u014b\u014c\7\6\2\2\u014c\u014d\5.\30\2\u014d"+
		"\u014e\5.\30\2\u014e\u014f\5.\30\2\u014f\u0150\5.\30\2\u0150\u0151\7\f"+
		"\2\2\u0151\u0152\7\f\2\2\u0152\u0153\7\n\2\2\u0153\u0154\7\4\2\2\u0154"+
		"\u0155\b\17\1\2\u0155\35\3\2\2\2\u0156\u0157\7\16\2\2\u0157\u0158\7\6"+
		"\2\2\u0158\u0159\5.\30\2\u0159\u015a\5.\30\2\u015a\u015b\5.\30\2\u015b"+
		"\u015c\5.\30\2\u015c\u015d\7\f\2\2\u015d\u015e\7\n\2\2\u015e\u015f\7\4"+
		"\2\2\u015f\u0160\b\20\1\2\u0160\37\3\2\2\2\u0161\u0162\7\16\2\2\u0162"+
		"\u0163\7\6\2\2\u0163\u0164\5.\30\2\u0164\u0165\5.\30\2\u0165\u0166\5."+
		"\30\2\u0166\u0167\7\f\2\2\u0167\u0168\7\n\2\2\u0168\u0169\7\4\2\2\u0169"+
		"\u016a\b\21\1\2\u016a!\3\2\2\2\u016b\u016c\7\17\2\2\u016c\u016d\7\5\2"+
		"\2\u016d\u016e\5.\30\2\u016e\u016f\5.\30\2\u016f\u0170\5.\30\2\u0170\u0171"+
		"\5.\30\2\u0171\u0172\5.\30\2\u0172\u0173\5.\30\2\u0173\u0174\5.\30\2\u0174"+
		"\u0175\7\f\2\2\u0175\u0176\7\f\2\2\u0176\u0177\5*\26\2\u0177\u0178\7\3"+
		"\2\2\u0178\u0179\b\22\1\2\u0179#\3\2\2\2\u017a\u017b\7\17\2\2\u017b\u017c"+
		"\7\6\2\2\u017c\u017d\5.\30\2\u017d\u017e\5.\30\2\u017e\u017f\5.\30\2\u017f"+
		"\u0180\5.\30\2\u0180\u0181\5.\30\2\u0181\u0182\5.\30\2\u0182\u0183\5."+
		"\30\2\u0183\u0184\7\f\2\2\u0184\u0185\7\f\2\2\u0185\u0186\7\n\2\2\u0186"+
		"\u0187\7\4\2\2\u0187\u0188\b\23\1\2\u0188%\3\2\2\2\u0189\u018a\7\17\2"+
		"\2\u018a\u018b\7\6\2\2\u018b\u018c\5.\30\2\u018c\u018d\5.\30\2\u018d\u018e"+
		"\5.\30\2\u018e\u018f\5.\30\2\u018f\u0190\5.\30\2\u0190\u0191\7\f\2\2\u0191"+
		"\u0192\7\f\2\2\u0192\u0193\7\n\2\2\u0193\u0194\7\4\2\2\u0194\u0195\b\24"+
		"\1\2\u0195\'\3\2\2\2\u0196\u0197\7\17\2\2\u0197\u0198\7\6\2\2\u0198\u0199"+
		"\5.\30\2\u0199\u019a\5.\30\2\u019a\u019b\5.\30\2\u019b\u019c\5.\30\2\u019c"+
		"\u019d\5.\30\2\u019d\u019e\7\f\2\2\u019e\u019f\7\n\2\2\u019f\u01a0\7\4"+
		"\2\2\u01a0\u01a1\b\25\1\2\u01a1)\3\2\2\2\u01a2\u01a3\7\n\2\2\u01a3\u01a7"+
		"\b\26\1\2\u01a4\u01a5\7\f\2\2\u01a5\u01a7\b\26\1\2\u01a6\u01a2\3\2\2\2"+
		"\u01a6\u01a4\3\2\2\2\u01a7+\3\2\2\2\u01a8\u01a9\7\13\2\2\u01a9\u01ad\b"+
		"\27\1\2\u01aa\u01ab\7\n\2\2\u01ab\u01ad\b\27\1\2\u01ac\u01a8\3\2\2\2\u01ac"+
		"\u01aa\3\2\2\2\u01ad-\3\2\2\2\u01ae\u01af\5,\27\2\u01af\u01b0\b\30\1\2"+
		"\u01b0\u01d6\3\2\2\2\u01b1\u01b2\5,\27\2\u01b2\u01b3\7\32\2\2\u01b3\u01b4"+
		"\b\30\1\2\u01b4\u01d6\3\2\2\2\u01b5\u01b6\5,\27\2\u01b6\u01b7\7\33\2\2"+
		"\u01b7\u01b8\b\30\1\2\u01b8\u01d6\3\2\2\2\u01b9\u01ba\5,\27\2\u01ba\u01bb"+
		"\7\34\2\2\u01bb\u01bc\b\30\1\2\u01bc\u01d6\3\2\2\2\u01bd\u01be\5,\27\2"+
		"\u01be\u01bf\7\35\2\2\u01bf\u01c0\b\30\1\2\u01c0\u01d6\3\2\2\2\u01c1\u01c2"+
		"\5,\27\2\u01c2\u01c3\7\25\2\2\u01c3\u01c4\b\30\1\2\u01c4\u01d6\3\2\2\2"+
		"\u01c5\u01c6\5,\27\2\u01c6\u01c7\7\26\2\2\u01c7\u01c8\b\30\1\2\u01c8\u01d6"+
		"\3\2\2\2\u01c9\u01ca\5,\27\2\u01ca\u01cb\7\27\2\2\u01cb\u01cc\b\30\1\2"+
		"\u01cc\u01d6\3\2\2\2\u01cd\u01ce\5,\27\2\u01ce\u01cf\7\30\2\2\u01cf\u01d0"+
		"\b\30\1\2\u01d0\u01d6\3\2\2\2\u01d1\u01d2\5,\27\2\u01d2\u01d3\7\31\2\2"+
		"\u01d3\u01d4\b\30\1\2\u01d4\u01d6\3\2\2\2\u01d5\u01ae\3\2\2\2\u01d5\u01b1"+
		"\3\2\2\2\u01d5\u01b5\3\2\2\2\u01d5\u01b9\3\2\2\2\u01d5\u01bd\3\2\2\2\u01d5"+
		"\u01c1\3\2\2\2\u01d5\u01c5\3\2\2\2\u01d5\u01c9\3\2\2\2\u01d5\u01cd\3\2"+
		"\2\2\u01d5\u01d1\3\2\2\2\u01d6/\3\2\2\2\n\65\u0095\u00e2\u00f0\u012c\u01a6"+
		"\u01ac\u01d5";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}
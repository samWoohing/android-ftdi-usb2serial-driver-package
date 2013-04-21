// Generated from D:\ShanSong\Android-FTDI-usb2serial\trunk\FootprintParser\grammar\FootprintParser.g4 by ANTLR 4.0

package cn.songshan99.FootprintParser;

import cn.songshan99.realicfootprint.ICFootprint;
import cn.songshan99.realicfootprint.ICFootprint.*;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface FootprintParserListener extends ParseTreeListener {
	void enterPad(FootprintParserParser.PadContext ctx);
	void exitPad(FootprintParserParser.PadContext ctx);

	void enterElement(FootprintParserParser.ElementContext ctx);
	void exitElement(FootprintParserParser.ElementContext ctx);

	void enterElement_hi_format(FootprintParserParser.Element_hi_formatContext ctx);
	void exitElement_hi_format(FootprintParserParser.Element_hi_formatContext ctx);

	void enterFlags(FootprintParserParser.FlagsContext ctx);
	void exitFlags(FootprintParserParser.FlagsContext ctx);

	void enterMeasure(FootprintParserParser.MeasureContext ctx);
	void exitMeasure(FootprintParserParser.MeasureContext ctx);

	void enterPin_hi_format(FootprintParserParser.Pin_hi_formatContext ctx);
	void exitPin_hi_format(FootprintParserParser.Pin_hi_formatContext ctx);

	void enterAttribute(FootprintParserParser.AttributeContext ctx);
	void exitAttribute(FootprintParserParser.AttributeContext ctx);

	void enterNumber(FootprintParserParser.NumberContext ctx);
	void exitNumber(FootprintParserParser.NumberContext ctx);

	void enterPin_newformat(FootprintParserParser.Pin_newformatContext ctx);
	void exitPin_newformat(FootprintParserParser.Pin_newformatContext ctx);

	void enterPin_1_6_3_format(FootprintParserParser.Pin_1_6_3_formatContext ctx);
	void exitPin_1_6_3_format(FootprintParserParser.Pin_1_6_3_formatContext ctx);

	void enterPin_oldformat(FootprintParserParser.Pin_oldformatContext ctx);
	void exitPin_oldformat(FootprintParserParser.Pin_oldformatContext ctx);

	void enterElement_1_7_format(FootprintParserParser.Element_1_7_formatContext ctx);
	void exitElement_1_7_format(FootprintParserParser.Element_1_7_formatContext ctx);

	void enterRelementdefs(FootprintParserParser.RelementdefsContext ctx);
	void exitRelementdefs(FootprintParserParser.RelementdefsContext ctx);

	void enterPin_1_7_format(FootprintParserParser.Pin_1_7_formatContext ctx);
	void exitPin_1_7_format(FootprintParserParser.Pin_1_7_formatContext ctx);

	void enterElementdefinitions(FootprintParserParser.ElementdefinitionsContext ctx);
	void exitElementdefinitions(FootprintParserParser.ElementdefinitionsContext ctx);

	void enterElement_1_3_4_format(FootprintParserParser.Element_1_3_4_formatContext ctx);
	void exitElement_1_3_4_format(FootprintParserParser.Element_1_3_4_formatContext ctx);

	void enterPad_hi_format(FootprintParserParser.Pad_hi_formatContext ctx);
	void exitPad_hi_format(FootprintParserParser.Pad_hi_formatContext ctx);

	void enterElement_oldformat(FootprintParserParser.Element_oldformatContext ctx);
	void exitElement_oldformat(FootprintParserParser.Element_oldformatContext ctx);

	void enterPad_1_7_format(FootprintParserParser.Pad_1_7_formatContext ctx);
	void exitPad_1_7_format(FootprintParserParser.Pad_1_7_formatContext ctx);

	void enterElement_newformat(FootprintParserParser.Element_newformatContext ctx);
	void exitElement_newformat(FootprintParserParser.Element_newformatContext ctx);

	void enterRelementdef(FootprintParserParser.RelementdefContext ctx);
	void exitRelementdef(FootprintParserParser.RelementdefContext ctx);

	void enterPad_newformat(FootprintParserParser.Pad_newformatContext ctx);
	void exitPad_newformat(FootprintParserParser.Pad_newformatContext ctx);

	void enterElementdefinition(FootprintParserParser.ElementdefinitionContext ctx);
	void exitElementdefinition(FootprintParserParser.ElementdefinitionContext ctx);
}
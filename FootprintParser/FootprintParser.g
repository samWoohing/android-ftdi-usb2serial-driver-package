grammar FootprintParser;

options {
	language=Java;
}

@header {
import java.util.HashMap;
import cn.songshan99.realicfootprint;
}

@lexer::header {
import java.util.HashMap;
}

@members {
/** Map variable name to Integer object holding value */
HashMap memory = new HashMap(); 
}

/* %start-doc pcbfile Element

@syntax
Element [SFlags "Desc" "Name" "Value" MX MY TX TY TDir TScale TSFlags] (
Element (NFlags "Desc" "Name" "Value" MX MY TX TY TDir TScale TNFlags) (
Element (NFlags "Desc" "Name" "Value" TX TY TDir TScale TNFlags) (
Element (NFlags "Desc" "Name" TX TY TDir TScale TNFlags) (
Element ("Desc" "Name" TX TY TDir TScale TNFlags) (
@ @ @ @dots{} contents @dots{}
)
@end syntax

@table @var
@item SFlags
Symbolic or numeric flags, for the element as a whole.
@item NFlags
Numeric flags, for the element as a whole.
@item Desc
The description of the element.  This is one of the three strings
which can be displayed on the screen.
@item Name
The name of the element, usually the reference designator.
@item Value
The value of the element.
@item MX MY
The location of the element's mark.  This is the reference point
for placing the element and its pins and pads.
@item TX TY
The upper left corner of the text (one of the three strings).
@item TDir
The relative direction of the text.  0 means left to right for
an unrotated element, 1 means up, 2 left, 3 down.
@item TScale
Size of the text, as a percentage of the ``default'' size of of the
font (the default font is about 40 mils high).  Default is 100 (40
mils).
@item TSFlags
Symbolic or numeric flags, for the text.
@item TNFlags
Numeric flags, for the text.
@end table

Elements may contain pins, pads, element lines, element arcs,
attributes, and (for older elements) an optional mark.  Note that
element definitions that have the mark coordinates in the element
line, only support pins and pads which use relative coordinates.  The
pin and pad coordinates are relative to the mark.  Element definitions
which do not include the mark coordinates in the element line, may
have a Mark definition in their contents, and only use pin and pad
definitions which use absolute coordinates.

%end-doc */

element returns [ICFootprint footprint]
@init {
	//$elementList = new ArrayList<PinOrPadOrDraftLine>();
	$footprint = new ICFootprint;
}
    : element_oldformat[$footprint]
    | element_1_3_4_format[$footprint]
    | element_newformat[$footprint]
    | element_1_7_format[$footprint]
    | element_hi_format[$footprint]
    ;

element_oldformat[ICFootprint footprint]
      /* element_flags, description, pcb-name,
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '(' STRING STRING measure measure INTEGER ')' '('
      {
        //set the element parameters
      }
      elementdefinitions[$footprint] ')'
      {
        //do the element centering, recalculate the bounding box
      }
    ;

element_1_3_4_format[ICFootprint footprint]
      /* element_flags, description, pcb-name,
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '(' INTEGER STRING STRING measure measure measure measure INTEGER ')' '('
      {
        //set the element parameters
      }
      elementdefinitions[$footprint] ')'
      {
        //do the element centering, recalculate the bounding box
      }
    ;

element_newformat[ICFootprint footprint]
      /* element_flags, description, pcb-name, value, 
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '(' INTEGER STRING STRING STRING measure measure measure measure INTEGER ')' '('
      {
        //set the element parameters
      }
      elementdefinitions[$footprint] ')'
      {
        //do the element centering, recalculate the bounding box
      }
    ;

element_1_7_format[ICFootprint footprint]
      /* element_flags, description, pcb-name, value, mark_x, mark_y,
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '(' INTEGER STRING STRING STRING measure measure
      measure measure number number INTEGER ')' '('
      {
        //set the element parameters
      }
      relementdefs[$footprint] ')'
      {
        //do the element centering, recalculate the bounding box
      }
    ;

element_hi_format[ICFootprint footprint]
      /* element_flags, description, pcb-name, value, mark_x, mark_y,
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '[' flags STRING STRING STRING measure measure
      measure measure number number flags ']' '('
      {
        //set the element parameters
      }
      relementdefs[$footprint] ')'
      {
		//Add returnvalues (pins, pads, ) from elementdefs to the element
        //do the element centering, recalculate the bounding box
      }
    ;

/* %start-doc pcbfile ElementLine

@syntax
ElementLine [X1 Y1 X2 Y2 Thickness]
ElementLine (X1 Y1 X2 Y2 Thickness)
@end syntax

@table @var
@item X1 Y1 X2 Y2
Coordinates of the endpoints of the line.  These are relative to the
Element's mark point for new element formats, or absolute for older
formats.
@item Thickness
The width of the silk for this line.
@end table

%end-doc */

/* %start-doc pcbfile ElementArc

@syntax
ElementArc [X Y Width Height StartAngle DeltaAngle Thickness]
ElementArc (X Y Width Height StartAngle DeltaAngle Thickness)
@end syntax

@table @var
@item X Y
Coordinates of the center of the arc.  These are relative to the
Element's mark point for new element formats, or absolute for older
formats.
@item Width Height
The width and height, from the center to the edge.  The bounds of the
circle of which this arc is a segment, is thus @math{2*Width} by
@math{2*Height}.
@item StartAngle
The angle of one end of the arc, in degrees.  In PCB, an angle of zero
points left (negative X direction), and 90 degrees points down
(positive Y direction).
@item DeltaAngle
The sweep of the arc.  This may be negative.  Positive angles sweep
counterclockwise.
@item Thickness
The width of the silk line which forms the arc.
@end table

%end-doc */

/* %start-doc pcbfile Mark

@syntax
Mark [X Y]
Mark (X Y)
@end syntax

@table @var
@item X Y
Coordinates of the Mark, for older element formats that don't have
the mark as part of the Element line.
@end table

%end-doc */

elementdefinitions [ICFootprint footprint] returns [int totalPadPinNum, int totalDraftLineNum]
@init {
	//$elementList = new ArrayList<PinOrPadOrDraftLine>();
	$totalPadPinNum = 0;
	$totalDraftLineNum = 0;
}
    : (elementdefinition{/*TODO: check the type of element, and insert to elementList*/})+
    ;
//    : elementdefinition
//    | elementdefinitions elementdefinition
//    ;

elementdefinition returns [PinOrPadOrDraftLine obj]
    : pinpad=pin_1_6_3_format{$obj = $pinpad.newpin}
    | pinpad=pin_newformat{$obj = $pinpad.newpin}
    | pinpad=pin_oldformat{$obj = $pinpad.newpin}
    | pinpad=pad_newformat{$obj = $pinpad.newpad}
    | pinpad=pad{$obj = $pinpad.newpad}
      /* x1, y1, x2, y2, thickness */
    | T_ELEMENTLINE '[' x1=measure y1=measure x2=measure y2=measure th=measure ']'
      {
        //this is new unit, use directly
		//create element line object
		$obj = new ElementLine();
      }
      /* x1, y1, x2, y2, thickness */
    | T_ELEMENTLINE '(' x1=measure y1=measure x2=measure y2=measure th=measure ')'
      {
        //this is old unit, need to convert to CentiMil
		//create element line object
		$obj = new ElementLine();
      }
      /* x, y, width, height, startangle, anglediff, thickness */
    | T_ELEMENTARC '[' x=measure y=measure w=measure h=measure strt_ang=number diff_ang=number th=measure ']'
      {
        //this is new unit, use directly
		//create element line object
		$obj = new ElementArc();
      }
      /* x, y, width, height, startangle, anglediff, thickness */
    | T_ELEMENTARC '(' x=measure y=measure w=measure h=measure strt_ang=number diff_ang=number th=measure ')'
      {
        //this is old unit, need to convert to CentiMil
		//create element line object
		$obj = new ElementArc();
      }
      /* x, y position */
    | T_MARK '[' x=measure y=measure ']'
      {
        //this is new unit, use directly
		//create mark object
		$obj = new Mark();
      }
    | T_MARK '(' x=measure y=measure ')'
      {
        //this is old unit, need to convert to CentiMil
		//create mark object
		$obj = new Mark();
      }
    | { /*do the attibute related works, prefer to ignore*/ } attribute
    ;

attribute
    : T_ATTRIBUTE '(' STRING STRING ')'
      {
        //attribute will not be used by gEDA/PCB, let's ignore it. no action needed
      }
    ;
    
relementdefs [ICFootprint footprint] returns [int totalPadPinNum, int totalDraftLineNum]
@init {
	//$elementList = new ArrayList<PinOrPadOrDraftLine>();
	$totalPadPinNum = 0;
	$totalDraftLineNum = 0;
}
    : (relementdef{/*TODO: check the type of element, and insert to elementList*/})+
    ;
//    | relementdefs relementdef
//    ;

relementdef returns [PinOrPadOrDraftLine obj]
    : pinpad=pin_1_7_format{$obj = $pinpad.newpin}
    | pinpad=pin_hi_format{$obj = $pinpad.newpin}
    | pinpad=pad_1_7_format{$obj = $pinpad.newpad}
    | pinpad=pad_hi_format{$obj = $pinpad.newpad}
      /* x1, y1, x2, y2, thickness */
    | T_ELEMENTLINE '[' x1=measure y1=measure x2=measure y2=measure th=measure ']'
      {
        //this is new unit, use directly
		//create element line object
		$obj = new ElementLine();
      }
    | T_ELEMENTLINE '(' x1=measure y1=measure x2=measure y2=measure th=measure ')'
      {
		//this is old unit, need to convert to CentiMil
        //create element line object
		$obj = new ElementLine();
      }
      /* x, y, width, height, startangle, anglediff, thickness */
    | T_ELEMENTARC '[' x=measure y=measure w=measure h=measure strt_ang=number diff_ang=number th=measure ']'
      {
        //this is new unit, use directly
		//create element line object
		$obj = new ElementArc();
      }
    | T_ELEMENTARC '(' x=measure y=measure w=measure h=measure strt_ang=number diff_ang=number th=measure ')'
      {
		//this is old unit, need to convert to CentiMil
        //create element line object
		$obj = new ElementArc();
      }
    | { /*do the attibute related works, prefer to ignore*/ } attribute
    ;

/* %start-doc pcbfile Pin

@syntax
Pin [rX rY Thickness Clearance Mask Drill "Name" "Number" SFlags]
Pin (rX rY Thickness Clearance Mask Drill "Name" "Number" NFlags)
Pin (aX aY Thickness Drill "Name" "Number" NFlags)
Pin (aX aY Thickness Drill "Name" NFlags)
Pin (aX aY Thickness "Name" NFlags)
@end syntax

@table @var
@item rX rY
coordinates of center, relative to the element's mark
@item aX aY
absolute coordinates of center.
@item Thickness
outer diameter of copper annulus
@item Clearance
add to thickness to get clearance diameter
@item Mask
diameter of solder mask opening
@item Drill
diameter of drill
@item Name
name of pin
@item Number
number of pin
@item SFlags
symbolic or numerical flags
@item NFlags
numerical flags only
@end table

%end-doc */

pin_hi_format returns [Pin newpin]
      /* x, y, thickness, clearance, mask, drilling hole, name,
         number, flags */
    : T_PIN '[' x=measure y=measure th=measure cl=measure mk=measure dr=measure nm=STRING pn=STRING fl=flags ']'
      {
        //convert name and pin number string
		//create a new pin
		$newpin = new Pin();
		//return it as return value. HOW TO USE THE RETURN VALUE? $e.value
      }
    ;
pin_1_7_format returns [Pin newpin]
      /* x, y, thickness, clearance, mask, drilling hole, name,
         number, flags */
    : T_PIN '(' x=measure y=measure th=measure cl=measure mk=measure dr=measure nm=STRING pn=STRING fl=INTEGER ')'
      {
        //create a new pin
		$newpin = new Pin();
		//return it as return value. HOW TO USE THE RETURN VALUE? $e.value
      }
    ;

pin_1_6_3_format returns [Pin newpin]
      /* x, y, thickness, drilling hole, name, number, flags */
    : T_PIN '(' x=measure y=measure th=measure dr=measure nm=STRING pn=STRING fl=INTEGER ')'
      {
        //create a new pin
		$newpin = new Pin();
		//return it as return value. HOW TO USE THE RETURN VALUE? $e.value
      }
    ;

pin_newformat returns [Pin newpin]
      /* x, y, thickness, drilling hole, name, flags */
    : T_PIN '(' x=measure y=measure th=measure dr=measure nm=STRING fl=INTEGER ')'
      {
        //create a new pin
		$newpin = new Pin();
		//return it as return value. HOW TO USE THE RETURN VALUE? $e.value
      }
    ;

pin_oldformat returns [Pin newpin]
      /* old format: x, y, thickness, name, flags
       * drilling hole is 40% of the diameter
       */
    : T_PIN '(' x=measure y=measure th=measure nm=STRING fl=INTEGER ')'
      {
        //create a new pin
		$newpin = new Pin();
		//return it as return value. HOW TO USE THE RETURN VALUE? $e.value
      }
    ;

/* %start-doc pcbfile Pad

@syntax
Pad [rX1 rY1 rX2 rY2 Thickness Clearance Mask "Name" "Number" SFlags]
Pad (rX1 rY1 rX2 rY2 Thickness Clearance Mask "Name" "Number" NFlags)
Pad (aX1 aY1 aX2 aY2 Thickness "Name" "Number" NFlags)
Pad (aX1 aY1 aX2 aY2 Thickness "Name" NFlags)
@end syntax

@table @var
@item rX1 rY1 rX2 rY2
Coordinates of the endpoints of the pad, relative to the element's
mark.  Note that the copper extends beyond these coordinates by half
the thickness.  To make a square or round pad, specify the same
coordinate twice.
@item aX1 aY1 aX2 aY2
Same, but absolute coordinates of the endpoints of the pad.
@item Thickness
width of the pad.
@item Clearance
add to thickness to get clearance width.
@item Mask
width of solder mask opening.
@item Name
name of pin
@item Number
number of pin
@item SFlags
symbolic or numerical flags
@item NFlags
numerical flags only
@end table

%end-doc */

pad_hi_format returns [Pad newpad]
      /* x1, y1, x2, y2, thickness, clearance, mask, name , pad number, flags */
    : T_PAD '[' x1=measure y1=measure x2=measure y2=measure th=measure cl=measure mk=measure nm=STRING pn=STRING fl=flags ']'
      {
        //create a new pad
		$newpad = new Pad();
		//return it as return value. HOW TO USE THE RETURN VALUE? $e.value
      }
    ;

pad_1_7_format returns [Pad newpad]
      /* x1, y1, x2, y2, thickness, clearance, mask, name , pad number, flags */
    : T_PAD '(' x1=measure y1=measure x2=measure y2=measure th=measure cl=measure mk=measure nm=STRING pn=STRING fl=INTEGER ')'
      {
        //create a new pad
		$newpad = new Pad();
		//return it as return value. HOW TO USE THE RETURN VALUE? $e.value
      }
    ;

pad_newformat returns [Pad newpad]
      /* x1, y1, x2, y2, thickness, name , pad number, flags */
    : T_PAD '(' x1=measure y1=measure x2=measure y2=measure th=measure nm=STRING pn=STRING fl=INTEGER ')'
      {
        //create a new pad
		$newpad = new Pad();
		//return it as return value. HOW TO USE THE RETURN VALUE? $e.value
      }
    ;

pad returns [Pad newpad]
      /* x1, y1, x2, y2, thickness, name and flags */
    : T_PAD '(' x1=measure y1=measure x2=measure y2=measure th=measure nm=STRING fl=INTEGER ')'
      {
        //create a new pad
		$newpad = new Pad();
		//return it as return value. HOW TO USE THE RETURN VALUE? $e.value
      }
    ;

flags returns [int value]  
	: INTEGER { $value = Integer.valueof($INTEGER.text);/*converter to integer as return value*/ }
    | STRING  { /*converter to integer as return value, depends on the function in ICFootprint*/ }
    ;

//symbols
//    : symbol
//    | symbols symbol
//    ;
    
number returns [float value]
    : FLOATING  { $value = Float.valueof($FLOATING.text);/*convert to float and return the value*/ }
    | INTEGER { $value = Float.valueof($INTEGER.text);/*convert to float and return the value*/ }
    ;

measure returns [float value]
    /* Default unit (no suffix) is cmil */
    : r=number  { $value = $r.value;/*default unit is CentiMil*/ }
    | r=number T_UMIL { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.toCentiMil.UNIT_UMIL);/*umil*/ }
    | r=number T_CMIL { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.toCentiMil.UNIT_CMIL);/*centimil*/ }
    | r=number T_MIL  { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.toCentiMil.UNIT_MIL);/*mil*/ }
    | r=number T_IN { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.toCentiMil.UNIT_INCH);/*inch*/ }
    | r=number T_NM { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.toCentiMil.UNIT_NM);/*nm*/ }
    | r=number T_UM { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.toCentiMil.UNIT_UM);/*um*/ }
    | r=number T_MM { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.toCentiMil.UNIT_MM);/*mm*/ }
    | r=number T_M  { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.toCentiMil.UNIT_M);/*m*/ }
    | r=number T_KM { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.toCentiMil.UNIT_KM);/*km*/ }
    ;
    
HEX :         '0''x'('0'..'9'|'a'..'f'|'A'..'F')+;//[0-9a-fA-F]+;
INTEGER :       ('+'|'-')?(('1'..'9')('0'..'9')*)|'0'; //[+-]?([1-9][0-9]*|0);
FLOATING :     (INTEGER)'.'('0'..'9')*;
STRINGCHAR :   ( ~('"'|'\\'|'\n'|'\r')|('\\'.)) ;//([^"\n\r\\]|\\.);×ªÒå×Ö·û?
STRING:		'"'(STRINGCHAR)*'"';

//basic token definitions
T_PIN:	"Pin";
T_PAD:	"Pad";
T_ELEMENTLINE: "ElementLine";
T_ELEMENTARC:	"ElementArc";
T_ELEMENT:		"Element";
T_MARK:			"Mark";
T_ATTRIBUTE		"Attribute";

//unit token definitions. Not very useful though...
T_NM:	"nm";
T_UM:	"um";
T_MM	"mm";
T_M		"m";
T_KM	"km";
T_UMIL	"umil";
T_CMIL	"cmil";
T_MIL	"mil";
T_IN	"in";
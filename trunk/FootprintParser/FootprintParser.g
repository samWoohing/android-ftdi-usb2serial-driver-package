grammar FootprintParser;

options {
	language=Java;
}

@header {
import java.util.HashMap;
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

element
    : element_oldformat
    | element_1_3_4_format
    | element_newformat
    | element_1_7_format
    | element_hi_format
    ;

element_oldformat
      /* element_flags, description, pcb-name,
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '(' STRING STRING measure measure INTEGER ')' '('
      {
        yyElement = CreateNewElement(yyData, yyElement, yyFont, NoFlags(),
          $3, $4, NULL, OU ($5), OU ($6), $7, 100, NoFlags(), false);
        free ($3);
        free ($4);
        pin_num = 1;
      }
      elementdefinitions ')'
      {
        SetElementBoundingBox(yyData, yyElement, yyFont);
      }
    ;

element_1_3_4_format
      /* element_flags, description, pcb-name,
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '(' INTEGER STRING STRING measure measure measure measure INTEGER ')' '('
      {
        yyElement = CreateNewElement(yyData, yyElement, yyFont, OldFlags($3),
          $4, $5, NULL, OU ($6), OU ($7), IV ($8), IV ($9), OldFlags($10), false);
        free ($4);
        free ($5);
        pin_num = 1;
      }
      elementdefinitions ')'
      {
        SetElementBoundingBox(yyData, yyElement, yyFont);
      }
    ;

element_newformat
      /* element_flags, description, pcb-name, value, 
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '(' INTEGER STRING STRING STRING measure measure measure measure INTEGER ')' '('
      {
        yyElement = CreateNewElement(yyData, yyElement, yyFont, OldFlags($3),
          $4, $5, $6, OU ($7), OU ($8), IV ($9), IV ($10), OldFlags($11), false);
        free ($4);
        free ($5);
        free ($6);
        pin_num = 1;
      }
      elementdefinitions ')'
      {
        SetElementBoundingBox(yyData, yyElement, yyFont);
      }
    ;

element_1_7_format
      /* element_flags, description, pcb-name, value, mark_x, mark_y,
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '(' INTEGER STRING STRING STRING measure measure
      measure measure number number INTEGER ')' '('
      {
        yyElement = CreateNewElement(yyData, yyElement, yyFont, OldFlags($3),
          $4, $5, $6, OU ($7) + OU ($9), OU ($8) + OU ($10),
          $11, $12, OldFlags($13), false);
        yyElement->MarkX = OU ($7);
        yyElement->MarkY = OU ($8);
        free ($4);
        free ($5);
        free ($6);
      }
      relementdefs ')'
      {
        SetElementBoundingBox(yyData, yyElement, yyFont);
      }
    ;

element_hi_format
      /* element_flags, description, pcb-name, value, mark_x, mark_y,
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '[' flags STRING STRING STRING measure measure
      measure measure number number flags ']' '('
      {
        yyElement = CreateNewElement(yyData, yyElement, yyFont, $3,
          $4, $5, $6, NU ($7) + NU ($9), NU ($8) + NU ($10),
          $11, $12, $13, false);
        yyElement->MarkX = NU ($7);
        yyElement->MarkY = NU ($8);
        free ($4);
        free ($5);
        free ($6);
      }
      relementdefs ')'
      {
        SetElementBoundingBox(yyData, yyElement, yyFont);
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

elementdefinitions
    : elementdefinition+
    ;
//    : elementdefinition
//    | elementdefinitions elementdefinition
//    ;

elementdefinition
    : pin_1_6_3_format
    | pin_newformat
    | pin_oldformat
    | pad_newformat
    | pad
      /* x1, y1, x2, y2, thickness */
    | T_ELEMENTLINE '[' measure measure measure measure measure ']'
      {
        CreateNewLineInElement(yyElement, NU ($3), NU ($4), NU ($5), NU ($6), NU ($7));
      }
      /* x1, y1, x2, y2, thickness */
    | T_ELEMENTLINE '(' measure measure measure measure measure ')'
      {
        CreateNewLineInElement(yyElement, OU ($3), OU ($4), OU ($5), OU ($6), OU ($7));
      }
      /* x, y, width, height, startangle, anglediff, thickness */
    | T_ELEMENTARC '[' measure measure measure measure number number measure ']'
      {
        CreateNewArcInElement(yyElement, NU ($3), NU ($4), NU ($5), NU ($6), $7, $8, NU ($9));
      }
      /* x, y, width, height, startangle, anglediff, thickness */
    | T_ELEMENTARC '(' measure measure measure measure number number measure ')'
      {
        CreateNewArcInElement(yyElement, OU ($3), OU ($4), OU ($5), OU ($6), $7, $8, OU ($9));
      }
      /* x, y position */
    | T_MARK '[' measure measure ']'
      {
        yyElement->MarkX = NU ($3);
        yyElement->MarkY = NU ($4);
      }
    | T_MARK '(' measure measure ')'
      {
        yyElement->MarkX = OU ($3);
        yyElement->MarkY = OU ($4);
      }
    | { attr_list = & yyElement->Attributes; } attribute
    ;

attribute
    : T_ATTRIBUTE '(' STRING STRING ')'
      {
        CreateNewAttribute (attr_list, $3, $4 ? $4 : (char *)"");
        free ($3);
        free ($4);
      }
    ;
    
relementdefs
    : relementdef+
    ;
//    | relementdefs relementdef
//    ;

relementdef
    : pin_1_7_format
    | pin_hi_format
    | pad_1_7_format
    | pad_hi_format
      /* x1, y1, x2, y2, thickness */
    | T_ELEMENTLINE '[' measure measure measure measure measure ']'
      {
        CreateNewLineInElement(yyElement, NU ($3) + yyElement->MarkX,
          NU ($4) + yyElement->MarkY, NU ($5) + yyElement->MarkX,
          NU ($6) + yyElement->MarkY, NU ($7));
      }
    | T_ELEMENTLINE '(' measure measure measure measure measure ')'
      {
        CreateNewLineInElement(yyElement, OU ($3) + yyElement->MarkX,
          OU ($4) + yyElement->MarkY, OU ($5) + yyElement->MarkX,
          OU ($6) + yyElement->MarkY, OU ($7));
      }
      /* x, y, width, height, startangle, anglediff, thickness */
    | T_ELEMENTARC '[' measure measure measure measure number number measure ']'
      {
        CreateNewArcInElement(yyElement, NU ($3) + yyElement->MarkX,
          NU ($4) + yyElement->MarkY, NU ($5), NU ($6), $7, $8, NU ($9));
      }
    | T_ELEMENTARC '(' measure measure measure measure number number measure ')'
      {
        CreateNewArcInElement(yyElement, OU ($3) + yyElement->MarkX,
          OU ($4) + yyElement->MarkY, OU ($5), OU ($6), $7, $8, OU ($9));
      }
    | { attr_list = & yyElement->Attributes; } attribute
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

pin_hi_format
      /* x, y, thickness, clearance, mask, drilling hole, name,
         number, flags */
    : T_PIN '[' measure measure measure measure measure measure STRING STRING flags ']'
      {
        CreateNewPin(yyElement, NU ($3) + yyElement->MarkX,
          NU ($4) + yyElement->MarkY, NU ($5), NU ($6), NU ($7), NU ($8), $9,
          $10, $11);
        free ($9);
        free ($10);
      }
    ;
pin_1_7_format
      /* x, y, thickness, clearance, mask, drilling hole, name,
         number, flags */
    : T_PIN '(' measure measure measure measure measure measure STRING STRING INTEGER ')'
      {
        CreateNewPin(yyElement, OU ($3) + yyElement->MarkX,
          OU ($4) + yyElement->MarkY, OU ($5), OU ($6), OU ($7), OU ($8), $9,
          $10, OldFlags($11));
        free ($9);
        free ($10);
      }
    ;

pin_1_6_3_format
      /* x, y, thickness, drilling hole, name, number, flags */
    : T_PIN '(' measure measure measure measure STRING STRING INTEGER ')'
      {
        CreateNewPin(yyElement, OU ($3), OU ($4), OU ($5), 2*GROUNDPLANEFRAME,
          OU ($5) + 2*MASKFRAME, OU ($6), $7, $8, OldFlags($9));
        free ($7);
        free ($8);
      }
    ;

pin_newformat
      /* x, y, thickness, drilling hole, name, flags */
    : T_PIN '(' measure measure measure measure STRING INTEGER ')'
      {
        char  p_number[8];

        sprintf(p_number, "%d", pin_num++);
        CreateNewPin(yyElement, OU ($3), OU ($4), OU ($5), 2*GROUNDPLANEFRAME,
          OU ($5) + 2*MASKFRAME, OU ($6), $7, p_number, OldFlags($8));

        free ($7);
      }
    ;

pin_oldformat
      /* old format: x, y, thickness, name, flags
       * drilling hole is 40% of the diameter
       */
    : T_PIN '(' measure measure measure STRING INTEGER ')'
      {
        Coord hole = OU ($5) * DEFAULT_DRILLINGHOLE;
        char  p_number[8];

          /* make sure that there's enough copper left */
        if (OU ($5) - hole < MIN_PINORVIACOPPER && 
          OU ($5) > MIN_PINORVIACOPPER)
          hole = OU ($5) - MIN_PINORVIACOPPER;

        sprintf(p_number, "%d", pin_num++);
        CreateNewPin(yyElement, OU ($3), OU ($4), OU ($5), 2*GROUNDPLANEFRAME,
          OU ($5) + 2*MASKFRAME, hole, $6, p_number, OldFlags($7));
        free ($6);
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

pad_hi_format
      /* x1, y1, x2, y2, thickness, clearance, mask, name , pad number, flags */
    : T_PAD '[' measure measure measure measure measure measure measure STRING STRING flags ']'
      {
        CreateNewPad(yyElement, NU ($3) + yyElement->MarkX,
          NU ($4) + yyElement->MarkY,
          NU ($5) + yyElement->MarkX,
          NU ($6) + yyElement->MarkY, NU ($7), NU ($8), NU ($9),
          $10, $11, $12);
        free ($10);
        free ($11);
      }
    ;

pad_1_7_format
      /* x1, y1, x2, y2, thickness, clearance, mask, name , pad number, flags */
    : T_PAD '(' measure measure measure measure measure measure measure STRING STRING INTEGER ')'
      {
        CreateNewPad(yyElement,OU ($3) + yyElement->MarkX,
          OU ($4) + yyElement->MarkY, OU ($5) + yyElement->MarkX,
          OU ($6) + yyElement->MarkY, OU ($7), OU ($8), OU ($9),
          $10, $11, OldFlags($12));
        free ($10);
        free ($11);
      }
    ;

pad_newformat
      /* x1, y1, x2, y2, thickness, name , pad number, flags */
    : T_PAD '(' measure measure measure measure measure STRING STRING INTEGER ')'
      {
        CreateNewPad(yyElement,OU ($3),OU ($4),OU ($5),OU ($6),OU ($7), 2*GROUNDPLANEFRAME,
          OU ($7) + 2*MASKFRAME, $8, $9, OldFlags($10));
        free ($8);
        free ($9);
      }
    ;

pad
      /* x1, y1, x2, y2, thickness, name and flags */
    : T_PAD '(' measure measure measure measure measure STRING INTEGER ')'
      {
        char    p_number[8];

        sprintf(p_number, "%d", pin_num++);
        CreateNewPad(yyElement,OU ($3),OU ($4),OU ($5),OU ($6),OU ($7), 2*GROUNDPLANEFRAME,
          OU ($7) + 2*MASKFRAME, $8,p_number, OldFlags($9));
        free ($8);
      }
    ;

flags   : INTEGER { $$ = OldFlags($1); }
    | STRING  { $$ = string_to_flags ($1, yyerror); }
    ;

//symbols
//    : symbol
//    | symbols symbol
//    ;
    
number
    : FLOATING  { $$ = $1; }
    | INTEGER { $$ = $1; }
    ;

measure
    /* Default unit (no suffix) is cmil */
    : number  { do_measure(&$$, $1, MIL_TO_COORD ($1) / 100.0, 0); }
    | number T_UMIL { M ($$, $1, MIL_TO_COORD ($1) / 100000.0); }
    | number T_CMIL { M ($$, $1, MIL_TO_COORD ($1) / 100.0); }
    | number T_MIL  { M ($$, $1, MIL_TO_COORD ($1)); }
    | number T_IN { M ($$, $1, INCH_TO_COORD ($1)); }
    | number T_NM { M ($$, $1, MM_TO_COORD ($1) / 1000000.0); }
    | number T_UM { M ($$, $1, MM_TO_COORD ($1) / 1000.0); }
    | number T_MM { M ($$, $1, MM_TO_COORD ($1)); }
    | number T_M  { M ($$, $1, MM_TO_COORD ($1) * 1000.0); }
    | number T_KM { M ($$, $1, MM_TO_COORD ($1) * 1000000.0); }
    ;
    
HEX :         '0''x'('0'..'9'|'a'..'f'|'A'..'F');//[0-9a-fA-F]+;
INTEGER :       ('+'|'-')?('1'..'9')('0'..'9')*|'0'; //[+-]?([1-9][0-9]*|0);
FLOATING :     {INTEGER}'.'('0'..'9')*;
STRINGCHAR :   ( ~('"'|'\\'|'\n'|'\r')|'\\'.) ;//([^"\n\r\\]|\\.);×ªÒå×Ö·û
grammar FootprintParser;

options {
	language=Java;
}



@header {
package cn.songshan99.FootprintParser;

import cn.songshan99.realicfootprint.ICFootprint;
import cn.songshan99.realicfootprint.ICFootprint.*;
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
	
	$footprint = new ICFootprint();
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
    
    : T_ELEMENT '(' tx=STRING ty=STRING tdir=measure tscale=measure tflag=INTEGER ')' '('
      //unit is mil. old format
      {
        
        ICText ictext = new ICText();
	ictext.aX=Float.valueOf($tx.text)*100;//TODO: should fetch the substring?
	ictext.aY=Float.valueOf($ty.text)*100;
	ictext.dir = $tdir.value;
	ictext.scale = $tscale.value;
	ictext.flags = Integer.valueOf($tflag.text);
	$footprint.setmICText(ictext);
    
        Mark mark = new Mark(0, 0);
    
        $footprint.setmICText(ictext);
        $footprint.setmMark(mark);
        $footprint.setFlags(0);
        $footprint.setmDesc("");
        $footprint.setmName("");
        $footprint.setmValue("");
      }
      elementdefinitions[$footprint] ')'//do the element centering, recalculate the bounding box
      {
        $footprint.centerTheFootprint();
      }
    ;

element_1_3_4_format[ICFootprint footprint]
      /* element_flags, description, pcb-name,
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '(' flag=INTEGER desc=STRING name=STRING tx=measure ty=measure tdir=measure tscale=measure tflag=INTEGER ')' '('//set the element parameters
      //unit is mil. old format
      {
        String dc, nm, vl;
        dc = $desc.text.substring(1, $desc.text.length() - 2);
        nm = $name.text.substring(1, $name.text.length() - 2);
        vl = "";
        
        ICText ictext = new ICText();
        ictext.aX=$tx.value*100;
	ictext.aY=$ty.value*100;
	ictext.dir = $tdir.value;
	ictext.scale = $tscale.value;
	ictext.flags = Integer.valueOf($tflag.text);
    
        Mark mark = new Mark(0, 0);
    
        $footprint.setmICText(ictext);
        $footprint.setmMark(mark);
        $footprint.setFlags(Integer.valueOf($flag.text));
        $footprint.setmDesc(dc);
        $footprint.setmName(nm);
        $footprint.setmValue(vl);
      }
      elementdefinitions[$footprint] ')'//do the element centering, recalculate the bounding box
      {
        $footprint.centerTheFootprint();
      }
    ;

element_newformat[ICFootprint footprint]
      /* element_flags, description, pcb-name, value, 
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '(' flag=INTEGER desc=STRING name=STRING value=STRING tx=measure ty=measure tdir=measure tscale=measure tflag=INTEGER ')' '('//set the element parameters
      //unit is mil. old format
      {
        String dc, nm, vl;
        dc = $desc.text.substring(1, $desc.text.length() - 2);
        nm = $name.text.substring(1, $name.text.length() - 2);
        vl = $value.text.substring(1, $value.text.length() - 2);
        
        ICText ictext = new ICText();
        ictext.aX=$tx.value*100;
	ictext.aY=$ty.value*100;
	ictext.dir = $tdir.value;
	ictext.scale = $tscale.value;
	ictext.flags = Integer.valueOf($tflag.text);
    
        Mark mark = new Mark(0, 0);
    
        $footprint.setmICText(ictext);
        $footprint.setmMark(mark);
        $footprint.setFlags(Integer.valueOf($flag.text));
        $footprint.setmDesc(dc);
        $footprint.setmName(nm);
        $footprint.setmValue(vl);
      }
      elementdefinitions[$footprint] ')'//do the element centering, recalculate the bounding box
      {
        $footprint.centerTheFootprint();
      }
    ;

element_1_7_format[ICFootprint footprint]
      /* element_flags, description, pcb-name, value, mark_x, mark_y,
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '(' flag=INTEGER desc=STRING name=STRING value=STRING mx=measure my=measure
      tx=measure ty=measure tdir=number tscale=number tflag=INTEGER ')' '('//set the element parameters
      //unit is mil. old format
      {
        String dc, nm, vl;
        dc = $desc.text.substring(1, $desc.text.length() - 2);
        nm = $name.text.substring(1, $name.text.length() - 2);
        vl = $value.text.substring(1, $value.text.length() - 2);
        
        ICText ictext = new ICText();
        ictext.aX=$tx.value*100;
	ictext.aY=$ty.value*100;
	ictext.dir = $tdir.value;
	ictext.scale = $tscale.value;
	ictext.flags = Integer.valueOf($tflag.text);
    
        Mark mark = new Mark($mx.value*100, $my.value*100);
    
        $footprint.setmICText(ictext);
        $footprint.setmMark(mark);
        $footprint.setFlags(Integer.valueOf($flag.text));
        $footprint.setmDesc(dc);
        $footprint.setmName(nm);
        $footprint.setmValue(vl);
      }
      relementdefs[$footprint] ')'//do the element centering, recalculate the bounding box
      {
        $footprint.centerTheFootprint();
      }
    ;

element_hi_format[ICFootprint footprint]
      /* element_flags, description, pcb-name, value, mark_x, mark_y,
       * text_x, text_y, text_direction, text_scale, text_flags
       */
    : T_ELEMENT '[' flag=flags desc=STRING name=STRING value=STRING mx=measure my=measure
      tx=measure ty=measure tdir=number tscale=number tflag=flags ']' '('//set the element parameters
      //unit is 1/100 mil. new format
      {
        String dc, nm, vl;
        dc = $desc.text.substring(1, $desc.text.length() - 2);
        nm = $name.text.substring(1, $name.text.length() - 2);
        vl = $value.text.substring(1, $value.text.length() - 2);
        
        ICText ictext = new ICText();
        ictext.aX=$tx.value;
	ictext.aY=$ty.value;
	ictext.dir = $tdir.value;
	ictext.scale = $tscale.value;
	ictext.flags = Integer.valueOf($tflag.text);
    
        Mark mark = new Mark($mx.value, $my.value);
    
        $footprint.setmICText(ictext);
        $footprint.setmMark(mark);
        $footprint.setFlags(Integer.valueOf($flag.text));
        $footprint.setmDesc(dc);
        $footprint.setmName(nm);
        $footprint.setmValue(vl);
      }
      relementdefs[$footprint] ')'//Add returnvalues (pins, pads, ) from elementdefs to the element
        //do the element centering, recalculate the bounding box
      {
	$footprint.centerTheFootprint();
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
	$totalPadPinNum = 0;
	$totalDraftLineNum = 0;
}
    : (val=elementdefinition[$footprint]
       {
        $footprint.addPinOrPadOrDraftLine($val.obj);
       
       })+/*check the type of element, and insert to elementList*/
    ;
//    : elementdefinition
//    | elementdefinitions elementdefinition
//    ;

elementdefinition [ICFootprint footprint] returns [PinOrPadOrDraftLine obj]
    : npin=pin_1_6_3_format[$footprint]{$obj = $npin.newpin;}
    | npin1=pin_newformat[$footprint]{$obj = $npin1.newpin;}
    | npin2=pin_oldformat[$footprint]{$obj = $npin2.newpin;}
    | npad=pad_newformat[$footprint]{$obj = $npad.newpad;}
    | npad1=pad[$footprint]{$obj = $npad1.newpad;}
      /* x1, y1, x2, y2, thickness */
    | T_ELEMENTLINE '[' x1=measure y1=measure x2=measure y2=measure th=measure ']'
      //unit is 1/100 mil. new format
      {
        float mx,my;
        mx=$footprint.getmMark().getaX();
        my=$footprint.getmMark().getaY();
        $obj = new ElementLine($x1.value+mx, $y1.value+my, $x2.value+mx, $y2.value+my, $th.value);
      }
      /* x1, y1, x2, y2, thickness */
    | T_ELEMENTLINE '(' x1=measure y1=measure x2=measure y2=measure th=measure ')'
      //unit is mil. old format. 
      {
        float mx,my;
        mx=$footprint.getmMark().getaX();
        my=$footprint.getmMark().getaY();
	$obj = new ElementLine($x1.value*100+mx, $y1.value*100+my, $x2.value*100+mx, $y2.value*100+my, $th.value*100);
      }
      /* x, y, width, height, startangle, anglediff, thickness */
    | T_ELEMENTARC '[' x=measure y=measure w=measure h=measure strt_ang=number diff_ang=number th=measure ']'
      //unit is 1/100 mil. new format
      {
        float mx,my;
        mx=$footprint.getmMark().getaX();
        my=$footprint.getmMark().getaY();
        $obj = new ElementArc($x.value+mx, $y.value+my, $w.value, $h.value, $strt_ang.value, $diff_ang.value, $th.value);
      }
      /* x, y, width, height, startangle, anglediff, thickness */
    | T_ELEMENTARC '(' x=measure y=measure w=measure h=measure strt_ang=number diff_ang=number th=measure ')'
      //unit is mil. old format
      {
        float mx,my;
        mx=$footprint.getmMark().getaX();
        my=$footprint.getmMark().getaY();
        $obj = new ElementArc($x.value*100+mx, $y.value*100+my, $w.value*100, $h.value*100, $strt_ang.value, $diff_ang.value, $th.value*100);
      }
      /* x, y position */
    | T_MARK '[' x=measure y=measure ']'
      //unit is 1/100 mil. new format
      {
        $obj = new Mark($x.value, $y.value);
      }
    | T_MARK '(' x=measure y=measure ')'//this is old unit, need to convert to CentiMil
      //unit is mil. old format
      {
        $obj = new Mark($x.value*100, $y.value*100);
      }
    | { } attribute/*do the attibute related works, prefer to ignore*/ 
    ;

attribute
    : T_ATTRIBUTE '(' STRING STRING ')'//attribute will not be used by gEDA/PCB, let's ignore it. no action needed
      //unit is mil. old format
      {
        
      }
    ;
    
relementdefs [ICFootprint footprint] returns [int totalPadPinNum, int totalDraftLineNum]
@init {
	$totalPadPinNum = 0;
	$totalDraftLineNum = 0;
}
    : (val=relementdef[$footprint]
       {
        $footprint.addPinOrPadOrDraftLine($val.obj);
       
       })+/*TODO: check the type of element, and insert to elementList*/
    ;
//    | relementdefs relementdef
//    ;

relementdef [ICFootprint footprint] returns [PinOrPadOrDraftLine obj]
    : npin=pin_1_7_format[$footprint]{$obj = $npin.newpin;}
    | npin1=pin_hi_format[$footprint]{$obj = $npin1.newpin;}
    | npad=pad_1_7_format[$footprint]{$obj = $npad.newpad;}
    | npad1=pad_hi_format[$footprint]{$obj = $npad1.newpad;}
      /* x1, y1, x2, y2, thickness */
    | T_ELEMENTLINE '[' x1=measure y1=measure x2=measure y2=measure th=measure ']'
      //unit is 1/100 mil. new format
      {
        float mx,my;
        mx=$footprint.getmMark().getaX();
        my=$footprint.getmMark().getaY();
        $obj = new ElementLine($x1.value+mx, $y1.value+my, $x2.value+mx, $y2.value+my, $th.value);
      }
    | T_ELEMENTLINE '(' x1=measure y1=measure x2=measure y2=measure th=measure ')'
      //unit is mil. old format
      {
        float mx,my;
        mx=$footprint.getmMark().getaX();
        my=$footprint.getmMark().getaY();
	$obj = new ElementLine($x1.value*100+mx, $y1.value*100+my, $x2.value*100+mx, $y2.value*100+my, $th.value*100);
      }
      /* x, y, width, height, startangle, anglediff, thickness */
    | T_ELEMENTARC '[' x=measure y=measure w=measure h=measure strt_ang=number diff_ang=number th=measure ']'
      {
        float mx,my;
        mx=$footprint.getmMark().getaX();
        my=$footprint.getmMark().getaY();
        $obj = new ElementArc($x.value+mx, $y.value+my, $w.value, $h.value, $strt_ang.value, $diff_ang.value, $th.value);
      }
    | T_ELEMENTARC '(' x=measure y=measure w=measure h=measure strt_ang=number diff_ang=number th=measure ')'
      //unit is mil. old format
      {
        float mx,my;
        mx=$footprint.getmMark().getaX();
        my=$footprint.getmMark().getaY();
        $obj = new ElementArc($x.value*100+mx, $y.value*100+my, $w.value*100, $h.value*100, $strt_ang.value, $diff_ang.value, $th.value*100);
      }
    | {  } attribute/*do the attibute related works, prefer to ignore*/
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

pin_hi_format [ICFootprint footprint] returns [Pin newpin]
      /* x, y, thickness, clearance, mask, drilling hole, name,
         number, flags */
          //convert name and pin number string
    //create a new pin
    //return it as return value. HOW TO USE THE RETURN VALUE? $e.value
    : T_PIN '[' x=measure y=measure th=measure cl=measure mk=measure dr=measure nm=STRING pn=STRING fl=flags ']'
      //unit is 1/100 mil. new format
      {
        $newpin = new Pin(  $x.value,
                            $y.value,
                            $th.value,
                            $cl.value,
                            $mk.value,
                            $dr.value,
                            $nm.text.substring(1, $nm.text.length() - 2),
                            $pn.text.substring(1, $pn.text.length() - 2),
                            $fl.value);	
      }
    ;
pin_1_7_format [ICFootprint footprint] returns [Pin newpin]
      /* x, y, thickness, clearance, mask, drilling hole, name,
         number, flags */
    : T_PIN '(' x=measure y=measure th=measure cl=measure mk=measure dr=measure nm=STRING pn=STRING fl=INTEGER ')'
      //unit is mil. old format
      {
        $newpin = new Pin(  $x.value*100,
                            $y.value*100,
                            $th.value*100,
                            $cl.value*100,
                            $mk.value*100,
                            $dr.value*100,
                            $nm.text.substring(1, $nm.text.length() - 2),
                            $pn.text.substring(1, $pn.text.length() - 2),
                            Integer.valueOf($fl.text));
      }
    ;

pin_1_6_3_format [ICFootprint footprint] returns [Pin newpin]
      /* x, y, thickness, drilling hole, name, number, flags */
      //return it as return value. HOW TO USE THE RETURN VALUE? $e.value
      //create a new pin
    : T_PIN '(' x=measure y=measure th=measure dr=measure nm=STRING pn=STRING fl=INTEGER ')'
      //unit is mil. old format
      {
        $newpin = new Pin(  $x.value*100,
                            $y.value*100,
                            $th.value*100,
                            0.0f,
                            0.0f,
                            $dr.value*100,
                            $nm.text.substring(1, $nm.text.length() - 2),
                            $pn.text.substring(1, $pn.text.length() - 2),
                            Integer.valueOf($fl.text));	
      }
    ;

pin_newformat [ICFootprint footprint] returns [Pin newpin]
      /* x, y, thickness, drilling hole, name, flags */
    : T_PIN '(' x=measure y=measure th=measure dr=measure nm=STRING fl=INTEGER ')'
      //unit is mil. old format
      {
        $newpin = new Pin(  $x.value*100,
                            $y.value*100,
                            $th.value*100,
                            0.0f,
                            0.0f,
                            $dr.value*100,
                            $nm.text.substring(1, $nm.text.length() - 2),
                            "",
                            Integer.valueOf($fl.text));
      }
    ;

pin_oldformat [ICFootprint footprint] returns [Pin newpin]
      /* old format: x, y, thickness, name, flags
       * drilling hole is 40% of the diameter
       */
    : T_PIN '(' x=measure y=measure th=measure nm=STRING fl=INTEGER ')'
      //unit is mil. old format
      {
        $newpin = new Pin(  $x.value*100,
                            $y.value*100,
                            $th.value*100,
                            0.0f,
                            0.0f,
                            $th.value*40,
                            $nm.text.substring(1, $nm.text.length() - 2),
                            "",
                            Integer.valueOf($fl.text));
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

pad_hi_format [ICFootprint footprint] returns [Pad newpad]
      /* x1, y1, x2, y2, thickness, clearance, mask, name , pad number, flags */
    : T_PAD '[' x1=measure y1=measure x2=measure y2=measure th=measure cl=measure mk=measure nm=STRING pn=STRING fl=flags ']'
      //unit is 1/100 mil. new format
      {
        $newpad = new Pad(  $x1.value,
                            $y1.value,
                            $x2.value,
                            $y2.value,
                            $th.value,
                            $cl.value,
                            $mk.value,
                            $nm.text.substring(1, $nm.text.length() - 2),
                            $pn.text.substring(1, $pn.text.length() - 2),
                            $fl.value);
      }
    ;

pad_1_7_format [ICFootprint footprint] returns [Pad newpad]
      /* x1, y1, x2, y2, thickness, clearance, mask, name , pad number, flags */
    : T_PAD '(' x1=measure y1=measure x2=measure y2=measure th=measure cl=measure mk=measure nm=STRING pn=STRING fl=INTEGER ')'
      //unit is mil. old format
      {
        $newpad = new Pad(  $x1.value*100,
                            $y1.value*100,
                            $x2.value*100,
                            $y2.value*100,
                            $th.value*100,
                            $cl.value*100,
                            $mk.value*100,
                            $nm.text.substring(1, $nm.text.length() - 2),
                            $pn.text.substring(1, $pn.text.length() - 2),
                            Integer.valueOf($fl.text));
      }
    ;

pad_newformat [ICFootprint footprint] returns [Pad newpad]
      /* x1, y1, x2, y2, thickness, name , pad number, flags */
    : T_PAD '(' x1=measure y1=measure x2=measure y2=measure th=measure nm=STRING pn=STRING fl=INTEGER ')'
      //unit is mil. old format
      {
        $newpad = new Pad(  $x1.value*100,
                            $y1.value*100,
                            $x2.value*100,
                            $y2.value*100,
                            $th.value*100,
                            0.0f,
                            0.0f,
                            $nm.text.substring(1, $nm.text.length() - 2),
                            $pn.text.substring(1, $pn.text.length() - 2),
                            Integer.valueOf($fl.text));
      }
    ;

pad [ICFootprint footprint] returns [Pad newpad]
      /* x1, y1, x2, y2, thickness, name and flags */
    : T_PAD '(' x1=measure y1=measure x2=measure y2=measure th=measure nm=STRING fl=INTEGER ')'
      //unit is mil. old format
      {
        $newpad = new Pad(  $x1.value*100,
                            $y1.value*100,
                            $x2.value*100,
                            $y2.value*100,
                            $th.value*100,
                            0.0f,
                            0.0f,
                            $nm.text.substring(1, $nm.text.length() - 2),
                            "",
                            Integer.valueOf($fl.text));
      }
    ;

flags returns [int value]  
    : INTEGER { $value = Integer.valueOf($INTEGER.text); }/*converter to integer as return value*/
    | st=STRING  { $value = ICFootprint.stringToFlags($st.text.substring(1, $st.text.length() - 2)) }/*TODO: converter to integer as return value, depends on the function in ICFootprint*/
    ;

//symbols
//    : symbol
//    | symbols symbol
//    ;
    
number returns [float value]
    : FLOATING  { $value = Float.valueOf($FLOATING.text); }/*convert to float and return the value*/
    | INTEGER { $value = Float.valueOf($INTEGER.text); }/*convert to float and return the value*/
    ;

measure returns [float value]
    /* Default unit (no suffix) is cmil */
    : r=number  { $value = $r.value;}/*default unit is CentiMil*/ 
    | r=number T_UMIL { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.UNIT_UMIL);/*umil*/ }
    | r=number T_CMIL { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.UNIT_CMIL);/*centimil*/ }
    | r=number T_MIL  { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.UNIT_MIL);/*mil*/ }
    | r=number T_IN { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.UNIT_INCH);/*inch*/ }
    | r=number T_NM { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.UNIT_NM);/*nm*/ }
    | r=number T_UM { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.UNIT_UM);/*um*/ }
    | r=number T_MM { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.UNIT_MM);/*mm*/ }
    | r=number T_M  { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.UNIT_M);/*m*/ }
    | r=number T_KM { $value = ICFootprint.CentiMil.toCentiMil($r.value,ICFootprint.CentiMil.UNIT_KM);/*km*/ }
    ;

LINE_COMMENT: '#' ~[\r\n]* ('\r'? '\n' | EOF) -> channel(HIDDEN)
            ;

WS: ( ' ' | '\t' | '\r' | '\n' )+ -> channel(HIDDEN)
    ;

HEX :         '0''x'('0'..'9'|'a'..'f'|'A'..'F')+;//[0-9a-fA-F]+;
INTEGER :       ('+'|'-')?('1'..'9' '0'..'9'*)|'0'; //[+-]?([1-9][0-9]*|0);
FLOATING :     (INTEGER)'.'('0'..'9')*;

//STRINGCHAR :   (~["\\\n\r]|('\\'.)) ;//([^"\n\r\\]|\\.);
//STRING:		'"'(STRINGCHAR)*'"';

STRING:  '"' ( STRESC | ~('\\'|'"') )* '"'
    ;

STRESC: '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')//escape sequence of the string.
        ;

//basic token definitions
T_PIN:  'Pin';
T_PAD:  'Pad';
T_ELEMENTLINE: 'ElementLine';
T_ELEMENTARC: 'ElementArc';
T_ELEMENT:    'Element';
T_MARK:     'Mark';
T_ATTRIBUTE:   'Attribute';

//unit token definitions. Not very useful though...
T_NM: 'nm';
T_UM: 'um';
T_MM:  'mm';
T_M:   'm';
T_KM:  'km';
T_UMIL:  'umil';
T_CMIL:  'cmil';
T_MIL: 'mil';
T_IN:  'in';
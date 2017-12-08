package ch.weiss.terminal;

import java.util.Arrays;
import java.util.List;

import ch.weiss.check.Check;

public class Color
{
  private final EscCode foreground;
  private final EscCode background;
  
  public static final Color BLACK = new Color(EscCode.sgr(30), EscCode.sgr(40));
  public static final Color RED = new Color(EscCode.sgr(31), EscCode.sgr(41));
  public static final Color GREEN = new Color(EscCode.sgr(32), EscCode.sgr(42));
  public static final Color YELLOW = new Color(EscCode.sgr(33), EscCode.sgr(43));
  public static final Color BLUE = new Color(EscCode.sgr(34), EscCode.sgr(44));
  public static final Color MAGENTA = new Color(EscCode.sgr(35), EscCode.sgr(45));
  public static final Color CYAN = new Color(EscCode.sgr(36), EscCode.sgr(46));
  public static final Color WHITE = new Color(EscCode.sgr(37), EscCode.sgr(47));

  public static final Color BRIGHT_BLACK = new Color(EscCode.sgr(1, 30), EscCode.sgr(100));
  public static final Color BRIGHT_RED = new Color(EscCode.sgr(1, 31), EscCode.sgr(101));
  public static final Color BRIGHT_GREEN = new Color(EscCode.sgr(1, 32), EscCode.sgr(102));
  public static final Color BRIGHT_YELLOW = new Color(EscCode.sgr(1, 33), EscCode.sgr(103));
  public static final Color BRIGHT_BLUE = new Color(EscCode.sgr(1, 34), EscCode.sgr(104));
  public static final Color BRIGHT_MAGENTA = new Color(EscCode.sgr(1, 35), EscCode.sgr(105));
  public static final Color BRIGHT_CYAN = new Color(EscCode.sgr(1, 36), EscCode.sgr(106));
  public static final Color BRIGHT_WHITE = new Color(EscCode.sgr(1, 37), EscCode.sgr(107));
  
  public static final List<Color> STANDARD_COLORS = Arrays.asList(BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE); 

  private Color(EscCode foregroundEscCode, EscCode backgroundEscCode)
  {
    this.foreground = foregroundEscCode;
    this.background = backgroundEscCode;
  }
  
  public Color(int red, int green, int blue)
  {
    this(EscCode.sgr(38,2,red,green,blue), 
         EscCode.sgr(48,2,red,green, blue));
    rangeCheck(red, "red");
    rangeCheck(green, "green");
    rangeCheck(blue, "blue");
  }

  private void rangeCheck(int value, String parameter)
  {
    Check.parameter(parameter).withValue(value).isInRange(0, 255);
  }

  EscCode foreground()
  {
    return foreground;
  }

  EscCode background()
  {
    return background;
  }
}
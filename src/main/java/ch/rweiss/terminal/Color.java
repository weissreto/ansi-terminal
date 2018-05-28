package ch.rweiss.terminal;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ch.rweiss.check.Check;

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
  public static final List<Color> BRIGHT_STANDARD_COLORS = Arrays.asList(BRIGHT_BLACK, BRIGHT_RED, BRIGHT_GREEN, BRIGHT_YELLOW, BRIGHT_BLUE, BRIGHT_MAGENTA, BRIGHT_CYAN, BRIGHT_WHITE); 

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
  
  @Override
  public boolean equals(Object obj)
  {
    if (obj == this)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (obj.getClass() != Color.class)
    {
      return false;
    }
    Color other = (Color)obj;
    return Objects.equals(foreground, other.foreground) && 
           Objects.equals(background,  other.background);
  }
  
  @Override
  public int hashCode()
  {
    return foreground.hashCode();
  }
  
  private static void rangeCheck(int value, String parameter)
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

  public static Color standardColor(int colorId)
  {
    switch(colorId)
    {
      case 0:
        return Color.BLACK;
      case 1:
        return Color.RED;
      case 2:
        return Color.GREEN;
      case 3:
        return Color.YELLOW;
      case 4:
        return Color.BLUE;
      case 5:
        return Color.MAGENTA;
      case 6:
        return Color.CYAN;
      case 7:
        return Color.WHITE;
      default:
        throw new IllegalArgumentException("Color "+colorId+" is not a standard color id");
    }
  }
  
  public static Color brightStandardColor(int colorId)
  {
    switch(colorId)
    {
      case 0:
        return Color.BRIGHT_BLACK;
      case 1:
        return Color.BRIGHT_RED;
      case 2:
        return Color.BRIGHT_GREEN;
      case 3:
        return Color.BRIGHT_YELLOW;
      case 4:
        return Color.BRIGHT_BLUE;
      case 5:
        return Color.BRIGHT_MAGENTA;
      case 6:
        return Color.BRIGHT_CYAN;
      case 7:
        return Color.BRIGHT_WHITE;
      default:
        throw new IllegalArgumentException("Color "+colorId+" is not a bright standard color id");
    }
  }

}
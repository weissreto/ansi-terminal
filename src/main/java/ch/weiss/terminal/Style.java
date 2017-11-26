package ch.weiss.terminal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.weiss.check.Check;

public class Style
{
  private final List<EscCode> escCodes;
  static final EscCode RESET = EscCode.sgr(0);
  
  private Style(FontStyle fontStyle, Color color, Color backgroundColor)
  {
    List<EscCode> codes = new ArrayList<>();
    codes.add(RESET);
    if (fontStyle != null)
    {
      codes.add(fontStyle.fontStyle());
    }
    if (color != null)
    {
      codes.add(color.foreground());      
    }
    if (backgroundColor != null)
    {
      codes.add(backgroundColor.background());
    }
    escCodes = Collections.unmodifiableList(codes);
  }

  public static StyleBuilder create()
  {
    return new StyleBuilder();
  }
  
  List<EscCode> style()
  {
    return escCodes;
  }
  
  public static final class StyleBuilder
  {
    private Color color;
    private Color backgroundColor;
    private FontStyle fontStyle;
    
    public StyleBuilder withColor(Color color)
    {
      Check.parameter("color").value(color).isNotNull();

      this.color = color;
      return this;
    }

    public StyleBuilder withBackgroundColor(Color backgroundColor)
    {
      Check.parameter("backgroundColor").value(backgroundColor).isNotNull();
      
      this.backgroundColor = backgroundColor;
      return this;
    }
    
    public StyleBuilder withFontStyle(FontStyle fontStyle)
    {
      Check.parameter("fontStyle").value(fontStyle).isNotNull();
      
      this.fontStyle = fontStyle;
      return this;
    }
    
    public Style toStyle()
    {
      return new Style(fontStyle, color, backgroundColor);
    }
  }
}

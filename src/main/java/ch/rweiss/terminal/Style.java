package ch.rweiss.terminal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.rweiss.check.Check;

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
  
  @Override
  public boolean equals(Object obj)
  {
    if (this==obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (obj.getClass() != Style.class)
    {
      return false;
    }
    Style other = (Style)obj;
    return escCodes.equals(other.escCodes);     
  }
  
  @Override
  public int hashCode()
  {
    return escCodes.hashCode();
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
    
    public StyleBuilder withColor(@SuppressWarnings("hiding") Color color)
    {
      Check.parameter("color").withValue(color).isNotNull();

      this.color = color;
      return this;
    }

    public StyleBuilder withBackgroundColor(@SuppressWarnings("hiding") Color backgroundColor)
    {
      Check.parameter("backgroundColor").withValue(backgroundColor).isNotNull();
      
      this.backgroundColor = backgroundColor;
      return this;
    }
    
    public StyleBuilder withFontStyle(@SuppressWarnings("hiding") FontStyle fontStyle)
    {
      Check.parameter("fontStyle").withValue(fontStyle).isNotNull();
      
      this.fontStyle = fontStyle;
      return this;
    }
    
    public Style toStyle()
    {
      return new Style(fontStyle, color, backgroundColor);
    }
  }
}

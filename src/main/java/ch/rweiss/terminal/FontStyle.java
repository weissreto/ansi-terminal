package ch.rweiss.terminal;

public class FontStyle
{
  public static final FontStyle BOLD = new FontStyle(EscCode.sgr(1));
  public static final FontStyle UNDERLINE = new FontStyle(EscCode.sgr(4));
  public static final FontStyle NEGATIVE = new FontStyle(EscCode.sgr(7));

  private final EscCode fontStyle;
  
  private FontStyle(EscCode fontStyle)
  {
    this.fontStyle = fontStyle;
  }
  
  EscCode fontStyle()
  {
    return fontStyle;
  }  
}

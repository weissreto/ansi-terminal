package ch.rweiss.terminal.internal.buffer;

import ch.rweiss.terminal.AnsiTerminal;
import ch.rweiss.terminal.Color;
import ch.rweiss.terminal.FontStyle;

public class TerminalCharacter
{
  public static final TerminalCharacter EMPTY = new TerminalCharacter();
  private final Color color;
  private final Color backgroundColor;
  private final FontStyle fontStyle;
  private final char character;
  
  private TerminalCharacter()
  {
    this(' ', null, null, null);
  }

  public TerminalCharacter(char character, Color color, Color backgroundColor, FontStyle fontStyle)
  {
    this.character = character;
    this.color = color;
    this.backgroundColor = backgroundColor;
    this.fontStyle = fontStyle;
  }

  public void writeTo(AnsiTerminal ansiTerminal)
  {
    ansiTerminal.reset();
    if (fontStyle != null)
    {
      ansiTerminal.fontStyle(fontStyle);
    }
    if (backgroundColor != null)
    {
      ansiTerminal.backgroundColor(backgroundColor);
    }
    if (color != null)
    {
      ansiTerminal.color(color);
    }
    ansiTerminal.write(character);
  }

  char dump()
  {
    return character;
  }

  Color color()
  {
    return color;
  }
  
  Color backgroundColor()
  {
    return backgroundColor;
  }

  public FontStyle fontStyle()
  {
    return fontStyle;
  }
  
  @Override
  public String toString()
  {
    return ""+dump();
  }

}

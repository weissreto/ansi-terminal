package ch.rweiss.terminal.graphics;

import ch.rweiss.terminal.AnsiTerminal;
import ch.rweiss.terminal.Position;

public class GraphicsExample
{
  public static void simple()
  {
    AnsiTerminal terminal = AnsiTerminal.get();
    
    terminal.clear().screen();
    Position position = terminal.cursor().maxPosition();
    
    Rectangle rectangle = new Rectangle(Point.ORIGIN, position.column(), position.line());    
    terminal.graphics().drawRectangle(rectangle);
  }
}

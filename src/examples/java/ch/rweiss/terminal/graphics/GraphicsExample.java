package ch.rweiss.terminal.graphics;

import ch.rweiss.terminal.AnsiTerminal;
import ch.rweiss.terminal.Position;

public class GraphicsExample
{
  public static void main(String[] args)
  {
    AnsiTerminal terminal = AnsiTerminal.get();
    
    terminal.clear().screen();
    Position position = terminal.cursor().maxPosition();
    
    Rectangle rectangle = new Rectangle(Point.ORIGIN, position.column()-1, position.line()-1);    
    terminal.graphics().drawRectangle(rectangle);
    terminal.cursor().down();
  }
}

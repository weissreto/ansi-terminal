package ch.rweiss.terminal;

public class PositionExample
{
  public static void simple()
  {
    AnsiTerminal terminal = AnsiTerminal.get();
    String text = "Center of the screen";
    Position maxScreenPosition = terminal.cursor().maxPosition();
    terminal
        .cursor().position(maxScreenPosition.line()/2, (maxScreenPosition.column()-text.length())/2)
        .write(text);
  }
}

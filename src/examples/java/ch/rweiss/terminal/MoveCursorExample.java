package ch.rweiss.terminal;

public class MoveCursorExample
{
  public static void simple()
  {
    AnsiTerminal terminal = AnsiTerminal.get();
    terminal
      .cursor().up(5)
      .write("H")
      .cursor().down()
      .write("e")
      .cursor().down()
      .write("l")
      .cursor().down()
      .write("l")
      .cursor().down()
      .write("o");
  }
}

package ch.rweiss.terminal;

public class MoveCursorExample
{
  public static void main(String[] args)
  {
    AnsiTerminal terminal = AnsiTerminal.get();
    terminal
      .cursor().down(5)
      .write("H")
      .cursor().up()
      .write("e")
      .cursor().up()
      .write("l")
      .cursor().up()
      .write("l")
      .cursor().up()
      .write("o")
      .cursor().down(5);
  }
}

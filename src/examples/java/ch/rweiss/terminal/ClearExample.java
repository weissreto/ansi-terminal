package ch.rweiss.terminal;

public class ClearExample
{
  public static void simple()
  {
    AnsiTerminal terminal = AnsiTerminal.get();
    terminal.clear().screen();
    terminal.write("This is an empty screen!");
  }
}

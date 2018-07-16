package ch.rweiss.terminal;

public class OffScreenExample
{
  public static void main(String[] args)
  {
    AnsiTerminal terminal = AnsiTerminal.get();
    terminal.offScreen().on();
    try
    {
      terminal.write("This is written to the off screen buffer.");
      terminal.write("It is not visible on the terminal screen until the method syncToScreen() is called.");
      terminal.offScreen().syncToScreen();
    }
    finally
    {
      terminal.offScreen().off();
    }
  }
}

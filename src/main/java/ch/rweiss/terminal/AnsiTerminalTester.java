package ch.rweiss.terminal;

public class AnsiTerminalTester
{
  public static String dumpOffScreenBuffer()
  {
    return AnsiTerminal
        .get()
        .offScreen()
        .offScreenBuffer
        .dump();
  }
}

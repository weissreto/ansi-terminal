package ch.rweiss.terminal;

import ch.rweiss.terminal.internal.InputReader;
import ch.rweiss.terminal.internal.buffer.TerminalBuffer;

public class Helper
{
  public static void setOffScreenBufferFor(AnsiTerminal terminal, TerminalBuffer testee)
  {
    terminal.offScreen().on(testee);
  }

  public static InputReader getInputReaderOf(AnsiTerminal terminal)
  {
    return terminal.reader;
  }
}

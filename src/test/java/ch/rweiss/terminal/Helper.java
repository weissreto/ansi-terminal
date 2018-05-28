package ch.rweiss.terminal;

import ch.rweiss.terminal.internal.buffer.TerminalBuffer;

public class Helper
{
  public static void setOffScreenBufferFor(AnsiTerminal terminal, TerminalBuffer testee)
  {
    terminal.offScreen().on(testee);
  }
}

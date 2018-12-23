package ch.rweiss.terminal;

import ch.rweiss.terminal.internal.TerminalInput;
import ch.rweiss.terminal.internal.buffer.TerminalBuffer;

public class Helper
{
  public static void setOffScreenBufferFor(AnsiTerminal terminal, TerminalBuffer testee)
  {
    terminal.offScreen().on(testee);
  }

  public static TerminalInput getInputReaderOf(AnsiTerminal terminal)
  {
    return terminal.terminalInput;
  }
}

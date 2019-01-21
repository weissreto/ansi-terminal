package ch.rweiss.terminal;

import ch.rweiss.terminal.internal.buffer.TerminalBuffer;

public class Helper
{
  public static TerminalBuffer getOffScreenBuffer(AnsiTerminal terminal)
  {
    return terminal.offScreen().offScreenBuffer;
  }
}

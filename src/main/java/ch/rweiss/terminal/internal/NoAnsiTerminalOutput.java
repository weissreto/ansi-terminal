package ch.rweiss.terminal.internal;

import ch.rweiss.terminal.EscCode;

public class NoAnsiTerminalOutput implements TerminalOutput
{
  @Override
  public void print(String text)
  {
    System.out.print(text);
  }

  @Override
  public void print(char text)
  {
    System.out.print(text);
  }

  @Override
  public void print(long value)
  {
    System.out.print(value);
  }

  @Override
  public void println()
  {
    System.out.println();
  }

  @Override
  public void print(EscCode command)
  {
    // EscCode cannot be printed on non ansi terminal 
  }

}

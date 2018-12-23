package ch.rweiss.terminal.internal;

import ch.rweiss.terminal.EscCode;

public class AnsiTerminalOutput implements TerminalOutput
{
  @Override
  public void print(String text)
  {
    System.out.print(text);
  }

  @Override
  public void print(char character)
  {
    System.out.print(character);    
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
    System.out.print(command.escCode());
  }
}

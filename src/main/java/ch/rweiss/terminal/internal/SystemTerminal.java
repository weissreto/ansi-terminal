package ch.rweiss.terminal.internal;

import java.io.IOException;

import ch.rweiss.terminal.EscCode;

public class SystemTerminal implements Terminal
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

  @Override
  public int read() throws IOException
  {
    return System.in.read();
  }

}

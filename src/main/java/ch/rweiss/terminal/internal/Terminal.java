package ch.rweiss.terminal.internal;

import ch.rweiss.terminal.EscCode;

public interface Terminal
{
  void print(String text);
  void print(char text);
  void print(long value);
  void println();
  void print(EscCode command);
}

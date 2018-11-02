package ch.rweiss.terminal.internal;

import ch.rweiss.terminal.EscCode;

public class EscCodeParser
{
  public static final char ESCAPE = '\033';
  public static final char CSI_START_TOKEN = '[';
  public static final char CSI_PARAMETER_DELIMITER = ';';

  private StringBuilder command = new StringBuilder();
  
  static EscCodeParser start(char ch)
  {
    if (ch == ESCAPE)
    {
      return new EscCodeParser();
    }
    return null;
  }

  private EscCodeParser()
  {
  }

  void putNext(char ch)
  {
    command.append(ch);
  }
  
  boolean isNotComplete()
  {
    if (command.length() < 2)
    {
      return true;
    }
    if (command.charAt(0) == CSI_START_TOKEN)
    {
      char lastChar = command.charAt(command.length()-1);
      return lastChar == CSI_PARAMETER_DELIMITER || 
             Character.isDigit(lastChar);
    }
    return false;
  }

  EscCode toEscCode()
  {
    return EscCode.esc(command.toString());
  }
}
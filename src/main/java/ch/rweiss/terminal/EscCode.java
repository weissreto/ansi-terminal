package ch.rweiss.terminal;

import java.util.Arrays;
import java.util.Objects;

import ch.rweiss.check.Check;
import ch.rweiss.terminal.internal.EscCodeParser;

public class EscCode
{
  private static final char ESCAPE = EscCodeParser.ESCAPE;
  private static final char CSI_START_TOKEN = EscCodeParser.CSI_START_TOKEN;
  private static final char CSI_PARAMETER_DELIMITER = EscCodeParser.CSI_PARAMETER_DELIMITER;
  private static final char SGR_COMMAND = 'm';
  private static final int[] EMPTY_ARGUMENTS = new int[0];
  
  private final String escCode;
  
  private EscCode(String escCode)
  {
    this.escCode = escCode;
  }

  public static EscCode esc(String command)
  {
    Check.parameter("command").withValue(command).isNotNull();
    return new EscCode(ESCAPE+command);
  }
  
  public static EscCode csi(char command, int... arguments)
  {
    StringBuilder builder = new StringBuilder();
    boolean first = true;
    for (int argument : arguments)
    {
      if (!first)
      {
        builder.append(CSI_PARAMETER_DELIMITER);
      }
      first = false;
      builder.append(argument);
    }
    builder.append(command);

    return csi(builder.toString());
  }
  
  public static EscCode csi(String command)
  {
    Check.parameter(command).withValue(command).isNotBlank();
    return esc(CSI_START_TOKEN+command);
  }
  
  public static EscCode sgr(int... arguments)
  {
    return csi(SGR_COMMAND, arguments);
  }

  public String escCode()
  {
    return escCode;
  }
  
  @Override
  public String toString()
  {    
    StringBuilder builder = new StringBuilder();
    for (int pos = 0; pos < escCode.length(); pos++)
    {
      int ch = escCode.charAt(pos);
      if (builder.length() > 0)
      {
        builder.append(" ");
      }
      if (ch == ESCAPE)
      {
        builder.append("ESC");
      }
      else
      {
        builder.append((char)ch);
      }
    }
    return builder.toString();
  }
  
  public boolean isCsi()
  {
    // ESCACPE + CSI_START_TOKEN + COMMAND  = 3 characters at least for a csi escape sequence
    return escCode.length() >= 3 && escCode.charAt(1) == CSI_START_TOKEN; 
  }
  
  public boolean isSgr()
  {
    return isCsi() && csiCommand() == SGR_COMMAND;
  }

  public char csiCommand()
  {
    if (!isCsi())
    {
      throw new IllegalStateException(escCode+" is not a csi escape code");
    }
    return escCode.charAt(escCode.length()-1);
  }
  
  public int[] csiArguments()
  {
    if (!isCsi())
    {
      throw new IllegalStateException(escCode+" is not a csi escape code");
    }
    String argumentStr = escCode.substring(2, escCode.length()-1);
    if (argumentStr.isEmpty())
    {
      return EMPTY_ARGUMENTS;
    }
    String[] arguments = argumentStr.split(""+CSI_PARAMETER_DELIMITER);
    return Arrays.asList(arguments).stream().mapToInt(Integer::parseInt).toArray();    
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if (obj == this)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (obj.getClass() != EscCode.class)
    {
      return false;
    }
    EscCode other = (EscCode)obj;
    return Objects.equals(this.escCode, other.escCode);
  }
  
  @Override
  public int hashCode()
  {
    return escCode.hashCode();
  }

}

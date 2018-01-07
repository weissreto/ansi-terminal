package ch.weiss.terminal;

import java.io.IOException;
import java.io.InputStream;

import ch.weiss.check.Check;

public class EscCode
{
  private static final char ESCAPE = '\033';
  private static final char CSI_START_TOKEN = '[';
  private static final char CSI_PARAMETER_DELIMITER = ';';
  private static final char SGR_COMMAND = 'm';
  
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

  String escCode()
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
  
  boolean isCsi()
  {
    // ESCACPE + CSI_START_TOKEN + COMMAND  = 3 characters at least for a csi escape sequence
    return escCode.length() >= 3 && escCode.charAt(1) == CSI_START_TOKEN; 
  }

  char csiCommand()
  {
    if (!isCsi())
    {
      throw new IllegalStateException(escCode+" is not a csi escape code");
    }
    return escCode.charAt(escCode.length()-1);
  }
  
  int csiArgument(int index)
  {
    if (!isCsi())
    {
      throw new IllegalStateException(escCode+" is not a csi escape code");
    }
    String argumentStr = escCode.substring(2, escCode.length()-1);
    String[] arguments = argumentStr.split(""+CSI_PARAMETER_DELIMITER);
    Check.parameter("index").withValue(index).isPositive().isLessThan(arguments.length);
    return Integer.parseInt(arguments[index]);
  }

  public static EscCode readFrom(InputStream in) throws IOException
  {
    StringBuilder builder = new StringBuilder();
    int ch;
    do
    {
      ch = in.read();
      if (ch == EscCode.ESCAPE)
      {
        builder.append((char)ch);
      }
      else if (builder.length()>0)
      {
        builder.append((char)ch);
      }
    } while (builder.length()==0 || ch != 'R');
    return new EscCode(builder.toString());
  }
}

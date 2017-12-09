package ch.weiss.terminal;

import ch.weiss.check.Check;

public class EscCode
{
  private static final String ESCAPE = "\033";
  
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
  
  public static EscCode csi(String command, int... arguments)
  {
    Check.parameter("command").withValue(command).isNotNull();

    StringBuilder builder = new StringBuilder();
    builder.append("[");
    boolean first = true;
    for (int argument : arguments)
    {
      if (!first)
      {
        builder.append(";");
      }
      first = false;
      builder.append(argument);
    }
    builder.append(command);

    return esc(builder.toString());
  }
  
  public static EscCode sgr(int... arguments)
  {
    return csi("m", arguments);
  }

  String escCode()
  {
    return escCode;
  }
}

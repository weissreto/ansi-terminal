package ch.weiss.terminal;

import java.lang.reflect.Method;

public abstract class AbstractManualTest
{
  protected AnsiTerminal terminal = AnsiTerminal.get();
  private static final Style TITLE = Style.create().withColor(Color.BRIGHT_GREEN).toStyle();
  private static final Style SUBTITLE = Style.create().withColor(Color.BRIGHT_GREEN).withFontStyle(FontStyle.UNDERLINE).toStyle();
  
  protected void title(String title)
  {
    title = title.toUpperCase();
    terminal.newLine().newLine().style(TITLE);
    terminal.write("  ");
    for (int pos = 0; pos < title.length(); pos++)
    {
      terminal.write(title.charAt(pos));
      terminal.write(" ");
    }
    terminal.newLine();
    terminal.write("  ");
    for (int pos = 0; pos < title.length()-1; pos++)
    {
      terminal.write("==");
    }
    terminal.write("=");
    terminal.newLine().reset();
  }
  
  protected void subtitle(String title)
  {
    StringBuilder builder = new StringBuilder();
    for (int pos = 0; pos < title.length(); pos++)
    {
      if (pos == 0)
      {
        builder.append(Character.toUpperCase(title.charAt(pos)));
      }
      else
      {
        if (Character.isUpperCase(title.charAt(pos)))
        {
          builder.append(" ");
        }
        builder.append(title.charAt(pos));
      }
    }
    terminal.newLine().newLine().style(SUBTITLE).write(builder.toString()).newLine().newLine().reset();
  }
  
  protected AbstractManualTest clear()
  {
    terminal.clear().cursor().position(1,1);
    return this;
  }
  
  protected void sleep(int millis)
  {
    try
    {
      Thread.sleep(millis);
    }
    catch (InterruptedException e)
    {    
    }
  }
  
  
  public void main() throws Exception
  {
    String name = getClass().getSimpleName();
    if (name.startsWith("ManualTest"))
    {
      name = name.substring("ManualTest".length(), name.length());
    }
    title(name);
    
    for (Method method : getClass().getDeclaredMethods())
    {
      if (method.getReturnType() == Void.TYPE  && method.getParameterTypes().length == 0)
      {
        subtitle(method.getName());
        method.invoke(this);
      } 
    }
  }
}

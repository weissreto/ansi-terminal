package ch.weiss.terminal;

import java.io.PrintStream;

import ch.weiss.check.Check;
import ch.weiss.terminal.graphics.Graphics;
import ch.weiss.terminal.windows.AnsiTerminalForWindows;

public class AnsiTerminal 
{
  private static final AnsiTerminal INSTANCE = new AnsiTerminal();
  private static final PrintStream OUT = System.out;
  private final FontStyleInline fontStyle = new FontStyleInline();
  private final ForegroundColor color = new ForegroundColor();
  private final BackgroundColor backgroundColor = new BackgroundColor();
  private final Cursor cursor = new Cursor();;
  
  private AnsiTerminal()
  {
    AnsiTerminalForWindows.enableVirtualTerminalProcessing();
  }

  public static AnsiTerminal get()
  {
    return INSTANCE;
  }
  
  public AnsiTerminal write(String text)
  {
    Check.parameter("text").value(text).isNotNull();
    
    OUT.print(text);
    return this;
  }
  
  public AnsiTerminal write(char ch)
  {
    OUT.print(ch);
    return this;
  }
  
  public AnsiTerminal write(long value)
  {
    OUT.print(value);
    return this;
  }
  
  public AnsiTerminal write(EscCode command)
  {
    Check.parameter("command").value(command).isNotNull();
    
    OUT.print(command.escCode());
    return this;
  }
  
  public AnsiTerminal newLine()
  {
    OUT.println();
    return this;
  }
  
  public AnsiTerminal reset()
  {
    return write(Style.RESET);
  }
  
  public AnsiTerminal style(Style style)
  {
    Check.parameter("style").value(style).isNotNull();

    style.style().forEach(this::write);
    return this;
  }
  
  public AnsiTerminal clear()
  {
    return write(EscCode.CLEAR_SCREEN);
  }
  
  public ForegroundColor color()
  {
    return color;
  }
  
  public AnsiTerminal color(Color color)
  {
    Check.parameter("color").value(color).isNotNull();

    return write(color.foreground());
  }
  
  public BackgroundColor backgroundColor()
  {
    return backgroundColor;
  }
  
  public AnsiTerminal backgroundColor(Color color)
  {
    Check.parameter("color").value(color).isNotNull();
 
    return write(color.background());
  }
  
  public FontStyleInline fontStyle()
  {
    return fontStyle ;
  }
  
  public AnsiTerminal fontStyle(FontStyle fontStyle)
  {
    Check.parameter("fontStyle").value(fontStyle).isNotNull();

    return write(fontStyle.fontStyle());
  }
  
  public Cursor cursor()
  {
    return cursor ;
  }
    
  public class ForegroundColor
  {
    public AnsiTerminal black()
    {
      return color(Color.BLACK);
    }
    
    public AnsiTerminal red()
    {
      return color(Color.RED);
    }
    
    public AnsiTerminal green()
    {
      return color(Color.GREEN);
    }
    
    public AnsiTerminal yellow()
    {
      return color(Color.YELLOW);
    }
    
    public AnsiTerminal blue()
    {
      return color(Color.BLUE);
    }
    
    public AnsiTerminal magenta()
    {
      return color(Color.MAGENTA);
    }
    
    public AnsiTerminal cyan()
    {
      return color(Color.CYAN);
    }
    
    public AnsiTerminal white()
    {
      return color(Color.WHITE);
    }

    public AnsiTerminal brightBlack()
    {
      return color(Color.BRIGHT_BLACK);
    }

    public AnsiTerminal brightRed()
    {
      return color(Color.BRIGHT_RED);
    }
    
    public AnsiTerminal brightGreen()
    {
      return color(Color.BRIGHT_GREEN);
    }
    
    public AnsiTerminal brightYellow()
    {
      return color(Color.BRIGHT_YELLOW);
    }
    
    public AnsiTerminal brightBlue()
    {
      return color(Color.BRIGHT_BLUE);
    }
    
    public AnsiTerminal brightMagenta()
    {
      return color(Color.BRIGHT_MAGENTA);
    }
    
    public AnsiTerminal brightCyan()
    {
      return color(Color.BRIGHT_CYAN);
    }
    
    public AnsiTerminal brightWhite()
    {
      return color(Color.BRIGHT_WHITE);
    }
    
    public AnsiTerminal rgb(int red, int green, int blue)
    {
      return color(new Color(red, green, blue));
    }
  }

  public class BackgroundColor
  {
    public AnsiTerminal black()
    {
      return backgroundColor(Color.BLACK);
    }
    
    public AnsiTerminal red()
    {
      return backgroundColor(Color.RED);
    }
    
    public AnsiTerminal green()
    {
      return backgroundColor(Color.GREEN);
    }
    
    public AnsiTerminal yellow()
    {
      return backgroundColor(Color.YELLOW);
    }
    
    public AnsiTerminal blue()
    {
      return backgroundColor(Color.BLUE);
    }
    
    public AnsiTerminal magenta()
    {
      return backgroundColor(Color.MAGENTA);
    }
    
    public AnsiTerminal cyan()
    {
      return backgroundColor(Color.CYAN);
    }
    
    public AnsiTerminal white()
    {
      return backgroundColor(Color.WHITE);
    }

    public AnsiTerminal brightBlack()
    {
      return backgroundColor(Color.BRIGHT_BLACK);
    }

    public AnsiTerminal brightRed()
    {
      return backgroundColor(Color.BRIGHT_RED);
    }
    
    public AnsiTerminal brightGreen()
    {
      return backgroundColor(Color.BRIGHT_GREEN);
    }
    
    public AnsiTerminal brightYellow()
    {
      return backgroundColor(Color.BRIGHT_YELLOW);
    }
    
    public AnsiTerminal brightBlue()
    {
      return backgroundColor(Color.BRIGHT_BLUE);
    }
    
    public AnsiTerminal brightMagenta()
    {
      return backgroundColor(Color.BRIGHT_MAGENTA);
    }
    
    public AnsiTerminal brightCyan()
    {
      return backgroundColor(Color.BRIGHT_CYAN);
    }
    
    public AnsiTerminal brightWhite()
    {
      return backgroundColor(Color.BRIGHT_WHITE);
    }
    
    public AnsiTerminal rgb(int red, int green, int blue)
    {
      return backgroundColor(new Color(red, green, blue));
    }
  }
  
  public class FontStyleInline
  {

    public AnsiTerminal bold()
    {
      return fontStyle(FontStyle.BOLD);
    }

    public AnsiTerminal underline()
    {
      return fontStyle(FontStyle.UNDERLINE);
    }

    public AnsiTerminal negative()
    {
      return fontStyle(FontStyle.NEGATIVE);
    }
  }

  public class Cursor
  {
    public AnsiTerminal up()
    {
      return up(1);
    }
    
    public AnsiTerminal up(int count)
    {
      Check.parameter("count").value(count).isPositive().isNotZero();
      return csi("A", count);
    }
    
    public AnsiTerminal down()
    {
      return down(1);
    }
    
    public AnsiTerminal down(int count)
    {
      Check.parameter("count").value(count).isPositive().isNotZero();
      return csi("B", count);
    }

    public AnsiTerminal forward()
    {
      return forward(1);
    }

    public AnsiTerminal forward(int count)
    {
      Check.parameter("count").value(count).isPositive().isNotZero();
      return csi("C", count);
    }
    
    public AnsiTerminal backward()
    {
      return backward(1);
    }
    
    public AnsiTerminal backward(int count)
    {
      Check.parameter("count").value(count).isPositive().isNotZero();
      return csi("D", count);
    }

    public AnsiTerminal position(int line, int column)
    {
      Check.parameter("line").value(line).isPositive().isNotZero();
      Check.parameter("column").value(column).isPositive().isNotZero();
      return csi("H", line, column);
    }

    public AnsiTerminal previousLine()
    {
      return previousLine(1);
    }

    public AnsiTerminal previousLine(int lines)
    {
      Check.parameter("lines").value(lines).isPositive().isNotZero();
      return csi("F", lines);
    }

    public AnsiTerminal nextLine()
    {
      return nextLine(1);
    }
    
    public AnsiTerminal nextLine(int lines)
    {
      Check.parameter("lines").value(lines).isPositive().isNotZero();
      return csi("E", lines);
    }

    public AnsiTerminal hide()
    {
      return csi("?25l");      
    }

    public AnsiTerminal show()
    {
      return csi("?25h");
    }

    public AnsiTerminal column(int column)
    {
      Check.parameter("column").value(column).isPositive().isNotZero();
      return csi("G", column);
    }
    
    private AnsiTerminal csi(String command, int... arguments)
    {
      return write(EscCode.csi(command, arguments));
    }
  }
}

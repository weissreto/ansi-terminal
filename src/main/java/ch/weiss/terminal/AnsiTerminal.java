package ch.weiss.terminal;

import java.io.PrintStream;

import ch.weiss.terminal.windows.VirtualTerminal4Windows;

public class AnsiTerminal 
{
  private static final AnsiTerminal INSTANCE = new AnsiTerminal();
  private static final PrintStream OUT = System.out; 
  
  private AnsiTerminal()
  {
    VirtualTerminal4Windows.enableVirtualTerminalProcessing();
  }

  public static AnsiTerminal get()
  {
    return INSTANCE;
  }
  
  public AnsiTerminal write(String text)
  {
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
  
  public AnsiTerminal newLine()
  {
    OUT.println();
    return this;
  }
  
  public AnsiTerminal reset()
  {
    return sgr(0);
  }
  
  public AnsiTerminal sgr(int command)
  {
    return csi(command+"m");
  }

  public AnsiTerminal sgr(int command1, int command2)
  {
    return csi(command1+";"+command2+"m");
  }

  public AnsiTerminal csi(String command)
  {
    return esc("["+command);
  }
  
  private AnsiTerminal csi(int argument, String command)
  {
    return csi(argument+command);
  }
  
  private AnsiTerminal csi(int argument1, int argument2, String command)
  {
    return csi(argument1+";"+argument2+command);
  }
  
  public AnsiTerminal esc(String command)
  {
    return write("\033"+command);
  }
  
  public AnsiTerminal clear()
  {
    return csi(2, "J");
  }

  public ForegroundColor color()
  {
    return new ForegroundColor();
  }
  
  public BackgroundColor backgroundColor()
  {
    return new BackgroundColor();
  }
  
  public Style style()
  {
    return new Style();
  }
  
  public Cursor cursor()
  {
    return new Cursor();
  }
  
  public class ForegroundColor
  {
    public AnsiTerminal black()
    {
      return sgr(30);
    }

    public AnsiTerminal red()
    {
      return sgr(31);
    }
    
    public AnsiTerminal green()
    {
      return sgr(32);
    }
    
    public AnsiTerminal yellow()
    {
      return sgr(33);
    }
    
    public AnsiTerminal blue()
    {
      return sgr(34);
    }
    
    public AnsiTerminal magenta()
    {
      return sgr(35);
    }
    
    public AnsiTerminal cyan()
    {
      return sgr(36);
    }
    
    public AnsiTerminal white()
    {
      return sgr(37);
    }

    public AnsiTerminal brightBlack()
    {
      return sgr(1, 30);
    }

    public AnsiTerminal brightRed()
    {
      return sgr(1, 31);
    }
    
    public AnsiTerminal brightGreen()
    {
      return sgr(1, 32);
    }
    
    public AnsiTerminal brightYellow()
    {
      return sgr(1, 33);
    }
    
    public AnsiTerminal brightBlue()
    {
      return sgr(1, 34);
    }
    
    public AnsiTerminal brightMagenta()
    {
      return sgr(1, 35);
    }
    
    public AnsiTerminal brightCyan()
    {
      return sgr(1, 36);
    }
    
    public AnsiTerminal brightWhite()
    {
      return sgr(1, 37);
    }
  }

  public class BackgroundColor
  {
    public AnsiTerminal black()
    {
      return sgr(40);
    }

    public AnsiTerminal red()
    {
      return sgr(41);
    }
    
    public AnsiTerminal green()
    {
      return sgr(42);
    }
    
    public AnsiTerminal yellow()
    {
      return sgr(43);
    }
    
    public AnsiTerminal blue()
    {
      return sgr(44);
    }
    
    public AnsiTerminal magenta()
    {
      return sgr(45);
    }
    
    public AnsiTerminal cyan()
    {
      return sgr(46);
    }
    
    public AnsiTerminal white()
    {
      return sgr(47);
    }

    public AnsiTerminal brightBlack()
    {
      return sgr(100);
    }

    public AnsiTerminal brightRed()
    {
      return sgr(101);
    }
    
    public AnsiTerminal brightGreen()
    {
      return sgr(102);
    }
    
    public AnsiTerminal brightYellow()
    {
      return sgr(103);
    }
    
    public AnsiTerminal brightBlue()
    {
      return sgr(104);
    }
    
    public AnsiTerminal brightMagenta()
    {
      return sgr(105);
    }
    
    public AnsiTerminal brightCyan()
    {
      return sgr(106);
    }
    
    public AnsiTerminal brightWhite()
    {
      return sgr(107);
    }
  }
  
  public class Style
  {

    public AnsiTerminal bold()
    {
      return sgr(1);
    }

    public AnsiTerminal underline()
    {
      return sgr(4);
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
      return csi(count, "A");
    }
    
    public AnsiTerminal down()
    {
      return down(1);
    }
    
    public AnsiTerminal down(int count)
    {
      return csi(count, "B");
    }

    public AnsiTerminal forward()
    {
      return forward(1);
    }

    public AnsiTerminal forward(int count)
    {
      return csi(count, "C");
    }
    
    public AnsiTerminal backward()
    {
      return backward(1);
    }
    
    public AnsiTerminal backward(int count)
    {
      return csi(count, "D");
    }

    public AnsiTerminal position(int line, int column)
    {
      return csi(line, column, "H");
    }

    public AnsiTerminal previousLine()
    {
      return previousLine(1);
    }

    public AnsiTerminal previousLine(int lines)
    {
      return csi(lines, "F");
    }

    public AnsiTerminal nextLine()
    {
      return nextLine(1);
    }
    
    public AnsiTerminal nextLine(int lines)
    {
      return csi(lines, "E");
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
      return csi(column, "G");
    }
  }
}

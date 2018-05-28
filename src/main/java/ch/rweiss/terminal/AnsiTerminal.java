package ch.rweiss.terminal;

import java.io.IOException;

import com.sun.jna.Platform;

import ch.rweiss.check.Check;
import ch.rweiss.terminal.graphics.Graphics;
import ch.rweiss.terminal.internal.SystemTerminal;
import ch.rweiss.terminal.internal.Terminal;
import ch.rweiss.terminal.internal.buffer.TerminalBuffer;
import ch.rweiss.terminal.linux.AnsiTerminalForLinux;
import ch.rweiss.terminal.windows.AnsiTerminalForWindows;

public class AnsiTerminal 
{
  private static final AnsiTerminal INSTANCE = new AnsiTerminal();
  private final Terminal systemTerminal = new SystemTerminal();
  private Terminal terminal = systemTerminal;
  private final FontStyleInline fontStyleInline = new FontStyleInline();
  private final ForegroundColor foregroundColor = new ForegroundColor();
  private final BackgroundColor backgroundColor = new BackgroundColor();
  private final Graphics graphics = new Graphics(this);
  private final Cursor cursor = new Cursor();
  private final Clear clear = new Clear(this);
  private final OffScreen offScreen = new OffScreen();
  
  private AnsiTerminal()
  {
    if (Platform.isWindows())
    {
      AnsiTerminalForWindows.enableVirtualTerminalProcessing();
      AnsiTerminalForWindows.disableLineAndEchoInput();
      AnsiTerminalForWindows.changeToUtf8CodePage();
    }
    else if (Platform.isLinux())
    {
      AnsiTerminalForLinux.disableLineAndEchoInput();
    }
      
    terminal = new SystemTerminal();
  }

  public static AnsiTerminal get()
  {
    return INSTANCE;
  }
  
  public AnsiTerminal write(StyledText text)
  {
    for (StyledText.Part part : text.parts())
    {
      if (part.style() == null)
      {
        reset();
      }
      else
      {
        style(part.style());
      }
      write(part.text());
    }
    return this;
  }
  
  public AnsiTerminal write(String text)
  {
    terminal.print(text);
    return this;
  }
  
  public AnsiTerminal write(char ch)
  {
    terminal.print(ch);
    return this;
  }
  
  public AnsiTerminal write(long value)
  {
    terminal.print(value);
    return this;
  }
  
  public AnsiTerminal write(EscCode command)
  {
    Check.parameter("command").withValue(command).isNotNull();
    
    terminal.print(command);
    return this;
  }
  
  public AnsiTerminal newLine()
  {
    terminal.println();
    return this;
  }
  
  public AnsiTerminal reset()
  {
    return write(Style.RESET);
  }
  
  public AnsiTerminal style(Style style)
  {
    Check.parameter("style").withValue(style).isNotNull();

    style.style().forEach(this::write);
    return this;
  }
  
  public Clear clear()
  {
    return clear;
  }
  
  public Graphics graphics()
  {
    return graphics;
  }

  public ForegroundColor color()
  {
    return foregroundColor;
  }
  
  public AnsiTerminal color(Color color)
  {
    Check.parameter("color").withValue(color).isNotNull();

    return write(color.foreground());
  }
  
  public BackgroundColor backgroundColor()
  {
    return backgroundColor;
  }
  
  public AnsiTerminal backgroundColor(Color color)
  {
    Check.parameter("color").withValue(color).isNotNull();
 
    return write(color.background());
  }
  
  public FontStyleInline fontStyle()
  {
    return fontStyleInline ;
  }
  
  public AnsiTerminal fontStyle(FontStyle fontStyle)
  {
    Check.parameter("fontStyle").withValue(fontStyle).isNotNull();

    return write(fontStyle.fontStyle());
  }
  
  public Cursor cursor()
  {
    return cursor ;
  }
  
  public OffScreen offScreen()
  {
    return offScreen;
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
      Check.parameter("count").withValue(count).isPositive().isNotZero();
      return csi('A', count);
    }
    
    public AnsiTerminal down()
    {
      return down(1);
    }
    
    public AnsiTerminal down(int count)
    {
      Check.parameter("count").withValue(count).isPositive().isNotZero();
      return csi('B', count);
    }

    public AnsiTerminal forward()
    {
      return forward(1);
    }

    public AnsiTerminal forward(int count)
    {
      Check.parameter("count").withValue(count).isPositive().isNotZero();
      return csi('C', count);
    }
    
    public AnsiTerminal backward()
    {
      return backward(1);
    }
    
    public AnsiTerminal backward(int count)
    {
      Check.parameter("count").withValue(count).isPositive().isNotZero();
      return csi('D', count);
    }

    public AnsiTerminal position(int line, int column)
    {
      Check.parameter("line").withValue(line).isPositive();
      Check.parameter("column").withValue(column).isPositive();
      return csi('H', line, column);
    }
    
    public AnsiTerminal position(Position position)
    {
      Check.parameter("position").withValue(position).isNotNull();
      return position(position.line(), position.column());
    }
    
    public Position position()
    {
      write(EscCode.csi('n', 6));
      try
      {
        EscCode result = EscCode.readFrom(terminal);
        if (!result.isCsi() || result.csiCommand() != 'R')
        {
          throw new RuntimeException("Could not evaluate position of cursor. Received wrong escape code "+result);
        }
        return new Position(result.csiArguments()[0], result.csiArguments()[1]);
      }
      catch (IOException ex)
      {
        throw new RuntimeException("Could not evaluate position of cursor", ex);
      }
    }
    
    public Position maxPosition()
    {
      Position currentPosition = position();
      position(999, 999);
      Position maxPosition = position();
      position(currentPosition);
      return maxPosition;
    }
    
    public AnsiTerminal previousLine()
    {
      return previousLine(1);
    }

    public AnsiTerminal previousLine(int lines)
    {
      Check.parameter("lines").withValue(lines).isPositive().isNotZero();
      return csi('F', lines);
    }

    public AnsiTerminal nextLine()
    {
      return nextLine(1);
    }
    
    public AnsiTerminal nextLine(int lines)
    {
      Check.parameter("lines").withValue(lines).isPositive().isNotZero();
      return csi('E', lines);
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
      Check.parameter("column").withValue(column).isPositive().isNotZero();
      return csi('G', column);
    }
    
    private AnsiTerminal csi(char command, int... arguments)
    {
      return write(EscCode.csi(command, arguments));
    }

    private AnsiTerminal csi(String command)
    {
      return write(EscCode.csi(command));
    }
  }
  
  public static class Clear
  {
    private static final EscCode CLEAR_SCREEN_TO_END = EscCode.csi('J', 0);
    private static final EscCode CLEAR_SCREEN_FROM_BEGIN = EscCode.csi('J', 1);
    private static final EscCode CLEAR_SCREEN = EscCode.csi('J', 2);
    private static final EscCode CLEAR_LINE_TO_END = EscCode.csi('K', 0);
    private static final EscCode CLEAR_LINE_FROM_BEGIN = EscCode.csi('K', 1);
    private static final EscCode CLEAR_LINE = EscCode.csi('K', 2);
    
    private AnsiTerminal term;
    
    private Clear(AnsiTerminal term)
    {
      this.term = term;
    }
    
    public AnsiTerminal screen()
    {
      return term.write(CLEAR_SCREEN);
    }
    
    public AnsiTerminal screenFromBegin()
    {
      return term.write(CLEAR_SCREEN_FROM_BEGIN);
    }
    
    public AnsiTerminal screenToEnd()
    {
      return term.write(CLEAR_SCREEN_TO_END);
    }
    
    public AnsiTerminal line()
    {
      return term.write(CLEAR_LINE);
    }
    
    public AnsiTerminal lineFromBegin()
    {
      return term.write(CLEAR_LINE_FROM_BEGIN);
    }
    
    public AnsiTerminal lineToEnd()
    {
      return term.write(CLEAR_LINE_TO_END);
    }    
  }

  public class OffScreen
  {
    TerminalBuffer offScreenBuffer;

    public void on()
    {
      Position position = cursor().maxPosition();
      on(new TerminalBuffer(position.line(), position.column()));
    }

    void on(TerminalBuffer buffer)
    {
      offScreenBuffer = buffer;
      terminal = offScreenBuffer;
    }

    public void syncToScreen()
    {
      if (offScreenBuffer == null)
      {
        return;
      }
      Terminal current = terminal;
      terminal = systemTerminal;
      try
      {
        offScreenBuffer.writeTo(AnsiTerminal.this);
      }
      finally
      {
        terminal = current;
      }
    }

    public void off()
    {
      offScreenBuffer = null;
      terminal = systemTerminal;
    }
  }
}

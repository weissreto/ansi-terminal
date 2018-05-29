package ch.rweiss.terminal.internal.buffer;

import ch.rweiss.terminal.Color;
import ch.rweiss.terminal.EscCode;
import ch.rweiss.terminal.FontStyle;

class EscCodeInterpreter
{
  private TerminalBuffer terminal;

  EscCodeInterpreter(TerminalBuffer terminal)
  {
    this.terminal = terminal;
  }

  public void interpret(EscCode command)
  {
    if (command.isSgr())
    {
      sgr(command.csiArguments());
    }    
    else if (command.isCsi())
    {
      csi(command);
    }
    else
    {
      throw new IllegalArgumentException("Unsupported esc command");
    }
  }
  
  private void sgr(int[] arguments)
  {
    if (arguments.length == 0)
    {
      throw new IllegalArgumentException("Unsupported sgr command"); 
    }
    switch(arguments[0])
    {
      case 0: 
        reset();
        break;
      case 1:
        bold();
        break;
      case 4:
        underline();
        break;
      case 7:
        negative();
        break;
      default:
        color(arguments);
        break;
    }
  }

  private void reset()
  {
    terminal.font(null);
    terminal.color(null);
    terminal.backgroundColor(null);
  }

  private void bold()
  {
    terminal.font(FontStyle.BOLD);
  }

  private void underline()
  {
    terminal.font(FontStyle.UNDERLINE);
  }

  private void negative()
  {
    terminal.font(FontStyle.NEGATIVE);
  }

  private void color(int[] arguments)
  {
    int sgr = arguments[0];
    if (sgr <= 38 || (sgr >= 90 && sgr <= 97))
    {
      foregroundColor(arguments);
    }
    else
    {
      backgroundColor(arguments);
    }
  }
  
  private void backgroundColor(int[] arguments)
  {
    int sgr = arguments[0];
    Color backgroundColor = null;
    if (sgr >= 40 && sgr <= 47)
    {
      backgroundColor = Color.standardColor(sgr-40);
      terminal.backgroundColor(backgroundColor);
    }
    else if (sgr == 48 && arguments.length == 5 && arguments[1] == 2)
    {
      backgroundColor = new Color(arguments[2], arguments[3], arguments[4]);
    }
    if (sgr >= 100 && sgr <= 107)
    {
      backgroundColor = Color.brightStandardColor(sgr-100);
    }
    if (backgroundColor == null)
    {
      throw new IllegalArgumentException("Unsupported sgr command "+sgr);
    }
    terminal.backgroundColor(backgroundColor);
  }

  private void foregroundColor(int[] arguments)
  {
    int sgr = arguments[0];
    Color foregroundColor = null;
    if (sgr >= 30 && sgr <= 37)
    {
      foregroundColor = Color.standardColor(sgr-30);
    }
    else if (sgr >= 90 && sgr <= 97)
    {
      foregroundColor = Color.brightStandardColor(sgr-90);
    }
    else if (sgr == 38 && arguments.length == 5 && arguments[1] == 2)
    {
      foregroundColor = new Color(arguments[2], arguments[3], arguments[4]);
    }
    if (foregroundColor == null)
    {
      throw new IllegalArgumentException("Unsupported sgr command "+sgr);
    }
    terminal.color(foregroundColor);
    
  }

  private void csi(EscCode command)
  {
    switch(command.csiCommand())
    {
      case '?':
        cursor(command);
        break;
      case 'A':
        up(command.csiArguments());
        break;
      case 'B':
        down(command.csiArguments());
        break;
      case 'C':
        forward(command.csiArguments());
        break;
      case 'D':
        backward(command.csiArguments());
        break;
      case 'E':
        nextLine(command.csiArguments());
        break;
      case 'F':
        previousLine(command.csiArguments());
        break;
      case 'G':
        column(command.csiArguments());
        break;
      case 'H':
        setPosition(command.csiArguments());
        break;
      case 'J':
        clearScreen(command.csiArguments());
        break;
      case 'K':
        clearLine(command.csiArguments());
        break;
      case 'n':
        getPosition(command.csiArguments());
        break;
      default:
        throw new IllegalArgumentException("Unkown csi command "+command.csiCommand());
    }
  }

  private void up(int[] arguments)
  {
    terminal.moveCursorBy(-arguments[0], 0);
  }

  private void down(int[] arguments)
  {
    terminal.moveCursorBy(arguments[0], 0);    
  }

  private void forward(int[] arguments)
  {
    terminal.moveCursorBy(0, arguments[0]);
  }

  private void backward(int[] arguments)
  {
    terminal.moveCursorBy(0, -arguments[0]);
  }

  private void nextLine(int[] arguments)
  {
    down(arguments);
    terminal.moveCursorToColumn(1);    
  }

  private void previousLine(int[] arguments)
  {
    up(arguments);
    terminal.moveCursorToColumn(1);
  }

  private void column(int[] arguments)
  {
    terminal.moveCursorToColumn(arguments[0]);    
  }

  private void setPosition(int[] arguments)
  {
    terminal.moveCursorTo(arguments[0], arguments[1]);    
  }
  
  private void getPosition(int[] arguments)
  {
    if (arguments[0] == 6)
    {
      terminal.writePosition();
    }
  }

  private void clearScreen(int[] arguments)
  {
    Clear clear = Clear.fromCsiArgument(arguments[0]);
    terminal.clearScreen(clear);
  }

  private void clearLine(int[] arguments)
  {
    Clear clear = Clear.fromCsiArgument(arguments[0]);
    terminal.clearLine(clear);
  }

  private void cursor(EscCode command)
  {
    if (command.escCode().endsWith("25h"))
    {
      hideCursor();
    }
    else if (command.escCode().endsWith("25l"))
    {
      showCursor();
    }
    else
    {
      throw new IllegalArgumentException("Unsupported csi command "+command.escCode());
    }
  }

  private void showCursor()
  {
    terminal.showCursor(true);
  }

  private void hideCursor()
  {
    terminal.showCursor(false);
  }
}

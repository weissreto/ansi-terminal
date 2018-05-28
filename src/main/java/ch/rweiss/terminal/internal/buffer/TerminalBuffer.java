package ch.rweiss.terminal.internal.buffer;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import ch.rweiss.terminal.AnsiTerminal;
import ch.rweiss.terminal.Color;
import ch.rweiss.terminal.EscCode;
import ch.rweiss.terminal.FontStyle;
import ch.rweiss.terminal.Position;
import ch.rweiss.terminal.internal.Terminal;

public class TerminalBuffer implements Terminal
{
  private TerminalCharacter[][] buffer;
  private Position cursorPosition = new Position(1,1);
  private boolean showCursor = true;
  private Color color = null;
  private Color backgroundColor = null;
  private FontStyle fontStyle = null;
  private EscCodeInterpreter escCodeInterpreter = new EscCodeInterpreter(this);
  private int lines;
  private int columns;
  private StringReader result;
  
  public TerminalBuffer(int lines, int columns)
  {
    this.lines = lines;
    this.columns = columns;
    buffer = new TerminalCharacter[lines][];
    for (int row = 0; row < lines; row++)
    {
      buffer[row] = new TerminalCharacter[columns];
      Arrays.fill(buffer[row], TerminalCharacter.EMPTY);
    }
  }

  @Override
  public void print(String text)
  {
    if (text == null)
    {
      text = "null";
    }
    text.chars().forEach(ch -> print((char)ch));
  }

  @Override
  public void print(char ch)
  {
    TerminalCharacter newCharacter = new TerminalCharacter(ch, color, backgroundColor, fontStyle);
    setCurrentCharacter(newCharacter);
    moveCursorForward();
  }
  
  @Override
  public void print(long value)
  {
    print(Long.toString(value));    
  }

  @Override
  public void println()
  {
    moveCursorDown();
    moveCursorToColumn(1);
  }

  @Override
  public void print(EscCode command)
  {
    escCodeInterpreter.interpret(command);
  }
  
  @Override
  public int read() throws IOException
  {    
    return result.read();
  }
  
  public void writeTo(AnsiTerminal ansiTerminal)
  {
    ansiTerminal.clear().screen();
    ansiTerminal.cursor().position(1, 1);
    for (int line = 1; line < lines; line++)
    {
      for (int column = 1; column < columns; column++)
      {
        getCharacter(line, column).writeTo(ansiTerminal);
      }
      ansiTerminal.newLine();
    }
  }

  private void setCurrentCharacter(TerminalCharacter newCharacter)
  {
    setCharacter(cursorPosition.line(), cursorPosition.column(), newCharacter);
  }

  private void setCharacter(int line, int column, TerminalCharacter newCharacter)
  {
    int y = terminalToCoord(line);
    int x = terminalToCoord(column);
    buffer[y][x] = newCharacter;
  }

  TerminalCharacter getCharacter(int line, int column)
  {
    int y = terminalToCoord(line);
    int x = terminalToCoord(column);
    return buffer[y][x];
  }

  private static int terminalToCoord(int lineOrColumn)
  {
    return lineOrColumn - 1;
  }

  void color(Color foregroundColor)
  {
    this.color = foregroundColor;
  }

  void backgroundColor(Color bgColor)
  {
    backgroundColor = bgColor;
  }

  void font(FontStyle fontStyle)
  {
    this.fontStyle = fontStyle;
  }

  void showCursor(boolean visible)
  {
    this.showCursor = visible;
  }

  private void moveCursorForward()
  {
    moveCursorBy(0, 1);
  }
  
  private void moveCursorDown()
  {
    moveCursorBy(1,0);
  }

  void moveCursorToColumn(int column)
  {
    moveCursorTo(cursorPosition.line(), column);
  }

  void moveCursorTo(int line, int column)
  {
    column = Math.max(column, 1);
    column = Math.min(column, columns);
    line = Math.max(line, 1);
    line = Math.min(line, lines);
    cursorPosition = new Position(line, column);
  }

  void moveCursorBy(int lineDelta, int columnDelta)
  {
    int line = cursorPosition.line();
    int column = cursorPosition.column();
    column = column + columnDelta;
    line = line + lineDelta;
    if (column < 1)
    {
      column = columns;
      line = line - 1;
    }
    if (column > columns)
    {
      column = 1;
      line = line + 1;
    }
    if (line < 1)
    {
      line = lines;
    }
    if (line > lines)
    {
      line = 1;
    }
    moveCursorTo(line, column);
  }

  void clearScreen(Clear clear)
  {
    int start;
    int end;
    switch(clear)
    {
      case ALL:
        start = 1;
        end = lines;
        break;
      case FROM_BEGIN:
        start = 1;
        end = cursorPosition.line();
        break;
      case TO_END:
        start = cursorPosition.line();
        end = lines;
        break;
      default:
        throw new IllegalArgumentException("Unknown clear "+clear);
    }
    for (int line = start; line <= end; line++)
    {
      Clear lineClear = Clear.ALL;
      if (line == end && clear == Clear.FROM_BEGIN)
      {
        lineClear = clear;
      }
      if (line == start && clear == Clear.TO_END)
      {
        lineClear = clear;
      }
      clearLine(line, lineClear);
    }
  }

  void clearLine(Clear clear)
  {
    clearLine(cursorPosition.line(), clear);
  }
  
  private void clearLine(int line, Clear clear)
  {
    int start;
    int end;
    switch(clear)
    {
      case ALL:
        start = 1;
        end = columns;
        break;
      case FROM_BEGIN:
        start = 1;
        end = cursorPosition.column();
        break;
      case TO_END:
        start = cursorPosition.column();
        end = columns;
        break;    
      default:
        throw new IllegalArgumentException("Unknown clear "+clear);
    }
    for (int column = start; column <= end; column++)
    {
      setCharacter(line, column, TerminalCharacter.EMPTY);
    }
  }

  String dump()
  {
    StringBuilder builder = new StringBuilder();
    for (int line = 1; line <= lines; line++)
    {
      if (line > 1)
      {
        builder.append('\n');
      }
      for (int column = 1; column <= columns; column++)
      {
        builder.append(getCharacter(line, column).dump());
      }
    }
    return builder.toString();
  }

  void writePosition()
  {
    EscCode position = EscCode.csi('R', cursorPosition.line(), cursorPosition.column());
    result = new StringReader(position.escCode());
  }
}

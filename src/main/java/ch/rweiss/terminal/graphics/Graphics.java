package ch.rweiss.terminal.graphics;

import ch.rweiss.check.Check;
import ch.rweiss.terminal.AnsiTerminal;
import ch.rweiss.terminal.Color;
import ch.rweiss.terminal.FontStyle;
import ch.rweiss.terminal.Style;

public class Graphics
{
  private final AnsiTerminal terminal;
  private LineStyle currentLineStyle = LineStyle.SINGLE_LINE;

  public Graphics(AnsiTerminal terminal)
  {
    this.terminal = terminal;
  }
  
  public void color(Color color)
  {
    terminal.color(color);
  }
  
  public void backgroundColor(Color color)
  {
    terminal.backgroundColor(color);
  }
  
  public void fontStyle(FontStyle fontStyle)
  {
    terminal.fontStyle(fontStyle);
  }
  
  public void style(Style style)
  {
    terminal.style(style);
  }
  
  public void lineStyle(LineStyle lineStyle)
  {
    this.currentLineStyle = lineStyle; 
  }
  
  public void reset()
  {
    terminal.reset();
    currentLineStyle = LineStyle.SINGLE_LINE;
  }
  

  public void drawCharacter(Point position, char character)
  {
    Check.parameter("position").withValue(position).isNotNull();

    terminal
      .cursor()
      .position(position.line(), position.column())
      .write(character);
  }

  public void drawText(Point position, String text)
  {
    Check.parameter("position").withValue(position).isNotNull();
    Check.parameter("text").withValue(text).isNotNull();
       
    terminal
        .cursor()
        .position(position.line(), position.column())
        .write(text);
  }
  
  public void drawVerticalText(Point position, String text)
  {
    Check.parameter("position").withValue(position).isNotNull();
    Check.parameter("text").withValue(text).isNotNull();
    
    Point nextPosition = position;
    for (int pos = 0; pos < text.length(); pos++)
    {
      drawCharacter(nextPosition, text.charAt(pos));
      nextPosition = nextPosition.moveTo(Direction.DOWN, 1);
    }
  }

  public void drawText(Point position, Direction direction, String text)
  {
    Check.parameter("position").withValue(position).isNotNull();
    Check.parameter("direction").withValue(direction).isNotNull();
    Check.parameter("text").withValue(text).isNotNull();
    
    switch(direction)
    {
      case RIGHT:
        drawText(position, text);
        break;
      case LEFT:
        Point pos = position.moveTo(direction, text.length());
        String reverseText = reverseText(text);
        drawText(pos, text);
        break;
      case DOWN:
        drawVerticalText(position, text);
        break;
      case UP:
        pos = position.moveTo(direction, text.length());
        reverseText = reverseText(text);
        drawVerticalText(pos, reverseText);
        break;
      default:
        throw new IllegalArgumentException("Parameter direction unknown value "+direction);
    }
  }
  
  public void drawHorizontalLine(Point position, int length)
  {
    drawHorizontalLine(position, length, currentLineStyle.top());
  }

  public void drawHorizontalLine(Point position, int length, char lineStyle)
  {
    Check.parameter("position").withValue(position).isNotNull();
    Check.parameter("length").withValue(length).isPositive();

    StringBuilder line = new StringBuilder();
    for (int pos=0; pos < length; pos++)
    {
      line.append(lineStyle);
    }
    drawText(position, line.toString());
  }

  public void drawVerticalLine(Point position, int length)
  {
    drawVerticalLine(position, length, currentLineStyle.left());
  }
  
  public void drawVerticalLine(Point position, int length, char lineStyle)
  {
    Check.parameter("position").withValue(position).isNotNull();
    Check.parameter("length").withValue(length).isPositive();
    
    Point nextPosition = position;
    for (int pos = 0; pos < length; pos++)
    {
      drawCharacter(nextPosition, lineStyle);
      nextPosition = nextPosition.moveTo(Direction.DOWN, 1);
    }
  }

  public void drawLine(Point position, Direction direction, int length)
  {
    Check.parameter("direction").withValue(direction).isNotNull();
    char lineChar = currentLineStyle.forDirection(direction);
    drawLine(position, direction, length, lineChar);
  }

  public void drawLine(Point position, Direction direction, int length, char lineStyle)
  {
    Check.parameter("position").withValue(position).isNotNull();
    Check.parameter("direction").withValue(direction).isNotNull();
    Check.parameter("length").withValue(length).isPositive();
    
    switch(direction)
    {
      case RIGHT:
        drawHorizontalLine(position, length, lineStyle);
        break;
      case LEFT:
        Point pos = position.moveTo(direction, length-1);
        drawHorizontalLine(pos, length, lineStyle);
        break;
      case DOWN:
        drawVerticalLine(position, length, lineStyle);
        break;
      case UP:
        pos = position.moveTo(direction, length-1);
        drawVerticalLine(pos, length, lineStyle);
        break;
      default:
        throw new IllegalArgumentException("Parameter direction unknown value " + direction);
    }
  }

  public void drawLine(Point p1, Point p2)
  {
    drawLine(p1, p2, currentLineStyle.forAllDirections());
  }
    
  public void drawLine(Point p1, Point p2, char lineStyle)
  {
    Check.parameter("p1").withValue(p1).isNotNull();
    Check.parameter("p2").withValue(p2).isNotNull();
    
    int dx = Math.abs(p2.x() - p1.x());
    int dy = Math.abs(p2.y() - p1.y());

    int sx = (p1.x() < p2.x()) ? 1 : -1;
    int sy = (p1.y() < p2.y()) ? 1 : -1;

    int err = dx - dy;
    int x = p1.x();
    int y = p1.y();

    while (true) 
    {
      drawCharacter(new Point(x, y), lineStyle);

      if (x == p2.x() && y == p2.y()) 
      {
          break;
      }
      int e2 = 2 * err;
      if (e2 > -dy) 
      {
        err = err - dy;
        x = x + sx;
      }
      if (e2 < dx) 
      {
        err = err + dx;
        y = y + sy;
      }
    }
  }
  
  public void drawRectangle(Rectangle rectangle)
  {
    if (rectangle.height() < 1 || rectangle.width() < 1)
    {
      return;
    }
    drawCharacter(rectangle.topLeft(), currentLineStyle.topLeft());
    drawCharacter(rectangle.topRight(), currentLineStyle.topRight());
    drawCharacter(rectangle.bottomRight(), currentLineStyle.bottomRight());
    drawCharacter(rectangle.bottomLeft(), currentLineStyle.bottomLeft());
    if (rectangle.width() > 1)
    {
      drawHorizontalLine(rectangle.topLeft().moveTo(Direction.RIGHT, 1), rectangle.width()-1, currentLineStyle.top());
      drawHorizontalLine(rectangle.bottomLeft().moveTo(Direction.RIGHT, 1), rectangle.width()-1, currentLineStyle.bottom());
    }
    if (rectangle.height() > 1)
    {
      drawVerticalLine(rectangle.topLeft().moveTo(Direction.DOWN, 1), rectangle.height()-1, currentLineStyle.left());
      drawVerticalLine(rectangle.topRight().moveTo(Direction.DOWN, 1), rectangle.height()-1, currentLineStyle.right());
    }
  }
  
  private static String reverseText(String text)
  {
    StringBuilder builder = new StringBuilder();
    for (int pos = text.length()-1; pos >= 0; pos--)
    {
      builder.append(text.charAt(pos));
    }
    return builder.toString();
  }
}

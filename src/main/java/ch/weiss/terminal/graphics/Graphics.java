package ch.weiss.terminal.graphics;

import ch.weiss.check.Check;
import ch.weiss.terminal.AnsiTerminal;

public class Graphics
{
  private final AnsiTerminal terminal;

  public Graphics(AnsiTerminal terminal)
  {
    this.terminal = terminal;
  }

  public void drawCharacter(Point position, char character)
  {
    Check.parameter("position").value(position).isNotNull();

    terminal
      .cursor()
      .position(position.line(), position.column())
      .write(character);
  }

  public void drawText(Point position, String text)
  {
    Check.parameter("position").value(position).isNotNull();
    Check.parameter("text").value(text).isNotNull();
       
    terminal
        .cursor()
        .position(position.line(), position.column())
        .write(text);
  }
  
  public void drawVerticalText(Point position, String text)
  {
    Check.parameter("position").value(position).isNotNull();
    Check.parameter("text").value(text).isNotNull();
    
    for (int pos = 0; pos < text.length(); pos++)
    {
      drawCharacter(position, text.charAt(pos));
      position = position.moveTo(Direction.DOWN, 1);
    }
  }

  public void drawText(Point position, Direction direction, String text)
  {
    Check.parameter("position").value(position).isNotNull();
    Check.parameter("direction").value(direction).isNotNull();
    Check.parameter("text").value(text).isNotNull();
    
    switch(direction)
    {
      case RIGHT:
        drawText(position, text);
        break;
      case LEFT:
        position = position.moveTo(direction, text.length());
        text = reverseText(text);
        drawText(position, text);
        break;
      case DOWN:
        drawVerticalText(position, text);
        break;
      case UP:
        position = position.moveTo(direction, text.length());
        text = reverseText(text);
        drawVerticalText(position, text);
    }
  }
  
  public void drawHorizontalLine(Point position, int length)
  {
    drawHorizontalLine(position, length, LineStyle.SINGLE_LINE);
  }

  public void drawHorizontalLine(Point position, int length, LineStyle lineStyle)
  {
    Check.parameter("lineStyle").value(lineStyle).isNotNull();
    drawHorizontalLine(position, length, lineStyle.top());
  }
  
  public void drawHorizontalLine(Point position, int length, char lineStyle)
  {
    Check.parameter("position").value(position).isNotNull();
    Check.parameter("length").value(length).isPositive();

    StringBuilder line = new StringBuilder();
    for (int pos=0; pos < length; pos++)
    {
      line.append(lineStyle);
    }
    drawText(position, line.toString());
  }

  public void drawVerticalLine(Point position, int length)
  {
    drawVerticalLine(position, length, LineStyle.SINGLE_LINE);
  }

  public void drawVerticalLine(Point position, int length, LineStyle lineStyle)
  {
    Check.parameter("lineStyle").value(lineStyle).isNotNull();
    drawVerticalLine(position, length, lineStyle.left());
  }
  
  public void drawVerticalLine(Point position, int length, char lineStyle)
  {
    Check.parameter("position").value(position).isNotNull();
    Check.parameter("length").value(length).isPositive();
    
    for (int pos = 0; pos < length; pos++)
    {
      drawCharacter(position, lineStyle);
      position = position.moveTo(Direction.DOWN, 1);
    }
  }

  public void drawLine(Point position, Direction direction, int length)
  {
    drawLine(position, direction, length, LineStyle.SINGLE_LINE);
  }

  public void drawLine(Point position, Direction direction, int length, LineStyle lineStyle)
  {
    Check.parameter("position").value(position).isNotNull();
    Check.parameter("direction").value(direction).isNotNull();
    Check.parameter("length").value(length).isPositive();
    Check.parameter("lineStyle").value(lineStyle).isNotNull();
    
    switch(direction)
    {
      case RIGHT:
        drawHorizontalLine(position, length, lineStyle);
        break;
      case LEFT:
        position = position.moveTo(direction, length-1);
        drawHorizontalLine(position, length, lineStyle);
        break;
      case DOWN:
        drawVerticalLine(position, length, lineStyle);
        break;
      case UP:
        position = position.moveTo(direction, length-1);
        drawVerticalLine(position, length, lineStyle);
    }
  }

  public void drawLine(Point p1, Point p2, LineStyle lineStyle)
  {
    Check.parameter("p1").value(p1).isNotNull();
    Check.parameter("p2").value(p2).isNotNull();
    
    int dx = Math.abs(p2.x() - p1.x());
    int dy = Math.abs(p2.y() - p1.y());

    int sx = (p1.x() < p2.x()) ? 1 : -1;
    int sy = (p1.y() < p2.y()) ? 1 : -1;

    int err = dx - dy;
    int x = p1.x();
    int y = p1.y();

    while (true) 
    {
      drawCharacter(new Point(x, y), lineStyle.top());

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
    drawRectangle(rectangle, LineStyle.SINGLE_LINE);
  }
  
  public void drawRectangle(Rectangle rectangle, LineStyle style)
  {
    if (rectangle.height() < 2 || rectangle.width() < 2)
    {
      return;
    }
    drawCharacter(rectangle.topLeft(), style.topLeft());
    drawCharacter(rectangle.topRight(), style.topRight());
    drawCharacter(rectangle.bottomRight(), style.bottomRight());
    drawCharacter(rectangle.bottomLeft(), style.bottomLeft());
    if (rectangle.width() > 2)
    {
      drawHorizontalLine(rectangle.topLeft().moveTo(Direction.RIGHT, 1), rectangle.width()-2, style.top());
      drawHorizontalLine(rectangle.bottomLeft().moveTo(Direction.RIGHT, 1), rectangle.width()-2, style.bottom());
    }
    if (rectangle.height() > 2)
    {
      drawVerticalLine(rectangle.topLeft().moveTo(Direction.DOWN, 1), rectangle.height()-2, style.left());
      drawVerticalLine(rectangle.topRight().moveTo(Direction.DOWN, 1), rectangle.height()-2, style.right());
    }
  }
  
  private String reverseText(String text)
  {
    StringBuilder builder = new StringBuilder();
    for (int pos = text.length()-1; pos >= 0; pos--)
    {
      builder.append(text.charAt(pos));
    }
    return builder.toString();
  }
}

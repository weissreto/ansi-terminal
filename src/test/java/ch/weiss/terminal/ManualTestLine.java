package ch.weiss.terminal;

import ch.weiss.terminal.graphics.Direction;
import ch.weiss.terminal.graphics.Graphics;
import ch.weiss.terminal.graphics.LineStyle;
import ch.weiss.terminal.graphics.Point;

public class ManualTestLine extends AbstractManualTest
{
  private Graphics graphics = AnsiTerminal.get().graphics();
  int y = 8;
  
  public static void main(String[] args) throws Exception
  {
    new ManualTestLine().clear().main();
  }
  
  public void horizontalLine()
  {
    graphics.drawHorizontalLine(new Point(2, y), 6);
    y+=4;
  }

  public void horizontalDotLine()
  {
    graphics.drawHorizontalLine(new Point(2, y), 6, '.');
    y+=4;
  }

  public void horizontalDoubleLine()
  {
    graphics.lineStyle(LineStyle.DOUBLE_LINE);
    graphics.drawHorizontalLine(new Point(2, y), 6);
    graphics.reset();
    y+=4;
  }

  public void verticalLine()
  {
    graphics.drawVerticalLine(new Point(2, y), 6);
    y+=9;
  }

  public void verticalDotLine()
  {
    graphics.drawVerticalLine(new Point(2, y), 6, '.');
    y+=9;
  }

  public void verticalDoubleLine()
  {    
    graphics.lineStyle(LineStyle.DOUBLE_LINE);
    graphics.drawVerticalLine(new Point(2, y), 6);
    graphics.reset();
    y+=9;
  }
  
  public void drawLineDirection()
  {
    graphics.drawLine(new Point(10, y+3), Direction.UP, 3);
    graphics.drawLine(new Point(10, y+3), Direction.LEFT, 3);
    graphics.drawLine(new Point(10, y+3), Direction.RIGHT, 3);
    graphics.drawLine(new Point(10, y+3), Direction.DOWN, 3);
    y+=9;
  }

  public void drawXLineDirection()
  {
    graphics.lineStyle(LineStyle.X);
    graphics.drawLine(new Point(10, y+3), Direction.UP, 3);
    graphics.drawLine(new Point(10, y+3), Direction.LEFT, 3);
    graphics.reset();
    y+=7;
  }

}


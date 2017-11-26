package ch.weiss.terminal;

import ch.weiss.terminal.graphics.Direction;
import ch.weiss.terminal.graphics.LineStyle;
import ch.weiss.terminal.graphics.Point;

public class ManualTestLine extends AbstractManualTest
{
  int y = 8;
  public static void main(String[] args) throws Exception
  {
    new ManualTestLine().clear().main();
  }
  
  public void horizontalLine()
  {
    terminal.graphics().drawHorizontalLine(new Point(2, y), 6);
    y+=4;
  }

  public void horizontalDotLine()
  {
    terminal.graphics().drawHorizontalLine(new Point(2, y), 6, '.');
    y+=4;
  }

  public void horizontalDoubleLine()
  {
    terminal.graphics().drawHorizontalLine(new Point(2, y), 6, LineStyle.DOUBLE_LINE);
    y+=4;
  }

  public void verticalLine()
  {
    terminal.graphics().drawVerticalLine(new Point(2, y), 6);
    y+=9;
  }

  public void verticalDotLine()
  {
    terminal.graphics().drawVerticalLine(new Point(2, y), 6, '.');
    y+=9;
  }

  public void verticalDoubleLine()
  {
    terminal.graphics().drawVerticalLine(new Point(2, y), 6, LineStyle.DOUBLE_LINE);
    y+=9;
  }
  
  public void drawLineDirection()
  {
    terminal.graphics().drawLine(new Point(10, y+3), Direction.UP, 3);
    terminal.graphics().drawLine(new Point(10, y+3), Direction.LEFT, 3);
    terminal.graphics().drawLine(new Point(10, y+3), Direction.RIGHT, 3);
    terminal.graphics().drawLine(new Point(10, y+3), Direction.DOWN, 3);
    y+=9;
  }

  public void drawXLineDirection()
  {
    terminal.graphics().drawLine(new Point(10, y+3), Direction.UP, 3, LineStyle.X);
    terminal.graphics().drawLine(new Point(10, y+3), Direction.LEFT, 3, LineStyle.X);
    y+=7;
  }

}


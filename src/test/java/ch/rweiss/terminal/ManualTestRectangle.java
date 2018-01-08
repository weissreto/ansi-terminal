package ch.rweiss.terminal;

import ch.rweiss.terminal.graphics.LineStyle;
import ch.rweiss.terminal.graphics.Point;
import ch.rweiss.terminal.graphics.Rectangle;

public class ManualTestRectangle extends AbstractManualTest
{
  int y = 8;
  public static void main(String[] args) throws Exception
  {
    new ManualTestRectangle().clear().main();
  }
  
  public void drawRectangle()
  {
    terminal.graphics().drawRectangle(new Rectangle(new Point(2,y), new Point(5, y+3)));
    terminal.cursor().nextLine(2);
    y+=8;
  }

  public void drawSmallestRectangle()
  {
    terminal.graphics().drawRectangle(new Rectangle(new Point(2,y), new Point(3, y+1)));
    terminal.cursor().nextLine(3);
    y+=8;
  }

  public void drawThickRectangle()
  {
    terminal.graphics().lineStyle(LineStyle.DOUBLE_LINE);
    terminal.graphics().drawRectangle(new Rectangle(new Point(2,y), new Point(5, y+3)));
    terminal.cursor().nextLine(2);
    terminal.graphics().reset();
    y+=8;
  }

  public void drawXRectangle()
  {
    terminal.graphics().lineStyle(LineStyle.X);
    terminal.graphics().drawRectangle(new Rectangle(new Point(2,y), new Point(25, y+3)));
    terminal.cursor().nextLine(2);
    terminal.graphics().reset();
    y+=8;
  }

}


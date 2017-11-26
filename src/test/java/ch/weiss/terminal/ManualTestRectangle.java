package ch.weiss.terminal;

import ch.weiss.terminal.graphics.Point;
import ch.weiss.terminal.graphics.Rectangle;
import ch.weiss.terminal.graphics.LineStyle;

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
    terminal.graphics().drawRectangle(new Rectangle(new Point(2,y), new Point(5, y+3)), LineStyle.DOUBLE_LINE);
    terminal.cursor().nextLine(2);
    y+=8;
  }

  public void drawXRectangle()
  {
    terminal.graphics().drawRectangle(new Rectangle(new Point(2,y), new Point(25, y+3)), LineStyle.X);
    terminal.cursor().nextLine(2);
    y+=8;
  }

}


package ch.weiss.terminal;

import ch.weiss.terminal.graphics.LineStyle;
import ch.weiss.terminal.graphics.Point;

public class ManualTestLine2 extends AbstractManualTest
{
  int y = 8;
  public static void main(String[] args) throws Exception
  {
    new ManualTestLine2().clear().main();
  }
  
  public void lineToRightBottom()
  {
    terminal.graphics().drawLine(new Point(2, y), new Point(26, y+4), LineStyle.STAR);
    y+=8;
  }

  public void lineToRightTop()
  {
    terminal.graphics().drawLine(new Point(2, y+4), new Point(26, y), LineStyle.STAR);
    terminal.cursor().nextLine(4);
    y+=8;
  }
  
  public void lineToRightTop2()
  {
    terminal.graphics().drawLine(new Point(2, y+4), new Point(4, y), LineStyle.STAR);
    terminal.cursor().nextLine(4);
    y+=8;
  }

  public void lineToLeftTop2()
  {
    terminal.graphics().drawLine(new Point(4, y+4), new Point(2, y), LineStyle.STAR);
    terminal.cursor().nextLine(4);
    y+=8;
  }

  public void lineToLeftTop()
  {
    terminal.graphics().drawLine(new Point(26, y+4), new Point(2, y), LineStyle.STAR);
    terminal.cursor().nextLine(4);
    y+=9;
  }

}


package ch.weiss.terminal;

import ch.weiss.terminal.graphics.Graphics;
import ch.weiss.terminal.graphics.LineStyle;
import ch.weiss.terminal.graphics.Point;

public class ManualTestLine2 extends AbstractManualTest
{
  private Graphics graphics = AnsiTerminal.get().graphics();

  int y = 8;
  public static void main(String[] args) throws Exception
  {
    new ManualTestLine2().clear().main();
  }
  
  public void lineToRightBottom()
  {
    graphics.lineStyle(LineStyle.STAR);
    graphics.drawLine(new Point(2, y), new Point(26, y+4));
    graphics.reset();
    y+=8;
  }

  public void lineToRightTop()
  {
    graphics.lineStyle(LineStyle.STAR);
    graphics.drawLine(new Point(2, y+4), new Point(26, y));
    terminal.cursor().nextLine(4);
    graphics.reset();
    y+=8;
  }
  
  public void lineToRightTop2()
  {
    graphics.lineStyle(LineStyle.STAR);
    graphics.drawLine(new Point(2, y+4), new Point(4, y), LineStyle.STAR.top());
    terminal.cursor().nextLine(4);
    graphics.reset();
    y+=8;
  }

  public void lineToLeftTop2()
  {
    graphics.lineStyle(LineStyle.STAR);
    graphics.drawLine(new Point(4, y+4), new Point(2, y));
    terminal.cursor().nextLine(4);
    graphics.reset();
    y+=8;
  }

  public void lineToLeftTop()
  {
    graphics.lineStyle(LineStyle.X);
    graphics.drawLine(new Point(26, y+4), new Point(2, y));
    terminal.cursor().nextLine(4);
    graphics.reset();
    y+=9;
  }

}


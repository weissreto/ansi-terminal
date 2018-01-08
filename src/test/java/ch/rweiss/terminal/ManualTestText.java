package ch.rweiss.terminal;

import ch.rweiss.terminal.graphics.Direction;
import ch.rweiss.terminal.graphics.Point;

public class ManualTestText extends AbstractManualTest
{
  int y = 8;
  public static void main(String[] args) throws Exception
  {
    new ManualTestText().clear().main();
  }
  
  public void drawText()
  {
    terminal.graphics().drawText(new Point(4, y), "Hello World");
    y+=4;
  }

  public void drawVerticalText()
  {
    terminal.graphics().drawVerticalText(new Point(4, y), "Hello");
    y+=8;
  }

  public void drawTextToLeft()
  {
    terminal.graphics().drawText(new Point(20, y), Direction.LEFT, "Hello World");
    y+=4;
  }

  public void drawTextToUp()
  {
    terminal.graphics().drawText(new Point(4, y+6), Direction.UP, "World");
    y+=9;
  }

}


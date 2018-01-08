package ch.rweiss.terminal;

import ch.rweiss.terminal.Color;

public class ManualTestCursorNavigation extends AbstractManualTest
{
  public static void main(String[] args) throws Exception
  {
    new ManualTestCursorNavigation().main();
  }
  
  public void upAndDown()
  {
    terminal.newLine();
    for (int repeat = 0; repeat < 5; repeat++)
    {
      terminal.cursor().up().write("U");
      sleep(500);
      terminal.cursor().down().write("D");
      sleep(500);
    }    
  }

  public void upAndDown3()
  {
    terminal.newLine().newLine().newLine();
    for (int repeat = 0; repeat < 5; repeat++)
    {
      terminal.cursor().up(3).write("U");
      sleep(500);
      terminal.cursor().down(3).write("D");
      sleep(500);
    }    
  }
  
  public void forwardAndBackward()
  {
    for (int repeat = 0; repeat < 5; repeat++)
    {
      terminal.cursor().forward();
      terminal.cursor().forward().write("F");
      sleep(500);
      terminal.cursor().backward();
      terminal.cursor().backward().write("B");
      sleep(500);
    }    
  }

  public void forwardAndBackward3()
  {
    for (int repeat = 0; repeat < 5; repeat++)
    {
      terminal.cursor().forward(3).write("F");
      sleep(500);
      terminal.cursor().backward(2).write("B");
      sleep(500);
    }    
  }
  
  public void nextAndPreviousLine()
  {
    terminal.newLine();
    for (int repeat = 0; repeat < 5; repeat++)
    {
      terminal.color(Color.STANDARD_COLORS.get(repeat));
      terminal.cursor().previousLine();
      terminal.cursor().previousLine().write("P");
      sleep(500);
      terminal.cursor().nextLine();
      terminal.cursor().nextLine().write("N");
      sleep(500);
    }    
  }
  
  public void nextAndPreviousLine3()
  {
    terminal.newLine().newLine().newLine();
    for (int repeat = 0; repeat < 5; repeat++)
    {
      terminal.color(Color.STANDARD_COLORS.get(repeat));
      terminal.cursor().previousLine(3).write("P");
      sleep(500);
      terminal.cursor().nextLine(3).write("N");
      sleep(500);
    }    
  }
  
  public void column()
  {
    for (int column = 1; column < 80; column+=10)
    {
      terminal.cursor().column(column).write(column);
    }
  }

}    

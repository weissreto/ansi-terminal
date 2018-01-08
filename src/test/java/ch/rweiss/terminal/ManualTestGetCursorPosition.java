package ch.rweiss.terminal;

import ch.rweiss.terminal.Position;

public class ManualTestGetCursorPosition extends AbstractManualTest
{
  public static void main(String[] args) throws Exception
  {
    new ManualTestGetCursorPosition().main();
  }
  
  public void position() throws InterruptedException
  {
    while (true)
    {
      terminal.write("Change the size of the command window!");
      terminal.newLine();
      Position maxPosition = terminal.cursor().maxPosition();
      terminal.write("Max cursor position is: "+maxPosition);
      terminal.newLine();
      Position position = terminal.cursor().position();
      terminal.write("Cursor position is: "+position);
      Thread.sleep(500);
    }
  }

}

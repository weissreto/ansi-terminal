package ch.weiss.terminal;

public class ManualTestCursorPosition extends AbstractManualTest
{
  public static void main(String[] args) throws Exception
  {
    AnsiTerminal.get().clear().cursor().position(0,0);
    new ManualTestCursorPosition().main();
  }
  
  public void position()
  {
    for (int col = 1; col < 20; col+=2)
    {
      for(int line = 10; line < 30; line+=2)
      {
        terminal.cursor().position(9,3).write("line=").write(line).write(" column=").write(col);
        terminal.cursor().position(line,col).write("X");
        sleep(100);
      }
    }
  }
}    

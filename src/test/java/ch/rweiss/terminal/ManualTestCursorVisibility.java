package ch.rweiss.terminal;

public class ManualTestCursorVisibility extends AbstractManualTest
{
  public static void main(String[] args) throws Exception
  {
    new ManualTestCursorVisibility().main();
  }
  
  public void showAndHide()
  {
    terminal.cursor().hide();
    terminal.write("Curson not visible >");
    sleep(3000);
    terminal.newLine().write("Cursor visible >");
    terminal.cursor().show();
    sleep(3000);
    terminal.cursor().hide();
    terminal.newLine().write("Curson not visible >");
    sleep(3000);
    terminal.newLine().write("Cursor visible >");
    terminal.cursor().show();
    sleep(3000);
  }
}    

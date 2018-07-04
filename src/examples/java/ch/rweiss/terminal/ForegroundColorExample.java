package ch.rweiss.terminal;

public class ForegroundColorExample
{
  public static void simple()
  {
    AnsiTerminal terminal = AnsiTerminal.get();
    terminal.backgroundColor().red().write("This text has a red background").newLine();   
  }
  
  public static void extended()
  {
    AnsiTerminal terminal = AnsiTerminal.get();
    terminal.backgroundColor().brightRed().write("This text has a bright red background").newLine();
    
    terminal.backgroundColor().rgb(0, 0, 100).write("This text has a blue background").newLine();
    
    Color green = new Color(0, 200, 0);
    terminal.backgroundColor(green).write("This text has a green background").newLine();
    
    terminal.backgroundColor(Color.YELLOW).write("This text has a yellow background").newLine();
  }
}

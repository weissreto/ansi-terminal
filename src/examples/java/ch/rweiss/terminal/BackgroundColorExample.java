package ch.rweiss.terminal;

public class BackgroundColorExample
{
  public static void simple()
  {
    AnsiTerminal terminal = AnsiTerminal.get();
    terminal.color().red().write("This text is red").newLine();   
  }
  
  public static void extended()
  {
    AnsiTerminal terminal = AnsiTerminal.get();
    terminal.color().brightRed().write("This text is bright red").newLine();
    
    terminal.color().rgb(0, 0, 100).write("This text is blue").newLine();
    
    Color green = new Color(0, 200, 0);
    terminal.color(green).write("This text is green").newLine();
    
    terminal.color(Color.BRIGHT_YELLOW).write("This text is yellow").newLine();
  }
}

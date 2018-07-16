package ch.rweiss.terminal;

public class ForegroundColorExample
{
  public static void main(String[] args)
  {
    AnsiTerminal terminal = AnsiTerminal.get();
    terminal.color().red().write("This text is red").newLine();  
    terminal.reset();
  }
}

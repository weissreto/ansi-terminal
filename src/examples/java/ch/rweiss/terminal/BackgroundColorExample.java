package ch.rweiss.terminal;

public class BackgroundColorExample
{
  public static void main(String[] args)
  {
    AnsiTerminal terminal = AnsiTerminal.get();
    terminal.backgroundColor().red().write("This text has a red background").newLine();
    terminal.reset();
  }
}

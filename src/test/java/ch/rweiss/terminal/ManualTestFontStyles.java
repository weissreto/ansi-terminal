package ch.rweiss.terminal;

public class ManualTestFontStyles extends AbstractManualTest
{
  public static void main(String[] args) throws Exception
  {
    new ManualTestFontStyles().main();
  }
  
  public void bold()
  {
    terminal.fontStyle().bold().write("Bold").newLine();
    terminal.reset().write("Not Bold").newLine().reset();
  }
  
  public void underline()
  {
    terminal.fontStyle().underline().write("Underline").newLine().reset();
  }
  
  public void negative()
  {
    terminal.fontStyle().negative().write("Negative").newLine().reset();
  }
}

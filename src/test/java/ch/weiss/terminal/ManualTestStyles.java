package ch.weiss.terminal;

public class ManualTestStyles extends AbstractManualTest
{
  public static void main(String[] args) throws Exception
  {
    new ManualTestStyles().main();
  }

  public void defaultStyle()
  {
    terminal.write("Default Style");
  }
  
  public void reset()
  {
    Style red = Style.create().withColor(Color.RED).toStyle();
    terminal.style(red).write("Red Style").newLine();
    terminal.reset().write("Reset to Default Style");
  }
  
  public void changeStyle()
  {
    Style red = Style.create().withColor(Color.RED).toStyle();
    Style underlineRed = Style.create().withColor(Color.RED).withFontStyle(FontStyle.UNDERLINE).toStyle();
    Style underlineRedYellowBackground = Style.create().withColor(Color.RED).withBackgroundColor(Color.YELLOW).withFontStyle(FontStyle.UNDERLINE).toStyle();
    terminal.style(red).write("Red").newLine().newLine();
    terminal.style(underlineRed).write("Underline Red").newLine().newLine();
    terminal.style(underlineRedYellowBackground).write("Underline Red with Yellow background").newLine().newLine();
    terminal.style(underlineRed).write("Underline Red").newLine().newLine();
    terminal.style(red).write("Red").newLine();
  }
}

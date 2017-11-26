package ch.weiss.terminal;

public class ManualTestColor extends AbstractManualTest
{
  public static void main(String[] args) throws Exception
  {
    new ManualTestColor().main();
  }
  
  public void rgbBackgroundColors()
  {
    int column = 0;
    for (int red = 0; red < 256; red = red + 32)
    {
      for (int green = 0; green < 256; green = green + 32)
      {
        for (int blue = 0;  blue < 256; blue = blue + 32)
        {
          terminal.backgroundColor().rgb(red, green, blue).write("X");
          column++;
          if (column%64 == 0)
          {
            terminal.newLine();
          }
        }
      }
    }
  }

  public void rgbForegroundColors()
  {
    int column = 0;
    for (int red = 0; red < 256; red = red + 32)
    {
      for (int green = 0; green < 256; green = green + 32)
      {
        for (int blue = 0;  blue < 256; blue = blue + 32)
        {
          terminal.color().rgb(red, green, blue).write("X");
          column++;
          if (column%64 == 0)
          {
            terminal.newLine();
          }
        }
      }
    }
  }

  public void standardBrightBackgroundColors()
  {
    terminal.backgroundColor().brightBlack().write("Black ").reset();
    terminal.backgroundColor().brightRed().write("Red ");
    terminal.backgroundColor().brightGreen().write("Green ");
    terminal.backgroundColor().brightYellow().write("Yellow ");
    terminal.backgroundColor().brightBlue().write("Blue ");
    terminal.backgroundColor().brightMagenta().write("Magenta ");
    terminal.backgroundColor().brightCyan().write("Cyan ");
    terminal.backgroundColor().brightWhite().write("White ").newLine().newLine().reset();
  }

  public void standardBackgroundColors()
  {
    terminal.backgroundColor().black().write("Black ").reset();
    terminal.backgroundColor().red().write("Red ");
    terminal.backgroundColor().green().write("Green ");
    terminal.backgroundColor().yellow().write("Yellow ");
    terminal.backgroundColor().blue().write("Blue ");
    terminal.backgroundColor().magenta().write("Magenta ");
    terminal.backgroundColor().cyan().write("Cyan ");
    terminal.backgroundColor().white().write("White ").newLine().newLine().reset();
  }

  public void standardBrightForegroundColors()
  {
    terminal.color().brightBlack().write("Black ").reset();
    terminal.color().brightRed().write("Red ");
    terminal.color().brightGreen().write("Green ");
    terminal.color().brightYellow().write("Yellow ");
    terminal.color().brightBlue().write("Blue ");
    terminal.color().brightMagenta().write("Magenta ");
    terminal.color().brightCyan().write("Cyan ");
    terminal.color().brightWhite().write("White ").newLine().newLine().reset();
  }

  public void standardForegroundColors()
  {
    terminal.color().black().write("Black ").reset();
    terminal.color().red().write("Red ");
    terminal.color().green().write("Green ");
    terminal.color().yellow().write("Yellow ");
    terminal.color().blue().write("Blue ");
    terminal.color().magenta().write("Magenta ");
    terminal.color().cyan().write("Cyan ");
    terminal.color().white().write("White ").newLine().newLine().reset();
  }
}

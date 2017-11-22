package ch.weiss.terminal;

public class TestAnsiTerminal
{
  private static final AnsiTerminal terminal = AnsiTerminal.get();
  
  public static void main(String[] args) throws InterruptedException
  {
    terminal.newLine();
    terminal.write("This tests the AnsiTerminal Class").newLine();
    terminal.newLine();
    
    title("Foreground Colors:");
    terminal.color().black().backgroundColor().white().write("Black").newLine().reset();
    terminal.color().red().write("Red").newLine().reset();
    terminal.color().blue().write("Blue").newLine().reset();
    terminal.color().cyan().write("Cyan").newLine().reset();
    terminal.color().green().write("Green").newLine().reset();
    terminal.color().magenta().write("Magenta").newLine().reset();
    terminal.color().white().write("White").newLine().reset();
    terminal.color().yellow().write("Yellow").newLine().reset();

    title("Bright Foreground Colors:");
    terminal.color().brightBlack().write("Black").newLine().reset();
    terminal.color().brightRed().write("Red").newLine().reset();
    terminal.color().brightBlue().write("Blue").newLine().reset();
    terminal.color().brightCyan().write("Cyan").newLine().reset();
    terminal.color().brightGreen().write("Green").newLine().reset();
    terminal.color().brightMagenta().write("Magenta").newLine().reset();
    terminal.color().brightWhite().write("White").newLine().reset();
    terminal.color().brightYellow().write("Yellow").newLine().reset();

    title("Background Colors:");
    terminal.backgroundColor().black().write("Black").newLine().reset();
    terminal.backgroundColor().red().write("Red").newLine().reset();
    terminal.backgroundColor().blue().write("Blue").newLine().reset();
    terminal.backgroundColor().cyan().write("Cyan").newLine().reset();
    terminal.backgroundColor().green().write("Green").newLine().reset();
    terminal.backgroundColor().magenta().write("Magenta").newLine().reset();
    terminal.backgroundColor().white().color().black().write("White").newLine().reset();
    terminal.backgroundColor().yellow().write("Yellow").newLine().reset();

    title("Bright Background Colors:");
    terminal.backgroundColor().brightBlack().write("Black").newLine().reset();
    terminal.backgroundColor().brightRed().write("Red").newLine().reset();
    terminal.backgroundColor().brightBlue().write("Blue").newLine().reset();
    terminal.backgroundColor().brightCyan().write("Cyan").newLine().reset();
    terminal.backgroundColor().brightGreen().write("Green").newLine().reset();
    terminal.backgroundColor().brightMagenta().write("Magenta").newLine().reset();
    terminal.backgroundColor().brightWhite().write("White").newLine().reset();
    terminal.backgroundColor().brightYellow().write("Yellow").newLine().reset();
        
    title("Styles:");
    terminal.style().bold().write("Bold").newLine().reset();
    terminal.style().underline().write("Italic").newLine().reset();

    title("Cursor:");
    
    terminal.write("      <- Up").newLine();
    terminal.write("      <- Empty").newLine();
    terminal.write("      <- Down").newLine();
    terminal.cursor().up().cursor().up().cursor().up().write("U");
    terminal.cursor().down().cursor().down().write("D").newLine();

    terminal.newLine();
    terminal.write("      <- Up").newLine();
    terminal.write("      <- Empty").newLine();
    terminal.write("      <- Down").newLine();

    terminal.cursor().up(3).write("U");
    terminal.cursor().down(2).write("D").newLine();
    
    terminal.newLine();
    terminal.write("Forward :").newLine();
    terminal.write("Backward:").newLine();
    terminal.cursor().up(2).cursor().forward(10).write("F1").cursor().forward(4).write("F2").cursor().forward(4).write("F3");
    terminal.cursor().down();
    terminal.cursor().backward(2).write("B3").cursor().backward(8).write("B2").cursor().backward(8).write("B1");

    Thread.sleep(5000);
    terminal.clear();
    terminal.cursor().position(10,40).write("Position(10,40)");
    terminal.cursor().previousLine(4).write("Previous Line");
    terminal.cursor().nextLine(8).write("Next Line").newLine();
    terminal.cursor().column(10).write("10").cursor().column(20).write("20");
    
    terminal.cursor().nextLine(8);
    title("Hide Cursor");
    terminal.cursor().hide();
    Thread.sleep(5000);
    title("Show Cursor");
    terminal.cursor().show().newLine();
    Thread.sleep(5000);
  }
  private static void title(String title)
  {
    terminal.newLine().style().underline().write(title).newLine().reset();
  }
}

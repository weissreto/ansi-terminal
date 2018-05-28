package ch.rweiss.terminal;

public class ManualTestOffScreen extends AbstractManualTest
{
  public static void main(String[] args) throws Exception
  {
    new ManualTestOffScreen().main();
  }

  public void simpleTest()
  {
    terminal.offScreen().on();
    terminal.color().yellow();
    terminal.backgroundColor().red();
    terminal.write("Hello");
    terminal.cursor().down(5);
    terminal.write("World");
    terminal.offScreen().syncToScreen();
  }
}

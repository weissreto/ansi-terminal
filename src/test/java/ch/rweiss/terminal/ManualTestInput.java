package ch.rweiss.terminal;

public class ManualTestInput extends AbstractManualTest
{
  public static void main(String[] args) throws Exception
  {
    new ManualTestInput().main();
  }
  
  public void input()
  {
    Key key;
    do
    {
      key = terminal.input().waitForKey();
      terminal.write(key.toString());
    } while (!key.toString().equals("x"));
  }
}

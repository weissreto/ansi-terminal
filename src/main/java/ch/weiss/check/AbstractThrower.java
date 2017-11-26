package ch.weiss.check;

abstract class AbstractThrower implements Thrower
{
  private String what;
  private String name;

  AbstractThrower(String what, String name)
  {
    this.what = what;
    this.name = name;
  }
  
  String what()
  {
    return what;
  }
  
  String name()
  {
    return name;
  }
}

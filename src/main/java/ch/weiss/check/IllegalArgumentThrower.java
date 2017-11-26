package ch.weiss.check;

class IllegalArgumentThrower extends AbstractThrower
{
  public IllegalArgumentThrower(String what, String name)
  {
    super(what, name);
  }

  @Override
  public void throwNow(String message)
  {
    throw new IllegalArgumentException(what()+" "+name()+" "+message);
  }
}

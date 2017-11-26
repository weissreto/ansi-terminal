package ch.weiss.check;

class IllegelStateThrower extends AbstractThrower
{

  IllegelStateThrower(String what, String name)
  {
    super(what, name);
  }

  @Override
  public void throwNow(String message)
  {
    throw new IllegalStateException(what()+" "+name()+" "+message);
  }

}

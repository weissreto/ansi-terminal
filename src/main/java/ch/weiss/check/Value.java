package ch.weiss.check;

public class Value
{
  private final Thrower thrower;
  
  Value(Thrower thrower)
  {
    this.thrower = thrower;
  }
  
  public ObjectCheck value(Object value)
  {
    return new ObjectCheck(value, thrower);
  }
  
  public IntCheck value(int value)
  {
    return new IntCheck(value, thrower);
  }
}
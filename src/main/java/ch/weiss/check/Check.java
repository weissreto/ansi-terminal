package ch.weiss.check;

public class Check
{
  public static final Value parameter(String parameterName)
  {
    return new Value(new IllegalArgumentThrower("Parameter", parameterName));
  }
  
  public static final Value field(String fieldName)
  {
    return new Value(new IllegelStateThrower("Field", fieldName));
  }  
}

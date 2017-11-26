package ch.weiss.check;

public class ObjectCheck extends AbstractCheck
{
  private Object value;

  ObjectCheck(Object value, Thrower thrower)
  {
    super(thrower);
    this.value = value;
  }
  
  public void isNotNull()
  {
    if (value == null)
    {
      fail("must not be null");
    }
  }
}

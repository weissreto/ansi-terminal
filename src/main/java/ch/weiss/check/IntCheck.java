package ch.weiss.check;

public class IntCheck extends AbstractCheck
{
  private int value;
  
  IntCheck(int value, Thrower thrower)
  {
    super(thrower);
    this.value = value;
  }
  
  public IntCheck isPositive()
  {
    if (value < 0)
    {
      fail("must be positive but is {0}", value);
    }
    return this;
  }

  public IntCheck isInRange(int lowerBoundInclusive, int upperBoundInclusive)
  {
    if (value < lowerBoundInclusive || value > upperBoundInclusive)
    {
      fail("must be between {0} and {1} but is {2}", lowerBoundInclusive, upperBoundInclusive, value);
    }
    return this;
  }
  
  public IntCheck isNotZero()
  {
    if (value == 0)
    {
      fail("must not be zero");
    }
    return this;
  }

  public void isSmallerOrEqualTo(int largerOrEqual)
  {
    if (value > largerOrEqual)
    {
      fail("must be smaller or equal to {0} but is {1}", largerOrEqual, value);
    }
  }
}

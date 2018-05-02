package ch.rweiss.terminal.table;

import ch.rweiss.check.Check;

class ColumnLayout
{
  private final int preferedWidth;
  private final int minWidth;
 
  ColumnLayout(int fixedWidth)
  {
    this(fixedWidth, fixedWidth);
  }
  
  ColumnLayout(int minWidth, int preferedWidth)
  {
    Check.parameter("minWidth").withValue(minWidth).isGreaterThan(0);
    Check.parameter("preferedWidth").withValue(preferedWidth).isGreaterOrEqualTo(minWidth);
    this.minWidth = minWidth;
    this.preferedWidth = preferedWidth;
  }

  boolean isFixed()
  {
    return preferedWidth == minWidth;
  }
  
  boolean isStrechy()
  {
    return minWidth < preferedWidth;
  }
  
  int getMinWidth()
  {
    return minWidth;
  }
 
  ColumnLayout setMinWidth(int minWidth)
  {
    Check.parameter("minWidth").withValue(minWidth).isLessThan(preferedWidth);
    return new ColumnLayout(minWidth, preferedWidth);
  }
  
  int getPreferedWidth()
  {
    return preferedWidth;
  }

  int getDeltaWidth()
  {
    return preferedWidth - minWidth;
  }
}
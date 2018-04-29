package ch.rweiss.terminal.table;

import ch.rweiss.check.Check;

class ColumnLayout
{
  private boolean fixed;
  private int minWidth;
  
  ColumnLayout(int minWidth, boolean fixed)
  {
    Check.parameter("minWidth").withValue(minWidth).isGreaterThan(0);
    this.minWidth = minWidth;
    this.fixed = fixed;
  }

  boolean isFixed()
  {
    return fixed;
  }
  
  int getMinWidth()
  {
    return minWidth;
  }
}
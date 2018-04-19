package ch.rweiss.terminal.table;

class ColumnLayout
{
  private int fixedWidth;

  void setFixedWith(int fixedWidth)
  {
    this.fixedWidth = fixedWidth;
  }
  
  int getPreferredWidth()
  {
    return fixedWidth;
  }
}
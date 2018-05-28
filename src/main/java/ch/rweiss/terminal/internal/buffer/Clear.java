package ch.rweiss.terminal.internal.buffer;

enum Clear
{
  TO_END(0),
  FROM_BEGIN(1),
  ALL(2);
  
  private int csiArgument;

  private Clear(int csiArgument)
  {
    this.csiArgument = csiArgument;
  }
  
  static Clear fromCsiArgument(int csiArgument)
  {
    for (Clear clear : values())
    {
      if (clear.csiArgument == csiArgument)
      {
        return clear;
      }
    }
    throw new IllegalArgumentException("Unknown csi clear argument "+csiArgument);
  }
}

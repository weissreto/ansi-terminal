package ch.weiss.terminal.table;

import java.util.ArrayList;
import java.util.List;

class Row
{
  private final List<Object> values = new ArrayList<>();
  
  public void addValue(Object value)
  {
    values.add(value);
  }

  public Object getValue(int pos)
  {
    return values.get(pos);
  }
}
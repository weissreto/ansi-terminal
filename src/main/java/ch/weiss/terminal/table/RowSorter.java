package ch.weiss.terminal.table;

import java.util.Comparator;

public class RowSorter<R>
{
  private final Column<R,?> column;
  private boolean ascending = true;

  RowSorter(Column<R,?> column)
  {
    this.column = column;
  }
  
  Comparator<R> getComparator()
  {
    return this::compare;
  }

  private int compare(R row1, R row2)
  {
    int result = column.compareValue(row1, row2);
    if (!ascending)
    {
      result = result * -1;
    }
    return result;
  }
  
  public void ascending()
  {
    ascending = true;
  }
  
  public void descending()
  {
    ascending = false;
  }
}
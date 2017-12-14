package ch.weiss.terminal.table;

import java.util.Comparator;

public class RowSorter
{
  private final Column column;
  private final int columnPos;
  private boolean ascending = true;

  RowSorter(Column column, int columnPos)
  {
    this.column = column;
    this.columnPos = columnPos;
  }
  
  Comparator<Row> getComparator()
  {
    return this::compare;
  }

  private int compare(Row row1, Row row2)
  {
    Object value1 = row1.getValue(columnPos);
    Object value2 = row2.getValue(columnPos);
    int result = column.compareValue(value1, value2);
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
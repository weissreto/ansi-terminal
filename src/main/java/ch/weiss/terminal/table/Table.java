package ch.weiss.terminal.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.weiss.terminal.AnsiTerminal;

public class Table
{
  private final List<Column> columns = new ArrayList<>();
  private final List<Row> rows = new ArrayList<>();
  private Row currentRow;
  AnsiTerminal term = AnsiTerminal.get();
  private RowSorter sorter;
  
  public void addColumn(Column column)
  {
    columns.add(column);
  }

  public void addRow()
  {
    currentRow = new Row();
    rows.add(currentRow);
  }

  public void addValue(Object value)
  {
    currentRow.addValue(value);
  }

  public void print()
  {
    sortRows();
    for (Column column : columns)
    {
      column.printTitle();
    }
    term.clear().lineToEnd();
    term.newLine();
    for (Row row : rows)
    {
      int colPos = 0;
      for (Column column : columns)
      {
        column.printCell(row.getValue(colPos++));
      }
      term.clear().lineToEnd();
      term.newLine();
    }
    term.clear().screenToEnd();
  }
  
  private void sortRows()
  {
    if (sorter == null)
    {
      return;
    }
    
    Collections.sort(rows, sorter.getComparator());
  }

  public RowSorter sortColumn(String columnTitle)
  {
    Column column = findColumn(columnTitle);
    if (column == null)
    {
      throw new IllegalArgumentException("Column with title "+columnTitle+" not found given by parameter columnTitle");
    }
    int pos = columns.indexOf(column);
    sorter = new RowSorter(column, pos);
    return sorter;
  }

  private Column findColumn(String columnTitle)
  {
    return columns
        .stream()
        .filter(column -> column.getTitle().equals(columnTitle))
        .findAny()
        .orElse(null);
  }
}

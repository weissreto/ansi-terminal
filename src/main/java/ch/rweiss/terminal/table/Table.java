package ch.rweiss.terminal.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import ch.rweiss.terminal.AnsiTerminal;
import ch.rweiss.terminal.table.Column.ColumnBuilder;

public class Table<R>
{
  private final List<Column<R,?>> columns = new ArrayList<>();
  private final List<R> rows = new ArrayList<>();
  AnsiTerminal term = AnsiTerminal.get();
  private RowSorter<R> sorter;
  
  public void addColumn(Column<R,?> column)
  {
    columns.add(column);
  }
  
  public <V> ColumnBuilder<R, V> createColumn(String title, int width, Function<R, V> valueProvider)
  {
    return Column.create(title, width, valueProvider);
  }

  public ColumnBuilder<R, R> createColumn(String title, int width)
  {
    return Column.create(title, width);
  }

  public void addRow(R row)
  {
    rows.add(row);
  }

  public void print()
  {
    sortRows();
    for (Column<R,?> column : columns)
    {
      column.printTitle();
    }
    term.clear().lineToEnd();
    term.newLine();
    for (R row : rows)
    {
      for (Column<R,?> column : columns)
      {
        column.printCell(row);
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

  public RowSorter<R> sortColumn(String columnTitle)
  {
    Column<R,?> column = findColumn(columnTitle);
    if (column == null)
    {
      throw new IllegalArgumentException("Column with title "+columnTitle+" not found given by parameter columnTitle");
    }
    sorter = new RowSorter<>(column);
    return sorter;
  }

  private Column<R,?> findColumn(String columnTitle)
  {
    return columns
        .stream()
        .filter(column -> column.getTitle().equals(columnTitle))
        .findAny()
        .orElse(null);
  }
}

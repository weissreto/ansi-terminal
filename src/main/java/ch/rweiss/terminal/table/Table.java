package ch.rweiss.terminal.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import ch.rweiss.terminal.AnsiTerminal;
import ch.rweiss.terminal.table.Column.ColumnBuilder;

public class Table<R>
{
  private final List<Column<R,?>> columns = new ArrayList<>();
  private final List<R> rows = new ArrayList<>();
  AnsiTerminal term = AnsiTerminal.get();
  private RowSorter<R> sorter;
  private int topVisibleRow= 0;
  
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
  
  public void setRows(Collection<R> rows)
  {
    clear();
    this.rows.addAll(rows);
  }
  
  public void clear()
  {
    rows.clear();
  }
    
  public void printSingleRow(R row)
  {
    clear();
    addRow(row);
    printWithoutHeader();
  }
  
  public void print()
  {
    print(-1);
  }

  public void printWithoutHeader()
  {
    layoutColumns();
    printRows(-1);
  }
  
  public void printTop()
  {
    int lines = term.cursor().maxPosition().line() - 
                term.cursor().position().line() -  
                1; // header line
    print(lines);
  }

  public void print(int lines)
  {
    layoutColumns();
    printHeader();
    printRows(lines);
  }


  private void layoutColumns()
  {
    int maxWidth = getMaxAvailableWidth();
    new Layouter<>(columns).layoutColumns(maxWidth);
  }
  
  private int getMaxAvailableWidth()
  {
    return term.cursor().maxPosition().column()-1;
  }

  private void printHeader()
  {
    for (Column<R,?> column : columns)
    {
      column.printTitle();
    }
    term.clear().lineToEnd();
    term.newLine();
  }
  
  public void scrollUp()
  {
    topVisibleRow--;
    ensureTopRowIsVisible();
  }

  public void scrollDown()
  {
    topVisibleRow++;
    ensureTopRowIsVisible();
  }

  public void srollPageDown()
  {
    topVisibleRow += getMaxAvailableHeight();
    ensureTopRowIsVisible();
  }

  public void scrollPageUp()
  {
    topVisibleRow -= getMaxAvailableHeight();
    ensureTopRowIsVisible();
  }

  private int getMaxAvailableHeight()
  {
    return term.cursor().maxPosition().line() - 5;
  }

  private void ensureTopRowIsVisible() 
  {
    if (topVisibleRow >= rows.size())
    {
      topVisibleRow = rows.size() - 1;
    }
    if (topVisibleRow < 0)
    {
      topVisibleRow = 0;
    }
  }

  private void printRows(int maxLines)
  {
    sortRows();
    int printedLines = 0;
    for (R row : rows.stream().skip(topVisibleRow).collect(Collectors.toList()))
    {
      int line = 0;
      boolean needMoreLines;
      do
      {
        needMoreLines = false;
        for (Column<R,?> column : columns)
        {
          needMoreLines = needMoreLines | column.printCell(row, line);
        }
        term.clear().lineToEnd();
        line = line + 1;
        printedLines = printedLines + 1;
        if (maxLines >= 0 && printedLines >= maxLines)
        {
          return;
        }
        term.newLine();
      } while (needMoreLines);      
    }
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

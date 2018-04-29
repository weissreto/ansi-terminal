package ch.rweiss.terminal.table;

import java.util.List;
import java.util.stream.Collectors;

class Layouter<R>
{
  private final List<Column<R, ?>> columns;

  public Layouter(List<Column<R, ?>> columns)
  {
    this.columns = columns;
  }

  public void layoutColumns(int maxWidth)
  {
    makeAllColumnsVisible();
    int currentWidth = hideRightColumnsIfToLessSpace(maxWidth);    
    expandNonFixedColumnsToGatherAllAvailableSpace(maxWidth-currentWidth);
  }
  
  private void makeAllColumnsVisible()
  {
    columns.forEach(column -> column.setVisible(true));
  }

  private int hideRightColumnsIfToLessSpace(int maxWidth)
  {
    int currentWidth = getCurrentWidthOfVisibleColumns();
    while (currentWidth > maxWidth)
    {
      hideRightestVisibleColumn();
      currentWidth = getCurrentWidthOfVisibleColumns();
    }
    return currentWidth;
  }

  private int getCurrentWidthOfVisibleColumns()
  {
    int currentWidth = getVisibleColumns()
        .stream()
        .mapToInt(column -> column.getLayout().getMinWidth())
        .sum();
    return currentWidth;
  }

  private void hideRightestVisibleColumn()
  {
    List<Column<R,?>> visibleColumns = getVisibleColumns();
    visibleColumns.get(visibleColumns.size()-1).setVisible(false);
  }

  private List<Column<R, ?>> getVisibleColumns()
  {
    return columns
        .stream()
        .filter(column -> column.isVisible())
        .collect(Collectors.toList());
  }

  private void expandNonFixedColumnsToGatherAllAvailableSpace(int expandWidth)
  {
    if (expandWidth >= 0)
    {
      int nonFixedColumns = countExpandableColumns();
      int widthPerColumn = expandWidth / nonFixedColumns;
      int mod = expandWidth % nonFixedColumns;
      for (Column<?,?> columnToExpand : getExpandableColumns())
      {
        int modWidth = 0;
        if (mod > 0)
        {
          modWidth = 1;
        }
        mod = mod - 1;
        int newWidth = columnToExpand.getLayout().getMinWidth()+widthPerColumn+modWidth;
        columnToExpand.setWidth(newWidth);
      }
    }
  }

  private List<Column<?, ?>> getExpandableColumns()
  {
    return columns
        .stream()
        .filter(column -> !column.getLayout().isFixed())
        .collect(Collectors.toList());
  }

  private int countExpandableColumns()
  {
    return (int)columns
        .stream()
        .filter(column -> !column.getLayout().isFixed())
        .count();
  }
}

package ch.rweiss.terminal.table;

import java.util.List;
import java.util.stream.Collectors;

class Layouter<R>
{
  private static final int ROUND = 50_000;
  private static final int CONSTANT = ROUND * 2;
  private final List<Column<R, ?>> columns;

  public Layouter(List<Column<R, ?>> columns)
  {
    this.columns = columns;
  }

  public void layoutColumns(int maxWidth)
  {
    resetColumns();
    shrinkStrechyColumnsToFitSpace(maxWidth);
    int currentWidth = hideRightColumnsToFitSpace(maxWidth);    
    expandStrechyColumnsToGatherAllAvailableSpace(maxWidth-currentWidth);
  }
  
  private void shrinkStrechyColumnsToFitSpace(int maxWidth)
  {
    int currentWidth = getCurrentWidthOfVisibleColumns();
    if (currentWidth <= maxWidth)
    {
      return;
    }
    int minWidth = getMinWidthOfAllColumns();
    if (minWidth > maxWidth)
    {
      getStrechyColumns().forEach(column -> column.setWidth(column.getLayout().getMinWidth()));
      return;
    }
    int totalShrinkWidth = currentWidth - maxWidth;
    int deltaWidthWeigthSum = getStrechyColumns().stream().mapToInt(column -> column.getLayout().getDeltaWidth()).sum();
    int shrinkWidthPerWeight = totalShrinkWidth * CONSTANT / deltaWidthWeigthSum;
    int mod = totalShrinkWidth - shrinkWidthPerWeight * deltaWidthWeigthSum / CONSTANT;
    for (Column<?,?> shrinkColumn : getStrechyColumns())
    {
      int deltaWidthWeigth = shrinkColumn.getLayout().getDeltaWidth();
      int preferedWidth = shrinkColumn.getLayout().getPreferedWidth();
      int shrinkWidth =  (shrinkWidthPerWeight * deltaWidthWeigth + ROUND) / CONSTANT;
      int newWidth = preferedWidth - shrinkWidth;
      if (newWidth > shrinkColumn.getLayout().getMinWidth())
      {
        if (mod > 0)
        {
          newWidth = newWidth - 1;
          mod = mod - 1;
        }
      }
      shrinkColumn.setWidth(newWidth);
    }
  }

  private int getMinWidthOfAllColumns()
  {
    return columns
        .stream()
        .mapToInt(column -> column.getLayout().getMinWidth())
        .sum();
  }

  private void resetColumns()
  {
    columns.forEach(this::resetColumn);
  }
  
  private void resetColumn(Column<R,?> column)
  {
    column.setVisible(true);
    column.setWidth(column.getLayout().getPreferedWidth());
  }

  private int hideRightColumnsToFitSpace(int maxWidth)
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
        .mapToInt(column -> column.getWidth())
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

  private void expandStrechyColumnsToGatherAllAvailableSpace(int expandWidth)
  {
    if (expandWidth >= 0)
    {
      int preferedWidthWeightSum = getStrechyColumns().stream().mapToInt(column -> column.getLayout().getPreferedWidth()).sum();
      int expandWidthPerWeight = expandWidth * CONSTANT / preferedWidthWeightSum;
      int mod = expandWidth - expandWidthPerWeight * preferedWidthWeightSum / CONSTANT;
      for (Column<?,?> expandColumn : getStrechyColumns())
      {
        int preferedWidthWeight = expandColumn.getLayout().getPreferedWidth();
        int expandColumnWidth = (expandWidthPerWeight * preferedWidthWeight + ROUND) / CONSTANT;
        int newWidth = expandColumn.getWidth() + expandColumnWidth;
        if (mod > 0)
        {
          newWidth = newWidth + 1;
          mod = mod - 1;
        }
        expandColumn.setWidth(newWidth);
      }
    }
  }

  private List<Column<?, ?>> getStrechyColumns()
  {
    return columns
        .stream()
        .filter(column -> column.getLayout().isStrechy())
        .collect(Collectors.toList());
  }
}

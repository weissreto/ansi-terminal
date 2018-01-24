package ch.rweiss.terminal.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.rweiss.check.Check;
import ch.rweiss.terminal.graphics.Graphics;
import ch.rweiss.terminal.graphics.Point;
import ch.rweiss.terminal.graphics.Rectangle;

public class Grid extends Widget
{
  private final int rows;
  private final int columns;
  private final List<Widget> cells;
  
  public Grid(int rows, int columns, List<? extends Widget> cells)
  {
    Check.parameter("rows").withValue(rows).isPositive().isNotZero();
    Check.parameter("columns").withValue(columns).isPositive().isNotZero();
    Check.parameter("cells").withValue(cells).isNotNull();
    if (cells.size() != rows*columns)
    {
      throw new IllegalArgumentException("Parameter cells must have a size of "+rows*columns);
    }
    this.rows = rows;
    this.columns = columns;
    this.cells = Collections.unmodifiableList(new ArrayList<>(cells));
  }
      
  @Override
  public void bounds(Rectangle bounds)
  {
    int sectionWidth = bounds.width()/columns;
    int sectionWidthPart = bounds.width()%columns;
    int sectionHeight = bounds.height()/rows;
    int sectionHeightPart = bounds.height()%rows;
    
    int row=0; 
    int column=0;
   
    for (Widget cell : cells)
    {
      if (cell != null)
      {
        int w = sectionWidth;
        int h = sectionHeight;
        int x = column*sectionWidth;
        int y = row*sectionHeight;
        if (row < sectionHeightPart)
        {
          h = h + 1;
          y = y + row;
        }
        else
        {
          y = y + sectionHeightPart;
        }
        if (column < sectionWidthPart)
        {
          w = w + 1;
          x = x + column;
        }
        else
        {
          x = x + sectionWidthPart;
        }
        Point topLeft = bounds.topLeft().move(x, y);
        Point bottomRight = topLeft.move(w-1, h-1);
        Rectangle cellBounds = new Rectangle(topLeft, bottomRight);
        cell.bounds(cellBounds);
      }
      
      column++;
      if (column >= columns)
      {
        column=0;
        row++;
      }      
    }
  }
  
  @Override
  public void paint(Graphics graphics)
  {
    cells.forEach(widget -> widget.paint(graphics));
  }
}

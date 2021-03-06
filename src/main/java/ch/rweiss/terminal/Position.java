package ch.rweiss.terminal;

import java.util.Objects;

import ch.rweiss.check.Check;

/**
 * Position on the terminal. 
 * The position on the top left is line=1, column=1. 
 * Note that it is possible to have line=0 or column=0 values. 
 * This means that the terminal window is to small to have at least one line or column.    
 * @author Reto Weiss
 * @since 0.2
 */
public class Position
{
  private final int line;
  private final int column;
  
  public Position(int line, int column)
  {
    Check.parameter("line").withValue(line).isPositive();
    Check.parameter("column").withValue(column).isPositive();
    this.line = line;
    this.column = column;
  }
  
  public int line()
  {
    return line;
  }
  
  public int column()
  {
    return column;
  }
  
  @Override
  public String toString()
  {
    return "Position[line="+line+", column="+column+"]";
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if (obj == this)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (obj.getClass() != Position.class)
    {
      return false;
    }
    Position other = (Position)obj;
    return Objects.equals(line, other.line) &&
           Objects.equals(column, other.column);
  }
  
  @Override
  public int hashCode()
  {
    return Objects.hash(line, column);
  }
}

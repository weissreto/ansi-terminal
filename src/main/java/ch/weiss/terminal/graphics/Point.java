package ch.weiss.terminal.graphics;

public class Point
{
  private final int x;
  private final int y;
  
  public Point(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
  
  public int x()
  {
    return x;
  }
  
  public int y()
  {
    return y;
  }
  
  int column()
  {
    return x+1;
  }
  
  int line()
  {
    return y+1;
  }
  
  @Override
  public String toString()
  {
    return "["+x+","+y+"]";
  }

  public Point moveTo(Direction direction, int distance)
  {
    switch(direction)
    {
      case UP:
        return new Point(x, y-distance);
      case DOWN:
        return new Point(x, y+distance);
      case RIGHT:
        return new Point(x+distance, y);
      default:
        return new Point(x-distance, y);
    }
  }
}

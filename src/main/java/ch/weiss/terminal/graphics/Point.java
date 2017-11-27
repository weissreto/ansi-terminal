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
        return move(0, -distance);
      case DOWN:
        return move(0, +distance);
      case RIGHT:
        return move(distance, 0);
      default:
        return move(-distance, 0);
    }
  }
  
  public Point move(int deltaX, int deltaY)
  {
    return new Point(x+deltaX, y+deltaY);
  }
}

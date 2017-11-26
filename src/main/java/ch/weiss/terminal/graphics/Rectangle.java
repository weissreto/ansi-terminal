package ch.weiss.terminal.graphics;

import ch.weiss.check.Check;

public class Rectangle
{
  private final Point topLeft;
  private final Point bottomRight;
  
  public Rectangle(Point topLeft, Point bottomRight)
  {
    Check.parameter("topLeft").value(topLeft).isNotNull();
    Check.parameter("bottomRight").value(bottomRight).isNotNull();
    Check.parameter("topLeft.x()").value(topLeft.x()).isSmallerOrEqualTo(bottomRight.x());
    Check.parameter("topLeft.y()").value(topLeft.y()).isSmallerOrEqualTo(bottomRight.y());
    this.topLeft = topLeft;
    this.bottomRight = bottomRight;
  }
  
  public Point topLeft()
  {
    return topLeft;
  }
  
  public Point bottomRight()
  {
    return bottomRight;
  }
  
  public int leftX()
  {
    return topLeft.x();
  }
  
  public int rightX()
  {
    return bottomRight.x();
  }
  
  public int topY()
  {
    return topLeft.y();
  }
  
  public int bottomY()
  {
    return bottomRight().y();
  }
  
  public int width()
  {
    return rightX() - leftX() + 1;
  }
  
  public int height()
  {
    return bottomY() - topY() + 1;
  }

  public Point topRight()
  {
    return new Point(rightX(), topY());
  }

  public Point bottomLeft()
  {
    return new Point(leftX(), bottomY());
  }
}

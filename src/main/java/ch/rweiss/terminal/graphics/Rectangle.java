package ch.rweiss.terminal.graphics;

import ch.rweiss.check.Check;

public class Rectangle
{
  private final Point topLeft;
  private final Point bottomRight;
  
  public Rectangle(Point topLeft, Point bottomRight)
  {
    Check.parameter("topLeft").withValue(topLeft).isNotNull();
    Check.parameter("bottomRight").withValue(bottomRight).isNotNull();
    Check.parameter("topLeft.x()").withValue(topLeft.x()).isLessOrEqualTo(bottomRight.x());
    Check.parameter("topLeft.y()").withValue(topLeft.y()).isLessOrEqualTo(bottomRight.y());
    this.topLeft = topLeft;
    this.bottomRight = bottomRight;
  }
  
  public Rectangle(Point topLeft, int width, int height)
  {
    this(topLeft, new Point(topLeft.x()+width, topLeft.y()+height));
  }

  public Point topLeft()
  {
    return topLeft;
  }
  
  public Point bottomRight()
  {
    return bottomRight;
  }
  
  public Point topRight()
  {
    return new Point(rightX(), topY());
  }

  public Point bottomLeft()
  {
    return new Point(leftX(), bottomY());
  }
  
  public Point centerLeft()
  {
    return new Point(leftX(), topY() + height()/2); 
  }

  public Point centerRight()
  {
    return new Point(rightX(), topY() + height()/2); 
  }
  
  public Point centerTop()
  {
    return new Point(leftX() + width()/2, topY()); 
  }

  public Point centerBottom()
  {
    return new Point(leftX() + width()/2, bottomY()); 
  }

  public Point center()
  {
    return new Point(leftX()+width()/2, topY()+height()/2);
  }
  
  public Rectangle moveTo(Direction direction, int distance)
  {
    return new Rectangle(topLeft.moveTo(direction, distance), bottomRight.moveTo(direction, distance));
  }
  
  public Rectangle move(int deltaX, int deltaY)
  {
    return new Rectangle(topLeft.move(deltaX, deltaY), bottomRight.move(deltaX, deltaY));
  }
  
  public Rectangle moveTopLeftTo(Direction direction, int distance)
  {
    return new Rectangle(topLeft.moveTo(direction, distance), bottomRight);
  }

  public Rectangle moveTopLeft(int deltaX, int deltaY)
  {
    return new Rectangle(topLeft.move(deltaX, deltaY), bottomRight);
  }
  
  public Rectangle moveBottomRightTo(Direction direction, int distance)
  {
    return new Rectangle(topLeft, bottomRight.moveTo(direction, distance));
  }

  public Rectangle moveBottomRight(int deltaX, int deltaY)
  {
    return new Rectangle(topLeft, bottomRight.move(deltaX, deltaY));
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
    return rightX() - leftX();
  }
  
  public int height()
  {
    return bottomY() - topY();
  }
}

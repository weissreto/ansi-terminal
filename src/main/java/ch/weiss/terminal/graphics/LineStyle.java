package ch.weiss.terminal.graphics;

import ch.weiss.check.Check;

public class LineStyle
{
  public static final LineStyle SINGLE_LINE = new LineStyle('\u250C','\u2500','\u2510','\u2502','\u2518','\u2500','\u2514', '\u2502');
  public static final LineStyle DOUBLE_LINE = new LineStyle('\u2554','\u2550','\u2557','\u2551','\u255D','\u2550','\u255A', '\u2551');
  public static final LineStyle X = new LineStyle('X');
  public static final LineStyle STAR = new LineStyle('*');
  public static final LineStyle PLUS = new LineStyle('+');
  public static final LineStyle DOT = new LineStyle('.');
  
  private final char topLeft;
  private final char topRight;
  private final char bottomRight;
  private final char bottomLeft;
  private final char top;
  private final char right;
  private final char bottom;
  private final char left;

  public LineStyle(char singleCharacter)
  {
    this(singleCharacter, singleCharacter, singleCharacter, singleCharacter, singleCharacter, singleCharacter, singleCharacter, singleCharacter);
  }

  public LineStyle(char topLeft, char top, char topRight, char right, char bottomRight, char bottom, char bottomLeft, char left)
  {
    this.topLeft = topLeft;
    this.top = top;
    this.topRight = topRight;
    this.right = right;
    this.bottomRight = bottomRight;
    this.bottom = bottom;
    this.bottomLeft = bottomLeft;
    this.left = left;
  }
  
  public char topLeft()
  {
    return topLeft;
  }
  
  public char top()
  {
    return top;
  }
  
  public char topRight()
  {
    return topRight;
  }
  
  public char right()
  {
    return right;
  }
  
  public char bottomRight()
  {
    return bottomRight;
  }
  
  public char bottom()
  {
    return bottom;
  }
  
  public char bottomLeft()
  {
    return bottomLeft;
  }
  
  public char left()
  {
    return left;
  }

  public char forDirection(Direction direction)
  {
    Check.parameter("direction").withValue(direction).isNotNull();
    if (direction == Direction.UP || direction == Direction.DOWN)
    {
      return left;
    }
    return top;
  }

  public char forAllDirections()
  {
    return top;
  }
}

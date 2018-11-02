package ch.rweiss.terminal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class TestStyledText
{
  private static final Style BLUE = Style.create().withColor(Color.BLUE).toStyle();
  private static final Style RED = Style.create().withColor(Color.RED).toStyle();
  private static final Style GREEN = Style.create().withColor(Color.GREEN).toStyle();

  @Test
  public void nullArg()
  {
    assertThatThrownBy(() ->new StyledText(null)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void textOnly()
  {
    StyledText text = new StyledText("Hello World");
    assertThat(text.parts()).isNotNull().hasSize(1);
    assertThat(text.parts().get(0).text()).isEqualTo("Hello World");
    assertThat(text.parts().get(0).style()).isNull();
  }

  @Test
  public void textAndStyle()
  {
    StyledText text = new StyledText("Hello World", BLUE);
    assertThat(text.parts()).isNotNull().hasSize(1);
    assertThat(text.parts().get(0).text()).isEqualTo("Hello World");
    assertThat(text.parts().get(0).style()).isEqualTo(BLUE);
  }
  
  @Test
  public void length()
  {
    StyledText text = new StyledText("Hello", BLUE);
    assertThat(text.length()).isEqualTo(5);
    text = text.append("World", RED);
    assertThat(text.length()).isEqualTo(10);
  }
  
  @Test
  public void appendTextOnly()
  {
    StyledText text = new StyledText("Hello", BLUE);
    assertThat(text.length()).isEqualTo(5);
    text = text.append("World");
    assertThat(text.parts()).isNotNull().hasSize(1);
    assertThat(text.parts().get(0).text()).isEqualTo("HelloWorld");
    assertThat(text.parts().get(0).style()).isEqualTo(BLUE);
  }

  @Test
  public void appendTextAndSameStyle()
  {
    StyledText text = new StyledText("Hello", BLUE);
    assertThat(text.length()).isEqualTo(5);
    text = text.append("World", BLUE);
    assertThat(text.parts()).isNotNull().hasSize(1);
    assertThat(text.parts().get(0).text()).isEqualTo("HelloWorld");
    assertThat(text.parts().get(0).style()).isEqualTo(BLUE);
  }

  @Test
  public void appendTextAndDifferentStyle()
  {
    StyledText text = new StyledText("Hello", BLUE);
    assertThat(text.length()).isEqualTo(5);
    text = text.append("World", RED);
    assertThat(text.parts()).isNotNull().hasSize(2);
    assertThat(text.parts().get(0).text()).isEqualTo("Hello");
    assertThat(text.parts().get(0).style()).isEqualTo(BLUE);
    assertThat(text.parts().get(1).text()).isEqualTo("World");
    assertThat(text.parts().get(1).style()).isEqualTo(RED);
  }

  @Test
  public void appendStyledText()
  {
    StyledText text = new StyledText("Hello", BLUE);
    assertThat(text.length()).isEqualTo(5);
    text = text.append(new StyledText("World", RED));
    assertThat(text.parts()).isNotNull().hasSize(2);
    assertThat(text.parts().get(0).text()).isEqualTo("Hello");
    assertThat(text.parts().get(0).style()).isEqualTo(BLUE);
    assertThat(text.parts().get(1).text()).isEqualTo("World");
    assertThat(text.parts().get(1).style()).isEqualTo(RED);
    
    assertThatThrownBy(() -> new StyledText("Hello").append((StyledText)null)).isInstanceOf(IllegalArgumentException.class);
  }
  
  @Test
  public void appendLeft()
  {
    StyledText text = new StyledText("");
    text = text.appendLeft("Hi");
    assertThat(text.parts()).isNotNull().hasSize(1);
    assertThat(text.parts().get(0).text()).isEqualTo("Hi");
    assertThat(text.parts().get(0).style()).isNull();
    
    text = new StyledText("World", BLUE);
    text = text.appendLeft("Hello");
    assertThat(text.parts()).isNotNull().hasSize(1);
    assertThat(text.parts().get(0).text()).isEqualTo("HelloWorld");
    assertThat(text.parts().get(0).style()).isEqualTo(BLUE);
  }

  @Test
  public void left()
  {
    StyledText text = new StyledText("Hello", BLUE).append("World", RED);
    
    StyledText left = text.left(10);
    assertThat(left).isSameAs(text);
    
    left = text.left(9);
    assertThat(left).isNotSameAs(text);
    assertThat(left.length()).isEqualTo(9);
    assertThat(left.parts()).isNotNull().hasSize(2);
    assertThat(left.parts().get(0).text()).isEqualTo("Hello");
    assertThat(left.parts().get(0).style()).isEqualTo(BLUE);
    assertThat(left.parts().get(1).text()).isEqualTo("Worl");
    assertThat(left.parts().get(1).style()).isEqualTo(RED);

    left = text.left(6);
    assertThat(left.length()).isEqualTo(6);
    assertThat(left.parts()).isNotNull().hasSize(2);
    assertThat(left.parts().get(0).text()).isEqualTo("Hello");
    assertThat(left.parts().get(0).style()).isEqualTo(BLUE);
    assertThat(left.parts().get(1).text()).isEqualTo("W");
    assertThat(left.parts().get(1).style()).isEqualTo(RED);

    
    left = text.left(5);
    assertThat(left.length()).isEqualTo(5);
    assertThat(left.parts()).isNotNull().hasSize(1);
    assertThat(left.parts().get(0).text()).isEqualTo("Hello");
    assertThat(left.parts().get(0).style()).isEqualTo(BLUE);

    left = text.left(4);
    assertThat(left.length()).isEqualTo(4);
    assertThat(left.parts()).isNotNull().hasSize(1);
    assertThat(left.parts().get(0).text()).isEqualTo("Hell");
    assertThat(left.parts().get(0).style()).isEqualTo(BLUE);
    
    left = text.left(0);
    assertThat(left.length()).isEqualTo(0);
    assertThat(left.parts()).isNotNull().hasSize(1);
    assertThat(left.parts().get(0).text()).isEqualTo("");
    assertThat(left.parts().get(0).style()).isEqualTo(BLUE);
    
    assertThatThrownBy(() -> text.left(-1)).isInstanceOf(IllegalArgumentException.class);
  }
  
  @Test
  public void right()
  {
    StyledText text = new StyledText("Hello", BLUE).append("World", RED);
    
    StyledText right = text.right(10);
    assertThat(right).isSameAs(text);
    
    right = text.right(9);
    assertThat(right).isNotSameAs(text);
    assertThat(right.length()).isEqualTo(9);
    assertThat(right.parts()).isNotNull().hasSize(2);
    assertThat(right.parts().get(0).text()).isEqualTo("ello");
    assertThat(right.parts().get(0).style()).isEqualTo(BLUE);
    assertThat(right.parts().get(1).text()).isEqualTo("World");
    assertThat(right.parts().get(1).style()).isEqualTo(RED);

    right = text.right(6);
    assertThat(right.length()).isEqualTo(6);
    assertThat(right.parts()).isNotNull().hasSize(2);
    assertThat(right.parts().get(0).text()).isEqualTo("o");
    assertThat(right.parts().get(0).style()).isEqualTo(BLUE);
    assertThat(right.parts().get(1).text()).isEqualTo("World");
    assertThat(right.parts().get(1).style()).isEqualTo(RED);

    
    right = text.right(5);
    assertThat(right.length()).isEqualTo(5);
    assertThat(right.parts()).isNotNull().hasSize(1);
    assertThat(right.parts().get(0).text()).isEqualTo("World");
    assertThat(right.parts().get(0).style()).isEqualTo(RED);

    right = text.right(4);
    assertThat(right.length()).isEqualTo(4);
    assertThat(right.parts()).isNotNull().hasSize(1);
    assertThat(right.parts().get(0).text()).isEqualTo("orld");
    assertThat(right.parts().get(0).style()).isEqualTo(RED);
    
    right = text.right(0);
    assertThat(right.length()).isEqualTo(0);
    assertThat(right.parts()).isNotNull().hasSize(1);
    assertThat(right.parts().get(0).text()).isEqualTo("");
    assertThat(right.parts().get(0).style()).isEqualTo(RED);
    
    assertThatThrownBy(() -> text.right(-1)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void sub_ofTwoParts()
  {
    StyledText text = new StyledText("Hello", BLUE).append("World", RED);
    
    StyledText subtext = text.sub(0, 10);
    assertThat(subtext).isSameAs(text);
    
    subtext = text.sub(0, 9);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(9);
    assertThat(subtext.parts()).isNotNull().hasSize(2);
    assertThat(subtext.parts().get(0).text()).isEqualTo("Hello");
    assertThat(subtext.parts().get(0).style()).isEqualTo(BLUE);
    assertThat(subtext.parts().get(1).text()).isEqualTo("Worl");
    assertThat(subtext.parts().get(1).style()).isEqualTo(RED);
    
    subtext = text.sub(1, 9);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(9);
    assertThat(subtext.parts()).isNotNull().hasSize(2);
    assertThat(subtext.parts().get(0).text()).isEqualTo("ello");
    assertThat(subtext.parts().get(0).style()).isEqualTo(BLUE);
    assertThat(subtext.parts().get(1).text()).isEqualTo("World");
    assertThat(subtext.parts().get(1).style()).isEqualTo(RED);

    subtext = text.sub(1, 8);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(8);
    assertThat(subtext.parts()).isNotNull().hasSize(2);
    assertThat(subtext.parts().get(0).text()).isEqualTo("ello");
    assertThat(subtext.parts().get(0).style()).isEqualTo(BLUE);
    assertThat(subtext.parts().get(1).text()).isEqualTo("Worl");
    assertThat(subtext.parts().get(1).style()).isEqualTo(RED);

    subtext = text.sub(3, 4);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(4);
    assertThat(subtext.parts()).isNotNull().hasSize(2);
    assertThat(subtext.parts().get(0).text()).isEqualTo("lo");
    assertThat(subtext.parts().get(0).style()).isEqualTo(BLUE);
    assertThat(subtext.parts().get(1).text()).isEqualTo("Wo");
    assertThat(subtext.parts().get(1).style()).isEqualTo(RED);
    
    subtext = text.sub(0, 4);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(4);
    assertThat(subtext.parts()).isNotNull().hasSize(1);
    assertThat(subtext.parts().get(0).text()).isEqualTo("Hell");
    assertThat(subtext.parts().get(0).style()).isEqualTo(BLUE);

    subtext = text.sub(1, 3);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(3);
    assertThat(subtext.parts()).isNotNull().hasSize(1);
    assertThat(subtext.parts().get(0).text()).isEqualTo("ell");
    assertThat(subtext.parts().get(0).style()).isEqualTo(BLUE);

    subtext = text.sub(5, 4);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(4);
    assertThat(subtext.parts()).isNotNull().hasSize(1);
    assertThat(subtext.parts().get(0).text()).isEqualTo("Worl");
    assertThat(subtext.parts().get(0).style()).isEqualTo(RED);

    subtext = text.sub(6, 3);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(3);
    assertThat(subtext.parts()).isNotNull().hasSize(1);
    assertThat(subtext.parts().get(0).text()).isEqualTo("orl");
    assertThat(subtext.parts().get(0).style()).isEqualTo(RED);
  }

  @Test
  public void sub_ofThreeParts()
  {
    StyledText text = new StyledText("One", BLUE).append("Two", RED).append("Three", GREEN);

    StyledText subtext = text.sub(4, 2);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(2);
    assertThat(subtext.parts()).isNotNull().hasSize(1);
    assertThat(subtext.parts().get(0).text()).isEqualTo("wo");
    assertThat(subtext.parts().get(0).style()).isEqualTo(RED);

    subtext = text.sub(2, 5);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(5);
    assertThat(subtext.parts()).isNotNull().hasSize(3);
    assertThat(subtext.parts().get(0).text()).isEqualTo("e");
    assertThat(subtext.parts().get(0).style()).isEqualTo(BLUE);
    assertThat(subtext.parts().get(1).text()).isEqualTo("Two");
    assertThat(subtext.parts().get(1).style()).isEqualTo(RED);
    assertThat(subtext.parts().get(2).text()).isEqualTo("T");
    assertThat(subtext.parts().get(2).style()).isEqualTo(GREEN);
    
    subtext = text.sub(2, 0);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(0);
    assertThat(subtext.parts()).isNotNull().hasSize(1);
    assertThat(subtext.parts().get(0).text()).isEqualTo("");
    assertThat(subtext.parts().get(0).style()).isEqualTo(BLUE);
    
    subtext = text.sub(0, 0);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(0);
    assertThat(subtext.parts()).isNotNull().hasSize(1);
    assertThat(subtext.parts().get(0).text()).isEqualTo("");
    assertThat(subtext.parts().get(0).style()).isEqualTo(BLUE);
    
    subtext = text.sub(5, 0);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(0);
    assertThat(subtext.parts()).isNotNull().hasSize(1);
    assertThat(subtext.parts().get(0).text()).isEqualTo("");
    assertThat(subtext.parts().get(0).style()).isEqualTo(RED);

    subtext = text.sub(9, 0);
    assertThat(subtext).isNotSameAs(text);
    assertThat(subtext.length()).isEqualTo(0);
    assertThat(subtext.parts()).isNotNull().hasSize(1);
    assertThat(subtext.parts().get(0).text()).isEqualTo("");
    assertThat(subtext.parts().get(0).style()).isEqualTo(GREEN);
  }
  
  @Test
  public void indexOf()
  {
    final StyledText empty = new StyledText("");
    
    assertThatThrownBy(() -> empty.indexOf(-1, '\n')).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> empty.indexOf(0, '\n')).isInstanceOf(IllegalArgumentException.class);
    
    final StyledText text = new StyledText("One", BLUE).append("Two", RED).append("Three", GREEN);
  
    assertThat(text.indexOf(0, 'e')).isEqualTo(2);
    assertThat(text.indexOf(2, 'e')).isEqualTo(2);
    assertThat(text.indexOf(3, 'e')).isEqualTo(9);
    assertThat(text.indexOf(9, 'e')).isEqualTo(9);
    assertThat(text.indexOf(10, 'e')).isEqualTo(10);
    assertThat(text.indexOf(0, 'y')).isEqualTo(-1);
    
    assertThatThrownBy(() -> text.indexOf(11, 'e')).isInstanceOf(IllegalArgumentException.class);
  }
  
  @Test
  public void equals()
  {
    StyledText hi = new StyledText("hi");
    assertThat(hi)
        .isEqualTo(hi)
        .isEqualTo(new StyledText("hi"))
        .isNotEqualTo(new StyledText("world"))
        .isNotEqualTo(FontStyle.BOLD)
        .isNotEqualTo(null);
    
    StyledText complex = new StyledText("One", BLUE).append("Two", RED).append("Three", GREEN);
    assertThat(complex)
        .isEqualTo(complex)
        .isEqualTo(   new StyledText("One", BLUE).append("Two", RED ).append("Three", GREEN))
        .isNotEqualTo(new StyledText("One", RED ).append("Two", RED ).append("Three", GREEN))
        .isNotEqualTo(new StyledText("On",  BLUE).append("Two", RED ).append("Three", GREEN))
        .isNotEqualTo(new StyledText("One", BLUE).append("Two", BLUE).append("Three", GREEN))
        .isNotEqualTo(new StyledText("One", BLUE).append("Tw",  RED ).append("Three", GREEN))
        .isNotEqualTo(new StyledText("One", BLUE).append("Two", RED ).append("Thre",  GREEN))
        .isNotEqualTo(new StyledText("One", BLUE).append("Two", RED ).append("Three", RED  ));
  }
  
  @Test
  public void _hashCode()
  {
    StyledText hi = new StyledText("hi");
    assertThat(hi.hashCode())
        .isEqualTo(hi.hashCode())
        .isEqualTo(new StyledText("hi").hashCode())
        .isNotEqualTo(new StyledText("world").hashCode())
        .isNotEqualTo(FontStyle.BOLD.hashCode());

    StyledText complex = new StyledText("One", BLUE).append("Two", RED).append("Three", GREEN);
    assertThat(complex.hashCode())
        .isEqualTo(complex.hashCode())
        .isEqualTo(   new StyledText("One", BLUE).append("Two", RED ).append("Three", GREEN).hashCode())
        .isNotEqualTo(new StyledText("One", RED ).append("Two", RED ).append("Three", GREEN).hashCode())
        .isNotEqualTo(new StyledText("On",  BLUE).append("Two", RED ).append("Three", GREEN).hashCode())
        .isNotEqualTo(new StyledText("One", BLUE).append("Two", BLUE).append("Three", GREEN).hashCode())
        .isNotEqualTo(new StyledText("One", BLUE).append("Tw",  RED ).append("Three", GREEN).hashCode())
        .isNotEqualTo(new StyledText("One", BLUE).append("Two", RED ).append("Thre",  GREEN).hashCode())
        .isNotEqualTo(new StyledText("One", BLUE).append("Two", RED ).append("Three", RED  ).hashCode());
  }
  
  @Test
  public void _toString()
  {
    assertThat(new StyledText("hi").toString()).isEqualTo("hi");
    
    StyledText complex = new StyledText("One", BLUE).append("Two", RED).append("Three", GREEN);
    assertThat(complex.toString()).isEqualTo("OneTwoThree");
  }
}

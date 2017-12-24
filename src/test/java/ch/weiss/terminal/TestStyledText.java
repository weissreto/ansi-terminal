package ch.weiss.terminal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class TestStyledText
{
  private static final Style BLUE = Style.create().withColor(Color.BLUE).toStyle();
  private static final Style RED = Style.create().withColor(Color.RED).toStyle();

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
  public void abbreviate()
  {
    StyledText text = new StyledText("Hello", BLUE).append("World", RED);
    
    StyledText abbreviate = text.abbreviate(10);
    assertThat(abbreviate).isSameAs(text);
    
    abbreviate = text.abbreviate(9);
    assertThat(abbreviate).isNotSameAs(text);
    assertThat(abbreviate.length()).isEqualTo(9);
    assertThat(abbreviate.parts()).isNotNull().hasSize(2);
    assertThat(abbreviate.parts().get(0).text()).isEqualTo("Hello");
    assertThat(abbreviate.parts().get(0).style()).isEqualTo(BLUE);
    assertThat(abbreviate.parts().get(1).text()).isEqualTo("Worl");
    assertThat(abbreviate.parts().get(1).style()).isEqualTo(RED);

    abbreviate = text.abbreviate(6);
    assertThat(abbreviate.length()).isEqualTo(6);
    assertThat(abbreviate.parts()).isNotNull().hasSize(2);
    assertThat(abbreviate.parts().get(0).text()).isEqualTo("Hello");
    assertThat(abbreviate.parts().get(0).style()).isEqualTo(BLUE);
    assertThat(abbreviate.parts().get(1).text()).isEqualTo("W");
    assertThat(abbreviate.parts().get(1).style()).isEqualTo(RED);

    
    abbreviate = text.abbreviate(5);
    assertThat(abbreviate.length()).isEqualTo(5);
    assertThat(abbreviate.parts()).isNotNull().hasSize(1);
    assertThat(abbreviate.parts().get(0).text()).isEqualTo("Hello");
    assertThat(abbreviate.parts().get(0).style()).isEqualTo(BLUE);

    abbreviate = text.abbreviate(4);
    assertThat(abbreviate.length()).isEqualTo(4);
    assertThat(abbreviate.parts()).isNotNull().hasSize(1);
    assertThat(abbreviate.parts().get(0).text()).isEqualTo("Hell");
    assertThat(abbreviate.parts().get(0).style()).isEqualTo(BLUE);
    
    abbreviate = text.abbreviate(0);
    assertThat(abbreviate.length()).isEqualTo(0);
    assertThat(abbreviate.parts()).isNotNull().hasSize(1);
    assertThat(abbreviate.parts().get(0).text()).isEqualTo("");
    assertThat(abbreviate.parts().get(0).style()).isEqualTo(BLUE);
    
    assertThatThrownBy(() -> text.abbreviate(-1)).isInstanceOf(IllegalArgumentException.class);
  }

}
